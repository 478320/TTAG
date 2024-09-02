package com.example.uploadservice.service.impl;

import com.example.uploadservice.service.IUploadService;
import com.example.uploadservice.utils.UploadUtil;
import com.ttag.api.domain.dto.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 上传业务层实现类
 */
@Service
public class UploadService implements IUploadService {

    @Override
    public Result uploadImage(MultipartFile file, HttpServletRequest request) {
        //判断文件是否符合格式
        if (UploadUtil.isImage(file)) {
            try {
                String uploadImage = UploadUtil.uploadImage(file);
                return Result.ok("上传图片成功", uploadImage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return Result.fail("图片格式错误，上传图片失败");
    }

}
