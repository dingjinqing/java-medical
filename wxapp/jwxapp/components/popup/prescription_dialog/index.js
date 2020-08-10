var base = require("../base/base.js");
var util = require("../../../utils/util.js");
global.wxComponent({
  mixins: [base],

  /**
   * 组件的属性列表
   */
  properties: {
    prescriptionData:Object
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
    viewRxSearch(){
      return
      util.jumpLink(`pages/search/search${util.getUrlParams({
        pageFrom:102,
        outerPageParam:JSON.stringify({
          prescriptionCode:this.data.prescriptionData.prescriptionCode
        })
      })}`)
    },
    viewRepurchase(){
      // if(this.data.prescriptionData.isUsed === 1){
        util.jumpLink(`pages1/repurchaselist/repurchaselist${util.getUrlParams({
          prescriptionCode:this.data.prescriptionData.prescriptionCode
        })}`)
      // } else {
      //   let goodsList = this.data.prescriptionData.itemList.map(item=>{
      //     let {goodsId,medicinePrice:prdRealPrice,dragSumNum:goodsNum,prdId} = item
      //     return {goodsId,prdRealPrice,goodsNum,prdId}
      //   })
      //   util.jumpLink(`pages/checkout/checkout${util.getUrlParams({
      //     goodsList:JSON.stringify(goodsList),
      //     isPrescription:1,
      //     prescriptionCode:this.data.prescriptionData.prescriptionCode
      //   })}`)
      // }
    }
  }
});
