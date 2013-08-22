<#if !rownumbers??>
	<#assign rownumbers = true>
</#if>
<#assign prefix=ns?replace(".","_")+"_datagrid">

<script type="text/javascript">
	function _${prefix}_onClickRow(index) {
		context.log("_${prefix}_onClickRow fired");
		${ns}.onClickRow(index);
	}
	function _${prefix}_onDblClickRow(index, rowData) {
		context.log("_${prefix}_onDblClickRow fired");
		${ns}.onDblClickRow(index, rowData);
	}
</script>

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
            onClickRow:_${prefix}_onClickRow,
            onDblClickRow:_${prefix}_onDblClickRow
        ">
    <thead>
        <tr>
            ${body}
        </tr>
    </thead>
</table>

