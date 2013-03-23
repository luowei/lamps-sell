/**
1.外键约束，最好写在建表语句外面;
2.目前有一个关念，对表最好不要建立外键约束

*/
use information_schema;
drop database if exists lampssell;
create database lampssell;
use lampssell;

/********系统部分**********/

/**
* 用户表
*/
drop table if exists userinfo;
create table userinfo(
	id int(11) primary key auto_increment,
	username varchar(20) not null,		/*用户名*/
	password varchar(20) not null,		/*密码*/
	sex enum('男','女','保密'),		/*性别*/
	email varchar(20),			/*电子邮箱*/
	qq varchar(12),				/*qq号码*/
	phone varchar(15),				/*手机号*/
	zip varchar(8),					/*邮编*/
	isEnable int(11),			/*是否可用*/
	online bigint(20),			/*在线时长*/
	score int(11),				/*积分*/
	createtime datetime,			/*注册时间*/
	content text,				/*详细说明*/
	isBetter int(11)			/*是否为高用户，1是 0不是*/
) engine=innodb default charset=utf8 row_format=dynamic;
#alter table userinfo add column phone varchar(15) after qq;
#alter table userinfo add column zip varchar(8) after phone;

/**
* 角色表
*/
drop table if exists role;
create table role(
	id int(11) primary key auto_increment,
	rolename varchar(20) not null,		/*角色名*/
	roleinfo text,				/*角色介绍*/
	createtime datetime			/*创建时间*/
) engine=innodb default charset=utf8 row_format=dynamic;

/**
* 权限表
*/
drop table if exists action;
create table action(
	id int(11) primary key auto_increment,
	actionname varchar(20) not null,	/*权限名称*/
	orders int(11),				/*排序*/
	path varchar(200),			/*相对于主目录的路径*/
	createtime datetime,			/*创建时间*/
	moudleid int(11)			/*模块id*/
) engine=innodb default charset=utf8 row_format=dynamic;

/**
* 模块表
*/
drop table if exists module;
create table module(
	id int(11) primary key auto_increment,
	modulename varchar(765) not null,	/*模块名称*/
	moduleinfo text,			/*模块介绍*/
	createTime datetime			/*创建时间*/
) engine=innodb default charset=utf8 row_format=dynamic;
#alter table action drop foreign key FK_ACTION_MODULE;
###alter table action add constraint FK_ACTION_MODULE foreign key(moudleid) references module(id);

/**
* 菜单表
*/
drop table if exists menu;
create table menu(
	id int(11) primary key auto_increment,
	menuname varchar(150) not null,			/*菜单名*/
	parentid int(11) not null,				/*父菜单id*/
	url varchar(200),				/*链接地址*/
	imageurl varchar(200),				/*图片地址*/
	dept int default '0',					/*深度*/
	createtime datetime,				/*创建时间*/
	orders varchar(20)				/*排序*/
) engine=innodb default charset=utf8 row_format=dynamic;
#alter table menu drop foreign key FK_MENU_PARENTID;
###alter table menu add constraint FK_MENU_PARENTID foreign key(parentid)  references menu(id);

/**
* 用户角色表
* 先添加外键约束，再添加主键约束,才能成功
*/
drop table if exists userrole;
create table userrole(
	userid int(11),### references user(id),					/*用户id*/
	roleid int(11),### references role(id),					/*角色id*/
	primary key(userid,roleid)
) engine=innodb default charset=utf8 row_format=dynamic;
#alter table userrole drop foreign key FK_USERROLE_USERID;
#alter table userrole drop foreign key FK_USERROLE_ROLEID;
#alter table userrole drop primary key PK_USERROLE;
#alter table userrole add constraint FK_USERROLE_USERID foreign key(userid) references user(id);
#alter table userrole add constraint FK_USERROLE_ROLEID foreign key(roleid) references role(id);
#alter table userrole add constraint PK_USERROLE primary key(userid,roleid);

