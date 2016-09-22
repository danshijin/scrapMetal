package com.smm.scrapMetal.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.scrapMetal.bo.IUserBO;
import com.smm.scrapMetal.bo.impl.AreasBO;
import com.smm.scrapMetal.bo.impl.CategoryBO;
import com.smm.scrapMetal.bo.impl.CustomerBo;
import com.smm.scrapMetal.bo.impl.ItemBo;
import com.smm.scrapMetal.bo.impl.UserBO;
import com.smm.scrapMetal.domain.Areas;
import com.smm.scrapMetal.domain.Category;
import com.smm.scrapMetal.domain.Customer;
import com.smm.scrapMetal.domain.Item;
import com.smm.scrapMetal.domain.User;
import com.smm.scrapMetal.util.ExportUtil;
import com.smm.scrapMetal.util.JSONUtil;

/**
 * @author duqiang
 */
@Controller
public class CustomerController {
    private Logger     logger = Logger.getLogger(CustomerController.class);
    @Resource
    private CustomerBo customerBo;
    @Resource
    private AreasBO    areasBO;
    @Resource
    private CategoryBO categoryBO;
    @Resource
    private UserBO     userBO;
    @Resource
    private ItemBo     itemBo;
    @Resource
    private IUserBO    iuserBo;

    /**
     * 客户列表
     * 
     * @param request
     * @return
     */
    @RequestMapping("/customer/customerList")
    public ModelAndView toAddWarehouseReceipt(HttpServletRequest request) {
        logger.info("进入》》》》customerList");
        String nameType = request.getParameter("nameType");//名字查询类型
        String name = request.getParameter("name");//名字
        String prov = request.getParameter("prov");//省
        String city = request.getParameter("city");//市
        String timeType = request.getParameter("timeType");//时间类型
        String startDay = request.getParameter("startDay");//开始时间
        String endDay = request.getParameter("endDay");//结束时间
        String saleType = request.getParameter("saleType");//提供商还是采购商
        String dayType = request.getParameter("dayType");//最近时间查询的类型
        String itemName = request.getParameter("itemName");//品种id
        String type = request.getParameter("type");//

        Integer pno = null;
        ModelAndView mav = new ModelAndView("/customer/customerList");
        try {
            if (StringUtils.isNotBlank(name)) {
                name = URLDecoder.decode(name, "UTF-8");
            }

            if (null != request.getParameter("pno")) {
                pno = Integer.parseInt(request.getParameter("pno"));
            }
            if (pno == null)
                pno = 1;
            logger.info("pno=" + pno);
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("nameType", nameType);
            param.put("name", name);
            param.put("prov", prov);
            param.put("city", city);
            param.put("timeType", timeType);
            param.put("startDay", startDay);
            param.put("endDay", endDay);
            param.put("saleType", saleType);
            param.put("itemName", itemName);
            param.put("dayType", dayType);
            param.put("pno", pno);
            param.put("type", type);
            User user = (User) request.getSession().getAttribute("userInfo");
            param.put("userId", user.getId() + "");
            Map<String, Object> map = customerBo.newCustomerList(param);
            Integer count = (Integer) map.get("count");
            List<Customer> customerList = (List<Customer>) map.get("customerList");
            Integer page = count / 10;
            if (count % 10 != 0) {
                page += 1;
            }
            if (StringUtils.isNotBlank(saleType)) {//供应商还是提供商
                mav.addObject("saleType", saleType);
            } else {
                mav.addObject("saleType", "1");
            }

            List<Areas> areasList = areasBO.getParentAreas();
            mav.addObject("areasList", areasList);
            mav.addObject("nameType", nameType);
            mav.addObject("name", name);
            if (StringUtils.isNotBlank(prov)) {
                mav.addObject("prov", Integer.parseInt(prov));
            }
            if (StringUtils.isNotBlank(city) && !"null".equals(city)) {
                mav.addObject("city", Integer.parseInt(city));
            }
            List<Item> itemList = itemBo.showItemList();
            mav.addObject("itemList", itemList);
            mav.addObject("timeType", timeType);
            mav.addObject("startDay", startDay);
            mav.addObject("endDay", endDay);
            mav.addObject("type", type);
            mav.addObject("itemId", itemName);
            if (StringUtils.isBlank(dayType)) {
                mav.addObject("dayType", "6");
            } else {
                mav.addObject("dayType", dayType);
            }

            mav.addObject("customerList", customerList);
            mav.addObject("totalRecords", count);// 总条数
            mav.addObject("totalPage", page);// 总页数
        } catch (Exception e) {
            logger.error("showCustomerList系统错误", e);
        }
        return mav;
    }

