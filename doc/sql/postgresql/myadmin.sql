-- ----------------------------
-- Table structure for sys_api_limit
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_api_limit";
CREATE TABLE "public"."sys_api_limit" (
                                          "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                          "api_name" varchar(255) COLLATE "pg_catalog"."default",
                                          "api_uri" varchar(255) COLLATE "pg_catalog"."default",
                                          "single_frequency" int4,
                                          "single_time" int4,
                                          "single_time_unit" varchar(2) COLLATE "pg_catalog"."default",
                                          "whole_frequency" int4,
                                          "whole_time" int4,
                                          "whole_time_unit" varchar(2) COLLATE "pg_catalog"."default",
                                          "limit_type" varchar(2) COLLATE "pg_catalog"."default",
                                          "ret_msg" text COLLATE "pg_catalog"."default",
                                          "status" varchar(2) COLLATE "pg_catalog"."default",
                                          "remark" varchar(255) COLLATE "pg_catalog"."default",
                                          "create_id" varchar(32) COLLATE "pg_catalog"."default",
                                          "create_time" timestamp(6),
                                          "update_id" varchar(32) COLLATE "pg_catalog"."default",
                                          "update_time" timestamp(6),
                                          "is_delete" int4
)
;
COMMENT ON COLUMN "public"."sys_api_limit"."api_name" IS '接口名称';
COMMENT ON COLUMN "public"."sys_api_limit"."api_uri" IS '接口uri';
COMMENT ON COLUMN "public"."sys_api_limit"."single_frequency" IS '单个用户次数';
COMMENT ON COLUMN "public"."sys_api_limit"."single_time" IS '单个用户时间';
COMMENT ON COLUMN "public"."sys_api_limit"."single_time_unit" IS '单个用户时间单位';
COMMENT ON COLUMN "public"."sys_api_limit"."whole_frequency" IS '全部用户次数';
COMMENT ON COLUMN "public"."sys_api_limit"."whole_time" IS '全部用户时间';
COMMENT ON COLUMN "public"."sys_api_limit"."whole_time_unit" IS '全部用户时间单位';
COMMENT ON COLUMN "public"."sys_api_limit"."limit_type" IS '限制类型，0=限流，1=停用';
COMMENT ON COLUMN "public"."sys_api_limit"."ret_msg" IS '停用提示，当限制类型=1时，接口返回的提示内容';
COMMENT ON COLUMN "public"."sys_api_limit"."status" IS '限制状态，0=正常，1=停用';
COMMENT ON COLUMN "public"."sys_api_limit"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_api_limit"."create_id" IS '创建人';
COMMENT ON COLUMN "public"."sys_api_limit"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_api_limit"."update_id" IS '修改人';
COMMENT ON COLUMN "public"."sys_api_limit"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_api_limit"."is_delete" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "public"."sys_api_limit" IS '接口限制';

-- ----------------------------
-- Records of sys_api_limit
-- ----------------------------
INSERT INTO "public"."sys_api_limit" VALUES ('700df1aab01ead0db7ce6105a9be90a5', '测试接口', '/test/test', 10, 30, '0', 20, 30, '0', '0', NULL, '0', '测试', '1', '2023-05-23 15:28:08', '1', '2023-05-24 18:07:46', 0);
INSERT INTO "public"."sys_api_limit" VALUES ('71736a3b981f87f504bc4ae7bfb88008', '签到接口', '/sign', 0, 0, '0', 0, 0, '0', '1', '618期间，签到接口暂时停用', '0', '6月20日恢复', '1', '2023-05-23 15:32:12', '1', '2023-05-23 15:37:30', 0);
INSERT INTO "public"."sys_api_limit" VALUES ('ab6ac1ae29d57e70879e8af885f6f668', '测试单表分页', '/test/data/list3', 5, 30, '0', NULL, NULL, '0', '0', '618期间暂时关闭，6月20日恢复', '0', NULL, '1', '2023-05-24 15:27:49', '1', '2023-05-24 17:53:36', 0);

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_config";
CREATE TABLE "public"."sys_config" (
                                       "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                       "name" varchar(255) COLLATE "pg_catalog"."default",
                                       "key_va" varchar(255) COLLATE "pg_catalog"."default",
                                       "value" text COLLATE "pg_catalog"."default",
                                       "type" varchar(2) COLLATE "pg_catalog"."default",
                                       "status" varchar(2) COLLATE "pg_catalog"."default",
                                       "remark" varchar(255) COLLATE "pg_catalog"."default",
                                       "create_id" varchar(32) COLLATE "pg_catalog"."default",
                                       "create_time" timestamp(6),
                                       "update_id" varchar(32) COLLATE "pg_catalog"."default",
                                       "update_time" timestamp(6),
                                       "is_delete" int4
)
;
COMMENT ON COLUMN "public"."sys_config"."name" IS '参数名称';
COMMENT ON COLUMN "public"."sys_config"."key_va" IS '参数键值';
COMMENT ON COLUMN "public"."sys_config"."value" IS '参数键值';
COMMENT ON COLUMN "public"."sys_config"."type" IS '系统内置，0=否，1=是';
COMMENT ON COLUMN "public"."sys_config"."status" IS '参数状态，0=正常，1=停用';
COMMENT ON COLUMN "public"."sys_config"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_config"."create_id" IS '创建人';
COMMENT ON COLUMN "public"."sys_config"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_config"."update_id" IS '修改人';
COMMENT ON COLUMN "public"."sys_config"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_config"."is_delete" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "public"."sys_config" IS '系统参数';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO "public"."sys_config" VALUES ('0015263fa950c3ce40957fdb3b1e14a7', '系统验证码配置', 'sys.captcha.config', '{
	"config": {
		"type": 0,
		"lock": "true"
	},
	"image": {
		"type": 5,
		"width": 200,
		"height": 100,
		"codeCount": 4,
		"olCount": 10
	},
	"slider": {}
}', '1', '0', '如果删除或者禁用此参数，那么系统将不开启验证码
具体参数说明参考文档', '1', '2023-04-25 20:23:07', '1', '2023-05-19 22:03:25', 0);
INSERT INTO "public"."sys_config" VALUES ('0ce0ea3dc352bb6838e4d008d9e849fc', '系统钉钉配置', 'sys.dingTalk.config', '{
	"testbot": {
		"keywords": "",
		"secret": "SEC48ea902b5b3fb13876666666666666660a18e38bcb607ced5108f71f24a7",
		"accessToken": "e714cfa1e60734d196666666666666665f0d2f68d2fbc0c92b4dd42e57d",
		"remark": "测试群的机器人"
	},
	"xiaobai": {
		"keywords": "【定时任务异常】",
		"secret": "SEC48ea902b5b3fb13876566666666666666640a18e38bcb607ced5108f71f24a7",
		"accessToken": "e714cfa1e60734d19d666666666666666e75f0d2f68d2fbc0c92b4dd42e57d",
		"remark": "闲聊群的机器人"
	}
}', '1', '0', '如果删除或者禁用此参数，那么系统将无法发送钉钉通知
具体参数说明参考文档', '1', '2023-05-13 18:27:24', '1', '2023-05-13 20:40:29', 0);
INSERT INTO "public"."sys_config" VALUES ('0e4d78f5e0601e87489a563f40e30ff5', '图片上传限制策略', 'sys.upload.image', '{
    "limit": 6,
    "fileSize": 4,
    "fileType": ["bmp", "gif", "jpg", "jpeg", "png"],
    "isShowTip": true
}', '1', '0', '如果删除此参数的话，系统将使用默认参数
如果禁用此参数的话，系统 将禁止图片上传
limit=同时上传个数
fileSize=文件最大尺寸，单位MB
fileType=[]，支持的文件类型数组，不包含小数点
isShowTip=是否显示提示', '1', '2023-04-17 22:24:15', '1', '2023-05-14 09:34:24', 0);
INSERT INTO "public"."sys_config" VALUES ('101fbed52418ce72ffe30143c66fdd06', '系统注册默认信息', 'sys.register.default.info', '{
    "userType": "2",
    "deptCodes": ["XMY-JN-3"],
    "positionCodes": ["user"],
    "roleCodes": ["user"]
}', '1', '0', '此参数必存在，不存在的话，系统将禁止注册
userType，用户类型，必填，参考字典内
deptCodes，部门编号，必填，可多个
positionCodes，岗位编码，非必填，可多个
roleCodes，角色编码，必填，可多个', '1', '2023-04-25 21:00:26', '1', '2023-04-25 21:07:58', 0);
INSERT INTO "public"."sys_config" VALUES ('1a91bf36a72d9dcb253a941a94fa28fa', '文件上传限制策略', 'sys.upload.file', '{
    "limit": 5,
    "fileSize": 5,
    "fileType": ["zip", "txt"],
    "isShowTip": true
}', '1', '0', '如果删除此参数的话，系统将使用默认参数
如果禁用此参数的话，系统 将禁止文件上传
limit=同时上传个数
fileSize=文件最大尺寸，单位MB
fileType=[]，支持的文件类型数组，不包含小数点
isShowTip=是否显示提示', '1', '2023-04-17 22:06:58', '1', '2023-04-25 21:00:37', 0);
INSERT INTO "public"."sys_config" VALUES ('1c86983f628ffe1949171b01858d1aa5', '系统登录错误次数限制信息', 'sys.login.fail.info', '{
    "failCount": 5,
    "banSecond": 3600
}', '1', '0', '此参数如果不存在或者被禁用的话，那么将没有限制
failCount，错误次数将锁定，必填
banSecond，锁定的秒数，必填', '1', '2023-05-10 09:29:46', '1', '2023-05-10 09:34:40', 0);
INSERT INTO "public"."sys_config" VALUES ('2cd7adf8ceeb1386d85b5477ee03d0fe', '系统短信模板ID配置', 'sys.smsTemplate.config', '{
	"register": {
		"variable": "code",
		"templateId": "SMS_460755481",
		"length": 6
	},
	"login": {
		"variable": "code",
		"templateId": "SMS_460755481",
		"length": 6
	},
	"bindPhone": {
		"variable": "code",
		"templateId": "SMS_460755481",
		"length": 6
	},
	"findPassword": {
		"variable": "code",
		"templateId": "SMS_460755481",
		"length": 6
	},
	"jobError": {
		"variable": "code",
		"templateId": "SMS_460755481",
		"length": 70
	}
}', '1', '0', '如果删除或者禁用此参数，那么系统在以上特定场景将无法发送短信
具体参数说明参考文档', '1', '2023-05-14 16:29:54', '1', '2023-05-14 16:29:54', 0);
INSERT INTO "public"."sys_config" VALUES ('3d940640a0c040a0025c2c350da036f3', '系统注册开关', 'sys.lock.register', 'true', '1', '0', 'true=开启，false=关闭', '1', '2023-04-14 22:34:46', '1', '2023-04-25 21:31:29', 0);
INSERT INTO "public"."sys_config" VALUES ('4aa484dd076566c2e7d936525e36e8c4', '系统短信配置', 'sys.sms.config', '{
	"config": {
		"type": "aliyun"
	},
	"aliyun": {
		"enable": "true",
		"endpoint": "dysmsapi.aliyuncs.com",
		"accessKeyId": "LTAI5666666666666RpcQ9",
		"accessKeySecret": "N9GXnZ66666666666kX3GPqW8",
		"signName": "MyAdmin"
	},
	"tencent": {
		"enable": "true",
		"endpoint": "sms.tencentcloudapi.com",
		"accessKeyId": "AKID2oPDoCY66666666666dMp53mHfoYZGw",
		"accessKeySecret": "e6JTP5ex66666666666vhHYBl7C5g05",
		"signName": "个人开发记录网",
		"sdkAppId": "1400820693"
	}
}', '1', '0', '如果删除或者禁用此参数，那么系统将无法发送短信
具体参数说明参考文档', '1', '2023-05-13 10:05:25', '1', '2023-05-13 11:39:54', 0);
INSERT INTO "public"."sys_config" VALUES ('65600f7210f507b143421c4ed33a9b07', '文件列表是否开启预览', 'sys.file.previewListResource', 'true', '1', '0', 'true=开启，false=关闭', '1', '2023-04-16 23:37:01', '1', '2023-04-25 21:00:40', 0);
INSERT INTO "public"."sys_config" VALUES ('784d0f730c442a42bd6331e81516ebd8', '系统发送限制配置', 'sys.sendLimit.config', '{
	"email": {
		"limitType": 1,
		"needWait": 60,
		"dayMax": 8,
		"keepLive": 1800
	},
	"sms": {
		"limitType": 1,
		"needWait": 60,
		"dayMax": 8,
		"keepLive": 300
	}
}', '1', '0', '如果删除或者禁用此参数，那么系统发送验证码时将没有任何限制
具体参数说明参考文档', '1', '2023-05-19 15:09:31', '1', '2023-05-19 15:09:31', 0);
INSERT INTO "public"."sys_config" VALUES ('83332089ca838d1816498f2bfd874ad3', '系统邮箱配置', 'sys.email.config', '{
    "config": {
        "mode": "0"
    },
    "emails": [{
        "enable": "true",
        "host": "smtp.qq.com",
        "port": 587,
        "encode": "UTF-8",
        "protocol": "smtp",
        "email": "1330166564@qq.com",
        "from": "MyAdmin<1330166564@qq.com>",
        "password": "6666666666666666",
        "timeout": "25000",
        "auth": "true",
        "socketFactoryClass": "javax.net.ssl.SSLSocketFactory",
        "weight": "100"
    },{
        "enable": "true",
        "host": "smtp.qq.com",
        "port": 587,
        "encode": "UTF-8",
        "protocol": "smtp",
        "email": "1330166565@qq.com",
        "from": "MyAdmin<1330166565@qq.com>",
        "password": "6666666666666666",
        "timeout": "25000",
        "auth": "true",
        "socketFactoryClass": "javax.net.ssl.SSLSocketFactory",
        "weight": "100"
    }]
}', '1', '0', '如果删除或者禁用此参数，那么系统将无法发送邮件
具体参数说明参考文档
', '1', '2023-05-11 10:21:42', '1', '2023-05-22 16:42:58', 0);
INSERT INTO "public"."sys_config" VALUES ('cc1dd434b089df0a6e97fbe446c39377', '系统飞书配置', 'sys.feishu.config', '{
	"testbot": {
		"keywords": "",
		"secret": "",
		"accessToken": "207b5526-****-****-****-b57ac302626f",
		"remark": "测试群的机器人"
	}
}', '0', '0', '如果删除或者禁用此参数，那么系统将无法发送飞书通知
具体参数说明参考文档', '1', '2023-10-01 13:40:50', '1', '2023-10-01 13:40:50', 0);
INSERT INTO "public"."sys_config" VALUES ('64c70f1e91cbcd3cfc390e3966aeef6b', '系统企微配置', 'sys.wecom.config', '{
	"testbot": {
		"key": "b5caa377-****-****-****-3d656ed93b12",
		"remark": "测试群的机器人"
	}
}', '0', '0', '如果删除或者禁用此参数，那么系统将无法发送企业微信通知
具体参数说明参考文档', '1', '2023-10-01 13:41:53', '1', '2023-10-01 13:43:57', 0);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dept";
CREATE TABLE "public"."sys_dept" (
                                     "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                     "parent_id" varchar(32) COLLATE "pg_catalog"."default",
                                     "name" varchar(255) COLLATE "pg_catalog"."default",
                                     "code" varchar(255) COLLATE "pg_catalog"."default",
                                     "summary" text COLLATE "pg_catalog"."default",
                                     "status" varchar(2) COLLATE "pg_catalog"."default",
                                     "leader_user_id" varchar(32) COLLATE "pg_catalog"."default",
                                     "dept_level" int4,
                                     "sort" int4,
                                     "remark" varchar(255) COLLATE "pg_catalog"."default",
                                     "create_id" varchar(32) COLLATE "pg_catalog"."default",
                                     "create_time" timestamp(6),
                                     "update_id" varchar(32) COLLATE "pg_catalog"."default",
                                     "update_time" timestamp(6),
                                     "is_delete" int4
)
;
COMMENT ON COLUMN "public"."sys_dept"."parent_id" IS '父级部门ID，顶级为0';
COMMENT ON COLUMN "public"."sys_dept"."name" IS '部门名称';
COMMENT ON COLUMN "public"."sys_dept"."code" IS '部门编号';
COMMENT ON COLUMN "public"."sys_dept"."summary" IS '部门简介';
COMMENT ON COLUMN "public"."sys_dept"."status" IS '部门状态，0=正常，1=停用';
COMMENT ON COLUMN "public"."sys_dept"."leader_user_id" IS '部门负责人 关联用户ID';
COMMENT ON COLUMN "public"."sys_dept"."dept_level" IS '层级，顶级为0';
COMMENT ON COLUMN "public"."sys_dept"."sort" IS '排序';
COMMENT ON COLUMN "public"."sys_dept"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_dept"."create_id" IS '创建人';
COMMENT ON COLUMN "public"."sys_dept"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_dept"."update_id" IS '修改人';
COMMENT ON COLUMN "public"."sys_dept"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_dept"."is_delete" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "public"."sys_dept" IS '部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO "public"."sys_dept" VALUES ('100', '0', '熊猫眼集团', 'XMY', NULL, '0', '1', 0, 0, NULL, '1', '2023-04-10 14:35:22', '1', '2023-04-10 14:35:22', 0);
INSERT INTO "public"."sys_dept" VALUES ('101', '100', '济南分公司', 'XMY-JN', NULL, '0', '1', 1, 1, NULL, '1', '2023-04-10 14:35:22', '1', '2023-04-10 14:35:22', 0);
INSERT INTO "public"."sys_dept" VALUES ('102', '100', '北京分公司', 'XMY-BJ', NULL, '0', '1', 1, 2, NULL, '1', '2023-04-10 14:35:22', '1', '2023-04-10 14:35:22', 0);
INSERT INTO "public"."sys_dept" VALUES ('103', '101', '研发部门', 'XMY-JN-1', NULL, '0', '1', 2, 1, '', '1', '2023-04-10 14:35:22', '1', '2023-04-25 20:54:24', 0);
INSERT INTO "public"."sys_dept" VALUES ('104', '101', '市场部门', 'XMY-JN-2', NULL, '0', '1', 2, 2, NULL, '1', '2023-04-10 14:35:22', '1', '2023-04-12 21:26:01', 0);
INSERT INTO "public"."sys_dept" VALUES ('105', '101', '测试部门', 'XMY-JN-3', NULL, '0', '1', 2, 3, NULL, '1', '2023-04-10 14:35:22', '1', '2023-04-12 21:25:51', 0);
INSERT INTO "public"."sys_dept" VALUES ('106', '101', '财务部门', 'XMY-JN-4', NULL, '0', '1', 2, 4, NULL, '1', '2023-04-10 14:35:22', '1', '2023-04-10 14:35:22', 0);
INSERT INTO "public"."sys_dept" VALUES ('107', '101', '运维部门', 'XMY-JN-5', NULL, '0', '1', 2, 5, NULL, '1', '2023-04-10 14:35:22', '1', '2023-04-10 14:35:22', 0);
INSERT INTO "public"."sys_dept" VALUES ('108', '102', '市场部门', 'XMY-BJ-1', NULL, '0', '1', 2, 1, NULL, '1', '2023-04-10 14:35:22', '1', '2023-04-10 14:35:22', 0);
INSERT INTO "public"."sys_dept" VALUES ('109', '102', '财务部门', 'XMY-BJ-2', NULL, '0', '1', 2, 2, NULL, '1', '2023-04-10 14:35:22', '1', '2023-04-10 14:35:22', 0);

