package com.smm.scrapMetal.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smm.scrapMetal.bo.IAreasBO;
import com.smm.scrapMetal.bo.IItemBo;
import com.smm.scrapMetal.bo.IOrderBO;
import com.smm.scrapMetal.bo.PriceExplainBo;
import com.smm.scrapMetal.bo.PurchaseBo;
import com.smm.scrapMetal.bo.SupplyBO;
import com.smm.scrapMetal.domain.Areas;
import com.smm.scrapMetal.domain.Customer;
import com.smm.scrapMetal.domain.Item;
import com.smm.scrapMetal.domain.Order;
import com.smm.scrapMetal.domain.PageBean;
import com.smm.scrapMetal.domain.PriceExplain;
import com.smm.scrapMetal.domain.Supply;
import com.smm.scrapMetal.domain.User;
import com.smm.scrapMetal.util.ExportUtil;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Resource
	private IOrderBO orderBO;
	@Resource
	private IAreasBO areasBO;
	@Resource
	private PriceExplainBo priceExplainBo;
	@Resource
	private IItemBo itemBo;
	@Resource
	private PurchaseBo purchaseBo;
	@Resource
	private SupplyBO supplyBO;
	
	//查询订单列表
	@RequestMapping("query")
	public ModelAndView query(HttpServletRequest request,Integer pno,String orderStatus){
		ModelAndView view = new ModelAndView("/order/orderList");
		Map<String,Object> map = new HashMap<String, Object>();
		if (pno == null) {
			pno = 1;
		}
		if(orderStatus==null){
			orderStatus="-1";//默认查全部
		}
		String attribute = request.getParameter("attribute");
		String content = request.getParameter("content");
		String goodsProv = request.getParameter("goodsProv");
		String goodsCity = request.getParameter("goodsCity");
		String dates = request.getParameter("dates");
		String statDate = request.getParameter("statDate");
		String endDate = request.getParameter("endDate");
		String itemId = request.getParameter("itemId");
		
		map.put("attribute",attribute);
		map.put("content",content);
		map.put("goodsProv",goodsProv);
		map.put("goodsCity",goodsCity);
		map.put("dates",dates);
		map.put("statDate",statDate);
		if(endDate!=null &&!"".equals(endDate)){
			map.put("endDate",endDate+" 23:59:59");
		}
		map.put("itemId",itemId);
		map.put("orderStatus", orderStatus);
		
		List<Areas> areas =  areasBO.getParentAreas();
		List<Item> items =itemBo.showItemList();
		List<Order> listCount = orderBO.query(map);
		int count=listCount.size();
		PageBean page = new PageBean(10, pno, count);
		int startNum = page.getStartNum();
		int endNum = page.getEndNum();

		map.put("startNum", startNum);
		map.put("endNum", endNum);
		List<Order> list = orderBO.query(map);
		
		view.addObject("list",list);
		view.addObject("totalRecords", count);// 总条数
		view.addObject("totalPage", page.getTotalPages());// 总页数
		view.addObject("areas",areas);
		view.addObject("items",items);
		view.addObject("attribute",attribute);
		view.addObject("content",content);
		view.addObject("goodsProv",goodsProv);
		view.addObject("goodsCity",goodsCity);
		view.addObject("dates",dates);
		view.addObject("statDate",statDate);
		view.addObject("endDate",endDate);
		view.addObject("itemId",itemId);
		view.addObject("orderStatus",orderStatus);
		return view;
	}
	
	//修改订单状态
	@RequestMapping("updateOrderStatus")
	@ResponseBody
	public Map<String, Object> updateOrderStatus(Integer orderId,Integer orderStatus){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("orderId",orderId);
		map.put("orderStatus", orderStatus);
		Integer count =orderBO.updateOrderStatus(map);
		Map<String,Object> result = new HashMap<String, Object>();
		if(count!=null&&count>0){
			result.put("code", "succ");
			result.put("msg", "操作成功!");
		}else{
			result.put("code", "error");
			result.put("msg", "操作失败!");
		}
		return result;
	}
	
	//查看订单详情
	@RequestMapping("queryOrderInfo")
	public ModelAndView queryOrderInfo(Integer id){
		ModelAndView view = new ModelAndView("/order/orderDetail");
		Order order = orderBO.queryOrderInfo(id);
		view.addObject("order", order);
		BigDecimal bd = new BigDecimal(order.getQuantity() * order.getPrice());
		view.addObject("totalSumMoney", bd.setScale(2, BigDecimal.ROUND_HALF_UP));
		return view;
	}
	
	//从采购单进入生成订单
	@RequestMapping("toAddOrderByPurchase")
	public ModelAndView toAddOrderByPurchase(Integer id){
		ModelAndView view = new ModelAndView("/order/addOrder");
		List<Areas> areas =  areasBO.getParentAreas();//地区列表
		List<PriceExplain> priceExplainList=priceExplainBo.queryHomePriceExplain();
	 	Order mol =  orderBO.purchaseToAddOrderInfoById(id);
	 	mol.setSource(2);//来源  1 供货 2 采购  3客户
	 	mol.setSourceId(id);//来源id
	 	
	 	view .addObject("mol",mol);
	 	view.addObject("areas", areas);
	 	view.addObject("priceExplainList", priceExplainList);
		return view;
	}
	
	//从供应单进入生成订单
	@RequestMapping("toAddOrderBySupply")
	public ModelAndView toAddOrderBySupply(Integer id){
		ModelAndView view = new ModelAndView("/order/addOrder");
		List<Areas> areas =  areasBO.getParentAreas();//地区列表
		List<PriceExplain> priceExplainList=priceExplainBo.queryHomePriceExplain();
		Order mol =  orderBO.supplyToAddOrderInfoById(id);
		mol.setSource(1);//来源  1 供货 2 采购  3客户
	 	mol.setSourceId(id);//来源id
	 	
		view .addObject("mol",mol);
		view.addObject("areas", areas);
	 	view.addObject("priceExplainList", priceExplainList);
		return view;
	}
	
	//从采购客户生成订单
	@RequestMapping("toAddOrderByPurCustomer")
	public ModelAndView toAddOrderByPurCustomer(Integer id){
		ModelAndView view = new ModelAndView("/order/addOrder");
		List<Areas> areas =  areasBO.getParentAreas();//地区列表
		List<PriceExplain> priceExplainList=priceExplainBo.queryHomePriceExplain();
		Order mol =  orderBO.purCustomerToAddOrderInfoById(id);
		mol.setSource(3);//来源  1 供货 2 采购  3客户
	 	mol.setSourceId(id);//来源id
	 	
	 	view .addObject("mol",mol);
		view.addObject("areas", areas);
	 	view.addObject("priceExplainList", priceExplainList);
		return view;
	}
	
	//从供应客户生成订单
	@RequestMapping("toAddOrderBySupCustomer")
	public ModelAndView toAddOrderBySupCustomer(Integer id){
		ModelAndView view = new ModelAndView("/order/addOrder");
		List<Areas> areas =  areasBO.getParentAreas();//地区列表
		List<PriceExplain> priceExplainList=priceExplainBo.queryHomePriceExplain();
		Order mol =  orderBO.SupCustomerToAddOrderInfoById(id);
		mol.setSource(3);//来源  1 供货 2 采购  3客户
	 	mol.setSourceId(id);//来源id
	 	
	 	view .addObject("mol",mol);
		view.addObject("areas", areas);
	 	view.addObject("priceExplainList", priceExplainList);
		return view;
	}
	
	
	//生成订单
	@RequestMapping("addOrder")
	@ResponseBody
	public Map<String, Object> addOrder(HttpServletRequest request){
		String goodsProv =request.getParameter("goodsProv");
		String goodsCity = request.getParameter("goodsCity");
		String quantity = request.getParameter("quantity");
		String price = request.getParameter("price");
		String priceU = request.getParameter("price1");//价格类型(0 人民币 1 美元)
		String sourceId = request.getParameter("sourceId");
		String source = request.getParameter("source");
		String sellerPhone = request.getParameter("sellerPhone");
		String buyerPhone = request.getParameter("buyerPhone");
		String sellerContacter = request.getParameter("sellerContacter");
		String buyerContacter = request.getParameter("buyerContacter");
		String sellerCompanyName = request.getParameter("sellerCompanyName");
		String buyerCompanyName = request.getParameter("buyerCompanyName");
		String priceExplain = request.getParameter("priceExplain");
		String delivery = request.getParameter("delivery");
		String sellerId = request.getParameter("sellerId");
		String buyerId = request.getParameter("buyerId");
		String quantityUnit = request.getParameter("quantityUnit");
		String priceUnit = request.getParameter("priceUnit");
		
		User user = (User) request.getSession().getAttribute("userInfo");
		if(source.equals("1")){//从供货生成  要减库存、加冻结数量
			Supply s = new Supply();
			s.setId(Integer.valueOf(sourceId));
			s.setFrozenQuantity(new BigDecimal(quantity));
			orderBO.updateFrozenQuantity(s);
		}
		Order o = new Order();
		if(source.equals("3")){//客户生成,可以填写信息标题
			String infoTitle = request.getParameter("infoTitle");
			o.setInfoTitle(infoTitle);
		}
		if(goodsProv != null && !goodsProv.equals("")){
			o.setGoodsProv(Integer.parseInt(goodsProv));
		}
		if(goodsCity != null && !goodsCity.equals("")){
			o.setGoodsCity(Integer.parseInt(goodsCity));
		}
		
		o.setQuantity(Double.parseDouble(quantity));
		o.setPrice(Double.parseDouble(price));
		o.setSource(Integer.parseInt(source));
		o.setSourceId(Integer.parseInt(sourceId));
		o.setSellerPhone(sellerPhone);
		o.setSellerContacter(sellerContacter);
		o.setSellerCompanyName(sellerCompanyName);
		o.setBuyerPhone(buyerPhone);
		o.setBuyerContacter(buyerContacter);
		o.setBuyerCompanyName(buyerCompanyName);
		o.setPriceExplain(Integer.parseInt(priceExplain));
		o.setDelivery(Integer.parseInt(delivery));
		o.setQuantityUnit(Integer.valueOf(quantityUnit));
		o.setPriceUnit(Integer.valueOf(priceU));
		o.setCreatedAt(new Date());
		o.setCreatedBy(user.getId());
		o.setUpdatedBy(user.getId());
		o.setSellerId(Integer.valueOf(sellerId));
		o.setBuyerId(Integer.valueOf(buyerId));
		
		Integer sta = orderBO.addOrder(o);
		SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMdd");
		o.setOrderNo(sdf.format(new Date())+o.getId());
		Integer stat = orderBO.insOrderNoById(o);
		Map<String,Object> result = new HashMap<String, Object>();
		if(sta!=null&&sta==1&&stat>0){
			result.put("code", "succ");
			result.put("msg", "操作成功!");			
			result.put("data", o);
		}else{
			result.put("code", "error");
			result.put("msg", "操作失败!");			
		}
		return result;
	}
	
	//查询手机号
	@RequestMapping("queryCustomerByPhone")
	@ResponseBody
	public Map<String, Object> queryCustomerByPhone(String phone){
		Map<String,Object> result = new HashMap<String, Object>();
		Customer cus =  purchaseBo.queryCustomer(phone);
		if(cus==null){
			result.put("code", "error");
			result.put("cus", "");			
		}else{
			result.put("code", "succ");
			result.put("cus", cus);
		}
		return result;
	}
	
	/**
	 * 导出
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="ExporOrderList")
	public void ExporOrderList(HttpServletRequest request ,HttpServletResponse response) throws IOException{
		String attribute = request.getParameter("attribute");
		String content = request.getParameter("content");
		String goodsProv = request.getParameter("goodsProv");
		String goodsCity = request.getParameter("goodsCity");
		String dates = request.getParameter("dates");
		String statDate = request.getParameter("statDate");
		String endDate = request.getParameter("endDate");
		String itemId = request.getParameter("itemId");
		String orderStatus = request.getParameter("orderStatus");
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("attribute",attribute);
		map.put("content",content);
		map.put("goodsProv",goodsProv);
		map.put("goodsCity",goodsCity);
		map.put("dates",dates);
		map.put("statDate",statDate);
		if(endDate!=null &&!"".equals(endDate)){
			map.put("endDate",endDate+" 23:59:59");
		}
		map.put("itemId",itemId);
		map.put("orderStatus", orderStatus);
		List<Order> list = orderBO.ExporOrderList(map);
		if(list!=null){
			ExportUtil<Order> excel = new ExportUtil<Order>();	
 			String fileName = "订单";

			String[] headerNames = new String[] { "订单编号", "分类", "信息标题","价格", "数量", "总金额", "货物所在地", "价格说明", "卖方手机" ,"买方手机","下单时间","成交时间","负责人"};
			String[] header = new String[] { "orderNo", "itemName", "infoTitle", "price", "quantityStr", "sumPrice",
					"pcname", "strPriceExplain" ,"sellerPhone","buyerPhone","createdAt","dealTime","name"};
			String[] comments = new String[] { null, null, null, null, null, null, null, null, null, null, null, null,null };
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			excel.export("sheet1", headerNames, header, comments, list, os, "");
			byte[] contents = os.toByteArray();
			InputStream is = new ByteArrayInputStream(contents);
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
	
}
