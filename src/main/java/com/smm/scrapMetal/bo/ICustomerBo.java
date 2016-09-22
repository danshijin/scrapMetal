package com.smm.scrapMetal.bo;

import java.util.List;
import java.util.Map;

import com.smm.scrapMetal.domain.Customer;
import com.smm.scrapMetal.dto.ResultData;

public interface ICustomerBo {

    public void deleteCustomerByIds(String value);

    public List<Customer> newExcleCustomerList(Map<String, Object> map) throws Exception;

    public Map<String, Object> newCustomerList(Map<String, Object> map) throws Exception;

    public void loadAddCustomer(Map<String, Object> map) throws Exception;

    public void loadUpdateCustomer(Map<String, Object> map) throws Exception;

    public Customer getCustomerById(Map<String, Object> map) throws Exception;

    /**
     * 接口添加客户
     * 
     * @param map
     * @throws Exception
     */
    public ResultData addCustomerInterface(String paramsStr) throws Exception;

    /**
     * 修改客户接口
     * 
     * @param paramsStr
     * @return
     * @throws Exception
     */
    public ResultData updateCustomerInterface(String paramsStr) throws Exception;

    /**
     * 用户查看接口
     * 
     * @param paramsStr
     * @return
     * @throws Exception
     */
    public ResultData showCustomerDetailInteface(String paramsStr) throws Exception;

    /**
     * 校验用户是否需要从新登录
     * 
     * @param paramsStr
     * @return
     * @throws Exception
     */
    public ResultData customerIsLogin(String paramsStr) throws Exception;

    /**
     * 用户注销
     * 
     * @param paramsStr
     * @return
     * @throws Exception
     */
    public ResultData customerCancel(String paramsStr) throws Exception;

    public Customer selectCustomerIdByPhone(String phone);

	/** 通过customerId获取用户昵称，如果昵称为空，则使用用户名称代替
	 * @param customerId
	 * @return
	 */
	String selectNickNameById(int customerId);
}
