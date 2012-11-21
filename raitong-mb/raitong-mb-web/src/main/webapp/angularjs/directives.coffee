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

    renderCarouselControl = ()->
      leftNav.toggle activeIndex > 0
      valid = scope.$eval wizard.validations[activeIndex]
      rightNav.toggle activeIndex < items.length-1 and valid
      return

    next = ()->
      if activeIndex < items.length
        container.carousel 'next'
        activeIndex++
        renderCarouselControl()
      return

    previous = ()->
      if activeIndex >= 1
        container.carousel 'prev'
        activeIndex--
        renderCarouselControl()
      return
    
    leftNav = $ 'a.carousel-control.left', elements
    leftNav.click previous    
    rightNav = $ 'a.carousel-control.right', elements
    rightNav.click next
    renderCarouselControl()

    return

d.directive 'datePicker', ()->
  restrict: 'A'
  require: ['ngModel']
  link: (scope, elements, attrs, ctrls)->
    dayPicker = $ '.day', elements
    monthPicker = $ '.month', elements
    yearPicker = $ '.year', elements

    fragment = document.createDocumentFragment()
    for d in [1..31]
      option = $ '<option></option>', {value: d, text: d}
      option.appendTo fragment
    dayPicker.append fragment

    fragment = document.createDocumentFragment()
    for m in [
      {name:'January', value: 0}, 
      {name:'February', value: 1}, 
      {name:'March', value: 2}, 
      {name:'April', value: 3}, 
      {name:'May', value:4}, 
      {name:'June', value: 5}, 
      {name:'July', value: 6}, 
      {name:'August', value: 7}, 
      {name:'September', value: 8}, 
      {name:'October', value: 9}, 
      {name:'November', value: 10}, 
      {name:'December', value: 11}
    ]
      option = $ '<option></option>', {value: m.value, text: m.name}
      option.appendTo fragment
    monthPicker.append fragment

    fromYear = scope.$eval attrs.fromYear
    if not fromYear then fromYear = moment().year()
    toYear = scope.$eval attrs.toYear
    if not toYear then toYear = moment().year() - 80
    fragment = document.createDocumentFragment()
    for y in [fromYear..toYear]
      option = $ '<option></option>', {value: y, text: y}
      option.appendTo fragment
    yearPicker.append fragment

    getDateArray = ()->
      m = monthPicker.val()
      y = yearPicker.val()
      d = dayPicker.val()
      return [y, m, d]

    updateModel = ()->
      dateArray = getDateArray()
      date = moment [y, m, d]
      ngModelCtrl.$setViewValue date.valueOf()
    
    updatePicker = (input)->
      if angular.isArray input
        date = moment [input[0], input[1]]
        days = date.daysInMonth()
        options = dayPicker.children('option')
        options.css 'display', 'inline'
        toBeHidden = options.slice days
        toBeHidden.css 'display', 'none'
        d = parseInt input[2]
        if d > days then d = days
        dayPicker.val d
      else if angular.isNumber input
        date = moment input
        y = date.year()
        m = date.month()
        d = date.date()
        yearPicker.val y
        monthPicker.val m
        dayPicker.val d
      return

    ngModelCtrl = ctrls[0];
    if not ngModelCtrl.$modelValue
      updateModel()
    else
      mills = ngModelCtrl.$modelValue
      updatePicker mills
    ngModelCtrl.$render = ()->
      mills = ngModelCtrl.$modelValue
      updatePicker mills

    monthPicker.change ()->
      dateArray = getDateArray()
      updatePicker dateArray
      updateModel()
    
    yearPicker.change ()->
      dateArray = getDateArray()
      updatePicker dateArray
      updateModel()

    dayPicker.change updateModel

    return