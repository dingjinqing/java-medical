var util = require('../../utils/util.js')
var app = new getApp();
var set_time_out;
global.wxPage({
  /**
   * 页面的初始数据00
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    islogin: false, // 是否已登录
    invite_user: 0,
    gd: {},
    choose_list: {},
    inteGoods: {},
    group_id: '', 
    pinInte_id: null, // 组团id
    pin_info: {}, // 成团信息
    group_gd: {},
    total_micro_second: 0, // 倒计时总时间
    person: [], // 团员信息
    limit: [],
    share_group: true,
    display: false,
    act_open: 0,
    // show_user_modal: 0,
    end: false,
    is_share: 0, // 海报弹窗
    os_type: '', // 手机类型
    title_bgColor: "#f18a4f",
    pictorial: '', // 海报图片
    posterBase64: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    if (!util.check_setting(options)) return;
    that.setData({
      pinInte_id: options.pinInte_id,
      new_options: options,
      choose_list: {}
    })
    // new_options = options;
    // choose_list = {};
    clearTimeout(set_time_out);
    request_pinIntegration(that);
    // 判断用户是否登录
    var user_name = util.getCache('nickName');
    var user_avatar = util.getCache('avatarUrl');
    if (!user_name || user_name == '用户' + parseInt(util.getCache('user_id') + 10000)
      || user_name == util.getCache('openid') || !user_avatar
      || user_avatar.indexOf('image/admin/head_icon.png') > -1) {
      that.setData({
        islogin: false,
      })
    } else {
      that.setData({
        islogin: true,
      })
    }
  },
  // 倒计时
  countdown: function (that) {
    that.setData({
      clock: that.dateformat(that.data.total_micro_second)
    });
    if (that.data.total_micro_second <= 0) {
      that.setData({
        clock: "已结束",
        end: true,
        share_group: true
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
    if (hr < 10) { hr = "0" + hr }
    // 分钟位
    var min = Math.floor((second - hr * 60 * 60 - date * 24 * 3600) / 60);
    if (min < 10) { min = "0" + min }
    // 秒位
    var sec = second % 60;
    if (sec < 10) { sec = "0" + sec }
    var arrs = [];
    if (date == 0) {
      arrs[0] = hr;
      arrs[1] = min;
      arrs[2] = sec;
      return arrs;
    } else {
      arrs[0] = date;
      arrs[1] = hr;
      arrs[2] = min;
      arrs[3] = sec;
      return arrs;
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
    // 显示导航栏loading
    wx.showNavigationBarLoading();
    // 调用接口加载数据
    var that = this;
    clearTimeout(set_time_out);
    request_pinIntegration(that);
    that.setData({
      share_group: true
    });
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
    var that = this;
    var usernames = util.getCache('nickName');
    that.setData({
      share: true,
    })
    return {
      title: "【" + usernames + "@你】与我一起瓜分积分！",
      imageUrl: that.data.imageUrl + '/image/admin/poster_image/pin_inte_bg1.png',
      path: '/pages/pinintegration/pinintegration?pinInte_id=' + that.data.pinInte_id + '&invite_user=' + util.getCache('user_id') + '&group_id=' + that.data.group_id + '&invite_id=' + util.getCache('user_id'),
    }
  },
  
  close: function () {
    var that = this;
    that.setData({
      share_group: false,
      share: false,
    })
  },

  iwantgo: function (e) {
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    var group2 = '';
    // util.api('/api/wxapp/pin/integration/start', function (res) {
    //   if (res.content.can_pin.status == 0) {
    //     group2 = res.content.group_id;
    //   }
    //   if (res.content.can_pin.status > 0) {
    //     util.showModal('提示', res.content.can_pin.msg, function () {
    //     });
    //     return;
    //   }
    //   util.navigateTo({
    //     url: '/pages/pinintegration/pinintegration?pinInte_id=' + this.data.pinInte_id + '&group_id=' + group2,
    //   })
    // }, { pinInte_id: this.data.pinInte_id, form_id: form_id, open_id: open_id });
  },
  // 获取用户昵称 头像
  onGotUserInfo: function (e) {
    var that = this;
    if (e.detail.userInfo) {
      util.setCache("nickName", e.detail.userInfo.nickName);
      util.setCache("avatarUrl", e.detail.userInfo.avatarUrl);
      // util.api('/api/wxapp/account/updateUser', function (res) {
      // }, { username: util.getCache('nickName'), user_avatar: util.getCache('avatarUrl') });
      that.data.person[that.data.person.length - 1].user_avatar = util.getCache('avatarUrl');
      that.data.person[that.data.person.length - 1].username = util.getCache('nickName');
      that.setData({
        islogin: true,
        share_group: false,
        person: that.data.person,
      });
    } else {
      that.setData({
        islogin: false,
        share_group: false,
      });
    }
  },

  returnIndex: function () {
    util.reLaunch({
      url: '/pages/index/index',
    })
  },
  // 活动规则
  toRule: function () {
    util.jumpToWeb('/wxapp/pinintegration/help', choose_list);
  },
  // 我参与的活动
  toActivity: function () {
    wx: util.navigateTo({
      url: '/pages/pinintegrationdetail/pinintegrationdetail',
    })
    // wx: util.navigateTo({
    //   url: '/pages/pinintegrationdetail/pinintegrationdetail?user_id=' + util.getCache('user_id'),
    // })
  },
  // 积分商品列表
  to_integral: function (e) {
    var in_id = e.currentTarget.dataset.in_goods_id;
    util.navigateTo({
      url: '/pages/integralitem/integralitem?integral_goods_id=' + in_id,
    })
  },
  not_show_share: function () {
    var that = this;
    that.setData({
      is_share: 0
    })
  },
  // 生成海报
  go_share: function () {
    var that = this;
    wx.showLoading({
      title: '生成中',
    })
    // util.api('/api/wxapp/pictorial', function (res) {
    //   if (res.error == 0) {
    //     that.setData({ pictorial: res.content.pictorial })
    //     

    //     if (that.data.pictorial) {
    //       util.api('/api/wxapp/upayyun/image', function (res) {
    //         if (res.error == 0) {
    //           that.data.pictorial = that.data.imageUrl + that.data.pictorial + "!big";
    //           that.data.posterBase64 = res.content;
    //           that.setData({
    //             pictorial: that.data.posterBase64,
    //             is_share: 1
    //           })
    //           wx.hideLoading();
    //         }
    //       }, { image_path: that.data.pictorial });
    //     }
    //   } else {
    //     wx.hideLoading();
    //     util.toast_fail(res.message);
    //     return false;
    //   }
    // }, { action: 7, goods_id: that.data.pinInte_id, group_id: that.data.group_id })
  },

  saveImgToPhotosAlbumTap: function () {
    var that = this;
    if (posterBase64) {
      util.base64ImageHandle(posterBase64, function (res) {
        wx.getSystemInfo({
          success: function (res) {
            that.setData({
              os_type: res.platform
            })
          }
        })
        if (that.data.os_type == 'ios') {
          util.toast_success('保存成功');
        } else {
          util.toast_success('图片已保存到相册');
        }
        that.setData({
          is_share: 0
        })
      });
    } else {
      util.toast_fail('正在生成中...')
    }
  },
})
function request_pinIntegration(that) {
  if (that.data.new_options.group_id) {
    that.setData({
      group_id: that.data.new_options.group_id
    })
  } else {
    that.setData({
      group_id: ''
    })
  }
  mobile = util.getCache('mobile');
  if (that.data.new_options.invite_user) {
    that.setData({
      invite_user: that.data.new_options.invite_user
    })
    // util.api('/api/wxapp/pin/integration/start', function (res) {
    //   var group_gd = res.content;
    //   if (group_gd.can_pin.status == 0) {
    //    that.setData({
    //      group_id: group_gd.group_id
    //    })
    //   }
    //   that.setData({
    //     group_gd: group_gd,
    //   })
    //   util.api('/api/wxapp/pin/integration/detail', function (ress) {
    //     var gd = ress.content;
    //     if (ress.content) {
    //       util.api('/api/wxapp/user_goods/record', function (res1) {

    //       }, { goods_id: that.data.group_id, active_id: gd.pinInteInfo.id, active_type: 7, type: 1 })
    //     }
    //     var pin_info = ress.content.pinInteInfo;
    //     var inteGoods = ress.content.inteGoodsInfo;
    //     that.data.choose_list['limit_amount'] = pin_info.limit_amount;
    //     that.data.choose_list['inte_group'] = pin_info.inte_group;
    //     that.data.choose_list['join_limit'] = pin_info.join_limit;
    //     that.data.choose_list['is_day_divide'] = pin_info.is_day_divide;
    //     var num = pin_info.limit_amount - 1;
    //     var person = ress.content.groupInfo;
    //     var limit = [];
    //     for (var i = 0; i < num; i++) {
    //       limit.push(i);
    //     }
    //     that.setData({
    //       gd: gd,
    //       pin_info: pin_info,
    //       inteGoods: inteGoods,
    //       person: person,
    //       limit: limit,
    //     })
    //     // 倒计时
    //     if (that.data.gd.remain_time) {
    //       that.setData({ total_micro_second: that.data.gd.remain_time })
    //     }
    //     if (that.data.total_micro_second > 0) {
    //       that.countdown(that);
    //       that.setData({
    //         act_open: 1
    //       });
    //     }
    //   }, { pinInte_id: that.data.new_options.pinInte_id, group_id: that.data.group_id });
    // }, { pinInte_id: that.data.new_options.pinInte_id, group_id: that.data.group_id, invite_user: that.data.invite_user });
  } else if (that.data.group_id != '') {
    // util.api('/api/wxapp/pin/integration/detail', function (ress) {
    //   var gd = ress.content;
    //   if (ress.content) {
    //     if (ress.content) {
    //       util.api('/api/wxapp/user_goods/record', function (res1) {

    //       }, { goods_id: that.data.pinInte_id, active_id: gd.pinInteInfo.id, active_type: 7, type: 1 })
    //     }
    //   }
    //   var pin_info = ress.content.pinInteInfo;
    //   that.data.choose_list['limit_amount'] = pin_info.limit_amount;
    //   that.data.choose_list['inte_group'] = pin_info.inte_group;
    //   that.data.choose_list['join_limit'] = pin_info.join_limit;
    //   that.data.choose_list['is_day_divide'] = pin_info.is_day_divide;
    //   var num = pin_info.limit_amount - 1;
    //   var person = ress.content.groupInfo;
    //   var inteGoods = ress.content.inteGoodsInfo;
    //   var limit = [];
    //   for (var i = 0; i < num; i++) {
    //     limit.push(i);
    //   }
    //   that.setData({
    //     gd: gd,
    //     pin_info: pin_info,
    //     person: person,
    //     limit: limit,
    //     inteGoods: inteGoods,
    //   })
    //   // 倒计时
    //   if (that.data.gd.remain_time) {
    //     that.setData({ total_micro_second: that.data.gd.remain_time })
    //   }
    //   if (that.data.total_micro_second > 0) {
    //     that.countdown(that);
    //     that.setData({
    //       act_open: 1
    //     });

    //   }
    // }, { pinInte_id: that.data.new_options.pinInte_id, group_id: that.data.group_id });
  } else {
    // util.api('/api/wxapp/pin/integration/start', function (res) {
    //   if (res.content.can_pin.status == 0) {
    //     that.setData({ group_id: res.content.group_id })
    //     util.api('/api/wxapp/pin/integration/detail', function (ress) {
    //       var gd = ress.content;
    //       var pin_info = ress.content.pinInteInfo;
    //       that.data.choose_list['limit_amount'] = pin_info.limit_amount;
    //       that.data.choose_list['inte_group'] = pin_info.inte_group;
    //       that.data.choose_list['join_limit'] = pin_info.join_limit;
    //       that.data.choose_list['is_day_divide'] = pin_info.is_day_divide;
    //       var num = pin_info.limit_amount - 1;
    //       var person = ress.content.groupInfo;
    //       var inteGoods = ress.content.inteGoodsInfo;
    //       var limit = [];
    //       for (var i = 0; i < num; i++) {
    //         limit.push(i);
    //       }
    //       that.setData({
    //         gd: gd,
    //         pin_info: pin_info,
    //         person: person,
    //         limit: limit,
    //         inteGoods: inteGoods,
    //       })
    //       // 倒计时
    //       if (that.data.gd.remain_time) {
    //         that.setData({ total_micro_second: that.data.gd.remain_time })
    //       }
    //       if (that.data.total_micro_second > 0) {
    //         that.countdown(that);
    //         that.setData({
    //           act_open: 1
    //         });

    //       }
    //     }, { pinInte_id: that.data.pinInte_id, group_id: that.data.group_id });
    //   }
    //   if (res.content.can_pin.status > 0) {
    //     // util.showModal('提示', res.content.can_pin.msg);
    //     util.showModal('提示', res.content.can_pin.msg, function () {
    //       wx.navigateBack({
    //         delta: 1,
    //         fail: function (e) {
    //           util.navigateTo({
    //             url: '/pages/index/index',
    //           })
    //         }
    //       })
    //     }, false);
    //     return;
    //   }
    // }, {
    //     pinInte_id: that.data.pinInte_id
    //   });
  }
}
