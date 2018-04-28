package com.hui10.app.controller.acquirerhschannel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hui10.app.common.web.BaseController;
import com.hui10.app.model.acquirer.AcquirerHschannel;
import com.hui10.app.model.enums.CityTypeEnum;
import com.hui10.app.model.system.City;
import com.hui10.app.service.acquirerhschannel.AcquirerHschannelService;
import com.hui10.app.service.system.CityService;
import com.hui10.common.utils.PropertiesUtils;
import com.hui10.model.common.Result;

/**
 * @ClassName: AcquirerHschannelController.java
 * @Description:
 * @author zhangll
 * @date 2017年11月15日 下午2:45:38
 */
@Controller
public class AcquirerHschannelController extends BaseController {
	@Autowired
	private AcquirerHschannelService acquirerHschannelService;
	@Autowired
	private CityService cityService;

	@RequestMapping(value = "/hs/mercno/add", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> addHsMercNo(String token,String acquirerno,String acquirername,String provinceid,String channelmercid) {
		Result<Boolean> result = new Result<Boolean>(com.hui10.common.constants.ICommon.SUCCESS,
				PropertiesUtils.get(com.hui10.common.constants.ICommon.SUCCESS));
		validateStringParameterNotBlank(acquirerno,acquirername,provinceid,channelmercid);
		checkAdminToken(token);
		City city =	cityService.getCityById(provinceid, CityTypeEnum.PROVINCE);
		AcquirerHschannel acqHsch = new AcquirerHschannel();
		acqHsch.setAcquirerno(acquirerno);
		acqHsch.setAcquirername(acquirername);
		acqHsch.setProvinceid(provinceid);
		acqHsch.setChannelmercid(channelmercid);
		acqHsch.setProvincename(city.getProvincename());
		acquirerHschannelService.addHui10MerNo(acqHsch);
		return result;
	}
	
	@RequestMapping(value = "/hs/mercno/delete", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> deleteHsMercNo(String token,String id){
		Result<Boolean> result = new Result<Boolean>(com.hui10.common.constants.ICommon.SUCCESS,
				PropertiesUtils.get(com.hui10.common.constants.ICommon.SUCCESS));
		validateStringParameterNotBlank(id);
		checkAdminToken(token);
		acquirerHschannelService.deleteHsMercNo(id);
		return result;
		
	}
}
