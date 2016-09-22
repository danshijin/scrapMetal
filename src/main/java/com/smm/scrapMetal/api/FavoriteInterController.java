package com.smm.scrapMetal.api;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smm.scrapMetal.bo.impl.FavoriteBo;
import com.smm.scrapMetal.commom.ResErrorCode;
import com.smm.scrapMetal.dto.ResultData;

import freemarker.log.Logger;

/**
 * @author hece
 */

@Controller
public class FavoriteInterController {

    private static Logger logger = Logger.getLogger(FavoriteInterController.class.getName());
    @Resource
    private FavoriteBo    favoriteBo;

    /**
     * 点赞收藏接口
     * 
     * @param request
     * @return
     */
    @RequestMapping("/favoriteInter/editFavorite")
    @ResponseBody
    public ResultData editFavorite(HttpServletRequest request) {
        ResultData data = new ResultData();
        String paramsStr = request.getParameter("paramsStr");

        try {
            if (!StringUtils.isNotBlank(paramsStr)) {
                logger.error("addFavorite 的paramsStr  is null");
                data.setSuccess(false);
                data.setErrorcode(ResErrorCode.PARAMS_NULL_ERROR_CODE);
                data.setErrMsg(ResErrorCode.PARAMS_NULL_ERROR_MSG);
                data.setUnAuthorizedRequest(false);
                return data;
            }
            data = favoriteBo.editFavorite(paramsStr);
        } catch (Exception e) {
            logger.error("addFavorite系统错误", e);
            data.setSuccess(false);
            data.setErrorcode(ResErrorCode.ERROR_CODE);
            data.setErrMsg(ResErrorCode.ERROR_MSG);
        }
        return data;
    }

}
