package spring.cloud.async;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import spring.cloud.mapper.ds2.ApiInvokeRecordMapper;
import spring.cloud.utils.HttpUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class AsyncThread extends Thread{
	
	Logger logger = Logger.getLogger(AsyncThread.class);
	
	ApiInvokeRecordMapper apiInvokeRecordMapper;
	
	String ip;
	String url;
	JSONObject jsonObj;
	boolean isJob;
	
	public AsyncThread(ApiInvokeRecordMapper apiInvokeRecordMapper, String ip, String url, JSONObject jsonObj, boolean isJob){
		this.apiInvokeRecordMapper = apiInvokeRecordMapper;
		this.ip = ip;
		this.url = url;
		this.jsonObj = jsonObj;
		this.isJob = isJob;
	}
	
	@Override
	public void run() {
		boolean apiSuccess = false;
		String apiResult = "";
		String callbackResult = "";
		Calendar calender = Calendar.getInstance();
		long start = calender.getTimeInMillis();
		
		try {
			// 调用计算方法
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put(jsonObj.getString("callId"), (Map<String, Object>) AsyncTaskExecutor.excute(jsonObj.getString("callFunctions")));
			apiResult = JSON.toJSONString(resultMap);
			long end = Calendar.getInstance().getTimeInMillis();
			logger.info("计算结果为:" + apiResult + ", 计算时长为:" + (end - start) + "毫秒.");
			
			// 每隔2秒重试1次，共3次
			int count = 0;
			do {
				count++;
				// 调用回调接口
				callbackResult = HttpUtil.post(jsonObj.getString("callbackUrl"), JSON.toJSONString(resultMap));
				System.out.println("callback" + count + " result:" + callbackResult);
				if(StringUtils.isNotEmpty(callbackResult) && "2000".equals(JSONObject.parseObject(callbackResult).getString("code"))){
					apiSuccess = true;
					break;
				}
				
				try {
					Thread.sleep(2 * 1000L);
				} catch (InterruptedException e) {
				}
			} while(count < 3);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if(!isJob){
				// 记录调用日志
				Map<String,Object> insertParams = new HashMap<String, Object>();
				insertParams.put("ip", ip);
				insertParams.put("callId", jsonObj.getString("callId"));
				insertParams.put("requestUrl", url);
				insertParams.put("requestParams", jsonObj.toJSONString());
				insertParams.put("callbackUrl", jsonObj.getString("callbackUrl"));
				insertParams.put("requestTime", calender.getTime());
				insertParams.put("responseTime", Calendar.getInstance().getTime());
				insertParams.put("responseTimelong", Double.valueOf((Calendar.getInstance().getTimeInMillis() - start)/1000 +"."+ (Calendar.getInstance().getTimeInMillis() - start)%1000));
				insertParams.put("callbackSuccess", apiSuccess);
				insertParams.put("callbackResult", callbackResult);
				insertParams.put("responseResult", apiResult);
				apiInvokeRecordMapper.insert(insertParams);
			} else{
				// 更新调用日志
				Map<String,Object> updateParams = new HashMap<String, Object>();
				updateParams.put("callId", jsonObj.getString("callId"));
				updateParams.put("responseTime", Calendar.getInstance().getTime());
				updateParams.put("responseTimelong", Double.valueOf((Calendar.getInstance().getTimeInMillis() - start)/1000 +"."+ (Calendar.getInstance().getTimeInMillis() - start)%1000));
				updateParams.put("callbackSuccess", apiSuccess);
				updateParams.put("callbackResult", callbackResult);
				updateParams.put("responseResult", apiResult);
				apiInvokeRecordMapper.update(updateParams);
			}
		}
				
	}
}
