let util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    canBuyGoodsList: null,
    invalidGoodsList:null,
    totalPrice:0
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
    var value = Number(e.detail.value)
    var prdId = e.target.dataset.prd_id
    var limit_min = e.target.dataset.limit_min
    var limit_max = e.target.dataset.limit_max
    this.data.canBuyGoodsList.forEach((item, index) => {
      if (item.prdId == prdId) {
        if ((value >= limit_min) && (value <= limit_max)) {
          item.cartNumber = value
        } else {
          item.cartNumber = limit_min
        }
      }
    })
    this.setData({
      canBuyGoodsList: this.data.canBuyGoodsList,
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
})