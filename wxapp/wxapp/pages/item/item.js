const util = require('../../utils/util.js')
var decorate = require("../common/decorate.js")
var spec_mixin = require("../goodscommon/spec.js")

var app = getApp();
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var good_id = '';
var mobile = util.getCache('mobile');
var is_bind_mobile;
var is_block = 0;
var dist_id = '';
var goods_main_img = '';
var deli;
var code;
var pro_con_first = [];
var login_view = 0;
var goods_ids;
var pictorial;
var imgUrls;
var commimgUrls;
var zhege;
var form_id;
var open_id;
var off_buss;
var is_play = 0;
var is_share = 0;
var os_type = '';
var disableds = false;
var gd;
var net_type = 0;
var is_second = 0;
var winWidth = 0; //当前屏幕的宽度
var winHeight = 0; //当前屏幕的高度
var real_Height;
var coupon_key = 0;
var is_bind = 0;
var hh = 0;
var show_sales_number = 0;
var share_img;
var set_time_outs = [];
var con_cfg;
var page_id;
var content;
var mar_bott;
var real_Width; //当前屏幕宽度
var color;
var idx;
var vali;
var input_vali;
var group_id;
var goods_group = []; //商品分组
var is_phone_block = 0;
var scr_height; //屏幕高度
var startAnimation = [];
var restartAnimation = [];
var re_cou = 1;
var promotion_language = '';
var posterBase64 = '';
var launch_user_id;
var share_id;
var shareTime;
var share_activity_id;
var if_click = false;
var is_preview = 0;
var count_share = 0;
var purchase;
var p_count = 0;
var p_total = 0;
var pl;
var purchase_name;
var purchase_img;


function contains(arr, obj) {
  var i = arr.length;
  while (i--) {
    if (arr[i] === obj) {
      return true;
    }
  }
  return false;
}

