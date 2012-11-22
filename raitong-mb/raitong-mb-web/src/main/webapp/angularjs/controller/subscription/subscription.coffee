'use strict'
CreateSubscriptionCtrl = ($scope, $location, $routeParams, SubscriberService, SubscriptionService)->
  $scope.fromYear = moment().year() - 1
  $scope.toYear = moment().year() + 4
  subscriber = SubscriberService.get $routeParams, ()->
    $scope.subscriber = new Subscriber subscriber
  $scope.subscription = new Subscription
  $scope.createSubscription = (s)->
    customerNo = $routeParams.customerNo
    _subscription = SubscriptionService.create {customerNo: customerNo}, s, ()->
      $scope.subscription = _subscription
      $location.path "/subscribers/#{$scope.subscriber.customerNo}"

CreateSubscriptionCtrl.$inject = ['$scope', '$location', '$routeParams', 'SubscriberService', 'SubscriptionService']