'use strict';
angular.module('raitong.mb', ['raitong.mb.directive', 'raitong.mb.service'])
	.config(['$routeProvider', function($routeProvider) {
		$routeProvider
			.when('/subscribers', {templateUrl: 'resources/angularjs/partial/subscriber/subscriber-list.html'})
			.when('/subscribers/create', {templateUrl: 'resources/angularjs/partial/subscriber/subscriber-form.html'})
			.when('/subscribers/:customerNo', {templateUrl: 'resources/angularjs/partial/subscriber/subscriber-info.html'})
      .otherwise({redirectTo: '/subscribers'});
	}]);