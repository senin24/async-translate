package com.github.senin24.asynctranslate.service.repository;

import com.github.senin24.asynctranslate.service.api.TranslateResponse;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Pavel Senin
 */
public interface TranslateRequestDao {

  void create(TranslateResponse translateResponse);

  void update (TranslateResponse translateResponse);

  Optional<TranslateResponse> find (UUID id);
}