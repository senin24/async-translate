package com.github.senin24.asynctranslate.service.impl;

import static com.github.senin24.asynctranslate.service.config.TranslateServiceConfig.YANDEX_BEAN_NAME;

import com.github.senin24.asynctranslate.service.api.TranslateResponse;
import com.github.senin24.asynctranslate.service.api.TranslatorService;
import com.github.senin24.asynctranslate.service.integration.Translator;
import com.github.senin24.asynctranslate.service.repository.TranslateRequestDao;
import com.google.common.collect.Lists;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/** @author Pavel Senin */
@Service
@AllArgsConstructor
public class TranslatorServiceImpl implements TranslatorService {

  private final Map<String, Translator> translators;
  private final TranslateRequestDao translateDao;
  @NonNull
  @Qualifier("online-translate-executor")
  private final Executor translateExecutor;

  @Override
  public TranslateResponse translate(String textFrom, String fromLang, String toLang, String clientIp) {
    TranslateResponse translateResponse = createTranslateResponse(textFrom, fromLang, toLang, clientIp);
    translateDao.create(translateResponse);
    String textTo = translateAsyncBy(translateResponse, YANDEX_BEAN_NAME);
    translateResponse.setTextTo(textTo).setFinishedAt(Instant.now());
    translateDao.update(translateResponse);
    return translateResponse;
  }

  private String translateAsyncBy(TranslateResponse translateResponse, String translatorName) {
    Translator translator = translators.get(translatorName);
    String[] words = translateResponse.getTextFrom().trim().replaceAll("[^a-zA-Zа-яА-Я ]", "").toLowerCase().split("\\s+");
    return Stream.of(words)
        .map(
            word ->
                CompletableFuture.supplyAsync(
                    () -> translator.translate(word, translateResponse.getFromLang(), translateResponse.getToLang()), translateExecutor))
        .map(CompletableFuture::join)
        .collect(Collectors.joining(" "));
  }

  @Override
  @Async("async-translate-tasks-executor")
  public void createTranslateTaskAsync(String textFrom, String fromLang, String toLang, String clientIp, UUID id) {
    TranslateResponse translateResponse = createTranslateResponse(textFrom, fromLang, toLang, clientIp, id);
    translateDao.create(translateResponse);
    String textTo = translateAsyncBy(translateResponse, YANDEX_BEAN_NAME);
    translateResponse.setTextTo(textTo).setFinishedAt(Instant.now());
    translateDao.update(translateResponse);
  }

  @Override
  public Optional<TranslateResponse> findTranslate(UUID id) {
    return translateDao.find(id);
  }

  private TranslateResponse createTranslateResponse(String textFrom, String fromLang, String toLang, String clientIp) {
    return createTranslateResponse(textFrom, fromLang, toLang, clientIp, UUID.randomUUID());
  }

  private TranslateResponse createTranslateResponse(String textFrom, String fromLang, String toLang, String clientIp, UUID id) {
    return new TranslateResponse()
        .setId(id)
        .setTextFrom(textFrom)
        .setFromLang(fromLang)
        .setToLang(toLang)
        .setClientIp(clientIp)
        .setCreatedAt(Instant.now());
  }
}
