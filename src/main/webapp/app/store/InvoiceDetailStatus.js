Ext.define(App.path('store.InvoiceDetailStatus'), {
			extend : 'Ext.data.Store',
			model:App.path('model.InvoiceDetailStatus'),
			storeId : 'InvoiceDetailStatus',
			autoLoad: true,
			proxy : {
				type : 'ajax',
				url : 'invoicedetailstatusget',
				actionMethods : {
					create : 'POST',
					read : 'POST',
					update : 'POST',
					destroy : 'POST'
				},
				extraParams : {
					format : 'json'
				},
				reader : {
					type : 'json',
					root : 'result',
					totalProperty: 'total'
				}
			}
		});