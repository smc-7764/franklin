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
				return $http.get('requirements/'+$route.current.params["planId"]).then(function(response) {
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

franklin.factory('userFactory', ['$location',function($location) {
    
    var factory = { 
    		model: {}
    }; 

    factory.update = function(userModel) {
    	factory.model = userModel;
    } 
    factory.authenticated = function() {
       return factory.model.id != null
    }

    factory.logout = function() {
    	factory.model = {}; //reset
    	$location.path('login');
    }
    
    return factory;
}]);

/*
 * @scope - current scope
 * @summaries - what happened
 */
function err(scope, text) {
	var messages = [];
	if ( !text ) {
		 messages[0] = 'something bad happened. contact application development for more information';
	}

	scope.response = {
			hint: 'alert-danger',
			messages: messages
	};
}

/*
 * @scope - current scope
 * @summaries - what happened
 */
function errs(scope, issues) {
	var text = null;
	for ( var i = 0; i < issues.length; i++) {
		if ( text == null ) {
			text = issues[i];
		} else {
			text = text + '<br/>' + issues[i];
		}		
	}
	var messages = [];
	if ( !issues || issues.length == 0 ) {	
		messages[0] = 'something bad happened. contact application development for more information';
	} else {
		messages = issues;
	}

	scope.response = {
			hint: 'alert-danger',
			messages: messages
	};
}

/*
 * @scope - current scope
 * @summary - what happened
 */
function ok(scope, text) {
	var messages = [];
	if ( !text ) {
		 messages[0] = 'Success!';
	} else {
		 messages[0] = text;
	}
	scope.response = {
			hint: 'alert-success',
			messages: messages
	};
}


