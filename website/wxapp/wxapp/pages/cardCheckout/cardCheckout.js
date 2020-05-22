// pages/cardCheckout/cardCheckout.js
var app = getApp();
var imageUrl = app.globalData.imageUrl;
var util = require('../../utils/util.js');
var shop_avatar;
var is_fullprice = 0;
var code = 0;
var seckillId = 0;
var goods_id = 0;
var pack_id;
var order_action = 1;
var card_id = 0;
var score_pay_control = 0;
var account_pay_control = 0;
var card_account_pay_control = 0;

global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    total_micro_second: -1,
    is_delete: 0,
    info: null,
    cardMode:true,
    goods:{},
    user_money:{
      score_ratio: 100
    },
    card_account_pay_control:0,
    account_pay_control:0,
    choose_card: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    if (!util.check_setting(options)) return;
    console.log(options)
    if (options.is_fullprice) {
      is_fullprice = options.is_fullprice;
    } else {
      is_fullprice = 0
    }
    order_action = options.order_action ? options.order_action : 1;
    card_id = options.card_id ? options.card_id : card_id;
    seckillId = options.seckillId ? options.seckillId : 0;
    code = options.code ? options.code : 0;
    goods_id = options.goods_id ? options.goods_id : 0;
    if(order_action == 2){
      that.setData({
        page_name:"优惠券礼包结算"
      })
    }
    var winWidth = wx.getSystemInfoSync().windowWidth;
    var viewHeight = winWidth * 0.94 / 1.8;
    this.setData({
      viewHeight: viewHeight,
    })
    util.api('/api/wxapp/card/pay', function (res) {
      if(res.error == 0){
        var info = res.content;
        var card_info = res.content.card_info;
        var cou_info = res.content.order_goods;
        that.data.goods = info.card_info;

        shop_avatar = res.content.shop_avatar
        if (shop_avatar == null) {
          shop_avatar = imageUrl + 'image/wxapp/shop_logo_default.png';
        }
        var bg;
        if(order_action == 1){
          card_info.bg_img = imageUrl + card_info.bg_img;
          var date_type = card_info.date_type;
          if (card_info.bg_type == 1) {
            bg = 'url(' + card_info.bg_img + ') no-repeat';
          } else {
            bg = card_info.bg_color;
          }
          if (info.card_info.is_delete == 1) {
            util.showModal('提示', '该会员卡已删除', function () {
              util.reLaunch({
                url: '/pages/index/index'
              })
            });
            return;
          }
          
          that.create_order = {
            account_dis: 0,
            score_dis: 0,
            money_paid: that.data.goods.pay_fee,
            card_id: options.card_id,
            order_amount: that.data.goods.pay_fee,
            account_discount: 0,
            total_price: that.data.goods.pay_fee
          }
          that.setData({
            goods: that.data.goods,
            user_money: that.data.user_money,
            create_order: that.create_order,
            card_info: card_info,
            bg: bg,
            shop_avatar: shop_avatar,
           
          })
          if (that.data.goods.pay_type == 1) {
            that.setData({
              score_money_input: that.data.goods.pay_fee
            })
          }
        }else{
          for (var i in cou_info){
            if (cou_info[i].act_code == "discount"){
              cou_info[i].denomination = parseFloat(cou_info[i].denomination).toFixed(0);
            }
          }
          that.setData({
            cou_info: cou_info,
          })
        }

        that.data.user_money.account = info.account;
        that.data.user_money.score = info.score;
        that.data.user_money.member_card_money = info.member_card_info ? info.member_card_info.money : 0;
        that.data.create_order
        that.data.create_order = {
          money_paid: info.money_paid,
          card_id: options.card_id,
          order_amount: info.order_amount,
          account_discount: 0,
          score_discount: 0,
          member_card_balance: 0,
          service_terms: info.service_terms,
          service_choose: info.service_choose,
          service_name: info.service_name
        }
        if ((order_action == 1 && card_info.pay_type == 1) || (order_action == 2 && card_info.access_mode == 1)) {
          that.data.create_order.score_discount = info.order_amount;
        }
        if (order_action == 2 && card_info.access_mode == 0) {
          that.initMemberCard(info);
        }
        that.setData({
          goods: that.data.goods,
          order_info: info,
          order_action: order_action,
          user_money: that.data.user_money,
          create_order: that.data.create_order
        })

      } else {
        util.showModal('提示', res.message);
        util.redirectTo({
          url: '/pages/index/index',
        })
        return false;
      }
    }, { card_id: card_id, order_action: order_action});
  },
  initMemberCard: function (postData) {
    // 会员卡
    var card_array = postData.member_card_list;
    for (var i in card_array) {
      card_array[i].src_yes = imageUrl + 'image/wxapp/selected.png';
      card_array[i].src_no = imageUrl + 'image/wxapp/icon_rectangle.png';
      card_array[i].card_src = imageUrl + 'image/wxapp/icon_rectangle.png';
      card_array[i].shop_logo = postData.shop_logo;
      if (!postData.shop_logo) {
        card_array[i].shop_logo = imageUrl + 'image/wxapp/shop_logo_default.png';
      }
      if (card_array[i].bg_type == 1) {
        card_array[i].bg = "url('" + imageUrl + card_array[i].bg_img + "') no-repeat";
      } else {
        card_array[i].bg = card_array[i].bg_color;
      }
      if (i.toString() == postData.member_card_no.toString()) {
        card_array[i].card_src = 1;
        // this.data.create_order.member_card_balance = parseFloat(card_array[i].member_card_money) > parseFloat(this.data.create_order.money_paid) ? this.data.create_order.money_paid : card_array[i].member_card_money;
        // this.data.create_order.money_paid = parseFloat(this.data.create_order.money_paid) - parseFloat(this.data.create_order.member_card_balance);
        // this.data.user_money.member_card_money = card_array[i].member_card_money;
        // this.setData({
        //   card_choose_name: card_array[i].card_name,
        //   create_order: this.data.create_order,
        //   card_account_pay_control: 2,
        //   member_card_input: this.data.create_order.member_card_balance,
        //   user_money: this.data.user_money
        // })
        this.data.choose_card.card_choose_name = card_array[i].card_name;
        this.data.choose_card.card_no = card_array[i].card_no;
        this.data.user_money.member_card_money = card_array[i].member_card_money;
        this.data.create_order.card_no = card_array[i].card_no;
        this.setData({
          card_choose_name: card_array[i].card_name,
          user_money: this.data.user_money
        })
      }
      if (card_array[i].expire_time) {
        card_array[i].expire_time = card_array[i].expire_time.substring(0, 10);
      }
    }
    this.setData({
      card_array: card_array
    })
  },
  cardConfirm: function (e) {
    this.setData({
      cardMode: true,
    });
    var member_card_info = this.data.card_array[this.data.choose_card.card_no];
    if (parseFloat(member_card_info.member_card_money, 2) < parseFloat(this.data.create_order.member_card_balance, 2)) {
      this.data.create_order.member_card_balance = member_card_info.member_card_money;
    }
    this.data.user_money.member_card_money = member_card_info.member_card_money;
    this.data.create_order.card_no = this.data.choose_card.card_no;
    this.setData({
      user_money : this.data.user_money,
      create_order: this.data.create_order,
      card_choose_name: this.data.choose_card.card_choose_name,
    });
  },
  jifen_fo: function (e) {
    this.setData({
      score_pay_control: 1,
    })
  },
  yue_fo: function () {
    this.setData({
      account_pay_control: 1
    })
  },
  mem_fo: function () {
    this.setData({
      card_account_pay_control: 1
    })
  },
  addMsg: function (e) {
    if (e.detail.value.replace(/^\s+|\s+$/g, '').length > 240 && shop_flag == 1) {
      util.showModal("提示", "备注长度不得超过240字");
      return false;
    }
    this.data.create_order.msg = e.detail.value;
  },
  radioChange: function (e) {
    this.data.create_order.pay_code = e.detail.value;
  },
  member_card: function (e) {
    var member_card_input = e.detail.value ? e.detail.value : 0;
    var old_member_card_balance = this.data.create_order.member_card_balance;
    if (isNaN(member_card_input) || !isNaN(member_card_input) && member_card_input < 0) {
      util.showModal('', "请输入正确的余额");
      this.setData({
        member_card_input: old_member_card_balance ? old_member_card_balance : ''
      })
      return false;
    } else {
      if (member_card_input > parseInt(this.data.user_money.member_card_money)) {
        var tishi3 = "最多可以使用会员卡余额：" + this.data.user_money.member_card_money + "元";
        util.showModal('提示', tishi3);
        this.setData({
          member_card_input: old_member_card_balance ? old_member_card_balance : ''
        })
        return false;
      }
    }
    var new_money_paid = (parseFloat(this.data.create_order.money_paid) + parseFloat(old_member_card_balance ? old_member_card_balance : 0) - parseFloat(member_card_input)).toFixed(2);
    if (new_money_paid < 0) {
      util.showModal('提示', '使用余额不得超过订单金额');
      this.setData({
        member_card_input: old_member_card_balance ? old_member_card_balance : ''
      })
      return false;
    }
    this.data.create_order.member_card_balance = parseFloat(member_card_input).toFixed(2);
    this.data.create_order.money_paid = new_money_paid;
    this.setData({
      member_card_input: member_card_input > 0 ? member_card_input : '',
      create_order: this.data.create_order,
      card_account_pay_control: this.data.create_order.member_card_balance > 0 ? 2 : 0
    })
  },
  // 余额
  user_account: function (e) {
    var user_account_input = e.detail.value ? e.detail.value : 0;
    var old_account_discount = this.data.create_order.account_discount;
    if (isNaN(user_account_input) || !isNaN(user_account_input) && user_account_input < 0) {
      util.showModal('', "请输入正确的余额");
      this.setData({
        user_account_input: old_account_discount ? old_account_discount : ''
      })
      return false;
    }
    else {
      var msg = '';
      if (user_account_input > parseInt(this.data.user_money.account)) {
        msg = "最多可以使用：" + this.data.user_money.account + "余额";
        user_account_input = this.data.user_money.account;
      }
      var new_money_paid = (parseFloat(this.data.create_order.money_paid) + parseFloat(old_account_discount ? old_account_discount : 0) - parseFloat(user_account_input)).toFixed(2);
      if (new_money_paid < 0) {
        msg = '使用余额不得超过订单金额';
        user_account_input = (parseFloat(this.data.create_order.money_paid) + parseFloat(old_account_discount ? old_account_discount : 0)).toFixed(2);
      }
      if (msg != '') {
        util.showModal('提示', msg);
      }

      this.data.create_order.account_discount = parseFloat(user_account_input).toFixed(2);
      this.data.create_order.money_paid = new_money_paid > 0 ? new_money_paid : 0;
      this.setData({
        user_account_input: user_account_input <= 0 ? '' : user_account_input,
        create_order: this.data.create_order,
        account_pay_control: this.data.create_order.account_discount > 0 ? 2 : 0
      });
    }
  },
  cardClick: function (e) {
    //会员卡弹框显示
    this.setData({
      cardMode: false
    })
  },
  cardCancel: function (e) {
    //关闭会员卡弹框
    this.setData({
      cardMode: true
    })
  },
  chooseCard: function (e) {
    //选择会员卡
    var card_choose_name = e.currentTarget.dataset.name;
    var card_no = e.currentTarget.dataset.card_no;
    var id = e.currentTarget.dataset.id;
    var card_arr = this.data.card_array;
    for (var i in card_arr) {
      if (i == id) {
        card_arr[i].card_src = 1;
      } else {
        card_arr[i].card_src = card_arr[i].src_no;
      }
    }
    this.data.choose_card.card_choose_name = card_choose_name;
    this.data.choose_card.card_no = card_no;
    this.setData({
      card_array: card_arr
    })
  },
  // 提交订单
  order_confirm: function (e) {
    var that = this;
    var create_order = that.data.create_order;
    create_order.open_id = util.getCache('openid');
    create_order.form_id = e.detail.formId;
    var order_info = JSON.stringify(create_order);
    if (that.data.is_submit) return false;
    that.data.is_submit = true;
    if (order_action == 1) {
      util.api('/api/card/judgement', function (res) {
        if (res.error == 0) {
          if (res.content.status == 0) {
            util.toast_fail('此卡已存在');
          } else {
            that.to_checkout(order_info)
          }

        }
      }, { card_id: create_order.card_id })
    } else {
      that.to_checkout(order_info)
    }
  },
  // 结算
  to_checkout: function (order_info) {
    var that = this;
    util.api('/api/wxapp/card/checkout', function (res) {
      if (res.error == 0) {
        var order_sn = res.content.order_sn;
        if (that.data.create_order.money_paid > 0) {
          wx.requestPayment({
            'timeStamp': res.content.timeStamp,
            'nonceStr': res.content.nonceStr,
            'package': res.content.package,
            'signType': typeof res.content.signType == "undefined" ? 'MD5' : res.content.signType,
            'paySign': res.content.paySign,
            'success': function (res) {
              util.toast_success('支付成功');
              if (order_action == 1) {
                if (is_fullprice == 0 && code == 0 && seckillId == 0 && goods_id == 0) {
                  util.redirectTo({
                    url: '/pages/usercardlist/usercardlist',
                  })
                } else {
                  util.redirectTo({
                    url: '/pages/usercardlist/usercardlist?is_fullprice=' + is_fullprice + "&code=" + code + "&seckillId=" + seckillId + '&goods_id=' + goods_id,
                  })
                }
              } else {
                util.redirectTo({
                  url: '/pages/couponlist/couponlist',
                })
              }
            },
            'fail': function (res) {
              util.redirectTo({
                url: '/pages/index/index',
              })
            },
            'complete': function (res) {
            }
          });
        } else {
          if (order_action == 1) {
            if (is_fullprice == 0 && code == 0 && seckillId == 0 && goods_id == 0) {
              util.redirectTo({
                url: '/pages/usercardlist/usercardlist',
              })
            } else {
              util.redirectTo({
                url: '/pages/usercardlist/usercardlist?is_fullprice=' + is_fullprice + "&code=" + code + "&seckillId=" + seckillId + '&goods_id=' + goods_id,
              })
            }
          } else {
            util.redirectTo({
              url: '/pages/couponlist/couponlist',
            })
          }
          
        }
      } else {
          util.showModal('提示', res.message);
          return false;
      }
      that.data.is_submit = false;
    }, { orderinfo: order_info, order_action: order_action });
  },
  // 使用商品
  to_search: function (e) {
    var al_code = e.currentTarget.dataset.al_code;
    util.jumpLink("/pages/searchs/search?alias_code=" + al_code);
  },
  toRule: function () {
    util.jumpToWeb('/wxapp/sercice/ServiceDocument');
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

})
