// pages/usercardrecord/usercardrecord.js
var util = require('../../utils/util.js');
var app = getApp()
var Url = app.globalData.baseUrl;
var is_submit = false;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    card_no: '',
    record_list: [],
    page: 1,
    last_page: 0,
    show_type: -1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    this.data.record_list = [];
    this.data.card_no = options.card_no;
    get_accout(that);
  },

  chargeDetail: function () {
    this.data.page = 1;
    this.data.show_type = 1;
    this.data.record_list = [];
    get_accout(this);
  },

  useDetail: function () {
    this.data.page = 1;
    this.data.show_type = -1;
    this.data.record_list = [];
    get_accout(this);
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
    if (this.data.page == this.data.last_page) return;
    this.data.page = this.data.page + 1;
    get_accout(this);
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})
function get_accout (that) {
  if (is_submit) return;
  is_submit = true;
  util.api('/api/wxapp/card/use', function (res) {
    var show_type = that.data.show_type;
    if (res.error == 0) {
      if (res.content.card_type == 0) {
        var title = '可用余额';
        var show_number = res.content.money;

      } else if (res.content.card_type == 1) {
        var title = '剩余可使用服务次数';
        var show_number = res.content.surplus;
        if (res.content.is_exchang > 0) {
          var title2 = '剩余可兑换商品次数';
          var show_number2 = res.content.exchang_surplus;
        }
      }
      if (show_type == 1) {
        that.data.page = res.content.charge_list.current_page;
        that.data.last_page = res.content.charge_list.last_page;
        that.data.record_list = that.data.record_list.concat(res.content.charge_list.data);
      } else {
        that.data.page = res.content.consume_list.current_page;
        that.data.last_page = res.content.consume_list.last_page;
        that.data.record_list = that.data.record_list.concat(res.content.consume_list.data);
      }
      that.setData({
        title: title,
        show_number: show_number,
        show_type: show_type,
        card_type: res.content.card_type,
        record_list: that.data.record_list,
        title2: title2 ? title2 : '',
        show_number2: show_number2 ? show_number2 : ''
      })
    }
    is_submit = false;
  }, { card_no: that.data.card_no, show_type: that.data.show_type, page: that.data.page })
}