<div id="${id!}" class="easyui-dialog" title="${title!}" style="width:${width!'auto'};height:${height!'auto'};padding:10px;"
    data-options="
            iconCls: '${icon!}',
            modal: true,
            closed: true,
            buttons: [
            <#if !confirm?? || confirm>
            {
                text:'${confirmText!}',
                iconCls:'icon-ok',
                handler:function(){
                    ${ns}.onDialogConfirmClick();
                }
            },
            </#if>
            {
                text:'关闭',
                iconCls:'icon-cancel',
                handler:function(){
                    $('#${id!}').dialog('close');
                    ${ns}.onDialogCancelClick();
                }
            }]
        ">
    ${body!}
</div>