    /**
     * 根据省得到市
     * 
     * @param request
     * @return
     */
    @RequestMapping("/customer/showCity")
    @ResponseBody
    public String showCityByProv(HttpServletRequest request) {
        String provId = request.getParameter("provId");
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
            if (StringUtils.isNotBlank(provId)) {
                List<Areas> areasList = areasBO.getChildArea(provId);
                rtnMap.put("areasList", areasList);
                rtnMap.put("info", "success");
            } else {
                rtnMap.put("info", "failed");
                logger.error("showCity 的params provId is null");
            }

            return JSONUtil.map2json(rtnMap);
        } catch (Exception e) {
            logger.error("showCity系统错误", e);
        }
        return null;
    }

    /**
     * 批量删除
     * 
     * @param request
     * @return
     */
    @RequestMapping("/customer/deleteCustomer")
    @ResponseBody
    public String deleteCustomer(HttpServletRequest request) {
        String idS = request.getParameter("idS");

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
            if (StringUtils.isNotBlank(idS)) {
                idS = idS.substring(0, idS.length() - 1);
                customerBo.deleteCustomerByIds(idS);
                rtnMap.put("info", "success");
            } else {
                rtnMap.put("info", "failed");
                logger.error("deleteCustomer 的params idS is null");
            }
            return JSONUtil.map2json(rtnMap);
        } catch (Exception e) {
            logger.error("deleteCustomer系统错误", e);
        }
        return null;
    }

    /**
     * 进入添加页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/customer/toAddCustomer")
    public ModelAndView toAddCustomer(HttpServletRequest request) {

        ModelAndView mav = new ModelAndView("/customer/addCustomer");
        try {
            List<Category> categoryList = categoryBO.getCategorys();
            List<User> userList = userBO.getUsers();
            List<Areas> areasList = areasBO.getParentAreas();
            mav.addObject("areasList", areasList);
            mav.addObject("categoryList", categoryList);
            mav.addObject("userList", userList);
            User user = (User) request.getSession().getAttribute("userInfo");
            mav.addObject("userId", user.getId());
        } catch (Exception e) {
            logger.error("toAddCustomer系统错误", e);
        }
        return mav;
    }

    /**
     * 品种列表
     * 
     * @param request
     * @return
     */
    @RequestMapping("/customer/showItemListView")
    public ModelAndView showItemListView(HttpServletRequest request) {

        ModelAndView mav = new ModelAndView("/customer/itemListModal");
        try {
            List<Item> itemList = itemBo.showItemList();
            mav.addObject("itemList", itemList);
        } catch (Exception e) {
            logger.error("showItemListView系统错误", e);
        }
        return mav;
    }

    /**
     * 添加客户
     * 
     * @param request
     * @return
     */
    @RequestMapping("/customer/addCustomer")
    @ResponseBody
    public String addCustomer(HttpServletRequest request) {
        String pic = request.getParameter("pic");
        String nickName = request.getParameter("nickName");
        String phone = request.getParameter("phone");
        String itemId = request.getParameter("itemId");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String companyName = request.getParameter("companyName");
        String email = request.getParameter("email");
        String categorybusiness = request.getParameter("categorybusiness");
        String entTypes = request.getParameter("entTypes");
        String prov = request.getParameter("prov");
        String city = request.getParameter("city");
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            User user = (User) request.getSession().getAttribute("userInfo");
            param.put("userId", user.getId() + "");
            param.put("pic", pic);
            param.put("name", name);
            param.put("nickName", nickName);
            param.put("phone", phone);
            param.put("address", address);
            param.put("itemId", itemId);
            param.put("companyName", companyName);
            param.put("email", email);
            param.put("categorybusiness", categorybusiness);
            param.put("entTypes", entTypes);
            param.put("prov", prov);
            param.put("city", city);
            customerBo.loadAddCustomer(param);
            rtnMap.put("info", "success");
            return JSONUtil.map2json(rtnMap);
        } catch (Exception e) {
            logger.error("addCustomer系统错误", e);
        }
        return null;
    }

    @RequestMapping("/customer/poolExportDel")
    public void poolExportDel(HttpServletRequest request, HttpServletResponse response) {
        logger.info("进入》》》》poolExportDel");
        String nameType = request.getParameter("nameType");//名字查询类型
        String name = request.getParameter("name");//名字
        String prov = request.getParameter("prov");//省
        String city = request.getParameter("city");//市
        String timeType = request.getParameter("timeType");//时间类型
        String startDay = request.getParameter("startDay");//开始时间
        String endDay = request.getParameter("endDay");//结束时间
        String saleType = request.getParameter("saleType");//提供商还是采购商
        String ids = request.getParameter("ids");//选中导出ids
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {

            Map<String, Object> param = new HashMap<String, Object>();
            param.put("nameType", nameType);
            param.put("name", name);
            param.put("prov", prov);
            param.put("city", city);
            param.put("timeType", timeType);
            param.put("startDay", startDay);
            param.put("endDay", endDay);
            param.put("saleType", saleType);
            param.put("ids", ids);
            List<Customer> customerList = customerBo.newExcleCustomerList(param);
            ExportUtil<Customer> excel = new ExportUtil<Customer>();

            String fileName = "客户信息列表";

            String[] headerNames = new String[] { "手机", "废旧品种", "姓名", "公司名", "地区", "上次成交时间", "负责人", "加入时间", "是否推广" };
            String[] header = new String[] { "phone", "itemName", "name", "companyName", "areas", "dealTime",
                    "userName", "createdAt", "isSpreadName" };
            String[] comments = new String[] { null, null, null, null, null, null, null, null, null };
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            excel.export("sheet1", headerNames, header, comments, customerList, os, "");

            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String((fileName + ".xls").getBytes("GBK"), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();

            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final Exception e) {
            logger.error("系统错误", e);
        } finally {
            try {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            } catch (Exception e2) {
                logger.error("系统错误", e2);
            }

        }
    }

    /**
     * 进入修改页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/customer/toUpdateCustomer")
    public ModelAndView toUpdateCustomer(HttpServletRequest request) {
        String customerId = request.getParameter("customerId");
        ModelAndView mav = new ModelAndView("/customer/updateCustomer");
        try {
            List<Category> categoryList = categoryBO.getCategorys();
            List<User> userList = userBO.getUsers();
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("customerId", customerId);
            Customer customer = customerBo.getCustomerById(param);
            List<Areas> areasList = areasBO.getParentAreas();
            //查询所有负责人
            List<User> user = iuserBo.getUsers();
            mav.addObject("user", user);
            mav.addObject("areasList", areasList);
            mav.addObject("categoryList", categoryList);
            mav.addObject("userList", userList);
            mav.addObject("customer", customer);
            //mav.addObject("userId", 1);
        } catch (Exception e) {
            logger.error("toUpdateCustomer系统错误", e);
        }
        return mav;
    }

    /**
     * 修改客户
     * 
     * @param request
     * @return
     */
    @RequestMapping("/customer/updateCustomer")
    @ResponseBody
    public String updateCustomer(HttpServletRequest request) {
        String pic = request.getParameter("pic");
        String customerId = request.getParameter("customerId");
        String nickName = request.getParameter("nickName");
        String phone = request.getParameter("phone");
        String itemId = request.getParameter("itemId");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String companyName = request.getParameter("companyName");
        String email = request.getParameter("email");
        String categorybusiness = request.getParameter("categorybusiness");
        String entTypes = request.getParameter("entTypes");
        String prov = request.getParameter("prov");
        String city = request.getParameter("city");
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
            if (StringUtils.isNotBlank(customerId)) {
                Map<String, Object> param = new HashMap<String, Object>();
                User user = (User) request.getSession().getAttribute("userInfo");
                param.put("userId", user.getId() + "");
                param.put("id", customerId);
                param.put("name", name);
                param.put("pic", pic);
                param.put("nickName", nickName);
                param.put("phone", phone);
                param.put("itemId", itemId);
                param.put("companyName", companyName);
                param.put("address", address);
                param.put("email", email);
                param.put("categorybusiness", categorybusiness);
                param.put("entTypes", entTypes);
                param.put("prov", prov);
                param.put("city", city);
                customerBo.loadUpdateCustomer(param);
                rtnMap.put("info", "success");
            } else {
                logger.error("updateCustomer params 修改条件的 customerId is null");
                rtnMap.put("info", "failed");
                rtnMap.put("error", "customerId is null");
                return JSONUtil.map2json(rtnMap);
            }
            return JSONUtil.map2json(rtnMap);
        } catch (Exception e) {
            logger.error("updateCustomer系统错误", e);
        }
        return null;
    }

    /**
     * 查看手机号是否存在
     * 
     * @param request
     * @return
     */
    @RequestMapping("/customer/showPhoneExist")
    @ResponseBody
    public String showPhoneExist(HttpServletRequest request) {
        String phone = request.getParameter("phone");

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
            if (StringUtils.isNotBlank(phone)) {
                Customer customer = customerBo.selectCustomerIdByPhone(phone);
                if (null != customer) {
                    rtnMap.put("info", "failed");
                    rtnMap.put("errorMsg", "手机号已经存在");
                    logger.info("showPhoneExist 手机号已经存在" + phone);
                } else {
                    rtnMap.put("info", "success");
                }
            } else {
                rtnMap.put("info", "failed");
                rtnMap.put("errorMsg", "手机号不能为空");
                logger.error("showPhoneExist 手机号不能为空");
            }
            return JSONUtil.map2json(rtnMap);
        } catch (Exception e) {
            logger.error("showPhoneExist系统错误", e);
        }
        return null;
    }
}
