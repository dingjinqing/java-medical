global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    couponList: {
      type: Array,
      value: null,
      observer(newVal) {
        if (newVal) {
          let newList = newVal.map(item => {
            let newItem = JSON.parse(JSON.stringify(item))
            if (newItem.actCode === 'voucher') {
              if (newItem.useConsumeRestrict === 1) {
                newItem.text = `${this.$t('components.decorate.full')}${
                  newItem.leastConsume
                  }${this.$t('components.decorate.reduce')}￥${newItem.denomination}`;
              } else {
                newItem.text = `${this.$t('components.decorate.coupon')}${this.$t(
                  'components.decorate.reduce'
                )}￥${newItem.denomination}`;
              }
            } else if (newItem.actCode === 'discount') {
              if (newItem.useConsumeRestrict === 1) {
                newItem.text = `${this.$t('components.decorate.full')}${
                  newItem.leastConsume
                  }${this.$t('components.decorate.hit')}${newItem.denomination}${this.$t('components.decorate.fracture')}`;
              } else {
                newItem.text = `${this.$t('components.decorate.coupon')}${this.$t(
                  'components.decorate.hit'
                )}${newItem.denomination}${this.$t('components.decorate.fracture')}`;
              }
            }
            return newItem
          })
          this.setData({
            coupons: newList
          })
        }
      }
    },
    promotion:Array,
    goodsGifts:Array
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
    },
    showPromotion() {
      console.log(111)
      this.setData({
        showPromotionDialog: true
      })
    },
    showGift(e){
      let {id} = e.currentTarget.dataset
      this.setData({
        showGiftsDialog:true,
        giftInfo:this.data.goodsGifts.find(item=>item.id === id)
      })
    }
  }
});