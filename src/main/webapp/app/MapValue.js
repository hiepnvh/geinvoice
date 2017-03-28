Ext.define(App.path('MapValue'), {
			extend : 'Ext.util.Observable',
			alternateClassName : 'App.MapValue',
			singleton : true,
			config : {
				mapAgent : null,
				mapStaff : null,
				mapInventory : null
			}
		});
