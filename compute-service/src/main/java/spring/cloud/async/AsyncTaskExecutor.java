package spring.cloud.async;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import spring.cloud.service.Computable;
import spring.cloud.web.SpringUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 异步计算类
 * @author liusu
 *
 */
public class AsyncTaskExecutor {

	static ExecutorService executor = Executors.newFixedThreadPool(20); 
	
	@SuppressWarnings("unchecked")
	public static Object excute(String json) throws Exception {
		CompletionService<Object> completionService = new ExecutorCompletionService<Object>(executor);

		// 提交异步计算
		JSONArray callFunctions = JSONArray.parseArray(json);
		for (int i = 0; i < callFunctions.size(); i++) {
            final JSONObject callFunction = (JSONObject) callFunctions.get(i);
            final String functionCallId = callFunction.getString("functionCallId");
            String function = callFunction.getString("function");
            final Computable computable = (Computable) SpringUtil.getBean(function);

            completionService.submit(new Callable<Object>() {
    			public Object call() throws Exception {
//					 	Thread.currentThread().sleep(new Random().nextInt(5000));
    				Map<String, Object> partResultMap = new HashMap<String, Object>();
    				partResultMap.put(functionCallId, computable.compute(callFunction));
    				return partResultMap;
    			}
    		});
	    }
		 
		// 汇总计算结果
		Map<String, Object> allResultMap = new HashMap<String, Object>();
		for (int i = 0; i < callFunctions.size(); i++) {
			Map<String, Object> partResultMap = (Map<String, Object>) completionService.take().get();
			allResultMap.putAll(partResultMap);
		}
		
		return allResultMap;
		
	}

}
