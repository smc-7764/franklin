var franklin = angular.module('franklin', [ 'ngRoute', 'controllers', 'smart-table' ]);

franklin.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/reports', {
		templateUrl : 'app/partials/report.html',
		controller : 'ReportCtrl'
	}).when('/planner/:planId', {
		templateUrl : 'app/partials/planner.html',
		controller : 'PlannerCtrl',
		resolve: {
			plan: ['$http','$route', function($http, $route) {
				return $http.get('plans/'+$route.current.params["planId"]).then(function(response) {
					return response.data
				})
			}]
		}
	}).when('/landing', {
		templateUrl : 'app/partials/landing.html',
		controller : 'LandingCtrl'
	}).when('/user/create', {
		templateUrl : 'app/partials/user.html',
		controller : 'UserCtrl'
	}).when('/login', {
		templateUrl : 'app/partials/login.html',
		controller : 'LoginCtrl'
	}).otherwise({
		redirectTo : '/login'
	});
} ]);

franklin.factory('userModel', ['$location',function($location) {
    
    var model = { }; 
 
    model.update = function(user) {
    	 model.id = user.id;
         model.firstName = user.firstName;
         model.lastName = user.lastName;
         model.userName = user.userName;
         model.email = user.email;
         //$rootScope.user = model;
    } 
    model.authenticated = function() {
       return model.id != null
    }

    model.logout = function() {
    	model = { }; //reset
    	$location.path('login');
    }
    
    return model;
}]);

/*
 * @scope - current scope
 * @summaries - what happened
 */
function err(scope, summaries) {
	if ( !summaries ) {
		summaries = 'something bad happened. contact application development for more information';
	}
	var out = '';
	for ( var i=0; i < summaries.length; i++) {
		out = summaries[i] + out;
	}
	scope.response = {
			hint: 'alert-danger',
			summary: out
	};
}

/*
 * @scope - current scope
 * @summary - what happened
 */
function ok(scope, summary) {
	scope.response = {
			hint: 'alert-success',
			summary: summary
	};
}


