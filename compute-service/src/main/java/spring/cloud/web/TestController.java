//package spring.cloud.web;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.Callable;
//import java.util.concurrent.CompletionService;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorCompletionService;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.FutureTask;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import spring.cloud.dao.UserMapper;
//
//@RestController
//public class TestController {
//	
//	public enum FundType{
//		DEBIT("借"), 
//		CREDIT("贷"),
//		DEBIT_CREDIT("借减贷"),
//		CREDIT_DEBIT("贷减借");
//		
//		private String description;
//		
//		private FundType(String description){
//			this.description = description;
//		}
//	} 
//	
//	@Autowired
//    private UserMapper userMapper;
//	
//	@RequestMapping("/amount/batch")
//	public void amount(String json){
//		long start = Calendar.getInstance().getTimeInMillis();
//////		String id, String type, String datasource, String method, String startDate, String endDate
//		
////		for (int i = 0; i < 1000; i++) {
//////			 borrowAmount("1001", "1", "0", "1", "2015", "2016-12-10");
////			System.out.println("i="+i +", money:"+ borrowAmount("1001", "1", "0", "1", "2015", "2016-12-10"));
////		}
//		
//		this.aa();
//		
//		long end = Calendar.getInstance().getTimeInMillis();
//	       System.out.println("take time:" + (end-start));
//	}
//	
//	public long aa(){
//		long end = 0;
//		
//		 ExecutorService executor = Executors.newFixedThreadPool(10); 
//		 
//		 List<FutureTask> list = new ArrayList<FutureTask>();
//		 for (int i = 0; i < 1000; i++) {
//			 CountService callable2 = new CountService("BBBB"+i,2000, userMapper, "1001", "1", "0", "1", "2015", "2016-12-10");
////			System.out.println("i="+i +", money:"+ borrowAmount("1001", "1", "0", "1", "2015", "2016-12-10"));
//			 FutureTask futureTask1 = new FutureTask(callable2); 
//			 
//			 executor.execute(futureTask1);
//			 list.add(futureTask1);
//		}
//
//		 end = 0L;
//		 int i=0;
//	       while (true) {
//	           try {
//	        	   for (FutureTask futureTask : list) {
//	        		   if(futureTask.isDone()){
//	        			   i++;
//	        			   list.remove(futureTask);
//	        		   }
//				   }
//	        	   
//	        	   if(list.size()==0){
//	        		   System.out.println("Done");
//	        		   executor.shutdown();
//	        		   end = Calendar.getInstance().getTimeInMillis();
//	        		   break;
//	        	   }
//	        	   
////	               if(futureTask1.isDone() && futureTask2.isDone()){//  两个任务都完成
////	                   System.out.println("Done");
////	                   executor.shutdown();                          // 关闭线程池和服务 
////	                   return;
////	               }
////	                
////	               if(!futureTask1.isDone()){ // 任务1没有完成，会等待，直到任务完成
////	                   System.out.println("FutureTask1 output="+futureTask1.get());
////	               }
////	                
////	               System.out.println("Waiting for FutureTask2 to complete");
////	               String s = (String) futureTask2.get(200L, TimeUnit.MILLISECONDS);
////	               if(s !=null){
////	                   System.out.println("FutureTask2 output="+s);
////	               }
//	        	   
//	           } catch (Exception e){
//	               //do nothing
//	           }
//	       }
//	       
//	       System.out.println("i=====" + i);
//	       return end;
//	}
//	
//	
//
//}
