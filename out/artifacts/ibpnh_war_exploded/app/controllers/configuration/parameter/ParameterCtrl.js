'use strict';
/**
 * Parameter Controller
 */
var ibpnhControllers = angular.module('ibpnhControllers');

ibpnhControllers.controller('ParameterCtrl',['$scope', '$rootScope', 'ParameterService', '$filter',
	function($scope, $rootScope, ParameterService, $filter) {
		$scope.parameter = {};
		$scope.roleType = {};
		$scope.parameters = [];
		$scope.editing = false;
		$scope.parameterTypeEnumsList=[];
	
		var paginationHelper;
		
		$scope.create = function() {
			if ($scope.myForm.$valid) {
				ParameterService.create($scope.parameter,
				function(response) {
					if (response.ok) {
						// parameter successfully created
						var par = JSON.parse(response.data);
		
						$rootScope.keepMessages = true;
						$scope.initialize();
					}
		
				}, $scope.errorManager);
			}
		};
		
		$scope.list = function(pagination) {
			ParameterService.list(pagination, function(response) {
				if (response.ok) {
					var responseObject = JSON.parse(response.data);

					if (responseObject.page) {
						$scope.parameters = responseObject.items;
						paginationHelper.extendCallback(responseObject);
					} else {
						$scope.parameters = responseObject;
					}
				}
			}, $rootScope.manageError);
		};
	
		$scope.edit = function(parameter) {
			$scope.editing = true;
	
			$scope.parameter =  parameter;
	
			$scope.parameters.splice($scope.parameters.indexOf($scope.parameter), 1);
		};
	
		$scope.modify = function() {
			if ($scope.myForm.$valid) {
				ParameterService.modify($scope.parameter, function(response) {
					if (response.ok) {
						// parameter successfully edited
						var par = JSON.parse(response.data);
		
						$scope.parameters.push(par);
		
						$rootScope.keepMessages = true;
						$scope.initialize();
					}
				}, this.errorManager);
			}
		};
	
		$scope.clean = function() {
			$scope.initialize();
		};
	
		$scope.cancel = function() {
			if ($scope.editing) {
	
				$scope.parameter = null;
				$scope.editing = false;
			}
	
			$scope.initialize();
		};
	
		$scope.remove = function(parameter) {
			ParameterService.remove(parameter, function(response) {
				if (response.ok) {
					$scope.parameters.splice($scope.parameters.indexOf(parameter),
							1);
				}
			}, this.errorManager);
		};
	
		$scope.search = function(pagination) {
			var data;
			
			if (pagination) {
				pagination.vo = $scope.parameter;
				data = pagination;
			} else {
				data = $scope.parameter;
			}
			
			ParameterService.search(data, function(response) {
				if (response.ok) {
					var responseObject = JSON.parse(response.data);
					
					if (responseObject.page) {
						$scope.parameters = responseObject.items;
						paginationHelper.extendCallback(responseObject);
					} else {
						$scope.parameters = responseObject;
					}
				}
			}, $rootScope.manageError);
		};
	
		paginationHelper = PaginationHelper($scope, 'parameterNameSpace', true);
		
		$scope.initialize = function() {
	
			$scope.editing = false;
			$scope.parameter = {'fixed':true,'global':true};
			if($scope.parameterTypeEnumsList.length>0){
				$scope.parameter.type=parameterTypeEnumsList[0];
			}

				//if ($rootScope.canAccess('/configuration/parameter:listParameter')) {
			$scope.list();
			//}
			$rootScope.areErrorMessages = false;
		};
		$scope.initialize();

		ParameterService.listParameterTypeForCreation(function(response) {
			if (response.ok) {
				$scope.parameterTypeEnumsList = JSON.parse(response.data);
			}
		}, $rootScope.errorManager);
	}
]);