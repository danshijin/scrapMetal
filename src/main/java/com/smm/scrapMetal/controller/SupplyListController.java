package com.smm.scrapMetal.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smm.scrapMetal.bo.IAreasBO;
import com.smm.scrapMetal.bo.IItemBo;
import com.smm.scrapMetal.bo.PriceExplainBo;
import com.smm.scrapMetal.bo.SupplyBO;
import com.smm.scrapMetal.commom.ResErrorCode;
import com.smm.scrapMetal.dao.SupplyDAO;
import com.smm.scrapMetal.domain.Areas;
import com.smm.scrapMetal.domain.Delivery;
import com.smm.scrapMetal.domain.Item;
import com.smm.scrapMetal.domain.PriceExplain;
import com.smm.scrapMetal.domain.SupplyImages;
import com.smm.scrapMetal.dto.ResultData;
import com.smm.scrapMetal.dto.SupplyListView;
import com.smm.scrapMetal.util.DateUtil;
import com.smm.scrapMetal.util.ExportUtil;
import com.smm.scrapMetal.util.JSONUtil;

import freemarker.log.Logger;

/**
 * 
 * @author hece
 *
 */

@Controller
@RequestMapping("/supply")
public class SupplyListController {

	private static Logger logger = Logger.getLogger(SupplyListController.class.getName());
	@Resource
	private SupplyBO supplybo;
	@Resource
	private IItemBo itembo;
	@Resource
	private IAreasBO areasbo;
	@Resource
	private PriceExplainBo PriceExplainBo;
	@Resource
	private SupplyDAO supplyDao;
	@Value("#{address['imgServerAdd']}")
    private String imgServerAddress;
	@Value("#{address['sendUrl']}")
    private String sendUrl;
	
	
	/**
	 * 供货单管理(后台)
	 * @throws UnsupportedEncodingException 
	 * */
	@RequestMapping(value="/orderListWeb")
	public ModelAndView orderListWeb(HttpServletRequest request) throws UnsupportedEncodingException{
		logger.info("----------供货单列表(后台)-----------");
		ModelAndView modelView = new ModelAndView("/supply/supplyOrderList");
		String type = request.getParameter("type");
		String recommend = request.getParameter("recommend");
		String isGet = request.getParameter("isGet");
//		if (recommend != null && isGet.equals("true")) {
//			recommend = new String(recommend.getBytes("ISO-8859-1"), "UTF-8");
//		}
		if (StringUtils.isNotBlank(recommend)) {
			recommend = URLDecoder.decode(recommend, "UTF-8");
        }
		String prov = request.getParameter("prov");
		String city = request.getParameter("city");
		String attribute = request.getParameter("attribute");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String itemid = request.getParameter("itemid");
		String status = request.getParameter("status");
		String pno = request.getParameter("pno");
		String isPriced = request.getParameter("isPriced");
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		if(startTime!=null&&!startTime.equals("")){
			map.put("startTime",DateUtil.doFormatDate(DateUtil.startOfTodDay(startTime), "yyyy-MM-dd HH:mm:ss"));
		}
		if(endTime!=null&&!endTime.equals("")){
			map.put("endTime",DateUtil.doFormatDate(DateUtil.endOfTodDay(endTime), "yyyy-MM-dd HH:mm:ss"));
		}
		map.put("status", status);
		map.put("type", type);
		map.put("recommend", recommend);
		map.put("prov", prov);
		map.put("city", city);
		map.put("attribute", attribute);
		map.put("itemid", itemid);
		map.put("pno", pno);
		map.put("isPriced", isPriced);
		logger.info(map.toString());
		Map<String,Object> supplymap = supplybo.supplyList(map);
		//所有分类名称
		List<Item> item = itembo.showItemList();
		//所有省名称
		List<Areas> areas = areasbo.getParentAreas();
		
		modelView.addObject("type", request.getParameter("type"));
		modelView.addObject("recommend", recommend);
		modelView.addObject("prov", request.getParameter("prov"));
		modelView.addObject("city", request.getParameter("city"));
		modelView.addObject("attribute", request.getParameter("attribute"));
		modelView.addObject("startTime", request.getParameter("startTime"));
		modelView.addObject("endTime", request.getParameter("endTime"));
		modelView.addObject("itemid", request.getParameter("itemid"));
		modelView.addObject("status", request.getParameter("status"));
		modelView.addObject("areas", areas);
		modelView.addObject("item", item);
		modelView.addObject("isPriced", isPriced);
		modelView.addObject("supplyList", supplymap.get("supplylist"));
		modelView.addObject("totalRecords", supplymap.get("totalRecords"));//总条数
		modelView.addObject("totalPage", supplymap.get("totalPage"));//总页数
		return modelView;
	}
	
