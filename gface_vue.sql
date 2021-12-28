/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : gface_vue

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 22/12/2021 22:17:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for att_config
-- ----------------------------
DROP TABLE IF EXISTS `att_config`;
CREATE TABLE `att_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `work_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '班次名称',
  `to_work_time` time(0) NOT NULL COMMENT '上班时间',
  `off_work_time` time(0) NOT NULL COMMENT '下班时间',
  `before_work_time` int(2) NOT NULL DEFAULT 0 COMMENT '班前可打卡（分）',
  `after_work_time` int(2) NOT NULL DEFAULT 0 COMMENT '班后可打卡（分）',
  `allow_late` int(2) NOT NULL DEFAULT 0 COMMENT '下班后可打卡(分)',
  `allow_retreat` int(2) NOT NULL DEFAULT 0 COMMENT '下班前可打卡(分)',
  `device_group` int(11) NOT NULL COMMENT '设备组',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dev_access_verification_config
-- ----------------------------
DROP TABLE IF EXISTS `dev_access_verification_config`;
CREATE TABLE `dev_access_verification_config`  (
  `device_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备唯一码',
  `access_enable` int(2) NULL DEFAULT NULL COMMENT '是否开启服务器权限验证：\r\n1是开启，2是关闭\r\n',
  `timeout` bigint(20) NULL DEFAULT NULL COMMENT '通行权限验证接口请求超时时间，默认5000毫秒，单位毫秒',
  `timeout_handlerF_fag` int(2) NULL DEFAULT NULL COMMENT '请求超时后处理标记（默认2）：\r\n1是开继电器，2是不开继电器\r\n',
  `access_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器通行权限验证接口的完整url，如果返回不是有效url设备不会开启服务器权限验证流程',
  PRIMARY KEY (`device_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dev_config
-- ----------------------------
DROP TABLE IF EXISTS `dev_config`;
CREATE TABLE `dev_config`  (
  `device_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备唯一码',
  `read_id_card_enable` int(2) NULL DEFAULT NULL COMMENT '是否开启身份证读取：\r\n1是开启，2是关闭\r\n',
  `relay_interval` int(2) NULL DEFAULT NULL COMMENT '继电器开关间隔，单位毫秒',
  `relay_default_mode` int(2) NULL DEFAULT NULL COMMENT '继电器默认模式：\r\n1是默认常开，2是默认常闭\r\n',
  `recognition_threshold` int(2) NULL DEFAULT NULL COMMENT '识别阈值，1-99',
  `device_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备名称',
  `heartbeat_interval` bigint(20) NULL DEFAULT NULL COMMENT '心跳请求间隔，需要大于100，单位毫秒',
  `liveness_type` int(2) NULL DEFAULT NULL COMMENT '活体类型，-1不开启活体，1单目活体，2双目活体（设置2时如果设备不支持双目会改为单目活体）',
  `capture_mode_enable` int(2) NULL DEFAULT NULL COMMENT '抓拍模式 -1是不启用 1是启用',
  `detection_temperature_enable` int(2) NULL DEFAULT NULL COMMENT '检测体温 -1是不启用 1是启用',
  `temperature_unit` int(2) NULL DEFAULT NULL COMMENT '温度单位 1是摄氏度 2是华氏度',
  `alarm_temperature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '高温报警温度 默认37.3',
  `detection_occlusion_enable` int(2) NULL DEFAULT NULL COMMENT '检测口罩 -1是不启用 1是启用',
  `update_time` bigint(20) NULL DEFAULT NULL COMMENT '平台修改设备参数的时间，单位是毫秒时间戳',
  `server_time` bigint(20) NULL DEFAULT NULL COMMENT '(仅作保留不赋值，服务器时间为java: new Date())请求接口时服务器时间，单位是毫秒时间戳。如果是有效值会同步设备时间',
  PRIMARY KEY (`device_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dev_device_door
-- ----------------------------
DROP TABLE IF EXISTS `dev_device_door`;
CREATE TABLE `dev_device_door`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备id',
  `vendors` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备厂商',
  `device_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备名',
  `open_type` int(2) NULL DEFAULT NULL COMMENT '设备类型',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '位置',
  `create_time` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建时间',
  `status(no)` int(2) NULL DEFAULT NULL COMMENT '设备状态（弃用，现使用heartbea_time字段）',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `heartbeat_time` bigint(20) NULL DEFAULT 0 COMMENT '每一次心跳都会记录时间，长时间未更新时间，状态为异常',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名（设备归属）',
  `device_group` int(11) NULL DEFAULT NULL COMMENT '组',
  `device_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备请求时的IP，一般是局域网ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `u_device_id`(`device_code`) USING BTREE,
  INDEX `user_name`(`user_name`, `device_group`) USING BTREE,
  INDEX `device_code`(`device_code`) USING BTREE,
  INDEX `open_type`(`open_type`) USING BTREE,
  INDEX `location`(`location`) USING BTREE,
  INDEX `heartbeat_time`(`heartbeat_time`) USING BTREE,
  INDEX `device_group`(`device_group`) USING BTREE,
  INDEX `device_name`(`device_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dev_device_door
-- ----------------------------
INSERT INTO `dev_device_door` VALUES (1, 'AA', NULL, NULL, 0, NULL, 1639834729503, NULL, NULL, 0, 'admin', 1, NULL);
INSERT INTO `dev_device_door` VALUES (2, 'BB', NULL, NULL, 0, NULL, 1640008995441, NULL, NULL, 0, 'admin', 1, NULL);
INSERT INTO `dev_device_door` VALUES (3, 'CC', NULL, NULL, 0, NULL, 1640009030878, NULL, NULL, 0, 'admin', 1, NULL);

-- ----------------------------
-- Table structure for dev_device_vis
-- ----------------------------
DROP TABLE IF EXISTS `dev_device_vis`;
CREATE TABLE `dev_device_vis`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备id',
  `vendors` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备厂商',
  `device_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备名',
  `open_type` int(2) NULL DEFAULT NULL COMMENT '设备类型',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '位置',
  `create_time` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建时间',
  `status(no)` int(2) NULL DEFAULT NULL COMMENT '设备状态（弃用，现使用heartbea_time字段）',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `heartbeat_time` bigint(20) NULL DEFAULT 0 COMMENT '每一次心跳都会记录时间，长时间未更新时间，状态为异常',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名（设备归属）',
  `device_group` int(11) NULL DEFAULT NULL COMMENT '组',
  `device_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备请求时的IP，一般是局域网ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `u_device_id`(`device_code`) USING BTREE,
  INDEX `device_code`(`device_code`) USING BTREE,
  INDEX `open_type`(`open_type`) USING BTREE,
  INDEX `location`(`location`) USING BTREE,
  INDEX `heartbeat_time`(`heartbeat_time`) USING BTREE,
  INDEX `device_group`(`device_group`) USING BTREE,
  INDEX `device_name`(`device_name`) USING BTREE,
  INDEX `user_name`(`user_name`, `device_group`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dev_device_vis
-- ----------------------------
INSERT INTO `dev_device_vis` VALUES (1, 'BB', NULL, NULL, 0, NULL, 1639831011053, NULL, NULL, 0, 'admin', 1, NULL);

-- ----------------------------
-- Table structure for dev_group
-- ----------------------------
DROP TABLE IF EXISTS `dev_group`;
CREATE TABLE `dev_group`  (
  `g_id` int(11) NOT NULL AUTO_INCREMENT,
  `g_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组名称',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关联账号',
  PRIMARY KEY (`g_id`) USING BTREE,
  UNIQUE INDEX `g_id`(`g_id`, `g_name`, `user_name`) USING BTREE,
  UNIQUE INDEX `g_name_2`(`g_name`, `user_name`) USING BTREE,
  INDEX `g_name`(`g_name`) USING BTREE,
  INDEX `user_name`(`user_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dev_group
-- ----------------------------
INSERT INTO `dev_group` VALUES (1, 'AA', 'admin');

-- ----------------------------
-- Table structure for dev_group_vis
-- ----------------------------
DROP TABLE IF EXISTS `dev_group_vis`;
CREATE TABLE `dev_group_vis`  (
  `g_id` int(11) NOT NULL AUTO_INCREMENT,
  `g_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组名称',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关联账号',
  PRIMARY KEY (`g_id`) USING BTREE,
  UNIQUE INDEX `g_id`(`g_id`, `g_name`, `user_name`) USING BTREE,
  INDEX `g_name`(`g_name`) USING BTREE,
  INDEX `user_name`(`user_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dev_group_vis
-- ----------------------------
INSERT INTO `dev_group_vis` VALUES (1, 'BB', 'admin');

-- ----------------------------
-- Table structure for dev_heartbeat_status
-- ----------------------------
DROP TABLE IF EXISTS `dev_heartbeat_status`;
CREATE TABLE `dev_heartbeat_status`  (
  `device_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `last_user_update_time` bigint(20) NULL DEFAULT 0,
  `last_device_config_update_time` bigint(20) NULL DEFAULT 0,
  `state` int(2) NULL DEFAULT 0,
  `apk_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`device_code`) USING BTREE,
  INDEX `last_user_update_time`(`last_user_update_time`) USING BTREE,
  INDEX `last_device_config_update_time`(`last_device_config_update_time`) USING BTREE,
  INDEX `state`(`state`) USING BTREE,
  INDEX `apk_url`(`apk_url`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dev_heartbeat_status
-- ----------------------------
INSERT INTO `dev_heartbeat_status` VALUES ('AA', 0, 0, 0, NULL);
INSERT INTO `dev_heartbeat_status` VALUES ('BB', 0, 0, 0, NULL);
INSERT INTO `dev_heartbeat_status` VALUES ('CC', 0, 0, 0, NULL);

-- ----------------------------
-- Table structure for dev_record_blacklist
-- ----------------------------
DROP TABLE IF EXISTS `dev_record_blacklist`;
CREATE TABLE `dev_record_blacklist`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `temperature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体温',
  `record_time` bigint(20) NULL DEFAULT NULL COMMENT '记录时间',
  `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '人脸照片',
  `confidence` int(2) NULL DEFAULT NULL COMMENT '比对的阈值',
  `person_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id（只有固定授权的有，暂时对应记录类型recordType的1和4）',
  `record_type` int(2) NULL DEFAULT NULL COMMENT '记录类型\r\n 1：人脸验证\r\n 2：人脸抓拍\r\n 3：刷身份证\r\n 4：刷ic卡\r\n 5：人证合一\r\n 8：国康码\r\n',
  `occlusion` int(2) NULL DEFAULT NULL COMMENT '是否佩戴口罩，未启用返回-1，未佩戴口罩返回0，佩戴返回1',
  `ic_card` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ic卡号',
  `person_type` int(2) NULL DEFAULT NULL COMMENT '用户类型',
  `device_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备ID',
  `qr_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'qrcode',
  `hsjl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '核酸记录',
  `ymjl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '疫苗记录',
  `xcjl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行程记录',
  `guokang_card_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `guokang_status` int(2) NULL DEFAULT NULL,
  `guokang_card_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `guokang_over_city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `guokang_code` int(2) NULL DEFAULT NULL,
  `id_card_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `temperature`(`temperature`) USING BTREE,
  INDEX `record_time`(`record_time`) USING BTREE,
  INDEX `pic_url`(`pic_url`) USING BTREE,
  INDEX `confidence`(`confidence`) USING BTREE,
  INDEX `person_id`(`person_id`) USING BTREE,
  INDEX `record_type`(`record_type`) USING BTREE,
  INDEX `occlusion`(`occlusion`) USING BTREE,
  INDEX `ic_card`(`ic_card`) USING BTREE,
  INDEX `person_type`(`person_type`) USING BTREE,
  INDEX `device_code`(`device_code`) USING BTREE,
  INDEX `qr_code`(`qr_code`) USING BTREE,
  INDEX `hsjl`(`hsjl`) USING BTREE,
  INDEX `guokang_card_id`(`guokang_card_id`) USING BTREE,
  INDEX `guokang_status`(`guokang_status`) USING BTREE,
  INDEX `guokang_card_name`(`guokang_card_name`) USING BTREE,
  INDEX `guokang_over_city`(`guokang_over_city`) USING BTREE,
  INDEX `guokang_code`(`guokang_code`) USING BTREE,
  INDEX `id_card_no`(`id_card_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dev_record_blacklist
-- ----------------------------
INSERT INTO `dev_record_blacklist` VALUES (4, '测试', '36.5', 1640008844192, '/device_img/face_img/2021-12-20/1640008959251@BB&sBBan9ND.jpeg', 99, 'ewqeqw', 1, 1, '123456', 2, 'BB', NULL, NULL, NULL, NULL, '**************8689', 1, '张*', '', 0, 'XXXXX');
INSERT INTO `dev_record_blacklist` VALUES (5, '测试', '37.5', 1640008844192, '/device_img/face_img/2021-12-20/1640009056310@BB&P9FTR6yO.jpeg', 99, 'ewqeqw', 1, 1, '123456', 2, 'BB', NULL, NULL, NULL, NULL, '**************8689', 1, '张*', '', 0, 'XXXXX');
INSERT INTO `dev_record_blacklist` VALUES (6, '测试', '37.5', 1640008844192, '/device_img/face_img/2021-12-20/1640009066982@BB&2pV11YqL.jpeg', 99, 'ewqeqw', 1, 1, '123456', 2, 'BB', NULL, NULL, NULL, NULL, '**************8689', 1, '张*', '', 0, 'XXXXX');
INSERT INTO `dev_record_blacklist` VALUES (7, '测试', '37.5', 1640008844192, '/device_img/face_img/2021-12-20/1640009152567@BB&1ZXgfgH1.jpeg', 99, 'ewqeqw', 1, 1, '123456', 2, 'BB', NULL, NULL, NULL, NULL, '**************8689', 1, '张*', '', 0, 'XXXXX');
INSERT INTO `dev_record_blacklist` VALUES (8, '测试', '36.5', 1640008844192, '/device_img/face_img/2021-12-20/1640009175012@BB&BDSviYA9.jpeg', 99, 'ewqeqw', 1, 1, '123456', 2, 'BB', NULL, NULL, NULL, NULL, '**************8689', 2, '张*', '', 0, 'XXXXX');
INSERT INTO `dev_record_blacklist` VALUES (9, '测试', '36.5', 1640008844192, '/device_img/face_img/2021-12-20/1640009181574@BB&izl56Gee.jpeg', 99, 'ewqeqw', 1, 1, '123456', 2, 'BB', NULL, NULL, NULL, NULL, '**************8689', 3, '张*', '', 0, 'XXXXX');
INSERT INTO `dev_record_blacklist` VALUES (10, '测试', '36.5', 1640008844192, '/device_img/face_img/2021-12-20/1640009189945@BB&tYVP83DO.jpeg', 99, 'ewqeqw', 1, 1, '123456', 2, 'BB', NULL, NULL, NULL, NULL, '**************8689', 1, '张*', '', 0, 'XXXXX');

-- ----------------------------
-- Table structure for dev_record_door
-- ----------------------------
DROP TABLE IF EXISTS `dev_record_door`;
CREATE TABLE `dev_record_door`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `temperature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体温',
  `record_time` bigint(20) NULL DEFAULT NULL COMMENT '记录时间',
  `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '人脸照片',
  `confidence` int(2) NULL DEFAULT NULL COMMENT '比对的阈值',
  `person_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id（只有固定授权的有，暂时对应记录类型recordType的1和4）',
  `record_type` int(2) NULL DEFAULT NULL COMMENT '记录类型\r\n 1：人脸验证\r\n 2：人脸抓拍\r\n 3：刷身份证\r\n 4：刷ic卡\r\n 5：人证合一\r\n 8：国康码\r\n',
  `occlusion` int(2) NULL DEFAULT NULL COMMENT '是否佩戴口罩，未启用返回-1，未佩戴口罩返回0，佩戴返回1',
  `ic_card` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ic卡号',
  `person_type` int(2) NULL DEFAULT NULL COMMENT '用户类型',
  `device_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备ID',
  `qr_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'qrcode',
  `hsjl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '核酸记录',
  `ymjl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '疫苗记录',
  `xcjl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行程记录',
  `guokang_card_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `guokang_status` int(2) NULL DEFAULT NULL,
  `guokang_card_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `guokang_over_city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `guokang_code` int(2) NULL DEFAULT NULL,
  `id_card_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `temperature`(`temperature`) USING BTREE,
  INDEX `record_time`(`record_time`) USING BTREE,
  INDEX `pic_url`(`pic_url`) USING BTREE,
  INDEX `confidence`(`confidence`) USING BTREE,
  INDEX `person_id`(`person_id`) USING BTREE,
  INDEX `record_type`(`record_type`) USING BTREE,
  INDEX `occlusion`(`occlusion`) USING BTREE,
  INDEX `ic_card`(`ic_card`) USING BTREE,
  INDEX `person_type`(`person_type`) USING BTREE,
  INDEX `device_code`(`device_code`) USING BTREE,
  INDEX `qr_code`(`qr_code`) USING BTREE,
  INDEX `hsjl`(`hsjl`) USING BTREE,
  INDEX `guokang_card_id`(`guokang_card_id`) USING BTREE,
  INDEX `guokang_status`(`guokang_status`) USING BTREE,
  INDEX `guokang_card_name`(`guokang_card_name`) USING BTREE,
  INDEX `guokang_over_city`(`guokang_over_city`) USING BTREE,
  INDEX `guokang_code`(`guokang_code`) USING BTREE,
  INDEX `id_card_no`(`id_card_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dev_record_door
-- ----------------------------
INSERT INTO `dev_record_door` VALUES (17, '测试', '36.5', 1640008844192, '/device_img/face_img/2021-12-20/1640008949989@BB&qSlGolni.jpeg', 99, '312', 1, 1, '123456', 0, 'BB', NULL, NULL, NULL, NULL, '**************8689', 1, '张*', '', 0, 'XXXXX');
INSERT INTO `dev_record_door` VALUES (18, '测试', '36.5', 1640008844192, '/device_img/face_img/2021-12-20/1640008953321@BB&UiSQbB2z.jpeg', 99, 'ewqeqw', 1, 1, '123456', 0, 'BB', NULL, NULL, NULL, NULL, '**************8689', 1, '张*', '', 0, 'XXXXX');
INSERT INTO `dev_record_door` VALUES (19, '测试', '36.5', 1640008844192, '/device_img/face_img/2021-12-20/1640008956739@BB&Ey2OXAap.jpeg', 99, 'ewqeqw', 1, 1, '123456', 1, 'BB', NULL, NULL, NULL, NULL, '**************8689', 1, '张*', '', 0, 'XXXXX');

-- ----------------------------
-- Table structure for dev_record_idcard_info
-- ----------------------------
DROP TABLE IF EXISTS `dev_record_idcard_info`;
CREATE TABLE `dev_record_idcard_info`  (
  `id_card_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份证',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `birth` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '出生年月日',
  `nation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '民族',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国家',
  `validity_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '有效期限',
  `id_card_pic_base64` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证照片',
  `issuing_authority` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '签发机构',
  PRIMARY KEY (`id_card_no`) USING BTREE,
  UNIQUE INDEX `id_card_no`(`id_card_no`, `name`) USING BTREE,
  INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dev_record_idcard_info
-- ----------------------------
INSERT INTO `dev_record_idcard_info` VALUES ('XXXXX', '男', '张三', '1988年08月08日', '汉', 'XX省XX', '中国', '2008.01.01-2028.01.01', NULL, 'XX县公安局');

-- ----------------------------
-- Table structure for dev_record_stranger
-- ----------------------------
DROP TABLE IF EXISTS `dev_record_stranger`;
CREATE TABLE `dev_record_stranger`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `temperature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体温',
  `record_time` bigint(20) NULL DEFAULT NULL COMMENT '记录时间',
  `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '人脸照片',
  `confidence` int(2) NULL DEFAULT NULL COMMENT '比对的阈值',
  `person_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id（只有固定授权的有，暂时对应记录类型recordType的1和4）',
  `record_type` int(2) NULL DEFAULT NULL COMMENT '记录类型\r\n 1：人脸验证\r\n 2：人脸抓拍\r\n 3：刷身份证\r\n 4：刷ic卡\r\n 5：人证合一\r\n 8：国康码\r\n',
  `occlusion` int(2) NULL DEFAULT NULL COMMENT '是否佩戴口罩，未启用返回-1，未佩戴口罩返回0，佩戴返回1',
  `ic_card` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ic卡号',
  `person_type` int(2) NULL DEFAULT NULL COMMENT '用户类型',
  `device_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备ID',
  `qr_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'qrcode',
  `hsjl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '核酸记录',
  `ymjl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '疫苗记录',
  `xcjl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行程记录',
  `guokang_card_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `guokang_status` int(2) NULL DEFAULT NULL,
  `guokang_card_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `guokang_over_city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `guokang_code` int(2) NULL DEFAULT NULL,
  `id_card_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `temperature`(`temperature`) USING BTREE,
  INDEX `record_time`(`record_time`) USING BTREE,
  INDEX `pic_url`(`pic_url`) USING BTREE,
  INDEX `confidence`(`confidence`) USING BTREE,
  INDEX `person_id`(`person_id`) USING BTREE,
  INDEX `record_type`(`record_type`) USING BTREE,
  INDEX `occlusion`(`occlusion`) USING BTREE,
  INDEX `ic_card`(`ic_card`) USING BTREE,
  INDEX `person_type`(`person_type`) USING BTREE,
  INDEX `device_code`(`device_code`) USING BTREE,
  INDEX `qr_code`(`qr_code`) USING BTREE,
  INDEX `hsjl`(`hsjl`) USING BTREE,
  INDEX `guokang_card_id`(`guokang_card_id`) USING BTREE,
  INDEX `guokang_status`(`guokang_status`) USING BTREE,
  INDEX `guokang_card_name`(`guokang_card_name`) USING BTREE,
  INDEX `guokang_over_city`(`guokang_over_city`) USING BTREE,
  INDEX `guokang_code`(`guokang_code`) USING BTREE,
  INDEX `id_card_no`(`id_card_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dev_record_stranger
-- ----------------------------
INSERT INTO `dev_record_stranger` VALUES (22, '测试', '36.5', 1640008844192, '/device_img/face_img/2021-12-20/1640008922342@BB&9zfuXMBJ.jpeg', 99, '', 1, 1, '123456', 0, 'BB', NULL, NULL, NULL, NULL, '**************8689', 1, '张*', '', 0, 'XXXXX');
INSERT INTO `dev_record_stranger` VALUES (23, '测试', '36.5', 1640008844192, '/device_img/face_img/2021-12-20/1640008938412@BB&QGgLiQem.jpeg', 99, '', 1, 1, '123456', 0, 'BB', NULL, NULL, NULL, NULL, '**************8689', 1, '张*', '', 0, 'XXXXX');
INSERT INTO `dev_record_stranger` VALUES (24, '测试', '36.5', 1640008844192, '/device_img/face_img/2021-12-20/1640008938968@BB&XJV4DFFQ.jpeg', 99, '', 1, 1, '123456', 0, 'BB', NULL, NULL, NULL, NULL, '**************8689', 1, '张*', '', 0, 'XXXXX');

-- ----------------------------
-- Table structure for dev_record_vis
-- ----------------------------
DROP TABLE IF EXISTS `dev_record_vis`;
CREATE TABLE `dev_record_vis`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `temperature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体温',
  `record_time` bigint(20) NULL DEFAULT NULL COMMENT '记录时间',
  `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '人脸照片',
  `confidence` int(2) NULL DEFAULT NULL COMMENT '比对的阈值',
  `person_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id（只有固定授权的有，暂时对应记录类型recordType的1和4）',
  `record_type` int(2) NULL DEFAULT NULL COMMENT '记录类型\r\n 1：人脸验证\r\n 2：人脸抓拍\r\n 3：刷身份证\r\n 4：刷ic卡\r\n 5：人证合一\r\n 8：国康码\r\n',
  `occlusion` int(2) NULL DEFAULT NULL COMMENT '是否佩戴口罩，未启用返回-1，未佩戴口罩返回0，佩戴返回1',
  `ic_card` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ic卡号',
  `person_type` int(2) NULL DEFAULT NULL COMMENT '用户类型',
  `device_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备ID',
  `qr_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'qrcode',
  `hsjl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '核酸记录',
  `ymjl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '疫苗记录',
  `xcjl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行程记录',
  `guokang_card_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `guokang_status` int(2) NULL DEFAULT NULL,
  `guokang_card_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `guokang_over_city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `guokang_code` int(2) NULL DEFAULT NULL,
  `id_card_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `temperature`(`temperature`) USING BTREE,
  INDEX `record_time`(`record_time`) USING BTREE,
  INDEX `pic_url`(`pic_url`) USING BTREE,
  INDEX `confidence`(`confidence`) USING BTREE,
  INDEX `person_id`(`person_id`) USING BTREE,
  INDEX `record_type`(`record_type`) USING BTREE,
  INDEX `occlusion`(`occlusion`) USING BTREE,
  INDEX `ic_card`(`ic_card`) USING BTREE,
  INDEX `person_type`(`person_type`) USING BTREE,
  INDEX `device_code`(`device_code`) USING BTREE,
  INDEX `qr_code`(`qr_code`) USING BTREE,
  INDEX `hsjl`(`hsjl`) USING BTREE,
  INDEX `guokang_card_id`(`guokang_card_id`) USING BTREE,
  INDEX `guokang_status`(`guokang_status`) USING BTREE,
  INDEX `guokang_card_name`(`guokang_card_name`) USING BTREE,
  INDEX `guokang_over_city`(`guokang_over_city`) USING BTREE,
  INDEX `guokang_code`(`guokang_code`) USING BTREE,
  INDEX `id_card_no`(`id_card_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dev_rsa_config
-- ----------------------------
DROP TABLE IF EXISTS `dev_rsa_config`;
CREATE TABLE `dev_rsa_config`  (
  `device_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备唯一码',
  `server_publicKey` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器公钥',
  `rsa_flag` int(2) NULL DEFAULT NULL COMMENT '是否需要开启RSA加密，1为开启， 其他不开启。如果开启后，4.1-4.7接口都会启用RSA加密，设备端请求使用服务器公钥加密，解析使用设备私钥解密',
  PRIMARY KEY (`device_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for g_user
-- ----------------------------
DROP TABLE IF EXISTS `g_user`;
CREATE TABLE `g_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `phone_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `profile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简介',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `pid` int(11) NOT NULL COMMENT '账号都关联创建者ID',
  `count` int(11) NULL DEFAULT NULL COMMENT '允许创建的子账号数',
  `server_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器url：http://ip:port/face/api/v1',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `u_user_name`(`user_name`) USING BTREE,
  INDEX `user_name`(`user_name`, `role`, `pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of g_user
-- ----------------------------
INSERT INTO `g_user` VALUES (1, 'admin', '21232F297A57A5A743894A0E4A801FC3', 'admin', 'Admin', NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL);

-- ----------------------------
-- Table structure for per_blacklist
-- ----------------------------
DROP TABLE IF EXISTS `per_blacklist`;
CREATE TABLE `per_blacklist`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `person_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '人员ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工姓名',
  `mobile_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机',
  `ic_card` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门禁卡号',
  `sex` int(2) NULL DEFAULT NULL COMMENT '性别',
  `valid_date_start` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `valid_date_end` bigint(20) NULL DEFAULT NULL COMMENT '过期时间',
  `face_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '员工照片',
  `face_base64` blob NULL COMMENT '人脸Base64',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `device_group` int(11) NULL DEFAULT NULL COMMENT '设备组',
  `operation_type` int(2) NULL DEFAULT 1 COMMENT '操作类型：\r\n1：新增或者更新\r\n2：删除\r\n',
  `update_time` bigint(20) NULL DEFAULT NULL COMMENT '人员修改时间，单位是毫秒时间戳',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '绑定用户',
  `id_card_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `u_person_id`(`person_id`) USING BTREE,
  INDEX `person_id`(`person_id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `mobile_phone`(`mobile_phone`) USING BTREE,
  INDEX `ic_card`(`ic_card`) USING BTREE,
  INDEX `sex`(`sex`) USING BTREE,
  INDEX `valid_date_start`(`valid_date_start`) USING BTREE,
  INDEX `valid_date_end`(`valid_date_end`) USING BTREE,
  INDEX `face_url`(`face_url`) USING BTREE,
  INDEX `remark`(`remark`) USING BTREE,
  INDEX `device_group`(`device_group`) USING BTREE,
  INDEX `operation_type`(`operation_type`) USING BTREE,
  INDEX `update_time`(`update_time`) USING BTREE,
  INDEX `user_name`(`user_name`) USING BTREE,
  INDEX `id_card_no`(`id_card_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for per_employee
-- ----------------------------
DROP TABLE IF EXISTS `per_employee`;
CREATE TABLE `per_employee`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `person_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '人员ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工姓名',
  `mobile_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机',
  `ic_card` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门禁卡号',
  `sex` int(2) NULL DEFAULT NULL COMMENT '性别',
  `valid_date_start` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `valid_date_end` bigint(20) NULL DEFAULT NULL COMMENT '过期时间',
  `face_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '员工照片',
  `face_base64` blob NULL COMMENT '人脸Base64',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `device_group` int(11) NULL DEFAULT NULL COMMENT '设备组',
  `operation_type` int(2) NULL DEFAULT 1 COMMENT '操作类型：\r\n1：新增或者更新\r\n2：删除\r\n',
  `update_time` bigint(20) NULL DEFAULT NULL COMMENT '人员修改时间，单位是毫秒时间戳',
  `job_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工号',
  `open_door_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开门密码',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '绑定用户',
  `id_card_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `u_person_id`(`person_id`) USING BTREE,
  INDEX `person_id`(`person_id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `mobile_phone`(`mobile_phone`) USING BTREE,
  INDEX `ic_card`(`ic_card`) USING BTREE,
  INDEX `sex`(`sex`) USING BTREE,
  INDEX `valid_date_start`(`valid_date_start`) USING BTREE,
  INDEX `valid_date_end`(`valid_date_end`) USING BTREE,
  INDEX `face_url`(`face_url`) USING BTREE,
  INDEX `remark`(`remark`) USING BTREE,
  INDEX `device_group`(`device_group`) USING BTREE,
  INDEX `operation_type`(`operation_type`) USING BTREE,
  INDEX `update_time`(`update_time`) USING BTREE,
  INDEX `user_name`(`user_name`) USING BTREE,
  INDEX `job_number`(`job_number`) USING BTREE,
  INDEX `open_door_password`(`open_door_password`) USING BTREE,
  INDEX `id_card_no`(`id_card_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of per_employee
-- ----------------------------
INSERT INTO `per_employee` VALUES (1, '1c2a82599f9166db6aa5a67992679a2c', 'ewqe', NULL, NULL, 0, 1639834609413, NULL, '/web_img/face_img/2021-12-18/1639834605088@1&e2DmZba4.jpeg', NULL, NULL, 1, 1, 1639834609413, NULL, NULL, 'admin', NULL);

-- ----------------------------
-- Table structure for per_visitor
-- ----------------------------
DROP TABLE IF EXISTS `per_visitor`;
CREATE TABLE `per_visitor`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `person_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '人员ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工姓名',
  `mobile_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机',
  `ic_card` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门禁卡号',
  `sex` int(2) NULL DEFAULT NULL COMMENT '性别',
  `valid_date_start` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `valid_date_end` bigint(20) NULL DEFAULT NULL COMMENT '过期时间',
  `face_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '员工照片',
  `face_base64` blob NULL COMMENT '人脸Base64',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `device_group` int(11) NULL DEFAULT NULL COMMENT '设备组',
  `operation_type` int(2) NULL DEFAULT 1 COMMENT '操作类型：\r\n1：新增或者更新\r\n2：删除\r\n',
  `update_time` bigint(20) NULL DEFAULT NULL COMMENT '人员修改时间，单位是毫秒时间戳',
  `job_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工号',
  `open_door_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开门密码',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '绑定用户',
  `id_card_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `u_person_id`(`person_id`) USING BTREE,
  INDEX `person_id`(`person_id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `mobile_phone`(`mobile_phone`) USING BTREE,
  INDEX `ic_card`(`ic_card`) USING BTREE,
  INDEX `sex`(`sex`) USING BTREE,
  INDEX `valid_date_start`(`valid_date_start`) USING BTREE,
  INDEX `valid_date_end`(`valid_date_end`) USING BTREE,
  INDEX `face_url`(`face_url`) USING BTREE,
  INDEX `remark`(`remark`) USING BTREE,
  INDEX `device_group`(`device_group`) USING BTREE,
  INDEX `operation_type`(`operation_type`) USING BTREE,
  INDEX `update_time`(`update_time`) USING BTREE,
  INDEX `user_name`(`user_name`) USING BTREE,
  INDEX `job_number`(`job_number`) USING BTREE,
  INDEX `open_door_password`(`open_door_password`) USING BTREE,
  INDEX `id_card_no`(`id_card_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for web_record_dashboard_early_warn
-- ----------------------------
DROP TABLE IF EXISTS `web_record_dashboard_early_warn`;
CREATE TABLE `web_record_dashboard_early_warn`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `temperature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体温',
  `record_time` bigint(20) NULL DEFAULT NULL COMMENT '记录时间',
  `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '人脸照片',
  `confidence` int(2) NULL DEFAULT NULL COMMENT '比对的阈值',
  `person_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id（只有固定授权的有，暂时对应记录类型recordType的1和4）',
  `record_type` int(2) NULL DEFAULT NULL COMMENT '记录类型\r\n 1：人脸验证\r\n 2：人脸抓拍\r\n 3：刷身份证\r\n 4：刷ic卡\r\n 5：人证合一\r\n 8：国康码\r\n',
  `occlusion` int(2) NULL DEFAULT NULL COMMENT '是否佩戴口罩，未启用返回-1，未佩戴口罩返回0，佩戴返回1',
  `ic_card` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ic卡号',
  `person_type` int(2) NULL DEFAULT NULL COMMENT '用户类型',
  `device_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备ID',
  `qr_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'qrcode',
  `hsjl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '核酸记录',
  `ymjl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '疫苗记录',
  `xcjl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行程记录',
  `guokang_card_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `guokang_status` int(2) NULL DEFAULT NULL,
  `guokang_card_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `guokang_over_city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `guokang_code` int(2) NULL DEFAULT NULL,
  `id_card_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号码',
  `read_msg` tinyint(1) NOT NULL DEFAULT 0 COMMENT '前端是否已读该消息（0未读默认，1已读）',
  `degree` int(2) NOT NULL DEFAULT -1 COMMENT '严重程度，1红色预警，2黄色预警，-1一般预警',
  `new_msg` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否新消息（1是，2否）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE,
  INDEX `temperature`(`temperature`) USING BTREE,
  INDEX `record_time`(`record_time`) USING BTREE,
  INDEX `pic_url`(`pic_url`) USING BTREE,
  INDEX `confidence`(`confidence`) USING BTREE,
  INDEX `person_id`(`person_id`) USING BTREE,
  INDEX `record_type`(`record_type`) USING BTREE,
  INDEX `occlusion`(`occlusion`) USING BTREE,
  INDEX `ic_card`(`ic_card`) USING BTREE,
  INDEX `person_type`(`person_type`) USING BTREE,
  INDEX `device_code`(`device_code`) USING BTREE,
  INDEX `qr_code`(`qr_code`) USING BTREE,
  INDEX `hsjl`(`hsjl`) USING BTREE,
  INDEX `guokang_card_id`(`guokang_card_id`) USING BTREE,
  INDEX `guokang_status`(`guokang_status`) USING BTREE,
  INDEX `guokang_card_name`(`guokang_card_name`) USING BTREE,
  INDEX `guokang_over_city`(`guokang_over_city`) USING BTREE,
  INDEX `guokang_code`(`guokang_code`) USING BTREE,
  INDEX `id_card_no`(`id_card_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of web_record_dashboard_early_warn
-- ----------------------------
INSERT INTO `web_record_dashboard_early_warn` VALUES (15, '测试', '37.5', 1640008844192, '/web_img/face_img/2021-12-18/1639834605088@1&e2DmZba4.jpeg', 99, 'ewqeqw', 1, 1, '123456', 2, 'BB', NULL, NULL, NULL, NULL, '**************8689', 1, '张*', '', 0, 'XXXXX', 0, -1, 1);
INSERT INTO `web_record_dashboard_early_warn` VALUES (16, '测试', '37.5', 1640008844192, '/web_img/face_img/2021-12-18/1639834605088@1&e2DmZba4.jpeg', 99, 'ewqeqw', 1, 1, '123456', 2, 'BB', NULL, NULL, NULL, NULL, '**************8689', 1, '张*', '', 0, 'XXXXX', 0, 1, 1);
INSERT INTO `web_record_dashboard_early_warn` VALUES (17, '测试', '36.5', 1640008844192, '/web_img/face_img/2021-12-18/1639834605088@1&e2DmZba4.jpeg', 99, 'ewqeqw', 1, 1, '123456', 2, 'BB', NULL, NULL, NULL, NULL, '**************8689', 2, '张*', '', 0, 'XXXXX', 0, 1, 1);
INSERT INTO `web_record_dashboard_early_warn` VALUES (18, '测试', '36.5', 1640008844192, '/web_img/face_img/2021-12-18/1639834605088@1&e2DmZba4.jpeg', 99, 'ewqeqw', 1, 1, '123456', 2, 'BB', NULL, NULL, NULL, NULL, '**************8689', 3, '张*', '', 0, 'XXXXX', 0, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
