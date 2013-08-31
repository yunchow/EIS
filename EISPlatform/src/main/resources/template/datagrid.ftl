<#if !rownumbers??>
	<#assign rownumbers = true>
</#if>

<script type="text/javascript">
	function _${fmtoken}_onClickRow(index) {
		${ns}.onClickRow(index);
	}
	function _${fmtoken}_onDblClickRow(index, rowData) {
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
            url: '${url!}',
            method: 'post',
            rownumbers: ${rownumbers?c},
            pagination: true,
            pageSize: ${pageSize!30},
            onClickRow:_${fmtoken}_onClickRow,
            onDblClickRow:_${fmtoken}_onDblClickRow
        ">
    <thead>
        <tr>
            ${body}
        </tr>
    </thead>
</table>

