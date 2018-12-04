/*
 Navicat Premium Data Transfer

 Source Server         : mysql root
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : sell

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 04/12/2018 21:56:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `detail_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `order_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属订单',
  `product_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品id',
  `product_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `product_price` decimal(8, 2) NOT NULL COMMENT '当前价格，单位分',
  `product_quantity` int(11) NOT NULL COMMENT '数量',
  `product_icon` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小图',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期，自动生成',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改日期，自动更新',
  PRIMARY KEY (`detail_id`) USING BTREE,
  INDEX `idx_order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES ('20181114171342921237016', '201811141713429212370', '100100100111', '麻婆豆腐', 19.90, 2, '#', '2018-11-14 17:13:50', '2018-11-14 17:13:50');
INSERT INTO `order_detail` VALUES ('201811152348493304634001', '201811152348493304634', '100100100111', '麻婆豆腐', 19.90, 12, '#', '2018-10-07 20:12:37', '2018-10-07 20:12:37');
INSERT INTO `order_detail` VALUES ('201811271728448918234001', '201811271728448918234', '100100100112', '家常豆腐', 19.90, 9, '#', '2018-11-27 17:28:47', '2018-11-27 17:28:47');

-- ----------------------------
-- Table structure for order_master
-- ----------------------------
DROP TABLE IF EXISTS `order_master`;
CREATE TABLE `order_master`  (
  `order_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `buyer_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '买家名字',
  `buyer_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '买家电话',
  `buyer_address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '买家地址',
  `buyer_openid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '买家微信openid',
  `order_amount` decimal(8, 2) NOT NULL COMMENT '订单总金额',
  `order_status` tinyint(3) NOT NULL DEFAULT 0 COMMENT '订单状态，默认为新下单',
  `pay_status` tinyint(3) NOT NULL DEFAULT 0 COMMENT '支付状态，默认未支付',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期，自动生成',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改日期，自动更新',
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `idx_buyer_openid`(`buyer_openid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_master
-- ----------------------------
INSERT INTO `order_master` VALUES ('201811141634505876780', '马星宇', '13529097020', 'A7', 'icefamer', 156.55, 0, 0, '2018-11-14 16:34:50', '2018-11-14 16:34:50');
INSERT INTO `order_master` VALUES ('201811152348493304634', 'REDwolf', '13529097020', 'VIP5', '9844176', 238.80, 0, 0, '2018-11-15 23:48:50', '2018-11-15 23:48:50');
INSERT INTO `order_master` VALUES ('201811271728448918234', 'icefamer', '13529097020', 'VIP5', '9844176', 179.10, 0, 0, '2018-11-27 17:28:47', '2018-11-27 17:28:47');

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category`  (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类目名称',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期，自动生成',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改日期，自动更新',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10002 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_category
-- ----------------------------
INSERT INTO `product_category` VALUES (10000, '豆腐类', 100100100, '2018-10-07 20:43:18', '2018-10-07 20:43:18');
INSERT INTO `product_category` VALUES (10001, '汤菜', 100100101, '2018-10-07 20:53:21', '2018-10-07 20:53:21');

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info`  (
  `product_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `product_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `product_price` decimal(8, 2) NOT NULL COMMENT '单价,8位长度，小数点后两位精度',
  `product_stock` int(11) NOT NULL COMMENT '库存',
  `product_description` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品描述',
  `product_icon` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小图引用地址',
  `product_status` tinyint(3) NULL DEFAULT 0 COMMENT '商品状态，0正常，1下架',
  `category_type` int(11) NOT NULL COMMENT '商品所属类目',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期，自动生成',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改日期，自动更新',
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_info
-- ----------------------------
INSERT INTO `product_info` VALUES ('100100100111', '麻婆豆腐', 19.90, 66, NULL, '#', 0, 100100100, '2018-10-07 20:12:37', '2018-10-07 20:12:37');
INSERT INTO `product_info` VALUES ('100100100112', '家常豆腐', 19.90, 83, NULL, '#', 0, 100100100, '2018-10-07 20:18:10', '2018-10-07 20:18:10');
INSERT INTO `product_info` VALUES ('100100100113', '铁板包浆豆腐', 29.90, 99, NULL, '#', 0, 100100100, '2018-10-07 20:19:10', '2018-10-07 20:19:10');
INSERT INTO `product_info` VALUES ('100100100114', '王牌豆腐', 39.90, 0, NULL, '#', 1, 100100100, '2018-10-07 20:19:48', '2018-10-07 20:19:48');

SET FOREIGN_KEY_CHECKS = 1;
