package spring.cloud.mapper.ds2;

import java.util.List;
import java.util.Map;

public interface ApiInvokeRecordMapper {
	
	List<Map<String,Object>> queryApiInvokeRecord(Map<String,Object> params);
	
	void insert(Map<String,Object> params);
	
	void update(Map<String,Object> params);
}