/**
* 角色权限表
*/
drop table if exists roleaction;
create table roleaction(
	roleid int(11),### references role(id),					/*角色id*/
	actionid int(11),### references action(id),				/*权限id*/
	primary key(roleid,actionid)
) engine=innodb default charset=utf8 row_format=dynamic;
#alter table roleaction add constraint FK_ROLEACTION_ROLEID foreign key(roleid) references role(id);
#alter table roleaction add constraint FK_ROLEACTION_ACTION foreign key(actionid) references action(id);

/**
* 角色菜单表
*/
drop table if exists rolemenu;
create table rolemenu(
	roleid int(11),### references role(id),					/*角色id*/
	menuid int(11),### references menu(id),					/*菜单id*/
	primary key(roleid,menuid)
) engine=innodb default charset=utf8 row_format=dynamic;
#alter table rolemenu add constraint FK_ROLEMENU_ROLEID foreign key(roleid) references role(id);
#alter table rolemenu add constraint FK_ROLEMENU_MENUID foreign key(menuid) references menu(id);




/*********应用部分**********/

/**
* 类别表
*/
drop table if exists category;
create table category (
	id int(11) primary key auto_increment,
	parent_id int(11) not null, #references category(id), 	/*列级添加外键约束*/
	name varchar(20),					/*名称*/
	status enum('上线','下线') default '上线',		/*状态*/
	product_count int(11) default '0',			/*产品数量*/
	createtime datetime,					/*创建时间*/
	orders int(11) default '0'
	/*foreign key (parent_id) references category(id)*/  /*表级添加外键约束*/
) engine=innodb default charset=utf8 row_format=dynamic;
#alter table category drop foreign key FK_CATEGORY_ID; 
###alter table category add constraint FK_CATEGORY_ID foreign key(parent_id) references category(id); 
#添加字段
#alter table category add column status enum('上线','下线') default '上线' after name;
#alter table category add column orders int(11) not null default '2'
#update category set orders=id;
#alter table category modify column orders int(11)  default '0';
#alter table category add column product_count int(11) default '0' after status;

/**
* 产品库存表
*/
drop table if exists product;
create table product(
	id int(11) primary key auto_increment,
	name varchar(50) not null,	/*名称*/
	type varchar(20),		/*产品分类*/
	category_id int(11) not null,	/*行业类别id*/
	status enum('上线','下线','缺货') default '上线', /*状态：0上线  1下线  2缺货*/
	sales_valume int(11) default '0',	/*销量*/
	produce_area varchar(100),	/*产地*/
	small_img varchar(100),		/*小图片*/
	big_img text,		/*大图片*/
	detail varchar(2048),		/*详细描述*/
	createtime datetime, 		/*生产日期:timestamp default CURRENT_TIMESTAMP*/
	price double default '0.0',	/*产品价格*/
	num int(11) default '0',	/*数量*/
	min_num int(11) default '1',	/*最小数量*/
	orders int(11) default '0'
	/*constraint PRODUCT_CATEGORY foreign key(category_id) references category(id)*/
) engine=innodb default charset=utf8 row_format=dynamic;
#alter table product drop foreign key FK_PRODUCT_CATEGORY;
###alter table product add constraint FK_PRODUCT_CATEGORY foreign key(category_id) references category(id);
#alter table product add constraint FK_PRODUCT_STOCK foreign key(id) references stock(id);
#alter table product add column orders int not null  default '2';
#alter table product drop column sort_num;
#update product set orders=id;
#alter table product add column sales_valume int(11) default '0' after produce_area;
#update product set sales_valume=1000;
#alter table product modify column orders int(11)  default '0';
#alter table product modify column detail text;


