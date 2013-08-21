<div id="${id!"tb"}" style="height:auto;">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="${ns!'me'}.append()">新增</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="${ns!'me'}.edit()">编辑</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="${ns!'me'}.removeit()">删除</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="${ns!'me'}.accept()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="${ns!'me'}.reject()">取消</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="${ns!'me'}.reload()">刷新</a>
    ${append!}
    <input class="easyui-searchbox" data-options="prompt:'请输入关键字',searcher:${ns!'me'}.doSearch" style="width:150px;float:right;"></input>
</div>