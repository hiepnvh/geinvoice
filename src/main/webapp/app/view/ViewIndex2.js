/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


Ext.define(App.path('view.ViewIndex2'), {
    extend: 'Ext.container.Container',
    itemId: 'viewindex2',
    layout: 'border',
    xtype:'viewindex2',
    requires: App.path('view.ViewMenu'),
    items: [{
            xtype: 'menu',
            itemId: 'menu',
            region: 'north',
            border: true,
            height: 105,
            bodyStyle: 'background-color: transparent'

        }, {
            region: 'west',
            layout: 'fit',
            collapsible: true,
            width: 600,
            itemId:'west',
            border: true,
            bodyStyle: 'background-color: transparent',
            items: {
                xtype: 'amxcontainer',
                itemId: 'WestView',
                layout: 'card'
            }
        }, {
            region: 'center',
            border: false,
            //margin:2,
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
    showWhat: function(itemIdWest, WestView, itemIdCenter, CenterView) {
        this.reset();
        
        this.down('#WestView').activateViewItem(itemIdWest, function() {
            return Ext.create(WestView);
        }, this).activate();

        this.down('#CenterView').activateViewItem(itemIdCenter, function() {
            return Ext.create(CenterView);
        }, this).activate();
    },
    showWhats: function(itemIdWest, WestView, itemIdCenter, CenterView,store,itemDetail,DetailView,storeDetail) {
        this.reset() ;
        var me=this;
        this.down('#WestView').activateViewItem(itemIdWest, function() {   
            var view =  Ext.create(WestView,{
                store:store
            });
            view.on('select',function(m, record){
                me.down('#CenterView').activateViewItem(itemDetail, function() {
                     var view_list = Ext.create(DetailView,{ record:record,store:storeDetail});
                    return view_list;
                }, this,function(viewItem){
                    viewItem.record = record;
                }).activate();
                
            });
            return view;
        }, this).activate();

        this.down('#CenterView').activateViewItem(itemIdCenter, function() {
            return Ext.create(CenterView);
        }, this).activate();
    },
   activate: function() {
        console.log('vao index');
    }
         
});