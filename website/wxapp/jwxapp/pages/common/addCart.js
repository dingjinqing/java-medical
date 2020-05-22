const util = require("../../utils/util.js");
var base = {
  properties: {
    
  },

  /**
   * 组件的方法列表
   */
  methods: {
    getAddCartGoods(data){
      let {goodsId, activityId=null, activityType=null} = data.detail
      this.getGoodsInfo(goodsId,activityId,activityType)
    },
    getGoodsInfo(goodsId,activityId,activityType){
      util.api("/api/wxapp/goods/detail",res=>{
        if(res.error === 0){
          let productsInfo = {
            activity:res.content.activity,
            defaultPrd:res.content.defaultPrd,
            goodsId:res.content.goodsId,
            goodsImgs:res.content.goodsImgs,
            goodsNumber:res.content.goodsNumber,
            limitBuyNum:res.content.limitBuyNum,
            limitMaxNum:res.content.limitMaxNum,
            products:res.content.products
          }
          this.setData({
            productsInfo,
            showSpec:true
          })
        }
      },{
        goodsId: goodsId,
        activityId: activityId,
        activityType: activityType,
        userId: util.getCache("user_id"),
        lon: null,
        lat: null
      })
    },
    bindCloseSpec(){
      this.setData({
        showSpec:false
      })
    },
    getProductData(e){
      this.setData({
        product:e.detail,
        limitInfo:{
          activityType:this.data.productsInfo.activity ? this.data.productsInfo.activity.activityType : null,
          limitBuyNum:e.detail.limitBuyNum,
          limitMaxNum:e.detail.limitMaxNum,
          prdNumber:e.detail.prdNumber
        }
      })
    },
    getGoodsNum(e) {
      this.setData({
        productInfo: { ...this.data.product, goodsNum:e.detail.goodsNum }
      });
      console.log(this.data.productInfo)
    },
  }
};
module.exports = base;