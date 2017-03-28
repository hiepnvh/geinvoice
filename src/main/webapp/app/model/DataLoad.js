function decode(v, record){
	switch(v)
		{
			case '-1' : return 'Giảm'; break;
			case -1 : return 'Giảm'; break;
			case '1' : return 'Tăng'; break;
			case 1 : return 'Tăng'; break;
		}
}

Ext.define(App.path('model.DataLoad'), {
	extend : 'Ext.data.Model',
	fields : [
			'data_load_id',
			'create_date',
			'symbol_of_invoice',
			'serial',
			'invoice_no',
			'customer_name',
			'company_name',
			'tax_code',
			'address',
			'msisdn',
			'customer_code',
			'erp_customer_code',
			'from_date',
			'to_date',
			'kind_of_payment',
			'kind_of_service_no',
			'kind_of_service',
			'unit',
			'quantity',
			'unit_price',
			'amount',
			'total',
			'vat_rate',
			'vat_amount',
			'grand_total',
			'sum_in_words',
//			'adjust_type',
			{name:'adjust_type',convert:decode},
			'parent_invoice_no'
 			]
});