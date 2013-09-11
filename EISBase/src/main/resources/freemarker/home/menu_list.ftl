<div class="easyui-accordion" data-options="fit:true,border:false">
<#list menus as menu >
    <div title="${menu.name}" data-options="iconCls:'${menu.icon}'" style="padding:0px">
        <ul id="tt" class="easyui-tree" data-options="lines:false">
        <#list menu.children as child>
        <#if  child.status == 'Y'>
	    	<#if child.children?size gt 0>
	    	<li>
		        <span>${child.name}</span>
		        <ul>
		        <#list child.children as grandson>
		            <li>
		                <span><a href="javascript:;" onclick="context.addTab('${grandson.name}','${grandson.url}','${grandson.icon}')">${grandson.name}</a></span>
		            </li>
		        </#list>
		        </ul>
	        </li>
	        <#else>
	        <li>
	        	<span><a href="javascript:;" onclick="context.addTab('${child.name}','${child.url}','${child.icon}')">${child.name}</a></span>
	        </li>
	        </#if>
        </#if>
        </#list>
        </ul>
        <#-- 
        <ul>
        <#list menu.children as child>
        	<li>
				<div data-options="iconCls:'pencil'" onclick="context.addTab('${child.name}','${child.url}','${child.icon}')">
					<a href="javascript:;">
						<span class="icon ${child.icon}">&nbsp;</span>
						<span class="nav">${child.name}</span>
					</a>
				</div>
			</li>
        </#list>
        </ul>
        -->
    </div>	
</#list>
</div>
  