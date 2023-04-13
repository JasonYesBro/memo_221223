<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="logo justify-content-center d-flex align-items-center">
	<h1>MEMO 게시판</h1>
</div>
<div class="status d-flex justify-content-end pr-5">
	<c:if test="${not empty userLoginId}">
		<span class="mr-4">${ userName } 님 환영합니다!</span>
		<span class="mr-4"><a href="/user/sign_out">logout</a></span>
	</c:if>
	<c:if test="${empty userLoginId}">
		<a href="/user/sign_in_view" class="font-weight-bold">로그인</a>
	</c:if>
</div>
<!-- 로그아웃 -->