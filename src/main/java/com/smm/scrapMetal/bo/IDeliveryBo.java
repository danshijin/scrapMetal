package com.smm.scrapMetal.bo;

import java.util.List;

import com.smm.scrapMetal.domain.Delivery;

public interface IDeliveryBo {
	
	public List<Delivery> queryAll();
	
	public int addDelivery(Delivery delivery);
	
	public int delDelivery(Integer id);

}
