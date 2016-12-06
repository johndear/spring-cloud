package spring.cloud.simple;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.cloud.async.MyAsyncTaskExecutor;

@RestController
public class TestController22 {

	@Autowired
	CountService countService;
	
	@RequestMapping("/amount/batch22")
	public void amount(String json){
		json = "[{itemId:\"1001\",type:\"1\",datasource:\"0\",method:\"1\",startDate:\"2015\",endDate:\"2017-12-01\"},{itemId:\"4001\",type:\"1\",datasource:\"0\",method:\"1\",startDate:\"2015\",endDate:\"2017-12-01\"},{itemId:\"1001\",type:\"1\",datasource:\"0\",method:\"1\",startDate:\"2015\",endDate:\"2017-12-01\"},{itemId:\"1001\",type:\"1\",datasource:\"0\",method:\"1\",startDate:\"2015\",endDate:\"2017-12-01\"}]";

		long start = Calendar.getInstance().getTimeInMillis();

		new MyAsyncTaskExecutor().excute(json, countService);

		long end = Calendar.getInstance().getTimeInMillis();
		System.out.println("take time:" + (end-start));
	}
	

}
