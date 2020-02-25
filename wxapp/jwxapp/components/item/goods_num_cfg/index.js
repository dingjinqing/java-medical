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
    }
  },

  /**
   * 组件的初始数据
   */
  data: {},

  /**
   * 组件的方法列表
   */
  methods: {
    init() {
      let { limitBuyNum } = this.data.limitInfo
      let goodsNum = (limitBuyNum && limitBuyNum > 0) ?  limitBuyNum : 1;
      let canMinus = false,canPlus = true;
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
        limitMaxNum = limitMaxNum && limitMaxNum > 0 ? limitMaxNum : prdNumber
        console.log(goodsNum,limitMaxNum,limitBuyNum)
        this.setData({
          canMinus: goodsNum <= limitBuyNum ? false : true,
          canPlus: goodsNum < limitMaxNum ? true : false,
          goodsNum
        })
        this.triggerEvent("goodsNumData", { goodsNum: this.data.goodsNum });
    }
  }
});