/**
* 企业表
*/
drop table if exists complany;
create table complany(
	id int(11) primary key auto_increment,
	name varchar(50) not null,		/*企业名称*/
	en_name varchar(50),			/*英文名*/
	short_name varchar(10),			/*简称*/
	type enum('有限责任','个人独资','股份有限','外商投资','国有企业','合伙企业','外国企业') default '有限责任', /*企业性质*/
	logo_img varchar(100),			/*企业logo*/
	img varchar(100),			/*企业图片*/
	business_mode enum('生产/制造','贸易','服务','政府机构','组织团体','其它') default '生产/制造',/*经营模式*/
	primary_business varchar(200),		/*主营业务*/
	address varchar(100),			/*企业地址*/
	business_location varchar(100),		/*贸易地区*/
	boss_name varchar(20),			/*法人名称*/
	detail varchar(2048),			/*简介描述*/
	createtime date,			/*成立时间*/
	orders int(11) default '0'
) engine=innodb default charset=utf8 row_format=dynamic;
#alter table complany add column order int not null  default '2';
#update complany set orders=id;
#alter table complany modify column orders int(11)  default '0';

/**
* 企业产品表
*/
drop table if exists complanyproduct;
create table complanyproduct(
	complany_id int(11),### references complany(id),
	product_id int(11),### references product(id),
	primary key(complany_id,product_id)
) engine=innodb default charset=utf8 row_format=dynamic;
#alter table complanyproduct drop foreign key FK_COMPLANYPRODUCT_COMPLANY;
#alter table complanyproduct drop foreign key FK_COMPLANYPRODUCT_PRODUCT;
#alter table complanyproduct add constraint FK_COMPLANYPRODUCT_COMPLANY foreign key(complany_id) references complany(id);
#alter table complanyproduct add constraint FK_COMPLANYPRODUCT_PRODUCT foreign key(product_id) references product(id);


/**
* 供求信息表
*/
drop table if exists tradInfo;
create table tradInfo(
	id int(11) primary key auto_increment,
	complany_id int(11),			/*企业id*/
	product_id int(11),			/*产品id*/
	name varchar(20) not null,		/*信息标题*/
	product_name varchar(50),			/*产品id*/
	detail text,			/*信息详细*/
	product_img varchar(100),		/*产品图片*/
	info_type enum('求购','供应','代理','招商','合作','招标'),	/*信息类型*/
	validity double,			/*有效期*/
	status enum('上线','下线') default '上线',	/*上线状态*/
	area varchar(100),			/*地区*/
	createtime date,			/*创建时间*/
	orders int(11) default '0'	/*排序号*/
) engine=innodb default charset=utf8 row_format=dynamic;
#alter table tradInfo drop foreign key FK_TRADINFO_COMPLANY;
###alter table tradInfo add constraint FK_TRADINFO_COMPLANY foreign key(complany_id) references complany(id);
#alter table tradInfo add column product_id int(11) after complany_id;
###alter table tradInfo add constraint FK_TRADINFO_PRODUCT foreign key(product_id) references product(id);
#alter table tradInfo add column orders int   not null default '0';
#update tradInfo set orders=id;
#alter table tradInfo change column title name varchar(20) not null;
#alter table tradInfo modify column orders int(11)  default '0';
#alter table tradInfo modify column detail text;
#insert into tradinfo(complany_id,product_id,name,product_name,detail,product_img,info_type,validity,status,area,createtime,orders)
#	select complany_id,product_id,name,product_name,detail,product_img,info_type,validity,status,area,createtime,orders from tradinfo;
#delete from tradinfo where id <> '1' and id <> '2';
#alter table tradinfo modify column info_type enum('求购','供应','代理','招商','合作','招标');

/**
* 留言表
*/
drop table if exists message;
create table message(
	id int(11) primary key auto_increment,
	type enum('产品','企业') default '产品',	/*留言的类型*/
	detail text,				/*留言内容*/
	user_id int(11),				/*用户id*/
	product_id int(11),				/*产品id*/
	complany_id int(11),				/*企业id*/
	createtime date,				/*留言时间*/
	status enum('上线','下线') default '上线',	/*可见状态*/
	orders int(11) default '0'
) engine=innodb default charset=utf8 row_format=dynamic;
#alter table message drop foreign key FK_MESSAGE_USER;
#alter table message drop foreign key FK_MESSAGE_PRODUCT;
#alter table message drop foreign key FK_MESSAGE_COMPLANY;
###alter table message add constraint FK_MESSAGE_USER foreign key(user_id) references userinfo(id);
###alter table message add constraint FK_MESSAGE_PRODUCT foreign key(product_id) references product(id);
###alter table message add constraint FK_MESSAGE_COMPLANY foreign key(complany_id) references complany(id);
#alter table message add column orders int(11) default '0';
###alter table message drop column date;
#update message set orders=id;
#alter table message modify detail text;
#alter table message modify orders int(11) default '0';
#insert into message(type,date,detail,user_id,product_id,complany_id,createtime,status,orders) 
#	select type,date,detail,user_id,product_id,complany_id,createtime,status,orders from message;

