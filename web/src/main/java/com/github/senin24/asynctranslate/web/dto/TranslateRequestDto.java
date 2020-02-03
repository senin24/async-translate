package com.github.senin24.asynctranslate.web.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/** @author Pavel Senin */
@Data
@Api(tags = "Запрос на перевод текста.")
public class TranslateRequestDto {

  @NotBlank
  @ApiModelProperty(value = "Текст для перевода.")
  private String textFrom;

  @NotBlank
  @ApiModelProperty(value = "Язык текста.", example = "en")
  private String fromLang;

  @NotBlank
  @ApiModelProperty(value = "На какой язык перевести.", example = "ru")
  private String toLang;
}
