// pages1/promoteinfo/promoteinfo.js
var util = require('../../utils/util.js')
var app = getApp()
var promote_info = [];
var launch_user_id; // 用户id
var launch_id; // 发起id
var actCode; // 活动code
var set_time_out;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    promote_info: [],
    goods_id: '',
    share_good: false, //分享弹窗
    is_promote_value: 0, // 每次助力值
    promote_ok: 0, // 助力成功
    promote_fail: 0, // 助力失败
    launched_width: 0, // 助力进度
    total_micro_second: 0, // 倒计时总时间
    clock: '', // 倒计时
    has_user: 1, // 需要授权才能助力
    is_shares: 0, // 是否有分享机会
    add_promote_value: 0, // 好友助力值
    modal_can_share: 0, // 好友助力可分享增加助力
    cant_promote: '', // 助力失败原因
    pictorial: '', // 海报图片
    posterBase64: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    actCode = options.actCode;
    // if (options.launch_user_id && options.launch_user_id != "") {
    //   launch_user_id = options.launch_user_id
    // } else {
    //   // launch_user_id = ''
    //   launch_user_id = util.getCache('user_id');
    // }
    launch_user_id = util.getCache('user_id');
    if (options.launch_id && options.launch_id != "") {
      launch_id = options.launch_id;
    } else {
      launch_id = ""
    }
    var that = this;
    clearTimeout(set_time_out)
    promote_request(that);

  },
  // 商品详情
  to_goods: function (e) {
    var goods_id = e.currentTarget.dataset.goods_id;
    util.jumpLink("/pages/item/item?gid=" + goods_id);
  },
  // 券购搜索
  to_cou_search: function (e) {
    var coupon_sn = e.currentTarget.dataset.coupon_sn;
    util.jumpLink('/pages1/search/search?couponSn=' + coupon_sn);
  },
  // 好友助力列表
  to_list: function () {
    util.jumpLink("/pages/promotelist/promotelist?launch_id=" + launch_id);
  },
  // 规则详情
  toRule: function () {
    util.jumpToWeb('/wxapp/promote/help')
  },
  // 发起助力
  shareGoods: function (e) {
    var that = this;
    if (promote_info.promoteStatus == -1) {
      launchAct(that);
    }
    // 打开分享弹窗
    this.setData({
      share_good: true
    })
  },
  // 关闭分享
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
    util.api('/api/wxapp/bargain/pictorial/info', function (res) {
      wx.hideLoading();
      if (res.error == 0) {
        that.setData({
          posterBase64: res.content,
          pictorial: res.content,
          is_share: 1
        })
      } else {
        util.toast_fail(res.message);
        return false;
      }
    }, { 
      activityId: promote_info.id,
      realPrice: promote_info.goodsInfo.marketPrice ? promote_info.goodsInfo.marketPrice : 0,
      linePrice: promote_info.goodsPrice,
      pageType: 2
    })
  },
  // 关闭海报
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
  // 保存图片
  saveImgToPhotosAlbumTap: function () {
    var that = this;
    var os_type = '';
    if (that.data.posterBase64) {
      util.base64ImageHandle(that.data.posterBase64, function (res) {
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
  // 好友点击助力
  friend_help: function (e) {
    var that = this;
    if (util.getCache('nickName') == '' && promote_info.promoteCondition == 1) {
      that.setData({
        has_user: 0
      })
      return false;
    }
    util.api('/api/wxapp/promote/participate', function (res) {
      if (res.error == 0) {
        var add_promote_value = res.content.promoteValue; // 助力值
        var modal_can_share = res.content.canShare; // 能否再分享
        var cant_promote = res.content.cantPromote; // 助力失败原因
        if (promote_info.canPromote == null && promote_info.canShare == null) {
          if (cant_promote == 0) {
            cant_promote = '该助力申请未发起'
          } else if (cant_promote == 1) {
            cant_promote = '助力已完成，不再需要助力'
          } else if (cant_promote == 2) {
            cant_promote = '今天的助力次数已经用完了'
          } else if (cant_promote == 3) {
            cant_promote = '助力次数已用完'
          }
          that.setData({
            promote_fail: 1,
            is_shares: 0,
            cant_promote: cant_promote,
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
    }, { 
      actCode: actCode, 
      launchId: launch_id, 
      userId: launch_user_id 
    })
  },
  // 助力成功后放弃分享
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
  // 助力失败后放弃发起
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
    util.jumpLink("/pages/orderinfo/orderinfo?orderSn=" + promote_info.orderSn);
  },
  // 去逛逛
  to_index: function () {
    util.jumpLink("/pages/index/index", 'reLaunch');
  },
  // 优惠券列表
  to_coupon: function () {
    util.jumpLink("/pages/coupon/coupon");
  },
  // 更多活动
  to_other_act: function (e) {
    var actcode = e.currentTarget.dataset.actcode;
    util.jumpLink('/pages1/promoteinfo/promoteinfo?actCode=' + actcode, 'reLaunch')
  },
  // 领取奖品-结算
  to_checkout: function () {
    let goodsList = [{
      goodsId: promote_info.goodsInfo.goodsId,
      goodsPrice: promote_info.goodsInfo.goodsPrice,
      goodsNum: 1,
      prdId: promote_info.goodsInfo.prdId,
      productId: promote_info.goodsInfo.prdId
    }]
    if (promote_info.rewardType == 0) {
      goodsList.prdRealPrice = 0
    } else if (promote_info.rewardType == 1) {
      goodsList.prdRealPrice = promote_info.goodsInfo.marketPrice
    }
    console.log(goodsList)
    util.jumpLink("/pages/checkout/checkout?activityType=14&activityId=" + promote_info.id + "&goodsList=" + JSON.stringify(goodsList));
  },
  // 倒计时
  countdown: function (that) {
    var clock = util.dateformat(that.data.total_micro_second);
    if (clock.length == 4) {
      clock = clock[0] + "天" + clock[1] + ":" + clock[2] + ":" + clock[3];
    } else {
      clock = clock[0] + ":" + clock[1] + ":" + clock[2]
    }
    that.setData({
      clock: clock
    });
    if (that.data.total_micro_second <= 0) {
      that.setData({
        clock: "已经截止"
      });
      return;
    }
    set_time_out = setTimeout(function () {
      // 放在最后--
      that.data.total_micro_second -= 1;
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
        if (promote_info.promoteCondition == 1 && promote_info.launchFlag == 2 && promote_info.promote_status == 0) {
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
            if (promote_info.promoteCondition == 1) {
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

    if (promote_info.promoteStatus == 0 && promote_info.launchFlag == 2 && promote_info.canShare == 1) {
      shareAdd(that);
      that.setData({
        is_shares: 0,
        promote_ok: 0,
      })
    }
    if (promote_info.activityShareType == 0) {
      if (promote_info.rewardType == 2) {
        // 优惠券
        if (promote_info.couponInfo.actCode == "discount") {
          var title_text = "我正在抢购" + promote_info.couponInfo.denomination + "折优惠券，需要你的助力！"
        } else {
          var title_text = "我正在抢购" + promote_info.couponInfo.denomination + "元优惠券，需要你的助力！"
        }
      } else {
        // 商品
        var title_text = "我正在抢购" + promote_info.goodsInfo.goodsName + "，需要你的助力！";
      }
      var share_img = promote_info.shareImgPath;
    } else {
      var title_text = promote_info.customShareWord;
      if (promote_info.shareImgType == 0) {
        var share_img = ""
      } else {
        var share_img = promote_info.customImgPath
      }
    }
    that.setData({
      share_good: false
    })
    setTimeout(function () {
      clearTimeout(set_time_out);
      that.onPullDownRefresh();
    }, 200);
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
      if (res.content.flag == 0) {
        // 助力失败
        that.setData({
          promote_fail: 1
        })
        if (res.content.msgCode == 0) {
          that.setData({
            cant_promote: '分享获取助力次数已用完'
          })
        }
      }
    } else {
      util.showModal('提示', res.message);
      return false
    }
  }, { userId: launch_user_id, launchId: launch_id });
};
// 发起助力
function launchAct(that) {
  util.api("/api/wxapp/promote/launch", function (res) {
    if (res.error == 0) {
      launch_id = res.content.launchId;
      launch_user_id = res.content.launchUserId;
    } else {
      if (res.message == 1) {
        util.showModal('提示', '活动已停用或删除');
      } else if(res.message == 2) {
        util.showModal('提示', '活动库存不足');
      } else if (res.message == 3) {
        util.showModal('提示', '活动商品库存不足');
      } else if (res.message == 4) {
        util.showModal('提示', '活动未开始');
      } else if (res.message == 5) {
        util.showModal('提示', '活动已结束');
      } else if (res.message == 6) {
        util.showModal('提示', '您已发起快邀请好友助力吧');
      } else if (res.message == 7) {
        util.showModal('提示', '数据入库失败');
      }
      return false
    }
  }, { actCode: actCode, userId: launch_user_id });
};
// 助力详情
function promote_request(that) {
  util.api('/api/wxapp/promote/info', function (res) {
    if (res.error == 0) {
      promote_info = res.content;
      console.log(promote_info);
      // 当前活动的助力总值
      var is_promote_value = promote_info.hasPromoteValue 
      // 助力进度
      var launched_width = parseFloat(660 * parseFloat(is_promote_value) / promote_info.promoteAmount).toFixed(0);
      if (promote_info.promoteStatus == 0) {
        // 单次助力活动时限
        that.data.total_micro_second = promote_info.surplusSecond;
        if (that.data.total_micro_second > 0) {
          that.countdown(that);
        }
      } else if (promote_info.promoteStatus == 1) {
        // 领取奖品时限
        that.data.total_micro_second = promote_info.rewardSpurTime;
        if (that.data.total_micro_second > 0) {
          that.countdown(that);
        }
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
        that.setData({
          goods_id: promote_info.couponInfo.couponId
        })
      } else {
        that.setData({
          goods_id: promote_info.goodsInfo.goodsId
        })
      }
    } else {
      util.showModal('提示', res.message);
      return false
    }
  }, {
      actCode: actCode,
      userId: launch_user_id,
      launchId: launch_id
  })
}