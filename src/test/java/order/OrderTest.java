package order;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-10-18 14:17
 **/

import com.alibaba.fastjson.JSON;
import com.hui10.app.model.lottery.LotteryOpenVo;
import com.hui10.app.service.loterry.LotteryInfoService;
import com.hui10.app.service.order.LotteryOrderSerice;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-redis.xml", "classpath:spring/spring-service.xml", "classpath:spring/spring-mq-productor.xml"})
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderTest {
    @Autowired
    private LotteryOrderSerice lotteryOrderSerice;
    @Autowired
    private LotteryInfoService lotteryInfoService;


    @Test
    public void test01() {
//        String jsonArray="[{\"bettype\":\"101\",\"codedetail\":\"01,02,03,04,05,06,08|01,02\",\"multiple\":2,\"selecttype\":\"1\"},{\"bettype\":\"101\",\"codedetail\":\"01,02,03,04,05,06,07|01,02\",\"multiple\":3,\"selecttype\":\"2\"},{\"bettype\":\"102\",\"codedetail\":\"01,02,03|04,05,06,07,08,09|01,02\",\"multiple\":1,\"selecttype\":\"2\"}]";
//
//       String issuenumber="2017051";
//       String gamecode="10001";
//        lotteryOrderSerice.generateOrderByApp(issuenumber,gamecode,jsonArray,"2017101718263829896919896");

        lotteryOrderSerice.createLotteryOrderAccount();


    }

    @Test
    public void test02() {
        List<LotteryOpenVo> lotteryOpenVos = lotteryInfoService.getLotteryOpenVo();

        System.out.println(JSON.toJSONString(lotteryOpenVos));

    }



    @Test
    public void test03() {
        int betNumber=0;

        betNumber=betNumber+( count(6,1)*3);
        betNumber=betNumber+( count(7,1)*4);
        betNumber=betNumber+(calculateDantuoAmount(4,5,3)*5);
        System.out.println(betNumber);

    }


    private int count(int redBall, int blueBall) {
        int p = 1;
        int c = 1;
        for (int i = 1; i <= 6; i++) {
            c = c * i;
        }
        for (int j = (redBall - 5); j <= redBall; j++) {
            p = p * j;
        }
        return p / c * blueBall;
    }



    /***
     * 计算胆拖总注数
     */

    private int calculateDantuoAmount(int danCount, int tuoCount, int blueCount) {
        if (danCount < 1 || tuoCount < 2 || blueCount < 1) {
            return 1;
        }

        return numerator(tuoCount, tuoCount, 6 - danCount) / fib(6 - danCount) * blueCount;
    }

    private int numerator(int m, int m2, int n) {
        if (m < (m2 - n + 1)) {  // m*(m-1)*(m-2)*(m-3)*(m-n+1)
            return 1;
        }
        return m * numerator(m - 1, m2, n);
    }


    private int fib(int n) {
        if (n <= 1) {
            return 1;
        }
        return n * fib(n - 1);

    }




}
