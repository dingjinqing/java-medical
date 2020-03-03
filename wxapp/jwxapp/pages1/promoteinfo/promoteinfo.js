// pages1/promoteinfo/promoteinfo.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var promote_info = [];
var goods_id;
var launch_user_id;
var launch_id;
var actCode;
var share_good;
var is_promote_value = 0;
var promote_ok = 0;
var promote_fail = 0;
var launched_width = 0;
var total_micro_second = 0;
var set_time_out;
var clock;
// 需要授权才能助力
var has_user = 1;
// 放弃分享之后的显示按钮
var is_shares = 0;
// 需要授权才能去分享
// var is_userinfos = 1;
var pictorial;
var posterBase64 = '';
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    promote_info: [],
    share_good: false,
    is_promote_value: 0,
    promote_ok: 0,
    promote_fail: 0,
    launched_width: 0,
    total_micro_second: 0,
    clock: '',
    has_user: 1,
    is_shares: 0,
    // is_userinfos:1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    actCode = options.actCode;
    if (options.launch_user_id && options.launch_user_id != "") {
      launch_user_id = options.launch_user_id
    } else {
      launch_user_id = ''
    }
    if (options.launch_id && options.launch_id != "") {
      launch_id = options.launch_id;
    } else {
      launch_id = ""
    }
    var that = this;
    clearTimeout(set_time_out)
    promote_request(that);

  },
  // 发起助力
  shareGoods: function (e) {
    var that = this;
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    // util.api("/api/wxapp/common/saveformid", function (res) { }, { form_id: form_id, open_id: open_id })
    // if (promote_info.promote_status == -1) {
    //   launchAct(that);
    // }
    this.setData({
      share_good: true
    })
  },
  bindClose: function () {
    this.setData({
      share_good: false
    })
    var that = this;
    setTimeout(function () {
      clearTimeout(set_time_out);
      that.onPullDownRefresh();
    }, 200);
  },
  // 下载海报
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
                share_good: false
              })
              wx.hideLoading();
              that.setData({
                is_share: 1
              })
            }
          }, { image_path: pictorial });
        }
      } else {
        wx.hideLoading();
        util.toast_fail(res.message);
        return false;
      }
    }, { action: 14, identity_id: launch_id })
  },
  not_show_share: function () {
    var that = this;
    that.setData({
      is_share: 0
    })
    setTimeout(function () {
      clearTimeout(set_time_out);
      that.onPullDownRefresh();
    }, 200);
  },
  saveImgToPhotosAlbumTap: function () {
    var that = this;
    var os_type = '';
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
    setTimeout(function () {
      clearTimeout(set_time_out);
      that.onPullDownRefresh();
    }, 200);
  },
  //规则详情
  toRule: function () {
    util.jumpToWeb('/wxapp/promote/help')
  },
  // 好友点击助力
  friend_help: function (e) {
    var that = this;
    if (util.getCache('nickName') == '' && promote_info.promote_condition == 1) {
      that.setData({
        has_user: 0
      })
      return false;
    }
    util.api('/api/wxapp/friend/promote', function (res) {
      if (res.error == 0) {
        var add_promote_value = res.content.promote_value;
        var modal_can_share = res.content.canShare;
        if (promote_info.canPromote == 0 && promote_info.canShare == 0) {
          that.setData({
            promote_fail: 1,
            is_shares: 0,
            add_promote_value: add_promote_value,
            modal_can_share: modal_can_share
          })
        } else {
          that.setData({
            promote_ok: 1,
            add_promote_value: add_promote_value,
            modal_can_share: modal_can_share
          })
        }

      } else {
        util.showModal('提示', res.message);
        return false
      }
    }, { actCode: actCode, launch_id: launch_id, launch_user_id: launch_user_id })
  },
  // 放弃分享
  forgive_share: function (e) {
    if (e.currentTarget.dataset.ifshare == 1) {
      this.setData({
        promote_ok: 0,
        is_shares: 1
      })
    }
    this.setData({
      promote_ok: 0,
    })
    var that = this;
    setTimeout(function () {
      clearTimeout(set_time_out);
      that.onPullDownRefresh();
    }, 200);
  },
  forgive_share_fail: function () {
    this.setData({
      promote_fail: 0,
      is_shares: 0
    })
    var that = this;
    setTimeout(function () {
      clearTimeout(set_time_out);
      that.onPullDownRefresh();
    }, 200);
  },
  // 好友发起自己的助力
  to_own_act: function () {
    util.jumpLink('/pages1/promoteinfo/promoteinfo?actCode=' + actCode, 'redirectTo')
  },
  // 查看订单
  to_order: function () {
    util.jumpLink("/pages/orderinfo/orderinfo?order_sn=" + promote_info.order_sn);
  },
  // 去逛逛
  to_index: function () {
    util.jumpLink("/pages/index/index", 'reLaunch');
  },
  // 好友主力列表
  to_list: function () {
    util.jumpLink("/pages/promotelist/promotelist?launch_id=" + launch_id);
  },
  // 优惠券列表
  to_coupon: function () {
    util.jumpLink("/pages/couponlist/couponlist");
  },
  // 券购搜索
  to_cou_search: function () {
    util.jumpLink('/pages/searchs/search?list=1&alias_code=' + promote_info.coupon_info.alias_code);
  },
  // 去逛逛
  to_index: function () {
    util.jumpLink("/pages/index/index", 'reLaunch');
  },
  // 更多活动
  to_other_act: function (e) {
    var actcode = e.currentTarget.dataset.actcode;
    util.jumpLink('/pages1/promoteinfo/promoteinfo?actCode=' + actcode, 'reLaunch')
  },
  // item
  to_goods: function (e) {
    var goods_id = e.currentTarget.dataset.goods_id;
    util.jumpLink("/pages/item/item?goods_id=" + goods_id);
  },
  // 结算
  to_checkout: function () {
    var params = {};
    params.goods_id = promote_info.goods_info.goods_id;
    params.goods_price = promote_info.goods_info.market_price;
    params.user_id = util.getCache('user_id');
    params.goods_number = 1;
    params.product_id = promote_info.goods_info.product_id;
    params.launch_id = launch_id;
    var query_param = {};
    query_param.goods_id = promote_info.goods_info.goods_id;
    console.log(JSON.stringify(params));
    util.jumpLink("/pages/goodsCheckout/goodsCheckout?order_type=friend_promote&choose_list=" + JSON.stringify(params) + '&query_param=' + JSON.stringify(query_param));
  },
  // 倒计时
  countdown: function (that) {
    clock = util.dateformat(total_micro_second);
    if (clock.length == 4) {
      clock = clock[0] + "天" + clock[1] + ":" + clock[2] + ":" + clock[3];
    } else {
      clock = clock[0] + ":" + clock[1] + ":" + clock[2]
    }
    that.setData({
      clock: clock
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
    }
      , 1000)
  },
  // 授权个人信息
  getUserInfo: function (e) {
    var that = this;
    var canIUse = wx.canIUse('button.open-type.getUserInfo');
    if (e.detail.userInfo) {
      if (canIUse) {
        var user_avatar = e.detail.userInfo.avatarUrl;
        var user_name = e.detail.userInfo.nickName;
        util.setCache("nickName", user_name);
        util.setCache("avatarUrl", user_avatar);
        if (promote_info.promote_condition == 1 && promote_info.launchFlag == 2 && promote_info.promote_status == 0) {
          that.setData({
            has_user: 1
          })
        }
        // util.api('/api/wxapp/account/updateUser', function (res) { }, {
        //   username: user_name,
        //   user_avatar: user_avatar
        // });
      } else {
        wx.getUserInfo({
          success: res => {
            var user_avatar = e.detail.userInfo.avatarUrl;
            var user_name = e.detail.userInfo.nickName;
            util.setCache("nickName", user_name);
            util.setCache("avatarUrl", user_avatar);
            // util.api('/api/wxapp/account/updateUser', function (res) { }, {
            //   username: user_name,
            //   user_avatar: user_avatar
            // });
            if (promote_info.promote_condition == 1) {
              that.setData({
                has_user: 1
              })
            }
          }
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
    promote_request(that);
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

    if (promote_info.promote_status == 0 && promote_info.launchFlag == 2 && promote_info.canShare == 1) {
      shareAdd(that);
      that.setData({
        is_shares: 0,
        promote_ok: 0,
      })
    }
    if (promote_info.activity_share_type == 0) {
      if (promote_info.reward_type == 2) {
        if (promote_info.coupon_info.act_code == "discount") {
          var title_text = "我正在抢购" + promote_info.coupon_info.denomination + "折优惠券，需要你的助力！"
        } else {
          var title_text = "我正在抢购" + promote_info.coupon_info.denomination + "元优惠券，需要你的助力！"
        }
      } else {
        var title_text = "我正在抢购" + promote_info.goods_info.goods_name + "，需要你的助力！";
      }
      var share_img = imageUrl + promote_info.share_img_path;
    } else {
      var title_text = promote_info.custom_share_word;
      if (promote_info.share_img_type == 0) {
        var share_img = ""
      } else {
        var share_img = imageUrl + promote_info.custom_img_path
      }
    }
    that.setData({
      share_good: false
    })
    setTimeout(function () {
      clearTimeout(set_time_out);
      that.onPullDownRefresh();
    }, 200);
    util.api("/api/wxapp/share/record", function (d) { }, { activity_id: launch_id, activity_type: 14 });
    return {
      path: '/pages1/promoteinfo/promoteinfo?actCode=' + actCode + '&launch_user_id=' + launch_user_id + "&launch_id=" + launch_id,
      title: title_text,
      imageUrl: share_img,
    }
  }
})
// 分享加机会
function shareAdd(that) {
  util.api("/api/wxapp/promote/addTimes", function (res) {
    if (res.error == 0) {
      util.api("/api/wxapp/share/record", function (d) { }, { activity_id: launch_id, activity_type: 14 });
    } else if (res.error == 400004) {
      that.setData({
        promote_fail: 1
      })
    } else {
      util.showModal('提示', res.message);
      return false
    }
  }, { launch_id: launch_id, add_promote_type: 'share' });
};
// 发起助力
function launchAct(that) {
  util.api("/api/wxapp/promote/launch", function (res) {
    if (res.error == 0) {
      launch_id = res.content.launchId;
    } else {
      util.showModal('提示', res.message);
      return false
    }
  }, { actCode: actCode });
};
// 助力详情
function promote_request(that) {
  util.api('/api/wxapp/promote/info', function (res) {
    if (res.error == 0) {
      promote_info = res.content;
      console.log(promote_info);
      // 助力值
      // if (promote_info.promoteAmount) {
      //   promote_info.promoteAmount = promote_info.promoteAmount.replace('.00', '');
      // }
      // 当前活动的助力总值
      if (promote_info.hasPromoteValue == 0) { 
        is_promote_value = 0; 
      } else { 
        is_promote_value = promote_info.hasPromoteValue 
      }
      // 助力进度
      launched_width = parseFloat(660 * parseFloat(is_promote_value) / promote_info.promoteAmount).toFixed(0);
      total_micro_second = promote_info.surplusSecond;
      if (total_micro_second > 0) {
        that.countdown(that);
      }
      if (promote_info.launchId) {
        launch_id = promote_info.launchId
      }
      that.setData({
        promote_info: promote_info,
        is_promote_value: is_promote_value,
        launched_width: launched_width
      })

      // 统计浏览记录
      if (promote_info.rewardType == 2) {
        goods_id = promote_info.couponInfo.couponId;
      } else {
        goods_id = promote_info.goodsInfo.goodsId;
      }
      // util.api('/api/wxapp/user_goods/record', function (res1) { }, {
      //   goods_id: goods_id, active_type: 14, active_id: promote_info.id, type: 1
      // })
    } else {
      util.showModal('提示', res.message);
      return false
    }
  }, {
      actCode: actCode,
      userId: util.getCache('user_id'),
      launchId: launch_id
  })
}