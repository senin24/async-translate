package com.github.senin24.asynctranslate.service.integration.google;

import com.github.senin24.asynctranslate.service.integration.Translator;
import org.springframework.stereotype.Component;

/**
 * @author Pavel Senin
 */
@Component
public class GoogleTranslatorImpl implements Translator {

  @Override
  public String translate(String textFrom, String langFrom, String langTo) {
    throw new UnsupportedOperationException("Google translate impl not ready yet.");
  }
}
