package com.javaex.comtroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

		}else if("login".equals(action)) {
			System.out.println("UserController.login");
			
			//파라미터에서 값 꺼내기
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
		
			//dao 회원정보 조회하기(세션 저장용)
			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getUser(id, pw);
			
			if(userVo != null) {
				System.out.println("로그인 성공!");
				//성공일 때(아이디, 비밀번호 일치) 세션에 저장
				HttpSession session = request.getSession();
				session.setAttribute("authUser", userVo); // jsp에 데이터 전달할때 비교 request.setAttribute();
				
				//리다이렉트 -메인페이지
				WebUtil.redirect(request, response, "./main");
			}else {
				System.out.println("로그인 실패ㅠ");
				
				//리다이렉트 -로그인페이지
				WebUtil.redirect(request, response, "/mysite/user?action=lform&result=fail");
			}
			
		}else if("logout".equals(action)) {
			System.out.println("UserController.logout");
			
			//세션에 있는 "authUser" 의 데이터 삭제
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			WebUtil.redirect(request, response, "./main");
		}else if("mform".equals(action)) {
			System.out.println("UserController.mform");
			//회원수정폼 포워드
			WebUtil.forword(request, response, "/WEB-INF/views/user/modifyForm.jsp");
		}else if("modify".equals(action)) {
			System.out.println("UserController.modify");
			
			//파라미터 꺼내기
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			//세션에서 no값 꺼내기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			
			//vo로 하나로 묶어주기
			UserVo userVo = new UserVo(no, pw, name, gender);
			
			//dao.insert(vo) --> db에 저장
			UserDao userDao = new UserDao();
			userDao.userUpdate(userVo);
			
			authUser.setName(name);
			
			//리다이렉트 -메인페이지
			WebUtil.redirect(request, response, "./main");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
