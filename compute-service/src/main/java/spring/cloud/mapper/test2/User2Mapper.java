package spring.cloud.mapper.test2;

import java.util.Map;

public interface User2Mapper {
	
	Map<String,Object> getOne(Long id);
}