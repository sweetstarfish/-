drop database if exists SMSdb;
create database if not exists SMSdb character set utf8;
use SMSdb;
# drop table if exists Goods;
create table if not exists Goods (
 Gid char(8) primary key,
 Cid char(4) not null,
 Name nvarchar(10) not null,
 Price decimal(8,2) not null,
 Unit nvarchar(4)  null,
 check(Price>=0 )
) character set utf8;
insert into Goods(Gid,Cid,Name,Price,Unit) values
  ('20010001', '0001', '白萝卜', 1.20, '斤'),
  ('20020001', '0002', '可口可乐', 3.00,'瓶'),
  ('20060003', '0010', '维达卷纸', 25.00,'提');
insert into  Goods(Gid,Cid,Name,Price) values('20020002','0002','雪碧', 3.00);