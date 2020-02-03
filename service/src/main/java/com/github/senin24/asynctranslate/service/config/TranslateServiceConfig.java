package com.github.senin24.asynctranslate.service.config;

import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
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

  public static final String TRANSLATE_EXECUTOR = "translate-executor";
  public static final String ASYNC_TASKS_TRANSLATE_EXECUTOR = "async-tasks-translate-executor";
  public static final String YANDEX_QUALIFIER = "yandex";
  public static final String GOOGLE_QUALIFIER = "google";

  private final IntegrationConfig config;
  private final RestTemplateBuilder restTemplateBuilder;

  @Bean
  public RestTemplate getRestTemplate() {
    return restTemplateBuilder
        .setConnectTimeout(Duration.ofSeconds(5))
        .setReadTimeout(Duration.ofSeconds(5))
        .build();
  }

  @Bean
  @Qualifier(TRANSLATE_EXECUTOR)
  public ExecutorService getExecutorService() {
    return Executors.newFixedThreadPool(
        config.getThreadPoolSize(),
        new ThreadFactory() {
          private int count = 1;

          @Override
          public Thread newThread(Runnable runnable) {
            return new Thread(runnable, TRANSLATE_EXECUTOR + "-" + count++);
          }
        });
  }

  @Bean(name = ASYNC_TASKS_TRANSLATE_EXECUTOR)
  public Executor asyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(config.getThreadPoolSize() * 10);
    executor.setThreadNamePrefix(ASYNC_TASKS_TRANSLATE_EXECUTOR);
    return executor;
  }
}
