<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="user_data_container">
	<div id="user_file_container">
		<div>
			<h2>${type}으로검색된 결과입니다.</h2>
		</div>
		<c:choose>
			<c:when test="${userData != null && userDataSize > 0}">
			<h3>총 ${userDataSize}개의 검색 결과가 있네요.</h3>
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
			</c:when>
			<c:otherwise>
				<h3>아무것도 없는데요..? :o</h3>
			</c:otherwise>
		</c:choose>
	</div>
</div>