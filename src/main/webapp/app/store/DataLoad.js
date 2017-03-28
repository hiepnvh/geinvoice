Ext.define(App.path('store.DataLoad'), {
			extend : 'Ext.data.Store',
			model:App.path('model.DataLoad'),
			storeId : 'DataLoad',
			proxy : {
				type : 'ajax',
				url : 'dataloadget',
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