package com.kongbig.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 开启swagger2
 *
 * @author lianggangda
 * @date 2019/12/30 10:55
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        //配置swagger 生成API的扫描范围
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kongbig.cloud"))
                .paths(PathSelectors.any()).build();
    }

    /**
     * 创建api文档信息
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("item微服务")
                .description("SpringCloud教程")
                .termsOfServiceUrl("https://github.com/kongbigliang/study-note/tree/master/study-cloud1")
                .version("1.0").build();
    }

}
