Ext.define(App.path('Setting'), {
    extend: 'Ext.util.Observable',
    config: {
        language: 'vi',
        hostUrl:'http://localhost:8084/iddproject/'
    },
    updateLanguage: function(newValue, oldValue) {
        this.fireEvent('languagechange', this, newValue, oldValue);
    }
});