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

 Date: 08/06/2024 13:18:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_logistics
-- ----------------------------
DROP TABLE IF EXISTS `tb_logistics`;
CREATE TABLE `tb_logistics`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `courier` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tracking_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` enum('pending','in_transit','delivered') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'pending',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_id`(`order_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_logistics
-- ----------------------------
INSERT INTO `tb_logistics` VALUES (1, 1797821164009598977, '等待送货人员中', '正在生成', 'pending');
INSERT INTO `tb_logistics` VALUES (2, 1797821971727691777, '等待送货人员中', '正在生成', 'pending');
INSERT INTO `tb_logistics` VALUES (3, 1797821977335476226, '等待送货人员中', '正在生成', 'pending');
INSERT INTO `tb_logistics` VALUES (4, 1797821981861130241, '等待送货人员中', '正在生成', 'pending');
INSERT INTO `tb_logistics` VALUES (5, 1797821985870884866, '等待送货人员中', '正在生成', 'pending');
INSERT INTO `tb_logistics` VALUES (6, 1797821990379761666, '等待送货人员中', '正在生成', 'pending');
INSERT INTO `tb_logistics` VALUES (7, 1797821994695700481, '等待送货人员中', '正在生成', 'pending');
INSERT INTO `tb_logistics` VALUES (8, 1797821998890004481, '等待送货人员中', '正在生成', 'pending');
INSERT INTO `tb_logistics` VALUES (9, 1797822003457601538, '等待送货人员中', '正在生成', 'pending');
INSERT INTO `tb_logistics` VALUES (10, 1798182308053839873, '等待送货人员中', '正在生成', 'pending');
INSERT INTO `tb_logistics` VALUES (11, 1798180797601083394, '等待送货人员中', '正在生成', 'pending');
INSERT INTO `tb_logistics` VALUES (12, 1798307759059959810, '等待送货人员中', '正在生成', 'pending');
INSERT INTO `tb_logistics` VALUES (13, 1798309865904996353, '等待送货人员中', '正在生成', 'pending');
INSERT INTO `tb_logistics` VALUES (14, 1798355955647508482, '等待送货人员中', '正在生成', 'pending');
INSERT INTO `tb_logistics` VALUES (15, 1798639312271093762, '等待送货人员中', '正在生成', 'pending');
INSERT INTO `tb_logistics` VALUES (16, 1799051371294552066, '等待送货人员中', '正在生成', 'pending');
INSERT INTO `tb_logistics` VALUES (17, 1799056152841486337, '等待送货人员中', '正在生成', 'pending');
INSERT INTO `tb_logistics` VALUES (18, 1799056136475312130, '等待送货人员中', '正在生成', 'pending');
INSERT INTO `tb_logistics` VALUES (19, 1799056128124452866, '等待送货人员中', '正在生成', 'pending');
INSERT INTO `tb_logistics` VALUES (20, 1799056111783444482, '等待送货人员中', '正在生成', 'pending');
INSERT INTO `tb_logistics` VALUES (21, 1799056095366938626, '等待送货人员中', '正在生成', 'pending');
INSERT INTO `tb_logistics` VALUES (22, 1799058469405917186, '等待送货人员中', '正在生成', 'pending');
INSERT INTO `tb_logistics` VALUES (23, 1799061951392329730, '等待送货人员中', '正在生成', 'pending');

SET FOREIGN_KEY_CHECKS = 1;
