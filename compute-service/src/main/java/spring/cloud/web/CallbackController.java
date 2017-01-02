package spring.cloud.web;

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
//		ServiceInstance instance = client.getLocalServiceInstance();
//		Thread.sleep(6000);
		i ++;
		if(i>5){
			System.out.println("data====>" + data);
			return data;
		}else{
			throw new Exception();
		}
	}

}
