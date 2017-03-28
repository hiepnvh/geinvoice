Ext.define(App.path('controller.Home'), {
    extend: 'Ext.app.Controller',
    refs: [{
            ref: 'MainView',
            selector: '#MainView' //  id:MainView 

        },{
            ref: 'WestView',
            selector: '#west' //  id:west 

        }],
        init: function() {
            this.control({
                '#dataloadadd' : {click: this.onMenuClick},
                '#invoiceadd' : {click: this.onMenuClick},
                '#invoiceadjust' : {click: this.onMenuClick},
                '#invoiceupdate' : {click: this.onMenuClick}, // update status for invoice
                '#invoiceconversion' : {click: this.onMenuClick}, // update status for invoice
                '#invoiceexport' : {click: this.onMenuClick},
                '#invoiceusagereport' : {click: this.onMenuClick},
                '#invoicerevenuereport' : {click: this.onMenuClick}
            });
        },
        onMenuClick: function(btn) {
        	if(('#'+btn.itemId) != location.hash) {
	            Ext.Router.redirect(btn.itemId === 'dataloadadd' ? 'dataloadadd' : btn.itemId);
	            Ext.Router.redirect(btn.itemId === 'invoiceadd' ? 'invoiceadd' : btn.itemId);
	            Ext.Router.redirect(btn.itemId === 'invoiceadjust' ? 'invoiceadjust' : btn.itemId);
	            Ext.Router.redirect(btn.itemId === 'invoiceupdate' ? 'invoiceupdate' : btn.itemId);
	            Ext.Router.redirect(btn.itemId === 'invoiceconversion' ? 'invoiceconversion' : btn.itemId);
	            Ext.Router.redirect(btn.itemId === 'invoiceexport' ? 'invoiceexport' : btn.itemId);
	            Ext.Router.redirect(btn.itemId === 'invoiceusagereport' ? 'invoiceusagereport' : btn.itemId);
	            Ext.Router.redirect(btn.itemId === 'invoicerevenuereport' ? 'invoicerevenuereport' : btn.itemId);
        	}
        },
	    showHome: function() {
            this.getMainView().activateViewItem('ViewIndex100', function() {
	             var viewItem = Ext.create(App.path('view.ViewIndex100'));
	             viewItem.activate();
	                return viewItem;
	            }, this,function(viewItem){
	//    viewItem.activate();
	   }).showWhat('ViewBlank', App.path('view.ViewBlank'));
	        }
});