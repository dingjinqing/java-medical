const base = require("../../../popup/base/base.js");
const actPrdType = {
  1:{prdListName:"groupBuyPrdMpVos",prdRealPrice:'groupPrice',prdLinePrice:'prdPrice',multiSkuAct:true},
  3:{prdRealPrice:'bargainPrice',multiSkuAct:false},
  5:{prdListName:"actProducts",prdRealPrice:'secKillPrice',prdLinePrice:'prdPrice',multiSkuAct:true},
  6:{prdListName:"reducePricePrdMpVos",prdRealPrice:"reducePrice",prdLinePrice:'prdPrice',multiSkuAct:true},
  18:{prdListName:"firstSpecialPrdMpVos",prdRealPrice:'firsSpecialPrice',prdLinePrice:'prdPrice',multiSkuAct:true},
  22:{prdListName:"gradePrdMpVos",prdRealPrice:'gradePrice',prdLinePrice:'prdPrice',multiSkuAct:true},
  98:{prdListName:"gradeReducePrdVos",prdRealPrice:'activityPrice',prdLinePrice:'prdPrice',multiSkuAct:true}
}
global.wxComponent({
  mixins: [base],
  /**
   * 组件的属性列表
   */
  properties: {
    productsInfo: {
      type: Object,
      value: null,
      observer(val) {
        if (val.defaultPrd === true){
          // 活动规格限制
          let actLimit = {}
          if(val.activity){
            actLimit.activityType = val.activity.activityType
            if(val.activity.limitMaxNum){
              actLimit.limitMaxNum = val.activity.limitMaxNum
            }
            if(actPrdType[val.activity.activityType]['prdListName']){
              actLimit.prdNumber = val.activity[actPrdType[val.activity.activityType]['prdListName']][0].stock
            }
            if(val.activity.limitBuyNum){
              actLimit.limitBuyNum = val.activity.limitBuyNum
            }
          }
          this.triggerEvent("productData", {
            goodsId: val.goodsId,
            ...val.products[0],
            limitBuyNum:val.limitBuyNum,
            limitMaxNum:val.limitMaxNum,
            ...actLimit
          });
        } else {
          if(val.activity){
            if(actPrdType[val.activity.activityType].multiSkuAct){
              let activityPrds = val.activity[actPrdType[val.activity.activityType]['prdListName']].map(({productId:prdId,stock:prdNumber,[actPrdType[val.activity.activityType].prdRealPrice]:prdRealPrice,[actPrdType[val.activity.activityType].prdLinePrice]:prdLinePrice,isGradePrice=null}) => {
                return {prdId,prdNumber,prdRealPrice,prdLinePrice,isGradePrice}
              })
              this.setData({
                activityPrds
              })
            }
          }
          this.formatSpec(val.products);
        }
      }
    }
  },
  /**
   * 组件的初始数据
   */
  data: {
    specList: null,
    checkedProduct: null
  },

  /**
   * 组件的方法列表
   */
  methods: {
    // 格式化规格信息
    formatSpec(spec) {
      let specList = {};
      let specAggregation = spec.map(item => item.prdDesc.split(";"));
      specAggregation.map(item => {
        item.map(d => {
          let specItem = d.split(":");
          if (!specList[specItem[0]]) {
            specList[specItem[0]] = [
              { specName: specItem[1], isChecked: true }
            ];
          } else {
            if (specList[specItem[0]].some(item => item.specName === specItem[1])) return;
            specList[specItem[0]] = [
              ...specList[specItem[0]],
              { specName: specItem[1], isChecked: false }
            ];
          }
        });
      });
      this.setData({
        specList: specList
      });
      this.getCheckedProduct(specList);
    },
    // 切换规格按钮
    tapSpec(e) {
      let d = this.eventData(e);
      let pastIndex = this.data.specList[d.key].findIndex(
        item => item.isChecked === true
      );
      this.setData({
        [`specList.${d.key}[${
          pastIndex !== -1 ? pastIndex : 0
        }].isChecked`]: false,
        [`specList.${d.key}[${d.index}].isChecked`]: true
      });
      this.getCheckedProduct(this.data.specList);
    },
    // 获取选中组合后规格信息
    getCheckedProduct(specList) {
      let str = "";
      for (let i in specList) {
        str += `;${i}:${
          specList[i].filter(item => item.isChecked)[0].specName
        }`;
      }
      str = str.substring(1);
      let productTarget = this.data.productsInfo.products.filter(
        item => item.prdDesc === str
      )[0];
      // if(this.data.productsInfo.activity && !actPrdType[this.data.productsInfo.activity.activityType].multiSkuAct){
      //   productTarget.prdLinePrice = productTarget.prdRealPrice
      //   productTarget.prdRealPrice = this.data.productsInfo.activity[actPrdType[this.data.productsInfo.activity.activityType].prdRealPrice]
      // }
      this.setData({
        checkedProduct: productTarget
      });
      let { limitBuyNum, limitMaxNum } = this.data.productsInfo;
      let actLimit = {}
      // 活动规格的限购数量
      if(this.data.productsInfo.activity){
        actLimit.activityType = this.data.productsInfo.activity.activityType
        if(this.data.productsInfo.activity.limitBuyNum){
          actLimit.limitMaxNum = this.data.productsInfo.activity.limitMaxNum
        }
        if(this.data.activityPrds){
          actLimit.prdNumber = this.data.activityPrds.find(item=>item.prdId === productTarget.prdId).prdNumber
        }
        if(this.data.productsInfo.activity.limitBuyNum){
          actLimit.limitBuyNum = this.data.productsInfo.activity.limitBuyNum
        }
      }
      this.triggerEvent("productData", {
        goodsId: this.data.productsInfo.goodsId,
        ...productTarget,
        limitBuyNum,
        limitMaxNum,
        ...actLimit
      });
    },
    bindClose(){
      this.triggerEvent('close')
    }
  }
});
