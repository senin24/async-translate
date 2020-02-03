package com.github.senin24.asynctranslate.service.integration.yandex;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/** @author Pavel Senin */
@Data
public class YandexResponse {
  private int code;
  private String lang;
  private List<String> text = new ArrayList<>();
}
