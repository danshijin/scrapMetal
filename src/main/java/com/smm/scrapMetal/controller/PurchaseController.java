package com.smm.scrapMetal.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.scrapMetal.bo.IAreasBO;
import com.smm.scrapMetal.bo.IItemBo;
import com.smm.scrapMetal.bo.PriceExplainBo;
import com.smm.scrapMetal.bo.PurchaseBo;
import com.smm.scrapMetal.domain.Areas;
import com.smm.scrapMetal.domain.Customer;
import com.smm.scrapMetal.domain.Item;
import com.smm.scrapMetal.domain.PageBean;
import com.smm.scrapMetal.domain.PriceExplain;
import com.smm.scrapMetal.domain.PurchaseDomain;
import com.smm.scrapMetal.domain.User;
import com.smm.scrapMetal.util.ExportUtil;

/**
 * 采购单
 * @author tantaigen
 *
 */
@Controller
@RequestMapping(value="Purchase")
public class PurchaseController {
	@Resource
	private PurchaseBo purchaseBo;
	@Resource
	private IItemBo ItemBo;
	@Resource IAreasBO IAreasBO;
	@Resource PriceExplainBo PriceExplainBo;
	/**
	 * 采购单列表
	 * @return
	 */
	@RequestMapping(value="list")
	public ModelAndView queryPurchaseList( HttpServletRequest request,Integer pno){
		ModelAndView view=new ModelAndView("purchase/purchaseList");
		if (pno == null) {
			pno = 1;
		}
		Map<String, Object> map=new HashMap<String, Object>();
		
		String itemId=request.getParameter("itemId");//
		String nameType=request.getParameter("nameType");//搜索类型
		String liekname=request.getParameter("liekname");//搜索类容
		String goodsProv=request.getParameter("goodsProv");//省
		String goodsCity=request.getParameter("goodsCity");//市
		String dateType=request.getParameter("dateType");//时间
		String startDate=request.getParameter("startDate");//开始时间
		String endDate=request.getParameter("endDate");//开始时间
		view.addObject("endDate", endDate);
		if(endDate!=null &&!"".equals(endDate)){
			endDate=endDate+" 23:59:59";
		}
		String status=request.getParameter("status");
		if(status!=null&&!"".equals(status)&&Integer.valueOf(status)!=-1){
			map.put("infoStatus", status);
		}
		map.put("itemId", itemId);
		map.put("nameType", nameType);
		map.put("liekname", liekname);
		map.put("goodsProv", goodsProv);
		map.put("goodsCity", goodsCity);
		map.put("dateType", dateType);
		map.put("startDate", startDate);
		map.put("endDate", endDate);		
		List<PurchaseDomain> purchas=purchaseBo.queryPurchase(map);
		int count=purchas.size();
		PageBean page = new PageBean(10, pno, count);
		int startNum = page.getStartNum();
		int endNum = page.getEndNum();
		List<Item> itemList=ItemBo.showItemList();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		List<PurchaseDomain> purchasList=purchaseBo.queryPurchase(map);
		view.addObject("purchasList", purchasList);
		view.addObject("totalRecords", count);// 总条数
		view.addObject("totalPage", page.getTotalPages());// 总页数
		List<Areas> areasList=IAreasBO.getParentAreas();
		view.addObject("areasList", areasList);
		view.addObject("nameType", nameType);
		view.addObject("liekname", liekname);
		view.addObject("goodsProv", goodsProv);
		view.addObject("goodsCity", goodsCity);
		view.addObject("dateType", dateType);
		view.addObject("startDate", startDate);
		view.addObject("itemList", itemList);
		view.addObject("itemId", itemId);
		if(status==null || "".equals(status)){
			status="-1";
		}
		view.addObject("status", status);
		
		
		
		return view;
	}
	
	/**
	 * 准备新建数据
	 * @return
	 */
	@RequestMapping(value="toAddPurchas")
	public 	ModelAndView toAddPurchas(){
		ModelAndView view=new ModelAndView("purchase/addPurchase");
		List<Item> itemList=ItemBo.showItemList();
		List<Areas> areasList=IAreasBO.getParentAreas();
		List<PriceExplain> priceExplainList=PriceExplainBo.queryHomePriceExplain();
		view.addObject("itemList", itemList);
		view.addObject("areasList", areasList);
		view.addObject("priceExplainList", priceExplainList);
		return view;
	}
	
