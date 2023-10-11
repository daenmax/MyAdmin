
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


-- ----------------------------
-- Table structure for sys_api_limit
-- ----------------------------
DROP TABLE IF EXISTS `sys_api_limit`;
CREATE TABLE `sys_api_limit`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `api_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口名称',
  `api_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口uri',
  `single_frequency` int NULL DEFAULT NULL COMMENT '单个用户次数',
  `single_time` int NULL DEFAULT NULL COMMENT '单个用户时间',
  `single_time_unit` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单个用户时间单位',
  `whole_frequency` int NULL DEFAULT NULL COMMENT '全部用户次数',
  `whole_time` int NULL DEFAULT NULL COMMENT '全部用户时间',
  `whole_time_unit` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '全部用户时间单位',
  `limit_type` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '限制类型，0=限流，1=停用',
  `ret_msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '停用提示，当限制类型=1时，接口返回的提示内容',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '限制状态，0=正常，1=停用',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除，0=正常，1=删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '接口限制' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_api_limit
-- ----------------------------
INSERT INTO `sys_api_limit` VALUES ('700df1aab01ead0db7ce6105a9be90a5', '测试接口', '/test/test', 10, 30, '0', 20, 30, '0', '0', NULL, '0', '测试', '1', '2023-05-23 15:28:08', '1', '2023-05-24 18:07:46', 0);
INSERT INTO `sys_api_limit` VALUES ('71736a3b981f87f504bc4ae7bfb88008', '签到接口', '/sign', 0, 0, '0', 0, 0, '0', '1', '618期间，签到接口暂时停用', '0', '6月20日恢复', '1', '2023-05-23 15:32:12', '1', '2023-05-23 15:37:30', 0);
INSERT INTO `sys_api_limit` VALUES ('ab6ac1ae29d57e70879e8af885f6f668', '测试单表分页', '/test/data/list3', 5, 30, '0', NULL, NULL, '0', '0', '618期间暂时关闭，6月20日恢复', '0', NULL, '1', '2023-05-24 15:27:49', '1', '2023-09-22 14:26:00', 0);

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数名称',
  `key_va` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数键值',
  `value` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '参数键值',
  `type` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '系统内置，0=否，1=是',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数状态，0=正常，1=停用',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除，0=正常，1=删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统参数' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('0015263fa950c3ce40957fdb3b1e14a7', '系统验证码配置', 'sys.captcha.config', '{\n	\"config\": {\n		\"type\": 0,\n		\"lock\": \"true\"\n	},\n	\"image\": {\n		\"type\": 5,\n		\"width\": 200,\n		\"height\": 100,\n		\"codeCount\": 4,\n		\"olCount\": 10\n	},\n	\"slider\": {}\n}', '1', '0', '如果删除或者禁用此参数，那么系统将不开启验证码\n具体参数说明参考文档', '1', '2023-04-25 20:23:07', '1', '2023-08-30 22:10:42', 0);
INSERT INTO `sys_config` VALUES ('0ce0ea3dc352bb6838e4d008d9e849fc', '系统钉钉配置', 'sys.dingTalk.config', '{\n	\"testbot\": {\n		\"keywords\": \"\",\n		\"secret\": \"SEC48ea902b5b3fb138765108354550ab5d0a240a18e38bcb607ced5108f71f24a7\",\n		\"accessToken\": \"e714cfa1e60734d19d3dd136215c97a29dce75f0d2f68d2fbc0c92b4dd42e57d\",\n		\"remark\": \"测试群的机器人\"\n	},\n	\"xiaobai\": {\n		\"keywords\": \"【定时任务异常】\",\n		\"secret\": \"SEC48ea902b5b3fb138765108354550ab5d0a240a18e38bcb607ced5108f71f24a7\",\n		\"accessToken\": \"e714cfa1e60734d19d3dd136215c97a29dce75f0d2f68d2fbc0c92b4dd42e57d\",\n		\"remark\": \"闲聊群的机器人\"\n	}\n}', '1', '0', '如果删除或者禁用此参数，那么系统将无法发送钉钉通知\n具体参数说明参考文档', '1', '2023-05-13 18:27:24', '1', '2023-10-01 13:36:57', 0);
INSERT INTO `sys_config` VALUES ('0e4d78f5e0601e87489a563f40e30ff5', '图片上传限制策略', 'sys.upload.image', '{\n    \"limit\": 6,\n    \"fileSize\": 4,\n    \"fileType\": [\"bmp\", \"gif\", \"jpg\", \"jpeg\", \"png\"],\n    \"isShowTip\": true\n}', '1', '0', '如果删除此参数的话，系统将使用默认参数\n如果禁用此参数的话，系统 将禁止图片上传\nlimit=同时上传个数\nfileSize=文件最大尺寸，单位MB\nfileType=[]，支持的文件类型数组，不包含小数点\nisShowTip=是否显示提示', '1', '2023-04-17 22:24:15', '1', '2023-05-14 09:34:24', 0);
INSERT INTO `sys_config` VALUES ('101fbed52418ce72ffe30143c66fdd06', '系统注册默认信息', 'sys.register.default.info', '{\n    \"userType\": \"2\",\n    \"deptCode\": \"XMY-JN-3\",\n    \"positionCodes\": [\"user\"],\n    \"roleCodes\": [\"user\"]\n}', '1', '0', '此参数必存在，不存在的话，系统将禁止注册\nuserType，用户类型，必填，参考字典内\ndeptCode，部门编号，必填\npositionCodes，岗位编码，非必填，可多个\nroleCodes，角色编码，必填，可多个', '1', '2023-04-25 21:00:26', '1', '2023-04-25 21:07:58', 0);
INSERT INTO `sys_config` VALUES ('1a91bf36a72d9dcb253a941a94fa28fa', '文件上传限制策略', 'sys.upload.file', '{\n    \"limit\": 5,\n    \"fileSize\": 5,\n    \"fileType\": [\"zip\", \"txt\"],\n    \"isShowTip\": true\n}', '1', '0', '如果删除此参数的话，系统将使用默认参数\n如果禁用此参数的话，系统 将禁止文件上传\nlimit=同时上传个数\nfileSize=文件最大尺寸，单位MB\nfileType=[]，支持的文件类型数组，不包含小数点\nisShowTip=是否显示提示', '1', '2023-04-17 22:06:58', '1', '2023-04-25 21:00:37', 0);
INSERT INTO `sys_config` VALUES ('1c86983f628ffe1949171b01858d1aa5', '系统登录错误次数限制信息', 'sys.login.fail.info', '{\n    \"failCount\": 5,\n    \"banSecond\": 3600\n}', '1', '0', '此参数如果不存在或者被禁用的话，那么将没有限制\nfailCount，错误次数将锁定，必填\nbanSecond，锁定的秒数，必填', '1', '2023-05-10 09:29:46', '1', '2023-05-10 09:34:40', 0);
INSERT INTO `sys_config` VALUES ('2cd7adf8ceeb1386d85b5477ee03d0fe', '系统短信模板ID配置', 'sys.smsTemplate.config', '{\n	\"register\": {\n		\"variable\": \"code\",\n		\"templateId\": \"SMS_460755481\",\n		\"length\": 6\n	},\n	\"login\": {\n		\"variable\": \"code\",\n		\"templateId\": \"SMS_460755481\",\n		\"length\": 6\n	},\n	\"bindPhone\": {\n		\"variable\": \"code\",\n		\"templateId\": \"SMS_460755481\",\n		\"length\": 6\n	},\n	\"findPassword\": {\n		\"variable\": \"code\",\n		\"templateId\": \"SMS_460755481\",\n		\"length\": 6\n	},\n	\"jobError\": {\n		\"variable\": \"code\",\n		\"templateId\": \"SMS_460755481\",\n		\"length\": 70\n	}\n}', '1', '0', '如果删除或者禁用此参数，那么系统在以上特定场景将无法发送短信\n具体参数说明参考文档', '1', '2023-05-14 16:29:54', '1', '2023-09-19 14:23:07', 0);
INSERT INTO `sys_config` VALUES ('3d940640a0c040a0025c2c350da036f3', '系统注册开关', 'sys.lock.register', 'true', '1', '0', 'true=开启，false=关闭', '1', '2023-04-14 22:34:46', '1', '2023-04-25 21:31:29', 0);
INSERT INTO `sys_config` VALUES ('4aa484dd076566c2e7d936525e36e8c4', '系统短信配置', 'sys.sms.config', '{\r\n    \"config\": {\r\n        \"type\": \"aliyun\"\r\n    },\r\n    \"platform\": {\r\n        \"aliyun\": {\r\n            \"enable\": \"true\",\r\n            \"endpoint\": \"dysmsapi.aliyuncs.com\",\r\n            \"accessKeyId\": \"LTAI5tN1L6r9WqHYse6RpcQ9\",\r\n            \"accessKeySecret\": \"N9GXnZctzCeRUVYntSYD10kX3GPqW8\",\r\n            \"signName\": \"MyAdmin\"\r\n        },\r\n        \"tencent\": {\r\n            \"enable\": \"true\",\r\n            \"endpoint\": \"sms.tencentcloudapi.com\",\r\n            \"accessKeyId\": \"AKID2oPDoCYwmM0sb9nREwPdMp53mHfoYZGw\",\r\n            \"accessKeySecret\": \"e6JTP5ex2q9DXJtAkBMGvhHYBl7C5g05\",\r\n            \"signName\": \"个人开发记录网\",\r\n            \"sdkAppId\": \"1400820693\"\r\n        }\r\n    }\r\n}', '1', '0', '如果删除或者禁用此参数，那么系统将无法发送短信\n具体参数说明参考文档', '1', '2023-05-13 10:05:25', '1', '2023-05-13 11:39:54', 0);
INSERT INTO `sys_config` VALUES ('64c70f1e91cbcd3cfc390e3966aeef6b', '系统企微配置', 'sys.wecom.config', '{\n	\"testbot\": {\n		\"key\": \"b5caa377-c430-43e7-8f00-3d656ed93b12\",\n		\"remark\": \"测试群的机器人\"\n	}\n}', '0', '0', '如果删除或者禁用此参数，那么系统将无法发送企业微信通知\n具体参数说明参考文档', '1', '2023-10-01 13:41:53', '1', '2023-10-01 13:43:57', 0);
INSERT INTO `sys_config` VALUES ('65600f7210f507b143421c4ed33a9b07', '文件列表是否开启预览', 'sys.file.previewListResource', 'true', '1', '0', 'true=开启，false=关闭', '1', '2023-04-16 23:37:01', '1', '2023-04-25 21:00:40', 0);
INSERT INTO `sys_config` VALUES ('784d0f730c442a42bd6331e81516ebd8', '系统发送限制配置', 'sys.sendLimit.config', '{\n	\"email\": {\n		\"limitType\": 1,\n		\"needWait\": 60,\n		\"dayMax\": 8,\n		\"keepLive\": 1800\n	},\n	\"sms\": {\n		\"limitType\": 1,\n		\"needWait\": 60,\n		\"dayMax\": 8,\n		\"keepLive\": 300\n	}\n}', '1', '0', '如果删除或者禁用此参数，那么系统发送验证码时将没有任何限制\n具体参数说明参考文档', '1', '2023-05-19 15:09:31', '1', '2023-09-19 14:17:41', 0);
INSERT INTO `sys_config` VALUES ('83332089ca838d1816498f2bfd874ad3', '系统邮箱配置', 'sys.email.config', '{\n    \"config\": {\n        \"mode\": \"0\"\n    },\n    \"emails\": [{\n        \"enable\": \"true\",\n        \"host\": \"smtp.qq.com\",\n        \"port\": 587,\n        \"encode\": \"UTF-8\",\n        \"protocol\": \"smtp\",\n        \"email\": \"1330166564@qq.com\",\n        \"from\": \"MyAdmin<1330166564@qq.com>\",\n        \"password\": \"jifutmxruxjiffhg\",\n        \"timeout\": \"25000\",\n        \"auth\": \"true\",\n        \"socketFactoryClass\": \"javax.net.ssl.SSLSocketFactory\",\n        \"weight\": \"100\"\n    },{\n        \"enable\": \"true\",\n        \"host\": \"smtp.qq.com\",\n        \"port\": 587,\n        \"encode\": \"UTF-8\",\n        \"protocol\": \"smtp\",\n        \"email\": \"1330166565@qq.com\",\n        \"from\": \"MyAdmin<1330166565@qq.com>\",\n        \"password\": \"jifutmxruxjiffhg\",\n        \"timeout\": \"25000\",\n        \"auth\": \"true\",\n        \"socketFactoryClass\": \"javax.net.ssl.SSLSocketFactory\",\n        \"weight\": \"100\"\n    }]\n}', '1', '0', '如果删除或者禁用此参数，那么系统将无法发送邮件\n具体参数说明参考文档\n', '1', '2023-05-11 10:21:42', '1', '2023-05-22 16:42:58', 0);
INSERT INTO `sys_config` VALUES ('cc1dd434b089df0a6e97fbe446c39377', '系统飞书配置', 'sys.feishu.config', '{\n	\"testbot\": {\n		\"keywords\": \"\",\n		\"secret\": \"\",\n		\"accessToken\": \"207b5526-1d06-4398-b3a1-b57ac302626f\",\n		\"remark\": \"测试群的机器人\"\n	}\n}', '0', '0', '如果删除或者禁用此参数，那么系统将无法发送飞书通知\n具体参数说明参考文档', '1', '2023-10-01 13:40:50', '1', '2023-10-01 13:40:50', 0);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级部门ID，顶级为0',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门编号',
  `summary` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '部门简介',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门状态，0=正常，1=停用',
  `leader_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门负责人 关联用户ID',
  `dept_level` int NULL DEFAULT 0 COMMENT '层级，顶级为0',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除，0=正常，1=删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('100', '0', '熊猫眼集团', 'XMY', NULL, '0', '1', 0, 0, NULL, '1', '2023-04-10 14:35:22', '1', '2023-04-10 14:35:22', 0);
