const util = require('../../../utils/util.js');
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    goodsData: {
      type: Object,
      value: null,
      observer(val) {
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
      this.handleToGoodsActivities();
    },
    // 处理商品活动
    handleToGoodsActivities() {
      // 处理商品图片底部出现的活动条
      let goodsItem = JSON.parse(JSON.stringify(this.data.goodsData));
      let arr = [];
      goodsItem.goodsActivities.forEach((item, index) => {
        switch (item.activityType) {
          case 22:
            goodsItem.isMembershipExclusive = true;
            break;
          case 18:
            goodsItem.isFirstOrder = true;
            break;
          case 6:
            goodsItem.isLimitedPrice = true;
            break;
          case 3:
            goodsItem.isBargain = true;
            break;
          case 1:
            goodsItem.assemble = true;
            goodsItem.groupDiscountPrice = item.discountPrice
        }
        this.handleToActivitiesLabel(item, arr);
      });
      goodsItem.activityLabelData = arr;
      this.setData({
        goodsItem
      });
    },
    // 处理价格上方显示的label条
    handleToActivitiesLabel(item, arr) {
      let obj = {};
      switch (item.activityType) {
        case 1:
          obj.text = this.$t('components.decorate.assemble');
          this.setData({
            groupDiscountPrice:item.discountPrice
          })
          break;
        case 3:
          obj.text = this.$t('components.decorate.bargain');
          break;
        case 5:
          obj.text = this.$t('components.decorate.seckill');
          break;
        case 6:
          obj.text = this.$t('components.decorate.limitedPriceReduction');
          break;
        case 10:
          obj.text = this.$t('components.decorate.advanceSale');
          break;
        case 19:
          if (item.actCode === 'voucher') {
            if (item.useConsumeRestrict === 1) {
              obj.text = `${this.$t('components.decorate.full')}${
                item.leastConsume
              }${this.$t('components.decorate.reduce')}￥${item.denomination}`;
            } else {
              obj.text = `${this.$t('components.decorate.coupon')}${this.$t(
                'components.decorate.reduce'
              )}￥${item.denomination}`;
            }
          } else if (item.actCode === 'discount') {
            if (item.useConsumeRestrict === 1) {
              obj.text = `${this.$t('components.decorate.full')}${
                item.leastConsume
              }${this.$t('components.decorate.hit')}￥${item.denomination}`;
            } else {
              obj.text = `${this.$t('components.decorate.coupon')}${this.$t(
                'components.decorate.hit'
              )}￥${item.denomination}${this.$t('components.decorate.fracture')}`;
            }
          }
          break;
        case 20:
          obj.text = this.$t('components.decorate.fullReduction');
          break;
        case 21:
          obj.text = this.$t('components.decorate.fullMemberDiscount');
          break;
      }
      if (!obj.text) return;
      arr.push(obj);
    },
    // getCouponDesc(){
    //    let coupon = this.data.goodsData.goodsActivities.filter(item => item.activityType === 19).map(item=> {
    //       let data = JSON.parse(JSON.stringify(item))
    //       let str = ''
    //       if (data.actCode === 'voucher'){
    //         if (data.useConsumeRestrict === 1){
    //           str = `满${data.leastConsume}减${data.denomination}`
    //         } else {
    //           str = `优惠券减${data.denomination}`
    //         }
    //       } else {
    //         if (data.useConsumeRestrict === 1) {
    //           str = `满${data.leastConsume}打${data.denomination}折`
    //         } else {
    //           str = `优惠券打${data.denomination}折`
    //         }
    //       }
    //       data.couponDesc = str
    //       return data
    //     })
    //     this.setData({
    //       coupons: coupon
    //     })
    //     console.log(this.data.coupons)
    // },
    toItem() {
      util.jumpLink(
        `pages/item/item?goodsId=${this.data.goodsData.goodsId}&activityType=${this.data.goodsData.activityType}&activityId=${this.data.goodsData.activityId}`,
        'navigateTo'
      );
    }
  }
});
