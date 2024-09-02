package com.example.uploadservice.controller;

import com.example.uploadservice.service.IUploadService;
import com.ttag.api.domain.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 上传表现层
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private IUploadService uploadImageService;

    /**
     * 上传图片
     *
     * @param file 要上传的图片
     * @return 图片的地址信息
     */
    @PostMapping("/add")
    public Result uploadImage(@RequestBody MultipartFile file, HttpServletRequest request) {
        return uploadImageService.uploadImage(file,request);
    }
}
