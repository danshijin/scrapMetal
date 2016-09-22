package com.smm.scrapMetal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.smm.scrapMetal.bo.impl.AreasBO;
import com.smm.scrapMetal.bo.impl.CustomerBo;
import com.smm.scrapMetal.bo.impl.FavoriteBo;
import com.smm.scrapMetal.bo.impl.ItemBo;
import com.smm.scrapMetal.domain.Areas;
import com.smm.scrapMetal.domain.Customer;
import com.smm.scrapMetal.domain.Item;
import com.smm.scrapMetal.dto.ResultData;

/**
 * 类customerTest.java的实现描述：TODO 类实现描述
 * 
 * @author duqiang 2016年2月18日 下午2:19:41
 */
@RunWith(SpringJUnit4ClassRunner.class)
//注明测试类运行者
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml" })
//加载spring配置文件
@Transactional
//测试结束后，所有数据库变更将自动回滚
public class customerTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private CustomerBo customerBo;
    @Autowired
    private ItemBo     itemBo;
    @Autowired
    private AreasBO    areasBO;
    @Autowired
    private FavoriteBo favoriteBo;

    private Logger     logger = Logger.getLogger(customerTest.class);

    /**
     * 客户列表分页
     */
    @Test
    public void showCustomerListPage() {
        logger.info("开始客户列表分页测试");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("nameType", "");
        param.put("name", "");
        param.put("prov", "");
        param.put("city", "");
        param.put("timeType", "");
        param.put("startDay", "");
        param.put("endDay", "");
        param.put("saleType", "1");
        param.put("itemName", "");
        param.put("dayType", "");
        param.put("pno", 1);
        try {
            Map<String, Object> map = customerBo.newCustomerList(param);
            Assert.assertNotNull(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改客户
     */
    @Test
    public void updateCustomer() {
        logger.info("开始修改客户测试");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", 1 + "");
        param.put("id", 1);
        param.put("name", "222222");
        param.put("pic", null);
        param.put("nickName", null);
        param.put("phone", null);
        param.put("itemId", null);
        param.put("companyName", null);
        param.put("address", null);
        param.put("email", null);
        param.put("categorybusiness", null);
        param.put("entTypes", null);
        try {
            customerBo.loadUpdateCustomer(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加客户
     */
    @Test
    public void addCustomer() {
        logger.info("开始添加客户测试");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", 1 + "");
        param.put("pic", "11");
        param.put("name", "22");
        param.put("nickName", "22");
        param.put("phone", "2222222445555");
        param.put("address", "22");
        param.put("itemId", "1");
        param.put("companyName", "2");
        param.put("email", "2");
        param.put("categorybusiness", "1");
        param.put("entTypes", "29");
        try {
            customerBo.loadAddCustomer(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 客户详情
     */
    @Test
    public void showCustomerDetail() {
        logger.info("开始客户详情测试");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("customerId", "1");
        try {
            Customer customer = customerBo.getCustomerById(param);
            Assert.assertEquals(customer.getId() + "", 1 + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加客户接口测试
     */
    @Test
    public void addCustomerInterface() {
        logger.info("添加客户接口测试");
        JSONObject obj = new JSONObject();
        obj.put("openId", "ddd111");
        obj.put("nickName", "11");
        obj.put("phone", "344444111");
        obj.put("scrapItemIds", "1");
        obj.put("name", "dfg");
        obj.put("address", "xy");
        obj.put("companyName", "sh");
        obj.put("email", "dd233d");
        obj.put("weChatAvatar", "222");
        obj.put("entTypes", "29");
        obj.put("prov", "222");
        obj.put("city", "29");
        try {
            ResultData rest = customerBo.addCustomerInterface(obj.toString());
            Assert.assertNotNull(rest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改客户接口
     */
    @Test
    public void editCustomer() {
        logger.info("修改客户接口测试");
        JSONObject obj = new JSONObject();
        obj.put("openId", "1");
        obj.put("scrapItemIds", "1");
        obj.put("name", "dfg");
        obj.put("address", "xy");
        obj.put("companyName", "sh");
        obj.put("email", "dd233d11111");
        obj.put("entTypes", "29");
        obj.put("nickName", "ddd");
        try {
            ResultData rest = customerBo.updateCustomerInterface(obj.toString());

            Assert.assertNotNull(rest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 客户查看接口
     */
    @Test
    public void showCustomerDetailInteface() {
        logger.info("客户查看接口测试");
        JSONObject obj = new JSONObject();
        obj.put("openId", "1");
        try {
            ResultData rest = customerBo.showCustomerDetailInteface(obj.toString());
            Assert.assertNotNull(rest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户是否登录
     */
    @Test
    public void customerIsLogin() {
        logger.info("客户是否登录接口测试");
        JSONObject obj = new JSONObject();
        obj.put("openId", "ddd");
        try {
            ResultData rest = customerBo.customerIsLogin(obj.toString());
            Assert.assertNotNull(rest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户注销
     */
    @Test
    public void customerCancel() {
        logger.info("客户注销接口测试");
        JSONObject obj = new JSONObject();
        obj.put("openId", "12");
        try {
            ResultData rest = customerBo.customerCancel(obj.toString());
            Assert.assertNotNull(rest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 品种列表
     */
    @Test
    public void showItemList() {
        logger.info("品种列表接口测试");
        try {
            List<Item> itemList = itemBo.showItemList();
            Assert.assertNotNull(itemList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 省级列表
     */
    @Test
    public void getProvAreas() {
        logger.info("省级接口测试");
        try {
            List<Areas> areasList = areasBO.getParentAreas();
            Assert.assertNotNull(areasList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据省得到市
     */
    @Test
    public void getCityAreas() {
        logger.info("市级接口测试");
        JSONObject obj = new JSONObject();
        obj.put("parentId", "5");
        try {
            ResultData data = areasBO.getCityAreas(obj.toString());
            Assert.assertEquals(data.isSuccess(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 收藏点赞（可以取消收藏跟点赞一起集合的接口）
     */
    @Test
    public void editFavorite() {
        logger.info("收藏点赞接口测试");
        JSONObject obj = new JSONObject();
        obj.put("source", "1");
        obj.put("sourceId", "1");
        obj.put("openId", "ddd");
        obj.put("type", "1");
        try {
            ResultData data = favoriteBo.editFavorite(obj.toString());
            Assert.assertEquals(data.isSuccess(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 客户删除接口
     */
    @Test
    public void deleteCusByIds() {
        logger.info("删除客户接口测试");
        try {
            customerBo.deleteCustomerByIds("1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void showPhoneExist() {
        logger.info("判断手机号是否已经存在测试");
        try {
            Customer customer = customerBo.selectCustomerIdByPhone("1");
            Assert.assertNotNull(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
