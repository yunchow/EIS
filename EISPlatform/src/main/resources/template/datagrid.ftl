<#if !rownumbers??>
	<#assign rownumbers = true>
</#if>

<table id="${id!'dg'}" class="easyui-datagrid"
        data-options="
            singleSelect: true,
            toolbar: '${toolbar!"#tb"}',
            fit: true,
            fitColumns: true,
            border: false,
            url: '${url}',
            method: 'post',
            rownumbers: ${rownumbers?c},
            pagination: true,
            pageSize: ${pageSize!30},
            onClickRow:${ns!'me'}.onClickRow,
            onDblClickRow:${ns!'me'}.onDblClickRow
        ">
    <thead>
        <tr>
            ${body}
        </tr>
    </thead>
</table>