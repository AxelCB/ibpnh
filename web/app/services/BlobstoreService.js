'use strict';
/**
 * DailyDevotional Service
 */
var ibpnhModules = angular.module('services');

ibpnhModules.factory('BlobstoreService', function(IbpnhService){
	return {
		uploadUrl: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/blobstore/uploadUrl.json', params, successFn, errorFn);
		},
		imageServingUrl: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/blobstore/serveImageUrl.json', params, successFn, errorFn);
		},
	};
});