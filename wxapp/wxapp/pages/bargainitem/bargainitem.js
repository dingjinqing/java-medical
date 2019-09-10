const util = require('../../utils/util.js')
var decorate = require("../common/decorate.js")
var spec_mixin = require("../goodscommon/spec.js")
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var good_id = '';
var bargain_id;
var mobile = util.getCache('mobile');
var is_bind_mobile;
var is_block = 0;
var dist_id = '';
var goods_main_img = '';
var deli;
var login_view = 0;
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
var winWidth = 0;//当前屏幕的宽度
var winHeight = 0;//当前屏幕的高度
var top_display;//判断回到顶部按钮是否显示
var real_Height;
var hh = 0;
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
    click_num: false,
    nickName: util.getCache('nickName'),
    kucun: true,
    deliver_fee: 0,
    is_second: 0,
    coupon_flag: 0,
    limit_buy_num: 0,
    limit_max_num: 0,
    is_block: 0,
    isFold: false,
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
    net_type: 0,
    winHeight: 0,
    winWidth: 0,
    top_display: 0,
    hh: 0,
    page_id: 0,
    cartGoodsNumber: 0,
    giftMore:false
  },
  onLoad: function (option) {
    if (!util.check_setting(option)) return;
    mobile = util.getCache('mobile');
    clearTimeout(set_time_out);
    top_display: 0;
    buy_number = 1;
    goods_main_img = '';
    good_nums = 1;
    limit_num = 1;
    bargain_id = option.bargain_id;
    var that = this;
    zhege = this;
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
      util.api('/api/wxapp/bargain/goods', function (res) {
        if (res.content.is_delete == 1) {
          util.showModal('提示', '该商品已删除', function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return;
        }
        if (res.content.goods_id) {
          util.getUserLocation(function (loc) {
            util.api('/api/wxapp/user_goods/record', null, {
              goods_id: res.content.goods_id,
              active_type: 3,
              lat: loc.latitude || '',
              lng: loc.longitude || '',
              type: 1,
              active_id: bargain_id
            })
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
        is_block = 0
        is_bind_mobile = gd.is_bind_mobile;//是否要绑定手机号判断
        //倒计时
        if (gd.can_bargain.remaining_time) {
          total_micro_second = gd.can_bargain.remaining_time;
        }
        if (total_micro_second > 0) {
          that.countdown(that);
          that.setData({
            act_open: 1
          });
        }
        off_buss = gd.business_state;
        that.data.limit_max_num = parseInt(gd.limit_max_num);

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

        for (var prd_index in gd.specs.prd_list) {
          if (gd.specs.prd_list[prd_index].prd_number > gd.goods_old_number) {
            gd.specs.prd_list[prd_index].goods_old_number = gd.goods_old_number;
          } else {
            gd.specs.prd_list[prd_index].goods_old_number = gd.specs.prd_list[prd_index].prd_number;
          }
        }

        //商品信息
        that.setData({
          gd: gd,
          goods_name: gd.goods_name,
          goods_ad: gd.goods_ad,
          item_price: gd.shop_price,
          goods_num: gd.goods_number,
          goods_old_number: gd.goods_old_number,
          sale_num: gd.bargain_join_number,
          imgUrls: gd.imgs,
          goods_img: gd.goods_img,
          spec_name: gd.specs.spec_name,
          limit_buy_num: gd.limit_buy_num,
          shop_info: gd.shop_info,
          collection: gd.collection,
          comment_info: gd.comment_info,
          is_block: is_block,
          good_nums: good_nums,
          bargain_id: bargain_id
        })
        that.getReplyRect(0);
      }, { bargain_id: bargain_id, location: JSON.stringify(location) }, '', true);
    })
    //登录
    that.setData({
      login_view: login_view
    })
    //购物车商品数
    that.changeCartGoodsNumber();
  },

  onShow: function () {
    if (this.data.page_id > 0) {
      this.requestDecoratePageData(this.data.page_id, 0, this.processWindowData.bind(this));
    }
    //购物车商品数
    this.changeCartGoodsNumber();
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
        content: '当前微信版本过低，无法使用该功能，请升级到最新微信版本后重试。',
        confirmColor: util.getCache("main_colors)"),
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
  checkSelBuy(use_exchange_card) {
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
  bindAddCart(e) {
    if (!this.checkSelBuy()) return;
    this.addCart(this.getChooseList(), e.detail.form_id, this._options);
    this.bindCloseSpec();
  },
  getChooseList() {
    var s = this._sel_buy;
    var gd = this._gd;
    return {
      bargain_id: bargain_id,
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
    util.api('/api/wxapp/cart/add', function (res) {
      util.getUserLocation(function (loc) {
        util.api('/api/wxapp/user_goods/record', function (res1) { }, {
          goods_id: choose_list['goods_id'],
          lat: loc.latitude || '',
          lng: loc.longitude || '',
          type: 2,
          active_type:3,
          prd_id: choose_list['prd_id'],
          num: choose_list['goods_number']
        })
      })
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

  go_share: function () {
    var that = this;
    util.api('/api/wxapp/bargain/goods', function (res) {
      if (res.content.is_delete == 1) {
        util.showModal('提示', '该商品已删除', function () {
          util.reLaunch({
            url: '/pages/index/index'
          })
        });
        return;
      }
      if (res.content.can_bargain.status == 1 || res.content.can_bargain.status == 2 || res.content.can_bargain.status == 4 || res.content.can_bargain.status == 6) {
        util.showModal('暂不支持分享', res.content.can_bargain.msg, function () {
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
                    pictorial: posterBase64
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
        }, { action: 3, goods_id: goods_ids, identity_id: bargain_id  })
      }
    }, { bargain_id: bargain_id  })
  },
  getUserInfo: function (e) {
    var that = this;
    var canIUse = wx.canIUse('button.open-type.getUserInfo');
    if (e.detail.userInfo) {
      if (canIUse) {
        that.updateUserInfo(e.detail.userInfo.nickName, e.detail.userInfo.avatarUrl)
      } else {
        wx.getUserInfo({
          success: res => {
            that.updateUserInfo(e.detail.userInfo.nickName, e.detail.userInfo.avatarUrl)
          }
        })
      }
      that.setData({
        nickName:user_name
      })
    }
    if(e.currentTarget.dataset.kj == 1){
      that.bindBargainBuy(e);
    }
    that.setData({
      click_num: true
    })
  },

  updateUserInfo(name,avatar){
    util.setCache("nickName", name);
    util.setCache("avatarUrl", avatar);
    util.api('/api/wxapp/account/updateUser', function (res) {
    }, { username: name, user_avatar: avatar });
    this.setData({
      nickName: name,
    })
  },

  not_show_share: function () {
    var that = this;
    that.setData({
      is_share: 0
    })
  },
  bindGetPhoneNumberOk(e){
    mobile = e.detail.phoneNumber;
  },

  bindSelectSpec:function(){
    if (this.data.buyType != 'bargain') {
      this.setData({ buyType: 'bargain', gd: this._gd })
    }
  },
  bindOneClickBuy: function (e) {
    var d = this.eventData(e);
    var gd = this._gd;
    if (this.data.buyType == 'bargain') {
      this.setData({ buyType: '', gd })
    }
    
    if (!this.checkSelBuy(d.exchang == 1)) return;
    this.bindCloseSpec();

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
    if (gd.card_can == true && d.exchang != 1) {
      var card_flag = 2;
      for (var i = 0, buy_card_list = gd.buy_card_list; i < buy_card_list.length; i++) {
        if (buy_card_list[i].status == 1) {
          card_flag = 0;
          break;
        } else if (buy_card_list[i].status == 2) {
          card_flag = 1;
        }
      }
      if (card_flag == 1) {
        util.showModal('提示', '请激活会员卡', function () {
          wx.navigateTo({
            url: '/pages/usercardlist/usercardlist',
          })
        });
        return false;
      }
    }
    var choose_list = this.getChooseList();
    util.navigateTo({
      url: "/pages/goodsCheckout/goodsCheckout?order_type=one_click_buy&choose_list=" + JSON.stringify(choose_list)
    })
  },
  bindBargainBuy: function (e) {
    var d = this.eventData(e);
    var gd = this._gd;
    if (this.data.buyType != 'bargain') {
      this.setData({ buyType: 'bargain', gd: this._gd })
    }
    if (!this.checkSelBuy()) return;
    this.bindCloseSpec();

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
    if (gd.card_can == true) {
      var card_flag = 2;
      for (var i = 0, buy_card_list = gd.buy_card_list; i < buy_card_list.length; i++) {
        if (buy_card_list[i].status == 1) {
          card_flag = 0;
          break;
        } else if (buy_card_list[i].status == 2) {
          card_flag = 1;
        }
      }
      if (card_flag == 1) {
        util.showModal('提示', '请激活会员卡', function () {
          wx.navigateTo({
            url: '/pages/usercardlist/usercardlist',
          })
        });
        return false;
      }
    }


    var choose_list = this.getChooseList();

    util.api("/api/wxapp/bargain/apply", function (res) {
      if (res.error == 0) {
        util.navigateTo({
          url: "/pages/bargaininfo/bargaininfo?record_id=" + res.content.record_id + "&bargain_money=" + res.content.bargain_money
        })
      } else {
        util.showModal('提示', res.content);
      }
    }, { choose_list: JSON.stringify(choose_list),
      form_id: e.detail.form_id,
      open_id: util.getCache("open_id")
    },'',true)
  },

  to_index: function () {
    util.jumpLink('/pages/index/index','reLaunch')
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

  toRule: function () {
    util.jumpToWeb('/wxapp/bargain/help');
  },
  onShareAppMessage: function (res) {
    var username = util.getCache('nickName');
    util.api("/api/wxapp/share/record", function (d) {

    }, { activity_id: bargain_id, activity_type: 3 });
    return {
      title: gd.share_title,
      imageUrl: gd.share_img,
      path: 'pages/bargainitem/bargainitem?bargain_id=' + bargain_id + '&invite_id=' + util.getCache('user_id'),
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
  bindContact(e) {
    if (e.detail.path) {
      var url = util.getPath(e.detail.path, e.detail.query);
      util.jumpLink(url, "redirectTo");
    }
  },
  changeCartGoodsNumber() {
    var that = this;
    util.api("/api/wxapp/cart/goods/number", function (res) {
      if (res.error == 0) {
        that.setData({ cartGoodsNumber: res.content.goods_number });
      }
    })
  },
  gift_view_more() {
    this.setData({
      giftMore: !this.data.giftMore
    })
  }
})
