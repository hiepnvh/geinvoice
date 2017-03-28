Ext.define(App.path('store.InvoiceAgency'), {
			extend : 'Ext.data.Store',
			model:App.path('model.InvoiceAgency'),
			storeId : 'InvoiceAgency',
			autoLoad: true,
			proxy : {
				type : 'ajax',
				url : 'invoiceagencyget',
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