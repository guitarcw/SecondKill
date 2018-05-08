--数据库初始话脚本
--创建数据库
create database seckill;
use seckill;
--创建秒杀库存表
create table seckill(
`seckill_id` bigint not null AUTO_INCREMENT comment '商品库存id',
`name` varchar(120) not null comment '商品名称',
`number` int not null comment '库存数量',
`start_time` datetime not null comment '秒杀开启时间',
`end_time` datetime not null comment '秒杀结束时间',
`create_time` timestamp not null DEFAULT CURRENT_TIMESTAMP comment '创建时间',
primary key (seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_creat_time(create_time)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 comment='秒杀库存表';
insert into seckill(name ,number,start_time,end_time)
values
  ('1000元秒杀iphone6',100,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
  ('1000元秒杀ipad2',200,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
  ('1000元秒杀小米4',300,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
  ('1000元秒杀红米note',400,'2015-11-01 00:00:00','2015-11-02 00:00:00');
--秒杀成功明细表
--用户登陆认证相关的信息
create table success_killed(
`seckill_id` bigint not null comment '秒杀商品id',
`user_phone` bigint not null comment '用户手机号',
`state` tinyint not null default -1 comment '状态：-1:无效 0：成功 1：已付款',
`create_time` timestamp not null comment '创建时间',
primary key (seckill_id,user_phone),/*联合主键*/
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 default charset=utf8 comment='秒杀成功明细表';
--连接数据库初始化
mysql -uroot -p1221