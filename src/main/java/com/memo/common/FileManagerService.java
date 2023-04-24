package com.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component // spring bean Autowired 사용가능
public class FileManagerService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// 실제 업로드 된 이미지가 저장될 경로(서버)
	public static final String FILE_UPLOAD_PATH = "/Users/jasonmilian/Desktop/megaProject/6_spring_project/memo/workspace/images/";
	
	
	// input : MultipartFile(이미지파일), loginId
	// userId + 시간으로 폴더를 만들기 위해
	// output : image path(String)
	public String saveFile(String loginId, MultipartFile file) {
		// 파일 디렉토리 (폴더) ex) tmdgus5611_141205/star.png
		String dirName = loginId + "_" + System.currentTimeMillis() + "/"; // 사용자의 id + 저장한 시간으로 폴더명을 지음
		String filePath = FILE_UPLOAD_PATH + dirName; // /Users/jasonmilian/Desktop/megaProject/6_spring_project/memo/workspace/images/tmdgus5611_141205/
		
		File directory = new File(filePath);
		if (directory.mkdir() == false) { // return boolean
			return null; // bo 쪽이랑 연관 -> 폴더 만드는데 실패 시 이미지경로가 null이 된다는의미
		}
		
		// 파일 업로드 : byte 단위로 업로드 됨
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(filePath + file.getOriginalFilename()); // 디렉토리명 + 파일명.확장자 getOriginalFilename은 사용자가 올린 파일명그대로 올리겠다. -> 파일명을 인코딩을 하는 로직을 내가 짜야함
			Files.write(path, bytes); // path를 bytes 단위로 올린다. -> 파일 업로드
					
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		// 파일 업로드가 성공했으면 이미지 url path를 리턴한다.
		// http://localhost/images/loginId_141205/star.png
		return "/images/" + dirName + file.getOriginalFilename();
	}

	// input : String imagePath , output : X
	public void deleteFile(String imagePath) { // /images/tmdgus5611_141205/sun.png
		// /Users/jasonmilian/Desktop/megaProject/6_spring_project/memo/workspace/images/
		// 겹치는 Images경로를 제거
		// imagePath 안에있는 /images/ 제거
		Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));
		
		// 이미지를 제거하고 폴더를 제거해줘야함
		if (Files.exists(path)) {
			// 부른쪽에서 해결하지 않고 여기서 해결함
			try {
				Files.delete(path);
			} catch (IOException e) {
				logger.error("[delete file] 이미지 삭제 실패. imagePath : {}", imagePath);
				return; // 수행안한다.
			}
			
		} 
		
		// 디렉토리 삭제
		path = path.getParent(); // 이미지가 있는 상위폴더
		if (Files.exists(path)) {
			try {
				Files.delete(path); // 폴더 삭제
			} catch (IOException e) {
				logger.error("[delete dir] 폴더 삭제 실패. path : {}", path);
				return;
			}
		}
	}
}
