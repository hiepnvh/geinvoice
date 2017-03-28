Ext.define(App.path('view.ViewInvoiceBatchAddNew'), {
	extend : 'Ext.form.Panel',
	itemId : 'ViewInvoiceBatchAddNew',
	id : 'ViewInvoiceBatchAddNew',
	bodyStyle : 'background-color: transparent',
	border : false,
	layout : 'fit',
	title : 'Phát hành dải hóa đơn',
	config : {
		record : null,
		fieldDefaults : {
			margin : '5'
		},
		items : [{
			region : 'center',
			border : false,
			height : 500,
			layout : {
				type : 'hbox',
				align : 'stretch',
				pack : 'start'
			},
			items : [{
				flex : 0.6,
				items : [{
					border : false,
					items : [{
						border : false,
						layout : 'hbox',
						items : [{
									flex : 1,
									margin : '5 15 5 5',
									xtype : 'combobox',
									itemId : 'invoice_agency',
									store : App.path('store.InvoiceAgency'),
									displayField : 'seller',
									valueField : 'invoice_agency_id',
									fieldLabel : 'Tên đơn vị<span style="color: red;">(*)</span>',
									listeners: {
										change: function( combobox, newValue, oldValue, eOpts ){
//								  			var value = combobox.getValue();
//								  		    record = combobox.findRecordByValue(value),
//								  		    index = combobox.getStore().indexOf(record);
								  			
								  			var newRecord = combobox.findRecordByValue(newValue);
								  		   if (newRecord) {
								  			 var form = this.up('form');
									  			form.LoadInvoice(newRecord.data);
								  		   }
								  	    }
								  	  }
								}, {
									xtype : 'label',
									flex : 1
								}]
					}, {
						border : false,
						layout : 'hbox',
						defaultType : 'textfield',
						items : [{
									flex : 1,
									itemId : 'seller',
									fieldLabel : 'Tên Người bán',
									readOnly : true,
									fieldStyle : 'background-color: #ddd; background-image: none;'
								}, {
									flex : 1,
									itemId : 'symbol_of_invoice',
									fieldLabel : 'Mẫu số<span style="color: red;">(*)</span>',
									allowBlank : false,
									blankText : 'Không cho phép để trống'
								}]
					}, {
						border : false,
						layout : 'hbox',
						defaultType : 'textfield',
						items : [{
									flex : 1,
									itemId : 'tin',
									fieldLabel : 'Mã số thuế',
									readOnly : true,
									fieldStyle : 'background-color: #ddd; background-image: none;'
								}, {
									flex : 1,
									itemId : 'serial',
									fieldLabel : 'Kí hiệu<span style="color: red;">(*)</span>',
									allowBlank : false,
									blankText : 'Không cho phép để trống'
								}]
					}, {
						border : false,
						layout : 'hbox',
						defaultType : 'textfield',
						items : [{
									flex : 1,
									margin : '5 15 5 5',
									itemId : 'address',
									fieldLabel : 'Địa chỉ',
									readOnly : true,
									fieldStyle : 'background-color: #ddd; background-image: none;'
								}, {
									xtype : 'label',
									flex : 1
								}]
					}, {
						border : false,
						layout : 'hbox',
						defaultType : 'textfield',
						items : [{
									flex : 1,
									itemId : 'tel',
									fieldLabel : 'Điện thoại',
									readOnly : true,
									fieldStyle : 'background-color: #ddd; background-image: none;'
								}, {
									flex : 1,
									itemId : 'fax',
									fieldLabel : 'Fax',
									readOnly : true,
									fieldStyle : 'background-color: #ddd; background-image: none;'
								}]
					}, {
						border : false,
						layout : 'hbox',
						defaultType : 'textfield',
						items : [{
									flex : 1,
//									xtype: 'textfield',
									vtype: 'dollar',
									itemId : 'quantity',
									fieldLabel : 'Số lượng HĐ<span style="color: red;">(*)</span>',
									allowBlank : false,
									blankText : 'Không cho phép để trống',
									minValue: 1,
									maxValue: 300000,
									allowNegative: false //,
//									enableKeyEvents: true,
//									listeners:{
//										keypress: function(thisField, e) {
//							                   thisField.setValue(Ext.util.Format.number(thisField.value.replace(/[\,]/g, ''), '0,000,000/i'));
//							               }
//						            }
								}, {
									flex : 1,
							        xtype: 'datefield',
							        itemId : 'invoice_created_date',
							        fieldLabel: 'Ngày BĐSD<span style="color: red;">(*)</span>',
							        minValue: Ext.Date.add (new Date(),Ext.Date.DAY,5),  // limited to the current date or prior
							        format: 'd/m/Y',
							        allowBlank : false,
									blankText : 'Không cho phép để trống'
							    }]
					}, {
						border : false,
						layout : 'hbox',
						items : [{
									xtype : 'tbfill'
								}, {
									width : 100,
									border : false,
									buttons : [{
												text : 'Phát hành HĐ',
												handler : function() {
													var form = this.up('form');
													form.Update();
												}
											}]
								}]
					}]
				}]
			}]
		}]
	},
	activate : function() {
		console.log('view invoice add new');
		this.getForm().reset();
	},
	LoadInvoice: function(data){
		this.down('#seller').setValue(data.seller);
		this.down('#tin').setValue(data.tin);
		this.down('#address').setValue(data.address);
		this.down('#tel').setValue(data.tel);
		this.down('#fax').setValue(data.fax);
	},
	Update : function() {
		var me = this;
		if(me.IsValidForm()){
			Ext.MessageBox.show({
			    title:'Thông báo',
			    msg:'Xác nhận thực hiện ?',
			    buttonText: {ok: 'Xác nhận', cancel: "Hủy"},
			    fn: function(btn){
			    	if(btn == 'ok'){
			    		/*CREATE INVOICE*/
			    		if(me.isValid()){
			    			Ext.get(document.body).mask('Chờ giây lát..');
			    			
			    			var invoice_batch = Ext.encode({
			    						invoice_agency_id : me.down('#invoice_agency').getValue().toString(),
			    						quantity : me.down('#quantity').getValue().toString().replace(/[^0-9]/g,''),
			    						symbol_of_invoice : me.down('#symbol_of_invoice').getValue().toString().trim().toUpperCase(),
			    						serial : me.down('#serial').getValue().toString().trim().toUpperCase(),
			    						invoice_created_date : App.ActionMe.ShortDateToMysqlDB(me.down('#invoice_created_date').getValue())
			    					});
			    			
			    			var text = 'Phát hành hóa đơn';
			    			App.Action.InvoiceBatchUpdate(App.Session.userName, invoice_batch,
			    					function(options, success, response) {
			    						Ext.get(document.body).unmask();
			    						if (success) {
			    							response = Ext.decode(response.responseText);
			    							if (response.success) {
			    								Ext.MessageBox.alert('Thông báo', text +' thành công');
			    								me.getForm().reset();
			    							} else {
			    								Ext.MessageBox.alert('Thông báo',text +' thất bại ' + response.info);
			    							}
			    						} else {
			    							Ext.MessageBox.alert('Thông báo', text +' thất bại ' + response.info);
			    						}
			    					});
			    			}/*END CREATE INVOICE*/
			    	}
			    }
			});
		}
	},
	IsValidForm : function() {
		if (this.down('#invoice_agency').getValue() == null) {
			Ext.MessageBox.alert('Thông báo', 'Chọn mục "Tên đơn vị".');
			return false;
		}
		if (this.down('#symbol_of_invoice').getValue() == "") {
			Ext.MessageBox.alert('Thông báo', 'Điền mục "Mẫu số".');
			return false;
		}
		if (this.down('#serial').getValue() == "") {
			Ext.MessageBox.alert('Thông báo', 'Điền mục "Kí hiệu".');
			return false;
		}
		if (this.down('#quantity').getValue() == "") {
			Ext.MessageBox.alert('Thông báo', 'Điền mục "Số lượng HĐ".');
			return false;
		}
		if (this.down('#invoice_created_date').getValue() == null) {
			Ext.MessageBox.alert('Thông báo', 'Điền mục "Ngày BĐSD".');
			return false;
		}
	return true;
	}
});


