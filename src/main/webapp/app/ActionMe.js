Ext.define(App.path('ActionMe'), {
			alternateClassName : 'App.ActionMe',
			statics : {
				GetMenu : function(menuId) {
					switch (menuId) {
						case 11 :
							return 'user';
						case 12 :
							return 'usergroup';

						case 21 :
							return 'smsgwconnect';
						case 22 :
							return 'routing';
						case 23 :
							return 'cdr';

						case 31 :
							return 'contentprovider';
						case 32 :
							return 'operator';

						case 41 :
							return 'smsrequest';
						case 42 :
							return 'smsauto';

						case 51 :
							return 'subcriber';
					}
				},
				//************* DATE ****************//
				ShortDateToMysqlDB : function(date_old) // date to database Mysql
				{
					var date = new Date(date_old);
					var dd = date.getDate();
					var mm = date.getMonth() + 1;
					var yyyy = date.getFullYear();					
					
					var hh = date.getHours();
					var mi = date.getMinutes();
					var ss = date.getSeconds();

					if (mm < 10) {mm = '0' + mm;}
					if (dd < 10) {dd = '0' + dd;}
					
					if (hh < 10) {hh = '0' + hh;}
					if (mi < 10) {mi = '0' + mi;}
					if (ss < 10) {ss = '0' + ss;}
					
					var date_new = dd + '-' + mm + '-' + yyyy + ' ' + hh + ':' + mi + ':' + ss;
//					dd-MM-yyyy HH:mm:ss
					return date_new;
				},
				MysqlDBToShortDate : function(date_old) // date to database Mysql
				{
//					console.log(date_old);
					var dd = date_old.substring(0, 2);
//					console.log(dd);
					var mm = date_old.substring(3, 5);
//					console.log(mm);
					var yyyy = date_old.substring(6, 10);
//					console.log(yyyy);
					var hh = date_old.substring(11, 13);
//					console.log(hh);
					var mi = date_old.substring(14, 16);
//					console.log(mi);
					var ss = date_old.substring(17, 19);
//					console.log(ss);
					var date_new = new Date();
					date_new.setDate(dd);
					date_new.setMonth(mm-1);
					date_new.setFullYear(yyyy);
					date_new.setHours(hh);
					date_new.setMinutes(mi);
					date_new.setSeconds(ss);
					return date_new;
				},
				
				
				
				
				
				
				DateFromDB : function(date_old) // date from database Oracle
				{
					var dd = date_old.substring(0, 2);
					var mm = ('JanFebMarAprMayJunJulAugSepOctNovDec')
							.indexOf(date_old.substring(3, 6))
							/ 3;
					var yyyy = date_old.substring(7, 11);
					var date_new = new Date();
					date_new.setDate(dd);
					date_new.setMonth(mm);
					date_new.setFullYear(yyyy);
					return date_new;
				},
				DateToDBDWH3 : function(date_old) // date to database Oracle
				{
					var date = new Date(date_old);
					var dd = date.getDate();
					var mm = date.getMonth() + 1;
					var yyyy = date.getFullYear();

					if (dd < 10) {
						dd = '0' + dd;
					}
					
					if (mm < 10) {
						mm = '0' + mm;
					}
					var date_new = yyyy + '/' + mm + '/' + dd;

					return date_new;
				},
				DateToDB : function(date_old) // date to database Oracle
				{
					var date = new Date(date_old);

					var month_names_short = ['Jan', 'Feb', 'Mar', 'Apr', 'May',
							'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

					var dd = date.getDate();
					var mm = month_names_short[date.getMonth()];
					var yyyy = date.getFullYear();

					if (dd < 10) {
						dd = '0' + dd;
					}
					var date_new = dd + '/' + mm + '/' + yyyy;

					return date_new;
				},
				DateToDBExactly : function(date_old){ // date to database Oracle
				
					var date = new Date(date_old);

					var month_names_short = ['Jan', 'Feb', 'Mar', 'Apr', 'May',
							'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

					var dd = date.getDate();
					var mm = month_names_short[date.getMonth()];
					var yyyy = date.getFullYear();
					var hh = date.getHours();
					var mi = date.getMinutes();
					var ss = date.getSeconds();

					if (dd < 10) {dd = '0' + dd;}
					
					if (hh < 10) {hh = '0' + hh;}
					if (mi < 10) {mi = '0' + mi;}
					if (ss < 10) {ss = '0' + ss;}
					
					var date_new = dd + '-' + mm + '-' + yyyy + ' ' + hh + ':' + mi + ':' + ss;

					return date_new;
				},
				DateFromDBExactly : function(date_old) // date from database Oracle
				{
					var dd = date_old.substring(0, 2);
					var mm = ('JanFebMarAprMayJunJulAugSepOctNovDec')
							.indexOf(date_old.substring(3, 6)) / 3;
					var yyyy = date_old.substring(7, 11);
					var hh = date_old.substring(12, 14);
					var mi = date_old.substring(15, 17);
					var ss = date_old.substring(18, 20);
					var date_new = new Date();
					date_new.setDate(dd);
					date_new.setMonth(mm);
					date_new.setFullYear(yyyy);
					date_new.setHours(hh);
					date_new.setMinutes(mi);
					date_new.setSeconds(ss);
					return date_new;
				},
				DateSetTime : function (date,hh,mi,ss){
					date.setHours(hh);
					date.setMinutes(mi);
					date.setSeconds(ss);
					
					return date;
				},
				ConsoleMe : function(consoleString) {  
					var is_chrome = window.chrome;
					if(is_chrome)
					{
						console.log(consoleString);
					}
				},
				ReplaceAll : function(myString,oldString,repString) {  
					return myString.split(oldString).join(repString);
				},
				FormatMsisdn : function(msisdn) // date from database Oracle
				{
					if(msisdn.substring(0,1)=='0')
						msisdn = '84' + msisdn.substring(1, msisdn.length);
					return msisdn; 
//					var patt = new RegExp("(099|0199)[0-9]{7}");
//    				var res = patt.test(msisdn);
//    				if(res == true)
//    				{
//						msisdn = '84' + msisdn.substring(1, msisdn.length);
//    				}
//					return msisdn;
				},
				GetDateNow : function() {
					/* Get Date */
					var today = new Date();
					var dd = today.getDate();
					var mm = today.getMonth() + 1; // January is 0!

					var yyyy = today.getFullYear();
					if (dd < 10) {
						dd = '0' + dd;
					}
					if (mm < 10) {
						mm = '0' + mm;
					}
					today = dd + '/' + mm + '/' + yyyy;
					return today;
					/**/
				},
				FormatToCurrency : function(num) {
					num = num.toString().replace(/\$|\,/g, '');
					if (isNaN(num))
						num = "0";
					sign = (num == (num = Math.abs(num)));
					num = Math.floor(num * 100 + 0.50000000001);
					cents = num % 100;
					num = Math.floor(num / 100).toString();
					if (cents < 10)
						cents = "0" + cents;
					for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
						num = num.substring(0, num.length - (4 * i + 3)) + ','
								+ num.substring(num.length - (4 * i + 3));
					return (((sign) ? '' : '-') + num);
					// return (((sign) ? '' : '-') + num + '.' + cents);
				},
				ReplaceAll : function(myString, oldString, repString) {
					return myString.split(oldString).join(repString);
				},
				filters : {
					ftype : 'filters',
					// encode and local configuration options defined previously
					// for easier reuse
					encode : false, // json encode the filter query
					local : true, // defaults to false (remote filtering)
					filters : [{
								type : 'boolean',
								dataIndex : 'visible'
							}]
				}

			}
		});
