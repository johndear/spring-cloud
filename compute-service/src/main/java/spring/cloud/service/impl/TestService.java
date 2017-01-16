package spring.cloud.service.impl;

import org.springframework.stereotype.Service;

import spring.cloud.service.ICountService;

import com.alibaba.fastjson.JSONObject;

@Service("fun_test")
public class TestService implements ICountService{
	
    public Object invoke(JSONObject newJson){
    	return 22;
	}
}
