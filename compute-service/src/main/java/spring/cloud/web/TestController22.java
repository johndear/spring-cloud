package spring.cloud.web;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.cloud.async.MyAsyncTaskExecutor;
import spring.cloud.simple.ICountService;
import spring.cloud.utils.HttpUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@RestController
public class TestController22 {

	ICountService countService;
	
//	{
//		callId: '',
//		callFunctions: [{
//			functionCallId: '',
//			function: '',
//			params: [{
//				name: '',
//				type: '',
//				length: '',
//				value: '',
//				sequence: ''
//			}]
//		}]
//	}

	@RequestMapping("/amount/test")
	public String test(String json){
		json= "{"+
				"callId: 'c_01'," + 
				"callbackUrl: 'http://localhost:2223/callback',"+
				"callFunctions: [{"+
					"functionCallId: 'f1',"+
					"function:\"fun_extract_amount\","+
					"params: [{"+
						"name: 'itemId',"+
						"value: '1001'"+
					"},{"+
						"name: 'companyId',"+
						"value: '13'"+
					"},{"+
						"name: 'extractType',"+
						"value: '1'"+
					"},{"+
						"name: 'dataSource',"+
						"value: '0'"+
					"},{"+
						"name: 'collectType',"+
						"value: '1'"+
					"},{"+
						"name: 'startDate',"+
						"value: '2015-01-01'"+
					"},{"+
						"name: 'startDateOffset',"+
						"value: '5'"+
					"},{"+
						"name: 'endDate',"+
						"value: '2017-12-01'"+
					"},{"+
						"name: 'endDateOffset',"+
						"value: '-1'"+
					"}]"+
				"},{"+
					"functionCallId: 'f2',"+
					"function:\"fun_extract_amount\","+
					"params: [{"+
						"name: 'itemId',"+
						"value: '4001'"+
					"},{"+
						"name: 'companyId',"+
						"value: '13'"+
					"},{"+
						"name: 'extractType',"+
						"value: '1'"+
					"},{"+
						"name: 'dataSource',"+
						"value: '0'"+
					"},{"+
						"name: 'collectType',"+
						"value: '1'"+
					"},{"+
						"name: 'startDate',"+
						"value: '2015-01-01'"+
					"},{"+
						"name: 'startDateOffset',"+
						"value: '5'"+
					"},{"+
						"name: 'endDate',"+
						"value: '2017-12-01'"+
					"},{"+
						"name: 'endDateOffset',"+
						"value: '1'"+
					"}]"
				+ "}]"+
			"}";
		
		JSONObject jsonObj = JSONObject.parseObject(json);
		long start = Calendar.getInstance().getTimeInMillis();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(jsonObj.getString("callId"), (Map<String, Object>) new MyAsyncTaskExecutor().excute(jsonObj.getString("callFunctions")));

		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("compute result:" + JSON.toJSONString(resultMap) + ", take timeInMillis:" + (end-start));
		
		// 每隔5分钟回调1次，共重试3次
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("data", JSON.toJSONString(resultMap));
		int i = 3;
		String html = "调用回调接口失败！";
		while(i-->0){
			html = HttpUtil.post(jsonObj.getString("callbackUrl"), params);;
			if(StringUtils.isNotEmpty(html)){
				System.out.println("callback result:" + html);
				break;
			}
			
			try {
				Thread.sleep(5 * 60 * 1000);
			} catch (InterruptedException e) {
			}
		}
		
		String callbackResult = "参数" + json + ", 回调结果：" + html;
		System.out.println(callbackResult);
		return callbackResult;
	}
	
	@RequestMapping("/amount/get")
	public String bb(String json){
		try {
			if(StringUtils.isEmpty(json)){
				return "json不允许为空！";
			}
			JSONObject jsonObj = JSONObject.parseObject(json);
			long start = Calendar.getInstance().getTimeInMillis();
	
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put(jsonObj.getString("callId"), (Map<String, Object>) new MyAsyncTaskExecutor().excute(jsonObj.getString("callFunctions")));
	
			long end = Calendar.getInstance().getTimeInMillis();
			System.out.println("compute result:" + JSON.toJSONString(resultMap) + ", take timeInMillis:" + (end-start));
			
			// 每隔5分钟回调1次，共重试3次
			int i = 3;
			String html = "调用回调接口失败！";
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("data", JSON.toJSONString(resultMap));
			while(i-->0){
				html = HttpUtil.post(jsonObj.getString("callbackUrl"), params);;
				if(StringUtils.isNotEmpty(html)){
					System.out.println("callback result:" + html);
					break;
				}
				
				try {
					Thread.sleep(5 * 60 * 1000);
				} catch (InterruptedException e) {
				}
			}
			
			String callbackResult = "参数" + json + ", 回调结果：" + html;
			System.out.println(callbackResult);
			return callbackResult;
			
		} catch (Exception e) {
			return "json格式有误,请仔细检查！参考格式如下：<br>"
					+ "{<br>"
					+ "&nbsp;&nbsp;callId: 'c0001',<br>"
					+ "&nbsp;&nbsp;callFunctions: [{<br>"
						+ "&nbsp;&nbsp;&nbsp;&nbsp;functionCallId: 'f0001',<br>"
						+ "&nbsp;&nbsp;&nbsp;&nbsp;function: 'count',<br>"
						+ "&nbsp;&nbsp;&nbsp;&nbsp;params: [{<br>"
							+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;name: 'itemId',<br>"
							+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value: 'xxxx',<br>"
						+ "&nbsp;&nbsp;&nbsp;&nbsp;}]<br>"
					+ "&nbsp;&nbsp;}]<br>"
				+ "}";
		}
	}
	
	@RequestMapping("/amount/batch222222")
	public void amount(String json){
		json = "[{itemId:\"4001\",type:\"1\",datasource:\"0\",method:\"1\",startDate:\"2015\",endDate:\"2017-12-01\",function:\"count22\"},{itemId:\"4001\",type:\"1\",datasource:\"0\",method:\"1\",startDate:\"2015\",endDate:\"2017-12-01\",function:\"count\"}]";

		long start = Calendar.getInstance().getTimeInMillis();

		new MyAsyncTaskExecutor().excute(json);

		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("take time:" + (end-start));
	}
	

}
