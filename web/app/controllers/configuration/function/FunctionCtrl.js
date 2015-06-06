'use strict';
/**
 * Function Controller
 */
var ibpnhControllers = angular.module('ibpnhControllers');

ibpnhControllers.controller('FunctionCtrl',['$scope', '$rootScope', 'FunctionService', '$filter',
	function($scope, $rootScope, FunctionService, $filter) {
		$scope.fx = {};
		$scope.fxs = [];
		$scope.editing = false;
	
		var paginationHelper;
		
		$scope.create = function() {
			if ($scope.myForm.$valid) {
				FunctionService.create($scope.fx, function(response) {
					if (response.ok) {
						// fx successfully created
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
			FunctionService.list(pagination, function(response) {
				if (response.ok) {
					var responseObject = JSON.parse(response.data);

					if (responseObject.page) {
						$scope.fxs = responseObject.items;
						paginationHelper.extendCallback(responseObject);
					} else {
						$scope.fxs = responseObject;
					}
				}
			}, $rootScope.manageError);
			//$.ajax({
			//	url: "/fx/list.json",
			//	type: "post",
			//	data: pagination,
			//	dataType: 'json',
			//	contentType:"text/json; charset=utf-8",
			//	beforeSend : function(xhr) {
			//		// set header
			//		xhr.setRequestHeader("Authorization", $rootScope.loggedFunction.token);
			//	},
			//	success: function (data) {
			//		alert(data);
			//	},
			//});
		};
	
		$scope.edit = function(fx) {
			$scope.editing = true;
	
			$scope.fx =  fx;
	
			$scope.fxs.splice($scope.fxs.indexOf($scope.fx), 1);
		};
	
		$scope.modify = function() {
			if ($scope.myForm.$valid) {
				FunctionService.modify($scope.fx, function(response) {
					if (response.ok) {
						// fx successfully edited
						var par = JSON.parse(response.data);
		
						$scope.fxs.push(par);
		
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
	
				$scope.fx = null;
				$scope.editing = false;
			}
	
			$scope.initialize();
		};
	
		$scope.remove = function(fx) {
			FunctionService.remove(fx, function(response) {
				if (response.ok) {
					$scope.fxs.splice($scope.fxs.indexOf(fx),
							1);
				}
			}, this.errorManager);
		};
	
		$scope.search = function(pagination) {
			var data;
			
			if (pagination) {
				pagination.vo = $scope.fx;
				data = pagination;
			} else {
				data = $scope.fx;
			}
			
			FunctionService.search(data, function(response) {
				if (response.ok) {
					var responseObject = JSON.parse(response.data);
					
					if (responseObject.page) {
						$scope.fxs = responseObject.items;
						paginationHelper.extendCallback(responseObject);
					} else {
						$scope.fxs = responseObject;
					}
				}
			}, $rootScope.manageError);
		};
	
		//paginationHelper = PaginationHelper($scope, 'fxNameSpace', true);
		
		$scope.initialize = function() {
	
			$scope.editing = false;
			$scope.fx = null;
	
			if ($rootScope.canAccess('/configuration/fx:listFunction')) {
				$scope.list();
			}
	
			$rootScope.areErrorMessages = false;
		};
		$scope.initialize();
	}
]);