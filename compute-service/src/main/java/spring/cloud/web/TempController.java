package spring.cloud.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TempController {
	
	int i = 0;
	
	@RequestMapping(method=RequestMethod.POST, path="/callback")
	public String test(String data) throws Exception{
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
