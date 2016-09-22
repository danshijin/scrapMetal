package com.smm.scrapMetal.bo.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.scrapMetal.bo.IDeliveryBo;
import com.smm.scrapMetal.dao.IDeliveryDao;
import com.smm.scrapMetal.domain.Delivery;

@Service
public class DeliveryImpl  implements IDeliveryBo{
	@Resource
	private IDeliveryDao dao;

	@Override
	public List<Delivery> queryAll() {
		return dao.queryAll();
	}

	@Override
	public int addDelivery(Delivery delivery) {
		return dao.addDelivery(delivery);
	}

	@Override
	public int delDelivery(Integer id) {
		return dao.delDelivery(id);
	}


}
