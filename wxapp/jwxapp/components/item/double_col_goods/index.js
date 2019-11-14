const util = require("../../../utils/util.js");
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    goodsData: {
      type: Object,
      value: null,
      observer(val){
        console.log(val)
        this.init()
      }
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
    init(){
      this.getCouponDesc()
    },
    getCouponDesc(){
       let coupon = this.data.goodsData.goodsActivities.filter(item => item.activityType === 19).map(item=> {
          let data = JSON.parse(JSON.stringify(item))
          let str = ''
          if (data.actCode === 'voucher'){
            if (data.useConsumeRestrict === 1){
              str = `满${data.leastConsume}减${data.denomination}`
            } else {
              str = `优惠券减${data.denomination}`
            }
          } else {
            if (data.useConsumeRestrict === 1) {
              str = `满${data.leastConsume}打${data.denomination}折`
            } else {
              str = `优惠券打${data.denomination}折`
            }
          }
          data.couponDesc = str
          return data
        })
        this.setData({
          coupons: coupon
        })
        console.log(this.data.coupons)
    },
    toItem(){
      util.jumpLink(`pages/item/item?goodsId=${this.data.goodsData.goodsId}`,'navigateTo')
    }
  }
});
