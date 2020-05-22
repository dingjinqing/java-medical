// pages/cart/cart.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var cart_info = [];
var full_info = [];
var purchase_info = [];
var normal_info = [];
var invalid_goods = [];
// 数量加减时点击的商品的索引
var del_index;
var add_index;
var inp_index;
// 活动商品点击时对应的活动id，普通商品是0，其他的都是identity_id
var act_del_index;
var act_add_index;
var act_inp_index;
var act_flag;
// 商品选中与否切换时的商品索引
var change_goods_ids;
// 选中的商品数组
var normal_choosed_goods = [];
var full_choosed_goods = [];
var purchase_choosed_goods = [];

// 切换活动弹框要用到的数组
var pro_full_info = [];
var pro_purchase_info = [];
// 删除
var key = false;
var startX = 0;
var startY = 0;
var maxRight = 0;
var endX = 0;
var endY = 0;
// 绑定手机号
var is_bind_mobile;
var is_block = 0;
var reco_goods = [];
// 优惠券礼包
var pack_info = [];
var load_only ;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    cart_info:[],
    normal_info:[],
    full_info:[],
    purchase_info:[],
    proMode: true,
    couMode:true,
    pack_info:[],
    if_fullprice:0,
    if_purchase:0,
    post_act:'',
    cart_datas:'',
    gd_actoins:''
  },
  /**
 * 生命周期函数--监听页面加载
 */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    this.setData({
      options: options,
    })
    load_only = true;
    cart_request(that);
    // 计算最大maxRight
    var info = wx.getSystemInfoSync();
    var screen_height = wx.getSystemInfoSync().windowHeight;
    var screen_width = info.windowWidth;
    var pxPerRPX = screen_width / 750;
    maxRight = pxPerRPX * 120;
  },
  // 跳转商品详情页
  to_goods:function(e){
    var goods_id = e.currentTarget.dataset.goods_id;
    var tips = e.currentTarget.dataset.tips;
    if(!tips || tips!=undefined){
      util.navigateTo({
        url: '/pages/item/item?goods_id='+goods_id,
      })
    }
  },

  //删除商品
  good_del: function (e) {
    var that = this;
    var del_rec_id = e.currentTarget.dataset.del_id;
    var cart_data = {};
    cart_data.rec_id = del_rec_id;
    that.data.post_act = 'del_goods';
    that.data.cart_datas = JSON.stringify(cart_data);
    that.data.gd_actoins = '';
    cart_request(that);
  },
  // 切换促销活动
  proClcik: function (e) {
    var that = this;
    var this_idnexs = e.currentTarget.dataset.this_rec_id;
    var this_acts = e.currentTarget.dataset.acts;
    var this_act_id = e.currentTarget.dataset.act_id;
    var this_rec_id = e.currentTarget.dataset.rec_id;
    if (this_acts == 1){
      if (purchase_info.list[this_act_id].goods_list[this_idnexs].can_join_activity != undefined){
        pro_purchase_info = purchase_info.list[this_act_id].goods_list[this_idnexs].can_join_activity;
      }
    } else if (this_acts == 2){
      if (full_info.list[this_act_id].goods_list[this_idnexs].can_join_activity != undefined){
        pro_purchase_info = full_info.list[this_act_id].goods_list[this_idnexs].can_join_activity;
      }
    }else{
      if (normal_info.list[this_idnexs].can_join_activity != undefined){
        pro_purchase_info = normal_info.list[this_idnexs].can_join_activity;
      }
    }
    if (pro_purchase_info != ""){
      for (var i in pro_purchase_info) {
        pro_purchase_info[i].is_che = 0;
        pro_purchase_info[i].rec_id = this_rec_id;
      }
      pro_purchase_info[0].is_che = 1;
      this.setData({
        pro_purchase_info: pro_purchase_info
      })
    }
    this.setData({
      proMode: false,
    })
  },
  proCancel: function () {
    this.setData({
      proMode: true
    })
  },
  proActionSheetChange: function () {
    this.setData({
      proMode: true
    })
  },
  // 优惠券礼包
  to_each_package:function(e){
    var pack_id = e.currentTarget.dataset.pack_id;
    util.jumpLink('/pages1/couponpackage/couponpackage?pack_id=' + pack_id);
  },
  show_list:function(){
    this.setData({
      couMode: false
    })
  },
  couCancel: function () {
    this.setData({
      couMode: true
    })
  },
  couActionSheetChange: function () {
    this.setData({
      couMode: true
    })
  },
  // 选择活动
  choose_acts:function(e){
    var that = this;
    var this_iden_id = e.currentTarget.dataset.ids;
    var this_prd_id = e.currentTarget.dataset.prd_ids;
    var this_actx = e.currentTarget.dataset.actx;
    var this_indexs = e.currentTarget.dataset.indexs;
    var this_rec_id = e.currentTarget.dataset.rec_id;
    for (var i in pro_purchase_info) {
      pro_purchase_info[i].is_che = 0;
      pro_purchase_info[i].rec_id = this_rec_id;
    }
    pro_purchase_info[this_indexs].is_che = 1;
    that.setData({
      pro_purchase_info: pro_purchase_info,
    })
    var cart_data = {};
    cart_data.action = this_actx;
    cart_data.identity_id = this_iden_id;
    cart_data.product_id = this_prd_id;
    cart_data.rec_id = this_rec_id;
    that.data.gd_actoins = this_actx;
    that.data.post_act = 'switch_goods_action';
    that.data.cart_datas = JSON.stringify(cart_data);
    cart_request(that);
  },
  // 商品点击减号
  btn_del_each:function(e){
    var that = this;
    del_index = e.currentTarget.dataset.indexs;
    act_del_index = e.currentTarget.dataset.act_in;
    act_flag = e.currentTarget.dataset.act;
    if (act_flag == 0){
      var action = 0;
      var identity_id = 0;
      if (normal_info.list[del_index].goods_number < 1) {
        normal_info.list[del_index].goods_number = 1;
        normal_info.list[del_index].is_less = 1;
        that.setData({
          normal_info: normal_info
        })
        return false;
      }
      normal_info.list[del_index].goods_number = parseInt(normal_info.list[del_index].goods_number) - 1;
      if (normal_info.list[del_index].goods_number <= 1) {
        normal_info.list[del_index].goods_number = 1;
        normal_info.list[del_index].is_less = 1;
        that.setData({
          normal_info: normal_info
        })
      }
      if (normal_info.list[del_index].goods_number < 1) {
        normal_info.list[del_index].goods_number = 1;
        normal_info.list[del_index].is_less = 1;
      }
      var cart_data = {};
      cart_data.goods_number = normal_info.list[del_index].goods_number;
      cart_data.product_id = normal_info.list[del_index].product_id;
      cart_data.action = 0;
      cart_data.identity_id = 0;
    } else if (act_flag == 1){
      var action = 1;
      var identity_id = purchase_info.list[act_del_index].identity_id;
      if (purchase_info.list[act_del_index].goods_list[del_index].goods_number < 1) {
        purchase_info.list[act_del_index].goods_list[del_index].goods_number = 1;
        purchase_info.list[act_del_index].goods_list[del_index].is_less = 1;
        that.setData({
          purchase_info: purchase_info
        })
        return false;
      }
      purchase_info.list[act_del_index].goods_list[del_index].goods_number = parseInt(purchase_info.list[act_del_index].goods_list[del_index].goods_number) - 1;
      if (purchase_info.list[act_del_index].goods_list[del_index].goods_number <= 1) {
        purchase_info.list[act_del_index].goods_list[del_index].goods_number = 1;
        purchase_info.list[act_del_index].goods_list[del_index].is_less = 1;
        that.setData({
          purchase_info: purchase_info
        })
      }
      if (purchase_info.list[act_del_index].goods_list[del_index].goods_number < 1) {
        purchase_info.list[act_del_index].goods_list[del_index].goods_number = 1;
        purchase_info.list[act_del_index].goods_list[del_index].is_less = 1;
      }
      var cart_data = {};
      cart_data.goods_number = purchase_info.list[act_del_index].goods_list[del_index].goods_number;
      cart_data.product_id = purchase_info.list[act_del_index].goods_list[del_index].product_id;
      cart_data.action = 1;
      cart_data.identity_id = purchase_info.list[act_del_index].identity_id;
    }else{
      var action = 2;
      var identity_id = full_info.list[act_del_index].identity_id;
      if (full_info.list[act_del_index].goods_list[del_index].goods_number < 1) {
        full_info.list[act_del_index].goods_list[del_index].is_less = 1;
        full_info.list[act_del_index].goods_list[del_index].goods_number = 1;
        that.setData({
          full_info: full_info
        })
        return false;
      }
      full_info.list[act_del_index].goods_list[del_index].goods_number = parseInt(full_info.list[act_del_index].goods_list[del_index].goods_number) - 1;
      if (full_info.list[act_del_index].goods_list[del_index].goods_number <= 1) {
        full_info.list[act_del_index].goods_list[del_index].is_less = 1;
        full_info.list[act_del_index].goods_list[del_index].goods_number = 1;
        that.setData({
          full_info: full_info
        })
      }
      if (full_info.list[act_del_index].goods_list[del_index].goods_number < 1) {
        full_info.list[act_del_index].goods_list[del_index].is_less = 1;
        full_info.list[act_del_index].goods_list[del_index].goods_number = 1;
      }
      var cart_data = {};
      cart_data.goods_number = full_info.list[act_del_index].goods_list[del_index].goods_number;
      cart_data.product_id = full_info.list[act_del_index].goods_list[del_index].product_id;
      cart_data.action = 2;
      cart_data.identity_id = full_info.list[act_del_index].identity_id;
    }
    util.api('/api/wxapp/cart/newlist', function (res) {
      if (res.error == 0) {
        cart_info = res.content;
        if (res.content.list.coupon_pack) {
          pack_info = res.content.list.coupon_pack;
          that.setData({
            pack_info: pack_info
          })
        }
        is_block = 0
        is_bind_mobile = cart_info.is_bind_mobile;//是否要绑定手机号判断
        normal_info = res.content.list.general;
        for (var i = 0; i < normal_info.list.length; i++) {

          if (parseFloat(normal_info.list[i].cart_goods_price) > parseFloat(normal_info.list[i].goods_price)) {
            normal_info.list[i].less_money = parseFloat(normal_info.list[i].cart_goods_price) - parseFloat(normal_info.list[i].goods_price);
            normal_info.list[i].less_money = parseFloat(normal_info.list[i].less_money).toFixed(2);
          } else {
            normal_info.list[i].less_money = 0
          }
          normal_info.list[i].is_less = 0;
          normal_info.list[i].is_most = 0;
          if (normal_info.list[i].goods_number == 1) {
            normal_info.list[i].is_less = 1;
          }
          
          if (normal_info.list[i].can_join_activity != "") {
            for (var z in normal_info.list[i].can_join_activity) {
              if (normal_info.list[i].can_join_activity[z].action == 2) {
                that.data.if_fullprice = 1;
                that.setData({
                  if_fullprice: that.data.if_fullprice
                })
              } else if (normal_info.list[i].can_join_activity[z].action == 1) {
                that.data.if_purchase = 1;
                that.setData({
                  if_purchase: that.data.if_purchase
                })
              }
            }
          }
        }
        full_info = res.content.list.full_price;
        for (var i in full_info.list) {
          for (var j in full_info.list[i].goods_list) {
            if (parseFloat(full_info.list[i].goods_list[j].cart_goods_price) > parseFloat(full_info.list[i].goods_list[j].goods_price)) {
              full_info.list[i].goods_list[j].less_money = parseFloat(full_info.list[i].goods_list[j].cart_goods_price) - parseFloat(full_info.list[i].goods_list[j].goods_price);
              full_info.list[i].goods_list[j].less_money = parseFloat(full_info.list[i].goods_list[j].less_money).toFixed(2);
            } else {
              full_info.list[i].goods_list[j].less_money = 0
            }
            full_info.list[i].goods_list[j].is_less = 0;
            full_info.list[i].goods_list[j].is_most = 0;
            if (full_info.list[i].goods_list[j].goods_number == 1) {
              full_info.list[i].goods_list[j].is_less = 1;
            }
            
            if (full_info.list[i].goods_list[j].can_join_activity != "") {
              for (var z in full_info.list[i].goods_list[j].can_join_activity) {
                if (full_info.list[i].goods_list[j].can_join_activity[z].action == 2) {
                  that.data.if_fullprice = 1;
                  that.setData({
                    if_fullprice: that.data.if_fullprice
                  })
                } else if (full_info.list[i].goods_list[j].can_join_activity[z].action == 1) {
                  that.data.if_purchase = 1;
                  that.setData({
                    if_purchase: that.data.if_purchase
                  })
                }
              }
            }
          }
        }
        purchase_info = res.content.list.purchase_price;
        for (var i in purchase_info.list) {
          for (var j in purchase_info.list[i].goods_list) {
            if (parseFloat(purchase_info.list[i].goods_list[j].cart_goods_price) > parseFloat(purchase_info.list[i].goods_list[j].goods_price)) {
              purchase_info.list[i].goods_list[j].less_money = parseFloat(purchase_info.list[i].goods_list[j].cart_goods_price) - parseFloat(purchase_info.list[i].goods_list[j].goods_price);
              purchase_info.list[i].goods_list[j].less_money = parseFloat(purchase_info.list[i].goods_list[j].less_money).toFixed(2);
            } else {
              purchase_info.list[i].goods_list[j].less_money = 0
            }
            purchase_info.list[i].goods_list[j].is_less = 0;
            purchase_info.list[i].goods_list[j].is_most = 0;
            if (purchase_info.list[i].goods_list[j].goods_number == 1) {
              purchase_info.list[i].goods_list[j].is_less = 1;
            }
            
            if (purchase_info.list[i].goods_list[j].can_join_activity != "") {
              for (var z in purchase_info.list[i].goods_list[j].can_join_activity) {
                if (purchase_info.list[i].goods_list[j].can_join_activity[z].action == 2) {
                  that.data.if_fullprice = 1;
                  that.setData({
                    if_fullprice: that.data.if_fullprice
                  })
                } else if (purchase_info.list[i].goods_list[j].can_join_activity[z].action == 1) {
                  that.data.if_purchase = 1;
                  that.setData({
                    if_purchase: that.data.if_purchase
                  })
                }
              }
            }
          }
        }
        if (act_flag == 0){
          that.setData({
            cart_info: cart_info,
            normal_info: normal_info
          })
        } else if (act_flag == 1) {
          that.setData({
            cart_info: cart_info,
            purchase_info: purchase_info
          })
        } else {
          that.setData({
            cart_info: cart_info,
            full_info: full_info
          })
        }
        that.setData({
          is_block:is_block
        })

      } else if (res.error == 10) {
        if (act_flag == 0) {
          normal_info.list[del_index].is_less = 1;
          normal_info.list[del_index].goods_number = normal_info.list[del_index].goods_number + 1;
          util.showModal("提示", res.message);
          that.setData({
            normal_info: normal_info
          })
          return false;
        } else if (act_flag == 1) {
          purchase_info.list[act_del_index].goods_list[del_index].is_less = 1;
          purchase_info.list[act_del_index].goods_list[del_index].goods_number = purchase_info.list[act_del_index].goods_list[del_index].goods_number + 1;
          util.showModal("提示", res.message);
          that.setData({
            purchase_info: purchase_info
          })
          return false;
        } else {
          full_info.list[act_del_index].goods_list[del_index].is_less = 1;
          full_info.list[act_del_index].goods_list[del_index].goods_number = full_info.list[act_del_index].goods_list[del_index].goods_number + 1;
          util.showModal("提示", res.message);
          that.setData({
            full_info: full_info
          })
          return false;
        }
      } else {
        util.showModal("提示", res.message);
        return false;
      }
    }, { post_action: 'change_goods_number', btn_click: 0,cart_data: JSON.stringify(cart_data), options: JSON.stringify(that.data.options) },'',false,);
  },
  // 商品点击加号
  btn_add_each:function(e){
    var that = this;
    add_index = e.currentTarget.dataset.indexs;
    act_add_index = e.currentTarget.dataset.act_in;
    act_flag = e.currentTarget.dataset.act;
    if (act_flag == 0) {
      var action = 0;
      var identity_id = 0;
      normal_info.list[add_index].goods_number = parseInt(normal_info.list[add_index].goods_number) + 1;
      normal_info.list[add_index].is_less = 0;
      var cart_data = {};
      cart_data.goods_number = normal_info.list[add_index].goods_number;
      cart_data.product_id = normal_info.list[add_index].product_id;
      cart_data.action = 0;
      cart_data.identity_id = 0;
    } else if (act_flag == 1) {
      var action = 1;
      var identity_id = purchase_info.list[act_add_index].identity_id;
      purchase_info.list[act_add_index].goods_list[add_index].goods_number = parseInt(purchase_info.list[act_add_index].goods_list[add_index].goods_number) + 1;
      purchase_info.list[act_add_index].goods_list[add_index].is_less = 0;
      var cart_data = {};
      cart_data.goods_number = purchase_info.list[act_add_index].goods_list[add_index].goods_number;
      cart_data.product_id = purchase_info.list[act_add_index].goods_list[add_index].product_id;
      cart_data.action = 1;
      cart_data.identity_id = purchase_info.list[act_add_index].identity_id;
    } else {
      var action = 2;
      var identity_id = full_info.list[act_add_index].identity_id;
      full_info.list[act_add_index].goods_list[add_index].goods_number = parseInt(full_info.list[act_add_index].goods_list[add_index].goods_number) + 1;
      full_info.list[act_add_index].goods_list[add_index].is_less = 0;
      var cart_data = {};
      cart_data.goods_number = full_info.list[act_add_index].goods_list[add_index].goods_number;
      cart_data.product_id = full_info.list[act_add_index].goods_list[add_index].product_id;
      cart_data.action = 2;
      cart_data.identity_id = full_info.list[act_add_index].identity_id;
    }
    util.api('/api/wxapp/cart/newlist', function (res) {
      if (res.error == 0) {
        cart_info = res.content;
        if (res.content.list.coupon_pack) {
          pack_info = res.content.list.coupon_pack;
          that.setData({
            pack_info: pack_info
          })
        }
        is_block = 0
        is_bind_mobile = cart_info.is_bind_mobile;//是否要绑定手机号判断
        normal_info = res.content.list.general;
        for (var i = 0; i < normal_info.list.length; i++) {
          if (parseFloat(normal_info.list[i].cart_goods_price) > parseFloat(normal_info.list[i].goods_price)) {
            normal_info.list[i].less_money = parseFloat(normal_info.list[i].cart_goods_price) - parseFloat(normal_info.list[i].goods_price);
            normal_info.list[i].less_money = parseFloat(normal_info.list[i].less_money).toFixed(2);
          } else {
            normal_info.list[i].less_money = 0
          }
          normal_info.list[i].is_less = 0;
          normal_info.list[i].is_most = 0;
          if (normal_info.list[i].goods_number == 1) {
            normal_info.list[i].is_less = 1;
          }
         
          if (normal_info.list[i].can_join_activity != "") {
            for (var z in normal_info.list[i].can_join_activity) {
              if (normal_info.list[i].can_join_activity[z].action == 2) {
                that.data.if_fullprice = 1;
                that.setData({
                  if_fullprice: that.data.if_fullprice
                })
              } else if (normal_info.list[i].can_join_activity[z].action == 1) {
                that.data.if_purchase = 1;
                that.setData({
                  if_purchase: that.data.if_purchase
                })
              }
            }
          }
        }
        full_info = res.content.list.full_price;
        for (var i in full_info.list) {
          for (var j in full_info.list[i].goods_list) {
            if (parseFloat(full_info.list[i].goods_list[j].cart_goods_price) > parseFloat(full_info.list[i].goods_list[j].goods_price)) {
              full_info.list[i].goods_list[j].less_money = parseFloat(full_info.list[i].goods_list[j].cart_goods_price) - parseFloat(full_info.list[i].goods_list[j].goods_price);
              full_info.list[i].goods_list[j].less_money = parseFloat(full_info.list[i].goods_list[j].less_money).toFixed(2);
            } else {
              full_info.list[i].goods_list[j].less_money = 0
            }
            full_info.list[i].goods_list[j].is_less = 0;
            full_info.list[i].goods_list[j].is_most = 0;
            if (full_info.list[i].goods_list[j].goods_number == 1) {
              full_info.list[i].goods_list[j].is_less = 1;
            }
           
            if (full_info.list[i].goods_list[j].can_join_activity != "") {
              for (var z in full_info.list[i].goods_list[j].can_join_activity) {
                if (full_info.list[i].goods_list[j].can_join_activity[z].action == 2) {
                  that.data.if_fullprice = 1;
                  that.setData({
                    if_fullprice: that.data.if_fullprice
                  })
                } else if (full_info.list[i].goods_list[j].can_join_activity[z].action == 1) {
                  that.data.if_purchase = 1;
                  that.setData({
                    if_purchase: that.data.if_purchase
                  })
                }
              }
            }
          }
        }
        purchase_info = res.content.list.purchase_price;
        for (var i in purchase_info.list) {
          for (var j in purchase_info.list[i].goods_list) {
            if (parseFloat(purchase_info.list[i].goods_list[j].cart_goods_price) > parseFloat(purchase_info.list[i].goods_list[j].goods_price)) {
              purchase_info.list[i].goods_list[j].less_money = parseFloat(purchase_info.list[i].goods_list[j].cart_goods_price) - parseFloat(purchase_info.list[i].goods_list[j].goods_price);
              purchase_info.list[i].goods_list[j].less_money = parseFloat(purchase_info.list[i].goods_list[j].less_money).toFixed(2);
            } else {
              purchase_info.list[i].goods_list[j].less_money = 0
            }
            purchase_info.list[i].goods_list[j].is_less = 0;
            purchase_info.list[i].goods_list[j].is_most = 0;
            if (purchase_info.list[i].goods_list[j].goods_number == 1) {
              purchase_info.list[i].goods_list[j].is_less = 1;
            }
            
            if (purchase_info.list[i].goods_list[j].can_join_activity != "") {
              for (var z in purchase_info.list[i].goods_list[j].can_join_activity) {
                if (purchase_info.list[i].goods_list[j].can_join_activity[z].action == 2) {
                  that.data.if_fullprice = 1;
                  that.setData({
                    if_fullprice: that.data.if_fullprice
                  })
                } else if (purchase_info.list[i].goods_list[j].can_join_activity[z].action == 1) {
                  that.data.if_purchase = 1;
                  that.setData({
                    if_purchase: that.data.if_purchase
                  })
                }
              }
            }
          }
        }
        if (act_flag == 0) {
          that.setData({
            cart_info: cart_info,
            normal_info: normal_info
          })
        } else if (act_flag == 1) {
          that.setData({
            cart_info: cart_info,
            purchase_info: purchase_info
          })
        } else {
          that.setData({
            cart_info: cart_info,
            full_info: full_info
          })
        }
        that.setData({
          is_block:is_block
        })
      } else if (res.error == 10){
        if (act_flag == 0) {
          normal_info.list[add_index].is_most = 1;
          normal_info.list[add_index].goods_number = normal_info.list[add_index].goods_number - 1;
          util.showModal("提示", res.message);
          that.setData({
            normal_info: normal_info
          })
          return false;
        } else if (act_flag == 1) {
          purchase_info.list[act_add_index].goods_list[add_index].is_most = 1;
          purchase_info.list[act_add_index].goods_list[add_index].goods_number = purchase_info.list[act_add_index].goods_list[add_index].goods_number - 1;
          util.showModal("提示", res.message);
          that.setData({
            purchase_info: purchase_info
          })
          return false;
        } else {
          full_info.list[act_add_index].goods_list[add_index].is_most = 1;
          full_info.list[act_add_index].goods_list[add_index].goods_number = full_info.list[act_add_index].goods_list[add_index].goods_number - 1;
          util.showModal("提示", res.message);
          that.setData({
            full_info: full_info
          })
          return false;
        }
      }else {
        util.showModal("提示", res.message);
        return false;
      }
    }, { post_action: 'change_goods_number',btn_click:1, cart_data: JSON.stringify(cart_data), options: JSON.stringify(that.data.options) }, '', false);
  },
  // 商品输入框
  inp_each:function(e){
    var that = this;
    inp_index = e.currentTarget.dataset.indexs;
    act_add_index = e.currentTarget.dataset.act_in;
    act_flag = e.currentTarget.dataset.act;
    if (act_flag == 0) {
      var action = 0;
      var identity_id = 0;
      normal_info.list[inp_index].goods_number = e.detail.value;
      var cart_data = {};
      cart_data.goods_number = normal_info.list[inp_index].goods_number;
      cart_data.product_id = normal_info.list[inp_index].product_id;
      cart_data.action = 0;
      cart_data.identity_id = 0;
    } else if (act_flag == 1) {
      var action = 1;
      var identity_id = purchase_info.list[act_add_index].identity_id;
      purchase_info.list[act_add_index].goods_list[inp_index].goods_number = e.detail.value;
      var cart_data = {};
      cart_data.goods_number = purchase_info.list[act_add_index].goods_list[inp_index].goods_number;
      cart_data.product_id = purchase_info.list[act_add_index].goods_list[inp_index].product_id;
      cart_data.action = 1;
      cart_data.identity_id = purchase_info.list[act_add_index].identity_id;
    } else {
      var action = 2;
      var identity_id = full_info.list[act_add_index].identity_id;
      full_info.list[act_add_index].goods_list[inp_index].goods_number = e.detail.value;
      var cart_data = {};
      cart_data.goods_number = full_info.list[act_add_index].goods_list[inp_index].goods_number;
      cart_data.product_id = full_info.list[act_add_index].goods_list[inp_index].product_id;
      cart_data.action = 2;
      cart_data.identity_id = full_info.list[act_add_index].identity_id;
    }
    util.api('/api/wxapp/cart/newlist', function (res) {
      if (res.error == 0) {
        cart_info = res.content;
        if (res.content.list.coupon_pack) {
          pack_info = res.content.list.coupon_pack;
          that.setData({
            pack_info: pack_info
          })
        }
        is_block = 0
        is_bind_mobile = cart_info.is_bind_mobile;//是否要绑定手机号判断
        normal_info = res.content.list.general;
        for (var i = 0; i < normal_info.list.length; i++) {
          if (parseFloat(normal_info.list[i].cart_goods_price) > parseFloat(normal_info.list[i].goods_price)) {
            normal_info.list[i].less_money = parseFloat(normal_info.list[i].cart_goods_price) - parseFloat(normal_info.list[i].goods_price);
            normal_info.list[i].less_money = parseFloat(normal_info.list[i].less_money).toFixed(2);
          } else {
            normal_info.list[i].less_money = 0
          }
          normal_info.list[i].is_less = 0;
          normal_info.list[i].is_most = 0;
          if (normal_info.list[i].goods_number == 1) {
            normal_info.list[i].is_less = 1;
          }
          
          if (normal_info.list[i].can_join_activity != "") {
            for (var z in normal_info.list[i].can_join_activity) {
              if (normal_info.list[i].can_join_activity[z].action == 2) {
                that.data.if_fullprice = 1;
                that.setData({
                  if_fullprice: that.data.if_fullprice
                })
              } else if (normal_info.list[i].can_join_activity[z].action == 1) {
                that.data.if_purchase = 1;
                that.setData({
                  if_purchase: that.data.if_purchase
                })
              }
            }
          }
        }
        full_info = res.content.list.full_price;
        for (var i in full_info.list) {
          for (var j in full_info.list[i].goods_list) {
            if (parseFloat(full_info.list[i].goods_list[j].cart_goods_price) > parseFloat(full_info.list[i].goods_list[j].goods_price)) {
              full_info.list[i].goods_list[j].less_money = parseFloat(full_info.list[i].goods_list[j].cart_goods_price) - parseFloat(full_info.list[i].goods_list[j].goods_price);
              full_info.list[i].goods_list[j].less_money = parseFloat(full_info.list[i].goods_list[j].less_money).toFixed(2);
            } else {
              full_info.list[i].goods_list[j].less_money = 0
            }
            full_info.list[i].goods_list[j].is_less = 0;
            full_info.list[i].goods_list[j].is_most = 0;
            if (full_info.list[i].goods_list[j].goods_number == 1) {
              full_info.list[i].goods_list[j].is_less = 1;
            }
            
            if (full_info.list[i].goods_list[j].can_join_activity != "") {
              for (var z in full_info.list[i].goods_list[j].can_join_activity) {
                if (full_info.list[i].goods_list[j].can_join_activity[z].action == 2) {
                  that.data.if_fullprice = 1;
                  that.setData({
                    if_fullprice: that.data.if_fullprice
                  })
                } else if (full_info.list[i].goods_list[j].can_join_activity[z].action == 1) {
                  that.data.if_purchase = 1;
                  that.setData({
                    if_purchase: that.data.if_purchase
                  })
                }
              }
            }
          }
        }
        purchase_info = res.content.list.purchase_price;
        for (var i in purchase_info.list) {
          for (var j in purchase_info.list[i].goods_list) {
            if (parseFloat(purchase_info.list[i].goods_list[j].cart_goods_price) > parseFloat(purchase_info.list[i].goods_list[j].goods_price)) {
              purchase_info.list[i].goods_list[j].less_money = parseFloat(purchase_info.list[i].goods_list[j].cart_goods_price) - parseFloat(purchase_info.list[i].goods_list[j].goods_price);
              purchase_info.list[i].goods_list[j].less_money = parseFloat(purchase_info.list[i].goods_list[j].less_money).toFixed(2);
            } else {
              purchase_info.list[i].goods_list[j].less_money = 0
            }
            purchase_info.list[i].goods_list[j].is_less = 0;
            purchase_info.list[i].goods_list[j].is_most = 0;
            if (purchase_info.list[i].goods_list[j].goods_number == 1) {
              purchase_info.list[i].goods_list[j].is_less = 1;
            }
            
            if (purchase_info.list[i].goods_list[j].can_join_activity != "") {
              for (var z in purchase_info.list[i].goods_list[j].can_join_activity) {
                if (purchase_info.list[i].goods_list[j].can_join_activity[z].action == 2) {
                  that.data.if_fullprice = 1;
                  that.setData({
                    if_fullprice: that.data.if_fullprice
                  })
                } else if (purchase_info.list[i].goods_list[j].can_join_activity[z].action == 1) {
                  that.data.if_purchase = 1;
                  that.setData({
                    if_purchase: that.data.if_purchase
                  })
                }
              }
            }
          }
        }
        if (act_flag == 0) {
          that.setData({
            cart_info: cart_info,
            normal_info: normal_info
          })
        } else if (act_flag == 1) {
          that.setData({
            cart_info: cart_info,
            purchase_info: purchase_info
          })
        } else {
          that.setData({
            cart_info: cart_info,
            full_info: full_info
          })
        }
        that.setData({
          is_block:is_block
        })
      } else if (res.error == 10) {
        if (act_flag == 0) {
          normal_info.list[inp_index].is_most = 1;
          normal_info.list[inp_index].goods_number = normal_info.list[inp_index].prd_number;
          util.showModal("提示", res.message, function () { that.onPullDownRefresh(); });
          that.setData({
            normal_info: normal_info
          })
          return false;
        } else if (act_flag == 1) {
          purchase_info.list[act_add_index].goods_list[inp_index].is_most = 1;
          purchase_info.list[act_add_index].goods_list[inp_index].goods_number = purchase_info.list[act_add_index].goods_list[inp_index].prd_number;
          util.showModal("提示", res.message, function () { that.onPullDownRefresh(); });
          that.setData({
            purchase_info: purchase_info
          })
          return false;
        } else {
          full_info.list[act_add_index].goods_list[inp_index].is_most = 1;
          full_info.list[act_add_index].goods_list[inp_index].goods_number = full_info.list[act_add_index].goods_list[inp_index].prd_number;
          util.showModal("提示", res.message, function () { that.onPullDownRefresh();});
          that.setData({
            full_info: full_info
          })
          return false;
        }
      } else {
        util.showModal("提示", res.message);
        return false;
      }
    }, { post_action: 'change_goods_number',btn_click:1,cart_data: JSON.stringify(cart_data), options: JSON.stringify(that.data.options) }, '', true);
  },
  // 切换商品
  check_single_goods:function(e){
    var that = this;
    change_goods_ids = e.currentTarget.dataset.keys;
    var index_big = e.currentTarget.dataset.index_big;
    act_flag = e.currentTarget.dataset.act;
    if(act_flag == 0){
      if (normal_info.list[change_goods_ids].can_buy != undefined && normal_info.list[change_goods_ids].can_buy == 0) {
        util.showModal("提示", "此商品限购");
        return false;
      }
      if (normal_info.list[change_goods_ids].is_checked == 0){
        normal_info.list[change_goods_ids].is_checked = 1;
        normal_choosed_goods.push(normal_info.list[change_goods_ids].rec_id);
      } else {
        normal_info.list[change_goods_ids].is_checked = 0;
        if (normal_choosed_goods.indexOf(normal_info.list[change_goods_ids].rec_id)>-1){
          normal_choosed_goods.splice(normal_choosed_goods.indexOf(normal_info.list[change_goods_ids].rec_id),1);
        }
      }
      var cart_data = {};
      cart_data.rec_id = normal_choosed_goods.join(",");
    } else if (act_flag == 1){
      if (purchase_info.list[index_big].goods_list[change_goods_ids].can_buy != undefined && purchase_info.list[index_big].goods_list[change_goods_ids].can_buy == 0) {
        util.showModal("提示", "此商品限购");
        return false;
      }
      if (purchase_info.list[index_big].goods_list[change_goods_ids].is_checked == 0) {
        purchase_info.list[index_big].goods_list[change_goods_ids].is_checked = 1;
        normal_choosed_goods.push(purchase_info.list[index_big].goods_list[change_goods_ids].rec_id);
      } else {
        purchase_info.list[index_big].goods_list[change_goods_ids].is_checked = 0;
        if (normal_choosed_goods.indexOf(purchase_info.list[index_big].goods_list[change_goods_ids].rec_id) > -1) {
          normal_choosed_goods.splice(normal_choosed_goods.indexOf(purchase_info.list[index_big].goods_list[change_goods_ids].rec_id), 1);
        }
      }
      var cart_data = {};
      cart_data.rec_id = normal_choosed_goods.join(",");
    }else{
      if (full_info.list[index_big].goods_list[change_goods_ids].can_buy != undefined && full_info.list[index_big].goods_list[change_goods_ids].can_buy == 0) {
        util.showModal("提示", "此商品限购");
        return false;
      }
      if (full_info.list[index_big].is_can_buy == 0 && full_info.list[index_big].is_exclusive == 1){
        util.showModal("提示","此活动为会员专属，开通即可参与！");
        return false;
      }
      if (full_info.list[index_big].goods_list[change_goods_ids].is_checked == 0) {
        full_info.list[index_big].goods_list[change_goods_ids].is_checked = 1;
        normal_choosed_goods.push(full_info.list[index_big].goods_list[change_goods_ids].rec_id);
      } else {
        full_info.list[index_big].goods_list[change_goods_ids].is_checked = 0;
        if (normal_choosed_goods.indexOf(full_info.list[index_big].goods_list[change_goods_ids].rec_id) > -1) {
          normal_choosed_goods.splice(normal_choosed_goods.indexOf(full_info.list[index_big].goods_list[change_goods_ids].rec_id), 1);
        }
      }
      var cart_data = {};
      cart_data.rec_id = normal_choosed_goods.join(",");
    }
    that.data.post_act = 'checked_goods';
    that.data.cart_datas = JSON.stringify(cart_data);
    that.data.gd_actoins = '';
    cart_request(that);
  },
  // 全选和不全选
  all_choose:function(){
    var that = this;
    if (cart_info.list.is_all_checked == 0){
      normal_choosed_goods = [];
      cart_info.list.is_all_checked = 1;
      for(var i in normal_info.list){
        normal_info.list[i].is_checked = 1
        normal_choosed_goods.push(normal_info.list[i].rec_id);
      }
      for (var i in full_info.list) {
        for (var j in full_info.list[i].goods_list) {
          full_info.list[i].goods_list[j].is_checked = 1;
          normal_choosed_goods.push(full_info.list[i].goods_list[j].rec_id);
        }
      }
      for (var i in purchase_info.list) {
        for (var j in purchase_info.list[i].goods_list) {
          if (purchase_info.list[i].goods_list[j].extend_id == 0){
            purchase_info.list[i].goods_list[j].is_checked = 1;
            normal_choosed_goods.push(purchase_info.list[i].goods_list[j].rec_id);
          }
        }
      }
      var cart_data = {};
      cart_data.rec_id = normal_choosed_goods.join(",");
    }else{
      cart_info.list.is_all_checked = 0;
      for (var i in normal_info.list) {
        normal_info.list[i].is_checked = 0
      }
      for (var i in full_info.list) {
        for (var j in full_info.list[i].goods_list) {
          full_info.list[i].goods_list[j].is_checked = 0;
        }
      }
      for (var i in purchase_info.list) {
        for (var j in purchase_info.list[i].goods_list) {
          purchase_info.list[i].goods_list[j].is_checked = 0;
        }
      }
      var cart_data = {};
      normal_choosed_goods = [];
      cart_data.rec_id = '';
    }
    that.data.post_act = 'checked_goods';
    that.data.cart_datas = JSON.stringify(cart_data);
    that.data.gd_actoins = '';
    cart_request(that);
  },
  // 跳转满折满减商品
  to_fullpage:function(e){
    var iden_id = e.currentTarget.dataset.ids;
    var store_id = this.data.options.store_id ? this.data.options.store_id : 0;
    util.navigateTo({
      url: '/pages/fullprice/fullprice?identity_id=' + iden_id + '&store_id=' + store_id,
    })
  },
  // 跳转加价购商品列表
  to_purchase:function(e){
    var iden_id = e.currentTarget.dataset.ids;
    var store_id = this.data.options.store_id ? this.data.options.store_id : 0;
    util.navigateTo({
      url: '/pages/maingoodslist/maingoodslist?identity_id=' + iden_id + '&store_id=' + store_id,
    })
  },
  // 跳转到秒杀商品
  to_seckill:function(e){
    var sec_id = e.currentTarget.dataset.seckill_id;
    util.navigateTo({
      url: "/pages/seckillitem/seckillitem?sk_id=" + sec_id
    })
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    // 显示导航栏loading
    wx.showNavigationBarLoading();
    // 调用接口加载数据
    var that = this;
    cart_request(that);
    wx.hideNavigationBarLoading();
    // 当处理完数据刷新后，wx.stopPullDownRefresh可以停止当前页面的下拉刷新
    wx.stopPullDownRefresh();
  },

  onShow:function(){
    console.log('woshionshow')
    if(load_only == false){
      cart_request(this);
    }else{
      load_only = false;
    }
  
  },

  // 删除的效果
  drawStart: function (e) {
    var dataId = e.currentTarget.dataset.id;
    var touch = e.changedTouches[0];
    var check_action = e.currentTarget.dataset.check_action;
    if (check_action == 0) {
      var cardTeams = normal_info.list;
    } else if (check_action == 1) {
      var activity_ids = e.currentTarget.dataset.activity_ids;
      var cardTeams = purchase_info.list[activity_ids].goods_list;
    } else if (check_action == 2) {
      var activity_ids = e.currentTarget.dataset.activity_ids;
      var cardTeams = full_info.list[activity_ids].goods_list;
    } else if (check_action == 99) {
      var cardTeams = invalid_goods.list;
    }
    for (var i in cardTeams) {
      var data = cardTeams[i];
      if (data.rec_id == dataId) {
        data.startX = touch.clientX;
        data.startY = touch.clientY;
        data.lastX = touch.clientX;
      }
    }
    this.direction = 0;  // 0 初始化,1 上下 2 左右
  },
  drawEnd: function (e) {
    if (this.direction != 2) {
      this.direction = 0;
      return;
    }
    this.direction = 0;
    var dataId = e.currentTarget.dataset.id;
    var touch = e.changedTouches[0];
    var check_action = e.currentTarget.dataset.check_action;
    if (check_action == 0) {
      var cardTeams = normal_info.list;
    } else if (check_action == 1) {
      var activity_ids = e.currentTarget.dataset.activity_ids;
      var cardTeams = purchase_info.list[activity_ids].goods_list;
    } else if (check_action == 2) {
      var activity_ids = e.currentTarget.dataset.activity_ids;
      var cardTeams = full_info.list[activity_ids].goods_list;
    } else if (check_action == 99) {
      var cardTeams = invalid_goods.list;
    }
    for (var i in cardTeams) {
      var data = cardTeams[i];
      if (data.rec_id == dataId) {
        if (data.startX > touch.clientX) {
          data.right = -1 * maxRight;
        } else {
          data.right = 0;
        }
      }
    }
    this.setData({
      normal_info: normal_info,
      full_info: full_info,
      purchase_info: purchase_info,
      invalid_goods: invalid_goods
    });
  },
  drawMove: function (e) {
    var dataId = e.currentTarget.dataset.id;
    var touch = e.changedTouches[0];
    var check_action = e.currentTarget.dataset.check_action;
    if (check_action == 0) {
      var cardTeams = normal_info.list;
    } else if (check_action == 1) {
      var activity_ids = e.currentTarget.dataset.activity_ids;
      var cardTeams = purchase_info.list[activity_ids].goods_list;
    } else if (check_action == 2) {
      var activity_ids = e.currentTarget.dataset.activity_ids;
      var cardTeams = full_info.list[activity_ids].goods_list;
    } else if (check_action == 99) {
      var cardTeams = invalid_goods.list;
    }
    for (var i in cardTeams) {
      var data = cardTeams[i];
      if (data.rec_id == dataId) {
        if (this.direction == 0) {
          var x = Math.abs(data.startX - touch.clientX);
          var y = Math.abs(data.startY - touch.clientY);
          if (x > y && x > 5) {
            this.direction = 2; // 左右移动距离超过5px，且大于上下移动距离，则为左右移动方向
          }
          if (x < y && y > 5) {
            this.direction = 1;
          }
        }
        if (this.direction == 1 || this.direction == 0) {
          continue;
        }

        var r = parseFloat(data.right);
        if (isNaN(r)) r = 0.0;
        var tmp = data.lastX - touch.clientX;
        data.lastX = touch.clientX;
        if (tmp > 0) {
          // 左移
          if (-r == maxRight) continue;
          data.right = -1 * (-r + tmp);
          if (- data.right > maxRight) data.right = -maxRight;
        } else {
          if (r == 0) continue;
          data.right = -1 * (-r + tmp);
          if (- data.right < 0) data.right = 0;
        }
      }
    }
    this.setData({
      normal_info: normal_info,
      full_info: full_info,
      purchase_info: purchase_info,
      invalid_goods: invalid_goods
    });
  },
  // 去结算
  to_checked: function () {
    var that = this;
    if (is_bind_mobile == 1 && util.getCache('mobile') == '') {
      util.checkSession(function () {
        that.setData({
          is_block: is_block = 1
        })
      })
      return false;
    }
    var choose_list  = {
      scan_store_id: that.data.options.store_id ? that.data.options.store_id : 0
    };
    if (normal_choosed_goods == ""){
      util.showModal("提示","请选择您想要购买的商品");
      return false;
    }
    util.navigateTo({
      url: '/pages/goodsCheckout/goodsCheckout?order_type=cart&choose_list=' + JSON.stringify(choose_list) ,
    })
  },

  // huidai首页
  go_index: function () {
    util.reLaunch({
      url: '/pages/index/index',
    })
  },
  // 扫码购
  scanBuy: function (e) {
    var store_id = this.data.options.store_id ? this.data.options.store_id : 0;
    var that = this;
    wx.scanCode({
      onlyFromCamera: true,
      scanType: ['barCode'],
      success: res => {
        util.api('/api/wxapp/store/cart/add', function (reponse) {
          if (reponse.error == 0) {
            util.navigateTo({
              url: '/pages/cart/cart?store_id=' + id,
            })
            cart_request(that)
          } else {
            util.showModal('提示', reponse.content);
          }

        }, { store_id: store_id, scan_code: res.result }, '', true)
       },
      fail: res => {
        util.showModal('提示', '扫码失败');
      },
      complete: res => { }
    })
  },
  // 移除无效商品
  remove_invalid_goods: function () {
    var that = this;
    util.showModal('提示', '是否清除全部无效商品', function () {
      that.data.post_act = 'remove_invalid_goods';
      that.data.cart_datas = '';
      that.data.gd_actoins = '';
      that.data.options = '';
      cart_request(that);
    },true,"取消","确定");
  },
  card_exclusive(){
    util.showModal('提示', '该商品为会员专享商品，请查看是否拥有可使用或已激活的此商品会员卡', function () {
      util.jumpLink('pages/usercardlist/usercardlist', 'navigateTo')
    }, true, '取消', '去查看')
  }
})
function cart_request(that){
  util.api('/api/wxapp/cart/newlist', function (res) {
    normal_choosed_goods = [];
    if (res.error == 0) {
      cart_info = res.content;
      if(res.content.list.coupon_pack){
        pack_info = res.content.list.coupon_pack;
      }
      is_block = 0
      is_bind_mobile = cart_info.is_bind_mobile;//是否要绑定手机号判断
      normal_info = res.content.list.general;
      invalid_goods = res.content.list.invalid_goods;

      for (var i = 0; i < normal_info.list.length;i++){
        if (normal_info.list[i].is_checked == 1 && !normal_info.list[i].tip){
          normal_choosed_goods.push(normal_info.list[i].rec_id);
        }
        if (parseFloat(normal_info.list[i].cart_goods_price) > parseFloat(normal_info.list[i].goods_price)) {
          normal_info.list[i].less_money = parseFloat(normal_info.list[i].cart_goods_price) - parseFloat(normal_info.list[i].goods_price);
          normal_info.list[i].less_money = parseFloat(normal_info.list[i].less_money).toFixed(2);
        } else {
          normal_info.list[i].less_money = 0
        }
        normal_info.list[i].is_less = 0;
        normal_info.list[i].is_most = 0;
        if (normal_info.list[i].goods_number == 1){
          normal_info.list[i].is_less = 1;
        }
        if (normal_info.list[i].goods_number == normal_info.list[i].limit_buy_num){
          normal_info.list[i].is_less = 1;
        }
        if (normal_info.list[i].goods_number == normal_info.list[i].limit_max_num) {
          normal_info.list[i].is_most = 1;
        }
        if (normal_info.list[i].can_join_activity != "") {
          for (var z in normal_info.list[i].can_join_activity) {
            if (normal_info.list[i].can_join_activity[z].action == 2) {
              that.data.if_fullprice = 1;
              that.setData({
                if_fullprice: that.data.if_fullprice
              })
            } else if (normal_info.list[i].can_join_activity[z].action == 1) {
              that.data.if_purchase = 1;
              that.setData({
                if_purchase: that.data.if_purchase
              })
            }
          }
        }
      }
      full_info = res.content.list.full_price;
      for (var i in full_info.list) {
        for (var j in full_info.list[i].goods_list){
          if (full_info.list[i].goods_list[j].is_checked == 1 && !full_info.list[i].goods_list[j].tip){
            normal_choosed_goods.push(full_info.list[i].goods_list[j].rec_id);
          }
          if (parseFloat(full_info.list[i].goods_list[j].cart_goods_price) > parseFloat(full_info.list[i].goods_list[j].goods_price)) {
            full_info.list[i].goods_list[j].less_money = parseFloat(full_info.list[i].goods_list[j].cart_goods_price) - parseFloat(full_info.list[i].goods_list[j].goods_price);
            full_info.list[i].goods_list[j].less_money = parseFloat(full_info.list[i].goods_list[j].less_money).toFixed(2);
          } else {
            full_info.list[i].goods_list[j].less_money = 0
          }
          full_info.list[i].goods_list[j].is_less = 0;
          full_info.list[i].goods_list[j].is_most = 0;
          if(full_info.list[i].goods_list[j].goods_number == 1) {
            full_info.list[i].goods_list[j].is_less = 1;
          }
          if (full_info.list[i].goods_list[j].goods_number == full_info.list[i].goods_list[j].limit_buy_num) {
            full_info.list[i].goods_list[j].is_less = 1;
          }
          if (full_info.list[i].goods_list[j].goods_number == full_info.list[i].goods_list[j].limit_max_num) {
            full_info.list[i].goods_list[j].is_most = 1;
          }
          if (full_info.list[i].goods_list[j].can_join_activity != "") {
            for (var z in full_info.list[i].goods_list[j].can_join_activity) {
              if (full_info.list[i].goods_list[j].can_join_activity[z].action == 2) {
                that.data.if_fullprice = 1;
                that.setData({
                  if_fullprice: that.data.if_fullprice
                })
              } else if (full_info.list[i].goods_list[j].can_join_activity[z].action == 1) {
                that.data.if_purchase = 1;
                that.setData({
                  if_purchase: that.data.if_purchase
                })
              }
            }
          }
        }
      }
      purchase_info = res.content.list.purchase_price;
      for (var i in purchase_info.list) {
        for (var j in purchase_info.list[i].goods_list){
          if (purchase_info.list[i].goods_list[j].is_checked == 1 && !purchase_info.list[i].goods_list[j].tip) {
            if (purchase_info.list[i].goods_list[j].extend_id == 0){
              normal_choosed_goods.push(purchase_info.list[i].goods_list[j].rec_id);
            }

          }
          if (parseFloat(purchase_info.list[i].goods_list[j].cart_goods_price) > parseFloat(purchase_info.list[i].goods_list[j].goods_price)) {
            purchase_info.list[i].goods_list[j].less_money = parseFloat(purchase_info.list[i].goods_list[j].cart_goods_price) - parseFloat(purchase_info.list[i].goods_list[j].goods_price);
            purchase_info.list[i].goods_list[j].less_money = parseFloat(purchase_info.list[i].goods_list[j].less_money).toFixed(2);
          } else {
            purchase_info.list[i].goods_list[j].less_money = 0
          }

          purchase_info.list[i].goods_list[j].is_less = 0;
          purchase_info.list[i].goods_list[j].is_most = 0;
          if (purchase_info.list[i].goods_list[j].goods_number == 1) {
            purchase_info.list[i].goods_list[j].is_less = 1;
          }
          if (purchase_info.list[i].goods_list[j].goods_number == purchase_info.list[i].goods_list[j].limit_buy_num) {
            purchase_info.list[i].goods_list[j].is_less = 1;
          }
          if (purchase_info.list[i].goods_list[j].goods_number == purchase_info.list[i].goods_list[j].limit_max_num) {
            purchase_info.list[i].goods_list[j].is_most = 1;
          }
          if (purchase_info.list[i].goods_list[j].can_join_activity != ""){
            for (var z in purchase_info.list[i].goods_list[j].can_join_activity){
              if (purchase_info.list[i].goods_list[j].can_join_activity[z].action == 2){
                that.data.if_fullprice = 1;
                that.setData({
                  if_fullprice: that.data.if_fullprice
                })
              } else if (purchase_info.list[i].goods_list[j].can_join_activity[z].action == 1){
                that.data.if_purchase = 1;
                that.setData({
                  if_purchase:that.data.if_purchase
                })
              }
            }
          }
        }
      }
      that.setData({
        cart_info: cart_info,
        normal_info: normal_info,
        full_info: full_info,
        purchase_info: purchase_info,
        invalid_goods: invalid_goods,
        is_block:is_block,
        proMode:true,
        pack_info:pack_info
      })
    } else {
      util.showModal("提示", res.message);
      return false;
    }
  }, {action: that.data.gd_actoins,post_action: that.data.post_act, cart_data:that.data.cart_datas, options: JSON.stringify(that.data.options) }, '', true);
}
