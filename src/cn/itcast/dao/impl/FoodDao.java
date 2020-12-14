package cn.itcast.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.print.attribute.standard.PDLOverrideSupported;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.dao.IFoodDao;
import cn.itcast.entity.Food;
import cn.itcast.utils.Condition;
import cn.itcast.utils.JdbcUtils;
import cn.itcast.utils.PageBean;

public class FoodDao implements IFoodDao {
	
	@Override
	public Food findById(int id) {
		StringBuffer sb = new StringBuffer();
		sb.append("select");
		sb.append(" f.id,");
		sb.append(" f.foodName,");
		sb.append(" f.price,");
		sb.append(" f.mprice,");
		sb.append(" f.remark,");
		sb.append(" f.img,");
		sb.append(" f.foodType_id,");
		sb.append(" t.typeName ");
		sb.append("from ");
		sb.append(" food f,");
		sb.append(" foodtype t ");
		sb.append(" where 1=1 ");
		sb.append(" and f.foodType_id=t.id ");
		
		try {
			return JdbcUtils.getQueryRunner().query(sb.toString(), new BeanHandler<Food>(Food.class));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	@Override
	public void getAll(PageBean<Food> pb) {
		//获取条件对象
		Condition condition = pb.getCondition();
		//条件之类别id
		int typeId = condition.getFoodTypeId();
		//条件之菜品名称
		String foodName = condition.getFoodName();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select");
		sb.append(" f.id,");
		sb.append(" f.foodName,");
		sb.append(" f.price,");
		sb.append(" f.mprice,");
		sb.append(" f.remark,");
		sb.append(" f.img,");
		sb.append(" f.foodType_id,");
		sb.append(" t.typeName ");
		sb.append("from ");
		sb.append(" food f,");
		sb.append(" foodtype t ");
		sb.append(" where 1=1 ");
		sb.append(" and f.foodType_id=t.id ");
		//存储查询条件对应的值
		List<Object> list = new ArrayList<Object>();
		/*******拼接查询条件********/
		//类别id条件
		if(typeId > 0){
			sb.append(" and f.foodType_id = ?");
			list.add(typeId);
		}
		//菜品名称
		if(foodName != null && !"".equals(foodName.trim())){
			sb.append(" and f.foodName like ?");
			list.add(foodName);
		}
		/*******分页条件********/
		sb.append(" LIMIT ?,?");
		//先查询总记录数
		int totalCount = getTotalCount(pb);
		pb.setTotalCount(totalCount);
		if(pb.getCurrentPage() < 1){
			pb.setCurrentPage(1);
		}else if(pb.getCurrentPage() >pb.getTotalPage()){
			pb.setCurrentPage(pb.getTotalPage());
		}
		//起始行
		int index = (pb.getCurrentPage() - 1 )* pb.getPageCount();
		//返回记录行
		int count = pb.getPageCount();
		list.add(index);
		list.add(count);
		
		
		try {
			List<Food> pageData = JdbcUtils.getQueryRunner().query(
					sb.toString(), 
					new BeanListHandler<Food>(Food.class),list.toArray());
			pb.setPageData(pageData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	@Override
	public int getTotalCount(PageBean<Food> pb) {
		//获取条件对象
		Condition condition = pb.getCondition();
		//条件之类别id
		int typeId = condition.getFoodTypeId();
		//条件之菜品名称
		String foodName = condition.getFoodName();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select");
		sb.append(" count(*) ");
		sb.append("from ");
		sb.append(" food f,");
		sb.append(" foodtype t ");
		sb.append(" where 1=1 ");
		sb.append(" and f.foodType_id=t.id ");
		//存储查询条件对应的值
		List<Object> list = new ArrayList<Object>();
		/*******拼接查询条件********/
		//类别id条件
		if(typeId > 0){
			sb.append(" and f.foodType_id = ?");
			list.add(typeId);
		}
		//菜品名称
		if(foodName != null && !"".equals(foodName.trim())){
			sb.append(" and f.foodName like ?");
			list.add(foodName);
		}
		//查询
		try {
			Long num = JdbcUtils.getQueryRunner().query(sb.toString(), new ScalarHandler<Long>(),list.toArray());
			return num.intValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

	

}
