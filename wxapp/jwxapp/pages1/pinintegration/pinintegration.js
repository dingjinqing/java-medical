var util = require('../../utils/util.js')
var app = new getApp();
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var good_id = '';
var mobile = util.getCache('mobile');
var total_micro_second = 0;
var gd;
var group_id = '';
var invite_user = 0;
var pin_info;
var group_gd;
var set_time_out;
var person = [];
var num;
var limit = [];
var pinInte_id;
var group2;
var status;
var show_user_modal;
var share_group = true;
var choose_list = {};
var inteGoods;
var new_options;
var is_share = 0;
var pictorial;
var os_type = '';
var posterBase64 = '';
global.wxPage({

  /**
   * 页面的初始数据00
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    total_micro_second: 0,
    person: [],
    limit: [],
    share_group: true,
    display: false,
    act_open: 0,
    show_user_modal: 0,
    end: false,
    is_share: 0,
    title_bgColor: "#f18a4f"
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    pinInte_id = options.pinInte_id;
    if (!util.check_setting(options)) return;
    var that = this;
    new_options = options;
    choose_list = {};
    clearTimeout(set_time_out);
    // request_pinIntegration(that);
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
  },
  countdown: function (that) {
    that.setData({
      clock: that.dateformat(total_micro_second)
    });
    if (total_micro_second <= 0) {
      that.setData({
        clock: "已结束",
        end: true,
        share_group: true
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
    util.api("/api/wxapp/share/record", function (d) {

    }, { activity_id: pinInte_id, activity_type: 7 });
    return {
      title: "【" + usernames + "@你】与我一起瓜分积分！",
      imageUrl: imageUrl + '/image/admin/poster_image/pin_inte_bg1.png',
      path: '/pages/pinintegration/pinintegration?pinInte_id=' + pinInte_id + '&invite_user=' + util.getCache('user_id') + '&group_id=' + group_id + '&invite_id=' + util.getCache('user_id'),
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
    group2 = '';
    util.api('/api/wxapp/pin/integration/start', function (res) {
      if (res.content.can_pin.status == 0) {
        group2 = res.content.group_id;
      }
      if (res.content.can_pin.status > 0) {
        util.showModal('提示', res.content.can_pin.msg, function () {
        });
        return;
      }
      util.navigateTo({
        url: '/pages/pinintegration/pinintegration?pinInte_id=' + pinInte_id + '&group_id=' + group2,
      })
    }, { pinInte_id: pinInte_id, form_id: form_id, open_id: open_id });
  },
  // 获取用户昵称 头像
  onGotUserInfo: function (e) {
    var that = this;
    if (e.detail.userInfo) {
      util.setCache("nickName", e.detail.userInfo.nickName);
      util.setCache("avatarUrl", e.detail.userInfo.avatarUrl);
      util.api('/api/wxapp/account/updateUser', function (res) {
      }, { username: util.getCache('nickName'), user_avatar: util.getCache('avatarUrl') });
      person[person.length - 1].user_avatar = util.getCache('avatarUrl');
      person[person.length - 1].username = util.getCache('nickName');
      that.setData({
        share_group: false,
        person: person,
      });
    } else {
      that.setData({
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
  // 我的活动
  toActivity: function () {
    wx: util.navigateTo({
      url: '/pages/pinintegrationdetail/pinintegrationdetail',
    })
    // wx: util.navigateTo({
    //   url: '/pages/pinintegrationdetail/pinintegrationdetail?user_id=' + util.getCache('user_id'),
    // })
  },
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
  go_share: function () {
    var that = this;
    wx.showLoading({
      title: '生成中',
    })
    util.api('/api/wxapp/pictorial', function (res) {
      if (res.error == 0) {
        pictorial = res.content.pictorial;

        if (pictorial) {
          util.api('/api/wxapp/upayyun/image', function (res) {
            if (res.error == 0) {
              pictorial = imageUrl + pictorial + "!big";
              posterBase64 = res.content;
              that.setData({
                pictorial: posterBase64,
                is_share: 1
              })
              wx.hideLoading();
            }
          }, { image_path: pictorial });
        }
      } else {
        wx.hideLoading();
        util.toast_fail(res.message);
        return false;
      }
    }, { action: 7, goods_id: pinInte_id, group_id: group_id })
  },

  saveImgToPhotosAlbumTap: function () {
    var that = this;
    if (posterBase64) {
      util.base64ImageHandle(posterBase64, function (res) {
        wx.getSystemInfo({
          success: function (res) {
            os_type = res.platform
          }
        })
        if (os_type == 'ios') {
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
  if (new_options.group_id) {
    group_id = new_options.group_id;
  } else {
    group_id = '';
  }
  mobile = util.getCache('mobile');
  if (new_options.invite_user) {
    invite_user = new_options.invite_user;
    util.api('/api/wxapp/pin/integration/start', function (res) {
      group_gd = res.content;
      if (group_gd.can_pin.status == 0) {
        group_id = group_gd.group_id;
      }
      that.setData({
        group_gd: group_gd,
      })
      util.api('/api/wxapp/pin/integration/detail', function (ress) {
        gd = ress.content;
        if (ress.content) {
          util.api('/api/wxapp/user_goods/record', function (res1) {

          }, { goods_id: group_id, active_id: gd.pinInteInfo.id, active_type: 7, type: 1 })
        }
        pin_info = ress.content.pinInteInfo;
        inteGoods = ress.content.inteGoodsInfo;
        choose_list['limit_amount'] = pin_info.limit_amount;
        choose_list['inte_group'] = pin_info.inte_group;
        choose_list['join_limit'] = pin_info.join_limit;
        choose_list['is_day_divide'] = pin_info.is_day_divide;
        num = pin_info.limit_amount - 1;
        person = ress.content.groupInfo;
        limit = [];
        for (var i = 0; i < num; i++) {
          limit.push(i);
        }
        that.setData({
          gd: gd,
          pin_info: pin_info,
          inteGoods: inteGoods,
          person: person,
          limit: limit,
        })
        // 倒计时
        if (gd.remain_time) {
          total_micro_second = gd.remain_time;
        }
        if (total_micro_second > 0) {
          that.countdown(that);
          that.setData({
            act_open: 1
          });
        }
      }, { pinInte_id: new_options.pinInte_id, group_id: group_id });
    }, { pinInte_id: new_options.pinInte_id, group_id: group_id, invite_user: invite_user });
  } else if (group_id != '') {
    util.api('/api/wxapp/pin/integration/detail', function (ress) {
      gd = ress.content;
      if (ress.content) {
        if (ress.content) {
          util.api('/api/wxapp/user_goods/record', function (res1) {

          }, { goods_id: pinInte_id, active_id: gd.pinInteInfo.id, active_type: 7, type: 1 })
        }
      }
      pin_info = ress.content.pinInteInfo;
      choose_list['limit_amount'] = pin_info.limit_amount;
      choose_list['inte_group'] = pin_info.inte_group;
      choose_list['join_limit'] = pin_info.join_limit;
      choose_list['is_day_divide'] = pin_info.is_day_divide;
      num = pin_info.limit_amount - 1;
      person = ress.content.groupInfo;
      inteGoods = ress.content.inteGoodsInfo;
      limit = [];
      for (var i = 0; i < num; i++) {
        limit.push(i);
      }
      that.setData({
        gd: gd,
        pin_info: pin_info,
        person: person,
        limit: limit,
        inteGoods: inteGoods,
      })
      // 倒计时
      if (gd.remain_time) {
        total_micro_second = gd.remain_time;
      }
      if (total_micro_second > 0) {
        that.countdown(that);
        that.setData({
          act_open: 1
        });

      }
    }, { pinInte_id: new_options.pinInte_id, group_id: group_id });
  } else {
    util.api('/api/wxapp/pin/integration/start', function (res) {
      if (res.content.can_pin.status == 0) {
        group_id = res.content.group_id;
        util.api('/api/wxapp/pin/integration/detail', function (ress) {
          gd = ress.content;
          pin_info = ress.content.pinInteInfo;
          choose_list['limit_amount'] = pin_info.limit_amount;
          choose_list['inte_group'] = pin_info.inte_group;
          choose_list['join_limit'] = pin_info.join_limit;
          choose_list['is_day_divide'] = pin_info.is_day_divide;
          num = pin_info.limit_amount - 1;
          person = ress.content.groupInfo;
          inteGoods = ress.content.inteGoodsInfo;
          limit = [];
          for (var i = 0; i < num; i++) {
            limit.push(i);
          }
          that.setData({
            gd: gd,
            pin_info: pin_info,
            person: person,
            limit: limit,
            inteGoods: inteGoods,
          })
          // 倒计时
          if (gd.remain_time) {
            total_micro_second = gd.remain_time;
          }
          if (total_micro_second > 0) {
            that.countdown(that);
            that.setData({
              act_open: 1
            });

          }
        }, { pinInte_id: pinInte_id, group_id: group_id });
      }
      if (res.content.can_pin.status > 0) {
        // util.showModal('提示', res.content.can_pin.msg);
        util.showModal('提示', res.content.can_pin.msg, function () {
          wx.navigateBack({
            delta: 1,
            fail: function (e) {
              util.navigateTo({
                url: '/pages/index/index',
              })
            }
          })
        }, false);
        return;
      }
    }, {
        pinInte_id: pinInte_id
      });
  }
}
