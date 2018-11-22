CREATE TABLE `t_ios_verify_receipt` (
  `id` bigint primary key NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `payment_id` varchar(32) not null COMMENT '支付交易流水号',
  `confirm_message` text not null COMMENT '需要提交的确认消息',
  `user_id` varchar(32) not null COMMENT '用户身份标识',
  `product_code` varchar(32) default null COMMENT '产品类型',
  `product_type` varchar(8) default null COMMENT '产品编码',
  `fee` varchar(32) default null COMMENT '价格',
  `apple_product_id` varchar(32) default null COMMENT '苹果计费点',
  `apple_app_id` varchar(32) default null COMMENT '苹果appId',
  `apple_transaction_id` varchar(32) default null COMMENT '苹果实物Id',
  `callback_url` varchar(1024) default null COMMENT '回调通知地址',
  `status` varchar(32) default null COMMENT '订单状态',
  `result_code` varchar(32) default null COMMENT '苹果支付凭证校验返回码',
  `result_msg` varchar(1024) default null COMMENT '苹果支付凭证校验返回码描述和异常描述',
  `create_time` TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP  COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX idx_paymentid ON t_ios_verify_receipt (payment_id);