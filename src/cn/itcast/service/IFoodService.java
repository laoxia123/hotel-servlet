package cn.itcast.service;

import cn.itcast.entity.Food;
import cn.itcast.utils.PageBean;

public interface IFoodService {
	/**
	 * 主键查询
	 * @param id
	 * @return
	 */
	Food findById(int id);
	
	/**
	 * 分页查询
	 * @param pb
	 */
	void getAll(PageBean<Food> pb);
}
