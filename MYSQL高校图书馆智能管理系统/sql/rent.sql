/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : code6

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 21/05/2022 22:49:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for rent
-- ----------------------------
DROP TABLE IF EXISTS `rent`;
CREATE TABLE `rent`  (
  `rid` int NOT NULL,
  `btime` datetime NULL DEFAULT NULL,
  `days` date NULL DEFAULT NULL,
  `uid` int NULL DEFAULT NULL,
  `bid` int NULL DEFAULT NULL,
  PRIMARY KEY (`rid`) USING BTREE,
  INDEX `ui`(`uid`) USING BTREE,
  INDEX `bi`(`bid`) USING BTREE,
  CONSTRAINT `bi` FOREIGN KEY (`bid`) REFERENCES `book` (`bid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ui` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rent
-- ----------------------------
INSERT INTO `rent` VALUES (80416542, '2022-05-17 13:32:16', '2005-01-12', 1, 624149362);
INSERT INTO `rent` VALUES (1370697766, '2022-05-17 13:37:08', '2005-01-12', 1, 624149362);

SET FOREIGN_KEY_CHECKS = 1;
