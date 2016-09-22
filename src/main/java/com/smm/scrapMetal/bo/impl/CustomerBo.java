package com.smm.scrapMetal.bo.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.smm.scrapMetal.bo.ICustomerBo;
import com.smm.scrapMetal.commom.ResErrorCode;
import com.smm.scrapMetal.dao.IChatRecordsDao;
import com.smm.scrapMetal.dao.IChatsDao;
import com.smm.scrapMetal.dao.ICustomerDao;
import com.smm.scrapMetal.dao.ItemDao;
import com.smm.scrapMetal.domain.Customer;
import com.smm.scrapMetal.domain.Item;
import com.smm.scrapMetal.dto.ChatsListView;
import com.smm.scrapMetal.dto.ResultData;
import com.smm.scrapMetal.util.DateUtil;

@Service
public class CustomerBo implements ICustomerBo {
    private Logger  logger = Logger.getLogger(CustomerBo.class);
    @Resource
    ICustomerDao    iCustomerDao;
    @Resource
    ItemDao         itemDao;

    @Resource
    IChatsDao       iChatsDao;

    @Resource
    IChatRecordsDao iChatRecordsDao;

    @Override
    public void deleteCustomerByIds(String value) {
        iCustomerDao.deleteCustomerByIds(value);

    }

    @Override
    public Customer selectCustomerIdByPhone(String phone) {
        return iCustomerDao.selectCustomerIdByPhone(phone);

    }

    /**
     * 生成导出excel所需的数据
     */
    public List<Customer> newExcleCustomerList(Map<String, Object> map) throws Exception {
        String nameType = (String) map.get("nameType");
        String name = (String) map.get("name");
        String prov = (String) map.get("prov");
        String city = (String) map.get("city");
        String timeType = (String) map.get("timeType");
        String startDay = (String) map.get("startDay");
        String endDay = (String) map.get("endDay");
        String saleType = (String) map.get("saleType");
        String ids = (String) map.get("ids");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("del", 0);
        param.put("ids", ids);
        if (StringUtils.isNotBlank(nameType) && !"0".equals(nameType)) {
            name = java.net.URLDecoder.decode(name, "UTF-8");
            if (nameType.equals("1")) {//品种
                param.put("itemName", name);
            } else if (nameType.equals("2")) {//手机号码
                param.put("phone", name);
            } else if (nameType.equals("3")) {//呢称
                param.put("nickName", name);
            } else if (nameType.equals("4")) {//公司名
                param.put("companyName", name);
            } else if (nameType.equals("5")) {//姓名
                param.put("name", name);
            } else if (nameType.equals("6")) {//分责人
                param.put("userName", name);
            }
        }
        if (StringUtils.isNotBlank(prov)) {
            param.put("goodsProv", Integer.parseInt(prov));
        }
        if (StringUtils.isNotBlank(city) && !city.equals("null")) {
            param.put("goodsCity", Integer.parseInt(city));
        }
        if (StringUtils.isNotBlank(timeType)) {
            param.put("startDay", startDay);
            param.put("endDay", endDay);
            if (timeType.equals("0")) {
                param.put("createdAt", new Date());
            } else if (timeType.equals("1")) {
                param.put("dealTime", new Date());
            }

        }
        List<Customer> customerList = new ArrayList<Customer>();
        List<Customer> customerListNew = new ArrayList<Customer>();
        if (StringUtils.isNotBlank(saleType)) {//供应商还是提供商
            if (saleType.equals("2")) {//提供商
                param.put("categorybusiness", "2");
            } else {
                param.put("categorybusiness", "1");
            }
        } else {
            param.put("categorybusiness", "1");
        }
        customerListNew = iCustomerDao.showCustomerList(param);
        for (Customer customer : customerListNew) {
            StringBuffer itemNames = new StringBuffer();
            //省市拼接到一起
            if (StringUtils.isNotBlank(customer.getGoodsCityName())) {
                customer.setAreas(customer.getGoodsProvName() + "/" + customer.getGoodsCityName());
            } else {
                customer.setAreas(customer.getGoodsProvName());
            }
            List<Item> itemList = itemDao.showItemList();
            //吧品种拼接成汉子
            for (Item item : itemList) {
                if (customer.getScrapItemIds().contains(item.getId() + "")) {
                    itemNames.append(item.getName() + ",");
                }
            }
            if (null != customer.getIsSpread()) {
                if (customer.getIsSpread().intValue() == 0) {
                    customer.setIsSpreadName("未推广");
                } else if (customer.getIsSpread().intValue() == 1) {
                    customer.setIsSpreadName("已推广");
                }
            }
            if (StringUtils.isNotBlank(itemNames.toString())) {
                customer.setItemName(itemNames.toString().substring(0, itemNames.toString().length() - 1));
            }
            customerList.add(customer);
        }

        return customerList;

    }

