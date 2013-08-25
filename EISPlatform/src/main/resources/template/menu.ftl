<div id="${id!'mm'}" class="easyui-menu" style="width:120px;">
    <div onclick="${ns!}.append()" data-options="iconCls:'icon-add'">追加</div>
    <div onclick="${ns!}.edit()" data-options="iconCls:'icon-edit'">编辑</div>
    <div onclick="${ns!}.removeit()" data-options="iconCls:'icon-remove'">删除</div>
    <div class="menu-sep"></div>
    <div onclick="${ns!}.collapse()">折叠</div>
    <div onclick="${ns!}.expand()">展开</div>
</div>