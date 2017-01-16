package spring.cloud.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	Logger logger = Logger.getLogger(TestController.class);

	@Autowired
	ExtractAmountApi extractAmountApi;
	
//	{
//		callId: '',
//	    callbackUrl:'',
//		callFunctions: [{
//			functionCallId: '',
//			function: '',
//			params: [{
//				name: '',
//				value: ''
//			}]
//		}]
//	}

	@RequestMapping("/amount/test")
	public String test(HttpServletRequest request, String json){
		json= "{"+
				"callId: 'c_01'," + 
				"callbackUrl: 'http://localhost:2223/callback',"+
				"callFunctions: [{"+
					"functionCallId: 'f1',"+
					"function:\"fun_test\","+
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
		
		return extractAmountApi.compute(request, json);
	}

}
