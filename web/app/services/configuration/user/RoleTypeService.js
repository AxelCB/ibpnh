'use strict';
/**
 * Role Type Service
 */
var ibpnhModules = angular.module('services');

ibpnhModules.factory('RoleTypeService', function(IbpnhService) {
	return {
		create: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/roleType/create.json', params, successFn, errorFn);
		},
		remove: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/roleType/delete.json', params, successFn, errorFn);
		},
		modify: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/roleType/modify.json', params, successFn, errorFn);
		},
		list: function (pagination, successFn, errorFn) {
			return IbpnhService.authPost('/roleType/list.json', pagination, successFn, errorFn);
		},
		listFunctions: function (successFn, errorFn) {
			return IbpnhService.authPost('/roleType/listFunctions.json', "", successFn, errorFn);
		},
		listRoleTypes: function (successFn, errorFn) {
			return IbpnhService.authPost('/roleType/listRoleTypes.json', "", successFn, errorFn);
		},
		listRoleTypeEnums: function (successFn, errorFn) {
			return IbpnhService.authPost('/roleType/listRoleTypeEnums.json', "", successFn, errorFn);
		},
		listMenuOrders: function (successFn, errorFn) {
			return IbpnhService.authPost('/roleType/listMenuOrders.json', "", successFn, errorFn);
		},
		search: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/roleType/search.json', params, successFn, errorFn);
		}
	};
});