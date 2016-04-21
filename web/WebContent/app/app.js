/**
 * Created by Axel on 06/01/2015.
 */

'use strict';
/**
 * Main App Module. Includes all other modules.
 */

angular.module('ibpnhApp', ['ngRoute','ngAnimate','ngCookies','routes','ibpnhControllers','filters','services','directives','ngFileUpload','ui.codemirror']).
    //, 'services', 'ui.bootstrap'
    config(['$routeProvider', function($routeProvider) {
    }])
    .run(['$rootScope', '$window', '$location', '$cookieStore', 'LoginService', 'IbpnhService', 'FooterService',
        function ($rootScope, $window, $location, $cookieStore, LoginService, IbpnhService,FooterService) {
            $rootScope.lang = defaultLang;
            $rootScope.canSubscribe = canSubscribe;
            $rootScope.systemTitle = "Iglesia Bautista Pueblo Nuevo Hernandez"

            IbpnhService.post("/lang/l", "",
                function(res) {
                    if (supported.indexOf(res) != -1) {
                        var defaultLang = res;
                    }
                    $rootScope.lang = defaultLang;
                    _run();
                }, _run);

            /**
             * Returns the constructed name for an array of names.
             */
            $rootScope.constructName = function(index, array) {
                var toReturn = 'menu.';
                for (var i = 0; i < (index+1); i++) {
                    toReturn += array[i] + '.';
                }
                return toReturn + 'name';
            };

            /**
             * Translates Functions.
             */
            $rootScope.translateFunctions = function(roleFunctions) {
                $.each(roleFunctions, function() {
                    var namesArray = [];
                    if (this.menuName) {
                        namesArray.push(this.menuName);
                    }
                    if (this.submenuName) {
                        namesArray.push(this.submenuName);
                    }
                    namesArray.push(this.actionName);
                    namesArray.push(this.name);

                    var constructedNamesArray = [];
                    var translatedNamesArray = [];
                    for (var i = 0; i < namesArray.length; i++) {
                        constructedNamesArray.push($rootScope.constructName(i, namesArray));
                        translatedNamesArray.push(i18n.t(constructedNamesArray[i]));
                    }
                    this.translatedNamesArray = translatedNamesArray;
                });
            };

            $rootScope.stringStartsWithAnyStringInArray = function(string,array){
               for(var i=0;i<array.length;i++){
                   if(string.indexOf(array[i])===0){
                       return true;
                   }
               }
               return false;
            };

            $rootScope.generatePermissions = function(user) {
                var permissions = [];
                var functions = user.role.roleFunctions;
                var index;
                for (index = 0; index < user.role.roleFunctions.length; index++) {
                    if (functions[index].enabled) {
                        var menuPath = "";
                        if (functions[index].menuName) {
                            menuPath += "/" + functions[index].menuName;
                        }
                        if (functions[index].submenuName) {
                            menuPath += "/" + functions[index].submenuName;
                        }
                        if (functions[index].actionName) {
                            menuPath += "/" + functions[index].actionName;
                        }
                        if (functions[index].name) {
                            menuPath += ":" + functions[index].name;
                        }
                        if (permissions.indexOf(menuPath) == -1) {
                            permissions.push(menuPath);
                        }
                    }
                }

                return permissions;
            };

            $rootScope.$on('$routeChangeStart', function (event, next, current) {
                $("body").removeClass("modal-open");
                $(".modal-backdrop").remove();
                if (next.redirectTo == undefined) {
                    //it's a real path

                    //cleans previous messages from previous page
                    if ($rootScope.keepMessages) {
                        $rootScope.keepMessages = false;
                    } else {
                        $rootScope.messages = [];
                    }

                    var pathsWithoutAuthorization=['/home','/contacto','/devocionales','/gbcs','/ministerios','/login'];
                    var variablePathsWithoutAuthorization =['/devocionales/detalle/'];
                    var path = $location.path();
                    var auxArray = path.substr(1, path.length).split("/");
                    $rootScope.currentArray = [];
                    if (auxArray.length > 0) {
                        $rootScope.currentArray.push(auxArray[0]);
                        for (var i = 1; i < auxArray.length; i++) {
                            $rootScope.currentArray.push($rootScope.currentArray[i-1] + "." + auxArray[i]);
                        }
                    }

                    if ($.inArray(path, pathsWithoutAuthorization) > -1 || $rootScope.stringStartsWithAnyStringInArray(path,variablePathsWithoutAuthorization)){

                    } else {
                        if (($rootScope.loggedUser)){
                            //if ($rootScope.loggedUser.firstLogin && path != "/changePassword") {
                            //    next.redirectTo = "/changePassword";
                            //} else {
                            //    //REF: would validation be needed here? I think not
                            //}
                        } else {
                            next.redirectTo = "/home";
                        }
                    }
                }
            });

            //$rootScope.globalInspectorFlag = document.globalInspectorFlag;
            $rootScope.globalInspectorFlag = false;

            $rootScope.dim = function() {
                $("#processing").css("display", "block");
            };

            $rootScope.unDim = function() {
                $("#processing").css("display", "none");
            };

            $rootScope.swap = function(vector, i, j) {
                var temp = vector[i];
                vector[i] = vector[j];
                vector[j] = temp;
            };

            $rootScope.messages = [];
            $rootScope.areErrorMessages = false;
            $rootScope.keepMessages = false;
            $rootScope.permissions = [];
            $rootScope.loggedUser = $cookieStore.get("loggedUser");

            $rootScope.go = function(path, removeParams) {
                if (removeParams) {
                    $location.url(path);
                } else {
                    $location.path(path);
                }
            };

            $rootScope.manageError = function () {
                $rootScope.unDim();
                if (!$rootScope.messages) {
                    $rootScope.messages = [];
                }
                $rootScope.messages.push(i18n.t('default.server.error'));
                $rootScope.areErrorMessages = true;
            };

            $rootScope.errorManager = $rootScope.manageError;

            $rootScope.canAccess = function(uri) {
                return $rootScope.permissions.indexOf(uri) != -1;
            };

            $rootScope.print = function() {
                window.print();
            };

            FooterService.getSystemName(function(res) {
                if (res.ok) {
                    $rootScope.systemTitle = JSON.parse(res.data).value;
                    document.title = $rootScope.systemTitle;
                }
            },function() {
                // do nothing
            });

            $rootScope.$watch(function() { return $rootScope.systemTitle; }, function() {
                document.title = $rootScope.systemTitle;
            });

            $rootScope.showErrorMessage = function(message, error) {
                $rootScope.areErrorMessages = error;
                $rootScope.messages = [message];
            };

            $rootScope.clearMessages = function() {
                $rootScope.areErrorMessages = false;
                $rootScope.messages = [];
            };

            /**
             * Performs the login on the client side, once the login on the server
             * was successful.
             *
             * @param loggedUser the loggedUser (as object)
             * @param rememberMe the rememberMe flag
             * @param checkRememberMe flag, if true, the rememberMe flag will be consulted and used
             *
             */
            $rootScope.performLogin = function(loggedUser, rememberMe, checkRememberMe) {
                // save the logged user to the rootscope (for global accessing)
                $rootScope.loggedUser = loggedUser;

                // generate permissions
                $rootScope.permissions = $rootScope.generatePermissions($rootScope.loggedUser);

                // sets the cookie with the user
                var userCookie = {};
                angular.copy($rootScope.loggedUser, userCookie);
                // before, delete all the big data so the cookie is not overflown
                userCookie.role = null;
                userCookie.roleType = null;
                userCookie.menuOrder = null;
                // store the cookie
                $cookieStore.put("loggedUser", userCookie);

                // manage the remember me cookie if necessary
                if (checkRememberMe) {
                    if (rememberMe) {
                        $cookieStore.put("userCredentials", $scope.userCredentials);
                    } else {
                        $cookieStore.remove("userCredentials");
                    }
                }

                // un-dim the screen
                $rootScope.unDim();

                // redirects to the /
                //TODO edit as needed
                $location.path("/pastor");
            };

            /**
             * Adds functionality to the object to be able to rollback it's status
             */
            $rootScope.snapshot = function(ob) {
                ob._snapshot = {};
                ob._snapshot = angular.copy(ob, ob._snapshot);

                ob.rollback = function() {
                    ob = ob._snapshot;

                    return ob;
                };

                ob.unSnapshot = function() {
                    delete ob['_snapshot'];
                    return ob;
                };

                return ob;
            };

            function _run() {
                //i18n object initialization
                var i18nextOptions = {
                    resGetPath: 'locales/__lng__/__ns__.json',
                    load: 'unspecific',
                    lng: defaultLang,
                    fallbackLng: defaultLang,
                    useCookie: false,
                    supportedLngs: supported
                };

                //if (document.globalInspectorFlag) {
                //    i18nextOptions.resGetPath = '../locales/__lng__/__ns__.json';
                //}

                i18n.init(i18nextOptions, function() {
                    document.title = $rootScope.systemTitle;
                    // this flag is watched by the LoginCtrl, in order to construct the tooltip only
                    // when the i18n system has been initialized
                    $rootScope.readyToConstructTooltip = true;
                    $rootScope.$digest();
                });

                if ($rootScope.loggedUser) {
                    LoginService.getLoggedUser(
                        function(response){
                            if (response.ok) {
                                $rootScope.loggedUser = JSON.parse(response.data);
                                $rootScope.permissions = $rootScope.generatePermissions($rootScope.loggedUser);
                            }
                        },
                        function(){

                        });
                }

            }
        }
    ]
);
