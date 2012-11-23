'use strict'
ListSubscriberCtrl = ($http, $scope)->
  getSubscribers = ()->
    req = $http.get 'subscribers'
    req.success (subscribers)->
      $scope.subscribers = subscribers
    return

  getSubscribers()
  $scope.del = (s)->
    req = $http.delete 'subscribers/'+s.customerNo
    req.success ()-> 
      getSubscribers()
      return
    return

  $scope.displayDate = (date)->
    return if date then moment(date).format 'Do MMMM YYYY' else ''
  $scope.displayLastestSubscriptionInfo = (s)->
    subs = s.subscriptions
    if not subs or subs.length < 1 then return 'No Subscription'
    lsubs = subs[subs.length-1]
    return "#{lsubs.months} months, start: #{$scope.displayDate(lsubs.startingDate)}"
  return
ListSubscriberCtrl.$inject = ['$http', '$scope']

CreateSubscriberCtrl = ($scope, $http, $location)->
  $scope.mode = 'create'
  $scope.birthdayToYear = moment().year()
  $scope.subscriber = new Subscriber
  $scope.notSpecificBD = yes
  $scope.create = (s)->
    req = $http.post 'subscribers', s
    req.success (subscriber) ->
      $location.path '/subscribers'
      return
CreateSubscriberCtrl.$inject = ['$scope', '$http', '$location']

SubscriberInfoCtrl = ($scope, $routeParams, SubscriberService, SubscriptionService)->
  subscriber = SubscriberService.get $routeParams, ()->
    $scope.subscriber = new Subscriber subscriber
    return
  $scope.displayInfo = (info)-> info || 'No Information'
  $scope.getInfoClass = (info)-> 'info-undefined' if !info
  $scope.displayDate = (millis, nullMsg)->
    if not nullMsg then nullMsg = 'No Information'
    if not millis then return nullMsg
    moment(millis).format 'Do MMMM YYYY'
  $scope.displayMoney = (money)->
    if angular.isNumber(money) is no then return 'No Information'
    money.toFixed 2
  $scope.del = (subscriber, subscription)->
    SubscriptionService.delete {customerNo: subscriber.customerNo, subscriptionId: subscription.id}, ()->
      subscriber = SubscriberService.get $routeParams, ()->
        $scope.subscriber = new Subscriber subscriber
        return
  return
SubscriberInfoCtrl.$inject = ['$scope', '$routeParams', 'SubscriberService', 'SubscriptionService'] 

EditSubscriberCtrl = ($scope, $routeParams, $location, SubscriberService)->
  $scope.mode = 'edit'
  subscriber = SubscriberService.get $routeParams, ()->
    $scope.subscriber = new Subscriber subscriber
    if not $scope.subscriber.birthday then $scope.notSpecificBD = yes
  $scope.save = (s)->
    SubscriberService.update s, ()->
      customerNo = subscriber.customerNo
      $location.path "/subscribers/#{customerNo}"
      return
    return
EditSubscriberCtrl.$inject = ['$scope', '$routeParams', '$location', 'SubscriberService']