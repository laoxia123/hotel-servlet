package cn.itcast.servlet;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.itcast.entity.DinnerTable;
import cn.itcast.entity.Food;
import cn.itcast.entity.FoodType;
import cn.itcast.utils.Condition;
import cn.itcast.utils.PageBean;

public class FoodServlet extends BaseServlet {
	
	/**
	 * 1.进入主页，显示菜品以及菜系
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public Object foodDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("dinnerTable");
		if(obj == null){
			//1.1获取餐桌ID，根据ID查询，再把查询到的结果保存到session(生成订单用)
			String tableId = request.getParameter("tableId");
			DinnerTable dt = dinnerTableService.findById(Integer.parseInt(tableId));
			//保存到session中
			session.setAttribute("dinnerTable", dt);
		}
		//1.2查询所有的“菜品信息”，保存
		PageBean<Food> pb = new PageBean<Food>();
		//分页参数：获取当前页参数
		String curPage = request.getParameter("currentPage");
		//判断
		if(curPage == null || "".equals(curPage.trim())){
			pb.setCurrentPage(1);
		}else{
			pb.setCurrentPage(Integer.parseInt(curPage));
		}
		//条件对象
		Condition condition = new Condition();
		//分页参数：菜系id
		String foodTypeId = request.getParameter("foodTypeId");
		if(foodTypeId != null){
			//设置条件
			condition.setFoodTypeId(Integer.parseInt(foodTypeId));
		}
		//分页参数：菜名称
		String foodName = request.getParameter("foodName");
		condition.setFoodName(foodName);
		pb.setCondition(condition);
		foodService.getAll(pb);
		//保存查询后的pb对象
		request.setAttribute("pb", pb);
		//1.3查询所有的“菜系信息”，保存
		List<FoodType> listFoodType = foodTypeService.getAll();
		request.setAttribute("listFoodType", listFoodType);
		
		//1.4跳转（转发）
		return request.getRequestDispatcher("/app/caidan.jsp");
		
	}
}


























