ALTER TABLE `hui_app_lottery_order`
ADD `ordertype` VARCHAR (2) DEFAULT '1' COMMENT '订单类型：1普通，2赠送';

ALTER TABLE `hui_app_lottery_order`
ADD `uid` VARCHAR (32) DEFAULT NULL COMMENT '用户ID';

ALTER TABLE `hui_app_lottery_order`
ADD COLUMN `actualamount`  int(11) NULL COMMENT '实际金额' AFTER `orderamount`;

ALTER TABLE `hui_app_lottery_order_detail`
DROP COLUMN `status`,
ADD COLUMN `status`  varchar(2) NULL DEFAULT 0 COMMENT '出票状态 0初始化 1成功 2失败' AFTER `bonus`;

ALTER TABLE `hui_app_lottery_order`
ADD COLUMN `betnumber`  int(11) NULL DEFAULT 0 COMMENT '注数' AFTER `uid`;

ALTER TABLE `hui_app_lottery_order`
MODIFY COLUMN `winprize`  bigint NULL DEFAULT 0 COMMENT '中奖金额' AFTER `isbigprize`,
CHANGE COLUMN `isbigprize` `prizelevel`  varchar(2) NULL DEFAULT '1' COMMENT '中奖等级,1-小奖，2-中奖，3-大奖' AFTER `winstatus`,
ADD COLUMN `sendprize`  bigint NULL COMMENT '派奖金额' AFTER `betnumber`;

