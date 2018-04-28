package com.hui10.app.common.constants;

/**
 * 新规范里用9位码表示 前3位表示项目代码 101-游戏 ，102-汇拾卡， 103-小二，104-商盟， 105银联汇拾APP， 106 银联聚合支付
 * 105000000-105009999  公用模块错误代码
 * 105100000-105109999
 * 105200000-105209999
 * 105300000-105309999
 * 105400000-105409999
 * 依次类推
 *
 * @author wengf
 * @ClassName: ICommon.java
 * @Description:
 * @date 2017年10月17日 下午4:00:27
 */
public interface ICommon {

    /****************************************公用模块错误代码***********************************************/

    /**
     * 参数异常
     */
    public static int PARAMETER_ERR = 105000000;
    /**
     * 授权失败
     */
    public static int VALIDATE_TOKEN_FAIL = 105000001;
    /**
     * 发送验证码失败
     */
    public static int SEND_VALIDATE_CODE_FAIL = 105000002;
    /**
     * 手机号格式错误
     */
    public static int VALIDATE_PHONE_FORMAT_FAIL = 105000003;

    /**
     * 您已经绑定过银行卡了
     */
    public static int USER_BANKCARD_BOUND = 105000004;

    /**
     * 此卡号已被绑定，请更换其它卡号重试
     */
    public static int BANKCARD_BOUND = 105000005;

    /**
     * 绑卡失败，请稍后重试
     */
    public static int BOUND_BANKCARD_FAIL = 105000006;

    /**
     * 验证码无效
     */
    public static int VALIDATE_CODE_INVALID = 105000007;

    /**
     * 持卡人不可为空
     */
    public static int CARDHOLDER_EMPTY = 105000008;

    /**
     * 银行预留手机不可为空
     */
    public static int RESERVEPHONE_EMPTY = 105000009;

    /**
     * 未绑定银行卡
     */
    public static int NOBOUND_BANKCARD = 105000010;

    /**
     * 录入信息与绑卡录入信息不匹配
     */
    public static int INFORMATION_MISMATCH = 105000011;

    /**
     * 暂不支持%s卡提现
     */
    public static int BANKCARD_NO_SUPPORT = 105000012;

    /**
     * 订单未找到
     */
    public static int ORDER_NOTFOND_ERROR = 105000013;

    /**
     * 该订单已存在绑定关系，无法再次绑定
     */
    public static int ORDER_HAVE_BIND_ERROR = 105000014;
    /**
     * 支付报文错误
     */
    public static int ORDER_BODY_EEROR = 105000015;
    /**
     * 查询提现状态返回错误码
     */
    public static int ORDER_RET_CODE = 105000016;

    /**
     * 交易号错误
     */
    public static int ORDER_TRADENO_ERROR = 105000017;

    /**
     * 获取支付信息错误
     */
    public static int ORDER_AROUSESDKPARAM_ERROR = 105000018;

    /**
     * 中奖不支持在线兑奖
     */
    public static int PRIZE_AMOUNT_BIG_ERROR = 105000019;
    /**
     * 您无奖金可兑奖
     */
    public static int PRIZE_AMOUNT_IS_ZERO_ERROR = 105000020;

    /**
     * 大奖不支持在线兑奖
     */
    public static int PRIZE_AMOUNT_TOO_BIG_ERROR = 105000021;

    /**
     * 大奖不支持委托
     */
    public static int PRIZE_AMOUNT_TOO_BIGER_ERROR = 105000022;

    /**
     * 您无奖金可兑奖,无需上传证件
     */
    public static int PRIZE_AMOUNT_ZERO_ERROR = 105000023;
    public static int PARAMETER_IS_NULL = 105000024;
    /**
     * 注册类型错误
     */
    public static int REGIST_TYPE_IS_ERROR = 105000025;
    /**
     * 不存在该用户
     */
    public static final int UID_USER_IS_NULL_CODE = 105000026;
    /**
     * 保存用户信息失败
     */
    public static int SAVE_USER_IS_ERROR = 105000027;
    /**
     * 原密码输入次数过多，请%d分钟后再试
     */
    public static int OLDPASSWORD_WRONG_NUMBER = 105000028;
    /**
     * 密码格式不正确，密码为6-18数字和字母组合
     */
    public static int PASSWORD_ISNOT_FORMAT = 105000029;
    /**
     * 密码错误
     */
    public static final int OLD_IS_ERROR_CODE = 105000030;

