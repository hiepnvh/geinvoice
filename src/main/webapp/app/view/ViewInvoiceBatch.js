Ext.define(App.path('view.ViewInvoiceBatch'), {
			extend : 'Ext.grid.Panel',
			border : true,
			itemId : 'ViewInvoiceBatch',
			xtype : 'ViewInvoiceBatch',
			id : 'ViewInvoiceBatch',
			autoScroll : true,
			initComponent : function(arguments) {
				var store = App.path('store.InvoiceBatch');
				var me = this;
				Ext.apply(this, {
							border : false,
							store : store,
							stripeRows : true,
							columnLines : true,
							selModel : Ext.create('Ext.selection.RadioModel'),
							columns : [{
										text : 'STT',
										xtype : 'rownumberer',
										width : 30
									}, {
										text : 'Ký hiệu',
										flex : 1,
										sortable : true,
										dataIndex : 'serial',
										filter : {
											type : 'string'
										}
									}, {
										text : 'Số HĐ bắt đầu',
										flex : 1,
										dataIndex : 'start_no'
									}, {
										text : 'Số lượng',
										flex : 1,
										dataIndex : 'quantity'
									}],
							bbar : Ext.create('Ext.PagingToolbar', {
										store : App.path('store.InvoiceBatch'),
										pageSize : App.Constant.myPageSize,
										displayInfo : true,
										displayMsg : '{0} - {1} / {2}',
										emptyMsg : "Không có DL"
									}),
									viewConfig : {
										enableTextSelection : true
									},
							dockedItems : [{
										dock : 'bottom',
										height : 30,
										border : false,
										bodyStyle : 'background-color: transparent',
										items : [{
													xtype : 'button',
													text : 'Thêm mới',
													cls : 'search_all',
													margin : 5,
													handler : function() {
														me.InvoiceAddNew();
													}
												}]

									}, {
										dock : 'top',
										height : 'fit',
										border : false,
										xtype : 'form',
										bodyStyle : 'background-color: transparent',
										items : [{
											xtype : 'fieldset',
											padding : '5 0 0 5',
											margin : '5 5 0 5',
											border : false,
											collapsible : false,
											items : [{
														xtype : 'textfield',
														fieldLabel : 'Serial',
														itemId : 'serial',
														anchor : '98%',
														margin : '5 0 5 0'
													}, {
														xtype : 'button',
														text : 'Tìm kiếm',
														cls : 'search_all',
														margin : 5,
														handler : function() {
															me.Search();
														}
													}]
										}]
									}]
						});
				this.callParent(arguments);
			},
			activate : function() {
				this.getStore().removeAll();
				this.down('#serial').setValue('');
			},
			InvoiceAddNew : function() {
				var view_center = Ext.getCmp('CenterView');
				view_center.activateViewItem('ViewInvoiceBatchAddNew', function() {
							var viewItem = Ext.create(App
									.path('view.ViewInvoiceBatchAddNew'));
							return viewItem;
						}).activate();
			},
			Search : function() {
				if(Ext.getCmp('ViewInvoiceBatchDetail') != undefined)
					Ext.getCmp('ViewInvoiceBatchDetail').getForm().reset();
				
				var store = this.getStore();
				var serial = this.down('#serial').getValue();

//				var user_id = App.Session.user_id;
//				var username = this.down('#username').getValue();
//				var profile_id = this.down('#profile_id').getValue();

				store.setProxy({
							type : 'ajax',
							url : 'invoicebatchget',
							actionMethods : {
								create : 'POST',
								read : 'POST',
								update : 'POST',
								destroy : 'POST'
							},
							extraParams : {
								serial : serial,
								username : App.Session.userName
//								username : username,
//								profile_id : profile_id
							},
							reader : {
								type : 'json',
								root : 'result',
								totalProperty : 'totalCount'
							}
						});
				store.load({
							callback : function(records, options, success) {
								if (success) {
									if(records.length == 0) {
										Ext.MessageBox.alert('Thông báo','Không có kết quả để hiển thị.');
									}
								}
							}
						});
			}
		});
