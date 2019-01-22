package com.example.demo.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.common.BaseResponse;
import com.example.demo.common.ResponseCode;
import com.example.demo.service.UploadService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping(value = "/file")
public class UploadController {

	@Value("${FILEPATHConfig.UPLOAD_FLODER}")
	private String UPLOAD_FLODER;

	private static final String SLASH = "/";

	@Autowired
	UploadService uploadService;

	/**
	 * 单文件上传
	 * 
	 * @param file
	 * @param idcard
	 * @return filePath
	 * @exception 同名文件上传会被拒绝
	 */
	@PostMapping("/upload")
	public BaseResponse singleFileUpload(@RequestParam(value = "file") MultipartFile file, String idcard) {
		if (file.getSize() == 0 || StringUtils.isEmpty(idcard)) {
			return BaseResponse.createByErrorMessage("please input folder name");
		}

		return uploadService.upload(file, idcard);
	}

	@PostMapping("/multiFileupload")
	public BaseResponse multiFileUpload(@RequestParam(value = "file") MultipartFile[] files, String idcard) {
		if (files.length == 0 || StringUtils.isEmpty(idcard)) {
			return BaseResponse.createByErrorMessage("please input folder name");
		}

		return uploadService.manyUpload(files, idcard);
	}

	/**
	 * 删除指定文件夹下所有文件
	 * 
	 * @param idcard
	 * @return msg
	 */
	@GetMapping("/delete")
	public BaseResponse delete(@RequestParam(value = "idcard") String idcard) {
		String fileRootPath = UPLOAD_FLODER + SLASH + idcard;
		return uploadService.delete(fileRootPath);
	}

	/**
	 * 查询方案内的可下载资源
	 *
	 * @param idcard
	 * @return
	 */
	@GetMapping("/list")
	public BaseResponse fileList(@RequestParam(value = "idcard") String idcard) {
		return uploadService.fileList(idcard);
	}

	/**
	 * 根据url下载文件
	 *
	 * @param responseServlet Respon 用来承载返回文件的内容
	 * @param url
	 *            需要下载文件的路径
	 * @return
	 */
	@GetMapping(value = "/download")
	public BaseResponse downLoad(HttpServletResponse response, @RequestParam String url) {
		if (StringUtils.isEmpty(url)) {
			return BaseResponse.createByErrorMessage(ResponseCode.NULLERROR.getCode(),
					ResponseCode.NULLERROR.getDesc());
		}
		return uploadService.download(response, url);
	}
}
