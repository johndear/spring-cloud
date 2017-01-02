package spring.cloud.web;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.cloud.async.MyAsyncTaskExecutor;
import spring.cloud.mapper.test1.User1Mapper;
import spring.cloud.mapper.test2.User2Mapper;
import spring.cloud.simple.ICountService;
import spring.cloud.utils.HttpUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@RestController
public class TestController {
	
	Logger logger = Logger.getLogger(TestController.class);

	ICountService countService;
	
	@Autowired
	private User1Mapper user1Mapper;
	
	@Autowired
	private User2Mapper user2Mapper;
	
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
		Map<String,Object> re = user2Mapper.getOne(1L);
		Map<String,Object> insertParams = new HashMap<String, Object>();
		insertParams.put("id", 2);
		insertParams.put("name", "liusu");
		insertParams.put("description", "liusu");
		user2Mapper.insert(insertParams);
		
		
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
		logger.info("compute result:" + JSON.toJSONString(resultMap) + ", take timeInMillis:" + (end-start));
		
		// 每隔5分钟回调1次，共重试3次
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("data", JSON.toJSONString(resultMap));
		int i = 3;
		String html = "调用回调接口失败！";
		while(i-->0){
			html = HttpUtil.post(jsonObj.getString("callbackUrl"), params);;
			if(StringUtils.isNotEmpty(html)){
				logger.info("callback result:" + html);
				break;
			}
			
			try {
				Thread.sleep(5 * 60 * 1000);
			} catch (InterruptedException e) {
			}
		}
		
		String callbackResult = "参数" + json + ", 回调结果：" + 123;
		logger.debug(callbackResult);
		return callbackResult;
	}

}
