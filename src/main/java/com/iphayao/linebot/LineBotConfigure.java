package com.iphayao.linebot;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class LineBotConfigure implements WebMvcConfigurer {
	Logger log = LoggerFactory.getLogger(this.getClass().getName());
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String downloadedContentUri = Application.downloadedContentDir.toUri().toASCIIString();
        log.info("downloaded Uri: {}", downloadedContentUri);
        registry.addResourceHandler("/downloaded/**")
                .addResourceLocations(downloadedContentUri);
    }
}
