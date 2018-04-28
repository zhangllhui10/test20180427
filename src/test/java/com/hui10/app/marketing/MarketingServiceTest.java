package com.hui10.app.marketing;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hui10.app.TestBase;
import com.hui10.app.model.marketing.Marketing;
import com.hui10.app.model.marketing.MarketingAccessChannel;
import com.hui10.app.model.marketing.enums.MarketingStatusEnum;
import com.hui10.app.service.marketing.ChannelService;
import com.hui10.app.service.marketing.MarketingService;
import com.hui10.app.service.marketing.ReceiveLotteryService;

/**
 * @ClassName: MarketingServiceTest.java
 * @Description:
 * @author zhangll
 * @date 2018年2月27日 下午1:39:02
 */
public class MarketingServiceTest extends TestBase {
	@Autowired
	private MarketingService marketingService;
	@Autowired
	private ReceiveLotteryService receiveLotteryService;
	
	@Test
	public void tes() {
		JSONObject object = new JSONObject();
	    object.put("gatewayid", "201803100446287615360000005264");
	    object.put("channelid", "201803100446287615360000005270");
	    object.put("merchantname", "POS机售彩-POS机售彩");
	    object.put("money", 20);
	    object.put("betnumber", 1);
	    object.put("citys", "1201,1301");
	    object.put("merchants", "UH99111371,UH99708520");

	    JSONObject object_2 = new JSONObject();
	    object_2.put("gatewayid", "201803100446287615360000005265");
	    object_2.put("channelid", "201803100446287615360000005271");
	    object_2.put("merchantname", "汇彩宝售彩-汇彩宝售彩");
	    object_2.put("money", 20);
	    object_2.put("betnumber", 1);
	    object_2.put("citys", "");
	    object_2.put("merchants", "");

	    JSONArray array = new JSONArray();
	    array.add(object);
	    array.add(object_2);
	    System.out.println(array.toJSONString());
	}

	@Test
	public void addMarketing_test(){
		String accessjson = "[{\"money\":20,\"merchantname\":\"POS机售彩-POS机售彩\",\"citys\":\"1201,1301\",\"merchants\":\"UH99111371,UH99708520\",\"gatewayid\":\"201803100446287615360000005264\",\"channelid\":\"201803100446287615360000005270\",\"betnumber\":1},{\"money\":20,\"merchantname\":\"汇彩宝售彩-汇彩宝售彩\",\"citys\":\"\",\"merchants\":\"\",\"gatewayid\":\"201803100446287615360000005265\",\"channelid\":\"201803100446287615360000005271\",\"betnumber\":1}]";
		Marketing marketing = new Marketing();
		marketing.setMarketingname("518彩票街");
		marketing.setStarttime(new Date());
		marketing.setEndtime(new Date());
		marketing.setBettimetype("1");
		marketing.setIndefinite("1");
		marketing.setStarttime(new Date());
		marketing.setEndtime(new Date());
		marketing.setBettime(null);
		marketing.setLotterycode("10001");
		marketing.setIsrandom("0");
		marketing.setBetnumberrule(1);
		marketing.setDays(4);
		marketing.setInitiator("发起方-汇拾");
		marketing.setStatus(MarketingStatusEnum.DELAY_ENABLE.getCode());
		marketing.setResource("2");
		marketingService.addMarketing(marketing, accessjson);
	}
	
	@Test
	public void updateMarketing_test(){
		String accessjson = "[{\"money\":20,\"merchantname\":\"POS机售彩-POS机售彩\",\"citys\":\"1201,1301\",\"merchants\":\"UH99111371,UH99708520\",\"gatewayid\":\"201803100446287615360000005264\",\"channelid\":\"201803100446287615360000005270\",\"betnumber\":1},{\"money\":20,\"merchantname\":\"汇彩宝售彩-汇彩宝售彩\",\"citys\":\"\",\"merchants\":\"\",\"gatewayid\":\"201803100446287615360000005265\",\"channelid\":\"201803100446287615360000005271\",\"betnumber\":1}]";
		Marketing marketing = new Marketing();
		marketing.setMarketingid("201804040716018292840000001441");
		marketing.setMarketingname("518彩票节");
		marketing.setStarttime(new Date());
		marketing.setEndtime(new Date());
		marketing.setBettimetype("1");
		marketing.setIndefinite("1");
		marketing.setStarttime(new Date());
		marketing.setEndtime(new Date());
		marketing.setBettime(null);
		marketing.setLotterycode("10001");
		marketing.setIsrandom("0");
		marketing.setBetnumberrule(1);
		marketing.setDays(4);
		marketing.setInitiator("发起方-汇彩宝-宝乐彩");
		marketing.setStatus(MarketingStatusEnum.ENABLE.getCode());
		marketing.setResource("2");
		marketingService.updateMarketing(marketing, accessjson);
	}

	
	@Autowired
	private  ChannelService channelService;
	MarketingAccessChannel channel  = null;
	String gatewayid = "201803100446287615360000005265";
	@Before
	public void init_channel(){
		channel = new MarketingAccessChannel();
		channel.setGatewayid(gatewayid);
		channel.setChannelname("junit_test_名称_edit2");
	}
	
	@Test
	public void addChannel_success_test(){
		channelService.addChannel(channel);
	}
	
	@Test
	public void addChannel_name_test(){
		channelService.addChannel(channel);
	}
	
	@Test
	public void editChannel_success_test(){
		channel.setChannelid("201804050201570100510000002321");
		channelService.editChannel(channel);
	}
	@Test
	public void updateStatus_success_test(){
		String channelid = "201804050201570100510000002321";
		String status = "1";
		channelService.updateStatus(channelid, status);
	}
}
