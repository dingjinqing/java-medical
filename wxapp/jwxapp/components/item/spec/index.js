global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    productsInfo: {
      type: Object,
      value: null
    },
    limitInfo: {
      type: Object,
      value: null
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    showSpec: false,
    product: null,
    goodsNum: null,
    product: null
  },

  /**
   * 组件的方法列表
   */
  lifetimes: {
    ready() {
      console.log(this);
    }
  },
  methods: {
    specDialogShow() {
      this.setData({
        showSpec: true
      });
    },
    getProductData(data) {
      this.setData({
        product: data.detail
      });
      this.triggerEvent("product", data.detail);
    },
    getGoodsNum(data) {
      this.setData({
        goodsNum: data.detail
      });
      this.setProductInfo();
    },
    setProductInfo() {
      this.setData({
        productInfo: { ...this.data.product, ...this.data.goodsNum }
      });
      this.triggerEvent("getProductInfo", this.data.productInfo);
    }
  }
});
