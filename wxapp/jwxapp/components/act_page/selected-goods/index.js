// components/act_page/selected-goods/index.js
const base = require('../../popup/base/base.js')
global.wxComponent({
  mixins:[base],
  /**
   * 组件的属性列表
   */
  properties: {
    goodsData:Array
  },

  /**
   * 组件的初始数据
   */
  data: {
    isEdit:false
  },

  /**
   * 组件的方法列表
   */
  methods: {
    editToogle(){
      this.setData({
        isEdit:!this.data.isEdit
      })
    },
    bindClose(){
      this.setData({
        show:false,
        isEdit:false
      })
    },
    cartChange(){	
      this.triggerEvent('cartChange')	
    }
  }
})
