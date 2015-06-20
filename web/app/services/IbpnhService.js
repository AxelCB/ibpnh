var server = angular.module('services', []);
/**
 * Main Universe Service Facade
 */
server.factory('IbpnhService', ['$http', '$rootScope', '$location', '$cookieStore', '$injector','Upload',
	function($http, $rootScope, $location,  $cookieStore, $injector,Upload) {

		$rootScope.reLoginCount = 0;
		$rootScope.ibpnhCoreServiceUrl = "/";
		//$rootScope.universeCoreServiceUrl = universeServiceUrl;

		var wrapperFunction = function(originalFunction) {

			return function(response) {
				if ($rootScope.keepMessages) {

					if ($rootScope.keepMessagesCount && $rootScope.keepMessagesCount > 0) {
						$rootScope.keepMessagesCount--;

						if ($rootScope.keepMessagesCount == 0) {
							$rootScope.keepMessages = false;
						}
					} else {
						$rootScope.keepMessages = false;
					}
				} else {
					$rootScope.messages = [];
					$rootScope.messages = response.messages;
					$rootScope.areErrorMessages = !response.ok;
				}

				if (response.action) {
					$rootScope.messages = response.messages;
					$rootScope.areErrorMessages = !response.ok;

					switch (response.action) {
						case "relogin":

							//refreshes logged state and redirects to the /login
							$rootScope.permissions = [];
							$rootScope.loggedUser = null;
							$cookieStore.remove("loggedUser");

							var userCredentials = $cookieStore.get("userCredentials");
							if ($rootScope.reLoginCount == 0 && userCredentials) {

								$rootScope.reLoginCount++;

								var loginService = $injector.get('LoginService');

								loginService.login(userCredentials,
									function (secondResponse) {
										if (secondResponse.ok) {
											$rootScope.performLogin(JSON.parse(response.data), null, false);
										} else {
											$rootScope.unDim();
											$cookieStore.remove("userCredentials");
											$location.path("/login");
										}
									}, function() {
										$rootScope.unDim();
										$cookieStore.remove("userCredentials");
										$location.path("/login");
									}
								);

							} else {
								$rootScope.reLoginCount = 0;
								//TODO: same code as in LoginService.js::_logout

								//we redirect to the /login
								$rootScope.unDim();
								$location.path("/login");
							}

							break;
						case "refreshUser":
							//assumes that in the body comes the user, refreshes it, the permissions and
							//redirects to the /
							$rootScope.performLogin(JSON.parse(response.data), null, false);
							break;
					}
				} else {
					originalFunction(response);
				}
			};
		};

		return {
			/*
			 * Posts The Data As JSON, and expects JSON (without authorization header)
			 */
			post: function(url, params , successFn, errorFn) {
				$http({method: 'POST', url: ibpnhServiceUrl + url, data: params,
					headers: {
						'Content-Type': 'text/json;charset=utf8',
						 'Accept':'text/json;charset=utf8'
					}}).
					success(wrapperFunction(successFn)).
					error(wrapperFunction(errorFn));
			},
			/*
			 * Posts The Data As JSON, and expects JSON (WITH authorization header)
			 */
			authPost: function(url, params , successFn, errorFn) {
				$http({method: 'POST', url: ibpnhServiceUrl + url, data: params,
					headers: {
						'Content-Type': 'text/json;charset=utf8',
						'Authorization': $rootScope.loggedUser.token,
						'Accept':'text/json;charset=utf8'
					}}).
					success(wrapperFunction(successFn)).
					error(wrapperFunction(errorFn));
			},
			/*
			 * Posts The Data As XML to a WS API, and expects XML (without authorization header)
			 */
			postWs: function(params, successFn, errorFn) {
				$http({method: 'POST', url: ibpnhServiceUrl + '/ws', data: params,
					headers: {
						'Content-Type': 'text/xml; charset=utf8',
						 'Accept':'text/json;charset=utf8'
					}}).
					success(wrapperFunction(successFn)).
					error(wrapperFunction(errorFn));
			},
			/*
			 * Posts The Data As XML to a WS API, and expects XML (WITH authorization header)
			 */
			syncAuthPost: function(url, params, promiseFn) {
				return $http({method: 'POST', url: ibpnhServiceUrl + url, data: params,
					headers: {
						'Content-Type': 'text/xml;charset=utf8',
						'Authorization': $rootScope.loggedUser.token,
						 'Accept':'text/json;charset=utf8'
					}}).then(promiseFn);
			},
			/*
			 * Posts The Data As Form Encoded, and expects JSON (without authorization header)
			 */
			postEncoded: function(url, params , successFn, errorFn) {
				$http({method: 'POST', url: ibpnhServiceUrl + url, data: $.param(params),
					headers: {
						'Content-Type': 'application/x-www-form-urlencoded;charset=utf8',
						 'Accept':'text/json;charset=utf8'
					}}).
					success(wrapperFunction(successFn)).
					error(wrapperFunction(errorFn));
			},
			/*
			 * Posts The Data As JSON, and expects JSON (WITH authorization header)
			 */
			fileAuthPost: function(url,params,file,successFn,errorFn) {
				Upload.upload({
						method: 'POST',
						url: url,
						data: params,
						file: file,
						headers: {
							'Content-Type': 'multipart/form-data;charset=utf8',
							'Authorization': $rootScope.loggedUser.token,
							'Accept':'text/json;charset=utf8'
						}
					}).progress(function (evt) {
						var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
						console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
					}).
					success(wrapperFunction(successFn)).
					error(wrapperFunction(errorFn));


				//$http({method: 'POST', url: ibpnhServiceUrl + url, data: $.param(params),
				//	headers: {
				//		'Content-Type': 'multipart/form-data',
				//		'Accept':'text/json;charset=utf8'
				//	}}).
				//	success(wrapperFunction(successFn)).
				//	error(wrapperFunction(errorFn));
			}
		};
	}
]);