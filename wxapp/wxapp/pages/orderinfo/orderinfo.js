// orderinfo.js
var util = require('../../utils/util.js');
var qrcode = require('../../utils/qrcode.js');
var app = getApp();
var imageUrl = app.globalData.imageUrl;
var Url = app.globalData.baseUrl;
var order_info = [];
var total_micro_second;
var set_time_out;
var is_lottery = 1;
var pull_order_sn;
var pull_coupon;
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    img_group: imageUrl + 'image/wxapp/icon_group3.png',
    click_more: imageUrl + '/image/wxapp/backward_right.png',
    img_noperson: imageUrl + 'image/wxapp/icon_group2.png',
    img_otherperson: imageUrl + 'image/wxapp/icon_group1.png',
    user_name: util.getCache('nickName'),
    imageUrl: app.globalData.imageUrl,
    main_url: Url,
    hiddenModal_pay: true,
    ruleMode: true,
    order_info: [],
    is_lottery: 1,
    action: ''
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    if (!util.check_setting(options)) return;
    var order_sn = options.order_sn;
    pull_order_sn = options.order_sn;
    pull_coupon = options.coupon;
    var coupon = options.coupon;
    this.data.toPay = options.toPay;
    if (options.action != undefined) {
      this.setData({
        action: options.action
      });
    }
    this.request_that(order_sn, coupon);
    clearTimeout(set_time_out);
  },
  request_that: function(order_sn, coupon) {
    var that = this;
    wx.hideShareMenu();
    util.api('/api/wxapp/order/info', function(res) {
      if (res.error == 0) {
        order_info = res.content;
        order_info.show_split = 0;
        var operation = res.content.operation;
        var order_goods = res.content.order_goods;
        var data = qrcode.createQrCodeImg(order_info.verify_code, {
          'size': 300
        });
        order_info.type_icon = ''
        order_info.goods_type = order_info.goods_type.split(",");
        for (var j in order_info.goods_type) {
          if (order_info.goods_type[j] == 1) {
            order_info.type_icon = "拼团";
            order_info.is_pin_group = 1;
          } else if (order_info.goods_type[j] == 3) {
            order_info.type_icon = '砍价'
          } else if (order_info.goods_type[j] == 5) {
            order_info.type_icon = "秒杀"
          } else {
            order_info.type_icon == ""
          }
        }



        //显示缺少的人数
        if (order_info.pin_user_group && order_info.pin_user_group != '') {
          if (order_info.pin_user_group.length > 5) {
            order_info.pin_user_group = order_info.pin_user_group.slice(0, 5);
          }

          if (order_info.pin_group_info.limit_amount > 5) {
            order_info.pin_group_info.show_noper = 5 - order_info.pin_user_group.length;
          } else {
            order_info.pin_group_info.show_noper = order_info.pin_group_info.limit_amount - order_info.pin_user_group.length;
          }
        }

        if (order_info.deliver_type == 1 && order_info.order_status == 3) {
          order_info.order_status_name = "待核销";
        }
        if (order_info.deliver_type == 0 && order_info.order_status == 3) {
          order_info.order_status_name = "待发货";
        }
        if (order_info.deliver_type == 1 && order_info.order_status == 5) {
          order_info.order_status_name = "已自提";
        }
        if (order_info.deliver_type == 0 && order_info.order_status == 5) {
          order_info.order_status_name = "已收货";
        }
        if (coupon == 1 && order_info.coupon != "" && order_info.type == 1 && that.data.toPay == 1) {
          order_info.show_split = 1;
        }
        if (order_info.coupon.cou_limit > order_info.coupon.surplus) {
          order_info.cou_xianshi = order_info.coupon.surplus;
        } else {
          order_info.cou_xianshi = order_info.coupon.cou_limit;
        }
        if (order_info.cancel_surplus_time) {
          total_micro_second = order_info.cancel_surplus_time;
          if (total_micro_second > 0) {
            that.countdown(that);
          } else if (total_micro_second == '-1000') {
            that.setData({
              clock: "已经截止",
              pre_sale_time: "尾款支付时间：" + order_info.pre_sale_time
            });
          } else {
            that.setData({
              clock: "已经截止"
            });
          }
        }
        if (order_info.goods_type == 10) {
          // order_info.pre_order_money = (parseFloat(order_info.money_paid) + parseFloat(order_info.pre_sale_discount)).toFixed(2);
          order_info.pre_sale_money = (parseFloat(order_info.money_paid) + parseFloat(order_info.member_card_balance) + parseFloat(order_info.use_account) + parseFloat(order_info.score_discount)).toFixed(2);
          order_info.pre_order_money = (parseFloat(order_info.pre_sale_money) + parseFloat(order_info.pre_sale_discount)).toFixed(2);
          if (order_info.bk_order_paid == 2) {
            order_info.money_paid = (parseFloat(order_info.money_paid) + parseFloat(order_info.bk_order_money)).toFixed(2);
          }
        }
        for (var i in order_goods) {
          if (order_info.goods_type == 4) {
            for (var i in order_goods) {
              order_goods[i].money = parseFloat(order_goods[i].goods_price - order_goods[i].goods_score / 100).toFixed(2);
            }
            order_info.money = parseFloat(order_info.order_amount - order_info.score_discount).toFixed(2);
            order_info.score = parseInt(order_info.score_discount * 100);
          }
          order_goods[i].free_ship = 0;
          for (var j in order_info.ship_goods) {
            if (order_goods[i].goods_id == order_info.ship_goods[j]) {
              order_goods[i].free_ship = 1;
            }
          }

        }
        if (order_info.sub_order_list) {
          for (let i = 0; i < order_info.sub_order_list.length; i++) {
            if (order_info.sub_order_list[i].order_status == 3) {
              order_info.sub_order_list[i].order_status_name = "待发货"
            }
          }
        }
        that.setData({
          order_info: order_info,
          order_goods: order_goods,
          operation: operation,
          img_code: data
        });
      }
    }, {
      order_sn: order_sn,
      coupon: coupon,
      cou_limit: order_info.cou_xianshi
    }, '', true);
  },
  to_lottery: function() {
    var lottery_id = order_info.coupon.lottery;
    util.navigateTo({
      url: '/pages/lottery/lottery?lottery_id=' + lottery_id + '&lottery_source=2&act_id=' + order_info.coupon.id,
    })
  },

  returnOrder: function(e) {
    var order_sn = e.target.dataset.order_sn;
    if (order_info.is_return == 0) {
      util.navigateTo({
        url: '/pages/returnorder/returnorder?order_sn=' + order_sn
      })
    } else {
      util.navigateTo({
        url: '../return_order_list/return_order_list?order_sn=' + order_sn
      })
    }
  },
  dateformat: function(micro_second) {
    // 秒数
    var second = Math.floor(micro_second);
    // 分钟位
    var min = Math.floor(second / 60);
    // 小时位
    var hour = Math.floor(min / 60);
    // 小时位
    var day = Math.floor(h / 24);
    // 秒位
    var sec = this.numberFormat(second % 60);
    var m = this.numberFormat(min % 60);
    var h = this.numberFormat(hour % 60);
    var str = '';
    if (day > 0) {
      str += this.numberFormat(day) + '天';
      if (h > 0) {
        str += h + '时' + m + "分" + sec + "秒";
      } else {
        str += '00时' + m + "分" + sec + "秒";
      }
    } else {
      if (h > 0) {
        str += h + '时' + m + "分" + sec + "秒";
      } else {
        str += m + "分" + sec + "秒";
      }
    }
    return str;
  },
  numberFormat: function(num) {
    if (num < 10) {
      return '0' + num;
    }
    return num;
  },
  countdown: function(that) {
    that.setData({
      clock: that.dateformat(total_micro_second)
    });
    if (total_micro_second <= 0) {
      that.setData({
        clock: "已经截止"
      });
      // timeout则跳出递归
      return;
    }
    set_time_out = setTimeout(function() {
      // 放在最后--
      total_micro_second -= 1;
      that.countdown(that);
    }, 1000)
  },
  showMap: function(e) {
    var address = order_info.address;
    var latitude = Number(order_info.store_info.latitude);
    var longitude = Number(order_info.store_info.longitude);
    wx.openLocation({
      latitude: latitude,
      longitude: longitude,
      scale: 28,
      name: address
    })
  },
  // 抽奖
  close_lottery: function() {
    this.setData({
      is_lottery: 0
    })
  },
  showlottery: function() {
    this.setData({
      is_lottery: 1
    })
  },
  //分享优惠券弹框
  guan: function() {
    var that = this;
    order_info.show_split = 0;
    that.setData({
      order_info: order_info
    })
  },
  kai: function() {
    var that = this;
    order_info.show_split = 0;
    that.setData({
      order_info: order_info
    })
  },
  showshare: function() {
    var that = this;
    order_info.show_split = 1;
    that.setData({
      order_info: order_info
    })
  },
  //商品评价
  comment: function(e) {
    var order_sn = e.target.dataset.order_sn;
    util.navigateTo({
      url: '../comment/comment?order_sn=' + order_sn
    })
  },
  bindOrderBut: function(e) {
    var data = e.target.dataset;
    var that = this;
    util.showModal('提示', '是否取消该订单', function() {
      util.api('/api/wxapp/order/cancel', function(res) {
        if (res.error == 0) {
          that.request_that(order_info.order_sn);
        }
      }, {
        order_sn: data.order_sn
      });
    }, true);

  },
  itemGood: function(e) {
    var good_id = e.target.dataset.good_id;
    util.navigateTo({
      url: '/pages/item/item?good_id=' + good_id
    })
  },
  bindOrderOk: function(e) {
    var order_sn = e.target.dataset.order_sn;
    util.showModal('提示', '是否确认收货', function(res) {
      util.api('/api/wxapp/order/receive', function(res) {
        if (res.error == 0) {
          util.jumpLink('/pages/commentcomplete/commentcomplete?can_send=' + order_info.can_send + '&order_sn=' + order_sn, 'navigateTo');
        }
      }, {
        order_sn: order_sn
      });

    }, true);
  },
  toExpress: function(e) {
    var ex_no = e.currentTarget.dataset.ex_no;
    var order_sn = e.currentTarget.dataset.order_sn;
    var batch_no = '';
    if (e.currentTarget.dataset.batch_no != undefined){
      batch_no = e.currentTarget.dataset.batch_no;
    }
    util.jumpLink('/pages/express/express?ex_no=' + ex_no + "&order_sn=" + order_sn + "&batch_no=" + batch_no);
  },
  listenerButton: function(e) {
    var that = this;
    if (total_micro_second <= 0) {
      util.showModal('提示', '该订单已取消需重新下单', function() {
        util.reLaunch({
          url: '/pages/index/index'
        })
      });
      return;
    }
    var order_sn = e.target.dataset.order_sn;
    var openid = util.getCache('openid');
    if (order_info.order_pay_way == 2) {
      util.navigateTo({
        url: '/pages/insteadinfo/insteadinfo?order_sn=' + order_sn,
      })
      return false;
    }
    util.api('/api/wxapp/order/pay', function(res) {
      if (res.error == 0) {
        var rest = res.content;
        wx.requestPayment({
          'timeStamp': rest.timeStamp,
          'nonceStr': rest.nonceStr,
          'package': rest.package,
          'signType': typeof res.content.signType == "undefined" ? 'MD5' : res.content.signType,
          'paySign': rest.paySign,
          'success': function(res) {
            util.toast_success('支付成功');
            util.navigateTo({
              url: '/pages/payment/payment?order_sn=' + order_sn + "&coupon=1&toPay=1",
            })
          },
          'fail': function(res) {
            util.navigateTo({
              url: '/pages/orderinfo/orderinfo?order_sn=' + order_sn,
            })
          },
          'complete': function(res) {}
        });
      } else {
        if (res.error == '9999') {
          util.showModal('提示', res.message, function() {
            that.data.is_can_order = 1;
            that.listenerButton(e);
          }, true, '再看看', '继续付款');
        } else {
          util.showModal('提示', res.message);
        }
        
      }
    }, {
      order_sn: order_sn,
      open_id: openid,
      is_can_order:that.data.is_can_order ? 1 : 0
    })
  },
  // 定金膨胀
  ruleCancel: function() {
    this.setData({
      ruleMode: true
    })
  },
  ruleShow: function() {
    this.setData({
      ruleMode: false
    })
  },
  preActionSheetChange: function() {
    this.setData({
      ruleMode: true
    })
  },
  go_couponlist: function() {
    util.navigateTo({
      url: '/pages/couponlist/couponlist',
    })
  },

  verifyOrder: function() {
    var that = this;
    util.showModal('提示', '是否确认核销', function() {
      util.api('/api/wxapp/verify/order', function(res) {
        if (res.error == 0) {
          util.showModal('提示', '核销成功',function(){
            that.request_that(order_info.order_sn);
          })
        } else {
          util.showModal('提示', res.message)
        }
      }, {
        order_sn: that.data.order_info.order_sn,
        verify_code: that.data.order_info.verify_code
      })
    }, true);
  },
  addCart: function (e) {
    var order_sn = e.currentTarget.dataset.order_sn;
    util.api('/api/wxapp/order/Repurchase', function (res) {
      if (res.error == 0) {
        util.toast_success('已加入购物车');
      } else {
        util.alert(res.message);
      }
    }, {
        order_sn: order_sn,
      });
  },
  to_presnet:function(){
    util.jumpLink('pages1/presentdetail/presentdetail?order_sn=' + order_info.main_order_sn)
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
    // 显示导航栏loading
    wx.showNavigationBarLoading();
    // 调用接口加载数据
    var that = this;
    that.request_that(pull_order_sn, pull_coupon);
    // 隐藏导航栏loading
    wx.hideNavigationBarLoading();
    // 当处理完数据刷新后，wx.stopPullDownRefresh可以停止当前页面的下拉刷新
    wx.stopPullDownRefresh();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },
  //参团分享
  toInfo: function() {
    var order_sns = order_info.order_sn;
    var group_ids = order_info.pin_group.group_id;
    if (order_info.goods_type == 8) {
      var group_draw_id = order_info.pin_group.group_draw_id;
      var goods_id = order_info.pin_group.goods_id;
      util.navigateTo({
        url: '/pages/pinlotteryinfo/pinlotteryinfo?order_sn=' + order_sns + '&group_id=' + group_ids + '&group_draw_id=' + group_draw_id + '&goods_id=' + goods_id,
      })
    } else {
      util.navigateTo({
        url: '/pages/groupbuyinfo/groupbuyinfo?order_sn=' + order_sns + '&group_id=' + group_ids,
      })
    }

  },
  copyOrder(){
    let _this = this
    wx.setClipboardData({
      data: _this.data.order_info.order_sn,
      success:(res) => {
        console.log('copy_success',res);
        util.toast_success('内容已复制');
      }
    })
  },
  /**
   * 用户点击右上角分享,立即分享
   */
  onShareAppMessage: function(res) {
    if (res.from === 'button') {}
    return {
      title: '分享优惠券',
      path: '/pages/splitcoupon/splitcoupon?user=' + order_info.user_id + "&coupon=" + order_info.coupon.id + "&shop_id=" + order_info.shop_id + "&order_sn=" + order_info.order_sn + "&cou_limit=" + order_info.cou_xianshi + "&invite_id=" + util.getCache('user_id'),
      imageUrl: imageUrl + 'image/wxapp/share_icon.jpg',
    }
  },
  wxshopping_recommend(e) {
    var goods_id = e.currentTarget.dataset.goods_id;
    var order_sn = e.currentTarget.dataset.order_sn;
    if (goods_id) {
      util.api('/api/wxapp/goods/wxshopping/recommend', function () {

      }, { goods_id: goods_id, order_sn: order_sn })
    }
  }
})
