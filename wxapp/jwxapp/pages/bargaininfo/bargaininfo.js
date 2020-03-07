// pages/bargaininfo/bargaininfo.js
var util = require('../../utils/util.js')
var app = getApp()
var record_id; // 发起砍价id
var bargain_info = {};
var set_time_out; 
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    baseUrl: app.globalData.baseUrl,
    nickName: util.getCache('nickName'),
    mobile: util.getCache('mobile'),
    // act_open: 0,
    total_micro_second: 0, // 倒计时总时间
    is_success: 1, // 砍价成功弹窗
    is_help: 0, // 帮砍弹窗
    is_share: 0, // 海报弹窗
    is_block: 0, // 绑定手机号
    islogin: false, // 是否已登录
    os_type: '', // 手机类型
    money_now_left: 0, // 砍价进度条
    posterBase64: '', 
    pictorial: ''  // 海报图片
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options)
    if (!util.check_setting(options)) return;
    clearTimeout(set_time_out);
    var that = this;
    record_id = Number(options.record_id);
    request_kanjia(that);
    this.selectComponent('#recommend').requestData() //请求推荐商品
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
  onReachBottom: function () {
    this.selectComponent('#recommend').requestData()
  },
  // 倒计时
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
  // 去活动规则
  toRule: function () {
    util.jumpToWeb('/wxapp/bargain/help');
  },
  // 去首页
  toIndex: function () {
    util.jumpLink('/pages/index/index', 'reLaunch')
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
    console.log(this.data.bargain_info)
    var record_id = bargain_info.recordInfo.id;
    var bargain_id = bargain_info.recordInfo.bargainId;
    if (bargain_info.recordInfo.bargainType == 1 && bargain_info.recordInfo.isOrdered == 1) {
      var check_money = parseFloat(bargain_info.recordInfo.goodsPrice - bargain_info.recordInfo.bargainMoney).toFixed(2);
      util.showModal("提示", "您有一笔待支付订单，是否继续支付？", function () {
        var order_sn = bargain_info.recordInfo.orderSn;
        util.navigateTo({
          url: '/pages/orderinfo/orderinfo?orderSn=' + order_sn,
        })
      }, true, "取消", "继续支付")
    } else {
      let goodsList = [{
        goodsId: bargain_info.recordInfo.goodsId,
        prdRealPrice: bargain_info.recordInfo.prdPrice,
        goodsPrice: bargain_info.recordInfo.goodsPrice,
        goodsNum: 1,
        prdId: bargain_info.recordInfo.prdId,
        productId: bargain_info.recordInfo.prdId
      }]
      console.log(goodsList)
      util.navigateTo({
        url: "/pages/checkout/checkout?activityType=3&activityId=" + bargain_id + "&recordId=" + record_id + "&goodsList=" + JSON.stringify(goodsList)
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
      url: "/pages/item/item?gid=" + bargain_info.recordInfo.goodsId
    })
  },
  // 好友得好物
  toWhere: function (e) {
    var bargain_id = bargain_info.recordInfo.bargainId;
    var goods_id = bargain_info.recordInfo.goodsId;
    console.log('pages/item/item?aid=' + bargain_id + '&&atp=3&&gid=' + goods_id)
    // 判断是否为多规格
    if (bargain_info.recordInfo.isDefaultProduct == 0) {
      var url = 'pages/item/item?aid=' + bargain_id + '&&atp=3&&gid=' + goods_id;
      util.jumpLink(url);
    } else if (bargain_info.recordInfo.isDefaultProduct == 1) {
      util.api("/api/wxapp/bargain/apply", function (res) {
        if (res.error == 0) {
          util.reLaunch({
            url: "/pages/bargaininfo/bargaininfo?record_id=" + res.content.recordId
          })
        } else {
          util.showModal('提示', res.content);
        }
      }, {
          bargainId: bargain_info.recordInfo.bargainId,
          prdId: bargain_info.recordInfo.prdId
        })
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
    if (util.getCache('mobile') == '' && bargain_info.recordInfo.needBindMobile == 1) {
      util.checkSession(function () {
        that.setData({
          is_block: is_block = 1
        })
      })
      return false;
    }
    util.api("/api/wxapp/bargain/cut", function (res) {
      if (res.error == 0) {
        if (res.content.state == 0) {
          that.setData({
            is_help: 1,
            cut_money: res.content.bargainMoney
          })
        }
        if (res.content.state == 12) {
          util.showModal('提示', '已达到设置的每日砍价次数限制');
        }
        setTimeout(function () {
          clearTimeout(set_time_out);
          that.onPullDownRefresh();
        }, 1000);
      } else {
        util.showModal('提示', res.content);
        return false;
      }
    }, { recordId: record_id });
  },
  // 订单详情
  toOrder: function (e) {
    var order_sn = e.currentTarget.dataset.order_sn;
    util.navigateTo({
      url: '/pages/orderinfo/orderinfo?orderSn=' + order_sn,
    })
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (options) {
    var that = this;
    if (bargain_info.state == 8) {
      clearTimeout(set_time_out);
      util.api("/api/wxapp/bargain/cut", function (res) {

      }, { recordId: record_id });
      setTimeout(function () {
        clearTimeout(set_time_out);
        that.onPullDownRefresh();
      }, 1000);
      that.setData({
        is_success: 0
      })
    }
    return {
      title: bargain_info.recordShareImg.shareDoc,
      path: 'pages/bargaininfo/bargaininfo?record_id=' + record_id + "&invite_id=" + util.getCache('user_id')
        + "&bargain_id=" + bargain_info.recordInfo.bargainId,
      imageUrl: bargain_info.recordShareImg.shareImg
    }
  },
  // 生成海报
  go_share: function () {
    var that = this;
    wx.showLoading({
      title: '生成中',
    })
    util.api('/api/wxapp/bargain/pictorial/info', function (res) {
      wx.hideLoading();
      if (res.error == 0) {
        that.data.posterBase64 = res.content
        that.setData({
          pictorial: that.data.posterBase64,
          is_share: 1
        })
      } else {
        util.toast_fail(res.message);
      }
    }, {
        activityId: bargain_info.recordInfo.bargainId,
        realPrice: bargain_info.recordInfo.expectationPrice,
        linePrice: bargain_info.recordInfo.prdPrice,
        pageType: 2
      })
  },
  // 取消海报
  not_show_share: function () {
    var that = this;
    that.setData({
      is_share: 0
    })
  },
  // 保存图片
  saveImgToPhotosAlbumTap: function () {
    var that = this;
    if (that.data.posterBase64) {
      util.base64ImageHandle(that.data.posterBase64, function (res) {
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
  // 获取用户信息
  getUserInfo: function (e) {
    var that = this;
    if (util.getUserInfoCommon) {
      util.getUserInfoCommon(e, function (userInfo) {
        if (userInfo) {
          console.log(userInfo)
          that.setData({
            islogin: true
          })
        }
      });
    } else {
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
          islogin: true
        })
      }
    }
    

    if (e.currentTarget.dataset.kj == 1) {
      that.toWhere(e);
    }
  }
})
function request_kanjia(that) {
  util.api("/api/wxapp/bargain/info", function (res) {
    if (res.error == 0) {
      bargain_info = res.content;
      console.log(bargain_info)
      if (bargain_info.state == 1 || bargain_info.state == 2) {
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
      that.data.money_now_left = parseFloat(bargain_info.recordInfo.progress_present / 100) * 670;
      that.data.money_now_left = parseFloat(that.data.money_now_left).toFixed(2);

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
      // if (that.data.total_micro_second > 0) {
      //   that.setData({
      //     act_open: 1
      //   });
      // }

      // 分享信息
      util.api('/api/wxapp/bargain/share/info', function (opt) {
        if (opt.error == 0) {
          bargain_info.recordShareImg.shareDoc = opt.content.shareDoc
          bargain_info.recordShareImg.shareImg = opt.content.imgUrl
          that.setData({
            bargain_info: bargain_info
          })
        }
      }, {
          activityId: bargain_info.recordInfo.bargainId,
          realPrice: bargain_info.recordInfo.expectationPrice,
          linePrice: bargain_info.recordInfo.prdPrice
        })


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