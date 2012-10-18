'use strict';
angular.module('raitong.mb', [])
	.config(['$routeProvider', function($routeProvider) {
		$routeProvider
			.when('/subscribers', {templateUrl: 'resources/angularjs/partial/subscriber/subscriber-list.html'})
			.otherwise({redirectTo: '/subscribers'});
	}]);