    /**
     * 用户输入的手机和注册时手机号不一致
     */
    public static int USER_PHONE_MISMATCHING = 105000031;
    /**
     * 开奖状态异常
     */
    public static int LOTTERY_STATUS_ERROR = 105000032;
    /**
     * 用户已注册
     */
    public static final int USER_EXIST = 105000033;

    /**
     * 小奖不需上传证件
     */
    public static final int LITTL_PRIZ_ERROR = 105000034;

    /**
     * 请勿重复上传证件
     */
    public static final int ALREDY_UPLOAD_ERROR = 105000035;

    /**
     * 兑奖已截止
     */
    public static final int PRIZE_DRAW_EXPIRE = 105000036;

    /**
     * 申请记录未找到
     */
    public static final int HANDLE_NOT_FOND = 105000037;

    /**
     * 委托人名字和身份证名字必须一致
     */
    public static final int PROXYNAME_ERROR = 105000038;
    /**
     * 没有找到对应区域信息
     */
    public static int SYS_AREA_IS_NOT_FOUND = 105000039;

    /**
     * 身份证长度不正确
     */
    public static int IDCARD_LENGTH_ERROR = 105000040;

    /**
     * phone notfond
     */
    public static int PHONE_NOT_FONT = 105000041;
    /**
     * 用户信息处理失败
     */
    public static int USER_INFO_FAILURE = 105000042;
    /**
     * 添加/修改彩票信息失败
     */
    public static int LOTTERY_ADD_EDIT_FAILURE = 105000043;
    /**
     * 银行卡号不可为空
     */
    public static int BANKCARD_NOT_EMPTY = 105000044;
    /**
     * 银行名称不可为空
     */
    public static int BANKNAME_NOT_EMPTY = 105000045;
    /**
     * 持卡人姓名不可为空
     */
    public static int CARDHOLDER_NOT_EMPTY = 105000046;
    /**
     * 银行卡号不正确
     */
    public static int BANKCARD_FORMAT_ERROR = 105000047;

    /**
     * %s输入过长
     */
    public static int PARAM_TOO_LONG = 105000048;

    /**
     * %s参数错误
     */
    public static int PARAMETER_IS_ERROR = 105000049;
    /**
     * 链接银联失败，详情：
     */
    public static int USER_UNIONPAY_WALLET_ERROR = 105000050;
    /***** 更换手机失败 **********/
    public static int USER_CHANGE_PHONE_FAILURE = 105000051;
    /**
     * 查询类型错误
     */
    int QUERY_TYPE_ERROR = 105000052;
    /**
     * 无权查看订单
     */
    int NOTRIGHT_VIEW_ORDER = 105000053;
    /**彩票的编号或名称重复了**/
    public static int LOTTERY_NAME_OR_CODE_REPEAT = 105000054;
    /**访问SSO时异常*/
    public static int CONNECT_SSO_IS_ERROR = 105000055;
    /****************************************聚合支付模块错误代码***********************************************/

    /**
     * 当前订单已被处理
     */
    public static int ORDER_ALREADY_PROCESSED = 105200001;

    /**
     * 此订单非待支付状态
     */
    public static int ORDER_PAY_OR_CANCLE = 105200002;
    /**
     * 此订单非已支付状态
     */
    public static int ORDER_NOPAY_OR_CANCLE = 105200003;

    /**
     * 您没有权利操作此订单
     */
    public static int ORDER_NOT_ALLOW = 105200004;
    /**
     * 投注号码不能为空
     */
    public static int BET_NUMBER_ERROR = 105200005;

