package com.github.lgdd.liferay.external.search.service;

import com.github.lgdd.liferay.external.search.api.ExternalSearch;
import com.github.lgdd.liferay.external.search.api.ExternalSearchException;
import com.github.lgdd.liferay.external.search.api.ExternalSearchResult;
import com.jayway.jsonpath.JsonPath;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import net.minidev.json.JSONArray;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseExternalSearch
    implements ExternalSearch {

  @Override
  public List<ExternalSearchResult> search(String query)
      throws ExternalSearchException {

    final StringBuilder urlBuilder = new StringBuilder("https://");
    addQueryAndOptions(urlBuilder, query, Collections.emptyMap());

    return getResults(urlBuilder.toString());
  }

  @Override
  public List<ExternalSearchResult> search(String query, Map<String, String> options)
      throws ExternalSearchException {

    final StringBuilder urlBuilder = new StringBuilder("https://");
    addQueryAndOptions(urlBuilder, query, options);

    return getResults(urlBuilder.toString());
  }

  @Override
  public List<ExternalSearchResult> search(String query, Locale locale)
      throws ExternalSearchException {

    return this.search(query);
  }

  @Override
  public List<ExternalSearchResult> search(String query, Locale locale, Map<String, String> options)
      throws ExternalSearchException {

    return this.search(query, options);
  }

  protected void addQueryAndOptions(StringBuilder urlBuilder, String query,
      Map<String, String> options) {

    urlBuilder.append(this.getDomain());
    urlBuilder.append(this.getContextPath());
    urlBuilder.append("?");
    urlBuilder.append(this.getQueryParamName());
    urlBuilder.append("=");
    urlBuilder.append(query);

    for (Entry<String, String> option : options.entrySet()) {
      urlBuilder.append("&");
      urlBuilder.append(option.getKey());
      urlBuilder.append("=");
      urlBuilder.append(option.getValue());
    }

    _addDefaultOptions(urlBuilder);
  }

  private void _addDefaultOptions(StringBuilder urlBuilder) {

    for (Entry<String, String> option : this.getDefaultOptions().entrySet()) {
      if (!urlBuilder.toString().contains(option.getKey())) {
        urlBuilder.append("&");
        urlBuilder.append(option.getKey());
        urlBuilder.append("=");
        urlBuilder.append(option.getValue());
      }
    }
  }

  protected List<ExternalSearchResult> getResults(String url)
      throws ExternalSearchException {

    final List<ExternalSearchResult> externalSearchResults = new ArrayList<>();
    final OkHttpClient client = new OkHttpClient();
    final Request request = new Request.Builder()
        .url(url)
        .build();

    if (_log.isDebugEnabled()) {
      _log.debug("Hitting {}", url);
    }

    try (Response response = client.newCall(request).execute()) {
      if (response.body() != null) {
        final String json = response.body().string();
        final JSONArray resultNames = JsonPath.read(json, this.getNameJsonPath());
        final JSONArray resultURLs = JsonPath.read(json, this.getUrlJsonPath());

        if (_log.isDebugEnabled()) {
          _log.debug("Response:\n{}", json);
          _log.debug("Results:\n");
        }

        for (int i = 0; i < resultNames.size(); i++) {
          String resultName = resultNames.get(i).toString();
          String resultURL = resultURLs.get(i).toString().contains("https://") ?
                             resultURLs.get(i).toString() :
                             "https://" + getDomain() + resultURLs.get(i).toString();

          ExternalSearchResult externalSearchResult =
              new ExternalSearchResult(resultName, resultURL);

          Map<String, Object> metadata = new HashMap<>();
          for (Entry<String, String> metadataJsonPath : this.getMetadataJsonPaths().entrySet()) {
            metadata.put(
                metadataJsonPath.getKey(),
                ((JSONArray) JsonPath.read(json, metadataJsonPath.getValue())).get(i)
            );
          }

          externalSearchResult.setMetadata(metadata);

          if (_log.isDebugEnabled()) {
            _log.debug("{}\n<{}>\n{}\n\n",
                       externalSearchResult.getName(),
                       externalSearchResult.getUrl(),
                       externalSearchResult.getMetadata());
          }

          externalSearchResults.add(externalSearchResult);
        }
      } else {
        throw new ExternalSearchException("Response body is null for " + url);
      }
    } catch (IOException e) {
      throw new ExternalSearchException(e);
    }
    return externalSearchResults;
  }

  protected abstract String getDomain();

  protected abstract String getContextPath();

  protected abstract String getQueryParamName();

  protected abstract String getNameJsonPath();

  protected abstract String getUrlJsonPath();

  protected abstract Map<String, String> getMetadataJsonPaths();

  protected abstract Map<String, String> getDefaultOptions();

  private static final Logger _log = LoggerFactory.getLogger(BaseExternalSearch.class);

}
