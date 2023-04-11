package com.memo.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memo.post.dao.PostMapper;

@Controller
public class TestController {
	
	@Autowired
	PostMapper postMapper;
	
	@ResponseBody
	@GetMapping("/test1")
	public String testString() {
		
		// 문자열 반환 테스트
		return "hello world";
	}
	
	@ResponseBody
	@GetMapping("/test2")
	public Map<String, Object> testMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("a", 1);
		map.put("b", 2);
		map.put("c", 3);
		map.put("d", 4);
		// jack lib 테스트
		return map;
	}
	
	@GetMapping("/test3")
	public String testView() {
		return "test/test";
	}
	
	@ResponseBody
	@RequestMapping("/test4")
	public List<Map <String, Object>> testDb() {
		// 빠르게 테스트하기 위해 Map 자료구조를 이용 & service단계 생략
				
		return postMapper.selectPostList();
	}
	
	
}
