<div class="easyui-accordion" data-options="fit:true,border:false">
<#list menus as menu >
    <div title="${menu.name}" style="padding:0px" data-options="iconCls:'${menu.icon}'">
        <ul id="tt" class="easyui-tree" data-options="lines:false,iconCls:'icon-reload'">
        <#list menu.children as child>
        <#if  child.status == 'Y'>
		    <li>
		    	<#if child.children?size gt 0>
			        <span>${child.name}</span>
			        <ul>
			        <#list child.children as grandson>
			        <#if  grandson.status == 'Y'>
			            <li>
			                <span><a href="javascript:;" onclick="context.addTab('${grandson.name}','${grandson.url}','${grandson.icon}')">${grandson.name}</a></span>
			            </li>
			        </#if>
			        </#list>
			        </ul>
		        <#else>
		        	<span><a href="javascript:;" onclick="context.addTab('${child.name}','${child.url}','${child.icon}')">${child.name}</a></span>
		        </#if>
		    </li>
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
  