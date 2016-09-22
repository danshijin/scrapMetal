/**
 * 
 */
package com.smm.scrapMetal.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smm.scrapMetal.bo.PriceExplainBo;
import com.smm.scrapMetal.commom.ResErrorCode;
import com.smm.scrapMetal.domain.Orders;
import com.smm.scrapMetal.domain.PriceExplain;
import com.smm.scrapMetal.dto.ResultData;
import com.smm.scrapMetal.util.JSONUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * PriceExplainInterController
 *
 * Copyright 2016 SMM, Inc. All Rights Reserved.
 * @author Yuanmeng at 2016年2月27日
 */
@Controller
@RequestMapping("/priceExplainInter")
public class PriceExplainInterController {
	
    private Logger logger = Logger.getLogger(PriceExplainInterController.class);
    
    @Resource
    private PriceExplainBo priceExplainBo;

    /**
     * 得到价格说明列表接口
     * 
     * @param request
     * @return
     */
    @RequestMapping("/priceExplainList")
    @ResponseBody
    public ResultData getMyPriceExplainList() {
        ResultData data = new ResultData();
        try {
        	List<PriceExplain> priceList = priceExplainBo.queryAll();
            Map<String, Object> result = new HashMap<>();
            result.put("currentList", priceList);
            data.setUnAuthorizedRequest(false);
            data.setSuccess(true);
            data.setResult(JSONUtil.doConvertObject2Json(result));
        } catch (Exception e) {
            logger.error("getMyPriceExplainList系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
            data.setUnAuthorizedRequest(false);
        }
        return data;
    }
}
