package com.javaex.comtroller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestVo;

@WebServlet("/gbc")
public class GuestBookController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("컨트롤러");
		
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("addList".equals(action)) {
			System.out.println("[리스트]");
			
			GuestDao guestDao  = new GuestDao();
			List<GuestVo> guestList = guestDao.getGuestList();
			
			request.setAttribute("gList", guestList);
			
			//RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/addList.jsp");
			//rd.forward(request, response);
			WebUtil.forword(request, response, "/WEB-INF/views/guestbook/addList.jsp");
			
		} else if ("add".equals(action)) {
			System.out.println("[등록]");
			
			request.setCharacterEncoding("UTF-8");

			GuestDao guestDao = new GuestDao();
			
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
			
			GuestVo guestVo = new GuestVo(name, password, content);
			
			guestDao.guestInsert(guestVo);
			
			//response.sendRedirect("/guestbook2/gbc?action=addList");
			WebUtil.redirect(request, response, "./gbc?action=addList");
			
		} else if("dform".equals(action)) {
			System.out.println("[삭제폼]");
			
			//RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/deleteForm.jsp");
			//rd.forward(request, response);
			WebUtil.forword(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
			
		} else if("delete".equals(action)) {
			System.out.println("[삭제]");
			
			request.setCharacterEncoding("UTF-8");

			GuestDao guestDao = new GuestDao();

			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");
			
			int count = guestDao.guestDelete(no, password);
			
			//response.sendRedirect("/guestbook2/gbc?action=addList");
			WebUtil.redirect(request, response, "./gbc?action=addList");

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
