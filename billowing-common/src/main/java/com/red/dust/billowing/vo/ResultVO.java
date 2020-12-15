package com.red.dust.billowing.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.red.dust.billowing.api.ErrorType;
import com.red.dust.billowing.enums.SystemErrorType;
import com.red.dust.billowing.exception.BaseException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.Instant;
import java.time.ZonedDateTime;

@ApiModel(description = "rest请求的返回模型，所有rest正常都返回该类的对象")
@Getter
public class ResultVO<T> {

    public static final String SUCCESSFUL_CODE = "2000";
    public static final String SUCCESSFUL_MESG = "处理成功";

    @ApiModelProperty(value = "处理结果code", required = true)
    private String code;
    @ApiModelProperty(value = "处理结果描述信息")
    private String mesg;
    @ApiModelProperty(value = "请求结果生成时间戳")
    private Instant time;
    @ApiModelProperty(value = "处理结果数据信息")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ResultVO() {
        this.time = ZonedDateTime.now().toInstant();
    }

    /**
     * @param errorType
     */
    public ResultVO(ErrorType errorType) {
        this.code = errorType.getCode();
        this.mesg = errorType.getMesg();
        this.time = ZonedDateTime.now().toInstant();
    }

    /**
     * @param errorType
     * @param data
     */
    public ResultVO(ErrorType errorType, T data) {
        this(errorType);
        this.data = data;
    }

    /**
     * 内部使用，用于构造成功的结果
     *
     * @param code
     * @param mesg
     * @param data
     */
    private ResultVO(String code, String mesg, T data) {
        this.code = code;
        this.mesg = mesg;
        this.data = data;
        this.time = ZonedDateTime.now().toInstant();
    }

    /**
     * 内部使用，用于构造成功的结果
     *
     * @param code
     * @param mesg
     */
    public ResultVO(String code, String mesg) {
        this.code = code;
        this.mesg = mesg;
        this.time = ZonedDateTime.now().toInstant();
    }


    /**
     * 快速创建成功结果并返回结果数据
     *
     * @param data
     * @return Result
     */
    public static ResultVO success(Object data) {
        return new ResultVO<>(SUCCESSFUL_CODE, SUCCESSFUL_MESG, data);
    }

    /**
     * 快速创建成功结果
     *
     * @return Result
     */
    public static ResultVO success() {
        return success(null);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @return Result
     */
    public static ResultVO fail() {
        return new ResultVO(SystemErrorType.SYSTEM_ERROR);
    }

    /**
     * 请求参数校验不通过
     *
     * @return
     */
    public static ResultVO notValid(){
        return new ResultVO(SystemErrorType.ARGUMENT_NOT_VALID);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @param baseException
     * @return Result
     */
    public static ResultVO fail(BaseException baseException) {
        return fail(baseException, null);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param data
     * @return Result
     */
    public static ResultVO fail(BaseException baseException, Object data) {
        return new ResultVO<>(baseException.getErrorType(), data);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param errorType
     * @param data
     * @return Result
     */
    public static ResultVO fail(ErrorType errorType, Object data) {
        return new ResultVO<>(errorType, data);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param errorType
     * @return Result
     */
    public static ResultVO fail(ErrorType errorType) {
        return ResultVO.fail(errorType, null);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param data
     * @return Result
     */
    public static ResultVO fail(Object data) {
        return new ResultVO<>(SystemErrorType.SYSTEM_ERROR, data);
    }


    /**
     * 成功code=000000
     *
     * @return true/false
     */
    @JsonIgnore
    public boolean isSuccess() {
        return SUCCESSFUL_CODE.equals(this.code);
    }

    /**
     * 失败
     *
     * @return true/false
     */
    @JsonIgnore
    public boolean isFail() {
        return !isSuccess();
    }
}
