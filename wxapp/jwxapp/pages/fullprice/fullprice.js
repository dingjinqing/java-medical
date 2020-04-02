// pages/fullprice/fullprice.js
var util = require('../../utils/util.js')
// var spec_mixin = require("../goodscommon/spec.js")
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var full_info = [];
var full_change_info = [];
var choose_list = {};
var this_cheks;
var this_chek;
var inp_check;
// 是否显示删除按钮 默认0 不显示
var can_del = 0;
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
    strategy_id: '', // 活动id
    store_id: '',
    checkMode: true,
    searchText: "", // 搜索内容
    page: 1,
    last_page: 1,
    full_info: [],
    full_goods_info: [],
    full_change_info: [],
    is_load: 0,
    specMove: true,
    al_goods_prices: '', // 金额
    can_del: 0,
    all_goods_doc: "", // 金额提示
    showSpec: false, // 规格弹窗
    specParams: {} // 规格信息
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    that.setData({
      strategy_id: Number(options.strategy_id),
      store_id: Number(options.store_id),
      searchText: ""
    })
    full_request(that);
  },
  // 跳转购物车
  go_to_cart: function () {
    util.navigateTo({ url: '/pages/cart/cart' })
    // if (this.data.store_id > 0) {
    //   util.navigateTo({
    //     url: '/pages/cart/cart?store_id=' + this.data.store_id,
    //   })
    // } else {
    //   util.navigateTo({
    //     url: '/pages/cart/cart',
    //   })
    // }
  },
  // 去开通会员
  to_member: function () {
    if (full_info.is_can_buy == 0 && full_info.card_list.length == 1 && full_info.card_list[0].card_type == 2) {
      util.showModal("提示", '您当前的会员等级不满足，仅拥有' + full_info.card_list[0].card_name + '等级卡用户可购买此商品。可在"个人中心"查看会员卡权益');
      return false
    }
    // util.navigateTo({
    //   url: '/pages/buycardlist/buycardlist?strategy_id=' + that.data.strategy_id + "&is_fullprice=" + strategy_id,
    // })
  },
  // 搜索
  searchGoods: function (e) {
    var searchText = e.detail.value;
    var that = this;
    that.setData({
      searchText: searchText
    })
    full_request(that);
  },
  // 删除已选商品
  to_del_goods: function () {
    var that = this;
    if (can_del == 0) {
      can_del = 1;
      that.data.can_del = 1;
    } else {
      can_del = 0;
      that.data.can_del = 0;
    }
    that.setData({
      can_del: can_del
    })
  },
  to_del_geted: function (e) {
    var goods_num = e.currentTarget.dataset.goods_number;
    var cart_id = e.currentTarget.dataset.cart_id;
    var that = this;
    wx.showLoading({
      title: '删除中...',
    })
    // util.api("/api/wxapp/common/removegoods", function (res) {
    //   if (res.error == 0) {
    //     util.api('/api/wxapp/fullprice/checkedlist', function (res) {
    //       if (res.error == 0) {
    //         wx.hideLoading();
    //         full_change_info = res.content;
    //         full_change_info.count = 0;

    //         for (var i = 0; i < full_change_info.list.length; i++) {
    //           full_change_info.list[i].is_zuida = 0;
    //           if (full_change_info.list[i].goods_number == 1) {
    //             full_change_info.list[i].is_zuixiao = 1;
    //           } else {
    //             full_change_info.list[i].is_zuixiao = 0;
    //           }
    //           full_change_info.count += full_change_info.list[i].goods_number;
    //         }

    //         if (full_change_info.count != 0) {
    //           can_del = 1;
    //           that.data.can_del = 1;
    //         } else {
    //           can_del = 0;
    //           that.data.can_del = 0;
    //         }

    //         that.setData({
    //           full_change_info: full_change_info,
    //           can_del: can_del,
    //         })
    //       } else {
    //         util.showModal("提示", res.message, function () {
    //           util.jumpLink("pages/index/index", "redirectTo")
    //         }, false);
    //         return false;
    //       }
    //     }, { strategy_id: that.data.strategy_id, store_id: that.data.store_id });
    //   } else {
    //     util.showModal("提示", res.message, function () {
    //       util.jumpLink("pages/index/index", 'redirectTo')
    //     }, false);
    //     return false;
    //   }
    // }, {
    //     identity_id: '',
    //     param_id: cart_id,
    //     action: "strategy"
    //   })
  },
  // 查看已选商品列表
  showCheck: function (e) {
    var that = this;
    that.setData({
      checkMode: false
    });
    // util.api('/api/wxapp/fullprice/checkedlist', function (res) {
    //   if (res.error == 0) {
    //     full_change_info = res.content;
    //     can_del = 0;
    //     that.data.can_del = 0;
    //     full_change_info.count = 0;
    //     for (var i = 0; i < full_change_info.list.length; i++) {
    //       full_change_info.list[i].is_zuida = 0;
    //       if (full_change_info.list[i].goods_number == 1) {
    //         full_change_info.list[i].is_zuixiao = 1;
    //       } else {
    //         full_change_info.list[i].is_zuixiao = 0;
    //       }
    //       full_change_info.count += full_change_info.list[i].goods_number;
    //     }
    //     that.setData({
    //       full_change_info: full_change_info,
    //       can_del: can_del,
    //     })
    //   } else {
    //     util.showModal("提示", res.message);
    //     return false;
    //   }
    // }, { strategy_id: that.data.strategy_id, store_id: that.data.store_id });
  },
  // 关闭已选商品弹窗
  closeCheck: function () {
    var that = this;
    full_request(that);
    that.setData({
      checkMode: true,
      can_del: 0
    });
  },
  // 换购商品-
  btn_del_al: function (e) {
    var that = this;
    this_cheks = e.currentTarget.dataset.idnes;
    if (full_change_info.list[this_cheks].goods_number < 1) {
      full_change_info.list[this_cheks].is_zuixiao = 1;
      full_change_info.list[this_cheks].goods_number = 1;
      that.setData({
        full_change_info: full_change_info
      })
      return false;
    }
    full_change_info.list[this_cheks].goods_number = parseInt(full_change_info.list[this_cheks].goods_number) - 1;
    if (full_change_info.list[this_cheks].goods_number < 1) {
      full_change_info.list[this_cheks].goods_number = 1;
      full_change_info.list[this_cheks].is_zuixiao = 1;
      that.setData({
        full_change_info: full_change_info
      });
      return false;
    }
    // util.api('/api/wxapp/cart/addnew', function (res) {
    //   if (res.error == 0) {
    //     full_change_info.count -= 1;
    //     that.setData({
    //       full_change_info: full_change_info,
    //       al_goods_prices: res.content.full_price,
    //       all_goods_doc: res.content.change_doc,
    //     });
    //   } else if (res.error == 10) {
    //     util.showModal("提示", res.message, function () {
    //       full_change_info.list[this_cheks].is_zuixiao = 1;
    //       full_change_info.list[this_cheks].goods_number = parseInt(full_change_info.list[this_cheks].goods_number) + 1;
    //       that.setData({
    //         full_change_info: full_change_info,
    //         al_goods_prices: res.content.full_price,
    //         all_goods_doc: res.content.change_doc,
    //       });
    //     });
    //     return false;
    //   } else {
    //     util.showModal("提示", res.message);
    //     return false;
    //   }
    // }, { action: 2, btn_click: 0, identity_id: that.data.strategy_id, change_goods_number: 1, product_id: full_change_info.list[this_cheks].prd_id, prd_number: full_change_info.list[this_cheks].goods_number, store_id: that.data.store_id })
  },
  // 换购商品+
  btn_add_al: function (e) {
    var that = this;
    this_chek = e.currentTarget.dataset.indexs;
    full_change_info.list[this_chek].goods_number = parseInt(full_change_info.list[this_chek].goods_number) + 1;
    if (full_change_info.list[this_chek].goods_number > 1) {
      full_change_info.list[this_chek].is_zuixiao = 0;
    }
    // util.api('/api/wxapp/cart/addnew', function (res) {
    //   if (res.error == 0) {
    //     full_change_info.count += 1;
    //     that.setData({
    //       full_change_info: full_change_info
    //     });
    //   } else if (res.error == 10) {
    //     util.showModal("提示", res.message, function () {
    //       full_change_info.list[this_chek].is_zuida = 1;
    //       full_change_info.list[this_chek].goods_number = parseInt(full_change_info.list[this_chek].goods_number) - 1;
    //       that.setData({
    //         full_change_info: full_change_info,
    //         al_goods_prices: res.content.full_price,
    //         all_goods_doc: res.content.change_doc,
    //       });
    //     });
    //     return false;
    //   } else {
    //     util.showModal("提示", res.message);
    //     return false;
    //   }
    // }, { action: 2, btn_click: 1, identity_id: that.data.strategy_id, change_goods_number: 1, product_id: full_change_info.list[this_chek].prd_id, prd_number: full_change_info.list[this_chek].goods_number, store_id: that.data.store_id })
  },
  // 选择商品数量
  get_al_num: function (e) {
    var that = this;
    var this_goods_number = e.detail.value;
    inp_check = e.currentTarget.dataset.idnes;
    full_change_info.list[inp_check].goods_number = this_goods_number;
    // util.api('/api/wxapp/cart/addnew', function (res) {
    //   if (res.error == 0) {
    //     full_change_info.count = 0;
    //     for (var i = 0; i < full_change_info.list.length; i++) {
    //       full_change_info.count += parseInt(full_change_info.list[i].goods_number);
    //     }
    //     that.setData({
    //       full_change_info: full_change_info,
    //       al_goods_prices: res.content.full_price,
    //       all_goods_doc: res.content.change_doc,
    //     });
    //   } else if (res.error == 10) {
    //     util.showModal("提示", res.message, function () {
    //       full_change_info.list[inp_check].is_zuida = 1;
    //       that.setData({
    //         full_change_info: full_change_info,
    //         al_goods_prices: res.content.full_price,
    //         all_goods_doc: res.content.change_doc,
    //       });
    //     });
    //     return false;
    //   } else {
    //     util.showModal("提示", res.message);
    //     return false;
    //   }
    // }, { action: 2, identity_id: that.data.strategy_id, change_goods_number: 1, product_id: full_change_info.list[inp_check].prd_id, prd_number: full_change_info.list[inp_check].goods_number, store_id: that.data.store_id })
  },
  // 加入购物车
  add_to_cart: function (e) {
    var that = this;
    var prdId = e.currentTarget.dataset.prd_id
    var goodsId = e.currentTarget.dataset.goods_id
    // 不可参与购买且会员列表不为空
    if (full_info.state == 4 && full_info.cardList.length == 1 && full_info.cardList[0].cardType == 2) {
      util.showModal("提示", '您当前的会员等级不满足，仅拥有' + full_info.cardList[0].cardName + '等级卡用户可购买此商品。可在"个人中心"查看会员卡权益');
      return false
    }
    // 不可参与购买跳转会员卡列表
    if (full_info.state == 4) {
      wx.showModal({
        title: '提示',
        content: '会员专享活动，开通会员即可参与！',
        cancelText: "取消",
        cancelColor: "#333333",
        confirmText: "开通会员",
        confirmColor: that.data.comColor,
        success(res) {
          if (res.confirm) {
            // util.navigateTo({
            //   url: '/pages/buycardlist/buycardlist?strategy_id=' + that.data.strategy_id + "&is_fullprice=" + strategy_id,
            // })
          }
        }
      })
      return false;
    }
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
          activityType: 21,
          activityId: that.data.strategy_id
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

  // 规格详情
  requestGoodsInfo(goodsId) {
    util.api('/api/wxapp/goods/detail', res=>{
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
    },{
        goodsId: goodsId,
        activityId: this.data.strategy_id,
        activityType: 21,
        userId: util.getCache('user_id'),
        lon: null,
        lat: null
    })
  },

  
  // 获取规格信息
  getProduct({
    detail: { prdNumber, limitBuyNum = null, limitMaxNum = null}
  }) {
    this.setData({
      limitInfo: {
        prdNumber,
        limitBuyNum,
        limitMaxNum
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

  // 添加购物车
  addCart() {
    let { goodsNum: goodsNumber, prdId } = this.data.productInfo
    util.api("/api/wxapp/cart/add", res => {
      if (res.error == 0) {
        util.toast_success('已加入购物车');
        full_request(this)
      } else {
        util.showModal("提示", res.message);
        return false;
      }
      this.bindCloseSpec()
    }, {
        goodsNumber: goodsNumber,
        prdId: prdId,
        activityType: 21,
        activityId: this.data.strategy_id
      });
  },

  // 关闭规格弹窗
  bindCloseSpec() {
    this.setData({
      showSpec: false,
      triggerButton: ''
    })
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
    util.api('/api/wxapp/fullprice/goodslist', function (res) {
      if (res.error == 0) {
        full_info = res.content;
        var full_goods_info = [];
        var full_goods_r = [];
        full_goods_r = full_info.goods.dataList; // 商品列表
        var al_goods_prices = full_info.totalPrice; // 金额
        // 金额提示
        if (full_info.fullPriceDoc) {
          var all_goods_doc = ''
          if (full_info.fullPriceDoc.docType == 0) {
            all_goods_doc = '快选择商品参加活动吧'
          } else if (full_info.fullPriceDoc.docType == 1) {
            all_goods_doc = '下单立减' + full_info.fullPriceDoc.reduceMoney + '元'
          } else if (full_info.fullPriceDoc.docType == 2) {
            all_goods_doc = '再选' + full_info.fullPriceDoc.diffPrice + '元，即可减' + full_info.fullPriceDoc.reduceMoney + '元'
          } else if (full_info.fullPriceDoc.docType == 3) {
            all_goods_doc = '再选' + full_info.fullPriceDoc.diffPrice + '元，即可打' + full_info.fullPriceDoc.discount + '折'
          } else if (full_info.fullPriceDoc.docType == 4) {
            all_goods_doc = '再选' + full_info.fullPriceDoc.diffNumber + '件，即可减' + full_info.fullPriceDoc.reduceMoney + '元'
          } else if (full_info.fullPriceDoc.docType == 5) {
            all_goods_doc = '再选' + full_info.fullPriceDoc.diffNumber + '件，即可打' + full_info.fullPriceDoc.discount + '折'
          }
        }
        if (full_goods_r.length > 0) {
          full_goods_info = full_goods_r;

          that.setData({
            full_info: full_info, // 全部信息
            full_goods_info: that.data.full_goods_info.concat(full_goods_info), // 加载商品列表
            al_goods_prices: al_goods_prices, // 金额
            all_goods_doc: all_goods_doc // 金额提示
          })

        }

      } else {
        util.showModal("提示", res.message, function () {
          wx.navigateBack({

          })
        });
        return false;
      }
    }, { strategyId: that.data.strategy_id, currentPage: that.data.page, search: that.data.searchText, pageRows: 10 });
  },

  proActionChange: function () {
    this.setData({
      checkMode: true
    })
  },
  // 跳转商品详情
  to_items: function (e) {
    var goods_id = e.currentTarget.dataset.goods_id;
    util.navigateTo({
      url: '/pages/item/item?gid=' + goods_id,
    })
  }
})
function full_request(that) {
  util.api('/api/wxapp/fullprice/goodslist', function (res) {
    if (res.error == 0) {
      full_info = res.content;
      that.data.last_page = full_info.goods.page.lastPage;
      var full_goods_info = [];
      var full_goods_r = [];
      full_goods_r = full_info.goods.dataList; // 商品列表
      var al_goods_prices = full_info.totalPrice; // 金额
      // 金额提示
      if (full_info.fullPriceDoc) {
        var all_goods_doc = ''
        if (full_info.fullPriceDoc.docType == 0) {
          all_goods_doc = '快选择商品参加活动吧'
        } else if (full_info.fullPriceDoc.docType == 1) {
          all_goods_doc = '下单立减' + full_info.fullPriceDoc.reduceMoney + '元'
        } else if (full_info.fullPriceDoc.docType == 2) {
          all_goods_doc = '再选' + full_info.fullPriceDoc.diffPrice + '元，即可减' + full_info.fullPriceDoc.reduceMoney + '元'
        } else if (full_info.fullPriceDoc.docType == 3) {
          all_goods_doc = '再选' + full_info.fullPriceDoc.diffPrice + '元，即可打' + full_info.fullPriceDoc.discount + '折'
        } else if (full_info.fullPriceDoc.docType == 4) {
          all_goods_doc = '再选' + full_info.fullPriceDoc.diffNumber + '件，即可减' + full_info.fullPriceDoc.reduceMoney + '元'
        } else if (full_info.fullPriceDoc.docType == 5) {
          all_goods_doc = '再选' + full_info.fullPriceDoc.diffNumber + '件，即可打' + full_info.fullPriceDoc.discount + '折'
        }
      }

      if (full_goods_r.length > 0) {
        full_goods_info = full_goods_r;

        // 是否存在限时降价活动
        var flag = full_goods_info.find((item, index) => {
          return item.goodsPriceAction == 2
        })
        if (flag != undefined) {
          full_info.is_show_reduce_doc = 1
        } else {
          full_info.is_show_reduce_doc = 0
        }

        that.setData({
          full_info: full_info, // 全部信息
          full_goods_info: full_goods_info, // 商品列表
          al_goods_prices: al_goods_prices, // 金额
          all_goods_doc: all_goods_doc // 金额提示
        })

      }
    } else {
      util.showModal("提示", res.message, function () {
        wx.navigateBack({

        })
      });
      return false;
    }
  }, { strategyId: that.data.strategy_id, currentPage: that.data.page, search: that.data.searchText, pageRows: 10 });
}
