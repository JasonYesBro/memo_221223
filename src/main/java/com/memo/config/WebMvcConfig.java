package com.memo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.common.FileManagerService;

@Configuration // 설정을 위한 어노테이션 spring bean
public class WebMvcConfig implements WebMvcConfigurer{
	
	// 서버에 업로드 된 이미지와 웹 이미지 주소와의 매핑 설정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry
			.addResourceHandler("/images/**") // 그 뒤에 무언가가 있다 ** 웹이미지 주소
			.addResourceLocations("file://http:" + FileManagerService.FILE_UPLOAD_PATH); // mac은 // 두개임 윈도우는 /// 3개
	}
}
