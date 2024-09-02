/*
 Navicat Premium Data Transfer

 Source Server         : ttag
 Source Server Type    : MySQL
 Source Server Version : 80300
 Source Host           : 47.113.145.156:3306
 Source Schema         : ttag-order

 Target Server Type    : MySQL
 Target Server Version : 80300
 File Encoding         : 65001

 Date: 08/06/2024 13:18:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_detail`;
CREATE TABLE `tb_order_detail`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单详情id ',
  `order_id` bigint NOT NULL COMMENT '订单id',
  `item_id` bigint NOT NULL COMMENT 'sku商品id',
  `num` int NOT NULL COMMENT '购买数量',
  `name` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '商品标题',
  `price` int NOT NULL COMMENT '价格,单位：分',
  `image` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '商品图片',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_id`(`order_id` ASC) USING BTREE,
  INDEX `key_order_id`(`order_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 102 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '订单详情表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of tb_order_detail
-- ----------------------------
INSERT INTO `tb_order_detail` VALUES (26, 1797821164009598977, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (27, 1797821971727691777, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (28, 1797821977335476226, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (29, 1797821981861130241, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (30, 1797821985870884866, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (31, 1797821990379761666, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (32, 1797821994695700481, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (33, 1797821998890004481, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (34, 1797822003457601538, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (35, 1797822007278612481, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (36, 1797822011300950018, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (37, 1797822015205847041, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (38, 1797822018766811137, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (39, 1797822022810120194, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (40, 1797822026702434305, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (52, 1798170856588898306, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (53, 1798170980924846081, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (54, 1798170998066966530, 16, 1, '总党表种力好', 84, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (55, 1798171011539070977, 15, 1, '成什样我', 27, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (56, 1798171021479571457, 14, 1, '称院口到', 6, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (57, 1798171031562678273, 13, 1, '各标群元', 99, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (58, 1798171041700311042, 12, 1, '队目而角', 20, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (59, 1798171051309461506, 11, 1, '为去他', 39, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (60, 1798171062424367106, 10, 1, '各酸是放', 14, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (61, 1798171078132035586, 9, 1, '以集众', 4, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (62, 1798171094405935105, 8, 1, '世强收', 57, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (63, 1798171104329658369, 7, 1, '际半力政发内', 69, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (64, 1798171116484751361, 6, 1, '该院中石可导', 36, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (65, 1798171127243141121, 5, 1, '习里示表实它适', 86, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (66, 1798171158226464769, 4, 1, '看多风济低被格', 97, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (67, 1798180797601083394, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (68, 1798182308053839873, 4, 1, '看多风济低被格', 97, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (69, 1798214731042975745, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (70, 1798307759059959810, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (71, 1798309865904996353, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (74, 1798312734464999426, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (75, 1798312751728754689, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (76, 1798313162862821377, 17, 1, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (77, 1798313693400334338, 16, 1, '总党表种力好', 84, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (78, 1798321375230361602, 4, 1, '看多风济低被格', 97, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (81, 1798355955647508482, 17, 5, '报长温', 91, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (83, 1798737120213938177, 1, 12, '共带利都', 26, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (84, 1798737936433885186, 3, 12, '做性性素离再走', 54, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (86, 1799049967515529217, 22, 12, 'Apple', 9, 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/96367da700c34709a33c4435a90896a9.jpg');
INSERT INTO `tb_order_detail` VALUES (87, 1799051371294552066, 22, 12, 'Apple', 9, 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/96367da700c34709a33c4435a90896a9.jpg');
INSERT INTO `tb_order_detail` VALUES (88, 1799055663521398785, 1, 1, '共带利都', 26, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (89, 1799055725215416322, 2, 2, '变历状示场在', 61, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (90, 1799055741686448130, 3, 2, '做性性素离再走', 54, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (91, 1799055750679035905, 1, 2, '共带利都', 26, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (92, 1799055768358027265, 2, 3, '变历状示场在', 61, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (93, 1799055784095055873, 3, 4, '做性性素离再走', 54, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (94, 1799056095366938626, 3, 4, '做性性素离再走', 54, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (95, 1799056111783444482, 2, 2, '变历状示场在', 61, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (96, 1799056128124452866, 2, 1, '变历状示场在', 61, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (98, 1799056152841486337, 3, 4, '做性性素离再走', 54, 'http://dummyimage.com/400x400');
INSERT INTO `tb_order_detail` VALUES (99, 1799058469405917186, 22, 12, 'Apple', 9, 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/96367da700c34709a33c4435a90896a9.jpg');
INSERT INTO `tb_order_detail` VALUES (100, 1799061951392329730, 22, 12, 'Apple', 9, 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/96367da700c34709a33c4435a90896a9.jpg');
INSERT INTO `tb_order_detail` VALUES (101, 1799083981357527041, 22, 12, 'Apple', 9, 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/96367da700c34709a33c4435a90896a9.jpg');

SET FOREIGN_KEY_CHECKS = 1;
