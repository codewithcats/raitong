'use strict'
CreateSubscriptionCtrl = ($scope, $routeParams, SubscriberService)->
  subscriber = SubscriberService.get $routeParams, ()->
    $scope.subscriber = new Subscriber subscriber

CreateSubscriptionCtrl.$inject = ['$scope', '$routeParams', 'SubscriberService']