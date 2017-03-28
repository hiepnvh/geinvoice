//------------------------------------------------------------------------------
//	store : Ext.create('Ext.data.Store', {
//				storeId : 'storeId3',
//				fields : ['msisdn']
//			}),
//------------------------------------------------------------------------------
//readOnly : true,
//fieldStyle: 'background-color: #ddd; background-image: none;'

//Ext.get(document.body).mask('Chờ giây lát..');
//Ext.get(document.body).unmask();

var report_date = App.Constant.FormatDate(new Date());
/********************* Reset form *********************/
this.getForm().reset();

/********************* Change color Label, Text*********************/
fieldLabel: 'State <span style="color: red;">(*)</span>'

/********************* Value checkbox*********************/
var active  = this.down('#active').getValue() ? 1 : 0;

/********************* search value in combobox*********************/

//queryMode: 'local',
//typeAhead: true,
//minChars: 1,


/****************** Filter in grid Panel ********************/
//Have to add lib grid and ajax (ux) lib of extjs

//Ext.Loader.setConfig({enabled: true});
//Ext.Loader.setPath('Ext.ux', 'lib/ux');
//Ext.require([
//    'Ext.grid.*',
//    'Ext.data.*',
//    'Ext.ux.grid.FiltersFeature',
//    'Ext.toolbar.Paging',
//    'Ext.ux.ajax.JsonSimlet',
//    'Ext.ux.ajax.SimManager'
//]);


//var filters = {
//    ftype: 'filters',
//    // encode and local configuration options defined previously for easier reuse
//    encode: false, // json encode the filter query
//    local: true,   // defaults to false (remote filtering)
//    filters: [{
//        type: 'boolean',
//        dataIndex: 'visible'
//    }]
//};

// in grid

//columns : [{
//	text: 'STT',
//	xtype: 'rownumberer',
//	width: 30
//	},{
//	text: 'Mã đại lý',	
//	flex     : .5,
//	sortable:true,
//	dataIndex: 'agent_code',
//	filter: {
//        type: 'string'
//    }
//}]

//*************************

//{
//xtype : 'combobox',
//flex : 1,
//itemId : 'state',
//name : 'state',
//fieldLabel : 'Trạng thái',
//store : Ext.create('Ext.data.Store', {
//			fields : ['id', 'name'],
//			data : [{
//						id : 1,
//						name : 'Chờ phê duyệt'
//					}, {
//						id : 2,
//						name : 'Đang hoạt động'
//					}, {
//						id : 3,
//						name : 'Dừng hoạt động'
//					}]
//		}),
//displayField : 'name', //require
//valueField : 'id'
//}

//*************************
