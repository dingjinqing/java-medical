const util = require('../../utils/util.js')
var decorate = require("../../pages/common/decorate.js")
var spec_mixin = require("../../pages/goodscommon/spec.js")

var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var good_id = '';
var mobile = util.getCache('mobile');
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
var integral_goods_id;
var winWidth = 0; //当前屏幕的宽度
var winHeight = 0; //当前屏幕的高度
var top_display; //判断回到顶部按钮是否显示
var real_Height;
var is_max = 0;
var is_min = 0;
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
    spec_view: 0,
    coupon_flag: 0,
    limit_buy_num: 0,
    is_block: 0,
    spec_array: [],
    spec_id_list: {},
    // choose_list: {},
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
    is_max: 0,
    is_min: 0,
    hh: 0,
    nickName: util.getCache('nickName'),
    click_num: false,
    isFold: false,
    page_id:0,
    giftMore: false
  },
  onLoad: function (option) {
    if (!util.check_setting(option)) return;
    mobile = util.getCache('mobile');
    integral_goods_id = option.integral_goods_id;
    // choose_list = {};
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
    good_id = option.good_id || option.goods_id;
    var that = this;
    zhege = this;
    var user_name = util.getCache('nickName');
    var user_avatar = util.getCache('avatarUrl');
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

    util.getUserLocation(function (location) {
      util.api('/api/wxapp/integral/goods', function (res) {
        if (res.content.is_delete == 1) {
          util.showModal('提示', '该商品已删除', function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return false;
        }
        // 活动不存在之类
        if (res.error != 0) {
          util.showModal('提示', res.content, function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return false;
        }
        if (res.content.goods_id) {
            util.getUserLocation(function (loc) {
                util.api('/api/wxapp/user_goods/record', function (res1) { }, {
                    goods_id: res.content.goods_id,
                    active_type: 4,
                    active_id: integral_goods_id,
                    type: 1,
                    lat: loc.latitude || '',
                    lng: loc.longitude || ''
                })
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
        off_buss = gd.shop_info.business_state;
        is_min = 1;
        is_max = 0;
        //最多购买限制
        if (gd.max_exchange_num != -1) {
          if (gd.max_exchange_num > gd.integral_goods_num) {
            gd.max_exchange_num = gd.integral_goods_num;
          }
        }
        if (gd.pictorial) {
          util.api('/api/wxapp/upayyun/image', function (res) {
            if (res.error == 0) {
                posterBase64 = res.content;
                gd.pictorial = posterBase64;
            }
          }, { image_path: gd.pictorial });
         
        }
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
        spec_name = gd.integral_spec.spec_name;
        
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
        //商品信息
        that.setData({
          gd: gd,
          goods_name: gd.goods_name,
          goods_ad: gd.goods_ad,
          item_price: gd.goods_integral,
          item_money: gd.goods_money,
          goods_num: gd.max_exchange_num,
          sale_num: gd.goods_sale_num,
          imgUrls: gd.imgs,
          goods_img: gd.goods_img,
          spec_name: gd.integral_spec.spec_name,
          limit_buy_num: gd.max_exchange_num,
          shop_info: gd.shop_info,
          collection: gd.collection,
          comment_info: gd.comment_info,
          is_block: is_block,
          good_nums: good_nums,
          is_min: is_min,
          is_max: is_max,
          pictorial:gd.pictorial,
          integral_goods_id: integral_goods_id
        })

        that.getReplyRect(0);
      }, { integral_goods_id: integral_goods_id, location: JSON.stringify(location) },'',true);
    })
    //获取cache
    // choose_list['user_id'] = util.getCache('user_id');
    //登录
    that.setData({
      login_view: login_view
    })
    //客服

  },
  onShow: function () {
    if (this.data.page_id > 0) {
      this.requestDecoratePageData(this.data.page_id, 0, this.processWindowData.bind(this));
    }
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
  goTop: function (e) { // 一键回到顶部
    if (wx.pageScrollTo) { //判断这个方法是否可用
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
      }, {
          goods_id: good_id
        });

    } else {
      util.api('/api/wxapp/collect/del', function (res) {
        that.setData({
          toast2Hidden: !that.data.toast2Hidden,
          collection: ''
        })
      }, {
          goods_id: good_id
        });

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
  specClick: function (e) { //规格
    is_max = 0;
    this.setData({
      is_max: is_max
    })
    var cat = e.target.dataset.cat;
    var key = e.target.dataset.key;
    var group = e.target.dataset.group;
    for (var i in spec_list[group].val) {
      spec_list[group].val[i].checked = 0;
    }
    spec_list[group].val[key].checked = 1;
    var param = {};
    if (contains(cat_list, group)) {
      for (var i = 0; i < prd_str.length; i++) {
        if (prd_str[i].indexOf(group + ":") == 0) {
          prd_str[i] = group + ":" + spec_list[group].val[key].id;
          break;
        }
      }
    } else {
      cat_list.push(group);
      prd_str.push(group + ":" + spec_list[group].val[key].id)
    }
    var str = "spec_id_list[" + group + "]";
    param[str] = key;
    this.setData(param);

    var prd_key = '';
    for (var i = 0; i < prd_str.length; i++) {
      if (i != prd_str.length - 1) {
        prd_key += prd_str[i] + "!!";
      } else {
        prd_key += prd_str[i];
      }
    }
    prd_key = prd_key.replace(/:/gi, ".").split('!!').sort(function (a, b) {
      return a - b
    }).join('!!').replace(/\./gi, ':');
    //检查库存
    var spce_list_num = 0;
    for (var i in spec_list) {
      spce_list_num = spce_list_num + 1;
      for (var j in spec_list[i].val) {
        spec_list[i].val[j].temp = 0;
      }
    }
    var v_arr = group + ":" + key;
    for (var i in prd_list) {
      if (prd_list[i].prd_number == 0) {
        var p_arr = i.split('!!'); //库存为0的
        var temp_arr = prd_key.split("!!"); //已选的
        if (p_arr.length == 2) { //两规格
          for (var k in temp_arr) {
            if (i.indexOf(temp_arr[k]) > -1) {
              for (var j in p_arr) {
                if (p_arr[j] != temp_arr[k]) {
                  var s_arr = p_arr[j].split(":");
                  spec_list[s_arr[0]].val[s_arr[1]].temp = 1;
                }
              }
            }
          }
        } else {

        }
      }
    }

    if (typeof (prd_list[prd_key]) != 'undefined') {
      param['item_price'] = prd_list[prd_key].score;
      param['item_money'] = prd_list[prd_key].money;
      param['goods_prd_num'] = prd_list[prd_key].stock;
      param['goods_img'] = prd_list[prd_key].prd_img;
      if (param.goods_img == '') {
        param['goods_img'] = goods_main_img;
      }
      param['prd_name'] = prd_list[prd_key].prd_name;
      param['prd_key'] = prd_key;
      param['prd_desc'] = param['prd_name'].trim().split(/\s+/);
      this.setData(param);

    } else {
      if (prd_str.length == spce_list_num) {
        this.setData({
          goods_prd_num: 0
        });
        util.showModal("提示", "此规格无库存");
      }
    }
    if (limit_num == 0 || limit_num == null) {
      limit_num = 1;
    }
    this.setData({
      spec_array: spec_list,
      spec_name: param.prd_name,
      kucun: false,
      buy_number: limit_num
    })
  },
  specShow: function () {
    this.setData({
      specMove: false
    })
  },
  cancel: function () {
    this.setData({
      actionSheetHidden: true
    })
  },
  actionSheetChange: function () {
    this.setData({
      actionSheetHidden: true
    })
  },
  specActionChange: function () {
    this.setData({
      specMove: true
    })
  },
  specCancel: function () {
    this.setData({
      specMove: true
    })
  },
  bindGetPhoneNumberOk(e) {
    mobile = e.detail.phoneNumber;
  },
  OneClickBuy: function (e) {
    if (!this.checkSelBuy()) return;
    this.bindCloseSpec();
    //判断是否要去绑定手机号
    var that = this;
    if (is_bind_mobile == 1 && util.getCache('mobile') == '') {
      util.checkSession(function () {
        that.setData({
          is_block: is_block = 1
        })
      })
      return false;
    }
    if (off_buss != 1) {
      util.showModal('提示', '店铺未营业，无法购买');
      return false;
    }

    var choose_list = this.getChooseList();
    util.navigateTo({
      url: "/pages/goodsCheckout/goodsCheckout?order_type=integral&choose_list=" + JSON.stringify(choose_list)
    })
  },
  to_index: function () {
    util.reLaunch({
      url: '/pages/index/index'
    })
  },
  to_cart: function () {
    util.navigateTo({
      url: '/pages/cart/cart'
    })
  },
  ViewCoupon: function () { //优惠券
    this.setData({
      actionSheetHidden: false
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
  proClcik: function () {
    this.setData({
      proMode: false
    })
  },
  preview: function (e) {
    var nowImgUrl = e.currentTarget.dataset.src;
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
  get_goods_number: function (e) {
    good_nums = e.detail.value;

    if (good_nums > this.data.goods_num && this.data.goods_num != -1) {
      util.showModal('提示', '最多可兑换' + gd.max_exchange_num + gd.unit);
      good_nums = gd.max_exchange_num;
      choose_list['goods_number'] = good_nums;
    } else if (good_nums > gd.integral_goods_num) {
      util.showModal('提示', '库存不足');
      good_nums = gd.integral_goods_num;
      choose_list['goods_number'] = good_nums;
    } else {
      choose_list['goods_number'] = e.detail.value;
    }
    if (good_nums < gd.max_exchange_num) {
      is_max = 0;
      this.setData({
        is_max: is_max
      })
    }
    if (e.currentTarget.dataset.max >= 1) {
      if (good_nums >= e.currentTarget.dataset.max) {
        is_max = 1;
        good_nums = e.currentTarget.dataset.max;
        this.setData({
          is_max: is_max,
          good_nums: good_nums
        })
      }
    } else {
      if (good_nums >= e.currentTarget.dataset.spec) {
        is_max = 1;
        good_nums = e.currentTarget.dataset.spec;
        this.setData({
          is_max: is_max,
          good_nums: good_nums
        })
      }
    }
    this.setData({
      good_nums: good_nums
    })
  },
  onShareAppMessage: function (res) {
    util.api("/api/wxapp/share/record", function (d) {

    }, {
        activity_id: integral_goods_id,
        activity_type: 4
      });
    return {
      title: gd.share_title,
      imageUrl: gd.share_img,
      path: '/pages/integralitem/integralitem?integral_goods_id=' + integral_goods_id+ '&invite_id=' + util.getCache('user_id'),
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
        util.api('/api/wxapp/account/updateUser', function (res) { }, {
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
            util.api('/api/wxapp/account/updateUser', function (res) { }, {
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
  go_share: function () {
    var that = this;
    that.setData({
      is_share: 1
    })
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
  getChooseList() {
    var s = this._sel_buy;
    var gd = this._gd;
    return {
      goods_id: gd.goods_id,
      integral_goods_id: integral_goods_id,
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
  }
})
