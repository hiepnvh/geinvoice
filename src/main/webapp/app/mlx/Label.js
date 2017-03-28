Ext.define(App.fullClassNameOf('mlx.Label'), {
    extend: 'Ext.form.Label',
    //xtype: 'amxlabel',
    alias: 'widget.amxlabel',
    config: {
        htmlMId: null,
        htmlParams: null
    },
    initComponent: function() {
        var me = this;
        this.callParent();
        App.Setting.on('languagechange', this._updateHtml, this);
        var htmlMIdDef = this.getHtmlMId();
        var htmlMId = null;
        this.setHtmlMId = function(val) {
            if (htmlMId != val) {
                htmlMId = val;
                me._updateHtml();
            }
        };
        this.getHtmlMId = function() {
            return htmlMId;
        };
        this.setHtmlMId(htmlMIdDef);
    },
    updateHtml: function(newValue, oldValue) {
        this.callParent(arguments);
        this.fireEvent('htmlchange', this, newValue, oldValue);
    },
    _updateHtml: function() {
        var htmlMId = this.getHtmlMId();
        if ('string' === typeof htmlMId) {
            this.setText(App.Resource.getString(htmlMId, this.getHtmlParams()));
        } else if (null != htmlMId && 'object' === typeof htmlMId) {
            this.setText(htmlMId[App.Setting.getLanguage()]);
        }
    },
    setHtmlParams: function() {
        this.callParent(arguments);
        this._updateHtml();
    }
});