controllers.controller('LandingCtrl', [
		'$scope',
		'$http',
		'$location',
		'userModel',
function($scope, $http, $location, userModel) {
	$scope.userModel = userModel;
	$scope.rowCollection = [];
	$scope.displayCollection = [];
	$scope.itemsByPage = 5;

	$http.get('plans').success(function(data) {
		$scope.rowCollection = data;
		$scope.displayCollection = data;
	});

	$scope.creator = {
		name : ''
	};
	$scope.createPlan = function() {
		var success = function(response) {
			if ( response.data.severity == 'SUCCESS') {
				$http.get('plans').success(function(data) {
					$scope.rowCollection = data;
					$scope.displayCollection = data;
				});
			} else {
				err($scope, response.data.summaries);
			}
		}
		var error = function() {
			alert('error!');
		}
		var url = 'plans/create/'
				+ encodeURIComponent($scope.creator.name) + '?userId=' + $scope.userModel.id;
		$http.put(url, {}).then(success, error);
	}
	
	$scope.createUser = function() {
		$location.path('user/create');
	}
	
	$scope.viewPlan = function(id) {
		$location.path('planner/' + id);		
	}
} ]);