INSERT INTO `sys_dept` VALUES ('101', '100', '济南分公司', 'XMY-JN', NULL, '0', '1', 1, 1, NULL, '1', '2023-04-10 14:35:22', '1', '2023-10-08 14:02:37', 0);
INSERT INTO `sys_dept` VALUES ('102', '100', '北京分公司', 'XMY-BJ', NULL, '0', '1', 1, 2, NULL, '1', '2023-04-10 14:35:22', '1', '2023-04-10 14:35:22', 0);
INSERT INTO `sys_dept` VALUES ('103', '101', '研发部门', 'XMY-JN-1', NULL, '0', '1', 2, 1, '', '1', '2023-04-10 14:35:22', '1', '2023-04-25 20:54:24', 0);
INSERT INTO `sys_dept` VALUES ('104', '101', '市场部门', 'XMY-JN-2', NULL, '0', '1', 2, 2, NULL, '1', '2023-04-10 14:35:22', '1', '2023-04-12 21:26:01', 0);
INSERT INTO `sys_dept` VALUES ('105', '101', '测试部门', 'XMY-JN-3', NULL, '0', '1', 2, 3, NULL, '1', '2023-04-10 14:35:22', '1', '2023-04-12 21:25:51', 0);
INSERT INTO `sys_dept` VALUES ('106', '101', '财务部门', 'XMY-JN-4', NULL, '0', '1', 2, 4, NULL, '1', '2023-04-10 14:35:22', '1', '2023-04-10 14:35:22', 0);
INSERT INTO `sys_dept` VALUES ('107', '101', '运维部门', 'XMY-JN-5', NULL, '0', '1', 2, 5, NULL, '1', '2023-04-10 14:35:22', '1', '2023-10-08 14:02:51', 0);
INSERT INTO `sys_dept` VALUES ('108', '102', '市场部门', 'XMY-BJ-1', NULL, '0', '1', 2, 1, NULL, '1', '2023-04-10 14:35:22', '1', '2023-04-10 14:35:22', 0);
INSERT INTO `sys_dept` VALUES ('109', '102', '财务部门', 'XMY-BJ-2', NULL, '0', '1', 2, 2, NULL, '1', '2023-04-10 14:35:22', '1', '2023-04-10 14:35:22', 0);

-- ----------------------------
-- Table structure for sys_dept_parent
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_parent`;
CREATE TABLE `sys_dept_parent`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门ID',
  `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级部门ID，顶级为0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门层级关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept_parent
-- ----------------------------
INSERT INTO `sys_dept_parent` VALUES ('06d36f0f8901fb4ffbe781b080f07dd6', '107', '100');
INSERT INTO `sys_dept_parent` VALUES ('1a0b09c29f5470ac94079b303b0ad12b', '101', '101');
INSERT INTO `sys_dept_parent` VALUES ('1fd041e9beb93fa50133d2d7321e1cb0', '108', '100');
INSERT INTO `sys_dept_parent` VALUES ('222c3eaf636f8edf7f83eda4f90d26f2', '105', '100');
INSERT INTO `sys_dept_parent` VALUES ('268027691ee65797e33da228ea194b6c', '107', '107');
INSERT INTO `sys_dept_parent` VALUES ('26d358c6733335e1bcb9aae9ee3e54db', '102', '0');
INSERT INTO `sys_dept_parent` VALUES ('28070f8ff40f690d48c308b76e59a993', '105', '105');
INSERT INTO `sys_dept_parent` VALUES ('2d6c36d425ff5be02ae48940f701c3ef', '103', '103');
INSERT INTO `sys_dept_parent` VALUES ('384202fb5d68394c886cbeefadaf01ce', '109', '100');
INSERT INTO `sys_dept_parent` VALUES ('3d41adfbf70d1af146f9b22cfa3c4903', '100', '100');
INSERT INTO `sys_dept_parent` VALUES ('4222314fa5495df9d762a953cfcf65ac', '107', '0');
INSERT INTO `sys_dept_parent` VALUES ('494ccdf978d57d8bc96b02d72a9f5a3a', '108', '108');
INSERT INTO `sys_dept_parent` VALUES ('550ccf63f48406ebf42219e7d3219582', '107', '101');
INSERT INTO `sys_dept_parent` VALUES ('59f10bf5e1c6f714b4f1be02910553fa', '103', '101');
INSERT INTO `sys_dept_parent` VALUES ('617f7108449586ef211c0955a189b222', '108', '102');
INSERT INTO `sys_dept_parent` VALUES ('68b753537bc2bfa1b09e677077a5e1cc', '109', '102');
INSERT INTO `sys_dept_parent` VALUES ('700950267ddc9c18243472f68a2e0c62', '106', '101');
INSERT INTO `sys_dept_parent` VALUES ('886b217dcfb0a77b4b281f6a5981c69b', '102', '102');
INSERT INTO `sys_dept_parent` VALUES ('96b6974412095ef1f056f5596d1df48d', '103', '0');
INSERT INTO `sys_dept_parent` VALUES ('98faed672906375a309a2ac4c2ce47f1', '104', '100');
INSERT INTO `sys_dept_parent` VALUES ('9d125f57b2a7251caac817bed4ddd6ee', '103', '100');
INSERT INTO `sys_dept_parent` VALUES ('a63973aaebc357a568b9ec3e15bdb774', '104', '101');
INSERT INTO `sys_dept_parent` VALUES ('a95a29cd20b7a3ee0deabdd83b468bd9', '101', '100');
INSERT INTO `sys_dept_parent` VALUES ('b19a436916284476e08aacfbc1012e17', '101', '0');
INSERT INTO `sys_dept_parent` VALUES ('b264e91699df1ed6db2253203308452d', '106', '106');
INSERT INTO `sys_dept_parent` VALUES ('bf0399783891c2e9df37b693fdadf6d2', '105', '0');
INSERT INTO `sys_dept_parent` VALUES ('c57966fa37859a0c0c153c5dd5ca2a6d', '106', '100');
INSERT INTO `sys_dept_parent` VALUES ('d56ed387ac2c6105ffbe3faa8d99f5eb', '100', '0');
INSERT INTO `sys_dept_parent` VALUES ('d570bd153cc62d2a8c346bf55769bceb', '105', '101');
INSERT INTO `sys_dept_parent` VALUES ('d6cd40b06e1a20bc20d741d52fb03fed', '109', '0');
INSERT INTO `sys_dept_parent` VALUES ('e0754a80b8ef4531839db616d6a4f797', '104', '104');
INSERT INTO `sys_dept_parent` VALUES ('e5a25858e3e18b34cf4e51dcd45ffee3', '106', '0');
INSERT INTO `sys_dept_parent` VALUES ('e651fc9a844a23121783ace242907138', '102', '100');
INSERT INTO `sys_dept_parent` VALUES ('f0f6e94712736e9cb689bf044f5667af', '104', '0');
INSERT INTO `sys_dept_parent` VALUES ('f5cd73b71673848ce9a5a7543ccd38fa', '109', '109');
INSERT INTO `sys_dept_parent` VALUES ('fae666e9d536d28b28dcf84674e34aaf', '108', '0');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典名称',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典编码',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典状态，0=正常，1=禁用',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除，0=正常，1=删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('2baef88eba108855ecc7aa28beee9a5f', '接口限制类型', 'sys_api_limit_type', '0', NULL, '1', '2023-05-23 11:02:12', '1', '2023-05-23 11:02:22', 0);
INSERT INTO `sys_dict` VALUES ('32a3d1d751b94bf4aff2f9c7cb0c667d', '通知公告类型', 'sys_notice_type', '0', NULL, '1', '2023-03-13 09:58:45', '1', '2023-04-20 22:50:51', 0);
INSERT INTO `sys_dict` VALUES ('3da162a1fca95ac279a83107f6989db2', 'OSS厂商类型', 'sys_oss_type', '0', NULL, '1', '2023-04-16 23:49:02', '1', '2023-04-20 22:52:49', 0);
INSERT INTO `sys_dict` VALUES ('4164ee7178b8cfe228e2606b2ee4c18e', '系统用户状态', 'sys_user_status', '0', NULL, '1', '2023-03-27 11:10:12', '1', '2023-04-14 23:07:34', 0);
INSERT INTO `sys_dict` VALUES ('485fc2c337d2430cb39449dad5ba970a', '系统状态', 'sys_common_status', '0', NULL, '1', '2023-03-13 09:58:45', '1', '2023-03-13 09:58:45', 0);
INSERT INTO `sys_dict` VALUES ('709ac842d94f444cb50c94e1d5bea3e7', '测试数据类型', 'test_data_type', '0', NULL, NULL, '2023-03-15 14:11:03', '1', '2023-04-20 22:50:55', 0);
INSERT INTO `sys_dict` VALUES ('7d7457a0562a4c9a80e6f3de0426f7e7', '用户性别', 'sys_user_sex', '0', NULL, '1', '2023-03-13 09:58:45', '1', '2023-03-13 09:58:45', 0);
INSERT INTO `sys_dict` VALUES ('7e9cdefc71e446ba8ea69cf401913890', '系统开关', 'sys_normal_disable', '0', NULL, '1', '2023-02-13 09:58:45', '1', '2023-03-13 09:58:45', 0);
INSERT INTO `sys_dict` VALUES ('824bf83ca68c8c235e75dc731088d2f8', '系统通知渠道', 'sys_notify_channel', '0', '', '1', '2023-05-13 19:08:34', '1', '2023-05-13 19:08:37', 0);
INSERT INTO `sys_dict` VALUES ('98c39286b939f05fb514782a4779a5bf', '系统时间单位', 'sys_time_unit', '0', NULL, '1', '2023-05-23 14:47:07', '1', '2023-06-26 15:49:09', 0);
INSERT INTO `sys_dict` VALUES ('9b86e9ae2728400189d76f9b8710c269', '系统是否', 'sys_yes_no', '0', NULL, '1', '2023-02-13 09:58:45', '1', '2023-03-13 09:58:45', 0);
INSERT INTO `sys_dict` VALUES ('a08cf8bf214b49f68bcc03d5b8ecef7d', '通知公告状态', 'sys_notice_status', '0', NULL, '1', '2023-03-13 09:58:45', '1', '2023-04-20 22:51:25', 0);
INSERT INTO `sys_dict` VALUES ('bc4fd7d4cdb70ac8b48532de2f7410f7', '任务分组', 'sys_job_group', '0', NULL, '1', '2023-05-01 14:56:03', '1', '2023-05-01 14:56:03', 0);
INSERT INTO `sys_dict` VALUES ('c7936e47545262dd3836b322a768015f', 'OSS桶权限类型', 'sys_oss_scope', '0', NULL, '1', '2023-04-18 23:46:04', '1', '2023-04-20 22:52:37', 0);
INSERT INTO `sys_dict` VALUES ('c9e8979aa7c14a9bae2ccd624067c734', '菜单状态', 'sys_show_hide', '0', NULL, '1', '2023-03-13 09:58:45', '1', '2023-03-13 09:58:45', 0);
INSERT INTO `sys_dict` VALUES ('cb460b35c6cc2bf90b0c0ad7d092024c', '系统用户类型', 'sys_user_type', '0', NULL, '1', '2023-03-23 10:57:44', '1', '2023-04-20 22:50:58', 0);
INSERT INTO `sys_dict` VALUES ('cc2b5556e60e496fb3863bda3ab283e3', '操作类型', 'sys_oper_type', '0', NULL, '1', '2023-03-13 09:58:45', '1', '2023-03-13 09:58:45', 0);
INSERT INTO `sys_dict` VALUES ('d123f703a673a86c2340954fd89a4566', '数据权限', 'data_scope', '0', NULL, '1', '2023-03-29 10:38:08', '1', '2023-04-04 15:16:14', 0);
INSERT INTO `sys_dict` VALUES ('ddff67e6d459cf953f1ac23c413f76d3', '任务状态', 'sys_job_status', '0', NULL, '1', '2023-05-01 14:55:38', '1', '2023-05-01 14:55:38', 0);

