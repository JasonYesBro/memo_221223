package com.memo.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
