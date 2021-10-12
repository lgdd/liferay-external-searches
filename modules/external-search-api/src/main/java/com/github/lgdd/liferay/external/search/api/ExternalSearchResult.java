package com.github.lgdd.liferay.external.search.api;

import java.util.HashMap;
import java.util.Map;

public class ExternalSearchResult {

  private String name;
  private String url;
  private Map<String, Object> metadata = new HashMap<>();

  public ExternalSearchResult(String name, String url) {

    this.name = name;
    this.url = url;
  }

  public String getName() {

    return name;
  }

  public void setName(String name) {

    this.name = name;
  }

  public String getURL() {

    return url;
  }

  public void setURL(String url) {

    this.url = url;
  }

  public String getUrl() {

    return url;
  }

  public void setUrl(String url) {

    this.url = url;
  }

  public Map<String, Object> getMetadata() {

    return metadata;
  }

  public void setMetadata(Map<String, Object> metadata) {

    this.metadata = metadata;
  }
}
