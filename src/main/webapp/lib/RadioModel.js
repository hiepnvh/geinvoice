Ext.define('Ext.selection.RadioModel', {
    alias: 'selection.radiomodel',
    extend: 'Ext.selection.RowModel',
    mode: 'SINGLE',
    injectRadio: 0,
    radioOnly: false,
    showHeaderRadio: false,
    headerWidth: 24,
    bindComponent: function(view) {
        var me = this;
        me.sortable = false;
        me.callParent(arguments);
        if (!me.hasLockedHeader() || view.headerCt.lockedCt) {
            me.addRadio(true);
            me.mon(view.ownerCt, 'reconfigure', me.onReconfigure, me);
        }
    },
    hasLockedHeader: function() {
        var views = this.views,
            vLen = views.length,
            v;
        for (v = 0; v < vLen; v++) {
            if (views[v].headerCt.lockedCt) {
                return true;
            }
        }
        return false;
    },
    addRadio: function(initial) {
        var me = this,
            radio = me.injectRadio,
            view = me.views[0],
            headerCt = view.headerCt;
        if (radio !== false) {
            if (radio == 'first') {
                radio = 0;
            } else if (radio == 'last') {
                radio = headerCt.getColumnCount();
            }
            Ext.suspendLayouts();
            headerCt.add(radio, me.getHeaderConfig());
            Ext.resumeLayouts();
        }
        if (initial !== true) {
            view.refresh();
        }
    },
    onReconfigure: function(grid, store, columns) {
        if(columns) {
            this.addRadio();
        }
    },
    getHeaderConfig: function() {
        var me = this,
            showRadio = me.showHeaderRadio = false;
        return {
            isRadiorHd: showRadio,
            text : '&#160;',
            width: me.headerWidth,
            sortable: false,
            draggable: false,
            resizable: false,
            hideable: false,
            menuDisabled: true,
            dataIndex: '',
            cls: '',
            renderer: Ext.Function.bind(me.renderer, me),
            editRenderer: me.editRenderer || me.renderEmpty,
            locked: me.hasLockedHeader()
        };
    },
    renderEmpty: function() {
        return '&#160;';    
    },
    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
        var baseCSSPrefix = Ext.baseCSSPrefix;
        metaData.tdCls = baseCSSPrefix + 'grid-cell-special ' + baseCSSPrefix + 'grid-cell-row-radio';
        return '<div class="' + baseCSSPrefix + 'grid-row-radio">&#160;</div>';
    },
    onRowMouseDown: function(view, record, item, index, e) {
        view.el.focus();
        var me = this,
            radior = e.getTarget('.' + Ext.baseCSSPrefix + 'grid-row-radio'),
            mode;
        if (!me.allowRightMouseSelection(e)) {
            return;
        }
        if (me.radioOnly && !radior) {
            return;
        }
        if (radior) {
            mode = me.getSelectionMode();
            if (mode !== 'SINGLE') {
                me.setSelectionMode('SINGLE');
            }
            me.selectWithEvent(record, e);
            me.setSelectionMode(mode);
        } else {
            me.selectWithEvent(record, e);
        }
    }
});
