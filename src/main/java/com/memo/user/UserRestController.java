package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.common.EncryptUtils;
import com.memo.user.bo.UserBO;
import com.memo.user.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@RestController
public class UserRestController {
	@Autowired
	UserBO userBO;
	
	/**
	 * 중복확인 api
	 * @param loginId
	 * @return
	 */
	@RequestMapping("/is_duplicated_id") // get , post 둘다 수용가능
	public Map<String, Object> idDuplicatedId(
			@RequestParam("loginId") String loginId) {
		Map<String, Object> result = new HashMap<>();
			
		// select
		User user = null;
		
		user = userBO.getUserByLoginId(loginId);
		
		if(user != null) {
			result.put("code", 1);
			result.put("result", true);
			
		} else {
			result.put("code", 500);
			result.put("result", false);
		}
		
		return result;
	}
	
	/**
	 * 회원가입 api
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/sign_up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId
			, @RequestParam("password") String password
			, @RequestParam("name") String name
			, @RequestParam("email") String email) {
		Map<String, Object> result = new HashMap<>();

		// 비밀번호 해싱 -> 보완적으로 취약한 알고리즘임 개인때는 제일 보완적으로 좋은 알고리즘 사용 아마 springsecurity
		String hashedPassword = EncryptUtils.md5(password); // static임
		
		int rowCnt = 0;
		// DB insert
		rowCnt = userBO.insertUser(loginId, hashedPassword, name, email);
		
		//rowCnt 활용 하여 분기
		if (rowCnt > 0) {
			result.put("code", 1);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("errorMessage", "회원가입에 실패하였습니다.");
		}

		return result;
	}
	
	@PostMapping("/sign_in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId
			, @RequestParam("password") String password
			, HttpServletRequest request) {
		
		Map<String, Object> result = new HashMap<>();
		//password 를 해싱해야 함
		String hashedPassword = EncryptUtils.md5(password);
		
		// select DB
		User user = null;
		user = userBO.getUserByLoginIdAndPassword(loginId, hashedPassword);
		
		if (user != null) {
			result.put("code", 1);
			result.put("result", "성공");	
			
			// 세션에 유저 정보 담기 (로그인 상태 유지)
			HttpSession session = request.getSession();
			
			// 이 데이터들이 있어야 개인 페이지 목록에 들어갔다는 분기를 할 수 있음
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userName", user.getName());
			
			
		} else {
			result.put("code", 500);
			result.put("errorMessage", "로그인에 실패하였습니다.");
		}
		
		// 로그인 처리

		return result;
	}
	
}
