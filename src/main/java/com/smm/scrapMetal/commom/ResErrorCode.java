package com.smm.scrapMetal.commom;

/**
 * Created by tangshulei on 2015/11/6.
 */
public class ResErrorCode {

    /**
     * 通用系统错误
     */
    public static final String ERROR_CODE                        = "1001";
    public static final String ERROR_MSG                         = "系统错误";
    public static final String PARAMS_NULL_ERROR_CODE            = "1002";
    public static final String PARAMS_NULL_ERROR_MSG             = "参数为空";

    /**
     * 市级
     */
    public static final String CITY_PARAMS_ERROR_CODE            = "1003";
    public static final String CITY_PARAMS_ERROR_MSG             = "省级Id为空";

    /**
     * 用户注册
     */
    public static final String CUS_PARAMS_ERROR_CODE             = "1003";
    public static final String CUS_PARAMS_ERROR_MSG              = "电话不能为空";
    public static final String CUS_EXIST_ERROR_CODE              = "1004";
    public static final String CUS_EXIST_ERROR_MSG               = "openId已经存在";
    /**
     * 用户查看
     */
    public static final String NO_EXIST_ERROR_CODE               = "1003";
    public static final String NO_EXIST_ERROR_MSG                = "openId不存在";

    /**
     * 采购单
     */
    public static final String PURCHASE_ERR_EMPTY_CODE           = "1003";
    public static final String PURCHASE_ERR_EMPTY_MSG            = "没有数据!";

    public static final String PURCHASE_ERR_ID_EMPTY_CODE        = "1004";
    public static final String PURCHASE_ERR_ID_EMPTY_MSG         = "采购单id不能为空!";

    public static final String PURCHASE_ERR_PHONE_EMPTY_CODE     = "1005";
    public static final String PURCHASE_ERR_PHONE_EMPTY_MSG      = "用户手机号不能为空!";

    public static final String PURCHASE_ERR_PHONE_ISNO_CODE      = "1006";
    public static final String PURCHASE_ERR_PHONE_ISNO_MSG       = "该手机用户不存在!";

    public static final String PURCHASE_ERR_PURCHASE_ISNO_CODE   = "1007";
    public static final String PURCHASE_ERR_PURCHASE_ISNO_MSG    = "采购单不存在!";
    public static final String PURCHASE_ERR_PURCHASE_ADD_CODE    = "1008";
    public static final String PURCHASE_ERR_PURCHASE_ADD_MSG     = "添加失败!";
    public static final String PURCHASE_ERR_PURCHASE_UPDATE_CODE = "1009";
    public static final String PURCHASE_ERR_PURCHASE_UPDATE_MSG  = "修改失败!";
    
    public static final String PURCHASE_API_ADD_ERROR             = "发布采购单出错";
    public static final String PURCHASE_API_UPD_ERROR             = "修改采购单出错";

    /**
     * 用户登录
     */
    public static final String ISLOGIN_ERROR_CODE                = "1003";
    public static final String ISLOGIN_ERROR_MSG                 = "用户没登录";

    /**
     * 订单
     */
    public static final String SUBMITORDER_ERROR_CODE            = "1003";
    public static final String SUBMITORDER_ERROR_MSG             = "订单提交失败";
    
    public static final String SUBMITORDER_ERROR_CODE1            = "1004";
    public static final String SUBMITORDER_ERROR_MSG1             = "phone跟买/卖家不匹配";
    public static final String SUBMITORDER_orderNo_code             = "1005";
    public static final String SUBMITORDER_orderNo_MSG             = "订单编号不能为空";
    public static final String SUBMITORDER_order_code             = "1006";
    public static final String SUBMITORDER_order_MSG             = "订单不存在";
    

    /**
     * 供货单
     */
    public static final String SUPPLYINSERT_ERROR_CODE           = "1008";
    public static final String SUPPLYINSERT_ERROR_MSG            = "添加失败!";
    public static final String SUPPLY_API_ADD_ERROR              = "发布供货单出错";
    public static final String SUPPLY_API_UPD_ERROR              = "修改供货单出错";
    

    /**
     * 收藏,点赞
     */
    public static final String SOURCE_NULL_ERROR_CODE            = "1003";
    public static final String SOURCE_NULL_ERROR_MSG             = "source is null";
    public static final String SOURCEID_NULL_ERROR_CODE          = "1004";
    public static final String SOURCEID_NULL_ERROR_MSG           = "sourceId is null";
    public static final String CUSTOMERID_NULL_ERROR_CODE        = "1005";
    public static final String CUSTOMERID_NULL_ERROR_MSG         = "customerId is null";
    public static final String TYPE_NULL_ERROR_CODE              = "1006";
    public static final String TYPE_NULL_ERROR_MSG               = "type is null";
    public static final String TYPE_NO_EXIST_ERROR_CODE          = "1007";
    public static final String TYPE_NO_EXIST_ERROR_MSG           = "type no exists";

    /**
     * 聊天
     */
    public static final String UNKNOWN_CHAT_INITIATOR = "未知的聊天发起人";
}
