package com.media.service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class StaticResourceConfiguration implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String uploadDirectory;



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("uploadDirectory: {}", uploadDirectory);

        registry
                .addResourceHandler("/api/v1/medias/images/**")
                .addResourceLocations("file:" + uploadDirectory);
    }
}
