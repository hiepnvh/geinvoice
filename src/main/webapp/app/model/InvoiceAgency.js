Ext.define(App.path('model.InvoiceAgency'), {
	extend : 'Ext.data.Model',
	fields : [
	         { name: 'invoice_agency_id', type: 'int' },
			'seller',
			'tin',
			'address',
			'tel',
			'fax'
 			]
});