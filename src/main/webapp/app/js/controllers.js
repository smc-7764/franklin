var controllers = angular.module('controllers', []);

controllers.controller('AppCtrl', [ '$rootScope', '$scope', '$http', '$location','userFactory',
                                    function($rootScope, $scope, $http, $location, userFactory) {
		$scope.dd = false;
		$scope.userModel = userFactory.model;
			
		$scope.logout = function() {
			$scope.dd = false;
			$scope.userFactory.logout();
			$location.path('login');	
		};
		$scope.update = function() {
			$scope.dd = false;
			$location.path('/user/create');			
		};
		
		$scope.toggle = function() {
			$scope.dd = !$scope.dd;
		}
		
		
		$scope.home = function() {
			if ( userFactory.authenticated() ) {
				$location.path('landing');				
			} else {
				userFactory.logout();	
				$location.path('login');	
			}
	
		}
		

		$rootScope.$on("$routeChangeStart", function(event, next, current) {
			//if they are not authenticated and not trying to create an account then kick-em
			if ( !$scope.userFactory.authenticated() && !next && !next.$$route && next.$$route.originalPath != '/user/create') {
				$location.path('login');	//force login
			}
		});
		
}]);

