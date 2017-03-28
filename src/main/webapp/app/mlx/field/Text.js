Ext.define(App.fullClassNameOf('mlx.field.Text'), {
    extend: 'Ext.form.field.Text',
    xtype: 'amxtextfield',
    config: {
        clearIcon: false,
        placeHolderMId: null,
        placeHolderParams: null,
        labelMId: null,
        labelParams: null,
        //baseCls: 'mlx-textfield-base',
        cls: 'mlx-textfield-normal',
        inputCls: 'mlx-inputfield'
    },
    initComponent: function() {
        this.callParent();
        App.Setting.on('languagechange', function() {
            this._updatePlaceHolder();
            this._updateLabel();
        }, this);
        this._updateLabel();
        this._updatePlaceHolder();
        var storevalue = this.getValue();
        var proc = function() {
            var value = this.getValue();
            if (value != storevalue) {
                storevalue = value;
                this.fireEvent('valuechange', this, value, storevalue);
            }
        };
        this.on('change', proc, this);
        this.on('paste', proc, this);
        this.on('keyup', proc, this);
    },
    _updatePlaceHolder: function() {
        var placeHolderMId = this.getPlaceHolderMId();
        if ('string' === typeof placeHolderMId) {
          this.emptyText = (App.Resource.getString(placeHolderMId, this.getPlaceHolderParams()));
          this.applyEmptyText();
        } else if (null != placeHolderMId && 'object' === typeof placeHolderMId) {
           this.emptyText= (placeHolderMId[App.Setting.getLanguage()]);
           this.applyEmptyText();
        }
    },
    _updateLabel: function() {
        var labelMId = this.getLabelMId();
        if ('string' === typeof labelMId) {
            this.setFieldLabel(App.Resource.getString(labelMId, this.getLabelParams()));
        } else if (null != labelMId && 'object' === typeof labelMId) {
            this.setLabel(labelMId[App.Setting.getLanguage()]);
        }
    },
    updatePlaceHolderMId: function() {
        this._updatePlaceHolder();
    },
    updatePlaceHolderParams: function() {
        this._updatePlaceHolder();
    },
    updateLabelMId: function() {
        this._updateLabel();
    },
    updateLabelParams: function() {
        this._updateLabel();
    }
});