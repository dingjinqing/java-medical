let util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    canBuyGoodsList: null,
    invalidGoodsList:null,
    totalPrice:0,
    proMode: true,
    // 切换活动弹框要用到的数组
    activityType: null, // 正在参与的活动类型
    proPurchaseInfo: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.requestCartList()
  },

  // 请求购物车列表
  requestCartList() {
    util.api('/api/wxapp/cart/list', (res) => {
      if (res.error === 0) {
        let { cartGoodsList: canBuyGoodsList = [], invalidCartList: invalidGoodsList = [], isAllCheck = null, totalPrice = null } = res.content || []
        this.selectComponent('#recommend').requestData() //请求推荐商品
        this.setData({
          canBuyGoodsList,
          invalidGoodsList,
          isAllCheck,
          totalPrice
        })
      }
    })
  },

  // 更改选中状态
  checkedToggle(e) {
    let cartId = e.currentTarget.dataset.cart_id
    let isChecked = e.currentTarget.dataset.is_checked
    util.api('/api/wxapp/cart/switch', res => {
      if (res.error === 0) {
        this.requestCartList()
      }
    }, { recIds: [cartId], isChecked: isChecked ? 0 : 1 })
  },

  // 更改全选状态
  changeAllChecked() {
    let cartIds = this.data.canBuyGoodsList.map(item => { return item.cartId })
    let isAllCheck = this.data.isAllCheck ? 0 : 1
    util.api('/api/wxapp/cart/switch', res => {
      if (res.error === 0) {
        this.requestCartList()
      }
    }, { recIds: cartIds, isChecked: isAllCheck })
  },

  // 更改商品数量
  goodsNumChange(e) {
    let type = e.currentTarget.dataset.type;
    let prdId = e.currentTarget.dataset.prd_id;
    let cartNumber = e.currentTarget.dataset.cart_number;
    util.api('/api/wxapp/cart/change', res => {
      if (res.error == 0) {
        this.requestCartList()
      }
    }, {
        productId: prdId,
        cartNumber: type == 'add' ? cartNumber + 1 : cartNumber - 1
      })
  },

  // 校验商品数量
  checkNumber(e) {
    var that = this;
    var value = Number(e.detail.value)
    var cartId = e.target.dataset.cart_id
    var limit_min = e.target.dataset.limit_min
    var limit_max = e.target.dataset.limit_max
    that.data.canBuyGoodsList.forEach((item, index) => {
      // if (item.prdId == prdId) {
      //   if ((value >= limit_min) && (value <= limit_max)) {
      //     item.cartNumber = value
      //   } else {
      //     item.cartNumber = limit_min
      //   }
      // }
      if (item.cartId == cartId) {
        item.cartNumber = value
        util.api('/api/wxapp/cart/change', res => {
          if (res.error == 0) {
            this.requestCartList()
          }
        }, {
            productId: item.productId,
            cartNumber: item.cartNumber
          })
      }
    })
  },

  // 删除购物车商品
  delCartGoods(e) {
    const cartId = e.currentTarget.dataset.cart_id
    util.api('/api/wxapp/cart/remove', (res) => {
      console.log(res)
      if (res.error === 0) {
        this.requestCartList()
      }
    }, { recId: cartId })
  },
  
  // 清除无效购物车列表
  clearCart(){
    let cartIds = this.data.invalidGoodsList.map(item => { return item.cartId })
    util.api('/api/wxapp/cart/removes',res=>{
      if(res.error === 0){
        this.requestCartList()
      }
    },{ recIds: cartIds })
  },
  
  //触摸改变
  handleTouchChange(e){
    this.moveX = e.detail.x
  },

  //触摸结束
  handleTouchEnd(e){
    let idx = e.currentTarget.dataset.index
    let target = 'canBuyGoodsList['+idx+'].x'
    if (this.moveX <= -20) {
      this.setData({
        [target]: -100
      });
    } else {
      this.setData({
        [target]: 0
      });
    }
  },

  // //计算商品价格
  // getCartPrice(){
  //   const canBuyList = this.data.canBuyGoodsList.filter(item => { return item.isChecked === 1 })
  //   let totalPrice = canBuyList.reduce((accumulator, currentValue)=>{
  //     return accumulator += currentValue.cartPrice * currentValue.cartNumber
  //   },0)
  //   let realPrice = totalPrice.toFixed(3)
  //   this.setData({
  //     totalPrice: realPrice.substring(0, realPrice.length - 1)
  //   })
  // },

  //  去结算
  toCheckOut(){
    let goodsList = this.data.canBuyGoodsList.filter(item => item.isChecked === 1).map(item=>{
      let { goodsId, cartPrice: prdRealPrice, cartNumber: goodsNum, prdId } = item
      return { goodsId, prdRealPrice, goodsNum, prdId, isCart:1 }
    })
    util.jumpLink(`pages/checkout/checkout?goodsList=${JSON.stringify(goodsList)}`, "navigateTo")
  },
  
  // 列表无数据跳转
  toIndex(){
    util.jumpLink('pages/index/index','navigateTo')
  },
  onReachBottom: function () {
    this.selectComponent('#recommend').requestData()
  },

  // 显示促销活动
  proClcik: function (e) {
    var that = this;
    var cartId = e.currentTarget.dataset.cart_id;
    var activityType = e.currentTarget.dataset.activity_type;
    that.setData({
      activityType: activityType
    })
    that.data.canBuyGoodsList.forEach((item, index) => {
      if (item.cartId == cartId) {
        that.setData({
          proPurchaseInfo: item.cartActivityInfos
        })
      }
    })
    console.log(that.data.proPurchaseInfo)
    // 显示促销弹窗
    that.setData({
      proMode: false,
    })
  },
  // 隐藏促销弹窗
  proCancel: function () {
    this.setData({
      proMode: true
    })
  },
  proActionSheetChange: function () {
    this.setData({
      proMode: true
    })
  },

  // 切换促销活动
  choose_acts: function (e) {
    var that = this;
    var activityType = e.currentTarget.dataset.activity_type;
    for (var i in pro_purchase_info) {
      pro_purchase_info[i].is_che = 0;
      pro_purchase_info[i].rec_id = this_rec_id;
    }
    pro_purchase_info[this_indexs].is_che = 1;
    that.setData({
      pro_purchase_info: pro_purchase_info,
    })
    // var cart_data = {};
    // cart_data.action = this_actx;
    // cart_data.identity_id = this_iden_id;
    // cart_data.product_id = this_prd_id;
    // cart_data.rec_id = this_rec_id;
    // that.data.gd_actoins = this_actx;
    // that.data.post_act = 'switch_goods_action';
    // that.data.cart_datas = JSON.stringify(cart_data);
    // cart_request(that);
  },

  // 秒杀抢购
  to_seckill: function (e) {
    util.jumpLink(e.currentTarget.dataset.link);
  }

})