// Generated by CoffeeScript 1.3.3
'use strict';

var CreateSubscriptionCtrl, EditSubscriptionCtrl;

CreateSubscriptionCtrl = function($scope, $location, $routeParams, SubscriberService, SubscriptionService) {
  var subscriber;
  $scope.mode = 'create';
  $scope.fromYear = moment().year() - 1;
  $scope.toYear = moment().year() + 4;
  subscriber = SubscriberService.get($routeParams, function() {
    return $scope.subscriber = new Subscriber(subscriber);
  });
  $scope.subscription = new Subscription;
  return $scope.createSubscription = function(s) {
    var customerNo, _subscription;
    customerNo = $routeParams.customerNo;
    return _subscription = SubscriptionService.create({
      customerNo: customerNo
    }, s, function() {
      $scope.subscription = _subscription;
      return $location.path("/subscribers/" + $scope.subscriber.customerNo);
    });
  };
};

CreateSubscriptionCtrl.$inject = ['$scope', '$location', '$routeParams', 'SubscriberService', 'SubscriptionService'];

EditSubscriptionCtrl = function($scope, $routeParams, $location, SubscriptionService) {
  var subscription;
  $scope.mode = 'edit';
  $scope.customerNo = $routeParams.customerNo;
  subscription = SubscriptionService.get({
    customerNo: $scope.customerNo,
    subscriptionId: $routeParams.subscriptionId
  }, function() {
    return $scope.subscription = subscription;
  });
  return $scope.saveSubscription = function(subscription) {
    return SubscriptionService.update({
      customerNo: $scope.customerNo
    }, subscription, function() {
      return $location.path("/subscribers/" + $scope.customerNo);
    });
  };
};

EditSubscriptionCtrl.$inject = ['$scope', '$routeParams', '$location', 'SubscriptionService'];
