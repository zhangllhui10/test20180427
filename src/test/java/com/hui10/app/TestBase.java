package com.hui10.app;

import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @ClassName: TestBase.java
 * @Description:
 * @author wengf
 * @date 2017年10月19日 上午10:57:02
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-redis.xml","classpath:spring/spring-service.xml","classpath:spring/spring-mq-productor.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class TestBase {

}
