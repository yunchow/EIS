jQuery.ns("flowmanage.processdef");

jQuery.define(flowmanage.processdef, base, {
	dgId: "processdefGrid",
	searchField: "name",
	
    init: function() {
    	context.log("flowmanage.processdef.init called");
    },
    
	onReady: function() {
		context.log("flowmanage.processdef is ready");
	},
	
	onClickRow: function(index){
		var row = this.$dg.datagrid('getSelected');
		if (row.suspend) {
			$("#activate").show();
			$("#suspend").hide();
		}
		else {
			$("#suspend").show();
			$("#activate").hide();
		}
    },
    
    convertToModel: function() {
    	var row = this.$dg.datagrid('getSelected');
    	if (!row) {
    		context.warn("请选择要转换的流程定义");
    	}
    	$.post("flowmanage/processdef/convert2/model/"+ row.id +".htm", function(data) {
    		context.info("转换成功，你可以在模型管理里查看或者编辑该模型");
    	});
    },
	
	formatSuspend: function(value, row, index) {
		if (typeof(value) != 'boolean') {
			return "<span style='color:yellow'>未知</span>";
		}
		if (value === false) {
			return "<span style='color:green;font-weight:bold;'>活动</span>";
		}
		return "<span style='color:red'>挂起</span>";
	},
	
	suspend: function() {
		var row = this.$dg.datagrid('getSelected'), me = this;
		if (!row) {
			context.warn("请选择要挂起的流程定义");
			return;
		}
		$.post("flowmanage/processdef/suspend.htm", {id: row.id}, function(data) {
			if (data == 'alreadySuspended') {
				context.warn("挂起操作失败：该流程已被挂起");
			}
			else {
				me.reload();
				$("#suspend").hide();
				$("#activate").hide();
			}
		})
	},
	
	activate: function() {
		var row = this.$dg.datagrid('getSelected'), me = this;
		if (!row) {
			context.warn("请选择要激活的流程定义");
			return;
		}
		$.post("flowmanage/processdef/activate.htm", {id: row.id}, function(data) {
			if (data == 'alreadyActive') {
				context.warn("激活操作失败：该流程已处于激活状态");
			}
			else {
				me.reload();
				$("#suspend").hide();
				$("#activate").hide();
			}
		})
	},
	
	formatXML: function(value, row, index) {
		return "<a href='javascript:window.open(\"flowmanage/processdef/xml/"+ row.id +".htm\");' style='text-decoration:underline;'>"+ value +"</a>";
	},
	
	formatDiagram: function(value, row, index) {
		return "<a href='javascript:flowmanage.processdef.viewFlowPicture(\""+ row.id +"\");' style='text-decoration:underline;'>"+ value +"</a>";
	},
	
	viewFlowPicture: function(pdid) {
		if (!pdid){
			var row = this.$dg.datagrid('getSelected');
			if (!row) {
				context.warn("请选择流程");
				return;
			}
			else {
				pdid = row.id;
			}
		}
		$.blockUI({ 
            message: $('<p style="color:red;text-align:left;margin-left:5px;">点击流程图可关闭<p><img style="cursor:pointer;" onclick="$.unblockUI();" src=flowmanage/processdef/img/'+ pdid +'.htm>'), 
            css: { 
                top:  '100px', 
                left: '100px', 
                width: 'auto' 
            } 
        });
	}
    
});