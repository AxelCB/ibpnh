'use strict';
/**
 * DailyDevotional Controller
 */
var ibpnhControllers = angular.module('ibpnhControllers');

ibpnhControllers.controller('DailyDevotionalCtrl',['$scope', '$rootScope','BlobstoreService',
		'DailyDevotionalService', '$filter','Upload','$routeParams',
	function($scope, $rootScope, BlobstoreService, DailyDevotionalService, $filter,Upload,$routeParams) {
		$scope.dailyDevotional = {};
		$scope.editing = false;
		$scope.dailyDevotionalDate = new Date();
		$scope.uploadUrl="";
		$scope.imageFile;
	
		var paginationHelper;

		$scope.create = function() {
			if ($scope.myForm.$valid) {
				$scope.dailyDevotional.date = DateFormatHelper.fullDateTimeToString($scope.dailyDevotionalDate);
				
				BlobstoreService.uploadUrl("/dailyDevotional/create.json",
					function(response){
						$scope.uploadUrl=response;
						$scope.dailyDevotional.imageUrl="sinImagen";

						DailyDevotionalService.create(
							$scope.uploadUrl,
							$scope.dailyDevotional,
							$scope.imageFile,
							function(response) {
								if (response.ok) {
									// dailyDevotional successfully created
									var par = JSON.parse(response.data);

									$rootScope.keepMessages = true;
									$scope.initialize();
								}

							}, $scope.errorManager);
					}, $scope.errorManager);
			} else {
				$rootScope.showErrorMessage(i18n.t('commissionSchema.validation.numberFormat'), true);
			}
		};
		
		$scope.list = function(pagination) {
			DailyDevotionalService.list(pagination, function(response) {
				if (response.ok) {
					var responseObject = JSON.parse(response.data);

					if (responseObject.page) {
						$scope.dailyDevotionals = responseObject.items;
						paginationHelper.extendCallback(responseObject);
					} else {
						$scope.dailyDevotionals = responseObject;
					}
				}
			}, $rootScope.manageError);
		};
	
		$scope.edit = function(dailyDevotional) {
			$scope.editing = true;
	
			$scope.dailyDevotional =  dailyDevotional;
	
			$scope.dailyDevotionals.splice($scope.dailyDevotionals.indexOf($scope.dailyDevotional), 1);
		};
	
		$scope.modify = function() {
			if ($scope.myForm.$valid) {
				$scope.dailyDevotional.date = DateFormatHelper.fullDateTimeToString($scope.dailyDevotionalDate);
				DailyDevotionalService.modify($scope.dailyDevotional, function(response) {
					if (response.ok) {
						// dailyDevotional successfully edited
						var par = JSON.parse(response.data);
		
						$scope.dailyDevotionals.push(par);
		
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
	
				$scope.dailyDevotional = null;
				$scope.editing = false;
			}
	
			$scope.initialize();
		};
	
		$scope.remove = function(dailyDevotional) {
			DailyDevotionalService.remove(dailyDevotional, function(response) {
				if (response.ok) {
					$scope.dailyDevotionals.splice($scope.dailyDevotionals.indexOf(dailyDevotional),
							1);
				}
			}, this.errorManager);
		};
	
		$scope.search = function(pagination) {
			var data;
			
			if (pagination) {
				pagination.vo = $scope.dailyDevotional;
				data = pagination;
			} else {
				data = $scope.dailyDevotional;
			}
			
			DailyDevotionalService.search(data, function(response) {
				if (response.ok) {
					var responseObject = JSON.parse(response.data);
					
					if (responseObject.page) {
						$scope.dailyDevotionals = responseObject.items;
						paginationHelper.extendCallback(responseObject);
					} else {
						$scope.dailyDevotionals = responseObject;
					}
				}
			}, $rootScope.manageError);
		};

		$scope.find = function() {
			DailyDevotionalService.find($scope.dailyDevotional, function(response) {
				if (response.ok) {
					$scope.dailyDevotional = JSON.parse(response.data);
				}
			}, $rootScope.manageError);
		};
	
		paginationHelper = PaginationHelper($scope, 'dailyDevotionalNameSpace', true);
		
		$scope.initialize = function() {
	
			$scope.editing = false;
			$scope.dailyDevotional = null;

				//if ($rootScope.canAccess('/configuration/dailyDevotional:listDailyDevotional')) {
			if($routeParams.devotionalId){
				$scope.dailyDevotional={'id':$routeParams.devotionalId};
				$scope.find();
			}else{
				$scope.list();
			}
			//}
			$rootScope.areErrorMessages = false;
		};
		$scope.initialize();

	}
]);