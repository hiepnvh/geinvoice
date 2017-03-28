Ext.define(App.path('view.ViewIndex100'), {
    extend: 'Ext.container.Container',
    itemId: 'viewindex100',
    id: 'viewindex100',
    layout: 'border',
    requires: App.path('view.ViewMenu'),
    items: [{
            xtype: 'menu',
            itemId: 'menu',
            region: 'north',
            border: true,
            height: 105,
            bodyStyle: 'background-color: transparent'

        }, {
            region: 'center',
            border: false,
            //margin:2,
            layout: 'fit',
            bodyStyle: 'background-color: transparent',
            items: {
                xtype: 'amxcontainer',
                itemId: 'CenterView',
                layout: 'card'
            }
        }],
    reset: function() {
//    	console.log("App.Session.userName:"+App.Session.userName);
//    	console.log("App.Session.functionlist:"+App.Session.functionlist);
    	var me = this;
		var noParam = "1";
		Ext.get(document.body).mask('Chờ giây lát..');
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
								if (success) {
									response = Ext.decode(response.responseText);
									if (response.success) {
										 	App.Session.setFunctionlist(response.function_list);
//										 	console.log("App.Session.functionlist after refesh:"+App.Session.functionlist);
										 	me.down('#menu').activate();
									} else {
										Ext.MessageBox.alert('Thông báo',text +' thất bại ' + response.info);
									}
								} else {
									Ext.MessageBox.alert('Thông báo', 'Lỗi đường truyền, vui lòng thử lại sau ' + response.info);
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
    showWhat: function(itemIdCenter, CenterView) {
        this.reset();
        this.down('#CenterView').activateViewItem(itemIdCenter, function() {
            return Ext.create(CenterView);
        }, this).activate();
    },
    activate: function() {
//    	this.down('#menu').activate();
    },
//    LoadDistributionStaff : function()
//    {
//    	var fields = [];
//    	fields.push('agent_id');
//    	fields.push('agent_type');
//    	fields.push('agent_name');
//    	fields.push('agent_code');
//    	
//    	var store = Ext.getStore(App.path('store.ParentDistributorStaff'));
//    	store.getProxy().extraParams.user_id = App.Session.user_id;
//    	store.getProxy().extraParams.min_agent_type = App.Constant.AGENT_TYPE_DISTRIBUTOR_STAFF;
//    	store.getProxy().extraParams.max_agent_type = App.Constant.AGENT_TYPE_DISTRIBUTOR_STAFF;
//    	store.getProxy().extraParams.fields = Ext.encode(fields);
//		store.getProxy().extraParams.limit = null;
//        store.load();
//    },
    
//    MapInventory : function()
//    {
//    	/*===============Begin Load All Agent and Set in A Map=================*/
//        var stoAllInventory = Ext.getStore(App.path('store.InventoryList'));
//        	stoAllInventory.getProxy().extraParams.user_id = App.Session.user_id;
//        	stoAllInventory.getProxy().extraParams.limit = null;
//        	stoAllInventory.load({
//        		scope : this,
//        		callback : function(records, ops, success)
//        		{
//        			if(success)
//        			{
//        				var mapInventory = new Ext.util.HashMap();
//        				for(var i=0;i<records.length;i++)
//        				{
//        					var value = {
//								name: records[i].data.name
//        					};
//        					mapInventory.add(records[i].data.code, value);
//        				}
//        				App.MapValue.setMapInventory(mapInventory);
//        			}
//        		}
//        	});
//        /*===============End Load All Agent and Set in A Map=================*/
//    },
    Test : function()
    {
    	var date = new Date();
    	var dd = date.getDate();
    	var mm = date.getMonth();
    	var yyyy = date.getFullYear();
    	
    }
});