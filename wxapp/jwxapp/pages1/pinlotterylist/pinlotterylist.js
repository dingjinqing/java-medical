// pages/pinlotterylist/pinlotterylist.js
var util = require('../../utils/util.js')
var app = getApp()
var group_draw_id;
var set_time_out;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    list_info: [], // 列表数据
    group_draw_id: '', // 活动id
    total_micro_second: 0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    that.setData({
      group_draw_id: options.group_draw_id,
    })
    clearTimeout(set_time_out);
    util.api('/api/wxapp/groupdraw/list', function (res) {
      if (res.error == 0) {
        var list_info = res.content;
        if (list_info.groupDraw.surplusSecond && list_info.groupDraw.surplusSecond != undefined) {
          that.setData({
            total_micro_second: list_info.groupDraw.surplusSecond
          })
          if (that.data.total_micro_second > 0) {
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
        util.showModal(that.$t('page1.pinlottery.over'), res.message, function () {
          util.redirectTo({
            url: '/pages/index/index',
          })
        });
        return false;
      }
    }, { group_draw_id: that.data.group_draw_id })
  },
  // 去商品详情
  to_item: function (e) {
    var goods_id = e.currentTarget.dataset.goods_id;
    util.navigateTo({
      url: 'pages/item/item?aid=' + this.data.group_draw_id + '&&atp=1&&gid=' + goods_id
    })
  },
  //倒计时
  countdown: function (that) {
    that.setData({
      clock: that.dateformat(that.data.total_micro_second)
    });
    if (that.data.total_micro_second <= 0) {
      that.setData({
        clock: that.$t('page1.pinlottery.over')
      });
      return;
    }
    set_time_out = setTimeout(function () {
      // 放在最后--
      that.data.total_micro_second -= 1;
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
    // return date + "天" + hr + '时' + min + "分" + sec + "秒";
    return date + this.$t('page1.pinlottery.day') + hr + this.$t('page1.pinlottery.hour') + min + this.$t('page1.pinlottery.minute') + sec + this.$t('page1.pinlottery.second');
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