/**
* 新闻表
*/
drop table if exists news;
create table news(
	id int(11) primary key auto_increment,
	title varchar(100),				/*新闻标题*/
	type enum('企业新闻','行业新闻','企业招聘','展会信息'),	/*新闻类型*/
	detail text,					/*详细信息*/
	user_id int(11),				/*发布人id*/
	complany_id int(11),				/*企业id*/
	status enum('上线','下线') default '上线',	/*可见状态*/
	createtime date,				/*发表时间*/
	orders int(11) default '0'
) engine=innodb default charset=utf8 row_format=dynamic;
#alter table news drop foreign key FK_NEWS_USER;
#alter table news drop foreign key FK_NEWS_COMPLANY;
###alter table news add constraint FK_NEWS_USER foreign key(user_id) references userinfo(id);
###alter table news add constraint FK_NEWS_COMPLANY foreign key(complany_id) references complany(id);
#alter table news add column order int(11) not null default '2';
#update news set orders=id;
#alter table news modify column news int(11) default '0';
#alter table news add column news text after type;
#insert into news(title,type,news,user_id,complany_id,status,createtime,orders) 
#	select title,type,news,user_id,complany_id,status,createtime,orders from news;
#alter table news change column news detail text;




/**
* 广告表
*/
drop table if exists ads;
create table ads(
	id int(11) primary key auto_increment,
	name varchar(100),				/*广告名称*/
	img varchar(100),				/*广告图片*/
	detail varchar(500),				/*详细信息*/
	status enum('上线','下线') default '上线',	/*状态*/
	createtime date,				/*发表时间*/
	orders int(11) default '0'
) engine=innodb default charset=utf8 row_format=dynamic;
#alter table ads add column order int(11) default '2';
#update ads set orders=id;
#alter table ads modify column orders int(11) default '0';

/**
* 联系人表
*/
drop table if exists contact;
create table contact(
	id int(11) primary key auto_increment,
	name varchar(20) not null,			/*姓名*/
	tel varchar(12),				/*电话号码*/
	phone varchar(11),				/*手机号码*/
	email varchar(50),				/*email*/
	qq varchar(12),					/*qq号码*/
	http varchar(100),				/*网址*/
	fax varchar(12),				/*传真*/
	zip varchar(6),					/*邮箱*/
	addr varchar(50),				/*联系地址*/
	complany_id int(11),				/*企业id*/
	orders int(11) default '0'
) engine=innodb default charset=utf8 row_format=dynamic;
#alter table contact drop foreign key FK_CONTACT_COMPLANY;
###alter table contact add constraint FK_CONTACT_COMPLANY foreign key(complany_id) references complany(id);
#alter table contact add column order int(11) default '2';
#update contact set orders=id;
#alter table contact modify column orders int(11) default '0';


