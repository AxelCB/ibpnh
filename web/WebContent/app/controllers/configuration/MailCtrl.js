'use strict';
/**
 * Mail Controller
 */
var ibpnhControllers = angular.module('ibpnhControllers');

ibpnhControllers.controller('MailCtrl',['$scope', '$rootScope', 'MailService', '$filter',
	function($scope, $rootScope, MailService, $filter) {
	
		$scope.mail = {};
		$scope.mails = [];
		$scope.editing = false;
		$scope.repetitions = [];
		$scope.fxs = [];
		$scope.testResult = {};
	
		var paginationHelper;
		
		 $scope.editorOptions = {
	        lineWrapping : true,
	        lineNumbers: true,
	        mode: 'htmlmixed',
	        htmlMode: true
	    };
		
		MailService.listRepetitions(function(response) {
			if (response.ok) {
				var repetitions = JSON.parse(response.data);
				$scope.repetitions = [];
				for (var i = 0; i < repetitions.length; i++) {
					$scope.repetitions.push({label: i18n.t('mail.repetition.' + repetitions[i]),name: repetitions[i]});
				};
			}
		}, $rootScope.manageError);
		
		MailService.listFx(function(response) {
			if (response.ok) {
				$scope.fxs = JSON.parse(response.data);
			}
		}, $rootScope.manageError);
		
		$scope.create = function() {
			if ($scope.mail && $scope.sendTimestampObject) {
				$scope.mail.sendTimestamp = $rootScope.fullDateTimeToString($scope.sendTimestampObject);
			}
			
			MailService.create($scope.mail, function(response) {
				if (response.ok) {
	
					// mail successfully created
					var par = JSON.parse(response.data);
					
					$('#sendTimestamp').data("DateTimePicker").setDate(null);
					$rootScope.keepMessages = true;
					$scope.initialize();
	
				}
	
			}, $scope.errorManager);
		};
		
		$scope.list = function(pagination) {
			MailService.list(pagination, function(response) {
				if (response.ok) {
					var responseObject = JSON.parse(response.data);
					
					if (responseObject.page) {
						$scope.mails = responseObject.items;
						paginationHelper.extendCallback(responseObject);
					} else {
						$scope.mails = responseObject;
					}	
				}
			}, $rootScope.manageError);
		};
	
		$scope.edit = function(mail) {
			$scope.editing = true;
	
			$scope.mail =  mail;
	
			if ($scope.mail.sendTimestamp) {
				$scope.sendTimestampObject = $rootScope.stringToDateTime($scope.mail.sendTimestamp);
				$('#sendTimestamp').data("DateTimePicker").setDate($scope.sendTimestampObject);
			} else {
				$scope.sendTimestampObject = null;
				$('#sendTimestamp').data("DateTimePicker").setDate(null);
			}
			
			$scope.modeChange();
			$scope.mails.splice($scope.mails.indexOf($scope.mail), 1);
		};
	
		$scope.modify = function() {
			if ($scope.mail && $scope.sendTimestampObject) {
				$scope.mail.sendTimestamp = $rootScope.fullDateTimeToString($scope.sendTimestampObject);
			}
			
			MailService.modify($scope.mail, function(response) {
				if (response.ok) {
					var par = JSON.parse(response.data);
					
					$('#sendTimestamp').data("DateTimePicker").setDate(null);
					$rootScope.keepMessages = true;
					$scope.initialize();
				}
			}, this.errorManager);
		};
	
		$scope.clean = function() {
			$scope.editing = false;
			$scope.mail = {};
			$('#sendTimestamp').data("DateTimePicker").setDate(null);
			$scope.initialize();
		};
	
		$scope.cancel = function() {
			if ($scope.editing) {
	
				$scope.mail = null;
				$scope.editing = false;
				$('#sendTimestamp').data("DateTimePicker").setDate(null);
			}
	
			$scope.initialize();
		};
	
		$scope.remove = function(mail) {
			MailService.remove(mail, function(response) {
				if (response.ok) {
					$scope.mails.splice($scope.mails.indexOf(mail),
							1);
				}
			}, this.errorManager);
		};
	
		$scope.search = function(pagination) {
			var data;
			
			if (pagination) {
				pagination.vo = $scope.mail;
				data = pagination;
			} else {
				data = $scope.mail;
			}
			
			MailService.search(data, function(response) {
				if (response.ok) {
					var responseObject = JSON.parse(response.data);
					
					if (responseObject.page) {
						$scope.mails = responseObject.items;
						paginationHelper.extendCallback(responseObject);
					} else {
						$scope.mails = responseObject;
					}
				}
			}, $rootScope.manageError);
		};
		
		$scope.trigger = function(mail) {
			MailService.trigger(mail, function(response) {
				//does nothing
			}, $rootScope.manageError);
		};
		
		$scope.fxExposedParams = function() {
			if ($scope.mail && $scope.mail.mailFxClass) {
				for (var i = 0; i < $scope.fxs.length; i++) {
					if ($scope.fxs[i].classCanonicalName == $scope.mail.mailFxClass) {
						var params = $scope.fxs[i].exposedParameters + "";
						params = params.replace(/\[/g,'');
						params = params.replace(/]/g,'');
						params = params.replace(/"/g,'');
						params = params.replace(/,/g,', ');
						return params;
					}
				}
			} else {
				return "";
			}
		};
	
		paginationHelper = PaginationHelper($scope, 'mailNameSpace', true);
		
		$scope.initialize = function() {
	
			$scope.mail = {};
			$scope.editing = false;
	
			if ($rootScope.canAccess('/configuration/mail:listMail')) {
				$scope.list();
			}
	
			$rootScope.areErrorMessages = false;
		};
		
		$scope.modeChange = function() {
			if ($scope.mail && $scope.mail.bodyJavascript) {
				$("#body textarea").data('CodeMirrorInstance').setOption("mode", "text/javascript");
			} else {
				$("#body textarea").data('CodeMirrorInstance').setOption("mode", "htmlmixed");
			}
		};
		
		$scope.testMail = function(mailVo) {
			MailService.testMail(mailVo, function(response) {
				if (response.ok) {
					$scope.testResult = JSON.parse(response.data);
					$("#testMailModal").modal("show");
					$("#testMailBody").html($scope.testResult.mailBody);
					$("#testMailFooter").html($scope.testResult.mailFooter);
				}
			}, $rootScope.manageError);
		};
		
		$scope.initialize();
	}
]);