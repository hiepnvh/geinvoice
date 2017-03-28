Ext.define(App.path('Session'), {
			extend : 'Ext.util.Observable',
			alternateClassName : 'App.Session',
			singleton : true,
			config : {
				userName : null,
				displayName : null,
				mobile : null,
				mail : null,
				functionlist : null
			},
			constructor : function(config) {
				this.initConfig(config);
			}
			,applyAgentId : function(val) {
				this.fireEvent('logginchange', this);
				return val;
			},

			isLoggedIn : function() {
				return (null != this.functionlist);

			}
		});