Ext.define(App.path('model.InvoiceBatch'), {
	extend : 'Ext.data.Model',
	fields : [
			'invoice_batch_id',
			'create_date',
			'invoice_agency_id',
			'symbol_of_invoice',
			'serial',
			'start_no',
			'quantity',
			'invoice_created_date'
 			]
});