Ext.define(App.path('plugins.Localize'), {
    alias: 'plugin.localize',
    config: {
        params: {},
        component: null
    },
    constructor: function(config) {
        this.initConfig(config);
    },
    relocalize: function() {
        var component = this.getComponent();
        var params = this.getParams();
        if (null != component && Ext.isObject(params)) {
            for (var key in params) {
                var value = params[key];
                component[key].call(component, Ext.isObject(value) ? value[App.Setting.getLanguage()] : App.Resource.getString(value));
            }
        }
    },
    init: function(component) {
        this.setComponent(component);
        this.relocalize.call(this);
        if (null != component) {
            App.Setting.on('languagechange', this.relocalize, this);
        }
    },
    updateParams: function(params) {
        this.relocalize.call(this);
    }
});