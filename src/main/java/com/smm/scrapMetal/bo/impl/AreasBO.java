package com.smm.scrapMetal.bo.impl;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.smm.scrapMetal.bo.IAreasBO;
import com.smm.scrapMetal.commom.ResErrorCode;
import com.smm.scrapMetal.dao.IAreasDao;
import com.smm.scrapMetal.domain.Areas;
import com.smm.scrapMetal.dto.ResultData;

/**
 * @author dongmiaonan
 */

@Service
public class AreasBO implements IAreasBO {
    private Logger logger = Logger.getLogger(AreasBO.class);
    @Resource
    IAreasDao      iAreasDao;

    @Override
    public List<Areas> getParentAreas() {
        return iAreasDao.getParentAreas();
    }

    @Override
    public List<Areas> getChildArea(String parentId) {
        return iAreasDao.getChildArea(parentId);
    }

    @Override
    public Areas getAreaById(String id) {
        return iAreasDao.getAreaById(id);
    }

    @Override
    public ResultData getCityAreas(String paramsStr) throws Exception {
        ResultData data = new ResultData();
        JSONObject obj = JSONObject.fromObject(paramsStr);
        String provId = obj.getString("parentId");
        if (StringUtils.isNotBlank(provId)) {//省id
            List<Areas> areasList = getChildArea(provId);
            JSONArray jsonArray = JSONArray.fromObject(areasList);
            data.setUnAuthorizedRequest(false);
            data.setSuccess(true);
            data.setResult(jsonArray.toString());
        } else {
            logger.error("getCityAreas 的params provId is null");
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.CITY_PARAMS_ERROR_CODE);
            data.setErrMsg(ResErrorCode.CITY_PARAMS_ERROR_MSG);
            data.setUnAuthorizedRequest(false);
        }
        return data;
    }

}
