package com.smm.scrapMetal.dao;

import java.util.List;
import java.util.Map;

import com.smm.scrapMetal.domain.Customer;

/**
 * 类ICustomerDao.java的实现描述：TODO 类实现描述
 * 
 * @author duqiang 2016年1月25日 下午5:07:34
 */
public interface ICustomerDao {
    /**
     * 分页列表
     * 
     * @param param
     * @return
     */
    public List<Customer> showCustomerListPage(Map<String, Object> param);

    /**
     * 列表
     * 
     * @param param
     * @return
     */
    public List<Customer> showCustomerList(Map<String, Object> param);

    /**
     * 列表条数
     * 
     * @param param
     * @return
     */
    public Integer showCustomerListPageCount(Map<String, Object> param);

    /**
     * 批量删除
     * 
     * @param value
     */
    public void deleteCustomerByIds(String value);

    /**
     * 添加客户
     * 
     * @param customer
     */
    public void addCustomer(Customer customer);

    /**
     * 客户明细
     * 
     * @param id
     * @return
     */
    public Customer showCustomerDetail(Integer id);

    /**
     * 修改客户跟据id
     * 
     * @param customer
     */
    public void updateCustomer(Customer customer);

    /**
     * 修改客户根据电话phone
     * 
     * @param customer
     */
    public Integer updateCustomerByPhone(Customer customer);

    /**
     * 根据openId得到客户信息
     * 
     * @param openId
     * @return
     */
    public Customer showClientCustomerDetail(String openId);

    /**
     * 查询用户是否登录或者注销
     * 
     * @param param
     * @return
     */
    public Integer customerIsLogin(Map<String, Object> param);

    /**
     * 注销用户，注册启用用户
     * 
     * @param param
     */
    public Integer editCustomerStatusByOpenId(Map<String, Object> param);

    /**
     * 根据openId获取customerId
     * 
     * @param openId
     * @return
     */
    public Integer selectCustomerIdByOpenId(String openId);

    /**
     * 根据手机号得到客户信息
     * 
     * @param phone
     * @return
     */
    public Customer selectCustomerIdByPhone(String phone);
    
    /** 通过customerId获取用户昵称，如果昵称为空，则使用用户名称代替
     * @param customerId
     * @return
     */
    public String selectNickNameById(int customerId);

}
