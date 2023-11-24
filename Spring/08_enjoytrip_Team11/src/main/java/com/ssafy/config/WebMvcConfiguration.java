package com.ssafy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final String uploadFilePath;

    public WebMvcConfiguration(@Value("${file.path.upload-images}") String uploadFilePath){
        this.uploadFilePath = uploadFilePath;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/upload/file/**").addResourceLocations("file:///" + uploadFilePath + "/")
                .setCachePeriod(3600).resourceChain(true).addResolver(new PathResourceResolver());
    }
}
