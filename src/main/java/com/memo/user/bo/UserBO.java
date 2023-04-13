package com.memo.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.user.dao.UserMapper;
import com.memo.user.model.User;

@Service
public class UserBO {
	
	@Autowired
	UserMapper userMapper;
	
	public User getUserByLoginId(String loginId) {
		
		return userMapper.selectUserByLoginId(loginId);
	}

	public int insertUser(String loginId, String password, String name, String email) {
		
		return userMapper.insertUser(loginId, password, name, email);
		
	}

	public User getUserByLoginIdAndPassword(String loginId, String password) {
		return userMapper.selectUserByLoginIdAndPassword(loginId, password);
	}
}
