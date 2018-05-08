package org.seckill.entity;

import javax.xml.crypto.Data;
import java.util.zip.DataFormatException;

public class SuccessKilled {
    private long seckillId;
    private long userPhone;
    private short state;
    private Data  createTime;
    //变通。
    private Seckill seckill;

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Data getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Data createTime) {
        this.createTime = createTime;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    @Override
    public String toString() {
        return "SuccessKill [id=" + seckillId + ", userPhone=" + userPhone + ", state=" + state + ", createTime=" + createTime
                + ", seckill=" + seckill + "]";
    }
}
