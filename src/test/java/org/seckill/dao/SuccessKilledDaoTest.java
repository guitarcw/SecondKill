package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration(locations={"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
    @Resource
    SuccessKilledDao successKilledDao;
    @Test
    public void insertSuccessKilled() {
        long id=1000L;
        long phone=15011276711L;
        int insertCount=successKilledDao.insertSuccessKilled(id,phone);
        System.out.println("insertCount=" + insertCount);

    }

    @Test
    public void queryByIdWithSeckill() {
        long id=1000L;
        long phone=15879329533L;
        SuccessKilled successKilled=successKilledDao.queryByIdWithSeckill(id,phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());

    }
}