package spring.cloud.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.cloud.dao.UserMapper;

@RestController
public class TestController {
	
	@Autowired
    private UserMapper userMapper;
	
	@RequestMapping("/likeName")
	public void aa(){
		System.out.println("aaa=======" + userMapper.findUserInfo());
		// 科目     提取方向       数据来源     汇总方法     核算有效期
		
		// 借
//		SELECT SUM(amount) FROM journalizing_info WHERE item_id=1001 AND TYPE=1 AND data_source=0; -- 汇总科目金额
//		SELECT SUM(amount) FROM journalizing_info WHERE item_id LIKE '1001%' AND TYPE=1 AND data_source=0; -- 汇总科目及其子科目金额
		
		// 贷
//		SELECT SUM(amount) FROM journalizing_info WHERE item_id=1001 AND TYPE=1 AND data_source=0; -- 汇总科目金额
//		SELECT SUM(amount) FROM journalizing_info WHERE item_id LIKE '1001%' AND TYPE=1 AND data_source=0; -- 汇总科目及其子科目金额
		
		// 借减贷
//		SELECT SUM(amount) FROM journalizing_info WHERE item_id=1001 AND TYPE=1 AND data_source=0;
//		SELECT SUM(amount) FROM journalizing_info WHERE item_id=1001 AND TYPE=1 AND data_source=0;
		
//		SELECT SUM(amount) FROM journalizing_info WHERE item_id LIKE '1001%' AND TYPE=1 AND data_source=0;
//		SELECT SUM(amount) FROM journalizing_info WHERE item_id LIKE '1001%' AND TYPE=1 AND data_source=0;
		
		// 贷减借
		
		
		
		
	}

}
