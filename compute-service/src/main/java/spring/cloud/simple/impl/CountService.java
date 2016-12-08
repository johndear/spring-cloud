package spring.cloud.simple.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.cloud.dao.UserMapper;
import spring.cloud.simple.ICountService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service("count")
public class CountService implements ICountService{
	
	@Autowired
    private UserMapper userMapper;
	
    public Object invoke(JSONObject newJson){
    	final String params = newJson.getString("params");
    	JSONArray newAccountInfo = JSONArray.parseArray(params);
    	
    	Map<String,String> paramsMap = new HashMap<String, String>();
    	for (int i = 0; i < newAccountInfo.size(); i++) {
    		JSONObject jsonObj = (JSONObject) newAccountInfo.get(i);
    		String name = jsonObj.getString("name");
    		String value = jsonObj.getString("value");
    		paramsMap.put(name,value);
    	}
    	
    	final String itemId = paramsMap.get("itemId");
        final String type = paramsMap.get("type");
        final String datasource = paramsMap.get("datasource");
        final String startDate = paramsMap.get("startDate");
        final String endDate = paramsMap.get("endDate");
		final String method = paramsMap.get("method");
		
		return borrowAmount(itemId, type, datasource, method, startDate, endDate);
	}
    
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
		
		return amount;
		
	}

}
