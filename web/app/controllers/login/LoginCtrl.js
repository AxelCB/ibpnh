'use strict';
/**
 * Login Controller
 */
var ibpnhControllers = angular.module('ibpnhControllers');

ibpnhControllers.controller('LoginCtrl',['$scope', '$rootScope', 'LoginService', '$location', '$cookieStore',
	function($scope, $rootScope, LoginService, $location, $cookieStore) {
		// watches the $rootScope.readyToConstructTooltip flag, in order to construct the tooltip
		// when we are surde that the i18n system is initialized
		//var stopWatching = $rootScope.$watch(function() { return $rootScope.readyToConstructTooltip; }, function() {
		//	if ($rootScope.readyToConstructTooltip) {
		//		$(document).ready(function() {
		//			$(".glyphicon-info-sign").tooltip({html: true, title: '<span style="text-align:justify;">' + i18n.t('login.remember.warning') + '</span>'});
		//		});
		//		// stops the watch
		//		stopWatching();
		//	}
		//});
	
		$scope.userCredentials = {};
		$scope.rememberMe = null;
		$scope.registrationEnabled = false;
		
		$scope.processMessages = function(response) {
			$rootScope.messages = response.messages;
			$rootScope.areErrorMessages = !response.ok;
		};
		
		$scope.login = function() {
			$rootScope.dim();
			$scope.userCredentials = {
				username: $("#username").val(),
				password: $("#password").val()
			};
			LoginService.login($scope.userCredentials,
				function (response) {
					if (response.ok) {
						// performs the login on the client
						$rootScope.performLogin(JSON.parse(response.data), $scope.rememberMe, true);
					}
					$rootScope.unDim();
				}, $rootScope.errorManager);
		};
		
		$scope.register = function() {
			// go to register page
			$rootScope.go("/register");
		};
		
		$scope.recoverPassword = function() {
			// go to recover page
			$rootScope.go("/recoverPassword");
		};
		
		$rootScope.keepMessages = true;
		LoginService.registrationEnabled(function(res) {
			$scope.registrationEnabled = JSON.parse(res.data);
		}, $rootScope.manageError);
	}
]);
