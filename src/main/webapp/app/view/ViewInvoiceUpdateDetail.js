Ext.define(App.path('view.ViewInvoiceUpdateDetail'), {
	extend : 'Ext.form.Panel',
	border : false,
	itemId : 'ViewInvoiceUpdateDetail',
	xtype : 'ViewInvoiceUpdateDetail',
	id : 'ViewInvoiceUpdateDetail',
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
							margin : '5 5 5 5',
							xtype : 'combobox',
							itemId : 'serial',
							name : 'serial',
							store : App.path('store.Serial'),
							displayField : 'serial',
							valueField : 'serial',
							labelWidth : 100,
							fieldLabel : 'Chọn kí hiệu <span style="color: red;">(*)</span>',
							listeners: {
								change: function( combobox, newValue, oldValue, eOpts ){
									var me = this.up('form');
									me.down('#updatebtn').setDisabled(true);
						  	    }
						  	  }
						
		    	        }, {
							flex : 1,
							margin : '5 5 5 5',
							xtype : 'combobox',
							itemId : 'invoice_detail_status',
							store : App.path('store.InvoiceDetailStatus'),
							displayField : 'description_short',
							valueField : 'status',
							fieldLabel : 'Trạng thái<span style="color: red;">(*)</span>',
							listeners: {
								render : function(field) {
									field.setValue(0);
								},
								change: function( combobox, newValue, oldValue, eOpts ){
									var me = this.up('form');
									me.down('#updatebtn').setDisabled(true);
						  	    }
							}
						} ]
			}, {
				border : false,
				layout : 'hbox',
				defaultType : 'textfield',
				items : [{
							flex : 1,
							margin : '5 5 5 5',
							vtype: 'dollar',
							itemId : 'invoice_no_from',
							fieldLabel : 'Từ số HĐ<span style="color: red;">(*)</span>',
							minValue: 1,
							allowNegative: false,
							listeners : {
						        change : function (f, e){
						        	var me = this.up('form');
									me.down('#updatebtn').setDisabled(true);
						        }
						    }
						}, {
							flex : 1,
							margin : '5 5 5 5',
							vtype: 'dollar',
							itemId : 'invoice_no_to',
							fieldLabel : 'Đến số HĐ<span style="color: red;">(*)</span>',
							minValue: 1,
							allowNegative: false,
							listeners : {
						        change : function (f, e){
						        	var me = this.up('form');
									me.down('#updatebtn').setDisabled(true);
						        }
						    }
						}]
			}, {
				border : false,
				layout : 'hbox',
				defaultType : 'textfield',
				items : [ {
					xtype : 'tbspacer',
					flex : 1
				}, {
					margin : '5 5 5 5',
					xtype : 'button',
					cls : 'search_all',
					text : 'Tìm kiếm',
					handler : function() {
						var me = this.up('form');
						me.Search(0);
					}
				}]
			}, {
				itemId : 'invoiceDetail',
				flex : 1,
				height : '100%',
				xtype : 'grid',
				border : true,
				stripeRows : true,
				columnLines : true,
				store :  App.path('store.InvoiceDetail'),
				columns : [ 
				            {text : 'STT', xtype : 'rownumberer', width : 30},
				            {header : 'Mã HĐ', flex : 1, resizable: false, dataIndex : 'invoice_detail_id', editor : {}},
				            {header : 'Mẫu số', flex : 1, resizable: false, dataIndex : 'symbol_of_invoice', editor : {}},
							{header : 'Ký hiệu', flex : 1, resizable: false, dataIndex : 'serial', editor : {}},
				            {header : 'Số HĐ', flex : 1, resizable: false, dataIndex : 'invoice_no', editor : {}},
							{header : 'Trạng thái', flex : 2, resizable: false, dataIndex : 'status', editor : {}},
							{header : 'Mã lô HĐ', flex : 1, resizable: false, dataIndex : 'invoice_batch_id', editor : {}},
							{header : 'Mã dữ liệu KH', flex : 1, resizable: false, dataIndex : 'data_load_id', editor : {}}
							],
							bbar: Ext.create('Ext.PagingToolbar', {
								store :  App.path('store.InvoiceDetail'),
				                pageSize: App.Constant.myPageSize,
				                displayInfo: true,
				                displayMsg:'Hiển thị kết quả {0} - {1} của {2} kết quả',
				                emptyMsg:"Không có dữ liệu để hiển thị&nbsp;"
							}),viewConfig: {
							      enableTextSelection: true
							   }
				}, {
					border : false,
					layout : 'hbox',
					defaultType : 'textfield',
					height : 50,
					items : [{
								width : 300,
								margin : '5 5 5 5',
								labelWidth : 200,
								xtype : 'combobox',
								itemId : 'invoice_detail_status_target',
								forceSelection : true,
								fieldLabel : '<b>Trạng thái muốn chuyển đến</b><span style="color: red;">(*)</span>',
								store : Ext.create('Ext.data.Store', {
									fields : [ 'id', 'value' ],
									data : [ {
										id : 11,
										value : 'Xóa'
									}, {
										id : 13,
										value : 'Hủy'
									} ]
								}),
								displayField : 'value', // require
								valueField : 'id',
								listeners : {
									render : function(field) {
										field.setValue(11);
									}
								}
							}, {
								xtype : 'label',
								flex : 1
							}, {
								itemId : 'updatebtn',
								margin : '5 5 5 5',
								xtype : 'button',
								cls : 'search_all',
								text : 'Cập nhật',
								handler : function() {
									var me = this.up('form');
									me.Update();
								}
							} ]
				}]
		}]
	},
	activate : function() {
		var store = this.down('#invoiceDetail').getStore();
		store.removeAll();
		this.down('#updatebtn').setDisabled(true);
	},
	Search: function(type){
		var me = this;
		
		var serial = this.down('#serial').getValue();
		var invoice_detail_status = (type == 0 ? this.down('#invoice_detail_status').getValue() : this.down('#invoice_detail_status_target').getValue());
		var invoice_no_from = this.down('#invoice_no_from').getValue().toString().replace(/[^0-9]/g,'');
		var invoice_no_to = this.down('#invoice_no_to').getValue().toString().replace(/[^0-9]/g,'');
		
		var grid = this.down('#invoiceDetail');
		var store = this.down('#invoiceDetail').getStore();
		store.removeAll();
		
		store.getProxy().extraParams.serial = serial;
		store.getProxy().extraParams.invoice_detail_status = invoice_detail_status;
		store.getProxy().extraParams.invoice_no_from = invoice_no_from;
		store.getProxy().extraParams.invoice_no_to = invoice_no_to;
		
		store.currentPage = 1;
		store.load({
			params:{
		        start: 0,
		        limit: 25
		    }, 	
			callback: function(records, operation, success) {
				Ext.get(document.body).unmask();
		        if (success) {
		        	if(records.length == 0)
		        		Ext.MessageBox.alert('Thông báo','Không có dữ liệu để hiển thị');
		        	else
		        		me.down('#updatebtn').setDisabled(false);
		        } else {
		        	Ext.MessageBox.alert('Thông báo','Xảy ra lỗi');
		        }
		    }
		});
	},
	Update : function () {
		var me = this;
		
		if(me.IsValidForm()){
			
			var serial = me.down('#serial').getValue();
			var old_status = me.down('#invoice_detail_status').getValue();
			var status = me.down('#invoice_detail_status_target').getValue();
			var invoice_no_from = me.down('#invoice_no_from').getValue().toString().replace(/[^0-9]/g,'');
				invoice_no_from = (invoice_no_from == "" ? null : invoice_no_from);
			var invoice_no_to = me.down('#invoice_no_to').getValue().toString().replace(/[^0-9]/g,'');
			invoice_no_to = (invoice_no_to == "" ? null : invoice_no_to);
			
			var params = Ext.encode({
				serial : serial,
				old_status : old_status,
				status : status,
				invoice_no_from : invoice_no_from,
				invoice_no_to : invoice_no_to
			});
			
			Ext.MessageBox.show({
			    title:'Thông báo',
			    msg:'Xác nhận thực hiện ?',
			    buttonText: {ok: 'Xác nhận', cancel: "Hủy"},
			    fn: function(btn){
			    	if(btn == 'ok') {
			    		/*ACTION WHEN OK*/
			    		Ext.get(document.body).mask('Chờ giây lát..');
				
				var msg1 = 'Cập nhật ';
				App.Action.InvoiceUpdate(App.Session.userName, params,
						function(options, success, response) {
							if (success) {
								response = Ext.decode(response.responseText);
								if (response.success) {
									Ext.MessageBox.alert('Thông báo',msg1 + 'thành công.');
									me.Search(1);
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
		if(this.down('#serial').getValue() == "") {
			Ext.MessageBox.alert('Thông báo', 'Điền "Ký hiệu".');
			return false;
		}
		if(this.down('#invoice_detail_status').getValue() == null) {
			Ext.MessageBox.alert('Thông báo', 'Chọn trạng thái muốn cập nhật.');
			return false;
		}
		if(this.down('#invoice_no_from').getValue() == "" && this.down('#invoice_no_to').getValue() == "")
			if(this.down('#invoice_no_from').getValue() > this.down('#invoice_no_to').getValue()) {
				Ext.MessageBox.alert('Thông báo', '"Từ số HĐ" phải nhỏ hơn hoặc bằng "Đến số HĐ".');
				return false;
			}
		if(this.down('#invoice_detail_status').getValue() == this.down('#invoice_detail_status_target').getValue()) {
			Ext.MessageBox.alert('Thông báo', 'Không cập nhật khi có cùng trạng thái.');
			return false;
		}
		if(this.down('#invoice_detail_status_target').getValue() == 13 && this.down('#invoice_detail_status').getValue() != 0) {
			Ext.MessageBox.alert('Thông báo', 'Chỉ có thể <b>Hủy</b> Hóa đơn chưa lập.');
			return false;
		}
		if(this.down('#invoice_detail_status_target').getValue() == 11 && (
				this.down('#invoice_detail_status').getValue() == 0
				|| this.down('#invoice_detail_status').getValue() == 11
				|| this.down('#invoice_detail_status').getValue() == 12
				|| this.down('#invoice_detail_status').getValue() == 13
			)) {
			Ext.MessageBox.alert('Thông báo', 'Chỉ có thể <b>Xóa</b> Hóa đơn đã lập.');
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
