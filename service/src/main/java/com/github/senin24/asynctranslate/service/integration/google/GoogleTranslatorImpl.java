package com.github.senin24.asynctranslate.service.integration.google;

import static com.github.senin24.asynctranslate.service.config.TranslateServiceConfig.GOOGLE_QUALIFIER;

import com.github.senin24.asynctranslate.service.integration.Translator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author Pavel Senin
 */
@Component
@Qualifier(GOOGLE_QUALIFIER)
public class GoogleTranslatorImpl implements Translator {

  @Override
  public String translate(String textFrom, String langFrom, String langTo) {
    throw new UnsupportedOperationException("Google translate impl not ready yet.");
  }
}
