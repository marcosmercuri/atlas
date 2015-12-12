package com.crossfit;

import com.crossfit.controller.DefaultErrorResponseAttributes;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AtlasApplicationConfiguration {

    @Bean
    public ErrorAttributes errorAttribute(){
        return new DefaultErrorResponseAttributes();
    }
}
