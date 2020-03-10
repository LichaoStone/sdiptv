/*
 * @# SystemExceptionCode.java Aug 23, 2012 2:11:19 PM
 * 
 * Copyright (C) 2011 - 2012 博瑞立方
 * BoRuiCube information technology co. ltd.
 * 
 * All rights reserved!
 */
package com.br.ott.common.exception;

/*
 * @author pananz
 * TODO
 */
public final class SystemExceptionCode {

	/**
	 * 系统异常公共
	 * @author xulun
	 */
	public final static class SysCommonCode {
		//系统未知异常
		public static final String UNKNOWN_ERROR = "20000";
		//异常消息读取失败
		public static final String EXCEPTION_MSG_READER_ERROR = "20001";
		//系统错误异常
		public static final String SYSTEM_ERROR = "20002";

		private SysCommonCode() {
			//empty!
		}
	}

	private SystemExceptionCode() {
		//empty!
	}

	/**
	 * 上传文件异常
	 * @author pananz
	 *
	 */
	public final static class UploadCode {
		//文件找不到
		public static final String FILE_NO_FOUND = "30000";
		//上传文件出错
		public static final String FILE_IO_UPLOAD_ERROR = "30001";
		//上传文件释放资源出错
		public static final String FILE_IO_CLOSE_ERROR = "30002";

		public static final String PRODUCT_OVER_MAX_SIZE = "30003";

		public static final String PARTNER_OVER_MAX_SIZE = "30004";
		public static final String PARTNER_OVER_MAX_SIZE2 = "30005";
		public static final String PARTNER_OVER_MAX_SIZE3 = "30006";
		public static final String PARTNER_OVER_MAX_SIZE4 = "30007";

		private UploadCode() {

		}
	}