-- ----------------------------
-- Table structure for sys_dict_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_detail`;
CREATE TABLE `sys_dict_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dict_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典编码',
  `label` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典标签',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典键值',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `css_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典状态，0=正常，1=禁用',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除，0=正常，1=删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_detail
-- ----------------------------
INSERT INTO `sys_dict_detail` VALUES ('00dce2554c6eb602a62bc45b23f19066', 'sys_job_status', '正常', '0', 0, NULL, 'success', '0', NULL, '1', '2023-05-01 14:57:26', '1', '2023-05-01 15:44:31', 0);
INSERT INTO `sys_dict_detail` VALUES ('015c16f7bcce3fbf50afa1b71646f05f', 'sys_api_limit_type', '停用', '1', 1, NULL, 'danger', '0', NULL, '1', '2023-05-23 11:02:45', '1', '2023-05-23 11:02:50', 0);
INSERT INTO `sys_dict_detail` VALUES ('0278ac576ea3fa7f1e3df720e4c051b2', 'test_data_type', '其他', '3', 4, NULL, 'warning', '0', '其他', '1', '2023-03-17 10:38:09', '1', '2023-04-20 22:50:55', 0);
INSERT INTO `sys_dict_detail` VALUES ('0402c072489ef66c7ec33916c2b1f938', 'sys_oper_type', '删除', '2', 2, NULL, 'danger', '0', NULL, '1', '2023-04-19 22:24:04', '1', '2023-04-19 22:24:12', 0);
INSERT INTO `sys_dict_detail` VALUES ('065f42570f8454a5a91477ce552ff24d', 'sys_notify_channel', '邮箱', '1', 1, NULL, 'primary', '0', NULL, '1', '2023-05-13 19:09:14', '1', '2023-05-13 19:09:14', 0);
INSERT INTO `sys_dict_detail` VALUES ('0a77ebdc362f6c039aec7e8261402a82', 'sys_oss_type', '华为云', '华为云', 0, NULL, 'danger', '0', NULL, '1', '2023-04-16 23:55:15', '1', '2023-04-20 22:52:49', 0);
INSERT INTO `sys_dict_detail` VALUES ('0ab7306ac5aaf342514eeed9488e4216', 'data_scope', '本人数据', '0', 0, NULL, 'info', '0', NULL, '1', '2023-03-29 10:38:22', '1', '2023-03-29 10:38:22', 0);
INSERT INTO `sys_dict_detail` VALUES ('0eee47140c4a07a008365f9fa8d8cdc1', 'sys_job_group', '默认', 'DEFAULT', 1, NULL, 'success', '0', NULL, '1', '2023-05-01 14:56:53', '1', '2023-05-01 14:57:01', 0);
INSERT INTO `sys_dict_detail` VALUES ('11abad5a8a2ac077c362b044bc99778e', 'sys_oper_type', '其他', '0', 0, NULL, 'info', '0', NULL, '1', '2023-04-19 22:23:37', '1', '2023-04-19 22:23:37', 0);
INSERT INTO `sys_dict_detail` VALUES ('1638c4c1734530de841be71cc5ebd8b3', 'sys_time_unit', '分钟', '1', 1, NULL, 'primary', '0', NULL, '1', '2023-05-23 14:48:01', '1', '2023-06-26 15:49:09', 0);
INSERT INTO `sys_dict_detail` VALUES ('19c1384a77f2421a95a75cb99ddbadd4', 'sys_yes_no', '是', '1', 2, NULL, 'primary', '0', '系统默认是', '1', '2023-03-15 16:36:49', '1', '2023-03-15 16:36:49', 0);
INSERT INTO `sys_dict_detail` VALUES ('1c48afa8c9c14fb8a3aec4c255b89769', 'test_data_type', '科技', '1', 2, NULL, 'info', '0', '科技', '1', '2023-03-15 16:36:49', '1', '2023-04-20 22:50:55', 0);
INSERT INTO `sys_dict_detail` VALUES ('325a4db1eb9c3d73ab4754875e2ef9bb', 'sys_oss_type', 'minio', 'minio', 0, '', 'primary', '0', NULL, '1', '2023-04-16 23:49:45', '1', '2023-04-20 22:52:49', 0);
INSERT INTO `sys_dict_detail` VALUES ('3400ec32fe6a4b69aacd2b5f7986da00', 'sys_user_sex', '女', '0', 1, NULL, NULL, '0', '性别女', '1', '2023-03-15 16:36:49', '1', '2023-03-15 16:36:49', 0);
INSERT INTO `sys_dict_detail` VALUES ('374e0ff900544b7fa60e407422ad78bd', 'sys_user_sex', '男', '1', 2, NULL, NULL, '0', '性别男', '1', '2023-03-15 16:36:49', '1', '2023-03-15 16:36:49', 0);
INSERT INTO `sys_dict_detail` VALUES ('37b9f8bb3b4d4b36dfd605c69f796540', 'sys_notify_channel', '短信', '2', 2, NULL, 'success', '0', NULL, '1', '2023-05-13 19:09:24', '1', '2023-05-13 19:09:24', 0);
INSERT INTO `sys_dict_detail` VALUES ('413a1e5fbfa246e6a520f01fab6a6025', 'sys_yes_no', '否', '0', 1, NULL, 'danger', '0', '系统默认否', '1', '2023-03-15 16:36:49', '1', '2023-03-15 16:36:49', 0);
INSERT INTO `sys_dict_detail` VALUES ('485e68dd0cf3ee70cb778a6409b57e1c', 'sys_job_status', '暂停', '1', 1, NULL, 'info', '0', NULL, '1', '2023-05-01 14:57:32', '1', '2023-05-01 15:44:57', 0);
INSERT INTO `sys_dict_detail` VALUES ('4a0a41f396405aedcf01c7e97d2c1860', 'sys_oper_type', '下载', '8', 8, NULL, 'warning', '0', NULL, '1', '2023-04-19 22:25:39', '1', '2023-04-19 22:25:39', 0);
INSERT INTO `sys_dict_detail` VALUES ('4b1b2891d50e4b419be63f230489562f', 'sys_notice_type', '公告', '2', 2, NULL, 'success', '0', '公告', '1', '2023-03-15 16:36:49', '1', '2023-04-20 22:50:51', 0);
INSERT INTO `sys_dict_detail` VALUES ('56d7b1c499d747b5bd0bfa4158562439', 'sys_common_status', '成功', '0', 1, NULL, 'success', '0', '正常状态', '1', '2023-03-15 16:36:49', '1', '2023-05-01 21:08:16', 0);
INSERT INTO `sys_dict_detail` VALUES ('5baa5fea23ed4f2c8ed4b32e63e8a405', 'sys_show_hide', '隐藏', '1', 2, NULL, 'danger', '0', '隐藏菜单', '1', '2023-03-15 16:36:49', '1', '2023-03-15 16:36:49', 0);
INSERT INTO `sys_dict_detail` VALUES ('6232d1a9d3cb4485b15bd20a03e807d0', 'sys_notice_status', '关闭', '1', 2, NULL, 'danger', '0', '关闭状态', '1', '2023-03-15 16:36:49', '1', '2023-04-20 22:51:25', 0);
INSERT INTO `sys_dict_detail` VALUES ('66824297a41647e72d8257d1b0dcaad7', 'sys_user_type', '运维', '2', 0, NULL, 'primary', '0', NULL, '1', '2023-03-23 11:04:58', '1', '2023-04-20 22:50:58', 0);
INSERT INTO `sys_dict_detail` VALUES ('6bf3a9656aa4697581a0be46ffc67736', 'sys_oss_scope', 'custom', '2', 2, NULL, 'primary', '0', NULL, '1', '2023-04-18 23:46:54', '1', '2023-04-20 22:52:38', 0);
INSERT INTO `sys_dict_detail` VALUES ('6f6212d3ee407955b6e1606d6bc692fa', 'sys_oss_type', '腾讯云', '腾讯云', 0, NULL, 'info', '0', NULL, '1', '2023-04-16 23:54:48', '1', '2023-04-20 22:52:49', 0);
INSERT INTO `sys_dict_detail` VALUES ('703f50ed1e45a4d8f43ca7644a3b5703', 'data_scope', '本部门数据', '1', 1, NULL, 'primary', '0', NULL, '1', '2023-03-29 10:38:44', '1', '2023-03-29 10:38:44', 0);
INSERT INTO `sys_dict_detail` VALUES ('707107e22b1e4021aad925c891652f0e', 'test_data_type', '民生', '0', 1, NULL, 'primary', '0', '民生', '1', '2023-03-15 16:36:49', '1', '2023-04-20 22:50:55', 0);
INSERT INTO `sys_dict_detail` VALUES ('74538cc5e03118a8f95bec4b9f60ac20', 'sys_oper_type', '查询', '4', 4, NULL, 'success', '0', NULL, '1', '2023-04-19 22:24:40', '1', '2023-04-19 22:24:40', 0);
INSERT INTO `sys_dict_detail` VALUES ('7e4e1fc182c3cbc573a32eb7fbc9a749', 'sys_notify_channel', '飞书', '4', 4, NULL, 'warning', '0', NULL, '1', '2023-10-01 14:33:28', '1', '2023-10-01 14:33:28', 0);
INSERT INTO `sys_dict_detail` VALUES ('7e85865f7e80a1f20b4f5767a6d6f8b5', 'sys_job_group', '系统', 'SYSTEM', 0, NULL, 'primary', '0', NULL, '1', '2023-05-01 14:56:36', '1', '2023-05-01 14:56:36', 0);
INSERT INTO `sys_dict_detail` VALUES ('847f21debb0a59235347dad479309d67', 'sys_api_limit_type', '限流', '0', 0, NULL, 'warning', '0', NULL, '1', '2023-05-23 11:02:37', '1', '2023-05-23 11:02:37', 0);
INSERT INTO `sys_dict_detail` VALUES ('87f33838c66f34a94d7c19c384e4758c', 'sys_notify_channel', '企业微信', '5', 5, NULL, 'warning', '0', NULL, '1', '2023-10-01 14:33:39', '1', '2023-10-01 14:33:39', 0);
INSERT INTO `sys_dict_detail` VALUES ('8cf5ea8761164a1e93f5524fad6df101', 'sys_normal_disable', '正常', '0', 1, NULL, 'primary', '0', '正常状态', '1', '2023-03-15 16:36:49', '1', '2023-03-15 16:36:49', 0);
INSERT INTO `sys_dict_detail` VALUES ('8d9fb34470f5f0351efd0dcf2f733577', 'sys_user_type', '开发', '1', 0, NULL, 'danger', '0', NULL, '1', '2023-03-23 11:04:52', '1', '2023-04-20 22:50:58', 0);
INSERT INTO `sys_dict_detail` VALUES ('8fff0e476aabc28003c96852dd515995', 'data_scope', '自定义权限', '4', 4, NULL, 'warning', '0', NULL, '1', '2023-03-29 10:39:40', '1', '2023-03-29 10:39:40', 0);
INSERT INTO `sys_dict_detail` VALUES ('92ffc9d5d14bd57bfdf48926b84edd44', 'sys_oper_type', '修改', '3', 3, NULL, 'warning', '0', NULL, '1', '2023-04-19 22:24:28', '1', '2023-04-19 22:24:28', 0);
INSERT INTO `sys_dict_detail` VALUES ('9908f3fc165afb446b2a469b848c84ea', 'sys_oss_type', '阿里云', '阿里云', 0, NULL, 'success', '0', NULL, '1', '2023-04-16 23:54:41', '1', '2023-04-20 22:52:49', 0);
INSERT INTO `sys_dict_detail` VALUES ('9d68e851142d435b88d12ba633f7e4ea', 'sys_user_sex', '未知', '2', 3, NULL, NULL, '0', '性别未知', '1', '2023-03-15 16:36:49', '1', '2023-03-15 16:36:49', 0);
INSERT INTO `sys_dict_detail` VALUES ('a30fee8cece01061abbe27922e94b7dd', 'sys_oper_type', '新增', '1', 1, NULL, 'primary', '0', NULL, '1', '2023-04-19 22:23:47', '1', '2023-04-19 22:24:15', 0);
INSERT INTO `sys_dict_detail` VALUES ('a741425d33008d8b69adde307897bff9', 'sys_oss_scope', 'private', '0', 0, NULL, 'success', '0', NULL, '1', '2023-04-18 23:46:26', '1', '2023-04-20 22:52:38', 0);
INSERT INTO `sys_dict_detail` VALUES ('a981115becdc2a8643ff774ac36dafe1', 'sys_time_unit', '秒', '0', 0, NULL, 'success', '0', NULL, '1', '2023-05-23 14:47:51', '1', '2023-06-26 15:49:09', 0);
INSERT INTO `sys_dict_detail` VALUES ('a9c8f290b3fe2e21e0e57953da78e8ff', 'sys_user_status', '停用', '1', 0, NULL, 'warning', '0', NULL, '1', '2023-03-27 11:10:43', '1', '2023-04-14 23:07:34', 0);
INSERT INTO `sys_dict_detail` VALUES ('b633a41a4c816cfb8622c2e874fbbb47', 'sys_oper_type', '上传', '7', 7, NULL, 'primary', '0', NULL, '1', '2023-04-19 22:25:26', '1', '2023-04-19 22:25:26', 0);
INSERT INTO `sys_dict_detail` VALUES ('b825663a1745a0aa65307acceff1e87c', 'data_scope', '本部门及以下数据', '2', 2, NULL, 'success', '0', NULL, '1', '2023-03-29 10:39:04', '1', '2023-03-29 10:39:04', 0);
INSERT INTO `sys_dict_detail` VALUES ('c46d3f2ed39d4933abccfeadeceae66c', 'sys_notice_status', '正常', '0', 1, NULL, 'primary', '0', '正常状态', '1', '2023-03-15 16:36:49', '1', '2023-04-20 22:51:25', 0);
INSERT INTO `sys_dict_detail` VALUES ('c7ebb92a91938d26c50ae8ea69e826d2', 'sys_oss_type', '七牛云', '七牛云', 0, NULL, 'warning', '0', NULL, '1', '2023-04-16 23:54:54', '1', '2023-04-20 22:52:49', 0);
INSERT INTO `sys_dict_detail` VALUES ('c84287c50997d6ec0ddf19bd68f84e87', 'sys_oper_type', '导出', '6', 6, NULL, 'warning', '0', NULL, '1', '2023-04-19 22:25:10', '1', '2023-04-19 22:25:10', 0);
INSERT INTO `sys_dict_detail` VALUES ('c8f8118fc33f410ba61a12f780e76c3f', 'sys_notice_type', '通知', '1', 1, NULL, 'warning', '0', '通知', '1', '2023-03-15 16:36:49', '1', '2023-04-20 22:50:51', 0);
INSERT INTO `sys_dict_detail` VALUES ('ca7ad3018855a44d543790fd313dc9a3', 'sys_user_status', '正常', '0', 0, NULL, 'success', '0', NULL, '1', '2023-03-27 11:10:32', '1', '2023-04-14 23:07:34', 0);
INSERT INTO `sys_dict_detail` VALUES ('cb021c733abc68910a1f0ceb03a06499', 'sys_notify_channel', '钉钉', '3', 3, NULL, 'warning', '0', NULL, '1', '2023-05-13 19:09:47', '1', '2023-05-13 19:10:00', 0);
INSERT INTO `sys_dict_detail` VALUES ('cbbc1ccaa65740479dde6e52529e9fda', 'sys_show_hide', '显示', '0', 1, NULL, 'primary', '0', '显示菜单', '1', '2023-03-15 16:36:49', '1', '2023-03-15 16:36:49', 0);
INSERT INTO `sys_dict_detail` VALUES ('ce73653c160744d9364aadc0a39f6d6d', 'test_data_type', '农业', '2', 3, NULL, 'success', '0', '农业', '1', '2023-03-17 10:37:50', '1', '2023-04-20 22:50:55', 0);
INSERT INTO `sys_dict_detail` VALUES ('dffa0d31ac0ae45cddd5f319c52bf818', 'data_scope', '全部数据', '3', 3, NULL, 'danger', '0', NULL, '1', '2023-03-29 10:39:28', '1', '2023-03-29 10:39:28', 0);
INSERT INTO `sys_dict_detail` VALUES ('e1720b95d3be9ce033efd041ea649134', 'sys_user_type', '测试', '3', 0, NULL, 'info', '0', NULL, '1', '2023-03-27 15:36:50', '1', '2023-04-20 22:50:58', 0);
INSERT INTO `sys_dict_detail` VALUES ('e1961a649aa19edaae657b88fd19e91b', 'sys_notify_channel', '不通知', '0', 0, NULL, 'info', '0', NULL, '1', '2023-05-13 19:09:01', '1', '2023-05-13 19:09:54', 0);
INSERT INTO `sys_dict_detail` VALUES ('e5e1239310b9383f5ce42d557093bac8', 'sys_user_status', '注销', '2', 0, NULL, 'danger', '0', NULL, '1', '2023-03-27 11:10:51', '1', '2023-04-14 23:07:34', 0);
INSERT INTO `sys_dict_detail` VALUES ('e83acff859cf850825835a80e7c9dc00', 'sys_oss_scope', 'public', '1', 1, NULL, 'danger', '0', NULL, '1', '2023-04-18 23:46:37', '1', '2023-04-20 22:52:38', 0);
INSERT INTO `sys_dict_detail` VALUES ('eadebaa7234ca12881ac845803b978a1', 'sys_time_unit', '小时', '2', 2, '', 'info', '0', NULL, '1', '2023-05-23 14:48:15', '1', '2023-06-26 15:49:09', 0);
INSERT INTO `sys_dict_detail` VALUES ('eaeffc348c4e40cf9a45e91db17d0902', 'sys_normal_disable', '停用', '1', 2, NULL, 'danger', '0', '停用状态', '1', '2023-03-15 16:36:49', '1', '2023-03-15 16:36:49', 0);
INSERT INTO `sys_dict_detail` VALUES ('ee69bc49e73716f01b4f369f1323a9e9', 'sys_oper_type', '导入', '5', 5, NULL, 'primary', '0', NULL, '1', '2023-04-19 22:24:55', '1', '2023-04-19 22:24:55', 0);
INSERT INTO `sys_dict_detail` VALUES ('f328b5c9651db1236cdf1d3682ac7f64', 'sys_oss_type', '京东云', '京东云', 0, NULL, 'danger', '0', NULL, '1', '2023-04-16 23:55:04', '1', '2023-04-20 22:52:49', 0);
INSERT INTO `sys_dict_detail` VALUES ('ff1467cd4b4d4d84868a8f31a4f32fec', 'sys_common_status', '失败', '1', 2, NULL, 'danger', '0', '停用状态', '1', '2023-03-15 16:36:49', '1', '2023-03-15 16:36:49', 0);
INSERT INTO `sys_dict_detail` VALUES ('ff3eed8202940077df815bfdf0b5d836', 'sys_time_unit', '天', '3', 3, NULL, 'warning', '0', NULL, '1', '2023-05-23 14:48:37', '1', '2023-06-26 15:49:09', 0);

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `original_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原始文件名称',
  `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `file_suffix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件后缀名',
  `file_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件URL地址',
  `file_size` int NULL DEFAULT NULL COMMENT '文件大小（字节）',
  `file_md5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件MD5（eTag）',
  `file_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件类型，例如：例如：image/jpeg',
  `oss_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属OSS配置ID',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件状态，0=正常，1=禁用',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除，0=正常，1=删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'OSS文件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_file
