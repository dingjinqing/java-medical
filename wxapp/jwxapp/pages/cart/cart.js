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
    let recId = e.currentTarget.dataset.prd_id
    let isChecked = e.currentTarget.dataset.is_checked
    util.api('/api/wxapp/cart/switch', res => {
      if (res.error === 0) {
        this.requestCartList()
      }
    }, { recIds: [recId], isChecked: isChecked })

    // let recId = e.currentTarget.dataset.rec_id
    // let idx = this.data.canBuyGoodsList.findIndex(item => item.recId === recId)
    // util.api('/api/axapp/cart/switch',res=>{
    //   if(res.error === 0){
    //     this.requestCartList()
    //   }
    // }, { recIds: [recId], isChecked: this.data.canBuyGoodsList[idx].isChecked ? 0 : 1 })
  },

  // 更改全选状态
  changeAllChecked() {
    let recIds = this.data.canBuyGoodsList.map(item => { return item.recId })
    let isAllCheck = isAllCheck ? 0 : 1
    util.api('/api/wxapp/cart/switch', res => {
      if (res.error === 0) {
        this.requestCartList()
      }
    }, { recIds, isChecked: isAllCheck })
  },

  // 更改商品数量
  goodsNumChange(e) {
    const type = e.currentTarget.dataset.type;
    const prdId = e.currentTarget.dataset.prd_id;
    const cartNumber = e.currentTarget.dataset.cart_number;
    util.api('/api/wxapp/cart/change', res => {
      if (res.error == 0) {
        this.requestCartList()
      }
    }, {
        productId: prdId,
        cartNumber: type == 'add' ? cartNumber + 1 : cartNumber - 1
      })

    // const type = e.currentTarget.dataset.type;
    // const recId = e.currentTarget.dataset.rec_id;
    // let target = this.data.canBuyGoodsList.find(item => { return item.recId === recId })
    // util.api('/api/wxapp/cart/change', res => {
    //   console.log(res)
    //   this.requestCartList()
    // }, {
    //     productId: target.prdId,
    //     cartNumber: type === 'add' ? target.cartNumber + 1 : target.cartNumber - 1
    //   })
  },

  // 校验商品数量
  checkNumber(e) {
    var value = Number(e.detail.value)
    var prdId = e.target.dataset.prd_id
    var limit_min = e.target.dataset.limit_min
    var limit_max = e.target.dataset.limit_max
    var allMoney = 0 // 总金额
    this.data.canBuyGoodsList.forEach((item, index) => {
      if (item.prdId == prdId) {
        if ((value >= limit_min) && (value <= limit_max)) {
          item.cartNumber = value
        } else {
          item.cartNumber = limit_min
        }
      }
      allMoney += item.cartNumber * item.cartPrice
    })
    this.setData({
      canBuyGoodsList: this.data.canBuyGoodsList,
      totalPrice: allMoney
    })
  },

  // 删除购物车商品
  delCartGoods(e) {
    console.log(e.currentTarget.dataset)
    // const recId = e.currentTarget.dataset.rec_id
    const recId = e.currentTarget.dataset.prd_id
    util.api('/api/wxapp/cart/remove', (res) => {
      console.log(res)
      if (res.error === 0) {
        this.requestCartList()
      }
    }, { recId: recId })
  },
  
  // 清除无效购物车列表
  clearCart(){
    let recIds = this.data.invalidGoodsList.map(item => {
      return item.recId
    })
    util.api('/api/wxapp/cart/removes',res=>{
      console.log(res)
      if(res.error === 0){
        this.requestCartList()
      }
    },{
        recIds
    })
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
  toCheckOut(){
    let goodsList = this.data.canBuyGoodsList.filter(item => item.isChecked === 1).map(item=>{
      let { goodsId, cartPrice: prdRealPrice, cartNumber: goodsNum, prdId } = item
      return { goodsId, prdRealPrice, goodsNum, prdId, isCart:1 }
    })
    util.jumpLink(`pages/checkout/checkout?goodsList=${JSON.stringify(goodsList)}`, "navigateTo")
  },
  toIndex(){
    util.jumpLink('pages/index/index','navigateTo')
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})