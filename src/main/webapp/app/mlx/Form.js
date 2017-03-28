Ext.define(App.fullClassNameOf('mlx.Form'), {
    extend: 'Ext.form.Panel',
    xtype: 'amxform',
    config: {
        titleMId:null,
        textParams:null
    },
     initComponent: function() {
        
        this.callParent();
        App.Setting.on('languagechange', this._update, this);
        this._update();
    },
    _update: function() {
       
        var tileMId = this.getTileMId();
        if ('string' === typeof tileMId) {
            this.setTitle(App.Resource.getString(tileMId, this.getTextParams()));
        } else if (null != tileMId && 'object' === typeof tileMId) {
            this.setTitle(tileMId[App.Setting.getLanguage()]);
        }
    },
    updateTextMId: function(newValue) {
        this._update();
    }
});