-- ----------------------------
-- Table structure for sys_dept_parent
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dept_parent";
CREATE TABLE "public"."sys_dept_parent" (
                                            "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                            "dept_id" varchar(32) COLLATE "pg_catalog"."default",
                                            "parent_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_dept_parent"."dept_id" IS '部门ID';
COMMENT ON COLUMN "public"."sys_dept_parent"."parent_id" IS '父级部门ID，顶级为0';
COMMENT ON TABLE "public"."sys_dept_parent" IS '部门层级关系表';

-- ----------------------------
-- Records of sys_dept_parent
-- ----------------------------
INSERT INTO "public"."sys_dept_parent" VALUES ('06d36f0f8901fb4ffbe781b080f07dd6', '107', '100');
INSERT INTO "public"."sys_dept_parent" VALUES ('1a0b09c29f5470ac94079b303b0ad12b', '101', '101');
INSERT INTO "public"."sys_dept_parent" VALUES ('1fd041e9beb93fa50133d2d7321e1cb0', '108', '100');
INSERT INTO "public"."sys_dept_parent" VALUES ('222c3eaf636f8edf7f83eda4f90d26f2', '105', '100');
INSERT INTO "public"."sys_dept_parent" VALUES ('268027691ee65797e33da228ea194b6c', '107', '107');
INSERT INTO "public"."sys_dept_parent" VALUES ('26d358c6733335e1bcb9aae9ee3e54db', '102', '0');
INSERT INTO "public"."sys_dept_parent" VALUES ('28070f8ff40f690d48c308b76e59a993', '105', '105');
INSERT INTO "public"."sys_dept_parent" VALUES ('2d6c36d425ff5be02ae48940f701c3ef', '103', '103');
INSERT INTO "public"."sys_dept_parent" VALUES ('384202fb5d68394c886cbeefadaf01ce', '109', '100');
INSERT INTO "public"."sys_dept_parent" VALUES ('3d41adfbf70d1af146f9b22cfa3c4903', '100', '100');
INSERT INTO "public"."sys_dept_parent" VALUES ('4222314fa5495df9d762a953cfcf65ac', '107', '0');
INSERT INTO "public"."sys_dept_parent" VALUES ('494ccdf978d57d8bc96b02d72a9f5a3a', '108', '108');
INSERT INTO "public"."sys_dept_parent" VALUES ('550ccf63f48406ebf42219e7d3219582', '107', '101');
INSERT INTO "public"."sys_dept_parent" VALUES ('59f10bf5e1c6f714b4f1be02910553fa', '103', '101');
INSERT INTO "public"."sys_dept_parent" VALUES ('617f7108449586ef211c0955a189b222', '108', '102');
INSERT INTO "public"."sys_dept_parent" VALUES ('68b753537bc2bfa1b09e677077a5e1cc', '109', '102');
INSERT INTO "public"."sys_dept_parent" VALUES ('700950267ddc9c18243472f68a2e0c62', '106', '101');
INSERT INTO "public"."sys_dept_parent" VALUES ('886b217dcfb0a77b4b281f6a5981c69b', '102', '102');
INSERT INTO "public"."sys_dept_parent" VALUES ('96b6974412095ef1f056f5596d1df48d', '103', '0');
INSERT INTO "public"."sys_dept_parent" VALUES ('98faed672906375a309a2ac4c2ce47f1', '104', '100');
INSERT INTO "public"."sys_dept_parent" VALUES ('9d125f57b2a7251caac817bed4ddd6ee', '103', '100');
INSERT INTO "public"."sys_dept_parent" VALUES ('a63973aaebc357a568b9ec3e15bdb774', '104', '101');
INSERT INTO "public"."sys_dept_parent" VALUES ('a95a29cd20b7a3ee0deabdd83b468bd9', '101', '100');
INSERT INTO "public"."sys_dept_parent" VALUES ('b19a436916284476e08aacfbc1012e17', '101', '0');
INSERT INTO "public"."sys_dept_parent" VALUES ('b264e91699df1ed6db2253203308452d', '106', '106');
INSERT INTO "public"."sys_dept_parent" VALUES ('bf0399783891c2e9df37b693fdadf6d2', '105', '0');
INSERT INTO "public"."sys_dept_parent" VALUES ('c57966fa37859a0c0c153c5dd5ca2a6d', '106', '100');
INSERT INTO "public"."sys_dept_parent" VALUES ('d56ed387ac2c6105ffbe3faa8d99f5eb', '100', '0');
INSERT INTO "public"."sys_dept_parent" VALUES ('d570bd153cc62d2a8c346bf55769bceb', '105', '101');
INSERT INTO "public"."sys_dept_parent" VALUES ('d6cd40b06e1a20bc20d741d52fb03fed', '109', '0');
INSERT INTO "public"."sys_dept_parent" VALUES ('e0754a80b8ef4531839db616d6a4f797', '104', '104');
INSERT INTO "public"."sys_dept_parent" VALUES ('e5a25858e3e18b34cf4e51dcd45ffee3', '106', '0');
INSERT INTO "public"."sys_dept_parent" VALUES ('e651fc9a844a23121783ace242907138', '102', '100');
INSERT INTO "public"."sys_dept_parent" VALUES ('f0f6e94712736e9cb689bf044f5667af', '104', '0');
INSERT INTO "public"."sys_dept_parent" VALUES ('f5cd73b71673848ce9a5a7543ccd38fa', '109', '109');
INSERT INTO "public"."sys_dept_parent" VALUES ('fae666e9d536d28b28dcf84674e34aaf', '108', '0');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dict";
CREATE TABLE "public"."sys_dict" (
                                     "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                     "name" varchar(255) COLLATE "pg_catalog"."default",
                                     "code" varchar(255) COLLATE "pg_catalog"."default",
                                     "status" varchar(2) COLLATE "pg_catalog"."default",
                                     "remark" varchar(255) COLLATE "pg_catalog"."default",
                                     "create_id" varchar(32) COLLATE "pg_catalog"."default",
                                     "create_time" timestamp(6),
                                     "update_id" varchar(32) COLLATE "pg_catalog"."default",
                                     "update_time" timestamp(6),
                                     "is_delete" int4
)
;
COMMENT ON COLUMN "public"."sys_dict"."name" IS '字典名称';
COMMENT ON COLUMN "public"."sys_dict"."code" IS '字典编码';
COMMENT ON COLUMN "public"."sys_dict"."status" IS '字典状态，0=正常，1=禁用';
COMMENT ON COLUMN "public"."sys_dict"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_dict"."create_id" IS '创建人';
COMMENT ON COLUMN "public"."sys_dict"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_dict"."update_id" IS '修改人';
COMMENT ON COLUMN "public"."sys_dict"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_dict"."is_delete" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "public"."sys_dict" IS '字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO "public"."sys_dict" VALUES ('2baef88eba108855ecc7aa28beee9a5f', '接口限制类型', 'sys_api_limit_type', '0', NULL, '1', '2023-05-23 11:02:12', '1', '2023-05-23 11:02:22', 0);
INSERT INTO "public"."sys_dict" VALUES ('32a3d1d751b94bf4aff2f9c7cb0c667d', '通知公告类型', 'sys_notice_type', '0', NULL, '1', '2023-03-13 09:58:45', '1', '2023-04-20 22:50:51', 0);
INSERT INTO "public"."sys_dict" VALUES ('3da162a1fca95ac279a83107f6989db2', 'OSS厂商类型', 'sys_oss_type', '0', NULL, '1', '2023-04-16 23:49:02', '1', '2023-04-20 22:52:49', 0);
INSERT INTO "public"."sys_dict" VALUES ('4164ee7178b8cfe228e2606b2ee4c18e', '系统用户状态', 'sys_user_status', '0', NULL, '1', '2023-03-27 11:10:12', '1', '2023-04-14 23:07:34', 0);
INSERT INTO "public"."sys_dict" VALUES ('485fc2c337d2430cb39449dad5ba970a', '系统状态', 'sys_common_status', '0', NULL, '1', '2023-03-13 09:58:45', '1', '2023-03-13 09:58:45', 0);
INSERT INTO "public"."sys_dict" VALUES ('709ac842d94f444cb50c94e1d5bea3e7', '测试数据类型', 'test_data_type', '0', NULL, NULL, '2023-03-15 14:11:03', '1', '2023-04-20 22:50:55', 0);
INSERT INTO "public"."sys_dict" VALUES ('7d7457a0562a4c9a80e6f3de0426f7e7', '用户性别', 'sys_user_sex', '0', NULL, '1', '2023-03-13 09:58:45', '1', '2023-03-13 09:58:45', 0);
INSERT INTO "public"."sys_dict" VALUES ('7e9cdefc71e446ba8ea69cf401913890', '系统开关', 'sys_normal_disable', '0', NULL, '1', '2023-02-13 09:58:45', '1', '2023-03-13 09:58:45', 0);
INSERT INTO "public"."sys_dict" VALUES ('824bf83ca68c8c235e75dc731088d2f8', '系统通知渠道', 'sys_notify_channel', '0', '', '1', '2023-05-13 19:08:34', '1', '2023-05-13 19:08:37', 0);
INSERT INTO "public"."sys_dict" VALUES ('98c39286b939f05fb514782a4779a5bf', '系统时间单位', 'sys_time_unit', '0', NULL, '1', '2023-05-23 14:47:07', '1', '2023-06-26 15:49:09', 0);
INSERT INTO "public"."sys_dict" VALUES ('9b86e9ae2728400189d76f9b8710c269', '系统是否', 'sys_yes_no', '0', NULL, '1', '2023-02-13 09:58:45', '1', '2023-03-13 09:58:45', 0);
INSERT INTO "public"."sys_dict" VALUES ('a08cf8bf214b49f68bcc03d5b8ecef7d', '通知公告状态', 'sys_notice_status', '0', NULL, '1', '2023-03-13 09:58:45', '1', '2023-04-20 22:51:25', 0);
INSERT INTO "public"."sys_dict" VALUES ('bc4fd7d4cdb70ac8b48532de2f7410f7', '任务分组', 'sys_job_group', '0', NULL, '1', '2023-05-01 14:56:03', '1', '2023-05-01 14:56:03', 0);
INSERT INTO "public"."sys_dict" VALUES ('c7936e47545262dd3836b322a768015f', 'OSS桶权限类型', 'sys_oss_scope', '0', NULL, '1', '2023-04-18 23:46:04', '1', '2023-04-20 22:52:37', 0);
INSERT INTO "public"."sys_dict" VALUES ('c9e8979aa7c14a9bae2ccd624067c734', '菜单状态', 'sys_show_hide', '0', NULL, '1', '2023-03-13 09:58:45', '1', '2023-03-13 09:58:45', 0);
INSERT INTO "public"."sys_dict" VALUES ('cb460b35c6cc2bf90b0c0ad7d092024c', '系统用户类型', 'sys_user_type', '0', NULL, '1', '2023-03-23 10:57:44', '1', '2023-04-20 22:50:58', 0);
INSERT INTO "public"."sys_dict" VALUES ('cc2b5556e60e496fb3863bda3ab283e3', '操作类型', 'sys_oper_type', '0', NULL, '1', '2023-03-13 09:58:45', '1', '2023-03-13 09:58:45', 0);
INSERT INTO "public"."sys_dict" VALUES ('d123f703a673a86c2340954fd89a4566', '数据权限', 'data_scope', '0', NULL, '1', '2023-03-29 10:38:08', '1', '2023-04-04 15:16:14', 0);
INSERT INTO "public"."sys_dict" VALUES ('ddff67e6d459cf953f1ac23c413f76d3', '任务状态', 'sys_job_status', '0', NULL, '1', '2023-05-01 14:55:38', '1', '2023-05-01 14:55:38', 0);

-- ----------------------------
-- Table structure for sys_dict_detail
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_dict_detail";
CREATE TABLE "public"."sys_dict_detail" (
                                            "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                            "dict_code" varchar(255) COLLATE "pg_catalog"."default",
                                            "label" varchar(255) COLLATE "pg_catalog"."default",
                                            "value" varchar(255) COLLATE "pg_catalog"."default",
                                            "sort" int4,
                                            "css_class" varchar(255) COLLATE "pg_catalog"."default",
                                            "list_class" varchar(255) COLLATE "pg_catalog"."default",
                                            "status" varchar(2) COLLATE "pg_catalog"."default",
                                            "remark" varchar(255) COLLATE "pg_catalog"."default",
                                            "create_id" varchar(32) COLLATE "pg_catalog"."default",
                                            "create_time" timestamp(6),
                                            "update_id" varchar(32) COLLATE "pg_catalog"."default",
                                            "update_time" timestamp(6),
                                            "is_delete" int4
)
;
COMMENT ON COLUMN "public"."sys_dict_detail"."dict_code" IS '字典编码';
COMMENT ON COLUMN "public"."sys_dict_detail"."label" IS '字典标签';
COMMENT ON COLUMN "public"."sys_dict_detail"."value" IS '字典键值';
COMMENT ON COLUMN "public"."sys_dict_detail"."sort" IS '排序';
COMMENT ON COLUMN "public"."sys_dict_detail"."css_class" IS '样式属性（其他样式扩展）';
COMMENT ON COLUMN "public"."sys_dict_detail"."list_class" IS '表格回显样式';
COMMENT ON COLUMN "public"."sys_dict_detail"."status" IS '字典状态，0=正常，1=禁用';
COMMENT ON COLUMN "public"."sys_dict_detail"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_dict_detail"."create_id" IS '创建人';
COMMENT ON COLUMN "public"."sys_dict_detail"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_dict_detail"."update_id" IS '修改人';
COMMENT ON COLUMN "public"."sys_dict_detail"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_dict_detail"."is_delete" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "public"."sys_dict_detail" IS '字典明细表';

-- ----------------------------
-- Records of sys_dict_detail
-- ----------------------------
INSERT INTO "public"."sys_dict_detail" VALUES ('00dce2554c6eb602a62bc45b23f19066', 'sys_job_status', '正常', '0', 0, NULL, 'success', '0', NULL, '1', '2023-05-01 14:57:26', '1', '2023-05-01 15:44:31', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('015c16f7bcce3fbf50afa1b71646f05f', 'sys_api_limit_type', '停用', '1', 1, NULL, 'danger', '0', NULL, '1', '2023-05-23 11:02:45', '1', '2023-05-23 11:02:50', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('0278ac576ea3fa7f1e3df720e4c051b2', 'test_data_type', '其他', '3', 4, NULL, 'warning', '0', '其他', '1', '2023-03-17 10:38:09', '1', '2023-04-20 22:50:55', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('0402c072489ef66c7ec33916c2b1f938', 'sys_oper_type', '删除', '2', 2, NULL, 'danger', '0', NULL, '1', '2023-04-19 22:24:04', '1', '2023-04-19 22:24:12', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('065f42570f8454a5a91477ce552ff24d', 'sys_notify_channel', '邮箱', '1', 1, NULL, 'primary', '0', NULL, '1', '2023-05-13 19:09:14', '1', '2023-05-13 19:09:14', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('0a77ebdc362f6c039aec7e8261402a82', 'sys_oss_type', '华为云', '华为云', 0, NULL, 'danger', '0', NULL, '1', '2023-04-16 23:55:15', '1', '2023-04-20 22:52:49', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('0ab7306ac5aaf342514eeed9488e4216', 'data_scope', '本人数据', '0', 0, NULL, 'info', '0', NULL, '1', '2023-03-29 10:38:22', '1', '2023-03-29 10:38:22', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('0eee47140c4a07a008365f9fa8d8cdc1', 'sys_job_group', '默认', 'DEFAULT', 1, NULL, 'success', '0', NULL, '1', '2023-05-01 14:56:53', '1', '2023-05-01 14:57:01', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('11abad5a8a2ac077c362b044bc99778e', 'sys_oper_type', '其他', '0', 0, NULL, 'info', '0', NULL, '1', '2023-04-19 22:23:37', '1', '2023-04-19 22:23:37', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('1638c4c1734530de841be71cc5ebd8b3', 'sys_time_unit', '分钟', '1', 1, NULL, 'primary', '0', NULL, '1', '2023-05-23 14:48:01', '1', '2023-06-26 15:49:09', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('19c1384a77f2421a95a75cb99ddbadd4', 'sys_yes_no', '是', '1', 2, NULL, 'primary', '0', '系统默认是', '1', '2023-03-15 16:36:49', '1', '2023-03-15 16:36:49', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('1c48afa8c9c14fb8a3aec4c255b89769', 'test_data_type', '科技', '1', 2, NULL, 'info', '0', '科技', '1', '2023-03-15 16:36:49', '1', '2023-04-20 22:50:55', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('325a4db1eb9c3d73ab4754875e2ef9bb', 'sys_oss_type', 'minio', 'minio', 0, '', 'primary', '0', NULL, '1', '2023-04-16 23:49:45', '1', '2023-04-20 22:52:49', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('3400ec32fe6a4b69aacd2b5f7986da00', 'sys_user_sex', '女', '0', 1, NULL, NULL, '0', '性别女', '1', '2023-03-15 16:36:49', '1', '2023-03-15 16:36:49', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('374e0ff900544b7fa60e407422ad78bd', 'sys_user_sex', '男', '1', 2, NULL, NULL, '0', '性别男', '1', '2023-03-15 16:36:49', '1', '2023-03-15 16:36:49', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('37b9f8bb3b4d4b36dfd605c69f796540', 'sys_notify_channel', '短信', '2', 2, NULL, 'success', '0', NULL, '1', '2023-05-13 19:09:24', '1', '2023-05-13 19:09:24', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('413a1e5fbfa246e6a520f01fab6a6025', 'sys_yes_no', '否', '0', 1, NULL, 'danger', '0', '系统默认否', '1', '2023-03-15 16:36:49', '1', '2023-03-15 16:36:49', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('485e68dd0cf3ee70cb778a6409b57e1c', 'sys_job_status', '暂停', '1', 1, NULL, 'info', '0', NULL, '1', '2023-05-01 14:57:32', '1', '2023-05-01 15:44:57', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('4a0a41f396405aedcf01c7e97d2c1860', 'sys_oper_type', '下载', '8', 8, NULL, 'warning', '0', NULL, '1', '2023-04-19 22:25:39', '1', '2023-04-19 22:25:39', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('4b1b2891d50e4b419be63f230489562f', 'sys_notice_type', '公告', '2', 2, NULL, 'success', '0', '公告', '1', '2023-03-15 16:36:49', '1', '2023-04-20 22:50:51', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('56d7b1c499d747b5bd0bfa4158562439', 'sys_common_status', '成功', '0', 1, NULL, 'success', '0', '正常状态', '1', '2023-03-15 16:36:49', '1', '2023-05-01 21:08:16', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('5baa5fea23ed4f2c8ed4b32e63e8a405', 'sys_show_hide', '隐藏', '1', 2, NULL, 'danger', '0', '隐藏菜单', '1', '2023-03-15 16:36:49', '1', '2023-03-15 16:36:49', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('6232d1a9d3cb4485b15bd20a03e807d0', 'sys_notice_status', '关闭', '1', 2, NULL, 'danger', '0', '关闭状态', '1', '2023-03-15 16:36:49', '1', '2023-04-20 22:51:25', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('66824297a41647e72d8257d1b0dcaad7', 'sys_user_type', '运维', '2', 0, NULL, 'primary', '0', NULL, '1', '2023-03-23 11:04:58', '1', '2023-04-20 22:50:58', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('6bf3a9656aa4697581a0be46ffc67736', 'sys_oss_scope', 'custom', '2', 2, NULL, 'primary', '0', NULL, '1', '2023-04-18 23:46:54', '1', '2023-04-20 22:52:38', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('6f6212d3ee407955b6e1606d6bc692fa', 'sys_oss_type', '腾讯云', '腾讯云', 0, NULL, 'info', '0', NULL, '1', '2023-04-16 23:54:48', '1', '2023-04-20 22:52:49', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('703f50ed1e45a4d8f43ca7644a3b5703', 'data_scope', '本部门数据', '1', 1, NULL, 'primary', '0', NULL, '1', '2023-03-29 10:38:44', '1', '2023-03-29 10:38:44', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('707107e22b1e4021aad925c891652f0e', 'test_data_type', '民生', '0', 1, NULL, 'primary', '0', '民生', '1', '2023-03-15 16:36:49', '1', '2023-04-20 22:50:55', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('74538cc5e03118a8f95bec4b9f60ac20', 'sys_oper_type', '查询', '4', 4, NULL, 'success', '0', NULL, '1', '2023-04-19 22:24:40', '1', '2023-04-19 22:24:40', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('7e85865f7e80a1f20b4f5767a6d6f8b5', 'sys_job_group', '系统', 'SYSTEM', 0, NULL, 'primary', '0', NULL, '1', '2023-05-01 14:56:36', '1', '2023-05-01 14:56:36', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('847f21debb0a59235347dad479309d67', 'sys_api_limit_type', '限流', '0', 0, NULL, 'warning', '0', NULL, '1', '2023-05-23 11:02:37', '1', '2023-05-23 11:02:37', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('8cf5ea8761164a1e93f5524fad6df101', 'sys_normal_disable', '正常', '0', 1, NULL, 'primary', '0', '正常状态', '1', '2023-03-15 16:36:49', '1', '2023-03-15 16:36:49', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('8d9fb34470f5f0351efd0dcf2f733577', 'sys_user_type', '开发', '1', 0, NULL, 'danger', '0', NULL, '1', '2023-03-23 11:04:52', '1', '2023-04-20 22:50:58', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('8fff0e476aabc28003c96852dd515995', 'data_scope', '自定义权限', '4', 4, NULL, 'warning', '0', NULL, '1', '2023-03-29 10:39:40', '1', '2023-03-29 10:39:40', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('92ffc9d5d14bd57bfdf48926b84edd44', 'sys_oper_type', '修改', '3', 3, NULL, 'warning', '0', NULL, '1', '2023-04-19 22:24:28', '1', '2023-04-19 22:24:28', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('9908f3fc165afb446b2a469b848c84ea', 'sys_oss_type', '阿里云', '阿里云', 0, NULL, 'success', '0', NULL, '1', '2023-04-16 23:54:41', '1', '2023-04-20 22:52:49', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('9d68e851142d435b88d12ba633f7e4ea', 'sys_user_sex', '未知', '2', 3, NULL, NULL, '0', '性别未知', '1', '2023-03-15 16:36:49', '1', '2023-03-15 16:36:49', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('a30fee8cece01061abbe27922e94b7dd', 'sys_oper_type', '新增', '1', 1, NULL, 'primary', '0', NULL, '1', '2023-04-19 22:23:47', '1', '2023-04-19 22:24:15', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('a741425d33008d8b69adde307897bff9', 'sys_oss_scope', 'private', '0', 0, NULL, 'success', '0', NULL, '1', '2023-04-18 23:46:26', '1', '2023-04-20 22:52:38', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('a981115becdc2a8643ff774ac36dafe1', 'sys_time_unit', '秒', '0', 0, NULL, 'success', '0', NULL, '1', '2023-05-23 14:47:51', '1', '2023-06-26 15:49:09', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('a9c8f290b3fe2e21e0e57953da78e8ff', 'sys_user_status', '停用', '1', 0, NULL, 'warning', '0', NULL, '1', '2023-03-27 11:10:43', '1', '2023-04-14 23:07:34', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('b633a41a4c816cfb8622c2e874fbbb47', 'sys_oper_type', '上传', '7', 7, NULL, 'primary', '0', NULL, '1', '2023-04-19 22:25:26', '1', '2023-04-19 22:25:26', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('b825663a1745a0aa65307acceff1e87c', 'data_scope', '本部门及以下数据', '2', 2, NULL, 'success', '0', NULL, '1', '2023-03-29 10:39:04', '1', '2023-03-29 10:39:04', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('c46d3f2ed39d4933abccfeadeceae66c', 'sys_notice_status', '正常', '0', 1, NULL, 'primary', '0', '正常状态', '1', '2023-03-15 16:36:49', '1', '2023-04-20 22:51:25', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('c7ebb92a91938d26c50ae8ea69e826d2', 'sys_oss_type', '七牛云', '七牛云', 0, NULL, 'warning', '0', NULL, '1', '2023-04-16 23:54:54', '1', '2023-04-20 22:52:49', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('c84287c50997d6ec0ddf19bd68f84e87', 'sys_oper_type', '导出', '6', 6, NULL, 'warning', '0', NULL, '1', '2023-04-19 22:25:10', '1', '2023-04-19 22:25:10', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('c8f8118fc33f410ba61a12f780e76c3f', 'sys_notice_type', '通知', '1', 1, NULL, 'warning', '0', '通知', '1', '2023-03-15 16:36:49', '1', '2023-04-20 22:50:51', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('ca7ad3018855a44d543790fd313dc9a3', 'sys_user_status', '正常', '0', 0, NULL, 'success', '0', NULL, '1', '2023-03-27 11:10:32', '1', '2023-04-14 23:07:34', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('cb021c733abc68910a1f0ceb03a06499', 'sys_notify_channel', '钉钉', '3', 3, NULL, 'warning', '0', NULL, '1', '2023-05-13 19:09:47', '1', '2023-05-13 19:10:00', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('cbbc1ccaa65740479dde6e52529e9fda', 'sys_show_hide', '显示', '0', 1, NULL, 'primary', '0', '显示菜单', '1', '2023-03-15 16:36:49', '1', '2023-03-15 16:36:49', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('ce73653c160744d9364aadc0a39f6d6d', 'test_data_type', '农业', '2', 3, NULL, 'success', '0', '农业', '1', '2023-03-17 10:37:50', '1', '2023-04-20 22:50:55', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('dffa0d31ac0ae45cddd5f319c52bf818', 'data_scope', '全部数据', '3', 3, NULL, 'danger', '0', NULL, '1', '2023-03-29 10:39:28', '1', '2023-03-29 10:39:28', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('e1720b95d3be9ce033efd041ea649134', 'sys_user_type', '测试', '3', 0, NULL, 'info', '0', NULL, '1', '2023-03-27 15:36:50', '1', '2023-04-20 22:50:58', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('e1961a649aa19edaae657b88fd19e91b', 'sys_notify_channel', '不通知', '0', 0, NULL, 'info', '0', NULL, '1', '2023-05-13 19:09:01', '1', '2023-05-13 19:09:54', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('e5e1239310b9383f5ce42d557093bac8', 'sys_user_status', '注销', '2', 0, NULL, 'danger', '0', NULL, '1', '2023-03-27 11:10:51', '1', '2023-04-14 23:07:34', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('e83acff859cf850825835a80e7c9dc00', 'sys_oss_scope', 'public', '1', 1, NULL, 'danger', '0', NULL, '1', '2023-04-18 23:46:37', '1', '2023-04-20 22:52:38', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('eadebaa7234ca12881ac845803b978a1', 'sys_time_unit', '小时', '2', 2, '', 'info', '0', NULL, '1', '2023-05-23 14:48:15', '1', '2023-06-26 15:49:09', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('eaeffc348c4e40cf9a45e91db17d0902', 'sys_normal_disable', '停用', '1', 2, NULL, 'danger', '0', '停用状态', '1', '2023-03-15 16:36:49', '1', '2023-03-15 16:36:49', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('ee69bc49e73716f01b4f369f1323a9e9', 'sys_oper_type', '导入', '5', 5, NULL, 'primary', '0', NULL, '1', '2023-04-19 22:24:55', '1', '2023-04-19 22:24:55', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('f328b5c9651db1236cdf1d3682ac7f64', 'sys_oss_type', '京东云', '京东云', 0, NULL, 'danger', '0', NULL, '1', '2023-04-16 23:55:04', '1', '2023-04-20 22:52:49', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('ff1467cd4b4d4d84868a8f31a4f32fec', 'sys_common_status', '失败', '1', 2, NULL, 'danger', '0', '停用状态', '1', '2023-03-15 16:36:49', '1', '2023-03-15 16:36:49', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('ff3eed8202940077df815bfdf0b5d836', 'sys_time_unit', '天', '3', 3, NULL, 'warning', '0', NULL, '1', '2023-05-23 14:48:37', '1', '2023-06-26 15:49:09', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('7e4e1fc182c3cbc573a32eb7fbc9a749', 'sys_notify_channel', '飞书', '4', 4, NULL, 'warning', '0', NULL, '1', '2023-10-01 14:33:28', '1', '2023-10-01 14:33:28', 0);
INSERT INTO "public"."sys_dict_detail" VALUES ('87f33838c66f34a94d7c19c384e4758c', 'sys_notify_channel', '企业微信', '5', 5, NULL, 'warning', '0', NULL, '1', '2023-10-01 14:33:39', '1', '2023-10-01 14:33:39', 0);

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_file";
CREATE TABLE "public"."sys_file" (
                                     "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                     "original_name" varchar(255) COLLATE "pg_catalog"."default",
                                     "file_name" varchar(255) COLLATE "pg_catalog"."default",
                                     "file_suffix" varchar(255) COLLATE "pg_catalog"."default",
                                     "file_url" varchar(500) COLLATE "pg_catalog"."default",
                                     "file_size" int4,
                                     "file_md5" varchar(255) COLLATE "pg_catalog"."default",
                                     "file_type" varchar(255) COLLATE "pg_catalog"."default",
                                     "oss_id" varchar(32) COLLATE "pg_catalog"."default",
                                     "status" varchar(2) COLLATE "pg_catalog"."default",
                                     "remark" varchar(255) COLLATE "pg_catalog"."default",
                                     "create_id" varchar(32) COLLATE "pg_catalog"."default",
                                     "create_time" timestamp(6),
                                     "update_id" varchar(32) COLLATE "pg_catalog"."default",
                                     "update_time" timestamp(6),
                                     "is_delete" int4
)
;
COMMENT ON COLUMN "public"."sys_file"."original_name" IS '原始文件名称';
COMMENT ON COLUMN "public"."sys_file"."file_name" IS '文件名称';
COMMENT ON COLUMN "public"."sys_file"."file_suffix" IS '文件后缀名';
COMMENT ON COLUMN "public"."sys_file"."file_url" IS '文件URL地址';
COMMENT ON COLUMN "public"."sys_file"."file_size" IS '文件大小（字节）';
COMMENT ON COLUMN "public"."sys_file"."file_md5" IS '文件MD5（eTag）';
COMMENT ON COLUMN "public"."sys_file"."file_type" IS '文件类型，例如：例如：image/jpeg';
COMMENT ON COLUMN "public"."sys_file"."oss_id" IS '所属OSS配置ID';
COMMENT ON COLUMN "public"."sys_file"."status" IS '文件状态，0=正常，1=禁用';
COMMENT ON COLUMN "public"."sys_file"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_file"."create_id" IS '创建人';
COMMENT ON COLUMN "public"."sys_file"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_file"."update_id" IS '修改人';
COMMENT ON COLUMN "public"."sys_file"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_file"."is_delete" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "public"."sys_file" IS 'OSS文件表';

-- ----------------------------
-- Records of sys_file
-- ----------------------------

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_job";
CREATE TABLE "public"."sys_job" (
                                    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                    "job_name" varchar(255) COLLATE "pg_catalog"."default",
                                    "job_group" varchar(255) COLLATE "pg_catalog"."default",
                                    "invoke_target" varchar(1024) COLLATE "pg_catalog"."default",
                                    "cron_expression" varchar(255) COLLATE "pg_catalog"."default",
                                    "status" varchar(2) COLLATE "pg_catalog"."default",
                                    "misfire_policy" varchar(2) COLLATE "pg_catalog"."default",
                                    "concurrent" varchar(2) COLLATE "pg_catalog"."default",
                                    "remark" varchar(255) COLLATE "pg_catalog"."default",
                                    "notify_channel" varchar(2) COLLATE "pg_catalog"."default",
                                    "notify_objs" varchar(1024) COLLATE "pg_catalog"."default",
                                    "create_id" varchar(32) COLLATE "pg_catalog"."default",
                                    "create_time" timestamp(6),
                                    "update_id" varchar(32) COLLATE "pg_catalog"."default",
                                    "update_time" timestamp(6),
                                    "is_delete" int4
)
;
COMMENT ON COLUMN "public"."sys_job"."job_name" IS '任务名称';
COMMENT ON COLUMN "public"."sys_job"."job_group" IS '任务分组';
COMMENT ON COLUMN "public"."sys_job"."invoke_target" IS '调用目标字符串';
COMMENT ON COLUMN "public"."sys_job"."cron_expression" IS 'cron执行表达式';
COMMENT ON COLUMN "public"."sys_job"."status" IS '任务状态，0=正常，1=暂停';
COMMENT ON COLUMN "public"."sys_job"."misfire_policy" IS '计划执行错误策略，1=立即执行，2=执行一次，3=放弃执行';
COMMENT ON COLUMN "public"."sys_job"."concurrent" IS '是否并发执行，0=允许，1=禁止';
COMMENT ON COLUMN "public"."sys_job"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_job"."notify_channel" IS '异常时，通知渠道，0=不通知，1=邮件，2=短信，3=钉钉，4=飞书，5=企业微信';
COMMENT ON COLUMN "public"."sys_job"."notify_objs" IS '异常时，通知对象，多个用,隔开
     * 邮件渠道时，写邮箱
     * 短信渠道时，写手机号
     * 钉钉、飞书、企业微信渠道时，写botName';
COMMENT ON COLUMN "public"."sys_job"."create_id" IS '创建人';
COMMENT ON COLUMN "public"."sys_job"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_job"."update_id" IS '修改人';
COMMENT ON COLUMN "public"."sys_job"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_job"."is_delete" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "public"."sys_job" IS '定时任务调度表';

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO "public"."sys_job" VALUES ('0a8d20cd101aad4a761e548ff062f593', '测试（无参数）', 'DEFAULT', 'TestTask.noParams', '6 2 * * * ?', '1', '1', '1', NULL, '1', '1330166565@qq.com,1747846658@qq.com', '1', '2023-05-01 16:57:10', '1', '2023-06-05 18:21:20', 0);
INSERT INTO "public"."sys_job" VALUES ('5ce12d1934bb1d67d4c3787aa037bc7c', '测试（多个参数）', 'DEFAULT', 'TestTask.multipleParams(''test'', true, 666L, 365.16D, 200)', '* * * * * ?', '1', '2', '1', NULL, '0', NULL, '1', '2023-05-01 15:45:55', '1', '2023-06-05 18:21:31', 0);
INSERT INTO "public"."sys_job" VALUES ('b28738a16605721937a74cd3a1089fb8', '测试（一个参数）', 'DEFAULT', 'TestTask.oneParams(''阿萨德'')', '0 0 * * * ?', '1', '1', '1', NULL, '0', NULL, '1', '2023-05-01 16:53:05', '1', '2023-06-05 18:21:25', 0);

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_job_log";
CREATE TABLE "public"."sys_job_log" (
                                        "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                        "job_id" varchar(255) COLLATE "pg_catalog"."default",
                                        "job_message" text COLLATE "pg_catalog"."default",
                                        "exception_info" text COLLATE "pg_catalog"."default",
                                        "start_time" timestamp(6),
                                        "end_time" timestamp(6),
                                        "execute_time" int4,
                                        "status" varchar(2) COLLATE "pg_catalog"."default",
                                        "remark" varchar(255) COLLATE "pg_catalog"."default",
                                        "create_id" varchar(32) COLLATE "pg_catalog"."default",
                                        "create_time" timestamp(6),
                                        "update_id" varchar(32) COLLATE "pg_catalog"."default",
                                        "update_time" timestamp(6),
                                        "is_delete" int4
)
;
COMMENT ON COLUMN "public"."sys_job_log"."job_id" IS '关联定时任务ID';
COMMENT ON COLUMN "public"."sys_job_log"."job_message" IS '日志信息';
COMMENT ON COLUMN "public"."sys_job_log"."exception_info" IS '异常信息';
COMMENT ON COLUMN "public"."sys_job_log"."start_time" IS '开始执行时间';
COMMENT ON COLUMN "public"."sys_job_log"."end_time" IS '结束执行时间';
COMMENT ON COLUMN "public"."sys_job_log"."execute_time" IS '执行耗时时间（毫秒）';
COMMENT ON COLUMN "public"."sys_job_log"."status" IS '执行结果，0=成功，1=失败';
COMMENT ON COLUMN "public"."sys_job_log"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_job_log"."create_id" IS '创建人';
COMMENT ON COLUMN "public"."sys_job_log"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_job_log"."update_id" IS '修改人';
COMMENT ON COLUMN "public"."sys_job_log"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_job_log"."is_delete" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "public"."sys_job_log" IS '定时任务调度日志表';

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log_login
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_log_login";
CREATE TABLE "public"."sys_log_login" (
                                          "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                          "username" varchar(255) COLLATE "pg_catalog"."default",
                                          "ip" varchar(255) COLLATE "pg_catalog"."default",
                                          "location" varchar(255) COLLATE "pg_catalog"."default",
                                          "browser" varchar(255) COLLATE "pg_catalog"."default",
                                          "os" varchar(255) COLLATE "pg_catalog"."default",
                                          "status" varchar(2) COLLATE "pg_catalog"."default",
                                          "remark" varchar(255) COLLATE "pg_catalog"."default",
                                          "create_id" varchar(32) COLLATE "pg_catalog"."default",
                                          "create_time" timestamp(6),
                                          "update_id" varchar(32) COLLATE "pg_catalog"."default",
                                          "update_time" timestamp(6),
                                          "is_delete" int4
)
;
COMMENT ON COLUMN "public"."sys_log_login"."username" IS '用户账号';
COMMENT ON COLUMN "public"."sys_log_login"."ip" IS '登录IP';
COMMENT ON COLUMN "public"."sys_log_login"."location" IS '登录地点';
COMMENT ON COLUMN "public"."sys_log_login"."browser" IS '浏览器';
COMMENT ON COLUMN "public"."sys_log_login"."os" IS '操作系统';
COMMENT ON COLUMN "public"."sys_log_login"."status" IS '登录结果，0=成功，1=失败';
COMMENT ON COLUMN "public"."sys_log_login"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_log_login"."create_id" IS '创建人';
COMMENT ON COLUMN "public"."sys_log_login"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_log_login"."update_id" IS '修改人';
COMMENT ON COLUMN "public"."sys_log_login"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_log_login"."is_delete" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "public"."sys_log_login" IS '登录日志表';

-- ----------------------------
-- Records of sys_log_login
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log_oper
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_log_oper";
CREATE TABLE "public"."sys_log_oper" (
                                         "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                         "name" varchar(255) COLLATE "pg_catalog"."default",
                                         "type" varchar(2) COLLATE "pg_catalog"."default",
                                         "method" varchar(255) COLLATE "pg_catalog"."default",
                                         "request_type" varchar(32) COLLATE "pg_catalog"."default",
                                         "request_url" varchar(255) COLLATE "pg_catalog"."default",
                                         "request_ip" varchar(255) COLLATE "pg_catalog"."default",
                                         "request_location" varchar(255) COLLATE "pg_catalog"."default",
                                         "request_params" text COLLATE "pg_catalog"."default",
                                         "request_time" timestamp(6),
                                         "response_result" text COLLATE "pg_catalog"."default",
                                         "response_time" timestamp(6),
                                         "error_msg" text COLLATE "pg_catalog"."default",
                                         "execute_time" int4,
                                         "status" varchar(2) COLLATE "pg_catalog"."default",
                                         "remark" varchar(255) COLLATE "pg_catalog"."default",
                                         "create_id" varchar(32) COLLATE "pg_catalog"."default",
                                         "create_time" timestamp(6),
                                         "update_id" varchar(32) COLLATE "pg_catalog"."default",
                                         "update_time" timestamp(6),
                                         "is_delete" int4
)
;
COMMENT ON COLUMN "public"."sys_log_oper"."name" IS '操作名称';
COMMENT ON COLUMN "public"."sys_log_oper"."type" IS '操作类型，0=其他，1=新增，2=删除，3=修改，4=查询，5=导入，6=导出';
COMMENT ON COLUMN "public"."sys_log_oper"."method" IS '请求方法';
COMMENT ON COLUMN "public"."sys_log_oper"."request_type" IS '请求方式';
COMMENT ON COLUMN "public"."sys_log_oper"."request_url" IS '请求URL';
COMMENT ON COLUMN "public"."sys_log_oper"."request_ip" IS '请求者IP';
COMMENT ON COLUMN "public"."sys_log_oper"."request_location" IS '请求者地址';
COMMENT ON COLUMN "public"."sys_log_oper"."request_params" IS '请求参数';
COMMENT ON COLUMN "public"."sys_log_oper"."request_time" IS '请求时间';
COMMENT ON COLUMN "public"."sys_log_oper"."response_result" IS '响应结果';
COMMENT ON COLUMN "public"."sys_log_oper"."response_time" IS '响应时间';
COMMENT ON COLUMN "public"."sys_log_oper"."error_msg" IS '错误信息';
COMMENT ON COLUMN "public"."sys_log_oper"."execute_time" IS '耗时时间（毫秒）';
COMMENT ON COLUMN "public"."sys_log_oper"."status" IS '请求结果，0=成功，1=失败';
COMMENT ON COLUMN "public"."sys_log_oper"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_log_oper"."create_id" IS '创建人';
COMMENT ON COLUMN "public"."sys_log_oper"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_log_oper"."update_id" IS '修改人';
COMMENT ON COLUMN "public"."sys_log_oper"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_log_oper"."is_delete" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "public"."sys_log_oper" IS '操作日志表';

-- ----------------------------
-- Records of sys_log_oper
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_menu";
CREATE TABLE "public"."sys_menu" (
                                     "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                     "parent_id" varchar(32) COLLATE "pg_catalog"."default",
                                     "menu_name" varchar(255) COLLATE "pg_catalog"."default",
                                     "order_num" int4,
                                     "path" varchar(500) COLLATE "pg_catalog"."default",
                                     "query_param" varchar(500) COLLATE "pg_catalog"."default",
                                     "component" varchar(500) COLLATE "pg_catalog"."default",
                                     "perms" varchar(255) COLLATE "pg_catalog"."default",
                                     "icon" varchar(255) COLLATE "pg_catalog"."default",
                                     "visible" varchar(2) COLLATE "pg_catalog"."default",
                                     "status" varchar(2) COLLATE "pg_catalog"."default",
                                     "menu_type" varchar(2) COLLATE "pg_catalog"."default",
                                     "is_frame" varchar(2) COLLATE "pg_catalog"."default",
                                     "is_cache" varchar(2) COLLATE "pg_catalog"."default",
                                     "remark" varchar(255) COLLATE "pg_catalog"."default",
                                     "create_id" varchar(32) COLLATE "pg_catalog"."default",
                                     "create_time" timestamp(6),
                                     "update_id" varchar(32) COLLATE "pg_catalog"."default",
                                     "update_time" timestamp(6),
                                     "is_delete" int4
)
;
COMMENT ON COLUMN "public"."sys_menu"."parent_id" IS '父级部门ID，顶级为0';
COMMENT ON COLUMN "public"."sys_menu"."menu_name" IS '菜单名称';
COMMENT ON COLUMN "public"."sys_menu"."order_num" IS '排序';
COMMENT ON COLUMN "public"."sys_menu"."path" IS '路由地址';
COMMENT ON COLUMN "public"."sys_menu"."query_param" IS '路由参数';
COMMENT ON COLUMN "public"."sys_menu"."component" IS '组件路径';
COMMENT ON COLUMN "public"."sys_menu"."perms" IS '权限标识';
COMMENT ON COLUMN "public"."sys_menu"."icon" IS '菜单图标';
COMMENT ON COLUMN "public"."sys_menu"."visible" IS '显示状态，0=正常，1=隐藏';
COMMENT ON COLUMN "public"."sys_menu"."status" IS '菜单状态，0=正常，1=禁用';
COMMENT ON COLUMN "public"."sys_menu"."menu_type" IS '菜单类型，1=目录，2=菜单，3=按钮';
COMMENT ON COLUMN "public"."sys_menu"."is_frame" IS '是否外链，0=是，1=否';
COMMENT ON COLUMN "public"."sys_menu"."is_cache" IS '是否缓存，0=缓存，1=不缓存';
COMMENT ON COLUMN "public"."sys_menu"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_menu"."create_id" IS '创建人';
COMMENT ON COLUMN "public"."sys_menu"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_menu"."update_id" IS '修改人';
COMMENT ON COLUMN "public"."sys_menu"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_menu"."is_delete" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "public"."sys_menu" IS '菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO "public"."sys_menu" VALUES ('1', '0', '系统管理', 1, 'system', NULL, NULL, NULL, 'system', '0', '0', '1', '1', '0', '系统管理目录', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('100', '1', '用户管理', 1, 'user', NULL, 'system/user/index', 'system:user:list', 'user', '0', '0', '2', '1', '0', '用户管理菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1001', '100', '用户查询', 1, NULL, NULL, NULL, 'system:user:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1003', '100', '用户修改', 3, NULL, NULL, NULL, 'system:user:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1004', '100', '用户删除', 4, NULL, NULL, NULL, 'system:user:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1005', '100', '用户导出', 5, NULL, NULL, NULL, 'system:user:export', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1007', '100', '重置密码', 7, NULL, NULL, NULL, 'system:user:resetPwd', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1008', '101', '角色查询', 1, NULL, NULL, NULL, 'system:role:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1009', '101', '角色新增', 2, NULL, NULL, NULL, 'system:role:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1010', '101', '角色修改', 3, NULL, NULL, NULL, 'system:role:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1011', '101', '角色删除', 4, NULL, NULL, NULL, 'system:role:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1012', '101', '角色导出', 5, NULL, NULL, NULL, 'system:role:export', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1013', '102', '菜单查询', 1, NULL, NULL, NULL, 'system:menu:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1015', '102', '菜单修改', 3, NULL, NULL, NULL, 'system:menu:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1016', '102', '菜单删除', 4, NULL, NULL, NULL, 'system:menu:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1018', '103', '部门新增', 2, NULL, NULL, NULL, 'system:dept:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1019', '103', '部门修改', 3, NULL, NULL, NULL, 'system:dept:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1020', '103', '部门删除', 4, NULL, NULL, NULL, 'system:dept:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1021', '104', '岗位查询', 1, NULL, NULL, NULL, 'system:position:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1022', '104', '岗位新增', 2, NULL, NULL, NULL, 'system:position:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1023', '104', '岗位修改', 3, NULL, NULL, NULL, 'system:position:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1024', '104', '岗位删除', 4, NULL, NULL, NULL, 'system:position:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1026', '105', '字典查询', 1, '#', NULL, NULL, 'system:dict:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1027', '105', '字典新增', 2, '#', NULL, NULL, 'system:dict:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1028', '105', '字典修改', 3, '#', NULL, NULL, 'system:dict:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('103', '1', '部门管理', 4, 'dept', NULL, 'system/dept/index', 'system:dept:list', 'tree', '0', '0', '2', '1', '0', '部门管理菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1030', '105', '字典导出', 5, '#', NULL, NULL, 'system:dict:export', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1031', '106', '参数查询', 1, '#', NULL, NULL, 'system:config:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1032', '106', '参数新增', 2, '#', NULL, NULL, 'system:config:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1034', '106', '参数删除', 4, '#', NULL, NULL, 'system:config:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1035', '106', '参数导出', 5, '#', NULL, NULL, 'system:config:export', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1037', '107', '公告新增', 2, '#', NULL, NULL, 'monitor:notice:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1038', '107', '公告修改', 3, '#', NULL, NULL, 'monitor:notice:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1039', '107', '公告删除', 4, '#', NULL, NULL, 'monitor:notice:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('104', '1', '岗位管理', 5, 'position', NULL, 'system/position/index', 'system:position:list', 'post', '0', '0', '2', '1', '0', '岗位管理菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1040', '500', '操作日志查询', 1, '#', NULL, NULL, 'monitor:logOper:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1041', '500', '操作日志删除', 2, '#', NULL, NULL, 'monitor:logOper:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1043', '501', '登录日志查询', 1, '#', NULL, NULL, 'monitor:logLogin:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1044', '501', '登录日志删除', 2, '#', NULL, NULL, 'monitor:logLogin:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1045', '501', '日志日志导出', 3, '#', NULL, NULL, 'monitor:logLogin:export', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('105', '1', '字典管理', 6, 'dict', NULL, 'system/dict/index', 'system:dict:list', 'dict', '0', '0', '2', '1', '0', '字典管理菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('107', '2', '通知公告', 5, 'notice', NULL, 'monitor/notice/index', 'monitor:notice:list', 'message', '0', '0', '2', '1', '0', '通知公告菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('108', '2', '日志管理', 1, 'log', NULL, NULL, NULL, 'log', '0', '0', '1', '1', '0', '日志管理菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('111', '63854784d5ac52bfd2ac578a48f0a46f', '数据监控', 1, 'druid', NULL, 'monitor/druid/index', 'monitor:druid:list', 'druid', '0', '0', '2', '1', '0', '数据监控菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('114', '3', '表单构建', 1, 'build', NULL, 'tool/build/index', 'tool:build:list', 'build', '0', '0', '2', '1', '0', '表单构建菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('118', '2', '文件管理', 6, 'file', NULL, 'monitor/file/index', 'monitor:file:list', 'upload', '0', '0', '2', '1', '0', '文件管理菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1500', '5', '测试单表', 1, 'data', NULL, 'test/data/index', 'test:data:list', '#', '0', '0', '2', '1', '0', '测试单表菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1501', '1500', '测试单表查询', 1, '#', NULL, NULL, 'test:data:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1502', '1500', '测试单表新增', 2, '#', NULL, NULL, 'test:data:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1503', '1500', '测试单表修改', 3, '#', NULL, NULL, 'test:data:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1504', '1500', '测试单表删除', 4, '#', NULL, NULL, 'test:data:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1505', '1500', '测试单表导出', 5, '#', NULL, NULL, 'test:data:export', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1511', '1500', '测试单表导入', 6, '#', NULL, NULL, 'test:data:import', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1507', '1506', '测试树表查询', 1, '#', NULL, NULL, 'test:dataTree:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1508', '1506', '测试树表新增', 2, '#', NULL, NULL, 'test:dataTree:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('0451a199b76ce5725fc8003ba7cc56d4', 'd5d81e35c103ed1dac93a70b97819877', '刷新限制缓存', 6, NULL, NULL, NULL, 'monitor:apiLimit:refreshCache', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1002', '100', '用户新增', 2, NULL, NULL, NULL, 'system:user:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1006', '100', '用户导入', 6, NULL, NULL, NULL, 'system:user:import', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('101', '1', '角色管理', 2, 'role', NULL, 'system/role/index', 'system:role:list', 'peoples', '0', '0', '2', '1', '0', '角色管理菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1014', '102', '菜单新增', 2, NULL, NULL, NULL, 'system:menu:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1017', '103', '部门查询', 1, NULL, NULL, NULL, 'system:dept:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('102', '1', '菜单管理', 3, 'menu', NULL, 'system/menu/index', 'system:menu:list', 'tree-table', '0', '0', '2', '1', '0', '菜单管理菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1025', '104', '岗位导出', 5, NULL, NULL, NULL, 'system:position:export', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1510', '1506', '测试树表删除', 4, '#', NULL, NULL, 'test:dataTree:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1600', '118', '文件查询', 1, '#', NULL, NULL, 'monitor:file:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1601', '118', '文件上传', 2, '#', NULL, NULL, 'monitor:file:upload', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1603', '118', '文件删除', 4, '#', NULL, NULL, 'monitor:file:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1604', '118', '配置添加', 5, '#', NULL, NULL, 'monitor:ossConig:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1605', '118', '配置编辑', 6, '#', NULL, NULL, 'monitor:ossConig:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1f7c8a04250067db11e6fcae4298234e', 'd5d81e35c103ed1dac93a70b97819877', '限制修改', 3, NULL, NULL, NULL, 'monitor:apiLimit:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('2', '0', '系统运维', 2, 'monitor', NULL, NULL, NULL, 'monitor', '0', '0', '1', '1', '0', '系统监控目录', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('268571f3b6e74a1fc5bde8df2594ed81', '42708c34ab41a691ef032ab1f4cf5db0', '任务查询', 1, NULL, NULL, NULL, 'monitor:job:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('2add827d98c3b366ce0aa160d8a281ee', 'd5d81e35c103ed1dac93a70b97819877', '限制查询', 1, NULL, NULL, NULL, 'monitor:apiLimit:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('2b6cc0d2b8a0907d2832f9557089a682', 'd5d81e35c103ed1dac93a70b97819877', '限制新增', 2, NULL, NULL, NULL, 'monitor:apiLimit:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('2d562fa236077b42b5142eb869db6b18', '63854784d5ac52bfd2ac578a48f0a46f', '服务监控', 2, 'server', NULL, 'monitor/server/index', 'monitor:server:list', 'server', '0', '0', '2', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('3', '0', '系统工具', 3, 'tool', NULL, NULL, NULL, 'tool', '0', '0', '1', '1', '0', '系统工具目录', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('36c032b1a5af71ad776641f68cbbeb60', '42708c34ab41a691ef032ab1f4cf5db0', '任务状态修改', 5, NULL, NULL, NULL, 'monitor:job:changeStatus', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('39cfe621da13b55952f2af58edc3e53a', '6b70f47f76199b8cd35aae0cda15d449', '发送钉钉', 3, NULL, NULL, NULL, 'tool:functest:sendDingTalk', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('42708c34ab41a691ef032ab1f4cf5db0', '2', '定时任务', 3, 'job', NULL, 'monitor/job/index', 'monitor:job:list', 'job', '0', '0', '2', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('5', '0', '测试菜单', 5, 'demo', NULL, NULL, NULL, 'star', '0', '0', '1', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('500', '108', '操作日志', 1, 'logOper', NULL, 'monitor/logOper/index', 'monitor:logOper:list', 'form', '0', '0', '2', '1', '0', '操作日志菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('501', '108', '登录日志', 2, 'logLogin', NULL, 'monitor/logLogin/index', 'monitor:logLogin:list', 'logininfor', '0', '0', '2', '1', '0', '登录日志菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('63854784d5ac52bfd2ac578a48f0a46f', '2', '系统监控', 2, 'monitor', NULL, NULL, NULL, 'time-range', '0', '0', '1', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('69519bae5ffa67623e8bfb1c8fabdd00', '118', '配置查询', 8, NULL, NULL, NULL, 'monitor:ossConig:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('6b70f47f76199b8cd35aae0cda15d449', '3', '功能测试', 3, 'functest', NULL, 'tool/functest/index', 'tool:functest:main', 'example', '0', '0', '2', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('74456b2c60b6e0b5edd5559a8e0cc108', '106', '刷新参数缓存', 6, NULL, NULL, NULL, 'system:config:refreshCache', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('7a5af154f7d305797b56b9488207b04a', '118', '配置管理', 9, NULL, NULL, NULL, 'monitor:ossConig:list', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('7aea821f10adc345dc4a003170bb7f16', 'd5d81e35c103ed1dac93a70b97819877', '限制删除', 4, NULL, NULL, NULL, 'monitor:apiLimit:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('85d0cbd4e4441e09f5df53214acddad9', '42708c34ab41a691ef032ab1f4cf5db0', '立即执行一次', 7, NULL, NULL, NULL, 'monitor:job:run', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('8833e71489eb0dfeb36d0769573408bd', '42708c34ab41a691ef032ab1f4cf5db0', '定时任务日志导出', 9, NULL, NULL, NULL, 'monitor:jobLog:export', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('928015b29ffdad7c6621f3b92ba85c6c', '63854784d5ac52bfd2ac578a48f0a46f', '缓存监控', 3, 'cache', NULL, 'monitor/cache/index', 'monitor:cache:list', 'redis', '0', '0', '2', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('937f0292079a4b62d9a26ebd5e8d57ed', '6b70f47f76199b8cd35aae0cda15d449', '发送短信', 2, NULL, NULL, NULL, 'tool:functest:sendSms', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('9901', '105', '刷新字典缓存', 6, '#', NULL, NULL, 'system:dict:refreshCache', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('adec0c7b9d6b37672dcb6b1315f8f4eb', '42708c34ab41a691ef032ab1f4cf5db0', '定时任务日志查询', 10, NULL, NULL, NULL, 'monitor:jobLog:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('b87a985b4ef717afe650f2df9cdbe087', '118', '配置删除', 7, NULL, NULL, NULL, 'monitor:ossConig:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('c0a98c5b6e56602800968efcacfa4e47', '6b70f47f76199b8cd35aae0cda15d449', '发送邮件', 1, NULL, NULL, NULL, 'tool:functest:sendEmail', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('c6e5f363977c052a6a6d6098b0736c8e', '42708c34ab41a691ef032ab1f4cf5db0', '定时任务日志删除', 11, NULL, NULL, NULL, 'monitor:jobLog:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('d5d81e35c103ed1dac93a70b97819877', '2', '接口限制', 4, 'apiLimit', NULL, 'monitor/apiLimit/index', 'monitor:apiLimit:list', 'lock', '0', '0', '2', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('ea70f3a842730a50686acc46fb981800', '42708c34ab41a691ef032ab1f4cf5db0', '定时任务日志列表', 8, NULL, NULL, NULL, 'monitor:jobLog:list', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('f37ba75d3347c084b50727ff35a386fa', 'd5d81e35c103ed1dac93a70b97819877', '限制状态修改', 5, NULL, NULL, NULL, 'monitor:apiLimit:changeStatus', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1029', '105', '字典删除', 4, '#', NULL, NULL, 'system:dict:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1033', '106', '参数修改', 3, '#', NULL, NULL, 'system:config:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1036', '107', '公告查询', 1, '#', NULL, NULL, 'monitor:notice:query', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1042', '500', '操作日志导出', 4, '#', NULL, NULL, 'monitor:logOper:export', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('106', '1', '参数设置', 7, 'config', NULL, 'system/config/index', 'system:config:list', 'edit', '0', '0', '2', '1', '0', '参数设置菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('13cbe1c8683a41dae7bccb289fd5dfd9', '42708c34ab41a691ef032ab1f4cf5db0', '任务修改', 3, NULL, NULL, NULL, 'monitor:job:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1506', '5', '测试树表', 1, 'dataTree', NULL, 'test/dataTree/index', 'test:dataTree:list', '#', '0', '0', '2', '1', '0', '测试树表菜单', '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1509', '1506', '测试树表修改', 3, '#', NULL, NULL, 'test:dataTree:edit', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1602', '118', '文件下载', 3, '#', NULL, NULL, 'monitor:file:download', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('1698eeba543e9f12542edba21ae2d16f', '42708c34ab41a691ef032ab1f4cf5db0', '任务删除', 4, NULL, NULL, NULL, 'monitor:job:remove', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);
INSERT INTO "public"."sys_menu" VALUES ('6971d165938715753fef8a07c112ba98', '42708c34ab41a691ef032ab1f4cf5db0', '任务新增', 2, NULL, NULL, NULL, 'monitor:job:add', '#', '0', '0', '3', '1', '0', NULL, '1', '2023-05-23 11:09:22', '1', '2023-05-23 11:09:22', 0);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_notice";
CREATE TABLE "public"."sys_notice" (
                                       "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                       "title" varchar(255) COLLATE "pg_catalog"."default",
                                       "content" text COLLATE "pg_catalog"."default",
                                       "type" varchar(2) COLLATE "pg_catalog"."default",
                                       "status" varchar(2) COLLATE "pg_catalog"."default",
                                       "remark" varchar(255) COLLATE "pg_catalog"."default",
                                       "create_id" varchar(32) COLLATE "pg_catalog"."default",
                                       "create_time" timestamp(6),
                                       "update_id" varchar(32) COLLATE "pg_catalog"."default",
                                       "update_time" timestamp(6),
                                       "is_delete" int4
)
;
COMMENT ON COLUMN "public"."sys_notice"."title" IS '标题';
COMMENT ON COLUMN "public"."sys_notice"."content" IS '内容';
COMMENT ON COLUMN "public"."sys_notice"."type" IS '类型，1=通知，2=公告';
COMMENT ON COLUMN "public"."sys_notice"."status" IS '状态，0=正常，1=关闭';
COMMENT ON COLUMN "public"."sys_notice"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_notice"."create_id" IS '创建人';
COMMENT ON COLUMN "public"."sys_notice"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_notice"."update_id" IS '修改人';
COMMENT ON COLUMN "public"."sys_notice"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_notice"."is_delete" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "public"."sys_notice" IS '通知公告表';

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO "public"."sys_notice" VALUES ('5f1d37b8a574402ff9ebfa9467020f82', '学思想、强党性、重实践、建新功', '<p><a href="https://www.baidu.com/" rel="noopener noreferrer" target="_blank"><img src="https://mbdp01.bdstatic.com/static/landing-pc/img/logo_top.79fdb8c2.png" alt="到百度首页"></a></p><p><br></p><p><a href="https://www.baidu.com/" rel="noopener noreferrer" target="_blank" style="color: rgb(0, 0, 0);">百度首页</a></p><p><a href="https://passport.baidu.com/v2/?login&amp;tpl=mn&amp;u=https%3A%2F%2Fbaijiahao.baidu.com%2Fs%3Fid%3D1763629368639186117" rel="noopener noreferrer" target="_blank" style="background-color: rgb(78, 113, 242); color: rgb(255, 255, 255);">登录</a></p><p>学思想、强党性、重实践、建新功｜汲取奋发进取的智慧和力量——各地认真推动学习贯彻习近平新时代中国特色社会主义思想主题教育走深走实</p><p><a href="https://author.baidu.com/home?from=bjh_article&amp;app_id=1537196318595058" rel="noopener noreferrer" target="_blank"><img src="https://gips0.baidu.com/it/u=219222052,3378137952&amp;fm=3012&amp;app=3012&amp;autime=1681354058&amp;size=b200,200"></a></p><p><a href="https://author.baidu.com/home?from=bjh_article&amp;app_id=1537196318595058" rel="noopener noreferrer" target="_blank" style="color: rgb(0, 0, 0);">新华网</a></p><p>2023-04-20 02:13</p><p>新华网官方帐号</p><p class="ql-align-center"><span style="color: rgb(255, 255, 255);">关注</span></p><p>新华社北京4月19日电&nbsp;<strong style="color: rgb(51, 51, 51);">题：汲取奋发进取的智慧和力量——各地认真推动学习贯彻习近平新时代中国特色社会主义思想主题教育走深走实</strong></p><p>新华社记者</p><p>学习贯彻习近平新时代中国特色社会主义思想主题教育工作会议4月3日在北京召开后，各地认真学习领会习近平总书记重要讲话精神，坚持把开展主题教育同贯彻落实党中央各项决策部署结合起来，同推动本地区本部门本单位的中心工作结合起来，认真推动主题教育走深走实。</p><p>近日，北京市召开学习贯彻习近平新时代中国特色社会主义思想主题教育工作会议强调，以严谨务实的工作作风和昂扬奋进的精神状态，坚持首善标准，高质量开展好主题教育。“牢记‘看北京首先要从政治上看’的要求，自觉把‘两个确立’融入血脉、见之行动，为党中央站好岗、放好哨。通过主题教育，我们要努力让新时代‘枫桥经验’不断结出累累硕果。”北京市公安局相关负责人说。</p><p>为推动主题教育走深走实，重庆在制定实施方案时抓纲带目，突出“五个一”，即贯穿一条党的创新理论武装的主线，建立一套迭代升级清单，实施一系列发展攻坚行动，健全一个推动落实体系，推广一批学习运用党的创新理论推动实践的先进典型。西部陆海新通道建设是重庆“实施一系列发展攻坚行动”的重要内容，下一步当地将努力把物流网络拓展到更多国家和地区，加快发展通道经济，切实做到学思想、强党性、重实践、建新功。</p><p>黑龙江紧扣振兴发展和现代化强省建设实际，制定出台全省主题教育实施方案和第一批工作方案、大兴调查研究实施方案、“牢记嘱托、全面振兴”系列微党课工作方案、省委常委班子工作方案等4个配套方案。目前，黑龙江全省第一批单位领导班子成员已确定调研课题831个。</p><p>4月10日，天津主题教育动员部署会召开，强调市委常委班子带头推动包括京津冀协同发展走深走实、科教兴市人才强市、制造业高质量发展等“十项行动”，通过主题教育进一步把准行动方向、细化行动路径、提升行动效果。</p><p>主题教育是一件事关全局的大事，时间紧、任务重、要求高，必须谋划好、组织好、落实好。为加强对所属地区、部门和单位的督促指导，各省区市党委和行业系统主管部门党组（党委）派出巡回指导组。</p><p>在广西，按照政治过硬、能力过硬、作风过硬和党内集中教育工作经验丰富的标准，从全自治区抽调的113名同志组成16个巡回指导组。截至4月18日，指导组已指导85家单位召开工作会议，全区主题教育第一批186家单位将于4月20日前全部完成启动。广西壮族自治区党委主题教育领导小组办公室副主任莫诗浦表示，巡回指导工作将突出精准指导，坚持同题共答，用好正反典型，督在点子上、导在关键处，确保主题教育取得实效。</p><p>开展主题教育以来，福建省直机关掀起读原著、学原文、悟原理的又一轮热潮。福建省公安厅有关负责人介绍，省公安厅组织党员干部认真研读主题教育指定学习书目和福建省特色学习书目，将开展主题教育与学习贯彻党的二十大精神、大兴调查研究之风有机结合，奋力推进福建公安工作现代化。</p><p>坚持多思多想、学深悟透，在新疆，自治区党委常委会制定开展主题教育的工作方案及理论学习、调查研究、检视问题、整改落实等方案，做到“规定动作”一个不少、“自选动作”务实管用。自治区政协办公厅会议活动处处长唐志荣表示，要弘扬优良学风，抓好支部学习，把学习贯彻习近平新时代中国特色社会主义思想主题教育成效体现在工作实践中。</p><p class="ql-align-right"><span style="color: rgb(78, 110, 242);">举报/反馈</span></p><p>大家都在搜</p><p><a href="https://baidu.com/s?word=%E5%A2%9E%E5%BC%BA%E7%94%A8%E5%85%9A%E7%9A%84%E4%BB%80%E4%B9%88%E6%94%BF%E6%B2%BB%E8%A7%89%E6%82%9F&amp;rsv_dl=feed_landingpage_rs&amp;from=1020853i&amp;rsf=" rel="noopener noreferrer" target="_blank" style="background-color: rgb(245, 245, 245); color: rgb(51, 51, 51);">增强用党的什么政治觉悟</a><a href="https://baidu.com/s?word=%E5%8A%A0%E5%BC%BA%E6%80%9D%E6%83%B3%E6%94%B9%E9%80%A0%2C%E6%8F%90%E9%AB%98%E5%85%9A%E6%80%A7%E4%BF%AE%E5%85%BB&amp;rsv_dl=feed_landingpage_rs&amp;from=1020853i&amp;rsf=" rel="noopener noreferrer" target="_blank" style="background-color: rgb(245, 245, 245); color: rgb(51, 51, 51);">加强思想改造,提高党性修养</a><a href="https://baidu.com/s?word=%E6%8A%93%E6%80%9D%E6%83%B3%E5%BB%BA%E8%AE%BE%2C%E6%8F%90%E5%8D%87&amp;rsv_dl=feed_landingpage_rs&amp;from=1020853i&amp;rsf=" rel="noopener noreferrer" target="_blank" style="background-color: rgb(245, 245, 245); color: rgb(51, 51, 51);">抓思想建设,提升</a><a href="https://baidu.com/s?word=%E5%A2%9E%E5%BC%BA%2C%E5%9D%9A%E5%AE%9A%2C%E5%81%9A%E5%88%B0%2C%E5%A2%9E%E5%BC%BA%E5%85%9A%E6%80%A7%E6%8F%90%E9%AB%98%E7%B4%A0%E8%B4%A8&amp;rsv_dl=feed_landingpage_rs&amp;from=1020853i&amp;rsf=" rel="noopener noreferrer" target="_blank" style="background-color: rgb(245, 245, 245); color: rgb(51, 51, 51);">增强,坚定,做到,增强党性提高素质</a><a href="https://baidu.com/s?word=%E5%85%9A%E6%80%A7%E5%BE%97%E5%88%B0%E4%BA%86%E5%8D%87%E5%8D%8E%2C%E6%80%9D%E6%83%B3%E5%BE%97%E5%88%B0%E6%8F%90%E5%8D%87&amp;rsv_dl=feed_landingpage_rs&amp;from=1020853i&amp;rsf=" rel="noopener noreferrer" target="_blank" style="background-color: rgb(245, 245, 245); color: rgb(51, 51, 51);">党性得到了升华,思想得到提升</a><a href="https://baidu.com/s?word=%E4%B8%8D%E6%96%AD%E9%94%A4%E7%82%BC%E5%85%9A%E6%80%A7%E4%BF%AE%E5%85%BB%E6%8F%90%E5%8D%87%E6%94%BF%E6%B2%BB%E7%B4%A0%E5%85%BB&amp;rsv_dl=feed_landingpage_rs&amp;from=1020853i&amp;rsf=" rel="noopener noreferrer" target="_blank" style="background-color: rgb(245, 245, 245); color: rgb(51, 51, 51);">不断锤炼党性修养提升政治素养</a></p><h2>发表评论</h2><p><br></p><p><span style="background-color: rgb(78, 110, 242); color: rgb(255, 255, 255);">发表</span></p><p><br></p><h2>作者最新文章</h2><p><a href="https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9399559918850015222%22%7D&amp;n_type=1&amp;p_from=3" rel="noopener noreferrer" target="_blank"><img src="https://t11.baidu.com/it/app=106&amp;f=JPEG&amp;fm=30&amp;fmt=auto&amp;u=3293015186%2C202157797?w=312&amp;h=208&amp;s=CC7220D7D99FF0C846741A6C0300B070"></a></p><p><a href="https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9399559918850015222%22%7D&amp;n_type=1&amp;p_from=3" rel="noopener noreferrer" target="_blank">新华调查：“答非所问”“妨碍维权”……部分“智能客服”不智能现象咋破解？</a></p><p><span style="color: rgb(145, 149, 163);">2分钟前</span></p><p><span style="color: rgb(145, 149, 163);">1阅读</span><a href="https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9694995450663559217%22%7D&amp;n_type=1&amp;p_from=3" rel="noopener noreferrer" target="_blank"><img src="https://t10.baidu.com/it/app=106&amp;f=JPEG&amp;fm=30&amp;fmt=auto&amp;u=1425908862%2C202157115?w=312&amp;h=208&amp;s=F4E866F94EF2CEC01234663E03005056"></a></p><p><a href="https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9694995450663559217%22%7D&amp;n_type=1&amp;p_from=3" rel="noopener noreferrer" target="_blank">谷雨时节农事忙</a></p><p><span style="color: rgb(145, 149, 163);">14分钟前</span></p><p><span style="color: rgb(145, 149, 163);">4阅读</span><a href="https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9538110341145820946%22%7D&amp;n_type=1&amp;p_from=3" rel="noopener noreferrer" target="_blank"><img src="https://t10.baidu.com/it/app=106&amp;f=JPEG&amp;fm=30&amp;fmt=auto&amp;u=148978184%2C202157038?w=312&amp;h=208&amp;s=D89B38D606B1967D02E3EF810300E08C"></a></p><p><a href="https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9538110341145820946%22%7D&amp;n_type=1&amp;p_from=3" rel="noopener noreferrer" target="_blank">新华全媒+｜加强国际合作 打通“最后一公里”——博鳌亚洲论坛健康产业国际论坛分论坛嘉宾共话消灭脊髓灰质炎</a></p><p><span style="color: rgb(145, 149, 163);">14分钟前</span></p><p><span style="color: rgb(145, 149, 163);">12阅读</span></p><h2>相关推荐</h2><p><br></p><p><a href="https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9933733552797237279%22%7D&amp;n_type=1&amp;p_from=4" rel="noopener noreferrer" target="_blank"><img src="https://t11.baidu.com/it/app=106&amp;f=JPEG&amp;fm=30&amp;fmt=auto&amp;u=4172481808%2C202136968?w=312&amp;h=208&amp;s=890060DD0433098809A5A810030010D3"></a></p><p><a href="https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9933733552797237279%22%7D&amp;n_type=1&amp;p_from=4" rel="noopener noreferrer" target="_blank">星火小学：赓续革命先烈精神，筑牢国家安全意识</a></p><p><a href="https://author.baidu.com/home?from=bjh_article&amp;app_id=1626433114323248" rel="noopener noreferrer" target="_blank" style="color: rgb(145, 149, 163);">湖南日报</a></p><p><a href="https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9546340671064833618%22%7D&amp;n_type=1&amp;p_from=4" rel="noopener noreferrer" target="_blank"><img src="https://t11.baidu.com/it/app=106&amp;f=JPEG&amp;fm=30&amp;fmt=auto&amp;u=2984651613%2C202135007?w=312&amp;h=208&amp;s=FF10698E646618AC76106C9F0300C082"></a></p><p><a href="https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9546340671064833618%22%7D&amp;n_type=1&amp;p_from=4" rel="noopener noreferrer" target="_blank">后冬奥时代持续发力 未来4个赛季北京将办11项国际滑联赛事</a></p><p><a href="https://author.baidu.com/home?from=bjh_article&amp;app_id=1601149438053974" rel="noopener noreferrer" target="_blank" style="color: rgb(145, 149, 163);">北京日报客户端</a></p><p><a href="https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9425631998140838769%22%7D&amp;n_type=1&amp;p_from=4" rel="noopener noreferrer" target="_blank"><img src="https://t12.baidu.com/it/app=106&amp;f=JPEG&amp;fm=30&amp;fmt=auto&amp;u=1940926112%2C202126133?w=312&amp;h=208&amp;s=5F9436C01053C3DE14B135590300D0D0"></a></p><p><a href="https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9425631998140838769%22%7D&amp;n_type=1&amp;p_from=4" rel="noopener noreferrer" target="_blank">乡村振兴的“京山样板”：打造“万企兴万村”试验田</a></p><p><a href="https://author.baidu.com/home?from=bjh_article&amp;app_id=1563462235469851" rel="noopener noreferrer" target="_blank" style="color: rgb(145, 149, 163);">光明网</a></p><p><a href="https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9551396633547016984%22%7D&amp;n_type=1&amp;p_from=4" rel="noopener noreferrer" target="_blank"><img src="https://t12.baidu.com/it/app=106&amp;f=JPEG&amp;fm=30&amp;fmt=auto&amp;u=2434219870%2C202119048?w=312&amp;h=208&amp;s=5DA582511FE3C14756B5A4C90300E0B3"></a></p><p><a href="https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9551396633547016984%22%7D&amp;n_type=1&amp;p_from=4" rel="noopener noreferrer" target="_blank">三穗县桐林镇鹿洞中心村列入州级乡村振兴示范点名单</a></p><p><a href="https://author.baidu.com/home?from=bjh_article&amp;app_id=1618362010206479" rel="noopener noreferrer" target="_blank" style="color: rgb(145, 149, 163);">天眼新闻</a></p><p><a href="https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_10340493477270232986%22%7D&amp;n_type=1&amp;p_from=4" rel="noopener noreferrer" target="_blank"><img src="https://t11.baidu.com/it/app=106&amp;f=JPEG&amp;fm=30&amp;fmt=auto&amp;u=2128214718%2C202119104?w=312&amp;h=208&amp;s=94915F94C327E2ED0485C159030030E6"></a></p><p><a href="https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_10340493477270232986%22%7D&amp;n_type=1&amp;p_from=4" rel="noopener noreferrer" target="_blank">联合国中文日｜在谷雨这天，感受中文之美</a></p><p><a href="https://author.baidu.com/home?from=bjh_article&amp;app_id=1563462235469851" rel="noopener noreferrer" target="_blank" style="color: rgb(145, 149, 163);">光明网</a></p><p><a href="https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_10340493477270232986%22%7D&amp;n_type=1&amp;p_from=4" rel="noopener noreferrer" target="_blank" style="color: rgb(145, 149, 163);">1评论</a></p><p><span style="color: rgb(98, 102, 117);">换一换</span></p><ul><li><span style="color: rgb(254, 45, 70);">1</span></li><li><a href="https://www.baidu.com/s?wd=%E8%A2%AB%E2%80%9C%E9%A9%AF%E6%9C%8D%E2%80%9D%E7%9A%84%E5%8E%85%E5%AE%98%E6%B2%A6%E4%B8%BA%E2%80%9C%E7%83%9F%E9%85%92%E8%80%81%E6%9D%BF%E2%80%9D&amp;sa=fyb_news_feedpc&amp;rsv_dl=fyb_news_feedpc&amp;from=feedpc" rel="noopener noreferrer" target="_blank" style="color: rgb(51, 51, 51);">被“驯服”的厅官沦为“烟酒老板”</a></li><li><span style="background-color: rgb(255, 102, 0); color: rgb(255, 255, 255);">热</span></li><li><span style="color: rgb(255, 102, 0);">2</span></li><li><a href="https://www.baidu.com/s?wd=%E5%A5%B3%E5%AD%90%E5%9C%A8mini%E5%B1%95%E5%8F%B0%E7%9B%B4%E6%92%AD%E8%B5%B7%E5%86%B2%E7%AA%81+%E8%AD%A6%E6%96%B9%E5%9B%9E%E5%BA%94&amp;sa=fyb_news_feedpc&amp;rsv_dl=fyb_news_feedpc&amp;from=feedpc" rel="noopener noreferrer" target="_blank" style="color: rgb(51, 51, 51);">女子在mini展台直播起冲突 警方回应</a></li><li><span style="color: rgb(250, 169, 14);">3</span></li><li><a href="https://www.baidu.com/s?wd=%E5%85%A8%E5%9B%BD%E7%B4%AF%E8%AE%A1%E8%B6%8510%E4%BA%BF%E4%BA%A9%E7%9A%84%E9%AB%98%E6%A0%87%E5%87%86%E5%86%9C%E7%94%B0&amp;sa=fyb_news_feedpc&amp;rsv_dl=fyb_news_feedpc&amp;from=feedpc" rel="noopener noreferrer" target="_blank" style="color: rgb(51, 51, 51);">全国累计超10亿亩的高标准农田</a></li><li><span style="color: rgb(145, 149, 163);">4</span></li><li><a href="https://www.baidu.com/s?wd=SpaceX%E6%98%9F%E8%88%B0%E5%8F%91%E5%B0%84%E5%A4%B1%E8%B4%A5&amp;sa=fyb_news_feedpc&amp;rsv_dl=fyb_news_feedpc&amp;from=feedpc" rel="noopener noreferrer" target="_blank" style="color: rgb(51, 51, 51);">SpaceX星舰发射失败</a></li><li><span style="background-color: rgb(214, 26, 110); color: rgb(255, 255, 255);">爆</span></li><li><span style="color: rgb(145, 149, 163);">5</span></li><li><a href="https://www.baidu.com/s?wd=48%E5%B2%81%E5%86%BB%E9%BE%84%E5%A6%88%E5%A6%88%E5%9B%9E%E5%BA%94%E6%95%B4%E5%AE%B9%E8%B4%A8%E7%96%91&amp;sa=fyb_news_feedpc&amp;rsv_dl=fyb_news_feedpc&amp;from=feedpc" rel="noopener noreferrer" target="_blank" style="color: rgb(51, 51, 51);">48岁冻龄妈妈回应整容质疑</a></li><li><span style="color: rgb(145, 149, 163);">6</span></li><li><a href="https://www.baidu.com/s?wd=%E5%86%B0%E6%B7%87%E6%B7%8B%E5%8F%AA%E7%BB%99%E5%A4%96%E5%9B%BD%E4%BA%BA%EF%BC%9F%E5%AE%9D%E9%A9%ACmini%E9%81%93%E6%AD%89&amp;sa=fyb_news_feedpc&amp;rsv_dl=fyb_news_feedpc&amp;from=feedpc" rel="noopener noreferrer" target="_blank" style="color: rgb(51, 51, 51);">冰淇淋只给外国人？宝马mini道歉</a></li><li><span style="background-color: rgb(255, 102, 0); color: rgb(255, 255, 255);">热</span></li><li><span style="color: rgb(145, 149, 163);">7</span></li><li><a href="https://www.baidu.com/s?wd=%E5%9B%A0%E5%A7%93%E6%B0%8F%E5%A4%AA%E7%BD%95%E8%A7%81+%E5%85%A8%E6%9D%91%E9%9B%86%E4%BD%93%E6%94%B9%E5%A7%93%E2%80%9C%E9%B8%AD%E2%80%9D&amp;sa=fyb_news_feedpc&amp;rsv_dl=fyb_news_feedpc&amp;from=feedpc" rel="noopener noreferrer" target="_blank" style="color: rgb(51, 51, 51);">因姓氏太罕见 全村集体改姓“鸭”</a></li><li><span style="color: rgb(145, 149, 163);">8</span></li><li><a href="https://www.baidu.com/s?wd=%E4%BB%8A%E5%B9%B4%E7%AC%AC1%E5%8F%B7%E5%8F%B0%E9%A3%8E%E2%80%9C%E7%8F%8A%E7%91%9A%E2%80%9D%E7%94%9F%E6%88%90&amp;sa=fyb_news_feedpc&amp;rsv_dl=fyb_news_feedpc&amp;from=feedpc" rel="noopener noreferrer" target="_blank" style="color: rgb(51, 51, 51);">今年第1号台风“珊瑚”生成</a></li><li><span style="color: rgb(145, 149, 163);">9</span></li><li><a href="https://www.baidu.com/s?wd=%E5%8F%B0%E5%8C%97101%E5%A4%A7%E6%A5%BC%E9%81%AD%E9%9B%B7%E5%87%BB%E7%9E%AC%E9%97%B4%E6%9B%9D%E5%85%89&amp;sa=fyb_news_feedpc&amp;rsv_dl=fyb_news_feedpc&amp;from=feedpc" rel="noopener noreferrer" target="_blank" style="color: rgb(51, 51, 51);">台北101大楼遭雷击瞬间曝光</a></li><li><span style="color: rgb(145, 149, 163);">10</span></li><li><a href="https://www.baidu.com/s?wd=%E3%80%8A%E7%81%8C%E7%AF%AE%E9%AB%98%E6%89%8B%E3%80%8B%E7%83%AD%E6%98%A0+%E4%BD%A0%E4%B8%BA%E9%9D%92%E6%98%A5%E8%A1%A5%E7%A5%A8%E5%90%97&amp;sa=fyb_news_feedpc&amp;rsv_dl=fyb_news_feedpc&amp;from=feedpc" rel="noopener noreferrer" target="_blank" style="color: rgb(51, 51, 51);">《灌篮高手》热映 你为青春补票吗</a></li></ul><p><a href="https://www.baidu.com/cache/sethelp/index.html" rel="noopener noreferrer" target="_blank" style="color: rgb(153, 153, 153);">设为首页</a></p><p>©&nbsp;Baidu&nbsp;</p><p><a href="https://www.baidu.com/duty/" rel="noopener noreferrer" target="_blank" style="color: rgb(153, 153, 153);">使用百度前必读</a></p><p><a href="http://jianyi.baidu.com/" rel="noopener noreferrer" target="_blank" style="color: rgb(153, 153, 153);">意见反馈</a></p><p>京ICP证030173号&nbsp;<img src="https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/copy_rignt_8.png" height="16" width="13"><a href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=11000002000001" rel="noopener noreferrer" target="_blank" style="color: rgb(153, 153, 153);">京公网安备11000002000001号</a></p>', '1', '0', NULL, '1', '2023-04-20 22:42:24', '1', '2023-04-20 22:42:24', 0);
INSERT INTO "public"."sys_notice" VALUES ('5f98161ae8c06f99fbdcbb267908da58', '快讯！外媒：美国“星舰”发射时发生爆炸', '<p>【环球网快讯】据英国《每日电讯报》刚刚消息，由美国太空探索技术公司（SpaceX）研发的最新一代运载火箭系统“星舰”当时时间20日再次发射，但在发射三分钟后，“超重型推进器”部分似乎未能分离，在高空发生爆炸。</p><p><br></p><p>SpaceX官网介绍称，“星舰”火箭系统第一级助推器被称为“超重型推进器”，第二级被称为“星舰”飞船。其中“超重型推进器”高达70米，直径9米，配置33台“猛禽”发动机，使用液态甲烷/液氧推进剂，安装4个固定栅格翼，不配置着陆腿，SpaceX公司计划采用地面塔架“捕捉”的形式进行回收。</p>', '2', '1', NULL, '1', '2023-04-20 22:43:09', '1', '2023-04-20 22:43:25', 0);

-- ----------------------------
-- Table structure for sys_oss_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_oss_config";
CREATE TABLE "public"."sys_oss_config" (
                                           "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                           "name" varchar(255) COLLATE "pg_catalog"."default",
                                           "access_key" varchar(255) COLLATE "pg_catalog"."default",
                                           "secret_key" varchar(255) COLLATE "pg_catalog"."default",
                                           "bucket_name" varchar(255) COLLATE "pg_catalog"."default",
                                           "prefix" varchar(255) COLLATE "pg_catalog"."default",
                                           "endpoint" varchar(255) COLLATE "pg_catalog"."default",
                                           "domain" varchar(255) COLLATE "pg_catalog"."default",
                                           "is_https" char(2) COLLATE "pg_catalog"."default",
                                           "region" varchar(255) COLLATE "pg_catalog"."default",
                                           "access_policy" char(2) COLLATE "pg_catalog"."default",
                                           "in_use" varchar(2) COLLATE "pg_catalog"."default",
                                           "status" varchar(2) COLLATE "pg_catalog"."default",
                                           "remark" varchar(255) COLLATE "pg_catalog"."default",
                                           "create_id" varchar(32) COLLATE "pg_catalog"."default",
                                           "create_time" timestamp(6),
                                           "update_id" varchar(32) COLLATE "pg_catalog"."default",
                                           "update_time" timestamp(6),
                                           "is_delete" int4
)
;
COMMENT ON COLUMN "public"."sys_oss_config"."name" IS '配置名称，例如：minio、阿里云、腾讯云、七牛云、京东云、华为云';
COMMENT ON COLUMN "public"."sys_oss_config"."access_key" IS 'accessKey';
COMMENT ON COLUMN "public"."sys_oss_config"."secret_key" IS 'secretKey';
COMMENT ON COLUMN "public"."sys_oss_config"."bucket_name" IS '存储桶名称';
COMMENT ON COLUMN "public"."sys_oss_config"."prefix" IS '前缀';
COMMENT ON COLUMN "public"."sys_oss_config"."endpoint" IS '访问站点endpoint';
COMMENT ON COLUMN "public"."sys_oss_config"."domain" IS '自定义域名';
COMMENT ON COLUMN "public"."sys_oss_config"."is_https" IS '是否https，0=否，1=是';
COMMENT ON COLUMN "public"."sys_oss_config"."region" IS '地域';
COMMENT ON COLUMN "public"."sys_oss_config"."access_policy" IS '存储桶权限类型，0=private，1=public，2=custom';
COMMENT ON COLUMN "public"."sys_oss_config"."in_use" IS '正在使用，0=否，1=是（在所有数据中，只有一条数据可是正在使用）';
COMMENT ON COLUMN "public"."sys_oss_config"."status" IS '配置状态，0=正常，1=禁用';
COMMENT ON COLUMN "public"."sys_oss_config"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_oss_config"."create_id" IS '创建人';
COMMENT ON COLUMN "public"."sys_oss_config"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_oss_config"."update_id" IS '修改人';
COMMENT ON COLUMN "public"."sys_oss_config"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_oss_config"."is_delete" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "public"."sys_oss_config" IS 'OSS配置表';

-- ----------------------------
-- Records of sys_oss_config
-- ----------------------------
INSERT INTO "public"."sys_oss_config" VALUES ('02a4c0e40c271fd516ea3ea566ade22e', '七牛云', '1', '13m', 'my-dev-test', 'MyAdmin', 's3-cn-north-1.qiniucs.com', 'qiniu.daenx.cn', '1 ', 'cn-north-1', '1 ', '0', '0', NULL, '1', '2023-04-13 22:38:26', '1', '2023-04-19 22:12:21', 0);
INSERT INTO "public"."sys_oss_config" VALUES ('3c59bfcc59b182930fe2d9c41246c50a', 'minio', '2', '2', 'test', 'MyAdmin', '127.0.0.1:9000', '', '0 ', '', '1 ', '1', '0', NULL, '1', '2023-04-16 17:03:47', '1', '2023-04-19 22:12:21', 0);
INSERT INTO "public"."sys_oss_config" VALUES ('k8jowsd604opw17q6hs8gze05s2ybpqr', '腾讯云', '3', '3', 'daen-1251663445', 'MyAdmin', 'cos.ap-nanjing.myqcloud.com', '', '0 ', 'ap-nanjing', '1 ', '0', '0', NULL, '1', '2023-04-16 20:06:31', '1', '2023-04-19 22:12:21', 0);

-- ----------------------------
-- Table structure for sys_position
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_position";
CREATE TABLE "public"."sys_position" (
                                         "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                         "name" varchar(255) COLLATE "pg_catalog"."default",
                                         "code" varchar(255) COLLATE "pg_catalog"."default",
                                         "summary" text COLLATE "pg_catalog"."default",
                                         "sort" int4,
                                         "status" varchar(2) COLLATE "pg_catalog"."default",
                                         "remark" varchar(255) COLLATE "pg_catalog"."default",
                                         "create_id" varchar(32) COLLATE "pg_catalog"."default",
                                         "create_time" timestamp(6),
                                         "update_id" varchar(32) COLLATE "pg_catalog"."default",
                                         "update_time" timestamp(6),
                                         "is_delete" int4
)
;
COMMENT ON COLUMN "public"."sys_position"."name" IS '岗位名称';
COMMENT ON COLUMN "public"."sys_position"."code" IS '岗位编码';
COMMENT ON COLUMN "public"."sys_position"."summary" IS '岗位简介';
COMMENT ON COLUMN "public"."sys_position"."sort" IS '排序';
COMMENT ON COLUMN "public"."sys_position"."status" IS '岗位状态，0=正常，1=禁用';
COMMENT ON COLUMN "public"."sys_position"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_position"."create_id" IS '创建人';
COMMENT ON COLUMN "public"."sys_position"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_position"."update_id" IS '修改人';
COMMENT ON COLUMN "public"."sys_position"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_position"."is_delete" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "public"."sys_position" IS '岗位表';

-- ----------------------------
-- Records of sys_position
-- ----------------------------
INSERT INTO "public"."sys_position" VALUES ('1', '董事长', 'ceo', '负责公司的发展等', 1, '0', NULL, '1', '2023-04-19 22:24:04', '1', '2023-04-19 22:24:04', 0);
INSERT INTO "public"."sys_position" VALUES ('2', '项目经理', 'se', NULL, 2, '0', NULL, '1', '2023-04-16 23:55:15', '1', '2023-04-16 23:55:15', 0);
INSERT INTO "public"."sys_position" VALUES ('3', '人力资源', 'hr', NULL, 3, '0', NULL, '1', '2023-03-29 10:38:22', '1', '2023-03-29 10:38:22', 0);
INSERT INTO "public"."sys_position" VALUES ('4', '普通员工', 'user', NULL, 4, '0', NULL, '1', '2023-04-19 22:23:37', '1', '2023-04-19 22:23:37', 0);

-- ----------------------------
-- Table structure for sys_position_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_position_user";
CREATE TABLE "public"."sys_position_user" (
                                              "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                              "position_id" varchar(32) COLLATE "pg_catalog"."default",
                                              "user_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_position_user"."position_id" IS '岗位ID';
COMMENT ON COLUMN "public"."sys_position_user"."user_id" IS '用户ID';
COMMENT ON TABLE "public"."sys_position_user" IS '岗位用户关联表';

-- ----------------------------
-- Records of sys_position_user
-- ----------------------------
INSERT INTO "public"."sys_position_user" VALUES ('1', '1', '1');
INSERT INTO "public"."sys_position_user" VALUES ('2', '3', '1');
INSERT INTO "public"."sys_position_user" VALUES ('42e86a19ad715b820d5c8cf7b3b72903', '4', '1650855280223846402');
INSERT INTO "public"."sys_position_user" VALUES ('717fdf27dcdd03d408eb36a1ef984758', '4', '2');
INSERT INTO "public"."sys_position_user" VALUES ('b3c2b07469aaf75e3a713a26ace28fee', '3', '2');
INSERT INTO "public"."sys_position_user" VALUES ('ca85f7ed198da507a18615493bcb88ae', '4', '1657562040456413185');
INSERT INTO "public"."sys_position_user" VALUES ('f0f6c52d96ddb3348c09119bf8bab858', '4', '1640613543627415553');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role";
CREATE TABLE "public"."sys_role" (
                                     "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                     "name" varchar(255) COLLATE "pg_catalog"."default",
                                     "code" varchar(255) COLLATE "pg_catalog"."default",
                                     "sort" int4,
                                     "data_scope" varchar(2) COLLATE "pg_catalog"."default",
                                     "menu_check_strictly" int4,
                                     "dept_check_strictly" int4,
                                     "status" varchar(2) COLLATE "pg_catalog"."default",
                                     "remark" varchar(255) COLLATE "pg_catalog"."default",
                                     "create_id" varchar(32) COLLATE "pg_catalog"."default",
                                     "create_time" timestamp(6),
                                     "update_id" varchar(32) COLLATE "pg_catalog"."default",
                                     "update_time" timestamp(6),
                                     "is_delete" int4
)
;
COMMENT ON COLUMN "public"."sys_role"."name" IS '角色名称';
COMMENT ON COLUMN "public"."sys_role"."code" IS '角色编码';
COMMENT ON COLUMN "public"."sys_role"."sort" IS '排序';
COMMENT ON COLUMN "public"."sys_role"."data_scope" IS '数据权限，0=本人数据，1=本部门数据，2=本部门及以下数据，3=全部数据，4=自定义权限';
COMMENT ON COLUMN "public"."sys_role"."menu_check_strictly" IS '菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）';
COMMENT ON COLUMN "public"."sys_role"."dept_check_strictly" IS '部门树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）';
COMMENT ON COLUMN "public"."sys_role"."status" IS '角色状态，0=正常，1=禁用';
COMMENT ON COLUMN "public"."sys_role"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_role"."create_id" IS '创建人';
COMMENT ON COLUMN "public"."sys_role"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_role"."update_id" IS '修改人';
COMMENT ON COLUMN "public"."sys_role"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_role"."is_delete" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "public"."sys_role" IS '角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO "public"."sys_role" VALUES ('1', '超级管理员', 'admin', 1, '3', 1, 1, '0', NULL, '1', '2023-03-31 21:04:06', '1', '2023-03-31 21:04:09', 0);
INSERT INTO "public"."sys_role" VALUES ('2', '普通用户', 'user', 2, '2', 1, 1, '0', NULL, '1', '2023-03-31 21:04:08', '1', '2023-06-28 10:12:48', 0);

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role_dept";
CREATE TABLE "public"."sys_role_dept" (
                                          "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                          "role_id" varchar(32) COLLATE "pg_catalog"."default",
                                          "dept_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_role_dept"."role_id" IS '角色ID';
COMMENT ON COLUMN "public"."sys_role_dept"."dept_id" IS '部门ID';
COMMENT ON TABLE "public"."sys_role_dept" IS '角色部门关联表';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role_menu";
CREATE TABLE "public"."sys_role_menu" (
                                          "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                          "role_id" varchar(32) COLLATE "pg_catalog"."default",
                                          "menu_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_role_menu"."role_id" IS '角色ID';
COMMENT ON COLUMN "public"."sys_role_menu"."menu_id" IS '菜单ID';
COMMENT ON TABLE "public"."sys_role_menu" IS '角色菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO "public"."sys_role_menu" VALUES ('010e144047516a84fc15cf639df13a76', '2', '1604');
INSERT INTO "public"."sys_role_menu" VALUES ('0442a38a627b41631433a2b8a134f460', '2', '1015');
INSERT INTO "public"."sys_role_menu" VALUES ('04967f1e50ae400476949b6d488e8368', '2', '1028');
INSERT INTO "public"."sys_role_menu" VALUES ('04992e2858f6174c89953bf9963394b1', '2', '106');
INSERT INTO "public"."sys_role_menu" VALUES ('059593dc769e38c8c334a4216c90c444', '2', '1039');
INSERT INTO "public"."sys_role_menu" VALUES ('083f98427d45d5254cd260aa36754923', '2', '118');
INSERT INTO "public"."sys_role_menu" VALUES ('096284fdae4ac4c45e0168a9cc09207f', '2', '103');
INSERT INTO "public"."sys_role_menu" VALUES ('09f15e83eb86899aa3016675831ff6e4', '2', '1508');
INSERT INTO "public"."sys_role_menu" VALUES ('0c2832d252a4519b89ea16f5d5667cd3', '2', '1507');
INSERT INTO "public"."sys_role_menu" VALUES ('0f18520aa1a03964afa0e90ef6f2d1de', '2', '3');
INSERT INTO "public"."sys_role_menu" VALUES ('134ed69e973e1f12e2cf05c23f456d47', '2', '1024');
INSERT INTO "public"."sys_role_menu" VALUES ('1455d3272bbf52deb1e4801102df8acc', '2', '937f0292079a4b62d9a26ebd5e8d57ed');
INSERT INTO "public"."sys_role_menu" VALUES ('17c1c7b2c455c59c7e1a035ccb4aa334', '2', '1002');
INSERT INTO "public"."sys_role_menu" VALUES ('17ff84f9922d1ec1d1c488000f7d9650', '2', '1021');
INSERT INTO "public"."sys_role_menu" VALUES ('1f73ea18235b80a6865faffba1ba871d', '2', '1032');
INSERT INTO "public"."sys_role_menu" VALUES ('20a95f48fd4de639ca9f426d37ad2e81', '2', '105');
INSERT INTO "public"."sys_role_menu" VALUES ('221ff7ffe9e3dc4ef90fb8ee4bd557ed', '2', '1042');
INSERT INTO "public"."sys_role_menu" VALUES ('23c9aabdb43a8178309a8681e1951683', '2', '1010');
INSERT INTO "public"."sys_role_menu" VALUES ('2481a08f1c848869dd814102febe1904', '2', '1');
INSERT INTO "public"."sys_role_menu" VALUES ('2dc1d7359c6687a6456802e1d3d9362b', '2', '1038');
INSERT INTO "public"."sys_role_menu" VALUES ('2ddb711279213abfe6cbd9cf7d78d445', '2', '1502');
INSERT INTO "public"."sys_role_menu" VALUES ('30fbfad02e7d9f1c1fd1092d56aefd91', '2', '1020');
INSERT INTO "public"."sys_role_menu" VALUES ('339ad61ae8cc820051c88da232db4d45', '2', '1003');
INSERT INTO "public"."sys_role_menu" VALUES ('3600c28e83efcf4ccb9c8df43777fa76', '2', '114');
INSERT INTO "public"."sys_role_menu" VALUES ('37f0f31815e06c4b2124194350fc2689', '2', '1501');
INSERT INTO "public"."sys_role_menu" VALUES ('39349f2070d298787e7a1531236d7d53', '2', '1029');
INSERT INTO "public"."sys_role_menu" VALUES ('3c5c0780039a7e16b2f38c2481b192d3', '2', '1027');
INSERT INTO "public"."sys_role_menu" VALUES ('41a2c7c92bf8436b727ba454fb9863a2', '2', '1505');
INSERT INTO "public"."sys_role_menu" VALUES ('46e194517968aa60dc6bdf44c4181429', '2', '69519bae5ffa67623e8bfb1c8fabdd00');
INSERT INTO "public"."sys_role_menu" VALUES ('4903d49065e23c68dc880db1248e6039', '2', 'b87a985b4ef717afe650f2df9cdbe087');
INSERT INTO "public"."sys_role_menu" VALUES ('49253b13004c15ad6c88064a787d6398', '2', '1006');
INSERT INTO "public"."sys_role_menu" VALUES ('4e8f3278c9db0b5f28a0b43e877162df', '2', '1605');
INSERT INTO "public"."sys_role_menu" VALUES ('58214e427dc03b0134f5283d4a5feccc', '2', 'c0a98c5b6e56602800968efcacfa4e47');
INSERT INTO "public"."sys_role_menu" VALUES ('598fb925ea65211e37ee2d976b43b03f', '2', '1506');
INSERT INTO "public"."sys_role_menu" VALUES ('5b044380ef24ff067934acd419c93935', '2', '2d562fa236077b42b5142eb869db6b18');
INSERT INTO "public"."sys_role_menu" VALUES ('5c431aaf26ee0ae6fc6544d141c00694', '2', '1040');
INSERT INTO "public"."sys_role_menu" VALUES ('64db475d623d29556ce6846e725e7778', '2', '1030');
INSERT INTO "public"."sys_role_menu" VALUES ('659645051d2c3a5f3186e95f2f02458b', '2', '6b70f47f76199b8cd35aae0cda15d449');
INSERT INTO "public"."sys_role_menu" VALUES ('660d48dceea79ebaa7ca29a4bc76e867', '2', '1500');
INSERT INTO "public"."sys_role_menu" VALUES ('6cc1d3c6030ac4a6daf52d301e8d5130', '2', '63854784d5ac52bfd2ac578a48f0a46f');
INSERT INTO "public"."sys_role_menu" VALUES ('6ce0aa79b167176bd10dc82cf4511321', '2', '1001');
INSERT INTO "public"."sys_role_menu" VALUES ('6d431f6df7d19e09ab4a157629e969f2', '2', '1005');
INSERT INTO "public"."sys_role_menu" VALUES ('710045b55eed9d79d4198984dead8359', '2', '1035');
INSERT INTO "public"."sys_role_menu" VALUES ('72d70a683fc836cf99daf5e161507ee2', '2', '1022');
INSERT INTO "public"."sys_role_menu" VALUES ('7356e376180d8a2795e75987312ce959', '2', '1007');
INSERT INTO "public"."sys_role_menu" VALUES ('764c287b3ccf95eac148f0a0e2e8c756', '2', '1504');
INSERT INTO "public"."sys_role_menu" VALUES ('79ced85499e46af653928e63f9cdd66a', '2', '1026');
INSERT INTO "public"."sys_role_menu" VALUES ('7bddfaf7e64262a85fc149c73b72b8eb', '2', '1013');
INSERT INTO "public"."sys_role_menu" VALUES ('7e8f1d0e77b174a93cd28665698a28b3', '2', '1008');
INSERT INTO "public"."sys_role_menu" VALUES ('80a71c4a9e21922bbe582a3e3b9fba5a', '2', '1023');
INSERT INTO "public"."sys_role_menu" VALUES ('828f59bcb7e306c68816468437469e61', '2', '501');
INSERT INTO "public"."sys_role_menu" VALUES ('8bd733bd34d3297fed3845be9c9466af', '2', '1034');
INSERT INTO "public"."sys_role_menu" VALUES ('8f77b9b099df75349d378a1eb42f41ec', '2', '1017');
INSERT INTO "public"."sys_role_menu" VALUES ('934ff858eebebf28fa2364d0dd7efdc5', '2', '1043');
INSERT INTO "public"."sys_role_menu" VALUES ('96d7b500a08728d11c4a2cb953598e56', '2', '1044');
INSERT INTO "public"."sys_role_menu" VALUES ('9a97fabed733a7dcba2000cac33de671', '2', '7a5af154f7d305797b56b9488207b04a');
INSERT INTO "public"."sys_role_menu" VALUES ('9daeb32b6f985ade43c2b0e052771ef2', '2', '100');
INSERT INTO "public"."sys_role_menu" VALUES ('a2ce0930df87870d78d4008e444689f8', '2', '5');
INSERT INTO "public"."sys_role_menu" VALUES ('a73949740b67262365b49d0f5ec75e17', '2', '1033');
INSERT INTO "public"."sys_role_menu" VALUES ('aa2f14fec48b6da5b4821ad22eb061a0', '2', '1011');
INSERT INTO "public"."sys_role_menu" VALUES ('aa30ec405c81e24a83226a22e1d426b9', '2', '1601');
INSERT INTO "public"."sys_role_menu" VALUES ('ab972376b8a9082cba0a7bd84c8a116d', '2', '1045');
INSERT INTO "public"."sys_role_menu" VALUES ('afa719c5d645a365bf910ebbb32b227c', '2', '108');
INSERT INTO "public"."sys_role_menu" VALUES ('b9e74e369f7e0f410a05f82ff51d19c8', '2', '102');
INSERT INTO "public"."sys_role_menu" VALUES ('c116881cb3c3fc012f396475d00b49f3', '2', '104');
INSERT INTO "public"."sys_role_menu" VALUES ('c2d6f3184eaae24dc784cb10b40e644f', '2', '1510');
INSERT INTO "public"."sys_role_menu" VALUES ('c49e969855bf0b98477e3301141b337e', '2', '39cfe621da13b55952f2af58edc3e53a');
INSERT INTO "public"."sys_role_menu" VALUES ('ca5968e0a84015865ce5b886f26640ec', '2', '111');
INSERT INTO "public"."sys_role_menu" VALUES ('ccc35335c199e05c8e5020444abef18b', '2', '500');
INSERT INTO "public"."sys_role_menu" VALUES ('cdf14f2dfc13bfbe684e39a97899bbdc', '2', '1014');
INSERT INTO "public"."sys_role_menu" VALUES ('d55aa1b25ec6b4db929793532b69e8b9', '2', '1037');
INSERT INTO "public"."sys_role_menu" VALUES ('d84c0d4fb4e50df5ee690e7e855960bd', '2', '1012');
INSERT INTO "public"."sys_role_menu" VALUES ('deb53079c64334ae7ef6edccddaba0b7', '2', '101');
INSERT INTO "public"."sys_role_menu" VALUES ('e1c98c8cf71a0307b8eda1a0a2f44f72', '2', '1025');
INSERT INTO "public"."sys_role_menu" VALUES ('e24d2b9811c6c9a7f9e41976b0b1335e', '2', '1019');
INSERT INTO "public"."sys_role_menu" VALUES ('e2c3126cf2799f8cc1e78ac900c5a46f', '2', '1004');
INSERT INTO "public"."sys_role_menu" VALUES ('e5a9d3ffeb6559c27992ee0b747cc91d', '2', '1509');
INSERT INTO "public"."sys_role_menu" VALUES ('e9870cd1a138158d6b2c1b902e25b4ce', '2', '1041');
INSERT INTO "public"."sys_role_menu" VALUES ('ea58cbc4210bf4810c8d86d9cbc404a6', '2', '1600');
INSERT INTO "public"."sys_role_menu" VALUES ('ea9d229b2a5c859f414528a5b2449194', '2', '1009');
INSERT INTO "public"."sys_role_menu" VALUES ('ed7f5e174396e092bedd878283b68318', '2', '2');
INSERT INTO "public"."sys_role_menu" VALUES ('ef34997f6f4c80b8377d6ec32cc0b948', '2', '1503');
INSERT INTO "public"."sys_role_menu" VALUES ('f169475beb8e05e90618bb89fb939f4f', '2', '1018');
INSERT INTO "public"."sys_role_menu" VALUES ('f28d06f07d1b32b4f6a2335d63f1337b', '2', '1031');
INSERT INTO "public"."sys_role_menu" VALUES ('f29d8b556b6d3f84e3c1099305bfaa0a', '2', '107');
INSERT INTO "public"."sys_role_menu" VALUES ('f404c15c05ff60c6b269afd153605d3a', '2', '1036');
INSERT INTO "public"."sys_role_menu" VALUES ('f4515d9717e0085d6cbcd68a73062bfb', '2', '1016');

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_role_user";
CREATE TABLE "public"."sys_role_user" (
                                          "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                          "role_id" varchar(32) COLLATE "pg_catalog"."default",
                                          "user_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_role_user"."role_id" IS '角色ID';
COMMENT ON COLUMN "public"."sys_role_user"."user_id" IS '用户ID';
COMMENT ON TABLE "public"."sys_role_user" IS '角色用户关联表';

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO "public"."sys_role_user" VALUES ('174eceae15692e5091c28f3d6a3a1f54', '2', '1650855280223846402');
INSERT INTO "public"."sys_role_user" VALUES ('1d6f2251b32492648a8b1c488f8a1f2f', '2', '1643141570206871554');
INSERT INTO "public"."sys_role_user" VALUES ('678a429aa947e14570e5077ebcea755a', '1', '1');
INSERT INTO "public"."sys_role_user" VALUES ('735afe9f516a5cf19e09326d05ea6190', '2', '1640613543627415553');
INSERT INTO "public"."sys_role_user" VALUES ('e12edec621a0f93ce2a9643d7e79b99e', '2', '1657562040456413185');
INSERT INTO "public"."sys_role_user" VALUES ('fed886b635dfcdb135ff7ca51b908861', '2', '2');

-- ----------------------------
-- Table structure for sys_user_dept
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user_dept";
CREATE TABLE "public"."sys_user_dept" (
                                          "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                          "user_id" varchar(32) COLLATE "pg_catalog"."default",
                                          "dept_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_user_dept"."user_id" IS '用户ID';
COMMENT ON COLUMN "public"."sys_user_dept"."dept_id" IS '部门ID';
COMMENT ON TABLE "public"."sys_user_dept" IS '角色用户关联表';

-- ----------------------------
-- Records of sys_user_dept
-- ----------------------------
INSERT INTO "public"."sys_user_dept" VALUES ('174eceae15692e5091c28f3d6a3a1f54', '1', '100');

-- ----------------------------
-- Table structure for sys_social
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_social";
CREATE TABLE "public"."sys_social" (
                                       "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                       "remark" varchar(255) COLLATE "pg_catalog"."default",
                                       "create_id" varchar(32) COLLATE "pg_catalog"."default",
                                       "create_time" timestamp(6),
                                       "update_id" varchar(32) COLLATE "pg_catalog"."default",
                                       "update_time" timestamp(6),
                                       "is_delete" int4,
                                       "uuid" varchar(255) COLLATE "pg_catalog"."default",
                                       "source" varchar(255) COLLATE "pg_catalog"."default",
                                       "access_token" varchar(255) COLLATE "pg_catalog"."default",
                                       "expire_in" int4,
                                       "refresh_token" varchar(255) COLLATE "pg_catalog"."default",
                                       "open_id" varchar(255) COLLATE "pg_catalog"."default",
                                       "uid" varchar(255) COLLATE "pg_catalog"."default",
                                       "access_code" varchar(255) COLLATE "pg_catalog"."default",
                                       "union_id" varchar(255) COLLATE "pg_catalog"."default",
                                       "scope" varchar(255) COLLATE "pg_catalog"."default",
                                       "token_type" varchar(255) COLLATE "pg_catalog"."default",
                                       "id_token" varchar(255) COLLATE "pg_catalog"."default",
                                       "mac_algorithm" varchar(255) COLLATE "pg_catalog"."default",
                                       "mac_key" varchar(255) COLLATE "pg_catalog"."default",
                                       "code" varchar(255) COLLATE "pg_catalog"."default",
                                       "oauth_token" varchar(255) COLLATE "pg_catalog"."default",
                                       "oauth_token_secret" varchar(255) COLLATE "pg_catalog"."default",
                                       "user_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."sys_social"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_social"."create_id" IS '创建人';
COMMENT ON COLUMN "public"."sys_social"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_social"."update_id" IS '修改人';
COMMENT ON COLUMN "public"."sys_social"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_social"."is_delete" IS '是否删除，0=正常，1=删除';
COMMENT ON COLUMN "public"."sys_social"."uuid" IS '第三方系统的唯一ID';
COMMENT ON COLUMN "public"."sys_social"."source" IS '第三方用户来源';
COMMENT ON COLUMN "public"."sys_social"."access_token" IS '用户的授权令牌';
COMMENT ON COLUMN "public"."sys_social"."expire_in" IS '第三方用户的授权令牌的有效期';
COMMENT ON COLUMN "public"."sys_social"."refresh_token" IS '刷新令牌';
COMMENT ON COLUMN "public"."sys_social"."open_id" IS '第三方用户的 open id';
COMMENT ON COLUMN "public"."sys_social"."uid" IS '第三方用户的 ID';
COMMENT ON COLUMN "public"."sys_social"."access_code" IS '个别平台的授权信息';
COMMENT ON COLUMN "public"."sys_social"."union_id" IS '第三方用户的 union id';
COMMENT ON COLUMN "public"."sys_social"."scope" IS '第三方用户授予的权限';
COMMENT ON COLUMN "public"."sys_social"."token_type" IS '个别平台的授权信息';
COMMENT ON COLUMN "public"."sys_social"."id_token" IS 'id token';
COMMENT ON COLUMN "public"."sys_social"."mac_algorithm" IS '小米平台用户的附带属性';
COMMENT ON COLUMN "public"."sys_social"."mac_key" IS '小米平台用户的附带属性';
COMMENT ON COLUMN "public"."sys_social"."code" IS '用户的授权code';
COMMENT ON COLUMN "public"."sys_social"."oauth_token" IS 'Twitter平台用户的附带属性';
COMMENT ON COLUMN "public"."sys_social"."oauth_token_secret" IS 'Twitter平台用户的附带属性';
COMMENT ON COLUMN "public"."sys_social"."user_id" IS '关联用户ID';
COMMENT ON TABLE "public"."sys_social" IS '三方Auth绑定表（暂未启用）';

-- ----------------------------
-- Records of sys_social
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user";
CREATE TABLE "public"."sys_user" (
                                     "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                     "username" varchar(255) COLLATE "pg_catalog"."default",
                                     "password" varchar(255) COLLATE "pg_catalog"."default",
                                     "status" varchar(2) COLLATE "pg_catalog"."default",
                                     "phone" varchar(255) COLLATE "pg_catalog"."default",
                                     "email" varchar(255) COLLATE "pg_catalog"."default",
                                     "open_id" varchar(255) COLLATE "pg_catalog"."default",
                                     "api_key" varchar(255) COLLATE "pg_catalog"."default",
                                     "ban_to_time" timestamp(6),
                                     "expire_to_time" timestamp(6),
                                     "user_type" varchar(2) COLLATE "pg_catalog"."default",
                                     "remark" varchar(255) COLLATE "pg_catalog"."default",
                                     "create_id" varchar(32) COLLATE "pg_catalog"."default",
                                     "create_time" timestamp(6),
                                     "update_id" varchar(32) COLLATE "pg_catalog"."default",
                                     "update_time" timestamp(6),
                                     "is_delete" int4
)
;
COMMENT ON COLUMN "public"."sys_user"."username" IS '用户账号';
COMMENT ON COLUMN "public"."sys_user"."password" IS '用户密码';
COMMENT ON COLUMN "public"."sys_user"."status" IS '账号状态，0=正常，1=停用';
COMMENT ON COLUMN "public"."sys_user"."phone" IS '用户手机号';
COMMENT ON COLUMN "public"."sys_user"."email" IS '邮箱';
COMMENT ON COLUMN "public"."sys_user"."open_id" IS '微信open_id';
COMMENT ON COLUMN "public"."sys_user"."api_key" IS '开放API key';
COMMENT ON COLUMN "public"."sys_user"."ban_to_time" IS '锁定结束时间';
COMMENT ON COLUMN "public"."sys_user"."expire_to_time" IS '到期时间，null则永不过期';
COMMENT ON COLUMN "public"."sys_user"."user_type" IS '用户类型，具体看字典';
COMMENT ON COLUMN "public"."sys_user"."remark" IS '备注';
COMMENT ON COLUMN "public"."sys_user"."create_id" IS '创建人';
COMMENT ON COLUMN "public"."sys_user"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_user"."update_id" IS '修改人';
COMMENT ON COLUMN "public"."sys_user"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_user"."is_delete" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "public"."sys_user" IS '用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO "public"."sys_user" VALUES ('1', 'admin', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '0', '18755555555', '1330166565@qq.com', NULL, NULL, NULL, NULL, '1', '宇宙第一帅的人', '1', '2023-03-01 13:42:39', '1', '2023-05-14 16:31:39', 0);

-- ----------------------------
-- Table structure for sys_user_detail
-- ----------------------------
DROP TABLE IF EXISTS "public"."sys_user_detail";
CREATE TABLE "public"."sys_user_detail" (
                                            "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                            "user_id" varchar(32) COLLATE "pg_catalog"."default",
                                            "nick_name" varchar(255) COLLATE "pg_catalog"."default",
                                            "real_name" varchar(255) COLLATE "pg_catalog"."default",
                                            "age" varchar(255) COLLATE "pg_catalog"."default",
                                            "sex" varchar(255) COLLATE "pg_catalog"."default",
                                            "profile" text COLLATE "pg_catalog"."default",
                                            "user_sign" varchar(255) COLLATE "pg_catalog"."default",
                                            "avatar" varchar(255) COLLATE "pg_catalog"."default",
                                            "money" int4,
                                            "create_id" varchar(32) COLLATE "pg_catalog"."default",
                                            "create_time" timestamp(6),
                                            "update_id" varchar(32) COLLATE "pg_catalog"."default",
                                            "update_time" timestamp(6),
                                            "is_delete" int4
)
;
COMMENT ON COLUMN "public"."sys_user_detail"."user_id" IS '关联用户ID';
COMMENT ON COLUMN "public"."sys_user_detail"."nick_name" IS '用户昵称';
COMMENT ON COLUMN "public"."sys_user_detail"."real_name" IS '真实姓名';
COMMENT ON COLUMN "public"."sys_user_detail"."age" IS '年龄';
COMMENT ON COLUMN "public"."sys_user_detail"."sex" IS '性别，0=女，1=男，2=未知';
COMMENT ON COLUMN "public"."sys_user_detail"."profile" IS '个人简介';
COMMENT ON COLUMN "public"."sys_user_detail"."user_sign" IS '个性签名';
COMMENT ON COLUMN "public"."sys_user_detail"."avatar" IS '头像（文件ID）';
COMMENT ON COLUMN "public"."sys_user_detail"."money" IS '账户余额，单位分';
COMMENT ON COLUMN "public"."sys_user_detail"."create_id" IS '创建人';
COMMENT ON COLUMN "public"."sys_user_detail"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sys_user_detail"."update_id" IS '修改人';
COMMENT ON COLUMN "public"."sys_user_detail"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."sys_user_detail"."is_delete" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "public"."sys_user_detail" IS '用户信息表';

-- ----------------------------
-- Records of sys_user_detail
-- ----------------------------
INSERT INTO "public"."sys_user_detail" VALUES ('21366e78e6a156e9cf45061da65d06e5', '1', '我是管理员', 'Daen', '18', '1', '每一次离开都是为了更好的相遇
你好世界', '不一样的烟花', '625bcbc99e7524b543c81ffaed6dd2ab', 350, NULL, '2023-03-01 13:42:39', '1', '2023-06-26 15:49:37', 0);

-- ----------------------------
-- Table structure for test_data
-- ----------------------------
DROP TABLE IF EXISTS "public"."test_data";
CREATE TABLE "public"."test_data" (
                                      "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                      "title" varchar(255) COLLATE "pg_catalog"."default",
                                      "content" varchar(255) COLLATE "pg_catalog"."default",
                                      "type" varchar(2) COLLATE "pg_catalog"."default",
                                      "status" varchar(2) COLLATE "pg_catalog"."default",
                                      "remark" varchar(255) COLLATE "pg_catalog"."default",
                                      "create_id" varchar(32) COLLATE "pg_catalog"."default",
                                      "create_time" timestamp(6),
                                      "update_id" varchar(32) COLLATE "pg_catalog"."default",
                                      "update_time" timestamp(6),
                                      "is_delete" int4
)
;
COMMENT ON COLUMN "public"."test_data"."type" IS '类型，0=民生，1=科技';
COMMENT ON COLUMN "public"."test_data"."status" IS '状态，0=正常，1=停用';
COMMENT ON COLUMN "public"."test_data"."remark" IS '备注';
COMMENT ON COLUMN "public"."test_data"."create_id" IS '创建人';
COMMENT ON COLUMN "public"."test_data"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."test_data"."update_id" IS '修改人';
COMMENT ON COLUMN "public"."test_data"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."test_data"."is_delete" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "public"."test_data" IS '测试数据';

-- ----------------------------
-- Records of test_data
-- ----------------------------
INSERT INTO "public"."test_data" VALUES ('8f05e1af476e3537e796c966ff4b7b53', '阿萨德', '阿萨德', '0', '0', '4', '1', '2023-06-03 10:56:21', '1', '2023-06-03 10:58:26', 0);

-- ----------------------------
-- Table structure for test_data_tree
-- ----------------------------
DROP TABLE IF EXISTS "public"."test_data_tree";
CREATE TABLE "public"."test_data_tree" (
                                           "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                           "parent_id" varchar(32) COLLATE "pg_catalog"."default",
                                           "title" varchar(255) COLLATE "pg_catalog"."default",
                                           "content" varchar(255) COLLATE "pg_catalog"."default",
                                           "type" varchar(2) COLLATE "pg_catalog"."default",
                                           "status" varchar(2) COLLATE "pg_catalog"."default",
                                           "remark" varchar(255) COLLATE "pg_catalog"."default",
                                           "create_id" varchar(32) COLLATE "pg_catalog"."default",
                                           "create_time" timestamp(6),
                                           "update_id" varchar(32) COLLATE "pg_catalog"."default",
                                           "update_time" timestamp(6),
                                           "is_delete" int4
)
;
COMMENT ON COLUMN "public"."test_data_tree"."parent_id" IS '父级ID，顶级为0';
COMMENT ON COLUMN "public"."test_data_tree"."type" IS '类型，0=民生，1=科技';
COMMENT ON COLUMN "public"."test_data_tree"."status" IS '状态，0=正常，1=停用';
COMMENT ON COLUMN "public"."test_data_tree"."remark" IS '备注';
COMMENT ON COLUMN "public"."test_data_tree"."create_id" IS '创建人';
COMMENT ON COLUMN "public"."test_data_tree"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."test_data_tree"."update_id" IS '修改人';
COMMENT ON COLUMN "public"."test_data_tree"."update_time" IS '修改时间';
COMMENT ON COLUMN "public"."test_data_tree"."is_delete" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "public"."test_data_tree" IS '测试树表数据';

-- ----------------------------
-- Records of test_data_tree
-- ----------------------------
INSERT INTO "public"."test_data_tree" VALUES ('2', '0', '我4-', '按时-', '1', '1', '更换-', '2', '2023-01-30 17:25:18', '1', '2023-07-04 11:02:19.654186', 0);
INSERT INTO "public"."test_data_tree" VALUES ('b53120f9af52dcdc229d42d8cb04623f', '2', '阿萨德', '阿萨德', '0', '1', NULL, '1', '2023-04-26 22:24:47', '1', '2023-07-04 11:02:19.686078', 0);
INSERT INTO "public"."test_data_tree" VALUES ('11', '1', '我3', '3', '0', '1', '导入1', '1', '2023-02-26 17:25:16', '1', '2023-07-04 11:02:42.578488', 0);
INSERT INTO "public"."test_data_tree" VALUES ('222', '11', '我2', '得过户', '1', '1', '个月', '1', '2023-02-15 17:25:14', '1', '2023-07-04 11:02:42.578488', 0);
INSERT INTO "public"."test_data_tree" VALUES ('1', '0', '我1', '阿萨德', '0', '0', '个', '1', '2023-02-10 17:25:11', '1', '2023-07-04 11:02:45.361928', 0);


-- ----------------------------
-- Primary Key structure for table sys_api_limit
-- ----------------------------
ALTER TABLE "public"."sys_api_limit" ADD CONSTRAINT "sys_api_limit_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_config
-- ----------------------------
ALTER TABLE "public"."sys_config" ADD CONSTRAINT "sys_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_dept
-- ----------------------------
ALTER TABLE "public"."sys_dept" ADD CONSTRAINT "sys_dept_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_dept_parent
-- ----------------------------
ALTER TABLE "public"."sys_dept_parent" ADD CONSTRAINT "sys_dept_parent_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_dict
-- ----------------------------
ALTER TABLE "public"."sys_dict" ADD CONSTRAINT "sys_dict_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_dict_detail
-- ----------------------------
ALTER TABLE "public"."sys_dict_detail" ADD CONSTRAINT "sys_dict_detail_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_file
-- ----------------------------
ALTER TABLE "public"."sys_file" ADD CONSTRAINT "sys_file_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_job
-- ----------------------------
ALTER TABLE "public"."sys_job" ADD CONSTRAINT "sys_job_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_job_log
-- ----------------------------
ALTER TABLE "public"."sys_job_log" ADD CONSTRAINT "sys_job_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_log_login
-- ----------------------------
ALTER TABLE "public"."sys_log_login" ADD CONSTRAINT "sys_log_login_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_log_oper
-- ----------------------------
ALTER TABLE "public"."sys_log_oper" ADD CONSTRAINT "sys_log_oper_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_menu
-- ----------------------------
ALTER TABLE "public"."sys_menu" ADD CONSTRAINT "sys_menu_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_notice
-- ----------------------------
ALTER TABLE "public"."sys_notice" ADD CONSTRAINT "sys_notice_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_oss_config
-- ----------------------------
ALTER TABLE "public"."sys_oss_config" ADD CONSTRAINT "sys_oss_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_position
-- ----------------------------
ALTER TABLE "public"."sys_position" ADD CONSTRAINT "sys_position_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_position_user
-- ----------------------------
ALTER TABLE "public"."sys_position_user" ADD CONSTRAINT "sys_position_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_role
-- ----------------------------
ALTER TABLE "public"."sys_role" ADD CONSTRAINT "sys_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_role_dept
-- ----------------------------
ALTER TABLE "public"."sys_role_dept" ADD CONSTRAINT "sys_role_dept_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_role_menu
-- ----------------------------
ALTER TABLE "public"."sys_role_menu" ADD CONSTRAINT "sys_role_menu_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_role_user
-- ----------------------------
ALTER TABLE "public"."sys_role_user" ADD CONSTRAINT "sys_role_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_social
-- ----------------------------
ALTER TABLE "public"."sys_social" ADD CONSTRAINT "sys_social_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_user
-- ----------------------------
ALTER TABLE "public"."sys_user" ADD CONSTRAINT "sys_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_user_detail
-- ----------------------------
ALTER TABLE "public"."sys_user_detail" ADD CONSTRAINT "sys_user_detail_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table test_data
-- ----------------------------
ALTER TABLE "public"."test_data" ADD CONSTRAINT "test_data_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table test_data_tree
-- ----------------------------
ALTER TABLE "public"."test_data_tree" ADD CONSTRAINT "test_data_tree_pkey" PRIMARY KEY ("id");


