controllers.controller('LandingCtrl', [
		'$scope',
		'$http',
		'$location',
		'userFactory',
function($scope, $http, $location, userFactory) {
			
			
	$scope.toggler = {
			requirements : true
	}		
			
	$scope.userFactory = userFactory;
	$scope.rowCollection = [];
	$scope.displayCollection = [];
	$scope.itemsByPage = 5;

	$http.get('requirements').success(function(data) {
		$scope.rowCollection = data.payload;
		$scope.displayCollection = data.payload;
	});

	$scope.creator = {
		name : ''
	};
	$scope.createPlan = function() {
		var success = function(response) {
			if ( response.data.severity == 'SUCCESS') {
				$http.get('requirements').success(function(response) {
					init(response);
				});
			} else {
				err($scope, response.data.summaries);
			}
		}
		var error = function() {
			alert('error!');
		}
		var url = 'requirements/create/'
				+ encodeURIComponent($scope.creator.name) + '?userId=' + $scope.userFactory.model.id;
		$http.put(url, {}).then(success, error);
	}
	
	$scope.createUser = function() {
		$location.path('user/create');
	}
	
	$scope.viewPlan = function(id) {
		$location.path('planner/' + id);		
	}
	
	
	function init( data ) {
		$scope.displayCollection = data;
		$scope.rowCollection = [];
		for ( var i =0; i < data.length; i++) {
			$scope.rowCollection.push( {
					id : data[i].id,
					name : data[i].name,
					creator: data[i].creator,
					updator: data[i].lastChangedBy,
					lastChanged: data[i].lastChanged
					
			});
		}
	}
	
} ]);