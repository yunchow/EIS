<div class="easyui-accordion" data-options="fit:true,border:false">
<#list menus as menu >
    <div title="${menu.name}" data-options="iconCls:'${menu.icon}'" style="padding:0px">
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
    </div>	
</#list>
</div>
  