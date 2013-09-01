jQuery.ns("flowmanage.deploy");

jQuery.define(flowmanage.deploy, base, {
	dgId: "deployPackageGrid",
	searchField: "name",
	deleteUrl: "flowmanage/deploy/delete/",
    saveUrl: "flowmanage/deploy/save.htm",
    updateUrl: "flowmanage/deploy/update.htm",
    
    init: function() {
    	context.log("flowmanage.deploy.init called");
    },
    
	onReady: function() {
		context.log("flowmanage.deploy is ready");
	},
	
	getRowValues: function() {
    	return {version: 1};
    },
    
    createUploader: function(){            
        $("#file-uploader").fineUploader({
        	request: {
			    endpoint: 'flowmanage/deploy/upload.htm'
			},
			validation: {
		        allowedExtensions: ['rar', 'zip', 'xml', 'bmp'],
		        itemLimit: 10,
		        sizeLimit: 5000000 // 50 kB = 50 * 1024 bytes
		    },
			text: {
			    uploadButton: '选择文件...',
	            cancelButton: '取消',
	            failUpload: '上传失败',
	            formatProgress: "{percent}%/{total_size}",
	            retryButton: '重试',
	            waitingForResponse: "上传中..."
			},
			/*dragAndDrop: {
				hideDropzones: false,
				extraDropzones: [document]
			},*/
			retry: {
				enableAuto: true,
				showButton: true,
				autoRetryNote: "重试 {retryNum}/{maxAuto}..."
			},
			failedUploadTextDisplay: {
		        mode: 'custom',
		        maxChars: 40,
		        responseProperty: 'error',
		        enableTooltip: true
		    },
		    messages: {
	            typeError: "{file} 文件格式不正确，支持的文件格式为: {extensions}.",
	            sizeError: "{file} 文件超过最大限制： {sizeLimit}.",
	            tooManyItemsError: "你选择的文件超过最大文件数量： {itemLimit}."
	        },
		    showMessage: function(message) {
		        $('#file-uploader').append('<div class="alert alert-error">' + message + '</div>');
		    },
			debug: true
        });
        $("#file-uploader").on("complete", function() {
        	context.log("uploader complete event fired");
        	$(".qq-upload-status-text").each(function() {
        		if(!$(this).html()) {
            		$(this).html("<font style='font-weight:bold;'>部署成功</font>" +
            				"<img style='vertical-align: bottom;height:17px;margin-left:5px;' src='resources/image/result_ok.png'>")
            	}
        	});
        });
    },
    
    onDialogCancelClick: function() {
    	this.reload();
    },
    
    onDblClickRow: function(index, rowData){
    	
    },
    
    formatDate: function(value, row, index) {
    	if (value == null) {
    		value = new Date();
    	}
    	return new Date(value).format("yyyy-MM-dd hh:mm:ss");
    },
    
    deployFlow: function() {
    	this.initUploadDialog();
    },
    
    initUploadDialog: function() {
    	$('#deployPackageDialog').dialog('open');
    	$.load("resources/plugins/fine-uploader-3.8.2/js/util.js");
    	$.load("resources/plugins/fine-uploader-3.8.2/js/button.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/handler.base.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/handler.form.api.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/handler.xhr.api.js")
		$.load("resources/plugins/fine-uploader-3.8.2/js/uploader.basic.api.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/dnd.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/upload-data.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/uploader.api.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/uploader.basic.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/uploader.js");
    	$.load("resources/plugins/fine-uploader-3.8.2/js/traditional/handler.form.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/traditional/handler.xhr.js");
    	$.load("resources/plugins/fine-uploader-3.8.2/js/ajax.requester.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/deletefile.ajax.requester.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/features.js");;
		$.load("resources/plugins/fine-uploader-3.8.2/js/iframe.xss.response.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/paste.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/promise.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/ui.handler.click.drc.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/ui.handler.click.filename.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/ui.handler.edit.filename.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/ui.handler.events.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/ui.handler.focus.filenameinput.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/ui.handler.focusin.filenameinput.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/version.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/window.receive.message.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/jquery-plugin.js");
		$.load("resources/plugins/fine-uploader-3.8.2/js/jquery-dnd.js");
		this.createUploader();
    }
});