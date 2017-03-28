Ext.define(App.path('view.ViewInvoiceAdjust'), {
	extend : 'Ext.form.Panel',
	itemId : 'ViewInvoiceAdjust',
	bodyStyle : 'background-color: transparent',
	border : false,
	layout : 'fit',
	config : {
		record : null,
		fieldDefaults : {
			margin : '5'
		},
		items : [ {
			region : 'center',
			border : false,
			items : [ {
				border : false,
				items : [ {
					border : false,
					layout : 'hbox',
					defaultType : 'textfield',
					items : [ {
						flex : 1,
						margin : '5 5 5 5',
						itemId : 'serial',
						fieldLabel : 'Kí hiệu'
					} ]
				}, {
					border : false,
					layout : 'hbox',
					defaultType : 'textfield',
					items : [ {
						flex : 1,
						itemId : 'msisdn',
						fieldLabel : 'Số điện thoại'
					} ]
				}, {
					border : false,
					layout : 'hbox',
					items : [ {
						flex : 1,
						xtype : 'combobox',
						itemId : 'used',
						fieldLabel : 'Trạng thái',
						forceSelection : true,
						store : Ext.create('Ext.data.Store', {
							fields : [ 'id', 'value' ],
							data : [ {
								id : 0,
								value : 'Chưa lập'
							}, {
								id : 1,
								value : 'Ðã lập'
							} ]
						}),
						displayField : 'value', // require
						valueField : 'id',
						listeners : {
							render : function(field) {
								field.setValue(0);
							}
						}
					} ]
				}, {
					border : false,
					layout : 'hbox',
					items : [ {
						flex : 1,
						xtype : 'combobox',
						itemId : 'adjust_type',
						fieldLabel : 'Loại điều chỉnh',
						forceSelection : true,
						store : Ext.create('Ext.data.Store', {
							fields : [ 'id', 'value' ],
							data : [ {
								id : -1,
								value : 'Giảm'
							}, {
								id : 1,
								value : 'Tăng'
							} ]
						}),
						displayField : 'value', // require
						valueField : 'id',
						listeners : {
							render : function(field) {
								field.setValue(-1);
							}
						}
					} ]
				} ]
			} ]
		} ],
		buttons : [ {
			text : 'Tìm kiếm',
			handler : function() {
				var form = this.up('form');
				form.Search();
			}
		} ]
	},
	activate : function() {
		var me = this;
	},
	Search : function() {
		var me = this;
		var msisdn = this.down('#msisdn').getValue();
		var used = this.down('#used').getValue();
		var adjust_type = this.down('#adjust_type').getValue();
		var serial = this.down('#serial').getValue();
		
		var params = [];
		params.push(msisdn);
		params.push(used);
		params.push(adjust_type);
		params.push(serial);
		
		var view_center = Ext.getCmp('CenterView');
		view_center.activateViewItem('ViewInvoiceAdjustDetail', function() {
			var viewItem = Ext.create(App.path('view.ViewInvoiceAdjustDetail'));
			return viewItem;
		}).search(params);
	}
});