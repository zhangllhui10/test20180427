package order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hui10.app.model.lottery.LotteryOpenVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-10-18 14:18
 **/
public class UtilsTest {


    @Test
    public void test01() {

        Map<String, Object> map = new HashMap();
        map.put("merchantno", "10001");
        map.put("spid", "111111111111");
        map.put("stationno", "33036666");
        map.put("stationprovince", "36");
        map.put("gamecode", "10001");
        map.put("lotterybetsize", 5);
        map.put("terminalno", "SB_708543269");
//        ArrayList<Map<String, Object>> array = new ArrayList<>(2);
//        Map<String, Object> betMap01 = new HashMap();
//        betMap01.put("bettype","100");
//        betMap01.put("codedetail","11,20,21,22,24,27|15");
//        betMap01.put("multiple",5);
//        betMap01.put("selecttype","1");
//        array.add(betMap01);
//        Map<String, Object> betMap02= new HashMap();
//        betMap02.put("bettype","100");
//        betMap02.put("codedetail","03,06,10,22,24,27|15");
//        betMap02.put("multiple",10);
//        betMap02.put("selecttype","1");
//        array.add(betMap02);
//
//        Map<String, Object> other = new HashMap();
//        other.put("gamecode","1001");
//        other.put("issuenumber","20171018002");
//        other.put("betrequestarray",array);
//
//        map.put("params",other);
//
        System.out.println(JSON.toJSONString(map));
//       String jsonStr= JSON.toJSONString(map);
//       Map result=JSON.parseObject(jsonStr,Map.class);
//       Map resultMap= (Map) result.get("params");
//       System.out.println( resultMap.get("issuenumber"));


        Map<String, Object> map1 = new HashMap<>();
        map1.put("merchantno", "10001");
        map1.put("orderid", "201710232309465428460000006522");
        map1.put("outtradeno", "201710200341470856540000003401");
        map1.put("spid", "111111111111");
        map1.put("serialno", "SB_708543269");
        map1.put("paytime", new Date().getTime());
        map1.put("channelid", "100000000000000000000000000000");
        map1.put("channelmercid", "100000000000000000000000000000");
        map1.put("gamecode", "10001");
//        ArrayList<String> arrayList1=new ArrayList<>();
//        arrayList1.add("10001");
//
//        map1.put("gamecodes",arrayList1);
        System.out.println(JSON.toJSONString(map1));


        ArrayList<Map> arrayList = new ArrayList<>();

        Map map2 = new HashMap();
        map2.put("bettype", 100);
        map2.put("codedetail", "01,02,03,04,05,06|07");
        map2.put("multiple", 2);
        map2.put("selecttype", 2);
        arrayList.add(map2);

        System.out.println(JSON.toJSONString(arrayList));


        Map map3 = JSON.parseObject(JSON.toJSONString(map1), Map.class);

        JSONObject obj = JSON.parseObject(JSON.toJSONString(map1));

        List list = JSON.parseArray(map3.get("gamecodes").toString());
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }


    }


    @Test
    public void test02() {


        String MD5_PREFIX = "c1661665505a3f81";
        String MD5_SUFFIX = "a04ad239d983388d";


        System.out.println(count(16, 16));

        String strs = "01,02,03,04,05,06,08";

        for (String str : strs.split(",")) {
            System.out.println(str);
        }

    }

    @Test
    public void test03() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date date=new Date(1509548400000l);
        Date start = new Date(1509505800000l);
        System.out.println(sdf.format(start));

        Date end = new Date(1509548400000l);
        System.out.println(sdf.format(end));

    }

    @Test
    public void test04() {
//
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd HH:mm:ss");
//
//        Calendar calendar=Calendar.getInstance();
//       // calendar.setTime(new Date());
//
//        calendar.set(Calendar.HOUR_OF_DAY,0);
//        calendar.set(Calendar.SECOND,0);
//        calendar.set(Calendar.MINUTE,0);
//
//        Date date=calendar.getTime();
//        System.out.println(sdf.format(date));
//


        Date now = new Date();
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        System.out.println(week);
        int day = 0;
        switch (week) {
            case 0:
                break;
            case 1:
                day += 1;
                week += 1;
                break;
            case 2:
                break;
            case 3:
                day += 1;
                week += 1;
                break;
            case 4:
                break;
            case 5:
                day += 2;
                week += 2;
                break;
            case 6:
                day += 1;
                week += 1;
                break;
        }
        LotteryOpenVo lotteryOpenVo = new LotteryOpenVo();
        if (day == 0) {
            lotteryOpenVo.setFlag(true);
        } else {
            cal.add(Calendar.DAY_OF_MONTH, day);
            lotteryOpenVo.setFlag(false);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        lotteryOpenVo.setGamecode("10001");
        lotteryOpenVo.setOpendate(cal.getTime());
        lotteryOpenVo.setWeek(weekDays[week]);

        System.out.println(weekDays[week]);
        System.out.println(sdf.format(cal.getTime()));
        System.out.println(cal.getTime().getTime());


    }

    private int count(int x, int y) {
        int tmp_count1;
        int a, b;
        long p = 1, c = 1;
        a = x;
        b = y;
        for (int i = 1; i <= 6; i++) {
            c = c * i;
        }
        for (int j = (a - 5); j <= a; j++) {
            p = p * j;
        }
        Long num = p / c * b;
        tmp_count1 = num.intValue();
        return tmp_count1;

    }

    @Test
    public void test08() {
        String phone = "18612563333";
        String pre = phone.substring(0, 3);
        String end = phone.substring(8,11);
        System.out.println(pre + "*****" + end);
    }


}
