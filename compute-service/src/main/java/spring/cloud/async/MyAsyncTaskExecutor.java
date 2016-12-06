package spring.cloud.async;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import spring.cloud.simple.ICountService;
import spring.cloud.simple.impl.CountService;
import spring.cloud.web.SpringUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 可以参考：http://blog.csdn.net/wxwzy738/article/details/8497853
 * @author Administrator
 *
 */
public class MyAsyncTaskExecutor {
	
	public void excute(String json){
		ExecutorService executor = Executors.newFixedThreadPool(20); 
		CompletionService<Object> completionService = new ExecutorCompletionService<Object>(executor);

		JSONArray newAccountInfo = JSONArray.parseArray(json);
		for (int i = 0; i < newAccountInfo.size(); i++) {
	            final JSONObject newJson = (JSONObject) newAccountInfo.get(i);
	    		completionService.submit(new Callable<Object>() {
	    			
	    			public Object call() throws Exception {
//					 	Thread.currentThread().sleep(new Random().nextInt(5000));
	    				String function = newJson.getString("function");
	    				ICountService countService = (ICountService) SpringUtil.getBean(function);
	    				return countService.invoke(newJson);
	    			}
	    		});
	        }
		 
		for (int i = 0; i < newAccountInfo.size(); i++) {
			 try {
				System.out.println(JSON.toJSONString(completionService.take().get()));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	}

}
