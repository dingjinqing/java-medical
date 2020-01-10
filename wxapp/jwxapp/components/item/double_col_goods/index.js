const util = require('../../../utils/util.js');
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    goodsData: {
      type: Object,
      value: null
    },
    delMarket: {
      type: Number,
      value: null
    },
    showCart: {
      type: Object,
      value: null
    }
  },
  /**
   * 组件的初始数据
   */
  lifetimes:{
    ready(){
      this.init()
    }
  },
  data: {},

  /**
   * 组件的方法列表
   */
  methods: {
    init() {
      this.handleToGoodsActivities();
      this.handleButtonSet()
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
            groupDiscountPrice: item.discountPrice
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
              )}￥${item.denomination}${this.$t('decorate.fracture')}`;
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
    // 处理购买按钮和划线价
    handleButtonSet() {
      let data = this.data
      const cartType = {
        '1-0': {
          className: 'iconfont icontianjia1',
          styleName:`color:${data.main_setting.comColor}`
        }, //开启购物车按钮-样式 
        '1-1': {
          className: 'iconfont icongouwuche5',
          styleName:`color:${data.main_setting.comColor}`
        },
        '1-2': {
          className: 'boder-button',
          styleName:`background-color:${data.main_setting.comColor};color:#fff;border-color:transparent;`,
          text:'马上抢'
        },
        '1-3': {
          className: 'boder-button',
          styleName:`background-color:#fff;color:#666;border-color:#888;`,
          text:'购买'
        },
        '0-1': {
          className:'gray-text line-through',
          text:`市场价: ${data.goodsData.linePrice}`
        }, //关闭购物车按钮-划线价类别
        '0-2': {
          className:'gray-text',
          text:`销量: ${data.goodsData.goodsSaleNum}`
        },
        '0-3': {
          className:'gray-text',
          text:`评价数: ${data.goodsData.commentNum}`
        },
      }
      let textContent = cartType[`${data.showCart.show_cart}-${data.showCart.show_cart ? data.showCart.cart_type : data.delMarket}`] || ''
        this.setData({
          textContent
        })
    },
    toItem() {
      util.jumpLink(
        `pages/item/item?goodsId=${this.data.goodsData.goodsId}&activityType=${this.data.goodsData.activityType}&activityId=${this.data.goodsData.activityId}`,
        'navigateTo'
      );
    }
  }
});