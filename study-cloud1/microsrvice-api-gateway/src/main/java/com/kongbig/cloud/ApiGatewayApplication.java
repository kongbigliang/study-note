package com.kongbig.cloud;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * 访问：http://127.0.0.1:8087/item-service/item/2
 * item-service路由到商品服务：127.0.0.1:8081
 * <br>
 * 访问：
 * http://127.0.0.1:8087/item-service/item/2
 * http://127.0.0.1:8087/order-service/order/201810300002
 * 测试网关
 *
 * @author lianggangda
 * @date 2019/12/11 13:50
 */
@EnableZuulProxy
@EnableSwagger2Doc
@SpringBootApplication
@ComponentScan(basePackages = "com.kongbig.cloud.filter")
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @RefreshScope
    @ConfigurationProperties("zuul")
    public ZuulProperties zuulProperties() {
        ZuulProperties properties = new ZuulProperties();
        System.out.println("properties: " + properties);
        return properties;
    }

    /**
     * 配置swagger
     */
    @Component
    @Primary
    class DocumentationConfig implements SwaggerResourcesProvider {

        @Override
        public List<SwaggerResource> get() {
            List<SwaggerResource> resource = new ArrayList<>();
            // name可以随便写，location前缀要与zuul配置的path一致。
            // zuul开了token验证，要加上token，否则不用加?token=1
            resource.add(swaggerResource("app-item", "/item-service/v2/api-docs?token=1", "2.0"));
            resource.add(swaggerResource("app-order", "/order-service/v2/api-docs?token=1", "2.0"));
            return resource;
        }

        private SwaggerResource swaggerResource(String name, String location, String version) {
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setName(name);
            swaggerResource.setLocation(location);
            swaggerResource.setSwaggerVersion(version);
            return swaggerResource;
        }

    }

}
