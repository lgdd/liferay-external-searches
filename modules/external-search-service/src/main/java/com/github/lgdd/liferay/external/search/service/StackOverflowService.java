package com.github.lgdd.liferay.external.search.service;

import com.github.lgdd.liferay.external.search.api.BaseExternalSearch;
import com.github.lgdd.liferay.external.search.api.ExternalSearch;
import java.util.HashMap;
import java.util.Map;
import org.osgi.service.component.annotations.Component;

@Component(
    property = "engine=" + StackOverflowService.ENGINE_NAME,
    service = ExternalSearch.class
)
public class StackOverflowService
    extends BaseExternalSearch {

  public static final String ENGINE_NAME = "Stack Overflow";

  @Override
  public String getEngineName() {

    return ENGINE_NAME;
  }

  @Override
  protected String getDomain() {

    return "api.stackexchange.com";
  }

  @Override
  protected String getContextPath() {

    return "/2.3/search/advanced";
  }

  @Override
  protected String getQueryParamName() {

    return "q";
  }

  @Override
  protected String getNameJsonPath() {

    return "$.items[*].title";
  }

  @Override
  protected String getUrlJsonPath() {

    return "$.items[*].link";
  }

  @Override
  protected Map<String, String> getMetadataJsonPaths() {

    final Map<String, String> metadataJsonPaths = new HashMap<>();
    metadataJsonPaths.put("is_answered", "$.items[*].is_answered");
    metadataJsonPaths.put("tags", "$.items[*].tags");

    return metadataJsonPaths;
  }

  @Override
  protected Map<String, String> getDefaultOptions() {

    final Map<String, String> options = new HashMap<>();
    options.put("site", "stackoverflow");
    options.put("page", "1");
    options.put("pagesize", "10");
    options.put("order", "desc");
    options.put("sort", "activity");
    return options;
  }
}
