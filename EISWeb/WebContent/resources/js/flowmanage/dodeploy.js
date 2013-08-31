jQuery.ns("flowmanage.dodeploy");

jQuery.define(flowmanage.dodeploy, base, {
	dgId: "dodeployPackageGrid",
	searchField: "name",

    init: function() {
    	$.load("resources/plugins/SWFUpload-v2.2.0.1/swfupload.js");
    	$.load("resources/plugins/jquery.swfupload/jquery.swfupload.js");
    	context.log("flowmanage.deploy.init called");
    },
    
	onReady: function() {
		$("#swfupload-control").swfupload({
			upload_url: "upload.php",
			file_size_limit : "10240",
			file_types : "*.*",
			file_types_description : "All Files",
			file_upload_limit : "0",
			flash_url : "resources/plugins/SWFUpload-v2.2.0.1/Flash/swfupload.swf",
			button_image_url : 'resources/plugins/jquery.swfupload/XPButtonUploadText_61x22.png',
			button_width : 61,
			button_height : 22,
			button_placeholder_id : "fileupload",
			debug: true
		});
		context.log("flowmanage.dodeploy is ready");
	},
	
	addFiles: function() {
		$("#swfupload-control").swfupload('selectFiles');
	},
	
	listeners: {
		swfuploadLoaded: function(event){
			$('.log', this).append('<li>Loaded</li>');
		},
		fileQueued: function(event, file){
			$('.log', this).append('<li>File queued - '+file.name+'</li>');
			// start the upload once it is queued
			// but only if this queue is not disabled
			if (!$('input[name=disabled]:checked', this).length) {
				$(this).swfupload('startUpload');
			}
		},
		fileQueueError: function(event, file, errorCode, message){
			$('.log', this).append('<li>File queue error - '+message+'</li>');
		},
		fileDialogStart: function(event){
			$('.log', this).append('<li>File dialog start</li>');
		},
		fileDialogComplete: function(event, numFilesSelected, numFilesQueued){
			$('.log', this).append('<li>File dialog complete</li>');
		},
		uploadStart: function(event, file){
			$('.log', this).append('<li>Upload start - '+file.name+'</li>');
			// don't start the upload if this queue is disabled
			if ($('input[name=disabled]:checked', this).length) {
				event.preventDefault();
			}
		},
		uploadProgress: function(event, file, bytesLoaded){
			$('.log', this).append('<li>Upload progress - '+bytesLoaded+'</li>');
		},
		uploadSuccess: function(event, file, serverData){
			$('.log', this).append('<li>Upload success - '+file.name+'</li>');
		},
		uploadComplete: function(event, file){
			$('.log', this).append('<li>Upload complete - '+file.name+'</li>');
			// upload has completed, lets try the next one in the queue
			// but only if this queue is not disabled
			if (!$('input[name=disabled]:checked', this).length) {
				$(this).swfupload('startUpload');
			}
		},
		uploadError: function(event, file, errorCode, message){
			$('.log', this).append('<li>Upload error - '+message+'</li>');
		}
	},
	
    addGridRow: function(file) {
    	this.files.push(file);
    	var size = file.size;
    	if (size > 1024) {
    		size = parseInt(size / 1024) + "KB"
    	}
    	var lastTime = file.lastModifiedDate;
    	if (lastTime) {
    		lastTime = new Date(lastTime).format("yyyy-MM-dd hh:mm:ss");
    	}
    	var data = {
    			fileName: file.name,
    			fileSize: size,
    			fileType: file.type,
    			lastUpdateTime: lastTime
    	};
    	this.getGrid().datagrid("appendRow", data);
    },
    
    deleteItems: function() {
    	var checkList = this.getGrid().datagrid("getChecked");
    	if (!checkList || checkList.length == 0) {
    		context.warn("请选择要删除的文件");
    	}
    	for (var i in checkList) {
    		var item = checkList[i];
    		var index = this.getGrid().datagrid("getRowIndex", item);
    		this.getGrid().datagrid("deleteRow", index);
    	}
    },
    
    doDeploy: function() {
    	$('#fileupload').fileupload("send", {
    		files: this.files
    	});
    }
	
});