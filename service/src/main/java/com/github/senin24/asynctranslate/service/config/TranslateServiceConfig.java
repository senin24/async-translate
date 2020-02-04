package com.github.senin24.asynctranslate.service.config;

import java.time.Duration;
import java.util.concurrent.Executor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

/** @author Pavel Senin */
@EnableAsync
@Configuration
@AllArgsConstructor
public class TranslateServiceConfig {

  public static final String YANDEX_BEAN_NAME = "yandexTranslatorImpl";
  public static final String GOOGLE_BEAN_NAME = "googleTranslatorImpl";

  private final IntegrationConfig config;
  private final RestTemplateBuilder restTemplateBuilder;

  @Bean
  public RestTemplate getRestTemplate() {
    return restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(5)).setReadTimeout(Duration.ofSeconds(5)).build();
  }

  @Bean
  @Qualifier("online-translate-executor")
  public Executor getExecutorForOnlineTranslator() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(config.getThreadPoolSize());
    executor.setThreadNamePrefix("online-translate-executor");
    return executor;
  }

  @Bean
  @Qualifier("async-translate-tasks-executor")
  public Executor getExecutorForAsyncTranslateTask() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(config.getThreadPoolSize() * 10);
    executor.setThreadNamePrefix("async-translate-tasks-executor");
    return executor;
  }
}