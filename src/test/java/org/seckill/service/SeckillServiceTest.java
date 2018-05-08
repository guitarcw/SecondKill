package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration(locations = { "classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml" })
public class SeckillServiceTest {
    private final Logger logger=LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;
    @Test
    public void getSeckillList() {
        List<Seckill> list=seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void getById() {
        Seckill seckill=seckillService.getById(1000);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void exportSeckillUrl() {
        long id=1000;
        Exposer exposer=seckillService.exportSeckillUrl(id);
        logger.info("exposer={}",exposer);
    }

    @Test
    public void executeSeckill() {
    }
}