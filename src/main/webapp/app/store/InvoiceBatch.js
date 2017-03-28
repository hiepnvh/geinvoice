Ext.define(App.path('store.InvoiceBatch'), {
			extend : 'Ext.data.Store',
			model:App.path('model.InvoiceBatch'),
			storeId : 'InvoiceBatch',
			proxy : {
				type : 'ajax',
				url : 'invoicebatchget',
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