    /**
     * 客户列表
     */
    public Map<String, Object> newCustomerList(Map<String, Object> map) throws Exception {
        Map<String, Object> rtn = new HashMap<String, Object>();
        String nameType = (String) map.get("nameType");
        String name = (String) map.get("name");
        String itemName = (String) map.get("itemName");
        String prov = (String) map.get("prov");
        String city = (String) map.get("city");
        String timeType = (String) map.get("timeType");
        String startDay = (String) map.get("startDay");
        String endDay = (String) map.get("endDay");
        String saleType = (String) map.get("saleType");
        String dayType = (String) map.get("dayType");
        String userId = (String) map.get("userId");
        String type = (String) map.get("type");
        Integer pno = Integer.parseInt(map.get("pno") + "");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("del", 0);
        if (StringUtils.isNotBlank(nameType) && !"0".equals(nameType)) {
            if (nameType.equals("1")) {//品种
                param.put("itemName", itemName);
            } else if (nameType.equals("2")) {//手机号码
                param.put("phone", name);
            } else if (nameType.equals("3")) {//呢称
                param.put("nickName", name);
            } else if (nameType.equals("4")) {//公司名
                param.put("companyName", name);
            } else if (nameType.equals("5")) {//姓名
                param.put("name", name);
            } else if (nameType.equals("6")) {//分责人
                param.put("userName", name);
            }
        }
        if (StringUtils.isNotBlank(prov)) {
            param.put("goodsProv", Integer.parseInt(prov));
        }
        if (StringUtils.isNotBlank(city) && !"null".equals(city)) {
            param.put("goodsCity", Integer.parseInt(city));
        }
        if (StringUtils.isNotBlank(timeType)) {
            param.put("startDay", startDay);
            param.put("endDay", DateUtil.dateAddAndSubtract("1", "yyyy-MM-dd",
                    new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(endDay))));
            if (timeType.equals("0")) {//加入时间
                param.put("createdAt", new Date());
            } else if (timeType.equals("1")) {//上次成交时间
                param.put("dealTime", new Date());
            }

        }
        if (null != dayType && !"".equals(dayType)) {//最近查询多少天的类型

            param.put("endDay", new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(new Date()));
            if (dayType.equals("1")) {//今日
                param.put("createdAt", new Date());
                param.put("startDay", new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(new Date()));
            } else if (dayType.equals("2")) {//最近7天
                param.put("createdAt", new Date());
                param.put("startDay", DateUtil.dateAddAndSubtract("-6", "yyyy-MM-dd 00:00:00", new SimpleDateFormat(
                        "yyyy-MM-dd 00:00:00").format(new Date())));
            } else if (dayType.equals("3")) {//本月新建
                param.put("createdAt", new Date());
                param.put("startDay", DateUtil.monthAddAndSubtract("-1", "yyyy-MM-dd 00:00:00", new SimpleDateFormat(
                        "yyyy-MM-dd 00:00:00").format(new Date())));
            }
        }
        param.put("startNum", (pno - 1) * 10);//分页开始条数
        param.put("endNum", 10);//结束条数

        if (null != saleType && !"".equals(saleType)) {//供应商还是提供商
            if (saleType.equals("2")) {//提供商
                param.put("categorybusiness", "2");
                if (null != dayType && !"".equals(dayType)) {
                    if (dayType.equals("4")) {//最近修改
                        param.put("updatedAt", new Date());
                        param.put("startDay", DateUtil.dateAddAndSubtract("-2", "yyyy-MM-dd 00:00:00",
                                new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(new Date())));
                        param.put("endDay", new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(new Date()));
                    } else if (dayType.equals("5")) {//回收站
                        param.put("del", 1);
                    }
                }

            } else {
                param.put("categorybusiness", "1");
                if (null != dayType && !"".equals(dayType)) {
                    if (dayType.equals("4")) {//最近修改
                        param.put("updatedAt", new Date());
                        param.put("startDay", DateUtil.dateAddAndSubtract("-2", "yyyy-MM-dd 00:00:00",
                                new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(new Date())));
                        param.put("endDay", new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(new Date()));

                    } else if (dayType.equals("5")) {//回收站
                        param.put("del", 1);
                    }
                }

            }
        } else {
            param.put("categorybusiness", "1");
            if (null != dayType && !"".equals(dayType)) {
                if (dayType.equals("4")) {//最近修改
                    param.put("updatedAt", new Date());
                    param.put("startDay", DateUtil.dateAddAndSubtract("-2", "yyyy-MM-dd 00:00:00",
                            new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(new Date())));
                    param.put("endDay", new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(new Date()));

                } else if (dayType.equals("5")) {//回收站
                    param.put("del", 1);
                }
            }
        }
        if (StringUtils.isNotBlank(type)) {//客户类型
            if (type.equals("2")) {
                param.put("userId", userId);
            } else if (type.equals("3")) {
                param.put("otherUserId", userId);
            }
        }

