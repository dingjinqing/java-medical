var util = require('../../utils/util.js')
var app = new getApp();
var mobile = util.getCache('mobile');
var set_time_out;
global.wxPage({
  /**
   * 页面的初始数据00
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    pinInte_id: '', // 活动id
    group_id: null, // 团id
    invite_user: null, // 邀请人

    gd: {}, // 活动详情
    pin_info: {}, // 活动信息
    person: [], // 组团用户信息
    limit: [], // 需要邀请团员人数
    inteGoods: {}, // 积分商品列表
    total_micro_second: 0, // 倒计时总时间
    group_gd: {}, // 被邀请参与活动信息
    end: false, // 倒计时结束
    share: false, // 分享成功弹窗
    share_group: true, // 被分享人弹窗
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
      pinInte_id: Number(options.pid),
      group_id: options.gid ? Number(options.gid) : null,
      invite_user: options.invid ? Number(options.invid) : null
    })
    clearTimeout(set_time_out);
    request_pinIntegration(that);
    // 获取积分商品列表
    request_integrationList(that);
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
  },
  // 活动规则
  toRule: function () {
    util.jumpToWeb('/wxapp/pinintegration/help', '&pid=' + this.data.pinInte_id);
  },
  // 跳转参与活动列表
  toActivity: function () {
    wx: util.navigateTo({
      url: '/pages1/pinintegrationdetail/pinintegrationdetail?user_id=' + util.getCache('user_id'),
    })
  },
  // 积分商品详情
  to_integral: function (e) {
    var in_goods_id = e.currentTarget.dataset.in_goods_id;
    var goods_id = e.currentTarget.dataset.goods_id;
    util.navigateTo({
      url: '/pages/item/item?aid=' + in_goods_id + '&atp=4&gid=' + goods_id,
    })
  },
  // 关闭弹窗
  close: function () {
    var that = this;
    that.setData({
      share_group: false,
      share: false,
    })
  },
  // 参加开团
  iwantgo: function (e) {
    util.navigateTo('/pages1/pinintegration/pinintegration?pid=' + this.data.pinInte_id);
  },
  // 去首页
  returnIndex: function () {
    util.reLaunch({ url: '/pages/index/index' })
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
    var date = Math.floor(second / 86400 );
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
      path: '/pages1/pinintegration/pinintegration?pid=' + that.data.pinInte_id + '&invid=' + util.getCache('user_id') + '&gid=' + that.data.group_id,
    }
  },
  // 获取用户昵称 头像
  getUserInfo: function (e) {
    var that = this;
    if (e.detail.userInfo) {
      util.setCache("nickName", e.detail.userInfo.nickName);
      util.setCache("avatarUrl", e.detail.userInfo.avatarUrl);
      // util.api('/api/wxapp/account/updateUser', function (res) {
      // }, { username: util.getCache('nickName'), user_avatar: util.getCache('avatarUrl') });
      that.data.person[that.data.person.length - 1].user_avatar = util.getCache('avatarUrl');
      that.data.person[that.data.person.length - 1].username = util.getCache('nickName');
      that.setData({
        share_group: false,
        person: that.data.person,
      });
    } else {
      that.setData({
        share_group: false,
      });
    }
  },
  // 生成海报
  go_share: function () {
    var that = this;
    wx.showLoading({
      title: '生成中',
    })
    // util.api('/api/wxapp/bargain/pictorial/info', function (res) {
    //   wx.hideLoading();
    //   if (res.error == 0) {
    //     that.setData({
    //       posterBase64: res.content,
    //       pictorial: res.content,
    //       is_share: 1
    //     })
    //   } else {
    //     util.toast_fail(res.message);
    //     return false;
    //   }
    // }, { 
    //   activityId: that.data.pinInte_id,
    //   pageType: 2
    // })
  },
  // 关闭海报
  not_show_share: function () {
    var that = this;
    that.setData({
      is_share: 0
    })
  },
  // 保存图片
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
  mobile = util.getCache('mobile');
  if (that.data.invite_user || that.data.group_id) {
    // 有邀请者
    util.api('/api/wxapp/pin/integration/start', function (res) {
      that.setData({ group_gd: res.content })
      if (res.content.canPin.status == 0) {
        that.setData({ group_id: res.content.groupId })
      }
      util.api('/api/wxapp/pin/integration/detail', function (ress) {
        if (ress.error == 0) {
          var gd = ress.content;
          var pin_info = ress.content.pinInteInfo;
          var num = pin_info.limitAmount - 1;
          var person = ress.content.groupInfo;
          var limit = [];
          for (var i = 0; i < num; i++) {
            limit.push(i);
          }
          that.setData({
            gd: gd, // 全部信息
            pin_info: pin_info, // 活动信息
            person: person, // 团成员信息
            limit: limit // 邀请人数
          })
          // 单次活动时限
          that.setData({ total_micro_second: parseInt(that.data.gd.remainTime / 1000) })
          if (that.data.total_micro_second > 0) {
            that.countdown(that);
          } else {
            that.setData({
              clock: "已结束",
              end: true,
              share_group: true
            });
          }
        } else {
          util.showModal('提示', ress.message);
          return;
        }
      }, {
          pinInteId: that.data.pinInte_id,
          groupId: that.data.group_id
        });
    }, {
      pinInteId: that.data.pinInte_id,
      groupId: that.data.group_id,
      inviteUser: that.data.invite_user
    })
  } else {
    util.api('/api/wxapp/pin/integration/start', function (res) {
      if (res.content.canPin.status == 0) {
        that.setData({ group_id: res.content.groupId })
        util.api('/api/wxapp/pin/integration/detail', function (ress) {
          if (ress.error == 0) {
            var gd = ress.content;
            var pin_info = ress.content.pinInteInfo;
            var num = pin_info.limitAmount - 1;
            var person = ress.content.groupInfo;
            var limit = [];
            for (var i = 0; i < num; i++) {
              limit.push(i);
            }
            that.setData({
              gd: gd, // 全部信息
              pin_info: pin_info, // 活动信息
              person: person, // 团成员信息
              limit: limit // 邀请人数
            })
            // 单次活动时限
            that.setData({ total_micro_second: parseInt(that.data.gd.remainTime / 1000) })
            if (that.data.total_micro_second > 0) {
              that.countdown(that);
            } else {
              that.setData({
                clock: "已结束",
                end: true,
                share_group: true
              });
            }
          } else {
            util.showModal('提示', ress.message);
            return;
          }
        }, {
            pinInteId: that.data.pinInte_id,
            groupId: that.data.group_id
          });
      }
      if (res.content.canPin.status > 0) {
        util.showModal('提示', res.content.canPin.msg, function () {
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
        pinInteId: that.data.pinInte_id
      });
  }









  
  
}
function request_integrationList(that) {
  util.api('/api/wxapp/pin/integration/goodDetail', function (res) {
    if (res.error == 0) {
      that.setData({
        inteGoods: res.content
      })
    } else {
      util.showModal('提示', res.message)
    }
  })
}
