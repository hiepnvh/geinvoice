Ext.define(App.path('model.User'), {
    extend: 'Ext.data.Model',
    fields:[
			'user_id',
			'active',
			'profile_id',
			'email',
			'password',
			'mobile',
			'name',
			'create_date',
			'username'
			]
});