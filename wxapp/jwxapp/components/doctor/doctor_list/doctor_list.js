// components/usercenter/useraddress/useraddress.js
import util from '../../../utils/util'
var app = new getApp();
var imageUrl = app.globalData.imageUrl;
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    options: Object
  },

  /**
   * 组件的初始数据
   */
  data: {
    addressList: [],
    select: '',
    prevPage: null
  },

  ready () {
    
  },

  /**
   * 组件的方法列表
   */
  methods: {
    onLoad (op) {
      let that = this
      console.log(op)

    },
     /**
     * 请求收货地址
     */
    initData () {
      let that = this
   
    },

    stringOpts (opts) {
      let str = ''
      for (const key in opts) {
        const op = opts[key];
        str += key+'='+ op +'&'
      }
      str = str.slice(0, str.length-1)
      return str
    }
  },
})
