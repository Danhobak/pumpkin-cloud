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
			<div class="user_menu">
				<a href="/leaveMember">회원탈퇴</a> | <a href="/logout">로그아웃</a>
			</div>
			<div class="snb">
				<img alt="snb_photo" id="snb_photo" class="snb_photo" data-alt-src="/resources/images/snb_photo_over.jpg" src="/resources/images/snb_photo.jpg">
				<img alt="snb_video" id="snb_video" class="snb_video" data-alt-src="/resources/images/snb_video_over.jpg" src="/resources/images/snb_video.jpg">
				<img alt="snb_doc" id="snb_doc" class="snb_doc" data-alt-src="/resources/images/snb_doc_over.jpg" src="/resources/images/snb_doc.jpg">
			</div>
		</div>
		<div class="main_right">
			<!-- <div class="top_navigator">
				<span><a href="/leaveMember">회원탈퇴</a> | <a href="/logout">로그아웃</a></span>
			</div> -->
			<div class="main_container">
				<c:choose>
				<c:when test="${pageUrl=='usertable'}">
					<div class="main_container_top_btns">
						<div>
							<img src="/resources/images/btn_new.png" id="mk_user_dir" width="129" height="129">
						</div>
					<form id="fileUpload" action="/upload" method="post" enctype="multipart/form-data">
						<input type="hidden" name="dir_depth" id="dir_depth" value="">
						<input multiple="multiple" type="file" name="file" id="file">
						<div>
							<img src="/resources/images/btn_upload.png" id="user_upload" width="129" height="129">
						</div>		
					</form>
						<div>
							<img src="/resources/images/btn_download.png" id="user_download" width="129" height="129">
						</div>
						<div>
							<img src="/resources/images/btn_delete.png" id="user_data_delete" width="129" height="129">
						</div>
					</div>
					<hr>
						<c:import url="../cloudservice/usertable.jsp"/>
				</c:when>
				<c:when test="${pageUrl=='typeSelect'}">
						<img src="/resources/images/btn_download.png" id="user_download" width="129" height="129">
						<img src="/resources/images/btn_delete.png" id="user_data_delete" width="129" height="129">
						<c:import url="../cloudservice/typeselect.jsp"/>
				</c:when>
				</c:choose>
			</div>
		</div>
	</div>
</body>
</html>