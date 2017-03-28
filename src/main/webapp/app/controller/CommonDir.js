Ext.define(App.path('controller.CommonDir'), {
    extend: 'Ext.app.Controller',
    refs: [{
            ref: 'MainView',
            selector: '#MainView' //  id:MainView 

        },{
            ref: 'WestView',
            selector: '#west' //  id:west 

        },{
            ref: 'CenterView',
            selector: '#CenterView' //  id:west 

        }],
	    showCommonDir : function() {
		var view = this.getMainView().activateViewItem('ViewIndex1',
				function() {
					var viewItem = Ext.create(App.path('view.ViewIndex1'));
					return viewItem;
				}, this).showWhat('ViewCommonDir',App.path('view.ViewCommonDir'));
	}
});