package com.smm.scrapMetal.dao;

import java.util.List;
import java.util.Map;

import com.smm.scrapMetal.domain.Areas;

/**
 * 
 * @author dongmiaonan
 *
 */
public interface IAreasDao {
   public List<Areas> getParentAreas();
   public List<Areas> getChildArea(String parentId);
   public Areas getAreaById(String id);
   
}
