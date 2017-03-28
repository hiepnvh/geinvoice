Ext.define(App.path('view.ViewInvoiceExport'), {
	extend : 'Ext.form.Panel',
	itemId : 'ViewInvoiceExport',
	bodyStyle : 'background-color: transparent',
	border : false,
	autoScroll : true,
	title : 'Xuất hóa đơn dạng thể hiện và hóa đơn gốc',
	layout : {
		type : 'hbox',
		pack : 'start',
		align : 'stretch'
	},
	config : {
		record : null,
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
				border : false,
				layout : 'hbox',
				items : [{
							flex : 1,
							margin : '10 15 5 50',
							itemId : 'in_month',
							xtype : 'datefield',
							format : 'm/Y',
							fieldLabel : 'Tháng<span style="color: red;">(*)</span>',
							value : new Date()
		    	        }, {
							flex : 1,
							margin : '5 50 5 5',
			            	xtype: 'form',
			            	itemId: 'file_form',
			    		    frame : false,
			    		    border : false,
			    	        defaults: {
			    	            anchor: '100%',
			    	            allowBlank: false,
			    	            msgTarget: 'side',
			    	            labelWidth: 50
			    	        },
			    	        items: [{
			    	            xtype: 'filefield',
			    	            emptyText: 'Chọn file *.xlsx',
			    	            regex     : (/.(xlsx)$/i),
			    				regexText : 'Chỉ chấp nhận file .xlsx',
			    	            fieldLabel: 'File',
			    	            name: 'file_path',
			    	            itemId: 'file_path',
			    	            buttonText: 'Chọn file',
			    	        }]
						} ]
			}, {
				border : false,
				layout : 'hbox',
				defaultType : 'textfield',
				items : [ {
					xtype : 'tbspacer',
					flex : 1
				}, {
					margin : '15 50 5 0',
					xtype : 'button',
					cls : 'bt_login',
					text : 'Lấy Hóa đơn',
					handler : function() {
						var me = this.up('form');
						me.Export();
					}
				}]
			}]
		}]
	},
	activate : function() {
		this.getForm().reset();
	},

	Export : function () {
		
		var me = this;
		var _in_month = me.down('#in_month').getValue();
		var in_month = _in_month.getFullYear() + '-' +( _in_month.getMonth() + 1);
		var form = this.down('#file_form').getForm();
		if(me.IsValidForm() && form.isValid()){
        		Ext.get(document.body).mask('Chờ giây lát..');
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
						
						/* SET URL TO URL */
						me.file_url = response.result.url;
						/* CALL FILE PREVIEW */
						App.Action.InvoiceExport(App.Session.userName, response.result.url, in_month, function(options,success, response) {
							if (success) {
								Ext.get(document.body).unmask();
								response = Ext.decode(response.responseText);
								if (response.success) {
									//Download
									var hiddenIFrameID = 'hiddenDownloader',
							        iframe = document.getElementById(hiddenIFrameID);
								    if (iframe === null) {
								        iframe = document.createElement('iframe');
								        iframe.id = hiddenIFrameID;
								        iframe.style.display = 'none';
								        document.body.appendChild(iframe);
								    }
								    iframe.src = response.url;
								} else {
									Ext.MessageBox.alert('Thông báo', response.info);
								}
							} else {
								Ext.get(document.body).unmask();
								Ext.MessageBox.alert('Thông báo', 'Có lỗi xảy ra trong quá trình xử lý');
							}
						});
						
					}
				});
            }
	},
	IsValidForm : function() {
		if(this.down('#file_path').getValue() == "") {
			Ext.MessageBox.alert('Thông báo', 'Chọn D/s số thuê bao.');
			return false;
		}
		return true;
	},
	ValidFomartMsisdn : function(msisdn){
		var patt = new RegExp("(099|0199|8499|84199)[0-9]{7}");
		var res = patt.test(msisdn);
		return res;
	}
});
