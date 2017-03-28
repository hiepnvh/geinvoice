Ext.define(App.fullClassNameOf('Resource'), {
    constructor: function() {
        this._mlstrings_ = Ext.getStore(App.fullClassNameOf('store.resource.MLStrings'));
    },
    getString: function(strId, params) {
        if (null == strId || '' == strId) {
            return strId;
        }
        function formatStr(str, params) {
            if (null != str && 'object' === typeof params) {
                for (var prop in params) {
                    str = str.replace(new RegExp('{' + prop + '}', 'g'), params[prop]);
                }
            }
            return str;
        }
        var lang = App.Setting.getLanguage();
        var item;
        switch (typeof strId) {
        case 'object':
            item = strId[lang];
            return ('string' === typeof item) ? formatStr(item, params) : item;
        case 'string':
            item = this._mlstrings_.getById(strId);
            if (undefined != item) {
                item = item.get(lang);
                return (undefined == item ) ? formatStr(strId, params) : formatStr(item, params);
            } else {
               // console.log('Warning: No definition of string "' + strId + '"');
                return undefined;
            }
        default:
            //console.log('Error: String "' + strId + '" has an invalid format');
            return undefined;
        }
    }
});