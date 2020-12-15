package com.red.dust.billowing.utils;

import com.red.dust.billowing.common.Contents;
import com.red.dust.billowing.common.LocalProvider;
import com.red.dust.billowing.common.SysUser;
import com.red.dust.billowing.enums.SystemErrorType;
import com.red.dust.billowing.vo.FileVO;
import com.red.dust.billowing.vo.ResultVO;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.events.Comment;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传工具类
 *
 * @author yangdc
 * @date Apr 18, 2012
 *
 * <pre>
 * </pre>
 */
@Component
public class UploadUtils {

    // 文件的目录名
    private String dirName = "images";

    // 定义允许上传的文件扩展名
    private Map<String, String> extMap = new HashMap<>();

    public UploadUtils() {
        // 其中images,flashs,medias,files,对应文件夹名称,对应dirName
        // key文件夹名称
        // value该文件夹内可以上传文件的后缀名
        extMap.put("images", "gif,jpg,jpeg,png,bmp");
        extMap.put("flashs", "swf,flv");
        extMap.put("medias", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("files", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
    }


    public ResultVO<FileVO> uploadFile(MultipartFile multipartFile) {
        FileVO fileVO = new FileVO();
        if (null == multipartFile || multipartFile.isEmpty()) {
            return new ResultVO<>(SystemErrorType.UPLOAD_NOT_FOUND);
        }
        // 文件名
        String fileName = multipartFile.getOriginalFilename();
        // 文件后缀名
        String extName = FilenameUtils.getExtension(fileName);
        if (StringUtils.isEmpty(extName)) {
            return new ResultVO<>(SystemErrorType.FILE_TYPE_NOT);
        }
        // 判断文件的后缀名是否符合规则
        validateFile(extName);
        try {
            fileVO.setBytes(multipartFile.getBytes());
            fileVO.setFileName(fileName);
            fileVO.setExtName(extName);
            fileVO.setFileSize(multipartFile.getSize());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultVO.success(fileVO);
    }

    public static String getFileUrl(String userId,String type,String fileType){
        StringBuffer fileUrl = new StringBuffer(Contents.COS_FOLDER_NAME).append(userId);
        if(StringUtils.isNotBlank(type)){
            fileUrl.append("/").append(type);
        }
        fileUrl.append("/").append(System.currentTimeMillis()).append(".").append(fileType);
        return fileUrl.toString();
    }

    public static String getFilePath(String userId,String type){
        StringBuffer fileUrl = new StringBuffer(Contents.COS_FOLDER_NAME).append(userId);
        if(StringUtils.isNotBlank(type)){
            fileUrl.append("/").append(type).append("/");
        }
        return fileUrl.toString();
    }

    /**
     * 方法描述：判断extension中是否存在extName
     * 创建时间：2018-10-20 20:46:18
     *
     * @param extName   文件的后缀名
     * @author "lixingwu"
     */
    public ResultVO validateFile(String extName) {
        if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(extName)) {// 检查扩展名
            //"上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。";
            return ResultVO.fail();
        }
        return ResultVO.success();
    }
}