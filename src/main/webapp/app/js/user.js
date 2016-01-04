controllers.controller('UserCtrl', ['$scope','$http','userFactory',
 function($scope, $http, userFactory) {
 $scope.um = userFactory;  
  $scope.user = {};
  if ( userFactory.authenticated() ) {
	  $scope.user.firstName = userFactory.firstName;
	  $scope.user.lastName = userFactory.lastName;
	  $scope.user.email = userFactory.email;
	  $scope.user.userName = userFactory.userName;
	  $scope.user.credential = userFactory.credential;
	  $scope.user.confirmation = userFactory.confirmation;
  }

  
  $scope.plans = {};

  $scope.saveUser = function(action) {
	 
	 var issues = [];
	 
	if ( !$scope.user.firstName ) {	
		issues.push( 'First name is required');
	}
	  
	if ( !$scope.user.userName ) {	
		issues.push( 'Username is required');
	}
	if ( $scope.user.credential != $scope.user.confirmation ) {
		issues.push( 'Passwords are not the same');
		$scope.user.confirmation = null;
	}
	if ( issues.length > 0 ) {
		errs($scope,issues);
		return;
	}
	var url = 'users/create';
	if ( action == 'UPDATE') {
		url = 'users/update';
	}
	
	var success = function(response) {
		$scope.response = {};
		var preface = 'Updated';
		if ( action == 'CREATE') {
			preface = 'Created';
		} 
		
		if ( response.data.severity == 'SUCCESS' ) {
			$scope.response = response.data;
			$scope.user = response.data.payload;
			ok($scope, preface + ' user ' + response.data.payload.userName + '; Id: ' + response.data.payload.id );
			
			/*
			$http.get('plans').success(function(data) {
				$scope.plans = data;
				$scope.rowCollection = [];
				for ( var i =0; i < data.length; i++) {
					$scope.rowCollection.push( {
							id : data[i].id,
							name : data[i].name,
							creator: 'TBD'
					});
				}
			});
			*/
			
		} else {
			$scope.response = response.data;
			$scope.response.hint = 'alert-danger';			
		}
		
	}
	var error = function(response) {
		alert('error! ' + response.status);
	}
	$http.put(url, $scope.user).then(success, error);

  };
  
  $scope.purgeResponse = function() {
	  $scope.response = null;
  }
  
  function reviewPlans() {
	  
  }
  
}]);
