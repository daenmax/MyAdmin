
-- ----------------------------
-- Table structure for SYS_API_LIMIT
-- ----------------------------
DROP TABLE "SYS_API_LIMIT";
CREATE TABLE "SYS_API_LIMIT" (
  "ID" NVARCHAR2(32) NOT NULL,
  "API_NAME" NVARCHAR2(255),
  "API_URI" NVARCHAR2(255),
  "SINGLE_FREQUENCY" NUMBER(11,0),
  "SINGLE_TIME" NUMBER(11,0),
  "SINGLE_TIME_UNIT" NVARCHAR2(2),
  "WHOLE_FREQUENCY" NUMBER(11,0),
  "WHOLE_TIME" NUMBER(11,0),
  "WHOLE_TIME_UNIT" NVARCHAR2(2),
  "LIMIT_TYPE" NVARCHAR2(2),
  "RET_MSG" NCLOB,
  "STATUS" NVARCHAR2(2),
  "REMARK" NVARCHAR2(255),
  "CREATE_ID" NVARCHAR2(32),
  "CREATE_TIME" DATE,
  "UPDATE_ID" NVARCHAR2(32),
  "UPDATE_TIME" DATE,
  "IS_DELETE" NUMBER(11,0)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_API_LIMIT"."API_NAME" IS '接口名称';
COMMENT ON COLUMN "SYS_API_LIMIT"."API_URI" IS '接口uri';
COMMENT ON COLUMN "SYS_API_LIMIT"."SINGLE_FREQUENCY" IS '单个用户次数';
COMMENT ON COLUMN "SYS_API_LIMIT"."SINGLE_TIME" IS '单个用户时间';
COMMENT ON COLUMN "SYS_API_LIMIT"."SINGLE_TIME_UNIT" IS '单个用户时间单位';
COMMENT ON COLUMN "SYS_API_LIMIT"."WHOLE_FREQUENCY" IS '全部用户次数';
COMMENT ON COLUMN "SYS_API_LIMIT"."WHOLE_TIME" IS '全部用户时间';
COMMENT ON COLUMN "SYS_API_LIMIT"."WHOLE_TIME_UNIT" IS '全部用户时间单位';
COMMENT ON COLUMN "SYS_API_LIMIT"."LIMIT_TYPE" IS '限制类型，0=限流，1=停用';
COMMENT ON COLUMN "SYS_API_LIMIT"."RET_MSG" IS '停用提示，当限制类型=1时，接口返回的提示内容';
COMMENT ON COLUMN "SYS_API_LIMIT"."STATUS" IS '限制状态，0=正常，1=停用';
COMMENT ON COLUMN "SYS_API_LIMIT"."REMARK" IS '备注';
COMMENT ON COLUMN "SYS_API_LIMIT"."CREATE_ID" IS '创建人';
COMMENT ON COLUMN "SYS_API_LIMIT"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "SYS_API_LIMIT"."UPDATE_ID" IS '修改人';
COMMENT ON COLUMN "SYS_API_LIMIT"."UPDATE_TIME" IS '修改时间';
COMMENT ON COLUMN "SYS_API_LIMIT"."IS_DELETE" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "SYS_API_LIMIT" IS '接口限制';

-- ----------------------------
-- Records of SYS_API_LIMIT
-- ----------------------------
INSERT INTO "SYS_API_LIMIT" VALUES ('700df1aab01ead0db7ce6105a9be90a5', '测试接口', '/test/test', '10', '30', '0', '20', '30', '0', '0', NULL, '0', '测试', '1', TO_DATE('2023-05-23 15:28:08', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-24 18:07:46', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_API_LIMIT" VALUES ('71736a3b981f87f504bc4ae7bfb88008', '签到接口', '/sign', '0', '0', '0', '0', '0', '0', '1', '618期间，签到接口暂时停用', '0', '6月20日恢复', '1', TO_DATE('2023-05-23 15:32:12', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-23 15:37:30', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_API_LIMIT" VALUES ('ab6ac1ae29d57e70879e8af885f6f668', '测试单表分页', '/test/data/list3', '5', '30', '0', NULL, NULL, '0', '0', '618期间暂时关闭，6月20日恢复', '0', NULL, '1', TO_DATE('2023-05-24 15:27:49', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-24 17:53:36', 'SYYYY-MM-DD HH24:MI:SS'), '0');

-- ----------------------------
-- Table structure for SYS_CONFIG
-- ----------------------------
DROP TABLE "SYS_CONFIG";
CREATE TABLE "SYS_CONFIG" (
  "ID" NVARCHAR2(32) NOT NULL,
  "NAME" NVARCHAR2(255),
  "KEY_VA" NVARCHAR2(255),
  "VALUE" NCLOB,
  "TYPE" NVARCHAR2(2),
  "STATUS" NVARCHAR2(2),
  "REMARK" NVARCHAR2(255),
  "CREATE_ID" NVARCHAR2(32),
  "CREATE_TIME" DATE,
  "UPDATE_ID" NVARCHAR2(32),
  "UPDATE_TIME" DATE,
  "IS_DELETE" NUMBER(11,0)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_CONFIG"."NAME" IS '参数名称';
COMMENT ON COLUMN "SYS_CONFIG"."KEY_VA" IS '参数键值';
COMMENT ON COLUMN "SYS_CONFIG"."VALUE" IS '参数键值';
COMMENT ON COLUMN "SYS_CONFIG"."TYPE" IS '系统内置，0=否，1=是';
COMMENT ON COLUMN "SYS_CONFIG"."STATUS" IS '参数状态，0=正常，1=停用';
COMMENT ON COLUMN "SYS_CONFIG"."REMARK" IS '备注';
COMMENT ON COLUMN "SYS_CONFIG"."CREATE_ID" IS '创建人';
COMMENT ON COLUMN "SYS_CONFIG"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "SYS_CONFIG"."UPDATE_ID" IS '修改人';
COMMENT ON COLUMN "SYS_CONFIG"."UPDATE_TIME" IS '修改时间';
COMMENT ON COLUMN "SYS_CONFIG"."IS_DELETE" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "SYS_CONFIG" IS '系统参数';

-- ----------------------------
-- Records of SYS_CONFIG
-- ----------------------------
INSERT INTO "SYS_CONFIG" VALUES ('784d0f730c442a42bd6331e81516ebd8', '系统发送限制配置', 'sys.sendLimit.config', '{
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
具体参数说明参考文档', '1', TO_DATE('2023-05-19 15:09:31', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-19 15:09:31', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_CONFIG" VALUES ('83332089ca838d1816498f2bfd874ad3', '系统邮箱配置', 'sys.email.config', '{
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
        "password": "ji6666666666fhg",
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
        "password": "ji6666666666fhg",
        "timeout": "25000",
        "auth": "true",
        "socketFactoryClass": "javax.net.ssl.SSLSocketFactory",
        "weight": "100"
    }]
}', '1', '0', '如果删除或者禁用此参数，那么系统将无法发送邮件
具体参数说明参考文档
', '1', TO_DATE('2023-05-11 10:21:42', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-22 16:42:58', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_CONFIG" VALUES ('cc1dd434b089df0a6e97fbe446c39377', '系统飞书配置', 'sys.feishu.config', '{
	"testbot": {
		"keywords": "",
		"secret": "",
		"accessToken": "207b5526-****-****-****-b57ac302626f",
		"remark": "测试群的机器人"
	}
}', '0', '0', '如果删除或者禁用此参数，那么系统将无法发送飞书通知
具体参数说明参考文档', '1', TO_DATE('2023-10-01 13:40:50', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-10-01 13:40:50', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_CONFIG" VALUES ('64c70f1e91cbcd3cfc390e3966aeef6b', '系统企微配置', 'sys.wecom.config', '{
	"testbot": {
		"key": "b5caa377-****-****-****-3d656ed93b12",
		"remark": "测试群的机器人"
	}
}', '0', '0', '如果删除或者禁用此参数，那么系统将无法发送企业微信通知
具体参数说明参考文档', '1', TO_DATE('2023-10-01 13:41:53', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-10-01 13:43:57', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_CONFIG" VALUES ('0015263fa950c3ce40957fdb3b1e14a7', '系统验证码配置', 'sys.captcha.config', '{
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
具体参数说明参考文档', '1', TO_DATE('2023-04-25 20:23:07', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-19 22:03:25', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_CONFIG" VALUES ('0ce0ea3dc352bb6838e4d008d9e849fc', '系统钉钉配置', 'sys.dingTalk.config', '{
	"testbot": {
		"keywords": "",
		"secret": "SEC48ea902b5b3fb138766666666666666660a18e38bcb607ced5108f71f24a7",
		"accessToken": "e714cfa1e60734d1666666666666666e75f0d2f68d2fbc0c92b4dd42e57d",
		"remark": "测试群的机器人"
	},
	"xiaobai": {
		"keywords": "【定时任务异常】",
		"secret": "SEC48ea902b5b3fb1387651066666666666666618e38bcb607ced5108f71f24a7",
		"accessToken": "e714cfa1e60734d19d66666666666666675f0d2f68d2fbc0c92b4dd42e57d",
		"remark": "闲聊群的机器人"
	}
}', '1', '0', '如果删除或者禁用此参数，那么系统将无法发送钉钉通知
具体参数说明参考文档', '1', TO_DATE('2023-05-13 18:27:24', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-13 20:40:29', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_CONFIG" VALUES ('0e4d78f5e0601e87489a563f40e30ff5', '图片上传限制策略', 'sys.upload.image', '{
    "limit": 6,
    "fileSize": 4,
    "fileType": ["bmp", "gif", "jpg", "jpeg", "png"],
    "isShowTip": true
}', '1', '0', '如果删除此参数的话，系统将使用默认参数
如果禁用此参数的话，系统 将禁止图片上传
limit=同时上传个数
fileSize=文件最大尺寸，单位MB
fileType=[]，支持的文件类型数组，不包含小数点
isShowTip=是否显示提示', '1', TO_DATE('2023-04-17 22:24:15', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-14 09:34:24', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_CONFIG" VALUES ('101fbed52418ce72ffe30143c66fdd06', '系统注册默认信息', 'sys.register.default.info', '{
    "userType": "2",
    "deptCode": "XMY-JN-3",
    "positionCodes": ["user"],
    "roleCodes": ["user"]
}', '1', '0', '此参数必存在，不存在的话，系统将禁止注册
userType，用户类型，必填，参考字典内
deptCode，部门编号，必填
positionCodes，岗位编码，非必填，可多个
roleCodes，角色编码，必填，可多个', '1', TO_DATE('2023-04-25 21:00:26', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-25 21:07:58', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_CONFIG" VALUES ('1a91bf36a72d9dcb253a941a94fa28fa', '文件上传限制策略', 'sys.upload.file', '{
    "limit": 5,
    "fileSize": 5,
    "fileType": ["zip", "txt"],
    "isShowTip": true
}', '1', '0', '如果删除此参数的话，系统将使用默认参数
如果禁用此参数的话，系统 将禁止文件上传
limit=同时上传个数
fileSize=文件最大尺寸，单位MB
fileType=[]，支持的文件类型数组，不包含小数点
isShowTip=是否显示提示', '1', TO_DATE('2023-04-17 22:06:58', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-25 21:00:37', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_CONFIG" VALUES ('1c86983f628ffe1949171b01858d1aa5', '系统登录错误次数限制信息', 'sys.login.fail.info', '{
    "failCount": 5,
    "banSecond": 3600
}', '1', '0', '此参数如果不存在或者被禁用的话，那么将没有限制
failCount，错误次数将锁定，必填
banSecond，锁定的秒数，必填', '1', TO_DATE('2023-05-10 09:29:46', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-10 09:34:40', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_CONFIG" VALUES ('2cd7adf8ceeb1386d85b5477ee03d0fe', '系统短信模板ID配置', 'sys.smsTemplate.config', '{
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
具体参数说明参考文档', '1', TO_DATE('2023-05-14 16:29:54', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-14 16:29:54', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_CONFIG" VALUES ('3d940640a0c040a0025c2c350da036f3', '系统注册开关', 'sys.lock.register', 'true', '1', '0', 'true=开启，false=关闭', '1', TO_DATE('2023-04-14 22:34:46', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-25 21:31:29', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_CONFIG" VALUES ('4aa484dd076566c2e7d936525e36e8c4', '系统短信配置', 'sys.sms.config', '{
	"config": {
		"type": "aliyun"
	},
	"aliyun": {
		"enable": "true",
		"endpoint": "dysmsapi.aliyuncs.com",
		"accessKeyId": "LTAI666666666RpcQ9",
		"accessKeySecret": "N9GXnZc666666666SYD10kX3GPqW8",
		"signName": "MyAdmin"
	},
	"tencent": {
		"enable": "true",
		"endpoint": "sms.tencentcloudapi.com",
		"accessKeyId": "AKID2oP666666666666Mp53mHfoYZGw",
		"accessKeySecret": "e6JTP6666666666vhHYBl7C5g05",
		"signName": "个人开发记录网",
		"sdkAppId": "1400820693"
	}
}', '1', '0', '如果删除或者禁用此参数，那么系统将无法发送短信
具体参数说明参考文档', '1', TO_DATE('2023-05-13 10:05:25', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-13 11:39:54', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_CONFIG" VALUES ('65600f7210f507b143421c4ed33a9b07', '文件列表是否开启预览', 'sys.file.previewListResource', 'true', '1', '0', 'true=开启，false=关闭', '1', TO_DATE('2023-04-16 23:37:01', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-25 21:00:40', 'SYYYY-MM-DD HH24:MI:SS'), '0');

