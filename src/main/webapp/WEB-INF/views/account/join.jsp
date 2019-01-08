<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 페이지</title>
<script type="text/javascript" src="/resources/js/lib/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/resources/js/account/joinForm.js"></script>
<link rel="stylesheet" href="/resources/css/account/account.css">

<style type="text/css">
	div[class $= 'errMsg']{
		color: red;
	}
</style>
</head>
<body>
<div class="join_logo_container">
	<img alt="login_logo_img" src="/resources/images/login_logo.jpg">
	<hr class="join_line">
	<img alt="정보를 입력하세요" src="/resources/images/join_guide.jpg" class="join_guid">
</div>
<form:form id="joinForm" action="join" method="post" commandName="accountDTO">
	<div id="join_inputs">
		<div class="join_input_block">
			<img alt="아이디" src="/resources/images/join_id.jpg">
			<form:input class="join_input_field" path="mid" id="join_id_input" placeholder="아이디"/>
			<img alt="중복확인" id="id_chk_btn" src="/resources/images/join_id_chk.jpg">
		</div>
		<div class="errMsg">
			<form:errors path="mid"/>
		</div>
		<div class="join_input_block">
			<img alt="비밀번호" src="/resources/images/join_pw.jpg">
			<form:input type="password" class="join_input_field" path="mpassword" id="join_password_input" placeholder="비밀번호"/>
		</div>
		<div class="errMsg">
			<form:errors path="mpassword"/>
		</div>
		<div class="join_input_block"><!-- 비밀번호 확인은 Script에서 처리할 예정 -->
			<img alt="비밀번호 확인" src="/resources/images/join_pw_chk.jpg">
			<input type="password" id="join_pwchk_input" class="join_input_field"  placeholder="비밀번호 확인">
		</div>
		<div class="pwchk_errMsg"></div>
		<div class="join_input_block">
			<img alt="이름" src="/resources/images/join_name.jpg">
			<form:input type="text" class="join_input_field" path="mname" id="join_name_input" placeholder="이름"/>
		</div>
		<div class="errMsg">
			<form:errors path="mpassword"/>
		</div>
		<div class="join_input_block">
			<img alt="전화번호" src="/resources/images/join_phone.jpg">
			<form:input type="text" class="join_input_field" path="mphone" id="join_phone_input" placeholder="휴대전화번호"/>
		</div>
		<div class="errMsg">
			<form:errors path="mpassword"/>
		</div>
		<div class="join_input_block">
			<img alt="이메일" src="/resources/images/join_email.jpg">
			<form:input type="text" class="join_input_field" path="memail" id="join_email_input" placeholder="이메일주소"/>
		</div>
		<div class="errMsg">
			<form:errors path="mpassword"/>		
		</div>
		<div>
			<img alt="회원가입" src="/resources/images/join_btn.jpg" onclick="checkJoinValidity();" class="join_btn">
			<br>
			<!-- <button type="button" onclick="checkJoinValidity();">회원가입</button>&nbsp;&nbsp;&nbsp;
			<button type="button" onclick="javascript:history.back();">취소</button> -->
			<a href="/login">취소</a>
		</div>
	</div>
</form:form>
</body>
</html>