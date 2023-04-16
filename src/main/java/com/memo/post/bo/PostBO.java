package com.memo.post.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.dao.PostMapper;

@Service
public class PostBO {
	
	@Autowired
	PostMapper postMapper;

	/**
	 * @param userId
	 * @param userloginId
	 * @param subject
	 * @param content
	 * @param file
	 * @return
	 */
	public int addPost(int userId, String userloginId, String subject, String content, MultipartFile file) {
		
		String imagePath = null;
		// TODO 서버에 이미지 업로드 후 imagaPath 받아옴
		if (file != null) {
			
		}
		return postMapper.insertPost(userId, userloginId, subject, content, file);	
	}
	
//	public List<Post> getPostList() {
//		return postBO.
//	}
	
}
