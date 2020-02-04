package com.github.senin24.asynctranslate.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class BotAsyncTranslateApplication {

  public static void main(String[] args) {
    ApiContextInitializer.init();
    SpringApplication.run(BotAsyncTranslateApplication.class, args);
  }
}
