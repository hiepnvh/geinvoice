Ext.define(App.fullClassNameOf('controller.Main'), {
    extend: 'Ext.app.Controller',
    
    onLaunch: function() {
        var viewItems = Ext.create(App.fullClassNameOf('view.ViewEmployeeLate'));
        Ext.create(App.fullClassNameOf('view.ViewIndex'),{
            items: viewItems
        });
    }
     
});