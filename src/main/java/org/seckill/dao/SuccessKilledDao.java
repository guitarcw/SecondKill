package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



public interface SuccessKilledDao {
    /**
     * 插入购买明细，可过滤重复
     * @param seckillId
     * @param userPhone
     * @return
     * 插入的行数
     */


    int insertSuccessKilled(@Param("seckillId") Long seckillId, @Param("userPhone") long userPhone);

    /**
     * 根据id查询SuccessKilled
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userNumber);
}
