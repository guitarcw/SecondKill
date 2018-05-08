package org.seckill.entity;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class Seckill {
    private long seckillId;
    private String name;
    private int number;
    private Date startTime;
    private Date endTime;
    private Date createTime;

    public long getSeckill() {
        return seckillId;
    }

    public void setSeckill(long seckill) {
        this.seckillId = seckill;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Seckill [seckillId=" + seckillId+ ", name=" + name + ", number=" + number + ", startTime=" + startTime
                + ", endTime=" + endTime + ", createTime=" + createTime + "]";
    }
}
