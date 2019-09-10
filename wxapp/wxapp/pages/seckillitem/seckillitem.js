const util = require('../../utils/util.js')
var decorate = require("../common/decorate.js")
var spec_mixin = require("../goodscommon/spec.js")

var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var good_id = '';
var sk_id;
var mobile = util.getCache('mobile');
var is_block = 0;
var dist_id = '';
var goods_main_img = '';
var spec_list = {};
var prd_list = {};
var deli;
var pro_con_first = [];
var is_prd_good = 0;
var spec_view = 0;
var login_view = 0;
var spec_check = 0;
var buy_number = 1;
var limit_num = 1;
var spec_name;
var pictorial;
var goods_ids;
var imgUrls;
var commimgUrls;
var zhege;
var form_id;
var open_id;
var off_buss;
var good_nums = 1;
var total_micro_second = 0;
var set_time_out;
var is_share = 0;
var gd;
var os_type = '';
var is_play = 0;
var net_type = 0;
var is_second = 0;
var winWidth = 0; //当前屏幕的宽度
var winHeight = 0; //当前屏幕的高度
var top_display; //判断回到顶部按钮是否显示
var real_Height;
var sk_price;
var sk_shop_price;
var act_status;
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
global.wxPage({
  mixins: [decorate, spec_mixin],
  data: {
    images: {},
    total_micro_second: 0,
    act_open: 0,
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
    actionSheetHidden: true,
    giftSheetHidden: true,
    kucun: true,
    deliver_fee: 0,
    is_second: 0,
    spec_view: 0,
    coupon_flag: 0,
    limit_buy_num: 0,
    limit_max_num: 0,
    stock: 0,
    is_block: 0,
    spec_array: [],
    spec_id_list: {},
    buy_number: '1',
    proMode: true,
    specMove: true,
    specMoves: true,
    toast1Hidden: true,
    toast2Hidden: true,
    toast3Hidden: true,
    good_nums: 1,
    is_share: 0,
    is_play: 0,
    isFold: false,
    net_type: 0,
    winHeight: 0,
    winWidth: 0,
    top_display: 0,
    sk_price: 0,
    sk_shop_price: 0,
    act_status: 0,
    nickName: util.getCache('nickName'),
    click_num:false,
    checked_card_name: '请选择',
    show_exchang: 0,
    exchang_block: 0,
    exchang_no:'',
    exchang_good:0,
    exchang_surplus:0,
    page_id:0,
    giftMore: false
  },
  onLoad: function(option) {
    if (!util.check_setting(option)) return;
    mobile = util.getCache('mobile');
    clearTimeout(set_time_out);
    top_display: 0;
    buy_number = 1;
    spec_list = {};
    prd_list = {};
    goods_main_img = '';
    good_nums = 1;
    limit_num = 1;
    is_prd_good = 0;
    sk_id = option.sk_id;
    // seckill_id = 43;
    var that = this;
    zhege = this;

    //邀请用户
    if (option.invite_id > 0) {
      this.setData({
        invite_id: option.invite_id
      })
    }

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
      util.api('/api/wxapp/seckill/goods', function(res) {
        if (res.content.is_delete == 1) {
          util.showModal('提示', '该商品已删除', function() {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return;
        }
          if (res.content.goods_id) {
                  util.api('/api/wxapp/user_goods/record', function (res1) { }, {
                      goods_id: res.content.goods_id, active_type: 5, active_id: res.content.sk_id, type: 1
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
        good_id = gd.goods_id;
        goods_ids = gd.goods_id;
        act_status = gd.can_seckill.status;
        is_block = 0
        util.api('/api/wxapp/sec/shareimg', function (res) {
          share_img = res.content.sec_share_img;
        }, { sk_id: sk_id });
        if (gd.exchang_card != 'undefined' && Array.isArray(gd.exchang_card)) {
          gd.exchang_card.forEach(function (e, i) {
            e.is_checked = 0;
          })
          console.log(gd.exchang_card)
        }
        //倒计时
        if (gd.can_seckill.remaining_time) {
          total_micro_second = gd.can_seckill.remaining_time;
        }
        if (total_micro_second > 0) {
          that.countdown(that);
          that.setData({
            act_open: 1
          });
        }
        off_buss = gd.business_state;
        //最低购买限制
        if (gd.limit_buy_num > 1) {
          buy_number = gd.limit_buy_num;
          good_nums = gd.limit_buy_num;
          that.setData({
            buy_number: gd.limit_buy_num,
            good_nums: gd.limit_buy_num
          })
        }
        that.data.limit_max_num = parseInt(gd.limit_max_num);
        that.data.stock = parseInt(gd.stock);

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
        var sttr = gd.seckill_price.split('~');
        var sk_shop = gd.shop_price.split('~');
        sk_price = sttr[0];
        if(sk_shop.length > 1){
          sk_shop_price = sk_shop[1];
        }else{
          sk_shop_price = sk_shop[0];
        }

        //商品信息
        that.setData({
          gd: gd,
          goods_name: gd.goods_name,
          goods_ad: gd.goods_ad,
          item_price: gd.shop_price,
          sec_price:gd.seckill_price,
          goods_num: gd.goods_number,
          goods_old_number: gd.goods_old_number,
          sale_num: gd.goods_sale_num,
          imgUrls: gd.imgs,
          goods_img: gd.goods_img,
          spec_name: gd.specs.spec_name,
          limit_buy_num: gd.limit_buy_num,
          shop_info: gd.shop_info,
          collection: gd.collection,
          comment_info: gd.comment_info,
          is_block: is_block,
          good_nums: good_nums,
          sk_price:sk_price,
          sk_shop_price:sk_shop_price,
          sk_id: sk_id
        })

        that.getReplyRect(0);

      }, { seckill_id: sk_id, location: JSON.stringify(location) },'',true);
    })

    //登录
    that.setData({
      login_view: login_view
    })
    //购物车商品数
    that.changeCartGoodsNumber();
  },

  getReplyRect: function (count) {
    var that = this;
    if (count > 1) return;
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
          that.getReplyRect(count + 1);
        }, 100);
        return;
              }
          }).exec();
  },
  //评论显隐藏
  showReply: function (e) {
    var that = this;
    that.setData({
      isFold: !that.data.isFold,
    });
  },

  onPullDownRefresh: function () {
    var that = this;
    if (page_id > 0) {
      this.requestDecoratePageData(page_id, 0, this.processWindowData.bind(this));
    }
    wx.stopPullDownRefresh();
  },
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

  toast1Tap: function (e) {
    var col = this.data.collection;
    var that = this;
    if (col == '' || col == null) {
      util.api('/api/wxapp/collect/add', function (res) {
        that.setData({
          toast1Hidden: !that.data.toast1Hidden,
          collection: res
        })
      }, { goods_id: good_id  });

    }
    else {
      util.api('/api/wxapp/collect/del', function (res) {
        that.setData({
          toast2Hidden: !that.data.toast2Hidden,
          collection: ''
        })
      }, { goods_id: good_id  });

    }
  },
  toast1Change: function (e) {
    this.setData({
      toast1Hidden: true
    })
  },
  toast2Change: function (e) {
    this.setData({
      toast2Hidden: true
    })
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
  gotoCart: function () {
    util.navigateTo({
      url: '/pages/cart/cart'
    })
  },

  countdown: function (that) {
    that.setData({
      clock: util.dateformat(total_micro_second)
    });
    if (total_micro_second <= 0) {
      util.navigateTo({
        url: '/pages/seckillitem/seckillitem?sk_id=' + sk_id,
      })
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

  onShow: function () {
    if (this.data.page_id > 0) {
      this.requestDecoratePageData(this.data.page_id, 0, this.processWindowData.bind(this));
    }
    //购物车商品数
    this.changeCartGoodsNumber();
  },
  /**
 * 用户点击右上角分享
 */
  onShareAppMessage: function (res) {
    var usernames = util.getCache('nickName');
    return {
      title: gd.share_title,
      imageUrl: imageUrl + share_img,
      path: '/pages/seckillitem/seckillitem?sk_id=' + sk_id + '&invite_id=' + util.getCache('user_id'),
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
        nickName:user_name,
      })
    }
    that.setData({
      click_num: true,
    })
  },
  go_share: function () {
    var that = this;
    util.api('/api/wxapp/seckill/goods', function(res) {
      if (res.content.is_delete == 1) {
        util.showModal('提示', '该商品已删除', function() {
          util.reLaunch({
            url: '/pages/index/index'
          })
        });
        return;
      }
      if(res.content.can_seckill.status == 1 || res.content.can_seckill.status == 2 || res.content.can_seckill.status == 4 || res.content.can_seckill.status == 6){
        util.showModal('暂不支持分享', res.content.can_seckill.msg, function () {
          util.navigateTo({
            url: "/pages/item/item?goods_id=" + goods_ids
          })
        }, false, '取消', '原价购买')
        return false;
      } else {
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
                    is_share: 1
                  })
                  wx.hideLoading();
                }
              }, { image_path: pictorial })
            }
          } else {
            wx.hideLoading();
            util.toast_fail(res.message);
            return false;
          }
        }, { action: 4, goods_id: goods_ids, identity_id: sk_id })
      }
    },{ seckill_id: sk_id  })
  },
  not_show_share: function () {
    var that = this;
    that.setData({
      is_share: 0
    })
  },
  bindGetPhoneNumberOk(e) {
    mobile = e.detail.phoneNumber;
  },

  to_index: function () {
    util.jumpLink('/pages/index/index',"reLaunch")
  },
  to_cart: function () {
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

  to_goods:function(e){
    var goods_id = e.currentTarget.dataset.goods;
    util.navigateTo({
      url: "/pages/item/item?goods_id=" + goods_id
    })
  },

  get_card: function (e) {
    var card_id = e.currentTarget.dataset.card_id
    var card_info;
    gd.exclusive_card_list.forEach(item => {
      if (item.id == card_id) {
        card_info = item;
        return;
      }
    })
    util.redirectTo({
      url: '/pages/usercardinfo/usercardinfo?card_list=1&card_id=' + card_id,
    })
  },
  
  // 自定义模板加载
  processWindowData: function (pageContent) {
    this.setData({
      pageContent: pageContent
    })
  },
  shareGoods: function () {
    this.setData({
      share_good: true
    })
  },

  getChooseList() {
    var s = this._sel_buy;
    var gd = this._gd;
    return {
      sk_id: sk_id,
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
      this.showSpec(use_exchange_card);
      return false;
    }

    if (use_exchange_card && !this._sel_buy.is_card_surplus_enough) {
      util.alert("选择数量超出可兑换数量")
      this.showSpec(use_exchange_card);
      return false;
    }
    return true;
  },
  bindOneClickBuy: function (e) {
    var d = this.eventData(e);
    console.log(d)
    var gd = this._gd;

    if (!this.checkSelBuy(d.exchang == 1)) return;
    this.bindCloseSpec();

    if (!this.checkSubmit(d.exchang == 1)) return;

    var choose_list = this.getChooseList();

    util.navigateTo({
      url: "/pages/item/item?goods_id=" + goods_id + "&speclist=" + JSON.stringify(choose_list)
    })
  },
  bindSecKillBuy: function (e) {
      var d = this.eventData(e);
      var gd = this._gd;
      var that = this;
      util.api('/api/wxapp/sec/check/stock', function (res) {
          if (res.content.can_buy == 0) {
              util.showModal('提示', '该商品秒杀数量已用尽', function () {
                  util.navigateTo({
                      url: "/pages/item/item?goods_id=" + good_id
                  })
              }, true, '取消', '原价购买')
          }else{
              console.log(d)
              if (!that.checkSelBuy(d.exchang == 1)) return;
              that.bindCloseSpec();

              if (!that.checkSubmit(d.exchang == 1)) return;

              var choose_list = that.getChooseList();

              if (d.exchang == 1) {
                  util.navigateTo({
                      url: "/pages/goodsCheckout/goodsCheckout?order_type=one_click_buy&choose_list=" +
                      JSON.stringify(choose_list) + '&query_param=' + JSON.stringify(that._options) +
                      '&card_no=' + that._sel_buy.select_card.card_no + '&exchang_good=1',
                  })
                  that.setData({
                      exchang_good: 0
                  })
              } else {
                  var form_id = e.detail.formId;
                  util.api('/api/wxapp/sec/check', function (res) {
                      if (res.content.can_sec_kill.status == 0) {
                          util.navigateTo({
                              url: "/pages/goodsCheckout/goodsCheckout?order_type=sec_kill&choose_list=" + JSON.stringify(choose_list)
                          })
                      } else if (res.content.can_sec_kill.status != 7) {
                          if (res.content.can_sec_kill.oringeStock > 0) {
                              util.showModal('提示', res.content.can_sec_kill.msg, function () {
                                  util.navigateTo({
                                      url: "/pages/item/item?goods_id=" + good_id
                                  })
                              }, false, '取消', '原价购买')
                          } else {
                              util.showModal('提示', res.content.can_sec_kill.msg, function () {
                                  util.navigateTo({
                                      url: '/pages/index/index'
                                  })
                              })
                          }

                      } else if (res.content.can_sec_kill.status == 7) {
                          wx.showModal({
                              title: '提示',
                              content: res.content.can_sec_kill.msg,
                              cancelText: '原价购买',
                              confirmText: '去支付',
                              confirmColor: that.data.comColor,
                              success: function (ress) {
                                  if (ress.confirm) {
                                      util.reLaunch({
                                          url: '/pages/payment/payment?order_sn=' + res.content.can_sec_kill.unpaid_order_sn
                                      })
                                  } else if (ress.cancel) {
                                      util.reLaunch({
                                          url: '/pages/item/item?goods_id=' + res.content.can_sec_kill.goods_id
                                      })
                                  }
                              }
                          })
                      }
                  }, { sec_kill_id: sk_id, choose_list: JSON.stringify(choose_list), open_id: util.getCache("openid"), form_id: form_id })
              }
          }
      }, { sec_kill_id: sk_id })
  },

  checkSubmit(is_exchang){
    var gd = this._gd;
    if (gd.business_state != 1) {
      util.showModal('提示', '店铺未营业，无法购买');
      return false;
    }

    //判断是否要去绑定手机号
    var that = this;
    if (gd.is_bind_mobile == 1 && util.getCache('mobile') == '') {
      util.checkSession(function () {
        that.setData({
          is_block: is_block = 1
        })
      });
      return false;
    }

    // 如果会员卡可购买，且会员卡未激活，先激活后才可购买
    if (gd.card_can == true && !is_exchang) {
      var card_flag = 2;
      for (var i = 0, buy_card_list = gd.exclusive_card_list; i < buy_card_list.length; i++) {
        if (buy_card_list[i].status == 1) {
          card_flag = 0;
          break;
        } else if (buy_card_list[i].status == 2) {
          card_flag = 1;
        }
      }
      if (card_flag == 1) {
        util.showModal('提示', '请激活会员卡', function () {
          util.navigateTo({
            url: '/pages/usercardlist/usercardlist',
          })
        });
        return false;
      }
    }
    return true;
  },
  bindTopMore(e) {
    var gd = this._gd;
    if (gd.is_exclusive == 1 && gd.exclusive_card_list.length == 1 && gd.exclusive_card_list[0].card_type == 2) {
      util.showModal('提示', '您当前的会员等级不满足，仅拥有“' + gd.exclusive_card_list[0].card_name + '”等级卡用户可购买此商品。可在“个人中心”查看会员卡权益');
      return false;
    }
    var goods_id = gd.goods_id;
    if (gd.exclusive_card_list != 'undefined' && gd.exclusive_card_list.length == 1) {
      util.navigateTo({
        url: '/pages/usercardinfo/usercardinfo?card_list=1&card_id=' + gd.exclusive_card_list[0].id + '&seckillId=' + sk_id,
      })
    } else {
      util.navigateTo({
        url: '/pages/buycardlist/buycardlist?seckillId=' + sk_id,
      })
    }
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
  }
})
