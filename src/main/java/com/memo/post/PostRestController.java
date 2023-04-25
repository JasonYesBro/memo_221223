package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.bo.PostBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@RestController
public class PostRestController {
	
	@Autowired
	private PostBO postBO;
	
	/**
	 * 글쓰기 API
	 * @param subject
	 * @param content
	 * @param file
	 * @param session
	 * @return
	 */
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("subject") String subject,
			@RequestParam(value="content", required=false) String content,
			@RequestParam(value="file", required=false) MultipartFile file,
			HttpSession session) {
		
		// 세션에서 유저 정보 꺼내옴
		int userId = (int)session.getAttribute("userId");
		String userloginId = (String)session.getAttribute("userLoginId");
		
		// db insert
		int rowCount = postBO.addPost(userId, userloginId, subject, content, file);
		
		// 응답
		Map<String, Object> result = new HashMap<>();
		if (rowCount > 0) {
			result.put("code", 1);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "메모를 저장하지 못했습니다.");
		}
		return result;
	}
	
	/**
	 * 게시글 수정하기 API
	 * @param postId
	 * @param subject
	 * @param content
	 * @param file
	 * @param session
	 * @return
	 */
	@PutMapping("/update")
	public Map<String, Object> update(
			@RequestParam("postId") int postId,
			@RequestParam("subject") String subject,
			@RequestParam(value="content", required=false) String content,
			@RequestParam(value="file", required=false) MultipartFile file,
			HttpSession session) {
		// session의 userId 를 이용하여 userId가 쓴 post인 것을 찾기 위함
		
		int userId = (int)session.getAttribute("userId"); // 무조건 있어야 하므로 int
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		// update DB
		postBO.updatePost(postId, userId, userLoginId, subject, content, file);
		
		Map<String, Object> result = new HashMap<>();
		result.put("code", 1);
		result.put("result", "성공");
		
		return result;
	}
	
	@DeleteMapping("/delete")
	public Map<String, Object> delete(
			@RequestParam("postId") int postId
			, HttpSession session) {
		Map<String, Object> result = new HashMap<>();
		
		int userId = (int)session.getAttribute("userId");
		int rowCnt = postBO.deletePostByPostIdAndUserId(postId, userId);
		
		if (rowCnt > 0) {
			result.put("code", 1);
			result.put("result", "성공");			
		} else {
			result.put("code", 500);
			result.put("errorMessage", "댓글 삭제하는데 실패하였습니다.");
		}
		
		return result;
	}

}
