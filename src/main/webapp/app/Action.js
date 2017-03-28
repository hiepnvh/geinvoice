Ext.define(App.path('Action'), {
	alternateClassName: 'App.Action',
	statics: {
		ResponseCode: {
			OK: 0
		},
		AjaxRequest: function(url ,params, callback, scope) {
	        Ext.Ajax.request({
	            url: url,
	            actionMethods : {
						create : 'POST',
						read : 'POST',
						update : 'POST',
						destroy : 'POST'
					},
//				headers : {
//	                "Access-Control-Allow-Origin":"http://http://10.16.69.83/"
//	            },
	            params: params,
	            timeout: 300000,
	            callback : callback,//options,success,response 
	            scope: scope
	        });
		},
		loadJsonStore:function( url, params,model, callback, scope) {
	        Ext.create('Ext.data.Store', {
	             model: model,
	             proxy: {
	                 type: 'ajax',
	                 url: url,//App.Setting.getHostUrl() + '/' + url,
	                 extraParams: params,
	                 reader: {
	                     type: 'json',
	                     root: 'data'
	                 }
	             }
	         }).load({ callback: callback, scope: scope});
	     },
	     
	     /* GET FUNCTION */
	     UserMenuFunctionGet:function(userName, callback, scope){
//	    	 this.AjaxRequest('http://localhost:8080/gportal/usermenufunctionget',{userName:userName, webapp_id:App.Constant.WEBAPP_ID},callback, scope);
//	    	 this.AjaxRequest('https://bpm.gmobile.vn/gportal/usermenufunctionget',{userName:userName, webapp_id:App.Constant.WEBAPP_ID},callback, scope);
//	    	 this.AjaxRequest('http://10.16.69.83/gportal/usermenufunctionget',{userName:userName, webapp_id:App.Constant.WEBAPP_ID},callback, scope);
	    	 this.AjaxRequest(location.origin + '/gportal/usermenufunctionget',{userName:userName, webapp_id:App.Constant.WEBAPP_ID},callback, scope);
	     },
	     /*******************************************************************************/
	     
	     /* FILE */
	     DownloadFile:function(username, filename, callback, scope){
	         this.AjaxRequest('downloadfile',{username:username, filename:filename},callback, scope);
	     },
	     
	     /*******************************************************************************/
	     
	     /* USER */
	     UserGet:function(webapp_id,callback, scope){
	          this.AjaxRequest('getuserinfo',{webapp_id:webapp_id},callback, scope);
	     },
	     UserLoginInfoGet:function(noParam, callback, scope){
	         this.AjaxRequest('userLoginInfoGet',{noParam:noParam}, callback, scope);
	     },
	     
	     /* FILE PREVIEW */
	     FilePreview:function(userName, file_path, callback, scope){
	    	 this.AjaxRequest('filepreview',{userName:userName,file_path:file_path},callback, scope);
	     },
	     
	     /* DATA LOAD */
	     DataLoadUpdate:function(userName, file_path, callback, scope){
	    	 this.AjaxRequest('dataloadupdate',{userName:userName,file_path:file_path},callback, scope);
	     },
	     
	     /* INVOICE */
	     InvoiceBatchUpdate:function(userName, invoice_batch, callback, scope){
	    	 this.AjaxRequest('invoicebatchupdate',{userName:userName,invoice_batch:invoice_batch},callback, scope);
	     },
	     InvoiceGenerate:function(userName, serial, adjust, callback, scope){
	    	 this.AjaxRequest('invoicegenerate',{userName:userName, serial:serial, adjust:adjust},callback, scope);
	     },
	     InvoiceAdjust:function(userName, file_path, adjust, callback, scope){
	    	 this.AjaxRequest('dataloadupdate',{userName:userName,file_path:file_path, adjust:adjust},callback, scope);
	     },
	     InvoiceUpdate:function(userName, params, callback, scope){
	    	 this.AjaxRequest('invoiceupdate',{userName:userName, params:params},callback, scope);
	     },
	     InvoiceConversion:function(userName, file_path, in_month, callback, scope){
	    	 this.AjaxRequest('invoiceconversion',{userName:userName,file_path:file_path, in_month:in_month},callback, scope);
	     },
	     InvoiceExport:function(userName, file_path, in_month, callback, scope){
	    	 this.AjaxRequest('invoicefilegetrequest',{userName:userName,file_path:file_path, in_month:in_month},callback, scope);
	     },
	     /* REPORT */
	     InvoiceRevenueReport:function(userName, invoice_agency_id, in_month, callback, scope){
	    	 this.AjaxRequest('invoicerevenuereport',{userName:userName, invoice_agency_id:invoice_agency_id, in_month:in_month},callback, scope);
	     },
	     InvoiceUsageReport:function(userName, invoice_agency_id, in_month, fixed, callback, scope){
	    	 this.AjaxRequest('invoiceusagereport',{userName:userName, invoice_agency_id:invoice_agency_id, in_month:in_month, fixed:fixed},callback, scope);
	     }
    }
});

