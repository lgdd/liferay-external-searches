<%@ page
        import="com.github.lgdd.liferay.external.search.results.web.internal.portlet.ExternalSearchResultsWebPortlet" %>
<%@ page import="com.liferay.portal.kernel.util.PortalUtil" %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="com.liferay.petra.string.StringPool" %>
<%@ include file="init.jsp" %>

<%
    List<String> externalSearchEngines =
            (List<String>) request.getAttribute("externalSearchEngines");

    String externalSearchEngine =
            PrefsParamUtil.getString(
                    portletPreferences, request, "externalSearchEngine", null
            );
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL"/>

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL"/>

<liferay-frontend:edit-form
        action="<%= configurationActionURL %>"
        method="post"
        name="fm"
>
    <aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>"/>
    <aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>"/>

    <liferay-frontend:edit-form-body>
        <clay:row>
            <clay:col md="12">
                <liferay-frontend:fieldset-group>
                    <liferay-frontend:fieldset>
                        <div class="display-template">
                            <liferay-ddm:template-selector
                                    className="<%= ExternalSearchResultsWebPortlet.class.getName() %>"
                                    displayStyle="<%= displayStyle %>"
                                    displayStyleGroupId="<%= displayStyleGroupId %>"
                                    refreshURL="<%= PortalUtil.getCurrentURL(request) %>"
                            />
                        </div>
                    </liferay-frontend:fieldset>
                </liferay-frontend:fieldset-group>
            </clay:col>
            <clay:col md="12">
                <liferay-frontend:fieldset-group>
                    <liferay-frontend:fieldset>
                        <aui:select label="external-search-engine"
                                    name="preferences--externalSearchEngine--">
                            <aui:option label=''
                                        selected='<%= externalSearchEngine == null %>'
                                        value="<%= StringPool.BLANK %>"/>
                            <%
                                for (String searchEngine : externalSearchEngines) {
                            %>

                            <aui:option label='<%= searchEngine %>'
                                        selected='<%= searchEngine.equalsIgnoreCase(externalSearchEngine) %>'
                                        value="<%= searchEngine %>"/>

                            <%
                                }
                            %>
                        </aui:select>
                    </liferay-frontend:fieldset>
                </liferay-frontend:fieldset-group>
            </clay:col>
        </clay:row>
    </liferay-frontend:edit-form-body>

    <liferay-frontend:edit-form-footer>
        <aui:button type="submit"/>

        <aui:button type="cancel"/>
    </liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>
