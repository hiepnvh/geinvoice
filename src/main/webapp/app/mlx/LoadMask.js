Ext.define(App.fullClassNameOf('mlx.LoadMask'), {
    extend: 'Ext.LoadMask',
    xtype: 'amxloadmask',
    config: {
        messageMId: null,
        messageParams: null
    },
    initialize: function() {
        this.callParent();
        this.on('erased', function() {
            App.Setting.un('languagechange', this._update, this);
        }, this);
        App.Setting.on('languagechange', this._update, this);
    },
    _update: function() {
        var messageMId = this.getMessageMId();
        if ('string' === typeof messageMId) {
            this.setMessage(App.Resource.getString(messageMId, this.getMessageParams()));
        } else if (null != messageMId && 'object' === typeof messageMId) {
            this.setMessage(messageMId[App.Setting.getLanguage()]);
        }
    },
    updateMessageMId: function() {
        this._update();
    },
    updateMessageParams: function() {
        this._update();
    }
});