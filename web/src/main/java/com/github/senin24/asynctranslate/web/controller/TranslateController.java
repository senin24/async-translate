package com.github.senin24.asynctranslate.web.controller;

import com.github.senin24.asynctranslate.service.api.TranslateResponse;
import com.github.senin24.asynctranslate.service.api.TranslatorService;
import com.github.senin24.asynctranslate.web.dto.TranslateRequestDto;
import io.swagger.annotations.Api;
import java.net.URI;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

/**
 * @author Pavel Senin
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "Переводчик")
@RequestMapping("/translate")
public class TranslateController {

  private final TranslatorService translatorService;

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<TranslateResponse> translate(@Valid @RequestBody TranslateRequestDto request, HttpServletRequest servletRequest){
    TranslateResponse translate = translatorService
        .translate(request.getTextFrom(), request.getFromLang(), request.getToLang(), servletRequest.getRemoteAddr());
    URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(translate.getId()).toUri();
    return ResponseEntity.created(uri).body(translate);
  }

  @RequestMapping(path = "/async", method = RequestMethod.POST)
  public ResponseEntity<UUID> createTranslateTaskAsync(@Valid @RequestBody TranslateRequestDto request, HttpServletRequest servletRequest){
    UUID id = UUID.randomUUID();
    translatorService.createTranslateTaskAsync(request.getTextFrom(), request.getFromLang(), request.getToLang(), servletRequest.getRemoteAddr(), id);
    URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(id).toUri();
    return ResponseEntity.created(uri).body(id);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<TranslateResponse> findTranslateTask(@PathVariable UUID id){
    return ResponseEntity.of(translatorService.findTranslate(id));
  }

}
