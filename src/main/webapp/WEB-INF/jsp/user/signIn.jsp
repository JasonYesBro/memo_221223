<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="login-box" class="container mt-5">
	<h1 id="title" class="display-4 font-weight-bold text-warning text-center">MEMHO</h1>
	<form action="/user/sign_in" method="post" class="" id="loginForm">
		<div class="form-group">
			<input type="text" name="loginId" id="loginId"
				class="form-control form-control-lg" placeholder="아이디를 입력하세요.">
		</div>
		<div class="form-group">
			<input type="password" name="password" id="password"
				class="form-control form-control-lg" placeholder="비밀번호를 입력하세요.">
		</div>
		<button type="submit" class="btn btn-primary form-control mb-1">로그인</button>
		<button type="button" class="btn btn-secondary form-control">회원가입</button>
	</form>
</div>

<script>
$(document).ready(function() {
	// 로그인
	// click 은 기본 동작이 없기 때문에 preventDefault 소용없음
	$('#loginForm').on('submit', function(e) {
		e.preventDefault();
		
		// validation
		let loginId = $('#loginId').val().trim();
		let password = $('#password').val();
		
		if (!loginId) {
			alert('아이디를 입력하세요.');
			return false;
		}
		if (!password) {
			alert('비밀번호를 입력하세요.');
			return false;
		}
		
		let url = $('#loginForm').attr('action');
		let params = $(this).serialize();
		
		$.post(url, params)
		.done(function(data) {
			if (data.code == 1) {
				alert("로그인에 성공하였습니다.");
				location.href = "/post/post_list_view";
			} else {
				alert(data.errorMessage);
			}
		});
	});
});
</script>