package com.smm.scrapMetal.dao;

import java.util.List;

import com.smm.scrapMetal.domain.Delivery;

public interface IDeliveryDao {
	
	public List<Delivery> queryAll();
	
	public int addDelivery(Delivery delivery);
	
	public int delDelivery(Integer id);

}
