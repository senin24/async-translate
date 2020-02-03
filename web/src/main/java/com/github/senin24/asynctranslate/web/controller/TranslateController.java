package com.github.senin24.asynctranslate.web.controller;

import com.github.senin24.asynctranslate.service.api.TranslateResponse;
import com.github.senin24.asynctranslate.service.api.TranslatorService;
import com.github.senin24.asynctranslate.web.dto.TranslateRequestDto;
import io.swagger.annotations.Api;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pavel Senin
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "Переводчик")
public class TranslateController {

  private final TranslatorService translatorService;

  @ResponseBody
  @RequestMapping(path = "/translate", method = RequestMethod.POST)
  public TranslateResponse translate(@Valid @RequestBody TranslateRequestDto request, HttpServletRequest servletRequest){
    return translatorService.translate(request.getTextFrom(), request.getFromLang(), request.getToLang(), servletRequest.getRemoteAddr());
  }

}
