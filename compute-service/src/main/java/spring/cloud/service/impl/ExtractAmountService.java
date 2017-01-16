package spring.cloud.service.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.cloud.dto.AmountExtractDirectionEnum;
import spring.cloud.dto.CollectTypeEnum;
import spring.cloud.mapper.ds1.JournalizingInfoMapper;
import spring.cloud.service.ICountService;
import spring.cloud.utils.DateUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service("fun_extract_amount")
public class ExtractAmountService implements ICountService{
	
	@Autowired
    private JournalizingInfoMapper userMapper;
	
    public Object invoke(JSONObject newJson) throws Exception{
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
    	final String companyId = paramsMap.get("companyId");
        final String extractType = paramsMap.get("extractType");
        final String dataSource = paramsMap.get("dataSource");
        final String collectType = paramsMap.get("collectType");
        int startDateOffset = 0;
        int endDateOffset = 0;
        if(StringUtils.isNotEmpty(paramsMap.get("startDateOffset"))){
        	startDateOffset = Integer.parseInt(paramsMap.get("startDateOffset"));
        }
        if(StringUtils.isNotEmpty(paramsMap.get("endDateOffset"))){
        	endDateOffset = Integer.parseInt(paramsMap.get("endDateOffset"));
        }
        String startDate = null;
        String endDate = null;
		try {
			startDate = DateUtil.getDateByDifferDays(paramsMap.get("startDate"), startDateOffset);
			endDate =  DateUtil.getDateByDifferDays(paramsMap.get("endDate"), endDateOffset);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return borrowAmount(itemId, companyId, extractType, dataSource, collectType, startDate, endDate);
	}
    
    /**
	 * 
	 * @param id
	 * @param extractType-提取方向： 0：借 1：贷 2：借减贷 3：贷减借
	 * @param datasource 0:普通核算分录 1：政策生成分录 2：全分录
	 * @param collectType-汇总方法： 0:汇总科目及其子科目金额  1:汇总科目金额 
	 * @param startDate
	 * @param endDate
	 * 统计维度：科目     提取方向     数据来源   汇总方法     核算有效期
     * @throws Exception 
	 */
	public int borrowAmount(String id, String companyId, String extractTypeStr, String datasource, String collectTypeStr, String startDate, String endDate) throws Exception{
		AmountExtractDirectionEnum amountExtractDirection = AmountExtractDirectionEnum.getEnum(extractTypeStr);
		if(amountExtractDirection == null){
			throw new Exception("目前只支持【借、贷 、借减贷、贷减借】提取方向");
		}
		CollectTypeEnum collectType = CollectTypeEnum.getEnum(collectTypeStr);
		if(collectType == null){
			throw new Exception("目前只支持【汇总科目金额、汇总科目及其子科目金额】汇总方法 ");
		}
		
		Integer amount = 0;
		// 单向：借、贷
		if(AmountExtractDirectionEnum.DEBIT == amountExtractDirection || AmountExtractDirectionEnum.CREDIT == amountExtractDirection){
			if(CollectTypeEnum.TYPE0 == collectType){
				amount = userMapper.sumCurrentSubject(id, companyId, amountExtractDirection.getValue(), datasource, startDate, endDate);
			}else{
				amount = userMapper.sumContainChildrenSubject(id, companyId, amountExtractDirection.getValue(), datasource, startDate, endDate);
			}
			
		// 双向：借减贷、贷减借
		}else{
			// 借减贷
			int a,b;
			if(CollectTypeEnum.TYPE0 == collectType){
				a = userMapper.sumCurrentSubject(id, companyId, AmountExtractDirectionEnum.DEBIT.getValue(), datasource, startDate, endDate);
				b = userMapper.sumCurrentSubject(id, companyId, AmountExtractDirectionEnum.CREDIT.getValue(), datasource, startDate, endDate);
			}else{
				a = userMapper.sumContainChildrenSubject(id, companyId, AmountExtractDirectionEnum.DEBIT.getValue(), datasource, startDate, endDate);
				b = userMapper.sumContainChildrenSubject(id, companyId, AmountExtractDirectionEnum.CREDIT.getValue(), datasource, startDate, endDate);
			}
			amount = a - b;
			
			// 贷减借
			if(AmountExtractDirectionEnum.CREDIT_MINUS_DEBIT == amountExtractDirection){
				amount = amount * -1;
			}

		}
		
		return amount;
		
	}

}
