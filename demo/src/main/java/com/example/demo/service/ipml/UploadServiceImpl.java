package com.example.demo.service.ipml;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.common.BaseResponse;
import com.example.demo.common.ResponseCode;
import com.example.demo.exception.BusinessException;
import com.example.demo.service.UploadService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UploadServiceImpl implements UploadService {
	
	 @Value("${FILEPATHConfig.UPLOAD_FLODER}")
	 private String UPLOAD_FLODER;
	 
	 private static final String SLASH = "/";
		
	/**
	 * 单文件上传demo
	 * @author wy
	 * @param MultipartFile file
	 * 
	 * @return fileReturnPath
	 */
	@Override
	public BaseResponse upload(MultipartFile file, String idcard) {
		
		StringBuilder folderNamePath = new StringBuilder(UPLOAD_FLODER + SLASH + idcard);
		
		String originalFilename = file.getOriginalFilename();
		
		StringBuilder returnPath = new StringBuilder(folderNamePath + SLASH+idcard+SLASH + originalFilename);
		
		File folderNameDir = new File(folderNamePath.toString());
        if (!folderNameDir.exists()) {
            folderNameDir.mkdirs();
        }
        
        File filer = new File(folderNameDir, originalFilename);
       
        if (filer.exists()) {
            return BaseResponse.createByErrorMessage("The file exist");
        }
        try {
            file.transferTo(filer);
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.UPLODAFAIL.getCode(), ResponseCode.UPLODAFAIL.getDesc());
        }

        return BaseResponse.createBySuccess("upload success", returnPath.toString());
	}
	
	/**
	 * 文件删除
	 * @param path
	 * 
	 */
	@Override
	public BaseResponse delete(String path) {
		 File fileDirectory = new File(path);
	        File[] files = fileDirectory.listFiles();
	        if (files != null) {
	            for (File file : files) {
	                if (file.isFile()) {
	                    file.delete();
	                } else if (file.isDirectory()) {
	                    this.delete(file.getAbsolutePath());
	                }
	            }
	        }
	        if (!"upload".equals(fileDirectory.getName())) {
	            fileDirectory.delete();
	        }
	        return BaseResponse.createBySuccess(ResponseCode.SUCCESS.getCode(), "delete success");
	}
	
	/**
	 * 查看指定文件夹下文件列表
	 * @param idcard
	 * @return filename list
	 */
	@Override
	public BaseResponse fileList(String idcard) {
		String fileRootPath =  UPLOAD_FLODER + SLASH + idcard;
        String returnRootPath = "";
        
        File file = new File(fileRootPath);
        List<String> fileNames = new ArrayList<String>();
        Map<Long,String> pathMaps = new TreeMap<>();

        File[] listFiles = file.listFiles();
		
        if (listFiles != null) {
            if (listFiles != null && listFiles.length > 0) {
                for (File f : listFiles) {
                    pathMaps.put(f.lastModified(),f.getName());
                }
                for (Map.Entry<Long,String> entry: pathMaps.entrySet()){
                    fileNames.add(returnRootPath + SLASH + entry.getValue());
                }
            }
        } else {
            return BaseResponse.createByErrorMessage(ResponseCode.NULLERROR.getCode(), ResponseCode.NULLERROR.getDesc());
        }
        return BaseResponse.createBySuccess(ResponseCode.SUCCESS.getCode(), fileNames);
	}

	@Override
	public BaseResponse download(HttpServletResponse response, String url) {
		String completeUrl = UPLOAD_FLODER + SLASH + url;
		File file = new File(completeUrl);
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        if (file.exists()) {
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attchment;fileName=" + fileName);

            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null;
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return BaseResponse.createByErrorMessage(ResponseCode.FILE_PATH_ERROR.getCode(), ResponseCode.FILE_PATH_ERROR.getDesc());
        }
        return null;
	}

	/**
	 * 多文件上传
	 * 
	 */
	@Override
	public BaseResponse manyUpload(MultipartFile[] files, String idcard) {
		if (idcard == null || "".equals(idcard)) {
			throw new BusinessException("","");
		}
		for(int i = 0;i<files.length;i++){
			if ((files[i].getSize()) == 0) {
				throw new BusinessException("","");
			}
		}
		try {
			idcard = URLEncoder.encode(idcard, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
			throw new BusinessException("","");
		}
		String folderNamePath = UPLOAD_FLODER + SLASH + idcard;
		File folderNameDir = new File(folderNamePath);
		if (!folderNameDir.exists()) {
			folderNameDir.mkdirs();
		}
		List<String> servicePath = new ArrayList<>();
		InputStream stream = null;
		if (files != null && files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				MultipartFile multipartFile = files[i];
				try {
					stream = multipartFile.getInputStream();
				} catch (IOException e) {
					
				} finally {
					try {
						stream.close();
					} catch (IOException e) {
						
					}
				}
				String originalFilename = multipartFile.getOriginalFilename();
				String fileSuf = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
				
				File file = new File(folderNameDir, originalFilename);
				if (file.exists()) {
					 return BaseResponse.createByErrorMessage("The file exist");
				}
				try {
					multipartFile.transferTo(file);
				} catch (Exception e) {
					
					throw new BusinessException("","");
				}
				String uploadFilePath = UPLOAD_FLODER + SLASH + originalFilename;
				servicePath.add(uploadFilePath.replaceAll("\\\\", "/"));
			}
			
		}

	    return BaseResponse.createBySuccess("upload success", servicePath.toString());
	}

}