-- ----------------------------

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务分组',
  `invoke_target` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT 'cron执行表达式',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务状态，0=正常，1=暂停',
  `misfire_policy` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '3' COMMENT '计划执行错误策略，1=立即执行，2=执行一次，3=放弃执行',
  `concurrent` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否并发执行，0=允许，1=禁止',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `notify_channel` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '异常时，通知渠道，0=不通知，1=邮件，2=短信，3=钉钉，4=飞书，5=企业微信',
  `notify_objs` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '异常时，通知对象，多个用,隔开\r\n     * 邮件渠道时，写邮箱\r\n     * 短信渠道时，写手机号\r\n     * 钉钉、飞书、企业微信渠道时，写botName',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除，0=正常，1=删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务调度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES ('0a8d20cd101aad4a761e548ff062f593', '测试（无参数）', 'DEFAULT', 'TestTask.noParams', '6 2 * * * ?', '1', '1', '1', NULL, '1', '1330166565@qq.com,1747846658@qq.com', '1', '2023-05-01 16:57:10', '1', '2023-06-05 18:21:20', 0);
INSERT INTO `sys_job` VALUES ('5ce12d1934bb1d67d4c3787aa037bc7c', '测试（多个参数）', 'DEFAULT', 'TestTask.multipleParams(\'test\', true, 666L, 365.16D, 200)', '* * * * * ?', '1', '2', '1', NULL, '0', NULL, '1', '2023-05-01 15:45:55', '1', '2023-06-05 18:21:31', 0);
INSERT INTO `sys_job` VALUES ('b28738a16605721937a74cd3a1089fb8', '测试（一个参数）', 'DEFAULT', 'TestTask.oneParams(\'阿萨德\')', '0 0 * * * ?', '1', '1', '1', NULL, '4', 'testbot', '1', '2023-05-01 16:53:05', '1', '2023-10-01 14:36:33', 0);

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联定时任务ID',
  `job_message` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '日志信息',
  `exception_info` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '异常信息',
  `start_time` datetime NULL DEFAULT NULL COMMENT '开始执行时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束执行时间',
  `execute_time` int NULL DEFAULT 0 COMMENT '执行耗时时间（毫秒）',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行结果，0=成功，1=失败',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除，0=正常，1=删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务调度日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log_login
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_login`;
CREATE TABLE `sys_log_login`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户账号',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录IP',
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录地点',
  `browser` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `os` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录结果，0=成功，1=失败',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除，0=正常，1=删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '登录日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log_login
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log_oper
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_oper`;
CREATE TABLE `sys_log_oper`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作名称',
  `type` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作类型，0=其他，1=新增，2=删除，3=修改，4=查询，5=导入，6=导出',
  `method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `request_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  `request_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求URL',
  `request_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求者IP',
  `request_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求者地址',
  `request_params` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求参数',
  `request_time` datetime NULL DEFAULT NULL COMMENT '请求时间',
  `response_result` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '响应结果',
  `response_time` datetime NULL DEFAULT NULL COMMENT '响应时间',
  `error_msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '错误信息',
  `execute_time` int NULL DEFAULT NULL COMMENT '耗时时间（毫秒）',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求结果，0=成功，1=失败',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除，0=正常，1=删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log_oper
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级部门ID，顶级为0',
  `menu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `order_num` int NULL DEFAULT NULL COMMENT '排序',
  `path` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由地址',
  `query_param` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由参数',
  `component` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `perms` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `visible` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '显示状态，0=正常，1=隐藏',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单状态，0=正常，1=禁用',
  `menu_type` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单类型，1=目录，2=菜单，3=按钮',
  `is_frame` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否外链，0=是，1=否',
  `is_cache` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否缓存，0=缓存，1=不缓存',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除，0=正常，1=删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('0451a199b76ce5725fc8003ba7cc56d4', 'd5d81e35c103ed1dac93a70b97819877', '刷新限制缓存', 6, NULL, NULL, NULL, 'monitor:apiLimit:refreshCache', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', 1, 'system', NULL, NULL, NULL, 'system', '0', '0', '1', '1', '0', '系统管理目录', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('100', '1', '用户管理', 1, 'user', NULL, 'system/user/index', 'system:user:list', 'user', '0', '0', '2', '1', '0', '用户管理菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1001', '100', '用户查询', 1, NULL, NULL, NULL, 'system:user:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1002', '100', '用户新增', 2, NULL, NULL, NULL, 'system:user:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1003', '100', '用户修改', 3, NULL, NULL, NULL, 'system:user:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1004', '100', '用户删除', 4, NULL, NULL, NULL, 'system:user:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1005', '100', '用户导出', 5, NULL, NULL, NULL, 'system:user:export', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1006', '100', '用户导入', 6, NULL, NULL, NULL, 'system:user:import', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1007', '100', '重置密码', 7, NULL, NULL, NULL, 'system:user:resetPwd', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1008', '101', '角色查询', 1, NULL, NULL, NULL, 'system:role:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1009', '101', '角色新增', 2, NULL, NULL, NULL, 'system:role:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('101', '1', '角色管理', 2, 'role', NULL, 'system/role/index', 'system:role:list', 'peoples', '0', '0', '2', '1', '0', '角色管理菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1010', '101', '角色修改', 3, NULL, NULL, NULL, 'system:role:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1011', '101', '角色删除', 4, NULL, NULL, NULL, 'system:role:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1012', '101', '角色导出', 5, NULL, NULL, NULL, 'system:role:export', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1013', '102', '菜单查询', 1, NULL, NULL, NULL, 'system:menu:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1014', '102', '菜单新增', 2, NULL, NULL, NULL, 'system:menu:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1015', '102', '菜单修改', 3, NULL, NULL, NULL, 'system:menu:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1016', '102', '菜单删除', 4, NULL, NULL, NULL, 'system:menu:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1017', '103', '部门查询', 1, NULL, NULL, NULL, 'system:dept:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1018', '103', '部门新增', 2, NULL, NULL, NULL, 'system:dept:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1019', '103', '部门修改', 3, NULL, NULL, NULL, 'system:dept:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('102', '1', '菜单管理', 3, 'menu', NULL, 'system/menu/index', 'system:menu:list', 'tree-table', '0', '0', '2', '1', '0', '菜单管理菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1020', '103', '部门删除', 4, NULL, NULL, NULL, 'system:dept:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1021', '104', '岗位查询', 1, NULL, NULL, NULL, 'system:position:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1022', '104', '岗位新增', 2, NULL, NULL, NULL, 'system:position:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1023', '104', '岗位修改', 3, NULL, NULL, NULL, 'system:position:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1024', '104', '岗位删除', 4, NULL, NULL, NULL, 'system:position:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1025', '104', '岗位导出', 5, NULL, NULL, NULL, 'system:position:export', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1026', '105', '字典查询', 1, '#', NULL, NULL, 'system:dict:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1027', '105', '字典新增', 2, '#', NULL, NULL, 'system:dict:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1028', '105', '字典修改', 3, '#', NULL, NULL, 'system:dict:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1029', '105', '字典删除', 4, '#', NULL, NULL, 'system:dict:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('103', '1', '部门管理', 4, 'dept', NULL, 'system/dept/index', 'system:dept:list', 'tree', '0', '0', '2', '1', '0', '部门管理菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1030', '105', '字典导出', 5, '#', NULL, NULL, 'system:dict:export', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1031', '106', '参数查询', 1, '#', NULL, NULL, 'system:config:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1032', '106', '参数新增', 2, '#', NULL, NULL, 'system:config:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1033', '106', '参数修改', 3, '#', NULL, NULL, 'system:config:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1034', '106', '参数删除', 4, '#', NULL, NULL, 'system:config:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1035', '106', '参数导出', 5, '#', NULL, NULL, 'system:config:export', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1036', '107', '公告查询', 1, '#', NULL, NULL, 'monitor:notice:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1037', '107', '公告新增', 2, '#', NULL, NULL, 'monitor:notice:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1038', '107', '公告修改', 3, '#', NULL, NULL, 'monitor:notice:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1039', '107', '公告删除', 4, '#', NULL, NULL, 'monitor:notice:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('104', '1', '岗位管理', 5, 'position', NULL, 'system/position/index', 'system:position:list', 'post', '0', '0', '2', '1', '0', '岗位管理菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1040', '500', '操作日志查询', 1, '#', NULL, NULL, 'monitor:logOper:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1041', '500', '操作日志删除', 2, '#', NULL, NULL, 'monitor:logOper:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1042', '500', '操作日志导出', 4, '#', NULL, NULL, 'monitor:logOper:export', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1043', '501', '登录日志查询', 1, '#', NULL, NULL, 'monitor:logLogin:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1044', '501', '登录日志删除', 2, '#', NULL, NULL, 'monitor:logLogin:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1045', '501', '日志日志导出', 3, '#', NULL, NULL, 'monitor:logLogin:export', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('105', '1', '字典管理', 6, 'dict', NULL, 'system/dict/index', 'system:dict:list', 'dict', '0', '0', '2', '1', '0', '字典管理菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('106', '1', '参数设置', 7, 'config', NULL, 'system/config/index', 'system:config:list', 'edit', '0', '0', '2', '1', '0', '参数设置菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('107', '2', '通知公告', 5, 'notice', NULL, 'monitor/notice/index', 'monitor:notice:list', 'message', '0', '0', '2', '1', '0', '通知公告菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('108', '2', '日志管理', 1, 'log', NULL, NULL, NULL, 'log', '0', '0', '1', '1', '0', '日志管理菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('111', '63854784d5ac52bfd2ac578a48f0a46f', '数据监控', 1, 'druid', NULL, 'monitor/druid/index', 'monitor:druid:list', 'druid', '0', '0', '2', '1', '0', '数据监控菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('114', '3', '表单构建', 1, 'build', NULL, 'tool/build/index', 'tool:build:list', 'build', '0', '0', '2', '1', '0', '表单构建菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('118', '2', '文件管理', 6, 'file', NULL, 'monitor/file/index', 'monitor:file:list', 'upload', '0', '0', '2', '1', '0', '文件管理菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('13cbe1c8683a41dae7bccb289fd5dfd9', '42708c34ab41a691ef032ab1f4cf5db0', '任务修改', 3, NULL, NULL, NULL, 'monitor:job:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1500', '5', '测试单表', 1, 'data', NULL, 'test/data/index', 'test:data:list', '#', '0', '0', '2', '1', '0', '测试单表菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1501', '1500', '测试单表查询', 1, '#', NULL, NULL, 'test:data:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1502', '1500', '测试单表新增', 2, '#', NULL, NULL, 'test:data:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1503', '1500', '测试单表修改', 3, '#', NULL, NULL, 'test:data:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1504', '1500', '测试单表删除', 4, '#', NULL, NULL, 'test:data:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1505', '1500', '测试单表导出', 5, '#', NULL, NULL, 'test:data:export', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1506', '5', '测试树表', 1, 'dataTree', NULL, 'test/dataTree/index', 'test:dataTree:list', '#', '0', '0', '2', '1', '0', '测试树表菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1507', '1506', '测试树表查询', 1, '#', NULL, NULL, 'test:dataTree:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1508', '1506', '测试树表新增', 2, '#', NULL, NULL, 'test:dataTree:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1509', '1506', '测试树表修改', 3, '#', NULL, NULL, 'test:dataTree:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1510', '1506', '测试树表删除', 4, '#', NULL, NULL, 'test:dataTree:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1600', '118', '文件查询', 1, '#', NULL, NULL, 'monitor:file:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1601', '118', '文件上传', 2, '#', NULL, NULL, 'monitor:file:upload', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1602', '118', '文件下载', 3, '#', NULL, NULL, 'monitor:file:download', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1603', '118', '文件删除', 4, '#', NULL, NULL, 'monitor:file:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1604', '118', '配置添加', 5, '#', NULL, NULL, 'monitor:ossConig:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1605', '118', '配置编辑', 6, '#', NULL, NULL, 'monitor:ossConig:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1698eeba543e9f12542edba21ae2d16f', '42708c34ab41a691ef032ab1f4cf5db0', '任务删除', 4, NULL, NULL, NULL, 'monitor:job:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('1f7c8a04250067db11e6fcae4298234e', 'd5d81e35c103ed1dac93a70b97819877', '限制修改', 3, NULL, NULL, NULL, 'monitor:apiLimit:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('2', '0', '系统运维', 2, 'monitor', NULL, NULL, NULL, 'monitor', '0', '0', '1', '1', '0', '系统监控目录', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('268571f3b6e74a1fc5bde8df2594ed81', '42708c34ab41a691ef032ab1f4cf5db0', '任务查询', 1, NULL, NULL, NULL, 'monitor:job:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('2add827d98c3b366ce0aa160d8a281ee', 'd5d81e35c103ed1dac93a70b97819877', '限制查询', 1, NULL, NULL, NULL, 'monitor:apiLimit:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('2b6cc0d2b8a0907d2832f9557089a682', 'd5d81e35c103ed1dac93a70b97819877', '限制新增', 2, NULL, NULL, NULL, 'monitor:apiLimit:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('2d562fa236077b42b5142eb869db6b18', '63854784d5ac52bfd2ac578a48f0a46f', '服务监控', 2, 'server', NULL, 'monitor/server/index', 'monitor:server:list', 'server', '0', '0', '2', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('3', '0', '系统工具', 3, 'tool', NULL, NULL, NULL, 'tool', '0', '0', '1', '1', '0', '系统工具目录', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('36c032b1a5af71ad776641f68cbbeb60', '42708c34ab41a691ef032ab1f4cf5db0', '任务状态修改', 5, NULL, NULL, NULL, 'monitor:job:changeStatus', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('39cfe621da13b55952f2af58edc3e53a', '6b70f47f76199b8cd35aae0cda15d449', '发送钉钉', 3, NULL, NULL, NULL, 'tool:functest:sendDingTalk', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('42708c34ab41a691ef032ab1f4cf5db0', '2', '定时任务', 3, 'job', NULL, 'monitor/job/index', 'monitor:job:list', 'job', '0', '0', '2', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('5', '0', '测试菜单', 5, 'demo', NULL, NULL, NULL, 'star', '0', '0', '1', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('500', '108', '操作日志', 1, 'logOper', NULL, 'monitor/logOper/index', 'monitor:logOper:list', 'form', '0', '0', '2', '1', '0', '操作日志菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('501', '108', '登录日志', 2, 'logLogin', NULL, 'monitor/logLogin/index', 'monitor:logLogin:list', 'logininfor', '0', '0', '2', '1', '0', '登录日志菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('63854784d5ac52bfd2ac578a48f0a46f', '2', '系统监控', 2, 'monitor', NULL, NULL, NULL, 'time-range', '0', '0', '1', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('69519bae5ffa67623e8bfb1c8fabdd00', '118', '配置查询', 8, NULL, NULL, NULL, 'monitor:ossConig:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('6971d165938715753fef8a07c112ba98', '42708c34ab41a691ef032ab1f4cf5db0', '任务新增', 2, NULL, NULL, NULL, 'monitor:job:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('6b70f47f76199b8cd35aae0cda15d449', '3', '功能测试', 3, 'functest', NULL, 'tool/functest/index', 'tool:functest:main', 'example', '0', '0', '2', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('74456b2c60b6e0b5edd5559a8e0cc108', '106', '刷新参数缓存', 6, NULL, NULL, NULL, 'system:config:refreshCache', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('7a5af154f7d305797b56b9488207b04a', '118', '配置管理', 9, NULL, NULL, NULL, 'monitor:ossConig:list', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('7aea821f10adc345dc4a003170bb7f16', 'd5d81e35c103ed1dac93a70b97819877', '限制删除', 4, NULL, NULL, NULL, 'monitor:apiLimit:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('85d0cbd4e4441e09f5df53214acddad9', '42708c34ab41a691ef032ab1f4cf5db0', '立即执行一次', 7, NULL, NULL, NULL, 'monitor:job:run', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('8833e71489eb0dfeb36d0769573408bd', '42708c34ab41a691ef032ab1f4cf5db0', '定时任务日志导出', 9, NULL, NULL, NULL, 'monitor:jobLog:export', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('928015b29ffdad7c6621f3b92ba85c6c', '63854784d5ac52bfd2ac578a48f0a46f', '缓存监控', 3, 'cache', NULL, 'monitor/cache/index', 'monitor:cache:list', 'redis', '0', '0', '2', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('937f0292079a4b62d9a26ebd5e8d57ed', '6b70f47f76199b8cd35aae0cda15d449', '发送短信', 2, NULL, NULL, NULL, 'tool:functest:sendSms', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('9901', '105', '刷新字典缓存', 6, '#', NULL, NULL, 'system:dict:refreshCache', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('adec0c7b9d6b37672dcb6b1315f8f4eb', '42708c34ab41a691ef032ab1f4cf5db0', '定时任务日志查询', 10, NULL, NULL, NULL, 'monitor:jobLog:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('b87a985b4ef717afe650f2df9cdbe087', '118', '配置删除', 7, NULL, NULL, NULL, 'monitor:ossConig:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('c0a98c5b6e56602800968efcacfa4e47', '6b70f47f76199b8cd35aae0cda15d449', '发送邮件', 1, NULL, NULL, NULL, 'tool:functest:sendEmail', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('c6e5f363977c052a6a6d6098b0736c8e', '42708c34ab41a691ef032ab1f4cf5db0', '定时任务日志删除', 11, NULL, NULL, NULL, 'monitor:jobLog:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('d5d81e35c103ed1dac93a70b97819877', '2', '接口限制', 4, 'apiLimit', NULL, 'monitor/apiLimit/index', 'monitor:apiLimit:list', 'lock', '0', '0', '2', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('ea70f3a842730a50686acc46fb981800', '42708c34ab41a691ef032ab1f4cf5db0', '定时任务日志列表', 8, NULL, NULL, NULL, 'monitor:jobLog:list', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO `sys_menu` VALUES ('f37ba75d3347c084b50727ff35a386fa', 'd5d81e35c103ed1dac93a70b97819877', '限制状态修改', 5, NULL, NULL, NULL, 'monitor:apiLimit:changeStatus', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `type` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型，1=通知，2=公告',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态，0=正常，1=关闭',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除，0=正常，1=删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES ('5f1d37b8a574402ff9ebfa9467020f82', '学思想、强党性、重实践、建新功', '<p><a href=\"https://www.baidu.com/\" rel=\"noopener noreferrer\" target=\"_blank\"><img src=\"https://mbdp01.bdstatic.com/static/landing-pc/img/logo_top.79fdb8c2.png\" alt=\"到百度首页\"></a></p><p><br></p><p><a href=\"https://www.baidu.com/\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(0, 0, 0);\">百度首页</a></p><p><a href=\"https://passport.baidu.com/v2/?login&amp;tpl=mn&amp;u=https%3A%2F%2Fbaijiahao.baidu.com%2Fs%3Fid%3D1763629368639186117\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"background-color: rgb(78, 113, 242); color: rgb(255, 255, 255);\">登录</a></p><p>学思想、强党性、重实践、建新功｜汲取奋发进取的智慧和力量——各地认真推动学习贯彻习近平新时代中国特色社会主义思想主题教育走深走实</p><p><a href=\"https://author.baidu.com/home?from=bjh_article&amp;app_id=1537196318595058\" rel=\"noopener noreferrer\" target=\"_blank\"><img src=\"https://gips0.baidu.com/it/u=219222052,3378137952&amp;fm=3012&amp;app=3012&amp;autime=1681354058&amp;size=b200,200\"></a></p><p><a href=\"https://author.baidu.com/home?from=bjh_article&amp;app_id=1537196318595058\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(0, 0, 0);\">新华网</a></p><p>2023-04-20 02:13</p><p>新华网官方帐号</p><p class=\"ql-align-center\"><span style=\"color: rgb(255, 255, 255);\">关注</span></p><p>新华社北京4月19日电&nbsp;<strong style=\"color: rgb(51, 51, 51);\">题：汲取奋发进取的智慧和力量——各地认真推动学习贯彻习近平新时代中国特色社会主义思想主题教育走深走实</strong></p><p>新华社记者</p><p>学习贯彻习近平新时代中国特色社会主义思想主题教育工作会议4月3日在北京召开后，各地认真学习领会习近平总书记重要讲话精神，坚持把开展主题教育同贯彻落实党中央各项决策部署结合起来，同推动本地区本部门本单位的中心工作结合起来，认真推动主题教育走深走实。</p><p>近日，北京市召开学习贯彻习近平新时代中国特色社会主义思想主题教育工作会议强调，以严谨务实的工作作风和昂扬奋进的精神状态，坚持首善标准，高质量开展好主题教育。“牢记‘看北京首先要从政治上看’的要求，自觉把‘两个确立’融入血脉、见之行动，为党中央站好岗、放好哨。通过主题教育，我们要努力让新时代‘枫桥经验’不断结出累累硕果。”北京市公安局相关负责人说。</p><p>为推动主题教育走深走实，重庆在制定实施方案时抓纲带目，突出“五个一”，即贯穿一条党的创新理论武装的主线，建立一套迭代升级清单，实施一系列发展攻坚行动，健全一个推动落实体系，推广一批学习运用党的创新理论推动实践的先进典型。西部陆海新通道建设是重庆“实施一系列发展攻坚行动”的重要内容，下一步当地将努力把物流网络拓展到更多国家和地区，加快发展通道经济，切实做到学思想、强党性、重实践、建新功。</p><p>黑龙江紧扣振兴发展和现代化强省建设实际，制定出台全省主题教育实施方案和第一批工作方案、大兴调查研究实施方案、“牢记嘱托、全面振兴”系列微党课工作方案、省委常委班子工作方案等4个配套方案。目前，黑龙江全省第一批单位领导班子成员已确定调研课题831个。</p><p>4月10日，天津主题教育动员部署会召开，强调市委常委班子带头推动包括京津冀协同发展走深走实、科教兴市人才强市、制造业高质量发展等“十项行动”，通过主题教育进一步把准行动方向、细化行动路径、提升行动效果。</p><p>主题教育是一件事关全局的大事，时间紧、任务重、要求高，必须谋划好、组织好、落实好。为加强对所属地区、部门和单位的督促指导，各省区市党委和行业系统主管部门党组（党委）派出巡回指导组。</p><p>在广西，按照政治过硬、能力过硬、作风过硬和党内集中教育工作经验丰富的标准，从全自治区抽调的113名同志组成16个巡回指导组。截至4月18日，指导组已指导85家单位召开工作会议，全区主题教育第一批186家单位将于4月20日前全部完成启动。广西壮族自治区党委主题教育领导小组办公室副主任莫诗浦表示，巡回指导工作将突出精准指导，坚持同题共答，用好正反典型，督在点子上、导在关键处，确保主题教育取得实效。</p><p>开展主题教育以来，福建省直机关掀起读原著、学原文、悟原理的又一轮热潮。福建省公安厅有关负责人介绍，省公安厅组织党员干部认真研读主题教育指定学习书目和福建省特色学习书目，将开展主题教育与学习贯彻党的二十大精神、大兴调查研究之风有机结合，奋力推进福建公安工作现代化。</p><p>坚持多思多想、学深悟透，在新疆，自治区党委常委会制定开展主题教育的工作方案及理论学习、调查研究、检视问题、整改落实等方案，做到“规定动作”一个不少、“自选动作”务实管用。自治区政协办公厅会议活动处处长唐志荣表示，要弘扬优良学风，抓好支部学习，把学习贯彻习近平新时代中国特色社会主义思想主题教育成效体现在工作实践中。</p><p class=\"ql-align-right\"><span style=\"color: rgb(78, 110, 242);\">举报/反馈</span></p><p>大家都在搜</p><p><a href=\"https://baidu.com/s?word=%E5%A2%9E%E5%BC%BA%E7%94%A8%E5%85%9A%E7%9A%84%E4%BB%80%E4%B9%88%E6%94%BF%E6%B2%BB%E8%A7%89%E6%82%9F&amp;rsv_dl=feed_landingpage_rs&amp;from=1020853i&amp;rsf=\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"background-color: rgb(245, 245, 245); color: rgb(51, 51, 51);\">增强用党的什么政治觉悟</a><a href=\"https://baidu.com/s?word=%E5%8A%A0%E5%BC%BA%E6%80%9D%E6%83%B3%E6%94%B9%E9%80%A0%2C%E6%8F%90%E9%AB%98%E5%85%9A%E6%80%A7%E4%BF%AE%E5%85%BB&amp;rsv_dl=feed_landingpage_rs&amp;from=1020853i&amp;rsf=\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"background-color: rgb(245, 245, 245); color: rgb(51, 51, 51);\">加强思想改造,提高党性修养</a><a href=\"https://baidu.com/s?word=%E6%8A%93%E6%80%9D%E6%83%B3%E5%BB%BA%E8%AE%BE%2C%E6%8F%90%E5%8D%87&amp;rsv_dl=feed_landingpage_rs&amp;from=1020853i&amp;rsf=\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"background-color: rgb(245, 245, 245); color: rgb(51, 51, 51);\">抓思想建设,提升</a><a href=\"https://baidu.com/s?word=%E5%A2%9E%E5%BC%BA%2C%E5%9D%9A%E5%AE%9A%2C%E5%81%9A%E5%88%B0%2C%E5%A2%9E%E5%BC%BA%E5%85%9A%E6%80%A7%E6%8F%90%E9%AB%98%E7%B4%A0%E8%B4%A8&amp;rsv_dl=feed_landingpage_rs&amp;from=1020853i&amp;rsf=\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"background-color: rgb(245, 245, 245); color: rgb(51, 51, 51);\">增强,坚定,做到,增强党性提高素质</a><a href=\"https://baidu.com/s?word=%E5%85%9A%E6%80%A7%E5%BE%97%E5%88%B0%E4%BA%86%E5%8D%87%E5%8D%8E%2C%E6%80%9D%E6%83%B3%E5%BE%97%E5%88%B0%E6%8F%90%E5%8D%87&amp;rsv_dl=feed_landingpage_rs&amp;from=1020853i&amp;rsf=\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"background-color: rgb(245, 245, 245); color: rgb(51, 51, 51);\">党性得到了升华,思想得到提升</a><a href=\"https://baidu.com/s?word=%E4%B8%8D%E6%96%AD%E9%94%A4%E7%82%BC%E5%85%9A%E6%80%A7%E4%BF%AE%E5%85%BB%E6%8F%90%E5%8D%87%E6%94%BF%E6%B2%BB%E7%B4%A0%E5%85%BB&amp;rsv_dl=feed_landingpage_rs&amp;from=1020853i&amp;rsf=\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"background-color: rgb(245, 245, 245); color: rgb(51, 51, 51);\">不断锤炼党性修养提升政治素养</a></p><h2>发表评论</h2><p><br></p><p><span style=\"background-color: rgb(78, 110, 242); color: rgb(255, 255, 255);\">发表</span></p><p><br></p><h2>作者最新文章</h2><p><a href=\"https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9399559918850015222%22%7D&amp;n_type=1&amp;p_from=3\" rel=\"noopener noreferrer\" target=\"_blank\"><img src=\"https://t11.baidu.com/it/app=106&amp;f=JPEG&amp;fm=30&amp;fmt=auto&amp;u=3293015186%2C202157797?w=312&amp;h=208&amp;s=CC7220D7D99FF0C846741A6C0300B070\"></a></p><p><a href=\"https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9399559918850015222%22%7D&amp;n_type=1&amp;p_from=3\" rel=\"noopener noreferrer\" target=\"_blank\">新华调查：“答非所问”“妨碍维权”……部分“智能客服”不智能现象咋破解？</a></p><p><span style=\"color: rgb(145, 149, 163);\">2分钟前</span></p><p><span style=\"color: rgb(145, 149, 163);\">1阅读</span><a href=\"https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9694995450663559217%22%7D&amp;n_type=1&amp;p_from=3\" rel=\"noopener noreferrer\" target=\"_blank\"><img src=\"https://t10.baidu.com/it/app=106&amp;f=JPEG&amp;fm=30&amp;fmt=auto&amp;u=1425908862%2C202157115?w=312&amp;h=208&amp;s=F4E866F94EF2CEC01234663E03005056\"></a></p><p><a href=\"https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9694995450663559217%22%7D&amp;n_type=1&amp;p_from=3\" rel=\"noopener noreferrer\" target=\"_blank\">谷雨时节农事忙</a></p><p><span style=\"color: rgb(145, 149, 163);\">14分钟前</span></p><p><span style=\"color: rgb(145, 149, 163);\">4阅读</span><a href=\"https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9538110341145820946%22%7D&amp;n_type=1&amp;p_from=3\" rel=\"noopener noreferrer\" target=\"_blank\"><img src=\"https://t10.baidu.com/it/app=106&amp;f=JPEG&amp;fm=30&amp;fmt=auto&amp;u=148978184%2C202157038?w=312&amp;h=208&amp;s=D89B38D606B1967D02E3EF810300E08C\"></a></p><p><a href=\"https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9538110341145820946%22%7D&amp;n_type=1&amp;p_from=3\" rel=\"noopener noreferrer\" target=\"_blank\">新华全媒+｜加强国际合作 打通“最后一公里”——博鳌亚洲论坛健康产业国际论坛分论坛嘉宾共话消灭脊髓灰质炎</a></p><p><span style=\"color: rgb(145, 149, 163);\">14分钟前</span></p><p><span style=\"color: rgb(145, 149, 163);\">12阅读</span></p><h2>相关推荐</h2><p><br></p><p><a href=\"https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9933733552797237279%22%7D&amp;n_type=1&amp;p_from=4\" rel=\"noopener noreferrer\" target=\"_blank\"><img src=\"https://t11.baidu.com/it/app=106&amp;f=JPEG&amp;fm=30&amp;fmt=auto&amp;u=4172481808%2C202136968?w=312&amp;h=208&amp;s=890060DD0433098809A5A810030010D3\"></a></p><p><a href=\"https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9933733552797237279%22%7D&amp;n_type=1&amp;p_from=4\" rel=\"noopener noreferrer\" target=\"_blank\">星火小学：赓续革命先烈精神，筑牢国家安全意识</a></p><p><a href=\"https://author.baidu.com/home?from=bjh_article&amp;app_id=1626433114323248\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(145, 149, 163);\">湖南日报</a></p><p><a href=\"https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9546340671064833618%22%7D&amp;n_type=1&amp;p_from=4\" rel=\"noopener noreferrer\" target=\"_blank\"><img src=\"https://t11.baidu.com/it/app=106&amp;f=JPEG&amp;fm=30&amp;fmt=auto&amp;u=2984651613%2C202135007?w=312&amp;h=208&amp;s=FF10698E646618AC76106C9F0300C082\"></a></p><p><a href=\"https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9546340671064833618%22%7D&amp;n_type=1&amp;p_from=4\" rel=\"noopener noreferrer\" target=\"_blank\">后冬奥时代持续发力 未来4个赛季北京将办11项国际滑联赛事</a></p><p><a href=\"https://author.baidu.com/home?from=bjh_article&amp;app_id=1601149438053974\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(145, 149, 163);\">北京日报客户端</a></p><p><a href=\"https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9425631998140838769%22%7D&amp;n_type=1&amp;p_from=4\" rel=\"noopener noreferrer\" target=\"_blank\"><img src=\"https://t12.baidu.com/it/app=106&amp;f=JPEG&amp;fm=30&amp;fmt=auto&amp;u=1940926112%2C202126133?w=312&amp;h=208&amp;s=5F9436C01053C3DE14B135590300D0D0\"></a></p><p><a href=\"https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9425631998140838769%22%7D&amp;n_type=1&amp;p_from=4\" rel=\"noopener noreferrer\" target=\"_blank\">乡村振兴的“京山样板”：打造“万企兴万村”试验田</a></p><p><a href=\"https://author.baidu.com/home?from=bjh_article&amp;app_id=1563462235469851\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(145, 149, 163);\">光明网</a></p><p><a href=\"https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9551396633547016984%22%7D&amp;n_type=1&amp;p_from=4\" rel=\"noopener noreferrer\" target=\"_blank\"><img src=\"https://t12.baidu.com/it/app=106&amp;f=JPEG&amp;fm=30&amp;fmt=auto&amp;u=2434219870%2C202119048?w=312&amp;h=208&amp;s=5DA582511FE3C14756B5A4C90300E0B3\"></a></p><p><a href=\"https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9551396633547016984%22%7D&amp;n_type=1&amp;p_from=4\" rel=\"noopener noreferrer\" target=\"_blank\">三穗县桐林镇鹿洞中心村列入州级乡村振兴示范点名单</a></p><p><a href=\"https://author.baidu.com/home?from=bjh_article&amp;app_id=1618362010206479\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(145, 149, 163);\">天眼新闻</a></p><p><a href=\"https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_10340493477270232986%22%7D&amp;n_type=1&amp;p_from=4\" rel=\"noopener noreferrer\" target=\"_blank\"><img src=\"https://t11.baidu.com/it/app=106&amp;f=JPEG&amp;fm=30&amp;fmt=auto&amp;u=2128214718%2C202119104?w=312&amp;h=208&amp;s=94915F94C327E2ED0485C159030030E6\"></a></p><p><a href=\"https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_10340493477270232986%22%7D&amp;n_type=1&amp;p_from=4\" rel=\"noopener noreferrer\" target=\"_blank\">联合国中文日｜在谷雨这天，感受中文之美</a></p><p><a href=\"https://author.baidu.com/home?from=bjh_article&amp;app_id=1563462235469851\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(145, 149, 163);\">光明网</a></p><p><a href=\"https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_10340493477270232986%22%7D&amp;n_type=1&amp;p_from=4\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(145, 149, 163);\">1评论</a></p><p><span style=\"color: rgb(98, 102, 117);\">换一换</span></p><ul><li><span style=\"color: rgb(254, 45, 70);\">1</span></li><li><a href=\"https://www.baidu.com/s?wd=%E8%A2%AB%E2%80%9C%E9%A9%AF%E6%9C%8D%E2%80%9D%E7%9A%84%E5%8E%85%E5%AE%98%E6%B2%A6%E4%B8%BA%E2%80%9C%E7%83%9F%E9%85%92%E8%80%81%E6%9D%BF%E2%80%9D&amp;sa=fyb_news_feedpc&amp;rsv_dl=fyb_news_feedpc&amp;from=feedpc\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(51, 51, 51);\">被“驯服”的厅官沦为“烟酒老板”</a></li><li><span style=\"background-color: rgb(255, 102, 0); color: rgb(255, 255, 255);\">热</span></li><li><span style=\"color: rgb(255, 102, 0);\">2</span></li><li><a href=\"https://www.baidu.com/s?wd=%E5%A5%B3%E5%AD%90%E5%9C%A8mini%E5%B1%95%E5%8F%B0%E7%9B%B4%E6%92%AD%E8%B5%B7%E5%86%B2%E7%AA%81+%E8%AD%A6%E6%96%B9%E5%9B%9E%E5%BA%94&amp;sa=fyb_news_feedpc&amp;rsv_dl=fyb_news_feedpc&amp;from=feedpc\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(51, 51, 51);\">女子在mini展台直播起冲突 警方回应</a></li><li><span style=\"color: rgb(250, 169, 14);\">3</span></li><li><a href=\"https://www.baidu.com/s?wd=%E5%85%A8%E5%9B%BD%E7%B4%AF%E8%AE%A1%E8%B6%8510%E4%BA%BF%E4%BA%A9%E7%9A%84%E9%AB%98%E6%A0%87%E5%87%86%E5%86%9C%E7%94%B0&amp;sa=fyb_news_feedpc&amp;rsv_dl=fyb_news_feedpc&amp;from=feedpc\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(51, 51, 51);\">全国累计超10亿亩的高标准农田</a></li><li><span style=\"color: rgb(145, 149, 163);\">4</span></li><li><a href=\"https://www.baidu.com/s?wd=SpaceX%E6%98%9F%E8%88%B0%E5%8F%91%E5%B0%84%E5%A4%B1%E8%B4%A5&amp;sa=fyb_news_feedpc&amp;rsv_dl=fyb_news_feedpc&amp;from=feedpc\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(51, 51, 51);\">SpaceX星舰发射失败</a></li><li><span style=\"background-color: rgb(214, 26, 110); color: rgb(255, 255, 255);\">爆</span></li><li><span style=\"color: rgb(145, 149, 163);\">5</span></li><li><a href=\"https://www.baidu.com/s?wd=48%E5%B2%81%E5%86%BB%E9%BE%84%E5%A6%88%E5%A6%88%E5%9B%9E%E5%BA%94%E6%95%B4%E5%AE%B9%E8%B4%A8%E7%96%91&amp;sa=fyb_news_feedpc&amp;rsv_dl=fyb_news_feedpc&amp;from=feedpc\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(51, 51, 51);\">48岁冻龄妈妈回应整容质疑</a></li><li><span style=\"color: rgb(145, 149, 163);\">6</span></li><li><a href=\"https://www.baidu.com/s?wd=%E5%86%B0%E6%B7%87%E6%B7%8B%E5%8F%AA%E7%BB%99%E5%A4%96%E5%9B%BD%E4%BA%BA%EF%BC%9F%E5%AE%9D%E9%A9%ACmini%E9%81%93%E6%AD%89&amp;sa=fyb_news_feedpc&amp;rsv_dl=fyb_news_feedpc&amp;from=feedpc\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(51, 51, 51);\">冰淇淋只给外国人？宝马mini道歉</a></li><li><span style=\"background-color: rgb(255, 102, 0); color: rgb(255, 255, 255);\">热</span></li><li><span style=\"color: rgb(145, 149, 163);\">7</span></li><li><a href=\"https://www.baidu.com/s?wd=%E5%9B%A0%E5%A7%93%E6%B0%8F%E5%A4%AA%E7%BD%95%E8%A7%81+%E5%85%A8%E6%9D%91%E9%9B%86%E4%BD%93%E6%94%B9%E5%A7%93%E2%80%9C%E9%B8%AD%E2%80%9D&amp;sa=fyb_news_feedpc&amp;rsv_dl=fyb_news_feedpc&amp;from=feedpc\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(51, 51, 51);\">因姓氏太罕见 全村集体改姓“鸭”</a></li><li><span style=\"color: rgb(145, 149, 163);\">8</span></li><li><a href=\"https://www.baidu.com/s?wd=%E4%BB%8A%E5%B9%B4%E7%AC%AC1%E5%8F%B7%E5%8F%B0%E9%A3%8E%E2%80%9C%E7%8F%8A%E7%91%9A%E2%80%9D%E7%94%9F%E6%88%90&amp;sa=fyb_news_feedpc&amp;rsv_dl=fyb_news_feedpc&amp;from=feedpc\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(51, 51, 51);\">今年第1号台风“珊瑚”生成</a></li><li><span style=\"color: rgb(145, 149, 163);\">9</span></li><li><a href=\"https://www.baidu.com/s?wd=%E5%8F%B0%E5%8C%97101%E5%A4%A7%E6%A5%BC%E9%81%AD%E9%9B%B7%E5%87%BB%E7%9E%AC%E9%97%B4%E6%9B%9D%E5%85%89&amp;sa=fyb_news_feedpc&amp;rsv_dl=fyb_news_feedpc&amp;from=feedpc\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(51, 51, 51);\">台北101大楼遭雷击瞬间曝光</a></li><li><span style=\"color: rgb(145, 149, 163);\">10</span></li><li><a href=\"https://www.baidu.com/s?wd=%E3%80%8A%E7%81%8C%E7%AF%AE%E9%AB%98%E6%89%8B%E3%80%8B%E7%83%AD%E6%98%A0+%E4%BD%A0%E4%B8%BA%E9%9D%92%E6%98%A5%E8%A1%A5%E7%A5%A8%E5%90%97&amp;sa=fyb_news_feedpc&amp;rsv_dl=fyb_news_feedpc&amp;from=feedpc\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(51, 51, 51);\">《灌篮高手》热映 你为青春补票吗</a></li></ul><p><a href=\"https://www.baidu.com/cache/sethelp/index.html\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(153, 153, 153);\">设为首页</a></p><p>©&nbsp;Baidu&nbsp;</p><p><a href=\"https://www.baidu.com/duty/\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(153, 153, 153);\">使用百度前必读</a></p><p><a href=\"http://jianyi.baidu.com/\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(153, 153, 153);\">意见反馈</a></p><p>京ICP证030173号&nbsp;<img src=\"https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/copy_rignt_8.png\" height=\"16\" width=\"13\"><a href=\"http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=11000002000001\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(153, 153, 153);\">京公网安备11000002000001号</a></p>', '1', '0', NULL, '1', '2023-04-20 22:42:24', '1', '2023-04-20 22:42:24', 0);
INSERT INTO `sys_notice` VALUES ('5f98161ae8c06f99fbdcbb267908da58', '快讯！外媒：美国“星舰”发射时发生爆炸', '<p>【环球网快讯】据英国《每日电讯报》刚刚消息，由美国太空探索技术公司（SpaceX）研发的最新一代运载火箭系统“星舰”当时时间20日再次发射，但在发射三分钟后，“超重型推进器”部分似乎未能分离，在高空发生爆炸。</p><p><br></p><p>SpaceX官网介绍称，“星舰”火箭系统第一级助推器被称为“超重型推进器”，第二级被称为“星舰”飞船。其中“超重型推进器”高达70米，直径9米，配置33台“猛禽”发动机，使用液态甲烷/液氧推进剂，安装4个固定栅格翼，不配置着陆腿，SpaceX公司计划采用地面塔架“捕捉”的形式进行回收。</p>', '2', '1', NULL, '1', '2023-04-20 22:43:09', '1', '2023-04-20 22:43:25', 0);

-- ----------------------------
-- Table structure for sys_oss_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss_config`;
CREATE TABLE `sys_oss_config`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置名称，例如：minio、阿里云、腾讯云、七牛云、京东云、华为云',
  `access_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'accessKey',
  `secret_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'secretKey',
  `bucket_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '存储桶名称',
  `prefix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '前缀',
  `endpoint` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '访问站点endpoint',
  `domain` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '自定义域名',
  `is_https` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否https，0=否，1=是',
  `region` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '地域',
  `access_policy` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '存储桶权限类型，0=private，1=public，2=custom',
  `in_use` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '正在使用，0=否，1=是（在所有数据中，只有一条数据可是正在使用）',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置状态，0=正常，1=禁用',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除，0=正常，1=删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'OSS配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oss_config
