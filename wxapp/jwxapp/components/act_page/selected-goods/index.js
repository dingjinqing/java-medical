// components/act_page/selected-goods/index.js
const base = require('../../popup/base/base.js')
global.wxComponent({
  mixins:[base],
  /**
   * 组件的属性列表
   */
  properties: {
    goodsData:Array,
    customFooterLeft:{
      type:Boolean,
      value:false
    },
    customDelete:{
      type:Boolean,
      value:false
    },
    customControlNum:{
      type:Boolean,
      value:false
    }
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
    },
    deletCart({detail}){
      this.triggerEvent('deletCart',{...detail})
    },
    cartNumChange({detail}){
      this.triggerEvent('cartNumChange',{...detail})
    }
  }
})