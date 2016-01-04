controllers.controller('UserCtrl', ['$scope','$http','userModel',
 function($scope, $http, userModel) {
	$scope.um = userModel;  
  $scope.user = {};
  if ( userModel.authenticated() ) {
	  $scope.user.firstName = userModel.firstName;
	  $scope.user.lastName = userModel.lastName;
	  $scope.user.email = userModel.email;
	  $scope.user.userName = userModel.userName;
	  $scope.user.credential = userModel.credential;
	  $scope.user.confirmation = userModel.confirmation;
  }

  
  $scope.plans = {};

  $scope.saveUser = function(action) {
	  
	if ( !$scope.user.userName ) {	
		err($scope, 'First name is required');
		return;
	}
	  
	if ( !$scope.user.userName ) {	
		err($scope, 'Username is required');
		return;
	}
	if ( $scope.user.credential != $scope.user.confirmation ) {
		err($scope, 'Passwords are not the same');
		$scope.user.confirmation = null;
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
