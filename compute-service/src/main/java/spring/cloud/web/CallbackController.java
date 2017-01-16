package spring.cloud.web;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallbackController {
	
	@Autowired
    private DiscoveryClient client;
	
	int i = 0;
	
	@RequestMapping(method=RequestMethod.POST, path="/callback")
	public String test(String data) throws Exception{
		System.out.println("data====>" + data);
		int result = new Random().nextInt(10);
		if(result > 5){
			return "{code:2000}";
		}else{
			return data;
		}
	}

}
