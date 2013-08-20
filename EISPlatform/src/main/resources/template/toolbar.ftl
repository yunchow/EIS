<div id="tb" style="height:auto;">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="sysmanage.datadic.append()">新增</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="sysmanage.datadic.edit()">编辑</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="sysmanage.datadic.removeit()">删除</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="sysmanage.datadic.accept()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="sysmanage.datadic.reject()">取消</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="sysmanage.datadic.reload()">刷新</a>
    <input class="easyui-searchbox" data-options="prompt:'请输入关键字',searcher:sysmanage.datadic.doSearch" style="width:150px;float:right;"></input>
</div>