    /**
     * 本期销售已结束
     */
    public static int QUERYONSALEISSUE_ERROR = 105200006;
    /**
     * 本期销售已结束
     */
    public static int ONSALEISSUE_ERROR = 105200007;

    /**
     * 非pos产生订单，不能取消
     */
    public static int ORDER_NOT_POS = 105200008;

    /**
     * 出票失败
     */
    public static int BET_ORDER_ERROR = 105200009;
    /**
     * 请求数据包不合法
     */
    public static int PARAM_SIG_ERROE = 105200010;
    /**
     * 订单金额不正确
     */
    public static int ORDER_AMOUNT_ERROR = 105200011;
    /**
     * 投注最大倍数不能超过99倍
     */
    public static int LOTTERY_BIG_MULTIPLE = 105200012;
    /**
     * 本期已期结，无法投注
     */
    public static int ORDER_ISSUENUMBER_ERROR = 105200013;
    /**
     * 投注倍数错误
     */
    public static int LOTTERY_MIN_MULTIPLE = 105200014;
    /**
     * 参数【%s】不能为空
     */
    public static int LOTTERY_PARAMS_ERROR = 105200015;

    /**
     * 105200016=[%s]期，已期结
     */
    int LOTTERY_SALE_END = 105200016;

    /**
     * 暂时没有彩种信息
     */
    int LOTTERY_CODE_ERROR = 105200017;
    /**
     * SPID信息错误
     */
    int LOTTERY_SPID_ERROR = 105200018;
    /**
     * 此订单未中奖
     */
    int LOTTERY_NOT_WINNING = 105200019;
    /**
     * 不是大奖不可打印
     */
    int LOTTERY_NOT_BIGPRIZE = 105200020;

    /**
     * 金额不能超出20000元
     */
    int LOTTERY_BIG_AMOUNT_ERROR = 105200021;

    /****************************************系统模块错误代码***********************************************/
    /**
     * identity不可为空
     */
    public static int IDENTITY_EMPTY = 105300001;
    /**
     * 验证码错误
     */
    public static int CAPTCHA_ERROR = 105300002;

    /***********************渠道（收单机构），渠道商户代码******************************************/
    /**
     * 收单机构已存在
     */
    public static int ACQUIRER_EXIST = 105400001;
    /**
     * 渠道商户已存在
     */
    public static int MERCHANT_EXIST = 105400002;
    /**
     * 不存在此渠道商户
     */
    public static int MERCHANT_NOT_EXIST = 105400003;
    /**
     * 不存在此收单机构
     */
    public static int ACQUIRER_NOT_EXIST = 105400004;
    /**
     * 渠道商户和其申请表不匹配
     */
    public static int MERCHANT_APPLY_MISMATCHING = 105400005;
    /**
     * 不存在此渠道商户申请信息
     */
    public static int MERCHANT_APPLY_NOT_EXIST = 105400006;
    /**
     * 渠道商户已处于%状态
     */
    public static int MERCHANT_APPLY_MANAGE = 105400007;
    /**
     * 渠道商户审核失败
     */
    public static int MERCHANT_APPLY_SUCCESS = 105400008;
    /**
     * 汇拾商户号已存在
     */
    public static int HS_MERCID_EXIST = 105400009;

    /**
     * 汇拾在该收单机构所在省份已有商户号
     */
    public static int ACQ_PROVINCE_HS_MERCID_EXIST = 105400010;
    /**
     * 不存在此信息
     */
    public static int HS_MERCNO_NO_EXIST = 105400011;

    /***********************领奖后台代码******************************************/
    /**
     * 审核状态不正确
     */
    public static int IDCARD_STATUS_ERROR = 105500001;
    
    /**
     * 税后金额不可大于中奖金额
     */
    public static int WIN_PRIZE_ERROR = 105500002;

