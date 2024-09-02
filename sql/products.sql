/*
 Navicat Premium Data Transfer

 Source Server         : ttag
 Source Server Type    : MySQL
 Source Server Version : 80300
 Source Host           : 47.113.145.156:3306
 Source Schema         : ttag-product

 Target Server Type    : MySQL
 Target Server Version : 80300
 File Encoding         : 65001

 Date: 08/06/2024 13:17:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `price` int NOT NULL,
  `stock` int NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `farmer_id` bigint NOT NULL,
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ship_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `service` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `score` double NOT NULL,
  `status` enum('pending','accept','reject') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'pending',
  `version` int NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `farmer_id`(`farmer_id` ASC) USING BTREE,
  FULLTEXT INDEX `name`(`name`),
  CONSTRAINT `stock_check` CHECK (`stock` >= 0)
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of products
-- ----------------------------
INSERT INTO `products` VALUES (1, '共带利都', 26, 42, '何始体以则他单通个资主件实采空。持便子而好西要厂之文记主美市联东准。非安组即果真重理么别信劳场种。条例容边算比见术引走作他积标治事。', 1795427135718121474, 'http://dummyimage.com/400x400', '内蒙古自治区来宾市长滨乡', 'anim consectetur nostrud in', 5, 'accept', 1);
INSERT INTO `products` VALUES (2, '变历状示场在', 61, 18, '白大非美何示平而它感集飞周东已。几育军便五引响方国个至程是。多方节将光集光省流种量放整干处适且。效间以出土少价千今次复圆务。权要发县其离再分车可二里写四热。', 1795427135718121474, 'http://dummyimage.com/400x400', '安徽省南昌市隆尧县', 'exercitation aute', 0, 'accept', 1);
INSERT INTO `products` VALUES (3, '做性性素离再走', 54, 33, '层特清拉准机斗单规人音划增低即日。看象处经议快开如联天四本却场正已。示发处名物风使阶江被自器变装内力物。务十象里机消已根专里例如切着全。则法带变增区往次月料求公细以他米机。', 1795427135718121474, 'http://dummyimage.com/400x400', '广东省株洲市桥西区', 'sint Duis', 4, 'accept', 1);
INSERT INTO `products` VALUES (21, 'Apple', 9, 10, '好吃！便宜！实惠！', 1795427135718121474, 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/3400fb7ed74b4ce18203171d69e2a642.jpg', '缅甸', '9.9包邮', 0, 'reject', 1);
INSERT INTO `products` VALUES (22, 'Apple', 9, 24, '好吃实惠', 1795427135718121474, 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/96367da700c34709a33c4435a90896a9.jpg', '缅甸', '9.9包邮', 0, 'accept', 1);
INSERT INTO `products` VALUES (24, 'asd', 12, 123, 'asd', 1795427135718121474, 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/4ea58ee327a84179b68df6146a87c397.jpg', 'asd', 'asd', 0, 'reject', 1);
INSERT INTO `products` VALUES (29, '榴莲', 9, 100, '好吃', 1799049886463737858, 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/78238689ef1a45078dadf9b209eed4a1.jpg', '福建省福州市', '包邮', 0, 'pending', 1);

SET FOREIGN_KEY_CHECKS = 1;
