package com.wisdom.mng.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/***
 * @author CHENWEICONG
 * @create 2019-01-21 13:51
 * @desc
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

    @Autowired
    private Environment env;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(env.getProperty("upload.url")+"**").addResourceLocations(
                "file:"+env.getProperty("upload.file.path"));
    }
}
