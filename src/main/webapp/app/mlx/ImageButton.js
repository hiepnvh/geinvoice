Ext.define(App.fullClassNameOf('mlx.ImageButton'), {
    extend: 'Ext.Button',
    xtype: 'amximagebutton',
    config: {
        bkgId: null
    },
    updateBkgId: function(newValue) {
        this.setHtml((null != newValue) ? ('<img src="' + App.Resource.getImageInfo(newValue).src + '"/>') : '');
    }
});