package com.github.senin24.asynctranslate.service.integration.yandex;

import static com.github.senin24.asynctranslate.service.config.TranslateServiceConfig.YANDEX_QUALIFIER;
import static java.util.Objects.isNull;

import com.github.senin24.asynctranslate.service.config.IntegrationConfig;
import com.github.senin24.asynctranslate.service.integration.Translator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/** @author Pavel Senin */
@Component
@AllArgsConstructor
@Qualifier(YANDEX_QUALIFIER)
public class YandexTranslatorImpl implements Translator {

  private final RestTemplate restTemplate;
  private final IntegrationConfig config;

  @Override
  public String translate(String textFrom, String langFrom, String langTo) {
    HttpEntity<MultiValueMap<String, String>> request = createRequest(textFrom, langFrom, langTo);

    ResponseEntity<YandexResponse> response = restTemplate.postForEntity(config.getYandex().getUrl(), request, YandexResponse.class);

    if (isNull(response)
        || !response.getStatusCode().is2xxSuccessful()
        || isNull(response.getBody())
        || response.getBody().getText().isEmpty()) {
      throw new RuntimeException("Can't get success response from Yandex-translate. Current response: " + response);
    }

    String translatedText = response.getBody().getText().get(0);
    return isNull(translatedText) ? "" : translatedText;
  }

  private HttpEntity<MultiValueMap<String, String>> createRequest(String textFrom, String langFrom, String langTo) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("key", config.getYandex().getApiKey());
    body.add("text", textFrom);
    body.add("lang", String.format("%s-%s", langFrom, langTo));
    return new HttpEntity<>(body, headers);
  }
}
