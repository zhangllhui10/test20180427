package com.hui10.app.dao.marketing;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hui10.app.model.marketing.UnionLottoPoolTicket;

public interface UnionLottoPoolDao {
	
	int updatePoolTicketStatus(@Param("ticketid")String ticketid, @Param("ticketstatus")String ticketstatus);
	
	
	int updatePoolExpireTicket(@Param("ticketstatus")String ticketstatus, @Param("status")String status);
	
	UnionLottoPoolTicket pullLotteryTicket();
	
	int addLottoTicketToPool(@Param("list")List<UnionLottoPoolTicket> list);
}
