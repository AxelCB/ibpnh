'use strict';
/**
 * DailyDevotional Service
 */
var ibpnhModules = angular.module('services');

ibpnhModules.factory('DailyDevotionalService', function(IbpnhService){
	return {
		create: function (url,params,file, successFn, errorFn) {
			//return IbpnhService.authPost('/dailyDevotional/create.json', params, successFn, errorFn);
			return IbpnhService.fileAuthPost(url, params,file,successFn, errorFn);
		},
		remove: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/dailyDevotional/delete.json', params, successFn, errorFn);
		},
		modify: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/dailyDevotional/modify.json', params, successFn, errorFn);
		},
		list: function (params, successFn, errorFn) {
			return IbpnhService.post('/dailyDevotional/public/list.json', params, successFn, errorFn);
		},
		search: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/dailyDevotional/search.json', params, successFn, errorFn);
		},
		lastDevotionals: function(params,successFn, errorFn) {
			return IbpnhService.post('/dailyDevotional/public/lastDevotionals.json',params, successFn, errorFn);
		},
		find: function (params, successFn, errorFn) {
			return IbpnhService.post('/dailyDevotional/public/find.json', params, successFn, errorFn);
		},
	};
});