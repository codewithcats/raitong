// Generated by CoffeeScript 1.3.3
'use strict';

var d;

d = angular.module('raitong.mb.directive', []);

d.directive('wizard', function() {
  return {
    restrict: 'A',
    link: function(scope, elements, attrs) {
      var activeIndex, container, firstActiveItem, items, leftNav, next, previous, renderCarouselControl, rightNav, v, wizard, _i, _len, _ref;
      wizard = scope.$eval(attrs.wizard);
      _ref = wizard.validations;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        v = _ref[_i];
        scope.$watch(v, function(valid) {
          return renderCarouselControl(activeIndex, valid);
        });
      }
      container = $('.carousel', elements);
      container.carousel({
        interval: false
      });
      items = $('.item', elements);
      if (items.length < 1) {
        throw 'No items at all!';
      }
      firstActiveItem = $('.active.item', elements);
      if (firstActiveItem.length < 1) {
        throw 'No active item specific!';
      }
      activeIndex = items.index(firstActiveItem);
      renderCarouselControl = function() {
        var valid;
        leftNav.toggle(activeIndex > 0);
        valid = scope.$eval(wizard.validations[activeIndex]);
        rightNav.toggle(activeIndex < items.length - 1 && valid);
      };
      next = function() {
        if (activeIndex < items.length) {
          container.carousel('next');
          activeIndex++;
          renderCarouselControl();
        }
      };
      previous = function() {
        if (activeIndex >= 1) {
          container.carousel('prev');
          activeIndex--;
          renderCarouselControl();
        }
      };
      leftNav = $('a.carousel-control.left', elements);
      leftNav.click(previous);
      rightNav = $('a.carousel-control.right', elements);
      rightNav.click(next);
      renderCarouselControl();
    }
  };
});

d.directive('datePicker', function() {
  return {
    restrict: 'A',
    require: ['ngModel'],
    link: function(scope, elements, attrs, ctrls) {
      var dayPicker, fragment, fromYear, m, monthPicker, ngModelCtrl, option, toYear, updateModel, updatePicker, y, yearPicker, _i, _j, _k, _len, _ref;
      dayPicker = $('.day', elements);
      monthPicker = $('.month', elements);
      yearPicker = $('.year', elements);
      fragment = document.createDocumentFragment();
      for (d = _i = 1; _i <= 31; d = ++_i) {
        option = $('<option></option>', {
          value: d,
          text: d
        });
        option.appendTo(fragment);
      }
      dayPicker.append(fragment);
      fragment = document.createDocumentFragment();
      _ref = [
        {
          name: 'January',
          value: 0
        }, {
          name: 'February',
          value: 1
        }, {
          name: 'March',
          value: 2
        }, {
          name: 'April',
          value: 3
        }, {
          name: 'May',
          value: 4
        }, {
          name: 'June',
          value: 5
        }, {
          name: 'July',
          value: 6
        }, {
          name: 'August',
          value: 7
        }, {
          name: 'September',
          value: 8
        }, {
          name: 'October',
          value: 9
        }, {
          name: 'November',
          value: 10
        }, {
          name: 'December',
          value: 11
        }
      ];
      for (_j = 0, _len = _ref.length; _j < _len; _j++) {
        m = _ref[_j];
        option = $('<option></option>', {
          value: m.value,
          text: m.name
        });
        option.appendTo(fragment);
      }
      monthPicker.append(fragment);
      fromYear = scope.$eval(attrs.fromYear);
      toYear = scope.$eval(attrs.toYear);
      fragment = document.createDocumentFragment();
      for (y = _k = fromYear; fromYear <= toYear ? _k <= toYear : _k >= toYear; y = fromYear <= toYear ? ++_k : --_k) {
        option = $('<option></option>', {
          value: y,
          text: y
        });
        option.appendTo(fragment);
      }
      yearPicker.append(fragment);
      updateModel = function() {
        var _moment;
        m = monthPicker.val();
        y = yearPicker.val();
        d = dayPicker.val();
        _moment = moment([y, m, d]);
        return ngModelCtrl.$setViewValue(_moment.valueOf());
      };
      ngModelCtrl = ctrls[0];
      if (!ngModelCtrl.$modelValue) {
        updateModel();
      }
      updatePicker = function() {
        var days, options, toBeHidden, _moment;
        m = monthPicker.val();
        y = yearPicker.val();
        d = dayPicker.val();
        _moment = moment([y, m]);
        days = _moment.daysInMonth();
        options = dayPicker.children('option');
        options.css('display', 'inline');
        toBeHidden = options.slice(days);
        toBeHidden.css('display', 'none');
        d = parseInt(d);
        if (d > days) {
          d = days;
        }
        dayPicker.val(d);
        updateModel();
      };
      monthPicker.change(updatePicker);
      yearPicker.change(updatePicker);
      dayPicker.change(updateModel);
    }
  };
});
