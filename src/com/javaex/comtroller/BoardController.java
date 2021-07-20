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
		}else if("wform".equals(action)) {
			System.out.println("[wform]");
			
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
			BoardVo boardVo = new BoardVo(title, content, no);
			
			//boardDao.insert();
			BoardDao boardDao = new BoardDao();
			boardDao.boardInsert(boardVo);
			
			WebUtil.redirect(request, response, "/mysite/board?action=list");
		}else if("read".equals(action)) {
			System.out.println("[read]");
			
			//파라미터 값 받기
			int no = Integer.parseInt(request.getParameter("no"));
			
			//boardDao.boardHit();
			BoardDao boardDao = new BoardDao();
			boardDao.boardHit(no);
			
			//boardDao.read()해와서 vo로 묶어주기
			BoardVo readVo = boardDao.boardRead(no);
			
			//request 어트리부트에 묶어준vo 넣기
			request.setAttribute("readVo", readVo);
			
			//포워드
			WebUtil.forword(request, response, "/WEB-INF/views/board/read.jsp");
			
		}else if("mform".equals(action)) {
			System.out.println("[mform]");
			
			//파라미터 값 받기
			int bNo = Integer.parseInt(request.getParameter("bNo"));
			
			//세션에 authUser에 users에 no를 받기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int uNo = authUser.getNo();
			
			//dao를 메모리에 올리고 dao.getBoardModify()에 받은 값을 넣어 호출 해준 후에
			//vo로 묶어준다.
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getBoardModify(uNo, bNo);
			
			//묶어준 vo를 modifyVo라는 이름으로 어트리부트.
			request.setAttribute("modifyVo", boardVo);
			
			//포워드
			WebUtil.forword(request, response, "/WEB-INF/views/board/modifyForm.jsp");
			
			
		}else if("modify".equals(action)) {
			System.out.println("[modify]");
			
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int no = Integer.parseInt(request.getParameter("no"));
			
			
			BoardDao boardDao = new BoardDao();
			boardDao.boardUpdate(new BoardVo(no, title, content));
			
			WebUtil.redirect(request, response, "/mysite/board?action=list");
		}else if("delete".equals(action)) {
			System.out.println("[delete]");
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao boardDao = new BoardDao();
			boardDao.boardDelete(no);
			
			WebUtil.redirect(request, response, "/mysite/board?action=list");
		}else if("search".equals(action)) {
			System.out.println("[search]");
			
			String search = request.getParameter("search_txt");
			
			BoardDao boardDao = new BoardDao();
			
			List<BoardVo> boardList = boardDao.getBoardList(search);
			
			request.setAttribute("bList", boardList);
			
			WebUtil.forword(request, response, "/WEB-INF/views/board/list.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
