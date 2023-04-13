package com.memo.user.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.memo.user.model.User;

@Repository
public interface UserMapper {
	public User selectUserByLoginId(String loginId);

	public int insertUser(
			@Param("loginId") String loginId
			, @Param("password") String password
			, @Param("name") String name
			, @Param("email") String email);

	public User selectUserByLoginIdAndPassword(
			@Param("loginId") String loginId
			, @Param("password") String password);
}
