package com.github.senin24.asynctranslate.web.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger spring config.
 *
 * @author Pavel Senin
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  /**
   * Docket settings.
   *
   * @return built docket
   */
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .paths(Predicates.not(PathSelectors.regex("/error.*")))
        .paths(Predicates.not(PathSelectors.regex("/actuator.*")))
        .build().apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("Асинхронный переводчик").build();
  }
}
