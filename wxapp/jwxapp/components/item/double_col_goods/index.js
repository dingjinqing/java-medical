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
      this.handleToGoodsActivities()
    },
    // 处理商品活动
    handleToGoodsActivities() {
      // 处理商品图片底部出现的活动条
      let goosItem = JSON.parse(JSON.stringify(this.data.goodsData))
      let arr = []
      goosItem.goodsActivities.forEach((item, index) => {
        switch (item.activityType) {
          case 22:
            goosItem.isMembershipExclusive = true
            break
          case 18:
            goosItem.isFirstOrder = true
            break
          case 6:
            goosItem.isLimitedPrice = true
            break
          case 3:
            goosItem.isBargain = true
            break
          case 1:
            goosItem.assemble = true
        }
        this.handleToActivitiesLabel(item,arr)
      })
      goosItem.activityLabelData = arr
      this.setData({
        goosItem
      })
    },
    // 处理价格上方显示的label条
    handleToActivitiesLabel(item,arr) {
      let obj = {}
      switch (item.activityType) {
        case 1:
          obj.text = '拼团'
          break
        case 3:
          obj.text = '砍价'
          break
        case 5:
          obj.text = '秒杀'
          break
        case 6:
          obj.text = '限时降价'
          break
        case 10:
          obj.text = '预售'
          break
        case 18:
          obj.text = '首单特惠'
          break
        case 19:
          if (item.actCode === 'voucher') {
            if (item.useConsumeRestrict === 1) {
              obj.text = `满${item.leastConsume}减￥${item.denomination}`
            } else {
              obj.text = `优惠卷减￥${item.denomination}`
            }
          } else if (item.actCode === 'discount') {
            if (item.useConsumeRestrict === 1) {
              obj.text = `满${item.leastConsume}打￥${item.denomination}`
            } else {
              obj.text = `优惠卷打￥${item.denomination}折`
            }
          }
          break
        case 20:
          obj.text = '满减'
          break
        case 21:
          obj.text = '会员满减价'
          break
        case 22:
          obj.text = '会员专享'
          break
      }
      arr.push(obj)
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
