package com.github.lgdd.liferay.external.search.service;

import com.github.lgdd.liferay.external.search.api.ExternalSearch;
import com.github.lgdd.liferay.external.search.api.ExternalSearchException;
import com.github.lgdd.liferay.external.search.api.ExternalSearchResult;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.osgi.service.component.annotations.Component;

@Component(
    property = "engine=" + WikipediaService.ENGINE_NAME,
    service = ExternalSearch.class
)
public class WikipediaService
    extends BaseExternalSearch {

  public static final String ENGINE_NAME = "Wikipedia";

  @Override
  public String getEngineName() {

    return ENGINE_NAME;
  }

  @Override
  protected String getDomain() {

    return "wikipedia.org";
  }

  @Override
  protected String getContextPath() {

    return "/w/api.php";
  }

  @Override
  protected String getQueryParamName() {

    return "search";
  }

  @Override
  protected String getNameJsonPath() {

    return "$[1]";
  }

  @Override
  protected String getUrlJsonPath() {

    return "$[3]";
  }

  @Override
  protected Map<String, String> getMetadataJsonPaths() {

    return Collections.emptyMap();
  }

  @Override
  protected Map<String, String> getDefaultOptions() {

    final HashMap<String, String> options = new HashMap<>();
    options.put("action", "opensearch");
    options.put("format", "json");

    return options;
  }

  @Override
  public List<ExternalSearchResult> search(String query)
      throws ExternalSearchException {

    final StringBuilder urlBuilder = new StringBuilder("https://en.");
    addQueryAndOptions(urlBuilder, query, Collections.emptyMap());

    return getResults(urlBuilder.toString());
  }

  @Override
  public List<ExternalSearchResult> search(String query, Map<String, String> options)
      throws ExternalSearchException {

    final StringBuilder urlBuilder = new StringBuilder("https://en.");
    addQueryAndOptions(urlBuilder, query, options);

    return getResults(urlBuilder.toString());
  }

  @Override
  public List<ExternalSearchResult> search(String query, Locale locale)
      throws ExternalSearchException {

    final StringBuilder urlBuilder = new StringBuilder("https://");
    urlBuilder.append(locale.getLanguage().toLowerCase());
    urlBuilder.append(".");
    addQueryAndOptions(urlBuilder, query, Collections.emptyMap());

    return getResults(urlBuilder.toString());
  }

  @Override
  public List<ExternalSearchResult> search(String query, Locale locale, Map<String, String> options)
      throws ExternalSearchException {

    final StringBuilder urlBuilder = new StringBuilder("https://");
    urlBuilder.append(locale.getLanguage().toLowerCase());
    urlBuilder.append(".");
    addQueryAndOptions(urlBuilder, query, options);

    return getResults(urlBuilder.toString());
  }
}