-- ----------------------------
INSERT INTO `sys_oss_config` VALUES ('02a4c0e40c271fd516ea3ea566ade22e', '七牛云', '1', '13m', 'my-dev-test', 'MyAdmin', 's3-cn-north-1.qiniucs.com', 'qiniu.daenx.cn', '1', 'cn-north-1', '1', '0', '0', NULL, '1', '2023-04-13 22:38:26', '1', '2023-09-19 14:34:46', 0);
INSERT INTO `sys_oss_config` VALUES ('3c59bfcc59b182930fe2d9c41246c50a', 'minio', '2', '2', 'test', 'MyAdmin', '127.0.0.1:9000', '', '0', '', '1', '1', '0', NULL, '1', '2023-04-16 17:03:47', '1', '2023-09-19 14:34:46', 0);
INSERT INTO `sys_oss_config` VALUES ('k8jowsd604opw17q6hs8gze05s2ybpqr', '腾讯云', '3', '3', 'daen-1251663445', 'MyAdmin', 'cos.ap-nanjing.myqcloud.com', '', '0', 'ap-nanjing', '1', '0', '0', NULL, '1', '2023-04-16 20:06:31', '1', '2023-09-19 14:34:46', 0);

-- ----------------------------
-- Table structure for sys_position
-- ----------------------------
DROP TABLE IF EXISTS `sys_position`;
CREATE TABLE `sys_position`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '岗位名称',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '岗位编码',
  `summary` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '岗位简介',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '岗位状态，0=正常，1=禁用',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除，0=正常，1=删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '岗位表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_position
