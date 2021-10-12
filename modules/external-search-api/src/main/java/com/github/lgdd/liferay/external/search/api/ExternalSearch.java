package com.github.lgdd.liferay.external.search.api;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface ExternalSearch {

  String getEngineName();

  List<ExternalSearchResult> search(String query)
      throws ExternalSearchException;

  List<ExternalSearchResult> search(String query, Map<String, String> options)
      throws ExternalSearchException;

  List<ExternalSearchResult> search(String query, Locale locale)
      throws ExternalSearchException;

  List<ExternalSearchResult> search(String query, Locale locale, Map<String, String> options)
      throws ExternalSearchException;

}