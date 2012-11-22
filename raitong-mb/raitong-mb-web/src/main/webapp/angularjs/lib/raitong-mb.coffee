'use strict'
Subscriber = (subscriber)->
  this.gender = 'MALE'
  this.getReadableGender = ()->
    if this.gender is 'MALE'
      return 'Male'
    else if this.gender is 'FEMALE'
      return 'Female'
    else if this.gender is 'NOT_SPECIFIC'
      return 'Not Specific'
    else
      throw 'Invalid gender'
  this.isGenderActive = (gender)->
    if this.gender is gender
      return 'active btn-primary'
    else return undefined
  this.setGender = (gender)->
    this.gender = gender
    return
  this.haveSubscriptions = ()->
    !!this.subscriptions and !!this.subscriptions.length

  $.extend true, this, subscriber or {}
  return

Subscription = (subscription)->
  this.months = 1
  $.extend true, this, subscription or {}
