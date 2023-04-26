package com.memo.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.post.bo.PostBO;
import com.memo.post.model.Post;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@Controller
public class PostController {
	
	@Autowired
	PostBO postBO;
	
	@GetMapping("/post_list_view")
	public String postListView(
			@RequestParam(value="prevId", required=false) Integer prevIdParam
			, @RequestParam(value="nextId", required=false) Integer nextIdParam
			, Model model
			, HttpSession session) {
		
		Integer userId = (Integer)session.getAttribute("userId");
		
		if ( userId == null ) {
			// 비로그인 상태라면
			return "redirect:/user/sign_in_view";
		}
		
		List<Post> postList = postBO.getPostListByUserId(userId, prevIdParam, nextIdParam);
		
		int prevId = 0;
		int nextId = 0;
		
		// postList is not null		
		if (postList.isEmpty() == false) {
			prevId = postList.get(0).getId(); // 가져온 리스트의 가장 맨앞의 값
			nextId = postList.get(postList.size()-1).getId(); // 가져온 리스트의 가장 끝 값
			
			// 이전 방향의 끝인가?
			// prevId와 post테이블의 가장 큰 id와 같다면 이전 페이지 없음
			if (postBO.isPrevLastPage(userId, prevId)) {
				prevId = 0; // 0으로 초기화하여 이전 태그 노출x
				
			}
			
			// 다음 방향의 끝인가?
			// nextId와 post테이블의 가장 작은 id와 같다면 다음 페이지 없음
			if (postBO.isNextLastPage(userId, nextId)) {
				nextId = 0;
			}
		}
		
		// postList 받아오기
		model.addAttribute("prevId", prevId);
		model.addAttribute("nextId", nextId);
		model.addAttribute("postList", postList);
		model.addAttribute("view", "post/postList");
		return "template/layout";
	}
	
	
	/**
	 * 글쓰기 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/post_create_view")
	public String postCreateView(Model model) {
		model.addAttribute("view", "post/postCreate");
		return "template/layout";
	}
	
	@GetMapping("/post_detail_view")
	public String postDetailView(
			@RequestParam("postId") int postId
			, Model model
			, HttpSession session) {
		// postId에 해당하는 post를 가져와야 함 by postId ,(userId 더 안전)
		int userId = (int)session.getAttribute("userId");
		
		// session 이 없을 때 redirect 처리
		
		Post post = null;
		post = postBO.getPostByPostIdAndUserId(postId, userId);
		
		model.addAttribute("post", post);
		model.addAttribute("view", "post/postDetail");
		return "template/layout";
	}
	
}
