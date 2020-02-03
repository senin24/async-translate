package com.github.senin24.asynctranslate.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;

@SpringBootApplication
public class BotAsyncTranslateApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
//				TelegramBotsApi botsApi = new TelegramBotsApi().registerBot(asyncTranslateBot);
//
//		try {
//			botsApi.registerBot(new AsyncTranslateBot(asyncTranslateBot));
//		} catch (TelegramApiException e) {
//			e.printStackTrace();
//		}
		SpringApplication.run(BotAsyncTranslateApplication.class, args);
	}
}
