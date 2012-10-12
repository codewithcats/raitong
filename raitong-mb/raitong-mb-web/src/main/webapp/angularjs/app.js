'use strict';
angular.module('raitong.mb', [])
	.config(['$routeProvider', function($routeProvider) {
		$routeProvider
			.when('/subscribers', {templateUrl: 'resources/angularjs/partial/example.html'})
			.otherwise({redirectTo: '/subscribers'});
	}]);