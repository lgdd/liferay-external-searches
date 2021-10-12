<#if entries?has_content>
    <#list entries as result>
        <#assign metadata = result.getMetadata()>
        <#if metadata["is_answered"]>
          &#x1F7E2;&#xA0;
        <#else>
          &#x1F534;&#xA0;
        </#if>

        <a href="${result.getURL()}">${result.getName()}</a><br/>

        <#assign tags = metadata["tags"]>
        <#list tags as tag>
          <span class="badge badge-light">${tag}</span>
        </#list>
        <br/><br/>
    </#list>
</#if>