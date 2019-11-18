// components/item/itemInfo/index.js
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    goodsInfo: {
      type: Object,
      value: null,
      observer(data) {
        this.getPrice(data);
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
      if (data.defaultPrd) {
        this.setData({
          goodsPrice: data.products[0].prdRealPrice,
          markingPrice: data.products[0].prdLinePrice
        });
      } else {
        let priceArr = data.products.map(item => item.prdRealPrice);
        let markIngPriceArr = data.products.map(item => item.prdLinePrice);
        this.setData({
          goodsPrice: `${this.getMax(priceArr)}~${this.getMin(priceArr)}`,
          markingPrice: `${this.getMax(markIngPriceArr)}~${this.getMin(
            markIngPriceArr
          )}`
        });
      }
    },
    // 获取最小值
    getMax(arr) {
      return Math.min(...arr);
    },
    // 获取最大值
    getMin(arr) {
      return Math.max(...arr);
    },
    share(){
      this.setData({
        showShareDialog:true
      })
    },
    shareMoments(){
      this.triggerEvent('shareMoments')
    }
  }
});