    /***************** 营销管理  *********************************************/
    /**
     * 营销活动名称已存在，不能重复
     ***/
    public static int MARKETING_NAME_EXIST = 105600001;
    /****名称不能重复*********/
    public static int MARKETING_ACCESSGROUP_MERCHANTNAME_EXIST = 105600002;
    /*****接入方式不合法*******/
    public static int MARKETING_ACCESSPROUP_ACCESSSETINGID_ILLEGAL = 105600003;
    /******营销活动不合法**********/
    public static int MARKETING_ILLEGAL = 105600004;
    /****营销活动的状态不合法***/
    public static int MARKETING_STATUS_ILLEGAL = 105600005;
    /*****营销活动的状态已处理%s状态*******/
    public static int MARKETING_STATUS_SAME = 105600006;
    /*****开始时间不能大于结束时间*************************/
    public static int MARKETING_STARTTIME_ENDTIME = 105600007;
    /*****请至少选择一种接入途径********/
    public static int MARKETING_ACCESS_GROUP_NOT_NULL = 105600008;
    /*****赠送记录不存在*************************/
    public static int GIVING_RECORD_NOT_EXIST = 105600009;
    /*****彩票已领取*************************/
    public static int LOTTERY_ALREADY_RECEIVE = 105600010;
    /*****领取权限已过期*************************/
    public static int LOTTERY_HAS_EXPIRED = 105600011;

    /**
     * 赠送记录未找到
     */
    public static int LOTTERY_GIVING_RECORD_IS_NOT_FOUND = 105600012;

    /**
     * 彩票未抢到，请重新领取
     */
    public static int LOTTERY_GIVING_RECORD_UPDATE_FAIL = 105600013;

    /**
     * 彩票已经被领光
     */
    public static int LOTTERY_HAS_BEEN_RECEIVED_OUT = 105600014;

    /**
     * 领取失败，请稍后再试
     */
    public static int LOTTERY_RECEIVED_FAIL = 105600015;
    /**接入商不存在**/
    public static int MARKETING_CHANNEL_NOT_EXIST = 105600016;
    
    /********************投注站编号分配******************************/
    
    /**
     * 投注站编号分配失败
     */
    public static int STATION_DISTRIBUTED_FAILED = 105700016;
    
    /**
     * 获取投注站编号超时
     */
    public static int STATION_DISTRIBUTED_OVER_TIME = 105700017;
    
    /**
     * 投注站编号已经分配
     */
    public static int STATIONCODE_HAS_BEEN_DISTRIBUTED = 105700018;
    
    /*********************请求宝乐彩**********************************/
    
    /**
     * 请求宝乐彩超时
     */
    public static int REQUEST_BLC_TIMEOUT = 105800001;
    
    /**
     * 请求宝乐彩异常
     */
    public static int REQUEST_BLC_FAILED = 105800002;
    
    
    /**********************汇彩宝和宝乐彩对账*****************************/
    public static int DOWNLOAD_FILE_FROM_HCB_FAILED = 105810000;
    
    public static int GENERATE_ACCOUNT_FILE_FAILED = 105810001;
    
    public static int DOWNLOAD_FILE_FROM_BLC_FAILED = 105810002;
    
 /*************************异常订单处理**********************************/
    
    /**
     * 订单信息不存在
     */
    public static int ORDER_INFO_NOT_EXIST = 105850001;
    
    /**
     * 支付订单号错误
     */
    public static int PAY_ORDERNO_ERROR = 105850002;
    
    /**
     * 银行卡信息有误
     */
    public static int BANK_CARD_ERROR = 105850003;
    
    /**
     * 状态参数错误
     */
    public static int STATUS_ERROR = 105850004;

    /**
     * 支付订单号已存在
     */
    public static int PAY_ORDERNO_HAVA_EXIST = 105850005;
    
    /**
     * 银行卡信息不能为空
     */
    public static int BANK_CARD_NOT_NULL = 105850006;
    
    /**
     * 打款金额有误
     */
    public static int AMOUNT_ERROR = 105850007;
    
    
    /***商户PC端***/
    /**
     * 商户不存在或被停用
     */
    int MERCHANT_LIST_NOT_EXIST = 105900001;
}
