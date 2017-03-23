package spring.cloud.service.impl;

import org.springframework.stereotype.Service;

import spring.cloud.service.Computable;

import com.alibaba.fastjson.JSONObject;

@Service("fun_test")
public class TestService implements Computable{
	
    public Object compute(JSONObject newJson){
    	return 22;
	}
}