	/**
	 * 获取市
	 * @param request
	 * @return
	 */
	@RequestMapping(value="getChildAreas")
	@ResponseBody
	public List<Areas> getChildArea(HttpServletRequest request){
		List<Areas> list = null;
		String parentId = request.getParameter("parentId");
		if(StringUtils.isNotBlank(parentId)){
			list = IAreasBO.getChildArea(parentId);
			if(list!=null&&list.size()==0){
				list = null;
			}
		}
		return list;
	} 
	
	
	/**
	 * 获取国外价格说明
	 * @param request
	 * @return
	 */
	@RequestMapping(value="priceExplain")
	@ResponseBody
	public List<PriceExplain> priceExplain(HttpServletRequest request){
		String flag=request.getParameter("flag");
		List<PriceExplain> list = null;	
		if(flag!=null&&Integer.valueOf(flag)==2){
			list = PriceExplainBo.queryNotHomePriceExplain();
		}else {
			list = PriceExplainBo.queryHomePriceExplain();
		}
			
			
			if(list!=null&&list.size()==0){
				list = null;
			
		}
		return list;
	} 
	
	
	/**
	 * 新建采购单
	 * @param purchase
	 * @return
	 */
	@RequestMapping(value="savePurchase")
	@ResponseBody
	public  Map<String, Object> savePurchase(HttpServletRequest request){
		Map<String, Object> map=new HashMap<>();
		 PurchaseDomain purchase =new PurchaseDomain();
		 
		 User user=(User)request.getSession().getAttribute("userInfo");
		 
		 try {
			
		
		 if(request.getParameter("itemId")!=null&&!"".equals(request.getParameter("itemId"))){
		 purchase.setItemId(Integer.valueOf(request.getParameter("itemId")));
		 }
		 if(request.getParameter("goodsProv")!=null&&!"".equals(request.getParameter("goodsProv"))){
		 purchase.setGoodsProv(Integer.valueOf(request.getParameter("goodsProv")));
		 }
		 if(request.getParameter("goodsCity")!=null&&!"".equals(request.getParameter("goodsCity"))){
		 purchase.setGoodsCity(Integer.valueOf(request.getParameter("goodsCity")));
		 }else{
			 purchase.setGoodsCity(purchase.getGoodsProv());
		 }
		 purchase.setInfoTitle(request.getParameter("infoTitle"));
		 purchase.setDescription(request.getParameter("description"));
		 if(request.getParameter("quantity")!=null&&!"".equals(request.getParameter("quantity"))){
		 purchase.setQuantity(BigDecimal.valueOf(Double.valueOf(request.getParameter("quantity"))));
		 }
		 if(request.getParameter("expectPrice")!=null&&!"".equals(request.getParameter("expectPrice"))){
		 purchase.setExpectPrice(BigDecimal.valueOf(Double.valueOf(request.getParameter("expectPrice"))));
		 }
		 if(request.getParameter("expectPriceUnit")!=null&&!"".equals(request.getParameter("expectPriceUnit"))){
			 purchase.setExpectPriceUnit(Integer.valueOf(request.getParameter("expectPriceUnit")));
			 }
		
		 if(request.getParameter("priceNegotiable")!=null&&!"".equals(request.getParameter("priceNegotiable"))){
		 purchase.setPriceNegotiable(Integer.valueOf(request.getParameter("priceNegotiable")));
		 }else{
			 purchase.setPriceNegotiable(0); 
		 }
		 if(request.getParameter("priceExplain")!=null&&!"".equals(request.getParameter("priceExplain"))){
		 purchase.setPriceExplain(Integer.valueOf(request.getParameter("priceExplain")));
		 }
		 if(request.getParameter("expiryType")!=null&&!"".equals(request.getParameter("expiryType"))){
		 purchase.setExpiryType(Integer.valueOf(request.getParameter("expiryType")));
		 }
		 if(request.getParameter("phone")==null||"".equals(request.getParameter("phone"))){
			 	map.put("code", "error");
				map.put("msg", "电话号码不能为空！"); 
				return map;
		 }
		 Customer customer=purchaseBo.queryCustomer(request.getParameter("phone"));
		 if(customer==null){
			 	map.put("code", "error");
				map.put("msg", "该手机号码客户不存在！"); 
				return map; 
		 }
		 purchase.setUnit(0);
		 purchase.setCustomerId(customer.getId());
		 purchase.setPhone(request.getParameter("phone"));
		 purchase.setIsCreatedByCustomer(0);
		 purchase.setIsUpdatedByCustomer(0);
		 if(user!=null){
		 purchase.setCreatedBy(user.getId());
		 purchase.setUpdatedBy(user.getId());
		 }else {
			 map.put("code", "error");
			map.put("msg", "请登录！");
			return map;	
		}
		 purchase.setInfoStatus(1);
		 int a =purchaseBo.addPurchase(purchase);
			if(a==0){
				map.put("code", "error");
				map.put("msg", "添加失败");
				
			}else{
				map.put("code", "success");
				map.put("msg", "添加成功");
			}
		 } catch (Exception e) {
				e.printStackTrace();
				map.put("code", "error");
				map.put("msg", "异常,数量和价格整数位不能超过11位");
				return map;
			}
		
		return map;
	}
	/**
	 * 准备修改数据
	 * @return
	 */
	@RequestMapping(value="toUpdatePurchas")
	public 	ModelAndView toupdatePurchas(HttpServletRequest request){
		int id=Integer.valueOf(request.getParameter("id"));
		ModelAndView view=new ModelAndView("purchase/updatePurchase");
		List<Item> itemList=ItemBo.showItemList();
		List<Areas> areasList=IAreasBO.getParentAreas();
		PurchaseDomain purchase=purchaseBo.queryPurchaseById(id);
		List<PriceExplain> priceExplainList=PriceExplainBo.queryHomePriceExplain();
		if(purchase!=null&&purchase.getGoodsProv()==392){
			priceExplainList=PriceExplainBo.queryNotHomePriceExplain();
		}
		view.addObject("purchase", purchase);		
		view.addObject("itemList", itemList);
		view.addObject("areasList", areasList);
		view.addObject("priceExplainList", priceExplainList);
		return view;
	}
	
