package com.paro.hospitalservice.client;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class PatientClientFallbackFactory implements FallbackFactory<PatientClient> {

    @Override
    public PatientClient create(Throwable throwable) {
        return new PatientClientFallback(throwable);
    }


}
