package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.dao.PostMapper;
import com.memo.post.model.Post;

@Service
public class PostBO {
	
	@Autowired
	private PostMapper postMapper;

	@Autowired 
	private FileManagerService fileManager;
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
		if (file != null) {
			// 서버에 이미지 업로드 후 imagaPath 받아옴
			imagePath = fileManager.saveFile(userloginId, file);
		}
		return postMapper.insertPost(userId, subject, content, imagePath);	
	}

	public List<Post> getPostList() {
		return postMapper.selectPostList();
	}

	public Post getPostByPostIdAndUserId(int postId, int userId) {
		// TODO Auto-generated method stub
		return postMapper.selectPostByPostIdAndUserId(postId, userId);
	}

}
