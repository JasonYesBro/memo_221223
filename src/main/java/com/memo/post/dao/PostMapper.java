package com.memo.post.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.model.Post;

@Repository
public interface PostMapper {
	
	public List<Post> selectPostList();

	public int insertPost(
			@Param("userId") int userId
			, @Param("subject") String subject
			, @Param("content") String content
			, @Param("imagePath") String imagePath);

	public Post selectPostByPostIdAndUserId(
			@Param("postId") int postId
			, @Param("userId") int userId);

	public void updatePostByPostId(
			@Param("postId") int postId
			, @Param("subject") String subject
			, @Param("content") String content
			, @Param("imagePath") String imagePath);
}
