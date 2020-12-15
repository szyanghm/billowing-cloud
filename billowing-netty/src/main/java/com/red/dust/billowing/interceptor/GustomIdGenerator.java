package com.red.dust.billowing.interceptor;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.red.dust.billowing.utils.UUIDUtil;
import org.springframework.stereotype.Component;

@Component
public class GustomIdGenerator implements IdentifierGenerator {
    @Override
    public Number nextId(Object entity) {
        return null;
    }

    @Override
    public String nextUUID(Object entity) {
        return UUIDUtil.generateId();
    }
}
