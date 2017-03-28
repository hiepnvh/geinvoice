Ext.define(App.path('controller.Invoice'), {
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
		showInvoiceAdd: function(){
			if (App.Session.isLoggedIn()) {
				var store = App.path('store.InvoiceBatch');
	            var view = this.getMainView().activateViewItem('ViewIndex', function() {
	               var viewItem= Ext.create(App.path('view.ViewIndex'));
	               return viewItem;
	             }, this).showWhats('ViewInvoiceBatch', App.path('view.ViewInvoiceBatch'), 'ViewBlank', App.path('view.ViewBlank'),store,'ViewInvoiceBatchDetail',App.path('view.ViewInvoiceBatchDetail'));
	            this.getWestView().setTitle('Tìm kiếm dải hóa đơn đã phát hành');
	            view.on('select',function(me, record){});
	        } else {
	            Ext.Router.redirect('');
	        }
		},
		showInvoiceAdjust: function(){
			if (App.Session.isLoggedIn()) {
	            var view = this.getMainView().activateViewItem('ViewIndex', function() {
	               var viewItem= Ext.create(App.path('view.ViewIndex'));
	               return viewItem;
	             }, this).showWhats('ViewInvoiceAdjust', App.path('view.ViewInvoiceAdjust'), 'ViewInvoiceAdjustDetail',App.path('view.ViewInvoiceAdjustDetail'));
	            this.getWestView().setTitle('Tìm kiếm hóa đơn điều chỉnh');
	            view.on('select',function(me, record){});
	        } else {
	            Ext.Router.redirect('');
	        }
		},
		showInvoiceUpdate: function(){
			if (App.Session.isLoggedIn()) {
	    		this.getMainView().activateViewItem('ViewIndex100', function() {// tuong tu getCmp thay vao day la get vao mot khung view da dung san co id de dai dien cho view do
	            	var viewItem = Ext.create(App.path('view.ViewIndex100'));
	            	viewItem.activate();
	                return viewItem;
	            }, this).showWhat('ViewInvoiceUpdateDetail', App.path('view.ViewInvoiceUpdateDetail'));
	        } else {
	            Ext.Router.redirect('');
	        }
		},
		showInvoiceConversion: function(){
			if (App.Session.isLoggedIn()) {
	    		this.getMainView().activateViewItem('ViewIndex100', function() {// tuong tu getCmp thay vao day la get vao mot khung view da dung san co id de dai dien cho view do
	            	var viewItem = Ext.create(App.path('view.ViewIndex100'));
	            	viewItem.activate();
	                return viewItem;
	            }, this).showWhat('ViewInvoiceConversion', App.path('view.ViewInvoiceConversion'));
	        } else {
	            Ext.Router.redirect('');
	        }
		}
});