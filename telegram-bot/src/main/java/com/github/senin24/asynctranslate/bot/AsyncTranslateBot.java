package com.github.senin24.asynctranslate.bot;

import com.github.senin24.asynctranslate.service.api.TranslateResponse;
import com.github.senin24.asynctranslate.service.api.TranslatorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/** @author Pavel_Senin */
@Slf4j
@Component
@AllArgsConstructor
public class AsyncTranslateBot extends TelegramLongPollingBot {

//  private final TranslatorService translatorService;

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {

//      TranslateResponse translate =
//          translatorService.translate(update.getMessage().getText(), "ru", "en", update.getMessage().getFrom().getUserName());

      SendMessage message =
          new SendMessage()
              .setChatId(update.getMessage().getChatId())
//              .setText("Привет, " + translate.getClientIp() + "! Перевод на английский: " + translate.getTextTo());
              .setText("Привет, " + update.getMessage().getFrom().getUserName() + "!");
      try {
        execute(message);
      } catch (TelegramApiException e) {
        log.error("Can't send message to Telegram: ");
      }
    }
  }

  @Override
  public String getBotUsername() {
    return "asyncTranslateBot";
  }

  @Override
  public String getBotToken() {
    return "1086436798:AAGPespcNYNqC9Bzpl9oAxXLMx8-CVuLAMA";
  }
}