Ext.define(App.path('store.Serial'), {
			extend : 'Ext.data.Store',
			model:App.path('model.Serial'),
			storeId : 'Serial',
			autoLoad: true,
			proxy : {
				type : 'ajax',
				url : 'serialget',
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