-- ----------------------------
INSERT INTO `sys_position` VALUES ('1', '董事长', 'ceo', '负责公司的发展等', 1, '0', NULL, '1', '2023-04-19 22:24:04', '1', '2023-04-19 22:24:04', 0);
INSERT INTO `sys_position` VALUES ('2', '项目经理', 'se', NULL, 2, '0', NULL, '1', '2023-04-16 23:55:15', '1', '2023-04-16 23:55:15', 0);
INSERT INTO `sys_position` VALUES ('3', '人力资源', 'hr', NULL, 3, '0', NULL, '1', '2023-03-29 10:38:22', '1', '2023-03-29 10:38:22', 0);
INSERT INTO `sys_position` VALUES ('4', '普通员工', 'user', NULL, 4, '0', NULL, '1', '2023-04-19 22:23:37', '1', '2023-04-19 22:23:37', 0);

-- ----------------------------
-- Table structure for sys_position_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_position_user`;
CREATE TABLE `sys_position_user`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `position_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '岗位ID',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '岗位用户关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_position_user
-- ----------------------------
INSERT INTO `sys_position_user` VALUES ('1', '1', '1');
INSERT INTO `sys_position_user` VALUES ('1a31ee7fd56906a5ca54936771ff9e74', '3', '2');
INSERT INTO `sys_position_user` VALUES ('2', '3', '1');
INSERT INTO `sys_position_user` VALUES ('217b12c2ee17d54d1e1915d104fbe013', '4', '2');
INSERT INTO `sys_position_user` VALUES ('42e86a19ad715b820d5c8cf7b3b72903', '4', '1650855280223846402');
INSERT INTO `sys_position_user` VALUES ('517d21035a2a98a4f700750784f6a484', '4', '1640613543627415553');
INSERT INTO `sys_position_user` VALUES ('ca85f7ed198da507a18615493bcb88ae', '4', '1657562040456413185');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `data_scope` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '数据权限，0=本人数据，1=本部门数据，2=本部门及以下数据，3=全部数据，4=自定义权限',
  `menu_check_strictly` int NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）',
  `dept_check_strictly` int NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色状态，0=正常，1=禁用',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除，0=正常，1=删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'admin', 1, '3', 1, 1, '0', NULL, '1', '2023-03-31 21:04:06', '1', '2023-03-31 21:04:09', 0);
