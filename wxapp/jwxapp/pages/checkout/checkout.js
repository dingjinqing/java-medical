var util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    address:null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
  },
  addAddress(){
    wx.chooseAddress({
      success: (res) => {
        console.log(res);
        // var order_goods = JSON.stringify(this.data.goods);
        // var create_order_data = JSON.parse(JSON.stringify(this.data.create_order));;
        // util.api('/api/wxapp/address/choose', function (e) {
        //   // console.log(e);
        //   create_order_data.address_id = e.content.address_id;
        //   this.loadPage(create_order_data);
        // }, {
        //     address: JSON.stringify(res),
        //     orderGoods: order_goods,
        //     post_type: post_type,
        //     create_order: JSON.stringify(this.data.create_order),
        //     goods_type: this.data.goods_type
        //   }, '', true);
        let address = res
        this.setData({
          address: address
        })
      },
      fail: function () {
        wx.getSetting({
          success: function (res) {
            if (!res.authSetting['scope.address']) {
              util.showModal('是否打开设置页面', '需要获取您的位置信息，请到小程序的设置页面打开授权', function () {
                wx.openSetting({
                  success: function (res) {
                  }
                })
              })
            }
          }
        })
      }
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