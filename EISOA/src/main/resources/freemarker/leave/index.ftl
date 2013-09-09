<#-- Deprecated at 13-9-9 -->
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'west',split:true,border:true" title="请假业务导航" style="width:180px;padding:10px;line-height:20px;">
    	<ul class="easyui-tree" data-options="lines:true">
		    <li><span><a href="javascript:$('#leaveContainer').panel('refresh', 'oa/leave/form.htm');">待处理</a></span></li>
		    <li><span><a href="javascript:$('#leaveContainer').panel('refresh', 'oa/leave/form.htm');">已处理</a></span></li>
		    <li><span><a href="javascript:$('#leaveContainer').panel('refresh', 'oa/leave/form.htm');">请假申请</a></span></li>
		</ul>
    </div>
    <div id="leaveContainer" data-options="region:'center',border:true" href="oa/leave/form.htm"  title='请假申请' style="padding:5px;">
    </div>
</div>

