const util = require('../../utils/util.js')
var spec_mixin = require("../goodscommon/spec.js")
var decorate = require("../common/decorate.js")
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var good_id = '';
var mobile = util.getCache('mobile');
var is_bind_mobile;
var is_block = 0;
var dist_id = '';
var goods_main_img = '';
var choose_list = {};
var spec_list = {};
var prd_list = {};
var deli;
var code;
var cat_list = [];
var prd_str = [];
var pro_con_first = [];
var is_prd_good = 0;
var spec_view = 0;
var login_view = 0;
var spec_check = 0;
var buy_number = 1;
var limit_num = 1;
var spec_name;
var goods_ids;
var speclist = [];
var pictorial;
var imgUrls;
var commimgUrls;
var zhege;
var form_id;
var open_id;
var off_buss;
var good_nums = 1;
var is_play = 0;
var is_share = 0;
var os_type = '';
var disableds = false;
var gd;
var set_time_out;
var total_micro_second = 0;
var vali;
var net_type = 0;
var is_second = 0;
var winWidth = 0; //当前屏幕的宽度
var winHeight = 0; //当前屏幕的高度
var top_display; //判断回到顶部按钮是否显示
var real_Height;
var is_max = 0;
var is_min = 0;
var coupon_key = 0;
var is_bind = 0;
var hh = 0;
var show_sales_number = 0;
var share_img;
var set_time_outs = [];
var real_Width; //当前屏幕宽度
var input_vali;
var presale_id;
var page_id;
var posterBase64 = '';
function contains(arr, obj) {
  var i = arr.length;
  while (i--) {
    if (arr[i] === obj) {
      return true;
    }
  }
  return false;
}

