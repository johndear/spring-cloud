package spring.cloud.service;

import com.alibaba.fastjson.JSONObject;

public interface ICountService {
	
	Object invoke(JSONObject newJson) throws Exception;

}