global.wxPage({
  mixins: [decorate, spec_mixin],
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
    show_recommend_coupons: false,
    giftSheetHidden: true,
    deliver_fee: 0,
    coupon_flag: 0,
    is_block: 0,
    proMode: true,
    toast1Hidden: true,
    toast2Hidden: true,
    toast3Hidden: true,
    is_play: 0,
    is_share: 0,
    disableds: false,
    net_type: 0,
    winHeight: 0,
    winWidth: 0,
    is_second: 0,
    isFold: false,
    hh: 0,
    is_bind: 0,
    blen: 0,
    nickName: util.getCache('nickName'),
    click_num: false,
    show_sales_number: 0,
    scr_height: 0,
    window_data: {},
    color: "#f5f5f5",
    page_id: 0,
    checked_card_name: '请选择',
    exchang_block: 0,
    pageData: {},
    re_cou: 1,
    cartGoodsNumber: 0,
    is_gift_goods: 0,
    giftMore: false,
    share_show: false,
    if_fullprice: 0,
    if_package: 0,
    if_purchase: 0,
    purchaseAnimation: {}
  },
  onLoad: function(option) {
    if (!util.check_setting(option)) return;
    launch_user_id = option.launch_user_id ? option.launch_user_id : 0;
    share_id = option.share_id ? option.share_id : 0;
    shareTime = option.shareTime ? option.shareTime : 0;
    is_preview = option.is_preview ? 1 : 0;
    mobile = util.getCache('mobile');
    this.data.query_param = option;
    goods_main_img = '';
    good_id = option.good_id || option.goods_id;
    show_sales_number = 0;
    var that = this;
    zhege = this;
    goods_group = [];
    p_count = 0;
    p_total = 0;
    var lat;
    var lng;
    var user_name = util.getCache('nickName');
    var user_avatar = util.getCache('avatarUrl');
    this.setData({
      from_count_card: option.from_count_card ? option.from_count_card : 0
    })
    if (!user_name || user_name == '用户' + parseInt(util.getCache('user_id') + 10000) ||
      user_name == util.getCache('openid') || !user_avatar ||
      user_avatar.indexOf('image/admin/head_icon.png') > -1) {
      that.setData({
        getsq: false,
      })
    } else {
      that.setData({
        getsq: true,
      })
    }
    util.getUserLocation(function(location) {
      util.api('/api/wxapp/goods', function(res) {
        if (res.error > 0) {
          util.showModal('提示', res.message, function() {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return;
        }
        if (res.content.goods_id) {
          var active_type = 0;
          if (res.content.goods_type == 6) {
            active_type = 9;
          }
          util.api('/api/wxapp/user_goods/record', function(res1) {

          }, {
            goods_id: res.content.goods_id,
            active_type: active_type,
            active_id: "",
            type: 1
          })
        }

        that._gd = gd = res.content;
        page_id = gd.goods_page_id;
        that.page_id = page_id;
        that.setData({
          page_id: page_id,
        })
        if (page_id > 0) {
          that.requestDecoratePageData(page_id, 0, that.processWindowData.bind(that));
        }
        if (gd.is_rebate_price == 1) {
          wx.hideShareMenu();
          that.setData({
            show_back: false
          })
        }
        if (gd.exchang_card != 'undefined' && Array.isArray(gd.exchang_card)) {
          gd.exchang_card.forEach(function(e, i) {
            e.is_checked = 0;
          })
        }
        is_block = 0
        is_bind_mobile = gd.is_bind_mobile; //是否要绑定手机号判断
        goods_ids = gd.goods_id;
        off_buss = gd.business_state;
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
            scr_height = res.windowHeight;
            that.setData({
              scr_height: scr_height,
            })
          },
        })
        imgUrls = gd.imgs,
          goods_main_img = gd.goods_img;
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
          if (gd.comment_info.comm_img) {
            gd.comment_info.comm_img = JSON.parse(gd.comment_info.comm_img);
            commimgUrls = gd.comment_info.comm_img;
          }
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

        if (gd.reduce_price != undefined) {
          that.setData({
            reduce_price: gd.reduce_price
          })
        }
        if (gd.buy_card_list != null && gd.buy_card_list.length > 0) {
          gd.buy_card_list.forEach((element, i) => {
            if (element.pay_type == 1) {
              element.pay_fee = element.pay_fee.substring(0, element.pay_fee.length - 3);
            }
          })
        }
        //商品信息
        var sale_display = gd.goods_sale_num;
        if (gd.base_sale_flag == 1) {
          sale_display += gd.base_sale;
        }
        that.setData({
          gd: gd,
          goods_name: gd.goods_name,
          goods_ad: gd.goods_ad,
          item_price: gd.shop_price,
          grade_price: gd.grade_price,
          goods_num: gd.goods_number,
          sale_num: sale_display,
          imgUrls: gd.imgs,
          goods_img: gd.goods_img,
          shop_info: gd.shop_info,
          collection: gd.collection,
          comment_info: gd.comment_info,
          is_block: is_block,
          show_sales_number: gd.show_sales_number,
          re_cou: re_cou,
          show_purchase: true
        })

        //分销推广语
        promotion_language = gd.promotion_language ? gd.promotion_language : '';

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

        // 促销活动
        if (gd.can_join_activity != "") {
          for (let i = 0; i < gd.can_join_activity.length; i++) {
            if (gd.can_join_activity[i].action == 2) {
              that.data.if_fullprice = 1;
            }
            if (gd.can_join_activity[i].action == 1) {
              that.data.if_purchase = 1;
            }
            if (gd.can_join_activity[i].action == 4) {
              that.data.if_package = 1;
            }
          }
          that.setData({
            if_fullprice: that.data.if_fullprice,
            if_purchase: that.data.if_purchase,
            if_package: that.data.if_package
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
        if (gd.goods_records != ''){
          var top_nav = 0
          if (typeof wx.getMenuButtonBoundingClientRect === 'function') {
           top_nav = wx.getMenuButtonBoundingClientRect().bottom
          } else {
            wx.getSystemInfo({
              success: (res) => {
               top_nav = res.statusBarHeight * 3
              }
            })
          }
          purchase = gd.goods_records;
          purchase_name = purchase[0].username;
          purchase_img = purchase[0].user_avatar
          that.setData({
            purchase_name: purchase_name,
            purchase_img: purchase_img,
            top_nav: top_nav
          })
          pl = purchase.length;
          if (pl > 0) {
            console.log('begin');
            that.slideupshow(that, 1, 0, 0)
          }
        }


      }, {
        goods_id: good_id,
        location: JSON.stringify(location),
        rebate_config: option.rebate_config,
        launch_user_id: launch_user_id,
        share_id: share_id,
        shareTime: shareTime,
        is_preview: is_preview
      }, '', true);
    })

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


    util.api('/api/wxapp/reduce/shareimg', function(response) {
      if (response.error == 0) {
        share_img = response.content;
      }

    }, {
      goods_id: good_id
    });



  },

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

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {
    var that = this;



  },
  // 满折满减
  to_full: function(e) {
    var ids = e.currentTarget.dataset.ids;
    util.navigateTo({
      url: '/pages/fullprice/fullprice?identity_id=' + ids,
    })
  },
  // 加价购
  to_purchase: function(e) {
    var ids = e.currentTarget.dataset.ids;
    util.navigateTo({
      url: '/pages/maingoodslist/maingoodslist?identity_id=' + ids,
    })
  },
  // 赠品
  to_giftgoods: function(e) {
    var ids = e.currentTarget.dataset.ids;
    util.navigateTo({
      url: '/pages/maingiftlist/maingiftlist?gift_id=' + ids,
    })
  },
  // 满包邮
  to_fullship: function(e) {
    var ids = e.currentTarget.dataset.ids;
    util.navigateTo({
      url: '/pages/fullship/fullship?identity_id=' + ids,
    })
  },
  // 普通商品
  to_itemgoods: function(e) {
    var id = e.currentTarget.dataset.id;
    util.navigateTo({
      url: '/pages/item/item?goods_id=' + id,
    })
  },
  // 一口价
  to_package: function(e) {
    var package_id = e.currentTarget.dataset.ids;
    util.navigateTo({
      url: '/pages/packagesalelist/packagesalelist?package_id=' + package_id,
    })
  },
  to_xiangqing: function() {
    if (gd.can_join_activity[0].action == 1) {
      util.navigateTo({
        url: '/pages/maingoodslist/maingoodslist?identity_id=' + gd.can_join_activity[0].identity_id,
      })
    } else if (gd.can_join_activity[0].action == 2) {
      util.navigateTo({
        url: '/pages/fullprice/fullprice?identity_id=' + gd.can_join_activity[0].identity_id,
      })
    } else if (gd.can_join_activity[0].action == 4) {
      util.navigateTo({
        url: '/pages/packagesalelist/packagesalelist?package_id=' + gd.can_join_activity[0].identity_id,
      })
    } else if (gd.can_join_activity[0].action == 5) {
      util.navigateTo({
        url: '/pages/fullship/fullship?identity_id=' + gd.can_join_activity[0].identity_id,
      })
    }
  },
  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {},
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    //购物车商品数
    this.changeCartGoodsNumber();

  },
  //  下拉加载
  onPullDownRefresh: function() {
    // 显示导航栏loading
    wx.showNavigationBarLoading();
    // 调用接口加载数据
    var that = this;
    if (page_id > 0) {
      this.requestDecoratePageData(page_id, 0, this.processWindowData.bind(this));
    }
    // 隐藏导航栏loading
    wx.hideNavigationBarLoading();
    // 当处理完数据刷新后，wx.stopPullDownRefresh可以停止当前页面的下拉刷新
    wx.stopPullDownRefresh();
  },


  //评论显隐藏
  showReply: function(e) {
    var that = this;
    that.setData({
      isFold: !that.data.isFold,
    });
  },
  // 收藏
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
        goods_id: good_id
      });

    } else {
      util.api('/api/wxapp/collect/del', function(res) {
        that.setData({
          toast2Hidden: !that.data.toast2Hidden,
          collection: ''
        })
      }, {
        goods_id: good_id
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
  // 购物车
  gotoCart: function() {
    util.redirectTo({
      url: '/pages/cart/cart'
    })
  },
  bindShowCouponPopup: function() { //优惠券
    this.setData({
      show_recommend_coupons: true
    })
  },
  ViewGift: function(e) { //赠品
    let index = e.currentTarget.dataset.index;
    let giftInfo = this.data.gd.gift[index];
    console.log(giftInfo)
    this.setData({
      giftInfo: giftInfo,
      giftSheetHidden: false
    })
  },
  proGiftCancel: function() {
    this.setData({
      giftSheetHidden: true
    })
  },
  proClcik: function() {
    this.setData({
      proMode: false
    })
  },

  proCancel: function() {
    this.setData({
      proMode: true
    })
  },
  proActionSheetChange: function() {
    this.setData({
      proMode: true
    })
  },


  powerDrawer: function(e) {
    var currentStatu = e.currentTarget.dataset.statu;
    this.utilSpec(currentStatu)
  },
  utilSpec: function(currentStatu) {
    /* 动画部分 */
    // 第1步：创建动画实例
    var animation = wx.createAnimation({
      duration: 200, //动画时长
      timingFunction: "linear", //线性
      delay: 0 //0则不延迟
    });
    // 第2步：这个动画实例赋给当前的动画实例
    this.animation = animation;

    // 第3步：执行第一组动画
    animation.opacity(0).rotateX(-100).step();

    // 第4步：导出动画对象赋给数据对象储存
    this.setData({
      animationData: animation.export()
    })
    // 第5步：设置定时器到指定时候后，执行第二组动画
    setTimeout(function() {
      // 执行第二组动画
      animation.opacity(1).rotateX(0).step();
      // 给数据对象储存的第一组动画，更替为执行完第二组动画的动画对象
      this.setData({
        animationData: animation
      })
      //关闭
      if (currentStatu == "close") {
        this.setData({
          showModalStatus: false
        });
      }
    }.bind(this), 200)
    // 显示
    if (currentStatu == "open") {
      this.setData({
        showModalStatus: true
      });
    }
  },

  to_index: function() {
    util.jumpLink('/pages/index/index', "reLaunch")
  },
  close_coupon: function() {
    re_cou = 0;
    this.data.re_cou = 0;
    this.setData({
      re_cou: re_cou
    })
  },
  to_cart: function() {
    util.navigateTo({
      url: '/pages/cart/cart'
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
  imageLoad: function(e) {
    var $width = e.detail.width, //获取图片真实宽度
      $height = e.detail.height,
      ratio = $width / $height; //图片的真实宽高比例
    var viewWidth = 718, //设置图片显示宽度，左右留有16rpx边距
      viewHeight = 718 / ratio; //计算的高度值
    var image = this.data.images;
    //将图片的datadata-index作为image对象的key,然后存储图片的宽高值
    image = {
      width: viewWidth,
      height: viewHeight
    }
    this.setData({
      images: image
    })
  },

  onShareAppMessage: function(res) {
    var usernames = util.getCache('nickName');
    var goodnmame = gd.goods_name;
    shareTime = util.formatTime(new Date());
    var value;
    if (goodnmame.length > 25) {
      goodnmame = goodnmame.substring(0, 24) + "...";
    }
    util.api("/api/wxapp/share/record", function (d) {

    }, {
        activity_id: good_id,
        activity_type: 0
      });
    if (gd.shareAward == 0) {
      return {
        path: '/pages/item/item?goods_id=' + good_id + '&invite_id=' + util.getCache('user_id'),
        title: gd.share_title ? gd.share_title : usernames + '为您独家推荐' + goodnmame,
        imageUrl: share_img,
      }
    } else {
      util.api("/api/wxapp/shareaward/share", function(res) {}, {
        user_id: util.getCache('user_id'),
        goods_id: gd.goods_id,
        share_id: gd.share_id,
        shareTime: shareTime
      });
      return {
        path: '/pages/item/item?shareAward&goods_id=' + good_id + '&share_id=' + gd.share_id + '&launch_user_id=' + util.getCache('user_id') + '&shareTime=' + shareTime + '&invite_id=' + util.getCache('user_id'),
        title: gd.share_title ? gd.share_title : usernames + '为您独家推荐' + goodnmame,
        imageUrl: share_img,
      }
    }
  },
  to_rebate: function() {
    if (gd.can_rebate == 1 || (gd.send_coupon == 1 && this.data.coupon_list.length > 0)) {
      util.jumpLink('/pages/sharerebate/sharerebate?goods_id=' + good_id + '&rebate_id=' + gd.rebate_id);
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
  // close_video:function(){
  //   var that = this;
  //   that.setData({
  //     is_play: 0
  //   })
  // },
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
  go_share: function(e) {
    var that = this;
    wx.showLoading({
      title: '生成中',
    })
    if (e.currentTarget.dataset.if_tor == 1){
      var if_tor = 1;
    }else{
      var if_tor = 0;
    }
    util.api('/api/wxapp/pictorial', function(res) {
      if (res.error == 0) {
        pictorial = res.content.pictorial;
        if (pictorial) {
          util.api('/api/wxapp/upayyun/image', function(res) {
            if (res.error == 0) {
              pictorial = imageUrl + pictorial + "!big";
              posterBase64 = res.content;
              that.setData({
                pictorial: posterBase64,
                if_tor: if_tor
              })
              wx.hideLoading();
              that.setData({
                is_share: 1,
                share_good: false,
                
              })
            }
          }, {
            image_path: pictorial
          });
        }
      } else {
        wx.hideLoading();
        util.toast_fail(res.message);
        return false;
      }
    }, {
      action: 1,
      goods_id: goods_ids
    })

  },
  not_show_share: function() {
    var that = this;
    that.setData({
      is_share: 0
    })
  },
  save_photo_tips: function (that){
    //复制
    if (promotion_language != '') {
      wx.setClipboardData({
        data: promotion_language,
        success: function (res) {
          wx.hideToast();
          that.setData({
            toastInfo: {
              icon: 'success',
              duration: 4000,
              title: '图片已保存到相册',
              content: promotion_language + '　以上推广语已复制'
            },
            copyComplete: true
          })
        }
      });
    } else {
      that.setData({
        toastInfo: {
          icon: 'success',
          duration: 2000,
          title: '图片已保存到相册'
        },
        copyComplete: true,
      })
    }
    that.setData({
      is_share: 0
    })
  },
  saveImgToPhotosAlbumTap: function(e) {
    var that = this;
    if (posterBase64) {
      if (e.currentTarget.dataset.iftor == 1){
        util.base64ImageHandle(posterBase64, function (res) { });
        wx.showLoading({
          title: '保存中',
        })
        util.api('/api/wxapp/upayyun/image', function (res) {
          console.log(res.content.length);
          if (res.error == 0) {
            let le = res.content.length;
            let if_ok = 0;
            for(let i in res.content){
              if_ok++;
              util.base64ImageHandle(res.content[i], function (res) {
                if (if_ok == le) {
                  wx.hideLoading();
                  that.save_photo_tips(that);
                }
              });
            }
          }
        }, { goods_id: gd.goods_id });
      }else{
        util.base64ImageHandle(posterBase64, function (res) { 
          that.save_photo_tips(that);
        });
      }
    } else {
      util.toast_fail('正在生成中...')
    }
  },
  // 自定义模板加载
  processWindowData: function(pageContent) {
    this.setData({
      pageContent: pageContent
    })
  },

  get_card: function(e) {
    var card_id = e.currentTarget.dataset.card_id
    var card_info;
    gd.buy_card_list.forEach(item => {
      if (item.id == card_id) {
        card_info = item;
        return;
      }
    })
    if (card_info.status == 0 && card_info.flag == 2) {
      util.showModal('提示', '该会员卡已停用');
      return false;
    }
    if (card_info.status == 0 && card_info.expire_type == 0) {
      var date = new Date(card_info.end_time);
      var now_time = new Date().getTime();
      var end_time = date.getTime();
      if (now_time >= end_time) {
        util.showModal('提示', '该会员卡已过期');
        return false;
      }
    }
    util.redirectTo({
      url: '/pages/usercardinfo/usercardinfo?card_list=1&card_id=' + card_id + '&goods_id=' + gd.goods_id,
    })
  },
  // 阻止优惠券滑动
  preventTouch: function() {},

  shareGoods: function(e) {
    var that = this;
    var gd = that._gd;
    var shareAward = e.currentTarget.dataset.shareaward;
    if (shareAward == 1) {
      util.api('/api/wxapp/shareaward/record', function(res) {
        if (res.error == 0) {
          console.log(res);
          var shareinfo = res.content.shareinfo;
          if (shareinfo.activity_id) share_activity_id = shareinfo.activity_id;
          that.setData({
            shareinfo: shareinfo,
            share_good: true
          })
        }
      }, {
        user_id: util.getCache('user_id'),
        goods_id: gd.goods_id,
        share_id: gd.share_id,
      });
    } else {
      that.setData({
        share_good: true
      })
    }
  },
  // 获取优惠
  getShare: function(e) {
    if (if_click == true) return;
    var that = this;
    if_click = true;
    var level = e.currentTarget.dataset.level;
    var share_type = e.currentTarget.dataset.share_type;
    var lottery = e.currentTarget.dataset.lottery;

    util.api('/api/wxapp/shareaward/takeAward', function(res) {
      if (res.error == 0) {
        console.log(res);
        var message = res.content.result.award_con;
        if (share_type == 3) {
          util.jumpLink('pages/lottery/lottery?lottery_id=' + lottery, 'navigateTo')
          that.setData({
            shareinfo: res.content.shareinfo,
          })
        } else {
          that.setData({
            message: message,
            share_show: true,
            shareinfo: res.content.shareinfo,
            share_type: share_type,
          })
        }
      } else if (res.error == 400003) {
        util.showModal('提示', '奖品已经领完');
        if_click = false;
        return false;
      }
      if_click = false;
    }, {
      goods_id: gd.goods_id,
      share_id: gd.share_id,
      user_id: util.getCache('user_id'),
      level: level,
      share_activity_id: share_activity_id
    });
  },
  to_page(e) {
    var page_type = e.currentTarget.dataset.page_type;
    var lottery = e.currentTarget.dataset.lottery;
    if (page_type == 1) {
      util.jumpLink('pages1/integral/integral?num=0', 'reLaunch');
    } else if (page_type == 2) {
      util.jumpLink('/pages/couponlist/couponlist', 'reLaunch');
    }
  },
  guandiao1(e) {
    this.setData({
      share_show: false
    })
  },
  bindClose(e) {
    this.setData({
      share_good: false
    });
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
  bindAddCart(e) {
    if (e.currentTarget.dataset.showSpec == 1) {
      this.showSpec(0, true);
      return false;
    }
    if (!this.checkSelBuy()) return false;
    // 如果会员卡可购买，且会员卡未激活，先激活后才可购买
    if (gd.card_can == true && gd.is_card_exclusive == 1) {
      let count = 0;
      if (gd.buy_card_list.length != 0) {
        for (var i = 0, buy_card_list = gd.buy_card_list; i < buy_card_list.length; i++) {
          if (buy_card_list[i].status == 1) {
            count++;
            break;
          }
        }
      }
      if ((gd.user_grade_card != null && gd.user_grade_card.activation == 1 && gd.user_grade_card.activation_time != null) || (gd.user_grade_card && gd.user_grade_card.activation == 0)) {
        count++;
      }
      if (count == 0) {
        util.showModal('提示', '请激活会员卡', function() {
          util.navigateTo({
            url: '/pages/usercardlist/usercardlist',
          })
        });
        return false;
      }
    }
    this.addCart(this.getChooseList(), e.detail.form_id, this._options);
    this.bindCloseSpec();
  },
  getChooseList() {
    var s = this._sel_buy;
    var gd = this._gd;
    return {
      goods_id: gd.goods_id,
      goods_price: s.has_spec ? s.goods_price : gd.shop_price,
      grade: gd.grade_price != -1 ? 1 : undefined,
      goods_number: s.goods_number,
      prd_sn: s.has_spec ? s.select_prd.prd_sn : gd.goods_sn,
      prd_id: s.has_spec ? s.select_prd.prd_id : gd.prd_id,
      product_id: s.has_spec ? s.select_prd.prd_id : gd.prd_id,
      user_id: util.getCache('user_id')
    };
  },
  addCart(choose_list, form_id, options) {
    var that = this;
    util.api('/api/wxapp/cart/add', function(res) {
      if (res.error == 0) {
        util.toast_success('已加入购物车')
        that.changeCartGoodsNumber();
      } else {
        util.toast_fail(res.message);
      }
    }, {
      choose_list: JSON.stringify(choose_list),
      form_id: form_id,
      open_id: util.getCache("open_id"),
      query_param: JSON.stringify(options)
    });
  },
  bindGetPhoneNumberOk(e) {},
  bindOneClickBuy: function(e) {
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
    if (gd.is_bind_mobile == 1 && util.getCache('mobile') == '') {
      util.checkSession(function() {
        that.setData({
          is_block: is_block = 1
        })
      });
      return false;
    }


    // 如果会员卡可购买，且会员卡未激活，先激活后才可购买
    if (gd.card_can == true && d.exchang != 1 && gd.is_card_exclusive == 1) {
      let count = 0;
      if (gd.buy_card_list.length != 0) {
        for (var i = 0, buy_card_list = gd.buy_card_list; i < buy_card_list.length; i++) {
          if (buy_card_list[i].status == 1) {
            count++;
            break;
          }
        }
      }
      if ((gd.user_grade_card != null && gd.user_grade_card.activation == 1 && gd.user_grade_card.activation_time != null) || (gd.user_grade_card && gd.user_grade_card.activation == 0)) {
        count++
      }

      if (count == 0) {
        util.showModal('提示', '请激活会员卡', function() {
          util.navigateTo({
            url: '/pages/usercardlist/usercardlist',
          })
        });
        return false;
      }
    }


    var choose_list = this.getChooseList();

    if (d.exchang == 1) {
      util.navigateTo({
        url: "/pages/goodsCheckout/goodsCheckout?order_type=one_click_buy&choose_list=" +
          JSON.stringify(choose_list) + '&query_param=' + JSON.stringify(this._options) +
          '&card_no=' + this._sel_buy.select_card.card_no + '&exchang_good=1',
      })
    } else {
      util.navigateTo({
        url: "/pages/goodsCheckout/goodsCheckout?order_type=one_click_buy&choose_list=" +
          JSON.stringify(choose_list) + '&query_param=' + JSON.stringify(this._options),
      })
    }

  },
  bindTopMore(e) {
    var gd = this._gd;
    if (gd.is_card_exclusive == 1 && gd.buy_card_list.length <= 0 && gd.exclusive_grade_card != null) {
      util.showModal('提示', '您当前的会员等级不满足，仅拥有“' + gd.exclusive_grade_card.card_name + '”等级卡用户可购买此商品。可在“个人中心”查看会员卡权益');
      return false;
    }

    var goods_id = gd.goods_id;
    if (gd.buy_card_list != 'undefined' && gd.buy_card_list.length == 1) {
      util.navigateTo({
        url: '/pages/usercardinfo/usercardinfo?card_list=1&card_id=' + gd.buy_card_list[0].id + '&goods_id=' + goods_id,
      })
    } else {
      util.navigateTo({
        url: '/pages/buycardlist/buycardlist?goods_id=' + goods_id,
      })
    }
  },
  brand_search(e) {
    let brand_id = e.currentTarget.dataset.brand_id;
    util.jumpLink('/pages/searchs/search?brand_id=' + brand_id, 'navigateTo')
  },
  bindContact(e) {
    if (e.detail.path) {
      var url = util.getPath(e.detail.path, e.detail.query);
      util.jumpLink(url, "redirectTo");
    }
  },
  wxshopping_recommend(e) {
    var goods_id = e.currentTarget.dataset.goods_id;
    if (goods_id) {
      util.api('/api/wxapp/goods/wxshopping/recommend', function() {

      }, {
        goods_id: goods_id
      })
    }
  },
  changeCartGoodsNumber() {
    var that = this;
    util.api("/api/wxapp/cart/goods/number", function(res) {
      if (res.error == 0) {
        that.setData({
          cartGoodsNumber: res.content.goods_number
        });
      }
    })
  },
 
 
  to_send_gift: function(e) {
    var d = this.eventData(e);
    var gd = this._gd;
    if (gd.card_can == false && gd.is_card_exclusive == 1) {
      if (gd.is_card_exclusive == 1 && gd.buy_card_list.length <= 0 && gd.exclusive_grade_card != null) {
        util.showModal('提示', '您当前的会员等级不满足，仅拥有“' + gd.exclusive_grade_card.card_name + '”等级卡用户可购买此商品。可在“个人中心”查看会员卡权益');
        return false;
      }
      util.showModal('提示', '本商品为会员专享商品，开通会员即可购买', function() {
        var goods_id = gd.goods_id;
        if (gd.buy_card_list != 'undefined' && gd.buy_card_list.length == 1) {
          util.navigateTo({
            url: '/pages/usercardinfo/usercardinfo?card_list=1&card_id=' + gd.buy_card_list[0].id + '&goods_id=' + goods_id,
          })
        } else {
          util.navigateTo({
            url: '/pages/buycardlist/buycardlist?goods_id=' + goods_id,
          })
        }
      }, true, '取消', '开通会员')
      return false;
    }
    if (gd.card_can == true && gd.is_card_exclusive == 1) {
      let count = 0;
      if (gd.buy_card_list.length != 0) {
        for (var i = 0, buy_card_list = gd.buy_card_list; i < buy_card_list.length; i++) {
          if (buy_card_list[i].status == 1) {
            count++;
            break;
          }
        }
      }
      if ((gd.user_grade_card != null && gd.user_grade_card.activation == 1 && gd.user_grade_card.activation_time != null) || (gd.user_grade_card && gd.user_grade_card.activation == 0)) {
        count++;
      }
      if (count == 0) {
        util.showModal('提示', '请激活会员卡', function() {
          util.navigateTo({
            url: '/pages/usercardlist/usercardlist',
          })
        });
        return false;
      }
    }
    this.setData({
      is_gift_goods: 1
    })
    if (!this.checkSelBuy(d.exchang == 1)) return;
    this.bindCloseSpec();

    var all_info = this.getChooseList();
    util.jumpLink("/pages1/presentinfo/presentinfo?gift_id=" + gd.give_gift.id + "&product_id=" + all_info.product_id + "&product_num=" + all_info.goods_number);
  },
  gift_view_more() {
    this.setData({
      giftMore: !this.data.giftMore
    })
  },
  slideupshow: function(that, opacity, delay, p_total) {
    console.log('kaishi');
    let parms = '';
    let animation = wx.createAnimation({
      duration: 2000,
      timingFunction: 'ease',
      delay: delay
    });
    animation.translateY(0).step();
    animation.translateY(-120).step();
    parms = animation.export();
    that.setData({
      animation: parms,

    })
  },
  backStart: function() {
    p_count++;
    if (p_count < 2) return false;
    p_total++;
    console.log('第一次动画结束！');
    p_count = 0;
    if (p_total >= pl){
      this.setData({
        show_purchase: false,
      })
    } else{
      let parms1 = '';
      let animation1 = wx.createAnimation({
        duration: 0,
      });
      animation1.translateY(30).step();
      parms1 = animation1.export();
      purchase_name = purchase[p_total].username;
      purchase_img = purchase[p_total].user_avatar
      this.setData({
        animation: parms1,
        purchase_name: purchase_name,
        purchase_img: purchase_img
      })
      setTimeout(function() {
        this.slideupshow(this, 1, 0, p_total)
      }.bind(this), 100)
    } 
  },

})