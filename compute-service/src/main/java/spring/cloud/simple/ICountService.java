package spring.cloud.simple;

import com.alibaba.fastjson.JSONObject;

public interface ICountService {
	
	Object invoke(JSONObject newJson);

}
