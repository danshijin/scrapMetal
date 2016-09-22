package com.smm.scrapMetal.bo;

import java.util.List;

import com.smm.scrapMetal.domain.Areas;
import com.smm.scrapMetal.dto.ResultData;

/**
 * @author dongmiaonan
 */
public interface IAreasBO {
    /**
     * @param 获取一级省份 Parent=0
     * @return
     */
    public abstract List<Areas> getParentAreas();

    /**
     * @param 根据父级ID获取子级城市
     * @return
     */
    public abstract List<Areas> getChildArea(String parentId);

    /**
     * @param 根据主键查询
     * @return
     */

    public abstract Areas getAreaById(String id);

    public ResultData getCityAreas(String paramsStr) throws Exception;

}
