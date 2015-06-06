'use strict';
/**
 * DailyDevotional Controller
 */
var ibpnhControllers = angular.module('ibpnhControllers');

ibpnhControllers.controller('HomeCtrl',['$scope', '$rootScope', 'DailyDevotionalService', '$filter',
	function($scope, $rootScope, DailyDevotionalService, $filter) {
		$scope.dailyDevotionals = {};
		$scope.todayDate = new Date();

		$scope.lastDevotionals = function(){
			DailyDevotionalService.lastDevotionals(
				{'amount':2,
				'today':DateFormatHelper.dateToString($scope.todayDate)},
				function(response){
					if(response.ok){
						$scope.dailyDevotionals = JSON.parse(response.data);
					}else{

					}
				},$scope.errorManager);
		}

	}
]);