function decode(v, record){
	switch(v)
		{
			case 0 : return 'Mới tạo (HĐ trống)'; break;
			case 1 : return 'Đã có dữ liệu'; break;
			case 2 : return 'Được tạo và ký điện tử'; break;
			case 3 : return 'Đã được chuyển đổi'; break;
			case 11 : return 'Đã xóa'; break;
			case 12 : return 'Đã mất'; break;
			case 13 : return 'Đã hủy'; break;
		}
}

function decode2st(v, record){
	return String("0000000" + v).slice(-7);
}

Ext.define(App.path('model.InvoiceDetail'), {
	extend : 'Ext.data.Model',
	fields : [
			'invoice_detail_id',
//			'invoice_no',
			{name:'invoice_no',convert:decode2st},
			'symbol_of_invoice',
//			'status',
			{name:'status',convert:decode},
			'invoice_batch_id',
			'data_load_id',
			'symbol_of_invoice',
			'serial'
 			]
});