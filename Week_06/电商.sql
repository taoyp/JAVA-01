/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2021/2/27 23:57:24                           */
/*==============================================================*/


drop table if exists TF_B_CARD_GOOD;

drop index IDX_CARD_USER_ID on TF_B_CART;

drop table if exists TF_B_CART;

drop index IDX_COUPON_USER_ID on TF_B_COUPON;

drop table if exists TF_B_COUPON;

drop index IDX_GOOD_NAME on TF_B_GOOD;

drop table if exists TF_B_GOOD;

drop index IDX_HOBBY_USER_ID on TF_B_HOBBY;

drop table if exists TF_B_HOBBY;

drop index IDX_ORDER_USER_ID on TF_B_ORDER;

drop table if exists TF_B_ORDER;

drop table if exists TF_B_ORDER_GOOD;

drop table if exists TF_B_ORDER_HISTORY;

drop table if exists TF_B_PROVIDER;

drop index IDX_TRANSPORT_ORDER on TF_B_TRANSPORT;

drop table if exists TF_B_TRANSPORT;

drop index IDX_ADDR_USER_ID on TF_B_USER_ADDRESS;

drop table if exists TF_B_USER_ADDRESS;

drop index IDX_USER_NAME on TF_F_USER;

drop table if exists TF_F_USER;

/*==============================================================*/
/* Table: TF_B_CARD_GOOD                                        */
/*==============================================================*/
create table TF_B_CARD_GOOD
(
   GOOD_ID              varchar(32) not null,
   CART_ID              varchar(32) not null,
   DISCOUNT             numeric(8,2),
   BUY_COUNT            int,
   GOOD_PRICE           numeric(8,2),
   primary key (GOOD_ID, CART_ID)
);

/*==============================================================*/
/* Table: TF_B_CART                                             */
/*==============================================================*/
create table TF_B_CART
(
   CART_ID              varchar(32) not null,
   USER_ID              varchar(32) not null,
   PRICE                numeric(8,2),
   DISCOUNT             numeric(8,2),
   COUNT                int,
   primary key (CART_ID)
);

/*==============================================================*/
/* Index: IDX_CARD_USER_ID                                      */
/*==============================================================*/
create index IDX_CARD_USER_ID on TF_B_CART
(
   USER_ID
);

/*==============================================================*/
/* Table: TF_B_COUPON                                           */
/*==============================================================*/
create table TF_B_COUPON
(
   COUPON_ID            varchar(32) not null,
   USER_ID              varchar(32),
   CONPON_NAME          varchar(128),
   primary key (COUPON_ID)
);

/*==============================================================*/
/* Index: IDX_COUPON_USER_ID                                    */
/*==============================================================*/
create index IDX_COUPON_USER_ID on TF_B_COUPON
(
   USER_ID
);

/*==============================================================*/
/* Table: TF_B_GOOD                                             */
/*==============================================================*/
create table TF_B_GOOD
(
   GOOD_ID              varchar(32) not null,
   GOOD_NAME            varchar(128),
   GOOD_TYPE            char(2),
   PROVIDER_ID          varchar(32),
   PRICE                numeric(8,2),
   COUNT                bigint,
   CREATE_TIME          timestamp,
   UPDATE_TIME          timestamp,
   UPDATE_USER          varchar(32),
   BRAND                varchar(50),
   GOOD_DESC            varchar(1024),
   GOOD_WEIGHT          numeric(8,2),
   primary key (GOOD_ID)
);

/*==============================================================*/
/* Index: IDX_GOOD_NAME                                         */
/*==============================================================*/
create index IDX_GOOD_NAME on TF_B_GOOD
(
   GOOD_NAME
);

/*==============================================================*/
/* Table: TF_B_HOBBY                                            */
/*==============================================================*/
create table TF_B_HOBBY
(
   HOBBY_ID             varchar(32) not null,
   USER_ID              varchar(32),
   HOBBY_TAG            varchar(32),
   primary key (HOBBY_ID)
);

/*==============================================================*/
/* Index: IDX_HOBBY_USER_ID                                     */
/*==============================================================*/
create index IDX_HOBBY_USER_ID on TF_B_HOBBY
(
   USER_ID
);

/*==============================================================*/
/* Table: TF_B_ORDER                                            */
/*==============================================================*/
create table TF_B_ORDER
(
   ORDER_ID             varchar(32) not null,
   USER_ID              varchar(32) not null,
   STATUS               char(1) comment '1 新建； 2 待支付； 3 支付成功； 4 发送物流； 5 完成',
   ORDER_PRICE          numeric(20,2),
   PAY_TYPE             char(2) comment '1 银行卡； 2 信用卡； 3 微信； 4 支付宝； 5 其他',
   ORDER_ADDRESS        varchar(128),
   ORDER_DISCOUNT       numeric(20,2),
   ORDER_PAY            numeric(20,2),
   CREATE_TIME          timestamp,
   UPDATE_TIME          timestamp,
   primary key (ORDER_ID)
);

