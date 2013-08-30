<div id="${id!}" class="easyui-dialog" title="${title!}" style="width:${width!'auto'};height:${height!'auto'};padding:10px;"
    data-options="
            iconCls: 'icon-add',
            modal: true,
            closed: true,
            buttons: [{
                text:'确认',
                iconCls:'icon-ok',
                handler:function(){
                    ${ns}.onDialogConfirmClick();
                }
            },{
                text:'取消',
                iconCls:'icon-cancel',
                handler:function(){
                    $('#${id!}').dialog('close');
                    ${ns}.onDialogCancelClick();
                }
            }]
        ">
    ${body!}
</div>