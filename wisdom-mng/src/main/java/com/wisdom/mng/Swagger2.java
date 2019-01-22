package com.wisdom.mng;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/***
 * @author CHENWEICONG
 * @create 2018-11-23 13:56
 * @desc chenweicong
 */
@Configuration
@EnableSwagger2
@EnableWebMvc
public class Swagger2 extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                        .apiInfo(apiInfo())
                        .select()
                        .apis(RequestHandlerSelectors.basePackage("com.wisdom.mng.controller"))
                         .paths(PathSelectors.any())
                         .build()
                         .ignoredParameterTypes(ApiIgnore.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("wisdom 官网")
                .description("wisdom 官网 后台API文档")
                .termsOfServiceUrl("http://localhost:8888/swagger-ui.html")
                .contact("陈伟聪")
                .version("1.0")
                .build();
        }
}