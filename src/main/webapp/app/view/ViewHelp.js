Ext.define(App.fullClassNameOf('view.ViewHelp'), {
    extend:'Ext.form.Panel',
    xtype:'ViewHelp',
    itemId:'ViewHelp',
    autoScroll:true,
    border:false,
    activate:function(){
       var me= this;
        var dynamicPanel = new Ext.Component({
           loader: {
              url: 'app/html/help.html',
              renderer: 'html',
              autoLoad: true,
              scripts: true
              }
           });

       me.add(dynamicPanel);
//       Ext.Ajax.request({
//            url: 'app/html/help.html',
//            success: function(response){
//                me.html=response;
//            }
//        }); 
    }
})