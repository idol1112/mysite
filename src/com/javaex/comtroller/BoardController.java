package com.javaex.comtroller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		if("list".equals(action)) {
			System.out.println("[boardList]");
			
			BoardDao boardDao = new BoardDao();
			List<BoardVo> boardList = boardDao.getBoardList();
			request.setAttribute("bList", boardList);
			
			WebUtil.forword(request, response, "/WEB-INF/views/board/list.jsp");
		}else if("wForm".equals(action)) {
			System.out.println("[wForm]");
			
			WebUtil.forword(request, response, "/WEB-INF/views/board/writeForm.jsp");
		}else if("write".equals(action)) {
			System.out.println("[write]");
			
			//세션에서 login할때 넣어 준 authUser값을 받는다
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			//파라미터 값 받기
			int no = authUser.getNo();
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			//Vo로 묶기
			BoardVo boardVo = new BoardVo(no, title, content);
			
			//boardDao.insert();
			BoardDao boardDao = new BoardDao();
			boardDao.boardInsert(boardVo);
			
			WebUtil.forword(request, response, "/mysite/board?action=list");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
