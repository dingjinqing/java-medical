var util = require('../../utils/util.js')
var decorate = require("../common/decorate.js")
var spec_mixin = require("../goodscommon/spec.js")
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var is_block = 0;
var is_bind_mobile;
var dist_id = '';
var goods_main_img = '';
var spec_list = {};
var prd_list = {};
var gd;
var spec_lists = {};
var prd_lists = {};
var clocks = [];
var deli;
var time_arr = [];
var times_arr = [];
var cat_list = [];
var pictorial;
var prd_str = [];
var cat_lists = [];
var prd_strs = [];
var pro_con_first = [];
var is_prd_good = 0;
var spec_view = 0;
var is_prd_goods = 0;
var spec_views = 0;
var login_view = 0;
var spec_check = 0;
var buy_number = 1;
var limit_num = 1;
var spec_name;
var spec_names;
var goods_id;
var pin_group_id;
var imgUrls;
var commimgUrls;
var set_time_out;
var set_time_outs;
var zhege;
var is_shows = 0;
var total_micro_second = 0;
var total_seconds = 0;
var group_id;
var is_share = 0;
var os_type = '';
var is_play = 0;
var net_type = 0;
var is_second;
var winWidth = 0;//当前屏幕的宽度
var winHeight = 0;//当前屏幕的高度
var top_display;//判断回到顶部按钮是否显示
var real_Height;
var share_img;
var hh = 0;
var is_old_customer = 0;
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
  /**
   * 页面的初始数据
   */
  data: {
    images: {},
    collect_no: imageUrl + 'image/wxapp/collect_no.png',
    collect_yes: imageUrl + 'image/wxapp/collect_yes.png',
    click_more: imageUrl + '/image/wxapp/backward_right.png',
    img_sercer: imageUrl + '/image/wxapp/server_icon.png',
    img_close: imageUrl + '/image/wxapp/close_icon.png',
    img_service: imageUrl + 'image/wxapp/icon_service.png',
    img_store: imageUrl + 'image/wxapp/sto_logo1.png',
    img_hide: imageUrl + 'image/wxapp/icon_hide.png',
    imageUrl: app.globalData.imageUrl,
    baseUrl: app.globalData.baseUrl,
    indicatorDots: false,
    interval: 3000,
    total_micro_second: 0,
    total_seconds: 0,
    act_open: 0,
    duration: 1000,
    showModalStatus: false,
    actionSheetHidden: true,
    giftSheetHidden: true,
    kucun: true,
    kucuns: true,
    deliver_fee: 0,
    spec_view: 0,
    spec_views: 0,
    is_block: 0,
    spec_array: [],
    spec_arrays: [],
    spec_id_list: {},
    // choose_list: {},
    // choose_lists: {},
    proMode: true,
    specMove: true,
    specshide: true,
    toast1Hidden: true,
    toast2Hidden: true,
    toast3Hidden: true,
    is_share: 0,
    is_play: 0,
    net_type: 0,
    is_shows: 0,  //0是隐藏，1是显示
    is_second: 0,
    winHeight: 0,
    winWidth: 0,
    top_display: 0,
    hh: 0,
    nickName: util.getCache('nickName'),
    click_num:false,
    isFold: false,
    page_id: 0,
    buyType: '',
    cartGoodsNumber: 0,
    giftMore: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    mobile = util.getCache('mobile');
    // choose_list = {};
    group_id = options.group_id;
    pin_group_id = options.pin_group_id;
    // choose_lists = {};
    spec_list = {};
    spec_lists = {};
    prd_list = {};
    prd_lists = {};
    goods_main_img = '';
    clearTimeout(set_time_out);
    clearTimeout(set_time_outs);
    is_prd_good = 0;
    is_prd_goods = 0;
    cat_list = [];
    prd_str = [];
    cat_lists = [];
    prd_strs = [];
    time_arr = [];
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
      util.api('/api/wxapp/pin_goods', function (res) {
        if (res.error != 0) {
          util.showModal("提示", res.content, function () {
            wx.navigateBack({

            })
          }, false);
          return false
        }
        if (res.content.goods_id) {
            util.getUserLocation(function (loc) {
                util.api('/api/wxapp/user_goods/record', function (res1) { }, {
                    goods_id: res.content.goods_id, active_type: 1, active_id: pin_group_id, type: 1, lat: loc.latitude || '',
                    lng: loc.longitude || ''
                })
            })
        }
        cat_list = [];
        prd_str = [];
        cat_lists = [];
        prd_strs = [];
        that._gd = gd = res.content;
        that.goodsSpecs = res.content.specs;
        that.pinGoodsSpecs = res.content.pin_specs;
        is_block = 0;
        is_shows = 0;
        page_id = gd.goods_page_id;
        that.page_id = page_id;
        that.setData({
          page_id: page_id,
        })
        if (page_id > 0) {
          that.requestDecoratePageData(page_id, 0, that.processWindowData.bind(that));
        }
        goods_id = gd.goods_id;
        top_display: 0;
        is_bind_mobile = gd.is_bind_mobile;//是否要绑定手机号判断
        imgUrls = gd.imgs,
          goods_main_img = gd.goods_img;
        if (gd.pin_specs.spec_name != null) {
          spec_name = gd.pin_specs.spec_name;
        }
        if (gd.specs.spec_name != null) {
          spec_names = gd.specs.spec_name;
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
        if (gd.goods_desc != null) {
		      gd.goods_desc = util.filterRichText(gd.goods_desc);
        }

        //显示的进行中的团
        if (gd.pin_groups.length > 2) {
          gd.show_group = gd.pin_groups.slice(0, 2);
        } else {
          gd.show_group = gd.pin_groups;
        }

        //差几人

        for (var i = 0; i < gd.pin_groups.length; i++) {
          gd.pin_groups[i].wait_num = gd.pin_group_info.limit_amount - gd.pin_groups[i].grouping_num;
          var time_infos = gd.pin_groups[i].remaining_time;
          total_seconds = (time_infos.hour * 60 + time_infos.minute) * 60 + time_infos.second;
          time_arr.push(total_seconds);
        }
        that.countdowns(that, time_arr);


        //拼团活动的显示价格
        if (gd.pin_shop_price) {
          if (gd.pin_shop_price.indexOf("~") > -1) {
            gd.pin_min_price = gd.pin_shop_price.split("~")[0];
            gd.pin_min_price = parseFloat(gd.pin_min_price).toFixed(2);
            if (gd.pin_min_price.split('.')[1] == '00') {
              gd.pin_min_price = parseFloat(gd.pin_min_price).toFixed(0);
            }
          } else {
            gd.pin_min_price = gd.pin_shop_price;
            gd.pin_min_price = parseFloat(gd.pin_min_price).toFixed(2);
            if (gd.pin_min_price.split('.')[1] == '00') {
              gd.pin_min_price = parseFloat(gd.pin_min_price).toFixed(0);
            }
          }
        }
        if (gd.shop_price.indexOf("~") > -1) {
          gd.shop_max_price = gd.shop_price.split("~")[1];
          gd.shop_max_price = parseFloat(gd.shop_max_price).toFixed(2);
          if (gd.shop_max_price.split('.')[1] == '00') {
            gd.shop_max_price = parseFloat(gd.shop_max_price).toFixed(0);
          }
          gd.shop_min_price = gd.shop_price.split("~")[0];
        } else {
          gd.shop_max_price = gd.shop_price;
          gd.shop_max_price = parseFloat(gd.shop_max_price).toFixed(2);
          if (gd.shop_max_price.split('.')[1] == '00') {
            gd.shop_max_price = parseFloat(gd.shop_max_price).toFixed(0);
          }
          gd.shop_min_price = gd.shop_price;
          gd.shop_min_price = parseFloat(gd.shop_min_price).toFixed(2);
        }
        gd.save_price = parseFloat(gd.shop_max_price) - parseFloat(gd.pin_min);
        gd.save_price = gd.save_price.toFixed(2);
        if (gd.save_price.split('.')[1] == '00') {
          gd.save_price = parseFloat(gd.save_price).toFixed(0);
        }
        if (gd.save_price) {
          var max = gd.shop_max_price;
          var min = gd.pin_min_price;
          util.api('/api/wxapp/pin/shareimg', function (res) {
            share_img = res.content;
          }, { max: max, min: min, goods_id: goods_id, pin_group_id : pin_group_id });
        }
        //倒计时
        var pin_time_info = gd.pin_group_info.remaining_time;
        var cancel_time = pin_time_info.date * 24 * 60 + pin_time_info.hour * 60 + pin_time_info.minute;
        total_micro_second = cancel_time * 60 + pin_time_info.second;
        if (total_micro_second > 0) {
          that.countdown(that);
          that.setData({
            act_open: 1
          });
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
          item_price: gd.pin_shop_price,
          item_prices: gd.shop_price,
          show_price: gd.pin_min_price,
          goods_num: gd.goods_number,
          sale_num: gd.goods_sale_num,
          imgUrls: gd.imgs,
          goods_img: gd.goods_img,
          spec_name: gd.pin_specs.spec_name,
          spec_names: gd.specs.spec_name,
          shop_info: gd.shop_info,
          collection: gd.collection,
          comment_info: gd.comment_info,
          is_shows: is_shows,
          pin_group_id: pin_group_id
        })
        that.getReplyRect(0);
      }, { pin_group_id: pin_group_id, location: JSON.stringify(location) },'',true);
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
  //团购弹框
  isShow: function () {
    this.setData({
      is_shows: 1
    })
  },
  isHide: function () {
    this.setData({
      is_shows: 0
    })
  },
  // 收藏
  toast1Tap: function (e) {
    var col = this.data.collection;
    var that = this;
    if (col == '' || col == null) {
      util.api('/api/wxapp/collect/add', function (res) {
        that.setData({
          toast1Hidden: !that.data.toast1Hidden,
          collection: res
        })
      }, { goods_id: goods_id  });

    }
    else {
      util.api('/api/wxapp/collect/del', function (res) {
        that.setData({
          toast2Hidden: !that.data.toast2Hidden,
          collection: ''
        })
      }, { goods_id: goods_id  });

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
 
  //规则详情
  toRule: function () {
    util.jumpToWeb('/wxapp/group/help')
  },
  // 查看全部评价
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
  //返回首页
  toIndex: function () {
    util.reLaunch({
      url: '/pages/index/index',
    })
  },
  //轮播图预览
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
  getPhoneNumber: function (e) {
    var that = this;
    is_block = 0;
    that.setData({
      is_block: is_block
    })
    if (e.detail.errMsg == 'getPhoneNumber:ok') {
      util.api('/api/wxapp/wxdecrypt', function (res) {
        if (res.error == 0) {
          mobile = res.content.phoneNumber;
          util.setCache("mobile", mobile);
        }
      }, {   iv: e.detail.iv, crypt_data: e.detail.encryptedData })
    }
  },
  phoneClose: function (e) {
    is_block = 0;
    this.setData({
      is_block: is_block
    })
  },
 
  countdown: function (that) {
    that.setData({
      clock: util.dateformat(total_micro_second),

    });
    // console.log(util.dateformat(total_micro_second));
    if (total_micro_second <= 0) {
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
  countdowns: function (that, time_arr) {
    set_time_outs = setTimeout(function () {
      // 放在最后--
      for (var i = 0; i < time_arr.length; i++) {
        time_arr[i] = time_arr[i] - 1;
        times_arr[i] = that.dateformats(time_arr[i]);
        that.setData({
          clocks: times_arr
        });
      }
      that.countdowns(that, time_arr);
    }
      , 1000)
  },
  dateformats: function (micro_second) {
    // 秒数
    var second = Math.floor(micro_second);
    // 小时位
    var hr = Math.floor((second) / 3600);
    // 分钟位
    var min = Math.floor((second - hr * 60 * 60) / 60);
    // 秒位
    var sec = second % 60;
    return hr + ':' + min + ":" + sec;
  },
  //去参团
  toJoin:function(e){
      var groups_id = e.currentTarget.dataset.group_ids;
      var group_type = e.currentTarget.dataset.group_type;
      var grouper_id = e.currentTarget.dataset.grouper_id;
    if (group_type == 2 && gd.have_order_flag && grouper_id != util.getCache('user_id')){
          this.old_customer();
      }else{
          util.navigateTo({
              url: '/pages/groupbuyinfo/groupbuyinfo?group_id=' + groups_id,
          })
      }
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
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
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    util.api("/api/wxapp/share/record", function (d) {

    }, { activity_id: pin_group_id, activity_type: 1 });
    return {
      title: gd.pin_group_info.share_title,
      imageUrl: imageUrl + share_img,
      path: "/pages/groupbuyitem/groupbuyitem?pin_group_id=" + pin_group_id + "&invite_id=" + util.getCache('user_id'),
    }
  },

  go_share: function () {
    var that = this;
    util.api('/api/wxapp/pin_goods', function (res) {
      if (res.error != 0) {
        util.showModal("提示", res.content, function () {
          wx.navigateBack({

          })
        }, false);
        return false
      }
      if (res.content.can_pin_group.status == 1 || res.content.can_pin_group.status == 2 || res.content.can_pin_group.status == 4 || res.content.can_pin_group.status == 5) {
        util.showModal('暂不支持分享', res.content.can_pin_group.msg, function () {
          util.navigateTo({
            url: "/pages/item/item?goods_id=" + goods_id
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
        }, { action: 2, goods_id: goods_id, identity_id: pin_group_id })
      }
    }, { pin_group_id: pin_group_id  })
  },
  not_show_share: function () {
    var that = this;
    that.setData({
      is_share: 0
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
  gotoCart: function () {
    util.reLaunch({
      url: '/pages/cart/cart'
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
  old_customer: function () {
      is_old_customer = 1;
      this.setData({
          is_old_customer: is_old_customer
      })
  },
  not_show_customer: function () {
      is_old_customer = 0;
      this.setData({
          is_old_customer: is_old_customer
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


  getUserInfo: function (e) {
    var that = this;
    var canIUse = wx.canIUse('button.open-type.getUserInfo');
    if (e.detail.userInfo) {
      if (canIUse) {
        that.updateUserInfo(e.detail.userInfo.nickName, e.detail.userInfo.avatarUrl)
      } else {
        wx.getUserInfo({
          success: res => {
            that.updateUserInfo(e.detail.userInfo.nickName,e.detail.userInfo.avatarUrl)
          }
        })
      }
      that.setData({
        nickName: user_name,
      })
    }
    if (e.currentTarget.dataset.kt == 0) {
      that.toJoin(e);
    }
    if (e.currentTarget.dataset.kt == 1) {
      that.bindGroupBuy(e);
    }
    if (e.currentTarget.dataset.xz == 1) {
      that.go_share(e);
    }
    that.setData({
      click_num: true,
    })
  },
  updateUserInfo(name,avatar){
    util.setCache("nickName", name);
    util.setCache("avatarUrl", avatar);
    util.api('/api/wxapp/account/updateUser', null, { username: name, user_avatar: avatar });
  },
  checkSelBuy(buyType) {
    this.changeGoodsSpecs(buyType);
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
  changeGoodsSpecs(buyType){
    buyType = buyType || 'item';
    if (buyType != this.data.buyType) {
      var goodsData = JSON.parse(JSON.stringify(this._gd));
      if (buyType == 'group') {
        goodsData.shop_price = this._gd.pin_shop_price
        goodsData.specs = this._gd.pin_specs
      }
      this.setData({ gd: goodsData, buyType: buyType })
    }
  },
  bindAddCart(e) {
    if (!this.checkSelBuy('item')) return;
    this.addCart(this.getChooseList(), e.detail.form_id, this._options);
    this.bindCloseSpec();
  },
  getChooseList() {
    var s = this._sel_buy;
    var gd = this._gd;
    return {
      pin_group_id: pin_group_id,
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
  bindSelectSpec() {
    if (!this._sel_buy.has_spec) return;
    this.changeGoodsSpecs('group');
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
          prd_id: choose_list['prd_id'],
          num: choose_list['goods_number']
        })
      })
      if (res.error == 0) {
        util.getUserLocation(function (loc) {
          util.api('/api/wxapp/user_goods/record', function (res1) { }, {
            goods_id: choose_list['goods_id'],
            lat: loc.latitude || '',
            lng: loc.longitude || '',
            type: 2,
            prd_id: choose_list['prd_id'],
            num: choose_list['goods_number']
          })
        });
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
  bindOneClickBuy: function (e) {
    if (!this.checkSelBuy('item')) return;
    this.bindCloseSpec();
    if (!this.submitCheck()) return;

    var choose_list = this.getChooseList();
    wx.navigateTo({
      url: "/pages/goodsCheckout/goodsCheckout?order_type=one_click_buy&choose_list=" +
        JSON.stringify(choose_list) + '&query_param=' + JSON.stringify(this._options),
    })
  },
  bindGroupBuy: function (e) {
    if (!this.checkSelBuy('group')) return;
    this.bindCloseSpec();
    if (!this.submitCheck()) return;

    var choose_list = this.getChooseList();
    var form_id = e.detail.formId;
    if (gd.pin_goods_num == 0) {
      util.showModal('提示', '库存不足，无法参团')
    } else {
      util.api('/api/wxapp/pin/check', function (res) {
        if (res.content.can_pin_group.status == 0) {
          wx.navigateTo({
            url: "/pages/goodsCheckout/goodsCheckout?order_type=pin_group&choose_list=" + JSON.stringify(choose_list)
          })
        } else {
          util.showModal('提示', res.content.can_pin_group.msg)
        }
      }, { pin_group_id: pin_group_id, form_id: form_id, open_id: util.getCache("openid") })
    }
  },

  submitCheck(){
    var gd = this._gd;

    //判断是否要去绑定手机号
    var that = this;
    if (is_bind_mobile == 1 && util.getCache('mobile') == '') {
      util.checkSession(function () {
        that.setData({
          is_block: is_block = 1
        })
      });
      return false;
    }
    return true;
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
