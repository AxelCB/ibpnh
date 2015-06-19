'use strict';
/**
 * DailyDevotional Service
 */
var ibpnhModules = angular.module('services');

ibpnhModules.factory('DailyDevotionalService', function(IbpnhService){
	return {
		create: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/dailyDevotional/create.json', params, successFn, errorFn);
		},
		remove: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/dailyDevotional/delete.json', params, successFn, errorFn);
		},
		modify: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/dailyDevotional/modify.json', params, successFn, errorFn);
		},
		list: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/dailyDevotional/list.json', params, successFn, errorFn);
		},
		search: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/dailyDevotional/search.json', params, successFn, errorFn);
		},
		lastDevotionals: function(params,successFn, errorFn) {
			return IbpnhService.post('/dailyDevotional/lastDevotionals.json',params, successFn, errorFn);
		},
		listView: function (params, successFn, errorFn) {
			return IbpnhService.post('/dailyDevotional/list.json', params, successFn, errorFn);
		},
	};
});