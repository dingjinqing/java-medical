const util = require("../../../utils/util.js");
const priceName = {
  1:{prdListName:"groupBuyPrdMpVos",prdRealPrice:'groupPrice',prdLinePrice:'prdPrice',multiSkuAct:true},
  3:{prdRealPrice:'bargainPrice',multiSkuAct:false},
  5:{prdListName:"actProducts",prdRealPrice:'secKillPrice',prdLinePrice:'prdPrice',multiSkuAct:true}
}
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    goodsInfo: {
      type: Object,
      value: null,
      observer(data) {
        console.log(data)
        if(!data.activity){
          this.getPrice(data);
        } else {
            let defaultPrd = data.defaultPrd
            let products = priceName[data.activity.activityType].multiSkuAct ? data.activity[priceName[data.activity.activityType].prdListName].map(item => {
              let {productId:prdId,[priceName[data.activity.activityType].prdRealPrice]:prdRealPrice,[priceName[data.activity.activityType].prdLinePrice]:prdLinePrice} = item
              return {prdId,prdRealPrice,prdLinePrice}
            }) : data.activity[priceName[data.activity.activityType].prdRealPrice]
            this.getPrice({defaultPrd,products,activityType:data.activity.activityType})
        }
        this.setData({
          isCollected:data.isCollected
        })
      }
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    goodsPrice: null,
    markingPrice: null,
    showShareDialog:false
  },

  /**
   * 组件的方法列表
   */
  methods: {
    // 商品价格/划线价
    getPrice(data) {
      if(data.activityType && priceName[data.activityType].multiSkuAct){
        if (data.defaultPrd) {
          this.setData({
            goodsPrice: data.products[0].prdRealPrice,
            markingPrice: data.products[0].prdLinePrice
          });
        } else {
          let priceArr = data.products.map(item => item.prdRealPrice);
          let markIngPriceArr = data.products.map(item => item.prdLinePrice);
          this.setData({
            goodsPrice: `${this.getMin(priceArr)}~${this.getMax(priceArr)}`,
            markingPrice: `${this.getMin(markIngPriceArr)}~${this.getMax(
              markIngPriceArr
            )}`
          });
        }
      } else {
        this.setData({
          goodsPrice:data.products,
          markingPrice: data.defaultPrd ? this.data.goodsInfo.products[0].prdLinePrice : `${this.getMin(this.data.goodsInfo.products.map(item => item.prdRealPrice))}~${this.getMin(this.data.goodsInfo.products.map(item => item.prdRealPrice))}`
        })
      }
    },
    // 获取最小值
    getMin(arr) {
      return Math.min(...arr);
    },
    // 获取最大值
    getMax(arr) {
      return Math.max(...arr);
    },
    share(){
      this.setData({
        showShareDialog:true
      })
    },
    shareMoments(){
      this.triggerEvent('shareMoments')
    },
    // 切换收藏
    toogleCollect(){
      let {goodsId} = this.data.goodsInfo
      const apiMap = new Map([
        [true,{api:'/api/wxapp/cancel/collect',msg:'已取消',error:'取消失败'}],
        [false,{api:'/api/wxapp/add/collect',msg:'收藏成功',error:'收藏失败'}]
      ])
      util.api(apiMap.get(this.data.isCollected).api,res=>{
        if(res.error === 0){
          util.toast_success(apiMap.get(this.data.isCollected).msg)
          this.setData({
            isCollected:!this.data.isCollected
          })
        } else {
          util.toast_fail(apiMap.get(this.data.isCollected).error)
        }
      },{goodsId})
    },
    getNeedTemplateId(e){
      let typs = e.currentTarget.dataset.typs
      util.getNeedTemplateId(typs)
    }
  }
});
