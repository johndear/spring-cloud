package spring.cloud.web;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import spring.cloud.async.MyAsyncTaskExecutor;
import spring.cloud.simple.ICountService;

@RestController
public class TestController22 {

//	@Autowired
	ICountService countService;
	
	@RequestMapping("/amount/batch")
	public void amount(String json){
		json = "[{itemId:\"4001\",type:\"1\",datasource:\"0\",method:\"1\",startDate:\"2015\",endDate:\"2017-12-01\",function:\"count22\"},{itemId:\"4001\",type:\"1\",datasource:\"0\",method:\"1\",startDate:\"2015\",endDate:\"2017-12-01\",function:\"count\"}]";

		long start = Calendar.getInstance().getTimeInMillis();

		new MyAsyncTaskExecutor().excute(json);

		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("take time:" + (end-start));
	}
	
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

	
	@RequestMapping("/amount/batch22")
	public void amount22(String json){
		json = "[{itemCode:\"4001\",type:\"1\",datasource:\"0\",method:\"1\",startDate:\"2015\",endDate:\"2017-12-01\"},{itemCode:\"4001\",type:\"1\",datasource:\"0\",method:\"1\",startDate:\"2015\",endDate:\"2017-12-01\"},{itemCode:\"4001\",type:\"1\",datasource:\"0\",method:\"1\",startDate:\"2015\",endDate:\"2017-12-01\"},{itemCode:\"4001\",type:\"1\",datasource:\"0\",method:\"1\",startDate:\"2015\",endDate:\"2017-12-01\"}]";

		long start = Calendar.getInstance().getTimeInMillis();

		new MyAsyncTaskExecutor().excute(json);

		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("take time:" + (end-start));
	}
	
	@RequestMapping("/amount/batch33")
	public void aa(String json){
		json= "{"+
				"callId: '111',"+
				"callFunctions: [{"+
					"functionCallId: '',"+
					"function:\"acount\","+
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
					"functionCallId: '',"+
					"function:\"acount\","+
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

		new MyAsyncTaskExecutor().excute(jsonObj.getString("callFunctions"));

		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("take time:" + (end-start));
		
	}
	

}
