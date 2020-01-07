// components/item/coupon&promotion/index.js
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    couponList: {
      type: Array,
      value: null
    },
    promotion:Array
  },

  /**
   * 组件的初始数据
   */
  data: {},

  /**
   * 组件的方法列表
   */
  methods: {
    showCouponDialog() {
      this.setData({
        show_recommend_coupons: true
      });
    }
  }
});
