'use strict';
function RootCtrl($locationProvider) {
	$locationProvider.html5Mode(true);
}
RootCtrl.$inject = ['$locationProvider'];