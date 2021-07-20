package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;
import com.javaex.vo.GuestVo;
import com.javaex.vo.UserVo;

public class BoardDao {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			// System.out.println("접속성공");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	public List<BoardVo> getBoardList() {
		return getBoardList("");
	}
	
	public List<BoardVo> getBoardList(String search) {
		getConnection();
		
		List<BoardVo>boardList = new ArrayList<BoardVo>();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			
			query += " select  b.no as bNo, ";
			query += " 		   b.title as bTitle, ";
			query += "         u.name as uName, ";
			query += "         b.user_no as userNo, ";
			query += "         b.hit as bHit, ";
			query += " to_char(b.reg_date, 'yy-mm-dd hh24:mi') as bDate ";
			query += " from    users u, board b ";
			query += " where   u.no = b.user_no ";
			
			if(search != "" || search == null) {
			
				query += " and   (b.title || u.name || b.content) like ? "; //괄호를 쳐야 and문 작동
				query += " order by b.no desc ";
				pstmt = conn.prepareStatement(query);
			
				String searching = '%' + search + '%'; //받아온 search에 '%'문자열 추가
			
				pstmt.setString(1, searching);
			}else {
				query += " order by b.no desc ";
				pstmt = conn.prepareStatement(query);
			}
		
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("bNo");
				String title = rs.getString("bTitle");
				String uName = rs.getString("uName");
				int userNo = rs.getInt("userNo");
				int hit = rs.getInt("bHit");
				String regDate = rs.getString("bDate");
				
				boardList.add(new BoardVo(no, title, hit, regDate, userNo, uName));
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		return boardList;
	}
	
	public int boardInsert(BoardVo boardVo) {
		
		int count = 0;
		getConnection();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " insert into board ";
			query += " values (seq_board_no.nextval, ";
			query += " 			?, ";
			query += " 			?, ";
			query += " 			0, ";
			query += " 			sysdate, ";
			query += " 			? ";
			query += " 			) ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getUserNo());
			
			count = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println(count + "건 저장되었습니다.");
			
		} catch (SQLException e) {
		System.out.println("error:" + e);
		} 
		close();
		return count;
	}
	
	public int boardHit(int no) {
		getConnection();
		int count = 0;
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " update board ";
			query += " set    hit = hit + 1 ";
			query += " 		  where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1 , no);
			count = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println(no + "번째 게시물 조회수가" + count + "올랐습니다");
			
		} catch (SQLException e) {
		System.out.println("error:" + e);
		} 
		
		close();
		return count;
	}
	
	public BoardVo boardRead(int no) {
		getConnection();
		BoardVo boardVo = null;
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select b.title as title, ";
			query += "        b.content as content, ";
			query += " 		  b.hit as hit, ";
			query += " 		  b.user_no as userNo, ";
			query += " 		  b.reg_date as reg_date, ";
			query += " 		  u.name as name ";
			query += " from board b,users u ";
			query += " where u.no = b.user_no ";
			query += " and b.no= ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1 , no);
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			if(rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				int userNo = rs.getInt("userNo");
				String date = rs.getString("reg_date");
				String name = rs.getString("name");

				boardVo = new BoardVo(title, content, hit, date, userNo, name);
			}
			
		} catch (SQLException e) {
		System.out.println("error:" + e);
		} 
		
		close();
		return boardVo;
	}
	
	public BoardVo getBoardModify(int uNo, int bNo) {
		getConnection();
		
		BoardVo boardVo = null;
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select u.name as name, ";
			query += "        b.hit as hit, ";
			query += " 		  b.reg_date as reg_date, ";
			query += " 		  b.title as title, ";
			query += " 		  b.no as boardNo, ";
			query += " 		  b.content as content ";
			query += " from board b,users u ";
			query += " where u.no = b.user_no ";
			query += " and u.no= ? ";
			query += " and b.no= ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1 , uNo);
			pstmt.setInt(2 , bNo);
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			if(rs.next()) {
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				String title = rs.getString("title");
				int boardNo = rs.getInt("boardNo");
				String content = rs.getString("content");

				boardVo = new BoardVo(boardNo, title, content, hit, regDate, name);
			}
			
		} catch (SQLException e) {
		System.out.println("error:" + e);
		} 
		
		close();
		return boardVo;
		
	}
	
	public int boardUpdate(BoardVo boardVo) {
		getConnection();
		int count = 0;
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " update board ";
			query += " set    title = ?, ";
			query += "        content = ? ";
			query += " where  no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3 , boardVo.getNo());
			
			count = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println(count + "건이 수정되었습니다");
			
		} catch (SQLException e) {
		System.out.println("error:" + e);
		} 
		
		close();
		return count;
	}
	
	public int boardDelete(int no) {
		getConnection();
		int count = 0;
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " DELETE board ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			count = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println(count + "건이 삭제되었습니다");
			
		} catch (SQLException e) {
		System.out.println("error:" + e);
		} 
		
		close();
		return count;
	}
}
