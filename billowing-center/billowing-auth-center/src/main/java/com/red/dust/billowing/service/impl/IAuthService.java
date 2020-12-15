package com.red.dust.billowing.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface IAuthService {

    Jws<Claims> getJwt(String jwtToken);

    boolean invalidJwtAccessToken(String authentication);
}
