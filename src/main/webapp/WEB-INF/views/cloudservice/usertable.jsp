<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="user_data_container">
<div>현재위치 : ${nowPath}</div>
<!-- dir 출력 -->
	<div id="user_dir_container">
	<c:if test="${isDefaultPath}">
		<img src="resources/images/dir-back-img.png" width="165" height="164" class="dir_back_img" id="dir_back_img">
	</c:if>
	<c:if test="${!(dirList != null && dirListSize > 0) && !(userData != null && userDataSize > 0)}">
		<h1>여긴 아무것도 없어요!</h1>
	</c:if>
	<c:if test="${dirList != null && dirListSize > 0}">
		<c:forEach var="dir" items="${dirList}" varStatus="status">
			<div id="dir_${status.index}">
				<img src="resources/images/dir-img.png" width="165" height="164" id="dir_img">
				<br>
				<span id="dir_name_${status.index}">${dir}</span>
				<br>
				<input type="checkbox" name="checkbox_dir_${status.index}" value="${dir}" class="user_data_checkbox">
			</div>
		</c:forEach>
	</c:if>
	</div>
<!-- file 출력 -->
	<div id="user_file_container">
	<c:if test="${userData != null && userDataSize > 0}">
		<form id="file_detail_form">
		<c:forEach var="data" items="${userData}" varStatus="status">
			<div id="file_detail_${status.index}">
				<img src="resources/images/test-img.jpg" width="100" height="80"  id="file_img">
				<br>
				<span id="file_name_${status.index}">${data.ut_file_name }</span>
				<br>
				<span id="file_size">${data.ut_file_size }</span>
				<br>
				<span>${data.ut_file_reg_date }</span>
				<br>
				<input type="checkbox" name="checkbox_file_${status.index}" value="${data.ut_file_name }" class="user_data_checkbox">
			</div>
		</c:forEach>
		</form>
	</c:if>
	</div>
</div>
 