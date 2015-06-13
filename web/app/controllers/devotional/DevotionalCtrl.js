'use strict';
/**
 * DailyDevotional Controller
 */
var ibpnhControllers = angular.module('ibpnhControllers');

ibpnhControllers.controller('DevotionalCtrl',['$scope', '$rootScope', 'DailyDevotionalService', '$filter',
	function($scope, $rootScope, DailyDevotionalService, $filter) {
		$scope.dailyDevotionals = [];
	
		var paginationHelper;

		$scope.list = function(pagination) {
			DailyDevotionalService.listView(pagination, function(response) {
				if (response.ok) {
					var responseObject = JSON.parse(response.data);

					if (responseObject.page) {
						$scope.dailyDevotionals = responseObject.items;
						paginationHelper.extendCallback(responseObject);
					} else {
						$scope.dailyDevotionals = responseObject;
					}
					$filter('orderBy')($scope.dailyDevotionals, 'date');
				}
			}, $rootScope.manageError);
		};

		paginationHelper = PaginationHelper($scope, 'dailyDevotionalNameSpace', true);

		$scope.list();

	}
]);