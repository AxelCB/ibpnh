'use strict';
/**
 * Document Type Controller
 */
var ibpnhControllers = angular.module('ibpnhControllers');

ibpnhControllers.controller('DocumentTypeCtrl',['$scope', '$rootScope', 'DocumentTypeService', '$filter',
	function($scope, $rootScope, DocumentTypeService, $filter) {
		$scope.documentType = {};
		$scope.documentTypes = [];
		$scope.editing = false;
	
		var paginationHelper;
		
		$scope.create = function() {
			if ($scope.myForm.$valid) {
				DocumentTypeService.create($scope.documentType, function(response) {
					if (response.ok) {
						// documentType successfully created
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
			DocumentTypeService.list(pagination, function(response) {
				if (response.ok) {
					var responseObject = JSON.parse(response.data);
					
					if (responseObject.page) {
						$scope.documentTypes = responseObject.items;
						paginationHelper.extendCallback(responseObject);
					} else {
						$scope.documentTypes = responseObject;
					}	
				}
			}, $rootScope.manageError);
		};
	
		$scope.edit = function(documentType) {
			$scope.editing = true;
	
			$scope.documentType =  documentType;
	
			$scope.documentTypes.splice($scope.documentTypes.indexOf($scope.documentType), 1);
		};
	
		$scope.modify = function() {
			if ($scope.myForm.$valid) {
				DocumentTypeService.modify($scope.documentType, function(response) {
					if (response.ok) {
						// documentType successfully edited
						var par = JSON.parse(response.data);
		
						$scope.documentTypes.push(par);
		
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
	
				$scope.documentType = null;
				$scope.editing = false;
			}
	
			$scope.initialize();
		};
	
		$scope.remove = function(documentType) {
			DocumentTypeService.remove(documentType, function(response) {
				if (response.ok) {
					$scope.documentTypes.splice($scope.documentTypes.indexOf(documentType),
							1);
				}
			}, this.errorManager);
		};
	
		$scope.search = function(pagination) {
			var data;
			
			if (pagination) {
				pagination.vo = $scope.documentType;
				data = pagination;
			} else {
				data = $scope.documentType;
			}
			
			DocumentTypeService.search(data, function(response) {
				if (response.ok) {
					var responseObject = JSON.parse(response.data);
					
					if (responseObject.page) {
						$scope.documentTypes = responseObject.items;
						paginationHelper.extendCallback(responseObject);
					} else {
						$scope.documentTypes = responseObject;
					}
				}
			}, $rootScope.manageError);
		};
	
		paginationHelper = PaginationHelper($scope, 'documentTypeNameSpace', true);
		
		$scope.initialize = function() {
	
			$scope.editing = false;
			$scope.documentType = null;
	
			if ($rootScope.canAccess('/configuration/documentType:listDocumentType')) {
				$scope.list();
			}
	
			$rootScope.areErrorMessages = false;
		};
		$scope.initialize();
	}
]);