	/**
	 * 对外接口异常
	 * @author pananz
	 *
	 */
	public final static class ApiCode {
		// 成功
		public static final String SUCCESS = "0";
		// 必填项参数为空
		public static final String NULL_PARAM = "99";
		// 系统错误
		public static final String ERROR = "500";
		//缺少用户id
		public static final String NO_USERID = "9030000";
		//缺少用户令牌
		public static final String NO_USERTOKEN = "9030001";
		//接口数据解析失败
		public static final String DECODE_JSON_ERROR = "9040000";
		//数据格式不正确
		public static final String DATA_FORMAT_ERROR = "9040001";
		//数据流转为字条串失败
		public static final String STREAMTOSTRING_ERROR = "9040002";
		//缺少产品类型
		public static final String PRODUCT_NO_PTYPE = "9050000";
		//产品列表查询失败
		public static final String PRODUCT_FIND_ERROR = "9050001";
		//缺少上一级产品类型
		public static final String PRODUCTTYPE_NO_PTYPE = "9050002";
		//产品类型查询失败
		public static final String PRODUCTTYPE_FIND_ERROR = "9050003";
		//缺少产品ID
		public static final String PRODUCT_NO_ID = "9050004";
		//产品详情查询失败
		public static final String PRODUCT_GET_ERROR = "9050005";
		//缺少评分值
		public static final String PRODUCT_NO_SCORE = "9050006";
		//产品评分失败
		public static final String PRODUCT_SCORE_ERROR = "9050007";
		//产品记录下载量失败
		public static final String PRODUCT_DOWNLOAD_ERROR = "9050008";
		//暂无此产品信息
		public static final String PRODUCT_NO_FIND = "9050009";
		//产品APP升级请求参数不能为空
		public static final String PRODUCT_UPGRADE_NOINFO = "9050010";
		//产品APP升级请求业务出错
		public static final String PRODUCT_UPGRADE_ERROR = "9050011";
		//机器检验失败
		public static final String MACHINE_CHECK_ERROR = "9060000";
		//用户帐户检验失败
		public static final String USER_CHECK_ERROR = "9060001";
		//机器检验缺少用户ID
		public static final String MACHINE_CHECK_NO_USERID = "9060002";
		//机器检验缺少机器唯一编号
		public static final String MACHINE_CHECK_NO_MACHINECODE = "9060003";
		//机器检验缺少机器标识
		public static final String MACHINE_CHECK_NO_MACHINEIMIE = "9060004";
		//用户订购产品检验失败
		public static final String USER_ORDER_CHECK_ERROR = "9060005";
		//渠道id缺少
		public static final String CPID_CHECK_NO_ID = "9060007";
		//注册智能机失败
		public static final String MACHINE_REGIST_ERROR = "9060008";
		//添加产品点击使用率记录失败
		public static final String PRODUCT_ADD_ERROR = "9070000";
		// 获取用户信息失败
		public static final String APP_NO_USERID = "9051000";
		// 获取用户应用信息失败
		public static final String APP_NO_APPID = "9052000";
		//儿童锁密码确认失败
		public static final String USER_GET_ERROR = "9080000";
		//儿童锁密码修改失败
		public static final String USER_MODIFY_ERROR = "9080001";
		//查看账单缺少用户Id
		public static final String USER_CHECK_BILL = "9080002";
		//查看账单实体类失败
		public static final String USER_ORDER_FIND_ERROR = "9080003";
		//查看账单虚拟类失败
		public static final String USER_APPORDER_ERROR = "9080004";
		//修改用户资料失败
		public static final String USERT_MODIFY_ERROR = "9080005";
		//修改用户资料id缺少
		public static final String USERT_ACCOUNT_ID_ERROR = "9080006";
		//用户订购缺少产品ID
		public static final String USER_ORDER_NO_PID = "9090000";
		//用户订购检验产品失败
		public static final String USER_PRODUCT_CHECK_NULL = "9090001";
		//用户订购业务检验产品失败
		public static final String USER_PRODUCT_CHECK_ERROR = "9090002";
		//产品计费失败
		public static final String USER_ORDER_BILLING_ERROR = "9090003";
		//用户订购失败
		public static final String USER_ORDER_ERROR = "9090004";
		//申请成为平台级会员失败
		public static final String USER_BECOME_MEMEBER_ERROR = "9090005";
		//用户已经订购过此产品
		public static final String USER_AREADY_ORDER = "9090006";
		//生成订购失败
		public static final String CREATE_ORDER_ERROR = "9090007";
		/// 用户身份验证失败
		public static final String USER_AUTH_FAIL = "9100000";
		// 用户信息已存在
		public static final String USER_HAV_INFO = "9100001";
		// 用户产生userToken失败
		public static final String USER_CREATE_TOKEN_ERROR = "9100002";
		//更新底座更新软件名称
		public static final String BASE_SOFTWARE_NAME_ERROR = "9110000";
		//缺少软件唯一标示
		public static final String BASE_SOFTWARE_CODE_ERROR = "9110001";
		//缺少软件版本
		public static final String BASE_SOFTWARE_VERSION_ERROR = "9110002";
		//缺少终端操作系统
		public static final String BASE_SOFTWARE_OSYSYTEM_ERROR = "9110003";
		//底座软件更新失败
		public static final String BASE_SOFTWARE_UPDATE_ERROR = "9110004";
		//缺少底座软件硬件版本
		public static final String BASE_HARDWARE_TYPE = "9110005";
		/**
		 * 底座通信BASE_
		 */
		public static final String BASE_100 = "100";
		public static final String BASE_101 = "101";
		public static final String BASE_102 = "102";
		public static final String BASE_103 = "103";
		public static final String BASE_104 = "104";
		public static final String BASE_105 = "105";
		//		public static final String BASE_106 = "106";

		// 传入机器MAC 信息为空
		public static final String THE_MACHINE_MAC = "9151001";

		// 绑定机器MAC 和手机号码信息失败
		public static final String MACHINE_MAC = "9151002";

		// 机器MAC 绑定号码为空 
		public static final String MACHINE_BINGDING_NUMBER = "9151003";

		// 宽带状态绑定 
		public static final String BROADBAND_STATE_BINDING_OK = "0"; // 处理成功

		public static final String BROADBAND_STATE_BINDING_ANOMALY = "9"; // 账户不存在或异常

		public static final String BROADBAND_PRODUCTS_NOT_FOUND = "1001"; // 产品未找到

