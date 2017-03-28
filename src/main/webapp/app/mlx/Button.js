Ext.define(App.fullClassNameOf('mlx.Button'), {
    extend: 'Ext.button.Button',
    xtype: 'amxbutton',
    config: {
        textMId: null,
        textParams: null,
        baseCls: 'mlx-button',
        pressedCls: 'mlx-button-pressed',
        disabledCls: 'mlx-button-disabled'
    },
    initComponent: function() {
        
        this.callParent();
        App.Setting.on('languagechange', this._update, this);
        this._update();
    },
    _update: function() {
       
        var textMId = this.getTextMId();
        if ('string' === typeof textMId) {
            this.setText(App.Resource.getString(textMId, this.getTextParams()));
        } else if (null != textMId && 'object' === typeof textMId) {
            this.setText(textMId[App.Setting.getLanguage()]);
        }
    },
    updateTextMId: function(newValue) {
        this._update();
    }
});
