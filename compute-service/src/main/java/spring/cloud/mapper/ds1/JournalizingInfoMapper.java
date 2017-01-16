package spring.cloud.mapper.ds1;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface JournalizingInfoMapper {
	
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
	
	public void insertApiHistoryRecord(@Param("ip") String ip);

}