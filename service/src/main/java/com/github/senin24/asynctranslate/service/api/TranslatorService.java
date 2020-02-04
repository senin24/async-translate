package com.github.senin24.asynctranslate.service.api;

import java.util.Optional;
import java.util.UUID;

/** @author Pavel Senin */
public interface TranslatorService {

  /**
   * Синхронный запрос перевода.
   *
   * @param textFrom
   * @param fromLang
   * @param toLang
   * @param clientIp
   * @return
   */
  TranslateResponse translate(String textFrom, String fromLang, String toLang, String clientIp);

  /**
   * Асинхронный запрос перевода.
   *
   * @param textFrom
   * @param fromLang
   * @param toLang
   * @param clientIp
   * @return
   */
  void createTranslateTaskAsync(String textFrom, String fromLang, String toLang, String clientIp, UUID id);

  /**
   * Поиск результатов перевода.
   *
   * @param id запроса перевода
   * @return
   */
  Optional<TranslateResponse> findTranslate(UUID id);
}
