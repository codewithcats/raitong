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