'use strict'
d = angular.module 'raitong.mb.directive', []
d.directive 'wizard', ()->
  postLink = (scope, elements, attrs) ->
    wizard = scope.$eval attrs.wizard

    container = $ '.carousel', elements
    container.carousel {interval: false}
    items = $ '.item', elements
    if items.length < 1 then throw 'No items at all!'

    firstActiveItem = $ '.active.item', elements
    if firstActiveItem.length < 1 then throw 'No active item specific!'
    activeIndex = items.index firstActiveItem

    renderCarouselControl = (doValidate) ->
      leftNav.toggle activeIndex>0
      if doValidate
        validation = wizard.validations[activeIndex]
        valid = validation.validate validation.subject, scope
        rightNav.toggle valid and activeIndex < items.length-1
        return
      rightNav.toggle items.length-1
    
    leftNav = $ 'a.carousel-control.left', elements
    leftNav.click ()->
      if activeItem >= 1
        container.carousel 'prev'
        activeIndex--
      renderCarouselControl false
    
    rightNav = $ 'a.carousel-control.right', elements
    rightNav.click ()->
      if activeIndex < items.length
        validation = wizard.validations[activeIndex]
        valid = validation.validate validation.subject, scope
        if valid
          container.carousel 'next'
          activeIndex++
          renderCarouselControl true
          return
        renderCarouselControl false
    
    renderCarouselControl true

    return
  return postLink
