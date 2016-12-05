package spring.cloud.dao;

import org.apache.ibatis.annotations.Mapper;

//@Mapper
public interface UserMapper {

	public Integer findUserInfo();
	
}
