'use strict';
angular.module('raitong.mb.directive', [])
	.directive('wizard', function factory() {
		return function postLink(scope, elements, attrs) {
			var container = $('.carousel', elements);
			container.carousel({interval: false});
			var items = $('.item', elements);
			if(items.length < 1) throw 'No items at all!';
			
			var firstActiveItem = $('.active.item', elements);
			if(firstActiveItem.length < 1) throw 'No active item specific!';
			
			var activeIndex = items.index(firstActiveItem);
			var leftNav = $('a.carousel-control.left', elements)
				.click(function() {
					activeIndex--;
					if(activeIndex >= 0) {
						container.carousel('prev');
						renderCarouselControl();
					}
				});
			var rightNav = $('a.carousel-control.right', elements)
				.click(function() {
					activeIndex++;
					if(activeIndex < items.length) {
						container.carousel('next');
						renderCarouselControl();
					}
				});
			renderCarouselControl();
			
			function renderCarouselControl() {
				leftNav.toggle(activeIndex > 0);
				rightNav.toggle(activeIndex < items.length-1);
			}
		};
	});