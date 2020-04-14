// pages1/pinlotteryinfo/pinlotteryinfo.js
var util = require('../../utils/util.js')
var app = getApp()
var group_draw_id;
var goods_id;
var group_id;
// var group_info = [];
var set_time_out;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    user_arr: [],
    total_micro_second: 0,
    group_info: [],
    show_user_modal: 0,
    nickName: util.getCache('nickName'),
    click_num: false,
    is_show_modal: 0, // 分享弹窗
    order_sn: '', // 订单号
    share_img: '', // 分享图片
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    group_draw_id = options.group_draw_id;
    goods_id = options.goods_id;
    group_id = options.group_id;
    var that = this;
    // 判断用户是否登录
    var user_name = util.getCache('nickName');
    var user_avatar = util.getCache('avatarUrl');
    if (!user_name || user_name == '用户' + parseInt(util.getCache('user_id') + 10000)
      || user_name == util.getCache('openid') || !user_avatar
      || user_avatar.indexOf('image/admin/head_icon.png') > -1) {
      that.setData({
        getsq: false,
      })
    } else {
      that.setData({
        getsq: true,
      })
    }
    if (user_avatar == "" || user_name == "") {
      that.setData({
        show_user_modal: 1
      })
    }
    clearTimeout(set_time_out);
    // 获取活动详情
    util.getNeedTemplateId('group_draw', () => {
      this.request_group()
    })
  },

  request_group () {
    util.api('/api/wxapp/groupdraw/info', function (res) {
      if (res.error == 0) {
        var group_info = res.content;
        if (group_info.userGroupInfo) {
          that.setData({
            order_sn: group_info.userGroupInfo.orderSn
          })
        }

        // 判断更多抽奖活动的个数
        if (group_info.drawGoods != '' && group_info.drawGoods) {
          if (group_info.drawGoods.length > 6) {
            group_info.drawGoods = group_info.drawGoods.slice(0, 6);
          }
        }
        // 倒计时
        if (group_info.surplusSecond) {
          that.setData({
            total_micro_second: group_info.surplusSecond
          })
          if (that.data.total_micro_second > 0) {
            that.countdown(that);
          }
        }
        if (group_info.groupJoinDetail.userList.length > 0) {
          var user_arr = group_info.groupJoinDetail.userList.slice(1);
        }
        that.setData({
          group_info: group_info,
          user_arr: user_arr
        })
      } else {
        util.showModal("提示", res.message, function () {
          util.reLaunch({
            url: '/pages/index/index',
          })
        });
        return false;
      }
    }, {
        group_draw_id: group_draw_id,
        goods_id: goods_id,
        group_id: group_id,
        options: {
          group_draw_id: group_draw_id,
          goods_id: goods_id,
          group_id: group_id
        }
      })
  },

  // 查看活动列表
  to_lists: function () {
    if (this.data.group_info.groupDraw.status == 2) {
      util.reLaunch({
        url: '/pages/index/index',
      })
    } else {
      util.navigateTo({
        url: '/pages/pinlotterylist/pinlotterylist?group_draw_id=' + group_draw_id,
      })
    }
  },
  // 更多活动去详情页
  to_group: function (e) {
    var goods_id = e.currentTarget.dataset.goods_id;
    var group_draw_id = e.currentTarget.dataset.draw_id;
    util.navigateTo({
      url: 'pages/item/item?aid=' + group_draw_id + '&atp=1&gid=' + goods_id
    })
  },
  // 去参团
  to_join: function () {
    let goodsList = [{
      goodsId: goods_id,
      prdRealPrice: this.data.group_info.groupDraw.payMoney,
      goodsPrice: this.data.group_info.goods.shopPricee,
      goodsNum: 1,
      prdId: this.data.group_info.groupDraw.productId,
      productId: this.data.group_info.groupDraw.productId
    }]
    console.log(goodsList)
    util.navigateTo({
      url: "/pages/checkout/checkout?activityType=8&activityId=" + Number(group_draw_id) + "&groupid=" + Number(group_id) + "&goodsList=" + JSON.stringify(goodsList)
    })
  },
  // 去开团
  to_open: function () {
    let goodsList = [{
      goodsId: goods_id,
      prdRealPrice: this.data.group_info.groupDraw.payMoney,
      goodsPrice: this.data.group_info.goods.shopPricee,
      goodsNum: 1,
      prdId: this.data.group_info.groupDraw.productId,
      productId: this.data.group_info.groupDraw.productId
    }]
    console.log(goodsList)
    util.navigateTo({
      url: "/pages/checkout/checkout?activityType=8&activityId=" + Number(group_draw_id) + "&goodsList=" + JSON.stringify(goodsList)
    })
  },
  // 关闭分享弹窗
  close_this: function () {
    this.setData({
      is_show_modal: 0
    })
  },
  // 倒计时
  countdown: function (that) {
    that.setData({
      clock: that.dateformat(that.data.total_micro_second)
    });
    if (that.data.total_micro_second <= 0) {
      that.setData({
        clock: "已经截止"
      });
      return;
    }
    set_time_out = setTimeout(function () {
      that.data.total_micro_second -= 1;
      that.countdown(that);
    }
      , 1000)
  },
  // 时间格式化输出，如3:25:19 86。每10ms都会调用一次
  dateformat: function (micro_second) {
    // 秒数
    var second = Math.floor(micro_second);
    //天数位
    var date = Math.floor(second / 86400);
    // 小时位
    var hr = Math.floor((second - date * 24 * 3600) / 3600);
    if (hr < 10) { hr = "0" + hr }
    // 分钟位
    var min = Math.floor((second - hr * 60 * 60 - date * 24 * 3600) / 60);
    if (min < 10) { min = "0" + min }
    // 秒位
    var sec = second % 60;
    if (sec < 10) { sec = "0" + sec }
    var arrs = [];
    if (date == 0) {
      return hr + ':' + min + ":" + sec;
    } else {
      return date + '天' + hr + ':' + min + ":" + sec;
    }
  },
  getUserInfo: function (e) {
    this.setData({
      show_user_modal: 0
    });
    var that = this;
    var canIUse = wx.canIUse('button.open-type.getUserInfo');
    if (e.detail.userInfo) {
      if (canIUse) {
        var user_avatar = e.detail.userInfo.avatarUrl;
        var user_name = e.detail.userInfo.nickName;
        util.setCache("nickName", user_name);
        util.setCache("avatarUrl", user_avatar);
        // util.api('/api/wxapp/account/updateUser', function (res) {
        // }, {
        //     username: user_name,
        //     user_avatar: user_avatar
        //   });
      } else {
        wx.getUserInfo({
          success: res => {
            var user_avatar = e.detail.userInfo.avatarUrl;
            var user_name = e.detail.userInfo.nickName;
            util.setCache("nickName", user_name);
            util.setCache("avatarUrl", user_avatar);
            // util.api('/api/wxapp/account/updateUser', function (res) {
            // }, {
            //     username: user_name,
            //     user_avatar: user_avatar
            //   });
          }
        })
      }
      that.setData({
        nickName: user_name,
      })
    }
    if (e.currentTarget.dataset.join == 1) {
      that.to_join(e);
    }
    if (e.currentTarget.dataset.open == 1) {
      that.to_open(e);
    }
    that.setData({
      click_num: true,
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
  // 查看订单详情
  to_orderinfo: function () {
    if (this.data.group_info.groupDraw.status == 2) {
      util.reLaunch({
        url: '/pages/index/index',
      })
    } else {
      util.navigateTo({
        url: '/pages/orderinfo/orderinfo?orderSn=' + this.data.order_sn,
      })
    }
  },
  // 退款详情
  to_orderinfos: function () {
    util.navigateTo({
      url: '/pages/orderinfo/orderinfo?orderSn=' + this.data.order_sn,
    })
  },
  // 查看优惠券列表
  to_coupon_list: function () {
    util.jumpLink('pages/coupon/coupon');
  },
  // 查看活动规则
  to_rule: function () {
    util.jumpToWeb('/wxapp/pinlottery/help');
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    var that = this;
    return {
      title: "快来参与" + that.data.group_info.group_draw.pay_money + "元拼团大抽奖吧",
      // imageUrl: that.data.imageUrl + that.data.share_img,
      path: '/pages/pinlotteryinfo/pinlotteryinfo?group_draw_id=' + group_draw_id + "&goods_id=" + goods_id + "&group_id=" + group_id + '&invite_id=' + util.getCache('user_id'),
    }
  },
  
})
