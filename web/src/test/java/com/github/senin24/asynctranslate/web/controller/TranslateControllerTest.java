package com.github.senin24.asynctranslate.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


import com.github.senin24.asynctranslate.service.api.TranslateResponse;
import com.github.senin24.asynctranslate.service.api.TranslatorService;
import com.github.senin24.asynctranslate.web.dto.TranslateRequestDto;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/** @author Pavel_Senin */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TranslateControllerTest {

  private static final String BASE_URL = "/translate";
  private static final String TRANSLATE_RESULT = "check translate";

  @Autowired private TestRestTemplate restTemplate;

  @MockBean private TranslatorService translatorService;

  @Test
  @DisplayName("Should return correct mapped TranslateResponse")
  void testTranslate() {
    TranslateRequestDto translateRequest = new TranslateRequestDto().setFromLang("ru").setToLang("en").setTextFrom("Проверка перевода");
    UUID id = UUID.randomUUID();
    Instant createdAt = Instant.now();
    when(translatorService.translate(anyString(), anyString(), anyString(), anyString()))
        .thenReturn(getTranslateResponse(id, translateRequest, createdAt));

    ResponseEntity<TranslateResponse> translateResponseEntity = translate(BASE_URL, translateRequest);

    assertThat(translateResponseEntity).isNotNull();
    assertThat(translateResponseEntity.getStatusCode().is2xxSuccessful()).isTrue();

    TranslateResponse translateResponse = translateResponseEntity.getBody();
    assertThat(translateResponse.getId()).isEqualTo(id);
    assertThat(translateResponse.getTextFrom()).isEqualTo(translateRequest.getTextFrom());
    assertThat(translateResponse.getTextTo()).isEqualTo(TRANSLATE_RESULT);
    assertThat(translateResponse.getFromLang()).isEqualTo(translateRequest.getFromLang());
    assertThat(translateResponse.getToLang()).isEqualTo(translateRequest.getToLang());
    assertThat(translateResponse.getClientIp()).isEqualTo("8.8.8.8");
    assertThat(translateResponse.getCreatedAt()).isEqualTo(createdAt);
    assertThat(translateResponse.getFinishedAt()).isEqualTo(createdAt.plusSeconds(10));
  }

  @Test
  @DisplayName("Should return translate task id")
  void createTranslateTaskAsync() {
    TranslateRequestDto translateRequest = new TranslateRequestDto().setFromLang("ru").setToLang("en").setTextFrom("Проверка перевода");

    ResponseEntity<UUID> responseId = translateAsync(BASE_URL + "/async", translateRequest);

    assertThat(responseId).isNotNull();
    assertThat(responseId.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(responseId.getBody()).isNotNull();
  }

  @Test
  @DisplayName("Should find TranslateResponse")
  void testFindTranslateTask() {
    UUID id = UUID.randomUUID();
    Instant createdAt = Instant.now();
    TranslateRequestDto translateRequest = new TranslateRequestDto().setFromLang("ru").setToLang("en").setTextFrom("Проверка перевода");
    when(translatorService.findTranslate(id)).thenReturn(Optional.of(getTranslateResponse(id, translateRequest, createdAt)));

    ResponseEntity<TranslateResponse> translateResponseEntity = find(BASE_URL + "/" + id);

    assertThat(translateResponseEntity).isNotNull();
    assertThat(translateResponseEntity.getStatusCode().is2xxSuccessful()).isTrue();

    TranslateResponse translateResponse = translateResponseEntity.getBody();
    assertThat(translateResponse.getId()).isEqualTo(id);
    assertThat(translateResponse.getTextFrom()).isEqualTo(translateRequest.getTextFrom());
    assertThat(translateResponse.getTextTo()).isEqualTo(TRANSLATE_RESULT);
    assertThat(translateResponse.getFromLang()).isEqualTo(translateRequest.getFromLang());
    assertThat(translateResponse.getToLang()).isEqualTo(translateRequest.getToLang());
    assertThat(translateResponse.getClientIp()).isEqualTo("8.8.8.8");
    assertThat(translateResponse.getCreatedAt()).isEqualTo(createdAt);
    assertThat(translateResponse.getFinishedAt()).isEqualTo(createdAt.plusSeconds(10));
  }

  private TranslateResponse getTranslateResponse(UUID id, TranslateRequestDto translateRequest, Instant createdAt) {
    return new TranslateResponse()
        .setId(id)
        .setTextFrom(translateRequest.getTextFrom())
        .setTextTo(TRANSLATE_RESULT)
        .setFromLang(translateRequest.getFromLang())
        .setToLang(translateRequest.getToLang())
        .setClientIp("8.8.8.8")
        .setCreatedAt(createdAt)
        .setFinishedAt(createdAt.plusSeconds(10));
  }

  ResponseEntity<TranslateResponse> translate(String baseUrl, TranslateRequestDto data) {
    HttpEntity httpEntity = new HttpEntity<>(data);
    return restTemplate.exchange(baseUrl, HttpMethod.POST, httpEntity, getTypeReference());
  }

  private ResponseEntity<UUID> translateAsync(String baseUrl, TranslateRequestDto data) {
    HttpEntity httpEntity = new HttpEntity<>(data);
    return restTemplate.exchange(baseUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<UUID>() {});
  }

  ResponseEntity<TranslateResponse> find(String url) {
    return restTemplate.exchange(url, HttpMethod.GET, null, getTypeReference());
  }

  private ParameterizedTypeReference<TranslateResponse> getTypeReference() {
    return new ParameterizedTypeReference<TranslateResponse>() {};
  }
}