	/**
	 * 供货单管理(接口)
	 * */
	@RequestMapping(value="/orderList")
	@ResponseBody
	public ResultData orderList(HttpServletRequest request){
		logger.info("----------供货单列表(接口)-----------");
		String paramsStr = request.getParameter("paramsStr");
		ResultData data = new ResultData();
        try {
            data = supplybo.supplyListAPI(paramsStr);
        } catch (Exception e) {
            logger.error("orderList系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
        }
        return data;
	}
	
	/**
	 * 新建供货单
	 * */
	@RequestMapping(value="/insert")
	public ModelAndView insert(){
		ModelAndView modelView = new ModelAndView("/supply/supplyInsert");
		//所有分类名称
		List<Item> item = itembo.showItemList();
		//货物所在地名称
		List<Areas> areas = areasbo.getParentAreas();
		//价格说明
		List<PriceExplain> priceExplainList=PriceExplainBo.queryHomePriceExplain();
		//交货方式
		List<Delivery> listDel = supplyDao.queryDomestic();
		
		modelView.addObject("listDel", listDel);
		modelView.addObject("priceExplainList", priceExplainList);
		modelView.addObject("supp", new SupplyListView());
		modelView.addObject("item", item);
		modelView.addObject("areas", areas);
		return modelView;
	}
	
	/**
	 * 获取国外交货方式
	 * @param request
	 * @return
	 */
	@RequestMapping(value="queryDelivery")
	@ResponseBody
	public List<Delivery> priceExplain(HttpServletRequest request){
		String flag=request.getParameter("flag");
		List<Delivery> listDel = null;	
		if(flag!=null&&Integer.valueOf(flag)==2){
			listDel = supplyDao.queryAbroad();
		}else {
			listDel = supplyDao.queryDomestic();
		}
		if(listDel!=null && listDel.size()==0){
			listDel = null;
		}
		return listDel;
	} 
	
	/**
	 * 添加供货单
	 * */
	@RequestMapping(value="/insertSupply")
	@ResponseBody
	public Map<String, Object> insertSupply(HttpServletRequest request){
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("itemid", request.getParameter("itemid"));
		resultMap.put("prov", request.getParameter("prov"));
		resultMap.put("city", request.getParameter("city"));
		resultMap.put("infoTitle", request.getParameter("infoTitle"));
		resultMap.put("description", request.getParameter("description"));
		resultMap.put("usableQuantity", request.getParameter("usableQuantity"));
		resultMap.put("price", request.getParameter("price"));
		resultMap.put("priceUnit", request.getParameter("priceUnit"));
		resultMap.put("priceExplain", request.getParameter("priceExplain"));
		resultMap.put("delivery", request.getParameter("delivery"));
		resultMap.put("expiryType", request.getParameter("expiryType"));
		resultMap.put("phone", request.getParameter("phone"));
		resultMap.put("himg",request.getParameter("hiddimg"));
		resultMap.put("priceNegotiable",request.getParameter("priceNegotiable"));
		int insertsupp = supplybo.addSupply(resultMap,request);
		if(insertsupp == 1){
			map.put("result", "添加成功!");
			map.put("success", 1);
			map.put("errorcode", "");
			map.put("unAuthorizedRequest", false);
		}else{
			map.put("result", "添加失败!");
			map.put("success", insertsupp);
			map.put("errorcode", 1002);
			map.put("unAuthorizedRequest", false);
		}
		return map;
	}
	
	/**
	 * 新建供货单(接口)
	 * */
	@RequestMapping(value="/addSupply")
	@ResponseBody
	public ResultData addSupply(HttpServletRequest request){
		logger.info("-----------新建供货单(接口)-----------");
		ResultData data = new ResultData();
        String paramsStr = request.getParameter("paramsStr");
        try {
            if (!StringUtils.isNotBlank(paramsStr)) {
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
                return data;
            }
           data = supplybo.addSupplyAPI(paramsStr);
        } catch (Exception e) {
            logger.error("customerCancel系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
        }
        return data;
	}
	
	
	/**
	 * 通过ID查询详情
	 * */
	@RequestMapping(value="/supplyDetailById")
	public ModelAndView supplyDetailById(String suppid,String status){
		ModelAndView modelView = new ModelAndView("/supply/supplyUpdate");
		//所有分类名称
		List<Item> item = itembo.showItemList();
		//货物所在地名称
		List<Areas> areas = areasbo.getParentAreas();
		//通过ID查询详情
		SupplyListView suppDetail = supplybo.supplyDetailById(suppid);
		//价格说明
		List<PriceExplain> priceExplainList=PriceExplainBo.queryHomePriceExplain();
		//交货方式
		List<Delivery> listDel = supplybo.queryDomestic();
		//查询供货单图片
		List<SupplyImages> img = new ArrayList<SupplyImages>();
		String imgAddr = "";
		if(suppDetail != null && suppDetail.getId() != null && !suppDetail.getId().equals("")){
			List<SupplyImages> suppImg = supplyDao.querySupplyImages(suppDetail.getId().toString());
			if(suppImg.size() > 0){
				for(int i=0;i<suppImg.size();i++){
					SupplyImages sImg = new SupplyImages();
					sImg = suppImg.get(i);
					sImg.setImgName(sImg.getName());
					sImg.setName(imgServerAddress+sImg.getName());
					
					img.add(sImg);
					if(StringUtils.isNotBlank(imgAddr)){
						imgAddr +=",";
					}
					imgAddr += sImg.getImgName();
				}
			}
		}
		modelView.addObject("imgAddr", imgAddr);
		modelView.addObject("suppImg", img);
		modelView.addObject("priceExplainList", priceExplainList);
		modelView.addObject("listDel", listDel);
		modelView.addObject("supp", suppDetail);
		modelView.addObject("item", item);
		modelView.addObject("areas", areas);
		modelView.addObject("status", status);
		return modelView;
	}
	
	/**
	 * 供货单详情
	 * */
	@RequestMapping(value="/supplyDetail")
	public ModelAndView supplyDetail(String id){
		ModelAndView modelView = new ModelAndView("/supply/supplyDetail");
		//通过ID查询详情
		SupplyListView suppDetail = supplybo.supplyDetailById(id);
		List<SupplyImages> img = new ArrayList<SupplyImages>();
		//查询供货单图片
		if(suppDetail != null && suppDetail.getId() != null && !suppDetail.getId().equals("")){
			List<SupplyImages> suppImg = supplyDao.querySupplyImages(suppDetail.getId().toString());
			if(suppImg.size() > 0){
				for(int i=0;i<suppImg.size();i++){
					SupplyImages sImg = new SupplyImages();
					sImg = suppImg.get(i);
					sImg.setName(imgServerAddress+sImg.getName());
					img.add(sImg);
				}
			}
		}
		modelView.addObject("suppDetail", suppDetail);
		modelView.addObject("suppImg", img);
		return modelView;
	}
	
	/**
	 * 修改供货单
	 * */
	@RequestMapping(value="/updateSupply")
	@ResponseBody
	public Map<String, Object> updateSupply(HttpServletRequest request){
		Map<String, Object> map = new HashMap<>();
		int insertsupp = supplybo.updateSupply(request);
		if(insertsupp == 1){
			map.put("result", "修改成功!");
			map.put("success", insertsupp);
			map.put("errorcode", "");
			map.put("unAuthorizedRequest", false);
		}else{
			map.put("result", "修改失败!");
			map.put("success", insertsupp);
			map.put("errorcode", 1002);
			map.put("unAuthorizedRequest", false);
		}
		return map;
	}
	
//	/**
//	 * 供货单详情(接口)
//	 * */
//	@RequestMapping(value="/supplyDetail")
//	@ResponseBody
//	public ResultData supplyDetail(HttpServletRequest request){
//		logger.info("-----------供货单详情(接口)-----------");
//		ResultData data = new ResultData();
//        String paramsStr = request.getParameter("paramsStr");
//        try {
//            if (!StringUtils.isNotBlank(paramsStr)) {
//                data.setSuccess(false);
//                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
//                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
//                data.setUnAuthorizedRequest(false);
//                return data;
//            }
//            data = supplybo.supplyDetailByIdAPI(paramsStr);
//        } catch (Exception e) {
//            logger.error("customerCancel系统错误", e);
//            data.setSuccess(false);
//            data.setErrorcode(ResErrorCode.ERROR_CODE);
//            data.setErrMsg(ResErrorCode.ERROR_MSG);
//        }
//        return data;
//	}
	
	/**
	 * 
	 * @param request
	 * @return
	 * 
	 * 	批量更新,删除
	 */
    @RequestMapping("/updateById")
    @ResponseBody
    public String updateById(HttpServletRequest request) {
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        DateUtil date = new DateUtil();
        date.currentDate();
        String sid[] = id.split(",");
        try {
            if (StringUtils.isNotBlank(id)) {
                id = id.substring(0, id.length() - 1);
                if(type != null && type.equals("1")){
                	for(int i=0;i<sid.length;i++){
                		String status = "";
                		SupplyListView supp = supplybo.supplyDetailById(sid[i]);
                		if(supp != null){
                			if(supp.getLastInfoStatus() != null && !supp.getLastInfoStatus().equals("")){
//            					status = supp.getLastInfoStatus().toString(); 批量更新时，不更新状态了 2016-4-14
            					supplybo.updateSupplyById(sid[i],status,date.currentDate());
                			}else{
                				supplybo.updateSupplyById(sid[i],status,date.currentDate());
                			}
                		}
                    }
                	
                	
                }else if(type != null && type.equals("2")){
                	supplybo.deleteSupplyById(id);
                }
                rtnMap.put("result", "success");
            } else {
                rtnMap.put("result", "error");
            }
            return JSONUtil.map2json(rtnMap);
        } catch (Exception e) {
            logger.error("系统错误", e);
        }
        return null;
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws Exception
     * 	导出excel
     */
    @RequestMapping("/poolExportDel")
    public void poolExportDel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String type = request.getParameter("type");//查询类型
        String recommend = request.getParameter("recommend");//内容
        String prov = request.getParameter("prov");//省
        String city = request.getParameter("city");//市
        String attribute = request.getParameter("attribute");//时间类型
        String startTime = request.getParameter("startTime");//开始时间
        String endTime = request.getParameter("endTime");//结束时间
        String itemid = request.getParameter("itemid");//品目类型
        String checks = request.getParameter("checks");//选中的订单
        String status = request.getParameter("status");//选中列表的状态
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("type", type);
            param.put("recommend", recommend);
            param.put("prov", prov);
            param.put("city", city);
            param.put("attribute", attribute);
            param.put("startTime", startTime);
            param.put("endTime", endTime);
            param.put("itemid", itemid);
            param.put("checks", checks);
            param.put("status", status);

            List<SupplyListView> supplist = supplybo.newExcleSupplyList(param);
            ExportUtil<SupplyListView> excel = new ExportUtil<SupplyListView>();

            String fileName = "供货单列表";
            for (int i = 0; i < supplist.size(); i++) {
				if(supplist.get(i)!=null&&supplist.get(i).getExpiryType()!=null&&supplist.get(i).getExpiryType()==0){
					supplist.get(i).setExpiryTypeName("一周");
				}
				if(supplist.get(i)!=null&&supplist.get(i).getExpiryType()!=null&&supplist.get(i).getExpiryType()==1){
					supplist.get(i).setExpiryTypeName("一月");
				}
				if (StringUtils.isNotBlank(supplist.get(i).getCityName())) {
					if(supplist.get(i).getCityName().equals(supplist.get(i).getProyName())){
						supplist.get(i).setProyName(supplist.get(i).getProyName());
					}else{
						supplist.get(i).setProyName(supplist.get(i).getProyName() + "/" + supplist.get(i).getCityName());
					}
	            } else {
	            	supplist.get(i).setProyName(supplist.get(i).getProyName());
	            }
				if(supplist.get(i)!=null && supplist.get(i).getIsCreatedByCustomer() != null && supplist.get(i).getIsCreatedByCustomer() == 0){
					supplist.get(i).setExportName(supplist.get(i).getUname());
				}else{
					if(StringUtils.isNotBlank(supplist.get(i).getCustomerName())){
						supplist.get(i).setExportName(supplist.get(i).getCustomerName());
					}else{
						supplist.get(i).setExportName(supplist.get(i).getNickName());
					}
				}
				if(supplist.get(i)!=null && supplist.get(i).getPriceNegotiable() != null && supplist.get(i).getPriceNegotiable() == 1){
					supplist.get(i).setExportPrice("面议");
				}else{
					if(supplist.get(i).getPrice() != null){
						supplist.get(i).setExportPrice(supplist.get(i).getPrice().toString());
					}else{
						supplist.get(i).setExportPrice("0");
					}
				}
			}

            String[] headerNames = new String[] { "手机", "分类", "信息标题", "货物所在地", "数量", "价格", "信息有效期", "创建人","更新时间" };
            String[] header = new String[] { "phone", "name", "infoTitle", "proyName", "usableQuantity", "exportPrice",
            		"expiryTypeName", "exportName","updatedAt"};
            String[] comments = new String[] { null, null, null, null, null, null, null, null,null};
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            excel.export("sheet1", headerNames, header, comments, supplist, os, "");
            //excel.export("sheet1", headerNames, header, comments, customerList, os, "");

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
        } catch (final IOException e) {
            logger.error("系统错误", e);
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
    } 
    
    /**
     * 	上传图片
     * @param filePath
     * @return
     * @throws Exception
     */
    @RequestMapping("/uploadmethod")
    @ResponseBody
    public String uploadFile(@RequestParam(value="file", required = true) MultipartFile imageFile) throws Exception {
    	Map<String,Object> resultmap = new HashMap<String, Object>();
        // 上传文件请求路径
        URL url = new URL(sendUrl);
        System.out.println(imageFile.getName());
        if (imageFile.isEmpty()) {
            resultmap.put("code", "上传的文件不存在");
            return JSONUtil.map2json(resultmap);
        }
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存
        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        // 请求正文信息
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // 必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + imageFile.getOriginalFilename() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);
        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(imageFile.getInputStream());
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            logger.error("发送POST请求出现异常！" + e);
            e.printStackTrace();
            throw new IOException("数据读取异常");
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        ObjectMapper om = new ObjectMapper();
        @SuppressWarnings("unchecked")
		Map<String, Object> map = om.readValue(buffer.toString(), Map.class);
        map.put("imgServerAddress", imgServerAddress);
        
        System.out.println("返回="+map.toString());
        return om.writeValueAsString(map);
    }
}