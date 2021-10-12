<%@ page import="com.liferay.portal.kernel.util.PrefsParamUtil" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ddm" prefix="liferay-ddm" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %>

<liferay-theme:defineObjects/>
<portlet:defineObjects/>

<%
    String displayStyle =
            PrefsParamUtil.getString(portletPreferences, request, "displayStyle", "basic");

    long displayStyleGroupId =
            PrefsParamUtil.getLong(portletPreferences, request, "displayStyleGroupId",
                                   themeDisplay.getScopeGroupId());
%>