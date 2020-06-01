// pages/personalcenter/personalcenter.js
var util = require("../../utils/util.js");

global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    tabIndex: 'usercenter',
    addressList: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      options: options
    })
    this.initData()
  },

  /**
   * 请求收货地址
   */
  initData () {
    let that = this
    util.api('/api/wxapp/address/list', res => {
      if (res.error === 0) {
        console.log(res.content)
        that.setData({
          addressList: res.content.addressList || []
        })
      }
    })
  },

  /**
   * 切换tab框
   */
  handleChangeNav (e) {
    console.log(e)
    let id = e.currentTarget.dataset.id
    console.log(id)
    console.log(this.data.tabIndex === 'usercenter', this.data.tabIndex === 'address')
    this.setData({
      tabIndex: id
    })
  },

  /**
   * 微信导入回调
   */
  refreshList () {
    this.initData()
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