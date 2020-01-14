// components/item/single_col_goods/index.js
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    isEdit:{
      type:Boolean,
      value:false
    },
    enableNumEdit:{
      type:Boolean,
      value:false
    },
    goodsData: {
      type: Object,
      value: null
    },
    delMarket: {
      type: Number,
      value: null
    },
    showCart: {
      type: Object,
      value: null,
      observer(val){
        let typeClass = {
          
        }
      }
    }
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {

  }
})
