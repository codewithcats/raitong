'use strict';
angular.module('raitong.mb', ['raitong.mb.directive', 'raitong.mb.service'])
	.config(['$routeProvider', function($routeProvider) {
		$routeProvider
			.when('/subscribers', {templateUrl: 'resources/angularjs/partial/subscriber/subscriber-list.html'})
			.when('/subscribers/create', {templateUrl: 'resources/angularjs/partial/subscriber/subscriber-form.html', controller: 'CreateSubscriberCtrl'})
			.when('/subscribers/:customerNo', {templateUrl: 'resources/angularjs/partial/subscriber/subscriber-info.html'})
      .when('/subscribers/:customerNo/edit', {templateUrl: 'resources/angularjs/partial/subscriber/subscriber-form.html', controller: 'EditSubscriberCtrl'})
      .when('/subscribers/:customerNo/subscriptions/create', {templateUrl: 'resources/angularjs/partial/subscription/subscription-form.html', controller: 'CreateSubscriptionCtrl'})
      .otherwise({redirectTo: '/subscribers'});
	}]);