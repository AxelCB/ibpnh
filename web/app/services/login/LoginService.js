'use strict';
/**
 * Login Service
 */
var ibpnhModules = angular.module('services');

ibpnhModules.factory('LoginService', ['IbpnhService', '$rootScope', '$cookieStore', '$location',
    function(IbpnhService, $rootScope, $cookieStore, $location) {
	
	function _logout() {
		$rootScope.permissions = [];
		$rootScope.loggedUser = null;
		$cookieStore.remove("loggedUser");
		$cookieStore.remove("userCredentials");
		//we redirect to the /login
		$rootScope.unDim();
		$location.path("/login");
	};
	
	return {
		login: function (params, successFn, errorFn) {
			//login method does not requires the Authorization header to be present
			return IbpnhService.post('/login/login.json', params, successFn, errorFn);
		},
		logout: function () {
			$rootScope.dim();
			return IbpnhService.authPost('/login/logout.json', "", _logout, _logout);
		},
		getLoggedUser: function(successFn, errorFn) {
			return IbpnhService.authPost('/login/getLoggedUser.json', "", successFn, errorFn);
		},
		changePassword: function (params, successFn, errorFn) {
			return IbpnhService.authPost('/login/changePassword.json', params, successFn, errorFn);
		},
		registrationEnabled: function(successFn, errorFn) {
			return IbpnhService.post('/login/registrationEnabled.json', '', successFn, errorFn);
		}
	};
}]);