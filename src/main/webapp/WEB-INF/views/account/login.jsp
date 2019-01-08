<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
<script type="text/javascript" src="/resources/js/lib/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/resources/js/account/login.js"></script>
<link rel="stylesheet" href="/resources/css/account/account.css">
</head>
<body>
<div class="login_logo_container">
	<img alt="login_logo_img" src="/resources/images/login_logo.jpg">
</div>
<form action="login" method="post" id="loginForm" class="loginForm">
	<div>
		<input type="text" name="mid" id="login_id_input" class="input_field" placeholder="아이디">
	</div>
	<div>
		<input type="password" name="mpassword" id="login_pw_input" class="input_field" placeholder="비밀번호">
	</div>
	<hr class="login_line">
	<div class="login_btn_container">
		<img alt="login_btn" src="/resources/images/login_btn.jpg" id="login_btn" class="login_btn">
		<br>	
		<a href="/join">회원가입</a>
	</div>
</form>
</body>
</html>