Ext.define(App.path('controller.Report'), {
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
    showInvoiceRevenueReport:function(){
    	if (App.Session.isLoggedIn()) {
    		this.getMainView().activateViewItem('ViewIndex100', function() {// tuong tu getCmp thay vao day la get vao mot khung view da dung san co id de dai dien cho view do
            	var viewItem = Ext.create(App.path('view.ViewIndex100'));
            	viewItem.activate();
                return viewItem;
            }, this).showWhat('ViewInvoiceRevenueReport', App.path('view.ViewInvoiceRevenueReport'));
        } else {
            Ext.Router.redirect('');
        }
    },
    showInvoiceUsageReport:function(){
    	if (App.Session.isLoggedIn()) {
    		this.getMainView().activateViewItem('ViewIndex100', function() {// tuong tu getCmp thay vao day la get vao mot khung view da dung san co id de dai dien cho view do
            	var viewItem = Ext.create(App.path('view.ViewIndex100'));
            	viewItem.activate();
                return viewItem;
            }, this).showWhat('ViewInvoiceUsageReport', App.path('view.ViewInvoiceUsageReport'));
        } else {
            Ext.Router.redirect('');
        }
    },
    showInvoiceExport:function(){
    	if (App.Session.isLoggedIn()) {
    		this.getMainView().activateViewItem('ViewIndex100', function() {// tuong tu getCmp thay vao day la get vao mot khung view da dung san co id de dai dien cho view do
            	var viewItem = Ext.create(App.path('view.ViewIndex100'));
            	viewItem.activate();
                return viewItem;
            }, this).showWhat('ViewInvoiceExport', App.path('view.ViewInvoiceExport'));
        } else {
            Ext.Router.redirect('');
        }
    }
});