		public static final String BROADBAND_REPEAT_ORDER = "2"; // 用户已经订购过该产品，不能重复订购

		public static final String BROADBAND_CANCEL_THE_RENEWAL = "3"; // 用户没有订购过该产品，无法取消续订

		public static final String BROADBAND_TYPE_DOES_NOT_EXIST = "4"; // 指定请求类型不存在

		public static final String BROADBAND_STATE_ANOMALY = "5"; // 用户状态异常

		public static final String BROADBAND_OTHER_ABNORMALITIES = "99"; // 其他异常

		//软件维护与反馈接口
		public static final String APP_MAC_NULL = "9120001"; // 机顶盒MAC地址为空或错误
		public static final String APP_ID_NULL = "9120002"; // 软件ID为空或错误
		public static final String APP_OP_TYPE_NULL = "9120003"; // 操作类型为空或错误

		//手机订购及获取验证码
		/** 缺少手机号码 */
		public static final String PHONE_ORDER_NO_MOBILE = "9130001";
		/** 缺少产品编号 */
		public static final String PHONE_ORDER_NO_PID = "9130002";
		/** 缺少产品套餐编号 */
		public static final String PHONE_ORDER_NO_PACKAGE = "9130003";
		/** 缺少订购类型 */
		public static final String PHONE_ORDER_NO_REQTYPE = "9130004";
		/** 缺少订购验证码 */
		public static final String PHONE_ORDER_NO_CODE = "9130005";
		/** 缺少产品订购数量 */
		public static final String PHONE_ORDER_NO_BUYSUM = "9130006";
		/** 手机验证码输入不正确 */
		public static final String PHONE_ORDER_CODE_ERROR = "9130007";
		/** 订购的产品不存在 */
		public static final String PHONE_ORDER_NOFOUND_PRODUCT = "9130008";
		/** 此产品套餐不存在 */
		public static final String PHONE_ORDER_NOFOUND_PACKAGE = "9130009";
		/** MAC帐号不存在 */
		public static final String PHONE_ORDER_NOFOUND_THISMAC = "9130010";
		/** 手机订购生成订单失败 */
		public static final String PHONE_ORDER_CREATEORDER_ERROR = "9130011";
		/** 手机产生验证码出错 */
		public static final String PHONE_ORDER_CREATECODE_ERROR = "9130012";
		/** 手机获取验证码失败 */
		public static final String PHONE_ORDER_GETCODE_ERROR = "9130013";
		/** 手机验证码时间过长，已失效 */
		public static final String PHONE_ORDER_CODE_TIMEOUT = "9130014";
		/** 手机获取验证输入出错次数超过5次，今天不能再获取了，请明天再试 */
		public static final String PHONE_ORDER_CODE_ERROR_MORE = "9130015";
		/** 产品套餐编号与产品包含的套餐不对应 */
		public static final String PHONE_ORDER_PACKAGE_FOR_PID_ERROR = "9130016";

		// 宽带订购产品
		/** 缺少产品编号 */
		public static final String BOUND_ORDER_NO_PID = "9140001";
		/** 缺少产品资费套餐 */
		public static final String BOUND_ORDER_NO_PACKAGE = "9140002";
		/** 缺少宽带帐号 */
		public static final String BOUND_ORDER_NO_ACCOUNT = "9140003";
		/** 缺少产品订购数量 */
		public static final String BOUND_ORDER_NO_BUYSUM = "9140004";
		/** MAC帐号不存在 */
		public static final String BOUND_ORDER_NOFOUND_THISMAC = "9140005";
		/** 订购的产品不存在 */
		public static final String BOUND_ORDER_NOFOUND_PRODUCT = "9140006";
		/** 订购的套餐不存在 */
		public static final String BOUND_ORDER_NOFOUND_PACKAGE = "9140007";
		/** 宽带订购生成订单失败 */
		public static final String BOUND_ORDER_CREATEORDER_ERROR = "9140008";
		/** 产品套餐编号与产品包含的套餐不对应 */
		public static final String BOUND_ORDER_PACKAGE_FOR_PID_ERROR = "9130009";

		private ApiCode() {
			//empty
		}
	}
}
