// USERCARDINFO.JS 2018.03.06
var app = new getApp();
var util = require('../../utils/util.js');
var qrcode = require('../../utils/qrcode.js');
var barcode = require('../../utils/barcode.js');
var imageUrl = app.globalData.imageUrl;
var iconflag = true;
var card_id;
var card_no;
var card_info = [];
var buy_score = [];
var discount1 = 1;
var grade_state = 1;
var score1 = 1;
var store_content = 1;
var card_activation = 0;
var is_grade = 0;
var is_fullprice = 0;
var code = 0;
var seckillId=0;
var goods_id = 0;
var card_code = '';
var card_num = '';
var card_pwd = '';
var gift_id = 0;

// 绑定手机号
var mobile = util.getCache('mobile');
var is_bind_mobile;
var is_block = 0;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    showModal: false,
    iconDown: imageUrl +'image/wxapp/down_normal.png',
    iconUp: imageUrl + 'image/wxapp/scancode_right.png',
    card_id: 1,
    card_info: [],
    buy_score:[],
    discount1: 1,
    grade_state: 1,
    score1: 1,
    store_content: 1,
    is_grade : 0,
    more:0,
    card_goods_box:1,
    coupon_content:true
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    card_no = options.card_no;
    card_id = options.card_id;
    if (options.is_fullprice) {
      is_fullprice = options.is_fullprice;
    } else {
      is_fullprice = 0
    }
    if (this.data.linColor) {
      let linColor = this.data.linColor.slice(0, this.data.linColor.lastIndexOf(',')) + ',0.3)';
      this.setData({
        bgColor: linColor
      })
    }
    seckillId = options.seckillId ? options.seckillId : 0;
    code = options.code ? options.code : 0;
    goods_id = options.goods_id ? options.goods_id : 0;
    gift_id = options.gift_id ? options.gift_id : 0;
    var card_list = options.card_list;
    var sgan = options.sgan;
    var that = this;
    var winWidth = wx.getSystemInfoSync().windowWidth;
    var viewHeight = winWidth * 0.94 / 1.8;
    this.setData({
      viewHeight: viewHeight,
    })
    var windowHeight;
    wx.getSystemInfo({
      success: function (res) {
        windowHeight = res.windowHeight;
      }
    })
    this.setData({
      windowHeight: windowHeight
    })
    if (card_no){
      util.api('/api/card/detail', function (res) {
        console.log(1)
        card_info = res.content;
        console.log(card_info)
        that.loadData(card_info, 0, that);
        that.setData({
          card_list: card_list
        })
      }, { card_no: card_no  })
    } else if (card_id){
      console.log(2)
      util.api('/api/card/judgement', function (res) {
        console.log(res.content);
        if (res.content.card_info.is_delete == 1) {
          util.showModal('提示', '该会员卡已失效', function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return;
        }
        if (res.content.card_info.flag == 2) {
          util.showModal('提示', '该会员卡已停用', function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return;
        }
        if (res.content.card_info.flag == 3) {
          util.showModal('提示', '该会员卡已过期', function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return;
        }
        if (res.content.status == 1 && res.content.card_info.flag == 1) {
          card_info = res.content.card_info;
          is_block = 0
          is_bind_mobile = card_info.is_bind_mobile;
          that.loadData(card_info, 1, that);
        } else if (res.content.status == 0) {
          card_info = res.content.card_info;
          card_no = res.content.card_info.card_no;
          that.loadData(card_info, 0, that);
        }
      }, {   card_id: card_id })
    }

  },
  loadData: function (card_info, sgan, _this) {
    if (card_info.next != -1 && card_info.next != '' && card_info.next){
      var grade_condition = JSON.parse(card_info.next.grade_condition);
      var grade_pro = 0;
      var grade_pro_style = 0;
      var amount_style = '';
      if (grade_condition.grade_score) {
        _this.setData({
          grade_score: grade_condition.grade_score
        })
      }
      if (grade_condition.grade_money){
        _this.setData({
          grade_money: grade_condition.grade_money
        })
      }
      if (grade_condition.grade_score){
        grade_pro = (parseInt(card_info.score_amount) / grade_condition.grade_score).toFixed(2);
      }else{
        grade_pro = (parseInt(card_info.paid_amount) / grade_condition.grade_money).toFixed(2);
      }
      grade_pro = Number(grade_pro) * 100;
      if (grade_pro > 100){
        grade_pro = 100;
      }
      grade_pro_style = grade_pro - 3;
      if (grade_condition.grade_score && card_info.score_amount && card_info.score_amount.length >= 5 && grade_pro_style > 85){
        amount_style = 'float:right;';
      }
      if (grade_condition.grade_score && card_info.score_amount && card_info.score_amount.length >= 5 && grade_pro_style <= 85) {
        amount_style = 'left:' + grade_pro_style + '%';
      }
      if (grade_condition.grade_score && card_info.score_amount && card_info.score_amount.length < 5){
        if (grade_pro_style <= 88 ){
          amount_style = 'left:' + grade_pro_style +'%';
        }else{
          amount_style = 'left: 88%';
        }
      }
      if (grade_condition.grade_score == '' && card_info.paid_amount.length >= 5 && grade_pro_style > 85) {
        amount_style = 'float:right;';
      }
      if (grade_condition.grade_score == '' && card_info.paid_amount.length >= 5 && grade_pro_style <= 85) {
        amount_style = 'left:' + grade_pro_style + '%';
      }
      if (grade_condition.grade_score == '' && card_info.paid_amount.length < 5) {
        if (grade_pro_style <= 88) {
          amount_style = 'left:' + grade_pro_style + '%';
        } else {
          amount_style = 'left: 88%';
        }
      }
      _this.setData({
        grade_pro: grade_pro,
        amount_style: amount_style
      })
      if (card_info.next.buy_score){
        var nex_score = JSON.parse(card_info.next.buy_score);
        var next_offset_value = nex_score[0].value;
        var next_score_full = [];
        var next_score_each = [];
        next_score_each = nex_score[nex_score.length - 1];
        for (var i = 1; i < nex_score.length - 1; i++) {
          next_score_full.push(nex_score[i]);
        }
        _this.setData({
          next_offset_value: next_offset_value,
          next_score_full: next_score_full,
          next_score_each: next_score_each
        })
      }
    }
    if (card_info.storeinfo_list){
      for (var i = 0; i < card_info.storeinfo_list.length-1;i++){
        card_info.storeinfo_list[i].store_name = card_info.storeinfo_list[i].store_name + '，'
      }
    }
    if (card_info.activation){
      card_activation = card_info.activation;
    }
    if (card_info.card_no || card_info.activation_time != null){
      card_activation = 0;
    }
    if (card_info.discount){
      card_info.discount = parseFloat(card_info.discount);
    }
    var bg;
    var data_type_name;
    if (card_info.date_type == 0) {
      data_type_name = "日";
    } else if (card_info.date_type == 1) {
      data_type_name = "周";
    } else {
      data_type_name = "月";
    }
    _this.setData({
      data_type_name: data_type_name,
    })
    card_info.bg_img = imageUrl + card_info.bg_img;
    var date_type = card_info.date_type;
    if (card_info.bg_type == 1) {
      bg = 'url(' + card_info.bg_img + ') no-repeat';
    } else {
      bg = card_info.bg_color;
    }
    if ((card_info.card_type == 0 || card_info.card_type == 2 ) && card_info.buy_score){
      buy_score = JSON.parse(card_info.buy_score);
      var offset_value = buy_score[0].value;
      var score_full = [];
      var score_each = [];
      score_each = buy_score[buy_score.length - 1];
      for (var i = 1; i < buy_score.length - 1; i++) {
        score_full.push(buy_score[i]);
      }
      this.setData({
        score_full: score_full,
        score_each: score_each,
        offset_value: offset_value,
      })
    }
    if (sgan == 0) {
      var data = qrcode.createQrCodeImg(card_no, { 'size': 300 });
      _this.setData({
        img_code: data,
      })
    }
    if (card_info.shop_avatar == null) {
      card_info.shop_avatar = imageUrl + 'image/wxapp/shop_logo_default.png';
    }
    if (card_info.storeinfo_list){
      var arr = [];
      for (var i in card_info.storeinfo_list){
        arr[i] = card_info.storeinfo_list[i].store_name;
      }
      card_info.store_name = arr.join('，');
    }
    _this.setData({
      card_info: card_info,
      buy_score: buy_score,
      bg: bg,
      sgan: sgan
    })
  },
  onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
  },

  setDefault:function(e){
    var that = this;
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    util.api('/api/card/default', function (res) {
      if(res.error == 0){
        card_info.is_default = 1;
        that.setData({
          card_info: card_info
        })
        util.toast_success('设置成功');
      }else{
        util.toast_fail('设置失败');
      }
    }, { card_no: card_no,  form_id:form_id,open_id:open_id })
  },
  recharge:function(e){
    var card_id = e.currentTarget.dataset.card_id;
    util.redirectTo({
      url: '/pages/cardpay/cardpay?card_id=' + card_id
    })
  },
  to_store: function (e) {
    var store_id = e.currentTarget.dataset.store_id;
    util.navigateTo({
      url: '/pages/storeinfo/storeinfo?id=' + store_id
    })
  },
  to_store_list: function (e) {
    util.navigateTo({
      url: '/pages/storelist/storelist'
    })
  },
  phoneCall: function (e) {
    wx.makePhoneCall({
      phoneNumber: card_info.mobile
    })
  },
  tapBlock1:function(e){
    if (discount1 == 0) {
      discount1 = 1;
      this.setData({
        discount1: discount1
      })
      return;
    }
    if (discount1 == 1) {
      discount1 = 0;
      this.setData({
        discount1: discount1
      })
      return;
    }
  },
  tapGrade:function(e){
    if (grade_state == 0) {
      grade_state = 1;
      this.setData({
        grade_state: grade_state
      })
      return;
    }
    if (grade_state == 1) {
      grade_state = 0;
      this.setData({
        grade_state: grade_state
      })
      return;
    }
  },
  tapScore1: function (e) {
    if (score1 == 0) {
      score1 = 1;
      this.setData({
        score1: score1
      })
      return;
    }
    if (score1 == 1) {
      score1 = 0;
      this.setData({
        score1: score1
      })
      return;
    }
  },
  storeBlock:function(e){
    if (store_content == 0){
      store_content = 1;
      this.setData({
        store_content: store_content
      })
      return;
    }
    if (store_content == 1){
      store_content = 0;
      this.setData({
        store_content: store_content
      })
      return;
    }
  },
  nextSet:function(e){
    is_grade = 1;
    this.setData({
      is_grade: is_grade
    })
  },
  closeGrade: function (e) {
    is_grade = 0;
    this.setData({
      is_grade: is_grade
    })
  },
  arrow: function(e) {
    var that = this;
    that.setData({
      iconUrl: imageUrl + 'image/wxapp/down_normal.png',
      iconflag: true
    });
  },
  modalinput: function () {
    barcode.barcode('barcode', card_no, 580, 150);
    this.setData({
      showModal: true
    })
  },

  // 领取会员卡
  getUserCard:function() {
    var that = this;
    that.data.card_info.buy_score = '';
    var card_id = that.data.card_info.id;
    var card = {};
    card.card_id = card_id;
    card_info = JSON.stringify(card);
    console.log(that.data.card_info)
    if (that.data.card_info.is_pay == 2){
      if (that.data.card_info.receive_action == 1){
        var receive_action = 1
      } else {
        var receive_action = 2
      }
      that.setData({
        is_receive: 1,
        receive_action: receive_action
      })
    } else {
      util.api('/api/card/getcard', function (res) {
        if (res.error == 0) {
          if (res.content == -6) {
            util.toast_fail('当前等级已最高');
            return;
          } else if (res.content.grade_card) {
            var text = '没有达到该卡的条件';
            if (res.content.grade_card.grade_score > 0) {
              text = '您的积分没有达到' + res.content.grade_card.grade_score + '积分';
            } else {
              text = '您的消费金额没有达到' + res.content.grade_card.grade_money + '元';
            }
            util.showModal('', text, function (res) {
              return false;
            }, false);
            return;
          } else if (res.content == -1) {
              util.toast_fail('此卡已存在');
          } else {
            util.toast_success('领取成功', function () {
              if (card_activation == 1) {
                setTimeout(function () {
                  util.navigateTo({
                    url: '/pages/memberinfo/memberinfo?act=1&card_no=' + res.content + '&examine=' + that.data.card_info.examine + "&is_fullprice=" + is_fullprice + "&code=" + code + "&seckillId=" + seckillId + '&goods_id=' + goods_id + "&gift_id=" + gift_id
                  })
                }, 2000);
              } else {
                if (is_fullprice == 0 && code == 0 && seckillId == 0 && goods_id == 0 && gift_id == 0) {
                  setTimeout(function () {
                    util.redirectTo({
                      url: '/pages/usercardlist/usercardlist',
                    })
                  }, 2000);
                } else if (parseInt(gift_id)) {
                  util.redirectTo({
                    url: 'pages1/presentchoose/presentchoose?gift_id=' + gift_id,
                  })
                } else if (parseInt(is_fullprice)) {
                  util.redirectTo({
                    url: '/pages/fullprice/fullprice?identity_id=' + is_fullprice,
                  })
                } else if (parseInt(seckillId)) {
                  util.redirectTo({
                    url: '/pages/seckillitem/seckillitem?sk_id=' + seckillId,
                  })
                } else if (parseInt(goods_id)){
                  util.redirectTo({
                    url: '/pages/item/item?good_id=' + goods_id,
                  })
                } else {
                  util.redirectTo({
                    url: '/pages/getcoupon/getcoupon?code=' + code,
                  })
                }
              }
            });
          }
        } else {
          util.toast_fail('领取失败');
        }
      }, {   card_info: card_info })
    }


  },
  getUsing:function(e){
    if (is_fullprice == 0 && code == 0 && seckillId == 0 && goods_id == 0){
      util.redirectTo({
        url: '/pages/memberinfo/memberinfo?act=1&card_no=' + card_no + '&examine=' + card_info.examine,
      })
    }else{
      util.redirectTo({
        url: '/pages/memberinfo/memberinfo?act=1&card_no=' + card_no + '&examine=' + card_info.examine + "&is_fullprice=" + is_fullprice + "&code=" + code + "&seckillId=" + seckillId + '&goods_id=' + goods_id
      })
    }

  },
  toCardRecord: function(e){
    util.navigateTo({
      url: '/pages/usercardrecord/usercardrecord?card_no=' + e.currentTarget.dataset.card_no,
    })
  },
  toUpgrade: function (e) {
    util.navigateTo({
      url: '/pages/usercardup/usercardup'
    })
  },
  /**
   * 隐藏模态对话框
   */
  hideModal: function () {
    this.setData({
      showModal: false
    });
  },
  get_card:function(e){
    var that = this;
    if (is_bind_mobile == 1 && util.getCache('mobile') == '') {
      util.checkSession(function () {
        that.setData({
          is_block: is_block = 1
        })
      })
      return false;
    }
    var card_id = e.currentTarget.dataset.card_id;
    util.api('/api/card/judgement', function (res) {
      if(res.error == 0){
        if(res.content.status == 0){
          util.toast_fail('此卡已存在');
        } else {
          if (is_fullprice == 0 && code == 0 && seckillId == 0 && goods_id == 0){
            util.redirectTo({
              url: '/pages/cardCheckout/cardCheckout?card_id=' + card_id
            })
          }else{
            util.redirectTo({
              url: '/pages/cardCheckout/cardCheckout?card_id=' + card_id + "&is_fullprice=" + is_fullprice + "&code=" + code + "&seckillId=" + seckillId + '&goods_id=' + goods_id,
            })
          }
        }
      }
    }, { card_id: card_id})
  },
  to_search:function(e){
    var card_id = e.currentTarget.dataset.card_id;
    util.navigateTo({
      url: '/pages/searchs/search?card_id=' + card_id
    })
  },

  bindGetPhoneNumberOk: function (e) {
    mobile = e.detail.phoneNumber;
  },

  closeIpt: function () {
    this.setData({
      is_receive: 0,
      receive_action: 0
    })
  },
  bindCode:function(e){
    card_code = e.detail.value;
  },
  bindCardNum:function(e){
    card_num = e.detail.value;
  },
  bindCardPwd:function(e){
    card_pwd = e.detail.value;
  },
  fetch_card:function(){
    var that = this;
    util.api('/api/wxapp/card/code/receive',function(res){
      if(res.error == 0){
        if (res.content == -6) {
          util.toast_fail('当前等级已最高');
          return;
        } else if (res.content.grade_card) {
          var text = '没有达到该卡的条件';
          if (res.content.grade_card.grade_score > 0) {
            text = '您的积分没有达到' + res.content.grade_card.grade_score + '积分';
          } else {
            text = '您的消费金额没有达到' + res.content.grade_card.grade_money + '元';
          }
          util.showModal('', text, function (res) {
            return false;
          }, false);
          return;
        } else if (res.content == -1) {
          util.toast_fail('此卡已存在');
        } else {
          util.toast_success('领取成功', function () {
            if (card_activation == 1) {
              setTimeout(function () {
                util.navigateTo({
                  url: '/pages/memberinfo/memberinfo?act=1&card_no=' + res.content + '&examine=' + that.data.card_info.examine,
                })
              }, 2000);
            } else {
              setTimeout(function () {
                util.redirectTo({
                  url: '/pages/usercardlist/usercardlist',
                })
              }, 2000);
            }
          });
        }
      } else {
        util.showModal('', res.message, function () {
          return false;
        }, false);
      }
    }, { user_id: util.getCache('user_id'), card_id: card_id, code: card_code, card_no: card_num, card_pwd: card_pwd})
  },
  checkMore:function(e){
    let more = e.currentTarget.dataset.more;
    more = more == 1 ? 0 : 1;
    this.setData({
      more:more
    })
  },
  goodsBoxBlock:function(e){
    let card_goods_box;
    card_goods_box = this.data.card_goods_box == 1 ? 0 : 1;
    this.setData({
      card_goods_box: card_goods_box
    })
  },
  to_goods:function(e){
    let goods_id = e.currentTarget.dataset.goods_id;
    if (card_info.exchang_surplus == 0){
      util.showModal('提示','此卡无剩余可兑换次数', function () {
        util.jumpLink('/pages/item/item?good_id=' + goods_id,'navigateTo')
      }, true,'取消', '原价购买')
    } else {
      util.navigateTo({
        url: '/pages/item/item?good_id=' + goods_id + '&from_count_card=1',
      })
    }
  },
  more_card_goods(){
    util.jumpLink('/pages/searchs/search?from_count_card=1', 'navigateTo')
  },
  couponToggle(){
    this.setData({
      coupon_content : !this.data.coupon_content
    })
  },
  viewCoupon(e){
    let coupon_id = e.currentTarget.dataset.coupon_id;
    util.jumpLink('/pages/getcoupon/getcoupon?code=' + coupon_id, 'navigateTo')

  },
  to_cou_package(e) {
    let pack_id = e.currentTarget.dataset.pack_id;
    util.jumpLink("/pages/couponpackage/couponpackage?pack_id=" + pack_id)
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
    wx.stopPullDownRefresh();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  }
})
