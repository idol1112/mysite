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
		getConnection();
		List<BoardVo>boardList = new ArrayList<BoardVo>();
		try {

			String query = "";
			
			query += " select  b.no as bNo, ";
			query += " 		   b.title as bTitle, ";
			query += "         u.name as uName, ";
			query += "         b.user_no as userNo, ";
			query += "         b.hit as bHit, ";
			query += " to_char(b.reg_date, 'yy-mm-dd hh24:mi') as bDate ";
			query += " from    users u, board b ";
			query += " where   u.no = b.user_no ";
			query += " order by b.no desc ";
			
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
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
}
