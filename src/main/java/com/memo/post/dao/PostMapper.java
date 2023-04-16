package com.memo.post.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface PostMapper {
	
	public List<Map<String, Object>> selectPostList();

	public int insertPost(
			@Param("userId") int userId
			, @Param("userloginId") String userloginId
			, @Param("subject") String subject
			, @Param("content") String content
			, @Param("imagePath") MultipartFile file);
}
