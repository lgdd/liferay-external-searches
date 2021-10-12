<#if entries?has_content>
    <#list entries as result>
      <#assign metadata = result.getMetadata()>
      <a href="${result.getURL()}">${result.getName()}</a>
      <br/>
      <span>&#x2B06;&#xA0;<small>${metadata["ups"]}</small></span>
      <span class="badge badge-secondary">${metadata["subreddit_name_prefixed"]}</span>
      <br/>
      <br/>
    </#list>
</#if>