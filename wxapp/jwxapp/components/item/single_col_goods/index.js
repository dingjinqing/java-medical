// components/item/single_col_goods/index.js
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    isEdit:{
      type:Boolean,
      value:false
    },
    enableNumEdit:{
      type:Boolean,
      value:false
    },
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
  data: {

  },
  lifetimes:{
    ready(){
      this.setCartData()
      this.setDelMarket()
    }
  },

  /**
   * 组件的方法列表
   */
  methods: {
    setCartData(){
      if(!this.data.showCart) return
      let data = this.data
      let typeClass = {
        0:{
          className: 'iconfont icontianjia1',
          styleName:`color:${data.main_setting.comColor}`
        },
        1:{
          className: 'iconfont icongouwuche1',
          styleName:`color:${data.main_setting.comColor}`
        },
        2:{
          className: 'boder-button',
          styleName:`background-color:${data.main_setting.comColor};color:#fff;border-color:transparent;`,
          text:'马上抢'
        },
        3:{
          className: 'boder-button',
          styleName:`background-color:#fff;color:#666;border-color:#888;`,
          text:'购买'
        }
      }
      this.setData({
        cartData:typeClass[this.data.showCart.cart_type]
      })
    },
    setDelMarket(){
      if(!this.data.delMarket) return
      let data = this.data
      let delMarketType = {
        1: {
          className:'gray-text line-through',
          text:`市场价: ${data.goodsData.linePrice}`
        }, //关闭购物车按钮-划线价类别
        2: {
          className:'gray-text',
          text:`销量: ${data.goodsData.goodsSaleNum}`
        },
        3: {
          className:'gray-text',
          text:`评价数: ${data.goodsData.commentNum}`
        },
      }
      this.setData({
        delMarketData:delMarketType[this.data.delMarket]
      })
    }
  }
})
