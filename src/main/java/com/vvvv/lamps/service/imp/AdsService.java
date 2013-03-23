package com.vvvv.lamps.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vvvv.common.service.imp.BaseService;
import com.vvvv.lamps.dao.AdsDAO;
import com.vvvv.lamps.dao.NewsDAO;
import com.vvvv.lamps.model.Ads;
import com.vvvv.lamps.service.IAdsService;

/**
 * @className:AdsService.java
 * @classDescription:
 * @author:wei.luo
 * @createTime:2012-4-4
 */
@Service
public class AdsService extends BaseService<Ads> implements IAdsService {
	@Resource(name = "adsDAO")
	private AdsDAO adsDAO;

	public int changeOrder(int newOrder, int oldOrder) {
		int max = this.adsDAO.findCountByHql("from Ads", "max(orders)");
		if (max == 0) {
			max = 1;
		}
		// 防止newOrder越界
		if (newOrder < 1) {
			newOrder = 1;
		}
		if (newOrder > max) {
			newOrder = max;
		}
		String hql = "";
		// 对排序进行处理
		if (newOrder > oldOrder) {
			hql = "update Ads set orders=orders-1 where orders <= " + newOrder + " and orders > " + oldOrder;
		} else if (newOrder < oldOrder) {
			hql = "update Ads set orders=orders+1 where orders >= " + newOrder + " and orders < " + oldOrder;
		} else {
			return newOrder;
		}
		this.adsDAO.executeSql(hql);
		return newOrder;
	}
	
}
