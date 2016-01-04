controllers.controller('LoginCtrl', [ '$scope', '$http', '$location','userFactory',
function($scope, $http, $location, userFactory) {
	$scope.userFactory = userFactory;

	$scope.register = function() {
		$location.path('user/create');
	}
	
	$scope.login = function() {
		var success = function(response) {
			if ( response.data.severity == 'SUCCESS') {
				$scope.userFactory.update(response.data.payload);
				$location.path('landing');
			} else {
				err($scope,response.data.summaries);
			}
		}
		var error = function() {
			err($scope,'error!');
		}
		$http.post('login?account='+ $scope.model.account +'&credential=' + $scope.model.credential, {}).then(success, error);
	}
} ]);

franklin.directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keypress", function (event) {
            if(event.which === 13) {
        		event.preventDefault();
        		scope.login(); 
            }
        });
    };
});


