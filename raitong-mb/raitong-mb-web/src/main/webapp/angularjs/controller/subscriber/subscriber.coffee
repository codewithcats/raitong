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
  return
ListSubscriberCtrl.$inject = ['$http', '$scope']

CreateSubscriberCtrl = ($scope, $http, $location)->
  $scope.wizard =
    validations: [
      'basicInfoForm.$valid'
      'contactInfoForm.$valid'
      'otherInfoForm.$valid'
    ]
  $scope.subscriber = {
    gender: 'MALE'
    getReadableGender: ()->
      if this.gender is 'MALE'
        return 'Male'
      else if this.gender is 'FEMALE'
        return 'Female'
      else if this.gender is 'NOT_SPECIFIC'
        return 'Not Specific'
      else
        throw 'Invalid gender'
    isGenderActive: (gender)->
      if this.gender is gender
        return 'active btn-primary'
      else return undefined
  }
  $scope.dateOptions = [
    {'value': 1}
  ]
  $scope.setGender = (gender, subscriber)->
    subscriber.gender = gender
  $scope.create = (s)->
    req = $http.post 'subscribers', s
    req.success (subscriber) ->
      $location.path '/subscribers'
      return
CreateSubscriberCtrl.$inject = ['$scope', '$http', '$location']

SubscriberInfoCtrl = ($scope, $http, $routeParams, SubscriberService)->
  subscriber = SubscriberService.get $routeParams, ()->
    $scope.subscriber = subscriber
    return
  return
SubscriberInfoCtrl.$inject = ['$scope', '$http', '$routeParams', 'SubscriberService'] 