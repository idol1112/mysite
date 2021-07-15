<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.javaex.vo.UserVo" %>
<%
	//로그인 관련
	UserVo authUserAll = (UserVo)session.getAttribute("authUserAll");
	System.out.println(authUserAll);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="assets/css/user.css" rel="stylesheet" type="text/css">

</head>

<body>
	<div id="wrap">

		<!--  header(로고, 로그인버튼) nav(메뉴들) -->
		<jsp:include page="/WEB-INF/views/includes/header.jsp"></jsp:include>

		<div id="container" class="clearfix">
			<div id="aside">
				<h2>회원</h2>
				<ul>
					<li>회원정보</li>
					<li>로그인</li>
					<li>회원가입</li>
				</ul>
			</div>
			<!-- //aside -->

			<div id="content">
			
				<div id="content-head">
					<h3>회원정보</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>회원</li>
							<li class="last">회원정보</li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				 <!-- //content-head -->
	
				<div id="user">
					<div id="modifyForm">
						<form action="./user" method="get">
	
							<!-- 아이디 -->
							<div class="form-group">
								<label class="form-text" for="input-uid">아이디</label> 
								<span class="text-large bold"><%=authUserAll.getId()%></span>
							</div>
	
							<!-- 비밀번호 -->
							<div class="form-group">
								<label class="form-text" for="input-pass">패스워드</label> 
								<input type="text" id="input-pass" name="pw" value="<%=authUserAll.getPw()%>">
							</div>
	
							<!-- 이름 -->
							<div class="form-group">
								<label class="form-text" for="input-name">이름</label> 
								<input type="text" id="input-name" name="name" value="<%=authUserAll.getName()%>">
							</div>
	
							<!-- //성별 -->
							<div class="form-group">
								<span class="form-text">성별</span> 
								<%if("male".equals(authUserAll.getGender())) { %>
									<label for="rdo-male">남</label> 
									<input type="radio" id="rdo-male" name="gender" value="male" checked="checked"> 
									
									<label for="rdo-female">여</label> 
									<input type="radio" id="rdo-female" name="gender" value="female"> 
								<%}else { %>
										<label for="rdo-male">남</label> 
										<input type="radio" id="rdo-male" name="gender" value="male"> 
									
										<label for="rdo-female">여</label> 
										<input type="radio" id="rdo-female" name="gender" value="female" checked="checked"> 
								<%} %>
							</div>
	
							<!-- 버튼영역 -->
							<div class="button-area">
								<button type="submit" id="btn-submit">회원정보수정</button>
							</div>
							<input type="hidden" name="id" value="<%=authUserAll.getId()%>">
							<input type="hidden" name="no" value="<%=authUserAll.getNo()%>">
							<input type="hidden" name="action" value="modify">
						</form>
					
					
					</div>
					<!-- //modifyForm -->
				</div>
				<!-- //user -->
			</div>
			<!-- //content  -->

		</div>
		<!-- //container  -->

		<!-- footer -->
		<jsp:include page="/WEB-INF/views/includes/footer.jsp"></jsp:include>
		
	</div>
	<!-- //wrap -->

</body>

</html>