-- ----------------------------
-- Table structure for SYS_DEPT
-- ----------------------------
DROP TABLE "SYS_DEPT";
CREATE TABLE "SYS_DEPT" (
  "ID" NVARCHAR2(32) NOT NULL,
  "PARENT_ID" NVARCHAR2(32),
  "NAME" NVARCHAR2(255),
  "CODE" NVARCHAR2(255),
  "SUMMARY" NCLOB,
  "STATUS" NVARCHAR2(2),
  "LEADER_USER_ID" NVARCHAR2(32),
  "DEPT_LEVEL" NUMBER(11,0),
  "SORT" NUMBER(11,0),
  "REMARK" NVARCHAR2(255),
  "CREATE_ID" NVARCHAR2(32),
  "CREATE_TIME" DATE,
  "UPDATE_ID" NVARCHAR2(32),
  "UPDATE_TIME" DATE,
  "IS_DELETE" NUMBER(11,0)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_DEPT"."PARENT_ID" IS '父级部门ID，顶级为0';
COMMENT ON COLUMN "SYS_DEPT"."NAME" IS '部门名称';
COMMENT ON COLUMN "SYS_DEPT"."CODE" IS '部门编号';
COMMENT ON COLUMN "SYS_DEPT"."SUMMARY" IS '部门简介';
COMMENT ON COLUMN "SYS_DEPT"."STATUS" IS '部门状态，0=正常，1=停用';
COMMENT ON COLUMN "SYS_DEPT"."LEADER_USER_ID" IS '部门负责人 关联用户ID';
COMMENT ON COLUMN "SYS_DEPT"."DEPT_LEVEL" IS '层级，顶级为0';
COMMENT ON COLUMN "SYS_DEPT"."SORT" IS '排序';
COMMENT ON COLUMN "SYS_DEPT"."REMARK" IS '备注';
COMMENT ON COLUMN "SYS_DEPT"."CREATE_ID" IS '创建人';
COMMENT ON COLUMN "SYS_DEPT"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "SYS_DEPT"."UPDATE_ID" IS '修改人';
COMMENT ON COLUMN "SYS_DEPT"."UPDATE_TIME" IS '修改时间';
COMMENT ON COLUMN "SYS_DEPT"."IS_DELETE" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "SYS_DEPT" IS '部门表';

-- ----------------------------
-- Records of SYS_DEPT
-- ----------------------------
INSERT INTO "SYS_DEPT" VALUES ('100', '0', '熊猫眼集团', 'XMY', NULL, '0', '1', '0', '0', NULL, '1', TO_DATE('2023-04-10 14:35:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-10 14:35:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DEPT" VALUES ('101', '100', '济南分公司', 'XMY-JN', NULL, '0', '1', '1', '1', NULL, '1', TO_DATE('2023-04-10 14:35:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-10 14:35:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DEPT" VALUES ('102', '100', '北京分公司', 'XMY-BJ', NULL, '0', '1', '1', '2', NULL, '1', TO_DATE('2023-04-10 14:35:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-10 14:35:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DEPT" VALUES ('103', '101', '研发部门', 'XMY-JN-1', NULL, '0', '1', '2', '1', NULL, '1', TO_DATE('2023-04-10 14:35:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-25 20:54:24', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DEPT" VALUES ('104', '101', '市场部门', 'XMY-JN-2', NULL, '0', '1', '2', '2', NULL, '1', TO_DATE('2023-04-10 14:35:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-12 21:26:01', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DEPT" VALUES ('105', '101', '测试部门', 'XMY-JN-3', NULL, '0', '1', '2', '3', NULL, '1', TO_DATE('2023-04-10 14:35:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-12 21:25:51', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DEPT" VALUES ('106', '101', '财务部门', 'XMY-JN-4', NULL, '0', '1', '2', '4', NULL, '1', TO_DATE('2023-04-10 14:35:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-10 14:35:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DEPT" VALUES ('107', '101', '运维部门', 'XMY-JN-5', NULL, '0', '1', '2', '5', NULL, '1', TO_DATE('2023-04-10 14:35:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-10 14:35:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DEPT" VALUES ('108', '102', '市场部门', 'XMY-BJ-1', NULL, '0', '1', '2', '1', NULL, '1', TO_DATE('2023-04-10 14:35:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-10 14:35:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DEPT" VALUES ('109', '102', '财务部门', 'XMY-BJ-2', NULL, '0', '1', '2', '2', NULL, '1', TO_DATE('2023-04-10 14:35:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-10 14:35:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');

-- ----------------------------
-- Table structure for SYS_DEPT_PARENT
-- ----------------------------
DROP TABLE "SYS_DEPT_PARENT";
CREATE TABLE "SYS_DEPT_PARENT" (
  "ID" NVARCHAR2(32) NOT NULL,
  "DEPT_ID" NVARCHAR2(32),
  "PARENT_ID" NVARCHAR2(32)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_DEPT_PARENT"."DEPT_ID" IS '部门ID';
COMMENT ON COLUMN "SYS_DEPT_PARENT"."PARENT_ID" IS '父级部门ID，顶级为0';
COMMENT ON TABLE "SYS_DEPT_PARENT" IS '部门层级关系表';

-- ----------------------------
-- Records of SYS_DEPT_PARENT
-- ----------------------------
INSERT INTO "SYS_DEPT_PARENT" VALUES ('06d36f0f8901fb4ffbe781b080f07dd6', '107', '100');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('1a0b09c29f5470ac94079b303b0ad12b', '101', '101');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('1fd041e9beb93fa50133d2d7321e1cb0', '108', '100');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('222c3eaf636f8edf7f83eda4f90d26f2', '105', '100');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('268027691ee65797e33da228ea194b6c', '107', '107');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('26d358c6733335e1bcb9aae9ee3e54db', '102', '0');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('28070f8ff40f690d48c308b76e59a993', '105', '105');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('2d6c36d425ff5be02ae48940f701c3ef', '103', '103');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('384202fb5d68394c886cbeefadaf01ce', '109', '100');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('3d41adfbf70d1af146f9b22cfa3c4903', '100', '100');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('4222314fa5495df9d762a953cfcf65ac', '107', '0');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('494ccdf978d57d8bc96b02d72a9f5a3a', '108', '108');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('550ccf63f48406ebf42219e7d3219582', '107', '101');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('59f10bf5e1c6f714b4f1be02910553fa', '103', '101');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('617f7108449586ef211c0955a189b222', '108', '102');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('68b753537bc2bfa1b09e677077a5e1cc', '109', '102');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('700950267ddc9c18243472f68a2e0c62', '106', '101');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('886b217dcfb0a77b4b281f6a5981c69b', '102', '102');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('96b6974412095ef1f056f5596d1df48d', '103', '0');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('98faed672906375a309a2ac4c2ce47f1', '104', '100');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('9d125f57b2a7251caac817bed4ddd6ee', '103', '100');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('a63973aaebc357a568b9ec3e15bdb774', '104', '101');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('a95a29cd20b7a3ee0deabdd83b468bd9', '101', '100');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('b19a436916284476e08aacfbc1012e17', '101', '0');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('b264e91699df1ed6db2253203308452d', '106', '106');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('bf0399783891c2e9df37b693fdadf6d2', '105', '0');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('c57966fa37859a0c0c153c5dd5ca2a6d', '106', '100');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('d56ed387ac2c6105ffbe3faa8d99f5eb', '100', '0');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('d570bd153cc62d2a8c346bf55769bceb', '105', '101');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('d6cd40b06e1a20bc20d741d52fb03fed', '109', '0');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('e0754a80b8ef4531839db616d6a4f797', '104', '104');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('e5a25858e3e18b34cf4e51dcd45ffee3', '106', '0');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('e651fc9a844a23121783ace242907138', '102', '100');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('f0f6e94712736e9cb689bf044f5667af', '104', '0');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('f5cd73b71673848ce9a5a7543ccd38fa', '109', '109');
INSERT INTO "SYS_DEPT_PARENT" VALUES ('fae666e9d536d28b28dcf84674e34aaf', '108', '0');

-- ----------------------------
-- Table structure for SYS_DICT
-- ----------------------------
DROP TABLE "SYS_DICT";
CREATE TABLE "SYS_DICT" (
  "ID" NVARCHAR2(32) NOT NULL,
  "NAME" NVARCHAR2(255),
  "CODE" NVARCHAR2(255),
  "STATUS" NVARCHAR2(2),
  "REMARK" NVARCHAR2(255),
  "CREATE_ID" NVARCHAR2(32),
  "CREATE_TIME" DATE,
  "UPDATE_ID" NVARCHAR2(32),
  "UPDATE_TIME" DATE,
  "IS_DELETE" NUMBER(11,0)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_DICT"."NAME" IS '字典名称';
COMMENT ON COLUMN "SYS_DICT"."CODE" IS '字典编码';
COMMENT ON COLUMN "SYS_DICT"."STATUS" IS '字典状态，0=正常，1=禁用';
COMMENT ON COLUMN "SYS_DICT"."REMARK" IS '备注';
COMMENT ON COLUMN "SYS_DICT"."CREATE_ID" IS '创建人';
COMMENT ON COLUMN "SYS_DICT"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "SYS_DICT"."UPDATE_ID" IS '修改人';
COMMENT ON COLUMN "SYS_DICT"."UPDATE_TIME" IS '修改时间';
COMMENT ON COLUMN "SYS_DICT"."IS_DELETE" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "SYS_DICT" IS '字典表';

-- ----------------------------
-- Records of SYS_DICT
-- ----------------------------
INSERT INTO "SYS_DICT" VALUES ('2baef88eba108855ecc7aa28beee9a5f', '接口限制类型', 'sys_api_limit_type', '0', NULL, '1', TO_DATE('2023-05-23 11:02:12', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-23 11:02:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT" VALUES ('32a3d1d751b94bf4aff2f9c7cb0c667d', '通知公告类型', 'sys_notice_type', '0', NULL, '1', TO_DATE('2023-03-13 09:58:45', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:50:51', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT" VALUES ('3da162a1fca95ac279a83107f6989db2', 'OSS厂商类型', 'sys_oss_type', '0', NULL, '1', TO_DATE('2023-04-16 23:49:02', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:52:49', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT" VALUES ('4164ee7178b8cfe228e2606b2ee4c18e', '系统用户状态', 'sys_user_status', '0', NULL, '1', TO_DATE('2023-03-27 11:10:12', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-14 23:07:34', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT" VALUES ('485fc2c337d2430cb39449dad5ba970a', '系统状态', 'sys_common_status', '0', NULL, '1', TO_DATE('2023-03-13 09:58:45', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-13 09:58:45', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT" VALUES ('709ac842d94f444cb50c94e1d5bea3e7', '测试数据类型', 'test_data_type', '0', NULL, NULL, TO_DATE('2023-03-15 14:11:03', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:50:55', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT" VALUES ('7d7457a0562a4c9a80e6f3de0426f7e7', '用户性别', 'sys_user_sex', '0', NULL, '1', TO_DATE('2023-03-13 09:58:45', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-13 09:58:45', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT" VALUES ('7e9cdefc71e446ba8ea69cf401913890', '系统开关', 'sys_normal_disable', '0', NULL, '1', TO_DATE('2023-02-13 09:58:45', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-13 09:58:45', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT" VALUES ('824bf83ca68c8c235e75dc731088d2f8', '系统通知渠道', 'sys_notify_channel', '0', NULL, '1', TO_DATE('2023-05-13 19:08:34', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-13 19:08:37', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT" VALUES ('98c39286b939f05fb514782a4779a5bf', '系统时间单位', 'sys_time_unit', '0', NULL, '1', TO_DATE('2023-05-23 14:47:07', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-06-26 15:49:09', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT" VALUES ('9b86e9ae2728400189d76f9b8710c269', '系统是否', 'sys_yes_no', '0', NULL, '1', TO_DATE('2023-02-13 09:58:45', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-13 09:58:45', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT" VALUES ('a08cf8bf214b49f68bcc03d5b8ecef7d', '通知公告状态', 'sys_notice_status', '0', NULL, '1', TO_DATE('2023-03-13 09:58:45', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:51:25', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT" VALUES ('bc4fd7d4cdb70ac8b48532de2f7410f7', '任务分组', 'sys_job_group', '0', NULL, '1', TO_DATE('2023-05-01 14:56:03', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:56:03', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT" VALUES ('c7936e47545262dd3836b322a768015f', 'OSS桶权限类型', 'sys_oss_scope', '0', NULL, '1', TO_DATE('2023-04-18 23:46:04', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:52:37', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT" VALUES ('c9e8979aa7c14a9bae2ccd624067c734', '菜单状态', 'sys_show_hide', '0', NULL, '1', TO_DATE('2023-03-13 09:58:45', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-13 09:58:45', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT" VALUES ('cb460b35c6cc2bf90b0c0ad7d092024c', '系统用户类型', 'sys_user_type', '0', NULL, '1', TO_DATE('2023-03-23 10:57:44', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:50:58', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT" VALUES ('cc2b5556e60e496fb3863bda3ab283e3', '操作类型', 'sys_oper_type', '0', NULL, '1', TO_DATE('2023-03-13 09:58:45', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-13 09:58:45', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT" VALUES ('d123f703a673a86c2340954fd89a4566', '数据权限', 'data_scope', '0', NULL, '1', TO_DATE('2023-03-29 10:38:08', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-04 15:16:14', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT" VALUES ('ddff67e6d459cf953f1ac23c413f76d3', '任务状态', 'sys_job_status', '0', NULL, '1', TO_DATE('2023-05-01 14:55:38', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:55:38', 'SYYYY-MM-DD HH24:MI:SS'), '0');

-- ----------------------------
-- Table structure for SYS_DICT_DETAIL
-- ----------------------------
DROP TABLE "SYS_DICT_DETAIL";
CREATE TABLE "SYS_DICT_DETAIL" (
  "ID" NVARCHAR2(32) NOT NULL,
  "DICT_CODE" NVARCHAR2(255),
  "LABEL" NVARCHAR2(255),
  "VALUE" NVARCHAR2(255),
  "SORT" NUMBER(11,0),
  "CSS_CLASS" NVARCHAR2(255),
  "LIST_CLASS" NVARCHAR2(255),
  "STATUS" NVARCHAR2(2),
  "REMARK" NVARCHAR2(255),
  "CREATE_ID" NVARCHAR2(32),
  "CREATE_TIME" DATE,
  "UPDATE_ID" NVARCHAR2(32),
  "UPDATE_TIME" DATE,
  "IS_DELETE" NUMBER(11,0)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_DICT_DETAIL"."DICT_CODE" IS '字典编码';
COMMENT ON COLUMN "SYS_DICT_DETAIL"."LABEL" IS '字典标签';
COMMENT ON COLUMN "SYS_DICT_DETAIL"."VALUE" IS '字典键值';
COMMENT ON COLUMN "SYS_DICT_DETAIL"."SORT" IS '排序';
COMMENT ON COLUMN "SYS_DICT_DETAIL"."CSS_CLASS" IS '样式属性（其他样式扩展）';
COMMENT ON COLUMN "SYS_DICT_DETAIL"."LIST_CLASS" IS '表格回显样式';
COMMENT ON COLUMN "SYS_DICT_DETAIL"."STATUS" IS '字典状态，0=正常，1=禁用';
COMMENT ON COLUMN "SYS_DICT_DETAIL"."REMARK" IS '备注';
COMMENT ON COLUMN "SYS_DICT_DETAIL"."CREATE_ID" IS '创建人';
COMMENT ON COLUMN "SYS_DICT_DETAIL"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "SYS_DICT_DETAIL"."UPDATE_ID" IS '修改人';
COMMENT ON COLUMN "SYS_DICT_DETAIL"."UPDATE_TIME" IS '修改时间';
COMMENT ON COLUMN "SYS_DICT_DETAIL"."IS_DELETE" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "SYS_DICT_DETAIL" IS '字典明细表';

-- ----------------------------
-- Records of SYS_DICT_DETAIL
-- ----------------------------
INSERT INTO "SYS_DICT_DETAIL" VALUES ('c8f8118fc33f410ba61a12f780e76c3f', 'sys_notice_type', '通知', '1', '1', NULL, 'warning', '0', '通知', '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:50:51', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('ca7ad3018855a44d543790fd313dc9a3', 'sys_user_status', '正常', '0', '0', NULL, 'success', '0', NULL, '1', TO_DATE('2023-03-27 11:10:32', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-14 23:07:34', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('cb021c733abc68910a1f0ceb03a06499', 'sys_notify_channel', '钉钉', '3', '3', NULL, 'warning', '0', NULL, '1', TO_DATE('2023-05-13 19:09:47', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-13 19:10:00', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('cbbc1ccaa65740479dde6e52529e9fda', 'sys_show_hide', '显示', '0', '1', NULL, 'primary', '0', '显示菜单', '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('ce73653c160744d9364aadc0a39f6d6d', 'test_data_type', '农业', '2', '3', NULL, 'success', '0', '农业', '1', TO_DATE('2023-03-17 10:37:50', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:50:55', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('dffa0d31ac0ae45cddd5f319c52bf818', 'data_scope', '全部数据', '3', '3', NULL, 'danger', '0', NULL, '1', TO_DATE('2023-03-29 10:39:28', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-29 10:39:28', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('e1720b95d3be9ce033efd041ea649134', 'sys_user_type', '测试', '3', '0', NULL, 'info', '0', NULL, '1', TO_DATE('2023-03-27 15:36:50', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:50:58', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('e1961a649aa19edaae657b88fd19e91b', 'sys_notify_channel', '不通知', '0', '0', NULL, 'info', '0', NULL, '1', TO_DATE('2023-05-13 19:09:01', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-13 19:09:54', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('e5e1239310b9383f5ce42d557093bac8', 'sys_user_status', '注销', '2', '0', NULL, 'danger', '0', NULL, '1', TO_DATE('2023-03-27 11:10:51', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-14 23:07:34', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('e83acff859cf850825835a80e7c9dc00', 'sys_oss_scope', 'public', '1', '1', NULL, 'danger', '0', NULL, '1', TO_DATE('2023-04-18 23:46:37', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:52:38', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('eadebaa7234ca12881ac845803b978a1', 'sys_time_unit', '小时', '2', '2', NULL, 'info', '0', NULL, '1', TO_DATE('2023-05-23 14:48:15', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-06-26 15:49:09', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('eaeffc348c4e40cf9a45e91db17d0902', 'sys_normal_disable', '停用', '1', '2', NULL, 'danger', '0', '停用状态', '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('ee69bc49e73716f01b4f369f1323a9e9', 'sys_oper_type', '导入', '5', '5', NULL, 'primary', '0', NULL, '1', TO_DATE('2023-04-19 22:24:55', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-19 22:24:55', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('f328b5c9651db1236cdf1d3682ac7f64', 'sys_oss_type', '京东云', '京东云', '0', NULL, 'danger', '0', NULL, '1', TO_DATE('2023-04-16 23:55:04', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:52:49', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('ff1467cd4b4d4d84868a8f31a4f32fec', 'sys_common_status', '失败', '1', '2', NULL, 'danger', '0', '停用状态', '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('ff3eed8202940077df815bfdf0b5d836', 'sys_time_unit', '天', '3', '3', NULL, 'warning', '0', NULL, '1', TO_DATE('2023-05-23 14:48:37', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-06-26 15:49:09', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('7e4e1fc182c3cbc573a32eb7fbc9a749', 'sys_notify_channel', '飞书', '4', '4', NULL, 'warning', '0', NULL, '1', TO_DATE('2023-10-01 14:33:28', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-10-01 14:33:28', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('87f33838c66f34a94d7c19c384e4758c', 'sys_notify_channel', '企业微信', '5', '5', NULL, 'warning', '0', NULL, '1', TO_DATE('2023-10-01 14:33:39', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-10-01 14:33:39', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('00dce2554c6eb602a62bc45b23f19066', 'sys_job_status', '正常', '0', '0', NULL, 'success', '0', NULL, '1', TO_DATE('2023-05-01 14:57:26', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 15:44:31', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('015c16f7bcce3fbf50afa1b71646f05f', 'sys_api_limit_type', '停用', '1', '1', NULL, 'danger', '0', NULL, '1', TO_DATE('2023-05-23 11:02:45', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-23 11:02:50', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('0278ac576ea3fa7f1e3df720e4c051b2', 'test_data_type', '其他', '3', '4', NULL, 'warning', '0', '其他', '1', TO_DATE('2023-03-17 10:38:09', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:50:55', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('0402c072489ef66c7ec33916c2b1f938', 'sys_oper_type', '删除', '2', '2', NULL, 'danger', '0', NULL, '1', TO_DATE('2023-04-19 22:24:04', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-19 22:24:12', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('065f42570f8454a5a91477ce552ff24d', 'sys_notify_channel', '邮箱', '1', '1', NULL, 'primary', '0', NULL, '1', TO_DATE('2023-05-13 19:09:14', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-13 19:09:14', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('0a77ebdc362f6c039aec7e8261402a82', 'sys_oss_type', '华为云', '华为云', '0', NULL, 'danger', '0', NULL, '1', TO_DATE('2023-04-16 23:55:15', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:52:49', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('0ab7306ac5aaf342514eeed9488e4216', 'data_scope', '本人数据', '0', '0', NULL, 'info', '0', NULL, '1', TO_DATE('2023-03-29 10:38:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-29 10:38:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('0eee47140c4a07a008365f9fa8d8cdc1', 'sys_job_group', '默认', 'DEFAULT', '1', NULL, 'success', '0', NULL, '1', TO_DATE('2023-05-01 14:56:53', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:57:01', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('11abad5a8a2ac077c362b044bc99778e', 'sys_oper_type', '其他', '0', '0', NULL, 'info', '0', NULL, '1', TO_DATE('2023-04-19 22:23:37', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-19 22:23:37', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('1638c4c1734530de841be71cc5ebd8b3', 'sys_time_unit', '分钟', '1', '1', NULL, 'primary', '0', NULL, '1', TO_DATE('2023-05-23 14:48:01', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-06-26 15:49:09', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('19c1384a77f2421a95a75cb99ddbadd4', 'sys_yes_no', '是', '1', '2', NULL, 'primary', '0', '系统默认是', '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('1c48afa8c9c14fb8a3aec4c255b89769', 'test_data_type', '科技', '1', '2', NULL, 'info', '0', '科技', '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:50:55', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('325a4db1eb9c3d73ab4754875e2ef9bb', 'sys_oss_type', 'minio', 'minio', '0', NULL, 'primary', '0', NULL, '1', TO_DATE('2023-04-16 23:49:45', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:52:49', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('3400ec32fe6a4b69aacd2b5f7986da00', 'sys_user_sex', '女', '0', '1', NULL, NULL, '0', '性别女', '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('374e0ff900544b7fa60e407422ad78bd', 'sys_user_sex', '男', '1', '2', NULL, NULL, '0', '性别男', '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('37b9f8bb3b4d4b36dfd605c69f796540', 'sys_notify_channel', '短信', '2', '2', NULL, 'success', '0', NULL, '1', TO_DATE('2023-05-13 19:09:24', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-13 19:09:24', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('413a1e5fbfa246e6a520f01fab6a6025', 'sys_yes_no', '否', '0', '1', NULL, 'danger', '0', '系统默认否', '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('485e68dd0cf3ee70cb778a6409b57e1c', 'sys_job_status', '暂停', '1', '1', NULL, 'info', '0', NULL, '1', TO_DATE('2023-05-01 14:57:32', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 15:44:57', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('4a0a41f396405aedcf01c7e97d2c1860', 'sys_oper_type', '下载', '8', '8', NULL, 'warning', '0', NULL, '1', TO_DATE('2023-04-19 22:25:39', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-19 22:25:39', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('4b1b2891d50e4b419be63f230489562f', 'sys_notice_type', '公告', '2', '2', NULL, 'success', '0', '公告', '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:50:51', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('56d7b1c499d747b5bd0bfa4158562439', 'sys_common_status', '成功', '0', '1', NULL, 'success', '0', '正常状态', '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 21:08:16', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('5baa5fea23ed4f2c8ed4b32e63e8a405', 'sys_show_hide', '隐藏', '1', '2', NULL, 'danger', '0', '隐藏菜单', '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('6232d1a9d3cb4485b15bd20a03e807d0', 'sys_notice_status', '关闭', '1', '2', NULL, 'danger', '0', '关闭状态', '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:51:25', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('66824297a41647e72d8257d1b0dcaad7', 'sys_user_type', '运维', '2', '0', NULL, 'primary', '0', NULL, '1', TO_DATE('2023-03-23 11:04:58', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:50:58', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('6bf3a9656aa4697581a0be46ffc67736', 'sys_oss_scope', 'custom', '2', '2', NULL, 'primary', '0', NULL, '1', TO_DATE('2023-04-18 23:46:54', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:52:38', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('6f6212d3ee407955b6e1606d6bc692fa', 'sys_oss_type', '腾讯云', '腾讯云', '0', NULL, 'info', '0', NULL, '1', TO_DATE('2023-04-16 23:54:48', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:52:49', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('703f50ed1e45a4d8f43ca7644a3b5703', 'data_scope', '本部门数据', '1', '1', NULL, 'primary', '0', NULL, '1', TO_DATE('2023-03-29 10:38:44', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-29 10:38:44', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('707107e22b1e4021aad925c891652f0e', 'test_data_type', '民生', '0', '1', NULL, 'primary', '0', '民生', '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:50:55', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('74538cc5e03118a8f95bec4b9f60ac20', 'sys_oper_type', '查询', '4', '4', NULL, 'success', '0', NULL, '1', TO_DATE('2023-04-19 22:24:40', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-19 22:24:40', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('7e85865f7e80a1f20b4f5767a6d6f8b5', 'sys_job_group', '系统', 'SYSTEM', '0', NULL, 'primary', '0', NULL, '1', TO_DATE('2023-05-01 14:56:36', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:56:36', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('847f21debb0a59235347dad479309d67', 'sys_api_limit_type', '限流', '0', '0', NULL, 'warning', '0', NULL, '1', TO_DATE('2023-05-23 11:02:37', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-23 11:02:37', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('8cf5ea8761164a1e93f5524fad6df101', 'sys_normal_disable', '正常', '0', '1', NULL, 'primary', '0', '正常状态', '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('8d9fb34470f5f0351efd0dcf2f733577', 'sys_user_type', '开发', '1', '0', NULL, 'danger', '0', NULL, '1', TO_DATE('2023-03-23 11:04:52', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:50:58', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('8fff0e476aabc28003c96852dd515995', 'data_scope', '自定义权限', '4', '4', NULL, 'warning', '0', NULL, '1', TO_DATE('2023-03-29 10:39:40', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-29 10:39:40', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('92ffc9d5d14bd57bfdf48926b84edd44', 'sys_oper_type', '修改', '3', '3', NULL, 'warning', '0', NULL, '1', TO_DATE('2023-04-19 22:24:28', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-19 22:24:28', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('9908f3fc165afb446b2a469b848c84ea', 'sys_oss_type', '阿里云', '阿里云', '0', NULL, 'success', '0', NULL, '1', TO_DATE('2023-04-16 23:54:41', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:52:49', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('9d68e851142d435b88d12ba633f7e4ea', 'sys_user_sex', '未知', '2', '3', NULL, NULL, '0', '性别未知', '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('a30fee8cece01061abbe27922e94b7dd', 'sys_oper_type', '新增', '1', '1', NULL, 'primary', '0', NULL, '1', TO_DATE('2023-04-19 22:23:47', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-19 22:24:15', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('a741425d33008d8b69adde307897bff9', 'sys_oss_scope', 'private', '0', '0', NULL, 'success', '0', NULL, '1', TO_DATE('2023-04-18 23:46:26', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:52:38', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('a981115becdc2a8643ff774ac36dafe1', 'sys_time_unit', '秒', '0', '0', NULL, 'success', '0', NULL, '1', TO_DATE('2023-05-23 14:47:51', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-06-26 15:49:09', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('a9c8f290b3fe2e21e0e57953da78e8ff', 'sys_user_status', '停用', '1', '0', NULL, 'warning', '0', NULL, '1', TO_DATE('2023-03-27 11:10:43', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-14 23:07:34', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('b633a41a4c816cfb8622c2e874fbbb47', 'sys_oper_type', '上传', '7', '7', NULL, 'primary', '0', NULL, '1', TO_DATE('2023-04-19 22:25:26', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-19 22:25:26', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('b825663a1745a0aa65307acceff1e87c', 'data_scope', '本部门及以下数据', '2', '2', NULL, 'success', '0', NULL, '1', TO_DATE('2023-03-29 10:39:04', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-29 10:39:04', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('c46d3f2ed39d4933abccfeadeceae66c', 'sys_notice_status', '正常', '0', '1', NULL, 'primary', '0', '正常状态', '1', TO_DATE('2023-03-15 16:36:49', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:51:25', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('c7ebb92a91938d26c50ae8ea69e826d2', 'sys_oss_type', '七牛云', '七牛云', '0', NULL, 'warning', '0', NULL, '1', TO_DATE('2023-04-16 23:54:54', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:52:49', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_DICT_DETAIL" VALUES ('c84287c50997d6ec0ddf19bd68f84e87', 'sys_oper_type', '导出', '6', '6', NULL, 'warning', '0', NULL, '1', TO_DATE('2023-04-19 22:25:10', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-19 22:25:10', 'SYYYY-MM-DD HH24:MI:SS'), '0');

-- ----------------------------
-- Table structure for SYS_FILE
-- ----------------------------
DROP TABLE "SYS_FILE";
CREATE TABLE "SYS_FILE" (
  "ID" NVARCHAR2(32) NOT NULL,
  "ORIGINAL_NAME" NVARCHAR2(255),
  "FILE_NAME" NVARCHAR2(255),
  "FILE_SUFFIX" NVARCHAR2(255),
  "FILE_URL" NVARCHAR2(500),
  "FILE_SIZE" NUMBER(11,0),
  "FILE_MD5" NVARCHAR2(255),
  "FILE_TYPE" NVARCHAR2(255),
  "OSS_ID" NVARCHAR2(32),
  "STATUS" NVARCHAR2(2),
  "REMARK" NVARCHAR2(255),
  "CREATE_ID" NVARCHAR2(32),
  "CREATE_TIME" DATE,
  "UPDATE_ID" NVARCHAR2(32),
  "UPDATE_TIME" DATE,
  "IS_DELETE" NUMBER(11,0)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_FILE"."ORIGINAL_NAME" IS '原始文件名称';
COMMENT ON COLUMN "SYS_FILE"."FILE_NAME" IS '文件名称';
COMMENT ON COLUMN "SYS_FILE"."FILE_SUFFIX" IS '文件后缀名';
COMMENT ON COLUMN "SYS_FILE"."FILE_URL" IS '文件URL地址';
COMMENT ON COLUMN "SYS_FILE"."FILE_SIZE" IS '文件大小（字节）';
COMMENT ON COLUMN "SYS_FILE"."FILE_MD5" IS '文件MD5（eTag）';
COMMENT ON COLUMN "SYS_FILE"."FILE_TYPE" IS '文件类型，例如：例如：image/jpeg';
COMMENT ON COLUMN "SYS_FILE"."OSS_ID" IS '所属OSS配置ID';
COMMENT ON COLUMN "SYS_FILE"."STATUS" IS '文件状态，0=正常，1=禁用';
COMMENT ON COLUMN "SYS_FILE"."REMARK" IS '备注';
COMMENT ON COLUMN "SYS_FILE"."CREATE_ID" IS '创建人';
COMMENT ON COLUMN "SYS_FILE"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "SYS_FILE"."UPDATE_ID" IS '修改人';
COMMENT ON COLUMN "SYS_FILE"."UPDATE_TIME" IS '修改时间';
COMMENT ON COLUMN "SYS_FILE"."IS_DELETE" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "SYS_FILE" IS 'OSS文件表';

-- ----------------------------
-- Records of SYS_FILE
-- ----------------------------

-- ----------------------------
-- Table structure for SYS_JOB
-- ----------------------------
DROP TABLE "SYS_JOB";
CREATE TABLE "SYS_JOB" (
  "ID" NVARCHAR2(32) NOT NULL,
  "JOB_NAME" NVARCHAR2(255),
  "JOB_GROUP" NVARCHAR2(255),
  "INVOKE_TARGET" NCLOB,
  "CRON_EXPRESSION" NVARCHAR2(255),
  "STATUS" NVARCHAR2(2),
  "MISFIRE_POLICY" NVARCHAR2(2),
  "CONCURRENT" NVARCHAR2(2),
  "REMARK" NVARCHAR2(255),
  "NOTIFY_CHANNEL" NVARCHAR2(2),
  "NOTIFY_OBJS" NCLOB,
  "CREATE_ID" NVARCHAR2(32),
  "CREATE_TIME" DATE,
  "UPDATE_ID" NVARCHAR2(32),
  "UPDATE_TIME" DATE,
  "IS_DELETE" NUMBER(11,0)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_JOB"."JOB_NAME" IS '任务名称';
COMMENT ON COLUMN "SYS_JOB"."JOB_GROUP" IS '任务分组';
COMMENT ON COLUMN "SYS_JOB"."INVOKE_TARGET" IS '调用目标字符串';
COMMENT ON COLUMN "SYS_JOB"."CRON_EXPRESSION" IS 'cron执行表达式';
COMMENT ON COLUMN "SYS_JOB"."STATUS" IS '任务状态，0=正常，1=暂停';
COMMENT ON COLUMN "SYS_JOB"."MISFIRE_POLICY" IS '计划执行错误策略，1=立即执行，2=执行一次，3=放弃执行';
COMMENT ON COLUMN "SYS_JOB"."CONCURRENT" IS '是否并发执行，0=允许，1=禁止';
COMMENT ON COLUMN "SYS_JOB"."REMARK" IS '备注';
COMMENT ON COLUMN "SYS_JOB"."NOTIFY_CHANNEL" IS '异常时，通知渠道，0=不通知，1=邮件，2=短信，3=钉钉，4=飞书，5=企业微信';
COMMENT ON COLUMN "SYS_JOB"."NOTIFY_OBJS" IS '异常时，通知对象，多个用,隔开
     * 邮件渠道时，写邮箱
     * 短信渠道时，写手机号
     * 钉钉、飞书、企业微信渠道时，写botName';
COMMENT ON COLUMN "SYS_JOB"."CREATE_ID" IS '创建人';
COMMENT ON COLUMN "SYS_JOB"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "SYS_JOB"."UPDATE_ID" IS '修改人';
COMMENT ON COLUMN "SYS_JOB"."UPDATE_TIME" IS '修改时间';
COMMENT ON COLUMN "SYS_JOB"."IS_DELETE" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "SYS_JOB" IS '定时任务调度表';

-- ----------------------------
-- Records of SYS_JOB
-- ----------------------------
INSERT INTO "SYS_JOB" VALUES ('0a8d20cd101aad4a761e548ff062f593', '测试（无参数）', 'DEFAULT', 'TestTask.noParams', '6 2 * * * ?', '1', '1', '1', NULL, '1', '1330166565@qq.com,1747846658@qq.com', '1', TO_DATE('2023-05-01 16:57:10', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-06-05 18:21:20', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_JOB" VALUES ('5ce12d1934bb1d67d4c3787aa037bc7c', '测试（多个参数）', 'DEFAULT', 'TestTask.multipleParams(''test'', true, 666L, 365.16D, 200)', '* * * * * ?', '1', '2', '1', NULL, '0', NULL, '1', TO_DATE('2023-05-01 15:45:55', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-06-05 18:21:31', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_JOB" VALUES ('b28738a16605721937a74cd3a1089fb8', '测试（一个参数）', 'DEFAULT', 'TestTask.oneParams(''阿萨德'')', '0 0 * * * ?', '1', '1', '1', NULL, '0', NULL, '1', TO_DATE('2023-05-01 16:53:05', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-06-05 18:21:25', 'SYYYY-MM-DD HH24:MI:SS'), '0');

-- ----------------------------
-- Table structure for SYS_JOB_LOG
-- ----------------------------
DROP TABLE "SYS_JOB_LOG";
CREATE TABLE "SYS_JOB_LOG" (
  "ID" NVARCHAR2(32) NOT NULL,
  "JOB_ID" NVARCHAR2(255),
  "JOB_MESSAGE" NCLOB,
  "EXCEPTION_INFO" NCLOB,
  "START_TIME" DATE,
  "END_TIME" DATE,
  "EXECUTE_TIME" NUMBER(11,0),
  "STATUS" NVARCHAR2(2),
  "REMARK" NVARCHAR2(255),
  "CREATE_ID" NVARCHAR2(32),
  "CREATE_TIME" DATE,
  "UPDATE_ID" NVARCHAR2(32),
  "UPDATE_TIME" DATE,
  "IS_DELETE" NUMBER(11,0)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_JOB_LOG"."JOB_ID" IS '关联定时任务ID';
COMMENT ON COLUMN "SYS_JOB_LOG"."JOB_MESSAGE" IS '日志信息';
COMMENT ON COLUMN "SYS_JOB_LOG"."EXCEPTION_INFO" IS '异常信息';
COMMENT ON COLUMN "SYS_JOB_LOG"."START_TIME" IS '开始执行时间';
COMMENT ON COLUMN "SYS_JOB_LOG"."END_TIME" IS '结束执行时间';
COMMENT ON COLUMN "SYS_JOB_LOG"."EXECUTE_TIME" IS '执行耗时时间（毫秒）';
COMMENT ON COLUMN "SYS_JOB_LOG"."STATUS" IS '执行结果，0=成功，1=失败';
COMMENT ON COLUMN "SYS_JOB_LOG"."REMARK" IS '备注';
COMMENT ON COLUMN "SYS_JOB_LOG"."CREATE_ID" IS '创建人';
COMMENT ON COLUMN "SYS_JOB_LOG"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "SYS_JOB_LOG"."UPDATE_ID" IS '修改人';
COMMENT ON COLUMN "SYS_JOB_LOG"."UPDATE_TIME" IS '修改时间';
COMMENT ON COLUMN "SYS_JOB_LOG"."IS_DELETE" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "SYS_JOB_LOG" IS '定时任务调度日志表';

-- ----------------------------
-- Records of SYS_JOB_LOG
-- ----------------------------

-- ----------------------------
-- Table structure for SYS_LOG_LOGIN
-- ----------------------------
DROP TABLE "SYS_LOG_LOGIN";
CREATE TABLE "SYS_LOG_LOGIN" (
  "ID" NVARCHAR2(32) NOT NULL,
  "USERNAME" NVARCHAR2(255),
  "IP" NVARCHAR2(255),
  "LOCATION" NVARCHAR2(255),
  "BROWSER" NVARCHAR2(255),
  "OS" NVARCHAR2(255),
  "STATUS" NVARCHAR2(2),
  "REMARK" NVARCHAR2(255),
  "CREATE_ID" NVARCHAR2(32),
  "CREATE_TIME" DATE,
  "UPDATE_ID" NVARCHAR2(32),
  "UPDATE_TIME" DATE,
  "IS_DELETE" NUMBER(11,0)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_LOG_LOGIN"."USERNAME" IS '用户账号';
COMMENT ON COLUMN "SYS_LOG_LOGIN"."IP" IS '登录IP';
COMMENT ON COLUMN "SYS_LOG_LOGIN"."LOCATION" IS '登录地点';
COMMENT ON COLUMN "SYS_LOG_LOGIN"."BROWSER" IS '浏览器';
COMMENT ON COLUMN "SYS_LOG_LOGIN"."OS" IS '操作系统';
COMMENT ON COLUMN "SYS_LOG_LOGIN"."STATUS" IS '登录结果，0=成功，1=失败';
COMMENT ON COLUMN "SYS_LOG_LOGIN"."REMARK" IS '备注';
COMMENT ON COLUMN "SYS_LOG_LOGIN"."CREATE_ID" IS '创建人';
COMMENT ON COLUMN "SYS_LOG_LOGIN"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "SYS_LOG_LOGIN"."UPDATE_ID" IS '修改人';
COMMENT ON COLUMN "SYS_LOG_LOGIN"."UPDATE_TIME" IS '修改时间';
COMMENT ON COLUMN "SYS_LOG_LOGIN"."IS_DELETE" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "SYS_LOG_LOGIN" IS '登录日志表';

-- ----------------------------
-- Records of SYS_LOG_LOGIN
-- ----------------------------

-- ----------------------------
-- Table structure for SYS_LOG_OPER
-- ----------------------------
DROP TABLE "SYS_LOG_OPER";
CREATE TABLE "SYS_LOG_OPER" (
  "ID" NVARCHAR2(32) NOT NULL,
  "NAME" NVARCHAR2(255),
  "TYPE" NVARCHAR2(2),
  "METHOD" NVARCHAR2(255),
  "REQUEST_TYPE" NVARCHAR2(32),
  "REQUEST_URL" NVARCHAR2(255),
  "REQUEST_IP" NVARCHAR2(255),
  "REQUEST_LOCATION" NVARCHAR2(255),
  "REQUEST_PARAMS" NCLOB,
  "REQUEST_TIME" DATE,
  "RESPONSE_RESULT" NCLOB,
  "RESPONSE_TIME" DATE,
  "ERROR_MSG" NCLOB,
  "EXECUTE_TIME" NUMBER(11,0),
  "STATUS" NVARCHAR2(2),
  "REMARK" NVARCHAR2(255),
  "CREATE_ID" NVARCHAR2(32),
  "CREATE_TIME" DATE,
  "UPDATE_ID" NVARCHAR2(32),
  "UPDATE_TIME" DATE,
  "IS_DELETE" NUMBER(11,0)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_LOG_OPER"."NAME" IS '操作名称';
COMMENT ON COLUMN "SYS_LOG_OPER"."TYPE" IS '操作类型，0=其他，1=新增，2=删除，3=修改，4=查询，5=导入，6=导出';
COMMENT ON COLUMN "SYS_LOG_OPER"."METHOD" IS '请求方法';
COMMENT ON COLUMN "SYS_LOG_OPER"."REQUEST_TYPE" IS '请求方式';
COMMENT ON COLUMN "SYS_LOG_OPER"."REQUEST_URL" IS '请求URL';
COMMENT ON COLUMN "SYS_LOG_OPER"."REQUEST_IP" IS '请求者IP';
COMMENT ON COLUMN "SYS_LOG_OPER"."REQUEST_LOCATION" IS '请求者地址';
COMMENT ON COLUMN "SYS_LOG_OPER"."REQUEST_PARAMS" IS '请求参数';
COMMENT ON COLUMN "SYS_LOG_OPER"."REQUEST_TIME" IS '请求时间';
COMMENT ON COLUMN "SYS_LOG_OPER"."RESPONSE_RESULT" IS '响应结果';
COMMENT ON COLUMN "SYS_LOG_OPER"."RESPONSE_TIME" IS '响应时间';
COMMENT ON COLUMN "SYS_LOG_OPER"."ERROR_MSG" IS '错误信息';
COMMENT ON COLUMN "SYS_LOG_OPER"."EXECUTE_TIME" IS '耗时时间（毫秒）';
COMMENT ON COLUMN "SYS_LOG_OPER"."STATUS" IS '请求结果，0=成功，1=失败';
COMMENT ON COLUMN "SYS_LOG_OPER"."REMARK" IS '备注';
COMMENT ON COLUMN "SYS_LOG_OPER"."CREATE_ID" IS '创建人';
COMMENT ON COLUMN "SYS_LOG_OPER"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "SYS_LOG_OPER"."UPDATE_ID" IS '修改人';
COMMENT ON COLUMN "SYS_LOG_OPER"."UPDATE_TIME" IS '修改时间';
COMMENT ON COLUMN "SYS_LOG_OPER"."IS_DELETE" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "SYS_LOG_OPER" IS '操作日志表';

-- ----------------------------
-- Records of SYS_LOG_OPER
-- ----------------------------

-- ----------------------------
-- Table structure for SYS_MENU
-- ----------------------------
DROP TABLE "SYS_MENU";
CREATE TABLE "SYS_MENU" (
  "ID" NVARCHAR2(32) NOT NULL,
  "PARENT_ID" NVARCHAR2(32),
  "MENU_NAME" NVARCHAR2(255),
  "ORDER_NUM" NUMBER(11,0),
  "PATH" NVARCHAR2(500),
  "QUERY_PARAM" NVARCHAR2(500),
  "COMPONENT" NVARCHAR2(500),
  "PERMS" NVARCHAR2(255),
  "ICON" NVARCHAR2(255),
  "VISIBLE" NVARCHAR2(2),
  "STATUS" NVARCHAR2(2),
  "MENU_TYPE" NVARCHAR2(2),
  "IS_FRAME" NVARCHAR2(2),
  "IS_CACHE" NVARCHAR2(2),
  "REMARK" NVARCHAR2(255),
  "CREATE_ID" NVARCHAR2(32),
  "CREATE_TIME" DATE,
  "UPDATE_ID" NVARCHAR2(32),
  "UPDATE_TIME" DATE,
  "IS_DELETE" NUMBER(11,0)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_MENU"."PARENT_ID" IS '父级部门ID，顶级为0';
COMMENT ON COLUMN "SYS_MENU"."MENU_NAME" IS '菜单名称';
COMMENT ON COLUMN "SYS_MENU"."ORDER_NUM" IS '排序';
COMMENT ON COLUMN "SYS_MENU"."PATH" IS '路由地址';
COMMENT ON COLUMN "SYS_MENU"."QUERY_PARAM" IS '路由参数';
COMMENT ON COLUMN "SYS_MENU"."COMPONENT" IS '组件路径';
COMMENT ON COLUMN "SYS_MENU"."PERMS" IS '权限标识';
COMMENT ON COLUMN "SYS_MENU"."ICON" IS '菜单图标';
COMMENT ON COLUMN "SYS_MENU"."VISIBLE" IS '显示状态，0=正常，1=隐藏';
COMMENT ON COLUMN "SYS_MENU"."STATUS" IS '菜单状态，0=正常，1=禁用';
COMMENT ON COLUMN "SYS_MENU"."MENU_TYPE" IS '菜单类型，1=目录，2=菜单，3=按钮';
COMMENT ON COLUMN "SYS_MENU"."IS_FRAME" IS '是否外链，0=是，1=否';
COMMENT ON COLUMN "SYS_MENU"."IS_CACHE" IS '是否缓存，0=缓存，1=不缓存';
COMMENT ON COLUMN "SYS_MENU"."REMARK" IS '备注';
COMMENT ON COLUMN "SYS_MENU"."CREATE_ID" IS '创建人';
COMMENT ON COLUMN "SYS_MENU"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "SYS_MENU"."UPDATE_ID" IS '修改人';
COMMENT ON COLUMN "SYS_MENU"."UPDATE_TIME" IS '修改时间';
COMMENT ON COLUMN "SYS_MENU"."IS_DELETE" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "SYS_MENU" IS '菜单表';

-- ----------------------------
-- Records of SYS_MENU
-- ----------------------------
INSERT INTO "SYS_MENU" VALUES ('1501', '1500', '测试单表查询', '1', '#', NULL, NULL, 'test:data:query', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1502', '1500', '测试单表新增', '2', '#', NULL, NULL, 'test:data:add', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1503', '1500', '测试单表修改', '3', '#', NULL, NULL, 'test:data:edit', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1504', '1500', '测试单表删除', '4', '#', NULL, NULL, 'test:data:remove', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1505', '1500', '测试单表导出', '5', '#', NULL, NULL, 'test:data:export', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1511', '1500', '测试单表导入', '6', '#', NULL, NULL, 'test:data:import', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1506', '5', '测试树表', '1', 'dataTree', NULL, 'test/dataTree/index', 'test:dataTree:list', '#', '0', '0', '2', '1', '0', '测试树表菜单', '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1507', '1506', '测试树表查询', '1', '#', NULL, NULL, 'test:dataTree:query', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1508', '1506', '测试树表新增', '2', '#', NULL, NULL, 'test:dataTree:add', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1509', '1506', '测试树表修改', '3', '#', NULL, NULL, 'test:dataTree:edit', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1510', '1506', '测试树表删除', '4', '#', NULL, NULL, 'test:dataTree:remove', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1600', '118', '文件查询', '1', '#', NULL, NULL, 'monitor:file:query', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1601', '118', '文件上传', '2', '#', NULL, NULL, 'monitor:file:upload', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1602', '118', '文件下载', '3', '#', NULL, NULL, 'monitor:file:download', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1603', '118', '文件删除', '4', '#', NULL, NULL, 'monitor:file:remove', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1604', '118', '配置添加', '5', '#', NULL, NULL, 'monitor:ossConig:add', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1605', '118', '配置编辑', '6', '#', NULL, NULL, 'monitor:ossConig:edit', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1698eeba543e9f12542edba21ae2d16f', '42708c34ab41a691ef032ab1f4cf5db0', '任务删除', '4', NULL, NULL, NULL, 'monitor:job:remove', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1f7c8a04250067db11e6fcae4298234e', 'd5d81e35c103ed1dac93a70b97819877', '限制修改', '3', NULL, NULL, NULL, 'monitor:apiLimit:edit', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('2', '0', '系统运维', '2', 'monitor', NULL, NULL, NULL, 'monitor', '0', '0', '1', '1', '0', '系统监控目录', '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('268571f3b6e74a1fc5bde8df2594ed81', '42708c34ab41a691ef032ab1f4cf5db0', '任务查询', '1', NULL, NULL, NULL, 'monitor:job:query', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('2add827d98c3b366ce0aa160d8a281ee', 'd5d81e35c103ed1dac93a70b97819877', '限制查询', '1', NULL, NULL, NULL, 'monitor:apiLimit:query', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('2b6cc0d2b8a0907d2832f9557089a682', 'd5d81e35c103ed1dac93a70b97819877', '限制新增', '2', NULL, NULL, NULL, 'monitor:apiLimit:add', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('2d562fa236077b42b5142eb869db6b18', '63854784d5ac52bfd2ac578a48f0a46f', '服务监控', '2', 'server', NULL, 'monitor/server/index', 'monitor:server:list', 'server', '0', '0', '2', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('3', '0', '系统工具', '3', 'tool', NULL, NULL, NULL, 'tool', '0', '0', '1', '1', '0', '系统工具目录', '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('36c032b1a5af71ad776641f68cbbeb60', '42708c34ab41a691ef032ab1f4cf5db0', '任务状态修改', '5', NULL, NULL, NULL, 'monitor:job:changeStatus', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('39cfe621da13b55952f2af58edc3e53a', '6b70f47f76199b8cd35aae0cda15d449', '发送钉钉', '3', NULL, NULL, NULL, 'tool:functest:sendDingTalk', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('42708c34ab41a691ef032ab1f4cf5db0', '2', '定时任务', '3', 'job', NULL, 'monitor/job/index', 'monitor:job:list', 'job', '0', '0', '2', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('5', '0', '测试菜单', '5', 'demo', NULL, NULL, NULL, 'star', '0', '0', '1', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('500', '108', '操作日志', '1', 'logOper', NULL, 'monitor/logOper/index', 'monitor:logOper:list', 'form', '0', '0', '2', '1', '0', '操作日志菜单', '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('501', '108', '登录日志', '2', 'logLogin', NULL, 'monitor/logLogin/index', 'monitor:logLogin:list', 'logininfor', '0', '0', '2', '1', '0', '登录日志菜单', '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('63854784d5ac52bfd2ac578a48f0a46f', '2', '系统监控', '2', 'monitor', NULL, NULL, NULL, 'time-range', '0', '0', '1', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('69519bae5ffa67623e8bfb1c8fabdd00', '118', '配置查询', '8', NULL, NULL, NULL, 'monitor:ossConig:query', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('6971d165938715753fef8a07c112ba98', '42708c34ab41a691ef032ab1f4cf5db0', '任务新增', '2', NULL, NULL, NULL, 'monitor:job:add', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('6b70f47f76199b8cd35aae0cda15d449', '3', '功能测试', '3', 'functest', NULL, 'tool/functest/index', 'tool:functest:main', 'example', '0', '0', '2', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('74456b2c60b6e0b5edd5559a8e0cc108', '106', '刷新参数缓存', '6', NULL, NULL, NULL, 'system:config:refreshCache', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('7a5af154f7d305797b56b9488207b04a', '118', '配置管理', '9', NULL, NULL, NULL, 'monitor:ossConig:list', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('7aea821f10adc345dc4a003170bb7f16', 'd5d81e35c103ed1dac93a70b97819877', '限制删除', '4', NULL, NULL, NULL, 'monitor:apiLimit:remove', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('85d0cbd4e4441e09f5df53214acddad9', '42708c34ab41a691ef032ab1f4cf5db0', '立即执行一次', '7', NULL, NULL, NULL, 'monitor:job:run', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('8833e71489eb0dfeb36d0769573408bd', '42708c34ab41a691ef032ab1f4cf5db0', '定时任务日志导出', '9', NULL, NULL, NULL, 'monitor:jobLog:export', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('928015b29ffdad7c6621f3b92ba85c6c', '63854784d5ac52bfd2ac578a48f0a46f', '缓存监控', '3', 'cache', NULL, 'monitor/cache/index', 'monitor:cache:list', 'redis', '0', '0', '2', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('937f0292079a4b62d9a26ebd5e8d57ed', '6b70f47f76199b8cd35aae0cda15d449', '发送短信', '2', NULL, NULL, NULL, 'tool:functest:sendSms', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('9901', '105', '刷新字典缓存', '6', '#', NULL, NULL, 'system:dict:refreshCache', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('adec0c7b9d6b37672dcb6b1315f8f4eb', '42708c34ab41a691ef032ab1f4cf5db0', '定时任务日志查询', '10', NULL, NULL, NULL, 'monitor:jobLog:query', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('b87a985b4ef717afe650f2df9cdbe087', '118', '配置删除', '7', NULL, NULL, NULL, 'monitor:ossConig:remove', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('c0a98c5b6e56602800968efcacfa4e47', '6b70f47f76199b8cd35aae0cda15d449', '发送邮件', '1', NULL, NULL, NULL, 'tool:functest:sendEmail', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('c6e5f363977c052a6a6d6098b0736c8e', '42708c34ab41a691ef032ab1f4cf5db0', '定时任务日志删除', '11', NULL, NULL, NULL, 'monitor:jobLog:remove', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('d5d81e35c103ed1dac93a70b97819877', '2', '接口限制', '4', 'apiLimit', NULL, 'monitor/apiLimit/index', 'monitor:apiLimit:list', 'lock', '0', '0', '2', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('ea70f3a842730a50686acc46fb981800', '42708c34ab41a691ef032ab1f4cf5db0', '定时任务日志列表', '8', NULL, NULL, NULL, 'monitor:jobLog:list', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('f37ba75d3347c084b50727ff35a386fa', 'd5d81e35c103ed1dac93a70b97819877', '限制状态修改', '5', NULL, NULL, NULL, 'monitor:apiLimit:changeStatus', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('106', '1', '参数设置', '7', 'config', NULL, 'system/config/index', 'system:config:list', 'edit', '0', '0', '2', '1', '0', '参数设置菜单', '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('0451a199b76ce5725fc8003ba7cc56d4', 'd5d81e35c103ed1dac93a70b97819877', '刷新限制缓存', '6', NULL, NULL, NULL, 'monitor:apiLimit:refreshCache', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1', '0', '系统管理', '1', 'system', NULL, NULL, NULL, 'system', '0', '0', '1', '1', '0', '系统管理目录', '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('100', '1', '用户管理', '1', 'user', NULL, 'system/user/index', 'system:user:list', 'user', '0', '0', '2', '1', '0', '用户管理菜单', '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1001', '100', '用户查询', '1', NULL, NULL, NULL, 'system:user:query', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1002', '100', '用户新增', '2', NULL, NULL, NULL, 'system:user:add', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1003', '100', '用户修改', '3', NULL, NULL, NULL, 'system:user:edit', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1004', '100', '用户删除', '4', NULL, NULL, NULL, 'system:user:remove', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1005', '100', '用户导出', '5', NULL, NULL, NULL, 'system:user:export', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1006', '100', '用户导入', '6', NULL, NULL, NULL, 'system:user:import', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1007', '100', '重置密码', '7', NULL, NULL, NULL, 'system:user:resetPwd', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1008', '101', '角色查询', '1', NULL, NULL, NULL, 'system:role:query', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1009', '101', '角色新增', '2', NULL, NULL, NULL, 'system:role:add', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('101', '1', '角色管理', '2', 'role', NULL, 'system/role/index', 'system:role:list', 'peoples', '0', '0', '2', '1', '0', '角色管理菜单', '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1010', '101', '角色修改', '3', NULL, NULL, NULL, 'system:role:edit', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1011', '101', '角色删除', '4', NULL, NULL, NULL, 'system:role:remove', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1012', '101', '角色导出', '5', NULL, NULL, NULL, 'system:role:export', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1013', '102', '菜单查询', '1', NULL, NULL, NULL, 'system:menu:query', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1014', '102', '菜单新增', '2', NULL, NULL, NULL, 'system:menu:add', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1015', '102', '菜单修改', '3', NULL, NULL, NULL, 'system:menu:edit', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1016', '102', '菜单删除', '4', NULL, NULL, NULL, 'system:menu:remove', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1017', '103', '部门查询', '1', NULL, NULL, NULL, 'system:dept:query', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1018', '103', '部门新增', '2', NULL, NULL, NULL, 'system:dept:add', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1019', '103', '部门修改', '3', NULL, NULL, NULL, 'system:dept:edit', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('102', '1', '菜单管理', '3', 'menu', NULL, 'system/menu/index', 'system:menu:list', 'tree-table', '0', '0', '2', '1', '0', '菜单管理菜单', '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1020', '103', '部门删除', '4', NULL, NULL, NULL, 'system:dept:remove', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1021', '104', '岗位查询', '1', NULL, NULL, NULL, 'system:position:query', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1022', '104', '岗位新增', '2', NULL, NULL, NULL, 'system:position:add', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1023', '104', '岗位修改', '3', NULL, NULL, NULL, 'system:position:edit', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1024', '104', '岗位删除', '4', NULL, NULL, NULL, 'system:position:remove', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1025', '104', '岗位导出', '5', NULL, NULL, NULL, 'system:position:export', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1026', '105', '字典查询', '1', '#', NULL, NULL, 'system:dict:query', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1027', '105', '字典新增', '2', '#', NULL, NULL, 'system:dict:add', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1028', '105', '字典修改', '3', '#', NULL, NULL, 'system:dict:edit', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1029', '105', '字典删除', '4', '#', NULL, NULL, 'system:dict:remove', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('103', '1', '部门管理', '4', 'dept', NULL, 'system/dept/index', 'system:dept:list', 'tree', '0', '0', '2', '1', '0', '部门管理菜单', '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1030', '105', '字典导出', '5', '#', NULL, NULL, 'system:dict:export', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1031', '106', '参数查询', '1', '#', NULL, NULL, 'system:config:query', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1032', '106', '参数新增', '2', '#', NULL, NULL, 'system:config:add', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1033', '106', '参数修改', '3', '#', NULL, NULL, 'system:config:edit', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1034', '106', '参数删除', '4', '#', NULL, NULL, 'system:config:remove', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1035', '106', '参数导出', '5', '#', NULL, NULL, 'system:config:export', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1036', '107', '公告查询', '1', '#', NULL, NULL, 'monitor:notice:query', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1037', '107', '公告新增', '2', '#', NULL, NULL, 'monitor:notice:add', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1038', '107', '公告修改', '3', '#', NULL, NULL, 'monitor:notice:edit', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1039', '107', '公告删除', '4', '#', NULL, NULL, 'monitor:notice:remove', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('104', '1', '岗位管理', '5', 'position', NULL, 'system/position/index', 'system:position:list', 'post', '0', '0', '2', '1', '0', '岗位管理菜单', '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1040', '500', '操作日志查询', '1', '#', NULL, NULL, 'monitor:logOper:query', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1041', '500', '操作日志删除', '2', '#', NULL, NULL, 'monitor:logOper:remove', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1042', '500', '操作日志导出', '4', '#', NULL, NULL, 'monitor:logOper:export', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1043', '501', '登录日志查询', '1', '#', NULL, NULL, 'monitor:logLogin:query', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1044', '501', '登录日志删除', '2', '#', NULL, NULL, 'monitor:logLogin:remove', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1045', '501', '日志日志导出', '3', '#', NULL, NULL, 'monitor:logLogin:export', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('105', '1', '字典管理', '6', 'dict', NULL, 'system/dict/index', 'system:dict:list', 'dict', '0', '0', '2', '1', '0', '字典管理菜单', '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('107', '2', '通知公告', '5', 'notice', NULL, 'monitor/notice/index', 'monitor:notice:list', 'message', '0', '0', '2', '1', '0', '通知公告菜单', '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('108', '2', '日志管理', '1', 'log', NULL, NULL, NULL, 'log', '0', '0', '1', '1', '0', '日志管理菜单', '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('111', '63854784d5ac52bfd2ac578a48f0a46f', '数据监控', '1', 'druid', NULL, 'monitor/druid/index', 'monitor:druid:list', 'druid', '0', '0', '2', '1', '0', '数据监控菜单', '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('114', '3', '表单构建', '1', 'build', NULL, 'tool/build/index', 'tool:build:list', 'build', '0', '0', '2', '1', '0', '表单构建菜单', '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('118', '2', '文件管理', '6', 'file', NULL, 'monitor/file/index', 'monitor:file:list', 'upload', '0', '0', '2', '1', '0', '文件管理菜单', '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('13cbe1c8683a41dae7bccb289fd5dfd9', '42708c34ab41a691ef032ab1f4cf5db0', '任务修改', '3', NULL, NULL, NULL, 'monitor:job:edit', '#', '0', '0', '3', '1', '0', NULL, '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_MENU" VALUES ('1500', '5', '测试单表', '1', 'data', NULL, 'test/data/index', 'test:data:list', '#', '0', '0', '2', '1', '0', '测试单表菜单', '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-01 14:34:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');

-- ----------------------------
-- Table structure for SYS_NOTICE
-- ----------------------------
DROP TABLE "SYS_NOTICE";
CREATE TABLE "SYS_NOTICE" (
  "ID" NVARCHAR2(32) NOT NULL,
  "TITLE" NVARCHAR2(255),
  "CONTENT" NCLOB,
  "TYPE" NVARCHAR2(2),
  "STATUS" NVARCHAR2(2),
  "REMARK" NVARCHAR2(255),
  "CREATE_ID" NVARCHAR2(32),
  "CREATE_TIME" DATE,
  "UPDATE_ID" NVARCHAR2(32),
  "UPDATE_TIME" DATE,
  "IS_DELETE" NUMBER(11,0)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_NOTICE"."TITLE" IS '标题';
COMMENT ON COLUMN "SYS_NOTICE"."CONTENT" IS '内容';
COMMENT ON COLUMN "SYS_NOTICE"."TYPE" IS '类型，1=通知，2=公告';
COMMENT ON COLUMN "SYS_NOTICE"."STATUS" IS '状态，0=正常，1=关闭';
COMMENT ON COLUMN "SYS_NOTICE"."REMARK" IS '备注';
COMMENT ON COLUMN "SYS_NOTICE"."CREATE_ID" IS '创建人';
COMMENT ON COLUMN "SYS_NOTICE"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "SYS_NOTICE"."UPDATE_ID" IS '修改人';
COMMENT ON COLUMN "SYS_NOTICE"."UPDATE_TIME" IS '修改时间';
COMMENT ON COLUMN "SYS_NOTICE"."IS_DELETE" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "SYS_NOTICE" IS '通知公告表';

-- ----------------------------
-- Records of SYS_NOTICE
-- ----------------------------
INSERT INTO "SYS_NOTICE" VALUES ('5f98161ae8c06f99fbdcbb267908da58', '快讯！外媒：美国“星舰”发射时发生爆炸', '<p>【环球网快讯】据英国《每日电讯报》刚刚消息，由美国太空探索技术公司（SpaceX）研发的最新一代运载火箭系统“星舰”当时时间20日再次发射，但在发射三分钟后，“超重型推进器”部分似乎未能分离，在高空发生爆炸。</p><p><br></p><p>SpaceX官网介绍称，“星舰”火箭系统第一级助推器被称为“超重型推进器”，第二级被称为“星舰”飞船。其中“超重型推进器”高达70米，直径9米，配置33台“猛禽”发动机，使用液态甲烷/液氧推进剂，安装4个固定栅格翼，不配置着陆腿，SpaceX公司计划采用地面塔架“捕捉”的形式进行回收。</p>', '2', '1', NULL, '1', TO_DATE('2023-04-20 22:43:09', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-20 22:43:25', 'SYYYY-MM-DD HH24:MI:SS'), '0');

-- ----------------------------
-- Table structure for SYS_OSS_CONFIG
-- ----------------------------
DROP TABLE "SYS_OSS_CONFIG";
CREATE TABLE "SYS_OSS_CONFIG" (
  "ID" NVARCHAR2(32) NOT NULL,
  "NAME" NVARCHAR2(255),
  "ACCESS_KEY" NVARCHAR2(255),
  "SECRET_KEY" NVARCHAR2(255),
  "BUCKET_NAME" NVARCHAR2(255),
  "PREFIX" NVARCHAR2(255),
  "ENDPOINT" NVARCHAR2(255),
  "DOMAIN" NVARCHAR2(255),
  "IS_HTTPS" NCHAR(2),
  "REGION" NVARCHAR2(255),
  "ACCESS_POLICY" NCHAR(2),
  "IN_USE" NVARCHAR2(2),
  "STATUS" NVARCHAR2(2),
  "REMARK" NVARCHAR2(255),
  "CREATE_ID" NVARCHAR2(32),
  "CREATE_TIME" DATE,
  "UPDATE_ID" NVARCHAR2(32),
  "UPDATE_TIME" DATE,
  "IS_DELETE" NUMBER(11,0)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_OSS_CONFIG"."NAME" IS '配置名称，例如：minio、阿里云、腾讯云、七牛云、京东云、华为云';
COMMENT ON COLUMN "SYS_OSS_CONFIG"."ACCESS_KEY" IS 'accessKey';
COMMENT ON COLUMN "SYS_OSS_CONFIG"."SECRET_KEY" IS 'secretKey';
COMMENT ON COLUMN "SYS_OSS_CONFIG"."BUCKET_NAME" IS '存储桶名称';
COMMENT ON COLUMN "SYS_OSS_CONFIG"."PREFIX" IS '前缀';
COMMENT ON COLUMN "SYS_OSS_CONFIG"."ENDPOINT" IS '访问站点endpoint';
COMMENT ON COLUMN "SYS_OSS_CONFIG"."DOMAIN" IS '自定义域名';
COMMENT ON COLUMN "SYS_OSS_CONFIG"."IS_HTTPS" IS '是否https，0=否，1=是';
COMMENT ON COLUMN "SYS_OSS_CONFIG"."REGION" IS '地域';
COMMENT ON COLUMN "SYS_OSS_CONFIG"."ACCESS_POLICY" IS '存储桶权限类型，0=private，1=public，2=custom';
COMMENT ON COLUMN "SYS_OSS_CONFIG"."IN_USE" IS '正在使用，0=否，1=是（在所有数据中，只有一条数据可是正在使用）';
COMMENT ON COLUMN "SYS_OSS_CONFIG"."STATUS" IS '配置状态，0=正常，1=禁用';
COMMENT ON COLUMN "SYS_OSS_CONFIG"."REMARK" IS '备注';
COMMENT ON COLUMN "SYS_OSS_CONFIG"."CREATE_ID" IS '创建人';
COMMENT ON COLUMN "SYS_OSS_CONFIG"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "SYS_OSS_CONFIG"."UPDATE_ID" IS '修改人';
COMMENT ON COLUMN "SYS_OSS_CONFIG"."UPDATE_TIME" IS '修改时间';
COMMENT ON COLUMN "SYS_OSS_CONFIG"."IS_DELETE" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "SYS_OSS_CONFIG" IS 'OSS配置表';

-- ----------------------------
-- Records of SYS_OSS_CONFIG
-- ----------------------------
INSERT INTO "SYS_OSS_CONFIG" VALUES ('02a4c0e40c271fd516ea3ea566ade22e', '七牛云', '1', '13m', 'my-dev-test', 'MyAdmin', 's3-cn-north-1.qiniucs.com', 'qiniu.daenx.cn', '1 ', 'cn-north-1', '1 ', '0', '0', NULL, '1', TO_DATE('2023-04-13 22:38:26', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-19 22:12:21', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_OSS_CONFIG" VALUES ('3c59bfcc59b182930fe2d9c41246c50a', 'minio', '2', '2', 'test', 'MyAdmin', '127.0.0.1:9000', NULL, '0 ', NULL, '1 ', '1', '0', NULL, '1', TO_DATE('2023-04-16 17:03:47', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-19 22:12:21', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_OSS_CONFIG" VALUES ('k8jowsd604opw17q6hs8gze05s2ybpqr', '腾讯云', '3', '3', 'daen-1251663445', 'MyAdmin', 'cos.ap-nanjing.myqcloud.com', NULL, '0 ', 'ap-nanjing', '1 ', '0', '0', NULL, '1', TO_DATE('2023-04-16 20:06:31', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-19 22:12:21', 'SYYYY-MM-DD HH24:MI:SS'), '0');

-- ----------------------------
-- Table structure for SYS_POSITION
-- ----------------------------
DROP TABLE "SYS_POSITION";
CREATE TABLE "SYS_POSITION" (
  "ID" NVARCHAR2(32) NOT NULL,
  "NAME" NVARCHAR2(255),
  "CODE" NVARCHAR2(255),
  "SUMMARY" NCLOB,
  "SORT" NUMBER(11,0),
  "STATUS" NVARCHAR2(2),
  "REMARK" NVARCHAR2(255),
  "CREATE_ID" NVARCHAR2(32),
  "CREATE_TIME" DATE,
  "UPDATE_ID" NVARCHAR2(32),
  "UPDATE_TIME" DATE,
  "IS_DELETE" NUMBER(11,0)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_POSITION"."NAME" IS '岗位名称';
COMMENT ON COLUMN "SYS_POSITION"."CODE" IS '岗位编码';
COMMENT ON COLUMN "SYS_POSITION"."SUMMARY" IS '岗位简介';
COMMENT ON COLUMN "SYS_POSITION"."SORT" IS '排序';
COMMENT ON COLUMN "SYS_POSITION"."STATUS" IS '岗位状态，0=正常，1=禁用';
COMMENT ON COLUMN "SYS_POSITION"."REMARK" IS '备注';
COMMENT ON COLUMN "SYS_POSITION"."CREATE_ID" IS '创建人';
COMMENT ON COLUMN "SYS_POSITION"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "SYS_POSITION"."UPDATE_ID" IS '修改人';
COMMENT ON COLUMN "SYS_POSITION"."UPDATE_TIME" IS '修改时间';
COMMENT ON COLUMN "SYS_POSITION"."IS_DELETE" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "SYS_POSITION" IS '岗位表';

-- ----------------------------
-- Records of SYS_POSITION
-- ----------------------------
INSERT INTO "SYS_POSITION" VALUES ('1', '董事长', 'ceo', '负责公司的发展等', '1', '0', NULL, '1', TO_DATE('2023-04-19 22:24:04', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-19 22:24:04', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_POSITION" VALUES ('2', '项目经理', 'se', NULL, '2', '0', NULL, '1', TO_DATE('2023-04-16 23:55:15', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-16 23:55:15', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_POSITION" VALUES ('3', '人力资源', 'hr', NULL, '3', '0', NULL, '1', TO_DATE('2023-03-29 10:38:22', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-29 10:38:22', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_POSITION" VALUES ('4', '普通员工', 'user', NULL, '4', '0', NULL, '1', TO_DATE('2023-04-19 22:23:37', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-19 22:23:37', 'SYYYY-MM-DD HH24:MI:SS'), '0');

-- ----------------------------
-- Table structure for SYS_POSITION_USER
-- ----------------------------
DROP TABLE "SYS_POSITION_USER";
CREATE TABLE "SYS_POSITION_USER" (
  "ID" NVARCHAR2(32) NOT NULL,
  "POSITION_ID" NVARCHAR2(32),
  "USER_ID" NVARCHAR2(32)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_POSITION_USER"."POSITION_ID" IS '岗位ID';
COMMENT ON COLUMN "SYS_POSITION_USER"."USER_ID" IS '用户ID';
COMMENT ON TABLE "SYS_POSITION_USER" IS '岗位用户关联表';

-- ----------------------------
-- Records of SYS_POSITION_USER
-- ----------------------------
INSERT INTO "SYS_POSITION_USER" VALUES ('1', '1', '1');
INSERT INTO "SYS_POSITION_USER" VALUES ('2', '3', '1');
INSERT INTO "SYS_POSITION_USER" VALUES ('42e86a19ad715b820d5c8cf7b3b72903', '4', '1650855280223846402');
INSERT INTO "SYS_POSITION_USER" VALUES ('717fdf27dcdd03d408eb36a1ef984758', '4', '2');
INSERT INTO "SYS_POSITION_USER" VALUES ('b3c2b07469aaf75e3a713a26ace28fee', '3', '2');
INSERT INTO "SYS_POSITION_USER" VALUES ('ca85f7ed198da507a18615493bcb88ae', '4', '1657562040456413185');
INSERT INTO "SYS_POSITION_USER" VALUES ('f0f6c52d96ddb3348c09119bf8bab858', '4', '1640613543627415553');

-- ----------------------------
-- Table structure for SYS_ROLE
-- ----------------------------
DROP TABLE "SYS_ROLE";
CREATE TABLE "SYS_ROLE" (
  "ID" NVARCHAR2(32) NOT NULL,
  "NAME" NVARCHAR2(255),
  "CODE" NVARCHAR2(255),
  "SORT" NUMBER(11,0),
  "DATA_SCOPE" NVARCHAR2(2),
  "MENU_CHECK_STRICTLY" NUMBER(11,0),
  "DEPT_CHECK_STRICTLY" NUMBER(11,0),
  "STATUS" NVARCHAR2(2),
  "REMARK" NVARCHAR2(255),
  "CREATE_ID" NVARCHAR2(32),
  "CREATE_TIME" DATE,
  "UPDATE_ID" NVARCHAR2(32),
  "UPDATE_TIME" DATE,
  "IS_DELETE" NUMBER(11,0)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_ROLE"."NAME" IS '角色名称';
COMMENT ON COLUMN "SYS_ROLE"."CODE" IS '角色编码';
COMMENT ON COLUMN "SYS_ROLE"."SORT" IS '排序';
COMMENT ON COLUMN "SYS_ROLE"."DATA_SCOPE" IS '数据权限，0=本人数据，1=本部门数据，2=本部门及以下数据，3=全部数据，4=自定义权限';
COMMENT ON COLUMN "SYS_ROLE"."MENU_CHECK_STRICTLY" IS '菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）';
COMMENT ON COLUMN "SYS_ROLE"."DEPT_CHECK_STRICTLY" IS '部门树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）';
COMMENT ON COLUMN "SYS_ROLE"."STATUS" IS '角色状态，0=正常，1=禁用';
COMMENT ON COLUMN "SYS_ROLE"."REMARK" IS '备注';
COMMENT ON COLUMN "SYS_ROLE"."CREATE_ID" IS '创建人';
COMMENT ON COLUMN "SYS_ROLE"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "SYS_ROLE"."UPDATE_ID" IS '修改人';
COMMENT ON COLUMN "SYS_ROLE"."UPDATE_TIME" IS '修改时间';
COMMENT ON COLUMN "SYS_ROLE"."IS_DELETE" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "SYS_ROLE" IS '角色表';

-- ----------------------------
-- Records of SYS_ROLE
-- ----------------------------
INSERT INTO "SYS_ROLE" VALUES ('1', '超级管理员', 'admin', '1', '3', '1', '1', '0', NULL, '1', TO_DATE('2023-03-31 21:04:06', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-31 21:04:09', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_ROLE" VALUES ('2', '普通用户', 'user', '2', '2', '1', '1', '0', NULL, '1', TO_DATE('2023-03-31 21:04:08', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-06-28 10:12:48', 'SYYYY-MM-DD HH24:MI:SS'), '0');

-- ----------------------------
-- Table structure for SYS_ROLE_DEPT
-- ----------------------------
DROP TABLE "SYS_ROLE_DEPT";
CREATE TABLE "SYS_ROLE_DEPT" (
  "ID" NVARCHAR2(32) NOT NULL,
  "ROLE_ID" NVARCHAR2(32),
  "DEPT_ID" NVARCHAR2(32)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_ROLE_DEPT"."ROLE_ID" IS '角色ID';
COMMENT ON COLUMN "SYS_ROLE_DEPT"."DEPT_ID" IS '部门ID';
COMMENT ON TABLE "SYS_ROLE_DEPT" IS '角色部门关联表';

-- ----------------------------
-- Records of SYS_ROLE_DEPT
-- ----------------------------

-- ----------------------------
-- Table structure for SYS_ROLE_MENU
-- ----------------------------
DROP TABLE "SYS_ROLE_MENU";
CREATE TABLE "SYS_ROLE_MENU" (
  "ID" NVARCHAR2(32) NOT NULL,
  "ROLE_ID" NVARCHAR2(32),
  "MENU_ID" NVARCHAR2(32)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_ROLE_MENU"."ROLE_ID" IS '角色ID';
COMMENT ON COLUMN "SYS_ROLE_MENU"."MENU_ID" IS '菜单ID';
COMMENT ON TABLE "SYS_ROLE_MENU" IS '角色菜单关联表';

-- ----------------------------
-- Records of SYS_ROLE_MENU
-- ----------------------------
INSERT INTO "SYS_ROLE_MENU" VALUES ('f28d06f07d1b32b4f6a2335d63f1337b', '2', '1031');
INSERT INTO "SYS_ROLE_MENU" VALUES ('f29d8b556b6d3f84e3c1099305bfaa0a', '2', '107');
INSERT INTO "SYS_ROLE_MENU" VALUES ('f404c15c05ff60c6b269afd153605d3a', '2', '1036');
INSERT INTO "SYS_ROLE_MENU" VALUES ('f4515d9717e0085d6cbcd68a73062bfb', '2', '1016');
INSERT INTO "SYS_ROLE_MENU" VALUES ('010e144047516a84fc15cf639df13a76', '2', '1604');
INSERT INTO "SYS_ROLE_MENU" VALUES ('0442a38a627b41631433a2b8a134f460', '2', '1015');
INSERT INTO "SYS_ROLE_MENU" VALUES ('04967f1e50ae400476949b6d488e8368', '2', '1028');
INSERT INTO "SYS_ROLE_MENU" VALUES ('04992e2858f6174c89953bf9963394b1', '2', '106');
INSERT INTO "SYS_ROLE_MENU" VALUES ('059593dc769e38c8c334a4216c90c444', '2', '1039');
INSERT INTO "SYS_ROLE_MENU" VALUES ('083f98427d45d5254cd260aa36754923', '2', '118');
INSERT INTO "SYS_ROLE_MENU" VALUES ('096284fdae4ac4c45e0168a9cc09207f', '2', '103');
INSERT INTO "SYS_ROLE_MENU" VALUES ('09f15e83eb86899aa3016675831ff6e4', '2', '1508');
INSERT INTO "SYS_ROLE_MENU" VALUES ('0c2832d252a4519b89ea16f5d5667cd3', '2', '1507');
INSERT INTO "SYS_ROLE_MENU" VALUES ('0f18520aa1a03964afa0e90ef6f2d1de', '2', '3');
INSERT INTO "SYS_ROLE_MENU" VALUES ('134ed69e973e1f12e2cf05c23f456d47', '2', '1024');
INSERT INTO "SYS_ROLE_MENU" VALUES ('1455d3272bbf52deb1e4801102df8acc', '2', '937f0292079a4b62d9a26ebd5e8d57ed');
INSERT INTO "SYS_ROLE_MENU" VALUES ('17c1c7b2c455c59c7e1a035ccb4aa334', '2', '1002');
INSERT INTO "SYS_ROLE_MENU" VALUES ('17ff84f9922d1ec1d1c488000f7d9650', '2', '1021');
INSERT INTO "SYS_ROLE_MENU" VALUES ('1f73ea18235b80a6865faffba1ba871d', '2', '1032');
INSERT INTO "SYS_ROLE_MENU" VALUES ('20a95f48fd4de639ca9f426d37ad2e81', '2', '105');
INSERT INTO "SYS_ROLE_MENU" VALUES ('221ff7ffe9e3dc4ef90fb8ee4bd557ed', '2', '1042');
INSERT INTO "SYS_ROLE_MENU" VALUES ('23c9aabdb43a8178309a8681e1951683', '2', '1010');
INSERT INTO "SYS_ROLE_MENU" VALUES ('2481a08f1c848869dd814102febe1904', '2', '1');
INSERT INTO "SYS_ROLE_MENU" VALUES ('2dc1d7359c6687a6456802e1d3d9362b', '2', '1038');
INSERT INTO "SYS_ROLE_MENU" VALUES ('2ddb711279213abfe6cbd9cf7d78d445', '2', '1502');
INSERT INTO "SYS_ROLE_MENU" VALUES ('30fbfad02e7d9f1c1fd1092d56aefd91', '2', '1020');
INSERT INTO "SYS_ROLE_MENU" VALUES ('339ad61ae8cc820051c88da232db4d45', '2', '1003');
INSERT INTO "SYS_ROLE_MENU" VALUES ('3600c28e83efcf4ccb9c8df43777fa76', '2', '114');
INSERT INTO "SYS_ROLE_MENU" VALUES ('37f0f31815e06c4b2124194350fc2689', '2', '1501');
INSERT INTO "SYS_ROLE_MENU" VALUES ('39349f2070d298787e7a1531236d7d53', '2', '1029');
INSERT INTO "SYS_ROLE_MENU" VALUES ('3c5c0780039a7e16b2f38c2481b192d3', '2', '1027');
INSERT INTO "SYS_ROLE_MENU" VALUES ('41a2c7c92bf8436b727ba454fb9863a2', '2', '1505');
INSERT INTO "SYS_ROLE_MENU" VALUES ('46e194517968aa60dc6bdf44c4181429', '2', '69519bae5ffa67623e8bfb1c8fabdd00');
INSERT INTO "SYS_ROLE_MENU" VALUES ('4903d49065e23c68dc880db1248e6039', '2', 'b87a985b4ef717afe650f2df9cdbe087');
INSERT INTO "SYS_ROLE_MENU" VALUES ('49253b13004c15ad6c88064a787d6398', '2', '1006');
INSERT INTO "SYS_ROLE_MENU" VALUES ('4e8f3278c9db0b5f28a0b43e877162df', '2', '1605');
INSERT INTO "SYS_ROLE_MENU" VALUES ('58214e427dc03b0134f5283d4a5feccc', '2', 'c0a98c5b6e56602800968efcacfa4e47');
INSERT INTO "SYS_ROLE_MENU" VALUES ('598fb925ea65211e37ee2d976b43b03f', '2', '1506');
INSERT INTO "SYS_ROLE_MENU" VALUES ('5b044380ef24ff067934acd419c93935', '2', '2d562fa236077b42b5142eb869db6b18');
INSERT INTO "SYS_ROLE_MENU" VALUES ('5c431aaf26ee0ae6fc6544d141c00694', '2', '1040');
INSERT INTO "SYS_ROLE_MENU" VALUES ('64db475d623d29556ce6846e725e7778', '2', '1030');
INSERT INTO "SYS_ROLE_MENU" VALUES ('659645051d2c3a5f3186e95f2f02458b', '2', '6b70f47f76199b8cd35aae0cda15d449');
INSERT INTO "SYS_ROLE_MENU" VALUES ('660d48dceea79ebaa7ca29a4bc76e867', '2', '1500');
INSERT INTO "SYS_ROLE_MENU" VALUES ('6cc1d3c6030ac4a6daf52d301e8d5130', '2', '63854784d5ac52bfd2ac578a48f0a46f');
INSERT INTO "SYS_ROLE_MENU" VALUES ('6ce0aa79b167176bd10dc82cf4511321', '2', '1001');
INSERT INTO "SYS_ROLE_MENU" VALUES ('6d431f6df7d19e09ab4a157629e969f2', '2', '1005');
INSERT INTO "SYS_ROLE_MENU" VALUES ('710045b55eed9d79d4198984dead8359', '2', '1035');
INSERT INTO "SYS_ROLE_MENU" VALUES ('72d70a683fc836cf99daf5e161507ee2', '2', '1022');
INSERT INTO "SYS_ROLE_MENU" VALUES ('7356e376180d8a2795e75987312ce959', '2', '1007');
INSERT INTO "SYS_ROLE_MENU" VALUES ('764c287b3ccf95eac148f0a0e2e8c756', '2', '1504');
INSERT INTO "SYS_ROLE_MENU" VALUES ('79ced85499e46af653928e63f9cdd66a', '2', '1026');
INSERT INTO "SYS_ROLE_MENU" VALUES ('7bddfaf7e64262a85fc149c73b72b8eb', '2', '1013');
INSERT INTO "SYS_ROLE_MENU" VALUES ('7e8f1d0e77b174a93cd28665698a28b3', '2', '1008');
INSERT INTO "SYS_ROLE_MENU" VALUES ('80a71c4a9e21922bbe582a3e3b9fba5a', '2', '1023');
INSERT INTO "SYS_ROLE_MENU" VALUES ('828f59bcb7e306c68816468437469e61', '2', '501');
INSERT INTO "SYS_ROLE_MENU" VALUES ('8bd733bd34d3297fed3845be9c9466af', '2', '1034');
INSERT INTO "SYS_ROLE_MENU" VALUES ('8f77b9b099df75349d378a1eb42f41ec', '2', '1017');
INSERT INTO "SYS_ROLE_MENU" VALUES ('934ff858eebebf28fa2364d0dd7efdc5', '2', '1043');
INSERT INTO "SYS_ROLE_MENU" VALUES ('96d7b500a08728d11c4a2cb953598e56', '2', '1044');
INSERT INTO "SYS_ROLE_MENU" VALUES ('9a97fabed733a7dcba2000cac33de671', '2', '7a5af154f7d305797b56b9488207b04a');
INSERT INTO "SYS_ROLE_MENU" VALUES ('9daeb32b6f985ade43c2b0e052771ef2', '2', '100');
INSERT INTO "SYS_ROLE_MENU" VALUES ('a2ce0930df87870d78d4008e444689f8', '2', '5');
INSERT INTO "SYS_ROLE_MENU" VALUES ('a73949740b67262365b49d0f5ec75e17', '2', '1033');
INSERT INTO "SYS_ROLE_MENU" VALUES ('aa2f14fec48b6da5b4821ad22eb061a0', '2', '1011');
INSERT INTO "SYS_ROLE_MENU" VALUES ('aa30ec405c81e24a83226a22e1d426b9', '2', '1601');
INSERT INTO "SYS_ROLE_MENU" VALUES ('ab972376b8a9082cba0a7bd84c8a116d', '2', '1045');
INSERT INTO "SYS_ROLE_MENU" VALUES ('afa719c5d645a365bf910ebbb32b227c', '2', '108');
INSERT INTO "SYS_ROLE_MENU" VALUES ('b9e74e369f7e0f410a05f82ff51d19c8', '2', '102');
INSERT INTO "SYS_ROLE_MENU" VALUES ('c116881cb3c3fc012f396475d00b49f3', '2', '104');
INSERT INTO "SYS_ROLE_MENU" VALUES ('c2d6f3184eaae24dc784cb10b40e644f', '2', '1510');
INSERT INTO "SYS_ROLE_MENU" VALUES ('c49e969855bf0b98477e3301141b337e', '2', '39cfe621da13b55952f2af58edc3e53a');
INSERT INTO "SYS_ROLE_MENU" VALUES ('ca5968e0a84015865ce5b886f26640ec', '2', '111');
INSERT INTO "SYS_ROLE_MENU" VALUES ('ccc35335c199e05c8e5020444abef18b', '2', '500');
INSERT INTO "SYS_ROLE_MENU" VALUES ('cdf14f2dfc13bfbe684e39a97899bbdc', '2', '1014');
INSERT INTO "SYS_ROLE_MENU" VALUES ('d55aa1b25ec6b4db929793532b69e8b9', '2', '1037');
INSERT INTO "SYS_ROLE_MENU" VALUES ('d84c0d4fb4e50df5ee690e7e855960bd', '2', '1012');
INSERT INTO "SYS_ROLE_MENU" VALUES ('deb53079c64334ae7ef6edccddaba0b7', '2', '101');
INSERT INTO "SYS_ROLE_MENU" VALUES ('e1c98c8cf71a0307b8eda1a0a2f44f72', '2', '1025');
INSERT INTO "SYS_ROLE_MENU" VALUES ('e24d2b9811c6c9a7f9e41976b0b1335e', '2', '1019');
INSERT INTO "SYS_ROLE_MENU" VALUES ('e2c3126cf2799f8cc1e78ac900c5a46f', '2', '1004');
INSERT INTO "SYS_ROLE_MENU" VALUES ('e5a9d3ffeb6559c27992ee0b747cc91d', '2', '1509');
INSERT INTO "SYS_ROLE_MENU" VALUES ('e9870cd1a138158d6b2c1b902e25b4ce', '2', '1041');
INSERT INTO "SYS_ROLE_MENU" VALUES ('ea58cbc4210bf4810c8d86d9cbc404a6', '2', '1600');
INSERT INTO "SYS_ROLE_MENU" VALUES ('ea9d229b2a5c859f414528a5b2449194', '2', '1009');
INSERT INTO "SYS_ROLE_MENU" VALUES ('ed7f5e174396e092bedd878283b68318', '2', '2');
INSERT INTO "SYS_ROLE_MENU" VALUES ('ef34997f6f4c80b8377d6ec32cc0b948', '2', '1503');
INSERT INTO "SYS_ROLE_MENU" VALUES ('f169475beb8e05e90618bb89fb939f4f', '2', '1018');

-- ----------------------------
-- Table structure for SYS_ROLE_USER
-- ----------------------------
DROP TABLE "SYS_ROLE_USER";
CREATE TABLE "SYS_ROLE_USER" (
  "ID" NVARCHAR2(32) NOT NULL,
  "ROLE_ID" NVARCHAR2(32),
  "USER_ID" NVARCHAR2(32)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_ROLE_USER"."ROLE_ID" IS '角色ID';
COMMENT ON COLUMN "SYS_ROLE_USER"."USER_ID" IS '用户ID';
COMMENT ON TABLE "SYS_ROLE_USER" IS '角色用户关联表';

-- ----------------------------
-- Records of SYS_ROLE_USER
-- ----------------------------
INSERT INTO "SYS_ROLE_USER" VALUES ('174eceae15692e5091c28f3d6a3a1f54', '2', '1650855280223846402');
INSERT INTO "SYS_ROLE_USER" VALUES ('1d6f2251b32492648a8b1c488f8a1f2f', '2', '1643141570206871554');
INSERT INTO "SYS_ROLE_USER" VALUES ('678a429aa947e14570e5077ebcea755a', '1', '1');
INSERT INTO "SYS_ROLE_USER" VALUES ('735afe9f516a5cf19e09326d05ea6190', '2', '1640613543627415553');
INSERT INTO "SYS_ROLE_USER" VALUES ('e12edec621a0f93ce2a9643d7e79b99e', '2', '1657562040456413185');
INSERT INTO "SYS_ROLE_USER" VALUES ('fed886b635dfcdb135ff7ca51b908861', '2', '2');

-- ----------------------------
-- Table structure for SYS_SOCIAL
-- ----------------------------
DROP TABLE "SYS_SOCIAL";
CREATE TABLE "SYS_SOCIAL" (
  "ID" NVARCHAR2(32) NOT NULL,
  "REMARK" NVARCHAR2(255),
  "CREATE_ID" NVARCHAR2(32),
  "CREATE_TIME" DATE,
  "UPDATE_ID" NVARCHAR2(32),
  "UPDATE_TIME" DATE,
  "IS_DELETE" NUMBER(11,0),
  "UUID" NVARCHAR2(255),
  "SOURCE" NVARCHAR2(255),
  "ACCESS_TOKEN" NVARCHAR2(255),
  "EXPIRE_IN" NUMBER(11,0),
  "REFRESH_TOKEN" NVARCHAR2(255),
  "OPEN_ID" NVARCHAR2(255),
  "UID" NVARCHAR2(255),
  "ACCESS_CODE" NVARCHAR2(255),
  "UNION_ID" NVARCHAR2(255),
  "SCOPE" NVARCHAR2(255),
  "TOKEN_TYPE" NVARCHAR2(255),
  "ID_TOKEN" NVARCHAR2(255),
  "MAC_ALGORITHM" NVARCHAR2(255),
  "MAC_KEY" NVARCHAR2(255),
  "CODE" NVARCHAR2(255),
  "OAUTH_TOKEN" NVARCHAR2(255),
  "OAUTH_TOKEN_SECRET" NVARCHAR2(255),
  "USER_ID" NVARCHAR2(32)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_SOCIAL"."REMARK" IS '备注';
COMMENT ON COLUMN "SYS_SOCIAL"."CREATE_ID" IS '创建人';
COMMENT ON COLUMN "SYS_SOCIAL"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "SYS_SOCIAL"."UPDATE_ID" IS '修改人';
COMMENT ON COLUMN "SYS_SOCIAL"."UPDATE_TIME" IS '修改时间';
COMMENT ON COLUMN "SYS_SOCIAL"."IS_DELETE" IS '是否删除，0=正常，1=删除';
COMMENT ON COLUMN "SYS_SOCIAL"."UUID" IS '第三方系统的唯一ID';
COMMENT ON COLUMN "SYS_SOCIAL"."SOURCE" IS '第三方用户来源';
COMMENT ON COLUMN "SYS_SOCIAL"."ACCESS_TOKEN" IS '用户的授权令牌';
COMMENT ON COLUMN "SYS_SOCIAL"."EXPIRE_IN" IS '第三方用户的授权令牌的有效期';
COMMENT ON COLUMN "SYS_SOCIAL"."REFRESH_TOKEN" IS '刷新令牌';
COMMENT ON COLUMN "SYS_SOCIAL"."OPEN_ID" IS '第三方用户的 open id';
COMMENT ON COLUMN "SYS_SOCIAL"."UID" IS '第三方用户的 ID';
COMMENT ON COLUMN "SYS_SOCIAL"."ACCESS_CODE" IS '个别平台的授权信息';
COMMENT ON COLUMN "SYS_SOCIAL"."UNION_ID" IS '第三方用户的 union id';
COMMENT ON COLUMN "SYS_SOCIAL"."SCOPE" IS '第三方用户授予的权限';
COMMENT ON COLUMN "SYS_SOCIAL"."TOKEN_TYPE" IS '个别平台的授权信息';
COMMENT ON COLUMN "SYS_SOCIAL"."ID_TOKEN" IS 'id token';
COMMENT ON COLUMN "SYS_SOCIAL"."MAC_ALGORITHM" IS '小米平台用户的附带属性';
COMMENT ON COLUMN "SYS_SOCIAL"."MAC_KEY" IS '小米平台用户的附带属性';
COMMENT ON COLUMN "SYS_SOCIAL"."CODE" IS '用户的授权code';
COMMENT ON COLUMN "SYS_SOCIAL"."OAUTH_TOKEN" IS 'Twitter平台用户的附带属性';
COMMENT ON COLUMN "SYS_SOCIAL"."OAUTH_TOKEN_SECRET" IS 'Twitter平台用户的附带属性';
COMMENT ON COLUMN "SYS_SOCIAL"."USER_ID" IS '关联用户ID';
COMMENT ON TABLE "SYS_SOCIAL" IS '三方Auth绑定表（暂未启用）';

-- ----------------------------
-- Records of SYS_SOCIAL
-- ----------------------------

-- ----------------------------
-- Table structure for SYS_USER
-- ----------------------------
DROP TABLE "SYS_USER";
CREATE TABLE "SYS_USER" (
  "ID" NVARCHAR2(32) NOT NULL,
  "DEPT_ID" NVARCHAR2(32),
  "USERNAME" NVARCHAR2(255),
  "PASSWORD" NVARCHAR2(255),
  "STATUS" NVARCHAR2(2),
  "PHONE" NVARCHAR2(255),
  "EMAIL" NVARCHAR2(255),
  "OPEN_ID" NVARCHAR2(255),
  "API_KEY" NVARCHAR2(255),
  "BAN_TO_TIME" DATE,
  "EXPIRE_TO_TIME" DATE,
  "USER_TYPE" NVARCHAR2(2),
  "REMARK" NVARCHAR2(255),
  "CREATE_ID" NVARCHAR2(32),
  "CREATE_TIME" DATE,
  "UPDATE_ID" NVARCHAR2(32),
  "UPDATE_TIME" DATE,
  "IS_DELETE" NUMBER(11,0)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_USER"."DEPT_ID" IS '部门ID';
COMMENT ON COLUMN "SYS_USER"."USERNAME" IS '用户账号';
COMMENT ON COLUMN "SYS_USER"."PASSWORD" IS '用户密码';
COMMENT ON COLUMN "SYS_USER"."STATUS" IS '账号状态，0=正常，1=停用';
COMMENT ON COLUMN "SYS_USER"."PHONE" IS '用户手机号';
COMMENT ON COLUMN "SYS_USER"."EMAIL" IS '邮箱';
COMMENT ON COLUMN "SYS_USER"."OPEN_ID" IS '微信open_id';
COMMENT ON COLUMN "SYS_USER"."API_KEY" IS '开放API key';
COMMENT ON COLUMN "SYS_USER"."BAN_TO_TIME" IS '锁定结束时间';
COMMENT ON COLUMN "SYS_USER"."EXPIRE_TO_TIME" IS '到期时间，null则永不过期';
COMMENT ON COLUMN "SYS_USER"."USER_TYPE" IS '用户类型，具体看字典';
COMMENT ON COLUMN "SYS_USER"."REMARK" IS '备注';
COMMENT ON COLUMN "SYS_USER"."CREATE_ID" IS '创建人';
COMMENT ON COLUMN "SYS_USER"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "SYS_USER"."UPDATE_ID" IS '修改人';
COMMENT ON COLUMN "SYS_USER"."UPDATE_TIME" IS '修改时间';
COMMENT ON COLUMN "SYS_USER"."IS_DELETE" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "SYS_USER" IS '用户表';

-- ----------------------------
-- Records of SYS_USER
-- ----------------------------
INSERT INTO "SYS_USER" VALUES ('1', '100', 'admin', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '0', '18731055555', '1330166565@qq.com', NULL, NULL, NULL, NULL, '1', '宇宙第一帅的人', '1', TO_DATE('2023-03-01 13:42:39', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-05-14 16:31:39', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_USER" VALUES ('1640613543627415553', '101', 'liumm', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '0', NULL, NULL, NULL, NULL, NULL, NULL, '3', NULL, '1', TO_DATE('2023-03-28 15:15:25', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-14 10:05:02', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_USER" VALUES ('1643141570206871554', '100', 'abcdefg', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '0', NULL, NULL, NULL, NULL, NULL, NULL, '3', NULL, '1', TO_DATE('2023-04-04 14:40:53', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-13 23:02:42', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_USER" VALUES ('1650855280223846402', '105', 'test01', '678e82d907d3e6e71f81d5cf3ddacc3671dc618c38a1b7a9f9393a83d025b296', '0', NULL, NULL, NULL, NULL, NULL, NULL, '2', NULL, NULL, TO_DATE('2023-04-25 21:32:25', 'SYYYY-MM-DD HH24:MI:SS'), NULL, TO_DATE('2023-04-25 21:32:25', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_USER" VALUES ('1657562040456413185', '105', 'admin22', '15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225', '0', NULL, NULL, NULL, NULL, NULL, NULL, '2', NULL, NULL, TO_DATE('2023-05-14 09:42:41', 'SYYYY-MM-DD HH24:MI:SS'), NULL, TO_DATE('2023-05-14 09:42:41', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_USER" VALUES ('2', '102', 'test', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '0', '18755533444', '555@163.com', '1', '2', TO_DATE('2023-05-10 11:01:47', 'SYYYY-MM-DD HH24:MI:SS'), NULL, '2', '4', '1', TO_DATE('2023-03-01 14:09:35', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-03-01 14:09:35', 'SYYYY-MM-DD HH24:MI:SS'), '0');

-- ----------------------------
-- Table structure for SYS_USER_DETAIL
-- ----------------------------
DROP TABLE "SYS_USER_DETAIL";
CREATE TABLE "SYS_USER_DETAIL" (
  "ID" NVARCHAR2(32) NOT NULL,
  "USER_ID" NVARCHAR2(32),
  "NICK_NAME" NVARCHAR2(255),
  "REAL_NAME" NVARCHAR2(255),
  "AGE" NVARCHAR2(255),
  "SEX" NVARCHAR2(255),
  "PROFILE" NCLOB,
  "USER_SIGN" NVARCHAR2(255),
  "AVATAR" NVARCHAR2(255),
  "MONEY" NUMBER(11,0),
  "CREATE_ID" NVARCHAR2(32),
  "CREATE_TIME" DATE,
  "UPDATE_ID" NVARCHAR2(32),
  "UPDATE_TIME" DATE,
  "IS_DELETE" NUMBER(11,0)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SYS_USER_DETAIL"."USER_ID" IS '关联用户ID';
COMMENT ON COLUMN "SYS_USER_DETAIL"."NICK_NAME" IS '用户昵称';
COMMENT ON COLUMN "SYS_USER_DETAIL"."REAL_NAME" IS '真实姓名';
COMMENT ON COLUMN "SYS_USER_DETAIL"."AGE" IS '年龄';
COMMENT ON COLUMN "SYS_USER_DETAIL"."SEX" IS '性别，0=女，1=男，2=未知';
COMMENT ON COLUMN "SYS_USER_DETAIL"."PROFILE" IS '个人简介';
COMMENT ON COLUMN "SYS_USER_DETAIL"."USER_SIGN" IS '个性签名';
COMMENT ON COLUMN "SYS_USER_DETAIL"."AVATAR" IS '头像（文件ID）';
COMMENT ON COLUMN "SYS_USER_DETAIL"."MONEY" IS '账户余额，单位分';
COMMENT ON COLUMN "SYS_USER_DETAIL"."CREATE_ID" IS '创建人';
COMMENT ON COLUMN "SYS_USER_DETAIL"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "SYS_USER_DETAIL"."UPDATE_ID" IS '修改人';
COMMENT ON COLUMN "SYS_USER_DETAIL"."UPDATE_TIME" IS '修改时间';
COMMENT ON COLUMN "SYS_USER_DETAIL"."IS_DELETE" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "SYS_USER_DETAIL" IS '用户信息表';

-- ----------------------------
-- Records of SYS_USER_DETAIL
-- ----------------------------
INSERT INTO "SYS_USER_DETAIL" VALUES ('21366e78e6a156e9cf45061da65d06e5', '1', '我是管理员', 'Daen', '18', '1', '每一次离开都是为了更好的相遇
你好世界', '不一样的烟花', '625bcbc99e7524b543c81ffaed6dd2ab', '350', NULL, TO_DATE('2023-03-01 13:42:39', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-06-26 15:49:37', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_USER_DETAIL" VALUES ('7550e77dfbabea70ec44aaebda2f90ed', '2', '测试用户', '宝贝', '18', '0', 'ε=(′ο｀*)))唉', '生气气1', NULL, '0', NULL, TO_DATE('2023-03-01 14:09:35', 'SYYYY-MM-DD HH24:MI:SS'), '2', TO_DATE('2023-04-16 19:49:01', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_USER_DETAIL" VALUES ('886c73e7e8f458649f3048f672f042e6', '1650855280223846402', NULL, NULL, '0', '2', NULL, NULL, NULL, '0', NULL, TO_DATE('2023-04-25 21:32:25', 'SYYYY-MM-DD HH24:MI:SS'), NULL, TO_DATE('2023-04-25 21:32:25', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_USER_DETAIL" VALUES ('945acc4f5450533dd672260cfa1d66c9', '1640613543627415553', '刘萌萌', '刘二萌', '18', '0', NULL, NULL, NULL, '85560', '1', TO_DATE('2023-03-28 15:15:25', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-14 10:05:03', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_USER_DETAIL" VALUES ('ac5182e7b0e1c9ce11e7381e19b4733d', '1657562040456413185', NULL, NULL, '0', '2', NULL, NULL, NULL, '0', NULL, TO_DATE('2023-05-14 09:42:41', 'SYYYY-MM-DD HH24:MI:SS'), NULL, TO_DATE('2023-05-14 09:42:41', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "SYS_USER_DETAIL" VALUES ('f6d09bb65ad0ac3d6239214d7e456f8e', '1643141570206871554', '阿萨德', NULL, '0', '0', '2', NULL, NULL, '0', '1', TO_DATE('2023-04-04 14:40:53', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-13 23:02:42', 'SYYYY-MM-DD HH24:MI:SS'), '0');

-- ----------------------------
-- Table structure for TEST_DATA
-- ----------------------------
DROP TABLE "TEST_DATA";
CREATE TABLE "TEST_DATA" (
  "ID" NVARCHAR2(32) NOT NULL,
  "TITLE" NVARCHAR2(255),
  "CONTENT" NVARCHAR2(255),
  "TYPE" NVARCHAR2(2),
  "STATUS" NVARCHAR2(2),
  "REMARK" NVARCHAR2(255),
  "CREATE_ID" NVARCHAR2(32),
  "CREATE_TIME" DATE,
  "UPDATE_ID" NVARCHAR2(32),
  "UPDATE_TIME" DATE,
  "IS_DELETE" NUMBER(11,0)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "TEST_DATA"."TYPE" IS '类型，0=民生，1=科技';
COMMENT ON COLUMN "TEST_DATA"."STATUS" IS '状态，0=正常，1=停用';
COMMENT ON COLUMN "TEST_DATA"."REMARK" IS '备注';
COMMENT ON COLUMN "TEST_DATA"."CREATE_ID" IS '创建人';
COMMENT ON COLUMN "TEST_DATA"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "TEST_DATA"."UPDATE_ID" IS '修改人';
COMMENT ON COLUMN "TEST_DATA"."UPDATE_TIME" IS '修改时间';
COMMENT ON COLUMN "TEST_DATA"."IS_DELETE" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "TEST_DATA" IS '测试数据';

-- ----------------------------
-- Records of TEST_DATA
-- ----------------------------
INSERT INTO "TEST_DATA" VALUES ('8f05e1af476e3537e796c966ff4b7b53', '阿萨德', '阿萨德', '0', '0', '4', '1', TO_DATE('2023-06-03 10:56:21', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-06-03 10:58:26', 'SYYYY-MM-DD HH24:MI:SS'), '0');

-- ----------------------------
-- Table structure for TEST_DATA_TREE
-- ----------------------------
DROP TABLE "TEST_DATA_TREE";
CREATE TABLE "TEST_DATA_TREE" (
  "ID" NVARCHAR2(32) NOT NULL,
  "PARENT_ID" NVARCHAR2(32),
  "TITLE" NVARCHAR2(255),
  "CONTENT" NVARCHAR2(255),
  "TYPE" NVARCHAR2(2),
  "STATUS" NVARCHAR2(2),
  "REMARK" NVARCHAR2(255),
  "CREATE_ID" NVARCHAR2(32),
  "CREATE_TIME" DATE,
  "UPDATE_ID" NVARCHAR2(32),
  "UPDATE_TIME" DATE,
  "IS_DELETE" NUMBER(11,0)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "TEST_DATA_TREE"."PARENT_ID" IS '父级ID，顶级为0';
COMMENT ON COLUMN "TEST_DATA_TREE"."TYPE" IS '类型，0=民生，1=科技';
COMMENT ON COLUMN "TEST_DATA_TREE"."STATUS" IS '状态，0=正常，1=停用';
COMMENT ON COLUMN "TEST_DATA_TREE"."REMARK" IS '备注';
COMMENT ON COLUMN "TEST_DATA_TREE"."CREATE_ID" IS '创建人';
COMMENT ON COLUMN "TEST_DATA_TREE"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "TEST_DATA_TREE"."UPDATE_ID" IS '修改人';
COMMENT ON COLUMN "TEST_DATA_TREE"."UPDATE_TIME" IS '修改时间';
COMMENT ON COLUMN "TEST_DATA_TREE"."IS_DELETE" IS '是否删除，0=正常，1=删除';
COMMENT ON TABLE "TEST_DATA_TREE" IS '测试树表数据';

-- ----------------------------
-- Records of TEST_DATA_TREE
-- ----------------------------
INSERT INTO "TEST_DATA_TREE" VALUES ('1', '0', '我1', '阿萨德', '0', '1', '个', '1', TO_DATE('2023-02-10 17:25:11', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-26 22:16:35', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "TEST_DATA_TREE" VALUES ('11', '1', '我3', '3', '0', '0', '导入1', '1', TO_DATE('2023-02-26 17:25:16', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-26 22:19:18', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "TEST_DATA_TREE" VALUES ('12', '2', '我51', '按时在', '0', '0', '官方', '2', TO_DATE('2023-02-22 17:25:21', 'SYYYY-MM-DD HH24:MI:SS'), '2', TO_DATE('2023-02-22 17:25:21', 'SYYYY-MM-DD HH24:MI:SS'), '1');
INSERT INTO "TEST_DATA_TREE" VALUES ('2', '0', '我4-', '按时-', '1', '0', '更换-', '2', TO_DATE('2023-01-30 17:25:18', 'SYYYY-MM-DD HH24:MI:SS'), '2', TO_DATE('2023-01-30 17:25:18', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "TEST_DATA_TREE" VALUES ('222', '11', '我2', '得过户', '1', '1', '个月', '1', TO_DATE('2023-02-15 17:25:14', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-26 22:16:10', 'SYYYY-MM-DD HH24:MI:SS'), '0');
INSERT INTO "TEST_DATA_TREE" VALUES ('50fac88b36e64da3d8bd94ae11f85bc6', '2', '测试', '阿萨德', '0', '0', NULL, '1', TO_DATE('2023-04-26 22:02:34', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-26 22:02:34', 'SYYYY-MM-DD HH24:MI:SS'), '1');
INSERT INTO "TEST_DATA_TREE" VALUES ('b53120f9af52dcdc229d42d8cb04623f', '2', '阿萨德', '阿萨德', '0', '0', NULL, '1', TO_DATE('2023-04-26 22:24:47', 'SYYYY-MM-DD HH24:MI:SS'), '1', TO_DATE('2023-04-26 22:24:47', 'SYYYY-MM-DD HH24:MI:SS'), '0');

-- ----------------------------
-- Primary Key structure for table SYS_API_LIMIT
-- ----------------------------
ALTER TABLE "SYS_API_LIMIT" ADD CONSTRAINT "SYS_C0013013" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_API_LIMIT
-- ----------------------------
ALTER TABLE "SYS_API_LIMIT" ADD CONSTRAINT "SYS_C0011047" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "SYS_API_LIMIT" ADD CONSTRAINT "SYS_C0012991" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table SYS_CONFIG
-- ----------------------------
ALTER TABLE "SYS_CONFIG" ADD CONSTRAINT "SYS_C0013014" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_CONFIG
-- ----------------------------
ALTER TABLE "SYS_CONFIG" ADD CONSTRAINT "SYS_C0011048" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "SYS_CONFIG" ADD CONSTRAINT "SYS_C0012989" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table SYS_DEPT
-- ----------------------------
ALTER TABLE "SYS_DEPT" ADD CONSTRAINT "SYS_C0013015" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_DEPT
-- ----------------------------
ALTER TABLE "SYS_DEPT" ADD CONSTRAINT "SYS_C0011049" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "SYS_DEPT" ADD CONSTRAINT "SYS_C0012990" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table SYS_DEPT_PARENT
-- ----------------------------
ALTER TABLE "SYS_DEPT_PARENT" ADD CONSTRAINT "SYS_C0011187" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_DEPT_PARENT
-- ----------------------------
ALTER TABLE "SYS_DEPT_PARENT" ADD CONSTRAINT "SYS_C0011184" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table SYS_DICT
-- ----------------------------
ALTER TABLE "SYS_DICT" ADD CONSTRAINT "SYS_C0013017" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_DICT
-- ----------------------------
ALTER TABLE "SYS_DICT" ADD CONSTRAINT "SYS_C0011050" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "SYS_DICT" ADD CONSTRAINT "SYS_C0012993" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table SYS_DICT_DETAIL
-- ----------------------------
ALTER TABLE "SYS_DICT_DETAIL" ADD CONSTRAINT "SYS_C0013022" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_DICT_DETAIL
-- ----------------------------
ALTER TABLE "SYS_DICT_DETAIL" ADD CONSTRAINT "SYS_C0011051" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "SYS_DICT_DETAIL" ADD CONSTRAINT "SYS_C0012995" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table SYS_FILE
-- ----------------------------
ALTER TABLE "SYS_FILE" ADD CONSTRAINT "SYS_C0013018" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_FILE
-- ----------------------------
ALTER TABLE "SYS_FILE" ADD CONSTRAINT "SYS_C0011052" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "SYS_FILE" ADD CONSTRAINT "SYS_C0012994" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table SYS_JOB
-- ----------------------------
ALTER TABLE "SYS_JOB" ADD CONSTRAINT "SYS_C0013019" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_JOB
-- ----------------------------
ALTER TABLE "SYS_JOB" ADD CONSTRAINT "SYS_C0011053" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "SYS_JOB" ADD CONSTRAINT "SYS_C0012996" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table SYS_JOB_LOG
-- ----------------------------
ALTER TABLE "SYS_JOB_LOG" ADD CONSTRAINT "SYS_C0013020" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_JOB_LOG
-- ----------------------------
ALTER TABLE "SYS_JOB_LOG" ADD CONSTRAINT "SYS_C0011054" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "SYS_JOB_LOG" ADD CONSTRAINT "SYS_C0012997" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table SYS_LOG_LOGIN
-- ----------------------------
ALTER TABLE "SYS_LOG_LOGIN" ADD CONSTRAINT "SYS_C0013021" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_LOG_LOGIN
-- ----------------------------
ALTER TABLE "SYS_LOG_LOGIN" ADD CONSTRAINT "SYS_C0011055" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "SYS_LOG_LOGIN" ADD CONSTRAINT "SYS_C0012998" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table SYS_LOG_OPER
-- ----------------------------
ALTER TABLE "SYS_LOG_OPER" ADD CONSTRAINT "SYS_C0013023" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_LOG_OPER
-- ----------------------------
ALTER TABLE "SYS_LOG_OPER" ADD CONSTRAINT "SYS_C0011056" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "SYS_LOG_OPER" ADD CONSTRAINT "SYS_C0012999" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table SYS_MENU
-- ----------------------------
ALTER TABLE "SYS_MENU" ADD CONSTRAINT "SYS_C0013034" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_MENU
-- ----------------------------
ALTER TABLE "SYS_MENU" ADD CONSTRAINT "SYS_C0011057" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "SYS_MENU" ADD CONSTRAINT "SYS_C0013000" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table SYS_NOTICE
-- ----------------------------
ALTER TABLE "SYS_NOTICE" ADD CONSTRAINT "SYS_C0013024" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_NOTICE
-- ----------------------------
ALTER TABLE "SYS_NOTICE" ADD CONSTRAINT "SYS_C0011058" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "SYS_NOTICE" ADD CONSTRAINT "SYS_C0013001" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table SYS_OSS_CONFIG
-- ----------------------------
ALTER TABLE "SYS_OSS_CONFIG" ADD CONSTRAINT "SYS_C0013025" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_OSS_CONFIG
-- ----------------------------
ALTER TABLE "SYS_OSS_CONFIG" ADD CONSTRAINT "SYS_C0011059" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "SYS_OSS_CONFIG" ADD CONSTRAINT "SYS_C0013002" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table SYS_POSITION
-- ----------------------------
ALTER TABLE "SYS_POSITION" ADD CONSTRAINT "SYS_C0013026" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_POSITION
-- ----------------------------
ALTER TABLE "SYS_POSITION" ADD CONSTRAINT "SYS_C0011060" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "SYS_POSITION" ADD CONSTRAINT "SYS_C0013003" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table SYS_POSITION_USER
-- ----------------------------
ALTER TABLE "SYS_POSITION_USER" ADD CONSTRAINT "SYS_C0013027" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_POSITION_USER
-- ----------------------------
ALTER TABLE "SYS_POSITION_USER" ADD CONSTRAINT "SYS_C0011061" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "SYS_POSITION_USER" ADD CONSTRAINT "SYS_C0013004" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table SYS_ROLE
-- ----------------------------
ALTER TABLE "SYS_ROLE" ADD CONSTRAINT "SYS_C0013028" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_ROLE
-- ----------------------------
ALTER TABLE "SYS_ROLE" ADD CONSTRAINT "SYS_C0011062" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "SYS_ROLE" ADD CONSTRAINT "SYS_C0013005" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table SYS_ROLE_DEPT
-- ----------------------------
ALTER TABLE "SYS_ROLE_DEPT" ADD CONSTRAINT "SYS_C0013029" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_ROLE_DEPT
-- ----------------------------
ALTER TABLE "SYS_ROLE_DEPT" ADD CONSTRAINT "SYS_C0011063" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "SYS_ROLE_DEPT" ADD CONSTRAINT "SYS_C0013006" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table SYS_ROLE_MENU
-- ----------------------------
ALTER TABLE "SYS_ROLE_MENU" ADD CONSTRAINT "SYS_C0013036" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_ROLE_MENU
-- ----------------------------
ALTER TABLE "SYS_ROLE_MENU" ADD CONSTRAINT "SYS_C0011064" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "SYS_ROLE_MENU" ADD CONSTRAINT "SYS_C0013007" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table SYS_ROLE_USER
-- ----------------------------
ALTER TABLE "SYS_ROLE_USER" ADD CONSTRAINT "SYS_C0013030" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_ROLE_USER
-- ----------------------------
ALTER TABLE "SYS_ROLE_USER" ADD CONSTRAINT "SYS_C0011065" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "SYS_ROLE_USER" ADD CONSTRAINT "SYS_C0013008" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table SYS_SOCIAL
-- ----------------------------
ALTER TABLE "SYS_SOCIAL" ADD CONSTRAINT "SYS_C0011188" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_SOCIAL
-- ----------------------------
ALTER TABLE "SYS_SOCIAL" ADD CONSTRAINT "SYS_C0011185" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table SYS_USER
-- ----------------------------
ALTER TABLE "SYS_USER" ADD CONSTRAINT "SYS_C0013031" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_USER
-- ----------------------------
ALTER TABLE "SYS_USER" ADD CONSTRAINT "SYS_C0011066" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "SYS_USER" ADD CONSTRAINT "SYS_C0013009" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table SYS_USER_DETAIL
-- ----------------------------
ALTER TABLE "SYS_USER_DETAIL" ADD CONSTRAINT "SYS_C0013032" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_USER_DETAIL
-- ----------------------------
ALTER TABLE "SYS_USER_DETAIL" ADD CONSTRAINT "SYS_C0011067" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "SYS_USER_DETAIL" ADD CONSTRAINT "SYS_C0013010" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table TEST_DATA
-- ----------------------------
ALTER TABLE "TEST_DATA" ADD CONSTRAINT "SYS_C0013033" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table TEST_DATA
-- ----------------------------
ALTER TABLE "TEST_DATA" ADD CONSTRAINT "SYS_C0011068" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "TEST_DATA" ADD CONSTRAINT "SYS_C0013011" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Primary Key structure for table TEST_DATA_TREE
-- ----------------------------
ALTER TABLE "TEST_DATA_TREE" ADD CONSTRAINT "SYS_C0013035" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table TEST_DATA_TREE
-- ----------------------------
ALTER TABLE "TEST_DATA_TREE" ADD CONSTRAINT "SYS_C0011069" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "TEST_DATA_TREE" ADD CONSTRAINT "SYS_C0013012" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

