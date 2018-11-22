package org.bqf.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 类描述: swagger 的配置类.
 * 创建人: baiqiufei
 * 创建时间: 2018/9/28 15:48
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  /**
   * get the swagger api info.
   * @return api info
   */
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("My API").description("test API").termsOfServiceUrl("")
            .version("1.0.0").contact(new Contact("", "", "")).build();
  }

  /**
   * get Docket.
   * @return Docket
   */
  @Bean
  public Docket customImplementation() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage("org.bqf"))
        .build().directModelSubstitute(org.joda.time.LocalDate.class, java.sql.Date.class)
        .directModelSubstitute(org.joda.time.DateTime.class, java.util.Date.class).apiInfo(apiInfo());
  }

}
