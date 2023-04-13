<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="sign-up-box" class="container mt-2">
	<h1 id="" class="display-4 text-secondary text-center mb-3">회원가입</h1>
	<form action="/user/sign_up" method="post" class="" id="signUpForm">
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
		<button type="submit" id="signUpBtn" class="btn btn-primary form-control">회원가입</button>
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
			//console.log(loginId);
			
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
					alert("관리자에게 문의해주세요.");
				}
				
			});
			
		});
		
		// 회원가입
		$('#signUpForm').on('submit', function(e) {
			e.preventDefault(); // submit 기능 중단시키기
			
			// validation
			let loginId = $('#loginId').val().trim();
			let password = $('#password').val();
			let confirmPassword = $('#confirmPassword').val();
			let name = $('#name').val().trim();
			let email = $('#email').val().trim();
			
			if(!loginId) {
				alert("아이디를 입력하세요.");
				return false;
			}
			if(!password || !confirmPassword) {
				alert("비밀번호를 입력하세요.");
				return false;
			}
			if(!name) {
				alert("이름을 입력하세요.");
				return false;
			}
			if(!email) {
				alert("이메일을 입력하세요.");
				return false;
			}
			
			// id 중복확인됐는지 확인! -> idCheckOk d-none이 있으면 alert()
			if($('#idCheckOk').hasClass("d-none")) {
				alert("아이디 중복확인을 다시 해주세요.");
				return false;
			}
			
			// 서버로 보내는 방법
			// 1) submit
			//$(this)[0].submit(); // 일반 컨트롤러로 (화면 이동)
		
			// 2) ajax
			let url = $(this).attr('action'); // 내가 누른 폼태그가 가지고 있는 속성인 action의 값을 가져오겠다.
			let params = $(this).serialize(); // 폼태그의 name들을 파라미터로 구성 개꿀?
			console.log(params);
			
			$.post(url, params) //request
			.done(function(data) { // 가독성 차원 줄바꿈
				//response
				if(data.code == 1) { // 성공
					alert("가입을 환영합니다. 로그인을 해주세요.");
					location.href = "/user/sign_in_view"; // 절대경로	
				
				} else { // logic상 실패
					alert(data.errorMessage);
				}
			}); 
			
			
		});
		
	});
</script>





