// pages/pinlotterylist/pinlotterylist.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var mobile = util.getCache('mobile');
var list_info = [];
var total_micro_second = 0;
var set_time_out;
var group_draw_id;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    list_info: [],
    total_micro_second: 0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    group_draw_id = options.group_draw_id;
    clearTimeout(set_time_out);
    util.api('/api/wxapp/groupdraw/list', function (res) {
      if (res.error == 0) {
        list_info = res.content;
        if (res.content) {
          // util.api('/api/wxapp/user_goods/record', function (res1) {

          // }, { goods_id: 0, active_id: list_info.groupDraw.id, active_type: 8, type: 1 })
        }
        if (list_info.groupDraw.surplusSecond && list_info.groupDraw.surplusSecond != undefined) {
          total_micro_second = list_info.groupDraw.surplusSecond;
          if (total_micro_second > 0) {
            that.countdown(that);
            that.setData({
              act_open: 1
            });
          }
        }
        list_info.groupDraw.startTime = list_info.groupDraw.startTime.substring(0, list_info.groupDraw.startTime.length - 3);
        that.setData({
          list_info: list_info
        })
      } else {
        util.showModal("提示", res.message, function () {
          util.redirectTo({
            url: '/pages/index/index',
          })
        });
        return false;
      }
    }, { group_draw_id: group_draw_id })
  },
  // 去商品详情
  to_item: function (e) {
    var goods_id = e.currentTarget.dataset.goods_id;
    util.navigateTo({
      url: 'pages/item/item?activityId=' + group_draw_id + '&&activityType=1&&goodsId=' + goods_id
    })
  },
  //倒计时
  countdown: function (that) {
    that.setData({
      clock: that.dateformat(total_micro_second)
    });
    if (total_micro_second <= 0) {
      that.setData({
        clock: "已经截止"
      });
      return;
    }
    set_time_out = setTimeout(function () {
      // 放在最后--
      total_micro_second -= 1;
      that.countdown(that);
    }, 1000)
  },
  // 时间格式化输出，如3:25:19 86。每10ms都会调用一次
  dateformat: function (micro_second) {
    // 秒数
    var second = Math.floor(micro_second);
    //天数位
    var date = Math.floor(second / 86400);
    // 小时位
    var hr = Math.floor((second - date * 24 * 3600) / 3600);
    if (hr < 10) {
      hr = "0" + hr;
    }
    // 分钟位
    var min = Math.floor((second - hr * 60 * 60 - date * 24 * 3600) / 60);
    if (min < 10) {
      min = "0" + min;
    }
    // 秒位
    var sec = second % 60;
    if (sec < 10) {
      sec = "0" + sec;
    }
    return date + "天" + hr + '时' + min + "分" + sec + "秒";
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
