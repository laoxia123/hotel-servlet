package cn.itcast.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.entity.FoodType;
import cn.itcast.factory.BeanFactory;
import cn.itcast.service.IFoodTypeService;
import cn.itcast.service.impl.FoodTypeService;
import cn.itcast.utils.WebUtils;

/**
 * 4.菜系管理Servlet开发
 * 
 * a.添加菜系
 * b.菜系列表展示
 * c.进入更新页面
 * d.删除
 * e.更新
 * @author Administrator
 *
 */
public class FoodTypeServlet extends BaseServlet {
	
	//a.添加菜系
	public Object addFoodType(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			Object uri = null;
			//1.获取请求数据封装
			String foodTypeName = request.getParameter("foodTypeName");
			FoodType ft = new FoodType();
			ft.setTypeName(foodTypeName);
			//2.调用service处理业务逻辑
			foodTypeService.save(ft);
			//3.跳转
			uri = request.getRequestDispatcher("/foodType?method=list");
			return uri;

	}

	
	//b.菜系列表展示
	public Object list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			Object uri = null;
		
			//调用Service查询所有的类别
			List<FoodType> list = foodTypeService.getAll();
			//保存
			request.setAttribute("listFoodType", list);
			//路径:跳转到菜系列表页面
//				uri = "/sys/type/foodtype_list.jsp";
			uri = request.getRequestDispatcher("/sys/type/foodtype_list.jsp");
			return uri;
	}

//	c.进入更新页面
	public Object viewUpdate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			Object uri = null;
			//1.获取请求id
			String id = request.getParameter("id");
			//2.根据id查询对象
			FoodType ft = foodTypeService.findById(Integer.parseInt(id));
			//3.保存
			request.setAttribute("foodType", ft);
			//4.跳转
//				uri = "/sys/type/foodtype_update.jsp";
			uri = request.getRequestDispatcher("/sys/type/foodtype_update.jsp");
			return uri;
	}
	
	//d.删除
	public Object delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Object uri;
		
		//1.获取请求id
		String id = request.getParameter("id");
		foodTypeService.delete(Integer.parseInt(id));
		uri = request.getRequestDispatcher("/foodType?method=list");
		
		return uri;
		
	}
	
	//e.更新
	public Object update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Object uri = null;
		//1.获取请求数据
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("foodTypeName");
		FoodType foodType = new FoodType();
		foodType.setId(id);
		foodType.setTypeName(name);
		//2.调用service更新
		foodTypeService.update(foodType);
		//3.跳转
		uri = "/foodType?method=list";
		return uri;
	}
	
	

}

















