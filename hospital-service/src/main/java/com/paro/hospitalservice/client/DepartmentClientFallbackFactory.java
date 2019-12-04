package com.paro.hospitalservice.client;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class DepartmentClientFallbackFactory implements FallbackFactory<DepartmentClient> {

    @Override
    public DepartmentClient create(Throwable throwable) {
        return new DepartmentClientFallback(throwable);
    }


}
