package com.javaex.comtroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;
@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("UserController");
		
		//텍스트 인코딩
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		
		
		if("jform".equals(action)) {//회원가입 폼
			System.out.println("UserController.lform");
			//회원가입폼 포워드
			WebUtil.forword(request, response, "/WEB-INF/views/user/joinForm.jsp");
		
		}else if("join".equals(action)) {//회원가입
			//회원가입
			System.out.println("UserController.join");
			
			//파라미터 꺼내기
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			//vo로 하나로 묶어주기
			UserVo userVo = new UserVo(id, pw, name, gender);
			
			//dao.insert(vo) --> db에 저장
			UserDao userDao = new UserDao();
			int count = userDao.userInsert(userVo);
			
			//포워드
			WebUtil.forword(request, response, "/WEB-INF/views/user/joinOk.jsp");
		}else if("lform".equals(action)) {
			//로그인
			System.out.println("UserController.lform");
			
			//로그인 폼 포워드
			WebUtil.forword(request, response, "/WEB-INF/views/user/loginForm.jsp");

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
