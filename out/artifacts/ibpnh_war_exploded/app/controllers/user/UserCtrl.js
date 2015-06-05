'use strict';
/**
 * User Controller
 */
var ibpnhControllers = angular.module('ibpnhControllers');

ibpnhControllers.controller('UserCtrl',['$scope', '$rootScope', 'UserService', '$filter',
	function($scope, $rootScope, UserService, $filter) {
		$scope.user = {};
		$scope.users = [];
		$scope.editing = false;
	
		var paginationHelper;
		
		$scope.create = function() {
			if ($scope.myForm.$valid) {
				UserService.create($scope.user, function(response) {
					if (response.ok) {
						// user successfully created
						var par = JSON.parse(response.data);
		
						$rootScope.keepMessages = true;
						$scope.initialize();
					}
		
				}, $scope.errorManager);
			} else {
				$rootScope.showErrorMessage(i18n.t('commissionSchema.validation.numberFormat'), true);
			}
		};
		
		$scope.list = function(pagination) {
			UserService.list(pagination, function(response) {
				if (response.ok) {
					var responseObject = JSON.parse(response.data);

					if (responseObject.page) {
						$scope.users = responseObject.items;
						paginationHelper.extendCallback(responseObject);
					} else {
						$scope.users = responseObject;
					}
				}
			}, $rootScope.manageError);
			//$.ajax({
			//	url: "/user/list.json",
			//	type: "post",
			//	data: pagination,
			//	dataType: 'json',
			//	contentType:"text/json; charset=utf-8",
			//	beforeSend : function(xhr) {
			//		// set header
			//		xhr.setRequestHeader("Authorization", $rootScope.loggedUser.token);
			//	},
			//	success: function (data) {
			//		alert(data);
			//	},
			//});
		};
	
		$scope.edit = function(user) {
			$scope.editing = true;
	
			$scope.user =  user;
	
			$scope.users.splice($scope.users.indexOf($scope.user), 1);
		};
	
		$scope.modify = function() {
			if ($scope.myForm.$valid) {
				UserService.modify($scope.user, function(response) {
					if (response.ok) {
						// user successfully edited
						var par = JSON.parse(response.data);
		
						$scope.users.push(par);
		
						$rootScope.keepMessages = true;
						$scope.initialize();
					}
				}, this.errorManager);
			} else {
				$rootScope.showErrorMessage(i18n.t('commissionSchema.validation.numberFormat'), true);
			}
		};
	
		$scope.clean = function() {
			$scope.initialize();
		};
	
		$scope.cancel = function() {
			if ($scope.editing) {
	
				$scope.user = null;
				$scope.editing = false;
			}
	
			$scope.initialize();
		};
	
		$scope.remove = function(user) {
			UserService.remove(user, function(response) {
				if (response.ok) {
					$scope.users.splice($scope.users.indexOf(user),
							1);
				}
			}, this.errorManager);
		};
	
		$scope.search = function(pagination) {
			var data;
			
			if (pagination) {
				pagination.vo = $scope.user;
				data = pagination;
			} else {
				data = $scope.user;
			}
			
			UserService.search(data, function(response) {
				if (response.ok) {
					var responseObject = JSON.parse(response.data);
					
					if (responseObject.page) {
						$scope.users = responseObject.items;
						paginationHelper.extendCallback(responseObject);
					} else {
						$scope.users = responseObject;
					}
				}
			}, $rootScope.manageError);
		};
	
		paginationHelper = PaginationHelper($scope, 'userNameSpace', true);
		
		$scope.initialize = function() {
	
			$scope.editing = false;
			$scope.user = null;
	
			if ($rootScope.canAccess('/configuration/user:listUser')) {
				$scope.list();
			}
	
			$rootScope.areErrorMessages = false;
		};
		$scope.initialize();
	}
]);