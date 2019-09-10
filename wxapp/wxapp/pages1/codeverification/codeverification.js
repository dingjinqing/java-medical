var util = require('../../utils/util.js');
var app = getApp();
var Url = app.globalData.baseUrl;

global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl:app.globalData.imageUrl,
    orderList:[],
    currentPage: 0,
    lastPage:1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options)
    this.getOrderList();
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    this.data.orderList = [];
    this.data.currentPage = 0;
    this.data.lastPage = 1;
    this.getOrderList();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    // this.data.currentPage ++;
    this.getOrderList();
  },
  submit_input:function(e){
    var code = e.detail.value.replace(/\s+/g, '');
    if (code != ''){
      this.getVerifyOrder(code);
    } else {
      util.toast_fail('请输入核销码');
    }
  },
  scanCode:function(){
    var that = this;
    wx.scanCode({
      onlyFromCamera: true,
      scanType: ['qrCode'],
      success: res => {
        console.log(res)
        that.getVerifyOrder(res.result)
      },
      fail: res => {
        util.toast_fail('扫码失败');
      },
      complete: res => { }
    })
  },

  getOrderList:function(){
    if (this.data.currentPage >= this.data.lastPage){
      return false;
    }
    var that = this;
    util.api('/api/wxapp/verify/orderlist',function(res){
      console.log(res)
      if (res.error == 0) {
        that.data.currentPage = res.content.current_page;
        that.data.lastPage = res.content.last_page;
        that.setData({
          orderList: that.data.orderList.concat(res.content.data)
        })
      } else {
        util.toast_fail('加载失败');
      }
    }, { page: (that.data.currentPage + 1) })
  },

  toOrderInfo:function(e) {
    let order_sn = e.currentTarget.dataset.orderSn;
    util.navigateTo({
      url: '/pages/orderinfo/orderinfo?action=verify&order_sn=' + order_sn,
    })
  },

  getVerifyOrder:function(verifyCode){
    if (!verifyCode) return false;
    util.api('/api/wxapp/verify/getorder',function(res){
      console.log(res)
      if (res.error == 0) {
        util.navigateTo({
          url: '/pages/orderinfo/orderinfo?action=verify&order_sn=' + res.content.order_sn,
        })
      } else {
        util.showModal('提示', res.message);
      }
    }, { verify_code: verifyCode }, '', 1)
  }
})
