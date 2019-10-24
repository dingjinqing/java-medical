let util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    canBuyGoodsList: null,
    invalidGoodsList:null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.requestCartList()
  },
  // 增加商品数量
  goodsNumPlus(){

  },
  // 减少商品数量
  goodsNumMinus(){

  },
  // 请求购物车列表
  requestCartList(){
    util.api('/api/wxapp/cart/list',(res)=>{
      console.log(res)
      if(res.error === 0){
        let canBuyList = this.getCanBuyList(res.content.cartGoodsList)
        let invalidList = this.getInvalidGoodsList(res.content.cartGoodsList)
        this.setData({
          canBuyGoodsList: canBuyList,
          invalidGoodsList: invalidList
        })
        console.log(canBuyList)
      }
    })
  },
  // 获取可购买商品列表
  getCanBuyList(listArray){
    if (!Array.isArray(listArray)) return []
    return listArray.filter(item => {
      return item.isOnSale === 1 && item.isDelete === 0 && item.tip !== '无效商品'
    })
  },
  // 获取不可购买商品列表
  getInvalidGoodsList(listArray){
    if (!Array.isArray(listArray)) return []
    return listArray.filter(item => {
      return item.isOnSale === 0 || item.isDelete === 1 || item.tip !== '无效商品'
    })
  },
  //触摸改变
  handleTouchChange(e){
    this.moveX = e.detail.x
  },
  //触摸结束
  handleTouchEnd(e){
    let idx = e.currentTarget.dataset.index
    console.log(this.moveX)
    let target = 'list['+idx+'].x'
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