	/**
	 * 修改采购单
	 * @param purchase
	 * @return
	 */
	@RequestMapping(value="updatePurchase")
	@ResponseBody
	public  Map<String, Object> updatePurchase(HttpServletRequest request){
		
		Map<String, Object> map=new HashMap<>();
		try {
					
		if(request.getParameter("id")==null||request.getParameter("id").equals("")){
			map.put("code", "error");
			map.put("msg", "修改ID为null!");
			return map;
		}
		Integer id=Integer.valueOf(request.getParameter("id"));
		if(id==null||id==0){
			map.put("code", "error");
			map.put("msg", "你所修改的数据不存在!");
			return map;
		}
		 PurchaseDomain purchase =purchaseBo.queryPurchaseById(id);
		 if(request.getParameter("itemId")!=null&&!"".equals(request.getParameter("itemId"))){
		 purchase.setItemId(Integer.valueOf(request.getParameter("itemId")));
		 }
		 if(request.getParameter("goodsProv")!=null&&!"".equals(request.getParameter("goodsProv"))){
		 purchase.setGoodsProv(Integer.valueOf(request.getParameter("goodsProv")));
		 }
		 if(request.getParameter("goodsCity")!=null&&!"".equals(request.getParameter("goodsCity"))){
		 purchase.setGoodsCity(Integer.valueOf(request.getParameter("goodsCity")));
		 }else{
			 purchase.setGoodsCity(purchase.getGoodsProv());
		 }
		 purchase.setInfoTitle(request.getParameter("infoTitle"));
		 purchase.setDescription(request.getParameter("description"));
		 if(request.getParameter("quantity")!=null&&!"".equals(request.getParameter("quantity"))){
		 purchase.setQuantity(BigDecimal.valueOf(Double.valueOf(request.getParameter("quantity"))));
		 }
		 if(request.getParameter("expectPrice")!=null&&!"".equals(request.getParameter("expectPrice"))){
		 purchase.setExpectPrice(BigDecimal.valueOf(Double.valueOf(request.getParameter("expectPrice"))));
		 }
		 if(request.getParameter("expectPriceUnit")!=null&&!"".equals(request.getParameter("expectPriceUnit"))){
			 purchase.setExpectPriceUnit(Integer.valueOf(request.getParameter("expectPriceUnit")));
		 }else{
			 purchase.setExpectPriceUnit(0);
		 }
		 if(request.getParameter("priceNegotiable")!=null&&!"".equals(request.getParameter("priceNegotiable"))){
		 purchase.setPriceNegotiable(Integer.valueOf(request.getParameter("priceNegotiable")));
		 }else{
			 purchase.setPriceNegotiable(0); 
		 }
		 if(request.getParameter("priceExplain")!=null&&!"".equals(request.getParameter("priceExplain"))){
		 purchase.setPriceExplain(Integer.valueOf(request.getParameter("priceExplain")));
		 }
		 if(request.getParameter("expiryType")!=null&&!"".equals(request.getParameter("expiryType"))){
		 purchase.setExpiryType(Integer.valueOf(request.getParameter("expiryType")));
		 }
		 if(request.getParameter("infoStatus")!=null&&!"".equals(request.getParameter("infoStatus"))){
			 Integer infoStatus = Integer.valueOf(request.getParameter("infoStatus"));
			 if(infoStatus.equals(3)){
					if(purchase != null && purchase.getInfoStatus() != null){
						purchase.setLastInfoStatus(purchase.getInfoStatus());
					}
				}
			 purchase.setInfoStatus(Integer.valueOf(request.getParameter("infoStatus")));
		 }
		 if(request.getParameter("auditedComment") != null && !request.getParameter("auditedComment").equals("")){
			 purchase.setAuditedComment(request.getParameter("auditedComment"));
		 }
		 purchase.setUnit(0);
		 purchase.setUpdatedAt(new Date());
		 purchase.setAuditedAt(new Date());
		 purchase.setIsUpdatedByCustomer(0);
		 purchase.setUpdatedAt(new Date());
		 User user=(User)request.getSession().getAttribute("userInfo");
		 if(user!=null){
		 purchase.setUpdatedBy(user.getId());
		 }
		int a =purchaseBo.updatePurchase(purchase);
		if(a==0){
			map.put("code", "error");
			map.put("msg", "修改失败");
			
		}else{
			map.put("code", "success");
			map.put("msg", "修改成功");
		} } catch (Exception e) {
			e.printStackTrace();
			map.put("code", "error");
			map.put("msg", "异常,数量和价格整数位不能超过11位");
			return map;
		}
		
		return map;
	}
	
