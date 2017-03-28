Ext.define(App.path('view.ViewInvoiceBatchDetail'), {
	extend : 'Ext.form.Panel',
	itemId : 'ViewInvoiceBatchDetail',
	id : 'ViewInvoiceBatchDetail',
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
						defaultType : 'textfield',
						items : [{
									flex : 1,
									margin : '5 15 5 5',
									xtype : 'combobox',
									itemId : 'invoice_agency',
									store : App.path('store.InvoiceAgency'),
									displayField : 'seller',
									valueField : 'invoice_agency_id',
									fieldLabel : 'Tên đơn vị<span style="color: red;">(*)</span>',
									readOnly : true,
									fieldStyle : 'background-color: #ddd; background-image: none;',
									listeners: {
								  		change: function(combobox, newValue, oldValue, eOpts ) {
//								  			var value = combobox.getValue();
//								  		    record = combobox.findRecordByValue(value),
//								  		    index = combobox.getStore().indexOf(record);
								  			
								  			var newRecord = combobox.findRecord(newValue);
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
									blankText : 'Không cho phép để trống',
									readOnly : true,
									fieldStyle : 'background-color: #ddd; background-image: none;'
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
									blankText : 'Không cho phép để trống',
									readOnly : true,
									fieldStyle : 'background-color: #ddd; background-image: none;'
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
									minValue: 0,
									maxValue: 30000,
									allowNegative: false,
									readOnly : true,
									fieldStyle : 'background-color: #ddd; background-image: none;' //,
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
							        fieldLabel: 'Ngày lập HĐ<span style="color: red;">(*)</span>',
//							        minValue: Ext.Date.add (new Date(),Ext.Date.DAY,5),  // limited to the current date or prior
							        format: 'd/m/Y',
							        allowBlank : false,
									blankText : 'Không cho phép để trống',
									readOnly : true,
									fieldStyle : 'background-color: #ddd; background-image: none;'
							    }]
					}]
				}]
			}]
		}]
	},
	activate : function() {
		var me = this;
		var data = me.record.data;
		
        this.down('#invoice_agency').setValue(data.invoice_agency_id);
		this.down('#quantity').setValue(data.quantity);
		this.down('#symbol_of_invoice').setValue(data.symbol_of_invoice);
		this.down('#serial').setValue(data.serial);
		this.down('#invoice_created_date').setValue(App.ActionMe.MysqlDBToShortDate(data.invoice_created_date));
	},
	LoadInvoice: function(data){
		this.down('#seller').setValue(data.seller);
		this.down('#tin').setValue(data.tin);
		this.down('#address').setValue(data.address);
		this.down('#tel').setValue(data.tel);
		this.down('#fax').setValue(data.fax);
	}
});


