// pages/widthdraw/widthdraw.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var mobile = util.getCache('mobile');
var withdraw_money;
var realName;
var sub_flag = '0';
var form_id;
var open_id;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    is_name: 0,
    withdraw_info: [],
    withdraw_money: "",
    realName: "",
    sub_flag: '0'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    wx.hideShareMenu();
    withdraw_request(that);
  },
  phoneClose: function () {
    this.setData({
      is_name: 0
    })
  },
  inpu_money: function (e) {
    withdraw_money = e.detail.value;
    this.setData({
      withdraw_money: withdraw_money
    })
  },
  all_withdraw: function () {
    withdraw_money = this.data.withdraw_info.withdraw;
    this.setData({
      withdraw_money: withdraw_money
    })
  },
  check_name: function (e) {
    realName = e.detail.value;
    this.setData({
      realName: realName
    })
  },
  real_names: function () {
    if (realName == "" || realName == null) {
      util.showModal("提示", "请输入您的真实姓名");
      return false;
    } else {
      this.setData({
        is_name: 0
      })
      sub_flag++;
    }
  },
  take_moneys: function (e) {
    var that = this;
    form_id = e.detail.formId;
    open_id = util.getCache("openid");
    setTimeout(function () {
      that.take_money();
    }, 1000);
  },
  take_money: function () {
    if (this.data.withdraw_info.withdraw_status == 0 || this.data.withdraw_info.withdraw_status == null) {
      util.showModal("提示", "暂时无法提现");
      return false;
    } else {
      var that = this;
      if (this.data.withdraw_info.is_public_user == 0 && this.data.withdraw_info.withdraw_source == 'wx_open') {
        util.showModal("提示", "请根据下方提示绑定公众号后提现");
        return false;
      }
      var re = /^\d+(?=\.{0,1}\d+$|$)/
      if (withdraw_money == '' || withdraw_money.replace(/^\s+|\s+$/g, '') == "") {
        util.showModal("提示", "请输入您要提现的金额");
        return false;
      }
      if (!(re.test(withdraw_money))) {
        util.showModal("提示", "请输入正确的价格");
        return false;
      }
      if (parseFloat(withdraw_money) < parseFloat(this.data.withdraw_info.withdraw_cash)) {
        util.showModal("提示", "提现金额不得小于单次最小提现金额");
        return false;
      }
      if (sub_flag == 0) {
        this.setData({
          is_name: 1
        })
      } else {

        util.api('/api/wxapp/distributor/withdraw/do', function (res) {
          if (res.error == 0) {
            wx.showModal({
              title: '提示',
              content: '提现申请已提交，请等待管理员审核',
              cancelText: "确定",
              cancelColor: "#333333",
              confirmText: "回到首页",
              confirmColor: "#ff6666",
              success(res) {
                if (res.confirm) {
                  util.reLaunch({
                    url: '/pages/index/index',
                  })
                } else if (res.cancel) {
                  wx.navigateBack({
                    delta: 2
                  })
                }
              }
            })
          } else {
            util.showModal("提示", res.message);
            return false;
          }
        }, {

            withdraw_cash: withdraw_money,
            real_name: realName,
            open_id: open_id,
            form_id: form_id
          })
      }
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
    var that = this;
    that.onPullDownRefresh();
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
    // 显示导航栏loading
    wx.showNavigationBarLoading();
    // 调用接口加载数据
    var that = this;
    withdraw_request(that);
    // 隐藏导航栏loading
    wx.hideNavigationBarLoading();
    // 当处理完数据刷新后，wx.stopPullDownRefresh可以停止当前页面的下拉刷新
    wx.stopPullDownRefresh();
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

function withdraw_request(that) {
  util.api('/api/wxapp/distributor/withdraw/detail', function (res) {
    if (res.error == 0) {
      sub_flag = '0';
      withdraw_money = '';
      that.data.withdraw_info = res.content;
      if (that.data.withdraw_info.withdraw_cash == null || that.data.withdraw_info.withdraw_cash == "") {
        that.data.withdraw_info.withdraw_cash = '1.00';
      }
      if (that.data.withdraw_info.withdraw_cash) {
        that.data.withdraw_info.withdraw_cash = parseFloat(that.data.withdraw_info.withdraw_cash).toFixed(2);
      }
      if (that.data.withdraw_info.real_name != '' && that.data.withdraw_info.real_name != null) {
        realName = that.data.withdraw_info.real_name;
        that.setData({
          realName: realName
        })
      }
      that.setData({
        withdraw_info: that.data.withdraw_info
      })
    } else {
      util.showModal("提示", "操作失败");
      return false;
    }
  }, {})
}
