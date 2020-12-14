package cn.itcast.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.entity.DinnerTable;
import cn.itcast.factory.BeanFactory;
import cn.itcast.service.IDinnerTableService;
import cn.itcast.utils.WebUtils;

public class IndexServlet extends BaseServlet {
	/*
	//创建service
	private IDinnerTableService dinnerTableService = BeanFactory.getInstance(
			"dinnerTableService", IDinnerTableService.class);
	//保存跳转资源(转发/重定向)
//	private Object uri;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			String method = request.getParameter("method");
			if(method == null){
				//默认执行的方法，进去前台列表的首页
				method = "listTable";
			}
			//判断
		    if("listTable".equals(method)){
				//1.前台首页，显示所有未预定的餐桌
				listTable(request,response);
			}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
	*/
	
	//1.前台首页，显示所有未预定的餐桌
	public Object listTable(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Object uri = null;
		
			//调用service
			List<DinnerTable> list = dinnerTableService.findNoUseTable();
			//保存到request
			request.setAttribute("listDinnerTable", list);
			//跳转到首页显示
			uri = request.getRequestDispatcher("/app/index.jsp");
			return uri;
	}

	

}