INSERT INTO `sys_role` VALUES ('2', '普通用户', 'user', 2, '2', 1, 1, '0', NULL, '1', '2023-03-31 21:04:08', '1', '2023-10-08 13:59:37', 0);

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色ID',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('02532770ccd6d4674bb8edc7c5eb9313', '2', '1605');
INSERT INTO `sys_role_menu` VALUES ('02c1629ad47aa15581fe50628b748be8', '2', '1023');
INSERT INTO `sys_role_menu` VALUES ('05681709ae0a6277141cfecd66548c9b', '2', '1022');
INSERT INTO `sys_role_menu` VALUES ('072864f618c9245150723c87ef3b7a96', '2', '104');
INSERT INTO `sys_role_menu` VALUES ('0ae458975646c8b6b3a0f8936c26c017', '2', '1019');
INSERT INTO `sys_role_menu` VALUES ('0d3d29c0a13fb294438b5285595cb935', '2', '1509');
INSERT INTO `sys_role_menu` VALUES ('0de5e4dab5780f287106c85d1d9d94d0', '2', '1016');
INSERT INTO `sys_role_menu` VALUES ('0fe8f35fef314e9bc77cceed72fc392d', '2', '1001');
INSERT INTO `sys_role_menu` VALUES ('1279ce6c507ab0609ad3ab8d86a4c76f', '2', '1507');
INSERT INTO `sys_role_menu` VALUES ('1343a72052259fc2181592055d8bc36f', '2', '1502');
INSERT INTO `sys_role_menu` VALUES ('13fc0858a7c43bd8a7894fa4690e576e', '2', '1004');
INSERT INTO `sys_role_menu` VALUES ('159ab171347c52f6720f472a8fe14769', '2', 'c0a98c5b6e56602800968efcacfa4e47');
INSERT INTO `sys_role_menu` VALUES ('1984b41de1e4c16684cf312a4d67bfdd', '2', '1505');
INSERT INTO `sys_role_menu` VALUES ('1b59f634f74ec25b34088669c8ad2e15', '2', '1014');
INSERT INTO `sys_role_menu` VALUES ('1f0f876008c2d0050d385afaf71f2e26', '2', '1041');
INSERT INTO `sys_role_menu` VALUES ('23cb195adbb0e40526aafa2183041fcc', '2', '1033');
INSERT INTO `sys_role_menu` VALUES ('2a0a3b8ba54fd4549da5a305451e8325', '2', '1026');
INSERT INTO `sys_role_menu` VALUES ('3491cebddd5f17a7c7a3e7967a7eef52', '2', '1008');
INSERT INTO `sys_role_menu` VALUES ('35724a5193a93ae38c01c8036a475e3a', '2', '1034');
INSERT INTO `sys_role_menu` VALUES ('381e0eb0105cb87b28513fd9b1d1a8f6', '2', 'b87a985b4ef717afe650f2df9cdbe087');
INSERT INTO `sys_role_menu` VALUES ('40c76cd64847d343620f728c7de093a1', '2', '1042');
INSERT INTO `sys_role_menu` VALUES ('45e4e05039dc4803b2f09d2f4b237e65', '2', '1039');
INSERT INTO `sys_role_menu` VALUES ('47e159c0671320cd28fc223de5a35350', '2', '1005');
INSERT INTO `sys_role_menu` VALUES ('49632ef6f1c49338a1c973ffe666f8a4', '2', '1013');
INSERT INTO `sys_role_menu` VALUES ('5253bc5e029a57815e4a9be9d55ccd8b', '2', '103');
INSERT INTO `sys_role_menu` VALUES ('582ca7ebc079ee8c1e985670f3fbeadd', '2', '1010');
INSERT INTO `sys_role_menu` VALUES ('58f3bf9ae7283057f32c79bdfabfcb42', '2', '1012');
INSERT INTO `sys_role_menu` VALUES ('66b1bacb4157504b1f1cbb8399ae550e', '2', '69519bae5ffa67623e8bfb1c8fabdd00');
INSERT INTO `sys_role_menu` VALUES ('66c9561f0efe227df1cb1c70200483d8', '2', '1035');
INSERT INTO `sys_role_menu` VALUES ('678f3fedb1e83ee62166c68bb60b31bd', '2', '501');
INSERT INTO `sys_role_menu` VALUES ('6a6e0b54f9b90b7d63eec2f81d3497b6', '2', '118');
INSERT INTO `sys_role_menu` VALUES ('6bb16ffcd49a25b456959fbcd7aa8fdc', '2', '1020');
INSERT INTO `sys_role_menu` VALUES ('6cfa11faa96ea88786129069e2acc338', '2', '1024');
INSERT INTO `sys_role_menu` VALUES ('726fbed93295a7ec80172aaf499b2917', '2', '108');
INSERT INTO `sys_role_menu` VALUES ('7275479ded9905306199ba3c91f3b8f3', '2', '500');
INSERT INTO `sys_role_menu` VALUES ('75d7ff75eb247eb16a85f40cb4180e84', '2', '1031');
INSERT INTO `sys_role_menu` VALUES ('764498a2a36559f573ff3fb546c61dd6', '2', '1030');
INSERT INTO `sys_role_menu` VALUES ('783ad1222af2e38a86b7bf94c51c1484', '2', '1007');
INSERT INTO `sys_role_menu` VALUES ('78ea96f8298a7fcb9c14f2889f8220f6', '2', '1504');
INSERT INTO `sys_role_menu` VALUES ('7ae8ad738e6a903edafedcaaa5dd3454', '2', '105');
INSERT INTO `sys_role_menu` VALUES ('7b767c66175c8d955b528d48435ddda8', '2', '1027');
INSERT INTO `sys_role_menu` VALUES ('7bac765bfb43347ec3c386f6865d79c3', '2', '7a5af154f7d305797b56b9488207b04a');
INSERT INTO `sys_role_menu` VALUES ('7d450cba1fa81eb5552809915117a3e4', '2', '1028');
INSERT INTO `sys_role_menu` VALUES ('7e87cc4e6cd10c98918d6899d183009f', '2', '1029');
INSERT INTO `sys_role_menu` VALUES ('80982125508f13ae48d59fef1b843be0', '2', '937f0292079a4b62d9a26ebd5e8d57ed');
INSERT INTO `sys_role_menu` VALUES ('80efa6c64c96aaf64c9073331088fee7', '2', '1501');
INSERT INTO `sys_role_menu` VALUES ('82f9c480dfd6b6fdead258e6b463ee85', '2', '39cfe621da13b55952f2af58edc3e53a');
INSERT INTO `sys_role_menu` VALUES ('86f7ba881da5a7dbac02564ef938873d', '2', '3');
INSERT INTO `sys_role_menu` VALUES ('8d48959b17281336ad07938a03dfe80a', '2', '1032');
INSERT INTO `sys_role_menu` VALUES ('924b234116306695c481028c80bf4e79', '2', '1601');
INSERT INTO `sys_role_menu` VALUES ('954fafb48de20d7bc0da445b9ee98693', '2', '107');
INSERT INTO `sys_role_menu` VALUES ('960f3f62d3b79816fd515a7ab325a649', '2', '6b70f47f76199b8cd35aae0cda15d449');
INSERT INTO `sys_role_menu` VALUES ('9fcc8d002dc9ba25cd8bddfb8a8dd0e1', '2', '1038');
INSERT INTO `sys_role_menu` VALUES ('a2c3f22381a9af73e3c5f10745d7b84b', '2', '1009');
INSERT INTO `sys_role_menu` VALUES ('a308a18a3bb7be4184a27c22e87f299b', '2', '1510');
INSERT INTO `sys_role_menu` VALUES ('a33bf2a8bafe0ce924b72141b0bbae33', '2', '1040');
INSERT INTO `sys_role_menu` VALUES ('a46e13419004516cb35d906cd99e643a', '2', '1506');
INSERT INTO `sys_role_menu` VALUES ('aac08237779b45345017519497e09db5', '2', '2d562fa236077b42b5142eb869db6b18');
INSERT INTO `sys_role_menu` VALUES ('ab30cec827b16efe9f32bcba3adaf284', '2', '101');
INSERT INTO `sys_role_menu` VALUES ('ad2349ce2c31c19fc7b34a248c477fe2', '2', '1044');
INSERT INTO `sys_role_menu` VALUES ('b0c853cfe0aa1775c86a1cbd05462a2d', '2', '1015');
INSERT INTO `sys_role_menu` VALUES ('b9c1c9918e9b94d1e0b537c0cd62216d', '2', '1017');
INSERT INTO `sys_role_menu` VALUES ('bf2900bac02ae6a1bd097b325f91383b', '2', '63854784d5ac52bfd2ac578a48f0a46f');
INSERT INTO `sys_role_menu` VALUES ('c119334264bac946d2c845f28d8674db', '2', '1043');
INSERT INTO `sys_role_menu` VALUES ('c4095accc7a974d0e1428fb23958e86f', '2', '100');
INSERT INTO `sys_role_menu` VALUES ('c563231dbf7bd1dc3df9e7782183c6bb', '2', '111');
INSERT INTO `sys_role_menu` VALUES ('c6b75b27dc3b3939aa2aa4657d9c124a', '2', '1006');
INSERT INTO `sys_role_menu` VALUES ('c968fce703ae4cab217f573fa794cb7c', '2', '1018');
INSERT INTO `sys_role_menu` VALUES ('caa0fa9f5c0b20254cc6ac91055a34f3', '2', '1011');
INSERT INTO `sys_role_menu` VALUES ('cde845687c7f5d27ed986fd5c522d972', '2', '1021');
INSERT INTO `sys_role_menu` VALUES ('cecbe74d4d49e8859fe349ce09f1274c', '2', '1036');
INSERT INTO `sys_role_menu` VALUES ('cf8d57072c37f52f30e57ffa2730a7b4', '2', '114');
INSERT INTO `sys_role_menu` VALUES ('d2f177889d07883b027d71fcc1a66d2b', '2', '1600');
INSERT INTO `sys_role_menu` VALUES ('d7a363c0ce33adfbd11a9a65f245522e', '2', '5');
INSERT INTO `sys_role_menu` VALUES ('dcc946803a67174fd97301b1ae77279a', '2', '106');
INSERT INTO `sys_role_menu` VALUES ('de2b4ac11823eb63979441811085b437', '2', '1508');
INSERT INTO `sys_role_menu` VALUES ('df55a35ee70b7254cf61f375ca7e2e39', '2', '1045');
INSERT INTO `sys_role_menu` VALUES ('e1e2196a03c24413f5fabd181d25fbaf', '2', '1500');
INSERT INTO `sys_role_menu` VALUES ('e6ff560056cd81f135de19f140fbda70', '2', '102');
INSERT INTO `sys_role_menu` VALUES ('eb8fd0e5f641e2c0c8d6942c47fd2a2e', '2', '1003');
INSERT INTO `sys_role_menu` VALUES ('eef664ac515067f9230e76f8904c7f57', '2', '2');
INSERT INTO `sys_role_menu` VALUES ('f22906140a1d6b8251c1022dcaf71919', '2', '1503');
INSERT INTO `sys_role_menu` VALUES ('f2d1e55123fb5893c3770317eebb69ec', '2', '1037');
INSERT INTO `sys_role_menu` VALUES ('fb828076c6615fff7856861aae5ec75c', '2', '1604');
INSERT INTO `sys_role_menu` VALUES ('fcd76a2ac444d4ce75237aa50b8b397e', '2', '1002');
INSERT INTO `sys_role_menu` VALUES ('fd7c89946f038db52ea9f62d3ace12a9', '2', '1025');
INSERT INTO `sys_role_menu` VALUES ('fee185061de207aba60be1c3c77a016d', '2', '1');

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色ID',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色用户关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES ('174eceae15692e5091c28f3d6a3a1f54', '2', '1650855280223846402');
INSERT INTO `sys_role_user` VALUES ('324278c72420d197e3ddbfaa0ea86d32', '2', '1640613543627415553');
INSERT INTO `sys_role_user` VALUES ('63578445258df9ce8ba9e9b15eb20ca2', '2', '2');
INSERT INTO `sys_role_user` VALUES ('678a429aa947e14570e5077ebcea755a', '1', '1');
INSERT INTO `sys_role_user` VALUES ('84ad78b7cf812e176217fe126ac29298', '2', '1643141570206871554');
INSERT INTO `sys_role_user` VALUES ('e12edec621a0f93ce2a9643d7e79b99e', '2', '1657562040456413185');

