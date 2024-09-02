package com.example.uploadservice.service;

import com.ttag.api.domain.dto.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 上传业务层
 */
public interface IUploadService {
    Result uploadImage(MultipartFile file, HttpServletRequest request);
}
