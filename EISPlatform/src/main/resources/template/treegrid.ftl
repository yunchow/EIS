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
	function _${fmtoken}_onContextMenu(e, rowData) {
		${ns}.onContextMenu(e, rowData);
	}
</script>

<table id="${id!'dg'}" class="easyui-treegrid"
        data-options="
            singleSelect: true,
            toolbar: '${toolbar!"#tb"}',
            fit: true,
            fitColumns: true,
            border: false,
            url: '${url}',
            method: 'post',
            rownumbers: ${rownumbers?c},
            idField: '${idField!"id"}',
            treeField: '${treeField!"name"}',
            onClickRow:_${fmtoken}_onClickRow,
            onDblClickRow:_${fmtoken}_onDblClickRow,
            onContextMenu:_${fmtoken}_onContextMenu
        ">
    <thead>
        <tr>
            ${body}
        </tr>
    </thead>
</table>