-- ----------------------------
-- Table structure for sys_social
-- ----------------------------
DROP TABLE IF EXISTS `sys_social`;
CREATE TABLE `sys_social`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除，0=正常，1=删除',
  `uuid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方系统的唯一ID',
  `source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方用户来源',
  `access_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户的授权令牌',
  `expire_in` int NULL DEFAULT NULL COMMENT '第三方用户的授权令牌的有效期',
  `refresh_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '刷新令牌',
  `open_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方用户的 open id',
  `uid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方用户的 ID',
  `access_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个别平台的授权信息',
  `union_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方用户的 union id',
  `scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方用户授予的权限',
  `token_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个别平台的授权信息',
  `id_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'id token',
  `mac_algorithm` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小米平台用户的附带属性',
  `mac_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小米平台用户的附带属性',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户的授权code',
  `oauth_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Twitter平台用户的附带属性',
  `oauth_token_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Twitter平台用户的附带属性',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '三方Auth绑定表（暂未启用）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_social
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门ID',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户账号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '账号状态，0=正常，1=停用',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户手机号',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `open_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信open_id',
  `api_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开放API key',
  `ban_to_time` datetime NULL DEFAULT NULL COMMENT '锁定结束时间',
  `expire_to_time` datetime NULL DEFAULT NULL COMMENT '到期时间，null则永不过期',
  `user_type` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户类型，具体看字典',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除，0=正常，1=删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '100', 'admin', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '0', '18731054244', '1330166565@qq.com', NULL, NULL, NULL, NULL, '1', '宇宙第一帅的人', '1', '2023-03-01 13:42:39', '1', '2023-05-14 16:31:39', 0);
INSERT INTO `sys_user` VALUES ('1640613543627415553', '101', 'liumm', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '0', NULL, NULL, NULL, NULL, NULL, NULL, '3', NULL, '1', '2023-03-28 15:15:25', '1', '2023-09-22 14:19:05', 0);
INSERT INTO `sys_user` VALUES ('1643141570206871554', '100', 'abcdefg', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '0', NULL, NULL, NULL, NULL, NULL, NULL, '3', NULL, '1', '2023-04-04 14:40:53', '1', '2023-09-22 14:18:23', 0);
INSERT INTO `sys_user` VALUES ('1650855280223846402', '105', 'test01', '678e82d907d3e6e71f81d5cf3ddacc3671dc618c38a1b7a9f9393a83d025b296', '0', NULL, NULL, NULL, NULL, NULL, NULL, '2', NULL, NULL, '2023-04-25 21:32:25', NULL, '2023-04-25 21:32:25', 0);
INSERT INTO `sys_user` VALUES ('1657562040456413185', '105', 'admin22', '15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225', '0', NULL, NULL, NULL, NULL, NULL, NULL, '2', NULL, NULL, '2023-05-14 09:42:41', NULL, '2023-05-14 09:42:41', 0);
INSERT INTO `sys_user` VALUES ('2', '102', 'test', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '0', '18755533444', '555@163.com', '1', '2', '2023-05-10 11:01:47', NULL, '2', '4', '1', '2023-03-01 14:09:35', '1', '2023-09-22 14:19:21', 0);

-- ----------------------------
-- Table structure for sys_user_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_detail`;
CREATE TABLE `sys_user_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联用户ID',
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `real_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `age` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '年龄',
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '2' COMMENT '性别，0=女，1=男，2=未知',
  `profile` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '个人简介',
  `user_sign` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个性签名',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像（文件ID）',
  `money` int NULL DEFAULT 0 COMMENT '账户余额，单位分',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除，0=正常，1=删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_detail
-- ----------------------------
INSERT INTO `sys_user_detail` VALUES ('21366e78e6a156e9cf45061da65d06e5', '1', '我是管理员', 'Daen', '18', '1', '每一次离开都是为了更好的相遇\n你好世界', '不一样的烟花', '625bcbc99e7524b543c81ffaed6dd2ab', 350, NULL, '2023-03-01 13:42:39', '1', '2023-06-26 15:49:37', 0);
INSERT INTO `sys_user_detail` VALUES ('7550e77dfbabea70ec44aaebda2f90ed', '2', '测试用户', '宝贝', '18', '0', 'ε=(´ο｀*)))唉', '生气气1', '', 0, NULL, '2023-03-01 14:09:35', '1', '2023-09-22 14:19:21', 0);
INSERT INTO `sys_user_detail` VALUES ('886c73e7e8f458649f3048f672f042e6', '1650855280223846402', NULL, NULL, '0', '2', NULL, NULL, NULL, 0, NULL, '2023-04-25 21:32:25', NULL, '2023-04-25 21:32:25', 0);
INSERT INTO `sys_user_detail` VALUES ('945acc4f5450533dd672260cfa1d66c9', '1640613543627415553', '刘萌萌', '刘二萌', '18', '0', NULL, NULL, NULL, 85560, '1', '2023-03-28 15:15:25', '1', '2023-09-22 14:19:05', 0);
INSERT INTO `sys_user_detail` VALUES ('ac5182e7b0e1c9ce11e7381e19b4733d', '1657562040456413185', NULL, NULL, '0', '2', NULL, NULL, NULL, 0, NULL, '2023-05-14 09:42:41', NULL, '2023-05-14 09:42:41', 0);
INSERT INTO `sys_user_detail` VALUES ('f6d09bb65ad0ac3d6239214d7e456f8e', '1643141570206871554', '阿萨德', NULL, '0', '0', '2', NULL, NULL, 0, '1', '2023-04-04 14:40:53', '1', '2023-09-22 14:18:23', 0);

-- ----------------------------
-- Table structure for test_data
-- ----------------------------
DROP TABLE IF EXISTS `test_data`;
CREATE TABLE `test_data`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型，0=民生，1=科技',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态，0=正常，1=停用',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除，0=正常，1=删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '测试数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of test_data
-- ----------------------------
INSERT INTO `test_data` VALUES ('3c59c3c11a2d3c5bcfac567dc326fa9b', '阿萨德1', '1', '0', '0', NULL, '1', '2023-09-22 09:36:32', '1', '2023-09-22 10:54:14', 0);
INSERT INTO `test_data` VALUES ('6cbae38f86d25cb80fb34a9892cf01b2', '你好', '22', '0', '0', NULL, '1', '2023-09-22 10:54:59', '1', '2023-09-22 21:38:45', 0);
INSERT INTO `test_data` VALUES ('70e15d15f8390b71ff38de32ec163c99', '我不好', '22', '0', '0', NULL, '1', '2023-09-22 09:10:33', '1', '2023-09-22 21:38:50', 0);
INSERT INTO `test_data` VALUES ('7e5bfc56a3600d717786fbe0cccbfcd5', '哈哈哈哈', '1', '0', '0', NULL, '1', '2023-09-22 09:09:20', '1', '2023-09-22 21:38:56', 0);

-- ----------------------------
-- Table structure for test_data_tree
-- ----------------------------
DROP TABLE IF EXISTS `test_data_tree`;
CREATE TABLE `test_data_tree`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父级ID，顶级为0',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型，0=民生，1=科技',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态，0=正常，1=停用',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除，0=正常，1=删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '测试树表数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of test_data_tree
-- ----------------------------
INSERT INTO `test_data_tree` VALUES ('1', '0', '我1', '阿萨德', '0', '1', '个', '1', '2023-02-10 17:25:11', '1', '2023-04-26 22:16:35', 0);
INSERT INTO `test_data_tree` VALUES ('11', '1', '我3', '3', '0', '0', '导入1', '1', '2023-02-26 17:25:16', '1', '2023-04-26 22:19:18', 0);
INSERT INTO `test_data_tree` VALUES ('2', '0', '我4-', '按时-', '1', '0', '更换-', '2', '2023-01-30 17:25:18', '2', '2023-01-30 17:25:18', 0);
INSERT INTO `test_data_tree` VALUES ('222', '11', '我2', '得过户', '1', '1', '个月', '1', '2023-02-15 17:25:14', '1', '2023-04-26 22:16:10', 0);
INSERT INTO `test_data_tree` VALUES ('b53120f9af52dcdc229d42d8cb04623f', '2', '阿萨德', '阿萨德', '0', '0', NULL, '1', '2023-04-26 22:24:47', '1', '2023-04-26 22:24:47', 0);


SET FOREIGN_KEY_CHECKS = 1;
