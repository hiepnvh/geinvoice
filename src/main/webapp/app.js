Ext.namespace('App');
App.NAME = 'HN';
App.path = function(name) {
    return App.NAME + '.' + name;
};
Ext.application({
    name: App.NAME,
    viewport: {
        autoMaximize: true
    },
    requires: [
        App.path('mlx.Container'),
        App.path('plugins.Localize'),
        App.path('Session'),
        App.path('Setting'),
        App.path('Action'),
        App.path('ActionMe'),
        App.path('Constant'),
        App.path('MapValue')
    ],
    models: [
    ],
    stores: [
    	App.path('store.User')
    	
    	,App.path('store.InvoiceAgency')
    	,App.path('store.DataLoad')
    	,App.path('store.InvoiceBatch')
    	,App.path('store.InvoiceDetail')
    	,App.path('store.InvoiceDetailStatus')
    	,App.path('store.Serial')
    	
    ],
    controllers: [
        App.path('controller.Home'),
		App.path('controller.DataLoad'),
		App.path('controller.Invoice'),
		App.path('controller.Report')
    ],
    enableRouter: true,
    routes: {
    	 '/': 'Home#showHome',
    	 'home':'Home#showHome',
    	 
    	 'dataloadadd': 'DataLoad#showDataLoadAdd',
    	 'invoiceadd': 'Invoice#showInvoiceAdd',
    	 'invoiceadjust': 'Invoice#showInvoiceAdjust',
    	 'invoiceupdate': 'Invoice#showInvoiceUpdate',
    	 'invoiceconversion': 'Invoice#showInvoiceConversion',
    	 'invoiceexport': 'Report#showInvoiceExport',
    	 'invoicerevenuereport': 'Report#showInvoiceRevenueReport',
    	 'invoiceusagereport': 'Report#showInvoiceUsageReport'
    },
    launch: function() {
        var me= this;
        Ext.ux.ActivityMonitor.isInactive = function() {
           me.logout();
        };
        Ext.ux.ActivityMonitor.interval = 5 * 1000;
        Ext.ux.ActivityMonitor.maxInactive = 30 * 60 * 1000;
        Ext.ux.ActivityMonitor.init({
            verbose: false
        });
        Ext.ux.Router.on({
            routemissed: function(token) {
                Ext.Msg.show({
                    title: 'Error 404',
                    msg: 'Route not found: ' + token,
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.ERROR
                });
            },   
            dispatch: function(token, match, params, controller) {
            }
        });
         
        Ext.create(App.path('view.ViewMain'));
    }
});
