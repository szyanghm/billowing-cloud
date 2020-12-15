package com.red.dust.billowing.enums;

import com.red.dust.billowing.api.ErrorType;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum SystemErrorType implements ErrorType {

    SYSTEM_ERROR("-1", "系统异常"),
    SYSTEM_BUSY("000001", "系统繁忙,请稍候再试"),

    GATEWAY_NOT_FOUND_SERVICE("010404", "服务未找到"),
    GATEWAY_ERROR("010500", "网关异常"),
    GATEWAY_CONNECT_TIME_OUT("010002", "网关超时"),

    ARGUMENT_NOT_VALID("020000", "请求参数校验不通过"),
    INVALID_TOKEN("020001", "无效token"),

    DUPLICATE_PRIMARY_KEY("030000","唯一键冲突"),

    USER_CHECK_FAILED("600001","user check failed"),

    USER_ALREADY_EXISTS("600002","用户已存在"),

    USER_LOCK_ING("600003","user is lock...."),

    USER_EXISTS("300001","user Already exists"),

    NETTY_PING_MESSAGE("100015","netty ping message"),

    RECEIVE_PONG_MESSAGE_ADDRESS("100016","receive pong message, address,获取Channel的远程IP地址"),

    UPLOAD_FILE_SIZE_LIMIT("100060", "上传文件大小超过限制"),

    UPLOAD_NOT_FOUND("100061","上传的文件对象不存在..."),

    FILE_TYPE_NOT("100062","文件类型未定义不能上传..."),

    NORMAL_MESSAGE("100086","Normal message");

    /**
     * 错误类型码
     */
    private String code;
    /**
     * 错误类型描述信息
     */
    private String mesg;

    SystemErrorType(String code, String mesg) {
        this.code = code;
        this.mesg = mesg;
    }
}
