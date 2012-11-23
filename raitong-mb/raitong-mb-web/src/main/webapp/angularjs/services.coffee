'use strict'
s = angular.module 'raitong.mb.service', ['ngResource']
s.factory 'SubscriberService', ($resource)->
  url = 'subscribers/:customerNo'
  actions = 
    get: {method: 'GET', params: {customerNo: '@customerNo'}}
    update: {method: 'POST', params: {customerNo: '@customerNo'}}
  return $resource url, {}, actions

s.factory 'SubscriptionService', ($resource)->
  url = 'subscribers/:customerNo/subscriptions/:subscriptionId'
  actions =
    create: {method: 'POST'}
    update: {method: 'PUT'}
  return $resource url, {}, actions