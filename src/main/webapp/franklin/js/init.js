app.factory('orderService', ['$rootScope', function($rootScope) {
  var sharedPool = {
    dashboard: []
  };

  return {
    dashboard: function() {
      return sharedPool["dashboard"];
    },
    updateDashboard: function(dashboard) {
      sharedPool["dashboard"] = dashboard;
      // var broadcast = new function( $rootScope) {
      // $rootScope.$broadcast('dashboard:updated',dashboard);
      // };

      $rootScope.$broadcast('dashboard:updated', dashboard);
    }

  }
}]);

/**
 * inspect shared state, if it is MIA rebuild ( A rebuild will happen on intial load or force refresh )
 * 
 * @param $scope
 * @param $http
 * @param $location
 * @param orderService
 * @param callback
 */
function init($scope, $http, $location, orderService, callback) {

  $scope.dashboardModel = orderService.dashboard();

  if (!$scope.dashboardModel.totals) {

    // setTimeout( 500, new function() {
    $http.get('orders/dashboard', {
      params: ''
    }).then(function(response) {

      $scope.dashboardModel = response.data;
      orderService.updateDashboard(response.data);
      $scope.lastRefresh = new Date();

      callback($scope);

      if (!dashSocket) {
        initWS(orderService);
      }
    });
    // });
  } else {
    callback($scope);
  }
}

var dashSocket;
/**
 * Initialize the WebSocket listener(s)
 * 
 * @param orderService
 */
function initWS(orderService) {

  dashSocket = new SockJS('/orderm/add');

  var stompClient = Stomp.over(dashSocket);

  stompClient.connect({}, function(frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/dashboard', function(dash) {

      var dashboard = JSON.parse(dash.body);
      // console.log('Orders total:' + dashboard.totals["orders"]);
      orderService.updateDashboard(dashboard);
    });
  });
}

/**
 * http://stackoverflow.com/questions/4812686/closing-websocket-correctly-html5-javascript
 */
window.onbeforeunload = function() {
  if (dashSocket) {
    dashSocket.onclose = function() {
    }; // disable onclose handler first
    dashSocket.close();
  }
};