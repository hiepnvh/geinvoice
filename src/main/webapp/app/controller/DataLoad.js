Ext.define(App.path('controller.DataLoad'), {
    extend: 'Ext.app.Controller',
    refs: [{
            ref: 'MainView',
            selector: '#MainView' //  id:MainView 

        },{
            ref: 'WestView',
            selector: '#west' //  id:west 

        },{
            ref: 'CenterView',
            selector: '#CenterView' //  id:west 

        }],
		showDataLoadAdd : function() {
			if (App.Session.isLoggedIn()) {
				var view = this.getMainView().activateViewItem('ViewIndex', function() {
					var viewItem = Ext.create(App.path('view.ViewIndex'));
					return viewItem;
//				}, this).showWhats('ViewAccountInfoMaster', App.path('view.ViewAccountInfoMaster'), 'ViewBlank', App.path('view.ViewBlank'), 'ViewAccountInfoMasterDetail',App.path('view.ViewAccountInfoMasterDetail'));
				}, this).showWhats('ViewDataLoad', App.path('view.ViewDataLoad'), 'ViewDataLoadDetail',App.path('view.ViewDataLoadDetail'));
					this.getWestView().setTitle('Tìm kiếm dữ liệu đã load');
	        } else {
	            Ext.Router.redirect('');
	        }
		}
});