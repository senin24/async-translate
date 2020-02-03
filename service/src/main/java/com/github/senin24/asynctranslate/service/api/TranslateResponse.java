package com.github.senin24.asynctranslate.service.api;

import java.time.Instant;
import java.util.UUID;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Pavel Senin
 */
@Data
@Accessors(chain = true)
public class TranslateResponse {

  private UUID id;

  private String textFrom;

  private String textTo;

  private String fromLang;

  private String toLang;

  private String clientIp;

  private Instant createdAt;

  private Instant finishedAt;
}
