/*
 Navicat Premium Data Transfer

 Source Server         : ttag
 Source Server Type    : MySQL
 Source Server Version : 80300
 Source Host           : 47.113.145.156:3306
 Source Schema         : ttag-user

 Target Server Type    : MySQL
 Target Server Version : 80300
 File Encoding         : 65001

 Date: 08/06/2024 13:17:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `phone_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `full_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `role` enum('farmer','user','admin','request') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'user',
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/a232c817490c464698865e4116918760.jpg',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `request_time` datetime NULL DEFAULT NULL,
  `money` int UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE,
  UNIQUE INDEX `phone_number`(`phone_number` ASC) USING BTREE,
  UNIQUE INDEX `email`(`email` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1799063328110718979 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1795426703268610050, 'huayu', '$2a$10$Oe0xR7Gc0qZIyEfFBQmHquvoa/hY4G9ymzytpndZkijqMr0khcHwa', '$2a$10$KcRlhmnZzavQySH0tFFTcOimQAa4f7w7VkC6Y/DLG8BVd6xx85jPK', NULL, NULL, 'admin', 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/a232c817490c464698865e4116918760.jpg', NULL, NULL, 0);
INSERT INTO `tb_user` VALUES (1795427135718121474, 'huayulianai', '$2a$10$zVFQrwNPGrp2fiZ.qinjteWi7KXJGrjZ7iJoN3qDfUMD4gzHEixHO', '$2a$10$V9IKm6opIimKl2L0EvPxM.w0D7WXxTCGMlBQMf8NdbxMlbTbCyJaS', NULL, '吉林省离岛深水埗区', 'farmer', 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/00f344f3c5b747bca35d97ebe6b869d5.jpg', '2874602095@qq.com', '2024-05-28 20:38:29', 6152);
INSERT INTO `tb_user` VALUES (1795435487135166466, 'huayulianai2', '$2a$10$vPzL8s90lBdxv0uwhjw3MeCI70X36RAzTpFwH2EQUWUkxl7Tpp3Xm', '$2a$10$VLtxdwd71G6Uk9FBdDMJ5eOxtD./583sw.9Z75bQVKO7WvYNUAjle', NULL, '福州大学', 'farmer', 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/a320c93bb48c40018bcabb4a155a41a5.jpg', '114514@qq.com', '2024-06-03 09:25:32', 9997981);
INSERT INTO `tb_user` VALUES (1796912505421025282, 'huayulianai4', '$2a$10$T5jRAl5KiPGtZ.Tx2eO/3eh4Y7aTRqsPedehLe3rsGWal.OxLtyP6', '$2a$10$g6jUAsQEV0Nr2pF6orrgeekku770xo/1v0uohDQ9hUsodTof.Lz2C', NULL, '吉林省离岛深水埗区', 'user', 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/a232c817490c464698865e4116918760.jpg', NULL, NULL, 9996724);
INSERT INTO `tb_user` VALUES (1798945170585075714, '测试', '$2a$10$.J8gyuk0zkpqaq2J.4hceeGqfPNjhbfzDHEcUJ2czq2Be.FKC7V7m', '$2a$10$EVTbB9x.Vq9XvgX.0Lk8RO2yDMfA0ahdCm5glONcda0msQY09MciC', NULL, NULL, 'farmer', 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/2108b0eabd4d4c8fadc4da789278d1d7.jpg', '1919810@qq.com', '2024-06-07 13:09:11', 0);
INSERT INTO `tb_user` VALUES (1798946414916739073, 'huayulianai566', '$2a$10$eH62LfVJyN7g5bR9MKIsLeEtr/sSEKR/.q8Z6.g/86MotMOR0xcnm', '$2a$10$hQwXsZqF48R1RivQn83V8.00Lwa1rxJI3Be3weSzYZAg4XEE0pv6O', NULL, NULL, 'user', 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/a232c817490c464698865e4116918760.jpg', 'm.onwjwdkiw@qq.com', '2024-06-07 13:14:33', 0);
INSERT INTO `tb_user` VALUES (1799049886463737858, 'HeJi', '$2a$10$UPloI3MDm6.s1NmzCJwK6e3Ld9eUcgbz0xjkCjBDzrL.GRS6YozW6', '$2a$10$XZbW1fadKBG1nwtnWTXsoOLTj22eBVkuLqtVmmLa8X1lhBZgBUVDu', NULL, '福建省福州市', 'farmer', 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/abd83b403efe4ffc8107e26fd01e9118.jpg', 'HeJi@heji.com', '2024-06-07 20:54:00', 999143);
INSERT INTO `tb_user` VALUES (1799050989918011394, 'HeJii', '$2a$10$EFnO3L8XMYI/L67wM23RfOzPAav1ACdZPKxDRxl6c3ucf7P/4Avx6', '$2a$10$XRJ7vr0L2SqvDezR9q8sF.3FNcZ.ukSFXELQjqjHPH3mGQ9jWvGDW', NULL, NULL, 'user', 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/a232c817490c464698865e4116918760.jpg', NULL, NULL, 0);
INSERT INTO `tb_user` VALUES (1799051293724033026, 'HeJiii', '$2a$10$0bgIx44T2UsMBdgr7vmi0Om3G5OLmxkWIqpsX5Q38SZEaOfig1tDu', '$2a$10$Vdl9kIObKzg.bZgTMSPN7O0rDkn.KnnKXA0VzEI2423ic1N6vtQGy', NULL, NULL, 'user', 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/a232c817490c464698865e4116918760.jpg', NULL, NULL, 0);
INSERT INTO `tb_user` VALUES (1799058420463091713, 'HeJiiiii', '$2a$10$O9V8T2dHbKjty82EgNpTW.jlt5WvLQalT7dwsCHnvL3d/z2XJBfne', '$2a$10$VpAGMDQda5OrbvXn5TOZzuNV.yxXia1aNw2C4lMqdcSIx5dTSPxJW', NULL, NULL, 'user', 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/a232c817490c464698865e4116918760.jpg', NULL, NULL, 0);
INSERT INTO `tb_user` VALUES (1799061666444914689, 'HeJs', '$2a$10$p2OmR43WR354yOjGc3T7W.ro55fa/tQyZhPjoWBV9Y3HlAV1SzQEu', '$2a$10$lNF35j6FS1V1yRoBdm8m0ePYabIxJRrTvwLnxmADwMfgRpy5xVDcG', NULL, NULL, 'user', 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/a232c817490c464698865e4116918760.jpg', NULL, NULL, 0);
INSERT INTO `tb_user` VALUES (1799061915049701378, 'Heasd', '$2a$10$yuwVBMsPOpGNRuwLLz/3pelu237uBSlnGvoLUXrcdS0LAUO3f95Ia', '$2a$10$j3k28vpCCYQ.vZ0lSfXAhOvp.3/0X1ALYysYX6vPpi1OM81xZ5Ltq', NULL, NULL, 'user', 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/a232c817490c464698865e4116918760.jpg', NULL, NULL, 0);
INSERT INTO `tb_user` VALUES (1799063328110718978, 'HeJ1', '$2a$10$fxW8d79QZHVvvY0kOtuRQ..aC7QTYshBY0u7dGChJd9n0C5PFm7Sq', '$2a$10$RMQuPiJI7jyG0fzvdAz/deN83UuoDp/9m.uqoRnpAWnXdL/.5TdmC', NULL, NULL, 'user', 'https://huayu-cooperate.oss-cn-guangzhou.aliyuncs.com/a232c817490c464698865e4116918760.jpg', NULL, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
