package com.github.lgdd.liferay.external.search.results.web.internal.portlet.template;

import com.github.lgdd.liferay.external.search.results.web.constants.ExternalSearchResultsWebKeys;
import com.github.lgdd.liferay.external.search.results.web.internal.portlet.ExternalSearchResultsWebPortlet;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import java.util.Locale;
import java.util.ResourceBundle;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
    immediate = true,
    property = "javax.portlet.name=" + ExternalSearchResultsWebKeys.PORTLET_ID,
    service = TemplateHandler.class
)
public class ExternalSearchResultsTemplateHandler
    extends BasePortletDisplayTemplateHandler {

  @Override
  public String getClassName() {

    return ExternalSearchResultsWebPortlet.class.getName();
  }

  @Override
  public String getName(Locale locale) {

    ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
        "content.Language", locale, getClass());

    String portletTitle = portal.getPortletTitle(
        ExternalSearchResultsWebKeys.PORTLET_ID, resourceBundle);

    return LanguageUtil.format(locale, "x-template", portletTitle, false);
  }

  @Override
  public String getResourceName() {

    return ExternalSearchResultsWebKeys.PORTLET_ID;
  }

  @Override
  protected String getTemplatesConfigPath() {

    return "com/github/lgdd/liferay/external/search/results/web/internal/template/dependencies/portlet-display-template.xml";
  }

  @Override
  public String getDefaultTemplateKey() {

    return "external-search-results-basic-ftl";
  }

  @Reference
  protected Portal portal;
}
