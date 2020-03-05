const base = require("../../../popup/base/base.js");
global.wxComponent({
  mixins: [base],
  /**
   * 组件的属性列表
   */
  properties: {
    giftsInfo:{
      type:Object,
      observer(val){
        let goodsList = val.goodsGiftPrdMpVos.map(item=>{
          let {prdImg:goodsImg,prdPrice:realPrice,goodsName,prdDesc} = item
          return {
            goodsImg,
            realPrice,
            goodsName,
            prdDesc
          }
        })
        this.setData({
          goodsList
        })
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
});
