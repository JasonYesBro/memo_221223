<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="sign-up-box" class="container mt-2">
	<h1 id="" class="display-4 text-secondary text-center mb-3">회원가입</h1>
	<form action="/" method="post" class="">
		<div class="form-group">
			<div class="d-flex justify-content-between">
				<input type="text" name="loginId" id="loginId"
					class="form-control col-8" placeholder="아이디를 입력하세요.">
				<button type="button" id="loginIdCheckBtn" 
					class="btn btn-primary form-control mb-1 col-3">중복확인</button>
			</div>
			<small id="idCheckLength" class="text-danger d-none">ID를 4자 이상 입력해주세요.</small>
			<small id="idCheckDuplicated" class="text-danger d-none">이미 사용중인 ID입니다.</small> 
			<small id="idCheckOk" class="text-primary d-none">사용가능한 ID입니다.</small>
		</div>
		<div class="form-group">
			<input type="text" name="password" id="password" class="form-control"
				placeholder="비밀번호를 입력하세요.">
		</div>
		<div class="form-group">
			<input type="text" name="confirmPassword" id="confirmPassword" class="form-control"
				placeholder="비밀번호를 다시 입력하세요."> 
			<small class="text-danger d-none">비밀번호가 일치하지 않습니다.</small>
		</div>
		<div class="form-group">
			<input type="text" name="name" id="name" class="form-control"
				placeholder="이름을 입력하세요.">
		</div>
		<div class="form-group">
			<input type="text" name="email" id="email" class="form-control"
				placeholder="이메일을 입력하세요.">
		</div>
		<button type="button" id="signUpBtn" class="btn btn-primary form-control">회원가입</button>
	</form>
</div>

<script>
	$(document).ready(function() {
		// 중복확인 버튼 클릭
		$('#loginIdCheckBtn').on('click', function() {
			
			// 경고 문구 초기화
			$('#idCheckLength').addClass('d-none');
			$('#idCheckDuplicated').addClass('d-none');
			$('#idCheckOk').addClass('d-none');
			
			let loginId = $('#loginId').val().trim();
			console.log(loginId);
			
			// id 가 4자 미만이면 문구 노출
			if(loginId.length < 4) {
				$('#idCheckLength').removeClass('d-none');
				return false;
				
			}
			
			// AJAX 통신 - 중복확인
			$.ajax({
				// request
				url : "/user/is_duplicated_id"
				, data : {"loginId" : loginId}
				// response	
				, success : function(data) { // call back 함수
					if(data.result) {
						$('#idCheckDuplicated').removeClass('d-none');
						
					} else {
						$('#idCheckOk').removeClass('d-none');				
						
					}
				}
				, error : function(status, request, error) {
					
				}
				
				
				
				
			});
			
		});
	});
</script>





