package spring.cloud.service;

import com.alibaba.fastjson.JSONObject;

public interface Computable {
	
	Object compute(JSONObject newJson) throws Exception;

}
