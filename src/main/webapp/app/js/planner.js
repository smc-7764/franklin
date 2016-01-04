controllers.controller('PlannerCtrl', ['$scope','$http','plan',
 function($scope, $http, plan) {
                                       
  $scope.plan = plan;

  $scope.addEntry = function(coordinate) {
	var success = function(response) {
		$scope.plan = response.data;
	}
	var error = function(response) {
		alert('error! ' + response.status);
	}
	var url ='/requirements/create/' + $scope.plan.planId + '/entry/'+ coordinate
	$http.post(url, {}).then(success, error);
  };
  
  $scope.toggler = {
		  id: null,
		  action: null
  };
  
  $scope.tabModel = {
		  descriptor: true,
		  move: false,
		  comments: false
  }
  
  $scope.action = function(node, action) {
	  $scope.nodeCurrent = node;
	  $scope.toggler.id = node.nodeId;
	  $scope.toggler.action = action;
	  if ( action == 'view') {
		  setTab('descriptor');
	  }
  }
  
  $scope.resetToggler = function() {
	  $scope.nodeCurrent = null;
	  $scope.toggler.id = null;
	  $scope.toggler.action = null;
  }

  
  $scope.updateNode = function() {	  	
		var success = function(response) {
			$scope.plan = response.data;	
		}
		var error = function(response) {
			alert('error! ' + response.status);
		}
		
		var url ='requirements/' + $scope.plan.planId + '/updateNode';
		$http.put(url, $scope.nodeCurrent).then(success, error);
 }
  
 $scope.move = function(node, direction) {
	 	var success = function(response) {
			$scope.plan = response.data;			
		}
		var error = function(response) {
			alert('error! ' + response.status);
		}
		//"/requirements/{planId}/updateNode"
		var url ='requirements/' + $scope.plan.planId + '/' + node.nodeId + '/' + direction;
		$http.put(url, {}).then(success, error);
 }
 
 $scope.toTab = function(tab) {
	setTab(tab);
 }
  
 function setTab(tab) {
	 if ( tab == 'descriptor') {
		 $scope.tabModel.descriptor = true;
		 $scope.tabModel.move = false;
		 $scope.tabModel.comments = false;
	 } else {
		 $scope.tabModel.descriptor = false;
		 $scope.tabModel.move = false;
		 $scope.tabModel.comments = true;
	 }
 }
}]);