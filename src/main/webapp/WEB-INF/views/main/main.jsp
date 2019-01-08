<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
<link rel="stylesheet" href="/resources/css/main/main.css">
<script type="text/javascript" src="/resources/js/lib/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/resources/js/main/main.js"></script>
<script type="text/javascript" src="/resources/js/main/snb.js"></script>
<style type="text/css">
.custom-menu {
    z-index:1000;
    position: absolute;
    background-color:#C0C0C0;
    border: 1px solid black;
    padding: 2px;
}
</style>
</head>
<body>
	<div class="main">
		<div class="main_left">
			<div class="main_logo_img">
				<a href="/main">
					<img alt="main_logo_img" src="/resources/images/main_logo.jpg">
				</a>
			</div>
			<div class="main_profile_img">
				<img alt="main_profile_img" src="/resources/images/main_profile_img.jpg">
			</div>
			<div class="user_id">${sessionMid}님</div>
			<div class="snb">
				<img alt="snb_photo" class="snb_photo" data-alt-src="/resources/images/snb_photo_over.jpg" src="/resources/images/snb_photo.jpg">
				<img alt="snb_video" class="snb_video" data-alt-src="/resources/images/snb_video_over.jpg" src="/resources/images/snb_video.jpg">
				<img alt="snb_doc" class="snb_doc" data-alt-src="/resources/images/snb_doc_over.jpg" src="/resources/images/snb_doc.jpg">
			</div>
		</div>
		<div class="main_right">
			<div class="top_navigator">
				<span><a href="/leaveMember">회원탈퇴</a> | <a href="/logout">로그아웃</a></span>
			</div>
			<div class="main_container">
					<div class="main_container_top_btns">
						<button id="mk_user_dir">폴더 생성</button>
				<form id="fileUpload" action="/upload" method="post" enctype="multipart/form-data">
						<input type="hidden" name="dir_depth" id="dir_depth" value="">
						<input multiple="multiple" type="file" name="file" id="file">		
						<button id="user_upload">업로드</button>
				</form>
						<button id="user_download">다운로드</button>
						<button id="user_data_delete">삭제</button>
					</div>
				<hr>
				<c:import url="../cloudservice/usertable.jsp"/>
			</div>
		</div>
	</div>
</body>
</html>