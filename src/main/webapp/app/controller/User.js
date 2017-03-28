Ext.define(App.path('controller.User'), {
    extend: 'Ext.app.Controller',
    refs: [{
            ref: 'MainView',
            selector: '#MainView' //  id:MainView 

        },{
            ref: 'WestView',
            selector: '#west' //  id:west 

        },{
            ref: 'CenterView',
            selector: '#CenterView' //  id:west 

        }],
    showUser:function(){
    	if (App.Session.isLoggedIn()) {
            var store = App.path('store.User');
            var view = this.getMainView().activateViewItem('ViewIndex', function() {
               var viewItem= Ext.create(App.path('view.ViewIndex'));
               return viewItem;
             }, this).showWhats('ViewUser', App.path('view.ViewUser'), 'ViewBlank', App.path('view.ViewBlank'),store,'ViewUserDetail',App.path('view.ViewUserDetail'));
             this.getWestView().setTitle('Tìm kiếm người dùng');
           view.on('select',function(me, record){
           });
        }else{
            Ext.Router.redirect('');
        }
    	
    },
    showProfile: function() {
        if (App.Session.isLoggedIn()) {
            var store = App.store.Profile;
            this.getMainView().activateViewItem('ViewIndex', function() {
                var viewItem = Ext.create(App.path('view.ViewIndex'));
                return viewItem;
             }, this).showWhats('ViewProfile', App.path('view.ViewProfile'), 'ViewBlank', App.path('view.ViewBlank'),store,'ViewProfileDetail',App.path('view.ViewProfileDetail'));
             this.getWestView().setTitle('Danh sách nhóm');
             view.on('select',function(me, record){             
           });
        } else {
            Ext.Router.redirect('');
        }
    },
    showUserInformation : function() {
        if (App.Session.isLoggedIn()) {
            this.getMainView().activateViewItem('ViewIndex100', function() {// tuong tu getCmp thay vao day la get vao mot khung view da dung san co id de dai dien cho view do
            	var viewItem = Ext.create(App.path('view.ViewIndex100'));
            	viewItem.activate();
                return viewItem;
            }, this).showWhat('ViewUserInfoLogIn', App.path('view.ViewUserInfoLogIn'));
        } else {
            Ext.Router.redirect('');
        }
    },
    showChangePassword : function() {
        if (App.Session.isLoggedIn()) {
            this.getMainView().activateViewItem('ViewIndex100', function() {// tuong tu getCmp thay vao day la get vao mot khung view da dung san co id de dai dien cho view do
            	var viewItem = Ext.create(App.path('view.ViewIndex100'));
            	viewItem.activate();
                return viewItem;
            }, this).showWhat('ViewChangePassword', App.path('view.ViewChangePassword'));
        } else {
            Ext.Router.redirect('');
        }
    }, 
    showLoginUnder : function() {
    	if (App.Session.isLoggedIn()) {
            this.getMainView().activateViewItem('ViewIndex100', function() {// tuong tu getCmp thay vao day la get vao mot khung view da dung san co id de dai dien cho view do
            	var viewItem = Ext.create(App.path('view.ViewIndex100'));
            	viewItem.activate();
                return viewItem;
            }, this).showWhat('ViewLoginUnder', App.path('view.ViewLoginUnder'));
        } else {
            Ext.Router.redirect('');
        }
    } 
});