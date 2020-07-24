
import util from '../../../utils/util'
var app = new getApp();
var imageUrl = app.globalData.imageUrl;
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    chatContent: Object
  },
  options:{
    multipleSlots:true
  },

  /**
   * 组件的初始数据
   */
  data: {
  
  },

  ready () {
    console.log(this.data.chatContent)
  },

  /**
   * 组件的方法列表
   */
  methods: {
    onLoad (op) {
      let that = this
    },

  },
})
