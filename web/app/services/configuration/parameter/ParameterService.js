'use strict';
/**
 * Parameter Service
 */
var ibpnhModules = angular.module('services');

ibpnhModules.factory('ParameterService', function(IbpnhService){
	return {
		create: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/parameter/create.json', params, successFn, errorFn);
		},
		remove: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/parameter/delete.json', params, successFn, errorFn);
		},
		modify: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/parameter/modify.json', params, successFn, errorFn);
		},
		list: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/parameter/list.json', params, successFn, errorFn);
		},
		search: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/parameter/search.json', params, successFn, errorFn);
		},
		listParameterTypeForCreation: function(successFn, errorFn) {
			return IbpnhService.authPost('/parameter/listParameterTypeForCreation.json', "", successFn, errorFn);
		},
	};
});