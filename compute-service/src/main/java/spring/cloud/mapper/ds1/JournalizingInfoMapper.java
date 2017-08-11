package spring.cloud.mapper.ds1;

import org.apache.ibatis.annotations.Param;

public interface JournalizingInfoMapper {
	
	/**
	 * 汇总科目金额
	 * @return
	 */
	public double sumCurrentSubject(@Param("id") String id, @Param("companyId") String companyId, @Param("type") Short type, @Param("datasource") String datasource,@Param("startDate") String startDate,@Param("endDate") String endDate);
	
	/**
	 * 汇总科目及其子科目金额 
	 * @return
	 */
	public double sumContainChildrenSubject(@Param("id") String id, @Param("companyId") String companyId, @Param("type") Short type, @Param("datasource") String datasource,@Param("startDate") String startDate,@Param("endDate") String endDate);
	
	public void insertApiHistoryRecord(@Param("ip") String ip);

}