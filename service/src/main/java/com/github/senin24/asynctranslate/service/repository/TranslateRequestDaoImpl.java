package com.github.senin24.asynctranslate.service.repository;

import static java.util.Objects.isNull;

import com.github.senin24.asynctranslate.service.api.TranslateResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/** @author Pavel Senin */
@Slf4j
@Repository
@AllArgsConstructor
public class TranslateRequestDaoImpl implements TranslateRequestDao {

  private static final String INSERT = "INSERT into ranslate_requests";
  private static final String UPDATE = "";
  private static final String FIND = "";
  private final NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public void create(TranslateResponse translateResponse) {
    Map<String, Object> namedParameters = new HashMap<>();
    namedParameters.put("id", translateResponse.getId());
    namedParameters.put("textFrom", translateResponse.getTextFrom());
    namedParameters.put("fromLang", translateResponse.getFromLang());
    namedParameters.put("toLang", translateResponse.getToLang());
    namedParameters.put("clientIp", translateResponse.getClientIp());
    namedParameters.put("createdAt", translateResponse.getCreatedAt());
    jdbcTemplate.update(
        "insert into translate_requests ( id, text_from, from_lang, to_lang, client_ip, created_at) values (:id, :textFrom, :fromLang, :toLang, :clientIp, :createdAt)",
        namedParameters);
  }

  @Override
  public void update(TranslateResponse translateResponse) {
    Map<String, Object> namedParameters = new HashMap<>();
    namedParameters.put("id", translateResponse.getId());
    namedParameters.put("textTo", translateResponse.getTextTo());
    namedParameters.put("finishedAt", translateResponse.getFinishedAt());
    jdbcTemplate.update(
        "update translate_requests set text_to = :textTo, finished_at = :finishedAt where id = :id",
        namedParameters);
  }

  @Override
  public Optional<TranslateResponse> find(UUID id) {
    if (isNull(id)) {
      return Optional.empty();
    }
    List<TranslateResponse> translates =
        jdbcTemplate.query(
            "select * from translate_requests where id =:id",
            new MapSqlParameterSource("id", id),
            (resultSet, i) -> toTranslateRequest(resultSet, id));
    if (translates.size() == 0) {
      return Optional.empty();
    }
    return Optional.ofNullable(translates.get(0));
  }

  private TranslateResponse toTranslateRequest(ResultSet resultSet, UUID id) {
    TranslateResponse translateResponse;
    try {
      translateResponse =
          new TranslateResponse()
              .setId(resultSet.getObject("id", UUID.class))
              .setTextFrom(resultSet.getString("text_from"))
              .setTextTo(resultSet.getString("text_to"))
              .setFromLang(resultSet.getString("from_lang"))
              .setToLang(resultSet.getString("to_lang"))
              .setClientIp(resultSet.getString("client_ip"))
              .setCreatedAt(resultSet.getObject("created_at", Instant.class))
              .setFinishedAt(resultSet.getObject("finished_at", Instant.class));
    } catch (SQLException e) {
      log.error("Can't find entity with id {}. ResultSet: {}", id, resultSet);
      return null;
    }
    return translateResponse;
  }
}
