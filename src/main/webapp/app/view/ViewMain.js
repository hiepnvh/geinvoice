Ext.define(App.path('view.ViewMain'), { 
    extend: 'Ext.container.Viewport',
    layout: 'fit',
    xtype: 'ViewMain',
    items: {
        xtype: 'amxcontainer',
        id: 'MainView',
        layout: 'card'
    }
});