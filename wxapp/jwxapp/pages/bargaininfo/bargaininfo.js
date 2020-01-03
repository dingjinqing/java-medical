// pages/bargaininfo/bargaininfo.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var record_id; // 发起砍价id
var mobile = util.getCache('mobile');
// var bargain_info = [];
var bargain_info = {};
var set_time_out;
var is_success;
var is_help;
var is_share = 0;
var os_type = '';
var is_second = 0;
var pictorial;
var reco_goods = [];
var money_now_left;
var is_block = 0;
var posterBase64 = '';
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    total_micro_second: 0,
    act_open: 0,
    money_no_left: 0,
    imageUrl: app.globalData.imageUrl,
    baseUrl: app.globalData.baseUrl,
    click_num: false,
    nickName: util.getCache('nickName'),
    is_success: 1,
    is_help: 0,
    is_share: 0,
    is_second: 0,
    reco_goods: [],
    is_block: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options)
    if (!util.check_setting(options)) return;
    clearTimeout(set_time_out);
    var that = this;
    // record_id = options.record_id;
    record_id = Number(options.record_id);
    request_kanjia(that);
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

    console.log(that.data.getsq)
  },
  countdown: function () {
    if (this.data.set_interval_id) clearInterval(this.data.set_interval_id);
    var that = this;
    that.data.set_interval_id = setInterval(function () {
      if (that.data.total_micro_second > 0) {
        that.data.total_micro_second -= 1;
        that.setData({
          clock: that.dateformat(that.data.total_micro_second)
        });
      } else {
        clearInterval(that.data.set_time_1);
        that.setData({
          clock: "已经截止"
        });
      }
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
    // 分钟位
    var min = Math.floor((second - hr * 60 * 60 - date * 24 * 3600) / 60);
    // 秒位
    var sec = second % 60;
    if (sec <= 9) {
      sec = '0' + sec;
    }
    if (hr <= 9) {
      hr = '0' + hr;
    }
    if (min <= 9) {
      min = '0' + min;
    }
    if (date == 0) {
      return hr + ':' + min + ":" + sec;
    } else {
      return date + '天' + hr + ':' + min + ":" + sec;
    }

  },
  toRule: function () {
    util.jumpToWeb('/wxapp/bargain/help');
  },
  toIndex: function () {
    util.reLaunch({
      url: '/pages/index/index',
    })
  },
  // 砍价商品详情
  toItem: function () {
    var bargain_id = bargain_info.recordInfo.bargainId;
    util.reLaunch({
      url: '/pages/bargainitem/bargainitem?bargain_id=' + bargain_id,
    })
  },
  // 砍价完成
  toCheckout: function (e) {
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    util.api("/api/wxapp/common/saveformid", function (res) { }, { form_id: form_id, open_id: open_id })
    var record_id = bargain_info.recordInfo.id;
    if (bargain_info.recordInfo.bargainType == 1 && bargain_info.recordInfo.isOrdered == 1) {
      var check_money = parseFloat(bargain_info.recordInfo.goodsPrice - bargain_info.recordInfo.bargainMoney).toFixed(2);
      util.showModal("提示", "您有一笔待支付订单，继续下单将以" + (check_money || 0) + "元结算并取消订单，是否继续下单？", function () {
        util.navigateTo({
          url: '/pages/goodsCheckout/goodsCheckout?order_type=bargain&record_id=' + record_id,
        })
      }, true, "取消", "去下单")
    } else {
      util.navigateTo({
        url: '/pages/goodsCheckout/goodsCheckout?order_type=bargain&record_id=' + record_id,
      })
    }
  },
  // 砍价人列表
  go_list: function () {
    var record_id = bargain_info.recordInfo.id;
    util.navigateTo({
      url: '/pages/bargainusers/bargainusers?record_id=' + record_id,
    })
  },
  // 直接购买
  to_buy: function () {
    util.navigateTo({
      url: "/pages/item/item?goods_id=" + bargain_info.recordInfo.goodsId
    })
  },
  toWhere: function (e) {
    var bargain_id = bargain_info.recordInfo.bargain_id;
    if (bargain_info.recordInfo.prd_desc != "") {
      util.reLaunch({
        url: '/pages/bargainitem/bargainitem?bargain_id=' + bargain_id,
      })
    } else if (bargain_info.recordInfo.prd_desc == "") {
      var choose_list = {};
      choose_list.user_id = util.getCache('user_id');
      choose_list.goods_id = bargain_info.recordInfo.goods_id;
      choose_list.prd_id = bargain_info.recordInfo.prd_id;
      choose_list.bargain_id = bargain_info.recordInfo.bargain_id;
      choose_list.goods_price = bargain_info.recordInfo.goods_price;
      util.api("/api/wxapp/bargain/apply", function (res) {
        if (res.error == 0) {
          util.reLaunch({
            url: "/pages/bargaininfo/bargaininfo?record_id=" + res.content.record_id + "&bargain_money=" + res.content.bargain_money
          })
        } else {
          util.showModal('提示', res.content);
        }
      }, { choose_list: JSON.stringify(choose_list) })
    }
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    // 显示导航栏loading
    wx.showNavigationBarLoading();
    // 调用接口加载数据
    var that = this;
    request_kanjia(that);
    wx.hideNavigationBarLoading();
    // 当处理完数据刷新后，wx.stopPullDownRefresh可以停止当前页面的下拉刷新
    wx.stopPullDownRefresh();
  },
  // 关闭弹窗
  guan: function (e) {
    var that = this;
    that.setData({
      is_success: 0
    })
  },
  guan1: function (e) {
    var that = this;
    that.setData({
      is_help: 0
    })
  },
  kai: function () {
    var that = this;
    that.setData({
      is_success: 1
    })
  },
  // 帮忙砍价
  toKnajia: function (e) {
    var that = this;
    // var form_info = {};
    // var open_id = util.getCache("openid");
    // var form_id = e.detail.formId;
    if (util.getCache('mobile') == '' && bargain_info.recordInfo.need_bind_mobile == 1) {
      util.checkSession(function () {
        that.setData({
          is_block: is_block = 1
        })
      })
      return false;
    }
    // 帮助砍价
    util.api("/api/wxapp/bargain/cut", function (res) {
      if (res.error == 0) {
        that.setData({
          is_help: 1,
          cut_money: res.content.bargainMoney
        })
        setTimeout(function () {
          clearTimeout(set_time_out);
          that.onPullDownRefresh();
        }, 1000);
      } else {
        util.showModal('提示', res.content);
        return false;
      }
    }, { record_id: record_id });

  },
  // 订单详情
  toOrder: function (e) {
    var order_sn = e.currentTarget.dataset.order_sn;
    util.navigateTo({
      url: '/pages/orderinfo/orderinfo?order_sn=' + order_sn,
    })
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    var that = this;
    if (bargain_info.record_status.status == 8 || bargain_info.record_status.status == 11) {
      clearTimeout(set_time_out);
      util.api("/api/wxapp/bargain/cut", function (res) {

      }, { record_id: record_id });
      setTimeout(function () {
        clearTimeout(set_time_out);
        that.onPullDownRefresh();
      }, 1000);
      that.setData({
        is_success: 0
      })
    }
    util.api("/api/wxapp/share/record", function (d) {

    }, { activity_id: bargain_info.recordInfo.bargain_id, activity_type: 3 });
    return {
      title: bargain_info.share_title,
      path: 'pages/bargaininfo/bargaininfo?record_id=' + record_id + "&invite_id=" + util.getCache('user_id')
        + "&bargain_id=" + bargain_info.recordInfo.bargain_id,
      imageUrl: that.data.imageUrl + bargain_info.record_share_img.bargain_image,
      complete: function () {

      }
    }

  },
  go_share: function () {
    var that = this;
    wx.showLoading({
      title: '生成中',
    })
    // util.api('/api/wxapp/pictorial', function (res) {
    //   if (res.error == 0) {
    //     pictorial = res.content.pictorial;
    //     if (pictorial) {
    //       util.api('/api/wxapp/upayyun/image', function (res) {
    //         if (res.error == 0) {
    //           pictorial = imageUrl + pictorial + "!big";
    //           posterBase64 = res.content
    //           that.setData({
    //             pictorial: posterBase64
    //           })
    //           wx.hideLoading();
    //           that.setData({
    //             is_share: 1
    //           })
    //         }
    //       }, { image_path: pictorial });
    //     }
    //   } else {
    //     wx.hideLoading();
    //     util.toast_fail(res.message);
    //     return false;
    //   }
    // }, { action: 3, goods_id: bargain_info.recordInfo.goods_id, record_id: record_id, identity_id: bargain_info.recordInfo.bargain_id })
  },
  not_show_share: function () {
    var that = this;
    that.setData({
      is_share: 0
    })
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
  getUserInfo: function (e) {
    var that = this;
    var canIUse = wx.canIUse('button.open-type.getUserInfo');
    if (e.detail.userInfo) {
      if (canIUse) {
        var user_avatar = e.detail.userInfo.avatarUrl;
        var user_name = e.detail.userInfo.nickName;
        util.setCache("nickName", user_name);
        util.setCache("avatarUrl", user_avatar);
        util.api('/api/wxapp/account/updateUser', function (res) {
        }, {

            username: user_name,
            user_avatar: user_avatar
          });
      } else {
        wx.getUserInfo({
          success: res => {
            var user_avatar = e.detail.userInfo.avatarUrl;
            var user_name = e.detail.userInfo.nickName;
            util.setCache("nickName", user_name);
            util.setCache("avatarUrl", user_avatar);
            util.api('/api/wxapp/account/updateUser', function (res) {
            }, {

                username: user_name,
                user_avatar: user_avatar
              });
          }
        })
      }
      that.setData({
        nickName: user_name,
      })
    }
    if (e.currentTarget.dataset.kj == 1) {
      that.toWhere(e);
    }
    that.setData({
      click_num: true,
    })
  },
  // 任意价格去下单
  to_pay_order: function (e) {
    var check_tips = "将以10.00元结算";
    var check_tips = "您有一笔待支付订单，继续下单将以8.00元结算并取消原订单，是否继续下单？"
    util.showModal('提示', check_tips, function () { }, true, "取消", '去下单');
  },
})
function request_kanjia(that) {
  util.api("/api/wxapp/bargain/info", function (res) {
    if (res.error == 0) {
      bargain_info = res.content;
      // if (bargain_info.recordInfo.goodsId) {
      //   util.getUserLocation(function (loc) {
      //     util.api('/api/wxapp/user_goods/record', function (res1) { }, {
      //       goods_id: bargain_info.recordInfo.goodsId, active_id: bargain_info.recordInfo.bargainId, active_type: 3, type: 1, lat: loc.latitude || '',
      //       lng: loc.longitude || ''
      //     })
      //   })
      // }
      if (bargain_info.status == 1 || bargain_info.status == 2) {
        util.showModal('提示', '砍价失败', function () {
          wx.navigateBack()
        });
      }
      if (bargain_info.recordInfo.expectationPrice == null) {
        bargain_info.recordInfo.expectationPrice = 0.00
      }
      if (bargain_info.recordInfo.floorPrice == null) {
        bargain_info.recordInfo.floorPrice = 0.00
      }
      if (bargain_info.recordInfo.bargainType == 1) {
        var left_value = (parseFloat(bargain_info.recordInfo.goodsPrice) - parseFloat(bargain_info.recordInfo.expectationPrice)) * 670 / (parseFloat(bargain_info.recordInfo.goodsPrice) - parseFloat(bargain_info.recordInfo.floorPrice));
        bargain_info.recordInfo.min_bargain_money = parseFloat(bargain_info.recordInfo.goodsPrice - bargain_info.recordInfo.floorPrice).toFixed(2);
        that.setData({
          left_value: parseFloat(left_value)
        })
      }
      // 用户画像
      if (!bargain_info.recordInfo.userAvatar) {
        bargain_info.recordInfo.userAvatar = imageUrl + "/image/admin/head_icon.png";
      }

      //剩余未砍价的钱
      bargain_info.recordInfo.not_freee_monet = parseFloat(bargain_info.recordInfo.goodsPrice -
        bargain_info.recordInfo.bargainMoney - bargain_info.recordInfo.expectationPrice).toFixed(2);

      //进度条
      if (bargain_info.recordInfo.bargainType == 0) {
        var bili = bargain_info.recordInfo.bargainMoney /
          (bargain_info.recordInfo.goodsPrice - bargain_info.recordInfo.expectationPrice);
      } else {
        var bili = bargain_info.recordInfo.bargainMoney /
          (bargain_info.recordInfo.goodsPrice - bargain_info.recordInfo.floorPrice);
      }
      bargain_info.recordInfo.progress_present = parseFloat(bili).toFixed(2);
      bargain_info.recordInfo.progress_present = bargain_info.recordInfo.progress_present * 100;

      //倒计时
      that.data.total_micro_second = bargain_info.recordInfo.remainingTime;
      that.countdown();

      // 进度条显示已砍价金额
      money_now_left = parseFloat(bargain_info.recordInfo.progress_present / 100) * 670;
      money_now_left = parseFloat(money_now_left).toFixed(2);

      // 砍价列表时间
      if (bargain_info.recordUserList.length > 0) {
        var now = new Date(bargain_info.timestamp).getTime();
        bargain_info.recordUserList.forEach((item, index) => {
          item.allTime = (now - new Date(item.createTime).getTime()) / 1000;
          if (item.allTime < 60) {
            item.show_time = '刚刚'
          } else if (item.allTime < 3600) {
            item.show_time = Math.ceil(item.allTime / 60) + '分钟前'
          } else if (item.allTime < 24 * 3600) {
            item.show_time = Math.ceil(item.allTime / 3600) + '小时前'
          } else {
            item.show_time = Math.ceil(item.allTime / (24 * 3600)) + '天前'
          }
        })
      }

      // 按钮判断
      if (that.data.total_micro_second > 0) {
        that.setData({
          act_open: 1
        });
      }
      that.setData({
        bargain_info: bargain_info,
        money_now_left: money_now_left
      })
    } else {
      util.showModal('提示', res.content, function () {
        wx.navigateBack({})
      });
    }
    
  }, { recordId: record_id })

}