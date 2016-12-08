package spring.cloud.web;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.cloud.async.MyAsyncTaskExecutor;
import spring.cloud.simple.ICountService;

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
				"callId: 'c_01',"+
				"callFunctions: [{"+
					"functionCallId: 'f1',"+
					"function:\"count\","+
					"params: [{"+
						"name: 'itemId',"+
						"value: '1001'"+
					"},{"+
						"name: 'type',"+
						"value: '1'"+
					"},{"+
						"name: 'datasource',"+
						"value: '0'"+
					"},{"+
						"name: 'method',"+
						"value: '1'"+
					"},{"+
						"name: 'startDate',"+
						"value: '2015'"+
					"},{"+
						"name: 'endDate',"+
						"value: '2017-12-01'"+
					"}]"+
				"},{"+
					"functionCallId: 'f2',"+
					"function:\"count\","+
					"params: [{"+
						"name: 'itemId',"+
						"value: '4001'"+
					"},{"+
						"name: 'type',"+
						"value: '1'"+
					"},{"+
						"name: 'datasource',"+
						"value: '0'"+
					"},{"+
						"name: 'method',"+
						"value: '1'"+
					"},{"+
						"name: 'startDate',"+
						"value: '2015'"+
					"},{"+
						"name: 'endDate',"+
						"value: '2017-12-01'"+
					"}]"
				+ "}]"+
			"}";
		
		JSONObject jsonObj = JSONObject.parseObject(json);
		long start = Calendar.getInstance().getTimeInMillis();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(jsonObj.getString("callId"), (Map<String, Object>) new MyAsyncTaskExecutor().excute(jsonObj.getString("callFunctions")));

		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("take timeInMillis:" + (end-start));
		
		return JSON.toJSONString(resultMap);
		
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
			System.out.println("take timeInMillis:" + (end-start));
			
			return JSON.toJSONString(resultMap);
			
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
