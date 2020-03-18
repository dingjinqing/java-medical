// pages/maingoodslist/maingoodslist.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var choose_list = {};
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
    changeMove: true, // 换购商品弹窗
    main_goods_info: [], // 主商品全部信息
    add_goods_info: [], // 主商品列表
    change_goods_info: [], // 换购列表
    is_load: 0,
    searchText: "", // 搜索内容
    page: 1,
    last_page: 1,
    get_price: '',
    get_doc: "",
    specMove: true,
    kucun: true,
    spec_array: [],
    spec_id_list: {},
    limit_buy_num: 1,
    limit_max_num: 0,
    showSpec: false, // 规格弹窗
    // triggerButton: 'left',
    specParams: {} // 规格信息
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    that.setData({
      identity_id: Number(options.identity_id),
      store_id: Number(options.store_id),
      purchase_change_goods: {},
      searchText: ''
    })
    if (options.is_show_change_page == 1) {
      this.showGoods();
    }
    main_request(that);
  },
  // 换购商品
  proActionChange: function () {
    this.setData({
      changeMove: true
    })
  },
  // 搜索
  searchGoods: function (e) {
    var searchText = e.detail.value;
    var that = this;
    that.setData({
      searchText: searchText
    })
    main_request(that);
  },
  // 获取换购商品
  showGoods: function (e) {
    var that = this;
    that.setData({
      changeMove: false
    })
    util.api('/api/wxapp/purchase/changegoods', function (res) {
      if (res.error == 0) {
        var change_goods_info = res.content;
        for (var i = 0; i < change_goods_info.list.length; i++) {
          if (change_goods_info.list[i].is_checked == 1) {
            purchase_change_goods[change_goods_info.list[i].prd_id] = change_goods_info.list[i].purchase_rule_id;
          }
        }
        that.setData({
          change_goods_info: change_goods_info
        })
      } else {
        util.showModal("提示", res.message, function () {
          util.reLaunch({
            url: '/pages/index/index'
          })
        });
        return false;
      }
    }, { purchasePriceId: that.data.identity_id, storeId: that.data.store_id });
  },
  // 关闭换购商品弹窗
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
    var change_goods_info = that.data.change_goods_info
    if (this_is_checked == 0) {
      change_goods_info.list[ids].isChecked = 1;
      change_goods_info.alreadyChangeNum = parseInt(change_goods_info.alreadyChangeNum) + 1;
      if (change_goods_info.maxChangePurchase > 0) {
        if (change_goods_info.alreadyChangeNum > change_goods_info.maxChangePurchase) {
          change_goods_info.list[ids].isChecked = 0;
          change_goods_info.alreadyChangeNum = parseInt(change_goods_info.alreadyChangeNum) - 1;
          util.showModal("提示", "换购数量已达上限");
          return false;
        }
      }
      purchase_change_goods[change_goods_info.list[ids].prdId] = change_goods_info.list[ids].purchaseRuleId;
      that.setData({
        change_goods_info: change_goods_info
      })
    } else {
      change_goods_info.list[ids].isChecked = 0;
      change_goods_info.alreadyChangeNum = parseInt(change_goods_info.alreadyChangeNum) - 1;
      if (purchase_change_goods.hasOwnProperty(change_goods_info.list[ids].prdId)) {
        var enen = change_goods_info.list[ids].prdId;
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
    var prdIds = [];
    that.data.change_goods_info.list.forEach((item, index) => {
      if (item.isChecked == 1) {
        prdIds.push(item.prdId)
      }
    })
    prdIds.forEach((item, index) => {
      that.add_cart(item)
    })
  },
  // 添加换购商品到购物车
  add_cart(data) {
    console.log(data)
    var that = this
    util.api('/api/wxapp/cart/add', function (res) {
      if (res.error == 0) {
        // var get_price = res.content.main_price;
        // var get_doc = res.content.change_doc;
        that.setData({
          changeMove: true,
          // get_price: get_price,
          // get_doc: get_doc
        })
      } else {
        util.showModal("提示", res.message);
        return false;
      }
    }, { goodsNumber: 1, prdId: data, activityType: 97, activityId: that.data.identity_id })
  },
  // 列表加入购物车
  add_to_cart: function (e) {
    var that = this;
    var goodsId = e.currentTarget.dataset.goods_id
    var prdId = e.currentTarget.dataset.prd_id
    // 添加购物车
    if (prdId) {
      // 单规格
      util.api('/api/wxapp/cart/add', function (res) {
        if (res.error == 0) {
          util.toast_success('已加入购物车');
        } else {
          util.showModal("提示", res.message);
          return false;
        }
      }, {
          goodsNumber: 1,
          prdId: prdId,
          activityType: 7,
          activityId: that.data.identity_id
        })
    } else {
      // 选择规格
      that.setData({
        showSpec: true,
        triggerButton: 'left'
      })
      that.requestGoodsInfo(goodsId)
    }
  },

  requestGoodsInfo(goodsId) {
    util.api('/api/wxapp/goods/detail', res => {
      if (res.error == 0) {
        let {
          goodsId,
          goodsNumber,
          defaultPrd,
          activity,
          products,
          limitBuyNum,
          limitMaxNum,
          goodsImgs
        } = res.content
        let specParams = {
          goodsId,
          goodsNumber,
          defaultPrd,
          activity,
          products,
          limitBuyNum,
          limitMaxNum,
          goodsImgs
        }
        this.setData({
          specParams
        })
      }
    }, {
        goodsId: goodsId,
        activityId: this.data.identity_id,
        activityType: 7,
        userId: util.getCache('user_id'),
        lon: null,
        lat: null
      })
  },

  // 获取规格信息
  getProduct({
    detail: { prdNumber, limitBuyNum = null, limitMaxNum = null }
  }) {
    this.setData({
      limitInfo: {
        prdNumber,
        limitBuyNum,
        limitMaxNum,
        activityType: 7
      }
    })
  },

  // 关闭规格弹窗
  bindCloseSpec() {
    this.setData({
      showSpec: false,
      triggerButton: ''
    })
  },

  // 去购物车
  click_to_cart: function (e) {
    util.navigateTo({ url: '/pages/cart/cart' })
    // if (that.data.store_id > 0) {
    //   util.navigateTo({
    //     url: '/pages/cart/cart?store_id=' + that.data.store_id,
    //   })
    // } else {
    //   util.navigateTo({
    //     url: '/pages/cart/cart',
    //   })
    // }
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
    var that = this;
    if (that.data.page == that.data.last_page) {
      that.setData({
        is_load: 0
      })
      return;
    } else {
      that.setData({
        is_load: 1
      })
    }

    that.data.page = that.data.page + 1;
    util.api('/api/wxapp/purchase/goodslist', function (res) {
      if (res.error == 0) {
        var main_goods_info = res.content;
        that.data.last_page = main_goods_info.goods.page.lastPage;
        var add_goods_info = main_goods_info.goods.dataList; // 商品列表
        var get_price = main_goods_info.mainPrice; // 金额
        // 金额提示
        if (main_goods_info.changeDoc) {
          var get_doc = ''
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
          main_goods_info: main_goods_info, // 全部信息
          add_goods_info: that.data.add_goods_info.concat(add_goods_info), // 商品列表
          searchText: that.data.searchText,
          get_price: get_price, // 金额
          get_doc: get_doc // 金额提示
        })
      } else {
        util.showModal("提示", res.message, function () {
          util.reLaunch({
            url: '/pages/index/index'
          })
        });
        return false;
      }
    }, { purchasePriceId: that.data.identity_id, search: that.data.searchText, currentPage: that.data.page, pageRows: 10 });
  },

  to_items: function (e) {
    var goods_id = e.currentTarget.dataset.goods_id;
    util.navigateTo({
      url: '/pages/item/item?gid=' + goods_id,
    })
  }
})
function main_request(that) {
  util.api('/api/wxapp/purchase/goodslist', function (res) {
    if (res.error == 0) {
      var main_goods_info = res.content;
      that.data.last_page = main_goods_info.goods.page.lastPage;
      var add_goods_info = main_goods_info.goods.dataList; // 商品列表
      var get_price = main_goods_info.mainPrice; // 金额
      // 金额提示
      if (main_goods_info.changeDoc) {
        var get_doc = ''
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
        add_goods_info: add_goods_info,
        searchText: that.data.searchText,
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
  }, { purchasePriceId: that.data.identity_id, search: that.data.searchText, currentPage: that.data.page, pageRows: 10 });
}
