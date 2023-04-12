package com.memo.user.dao;

import org.springframework.stereotype.Repository;

import com.memo.user.model.User;

@Repository
public interface UserMapper {
	public User selectUserByLoginId(String loginId);
}
