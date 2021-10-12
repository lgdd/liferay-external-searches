package com.github.lgdd.liferay.external.search.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.lgdd.liferay.external.search.api.ExternalSearchException;
import com.github.lgdd.liferay.external.search.api.ExternalSearchResult;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WikipediaServiceTest {

  @Test
  @DisplayName("Search should return 10 results")
  void expectTenResults()
      throws ExternalSearchException {

    List<ExternalSearchResult> externalSearchResults = _wikipediaService.search("ghost");
    assertEquals(10, externalSearchResults.size());
  }

  @Test
  @DisplayName("Search in French should return links to Wikipedia in French")
  void expectFrenchLinks()
      throws ExternalSearchException {

    List<ExternalSearchResult> externalSearchResults = _wikipediaService.search("baguette", Locale.FRENCH);
    for (ExternalSearchResult externalSearchResult : externalSearchResults) {
      assertTrue(externalSearchResult.getURL().contains("fr.wikipedia.org"));
    }
  }

  @Test
  @DisplayName("Search with gibberish keyword should not return results")
  void expectNoResult()
      throws ExternalSearchException {

    List<ExternalSearchResult> externalSearchResults = _wikipediaService.search("dwq51dsa564dsa");
    assertTrue(externalSearchResults.isEmpty());
  }

  private WikipediaService _wikipediaService = new WikipediaService();

}