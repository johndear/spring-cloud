package spring.cloud.simple;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;

import spring.cloud.dao.UserMapper;

public class CountService implements Callable {
	
    private UserMapper userMapper;
    private String id;
    private String type;
    private String datasource;
    private String method;
    private String startDate;
    private String endDate;
    private String name;
	
    private long waitTime; 
    public CountService(String name, int timeInMillis, UserMapper userMapper,String id, String type, String datasource, String method, String startDate, String endDate){ 
        this.name=name;
    	this.waitTime=timeInMillis;
        this.userMapper = userMapper;
        this.id=id;
        this.type=type;
        this.datasource=datasource;
        this.method=method;
        this.startDate=startDate;
        this.endDate=endDate;
    }
    @Override
    public String call() throws Exception {
    	borrowAmount(name, id, type, datasource, method, startDate, endDate);
    	//        Thread.sleep(waitTime);
        //return the thread name executing this callable task
        return Thread.currentThread().getName();
    }

    public int borrowAmount(String name, String id, String type, String datasource, String method, String startDate, String endDate){
		Integer amount = 0;
		// 单向：借、贷
		if("1".equals(type) || "2".equals(type)){
			if("1".equals(method)){
				amount = userMapper.sumCurrentSubject(id, type, datasource, startDate, endDate);
			}else{
				amount = userMapper.sumContainChildrenSubject(id, type, datasource, startDate, endDate);
			}
			
		// 双向：借减贷、贷减借
		}else{
			// 借减贷
			int a,b;
			if("1".equals(method)){
				a = userMapper.sumCurrentSubject(id, "1", datasource, startDate, endDate);
				b = userMapper.sumCurrentSubject(id, "2", datasource, startDate, endDate);
			}else{
				a = userMapper.sumContainChildrenSubject(id, "1", datasource, startDate, endDate);
				b = userMapper.sumContainChildrenSubject(id, "2", datasource, startDate, endDate);
			}
			
			amount = a - b;
			// 贷减借
			if("4".equals(type)){
				amount = amount * -1;
			}

		}
		
		System.out.println(name+"，aaa=======" + amount);
		return amount;
		
	}
 
}
