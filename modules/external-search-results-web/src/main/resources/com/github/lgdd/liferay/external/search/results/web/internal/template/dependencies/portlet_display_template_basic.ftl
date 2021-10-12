<#if entries?has_content>
    <#list entries as result>
      <a href="${result.getURL()}">${result.getName()}</a><br/>
    </#list>
</#if>