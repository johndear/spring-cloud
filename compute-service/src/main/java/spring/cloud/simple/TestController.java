package spring.cloud.simple;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.cloud.dao.UserMapper;
import spring.cloud.future.example1.MyCallable;

@RestController
public class TestController {
	
	public enum FundType{
		DEBIT("借"), 
		CREDIT("贷"),
		DEBIT_CREDIT("借减贷"),
		CREDIT_DEBIT("贷减借");
		
		private String description;
		
		private FundType(String description){
			this.description = description;
		}
	} 
	
	@Autowired
    private UserMapper userMapper;
	
	/**
	 * 
	 * @param id
	 * @param type 1：借 2：贷 3：借减贷 4：贷减借
	 * @param datasource
	 * @param method 1:汇总科目金额 2:汇总科目及其子科目金额
	 * @param startDate
	 * @param endDate
	 * 统计维度：科目     提取方向     数据来源     汇总方法     核算有效期
	 */
	@RequestMapping("/amount/get")
	public int borrowAmount(String id, String type, String datasource, String method, String startDate, String endDate){
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
		
		System.out.println("aaa=======" + amount);
		return amount;
		
	}
	
	
	@RequestMapping("/amount/batch")
	public long amount(String json){
		long start = Calendar.getInstance().getTimeInMillis();
////		String id, String type, String datasource, String method, String startDate, String endDate
////		JSONarray
//		for (int i = 0; i < 1000; i++) {
////			 borrowAmount("1001", "1", "0", "1", "2015", "2016-12-10");
//			System.out.println("i="+i +", money:"+ borrowAmount("1001", "1", "0", "1", "2015", "2016-12-10"));
//		}
//		
//		long end = Calendar.getInstance().getTimeInMillis();
//		return end-start;
		
		long end = this.aa();

	       return end-start;
	       
	}
	
	public long aa(){
		long end = 0;
		
		 ExecutorService executor = Executors.newFixedThreadPool(200); 
		 
		 List<FutureTask> list = new ArrayList<FutureTask>();
		 for (int i = 0; i < 1000; i++) {
			 CountService callable2 = new CountService("BBBB"+i,2000, userMapper, "1001", "1", "0", "1", "2015", "2016-12-10");
//			System.out.println("i="+i +", money:"+ borrowAmount("1001", "1", "0", "1", "2015", "2016-12-10"));
			 FutureTask futureTask1 = new FutureTask(callable2); 
			 
			 executor.execute(futureTask1);
			 list.add(futureTask1);
		}

		 end = 0L;
		 int i=0;
	       while (true) {
	           try {
	        	   for (FutureTask futureTask : list) {
	        		   if(futureTask.isDone()){
	        			   i++;
	        			   list.remove(futureTask);
	        		   }
				   }
	        	   
	        	   if(list.size()==0){
	        		   System.out.println("Done");
	        		   executor.shutdown();
	        		   end = Calendar.getInstance().getTimeInMillis();
	        		   break;
	        	   }
	        	   
//	               if(futureTask1.isDone() && futureTask2.isDone()){//  两个任务都完成
//	                   System.out.println("Done");
//	                   executor.shutdown();                          // 关闭线程池和服务 
//	                   return;
//	               }
//	                
//	               if(!futureTask1.isDone()){ // 任务1没有完成，会等待，直到任务完成
//	                   System.out.println("FutureTask1 output="+futureTask1.get());
//	               }
//	                
//	               System.out.println("Waiting for FutureTask2 to complete");
//	               String s = (String) futureTask2.get(200L, TimeUnit.MILLISECONDS);
//	               if(s !=null){
//	                   System.out.println("FutureTask2 output="+s);
//	               }
	        	   
	           } catch (Exception e){
	               //do nothing
	           }
	       }
	       
	       System.out.println("i=====" + i);
	       return end;
	}
	

}
