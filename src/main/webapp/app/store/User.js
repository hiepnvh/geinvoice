Ext.define(App.path('store.User'), {
			extend : 'Ext.data.Store',
			model:App.path('model.User'),
			storeId : 'User',
			proxy : {
				type : 'ajax',
				url : 'getuser',
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
					root : 'user_list'
				}
			}
		});