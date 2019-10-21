// pages/cart/cart.js
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    list: [
      { title: "大衣大衣大衣" },
      { title: "大衣大衣大衣" }
    ]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
  },
  // 增加商品数量
  goodsNumPlus(){

  },
  // 减少商品数量
  goodsNumMinus(){

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