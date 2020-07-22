const util = require('../../../utils/util.js')
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    isEdit: {
      type: Boolean,
      value: false
    },
    enableNumEdit: {
      type: Boolean,
      value: false
    },
    goodsData: {
      type: Object,
      value: null,
      observer(val){
        this.resetlimitData()
      }
    },
    delMarket: {
      type: Number,
      value: null
    },
    showCart: {
      type: Object,
      value: null
    },
    customDelete:{
      type:Boolean,
      value:false
    },
    customControlNum:{
      type:Boolean,
      value:false
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    canMinus: false,
    canPlus: false
  },
  lifetimes: {
    ready() {
      this.setCartData()
      this.setDelMarket()
      this.setGoodsActShow()
    }
  },

  /**
   * 组件的方法列表
   */
  methods: {
    setCartData() {
      if (!this.data.showCart) return
      let data = this.data
      let typeClass = {
        0: {
          className: 'iconfont icontianjia1',
          styleName: `color:${data.main_setting.comColor}`
        },
        1: {
          className: 'iconfont icongouwuche1',
          styleName: `color:${data.main_setting.comColor}`
        },
        2: {
          className: 'boder-button',
          styleName: `background-color:${data.main_setting.comColor};color:#fff;border-color:transparent;`,
          text: '马上抢'
        },
        3: {
          className: 'boder-button',
          styleName: `background-color:#fff;color:#666;border-color:#888;`,
          text: '购买'
        }
      }
      this.setData({
        cartData: typeClass[this.data.showCart.cart_type]
      })
    },
    setDelMarket() {
      if (!this.data.delMarket) return
      let data = this.data
      let delMarketType = {
        1: {
          className: 'gray-text line-through',
          text: `${data.goodsData.linePrice ? '￥' + data.goodsData.linePrice : ''}`
        }, //关闭购物车按钮-划线价类别
        2: {
          className: 'gray-text',
          text: `${data.goodsData.goodsSaleNum ? '销量: ' + data.goodsData.goodsSaleNum : ''}`
        },
        3: {
          className: 'gray-text',
          text: `${data.goodsData.commentNum ? '评价数: ' + data.goodsData.commentNum : ''}`
        }
      }
      this.setData({
        delMarketData: this.data.delMarket === 1 && data.goodsData.linePrice >= data.goodsData.realPrice ? null : delMarketType[this.data.delMarket]
      })
    },
    // 删除购物车
    delCartGoods(e) {
      if(this.data.customDelete){
        this.triggerEvent('deletCart',{...this.data.goodsData})
        return
      }
      util.api(
        '/api/wxapp/cart/remove',
        res => {
          console.log(res)
          if (res.error === 0) {
            this.triggerEvent('cartChange') //购物车改变触发
          }
        },
        { cartId: e.currentTarget.dataset.cartId }
      )
    },
    // 更改购物车商品数量
    changeGoodsNum(e) {
      let { type } = e.currentTarget.dataset
      let { cartNumber, productId, activityType, activityId } = this.data.goodsData
      if(this.data.customControlNum){
        this.triggerEvent('cartNumChange',{type,...this.data.goodsData})
        return
      }
      util.api('/api/wxapp/cart/add', res => {
        if (res.error == 0) {
          this.triggerEvent('cartChange') //购物车改变触发
        } else {
          util.showModal('提示', res.message)
          return false
        }
      }, {
          goodsNumber: type === 'plus' ? cartNumber + 1 : cartNumber - 1,
          prdId: productId,
          activityType: activityType,
          activityId: activityId,
          type: 1
      })
    },
    changeCartInput(e){
      let cartNumber = parseInt(e.detail.value)
      let { productId, activityType, activityId } = this.data.goodsData
      if(this.data.customControlNum){
        this.triggerEvent('customCartNum',{...this.data.goodsData,goodsNumber:cartNumber})
        return
      }
      if(!isNaN(cartNumber)){
        util.api('/api/wxapp/cart/add', res => {
          if (res.error == 0) {
            this.triggerEvent('cartChange') //购物车改变触发
          } else {
            util.showModal('提示', res.message)
            return false
          }
        }, {
            goodsNumber: cartNumber,
            prdId: productId,
            activityType: activityType,
            activityId: activityId,
            type: 1
          })
      }
    },
    // 打开规格弹窗
    showSpecDialog() {
      this.triggerEvent('showSpecDialog', this.data.goodsData)
    },
    // 去商品详情页
    goItem() {
      let { goodsId, activityId, activityType } = this.data.goodsData
      util.jumpLink(
        `pages/item/item?gid=${goodsId}&atp=${activityType}&aid=${activityId}`,
        'navigateTo'
      )
    },
    setGoodsActShow(){
      if(!this.data.goodsData || !this.data.goodsData.activityType) return
      console.log(this.data.goodsData,this.data.goodsData.activityType)
      let actInfo = {}
      switch (this.data.goodsData.activityType) {
        case 6:
          actInfo.actName="限时降价，立即查看"
          break;
        case 18:
          actInfo.actName="首单特惠，新人专享"
          break;
        case 22:
          actInfo.isVipPrice=true
          break;
        case 98:
          actInfo.actName="限时降价，立即查看"
          actInfo.isVipPrice=true
          break;
      }
      this.setData({
        actInfo
      })
    },
    resetlimitData(){
      let { prdNumber,limitBuyNum, limitMaxNum, cartNumber } = this.data.goodsData;
      limitBuyNum = (limitBuyNum && limitBuyNum > 0) ?  limitBuyNum : 1;
      limitMaxNum = limitMaxNum && limitMaxNum > 0 && prdNumber > limitMaxNum ? limitMaxNum : prdNumber
      this.setData({
        canMinus: cartNumber <= limitBuyNum ? false : true,
        canPlus: cartNumber < limitMaxNum ? true : false,
      })
    }
  }
})