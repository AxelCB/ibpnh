'use strict';
/**
 * Mail Service
 */
var ibpnhModules = angular.module('services');

ibpnhModules.factory('MailService', function(IbpnhService){
	return {
		create: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/mail/create.json', params, successFn, errorFn);
		},
		remove: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/mail/delete.json', params, successFn, errorFn);
		},
		modify: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/mail/modify.json', params, successFn, errorFn);
		},
		list: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/mail/list.json', params, successFn, errorFn);
		},
		search: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/mail/search.json', params, successFn, errorFn);
		},
		listRepetitions: function (successFn, errorFn) {
			return IbpnhService.authPost('/mail/listRepetitions.json', "", successFn, errorFn);
		},
		trigger: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/mail/trigger.json', params, successFn, errorFn);
		},
		listFx: function (successFn, errorFn) {
			return IbpnhService.authPost('/mail/listFx.json', "", successFn, errorFn);
		},
		testMail: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/mail/testMail.json', params, successFn, errorFn);
		}
	};
});