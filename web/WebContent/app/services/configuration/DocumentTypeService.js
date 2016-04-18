'use strict';
/**
 * DocumentType Service
 */
var ibpnhModules = angular.module('services');

ibpnhModules.factory('DocumentTypeService', function(IbpnhService){
	return {
		create: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/documentType/create.json', params, successFn, errorFn);
		},
		remove: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/documentType/delete.json', params, successFn, errorFn);
		},
		modify: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/documentType/modify.json', params, successFn, errorFn);
		},
		list: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/documentType/list.json', params, successFn, errorFn);
		},
		search: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/documentType/search.json', params, successFn, errorFn);
		}
	};
});