function get_deliver_fee(that) {
  util.api('?c=app_cart&m=get_goods_deliver_fee', function(res) {
    deli = res.content;
    if (typeof(deli.code) == 'undefined') {
      deli.code = 0;
    }
    that.setData({
      can_deli: deli.code,
      deliver_fee: deli.fee
    });
  }, {
    goods_id: good_id,
    dist_id: dist_id,
    buy_num: buy_number
  });
};
global.wxPage({
  mixins: [decorate,spec_mixin],
  data: {
    images: {},
    collect_no: imageUrl + 'image/wxapp/collect_no.png',
    collect_yes: imageUrl + 'image/wxapp/collect_yes.png',
    coupon_back: imageUrl + '/image/wxapp/cou-bac2.png',
    click_more: imageUrl + '/image/wxapp/backward_right.png',
    icon_head: imageUrl + '/image/admin/head_icon.png',
    img_sercer: imageUrl + '/image/wxapp/server_icon.png',
    img_cart: imageUrl + '/image/wxapp/cart_icon.png',
    img_close: imageUrl + '/image/wxapp/close_icon.png',
    img_service: imageUrl + 'image/wxapp/icon_service.png',
    imageUrl: app.globalData.imageUrl,
    baseUrl: app.globalData.baseUrl,
    indicatorDots: false,
    interval: 3000,
    duration: 1000,
    showModalStatus: false,
    total_micro_second: -1,
    actionSheetHidden: true,
    giftSheetHidden: true,
    kucun: true,
    deliver_fee: 0,
    spec_view: 0,
    coupon_flag: 0,
    limit_buy_num: 1,
    limit_max_num: 0,
    is_block: 0,
    spec_array: [],
    spec_id_list: {},
    choose_list: {},
    speclist: [],
    buy_number: '1',
    proMode: true,
    specMove: true,
    ruleMode: true,
    toast1Hidden: true,
    toast2Hidden: true,
    toast3Hidden: true,
    good_nums: 1,
    is_play: 0,
    is_share: 0,
    disableds: false,
    net_type: 0,
    winHeight: 0,
    winWidth: 0,
    top_display: 0,
    is_second: 0,
    isFold: false,
    hh: 0,
    is_bind: 0,
    blen: 0,
    is_max: 0,
    is_min: 0,
    nickName: util.getCache('nickName'),
    click_num: false,
    show_sales_number: 0,
    window_data: {},
    checked_card_name: '请选择',
    show_exchang: 0,
    exchang_block: 0,
    exchang_no:'',
    exchang_good:0,
    exchang_surplus:0,
    giftMore: false
  },
  onLoad: function(option) {
    if (!util.check_setting(option)) return;
    mobile = util.getCache('mobile');
    presale_id = option.presale_id;
    clearTimeout(set_time_out);
    choose_list = {};
    buy_number = 1;
    spec_list = {};
    prd_list = {};
    goods_main_img = '';
    good_nums = 1;
    limit_num = 1;
    is_prd_good = 0;
    cat_list = [];
    prd_str = [];
    top_display: 0;
    show_sales_number = 0;
    var that = this;
    zhege = this;
    var lat;
    var lng;
    util.getUserLocation(function(location) {
      util.api('/api/wxapp/presale/goods', function(res) {
        if (res.error > 0) {
          util.showModal('提示', res.message, function() {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return;
        }
        if (res.content.goods_id) {
          util.api('/api/wxapp/user_goods/record', function(res1) {

          }, {
            goods_id: res.content.goods_id,
            active_type: res.content.goods_type,
            active_id: "",
            type: 1
          })
        }
        cat_list = [];
        prd_str = [];
        that._gd = gd = res.content;
        page_id = gd.goods_page_id;
        that.page_id = page_id;
        that.setData({
          page_id: page_id,
        })
        if (page_id > 0) {
          that.requestDecoratePageData(page_id, 0, that.processWindowData.bind(that));
        }
        is_block = 0
        is_bind_mobile = gd.is_bind_mobile; //是否要绑定手机号判断
        goods_ids = gd.goods_id;
        off_buss = gd.business_state;
        util.api('/api/wxapp/presale/shareimg', function(res) {
          if (res.error == 0) {
            share_img = res.content;
          } else {}

        }, {
          presale_id: presale_id,
          goods_id: goods_ids
        });
        if (gd.exchang_card != 'undefined' && Array.isArray(gd.exchang_card)) {
          gd.exchang_card.forEach(function (e, i) {
            e.is_checked = 0;
          })
        }
        //倒计时
        total_micro_second = gd.countdown_time;
        if (total_micro_second > 0) {
          that.countdown(that);
        }
        //最低购买限制
        if (gd.limit_buy_num > 1) {
          buy_number = gd.limit_buy_num;
          good_nums = gd.limit_buy_num;
          that.setData({
            buy_number: gd.limit_buy_num,
            good_nums: gd.limit_buy_num
          })
        }
        that.data.limit_max_num = gd.limit_max_num;
        wx.getNetworkType({
          success: function(res) {
            if (res.networkType != 'wifi') {
              that.setData({
                net_type: 1
              })
            }
          },
        })
        wx.getSystemInfo({
          success: function(res) {
            real_Height = res.windowHeight * 1.5;
          },
        })
        imgUrls = gd.imgs,
          goods_main_img = gd.goods_img;
        limit_num = gd.limit_buy_num;
        spec_name = gd.specs.spec_name;
       
        if (gd.goods_desc != null) {
          gd.goods_desc = util.filterRichText(gd.goods_desc);
        }
        //评价
        var strRegex = "^((https|http|ftp|rtsp|mms)?://)";
        var re = new RegExp(strRegex);
        if (gd.comment_info != null) {
          if (!re.test(gd.comment_info.user_avatar)) {
            gd.comment_info.user_avatar = imageUrl + gd.comment_info.user_avatar;
          }
          gd.comment_info.comm_img = JSON.parse(gd.comment_info.comm_img);
          commimgUrls = gd.comment_info.comm_img;
          var com_reg = /^(.).+(.)$/g;
          gd.comment_info.name = gd.comment_info.username.replace(com_reg, "$1**$2");
        }
        if (gd.shop_info.shop_avatar == null) {
          gd.shop_info.shop_avatar = imageUrl + 'image/wxapp/shop_logo_default.png';
        }
        if (gd.fanli_percent != null && gd.fanli_percent != '0') {
          if (gd.shop_price.indexOf("~") > -1) {
            var enen = gd.shop_price.split("~")[1];
            gd.max_fanli = parseFloat(gd.fanli_percent) / 100 * enen;
          } else {
            gd.max_fanli = parseFloat(gd.fanli_percent) / 100 * gd.shop_price;
          }
          gd.max_fanli = gd.max_fanli.toFixed(2);
        }

        gd.preSale.start_time = gd.preSale.start_time.slice(5);
        gd.preSale.start_time = gd.preSale.start_time.substring(0, gd.preSale.start_time.length - 3);
        gd.preSale.end_time = gd.preSale.end_time.slice(5);
        gd.preSale.end_time = gd.preSale.end_time.substring(0, gd.preSale.end_time.length - 3);
        gd.preSale.deliver_time = gd.preSale.deliver_time.slice(5);
        gd.preSale.deliver_time = gd.preSale.deliver_time.substring(0, gd.preSale.deliver_time.length - 3);
        //商品信息
        that.setData({
          gd: gd,
          goods_name: gd.goods_name,
          goods_ad: gd.goods_ad,
          item_price: gd.shop_price,
          pre_sale_money: gd.presale_money || '0.00',
          pre_discount_money: gd.pre_discount_money || '0.00',
          goods_num: gd.goods_number,
          sale_num: gd.goods_sale_num,
          imgUrls: gd.imgs,
          goods_img: gd.goods_img,
          spec_name: gd.specs.spec_name,
          limit_buy_num: gd.limit_buy_num,
          limit_max_num: gd.limit_max_num,
          shop_info: gd.shop_info,
          collection: gd.collection,
          comment_info: gd.comment_info,
          is_block: is_block,
          good_nums: good_nums,
          is_min: is_min,
          show_sales_number: gd.show_sales_number,
          presale_id: presale_id,
        })
       
        //商品标签
        if (gd.labels) {
          var label_list = [];
          if (gd.labels != null) {
            for (var i = 0; i < gd.labels.length; i++) {
              label_list[i] = gd.labels[i];
            }
          }
          that.setData({
            label_list: label_list,
          })
        }
        //优惠券优惠券
        if (gd.recommend_coupons) {
          var view_coupon_list = [];
          var view_coupon = 1;
          for (var i = 0; i < gd.recommend_coupons.length; i++) {
            gd.recommend_coupons[i].disableds = false;
            if (i < 2) {
              view_coupon_list[i] = gd.recommend_coupons[i];
            }
          }
          that.setData({
            view_coupon_list: view_coupon_list,
            coupon_list: gd.recommend_coupons,
            coupon_flag: 1,
            view_coupon: view_coupon
          })
        }
        //促销
        if (gd.promotion) {
          var view_promotion_list = [];
          var view_promotion = 1;
          for (var i = 0; i < gd.promotion.length; i++) {
            view_promotion_list = gd.promotion[i];
          }
          var is_pro_type = view_promotion_list.type;
          var pro_con_amount;
          var pro_name_arr = ["每满减", "满减", "满折"];
          var pro_name;
          pro_con_first = [];
          pro_name = pro_name_arr[is_pro_type - 1];
          var promotion_condition = view_promotion_list.condition;
          pro_con_first = promotion_condition[0];
          pro_con_amount = pro_con_first.amount;
          that.setData({
            view_promotion_list: view_promotion_list,
            pro_name: pro_name,
            promotion_condition: promotion_condition,
            pro_con_first: pro_con_first,
            is_pro_type: is_pro_type,
            pro_con_amount: pro_con_amount,
            view_promotion: view_promotion
          })
        }
        that.getReplyRect();
      }, {
        presale_id: presale_id,
        location: JSON.stringify(location)
      }, '', true);
    })

    //获取cache
    choose_list['user_id'] = util.getCache('user_id');
    //登录
    that.setData({
      login_view: login_view
    })
    //购物车商品数
    that.changeCartGoodsNumber();
    //邀请用户
    if (option.invite_id > 0) {
      this.setData({
        invite_id: option.invite_id
      })
    }
  },
  onShow(){
    //购物车商品数
    this.changeCartGoodsNumber();
  },
  // 定金膨胀
  to_normal: function(e) {
    var form_id = e.detail.formId;
    var open_id = util.getCache('openid');
    util.api("/api/wxapp/common/saveformid", function(res) {

    }, {
      form_id: form_id,
      open_id: open_id
    });
    util.redirectTo({
      url: '/pages/item/item?good_id=' + gd.goods_id,
    })
  },
  return_tips: function(e) {
    if (gd.preSale.return_type == 1) {
      util.showModal('提示', '注：若未在指定时间内支付尾款，则待活动结束后定金将退回至原支付账户。');
      return false;
    } else {
      util.showModal('提示', '注：若未在指定时间内支付尾款，定金不予退还！');
      return false;
    }
  },
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
  // 倒计时
  countdown: function(that) {
    that.setData({
      clock: util.dateformat(total_micro_second)
    });
    if (total_micro_second <= 0) {
      that.setData({
        clock: "已经截止"
      });
      return;
    }
    set_time_out = setTimeout(function() {
      // 放在最后--
      total_micro_second -= 1;
      that.countdown(that);
    }, 1000)
  },
  // 评论回复
  getReplyRect: function() {
    var that = this;
    wx.createSelectorQuery().select('.reply_text').boundingClientRect(function(rect) {
      if (rect != null) {
        var h = rect.height;
        if (h > 85) {
          that.setData({
            isFold: !that.data.isFold,
            hh: h
          })
        }
      } else {
        setTimeout(function() {
          wx.createSelectorQuery().select('reply_text').boundingClientRect(function(rect) {
            if (rect != null) {
              var h = rect.height;
              if (h > 85) {
                that.setData({
                  isFold: !that.data.isFold,
                  hh: h
                })
              }
            }
          }).exec();
        }, 200);
      }
    }).exec();
  },
  onPullDownRefresh: function() {
    wx.stopPullDownRefresh();
  },
  // 滑动一屏半时出现回到顶部按钮
  onPageScroll: function(e) {
    var that = this;
    if (e.scrollTop >= real_Height) {
      that.setData({
        top_display: 1
      })
    } else {
      that.setData({
        top_display: 0
      })
    }

  },
  goTop: function(e) { // 一键回到顶部
    if (wx.pageScrollTo) { //判断这个方法是否可用
      wx.pageScrollTo({
        scrollTop: 0
      })
    } else {
      wx.showModal({
        title: '提示',
        content: '当前微信版本过低，无法使用该功能，请升级到最新微信版本后重试。'
      })
    }
  },
  //评论显隐藏
  showReply: function(e) {
    var that = this;
    that.setData({
      isFold: !that.data.isFold,
    });
  },

  toast1Tap: function(e) {
    var col = this.data.collection;
    var that = this;
    if (col == '' || col == null) {
      util.api('/api/wxapp/collect/add', function(res) {
        that.setData({
          toast1Hidden: !that.data.toast1Hidden,
          collection: res
        })
      }, {
        goods_id: good_id,

      });

    } else {
      util.api('/api/wxapp/collect/del', function(res) {
        that.setData({
          toast2Hidden: !that.data.toast2Hidden,
          collection: ''
        })
      }, {
        goods_id: good_id,

      });

    }
  },
  toast1Change: function(e) {
    this.setData({
      toast1Hidden: true
    })
  },
  toast2Change: function(e) {
    this.setData({
      toast2Hidden: true
    })
  },
  loginClose: function() {
    this.setData({
      login_view: 0
    })
  },
  
  goAll: function(e) {
    var goods_id = e.currentTarget.dataset.id;
    util.navigateTo({
      url: '/pages/goodscomment/goodscomment?goods_id=' + goods_id
    })
  },
  goType(e) {
    var goods_id = e.currentTarget.dataset.id;
    var comment_type = e.currentTarget.dataset.type;
    util.jumpLink('/pages/goodscomment/goodscomment?goods_id=' + goods_id + '&comment_type=' + comment_type, 'navigateTo')
  },
 
  ViewCoupon: function() { //优惠券
    this.setData({
      actionSheetHidden: false
    })
  },
 
  cancel: function() {
    this.setData({
      actionSheetHidden: true
    })
  },
  proCancel: function() {
    this.setData({
      proMode: true
    })
  },
  actionSheetChange: function() {
    this.setData({
      actionSheetHidden: true
    })
  },
  proActionSheetChange: function() {
    this.setData({
      proMode: true
    })
  },
  preActionSheetChange: function() {
    this.setData({
      ruleMode: true
    })
  },
 
  bindBlur: function(e) {
    input_vali = e.detail.value;
  },
  getCouponNew: function(e) {
    var coupon_id = e.currentTarget.dataset.coupon;
    var code = e.currentTarget.dataset.code;
    var vali = e.currentTarget.dataset.vali;
    gd.recommend_coupons[coupon_id].disableds = true;
    var that = this;
    that.setData({
      coupon_list: gd.recommend_coupons,
    })
    if (vali != '' && vali != undefined) {
      util.navigateTo({
        url: '/pages/getcoupon/getcoupon?code=' + code + "&goods_id=" + goods_ids
      })
    } else {
      wx.showLoading({
        title: '领取中···',
      })
      form_id = e.detail.formId;
      open_id = util.getCache("openid");
      var that = this;
      if (gd.recommend_coupons[coupon_id].disableds == true) {
        util.api('/api/wxapp/coupon/get?code=' + code, function(res) {
          wx.hideLoading();
          if (res.error == 0) {
            gd.recommend_coupons[coupon_id].can_fetch = 0;
            that.setData({
              coupon_list: gd.recommend_coupons
            })
          } else {
            util.toast_fail(res.message.msg);
          }
        }, {

          form_id: form_id,
          open_id: open_id
        });
      }
    }
  },

  bindGetPhoneNumberOk(e) {
    mobile = e.detail.phoneNumber;
  },
  OneClickBuy: function(e) {
    var d = this.eventData(e);
    var gd = this._gd;

    if (!this.checkSelBuy(d.exchang == 1)) return;
    this.bindCloseSpec();

    if (gd.business_state != 1) {
      util.showModal('提示', '店铺未营业，无法购买');
      return false;
    }

    //判断是否要去绑定手机号
    var that = this;
    if (util.getCache('mobile') == '') {
      util.checkSession(function () {
        that.setData({
          is_block: is_block = 1
        })
      });
      return false;
    }

    util.api("/api/wxapp/common/saveformid", function (res) {

    }, {
        form_id: e.detail.formId,
        open_id: util.getCache("openid")
      });
    var choose_list = this.getChooseList();

    if (d.exchang == 1) {
      util.navigateTo({
        url: "/pages/goodsCheckout/goodsCheckout?order_type=one_click_buy&choose_list=" +
          JSON.stringify(choose_list) + '&query_param=' + JSON.stringify(this._options) +
          '&card_no=' + this._sel_buy.select_card.card_no + '&exchang_good=1',
      })
    } else {
      util.navigateTo({
        url: "/pages/goodsCheckout/goodsCheckout?order_type=pre_sale&choose_list=" +
          JSON.stringify(choose_list) + '&query_param=' + JSON.stringify(this._options),
      })
    }
  },
  to_index: function() {
    util.reLaunch({
      url: '/pages/index/index'
    })
  },
  to_cart: function() {
    util.navigateTo({
      url: '/pages/cart/cart'
    })
  },
  // 赠品
  to_giftgoods: function (e) {
    var ids = e.currentTarget.dataset.ids;
    util.navigateTo({
      url: '/pages/maingiftlist/maingiftlist?gift_id=' + ids,
    })
  },
  // 普通商品
  to_itemgoods: function (e) {
    var id = e.currentTarget.dataset.id;
    util.navigateTo({
      url: '/pages/item/item?goods_id=' + id,
    })
  },
  ViewGift: function (e) { //赠品
    let index = e.currentTarget.dataset.index;
    let giftInfo = this.data.gd.gift[index];
    console.log(giftInfo)
    this.setData({
      giftInfo: giftInfo,
      giftSheetHidden: false
    })
  },
  proGiftCancel: function () {
    this.setData({
      giftSheetHidden: true
    })
  },
  preview: function(e) {
    var nowImgUrl = e.target.dataset.src;
    var arr = [];
    for (var i in imgUrls) {
      arr.push(imgUrls[i]); //属性
    }
    wx.previewImage({
      current: nowImgUrl, // 当前显示图片的http链接
      urls: arr // 需要预览的图片http链接列表
    })
  },
  // 购物车
  gotoCart: function() {
    util.navigateTo({
      url: '/pages/cart/cart'
    })
  },
  clickComment: function(e) {
    var nowImgUrl = e.target.dataset.src;
    var arr = [];
    for (var i in commimgUrls) {
      arr.push(commimgUrls[i]); //属性
    }
    wx.previewImage({
      current: nowImgUrl, // 当前显示图片的http链接
      urls: arr // 需要预览的图片http链接列表
    })
  },
 
  onShareAppMessage: function(res) {
    var usernames = util.getCache('nickName');
    var goodnmame = gd.goods_name;
    if (goodnmame.length > 25) {
      goodnmame = goodnmame.substring(0, 24) + "...";
    }
    util.api("/api/wxapp/share/record", function(d) {

    }, {
      activity_id: good_id,
      activity_type: 10
    });
    return {
      path: '/pages/presaleitem/presaleitem?goods_id=' + good_id + '&invite_id=' + util.getCache('user_id') + "&presale_id=" + presale_id,
      title: gd.share_title,
      imageUrl: imageUrl + share_img,
    }
  },
  to_play: function() {
    var that = this;
    that.setData({
      is_play: 1
    })
  },
  not_play: function() {
    var that = this;
    that.setData({
      is_play: 0
    })
  },
  close_video: function() {
    var that = this;
    that.setData({
      is_play: 0
    })
  },
  getUserInfo: function(e) {
    var that = this;
    var canIUse = wx.canIUse('button.open-type.getUserInfo');
    if (e.detail.userInfo) {
      if (canIUse) {
        var user_avatar = e.detail.userInfo.avatarUrl;
        var user_name = e.detail.userInfo.nickName;
        util.setCache("nickName", user_name);
        util.setCache("avatarUrl", user_avatar);
        util.api('/api/wxapp/account/updateUser', function(res) {}, {
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
            util.api('/api/wxapp/account/updateUser', function(res) {}, {
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
    that.setData({
      click_num: true,
    })
  },
  go_share: function() {
    var that = this;
    wx.showLoading({
      title: '生成中',
    })
    util.api('/api/wxapp/pictorial', function(res) {
      if (res.error == 0) {
        pictorial = res.content.pictorial;
        if (pictorial) {
          util.api('/api/wxapp/upayyun/image', function (res) {
            if (res.error == 0) {
              pictorial = imageUrl + pictorial + "!big";
              posterBase64 = res.content;
              that.setData({
                pictorial: posterBase64,
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
    }, {
      action: 9,

      goods_id: goods_ids,
      presale_id: presale_id
    })

  },
  not_show_share: function() {
    var that = this;
    that.setData({
      is_share: 0
    })
  },
  saveImgToPhotosAlbumTap: function() {
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
  
  shareGoods: function () {
    this.setData({
      share_good: true
    })
  },

  checkSelBuy(use_exchange_card) {
    if (use_exchange_card && this._gd.exchang_card.length > 1 && !this.data.show_spec) {
      // 多个限次卡兑换，总是显示卡选择
      this.showSpec(use_exchange_card, true);
      return false;
    }
    if (this._sel_buy.has_spec && !this._sel_buy.select_prd) {
      if (this.data.show_spec) util.alert("请选择规格！")
      this.showSpec(use_exchange_card);
      return false;
    }

    if (!this._sel_buy.is_stock_enough) {
      util.alert("库存不足")
      this.showSpec(use_exchange_card);
      return false;
    }

    if (use_exchange_card && !this._sel_buy.select_card) {
      if (this.data.show_spec) util.alert("请选择限次卡")
      this.showSpec(use_exchange_card, true);
      return false;
    }

    if (use_exchange_card && !this._sel_buy.is_card_surplus_enough) {
      if (this.data.show_spec) util.alert("选择数量超出可兑换数量")
      this.showSpec(use_exchange_card, true);
      return false;
    }
    return true;
  },
  shareGoods: function () {
    this.setData({
      share_good: true
    })
  },
  // 自定义模板加载
  processWindowData: function (pageContent) {
    this.setData({
      pageContent: pageContent
    })
  },
  getChooseList() {
    var s = this._sel_buy;
    var gd = this._gd;
    return {
      goods_id: gd.goods_id,
      pin_group_id: presale_id,
      goods_price: s.has_spec ? s.goods_price : gd.shop_price,
      grade: gd.grade_price != -1 ? 1 : undefined,
      goods_number: s.goods_number,
      prd_sn: s.has_spec ? s.select_prd.prd_sn : gd.goods_sn,
      prd_id: s.has_spec ? s.select_prd.prd_id : gd.prd_id,
      product_id: s.has_spec ? s.select_prd.prd_id : gd.prd_id,
      user_id: util.getCache('user_id')
    };
  },
  bindContact(e) {
    if (e.detail.path) {
      var url = util.getPath(e.detail.path, e.detail.query);
      util.jumpLink(url, "redirectTo");
    }
  },
  gift_view_more() {
    this.setData({
      giftMore: !this.data.giftMore
    })
  },
  changeCartGoodsNumber() {
    var that = this;
    util.api("/api/wxapp/cart/goods/number", function (res) {
      if (res.error == 0) {
        that.setData({
          cartGoodsNumber: res.content.goods_number
        });
      }
    })
  },
})