	/**
	 * 删除采购单
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="delPurchase")
	@ResponseBody
	public  Map<String, Object> delPurchase(String ids){
		Map<String, Object> map=new HashMap<>();
		String[] id = null;

		if (ids != null && !ids.equals("")) {
			id = ids.split(",");

		}
		int a=purchaseBo.delPurchase(id);
		if(a>0){
		map.put("code", "succ");
		map.put("msg", "系统提示，操作成功");
		}else{
			map.put("code", "err");
			map.put("msg", "系统提示，删除失败");	
		}

		return  map;
	}
	
	
	/**
	 * 批量更新
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="batchUpdatePurchase")
	@ResponseBody
	public  Map<String, Object> batchUpdatePurchase(String ids){
		Map<String, Object> map=new HashMap<>();
		String[] id = null;
		int a = 0;
		if (ids != null && !ids.equals("")) {
			id = ids.split(",");
			for(int i=0;i<id.length;i++){
				String status = "";
				PurchaseDomain pur = purchaseBo.queryPurchaseById(Integer.valueOf(id[i]));
				if(pur != null){
					if(pur.getLastInfoStatus() != null && !pur.getLastInfoStatus().equals("")){
//						status = pur.getLastInfoStatus().toString();   批量更新时，不更新状态了 2016-4-14
						Map<String ,Object> map1 =new HashMap<>();
						map1.put("array", id[i]);
						map1.put("status", status);
						map1.put("updatedAt", new Date());
						a=purchaseBo.batchUpdatePurchase(map1);
					}else{
						Map<String ,Object> map1 =new HashMap<>();
						map1.put("array", id[i]);
						map1.put("status", status);
						map1.put("updatedAt", new Date());
						a=purchaseBo.batchUpdatePurchase(map1);
					}
				}
			}
			
		}
		
		if(a>0){
		map.put("code", "succ");
		map.put("msg", "系统提示，操作成功");
		}else{
			map.put("code", "err");
			map.put("msg", "系统提示,更新失败");	
		}

		return  map;
	}
	
	
	
	
	/**
	 * 导出
	 * @param ids
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="ExporPurchase")
	public void ExporPurchase(String ids ,HttpServletRequest request,HttpServletResponse response) throws IOException{
		String[] id = null;
		if (ids != null && !ids.equals("")) {
			id = ids.split(",");

		}
		Map<String, Object> map=new HashMap<>();
		String nameType=request.getParameter("nameType");//搜索类型
		String liekname=request.getParameter("liekname");//搜索类容
		String goodsProv=request.getParameter("goodsProv");//省
		String goodsCity=request.getParameter("goodsCity");//市
		String dateType=request.getParameter("dateType");//时间
		String startDate=request.getParameter("startDate");//开始时间
		String endDate=request.getParameter("endDate");//开始时间		
		if(endDate!=null &&!"".equals(endDate)){
			endDate=endDate+" 23:59:59";
		}
		String status=request.getParameter("status");
		if(status!=null&&!"".equals(status)&&Integer.valueOf(status)!=-1){
			map.put("infoStatus", status);
		}
		map.put("array", id);
		map.put("nameType", nameType);
		map.put("liekname", liekname);
		map.put("goodsProv", goodsProv);
		map.put("goodsCity", goodsCity);
		map.put("dateType", dateType);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		List<PurchaseDomain> list=purchaseBo.ExporQueryPurchaseByIds(map);
		if(list!=null){
			ExportUtil<PurchaseDomain> excel = new ExportUtil<PurchaseDomain>();	
 			String fileName = "采购单";
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i)!=null&&list.get(i).getExpiryType()!=null&&list.get(i).getExpiryType()==0){
					list.get(i).setExpiryTypeName("一周");
				}
				if(list.get(i)!=null&&list.get(i).getExpiryType()!=null&&list.get(i).getExpiryType()==1){
					list.get(i).setExpiryTypeName("一月");
				}
			}

			String[] headerNames = new String[] { "手机", "分类", "信息标题", "货物所在地", "数量", "期望价格","价格说明", "有效期", "创建人" ,"更新时间"};
			String[] header = new String[] { "phone", "itemName", "infoTitle", "cityName", "quantityName", "expectPriceName","priceExplainName",
					"expiryTypeName", "createdByName" ,"updatedAt"};
			String[] comments = new String[] { null, null, null, null, null, null, null, null, null, null, null, null,null };
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			excel.export("sheet1", headerNames, header, comments, list, os, "");
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			// 设置response参数，可以打开下载页面
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String((fileName + ".xls").getBytes("GBK"), "iso-8859-1"));
			ServletOutputStream out = response.getOutputStream();
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
				bis = new BufferedInputStream(is);
				bos = new BufferedOutputStream(out);
				byte[] buff = new byte[2048];
				int bytesRead;
				// Simple read/write loop.
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
			} catch (final IOException e) {
				throw e;
			} finally {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			}
		}
	}
	/**
	 * 采购详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value="purchasDetail")
	public 	ModelAndView purchasDetail(HttpServletRequest request){
		int id=Integer.valueOf(request.getParameter("id"));
		ModelAndView view=new ModelAndView("purchase/purchaseDetail");

		PurchaseDomain purchase=purchaseBo.queryPurchaseById(id);
		
		view.addObject("purchase", purchase);		
			return view;
	}
	
	
	
}
