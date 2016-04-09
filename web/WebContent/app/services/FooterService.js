'use strict';
/**
 * Footer Service
 */
var ibpnhModules = angular.module('services');

ibpnhModules.factory('FooterService', ['IbpnhService', function(IbpnhService) {
	
	return {
		getFooterInformation: function (successFn, errorFn) {
			return IbpnhService.post('/footer/getFooterInformation.json', "", successFn, errorFn);
		},
		getSystemName: function (successFn, errorFn) {
			return IbpnhService.post('/footer/getSystemName.json', "", successFn, errorFn);
		}
	};
}]);