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
    
  },

  /**
   * 组件的初始数据
   */
  data: {
    imageUrl: imageUrl
  },

  /**
   * 组件的方法列表
   */
  methods: {

  }
})
