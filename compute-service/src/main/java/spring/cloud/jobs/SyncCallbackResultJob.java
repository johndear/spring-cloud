package spring.cloud.jobs;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.cloud.async.AsyncThread;
import spring.cloud.mapper.ds2.ApiInvokeRecordMapper;
import spring.cloud.utils.HttpUtil;

import com.alibaba.fastjson.JSONObject;

@Component
public class SyncCallbackResultJob {
	
	@Autowired
	private ApiInvokeRecordMapper apiInvokeRecordMapper;
	
	// 启动时调用一次，然后每间隔10分钟调用一次
	private int period = 10 * 60 * 1000;
	
	public void excute(){
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// 回调次数不超过10次
				Map<String,Object> params = new HashMap<String, Object>();
				List<Map<String,Object>> list = apiInvokeRecordMapper.queryApiInvokeRecord(params);
				for (Map<String, Object> map : list) {
					JSONObject jsonObj = JSONObject.parseObject(String.valueOf(map.get("request_params")));
					String responseResult = String.valueOf(map.get("response_result"));
					String callbackUrl = String.valueOf(map.get("callback_url"));
					// 1、是否有计算结果，没有计算结果，需要重新计算
					if(StringUtils.isEmpty(responseResult)){
						// 异步调用
						new AsyncThread(apiInvokeRecordMapper, "127.0.0.1", "localhost", jsonObj, true).start();
					}else{
						// 2、有计算结果，只需要推送，调用回调接口
						boolean apiSuccess = false;
						String callbackResult = HttpUtil.post(callbackUrl, responseResult);
						// 更新回调状态
						Map<String,Object> updatearams = new HashMap<String, Object>();
						updatearams.put("callId", jsonObj.getString("callId"));
						if(StringUtils.isNotEmpty(callbackResult) && "2000".equals(JSONObject.parseObject(callbackResult).getString("code"))){
							apiSuccess = true;
							updatearams.put("callbackSuccess", apiSuccess);
							updatearams.put("callbackResult", callbackResult);
						}
						// 更新回调次数
						apiInvokeRecordMapper.update(updatearams);
					}
				}
			}
			
		}, new Date(), period);
	}
	
}
