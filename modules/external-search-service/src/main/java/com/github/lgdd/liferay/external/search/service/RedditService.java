package com.github.lgdd.liferay.external.search.service;

import com.github.lgdd.liferay.external.search.api.BaseExternalSearch;
import com.github.lgdd.liferay.external.search.api.ExternalSearch;
import java.util.HashMap;
import java.util.Map;
import org.osgi.service.component.annotations.Component;

@Component(
    property = "engine=" + RedditService.ENGINE_NAME,
    service = ExternalSearch.class
)
public class RedditService
    extends BaseExternalSearch {

  public static final String ENGINE_NAME = "Reddit";

  @Override
  public String getEngineName() {

    return ENGINE_NAME;
  }

  @Override
  protected String getDomain() {

    return "www.reddit.com";
  }

  @Override
  protected String getContextPath() {

    return "/search.json";
  }

  @Override
  protected String getQueryParamName() {

    return "q";
  }

  @Override
  protected String getNameJsonPath() {

    return "$.data.children[*].data.title";
  }

  @Override
  protected String getUrlJsonPath() {

    return "$.data.children[*].data.permalink";
  }

  @Override
  protected Map<String, String> getMetadataJsonPaths() {

    final Map<String, String> metadataJsonPaths = new HashMap<>();
    metadataJsonPaths.put("ups", "$.data.children[*].data.ups");
    metadataJsonPaths.put(
        "subreddit_name_prefixed",
        "$.data.children[*].data.subreddit_name_prefixed"
    );
    return metadataJsonPaths;
  }

  @Override
  protected Map<String, String> getDefaultOptions() {

    final Map<String, String> options = new HashMap<>();
    options.put("limit", "10");
    options.put("sort", "hot");
    return options;
  }
}
