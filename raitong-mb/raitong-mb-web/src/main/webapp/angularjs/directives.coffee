'use strict'
d = angular.module 'raitong.mb.directive', []
d.directive 'wizard', ()->
  restrict: 'A'
  link: (scope, elements, attrs) ->
    wizard = scope.$eval attrs.wizard
    for v in wizard.validations
      scope.$watch v, (valid)-> renderCarouselControl activeIndex, valid
        

    container = $ '.carousel', elements
    container.carousel {interval: false}
    items = $ '.item', elements
    if items.length < 1 then throw 'No items at all!'

    firstActiveItem = $ '.active.item', elements
    if firstActiveItem.length < 1 then throw 'No active item specific!'
    activeIndex = items.index firstActiveItem

    renderCarouselControl = () ->
      leftNav.toggle activeIndex > 0
      valid = scope.$eval wizard.validations[activeIndex]
      rightNav.toggle activeIndex < items.length-1 and valid
      return
    
    leftNav = $ 'a.carousel-control.left', elements
    leftNav.click ()->
      if activeIndex >= 1
        container.carousel 'prev'
        activeIndex--
        renderCarouselControl activeIndex, valid
    
    rightNav = $ 'a.carousel-control.right', elements
    rightNav.click ()->
      if activeIndex < items.length
        container.carousel 'next'
        activeIndex++
        renderCarouselControl()
    renderCarouselControl()
    return
