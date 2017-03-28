Ext.define(App.path('mlx.Container'), {
    extend: 'Ext.container.Container',
    xtype: 'amxcontainer',
    config: {
        layout: {
            type: 'card',
            animation: {
                type: 'fade',
                duration: 200
            }
        },
        userData: null
    },
    addViewItem: function(view, id) {
        var me= this;
        var itemcount = me.items.length;
        for (var i = 0; i < itemcount; i++) {
            if (id === me.items.getAt(i).itemId)
                return me.items.getAt(i);
        }
        var item = me.add(view);
        if ('string' === typeof id && null !==item) {
            item.itemId = id ;
        }
        return item;
    },
    getViewItem: function(id) {
        var itemcount = this.items.length;
        switch (typeof id) {
            case 'number':
                return this.items.getAt(id);
            case 'string':
                for (var i = 0; i < itemcount; i++) {
                    if (id === this.items.getAt(i).itemId)
                        return this.items.getAt(i);
                }
                break;
            default:
                break;
        }
        return undefined;
    },
    getViewItemCount: function() {
        return this.getItems().length;
    },
    getIndexOfViewItem: function(item) {
        var itemcount = this.items.length;
        switch (typeof item) {
            case 'string':
                for (var i = 0; i < itemcount; i++) {
                    if (item === this.items.getAt(i).itemId)
                        return i;
                }
                break;
            case 'object':
                for (var i = 0; i < itemcount; i++) {
                    if (item === this.items.getAt(i))
                        return i;
                }
                break;
            default:
                break;
        }
        return -1;
    },
    getActiveViewItem: function() {
        var viewItem = this.layout.getActiveItem();
        return ('object' === typeof viewItem) ? viewItem : this.getViewItem(viewItem);
    },
    setActiveViewItem: function(item, animation) {
        var activeitem = this.getActiveViewItem();
        var itemcount = this.items.length;
        switch (typeof item) {
            case 'number':
                if (undefined !== animation) {
                    this.animateActiveItem(item, animation);
                } else {
                    this.layout.setActiveItem(item);
                }
                return true;
            case 'string':
                if (undefined !== activeitem && item === activeitem.itemId) {
                    return true;
                }
                for (var i = 0; i < itemcount; i++) {
                    if (item === this.items.getAt(i).itemId) {
                        if (undefined !== animation) {
                            this.animateActiveItem(i, animation);
                        } else {
                            this.layout.setActiveItem(i);
                        }
                        return true;
                    }
                }
                break;
            case 'object':
                if (item.itemId === activeitem.itemId) {
                    return true;
                }
                for (var i = 0; i < itemcount; i++) {
                    if (item.itemId === this.items.getAt(i).itemId) {
                        if (undefined !== animation) {
                            this.animateActiveItem(i, animation);
                        } else {
                            this.layout.setActiveItem(i);
                        }
                        return true;
                    }
                }
                break;
            default:
                break;
        }
        return false;
    },
    containsViewItem: function(item) {
        var itemcount = this.items.length;
        switch (typeof item) {
            case 'number':
                return item < itemcount;
            case 'string':
                for (var i = 0; i < itemcount; i++) {
                    if (item === this.items.getAt(i).itemId)
                        return true;
                }
                break;
            case 'object':
                for (var i = 0; i < itemcount; i++) {
                    if (item === this.items.getAt(i))
                        return true;
                }
                break;
            default:
                break;
        }
        return false;
    },
    removeViewItem: function(item, destroy) {
        var itemcount = this.items.length;
        switch (typeof item) {
            case 'number':
                var i = item;
                item = this.items.getAt(item);
                if (undefined !== item) {
                    this.removeAt(i);
                    if (true === destroy)
                        item.destroy();
                    return true;
                }
                break;
            case 'string':
                for (var i = 0; i < itemcount; i++) {
                    if (item === this.items.getAt(i).itemId) {
                        item = this.items.getAt(i);
                        this.removeAt(i);
                        if (true === destroy)
                            item.destroy();
                        return true;
                    }
                }
                break;
            case 'object':
                for (var i = 0; i < itemcount; i++) {
                    if (item === this.items.getAt(i)) {
                        this.removeAt(i);
                        return true;
                    }
                }
                break;
            default:
                break;
        }
        return false;
    },
    removeAllViewItems: function(destroy) {
        this.removeAll(destroy, false);
    },
    activateViewItem: function(viewItemId, creationFn, creationScope, activationFn, activationScope) {
        //try{
            var viewItem = this.getViewItem(viewItemId);
            if (undefined == viewItem) {
                if ('function' === typeof creationFn) {
                    viewItem = creationFn.call(creationScope);
                    viewItem.itemId = viewItemId;
                    this.addViewItem(viewItem);
                } else {
                    viewItem = this.add(creationFn);
                    viewItem.itemId = viewItemId;
                }
            } else if ('function' === typeof activationFn) {
                activationFn.call(activationScope, viewItem);
            }
            this.setActiveViewItem(viewItem);
            return viewItem;
//        }catch(ex){
//          alert(ex);   
//        }
        
    }
});