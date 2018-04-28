package com.hui10.app.dao.main;

import com.hui10.app.model.main.HomeLottery;
import com.hui10.app.model.user.BigprizeHandle;
import com.hui10.app.model.user.MediumHandle;
import com.hui10.app.model.user.WithdrawRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author fanht
 * @ClassName:
 * @Description:
 * @date 2018年03月06日 16:19
 */
public interface MainDao {

    List<HomeLottery> queryHomeOrders(@Param("uid") String uid, @Param("num") int num, @Param("sysdate") Date sysDate);

    MediumHandle queryWinOrderMedium(@Param("orderid") String orderid);

    WithdrawRecord querySmallBouns(@Param("orderid") String orderid);

    long queryWinTotalAmountByUid(@Param("uid") String uid);

    List<HomeLottery> queryGivingLotteryList(@Param("uid") String uid, @Param("num") int num);

    int queryWaitBettingNum(@Param("uid") String uid, @Param("sysdate") Date sysDate);

    int queryWinPrizeNum(@Param("uid") String uid);

    BigprizeHandle queryWinOrderBigPrize(@Param("orderid") String orderid);
}
