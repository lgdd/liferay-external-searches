package com.github.lgdd.liferay.external.search.results.web.internal.portlet;

import com.github.lgdd.liferay.external.search.api.ExternalSearch;
import com.github.lgdd.liferay.external.search.api.ExternalSearchException;
import com.github.lgdd.liferay.external.search.api.ExternalSearchResult;
import com.github.lgdd.liferay.external.search.results.web.constants.ExternalSearchResultsWebKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
    immediate = true,
    property = {
        "com.liferay.portlet.display-category=category.search",
        "com.liferay.portlet.instanceable=true",
        "javax.portlet.display-name=External Search Results",
        "javax.portlet.init-param.template-path=/",
        "javax.portlet.init-param.view-template=/view.jsp",
        "javax.portlet.name=" + ExternalSearchResultsWebKeys.PORTLET_ID,
        "javax.portlet.resource-bundle=content.Language",
        "javax.portlet.security-role-ref=power-user,user"
    },
    service = Portlet.class
)
public class ExternalSearchResultsWebPortlet
    extends MVCPortlet {

  @Override
  public void render(RenderRequest renderRequest, RenderResponse renderResponse)
      throws IOException, PortletException {

    final PortletPreferences preferences = renderRequest.getPreferences();
    final String externalSearchEngine = preferences.getValue("externalSearchEngine", null);

    if (externalSearchEngine != null) {
      Optional<ExternalSearch> externalSearch =
          externalSearches.stream()
                          .filter(search -> search.getEngineName()
                                                  .equalsIgnoreCase(externalSearchEngine))
                          .findFirst();
      if (externalSearch.isPresent()) {

        final PortletSharedSearchResponse searchResponse = _sharedSearchRequest.search(renderRequest);
        final String query = searchResponse.getSearchResponse().getRequest().getQueryString();

        if (Validator.isNotNull(query)) {
          try {
            final ThemeDisplay themeDisplay =
                (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
            final List<ExternalSearchResult> searchResults =
                externalSearch.get().search(query, themeDisplay.getLocale());
            renderRequest
                .setAttribute(ExternalSearchResultsWebKeys.ATTR_SEARCH_RESULTS, searchResults);
          } catch (ExternalSearchException e) {
            renderRequest
                .setAttribute(ExternalSearchResultsWebKeys.ATTR_SEARCH_RESULTS,
                              Collections.emptyList());
            _log.error(
                "Search '" + query + "' to " + externalSearch.get().getEngineName() + " failed", e
            );
          }
        } else {
          renderRequest
              .setAttribute(ExternalSearchResultsWebKeys.ATTR_SEARCH_RESULTS,
                            Collections.emptyList());
        }
      }
    }

    super.render(renderRequest, renderResponse);
  }

  private static final Logger _log = LoggerFactory.getLogger(ExternalSearchResultsWebPortlet.class);

  @Reference
  private PortletSharedSearchRequest _sharedSearchRequest;

  @Reference(
      cardinality = ReferenceCardinality.MULTIPLE,
      policyOption = ReferencePolicyOption.GREEDY
  )
  private List<ExternalSearch> externalSearches;

}