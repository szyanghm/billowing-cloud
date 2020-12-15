package com.red.dust.billowing.controller;


import com.red.dust.billowing.common.LocalProvider;
import com.red.dust.billowing.common.SysUser;
import com.red.dust.billowing.enums.SystemErrorType;
import com.red.dust.billowing.utils.BaseUtils;
import com.red.dust.billowing.utils.CosFileUtil;
import com.red.dust.billowing.utils.UploadUtils;
import com.red.dust.billowing.vo.FileVO;
import com.red.dust.billowing.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/file")
public class UploadController {

    @Autowired
    private UploadUtils uploadUtils;

    @Autowired
    private CosFileUtil cosFileUtil;

    @RequestMapping("/upload")
    public ResultVO<FileVO> upload(@RequestParam("file") MultipartFile file,@RequestParam("type")String type){
        ResultVO<FileVO> resultVO = uploadUtils.uploadFile(file);
        if(!resultVO.getCode().equals(ResultVO.SUCCESSFUL_CODE)){
            return resultVO;
        }
        Map<String,String> map = new HashMap<>();
        FileVO fileVO =resultVO.getData();
        SysUser sysUser = LocalProvider.getUser();
        String fileUrl = UploadUtils.getFileUrl(sysUser.getUserId(),type,fileVO.getExtName());
        String url = cosFileUtil.fileUpload(fileVO.getBytes(),fileUrl);
        map.put("url", url);
        log.info(url);
        return ResultVO.success(map);
    }

    @GetMapping(value = "/delete")
    public ResultVO delete(@RequestParam("key") String key){
        cosFileUtil.delete(key);
        return ResultVO.success();
    }

}
