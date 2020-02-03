package com.github.senin24.asynctranslate.service.integration;

/**
 * @author Pavel Senin
 */
public interface Translator {

  String translate(String textFrom, String langFrom, String langTo);
}
