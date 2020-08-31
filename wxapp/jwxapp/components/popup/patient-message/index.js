var base = require("../base/base.js");
var util = require("../../../utils/util.js");
var app = getApp()
global.wxComponent({
  mixins: [base],

  /**
   * 组件的属性列表
   */
  properties: {
     patientMessage:{
       type:Object,
       value:null
     }
  },

  /**
   * 组件的初始数据
   */
  data: {
    imageUrl:app.globalData.imageUrl
  },

  /**
   * 组件的方法列表
   */
  methods: {

  }
});
