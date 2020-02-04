package com.github.senin24.asynctranslate.web.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

/** @author Pavel Senin */
@Data
@Accessors(chain = true)
@Api(tags = "Запрос на перевод текста.")
public class TranslateRequestDto {

  @NotBlank
  @ApiModelProperty(value = "Текст для перевода.", example = "Часто спрашивают, что изучать первое - джаву или питон. А изучать надо английский")
  private String textFrom;

  @NotBlank
  @ApiModelProperty(value = "Язык текста.", example = "ru")
  private String fromLang;

  @NotBlank
  @ApiModelProperty(value = "На какой язык перевести.", example = "en")
  private String toLang;
}
