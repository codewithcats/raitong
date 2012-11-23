'use strict'
CreateSubscriptionCtrl = ($scope, $location, $routeParams, SubscriberService, SubscriptionService)->
  $scope.mode = 'create'
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

EditSubscriptionCtrl = ($scope, $routeParams, $location, SubscriptionService)->
  $scope.mode = 'edit'
  $scope.customerNo = $routeParams.customerNo
  subscription = SubscriptionService.get {customerNo: $scope.customerNo, subscriptionId: $routeParams.subscriptionId}, ()->
    $scope.subscription = subscription
  $scope.saveSubscription = (subscription)->
    SubscriptionService.update {customerNo: $scope.customerNo}, subscription, ()->
      $location.path "/subscribers/#{$scope.customerNo}"
EditSubscriptionCtrl.$inject = ['$scope', '$routeParams', '$location', 'SubscriptionService']
