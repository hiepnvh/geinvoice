Ext.define(App.path('view.ViewDataLoad'), {
	extend : 'Ext.form.Panel',
	itemId : 'viewdataload',
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
						store : Ext.create('Ext.data.Store', {
							fields : [ 'id', 'name' ],
							data : [ {
								id : 0,
								name : 'Chưa lập'
							}, {
								id : 1,
								name : 'Ðã lập'
							} ]
						}),
						displayField : 'name', // require
						valueField : 'id',
						listeners : {
							render : function(field) {
								field.setValue(0);
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
		var serial = this.down('#serial').getValue();
		//		console.log(used);
		var view_center = Ext.getCmp('CenterView');
		view_center.activateViewItem('ViewDataLoadDetail', function() {
			var viewItem = Ext.create(App.path('view.ViewDataLoadDetail'));
			return viewItem;
		}).search(msisdn, used, serial);
	}
});