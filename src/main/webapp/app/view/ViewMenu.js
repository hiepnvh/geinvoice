Ext.define(App.path('view.ViewMenu'), {
	extend : 'Ext.form.Panel',
	xtype : 'menu',
	itemId : 'Menu',
	bodyStyle : 'background-color: white',
	border : false,
	config : {
		items : [{
			minHeight : 70,
			layout : 'hbox',
			border : false,
			items : [{
				html : '<img src="./resources/img/gmobilelogo-vi.png" alt="logo1" class="logo_img"/>',
				height : '100%',
				width : '20%',
				layout : 'fit',
				border : false,
				listeners : {
					click : {
						element : 'el',
						fn : function() {
							Ext.Router.redirect('');
						}
					}
				}
			}, {
				border : false,
				height : '100%',
				width : '60%',
				html : '<p class="name_title_app"> Hệ thống hỗ trợ hóa đơn điện tử </p>',
				cls : 'title_app'
			}, {
				height : '100%',
				border : false,
				width : '20%',
				layout : 'fit',
				html : '<span style="position: absolute;bottom: 0;">Phát triển bởi IT R&D Gmobile ©</span>'
			}]
		}, {
			layout : 'hbox',
			bodyStyle : 'background-color: white',
			minHeight : 30,
			border : false,
			items : [{
				flex : 1,
				xtype : 'toolbar',
				bodyStyle : 'background-color: white',
				border : false,
				padding : 2,
				items : [
						{
							hidden : true,
							text : 'Quản lý hệ thống',
							itemId : 'menulist1',
							menu : [
									{
										hidden : true,
										text : 'Quản lý đơn vị',
										itemId : 'showcommondir',
										width : 200
									}]
						}, {
							text : 'Hóa đơn',
							itemId : 'menulist2',
							hidden : true,
							menu : [{
										hidden : true,
										text : 'Tạo hóa đơn mẫu',
										itemId : 'invoicetemplate',
										width : 200
									}, {
										hidden : true,
										text : 'Phát hành dải hóa đơn',
										itemId : 'invoiceadd'
									}, {
										hidden : true,
										text : 'Lập hóa đơn mới',
										itemId : 'dataloadadd'
									}, {
										hidden : true,
										text : 'Lập hóa đơn điều chỉnh',
										itemId : 'invoiceadjust'
									}, {
										hidden : true,
										text : 'Thay đổi trạng thái hóa đơn',
										itemId : 'invoiceupdate'
									}, {
										hidden : true,
										text : 'Hóa đơn chuyển đổi',
										itemId : 'invoiceconversion'
									}, {
										hidden : true,
										text : 'Xuất hóa đơn',
										itemId : 'invoiceexport'
									}]
						}, {
							text : 'Báo cáo',
							itemId : 'menulist3',
							hidden : true,
							menu : [{
										hidden : true,
										text : 'Báo cáo tình hình sử dụng hóa đơn',
										itemId : 'invoiceusagereport',
										width : 200
									}, {
										hidden : true,
										text : 'Báo cáo doanh thu theo hóa đơn',
										itemId : 'invoicerevenuereport'
									}]
						}]
			}, {
				border : false,
				bodyStyle : 'background-color: white',
				items : [{
					flex : 1,
					xtype : 'toolbar',
					bodyStyle : 'background-color: white',
					border : false,
					cls : 'account',
					items : [{
								xtype : 'label',
								html : 'Tên đăng nhập:'
							}, {
								text : App.Session.displayName,
								itemId : 'username',
								menu : [{
									text : 'Thoát',
									name : 'logout',
									itemId : 'logout',
									handler : function() {
										this.up('form').logout();
									}
								}]

							}]
				}]
			}]
		}]
	},
	logout : function() {
		document.location.href='https://accounts.gmobile.vn/sso/UI/Logout';
	},
	activate : function() {
		
		this.down('#invoicetemplate').setVisible(false);
		this.down('#invoiceadd').setVisible(false);
		this.down('#dataloadadd').setVisible(false);
		this.down('#invoiceadjust').setVisible(false);
		this.down('#invoiceupdate').setVisible(false);
		this.down('#invoiceconversion').setVisible(false);
		
		this.down('#invoiceusagereport').setVisible(false);
		this.down('#invoicerevenuereport').setVisible(false);
		this.down('#invoiceexport').setVisible(false);
		
		this.down('#menulist1').setVisible(false);
		this.down('#menulist2').setVisible(false);
		this.down('#menulist3').setVisible(false);
		
		var function_list = App.Session.functionlist;
		
		for(var i=0;i<function_list.length;i++) {
			this.FunctionMap(function_list[i].function_id);
		}
		// test 
//		this.down('#invoiceadjust').setVisible(false);
	},
	FunctionMap : function(itemId) {
		switch(itemId)
		{
			case 21 : this.ShowBoolean('invoicetemplate',true); this.ShowBoolean('menulist2',true); break;
			case 22 : this.ShowBoolean('invoiceadd',true); this.ShowBoolean('menulist2',true); break;
			case 23 : this.ShowBoolean('dataloadadd',true); this.ShowBoolean('menulist2',true); break;
			case 24 : this.ShowBoolean('invoiceadjust',true); this.ShowBoolean('menulist2',true); break;
			case 25 : this.ShowBoolean('invoiceupdate',true); this.ShowBoolean('menulist2',true); break;
			case 26 : this.ShowBoolean('invoiceconversion',true); this.ShowBoolean('menulist2',true); break;
			case 27 : this.ShowBoolean('invoiceexport',true); this.ShowBoolean('menulist2',true); break;
			
			case 31 : this.ShowBoolean('invoiceusagereport',true); this.ShowBoolean('menulist3',true); break;
			case 32 : this.ShowBoolean('invoicerevenuereport',true); this.ShowBoolean('menulist3',true); break;
		}
	},
	ShowBoolean : function(itemId, show) {
		var item = '#' + itemId;
		if (show) {
			this.down(item).setVisible(true);
		} else {
			this.down(item).setVisible(false);
		}
	}
});
