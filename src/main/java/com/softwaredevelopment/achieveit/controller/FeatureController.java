package com.softwaredevelopment.achieveit.controller;

import com.softwaredevelopment.achieveit.entity.FeatureVO;
import com.softwaredevelopment.achieveit.http.response.HttpResponse;
import com.softwaredevelopment.achieveit.service.FeatureService;
import com.softwaredevelopment.achieveit.utils.IOUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @author RainkQ
 * @date 2020/3/25 17:21
 */
@Api(tags = "功能接口")
@RequestMapping("feature")
@RestController
public class FeatureController extends BaseController {
    @Autowired
    FeatureService featureService;

    @ApiOperation("通过projectId(11位) 取得项目的所有Feature")
    @GetMapping("features_by_project_id")
    public HttpResponse<List<FeatureVO>> getFeaturesByProjectId(String projectId) {
        return responseOK(featureService.getFeaturesByProjectId(projectId));
    }


    @ApiOperation("上传功能的excel文件并解析存入数据库")
    @PostMapping("upload_features")
    public HttpResponse<Object> uploadFeatures(MultipartFile file, String projectId) {
        try {
            String newName = projectId + ".xlsx";
            // 组装路径
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            File upload = new File(path.getAbsolutePath(), "static/excels/");
            if (!upload.exists()) {
                upload.mkdirs();
            }
            String uploadPath = upload + "\\";


            file.transferTo(new File(uploadPath + newName));
            featureService.uploadFeature(file, projectId);
            return responseOK("上传成功!");
        } catch (IOException e) {
            return responseFail("上传失败 :" + e);
        }
    }

    @ApiOperation("下载项目的功能excel")
    @GetMapping("download_features")
    public Object downloadFeatures(String projectId) {
        String newName = projectId + ".xlsx";
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            // 应该不会发生
            responseFail("项目执行有问题");
        }
        File upload = new File(path.getAbsolutePath(), "static/excels/");
        String uploadPath = upload + "\\";
        String fileName = uploadPath + newName;


        try {
            FileSystemResource file = new FileSystemResource(fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", newName));
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");


            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(file.contentLength())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new ByteArrayResource(IOUtils.readAllBytes(file.getInputStream())));

        } catch (IOException e) {
            return responseFail("文件不存在");
        }
    }
}
