package com.memo.post.bo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.dao.PostMapper;
import com.memo.post.model.Post;

@Service
public class PostBO {
	//private Logger logger = LoggerFactory.getLogger(PostBO.class);
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
		return postMapper.selectPostByPostIdAndUserId(postId, userId);
	}

	public void updatePost(int postId, int userId, String userLoginId, String subject, String content, MultipartFile file) {
		// 기존글을 가져온다.(이미지가 교체될때 기존 이미지를 제거하기 위함)
		Post post = getPostByPostIdAndUserId(postId, userId); // 실무에서는 select 를 하는 것이 부담이기 때문에 캐시라는 임시 바구니에 담아놓는다. 그것을 BO단계에서 진행하기 때문에 BO의 메서드를 불러옴 or BO에서 많은 가공을 하고 있기때문에 BO의 가공된 값을 사용하기 위해.
		logger.warn("[update post] post is null. postId: {}. userId: {}", postId, userId);
		// 가져온post가 없다면 -> null 체크
		if (post == null) {
			logger.warn("[update post] post is null. postId: {}. userId: {}", postId, userId);
			return; // return type이 void이기 때문에 
		}
		// 업로드한 이미지가 있으면 서버 업로드 -> imagePath받아옴.
		// 업로드가 성공한다면 기존 이미지를 제거함 -> 무작정 제거하지 마쇼.
		String imagePath = null;
		
		if (file != null) {
			// 업로드
			imagePath = fileManager.saveFile(userLoginId, file);
			
			// 성공여부 체크 후 기존 이미지 제거
			// -- imagePath가 null이 아닐 때 (성공) 그리고 기존 image가 있을 때 -> 기존이미지 삭제
			if (imagePath != null && post.getImagePath() != null) {

				// 기존 이미지 제거하기 때문에 파라미터 조심
				fileManager.deleteFile(post.getImagePath());
			}
		}
		
		// DB update
		// 원래 이미지가 Null 인데 사용자가 또 null로 집어 넣을 수 있게 하기 위해 DB에서 구현함
		postMapper.updatePostByPostId(postId, subject, content, imagePath);
	}

}
