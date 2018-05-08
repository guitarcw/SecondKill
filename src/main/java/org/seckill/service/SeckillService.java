package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * 业务接口：站在站在“使用者”的角度设计接口
 * 三个方面：方法定义粒度，参数，返回类型
 */
public interface SeckillService {
    /**
     * 查询所有秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启是输出秒杀接口地址，
     * 否则输出系统时间和秒杀时间
     * @param seckillId
     * @return
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀结果
     * @param seckillId
     * @param phone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckill(long seckillId, long phone, String md5)throws SeckillException,RepeatKillException,SeckillCloseException;

    /**
     * 执行秒杀结果使用存储过程
     * @param seckillId
     * @param phone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckillProcedure(long seckillId, long phone, String md5);
}
