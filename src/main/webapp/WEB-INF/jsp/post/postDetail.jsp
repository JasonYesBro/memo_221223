<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="d-flex justify-content-center">
	<div class="w-50">
		<h1>글 상세 / 수정</h1>
		<input type="text" id="subject" class="form-control"
			placeholder="제목을 입력하세요">
		<textarea rows="10" class="form-control" id="content"
			placeholder=" ${post.content}"></textarea>
		<%-- 이미지가 있을 때만 이미지 영역 추가 not empty-> null 이거나 비어있거나 둘다 체크해줌 --%>
		<c:if test="${ not empty post.imagePath }">
			<div class="mt-3">
				<img src="${post.imagePath}" alt="업로드 된 이미지" width="300" />
			</div>
		</c:if>
		<div class="d-flex justify-content-end my-4">
			<input type="file" id="file" accept=".jpg, .jpeg, .png, .gif">
		</div>
		<div class="d-flex justify-content-between">
			<button type="button" id="postDeleteBtn" class="btn btn-secondary">삭제</button>

			<div>
				<a href="/post/post_list_view" class="btn btn-dark">목록</a>
				<button type="button" id="saveBtn" class="btn btn-warning" data-post-id="${post.id}">수정</button>
			</div>
		</div>
	</div>
	
	<script>
		$(document).ready(function() {
			$('#saveBtn').on('click', function() {
				// 제목은 trim으로 공백제거해줌
				let subject = $('#subject').val().trim();
				let content = $('#content').val();
				let postId = $(this).data("post-id");
				// file의 이름만 가져옴
				let file = $('#file').val();

				console.log(postId);
				
				if (!subject) {
					alert("제목을 입력해주세요.");
					return false;
				}
				
				if (!content) {
					alert("내용을 입력해주세요.");
					return false;
				}
				
				// 파일이 업로드 된 경우 확장자 체크
				if (file != '') {
					let ext = file.split('.').pop().toLowerCase();
					// 배열 안에 해당하지 않는 것은 -1로 나옴
					if ($.inArray(ext, ['jpg', 'jpeg', 'gif', 'png']) == -1) {
						alert('이미지 파일만 업로드 가능합니다.');
						$('#file').val('');  // 파일을 비운다.
						return;
					}
				}
				
				// 폼태그를 자바스크립트에서 만든다.(이미지 때문에)
				let formData = new FormData();
				
				formData.append("postId", postId);
				formData.append("subject", subject);
				formData.append("content", content);
				formData.append("file", $('#file')[0].files[0]); // 몇번째인지 꼭 적어줘야함
				
				$.ajax({
					// request
					type : "put"
					, url : "/post/update"
					, data : formData
					, encType : "multipart/form-data" // 파일 업로드를 위한 필수 설정
					, processData : false // 파일 업로드를 위한 필수 설정 json으로 변하게 하지 않음
					, contentType : false // 파일 업로드를 위한 필수 설정 json으로 변하게 하지 않음
					// response
					, success : function(data) {
						if (data.code == 1) {
							alert("메모가 수정되었습니다.");
							location.reload(true); // 새로고침
						} else {
							alert(data.errorMessage);
						}
					}
					, error : function(request, status, error) {
						alert("관리자에게 문의바랍니다.");
					}
				});
				
			});
		});
	</script>
</div>