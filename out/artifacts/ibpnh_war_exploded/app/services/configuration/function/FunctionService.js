
'use strict';
/**
 * Function Service
 */
var ibpnhModules = angular.module('services');

ibpnhModules.factory('FunctionService', function(IbpnhService){
	return {
		create: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/function/create.json', params, successFn, errorFn);
		},
		remove: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/function/delete.json', params, successFn, errorFn);
		},
		modify: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/function/modify.json', params, successFn, errorFn);
		},
		list: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/function/list.json', params, successFn, errorFn);
		},
		search: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/function/search.json', params, successFn, errorFn);
		}
	};
});