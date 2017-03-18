package spring.cloud.async;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import spring.cloud.service.ICountService;
import spring.cloud.web.SpringUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 可以参考：http://blog.csdn.net/wxwzy738/article/details/8497853
 * @author Administrator
 *
 */
public class AsyncTaskExecutor {

	static ExecutorService executor = Executors.newFixedThreadPool(20); 
	
	@SuppressWarnings("unchecked")
	public static Object excute(String json) throws Exception {
		CompletionService<Object> completionService = new ExecutorCompletionService<Object>(executor);

		JSONArray callFunctions = JSONArray.parseArray(json);
		for (int i = 0; i < callFunctions.size(); i++) {
            final JSONObject callFunction = (JSONObject) callFunctions.get(i);
    		completionService.submit(new Callable<Object>() {
    			
    			public Object call() throws Exception {
//					 	Thread.currentThread().sleep(new Random().nextInt(5000));
    				String functionCallId = callFunction.getString("functionCallId");
    				String function = callFunction.getString("function");
    				ICountService countService = (ICountService) SpringUtil.getBean(function);
    				
    				Map<String, Object> partResultMap = new HashMap<String, Object>();
    				partResultMap.put(functionCallId, countService.invoke(callFunction));
    				return partResultMap;
    			}
    		});
	    }
		 
		// 汇总计算结果
		Map<String, Object> allResultMap = new HashMap<String, Object>();
		for (int i = 0; i < callFunctions.size(); i++) {
			allResultMap.putAll((Map<? extends String, ? extends Object>) completionService.take().get());
		}
		
		return allResultMap;
		
	}

}
