package com.hui10.app.dao.acquirerhschannel;

import org.apache.ibatis.annotations.Param;

import com.hui10.app.model.acquirer.AcquirerHschannel;

/**
 * @ClassName: AcquirerHschannelDao.java
 * @Description:
 * @author zhangll
 * @date 2017年11月15日 下午2:49:17
 */
public interface AcquirerHschannelDao {
	
	AcquirerHschannel queryHschannel(@Param("acquirerno")String acquirerno,@Param("provinceid")String provinceid);
	
	AcquirerHschannel queryHschannelByMercid(@Param("channelmercid")String channelmercid);
	
	Integer addHschannel(AcquirerHschannel hschannel);
	
	Integer deleteHschannel(@Param("id") Integer id);
	
	AcquirerHschannel queryHschannelById(@Param("id") String id);

}
