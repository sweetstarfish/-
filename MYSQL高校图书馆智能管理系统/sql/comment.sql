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

 Date: 21/05/2022 22:49:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `cid` int NOT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `uid` int NOT NULL,
  `bid` int NULL DEFAULT NULL,
  PRIMARY KEY (`cid`) USING BTREE,
  INDEX `uid`(`uid`) USING BTREE,
  INDEX `bid`(`bid`) USING BTREE,
  CONSTRAINT `bid` FOREIGN KEY (`bid`) REFERENCES `book` (`bid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `uid` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (12, '评价', 1, 624149362);
INSERT INTO `comment` VALUES (353646059, '这是一个tezxt', 1, 624149362);
INSERT INTO `comment` VALUES (1422707188, '这是t', 1, 624149362);
INSERT INTO `comment` VALUES (1504268848, '测试', 1, 624149362);

SET FOREIGN_KEY_CHECKS = 1;
