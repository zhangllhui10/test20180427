/*
Navicat MySQL Data Transfer

Source Server         : app_platform
Source Server Version : 50635
Source Host           : 172.16.254.240:3306
Source Database       : hs_app_platform

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2018-01-04 10:46:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hui_app_acquirer_apply
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_acquirer_apply`;
CREATE TABLE `hui_app_acquirer_apply` (
  `applyid` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '主键ID',
  `acquirername` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '销售渠道名称',
  `acquirerno` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '销售渠道编号',
  `protocolname` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '协议名称',
  `protocolno` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '协议编号',
  `issuer` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '彩票发行方',
  `provider` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '彩票提供方',
  `provinceid` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '省份编号',
  `provincename` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '省份名称',
  `protocolphoto` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '三方协议扫描件',
  `formphoto` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '业务入网申请表扫描件',
  `starttime` datetime DEFAULT NULL COMMENT '协议生效时间',
  `endtime` datetime DEFAULT NULL COMMENT '协议截止时间',
  `bankaccount` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算卡账户',
  `accountname` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算卡账户名',
  `salestatus` varchar(2) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '销售状态（0 待启用/1 启用/2 停用/3 终止）',
  `auditstatus` varchar(2) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '审核状态（0 未提交/1 审核中/2 审核通过/3 审核拒绝）',
  `auditreason` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '审核拒绝理由',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `creater` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `modifytime` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '修改人',
  `bankname` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '开户总行名称',
  `bankcode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '开户总行编码',
  `branchname` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '开户支行名称',
  `branchcode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '开户支行编码',
  `channelmercno` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '收单机构分配汇拾商户号',
  `accounttype` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '结算卡账户类型（1 对公账户/2 对私账户）',
  `financephone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '财务手机号',
  `paycommission` float DEFAULT NULL COMMENT '支付手续费',
  PRIMARY KEY (`applyid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='销售渠道入网申请表';

-- ----------------------------
-- Table structure for hui_app_acquirer_hschannel
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_acquirer_hschannel`;
CREATE TABLE `hui_app_acquirer_hschannel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `acquirerno` varchar(64) DEFAULT NULL COMMENT '销售渠道编号',
  `acquirername` varchar(255) DEFAULT NULL COMMENT '销售渠道名称',
  `provinceid` varchar(20) DEFAULT NULL COMMENT '省份编号',
  `provincename` varchar(255) DEFAULT NULL COMMENT '省份名称',
  `channelmercid` varchar(64) DEFAULT NULL COMMENT '汇拾在销售渠道商户号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2017110206 DEFAULT CHARSET=utf8 COMMENT='汇拾对应销售渠道的商户号记录表';

-- ----------------------------
-- Table structure for hui_app_acquirer_info
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_acquirer_info`;
CREATE TABLE `hui_app_acquirer_info` (
  `acquirerid` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '主键ID',
  `acquirername` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '销售渠道名称',
  `acquirerno` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '销售渠道编号',
  `bankaccount` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算卡账户',
  `accountname` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算卡账户名',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `creater` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `modifytime` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '修改人',
  `secretkey` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '秘钥',
  `bankname` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '开户总行名称',
  `bankcode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '开户总行编码',
  `branchname` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '开户支行名称',
  `branchcode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '开户支行编码',
  `accounttype` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '结算卡账户类型（1 对公账户/2 对私账户）',
  `financephone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '财务手机号',
  `paycommission` float DEFAULT NULL COMMENT '支付手续费',
  PRIMARY KEY (`acquirerid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='销售渠道信息表';

-- ----------------------------
-- Table structure for hui_app_acquirer_lottery
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_acquirer_lottery`;
CREATE TABLE `hui_app_acquirer_lottery` (
  `id` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '主键ID',
  `applyid` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '申请ID',
  `acquirerno` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '销售渠道编号',
  `lotterycode` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '彩票类型编号',
  `lotteryname` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '彩种名称',
  `unionpayprop` float DEFAULT NULL COMMENT '银联分润比例',
  `acquirerprop` float DEFAULT NULL COMMENT '机构分润比例',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='渠道销售彩票信息表';

-- ----------------------------
-- Table structure for hui_app_lottery_info
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_lottery_info`;
CREATE TABLE `hui_app_lottery_info` (
  `lotterycode` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '彩票类型编号',
  `lotteryname` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '彩种名称',
  `lotteryicon` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '彩种图片',
  `price` bigint(13) DEFAULT NULL COMMENT '每注售价',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `creater` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `modifytime` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`lotterycode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='彩种信息表';

-- ----------------------------
-- Table structure for hui_app_lottery_order
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_lottery_order`;
CREATE TABLE `hui_app_lottery_order` (
  `orderid` varchar(64) NOT NULL,
  `stationprovince` varchar(32) DEFAULT '' COMMENT '投注站省份',
  `stationno` varchar(32) DEFAULT NULL COMMENT '投注站编号',
  `orderamount` int(11) DEFAULT NULL COMMENT '订单总金额',
  `outtradeno` varchar(64) DEFAULT NULL COMMENT '支付交易流水',
  `merchantno` varchar(64) DEFAULT NULL COMMENT '商户编号',
  `orderno` varchar(32) DEFAULT NULL COMMENT '投注流⽔号',
  `serialno` varchar(32) DEFAULT NULL COMMENT '设备SN',
  `issuenumber` varchar(32) DEFAULT NULL COMMENT '投注期号',
  `ordertime` datetime DEFAULT NULL COMMENT '订单投注时间',
  `gamecode` varchar(16) DEFAULT NULL COMMENT '彩票玩法代码',
  `paytime` datetime DEFAULT NULL COMMENT '支付时间',
  `source` tinyint(4) DEFAULT NULL COMMENT '订单来源 1-pos 2-App',
  `spid` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '彩票前置系统为汇拾分配的唯一性标识',
  `status` int(11) DEFAULT NULL COMMENT '状态 1-未支付，2-出票成功，3-取消, 4-已过期、5-出票失败',
  `lotterystatus` varchar(1) DEFAULT '0' COMMENT '开奖状态：0--未开奖，1--已开奖',
  `winstatus` tinyint(1) DEFAULT '1' COMMENT '是否中奖：1-未中奖，2-中奖',
  `isbigprize` varchar(2) DEFAULT '0' COMMENT '是否为大奖,0-不是大奖，1-大奖',
  `winprize` int(11) DEFAULT '0' COMMENT '中奖金额',
  `lotterytime` datetime DEFAULT NULL COMMENT '投注截止时间',
  `channelmercid` varchar(64) DEFAULT NULL COMMENT '汇拾在收单机构商户号',
  `acquirerno` varchar(64) DEFAULT NULL COMMENT '收单机构id',
  `bonusstatus` tinyint(1) DEFAULT '0' COMMENT '彩金领取：1-未领取 2-已领取  3-领奖处理中   4-领取失败    5-证件审核中   6-证件审核失败 7- 证件审核成功 8-线下领奖 ',
  `remark` text COMMENT '订单json字符串',
  `createdate` datetime DEFAULT NULL COMMENT '订单时间',
  `updatedate` datetime DEFAULT NULL COMMENT '更新时间',
  `userphone` varchar(15) DEFAULT NULL COMMENT '用户手机号',
  PRIMARY KEY (`orderid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='彩票订单';

-- ----------------------------
-- Table structure for hui_app_lottery_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_lottery_order_detail`;
CREATE TABLE `hui_app_lottery_order_detail` (
  `tradesn` varchar(64) NOT NULL COMMENT '序列号',
  `orderid` varchar(64) NOT NULL COMMENT '订单表ID 关联订单表',
  `orderno` varchar(64) DEFAULT NULL COMMENT '投注流⽔号(英泰)',
  `orderamount` int(11) DEFAULT NULL COMMENT '投注成功⾦额（单位：分）',
  `gamecode` varchar(255) DEFAULT NULL COMMENT '彩票玩法代码',
  `issuenumber` varchar(32) DEFAULT NULL COMMENT '投注期号',
  `ordertime` datetime DEFAULT NULL COMMENT '订单投注时间',
  `lotterytime` datetime DEFAULT NULL COMMENT '开奖时间',
  `selecttype` tinyint(1) DEFAULT NULL COMMENT '选码⽅式（1机选;2⾃选）',
  `bettype` varchar(10) DEFAULT NULL COMMENT '投注⽅式（乐透型：100单式；101复式；102胆拖。）',
  `multipl` int(2) DEFAULT NULL COMMENT '倍数',
  `codedetail` varchar(200) DEFAULT NULL COMMENT '注码详情',
  `bonus` int(11) DEFAULT NULL COMMENT '中奖金额',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`tradesn`),
  KEY `orderid_index` (`orderid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='彩票订单投注详情';

-- ----------------------------
-- Table structure for hui_app_lottery_past
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_lottery_past`;
CREATE TABLE `hui_app_lottery_past` (
  `id` varchar(50) DEFAULT NULL,
  `issuenumber` varchar(32) DEFAULT NULL COMMENT '期号',
  `lotterytime` datetime DEFAULT NULL COMMENT '开奖日期',
  `lotteryendtime` datetime DEFAULT NULL COMMENT '兑奖截止日期',
  `lotterynumber` varchar(100) DEFAULT NULL COMMENT '开奖号码，逗号隔开',
  `lotterytype` varchar(30) DEFAULT NULL COMMENT '彩票类型',
  `moneypool` varchar(50) DEFAULT NULL COMMENT '奖池',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for hui_app_lottery_profit_prop
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_lottery_profit_prop`;
CREATE TABLE `hui_app_lottery_profit_prop` (
  `id` varchar(64) NOT NULL,
  `lotterycode` varchar(64) DEFAULT NULL COMMENT '彩票类型编号',
  `provinceid` varchar(64) DEFAULT NULL COMMENT '省份编号',
  `totalprop` float DEFAULT NULL COMMENT '总分润比例',
  `status` varchar(2) DEFAULT NULL COMMENT '彩票销售状态：0 待启用/1 启用/2 停用/3 终止',
  `lotteryicon` varchar(200) DEFAULT NULL COMMENT '图片',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='彩票总的分润利率';

-- ----------------------------
-- Table structure for hui_app_lottery_region
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_lottery_region`;
CREATE TABLE `hui_app_lottery_region` (
  `id` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '主键ID',
  `lotterycode` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '彩票类型编号',
  `provinceid` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '销售省份编号',
  `provincename` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '省份名称',
  `lotteryname` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '彩种名称',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `creater` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `modifytime` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='彩票销售区域表';

-- ----------------------------
-- Table structure for hui_app_merchant_apply
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_merchant_apply`;
CREATE TABLE `hui_app_merchant_apply` (
  `applyid` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '主键ID',
  `merchantid` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '商户ID',
  `salestatus` varchar(2) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '销售状态（0 待启用/1 启用/2 停用/3 终止）',
  `auditstatus` varchar(2) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '入网审核状态（0 未提交/1 审核中/2 审核通过/3 审核拒绝）',
  `lotterystatus` varchar(2) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '彩票审核状态（0 未提交/1 审核中/2 审核通过/3 审核拒绝）',
  `auditreason` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '业务审核拒绝理由',
  `lotteryreason` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '彩票审核拒绝理由',
  `cardrate` float DEFAULT NULL COMMENT '贷记卡品牌费率',
  `paycommission` float DEFAULT NULL COMMENT '支付手续费',
  `formphoto` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '渠道商户申请表扫描件',
  `protocolname` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '协议名称',
  `protocolno` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '协议编号',
  `starttime` datetime DEFAULT NULL COMMENT '协议生效时间',
  `endtime` datetime DEFAULT NULL COMMENT '协议截止时间',
  `protocolphoto` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '渠道商户四方协议扫描件',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `creater` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `modifytime` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`applyid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='渠道商户入网申请表';

-- ----------------------------
-- Table structure for hui_app_merchant_info
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_merchant_info`;
CREATE TABLE `hui_app_merchant_info` (
  `merchantid` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '主键ID',
  `merchantno` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '商户编号',
  `merchantname` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '商户名称',
  `merchantshort` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '商户简称',
  `provinceid` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '省份编号',
  `provincename` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '省份名称',
  `cityid` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '城市编号',
  `cityname` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '城市名称',
  `address` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '商户地址',
  `industryno` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '商户所属行业编号',
  `industryname` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '商户所属行业名称',
  `contactperson` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '联系人',
  `contactnumber` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '联系电话',
  `legalperson` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '商户法人',
  `legalnumber` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '法人联系电话',
  `legaladdress` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '法人联系地址',
  `legalphoto` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '渠道商户企业营业执照扫描件',
  `bankaccount` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算卡账户',
  `accountname` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '结算卡账户名称',
  `acquirerno` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '销售渠道编号',
  `stationno` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '投注站编号',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `creater` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `modifytime` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '修改人',
  `status` varchar(2) COLLATE utf8_bin DEFAULT '1' COMMENT '状态（0 待启用/1 启用）',
  `bankname` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '开户总行名称',
  `bankcode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '开户总行编码',
  `branchname` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `branchcode` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '开户支行编码',
  `paymerchantno` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '机构录入商户号',
  `accounttype` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '结算卡账户类型（1 对公账户/2 对私账户）',
  `financephone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '财务手机号',
  PRIMARY KEY (`merchantid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='渠道商户信息表';

-- ----------------------------
-- Table structure for hui_app_merchant_lottery
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_merchant_lottery`;
CREATE TABLE `hui_app_merchant_lottery` (
  `id` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '主键ID',
  `applyid` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '申请ID',
  `merchantid` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '商户ID',
  `lotterycode` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '彩票类型编号',
  `lotteryname` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '彩种名称',
  `merchantprop` float DEFAULT NULL COMMENT '商户分润比例',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='渠道商户销售彩票信息表';

-- ----------------------------
-- Table structure for hui_app_merchant_terminal
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_merchant_terminal`;
CREATE TABLE `hui_app_merchant_terminal` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `merchantid` varchar(64) DEFAULT NULL COMMENT '商户ID',
  `terminalno` varchar(64) DEFAULT NULL COMMENT 'POS终端S/N编号',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `creater` varchar(64) DEFAULT NULL COMMENT '创建人',
  `modifytime` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier` varchar(64) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='渠道商户POS终端表';

-- ----------------------------
-- Table structure for hui_app_prizefile_info
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_prizefile_info`;
CREATE TABLE `hui_app_prizefile_info` (
  `prizefileid` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '主键',
  `gamecode` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '彩票类型',
  `issuenumber` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '彩票期号',
  `lotterytime` datetime DEFAULT NULL COMMENT '开奖时间',
  `filecheckcode` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '⽂件校验码',
  `status` varchar(2) CHARACTER SET utf8 DEFAULT NULL COMMENT '文件是否已处理的状态，0-未处理，1-已处理',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`prizefileid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='开奖文件的信息';

-- ----------------------------
-- Table structure for hui_app_profit_result
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_profit_result`;
CREATE TABLE `hui_app_profit_result` (
  `profitid` varchar(64) NOT NULL COMMENT '主键',
  `gamecode` varchar(255) DEFAULT NULL COMMENT '彩票种类',
  `provinceid` varchar(64) DEFAULT NULL COMMENT '省份',
  `partner` varchar(255) DEFAULT NULL COMMENT '分润方',
  `rate` int(11) DEFAULT NULL COMMENT '分润比例',
  `money` int(11) DEFAULT NULL COMMENT '分润的钱',
  `profitdate` varchar(255) DEFAULT NULL COMMENT '分润时间具体到某年某月',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`profitid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='分润结果表';

-- ----------------------------
-- Table structure for hui_app_qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_qrtz_blob_triggers`;
CREATE TABLE `hui_app_qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `hui_app_qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `hui_app_qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for hui_app_qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_qrtz_calendars`;
CREATE TABLE `hui_app_qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for hui_app_qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_qrtz_cron_triggers`;
CREATE TABLE `hui_app_qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `hui_app_qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `hui_app_qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for hui_app_qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_qrtz_fired_triggers`;
CREATE TABLE `hui_app_qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for hui_app_qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_qrtz_job_details`;
CREATE TABLE `hui_app_qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for hui_app_qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_qrtz_locks`;
CREATE TABLE `hui_app_qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for hui_app_qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_qrtz_paused_trigger_grps`;
CREATE TABLE `hui_app_qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for hui_app_qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_qrtz_scheduler_state`;
CREATE TABLE `hui_app_qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for hui_app_qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_qrtz_simple_triggers`;
CREATE TABLE `hui_app_qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `hui_app_qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `hui_app_qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for hui_app_qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_qrtz_simprop_triggers`;
CREATE TABLE `hui_app_qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `hui_app_qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `hui_app_qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for hui_app_qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_qrtz_triggers`;
CREATE TABLE `hui_app_qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  CONSTRAINT `hui_app_qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `hui_app_qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for hui_app_sys_city
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_sys_city`;
CREATE TABLE `hui_app_sys_city` (
  `provinceid` varchar(64) NOT NULL COMMENT '省份编号',
  `cityid` varchar(64) NOT NULL DEFAULT '' COMMENT '城市编号',
  `provincename` varchar(255) DEFAULT NULL COMMENT '省份名称',
  `cityname` varchar(255) DEFAULT NULL COMMENT '城市名称',
  `type` varchar(2) DEFAULT NULL COMMENT '类型：1省份，2城市',
  `sort` int(10) unsigned DEFAULT '0' COMMENT '排序',
  `status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '状态：0禁用，1启用',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`provinceid`,`cityid`),
  KEY `sort` (`sort`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地区信息';

-- ----------------------------
-- Table structure for hui_app_system_picture
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_system_picture`;
CREATE TABLE `hui_app_system_picture` (
  `id` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '主键ID',
  `picture` mediumblob COMMENT '图片',
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'MD5值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='图片存储表';

-- ----------------------------
-- Table structure for hui_app_user_bankcard
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_user_bankcard`;
CREATE TABLE `hui_app_user_bankcard` (
  `id` varchar(50) COLLATE utf8_bin NOT NULL,
  `uid` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `cardno` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '银行卡号',
  `cardname` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '银行名称',
  `cardholder` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '持卡人姓名',
  `reservephone` varchar(11) COLLATE utf8_bin DEFAULT NULL COMMENT '银行预留手机号',
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_uid` (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户银行卡';

-- ----------------------------
-- Table structure for hui_app_user_info
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_user_info`;
CREATE TABLE `hui_app_user_info` (
  `uid` varchar(64) NOT NULL DEFAULT '' COMMENT '用户编号',
  `nickname` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(50) DEFAULT NULL COMMENT '手机',
  `name` varchar(100) DEFAULT NULL COMMENT '真实姓名',
  `identity` varchar(50) DEFAULT NULL COMMENT '身份证',
  `idtype` varchar(2) DEFAULT NULL COMMENT '身份证类型',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `sex` varchar(1) DEFAULT '0' COMMENT '1男性，2女性，0是未知',
  `jointime` datetime NOT NULL COMMENT '注册时间',
  `updatetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `point` int(11) DEFAULT '0' COMMENT '用户总积分',
  `status` varchar(2) NOT NULL DEFAULT '1' COMMENT '状态,1-正常,2-停用,3-删除 ',
  `creditgrade` int(11) DEFAULT '0' COMMENT '信用等级',
  `creditscore` int(11) DEFAULT '0' COMMENT '信用分数',
  `profession` varchar(50) CHARACTER SET utf32 DEFAULT NULL COMMENT '职业',
  `portraiturl` varchar(255) DEFAULT NULL COMMENT '头像',
  `city` varchar(50) DEFAULT NULL COMMENT '地区',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `qq` varchar(50) DEFAULT NULL COMMENT 'QQ号',
  `education` varchar(50) DEFAULT NULL COMMENT '学历',
  `hobby` varchar(200) DEFAULT NULL COMMENT '爱好',
  `imagetype` varchar(2) DEFAULT '0' COMMENT '头像的类型，0-默认图片,1-上传的图片',
  `regposition` varchar(50) DEFAULT NULL COMMENT '注册地址(位置)',
  `regip` varchar(50) DEFAULT NULL COMMENT '注册IP',
  `regtype` varchar(2) NOT NULL DEFAULT '0' COMMENT '注册类型（1手机2微信3QQ4微博）',
  PRIMARY KEY (`uid`),
  KEY `phone` (`phone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户基本信息';

-- ----------------------------
-- Table structure for hui_app_user_order
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_user_order`;
CREATE TABLE `hui_app_user_order` (
  `id` varchar(32) NOT NULL,
  `uid` varchar(32) NOT NULL COMMENT '用户编号',
  `orderid` varchar(32) NOT NULL COMMENT '订单编号',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `user_order_uid_index` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for hui_app_user_winorder_bigprize_handle
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_user_winorder_bigprize_handle`;
CREATE TABLE `hui_app_user_winorder_bigprize_handle` (
  `id` varchar(50) NOT NULL,
  `uid` varchar(50) DEFAULT NULL COMMENT '用户ID',
  `department` varchar(50) DEFAULT NULL COMMENT '部门',
  `position` varchar(50) DEFAULT NULL COMMENT '职位',
  `handler` varchar(50) DEFAULT NULL COMMENT '奖金处理人员',
  `syshandler` varchar(50) DEFAULT NULL COMMENT '系统操作人',
  `screenshort` longblob COMMENT '支票截图',
  `winnerphoto` longblob COMMENT '中奖人照片',
  `winnername` varchar(50) DEFAULT NULL COMMENT '中奖人名字',
  `handletime` datetime DEFAULT NULL COMMENT '领奖时间',
  `orderid` varchar(50) DEFAULT NULL COMMENT '订单号',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for hui_app_user_winorder_medium_handle
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_user_winorder_medium_handle`;
CREATE TABLE `hui_app_user_winorder_medium_handle` (
  `id` varchar(50) DEFAULT NULL,
  `uid` varchar(50) DEFAULT NULL COMMENT '用户',
  `faceside` longblob COMMENT '身份证照片',
  `backside` longblob,
  `name` varchar(254) DEFAULT NULL COMMENT '中奖人名',
  `orderid` varchar(50) DEFAULT NULL COMMENT '中奖订单',
  `status` varchar(1) DEFAULT NULL COMMENT '审核状态：0-未审核 7-审核成功 6-审核失败 2-派发成功 4派发失败',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `department` varchar(50) DEFAULT NULL COMMENT '部门',
  `position` varchar(50) DEFAULT NULL COMMENT '职位',
  `handler` varchar(50) DEFAULT NULL COMMENT '奖金处理人员',
  `syshandler` varchar(50) DEFAULT NULL COMMENT '系统操作人',
  `bankno` varchar(50) DEFAULT NULL COMMENT '银行卡号',
  `bankname` varchar(50) DEFAULT NULL COMMENT '银行名称',
  `screenshort` longblob COMMENT '支票截图',
  `idcardno` varchar(20) DEFAULT NULL COMMENT '身份证号码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for hui_app_user_withdraw_record
-- ----------------------------
DROP TABLE IF EXISTS `hui_app_user_withdraw_record`;
CREATE TABLE `hui_app_user_withdraw_record` (
  `id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `uid` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `tradeno` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '第三方订单号',
  `orderid` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '兑奖订单号',
  `payeeno` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '收款人银行账户',
  `payeename` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '收款人姓名',
  `payeebank` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '收款人银行名称',
  `cash` double(11,2) NOT NULL COMMENT '提现金额',
  `fee` double(11,2) NOT NULL COMMENT '手续费',
  `status` varchar(2) COLLATE utf8_bin NOT NULL COMMENT '1申请提现  2提现成功  3提现失败',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `createtime` datetime NOT NULL,
  `updatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户提现记录';

-- ----------------------------
-- Table structure for hui_timetask_schedule
-- ----------------------------
DROP TABLE IF EXISTS `hui_timetask_schedule`;
CREATE TABLE `hui_timetask_schedule` (
  `id` varchar(50) NOT NULL,
  `jobclass` varchar(50) DEFAULT NULL COMMENT 'spring的bean名称',
  `jobname` varchar(50) DEFAULT NULL COMMENT '任务名称 同一个任务ID可以关联多个任务，任务名称必须是唯一的',
  `status` varchar(2) DEFAULT NULL COMMENT '任务状态 0停用 1启用',
  `cronexpression` varchar(50) DEFAULT NULL COMMENT ' 任务运行时间表达式',
  `lastexecutetime` datetime DEFAULT NULL COMMENT '最近一次执行时间',
  `nextexecutetime` datetime DEFAULT NULL COMMENT '下一次执行时间',
  `laststatus` varchar(2) DEFAULT NULL COMMENT '最近一次执行结果 0失败 1成功',
  `updatetime` datetime DEFAULT NULL COMMENT ' 更新时间，用于集群自动动态更新任务修改',
  `expiredate` datetime DEFAULT NULL COMMENT '过期时间，任务到了过期时间不再执行，自动会把state修改为0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
