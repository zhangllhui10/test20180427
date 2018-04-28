package com.hui10.app.dao.order;

import com.hui10.app.model.order.Ticket;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fanht
 * @ClassName:
 * @Description:
 * @date 2018年03月19日 15:00
 */
public interface TicketDao {

    int batchInsertTicket(@Param("list") List<Ticket> ticketList);
}
