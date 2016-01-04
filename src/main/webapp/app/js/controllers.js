var controllers = angular.module('controllers', []);

controllers.controller('AppCtrl', [ '$rootScope', '$scope', '$http', '$location','userModel',
                                    function($rootScope, $scope, $http, $location, userModel) {
		$scope.dd = false;
		$scope.userModel = userModel;
		
		$scope.logout = function() {
			$scope.dd = false;
			$scope.userModel.logout();
			$location.path('login');	
		};
		$scope.update = function() {
			$scope.dd = false;
			$location.path('/user/create');			
		};
		
		$scope.toggle = function() {
			$scope.dd = !$scope.dd;
		}
		

		$rootScope.$on("$routeChangeStart", function(event, next, current) {
			//if they are not authenticated and not trying to create an account then kick-em
			if ( !$scope.userModel.authenticated() && !next && !next.$$route && next.$$route.originalPath != '/user/create') {
				$location.path('login');	//force login
			}
		});
		
}]);

