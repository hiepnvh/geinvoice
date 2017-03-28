Ext.define(App.path('view.ViewIndex1'), {
    extend: 'Ext.container.Container',
    itemId: 'viewindex1',
    layout: 'border',
    requires: App.path('view.ViewMenu'),
    items: [{
            xtype: 'menu',
            itemId: 'menu',
            region: 'north',
            border: true,
            height: 105,
            bodyStyle: 'background-color: transparent'

        }, {
            region: 'center',
            border: false,
            layout: 'fit',
            bodyStyle: 'background-color: transparent',
            items: {
                xtype: 'amxcontainer',
                itemId: 'CenterView',
                layout: 'card'
            }
        }],
    reset: function() {
        this.down('#menu').activate();
    },
    showWhat: function( itemIdCenter, CenterView,data) {
        var _data = data + '';
        this.reset();
        this.down('#CenterView').activateViewItem(itemIdCenter, function() {
           var viewItem=Ext.create(CenterView);
           if(_data=='')
        	   viewItem.store.add(data);
           return viewItem;
        }, this).activate();
    },
   activate: function() {
    //    console.log('vao index');
    }
         
});