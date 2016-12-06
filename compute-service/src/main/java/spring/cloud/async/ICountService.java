package spring.cloud.async;

import com.alibaba.fastjson.JSONObject;

public interface ICountService {
	
	Object invoke(JSONObject newJson);

}
