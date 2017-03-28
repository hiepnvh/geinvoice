Ext.define(App.path('view.ViewInvoiceRevenueReport'), {
	extend : 'Ext.form.Panel',
	itemId : 'ViewInvoiceRevenueReport',
	bodyStyle : 'background-color: transparent',
	border : false,
	autoScroll : true,
	title : 'Báo cáo doanh thu theo hóa đơn',
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
							defaultType : 'textfield',
							items : [{
										flex : 1,
										margin : '15 15 5 50',
										xtype : 'combobox',
										itemId : 'invoice_agency',
										store : App
												.path('store.InvoiceAgency'),
										displayField : 'seller',
										valueField : 'invoice_agency_id',
										fieldLabel : 'Tên đơn vị<span style="color: red;">(*)</span>',
										listeners : {
											change : function(combobox, newValue, oldValue, eOpts) {
												var newRecord = combobox.findRecordByValue(newValue);
												if (newRecord) {
													var form = this.up('form');
													form.LoadInvoice(newRecord.data);
												}
											}
										}
									}, {
										flex : 1,
										margin : '15 50 5 5',
										itemId : 'seller',
										fieldLabel : 'Tên người bán',
										readOnly : true,
										fieldStyle : 'background-color: #ddd; background-image: none;'
										} ]
						}, {
							border : false,
							layout : 'hbox',
							defaultType : 'textfield',
							items : [{
										flex : 1,
										margin : '5 15 5 50',
										itemId : 'address',
										fieldLabel : 'Địa chỉ',
										readOnly : true,
										fieldStyle : 'background-color: #ddd; background-image: none;'
									}, {
										flex : 1,
										margin : '5 50 5 5',
										itemId : 'tin',
										fieldLabel : 'Mã số thuế',
										readOnly : true,
										fieldStyle : 'background-color: #ddd; background-image: none;'
										}]
						}, {
							border : false,
							layout : 'hbox',
							defaultType : 'textfield',
								items : [{
										flex : 1,
										margin : '5 15 5 50',
										itemId : 'tel',
										fieldLabel : 'Điện thoại',
										readOnly : true,
										fieldStyle : 'background-color: #ddd; background-image: none;'
									}, {
										flex : 1,
										margin : '5 50 5 5',
										itemId : 'fax',
										fieldLabel : 'Fax',
										readOnly : true,
										fieldStyle : 'background-color: #ddd; background-image: none;'
									}]
						}, {
							border : false,
							layout : 'hbox',
							items : [{
										flex : 1,
										margin : '5 15 5 50',
										itemId : 'in_month',
										xtype : 'datefield',
										format : 'm/Y',
										fieldLabel : 'Tháng<span style="color: red;">(*)</span>',
										value : new Date()
					    	        }, {
										xtype : 'label',
										margin : '5 50 5 5',
										flex : 1
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
								cls : 'search_all',
								text : 'Xuất báo cáo',
								handler : function() {
									var me = this.up('form');
									me.GetReport();
								}
							} ]
						}]
		}]
	},
	activate : function() {
		this.getForm().reset();
	},
	LoadInvoice: function(data){
		this.down('#seller').setValue(data.seller);
		this.down('#tin').setValue(data.tin);
		this.down('#address').setValue(data.address);
		this.down('#tel').setValue(data.tel);
		this.down('#fax').setValue(data.fax);
	},
	GetReport : function () {
		
		var me = this;
		
		if(me.IsValidForm()){
			
			var invoice_agency_id = me.down('#invoice_agency').getValue();
			var in_month = App.ActionMe.ShortDateToMysqlDB(me.down('#in_month').getValue());
			
			Ext.MessageBox.show({
			    title:'Thông báo',
			    msg:'Xác nhận gửi lệnh lấy báo cáo ?',
			    buttonText: {ok: 'Xác nhận', cancel: "Hủy"},
			    fn: function(btn){
			    	if(btn == 'ok') {
			    		/*ACTION WHEN OK*/
			    		Ext.get(document.body).mask('Chờ giây lát..');
						var me = this;
						
						var msg1 = 'Gửi lệnh lấy báo cáo ';
						var msg2 = ', kết quả sẽ được gửi qua email.';
						
				App.Action.InvoiceRevenueReport(App.Session.userName, invoice_agency_id, in_month, function(options, success, response) {
							if (success) {
								response = Ext.decode(response.responseText);
								if (response.success) {
									Ext.MessageBox.alert('Thông báo',msg1 + 'thành công' + msg2);
								} else {
									Ext.MessageBox.alert('Thông báo',msg1 + 'thất bại, ' + response.info);
								}
							} else {
								Ext.MessageBox.alert('Thông báo', msg1 + 'thất bại, ' + response.info);
							}
							Ext.get(document.body).unmask();
						});
			    	}/*END ACTION WHEN OK*/
			    }
			});
		}
	},
	IsValidForm : function() {
//		if(this.down('#invoice_agency').getValue() == null) {
//			Ext.MessageBox.alert('Thông báo', 'Chọn chi nhánh.');
//			return false;
//		}
//		if(this.down('#symbol_of_invoice').getValue().trim() == '') {
//			Ext.MessageBox.alert('Thông báo', 'Nhập Mẫu số');
//			return false;
//		}
//		if(this.down('#serial').getValue().trim() == ''){
//			Ext.MessageBox.alert('Thông báo', 'Nhập Ký hiệu');
//			return false;
//		}
		return true;
	},
	ValidFomartMsisdn : function(msisdn){
		var patt = new RegExp("(099|0199|8499|84199)[0-9]{7}");
		var res = patt.test(msisdn);
		return res;
	}
});
