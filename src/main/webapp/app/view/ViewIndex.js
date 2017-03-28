Ext.Loader.setConfig({enabled: true});
Ext.Loader.setPath('Ext.ux', 'lib/ux');
Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.ux.grid.FiltersFeature',
    'Ext.toolbar.Paging',
    'Ext.ux.ajax.JsonSimlet',
    'Ext.ux.ajax.SimManager'
]);

Ext.define(App.path('view.ViewIndex'), {
    extend: 'Ext.container.Container',
    xtype: 'viewindex',
    id:'ViewIndex',
    itemId: 'viewindex',
    layout: 'border',
    requires: [
    	App.path('view.ViewMenu')
	],
    items: [{
            xtype: 'menu',
            itemId: 'menu',
            region: 'north',
            border: true,
            height: 105,
            bodyStyle: 'background-color: transparent'

        }, {
            region: 'west',
            layout: 'fit',
            collapsible: true,
            width: 300,
            id: 'west',
            border: true,
            bodyStyle: 'background-color: transparent',
            items: {
                xtype: 'amxcontainer',
                id: 'WestView',
                layout: 'card'
            }
        }, {
            region: 'center',
            border: false,
            //margin:2,
            layout: 'fit',
            bodyStyle: 'background-color: transparent',
            items: {
                xtype: 'amxcontainer',
                id: 'CenterView',
                layout: 'card'
            }
        }],
    reset: function() {
//    	console.log("App.Session.userName:"+App.Session.userName);
    	var me = this;
		var noParam = "1";
		App.Action.UserLoginInfoGet(noParam, function(options, success, response) {
			Ext.get(document.body).unmask();
			if (success) {
				response = Ext.decode(response.responseText);
				if (response.success) {
					
					App.Session.setUserName(response.user_login_info.userName);
					App.Session.setDisplayName(response.user_login_info.displayName);
					App.Session.setMobile(response.user_login_info.mobile);
					App.Session.setMail(response.user_login_info.mail);
					
					me.down('#username').setText(response.user_login_info.displayName);
					
//					console.log("App.Session.userName after refesh:"+App.Session.userName);
					
					var text = 'Lấy dữ liệu';
			    	App.Action.UserMenuFunctionGet(App.Session.userName, function(options, success, response) {
								Ext.get(document.body).unmask();
								if (success) {
									response = Ext.decode(response.responseText);
//									console.log(response);
									if (response.success) {
										 	App.Session.setFunctionlist(response.function_list);
										 	me.down('#menu').activate();
									} else {
										Ext.MessageBox.alert('Thông báo',text +' thất bại ' + response.info);
									}
								} else {
									Ext.MessageBox.alert('Thông báo', text +' thất bại, Đường truyền lỗi');
								}
							});
				} else {
					Ext.MessageBox.alert('Thông báo',
							'Truy cập vào hệ thống thất bại ' + response.info);
				}
			} else {
				Ext.MessageBox.alert('Thông báo', 'Lỗi đường truyền, vui lòng thử lại sau ' + response.info);
			}
		});
    },
    showWhat: function(itemIdWest, WestView, itemIdCenter, CenterView) {
        this.reset();
        
        this.down('#WestView').activateViewItem(itemIdWest, function() {
            return Ext.create(WestView);
        }, this).activate();

        this.down('#CenterView').activateViewItem(itemIdCenter, function() {
            return Ext.create(CenterView);
        }, this).activate();
    },
    showWhats: function(itemIdWest, WestView, itemIdCenter, CenterView,store,itemDetail,DetailView) {
        this.reset() ;
        var me=this;
        this.down('#WestView').activateViewItem(itemIdWest, function() {   
            var view =  Ext.create(WestView,{
                store:store
            });
            view.on('select',function(m, record){
                me.down('#CenterView').activateViewItem(itemDetail, function() {
                    return Ext.create(DetailView,{ record:record});
                }, this,function(viewItem){
                    viewItem.record=record;
                }).activate();
            });
            return view;
        }, this).activate();

        this.down('#CenterView').activateViewItem(itemIdCenter, function() {
            return Ext.create(CenterView);
        }, this).activate();
    },
   	showWhatsTree: function(itemIdWest, WestView, itemIdCenter, CenterView,store,itemDetail,DetailView) {
   		App.Session.itemReport = itemDetail;
   		App.Session.itemReportDetail = DetailView;
        this.reset() ;
        var me=this;
        this.down('#WestView').activateViewItem(itemIdWest, function() {   
            var view =  Ext.create(WestView,{
                store:store
            });
            view.on('select',function(m, record){
                me.down('#CenterView').activateViewItem(App.Session.itemReport, function() {
                    return Ext.create(App.Session.itemReportDetail,{ record:record});
                }, this,function(viewItem){
                    viewItem.record=record;
                }).activate();
            });
            return view;
        }, this).activate();

        this.down('#CenterView').activateViewItem(itemIdCenter, function() {
            return Ext.create(CenterView);
        }, this).activate();
    },
	   showTree : function(itemIdWest, WestView, itemIdCenter, CenterView, store,
			itemDetail, DetailView) {
		this.reset();
		var me = this;
		var store1 = Ext.getStore(store);
		store1.setProxy({
					type : 'ajax',
					url : 'getagenttree',
					actionMethods : {
						create : 'POST',
						read : 'POST',
						update : 'POST',
						destroy : 'POST'
					},
					extraParams : {
						user_id : App.Session.user_id
					},
					reader : {
						type : 'json',
						root : 'agent_list',
						totalProperty : 'totalCount'
					}
				});
		store1.load();
		this.down('#WestView').activateViewItem(itemIdWest, function() {
			var view = Ext.create(WestView, {
						store : store1
					});
			view.on('select', function(m, record) {
						me.down('#CenterView').activateViewItem(itemDetail,
								function() {
									return Ext.create(DetailView, {
												record : record
											});
								}, this, function(viewItem) {
									viewItem.record = record;
								}).activate();
					});
			return view;
		}, this, function(viewItem) {
			/** *** */
			// console.log('active lan 2');
				/** ****** */
			}).activate();

		this.down('#CenterView').activateViewItem(itemIdCenter, function() {
					return Ext.create(CenterView);
				}, this).activate();
	},
//   activate: function() {
//	   console.log("view index active");
//   },
   DisableAll : function ()
   {
   	// console.log('DisableAll');
   	var mask = Ext.LoadMask(Ext.getCmp('ViewIndex'), {msg:'Chờ giây lát..'});
   		mask.show();
   },
   EnableAll : function ()
   {
   	Ext.LoadMask(Ext.getCmp('ViewIndex'), {msg:'Chờ giây lát..'}).hide();
   }
});