/*==============================================================*/
/* Index: IDX_ORDER_USER_ID                                     */
/*==============================================================*/
create index IDX_ORDER_USER_ID on TF_B_ORDER
(
   USER_ID
);

/*==============================================================*/
/* Table: TF_B_ORDER_GOOD                                       */
/*==============================================================*/
create table TF_B_ORDER_GOOD
(
   GOOD_ID              varchar(32) not null,
   ORDER_ID             varchar(32) not null,
   GOOD_PRICE           numeric(8,2),
   GOOD_DISCOUNT        numeric(8,2),
   GOOD_COUNT           int,
   primary key (GOOD_ID, ORDER_ID)
);

/*==============================================================*/
/* Table: TF_B_ORDER_HISTORY                                    */
/*==============================================================*/
create table TF_B_ORDER_HISTORY
(
   ORDER_ID             varchar(32) not null,
   USER_ID              varchar(32),
   STATUS               char(1) comment '1 新建； 2 待支付； 3 支付成功； 4 发送物流； 5 完成',
   ORDER_PRICE          numeric(20,2),
   PAY_TYPE             char(2) comment '1 银行卡； 2 信用卡； 3 微信； 4 支付宝； 5 其他',
   ORDER_DISCOUNT       numeric(20,2),
   ORDER_PAY            numeric(20,2),
   CREATE_TIME          timestamp,
   UPDATE_TIME          timestamp,
   ORDER_ADDRESS        varchar(128),
   primary key (ORDER_ID)
);

/*==============================================================*/
/* Table: TF_B_PROVIDER                                         */
/*==============================================================*/
create table TF_B_PROVIDER
(
   PROVIDER_ID          varchar(32) not null,
   PROVIDER_NAME        varchar(128),
   REGEDIT_NO           varchar(32),
   CONNECT_PERSON       varchar(50),
   PHONE                varchar(20),
   primary key (PROVIDER_ID)
);

/*==============================================================*/
/* Table: TF_B_TRANSPORT                                        */
/*==============================================================*/
create table TF_B_TRANSPORT
(
   TRANSPORT_ID         varchar(32) not null,
   ORDER_ID             varchar(32) not null,
   TRANSPORT_NO         varchar(32),
   COMPANY              varchar(128),
   RECEIVER_DATE        timestamp,
   STATUS               char(1),
   UPDATE_DATE          timestamp,
   CREATE_DATE          timestamp,
   PRICE                numeric(20,2),
   WEIGHT               numeric(20,2),
   primary key (TRANSPORT_ID)
);

alter table TF_B_TRANSPORT comment '1 快递发出； 2 快递签收';

/*==============================================================*/
/* Index: IDX_TRANSPORT_ORDER                                   */
/*==============================================================*/
create index IDX_TRANSPORT_ORDER on TF_B_TRANSPORT
(
   ORDER_ID
);

/*==============================================================*/
/* Table: TF_B_USER_ADDRESS                                     */
/*==============================================================*/
create table TF_B_USER_ADDRESS
(
   ADDRESS_ID           varchar(32) not null,
   USER_ID              varchar(32),
   PROVINCE_CODE        varchar(3),
   CITY_CODE            varchar(4),
   DISTINCT_CODE        varchar(4),
   ADDRESS              varchar(128),
   primary key (ADDRESS_ID)
);

/*==============================================================*/
/* Index: IDX_ADDR_USER_ID                                      */
/*==============================================================*/
create index IDX_ADDR_USER_ID on TF_B_USER_ADDRESS
(
   USER_ID
);

/*==============================================================*/
/* Table: TF_F_USER                                             */
/*==============================================================*/
create table TF_F_USER
(
   USER_ID              varchar(32) not null,
   USER_NAME            varchar(128) not null,
   PASSWORD             varchar(128) not null,
   USER_ALIAS           varchar(50) not null,
   USER_AGE             int not null,
   USER_SEX             char(1) not null,
   USER_ADDRESS         varchar(256) not null,
   STATUS               char(1) comment '0 有效； 1 无效',
   USER_MAIL            varchar(50) not null,
   USER_PHON            varchar(20) not null,
   USER_LEVEL           char(1),
   CREATE_DATE          timestamp,
   UPDATE_DATE          timestamp,
   primary key (USER_ID)
);

/*==============================================================*/
/* Index: IDX_USER_NAME                                         */
/*==============================================================*/
create index IDX_USER_NAME on TF_F_USER
(
   USER_NAME
);

