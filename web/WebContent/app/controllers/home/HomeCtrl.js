'use strict';
/**
 * DailyDevotional Controller
 */
var ibpnhControllers = angular.module('ibpnhControllers');

ibpnhControllers.controller('HomeCtrl',['$scope', '$rootScope', 'DailyDevotionalService', '$filter','$timeout',
	function($scope, $rootScope, DailyDevotionalService, $filter,$timeout) {
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
					$timeout(function () { // You might need this timeout to be sure its run after DOM render.
						$('.ui.dropdown').dropdown();
					}, 0, false);
				},$scope.errorManager);
		}

		$scope.lastDevotionals();

	}
]);