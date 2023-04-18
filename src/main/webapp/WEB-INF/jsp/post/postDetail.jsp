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

		<div class="d-flex justify-content-between">
			<button type="button" id="postDeleteBtn" class="btn btn-secondary">삭제</button>

			<div>
				<a href="/post/post_list_view" class="btn btn-dark">목록</a>
				<button type="button" id="saveBtn" class="btn btn-warning">저장</button>
			</div>
		</div>
	</div>
</div>