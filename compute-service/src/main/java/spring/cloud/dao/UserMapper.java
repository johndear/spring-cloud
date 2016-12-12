package spring.cloud.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

	/**
	 * 汇总科目金额
	 * @return
	 */
	public Integer sumCurrentSubject(@Param("id") String id, @Param("companyId") String companyId, @Param("type") String type, @Param("datasource") String datasource,@Param("startDate") String startDate,@Param("endDate") String endDate);
	
	/**
	 * 汇总科目及其子科目金额 
	 * @return
	 */
	public Integer sumContainChildrenSubject(@Param("id") String id, @Param("companyId") String companyId, @Param("type") String type, @Param("datasource") String datasource,@Param("startDate") String startDate,@Param("endDate") String endDate);
	
}
