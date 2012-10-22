'use strict';
function ListSubscriberCtrl($http, $scope){
	getSubscribers();
	
	$scope.del = function(s) {
		$http.delete('subscribers/' + s.customerNo)
			.success(function() {
				getSubscribers();
			});
		
	};
	
	function getSubscribers() {
		$http.get('subscribers')
		.success(function(data) {
			$scope.subscribers = data;
		});
	};
}

ListSubscriberCtrl.$inject = ['$http', '$scope'];

function CreateSubscriberCtrl($scope, $http) {
	
	$scope.create = function(s) {
		$http.post('subscribers', s)
			.success(function(data) {
				console.log(data);
			});
	};
	
	$scope.setGender = function(gender, subscriber) {
		subscriber.gender = gender;
	};
	
}

CreateSubscriberCtrl.$inject = ['$scope', '$http'];