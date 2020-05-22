const util = require('../../utils/util.js')
var spec_mixin = require("../../pages/goodscommon/spec.js")
var decorate = require("../../pages/common/decorate.js")

var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var good_id = '';
var mobile = util.getCache('mobile');
var time_arr = [];
var total_micro_second = 0;
var set_time_out;
var is_bind_mobile;
var is_block = 0;
var dist_id = '';
var goods_main_img = '';
// var choose_list = {};
var spec_list = {};
var prd_list = {};
var deli;
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
var group_draw_id;
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
var net_type = 0;
var is_second = 0;
var winWidth = 0;//当前屏幕的宽度
var winHeight = 0;//当前屏幕的高度
var top_display;//判断回到顶部按钮是否显示
var real_Height;
var is_max = 0;
var is_min = 0;
var hh = 0;
var share_img;
var show_sales_number = 0;
var share_img;
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
  util.api('?c=app_cart&m=get_goods_deliver_fee', function (res) {
    deli = res.content;
    if (typeof (deli.code) == 'undefined') {
      deli.code = 0;
    }
    that.setData({
      can_deli: deli.code,
      deliver_fee: deli.fee
    });
  }, { goods_id: good_id, dist_id: dist_id, buy_num: buy_number });

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
    total_micro_second: 0,
    showModalStatus: false,
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
    // choose_list: {},
    speclist: [],
    buy_number: '1',
    proMode: true,
    specMove: true,
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
    blen: 0,
    is_max: 0,
    is_min: 0,
    nickName: util.getCache('nickName'),
    click_num:false,
    show_sales_number: 0,
    buyType:'pinlottery',
    giftMore: false
  },
  onLoad: function (option) {
    if (!util.check_setting(option)) return;
    mobile = util.getCache('mobile');
    // choose_list = {};
    buy_number = 1;
    clearTimeout(set_time_out);
    spec_list = {};
    prd_list = {};
    goods_main_img = '';
    good_nums = 1;
    limit_num = 1;
    is_prd_good = 0;
    cat_list = [];
    prd_str = [];
    top_display: 0;
    good_id = option.good_id || option.goods_id;
    group_draw_id = option.group_draw_id;
    show_sales_number = 0;
    var that = this;
    zhege = this;
    var lat;
    var lng;
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
    util.getUserLocation(function (location) {
      util.api('/api/wxapp/groupdraw/goods', function (res) {
        if(res.error != 0){
          util.showModal('提示', res.message, function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return false;
        }
        if (res.content.is_delete == 1) {
          util.showModal('提示', '该商品已删除', function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return;
        }
        if (res.content.goods_id) {
          util.api('/api/wxapp/user_goods/record', function (res1) {

          }, { goods_id: res.content.goods_id, active_type: 8, active_id: res.content.group_draw.id, type: 1 })
            }
        cat_list = [];
        prd_str = [];
        that._gd =gd = res.content;
        page_id = gd.goods_page_id;
        that.page_id = page_id;
        that.setData({
          page_id: page_id,
        })
        if (page_id > 0) {
          that.requestDecoratePageData(page_id, 0, that.processWindowData.bind(that));
        }
        is_block = 0
        is_bind_mobile = gd.is_bind_mobile;//是否要绑定手机号判断
        goods_ids = gd.goods_id;
        off_buss = gd.business_state;
        if(gd.shop_price.indexOf("~")>-1){
          gd.shop_price = gd.shop_price.split('~')[0];
          gd.shop_price = parseFloat(gd.shop_price ).toFixed(2);
        }
        gd.group_draw.start_time = gd.group_draw.start_time.substring(0, gd.group_draw.start_time.length-3);
        wx.getNetworkType({
          success: function (res) {
            if (res.networkType != 'wifi') {
              that.setData({
                net_type: 1
              })
            }
          },
        })
        wx.getSystemInfo({
          success: function (res) {
            real_Height = res.windowHeight * 1.5;
          },
        })
        total_micro_second = gd.surplus_second;
        if (total_micro_second > 0) {
          that.countdown(that);
        }
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

        //商品信息
        that.setData({
          gd: gd,
          goods_name: gd.goods_name,
          goods_ad: gd.goods_ad,
          item_price: gd.shop_price,
          goods_num: gd.goods_number,
          sale_num: gd.goods_sale_num,
          imgUrls: gd.imgs,
          goods_img: gd.goods_img,
          spec_name: gd.specs.spec_name,
          shop_info: gd.shop_info,
          collection: gd.collection,
          comment_info: gd.comment_info,
          is_block: is_block,
          good_nums: good_nums,
          is_min: is_min,
          show_sales_number: gd.show_sales_number
        })
        that.getReplyRect();

      }, { goods_id: good_id, group_draw_id: group_draw_id, location: JSON.stringify(location) },'',true);
    })
    //获取cache
    // choose_list['user_id'] = util.getCache('user_id');
    //登录
    that.setData({
      login_view: login_view
    })
    // 分享图
    util.api('/api/wxapp/groupdraw/shareimg', function (res) {
      share_img = res.content;
    }, { goods_id: good_id, group_draw_id: group_draw_id, group_id: 0  });

    //邀请用户
    if (option.invite_id > 0) {
      this.setData({
        invite_id: option.invite_id
      })
    }
  },
  // 倒计时
  countdown: function (that) {
    that.setData({
      clock: util.dateformat(total_micro_second)
    });
    if (total_micro_second <= 0) {
      that.setData({
        clock: "已经截止"
      });
      // timeout则跳出递归
      return;
    }
    set_time_out = setTimeout(function () {
      // 放在最后--
      total_micro_second -= 1;
      that.countdown(that);
    }
      , 1000)
  },
  getReplyRect: function () {
    var that = this;
    wx.createSelectorQuery().select('.reply_text').boundingClientRect(function (rect) {
      if (rect != null) {
        var h = rect.height;
        if (h > 85) {
          that.setData({
            isFold: !that.data.isFold,
            hh: h
          })
        }
      } else {
        setTimeout(function () {
          wx.createSelectorQuery().select('reply_text').boundingClientRect(function (rect) {
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
  onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
  },
  // 滑动一屏半时出现回到顶部按钮
  onPageScroll: function (e) {
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
  goTop: function (e) {  // 一键回到顶部
    if (wx.pageScrollTo) {//判断这个方法是否可用
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
  showReply: function (e) {
    var that = this;
    that.setData({
      isFold: !that.data.isFold,
    });
  },
  loginClose: function () {
    this.setData({
      login_view: 0
    })
  },

  goAll: function (e) {
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
  
  bindGetPhoneNumberOk(e) {
    mobile = e.detail.phoneNumber;
  },

  OneClickBuy: function (e) {

    if (!this.checkSelBuy()) return;
    this.bindCloseSpec();

    //判断是否要去绑定手机号
    var that = this;
    if (is_bind_mobile == 1 && mobile == '') {
      util.checkSession(function () {
        that.setData({
          is_block: is_block = 1
        })
      })
      return false;
    }
    if (off_buss != 1) {
      util.showModal('提示', '店铺未营业，无法购买', function () {
      }, false);
      return false;
    }

    var choose_list = this.getChooseList();
    util.navigateTo({
      url: "/pages/goodsCheckout/goodsCheckout?order_type=group_draw&choose_list=" + JSON.stringify(choose_list),
    })
    
  },
  to_index: function () {
    util.navigateTo({
      url: '/pages/index/index'
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

  preview: function (e) {
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

  clickComment: function (e) {
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

  onShareAppMessage: function (res) {
    util.api("/api/wxapp/share/record", function (d) {

    }, { activity_id: group_draw_id, activity_type: 8 });
    return {
      path: '/pages/pinlotteryitem/pinlotteryitem?good_id=' + good_id + "&group_draw_id=" + group_draw_id + '&invite_id=' + util.getCache('user_id'),
      title: "快来参与" + gd.group_draw.pay_money + "元拼团大抽奖吧",
      imageUrl: imageUrl + share_img,
    }
  },
  to_play: function () {
    var that = this;
    that.setData({
      is_play: 1
    })
  },
  not_play: function () {
    var that = this;
    that.setData({
      is_play: 0
    })
  },
  close_video: function () {
    var that = this;
    that.setData({
      is_play: 0
    })
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
        nickName:user_name,
      })
    }
    if(e.currentTarget.dataset.pt == 1){
      that.OneClickBuy(e);
    }
    that.setData({
      click_num: true,
    })
  },
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
    }, { action: 8,   goods_id: good_id, group_draw_id: group_draw_id })

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


  // 原价购买
  to_item:function(){
    util.navigateTo({
      url: '/pages/item/item?goods_id=' + good_id,
    })
  },
  to_rule:function(){
    util.jumpToWeb('/wxapp/pinlottery/help');
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
  checkSelBuy() {
    if (this._sel_buy.has_spec && !this._sel_buy.select_prd) {
      if (this.data.show_spec) util.alert("请选择规格！")
      this.showSpec();
      return false;
    }
    if (!this._sel_buy.is_stock_enough) {
      util.alert("库存不足")
      this.showSpec();
      return false;
    }

    return true;
  },
  bindAddCart(e) {
    if (!this.checkSelBuy()) return;
    this.addCart(this.getChooseList(), e.detail.form_id, this._options);
    this.bindCloseSpec();
  },
  getChooseList() {
    var s = this._sel_buy;
    var gd = this._gd;
    return {
      goods_id: gd.goods_id,
      group_draw_id: group_draw_id,
      goods_price: s.has_spec ? s.goods_price : gd.shop_price,
      grade: gd.grade_price != -1 ? 1 : undefined,
      goods_number: 1,
      prd_sn: s.has_spec ? s.select_prd.prd_sn : gd.goods_sn,
      prd_id: s.has_spec ? s.select_prd.prd_id : gd.prd_id,
      product_id: s.has_spec ? s.select_prd.prd_id : gd.prd_id,
      user_id: util.getCache('user_id')
    };
  },
  gift_view_more(){
    this.setData({
      giftMore: !this.data.giftMore
    })
  }
})
