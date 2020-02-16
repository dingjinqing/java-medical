// components/popup/lottery-award-dialog/index.js
var util = require("../../../utils/util.js");
var base = require('../base/base.js');
var app = getApp()
var imageUrl = app.globalData.imageUrl;
global.wxComponent({
  mixins: [base],
  /**
   * 组件的属性列表
   */
  properties: {
    prizeInfo: Object
  },

  /**
   * 组件的初始数据
   */
  data: {
    show: false,
    imageUrl: imageUrl
  },

  observers: {
    prizeInfo: function() {
      console.log()
    }
  },

  /**
   * 组件的方法列表
   */
  methods: {

  },
  show: function() {
    console.log(this)
  }
})
