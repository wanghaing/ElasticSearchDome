package com.wangxiaoer.es.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket creatRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("edu.zut.cs.zutnlp.knowledge"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("zutnlp-knowledge")
                .description("zutnlp-knowledge API")
                .termsOfServiceUrl("http://127.0.0.1:8800/")
                //.contact(content)
                .version("1.0")
                .build();
    }
    //如果项目上线并且需要关闭swagger接口，可以通过配置权限
    // SwaggerConfig里面return new Docket的时候加多一个.enable(false)
}
