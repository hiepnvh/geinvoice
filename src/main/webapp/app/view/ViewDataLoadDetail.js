Ext.define(App.path('view.ViewDataLoadDetail'), {
	extend : 'Ext.form.Panel',
	border : false,
	itemId : 'viewdataloaddetail',
	xtype : 'viewdataloaddetail',
	id : 'viewdataloaddetail',
	bodyStyle : 'background-color: transparent',
	border : false,
	autoScroll : true,
	layout : {
		type : 'hbox',
		pack : 'start',
		align : 'stretch'
	},
	config : {
		record : null,
		file_url : null,
		adjust : false,
		fieldDefaults : {
			margin : '5 0 0 5'
		},
		items : [{
			flex : 1,
			border : true,
			layout : {
				type : 'vbox',
				pack : 'start',
				align : 'stretch'
			},
			items : [{
				itemId : 'dataLoad',
				flex : 1,
				height : '100%',
				xtype : 'grid',
				border : false,
				stripeRows : true,
				columnLines : true,
				store :  App.path('store.DataLoad'),
				columns : [ 
				            {text : 'STT', xtype : 'rownumberer', width : 30},
				            {header : 'Mã dữ liệu KH', resizable: false, dataIndex : 'data_load_id', editor : {}},
				            {header : 'Ngày lập', resizable: false, dataIndex : 'create_date', editor : {}},
							{header : 'Mẫu số', resizable: false, dataIndex : 'symbol_of_invoice', editor : {}},
							{header : 'Ký hiệu', resizable: false, dataIndex : 'serial', editor : {}},
							{header : 'Số hóa đơn', resizable: false, dataIndex : 'invoice_no', editor : {}},
							{header : 'Tên khách hàng', resizable: false, dataIndex : 'customer_name', editor : {}},
							{header : 'Đơn vị', resizable: false, dataIndex :'company_name', editor : {}},
							{header : 'Mã số thuế', resizable: false, dataIndex : 'tax_code', editor : {}},
							{header : 'Địa chỉ', resizable: false, width: '200px', dataIndex : 'address', editor : {}},
							{header : 'Số thuê bao', resizable: false, dataIndex : 'msisdn', editor : {}},
							{header : 'Mã khách hàng', resizable: false, dataIndex : 'customer_code', editor : {}},
							{header : 'Mã hạch toán ERP', resizable: false, dataIndex : 'erp_customer_code', editor : {}},
							{header : 'Thời gian TC từ', resizable: false, dataIndex : 'from_date', editor : {}},
							{header : 'Thời gian TC đến', resizable: false, dataIndex : 'to_date', editor : {}},
							{header : 'Hình thức TT', resizable: false, dataIndex : 'kind_of_payment', editor : {}},
							{header : 'Thứ tự', resizable: false, dataIndex : 'kind_of_service_no', editor : {}},
							{header : 'Dịch vụ SD', resizable: false, width: '200px', dataIndex : 'kind_of_service', editor : {}},
							{header : 'ĐVT', resizable: false, dataIndex : 'unit', editor : {}},
							{header : 'Số lượng', resizable: false, dataIndex : 'quantity', editor : {}},
							{header : 'Đơn giá', resizable: false, dataIndex : 'unit_price', editor : {}},
							{header : 'Thành tiền', resizable: false, dataIndex : 'amount', editor : {}},
							{header : 'Cộng tiền dịch vụ', resizable: false, dataIndex : 'total', editor : {}},
							{header : 'Thuế suất', resizable: false, dataIndex : 'vat_rate', editor : {}},
							{header : 'Tiền thuế', resizable: false, dataIndex : 'vat_amount', editor : {}},
							{header : 'Tổng tiền TT', resizable: false, dataIndex : 'grand_total', editor : {}},
							{header : 'Số tiền bằng chữ', resizable: false, width: '200px', dataIndex : 'sum_in_words', editor : {}}
							],
							bbar: Ext.create('Ext.PagingToolbar', {
								store :  App.path('store.DataLoad'),
				                displayInfo: true,
				                pageSize: App.Constant.myPageSize,
				                displayInfo: true,
				                displayMsg:'Hiển thị kết quả {0} - {1} của {2} kết quả',
				                emptyMsg:"Không có dữ liệu để hiển thị&nbsp;"
							}),viewConfig: {
							      enableTextSelection: true
							   }
				}
			, {
				border : false,
				layout : 'hbox',
				defaultType : 'textfield',
				items : [{
							xtype : 'tbspacer',
							flex : 1
						}, {
							xtype : 'button',
							itemId : 'dataloadexcelupload',
							margin: '5 5 5 5',
							text : 'Chọn từ file Excel',
							itemId : 'btnUploadExcel',
							handler : function() {
								var me = this.up('form');
								me.uploadFromExcel();
							}
						}, {
							xtype : 'button',
							itemId : 'dataloadadd',
							cls : 'search_all',
							text : 'Thêm dữ liệu',
							margin : 5,
							handler : function() {
								var me = this.up('form');
								me.UpdateDB();
							}
						}, {
							xtype : 'button',
							itemId : 'invoicegenerate',
							cls : 'search_all',
							text : 'Lập hóa đơn',
							margin : 5,
							handler : function() {
								var me = this.up('form');
								me.InvoiceGenerate();
							}
						}]
			}]
		}]
	},
	activate : function() {
		var store = this.down('#dataLoad').getStore();
		store.removeAll();
		this.file_url = null;
		this.down('#dataloadadd').setDisabled(true);
	},
	search: function(msisdn, used, serial){
		var me = this;
		Ext.get(document.body).mask('Chờ giây lát..');
		var grid = this.down('#dataLoad');
		var store = this.down('#dataLoad').getStore();
		store.removeAll();
		
		store.getProxy().extraParams.adjust = me.adjust;
		
		store.getProxy().extraParams.username = App.Session.userName;
		store.getProxy().extraParams.msisdn = msisdn;
		store.getProxy().extraParams.serial = serial;
		if(used != null)
			store.getProxy().extraParams.used = used;
		store.load({	
			callback: function(records, operation, success) {
				Ext.get(document.body).unmask();
		        if (success) {
		        	if(records.length == 0)
		        		Ext.MessageBox.alert('Thông báo','Không có dữ liệu để hiển thị.');
		        } else {
		        	Ext.MessageBox.alert('Thông báo','Xảy ra lỗi.');
		        }
		    }
		});
		
//		Ext.get(document.body).mask('Chờ giây lát..');
//		var store = this.down('#dataLoad').getStore();
//		store.removeAll();
//        store.setProxy({
//            type: 'ajax',
//            actionMethods : {
//					create : 'POST',
//					read : 'POST',
//					update : 'POST',
//					destroy : 'POST'
//				},
//            url: 'dataloadget',
//            extraParams: {
//            	username : App.Session.userName,
//            	msisdn : msisdn,
//            	used : used
//            },
//            reader: {
//                type: 'json',
//                root: 'result',
//                totalProperty : 'totalCount',
//                successProperty: 'success'
//            }
//        });
//        store.load({
//        callback : function(records, options, success)
//        {
//        	Ext.get(document.body).unmask();
//        }
//        });
        
	},
	uploadFromExcel : function() {
		var store = this.down('#dataLoad').getStore();
		store.removeAll();
		var me = this;
		
		/* RESET SOME VALUE */
		me.file_url = null;
		
		/*WINDOW FOR UPLOAD FILE*/
		var win = new Ext.Window({
			layout : 'fit',
			width : 500,
			plain : true,
			title : "Upload dữ liệu hóa đơn",
			shim : false,
			modal : true,
			autoDestroy : true,
			monitorValid : true,
			resizable : false,
            items:[{
            	xtype: 'form',
    		    frame : true,
    	        defaults: {
    	            anchor: '100%',
    	            allowBlank: false,
    	            msgTarget: 'side',
    	            labelWidth: 50
    	        },
    	        items: [{
    	            xtype: 'filefield',
    	            id: 'form-file',
    	            emptyText: 'Chọn file *.xlsx',
    	            regex     : (/.(xlsx)$/i),
    				regexText : 'Chỉ chấp nhận file .xlsx',
    	            fieldLabel: 'File',
    	            name: 'file_path',
    	            itemId: 'file_path',
    	            buttonText: 'Chọn file',
//					listeners: {
//						change: function(filefield,value) {
////							from.IsValidFile(filefield, 1, ".xls,xlsx")
//							if(!me.IsValidFile(filefield, 1, ".xlsx"))
//								console.log('file not ok');
//							else
//								console.log('file ok')
//						}
//				  	  }
    	        }],

    	        buttons: [{
    	            text: 'Xem trước',
    	            handler: function(){
    	                var form = this.up('form').getForm();
    	                var maxFileSize = 3;
    	                if(!me.IsValidFile(this.up('form').down('#file_path'), maxFileSize, ".xlsx"))
    	                	Ext.MessageBox.alert('Thông báo', 'File tải lên vượt quá kích thước cho phép: '+maxFileSize+'MB');
    	                else {
    	                	if(form.isValid()){
        	                    form.submit({
        							url : 'uploadfile',
        							actionMethods : {
        								create : 'POST',
        								read : 'POST',
        								update : 'POST',
        								destroy : 'POST'
        							},
        							success : function(form, response) {
        								/*UPLOAD FILE SUCCESS*/
        								win.close();
        								Ext.get(document.body).mask('Chờ giây lát..');
        								/* SET URL TO URL */
        								me.file_url = response.result.url;
        								/* CALL FILE PREVIEW */
        								App.Action.FilePreview(App.Session.userName, response.result.url, function(options,success, response) {
        									if (success) {
        										Ext.get(document.body).unmask();
        										response = Ext.decode(response.responseText);
        										if (response.success) {
        											/* load data to store */
        											store.loadData(response.preview_list);
        											me.down('#dataloadadd').setDisabled(false);
        										} else {
        											Ext.MessageBox.alert('Thông báo','Dữ liệu quá lớn không thể xem trước' + response.info);
        										}
        									} else {
        										Ext.get(document.body).unmask();
        										Ext.MessageBox.alert('Thông báo', 'Có lỗi xảy ra trong quá trình tạo giao dịch');
        									}
        								});
        								
        							}
        						});
        	                }
    	                }
    	            }
    	        },{
    	            text: 'Reset',
    	            hidden: true,
    	            handler: function() {
    	                this.up('form').getForm().reset();
    	            }
    	        }]
    		}]
		});

		win.show();
	},
	to_json : function(workbook) {
		var X= XLS;
		
		var result = {};
		workbook.SheetNames.forEach(function(sheetName) {
			var roa = X.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
			if(roa.length > 0){
				result[sheetName] = roa;
			}
		});
		return result;
	},
	fixdata : function(data) {
		//console.log('fix data');
		var o = "", l = 0, w = 10240;
		for(; l<data.byteLength/w; ++l) o+=String.fromCharCode.apply(null,new Uint8Array(data.slice(l*w,l*w+w)));
		o+=String.fromCharCode.apply(null, new Uint8Array(data.slice(l*w)));
		return o;
	},
	UpdateDB : function() {
		Ext.get(document.body).mask('Chờ giây lát..');
		var me = this;
		var store = this.down('#dataLoad').getStore();
		
		App.Action.DataLoadUpdate(App.Session.userName, me.file_url, function(options,success, response) {
			if (success) {
				Ext.get(document.body).unmask();
				response = Ext.decode(response.responseText);
				if (response.success) {
					store.removeAll();
					Ext.MessageBox.alert('Thông báo','Thêm dữ liệu thành công. ');
				} else {
					Ext.MessageBox.alert('Thông báo','Thêm dữ liệu thất bại. ' + response.info);
				}
				me.file_url = null;
				me.down('#dataloadadd').setDisabled(true);
			} else {
				Ext.get(document.body).unmask();
				Ext.MessageBox.alert('Thông báo', 'Thêm dữ liệu thất bại. '+ response.info);
			}
		});
	},
	InvoiceGenerate : function() {
		var me = this;

		var win = new Ext.Window({
			layout : 'fit',
			width : 500,
			plain : true,
			title : 'Thông báo',
			shim : false,
			modal : true,
			autoDestroy : true,
			monitorValid : true,
			resizable : false,
			autoShow: true,
            items:[{
            	xtype: 'form',
    		    frame : true,
    	        defaults: {
    	            anchor: '100%',
    	            allowBlank: false,
    	            msgTarget: 'side',
    	            labelWidth: 50
    	        },

    	        items: [{
//    	            xtype: 'textfield',
//    	            itemId : 'serial',
//    	            name : 'serial',
//    	            allowBlank: false,
//    	            emptyText: 'Không được để trống -Ký hiệu hóa đơn- sẽ lập',
//    	            fieldLabel: 'Ký hiệu'

					xtype : 'combobox',
					itemId : 'serial',
					name : 'serial',
					store : App.path('store.Serial'),
					displayField : 'serial',
					valueField : 'serial',
					labelWidth : 100,
					forceSelection : true,
					fieldLabel : 'Chọn kí hiệu <span style="color: red;">(*)</span>',
					listeners: {
//						change: function( combobox, newValue, oldValue, eOpts ){
//				  			var newRecord = combobox.findRecordByValue(newValue);
//				  	    }
				  	  }
				
    	        }],

    	        buttons: [{
    	            text: 'Chấp nhận',
    	            handler: function(){
    	                var form = this.up('form').getForm();
    	                var serial = form.findField("serial").getValue();
		    			if(serial == null) {
		    				Ext.MessageBox.alert('Thông báo', 'Không cho phép để trống "Ký hiệu".');
		    			} else {
		    				win.close();
		    				Ext.MessageBox.show({
		    				    title:'Thông báo',
		    				    msg:'Xác nhận gửi lệnh lập hóa đơn với Ký hiệu '+ serial +' ?',
		    				    buttonText: {ok: 'Xác nhận', cancel: "Hủy"},
		    				    fn: function(btn){
		    				    	if(btn == 'ok') {
		    				    		/*ACTION WHEN OK*/
		    				    		Ext.get(document.body).mask('Chờ giây lát..');
		    							var store = me.down('#dataLoad').getStore();
		    							
		    							var msg1 = 'Gửi lệnh lập hóa đơn ';
		    							var msg2 = 'Kết quả sẽ được gửi qua email.';
		    							
		    							App.Action.InvoiceGenerate(App.Session.userName, serial, me.adjust, function(options,success, response) {
		    								if (success) {
		    									Ext.get(document.body).unmask();
		    									response = Ext.decode(response.responseText);
		    									if (response.success) {
		    										store.removeAll();
		    										Ext.MessageBox.alert('Thông báo',msg1 + 'thành công. ' + msg2);
		    									} else {
		    										Ext.MessageBox.alert('Thông báo',msg1 + 'thất bại. ' + response.info);
		    									}
		    									me.file_url = null;
		    									me.down('#dataloadadd').setDisabled(true);
		    								} else {
		    									Ext.get(document.body).unmask();
		    									Ext.MessageBox.alert('Thông báo', msg1 + 'thất bại. ' + response.info);
		    								}
		    							});
		    				    	}/*END ACTION WHEN OK*/
		    				    }
		    				});
		    			}
    	            }
    	        }, {
    	            text: 'Hủy',
    	            handler: function(){
    	            	win.close();
    	            }
    	        	
    	        }]
    		}]
		});
	},
	ValidAccountInfoData: function(store){
		return true;
	},
	ValidFomartMsisdn : function(msisdn){
		var patt = new RegExp("(099|0199|8499|84199)[0-9]{7}");
		var res = patt.test(msisdn);
		return res;
	},
	IsValidFile : function (fileField, fileSize, allowedExtensions) {
		//For IE 9, IE 8, IE 7
		if (Ext.ieVersion > 0 && Ext.ieVersion >= 9) {
	 		var sizeInMB = fileField.fileInputEl.dom.size / (1024 * 1024);
	 		var fileExtension = fileField.value.replace(/^.*[\\\/]/, '').split('.')[fileField.value.replace(/^.*[\\\/]/, '').split('.').length-1];
	 		console.log(fileExtension);
	 		if (allowedExtensions.indexOf(fileExtension) > -1) {
				if (!Ext.isEmpty(fileSize)) {
					if (sizeInMB <= fileSize) {
	 					return true;
	 				} else
	 					return false;
	 			}
	 			return true;
	 		}
	 	}
		// For IE Version 10 and above, Chrome, Firefox, Safari etc
	 	if ((Ext.ieVersion === 0 || Ext.ieVersion > 9) &&
	            fileField.fileInputEl.dom.files.length > 0) {
			var sizeInMB = fileField.fileInputEl.dom.files[0].size / (1024 * 1024);
			var fileExtension = fileField.value.replace(/^.*[\\\/]/, '').split('.')[fileField.value.replace(/^.*[\\\/]/, '').split('.').length-1];
			console.log(fileExtension);
			if (allowedExtensions.indexOf(fileExtension) > -1) {
				if (!Ext.isEmpty(fileSize)) {
					if (sizeInMB <= fileSize) {
						return true;
					} else
						return false;
				}
				return true;
			}
		}
	 
		return false;
	}
});
