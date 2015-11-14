package com.fnx.webapp.controller.open.file;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fnx.webapp.controller.BaseController;
import com.fnx.webapp.model.common.ResponseModel;
import com.fnx.webapp.model.common.UploadImageModel;
import com.fnx.webapp.util.WebConstants;
import com.fnx.webapp.util.WebappPropertiesUtil;

import de.schlichtherle.truezip.file.TFile;

@RestController
@RequestMapping("/open")
public class FileController extends BaseController {

	public static final String IMAGE_NAME = "images";
	
	@ResponseBody
	@RequestMapping(value = "/image/upload", method = RequestMethod.POST)
	public ResponseModel uploadImage(MultipartFile imgFile)
			throws Exception {
		return uploadImageFile(imgFile);
	}	
	
	private ResponseModel uploadImageFile(MultipartFile imgFile) {
		ResponseModel responseModel = new ResponseModel();
		String originalFilename = imgFile.getOriginalFilename();
		if (!(StringUtils.endsWithIgnoreCase(originalFilename, ".png") || StringUtils.endsWithIgnoreCase(originalFilename, ".jpg")
				|| StringUtils.endsWithIgnoreCase(originalFilename, ".gif") || StringUtils.endsWithIgnoreCase(originalFilename, ".jpeg"))) {
			responseModel.setResponseCodeFailure();
			responseModel.setMsg(retrieveMessage("file.error.notSupport"));
			return responseModel;
		}
		try {
			InputStream inputStream = imgFile.getInputStream();
			StringBuilder imageFolderBuilder = new StringBuilder(80);
			imageFolderBuilder.append(WebappPropertiesUtil.getUploadPath());
			imageFolderBuilder.append(File.separator);
			imageFolderBuilder.append(FileController.IMAGE_NAME);
			String imageFolder = imageFolderBuilder.toString();
			// append three random number
			StringBuilder originalFilenameBuffer = new StringBuilder(30);
			originalFilenameBuffer.append(RandomStringUtils.random(5, true, true));
			originalFilenameBuffer.append("_").append(originalFilename);
			String originalFilenameAndSuffix = originalFilenameBuffer.toString();
			String imagePath = imageFolderBuilder.append(File.separator).append(originalFilenameAndSuffix).toString();
			TFile imageFolderFile = new TFile(imageFolder);
			imageFolderFile.mkdirs();
			TFile imageFile = new TFile(imagePath);
			TFile.cp(inputStream, imageFile);
			UploadImageModel uploadImageModel = new UploadImageModel();
			uploadImageModel.setPath(originalFilenameAndSuffix);
			uploadImageModel.setName(originalFilename);
			responseModel.setData(uploadImageModel);
			responseModel.setMsg(this.retrieveSuccessMessage());
			return responseModel;
		} catch (IOException e) {
			responseModel.setCode(WebConstants.RESPONSE_CODE_FAILURE);
			responseModel.setMsg(retrieveMessage("file.error"));
			return responseModel;
		}
	}	
}
