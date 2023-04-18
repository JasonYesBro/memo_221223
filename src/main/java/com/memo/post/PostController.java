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
	public String postListView(Model model, HttpSession session) {
		
		Integer userId = (Integer)session.getAttribute("userId");
		
		if ( userId == null ) {
			// 비로그인 상태라면
			return "redirect:/user/sign_in_view";
		}
		
		List<Post> postList = postBO.getPostList();
		
		// TODO postList 받아오기
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
		
		Post post = null;
		post = postBO.getPostByPostIdAndUserId(postId, userId);
		
		model.addAttribute("post", post);
		model.addAttribute("view", "post/postDetail");
		return "template/layout";
	}
}
