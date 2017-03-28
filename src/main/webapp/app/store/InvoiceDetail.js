Ext.define(App.path('store.InvoiceDetail'), {
			extend : 'Ext.data.Store',
			model:App.path('model.InvoiceDetail'),
			storeId : 'InvoiceDetail',
			proxy : {
				type : 'ajax',
				url : 'invoicedetailget',
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