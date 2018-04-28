package com.hui10.app.acquirerhschannel;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hui10.app.TestBase;
import com.hui10.app.model.acquirer.AcquirerHschannel;
import com.hui10.app.model.enums.CityTypeEnum;
import com.hui10.app.model.system.City;
import com.hui10.app.service.acquirerhschannel.AcquirerHschannelService;
import com.hui10.app.service.system.CityService;

/**
 * @ClassName: HschannelTest.java
 * @Description:
 * @author zhangll
 * @date 2017年11月15日 下午3:13:04
 */
public class HschannelTest extends TestBase{
	@Autowired
	private AcquirerHschannelService  acquirerHschannelService;
	@Autowired CityService cityService;
	
	@Test
	public void add_01(){
		String provinceid = "36";
		String acquirerno = "201710261343500001";
		String acquirername = "测试收单机构";
		String channelmercid = "10000000000000001";
		
		City city =	cityService.getCityById(provinceid, CityTypeEnum.PROVINCE);
		AcquirerHschannel acqHsch = new AcquirerHschannel();
		acqHsch.setAcquirerno(acquirerno);
		acqHsch.setAcquirername(acquirername);
		acqHsch.setProvinceid(provinceid);
		acqHsch.setChannelmercid(channelmercid);
		acqHsch.setProvincename(city.getProvincename());
		acquirerHschannelService.addHui10MerNo(acqHsch);
	}
	
	@Test
	public void add_02(){
		String provinceid = "11";
		String acquirerno = "201710261343500001";
		String acquirername = "测试收单机构";
		String channelmercid = "10000000000000001";
		
		City city =	cityService.getCityById(provinceid, CityTypeEnum.PROVINCE);
		AcquirerHschannel acqHsch = new AcquirerHschannel();
		acqHsch.setAcquirerno(acquirerno);
		acqHsch.setAcquirername(acquirername);
		acqHsch.setProvinceid(provinceid);
		acqHsch.setChannelmercid(channelmercid);
		acqHsch.setProvincename(city.getProvincename());
		acquirerHschannelService.addHui10MerNo(acqHsch);
	}
	@Test
	public void add_03(){
		String provinceid = "12";
		String acquirerno = "201710261343500001";
		String acquirername = "测试收单机构";
		String channelmercid = "10000000000000003";
		
		City city =	cityService.getCityById(provinceid, CityTypeEnum.PROVINCE);
		AcquirerHschannel acqHsch = new AcquirerHschannel();
		acqHsch.setAcquirerno(acquirerno);
		acqHsch.setAcquirername(acquirername);
		acqHsch.setProvinceid(provinceid);
		acqHsch.setChannelmercid(channelmercid);
		acqHsch.setProvincename(city.getProvincename());
		acquirerHschannelService.addHui10MerNo(acqHsch);
	}
	@Test
	public void add_04(){
		String provinceid = "1301";
		String acquirerno = "201710261343500001";
		String acquirername = "测试收单机构";
		String channelmercid = "10000000000000002";
		
		City city =	cityService.getCityById(provinceid, CityTypeEnum.PROVINCE);
		AcquirerHschannel acqHsch = new AcquirerHschannel();
		acqHsch.setAcquirerno(acquirerno);
		acqHsch.setAcquirername(acquirername);
		acqHsch.setProvinceid(provinceid);
		acqHsch.setChannelmercid(channelmercid);
		acqHsch.setProvincename(city.getProvincename());
		acquirerHschannelService.addHui10MerNo(acqHsch);
	}
	@Test
	public void deleteHsMercNo_01(){
		String id  = "201711020134ddd";
		acquirerHschannelService.deleteHsMercNo(id);
	}
	
	@Test
	public void deleteHsMercNo_02(){
		String id  = "2017110203";
		acquirerHschannelService.deleteHsMercNo(id);
	}
	

}
