// pages/maingoodslist/maingoodslist.js
var util = require('../../utils/util.js')
var app = getApp()
var purchase_change_goods = {};

global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    changeMove: true, // 换购商品弹窗
    main_goods_info: [], // 主商品全部信息
    add_goods_info: [], // 主商品列表
    change_goods_info: [], // 换购列表
    pIds: [], // 换购已选择规格id
    is_load: 0,
    searchText: "", // 搜索内容
    page: 1,
    last_page: 1,
    get_price: '',
    get_doc: "",
    showSpec: false, // 规格弹窗
    // triggerButton: 'left',
    specParams: {}, // 规格信息
    basicNumber: 0, // 多规格添加购物车时的基础数量
    basicLimit: null, // 多规格添加购物车时的限制
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    console.log(options.identity_id)
    that.setData({
      identity_id: Number(options.identity_id),
      store_id: options.store_id ? Number(options.store_id) : '',
      pIds: options.pIds ? JSON.parse(options.pIds) : [],
    })
    main_request(that);
  },

  // 搜索
  searchGoods: function (e) {
    var that = this;
    that.setData({
      searchText: e.detail.value
    })
    main_request(that);
  },

  // 去商品详情
  to_items: function (e) {
    var goods_id = e.currentTarget.dataset.goods_id;
    util.navigateTo({
      url: '/pages/item/item?gid=' + goods_id,
    })
  },

  // 列表加入购物车
  add_to_cart: function (e) {
    var that = this;
    var goodsId = e.currentTarget.dataset.goods_id
    var prdId = e.currentTarget.dataset.prd_id
    var cartNumber = e.currentTarget.dataset.cart_number
    var limitAmount = e.currentTarget.dataset.limit_amount
    that.setData({
      basicNumber: e.currentTarget.dataset.cart_number,
      basicLimit: e.currentTarget.dataset.limit_amount
    })
    // 添加购物车
    if (prdId) {
      // 单规格
      var value = cartNumber + 1
      if (limitAmount != null && limitAmount != 0 && (value > limitAmount)) {
        util.showModal('提示', '最大限购量为' + limitAmount + '个');
        return false
      }
      util.api('/api/wxapp/cart/add', function (res) {
        if (res.error == 0) {
          util.toast_success('已加入购物车');
          main_request(that);
        } else {
          util.showModal("提示", res.message);
          return false;
        }
      }, {
          goodsNumber: value,
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
  // 获取商品详情
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
  // 规格回调
  getProductData(e) {
    this.setData({
      product: e.detail,
      limitInfo: {
        prdNumber: e.detail.prdNumber,
        limitBuyNum: e.detail.limitBuyNum,
        limitMaxNum: e.detail.limitMaxNum,
        activityType: 7
      }
    })
  },
  // 数量回调
  getGoodsNum(e) {
    this.setData({
      productInfo: { ...this.data.product, goodsNum: e.detail.goodsNum }
    });
    console.log(this.data.productInfo)
  },
  // 关闭规格弹窗
  bindCloseSpec() {
    this.setData({
      showSpec: false,
      triggerButton: ''
    })
  },
  // 规格添加购物车
  addCart() {
    var that = this
    let { goodsNum: goodsNumber, prdId } = that.data.productInfo
    // 限购校验
    var value = that.data.basicNumber + goodsNumber
    if (that.data.basicLimit != null && that.data.basicLimit != 0 && (value > that.data.basicLimit)) {
      util.showModal('提示', '最大限购量为' + that.data.basicLimit + '个');
      return false
    }
    util.api("/api/wxapp/cart/add", res => {
      if (res.error == 0) {
        util.toast_success('已加入购物车');
        main_request(that)
      } else {
        util.showModal("提示", res.message);
        return false;
      }
      that.bindCloseSpec()
    }, {
        goodsNumber: value,
        prdId: prdId,
        activityType: 7,
        activityId: that.data.identity_id
      });
  },

  // 获取换购商品
  showGoods: function (e) {
    var that = this;
    console.log(that.data.pIds)
    that.setData({
      changeMove: false
    })
    util.api('/api/wxapp/purchase/changegoods', function (res) {
      if (res.error == 0) {
        var change_goods_info = res.content;
        // 已选个数
        change_goods_info.alreadyChangeNum = that.data.pIds.length
        change_goods_info.list.forEach(item => {
          // 已选换购商品
          that.data.pIds.forEach(val => {
            if (item.prdId == val) {
              item.isChecked = 1
            }
          })
          if (item.isChecked == 1) {
            purchase_change_goods[item.prdId] = item.purchaseRuleId
          }
        })
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

  // 去购物车
  click_to_cart: function (e) {
    util.navigateTo({ url: '/pages/cart/cart' })
  },

  // 关闭换购商品弹窗
  proActionChange: function () {
    this.setData({
      changeMove: true
    })
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
    var tip = e.currentTarget.dataset.tip;
    var this_is_checked = e.currentTarget.dataset.is_check;
    var ids = e.currentTarget.dataset.keys;
    var change_goods_info = that.data.change_goods_info
    if (tip == 0) {
      util.showModal('提示', '不满足换购条件');
      return false
    }
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
    var prdIds = []
    that.data.change_goods_info.list.forEach(item => {
      if (item.isChecked == 1) {
        prdIds.push(item.prdId)
      }
    })
    prdIds.forEach(item => {
      if (that.data.pIds.indexOf(item) == -1) {
        that.add_cart(item)
      }
    })
    that.data.change_goods_info.alreadyChangeNum = prdIds.length
    that.setData({
      pIds: prdIds,
      changeMove: true,
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
        main_request(that);
      } else {
        util.showModal("提示", res.message);
        return false;
      }
    }, { goodsNumber: 1, prdId: data, activityType: 97, activityId: that.data.identity_id })
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