        Integer count = iCustomerDao.showCustomerListPageCount(param);
        List<Customer> customerList = new ArrayList<Customer>();
        List<Customer> customerListNew = iCustomerDao.showCustomerListPage(param);

        //给品种进行格式化
        for (Customer customer : customerListNew) {
            StringBuffer itemNames = new StringBuffer();
            List<Item> itemList = itemDao.showItemList();
            for (Item item : itemList) {
                if (customer.getScrapItemIds().contains(item.getId() + "")) {
                    itemNames.append(item.getName() + ",");
                }
            }
            if (null != itemNames.toString() && !"".equals(itemNames.toString())) {
                customer.setItemName(itemNames.toString().substring(0, itemNames.toString().length() - 1));
            }
            customerList.add(customer);
        }
        rtn.put("customerList", customerList);
        rtn.put("count", count);
        return rtn;
    }

    /**
     * 修改用户
     */
    public void loadUpdateCustomer(Map<String, Object> map) throws Exception {
        String pic = (String) map.get("pic");
        String userId = (String) map.get("userId");
        String name = (String) map.get("name");
        String nickName = (String) map.get("nickName");
        String categorybusiness = (String) map.get("categorybusiness");
        String email = (String) map.get("email");
        String companyName = (String) map.get("companyName");
        String address = (String) map.get("address");
        String itemId = (String) map.get("itemId");
        String phone = (String) map.get("phone");
        Integer id = Integer.parseInt(map.get("id") + "");
        String entTypes = (String) map.get("entTypes");
        String prov = (String) map.get("prov");
        String city = (String) map.get("city");
        Customer customer = new Customer();
        customer.setId(id);
        if (null != pic && !"".equals(pic)) {
            customer.setPic(Integer.parseInt(pic));
        }
        if (!StringUtils.isNotBlank(userId)) {
            logger.error("userid is null 用户没登录");
            return;
        }
        if (StringUtils.isNotBlank(prov)) {
            customer.setProv(Integer.parseInt(prov));
        }
        if (StringUtils.isNotBlank(city)) {
            customer.setCity(Integer.parseInt(city));
        }
        customer.setNickName(nickName);
        customer.setPhone(phone);
        customer.setScrapItemIds(itemId);
        customer.setName(name);
        customer.setAddress(address);
        customer.setCompanyName(companyName);
        customer.setEmail(email);
        if (StringUtils.isNotBlank(categorybusiness)) {
            //如果为供应商也是采购商，那就设置为3，全部
            if (categorybusiness.contains("2") && categorybusiness.contains("1")) {
                customer.setCategorybusiness("3");
            } else {
                customer.setCategorybusiness(categorybusiness);
            }
        }
        customer.setEntTypes(entTypes);
        customer.setUpdatedBy(Integer.parseInt(userId));
        iCustomerDao.updateCustomer(customer);
    }

    /**
     * 添加客户
     */
    public void loadAddCustomer(Map<String, Object> map) throws Exception {
        String pic = (String) map.get("pic");
        String userId = (String) map.get("userId");
        String name = (String) map.get("name");
        String nickName = (String) map.get("nickName");
        String categorybusiness = (String) map.get("categorybusiness");
        String email = (String) map.get("email");
        String companyName = (String) map.get("companyName");
        String address = (String) map.get("address");
        String itemId = (String) map.get("itemId");
        String phone = (String) map.get("phone");
        String entTypes = (String) map.get("entTypes");
        String prov = (String) map.get("prov");
        String city = (String) map.get("city");
        Customer customer = new Customer();
        if (null != pic && !"".equals(pic)) {
            customer.setPic(Integer.parseInt(pic));
        }
        if (!StringUtils.isNotBlank(userId)) {
            logger.error("userid is null 用户没登录");
            return;
        }
        if (StringUtils.isNotBlank(prov)) {
            customer.setProv(Integer.parseInt(prov));
        }
        if (StringUtils.isNotBlank(city)) {
            customer.setCity(Integer.parseInt(city));
        }
        customer.setNickName(nickName);
        customer.setPhone(phone);
        customer.setScrapItemIds(itemId);
        customer.setName(name);
        customer.setAddress(address);
        customer.setCompanyName(companyName);
        customer.setEmail(email);
        if (null != categorybusiness && !"".equals(categorybusiness)) {
            //如果为供应商也是采购商，那就设置为3，全部
            if (categorybusiness.contains("2") && categorybusiness.contains("1")) {
                customer.setCategorybusiness("3");
            } else {
                customer.setCategorybusiness(categorybusiness);
            }
        } else {
            customer.setCategorybusiness("3");
        }
        customer.setEntTypes(entTypes);
        customer.setCreatedBy(Integer.parseInt(userId));
        customer.setUpdatedBy(Integer.parseInt(userId));
        iCustomerDao.addCustomer(customer);
    }

    public Customer getCustomerById(Map<String, Object> map) throws Exception {
        String customerId = (String) map.get("customerId");
        Customer customer = null;
        StringBuilder itemName = new StringBuilder();
        if (null != customerId && !"".equals(customerId)) {
            customer = iCustomerDao.showCustomerDetail(Integer.parseInt(customerId));
            if (null != customer) {
                if (StringUtils.isNotBlank(customer.getScrapItemIds())) {
                    String items = customer.getScrapItemIds();
                    if (null != items && !"".equals(items)) {//给废旧品种做格式化
                        List<Item> itemList = itemDao.showItemList();
                        for (Iterator<Item> iterator = itemList.iterator(); iterator.hasNext();) {
                            Item item = (Item) iterator.next();
                            if (items.contains(item.getId() + "")) {
                                itemName.append(item.getName() + ",");
                            }
                        }
                        customer.setItemName(itemName.toString().substring(0, itemName.toString().length() - 1));
                    }
                    logger.info("itemName>>>>>>=" + itemName + ",itemIds=" + customer.getScrapItemIds());
                }
            }

        } else {
            logger.error("toUpdateCustomer params customerId is null");
        }
        return customer;
    }

    /**
     * 添加客户（接口）
     */
    public ResultData addCustomerInterface(String paramsStr) throws Exception {
        ResultData data = new ResultData();
        JSONObject obj = JSONObject.fromObject(paramsStr);
        String openId = obj.getString("openId");
        String nickName = obj.getString("nickName");
        //String userId = obj.getString("userId");
        String phone = obj.getString("phone");
        String isSpread = obj.getString("isSpread");
        String itemId = obj.getString("scrapItemIds");
        String name = obj.getString("name");
        String address = obj.getString("address");
        String companyName = obj.getString("companyName");
        String email = obj.getString("email");
        String weChatAvatar = obj.getString("weChatAvatar");
        String entTypes = obj.getString("entTypes");
        String prov = obj.getString("prov");
        String city = obj.getString("city");

        if (StringUtils.isNotBlank(phone)) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("openId", openId);
            param.put("infoStatus", 1);
            //查询这个openId用户是否已经注册过(之前被注销过得)
            Integer count = iCustomerDao.customerIsLogin(param);
            if (count > 0) {//已经被注册过,之前修改成可用状态
                param.put("infoStatus", 0);
                iCustomerDao.editCustomerStatusByOpenId(param);
                data.setSuccess(true);
                data.setUnAuthorizedRequest(false);
            } else {
                param.put("infoStatus", 0);
                Integer count1 = iCustomerDao.customerIsLogin(param);
                if (count1 > 0) {//已经存在过
                    logger.error("registerCustomer 的openId 已经存在");
                    data.setSuccess(false);
                    data.setErrorcode(ResErrorCode.CUS_EXIST_ERROR_CODE);
                    data.setErrMsg(ResErrorCode.CUS_EXIST_ERROR_MSG);
                    data.setUnAuthorizedRequest(false);
                } else {

                    //这里再根据手机号查询是否存在这个手机用户，
                    //如果存在，直接把openid会写到这个手机记录里面
                    Customer hasCustomerByPhone = iCustomerDao.selectCustomerIdByPhone(phone);
                    if (hasCustomerByPhone != null && hasCustomerByPhone.getId() != null) {
                        hasCustomerByPhone.setOpenId(openId);
                        hasCustomerByPhone.setWeChatAvatar(weChatAvatar);
                        hasCustomerByPhone.setNickName(nickName);
                        hasCustomerByPhone.setProv(Integer.parseInt(prov));
                        hasCustomerByPhone.setCity(Integer.parseInt(city));
                        iCustomerDao.updateCustomer(hasCustomerByPhone);
                        data.setSuccess(true);
                        data.setUnAuthorizedRequest(false);
                    } else {
                        Customer customer = new Customer();
                        if (StringUtils.isNotBlank(prov) && !prov.equals("null")) {
                            customer.setProv(Integer.parseInt(prov));
                        }
                        if (StringUtils.isNotBlank(city) && !city.equals("null")) {
                            customer.setCity(Integer.parseInt(city));
                        }
                        customer.setPic(1);
                        customer.setNickName(nickName);
                        customer.setPhone(phone);
                        customer.setScrapItemIds(itemId);
                        customer.setName(name);
                        if (StringUtils.isNotBlank(isSpread) && !isSpread.equals("null")) {
                            customer.setIsSpread(Integer.parseInt(isSpread));
                        }

                        customer.setAddress(address);
                        customer.setCompanyName(companyName);
                        customer.setEmail(email);
                        customer.setCategorybusiness("3");
                        customer.setEntTypes(entTypes);
                        customer.setOpenId(openId);
                        customer.setWeChatAvatar(weChatAvatar);
                        customer.setCreatedBy(1);
                        customer.setUpdatedBy(1);
                        iCustomerDao.addCustomer(customer);
                        data.setSuccess(true);
                        data.setUnAuthorizedRequest(false);

                    }

                }

            }

        } else {
            logger.error("registerCustomer 的params phone is null");
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.CUS_PARAMS_ERROR_CODE);
            data.setErrMsg(ResErrorCode.CUS_PARAMS_ERROR_MSG);
            data.setUnAuthorizedRequest(false);
        }
        return data;
    }

    /**
     * 修改用户（接口）
     */
    public ResultData updateCustomerInterface(String paramsStr) throws Exception {
        ResultData data = new ResultData();
        JSONObject obj = JSONObject.fromObject(paramsStr);
        String openId = obj.getString("openId");
        String scrapItemIds = obj.getString("scrapItemIds");
        String name = obj.getString("name");
        String address = obj.getString("address");
        String companyName = obj.getString("companyName");
        String nickName = obj.getString("nickName");
        String email = obj.getString("email");
        String entTypes = obj.getString("entTypes");
        if (StringUtils.isNotBlank(openId)) {
            Customer customer = new Customer();
            //customer.setPhone(phone);
            customer.setOpenId(openId);
            customer.setScrapItemIds(scrapItemIds);
            customer.setName(name);
            customer.setAddress(address);
            customer.setEmail(email);
            customer.setEntTypes(entTypes);
            customer.setNickName(nickName);
            customer.setCompanyName(companyName);
            customer.setUpdatedBy(1);
            Integer count = iCustomerDao.updateCustomerByPhone(customer);
            if (count > 0) {
                data.setSuccess(true);
                data.setUnAuthorizedRequest(false);
            } else {
                logger.error("updateCustomerInterface 用户openid =" + openId + "，不存在");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.NO_EXIST_ERROR_CODE);
                data.setErrMsg(ResErrorCode.NO_EXIST_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
            }

        } else {
            logger.error("editCustomer 的params phone is null");
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.CUS_PARAMS_ERROR_CODE);
            data.setErrMsg(ResErrorCode.CUS_PARAMS_ERROR_MSG);
            data.setUnAuthorizedRequest(false);
        }
        return data;
    }

    /**
     * 查看用户（接口）
     */
    public ResultData showCustomerDetailInteface(String paramsStr) throws Exception {
        ResultData data = new ResultData();
        JSONObject obj = JSONObject.fromObject(paramsStr);
        String openId = obj.getString("openId");
        if (StringUtils.isNotBlank(openId)) {
            Customer customer = iCustomerDao.showClientCustomerDetail(openId);
            if (null != customer) {
                //customer.setPublishCount(customer.getSupplyCount() + customer.getPurchaseCount());
                JSONObject cusObj = JSONObject.fromObject(customer);
                int notReadMsgNum = iChatRecordsDao.getNotReadMsgNumByCustomerId(customer.getId());
                cusObj.put("notReadMsgNum", notReadMsgNum);
                data.setSuccess(true);
                data.setUnAuthorizedRequest(false);
                data.setResult(cusObj.toString());
            } else {
                logger.error("showCustomerDetail openId=" + openId + ",没查询到数据");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.NO_EXIST_ERROR_CODE);
                data.setErrMsg(ResErrorCode.NO_EXIST_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
            }

        } else {
            logger.error("showCustomerDetail 的params phone is null");
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.CUS_PARAMS_ERROR_CODE);
            data.setErrMsg(ResErrorCode.CUS_PARAMS_ERROR_MSG);
            data.setUnAuthorizedRequest(false);
        }
        return data;
    }

    /**
     * 用户是否登录（接口）
     */
    public ResultData customerIsLogin(String paramsStr) throws Exception {
        ResultData data = new ResultData();
        JSONObject obj = JSONObject.fromObject(paramsStr);
        String openId = obj.getString("openId");
        if (StringUtils.isNotBlank(openId)) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("openId", openId);
            param.put("infoStatus", 0);
            Integer count = iCustomerDao.customerIsLogin(param);
            if (count > 0) {//代表已经存在登录
                data.setSuccess(true);
                data.setUnAuthorizedRequest(false);
            } else {
                logger.error("customerIsLogin 用户openid =" + openId + "，需要重新登录");
                data.setSuccess(false);
                data.setUnAuthorizedRequest(false);
                data.setErrorcode(ResErrorCode.ISLOGIN_ERROR_CODE);
                data.setErrMsg(ResErrorCode.ISLOGIN_ERROR_MSG);
            }
        } else {
            logger.error("showCustomerDetail 的params phone is null");
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.CUS_PARAMS_ERROR_CODE);
            data.setErrMsg(ResErrorCode.CUS_PARAMS_ERROR_MSG);
            data.setUnAuthorizedRequest(false);
        }
        return data;
    }

    /**
     * 用户注销（接口）
     */
    public ResultData customerCancel(String paramsStr) throws Exception {
        ResultData data = new ResultData();
        JSONObject obj = JSONObject.fromObject(paramsStr);
        String openId = obj.getString("openId");
        if (StringUtils.isNotBlank(openId)) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("openId", openId);
            param.put("infoStatus", 1);
            //注销用户
            Integer count = iCustomerDao.editCustomerStatusByOpenId(param);
            if (count > 0) {
                data.setSuccess(true);
                data.setUnAuthorizedRequest(false);
            } else {
                logger.error("customerCancel 用户openid =" + openId + "，不存在");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.NO_EXIST_ERROR_CODE);
                data.setErrMsg(ResErrorCode.NO_EXIST_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
            }

        } else {
            logger.error("customerCancel 的params phone is null");
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.CUS_PARAMS_ERROR_CODE);
            data.setErrMsg(ResErrorCode.CUS_PARAMS_ERROR_MSG);
            data.setUnAuthorizedRequest(false);
        }
        return data;
    }

    /**
     * 用户未读消息列表接口
     */
    public ResultData getNotReadMsgInteface(String paramsStr) throws Exception {
        ResultData data = new ResultData();
        JSONObject obj = JSONObject.fromObject(paramsStr);
        String openId = obj.getString("openId");
        if (StringUtils.isNotBlank(openId)) {
            Customer customer = iCustomerDao.showClientCustomerDetail(openId);
            if (null != customer) {
                List<ChatsListView> notReadList = iChatsDao.getNotReadMsgListByCustomerId(customer.getId());
                List<ChatsListView> rltList = new ArrayList<ChatsListView>();
                for (ChatsListView ele : notReadList) {
                    if (ele.getNotReadNum() > 0) {
                        rltList.add(ele);
                    }
                }

                JSONArray cusObj = JSONArray.fromObject(rltList);
                data.setSuccess(true);
                data.setUnAuthorizedRequest(false);
                data.setResult(cusObj.toString());
            } else {
                logger.error("getNotReadMsgList openId=" + openId + ",没查询到数据");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.NO_EXIST_ERROR_CODE);
                data.setErrMsg(ResErrorCode.NO_EXIST_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
            }

        } else {
            logger.error("getNotReadMsgList 的params openId is null");
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.CUS_PARAMS_ERROR_CODE);
            data.setErrMsg(ResErrorCode.CUS_PARAMS_ERROR_MSG);
            data.setUnAuthorizedRequest(false);
        }
        return data;
    }

    @Override
    public String selectNickNameById(int customerId) {
        return iCustomerDao.selectNickNameById(customerId);
    }
}
