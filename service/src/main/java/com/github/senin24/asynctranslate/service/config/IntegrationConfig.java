package com.github.senin24.asynctranslate.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Pavel Senin
 */
@Data
@Component
@ConfigurationProperties("integration")
public class IntegrationConfig {

  private int threadPoolSize;

  private TranslatorIntegration yandex;
  private TranslatorIntegration google;

  @Data
  public static class TranslatorIntegration {
    private String url;
    private String apiKey;
  }
}
