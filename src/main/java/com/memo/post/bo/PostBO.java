package com.memo.post.bo;

import java.util.Collections;
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
	
	// 상수 선언 페이징 갯수 설정
	private static final int POST_MAX_SIZE = 3;
	
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

	// 페이지 클래스 따로 만들어서 만드는게 더 나음
	// 페이징 처리
	public List<Post> getPostListByUserId(int userId, Integer prevId, Integer nextId) {
		
		// 게시글 번호가 최신순으로 넘어오기 때문에 10 9 8 \ 7 6 5 \ 4 3 2 \ 1
		// 만약 4 3 2 페이지에 있을 때
		// 1) 다음 : 2보다 작은 3개 DESC
		
		
		// 2) 이전 : 4보다 큰 3개 ASC(5, 6, 7) -> List reverse (7, 6, 5)
		
		
		String direction = null;  // 방향
		Integer standardId = null; // 기준 postId
		
		if (prevId != null) {
			direction = "prev";
			standardId = prevId;
			
			List<Post> postList = postMapper.selectPostListByUserId(userId, direction, standardId, POST_MAX_SIZE);
			
			// 가져온 list 를 뒤집는다. 5 6 7 -> 7 6 5
			Collections.reverse(postList); // 저장까지 해주는 메서드임, void인데 설명서 읽어봐라
			
			// return 결과 -> 메소드 종료
			return postList;
			
		} else if (nextId != null) {
			direction = "next";
			standardId = nextId;
			
		}
		// 3) 만약 첫 페이지일 때 (이전, 다음 없음) DESC 3개
		
		
		return postMapper.selectPostListByUserId(userId, direction, standardId, POST_MAX_SIZE);
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
	
	public int deletePostByPostIdAndUserId(int postId, int userId) {
		Post post = getPostByPostIdAndUserId(postId, userId); // 실무에서는 select 를 하는 것이 부담이기 때문에 캐시라는 임시 바구니에 담아놓는다. 그것을 BO단계에서 진행하기 때문에 BO의 메서드를 불러옴 or BO에서 많은 가공을 하고 있기때문에 BO의 가공된 값을 사용하기 위해.
		
		// 가져온post가 없다면 -> null 체크
		if (post == null) {
			logger.warn("[delete post] post is null. postId: {}. userId: {}", postId, userId);
			return 0;
		}
		
		String imagePath = null;
		// 삭제할 시에 폴더의 이미지 또한 같이 삭제해줘야 함
		imagePath = post.getImagePath();

		// -- imagePath가 null이 아닐 때 (성공) 그리고 기존 image가 있을 때 -> 기존이미지 삭제
		if (imagePath != null) {
			// 기존 이미지 제거하기 때문에 파라미터 조심
			fileManager.deleteFile(post.getImagePath());
		}
		return postMapper.deletePostByPostIdAndUserId(postId, userId);
	}

	public boolean isPrevLastPage(int userId, int prevId) {
		int postId = postMapper.selectPostIdByUserIdSort(userId, "DESC");
		return postId == prevId; // true 면 끝 false 면 끝 아님
		
	}
	
	public boolean isNextLastPage(int userId, int nextId) {
		int postId = postMapper.selectPostIdByUserIdSort(userId, "ASC");
		return postId == nextId; // true 면 끝 false 면 끝 아님
	}

}
