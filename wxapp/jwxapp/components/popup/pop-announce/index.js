var base = require("../base/base.js");
var util = require("../../../utils/util.js");
var app = getApp()
global.wxComponent({
  mixins: [base],

  /**
   * 组件的属性列表
   */
  properties: {
    title:String,
    success_title:{
      type:String,
      value:'确定' //默认为确定
    },
    cancel_title:{
      type:String,
      value:'取消' //默认为取消
    },
    if_cancel:{
      type:Boolean,
      value:false
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
     close(){
       this.setData({
         show:false
       })
     },
     onTap(){
       this.triggerEvent('determine')
     }
  }
});
