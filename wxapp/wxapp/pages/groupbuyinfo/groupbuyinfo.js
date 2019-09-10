// pages/groupbuyinfo/groupbuyinfo.js
var util = require('../../utils/util.js')
var spec_mixin = require("../goodscommon/spec.js")
var app = new getApp();
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var good_id = '';
var mobile = util.getCache('mobile');
var is_block = 0;
var is_bind_mobile;
var order_sn;
var group_id;
var pin_content = [];
var total_micro_second = 0;
var set_time_out;
var is_prd_good = 0;
var spec_view = 0;
var choose_list = {};
var spec_list = {};
var buy_number = 1;
var prd_list = {};
var cat_list = [];
var prd_str = [];
var is_prd_good = 0;
var goods_main_img = '';
var goods_id;
var spec_name;
var is_share = 0;
var pictorial;
var os_type = '';
var is_second = 0;
var reco_goods = [];
var share_img ;
var is_old_customer = 0;
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
  mixins: [spec_mixin],
  /**
   * 页面的初始数据
   */
  data: {
    collect_yes: imageUrl + 'image/wxapp/collect_yes.png',
    img_noperson: imageUrl + 'image/wxapp/icon_group2.png',
    img_otherperson: imageUrl + 'image/wxapp/icon_group1.png',
    click_more: imageUrl + '/image/wxapp/backward_right.png',
    img_close: imageUrl + '/image/wxapp/close_icon.png',
    imageUrl: app.globalData.imageUrl,
    baseUrl:app.globalData.baseUrl,
    total_micro_second: 0,
    act_open: 0,
    specMove: true,
    spec_array: [],
    spec_id_list: {},
    choose_list: {},
    goods_main_img:'',
    is_block:0,
    is_share: 0,
    kucun: true,
    is_second:0,
    click_num:false,
    nickName: util.getCache('nickName'),
    reco_goods:[],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;

    var that = this;
    group_id = options.group_id;
    clearTimeout(set_time_out);
    choose_list = {};
    spec_list = {};
    prd_list = {};
    is_prd_good = 0;
    cat_list = [];
    prd_str = [];
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
    util.api('/api/wxapp/pin/share', function (e) {
      if(e.error == 0){
        pin_content = e.content;
        if (e.content.is_delete == 1) {
          util.showModal('提示', '该活动已失效', function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return;
        }
        if(pin_content.goods_info.goods_id){
            util.getUserLocation(function (loc) {
                util.api('/api/wxapp/user_goods/record', function (res1) { }, {
                    goods_id: pin_content.goods_info.goods_id,
                    lat: loc.latitude || '',
                    lng: loc.longitude || '',
                    type: 1,
                    active_type: 1,
                    active_id: pin_content.pin_info.id
                })
            })
        }


        is_block=0;
        // spec_name = pin_content.pin_specs.spec_name;
        is_bind_mobile = pin_content.is_bind_mobile;//是否要绑定手机号判断
        goods_main_img = pin_content.goods_info.goods_img;
        goods_id = pin_content.goods_info.goods_id;
        that.pin_group_id = pin_content.pin_info.id;
        //显示的价格
        if (pin_content.goods_info.pin_shop_price.indexOf('~')>-1){
          pin_content.pin_min_price = pin_content.goods_info.pin_shop_price.split("~")[0];
        }else{
          pin_content.pin_min_price = pin_content.goods_info.pin_shop_price
        }
        if (pin_content.goods_info.shop_price.indexOf('~') > -1) {
          pin_content.max_price = pin_content.goods_info.shop_price.split("~")[0];
        }else{
          pin_content.max_price = pin_content.goods_info.shop_price
        }

        var max = pin_content.max_price;
        var min = pin_content.pin_min_price;
        util.api('/api/wxapp/pin/shareimg', function (res) {
          share_img = res.content;
        }, {max:max,min:min,goods_id:goods_id});


        //显示缺少的人数
        if (pin_content.pin_user_list.length>5){
          pin_content.pin_user_list = pin_content.pin_user_list.slice(0,5);
        }
        if (pin_content.pin_info.limit_amount > 5){
          pin_content.pin_info.show_noperson = 5 - pin_content.pin_user_list.length;
        }else{
          pin_content.pin_info.show_noperson = pin_content.pin_info.limit_amount - pin_content.pin_user_list.length;
        }

        //倒计时
        var pin_time_info = pin_content.remaining_time;
        var cancel_time = pin_time_info.hour * 60 + pin_time_info.minute;
        total_micro_second = cancel_time * 60 + pin_time_info.second;
        if (total_micro_second > 0) {
          that.countdown(that);
          that.setData({
            act_open: 1
          });
        }
        pin_content.goods_info['specs'] = pin_content.pin_specs;
        that._gd = pin_content.goods_info;

        that.setData({
          pin_content: pin_content,
          goods_img: pin_content.goods_info.goods_img,
          // spec_name: pin_content.pin_specs.spec_name,
          goods_name: pin_content.goods_info.goods_name,
          item_price: pin_content.goods_info.pin_shop_price,
          goods_num: pin_content.goods_info.goods_number,
          is_block: is_block
        })
      }


    }, {   group_id: group_id });
  },

  //规则详情
  toRule: function () {
    util.jumpToWeb('/wxapp/group/help')
  },

  toJoin:function(e){
    var group_type = e.currentTarget.dataset.group_type;
    if (group_type == 2 && pin_content.have_order_flag){
      this.old_customer();
    } else {
      this.OneClickBuy(e);
    }
  },
  //一键开团
  OneClickBuy: function (e) {
    this.not_show_customer();
    //判断是否要去绑定手机号
    if (is_bind_mobile == 1 && util.getCache('mobile') == '') {
      var that = this;
      util.checkSession(function () {
        that.setData({
          is_block: is_block = 1
        })
      })
      return false;
    }
    //获取规格id
    if (!this.checkSelBuy()) return;
    this.bindCloseSpec();
    //限购数量
    if (pin_content.goods_info.pin_goods_num == 0) {
      util.showModal('提示', '该商品库存不足，无法购买', function () { }, false)
    } else {
      var choose_list = this.getChooseList();
      util.navigateTo({
        url: "/pages/goodsCheckout/goodsCheckout?order_type=pin_group&choose_list=" + JSON.stringify(choose_list) + "&group_id=" + group_id
      })
    }
  },

  //倒计时
  countdown: function (that) {
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
    set_time_out = setTimeout(function () {
      // 放在最后--
      total_micro_second -= 1;
      that.countdown(that);
    }
      , 1000)
  },
  // 时间格式化输出，如3:25:19 86。每10ms都会调用一次
  dateformat: function (micro_second) {
    // 秒数
    var second = Math.floor(micro_second);
    // 小时位
    var hr = Math.floor(second / 3600);
    // 分钟位
    var min = Math.floor((second - hr * 60 * 60) / 60);
    // 秒位
    var sec = second % 60;
    return  hr + ':' + min + ":" + sec;
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

  /**
  * 用户点击右上角分享,立即分享
  */
  onShareAppMessage: function (res) {
    util.api("/api/wxapp/share/record", function (d) {

    }, { activity_id: group_id, activity_type: 1 });
    return {
      title: pin_content.pin_info.limit_amount + '人拼购仅需' + pin_content.pin_min_price + '元，' + pin_content.goods_info.goods_name,
      imageUrl: imageUrl + share_img,
      path: '/pages/groupbuyinfo/groupbuyinfo?group_id=' + group_id + '&pin_group_id=' + pin_content.pin_info.id + '&invite_id=' + util.getCache('user_id'),
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
    if(e.currentTarget.dataset.ct == 0){
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
    }, { action: 2,   goods_id: pin_content.goods_info.goods_id, group_id: group_id })
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

  openGroup: function () {
    util.navigateTo({
      url: '/pages/groupbuyitem/groupbuyitem?pin_group_id=' + pin_content.pin_info.id,
    })
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
    // choose_list['pin_group_id'] = pin_content.pin_info.id;
        // choose_list['group_id'] = group_id;
    return {
      goods_id: gd.goods_id,
      group_id: group_id,
      pin_group_id: this.pin_group_id,
      goods_price: s.has_spec ? s.goods_price : gd.shop_price,
      grade: gd.grade_price != -1 ? 1 : undefined,
      goods_number: s.goods_number,
      prd_sn: s.has_spec ? s.select_prd.prd_sn : gd.goods_sn,
      prd_id: s.has_spec ? s.select_prd.prd_id : gd.prd_id,
      product_id: s.has_spec ? s.select_prd.prd_id : gd.prd_id,
      user_id: util.getCache('user_id')
    };
  },
})
