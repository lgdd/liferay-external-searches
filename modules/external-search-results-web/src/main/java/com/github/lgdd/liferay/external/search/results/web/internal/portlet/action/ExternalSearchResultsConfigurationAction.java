package com.github.lgdd.liferay.external.search.results.web.internal.portlet.action;


import com.github.lgdd.liferay.external.search.api.ExternalSearch;
import com.github.lgdd.liferay.external.search.results.web.constants.ExternalSearchResultsWebKeys;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;

@Component(
    immediate = true,
    property = "javax.portlet.name=" + ExternalSearchResultsWebKeys.PORTLET_ID,
    service = ConfigurationAction.class
)
public class ExternalSearchResultsConfigurationAction
    extends DefaultConfigurationAction {

  @Override
  public String getJspPath(HttpServletRequest httpServletRequest) {

    httpServletRequest.setAttribute(
        "externalSearchEngines",
        externalSearches.stream()
                        .map(ExternalSearch::getEngineName)
                        .sorted()
                        .collect(Collectors.toList())
    );

    return super.getJspPath(httpServletRequest);
  }

  @Override
  @Reference(
      target = "(osgi.web.symbolicname=" + ExternalSearchResultsWebKeys.SYMBOLIC_NAME + ")",
      unbind = "-"
  )
  public void setServletContext(ServletContext servletContext) {

    super.setServletContext(servletContext);
  }

  @Reference(
      cardinality = ReferenceCardinality.MULTIPLE,
      policyOption = ReferencePolicyOption.GREEDY
  )
  private List<ExternalSearch> externalSearches;
}
