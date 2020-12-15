package com.red.dust.billowing.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.red.dust.billowing.enums.SystemErrorType;
import com.red.dust.billowing.vo.ResultVO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author haimiyang
 * @date:2020/1/16 11:39
 * @version:1.0
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {
    private final ResultVO result;

    CustomOauthException(OAuth2Exception oAuth2Exception) {
        super(oAuth2Exception.getSummary(), oAuth2Exception);
        this.result = ResultVO.fail(SystemErrorType.valueOf(oAuth2Exception.getOAuth2ErrorCode().toUpperCase()), oAuth2Exception);
    }
}
