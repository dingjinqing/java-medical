// components/item/recommend/index.js
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    pageName:{
      type:String,
      value: 'cart',//cart,bargainitem,groupbuyitem,new_search,orderlist,payment,search,item
    },
    pageParams:{
      type:Object,
      value:null
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    pageParams:null
  },

  /**
   * 组件的方法列表
   */
  methods: {
    init(){

    }
  }
})
