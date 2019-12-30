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
      let { prdNumber, limitBuyNum } = this.data.limitInfo;
      let goodsNum = limitBuyNum === 0 ? 1 : limitBuyNum,
        canMinus = false,
        canPlus = true;
      if (prdNumber <= limitBuyNum) {
        goodsNum = prdNumber <= 0 ? 0 : prdNumber;
        canPlus = false;
      }
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
      this.setData({
        goodsNum:
          d.type === "plus" ? this.data.goodsNum + 1 : this.data.goodsNum - 1
      });
      if (
        (limitBuyNum === 0 && this.data.goodsNum <= 1) ||
        this.data.goodsNum <= limitBuyNum
      ) {
        this.setData({
          canMinus: false
        });
      } else {
        this.setData({
          canMinus: true
        });
      }
      if (
        (limitMaxNum !== 0 &&
          prdNumber <= limitMaxNum &&
          this.data.goodsNum >= prdNumber) ||
        (limitMaxNum === 0 && this.data.goodsNum >= prdNumber) ||
        this.data.goodsNum >= limitMaxNum
      ) {
        this.setData({
          canPlus: false
        });
      } else {
        this.setData({
          canPlus: true
        });
      }
      this.triggerEvent("goodsNumData", { goodsNum: this.data.goodsNum });
    }
  }
});
