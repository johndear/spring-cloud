package spring.cloud.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.cloud.dao.UserMapper;

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
	
	

}
