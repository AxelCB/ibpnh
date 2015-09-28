/**
 * Created by Axel on 07/01/2015.
 */

'use strict';
/** Routes Definitions Module
 */
angular.module('routes', []).config(['$routeProvider', function($routeProvider) {

    var viewsPrefix = "app/views";
    //routes definitions
    var routes = [
        {path: '/home', template: viewsPrefix+'/layout/home.html', controller: 'HomeCtrl'},
        {path: '/contacto', template: viewsPrefix+'/contacto/contacto.html', controller: null},
        {path: '/devocionales', template: viewsPrefix+'/devotional/devocionales.html', controller: 'DailyDevotionalCtrl'},
        {path: '/gbcs', template: viewsPrefix+'/gbcs/gbcs.html', controller: null},
        {path: '/ministerios', template: viewsPrefix+'/ministerios/ministerios.html', controller: null},
        {path: '/login', template: viewsPrefix+'/login/login.html', controller: 'LoginCtrl'},
        {path: '/pastor', template: viewsPrefix+'/pastor.html', controller: 'LoginCtrl'},
        {path: '/configuration/user', template: viewsPrefix+'/configuration/user/user.html', controller: 'UserCtrl'},
        {path: '/configuration/function', template: viewsPrefix+'/configuration/function/function.html', controller: 'FunctionCtrl'},
        {path: '/configuration/parameter', template: viewsPrefix+'/configuration/parameter/parameter.html', controller: 'ParameterCtrl'},
        {path: '/devotional', template: viewsPrefix+'/devotional/devotional.html', controller: 'DailyDevotionalCtrl'},
        {path: '/devocionales/detalle/:devotionalId', template: viewsPrefix+'/devotional/detalleDevocional.html', controller: 'DailyDevotionalCtrl'},

    ];

    //for every route definition in the array, creates an Angular route definition
    $.each(routes, function() {
        $routeProvider.when(this.path, {
            templateUrl: this.template,
            controller: this.controller
        });
    });
    //in any other case, redirects to the main view
    //$routeProvider.otherwise({redirectTo:'/home'});

}]);