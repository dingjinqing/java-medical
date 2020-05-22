// pages1/distributionorder/distributionorder.js
var util = require('../../utils/util.js')
var app = getApp()
var choose_lists = {};
var usersi;
var show_start_time;
var show_end_time;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    disorder_info: [],
    is_pink: 0,
    choose_lists: {},
    info_list: [],
    page: 1,
    last_page: 1,
    is_zero: 0,
    is_load: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    choose_lists = {};
    if (options.user_id) {
      usersi = options.user_id;
      choose_lists.user_id = usersi;
    }
    if (options.username) {
      var show_name = options.username;
      if (show_name.length > 5) {
        show_name = show_name.substring(0, 4) + '...';
      }
      this.setData({
        page_name: show_name + "的返利订单明细"
      })
    }
    var that = this;
    wx.hideShareMenu();
    if (this.data.comColor == '' || !this.data.comColor) {
      this.data.comColor = "#ff6666"
    }
    show_end_time = util.formatTime(new Date());
    show_end_time = show_end_time.substring(0, 10);
    show_start_time = Date.parse(show_end_time);
    show_start_time = show_start_time / 1000;
    show_start_time = show_start_time - 30 * 24 * 60 * 60;
    var dates = new Date(show_start_time * 1000);
    var Y = dates.getFullYear() + '-';
    var M = (dates.getMonth() + 1 < 10 ? '0' + (dates.getMonth() + 1) : dates.getMonth() + 1) + '-';
    var D = (dates.getDate() + 1 < 10 ? '0' + (dates.getDate() + 1) : dates.getDate() + 1) + ' ';
    show_start_time = Y + M + D;
    choose_lists.start_time = show_start_time;
    choose_lists.end_time = show_end_time;
    order_request(that);
  },

  // 时间选择
  bindDateChange: function (e) {
    var idx = e.currentTarget.dataset.cur_idx;
    var that = this;
    if (idx == 1) {
      var val1 = e.detail.value;
      if (val1 > show_end_time) {
        util.showModal('提示', '起始时间不能晚于结束时间');
        return false;
      }
      var ceshishijian = Date.parse(show_end_time);
      ceshishijian = ceshishijian / 1000;
      ceshishijian = ceshishijian - 6 * 30 * 24 * 60 * 60;
      var dates = new Date(ceshishijian * 1000);
      var Y = dates.getFullYear() + '-';
      var M = (dates.getMonth() + 1 < 10 ? '0' + (dates.getMonth() + 1) : dates.getMonth() + 1) + '-';
      var D = (dates.getDate() + 1 < 10 ? '0' + (dates.getDate() + 1) : dates.getDate() + 1) + ' ';
      ceshishijian = Y + M + D;
      if (val1 < ceshishijian) {
        util.showModal('提示', '请选择结束时间前六个月内的日期');
        return false;
      }
      show_start_time = val1;
      choose_lists.start_time = val1;
      choose_lists.end_time = show_end_time;
      order_request(that);
    }
    if (idx == 0) {
      var val2 = e.detail.value;
      var that = this;
      if (show_start_time > val2) {
        util.showModal('提示', '起始时间不能晚于结束时间');
        return false;
      }
      var ceshishijian = Date.parse(show_start_time);
      ceshishijian = ceshishijian / 1000;
      ceshishijian = ceshishijian + 6 * 30 * 24 * 60 * 60;
      var dates = new Date(ceshishijian * 1000);
      var Y = dates.getFullYear() + '-';
      var M = (dates.getMonth() + 1 < 10 ? '0' + (dates.getMonth() + 1) : dates.getMonth() + 1) + '-';
      var D = (dates.getDate() + 1 < 10 ? '0' + (dates.getDate() + 1) : dates.getDate() + 1) + ' ';
      ceshishijian = Y + M + D;
      if (val2 > util.formatTime(new Date())) {
        util.showModal('提示', '可选日期截止为今日');
        return false;
      }
      if (val2 > ceshishijian) {
        util.showModal('提示', '请选择开始时间之后六个月内的日期');
        return false;
      }
      show_end_time = val2;
      choose_lists.start_time = show_start_time;
      choose_lists.end_time = val2;
      order_request(that);
    }
    this.setData({
      disorder_info: disorder_info
    })
  },

  //切换状态
  change_order: function (e) {
    var f_type = e.currentTarget.dataset.ftype;
    var that = this;
    that.data.page = 1;
    if (f_type == "all") {
      choose_lists.status = 0;
      order_request(that);

      that.setData({
        is_pink: 0,
        is_zero: 0
      })
    } else if (f_type == "notF") {
      choose_lists.status = 1;
      order_request(that);
      that.setData({
        is_pink: 1,
        is_zero: 1
      })
    } else if (f_type == "yiF") {
      choose_lists.status = 2;
      order_request(that);
      that.setData({
        is_pink: 2,
        is_zero: 0
      })
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
    wx.stopPullDownRefresh();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    var that = this;
    that.setData({
      is_load: 1
    })
    if (that.data.page == that.data.last_page) {
      that.setData({
        is_load: 0
      })
      return
    }
    that.data.page = that.data.page + 1;

    // util.api('/api/wxapp/rebate/order_list', function (res) {
    //   var disorder_info = res.content;
    //   var info_list = [];
    //   if (disorder_info.rebate_order.data.length > 0) {
    //     for (var i = 0; i < disorder_info.rebate_order.data.length; i++) {
    //       disorder_info.rebate_order.data[i].order_sn = disorder_info.rebate_order.data[i].order_sn.substring(0, 5) + "********" + disorder_info.rebate_order.data[i].order_sn.substring(14, 19);
    //     }
    //     info_list = disorder_info.rebate_order.data;
    //     for (var i in info_list) {
    //       info_list[i].can_fanli_money = parseFloat(info_list[i].can_fanli_money).toFixed(2);
    //       info_list[i].fanli_money = parseFloat(info_list[i].fanli_money).toFixed(2);
    //     }
    //   }
    //   disorder_info.sum_fanli_money = parseFloat(disorder_info.sum_fanli_money).toFixed(2);
    //   disorder_info.rebate_info.total_money = parseFloat(disorder_info.rebate_info.total_money).toFixed(2);
    //   disorder_info.wait_fanli_money = parseFloat(disorder_info.wait_fanli_money).toFixed(2);
    //   that.setData({
    //     disorder_info: disorder_info,
    //     show_start_time: show_start_time,
    //     show_end_time: show_end_time,
    //     info_list: that.data.info_list.concat(info_list),
    //     is_load: 0,
    //     total_calculate_money: parseFloat(disorder_info.total_calculate_money || 0).toFixed(2)
    //   });

    // }, { choose_lists: JSON.stringify(choose_lists), pageNo: that.data.page });
  },
})
function order_request(that) {
  // util.api('/api/wxapp/rebate/order_list', function (res) {
  //   var disorder_info = res.content;
  //   var info_list = [];
  //   if (disorder_info.rebate_order.data.length > 0) {
  //     for (var i = 0; i < disorder_info.rebate_order.data.length; i++) {
  //       disorder_info.rebate_order.data[i].order_sn = disorder_info.rebate_order.data[i].order_sn.substring(0, 5) + "********" + disorder_info.rebate_order.data[i].order_sn.substring(14, 19);
  //     }
  //     info_list = disorder_info.rebate_order.data;
  //     for (var i in info_list) {
  //       info_list[i].can_fanli_money = parseFloat(info_list[i].can_fanli_money).toFixed(2);
  //       info_list[i].fanli_money = parseFloat(info_list[i].fanli_money).toFixed(2);
  //     }
  //   }

  //   that.data.last_page = disorder_info.rebate_order.last_page;
  //   that.data._page = 1;
  //   disorder_info.sum_fanli_money = parseFloat(disorder_info.sum_fanli_money).toFixed(2);
  //   disorder_info.rebate_info.total_money = parseFloat(disorder_info.rebate_info.total_money).toFixed(2);
  //   disorder_info.wait_fanli_money = parseFloat(disorder_info.wait_fanli_money).toFixed(2);
  //   that.setData({
  //     disorder_info: disorder_info,
  //     show_start_time: show_start_time,
  //     show_end_time: show_end_time,
  //     info_list: info_list,
  //     total_calculate_money: parseFloat(disorder_info.total_calculate_money || 0).toFixed(2)
  //   });

  // }, { choose_lists: JSON.stringify(choose_lists), pageNo: 1 });
}