// pages/maingoodslist/maingoodslist.js
var util = require('../../utils/util.js')
// var spec_mixin = require("../goodscommon/spec.js")
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var main_goods_info = [];
var change_goods_info = [];
var searchText = "";
var get_price;
var get_doc;
var spec_list = {};
var prd_list = {};
var prd_str = [];
var spec_view = 0;
var spec_check = 0;
var spec_name;
var speclist = [];
var buy_number = 1;
var limit_num = 1;
var is_prd_good = 0;
var prd_infs = [];
var cat_list = [];
var good_nums = 1;
var is_max = 0;
var is_min = 0;
var choose_list = {};
var identity_id;
var store_id;
var purchase_change_goods = {};
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
  // mixins: [spec_mixin],
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    img_close: imageUrl + '/image/wxapp/close_icon.png',
    changeMove: true,
    main_goods_info: [],
    change_goods_info: [],
    searchText: "",
    page: 1,
    last_page: 1,
    get_price: '',
    get_doc: "",
    specMove: true,
    kucun: true,
    spec_view: 0,
    spec_array: [],
    spec_id_list: {},
    speclist: [],
    prd_infs: [],
    cat_list: [],
    good_nums: 1,
    limit_buy_num: 1,
    limit_max_num: 0,
    is_max: 0,
    is_min: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    purchase_change_goods = {};
    identity_id = options.identity_id;
    store_id = options.store_id;
    purchase_change_goods = {};
    spec_list = {};
    prd_list = {};
    is_prd_good = 0;
    prd_str = [];
    cat_list = [];
    good_nums = 1;
    limit_num = 1;
    searchText = '';
    if (options.is_show_change_page == 1) {
      this.showGoods();
    }
    main_request(that);
  },
  proActionChange: function () {
    this.setData({
      changeMove: true
    })
  },

  // 搜索
  searchGoods: function (e) {
    searchText = e.detail.value;
    var that = this;
    that.setData({
      searchText: searchText
    })
    main_request(that);
  },
  showGoods: function (e) {
    var that = this;
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    that.setData({
      changeMove: false
    })
    // util.api('/api/wxapp/purchase/changegoods', function (res) {
    //   if (res.error == 0) {
    //     change_goods_info = res.content;
    //     for (var i = 0; i < change_goods_info.list.length; i++) {
    //       if (change_goods_info.list[i].is_checked == 1) {
    //         purchase_change_goods[change_goods_info.list[i].prd_id] = change_goods_info.list[i].purchase_rule_id;
    //       }
    //     }
    //     that.setData({
    //       change_goods_info: change_goods_info
    //     })
    //   } else {
    //     util.showModal("提示", res.message, function () {
    //       util.reLaunch({
    //         url: '/pages/index/index'
    //       })
    //     });
    //     return false;
    //   }
    // }, { purchase_price_id: identity_id, store_id: store_id, open_id: open_id, form_id: form_id });
  },
  close_change: function () {
    var that = this;
    that.setData({
      changeMove: true
    })
  },
  // 切换换购商品
  choose_chenage: function (e) {
    var that = this;
    var this_is_checked = e.currentTarget.dataset.is_check;
    var ids = e.currentTarget.dataset.keys;
    if (this_is_checked == 0) {
      change_goods_info.list[ids].is_checked = 1;
      change_goods_info.already_change_num = parseInt(change_goods_info.already_change_num) + 1;
      if (change_goods_info.max_change_purchase > 0) {
        if (change_goods_info.already_change_num > change_goods_info.max_change_purchase) {
          change_goods_info.list[ids].is_checked = 0;
          change_goods_info.already_change_num = parseInt(change_goods_info.already_change_num) - 1;
          util.showModal("提示", "换购数量已达上限");
          return false;
        }
      }
      purchase_change_goods[change_goods_info.list[ids].prd_id] = change_goods_info.list[ids].purchase_rule_id;
      that.setData({
        change_goods_info: change_goods_info
      })
    } else {
      change_goods_info.list[ids].is_checked = 0;
      change_goods_info.already_change_num = parseInt(change_goods_info.already_change_num) - 1;
      if (purchase_change_goods.hasOwnProperty(change_goods_info.list[ids].prd_id)) {
        var enen = change_goods_info.list[ids].prd_id;
        delete purchase_change_goods.enen;
        delete purchase_change_goods[enen];
      }
      that.setData({
        change_goods_info: change_goods_info
      })
    }
  },
  // 确认换购商品
  btn_confirm_change: function () {
    var that = this;
    util.api('/api/wxapp/cart/addnew', function (res) {
      if (res.error == 0) {
        get_price = res.content.main_price;
        get_doc = res.content.change_doc;
        that.setData({
          changeMove: true,
          get_price: get_price,
          get_doc: get_doc
        })

      } else {
        util.showModal("提示", res.message);
        return false;
      }
    }, { action: 1, purchase_change_goods: JSON.stringify(purchase_change_goods), identity_id: identity_id, store_id: store_id })
  },
  // 加入购物车
  add_to_cart: function (e) {
    var send_data = {};
    var that = this;
    var this_goods_id = e.currentTarget.dataset.goods_id;
    send_data.goods_id = this_goods_id;
    send_data.action = 1;
    send_data.identity_id = identity_id;
    send_data.store_id = store_id;
    send_data.user_id = util.getCache('user_id');
    // util.api('/api/wxapp/cart/addnew', function (res) {
    //   if (res.error == 0) {
    //     get_price = res.content.main_price;
    //     get_doc = res.content.change_doc;
    //     //规格
    //     if (res.content.is_show_spec) {
    //       res.content.goods.specs = res.content.goods.spec;
    //       that.setData({ goodsData: res.content.goods })
    //       that.bindAddCart()
    //     }
    //     else {
    //       util.toast_success('已加入购物车');
    //       that.setData({
    //         get_price: res.content.main_price
    //       })
    //     }
    //     that.setData({
    //       get_price: get_price,
    //       get_doc: get_doc,
    //     })
    //   } else {
    //     util.showModal("提示", res.message);
    //     return false;
    //   }
    // }, send_data);
  },

  // 去购物车
  click_to_cart: function (e) {
    if (store_id > 0) {
      util.navigateTo({
        url: '/pages/cart/cart?store_id=' + store_id,
      })
    } else {
      util.navigateTo({
        url: '/pages/cart/cart',
      })
    }

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

  to_items: function (e) {
    var goods_id = e.currentTarget.dataset.goods_id;
    util.navigateTo({
      url: '/pages/item/item?goods_id=' + goods_id,
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
  bindAddCart() {
    if (!this.checkSelBuy()) return;
    this.addCart(this.getChooseList());
  },
  getChooseList() {
    var s = this._sel_buy;
    var gd = this.data.goodsData;
    return {
      goods_id: gd.goods_id,
      identity_id: identity_id,
      action: 1,
      product_id: s.has_spec ? s.select_prd.prd_id : gd.prd_id,
      prd_number: s.goods_number,
      store_id: store_id
    };
  },
  addCart(choose_list) {
    var that = this;
    util.api('/api/wxapp/cart/addnew', function (res) {
      if (res.error == 0) {
        util.toast_success('已加入购物车');
        that.setData({
          get_price: res.content.main_price,
          get_doc: res.content.change_doc,
        })
        that.bindCloseSpec();
      } else {
        util.showModal("提示", res.message);
        return false;
      }
    }, choose_list)
  },
})
function main_request(that) {
  util.api('/api/wxapp/purchase/goodslist', function (res) {
    if (res.error == 0) {
      main_goods_info = res.content;
      that.data.last_page = main_goods_info.goods.page.lastPage;
      var add_goods_info = main_goods_info.goods.dataList; // 商品列表
      get_price = main_goods_info.mainPrice; // 金额
      // 金额提示
      if (main_goods_info.changeDoc) {
        if (main_goods_info.changeDoc.state == 0) {
          get_doc = '快选择商品参加活动吧'
        } else if (main_goods_info.changeDoc.state == 1) {
          get_doc = '再选' + main_goods_info.changeDoc.diffPrice + '元可以换购'
        } else if (main_goods_info.changeDoc.state == 2) {
          get_doc = '已满足一条换购规则'
        }
      }

      if (add_goods_info.length > 0) {
        // 是否存在限时降价活动
        var flag = add_goods_info.find((item, index) => {
          return item.goodsPriceAction == 2
        })
        if (flag != undefined) {
          main_goods_info.is_show_reduce_doc = 1
        } else {
          main_goods_info.is_show_reduce_doc = 0
        }
      }

      that.setData({
        main_goods_info: main_goods_info,
        searchText: searchText,
        get_price: get_price,
        get_doc: get_doc
      })
    } else {
      util.showModal("提示", res.message, function () {
        util.reLaunch({
          url: '/pages/index/index'
        })
      });
      return false;
    }
  }, { purchasePriceId: identity_id, search: searchText, currentPage: that.data.page, pageRows: 10 });
}
