<script type="text/javascript">
	function _${fmtoken}_doSearch(value, name) {
		${ns}.doSearch(value, name);
	}
</script>
<div id="${id!"tb"}" style="height:auto;">
    <#if !add?? || add>
    	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="${ns!}.append()">新增</a>
    </#if>
    <#if !update?? || update>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="${ns!}.edit()">编辑</a>
	</#if>
	<#if !delete?? || delete>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="${ns!}.removeit()">删除</a>
	</#if>
	<#if !save?? || save>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="${ns!}.accept()">保存</a>
	</#if>
	<#if !cancel?? || cancel>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="${ns!}.reject()">取消</a>
	</#if>
	<#if !reload?? || reload>
	    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="${ns!}.reload()">刷新</a>
	</#if>
    ${append!}
    <#if !searchbox?? || searchbox>
    	<input class="easyui-searchbox" data-options="prompt:'请输入关键字',searcher:_${fmtoken}_doSearch,menu:'${menu!}'" style="width:300px;float:right;"></input>
    </#if>
</div>