/**
* 订单
*/
drop table if exists orders;
create table orders(
	id int(11) primary key auto_increment,
	consignee varchar(20),							/*用户名*/
	phone varchar(15),							/*联系电话*/
	zip varchar(8),								/*邮编*/
	shippingaddr varchar(200),						/*收货地址*/
	deliverymode varchar(20),						/*配送方式*/
	pay_mode enum('银行支付','第三方支付','货到付款') default '银行支付',	/*付款方式*/
	pay_status enum('未付款','已付款') default '未付款',	/*付款状态*/
	sub_price double default '0.0',			/*总价*/
	orders_date timestamp default current_timestamp, 	/*订单日期*/
	user_id int(11),					/*用户id*/
	product_num int(11) default '0', 				/*产品数量*/
	orders int(11) default '0'				/*排序号*/
) engine=innodb default charset=utf8 row_format=dynamic;
#alter table orders drop foreign key FK_ORDERS_USER;
###alter table orders add constraint FK_ORDERS_USER foreign key(user_id) references userinfo(id);
#alter table orders add column order int(11) not null default '2';
#update orders set orders=id;
#alter table orders modify column orders int(11) default '0';
#alter table orders add column prodcut_num int(11) default '0';
#alter table orders change column sub_price total_Price double ;
#alter table orders add column number varchar(20);
#alter table orders change column pay_satus pay_status enum('未付款','已付款') default '未付款';
#alter table orders add column consignee varchar(20);
#alter table orders add column phone varchar(15);
#alter table orders add column zip varchar(8);
#alter table orders add column shippingaddr varchar(200);
#alter table orders add column deliverymode varchar(20);



/**
* 订单行
*/
drop table if exists orderrow;
create table orderrow(
	id int(11) primary key auto_increment,
	product_num  int(11),
	vip_price double,	#vip价格
	sub_price double,	#小计
	orders_id int(11),
	product_id int(11)
) engine=innodb default charset=utf8 row_format=dynamic;
#alter table orderrow add column vip_price double after product_num;
#alter table orderrow add column sub_price double after vip_price;



/**
* 订单产品表_详细表
*/
drop table if exists ordersproduct;
create table ordersproduct(
	orders_id int(11),### references orders(id),			/*订单id*/
	product_id int(11),### references product(id),			/*产品id*/
	primary key(orders_id,product_id)
) engine=innodb default charset=utf8 row_format=dynamic;
#alter table ordersproduct drop foreign key FK_ORDERSPRODUCT_ORDER;
#alter table ordersproduct drop foreign key FK_ORDERSPRODUCT_PRODUCT;
#alter table ordersproduct add constraint FK_ORDERSPRODUCT_ORDER foreign key(orders_id) references orders(id);
#alter table ordersproduct add constraint FK_ORDERSPRODUCT_PRODUCT foreign key(product_id) references product(id);

/**
* 用户产品表
*/
drop table if exists userproduct;
create table userproduct(
	user_id int(11),### references user(id),			/*用户id*/
	product_id int(11),### references product(id),			/*产品id*/
	primary key(user_id,product_id)
) engine=innodb default charset=utf8 row_format=dynamic;
#alter table userproduct drop foreign key FK_USERPRODUCT_USER;
#alter table userproduct drop foreign key FK_USERPRODUCT_PRODUCT;
#alter table userproduct add  constraint FK_USERPRODUCT_USER foreign key(user_id) references userinfo(id);
#alter table userproduct add  constraint FK_USERPRODUCT_PRODUCT foreign key(product_id) references product(id);


##################################################################################################################################
update product p1,product p2 set p1.big_img = p2.big_img where p2.id = '21' and p1.id like '%_1';
update product p1,product p2 set p1.big_img = p2.big_img where p2.id = '22' and p1.id like '%_2';
update product p1,product p2 set p1.big_img = p2.big_img where p2.id = '23' and p1.id like '%_3';
update product p1,product p2 set p1.big_img = p2.big_img where p2.id = '24' and p1.id like '%_4';
update product p1,product p2 set p1.big_img = p2.big_img where p2.id = '25' and p1.id like '%_5';
update product p1,product p2 set p1.big_img = p2.big_img where p2.id = '26' and p1.id like '%_6';
update product p1,product p2 set p1.big_img = p2.big_img where p2.id = '27' and p1.id like '%_7';
update product p1,product p2 set p1.big_img = p2.big_img where p2.id = '28' and p1.id like '%_8';
update product p1,product p2 set p1.big_img = p2.big_img where p2.id = '29' and p1.id like '%_9';
update product p1,product p2 set p1.big_img = p2.big_img where p2.id = '30' and p1.id like '%_0';


update product set small_img = big_img;



select * from menu where menuname = '订单管理';


alter table orders drop column prodcut_num

delete from orders;
delete from orderrow;

update message set createtime  = '2012-04-22' where createtime is null;




