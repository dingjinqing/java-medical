const util = require("../../../utils/util.js");
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    origin: {
      type: String,
      value: ""
    },
    limitInfo: {
      type: Object,
      value: null,
      observer(val) {
        console.log(val)
        this.init();
      }
    },
    triggerButton: {
      type: String,
      value: ''
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    goodsNum:1
  },

  /**
   * 组件的方法列表
   */
  methods: {
    init() {
      let { limitBuyNum,limitMaxNum,prdNumber } = this.data.limitInfo
      let goodsNum = (limitBuyNum && limitBuyNum > 0) ?  limitBuyNum : 1;
      limitMaxNum = limitMaxNum && limitMaxNum > 0 && prdNumber > limitMaxNum ? limitMaxNum : prdNumber
      let canMinus = false;
      let canPlus = goodsNum < limitMaxNum ? true : false
      this.setData({
        goodsNum,
        canMinus,
        canPlus
      });
      this.triggerEvent("goodsNumData", { goodsNum });
    },
    setNum(e) {
        let d = this.eventData(e);
        let { prdNumber, limitBuyNum, limitMaxNum } = this.data.limitInfo;
        let goodsNum = d.type === "plus" ? this.data.goodsNum + 1 : this.data.goodsNum - 1
        limitBuyNum = (limitBuyNum && limitBuyNum > 0) ?  limitBuyNum : 1;
        limitMaxNum = limitMaxNum && limitMaxNum > 0 && prdNumber > limitMaxNum ? limitMaxNum : prdNumber
        console.log(goodsNum,limitMaxNum,limitBuyNum)
        this.setData({
          canMinus: goodsNum <= limitBuyNum ? false : true,
          canPlus: goodsNum < limitMaxNum ? true : false,
          goodsNum
        })
        this.triggerEvent("goodsNumData", { goodsNum: this.data.goodsNum });
    },
    goodsNumChange(e){
      let {value:num} = e.detail
      let {limitBuyNum, limitMaxNum, prdNumber} = this.data.limitInfo
      limitBuyNum = (limitBuyNum && limitBuyNum > 0) ?  limitBuyNum : 1;
      limitMaxNum = limitMaxNum && limitMaxNum > 0 && prdNumber > limitMaxNum ? limitMaxNum : prdNumber
      if(num < limitBuyNum){
        util.showModal('提示','商品数量低于最小可购买数量')
        num = limitBuyNum
      }
      if(num > limitMaxNum){
        util.showModal('提示','商品数量高于最高可购买数量')
        num = limitMaxNum
      }
      this.setData({
        canMinus: num <= limitBuyNum ? false : true,
        canPlus: num < limitMaxNum ? true : false,
        goodsNum:Number(num)
      })
      this.triggerEvent("goodsNumData", { goodsNum: this.data.goodsNum });
    }
  }
});
