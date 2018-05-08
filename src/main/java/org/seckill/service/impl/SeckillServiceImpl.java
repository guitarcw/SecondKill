package org.seckill.service.impl;

import org.apache.commons.collections.MapUtils;
import org.seckill.dao.cache.RedisDao;
import org.seckill.service.SeckillService;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeckillServiceImpl implements SeckillService {
    private Logger logger=LoggerFactory.getLogger(this.getClass());

    @Resource
    private SeckillDao seckillDao;
    @Resource
    private SuccessKilledDao successKilledDao;

    @Autowired
    private  RedisDao redisDao;

    //用户混淆MD5
    private final String slat="jdjfjfisair37854jhfhnsu4h83hf$^*%($";
    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        //使用redis实现缓存优化
        /**
         * get from cache
         * if null
         * get db
         * else
         *  put cache
         *
        */
        Seckill seckill=redisDao.getSeckill(seckillId);
        if(seckill==null){
            //访问数据库
            seckill=seckillDao.queryById(seckillId);
            if(seckill==null){
                return new Exposer(false,seckillId);
            }else
                redisDao.putSeckill(seckill);
        }

        Date startTime=seckill.getStartTime();
        Date endTime=seckill.getEndTime();
        Date nowTime=new Date();
        if(nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime())
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        String md5=getMD5(seckillId);
        return new Exposer(true,md5,seckillId);

    }

    private String getMD5(long seckillId){
        String base=seckillId+"/"+slat;
        String md5=DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }



    @Override
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long phone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if(md5==null||!md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill date rewrite");
        }
        //执行业务逻辑
        Date nowTime=new Date();

        try {
            int insertCount=successKilledDao.insertSuccessKilled(seckillId,phone);
            if(insertCount<=0){
                throw new RepeatKillException("seckill repeated");
            }else {
                int updateCount=seckillDao.reduceNumber(seckillId,nowTime);
                if(updateCount<=0){
                    throw new SeckillCloseException("seckill is closed");
                }
                else {
                    SuccessKilled successKilled=successKilledDao.queryByIdWithSeckill(seckillId,phone);
                    return new SeckillExecution(seckillId,SeckillStatEnum.SUCCESS,successKilled);
                }
            }
        }
        catch (SeckillCloseException e1){
                throw  e1;
        }
        catch (RepeatKillException e2){
            throw  e2;
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            //所有编译器异常，转换为运行期异常
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
    }

    @Override
    public SeckillExecution executeSeckillProcedure(long seckillId, long phone, String md5) {
        if(md5==null||!md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill date rewrite");
        }
        Date killTime=new Date();
        Map<String,Object> map=new HashMap<>();
        map.put("seckillId",seckillId);
        map.put("phone",phone);
        map.put("killTime",killTime);
        map.put("result",null);
        try {
            seckillDao.killByProcedure(map);
            //获取result；
            int result=MapUtils.getInteger(map,"result",-2);
            if(result==1){
                SuccessKilled successKilled=successKilledDao.queryByIdWithSeckill(seckillId,phone);
                return new SeckillExecution(seckillId,SeckillStatEnum.SUCCESS,successKilled);
            }else {
                return new SeckillExecution(seckillId,SeckillStatEnum.stateOf(result));
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new SeckillExecution(seckillId,SeckillStatEnum.INNER_ERROR);
        }
    }
}
