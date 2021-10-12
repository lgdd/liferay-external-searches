<%@ page import="com.liferay.petra.string.StringPool" %>
<%@ page import="com.github.lgdd.liferay.external.search.api.ExternalSearchResult" %>
<%@ page
        import="com.github.lgdd.liferay.external.search.results.web.constants.ExternalSearchResultsWebKeys" %>
<%@ page
        import="com.github.lgdd.liferay.external.search.results.web.internal.portlet.ExternalSearchResultsWebPortlet" %>
<%@ include file="init.jsp" %>

<%
    String externalSearchEngine =
            PrefsParamUtil.getString(
                    portletPreferences, request, "externalSearchEngine", null
            );
%>

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL"/>

<c:choose>
    <c:when test="<%= externalSearchEngine == null || externalSearchEngine.equals(StringPool.BLANK) %>">
        <div class="alert alert-info text-center">
            <aui:a href="javascript:;" onClick="<%= portletDisplay.getURLConfigurationJS() %>">
                Configure an external search engine to be used.
            </aui:a>
        </div>
    </c:when>
    <c:otherwise>
        <%
            List<ExternalSearchResult> searchResults =
                    (List<ExternalSearchResult>) request
                            .getAttribute(ExternalSearchResultsWebKeys.ATTR_SEARCH_RESULTS);
        %>
        <c:if test="<%= searchResults != null && !searchResults.isEmpty() %>">
            <liferay-ddm:template-renderer
                    className="<%= ExternalSearchResultsWebPortlet.class.getName() %>"
                    displayStyle="<%= displayStyle %>"
                    displayStyleGroupId="<%= displayStyleGroupId %>"
                    entries="<%= searchResults %>"
            >

                <%-- Default render when there is no template available. --%>
                <c:forEach var="searchResult" items="<%= searchResults %>">
                    <a href="${searchResult.URL}">${searchResult.name}</a><br/>
                </c:forEach>

            </liferay-ddm:template-renderer>
        </c:if>
    </c:otherwise>
</c:choose>