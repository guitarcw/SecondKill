package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisDao {
    private final Logger logger=LoggerFactory.getLogger(this.getClass());
    private final JedisPool jedisPool;
    private static int TIMEOUT = 10000;
    public  RedisDao(String ip,int port,String password){
        JedisPoolConfig config=new JedisPoolConfig();
        jedisPool=new JedisPool(config,ip,port,TIMEOUT,password);
    }
    public RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    public Seckill getSeckill(long seckillId){
        try {
            Jedis jedis=jedisPool.getResource();
            try {
                String key="seckill:"+seckillId;
                //没有实现内部序列化
                //get->byte[]->反序列话->Object
                //采用自定义序列化方式
                //protostuff；pojo
                byte[] bytes = jedis.get(key.getBytes());
               if(bytes!=null){
                   //空对象
                   Seckill seckill = schema.newMessage();
                   ProtostuffIOUtil.mergeFrom(bytes,seckill,schema);
                   return seckill;
               }
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }
    public String putSeckill(Seckill seckill){
        // set(Object) ->序列化->bytes[]

        try {
            Jedis jedis=jedisPool.getResource();
            try{
                String key="seckill:"+seckill.getSeckill();
                byte[] bytes=ProtostuffIOUtil.toByteArray(seckill,schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                int timeout=60*60;
                String result=jedis.setex(key.getBytes(),timeout,bytes);
                return result;
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }
}
