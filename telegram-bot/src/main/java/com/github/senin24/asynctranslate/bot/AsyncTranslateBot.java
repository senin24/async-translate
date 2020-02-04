package com.github.senin24.asynctranslate.bot;

import com.github.senin24.asynctranslate.service.api.TranslateResponse;
import com.github.senin24.asynctranslate.service.api.TranslatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/** @author Pavel_Senin */
@Slf4j
@Component
@RequiredArgsConstructor
public class AsyncTranslateBot extends TelegramLongPollingBot {

  private final TranslatorService translatorService;

  @Value("${telegrambot.name}")
  private String botName;

  @Value("${telegrambot.apikey")
  private String apiKey;

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {

      TranslateResponse translate =
          translatorService.translate(update.getMessage().getText(), "ru", "en", update.getMessage().getFrom().getUserName());

      SendMessage message =
          new SendMessage()
              .setChatId(update.getMessage().getChatId())
              .setText("Привет, " + translate.getClientIp() + "! Перевод на английский: \n" + translate.toString());
      try {
        execute(message);
      } catch (TelegramApiException e) {
        log.error("Can't send message to Telegram: ");
      }
    }
  }

  @Override
  public String getBotUsername() {
    return botName;
  }

  @Override
  public String getBotToken() {
    return apiKey;
  }
}
