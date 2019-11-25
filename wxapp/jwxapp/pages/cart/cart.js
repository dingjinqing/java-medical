let util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    canBuyGoodsList: null,
    invalidGoodsList:null,
    totalPrice:0,
    allChecked:false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.requestCartList()
  },
  // 更改商品数量
  goodsNumChange(e){
    const type = e.currentTarget.dataset.type;
    const recId = e.currentTarget.dataset.rec_id;
    let target = this.data.canBuyGoodsList.find(item => {return item.recId === recId})
    util.api('/api/wxapp/cart/change', res => {
      console.log(res)
      this.requestCartList()
    }, {
        productId: target.prdId,
        cartNumber: type === 'add' ? target.cartNumber + 1 : target.cartNumber - 1
      })
  },
  // 请求购物车列表
  requestCartList(){
    util.api('/api/wxapp/cart/list',(res)=>{
      console.log(res)
      if(res.error === 0){
        let canBuyList = (res.content && res.content.cartGoodsList) || []
        let invalidList = (res.content && res.content.invalidCartList) || []
        this.setData({
          canBuyGoodsList: canBuyList,
          invalidGoodsList: invalidList
        })
        this.getAllCheckedStatus()
        console.log(canBuyList)
      }
    })
  },
  // // 获取可购买商品列表
  // getCanBuyList(listArray){
  //   if (!Array.isArray(listArray)) return []
  //   return listArray.filter(item => {
  //     return item.isOnSale === 1 && item.isDelete === 0 && item.tip !== '无效商品'
  //   })
  // },
  // // 获取不可购买商品列表
  // getInvalidGoodsList(listArray){
  //   if (!Array.isArray(listArray)) return []
  //   return listArray.filter(item => {
  //     return item.isOnSale === 0 || item.isDelete === 1 || item.tip == '无效商品'
  //   })
  // },
  // 清除无效购物车列表
  clearCart(){
    let recIds = this.data.invalidGoodsList.map(item => {
      return item.recId
    })
    util.api('/api/wxapp/cart/removes',res=>{
      console.log(res)
      if(res.error === 0){
      }
    },{
        recIds
    })
  },
  // 删除购物车商品
  delCartGoods(e){
    const recId = e.currentTarget.dataset.rec_id
    util.api('/api/wxapp/cart/remove', (res) => {
      console.log(res)
      if(res.error === 0){
        this.setData({
          canBuyGoodsList: this.data.canBuyGoodsList.filter(item => {
            return recId !== item.recId;
          })
        })
        this.getCartPrice()
      }
    }, { recId: recId })
  },
  // 更改选中状态
  checkedToggle(e){
    const targetIndex = this.data.canBuyGoodsList.findIndex(item => {return item.recId === e.currentTarget.dataset.rec_id})
    const target = `canBuyGoodsList[${targetIndex}].isChecked`
    this.setData({
      [target]: this.data.canBuyGoodsList[targetIndex].isChecked ? 0 : 1,
    })
    this.getAllCheckedStatus()
  },
  // 获取是否所有单选框全选
  getAllCheckedStatus(){
    this.setData({
      allChecked: this.data.canBuyGoodsList.every(item => { return item.isChecked === 1 })
    })
    this.getCartPrice()
  },
  // 更改全选状态
  changeAllChecked(){
    this.setData({
      allChecked: !this.data.allChecked,
      canBuyGoodsList: this.data.canBuyGoodsList.map(item => { item.isChecked = !this.data.allChecked ? 1 : 0; return item})
    })
    this.getCartPrice()
  },
  // 
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
  //计算商品价格
  getCartPrice(){
    const canBuyList = this.data.canBuyGoodsList.filter(item => { return item.isChecked === 1 })
    let totalPrice = canBuyList.reduce((accumulator, currentValue)=>{
      return accumulator += currentValue.cartPrice * currentValue.cartNumber
    },0)
    let realPrice = totalPrice.toFixed(3)
    this.setData({
      totalPrice: realPrice.substring(0, realPrice.length - 1)
    })
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