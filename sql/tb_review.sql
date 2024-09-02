/*
 Navicat Premium Data Transfer

 Source Server         : ttag
 Source Server Type    : MySQL
 Source Server Version : 80300
 Source Host           : 47.113.145.156:3306
 Source Schema         : ttag-review

 Target Server Type    : MySQL
 Target Server Version : 80300
 File Encoding         : 65001

 Date: 08/06/2024 13:17:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_review
-- ----------------------------
DROP TABLE IF EXISTS `tb_review`;
CREATE TABLE `tb_review`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NULL DEFAULT NULL,
  `product_id` bigint NULL DEFAULT NULL,
  `rating` int NULL DEFAULT NULL,
  `comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `order_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `orderId`(`order_id` ASC) USING BTREE,
  INDEX `product_id`(`product_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_review
-- ----------------------------
INSERT INTO `tb_review` VALUES (1, 1796912505421025282, 17, 4, '水果很甜，孩子很喜欢', '2024-06-04 10:44:53', 1797821164009598977);
INSERT INTO `tb_review` VALUES (2, 1796912505421025282, 17, 4, '水果很甜，孩子很喜欢1', '2024-06-04 10:48:22', 1797821971727691777);
INSERT INTO `tb_review` VALUES (3, 1796912505421025282, 17, 4, '水果很甜，孩子很喜欢2', '2024-06-04 10:51:00', 1797821977335476226);
INSERT INTO `tb_review` VALUES (4, 1796912505421025282, 17, 4, '水果很甜，孩子很喜欢3', '2024-06-04 10:51:49', 1797821981861130241);
INSERT INTO `tb_review` VALUES (5, 1796912505421025282, 17, 4, '水果很甜，孩子很喜欢4', '2024-06-04 10:52:21', 1797821985870884866);
INSERT INTO `tb_review` VALUES (6, 1796912505421025282, 17, 4, '水果很甜，孩子很喜欢5', '2024-06-04 10:52:52', 1797821990379761666);
INSERT INTO `tb_review` VALUES (7, 1796912505421025282, 17, 4, '水果很甜，孩子很喜欢6', '2024-06-04 10:53:16', 1797821994695700481);
INSERT INTO `tb_review` VALUES (8, 1796912505421025282, 17, 4, '水果很甜，孩子很喜欢6', '2024-06-04 10:53:34', 1797821998890004481);
INSERT INTO `tb_review` VALUES (9, 1796912505421025282, 17, 4, '水果很甜，孩子很喜欢6', '2024-06-04 10:54:05', 1797822003457601538);
INSERT INTO `tb_review` VALUES (10, 1795435487135166466, 17, 3, '好好吃！', '2024-06-06 16:28:50', 1798309865904996353);
INSERT INTO `tb_review` VALUES (17, 1795435487135166466, 17, 4, '水果很甜，孩子很喜欢6', '2024-06-06 16:57:23', 1798639312271093762);
INSERT INTO `tb_review` VALUES (25, 1795435487135166466, 17, 4, '好吃！', '2024-06-06 17:47:47', 1798355955647508482);
INSERT INTO `tb_review` VALUES (27, 1799049886463737858, 3, 4, '四星好评！！！', '2024-06-07 20:39:29', 1799056152841486337);
INSERT INTO `tb_review` VALUES (28, 1799049886463737858, 1, 5, '五星好评！', '2024-06-07 20:53:12', 1799056136475312130);

SET FOREIGN_KEY_CHECKS = 1;
