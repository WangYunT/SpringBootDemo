package com.example.demo.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.common.BaseResponse;

public interface UploadService {
	BaseResponse upload(MultipartFile file, String idcard);
	
	BaseResponse delete(String path);
	
	BaseResponse fileList(String idcard);
	
	BaseResponse download(HttpServletResponse response, String url);
	
	BaseResponse manyUpload(MultipartFile[] files, String idcard);
}
