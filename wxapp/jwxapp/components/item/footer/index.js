const util = require("../../../utils/util.js");
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    origin: {
      type: String,
      value: "item"
    },
    productInfo:{
      type: Object,
      value: null
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
    // 添加购物车
    addCart() {
      let { goodsNum: goodsNumber, prdId } = this.data.productInfo
      util.api(
        "/api/wxapp/cart/add",
        res => {
          console.log(res);
          this.triggerEvent('close')
        },
        {
          goodsNumber: goodsNumber,
          prdId: prdId
        }
      );
    },
    test() {
      util.jumpLink("pages/item/item", "navigateTo")
    },
    // 返回首页
    backHome() {
      util.jumpLink("pages/index/index", "redirectTo")
    },
    toCartList(){
      util.jumpLink("pages/cart/cart","navigateTo")
    }
  }
})
