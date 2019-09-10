var util = require('../../utils/util.js')
var spec_mixin = require("../goodscommon/spec.js")
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var searchText = "";
var full_info = [];
var full_goods_info = [];
var full_change_info = [];
var is_load = 0;
var spec_list = {};
var prd_list = {};
var spec_view = 0;
var spec_check = 0;
var spec_name;
var speclist = [];
var buy_number = 1;
var limit_num = 1;
var is_prd_good = 0;
var prd_infs = [];
var is_max = 0;
var is_min = 0;
var choose_list = {};
var al_goods_prices;
var all_goods_doc;
var this_cheks;
var this_chek;
var inp_check;
var rule_id;
var store_id;
var zk;
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
  mixins: [spec_mixin],
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    img_close: imageUrl + '/image/wxapp/close_icon.png',
    checkMode: true,
    searchText: "",
    page: 1,
    last_page: 1,
    full_info: [],
    full_goods_info: [],
    full_change_info: [],
    is_load: 0,
    specMove: true,
    spec_view: 0,
    speclist: [],
    prd_infs: [],
    al_goods_prices: '',
    all_goods_doc: "",
    zk: false,
    can_del:""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    rule_id = options.identity_id;
    searchText = "";
    full_request(that);
  },
  // 去购物车
  go_to_cart: function () {
      util.navigateTo({
        url: '/pages/cart/cart',
      })
  },
  // 搜索
  searchGoods: function (e) {
    searchText = e.detail.value;
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
    var record_id = e.currentTarget.dataset.record_id;
    var that = this;
    wx.showLoading({
      title: '删除中...',
    })
    util.api("/api/wxapp/common/removegoods", function (res) {
      if (res.error == 0) {
        util.api('/api/wxapp/ship/select/list', function (res) {
          if (res.error == 0) {
            wx.hideLoading();
            full_change_info = res.content;
            if (full_change_info.total_select_number != 0) {
              can_del = 1;
              that.data.can_del = 1;
            } else {
              can_del = 0;
              that.data.can_del = 0;
            }
            for (var i = 0; i < full_change_info.goods_list.length; i++) {
              full_change_info.goods_list[i].is_zuida = 0;
              if (full_change_info.goods_list[i].goods_number == 1) {
                full_change_info.goods_list[i].is_zuixiao = 1;
              } else {
                full_change_info.goods_list[i].is_zuixiao = 0;
              }
            }
            that.setData({
              full_change_info: full_change_info,
              can_del: can_del
            })
          } else {
            util.showModal("提示", res.message, function () {
              util.jumpLink("pages/index/index","redirectTo")
             }, false);
            return false;
          }
        }, { rule_id: rule_id });
      } else {
        util.showModal("提示", res.message, function () {
          util.jumpLink("pages/index/index", 'redirectTo')
        }, false);
        return false;
      }
    }, {
        identity_id: '',
        param_id: record_id,
        action: "free_ship"
      })
  },
  // 已选商品列表
  showCheck: function (e) {
    var that = this;
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    that.setData({
      checkMode: false
    });
    util.api('/api/wxapp/ship/select/list', function (res) {
      if (res.error == 0) {
        full_change_info = res.content;
        can_del = 0;
        that.data.can_del = 0;
        for (var i = 0; i < full_change_info.goods_list.length; i++) {
          full_change_info.goods_list[i].is_zuida = 0;
          if (full_change_info.goods_list[i].goods_number == 1) {
            full_change_info.goods_list[i].is_zuixiao = 1;
          } else {
            full_change_info.goods_list[i].is_zuixiao = 0;
          }
        }
        that.setData({
          full_change_info: full_change_info,
          can_del: can_del
        })
      } else {
        util.showModal("提示", res.message, function () {
          util.jumpLink("pages/index/index", 'redirectTo')
         }, false);
        return false;
      }
    }, { rule_id: rule_id, form_id: form_id, open_id: open_id });
  },
  closeCheck: function () {
    var that = this;
    full_request(that);
    that.setData({
      checkMode: true,
      can_del:0
    });
  },
  //减号
  btn_del_al: function (e) {
    var that = this;
    this_cheks = e.currentTarget.dataset.idnes;
    if (full_change_info.goods_list[this_cheks].goods_number <= 1) {
      full_change_info.goods_list[this_cheks].is_zuixiao = 1;
      full_change_info.goods_list[this_cheks].goods_number = 1;
      that.setData({
        full_change_info: full_change_info
      })
      return false;
    }
    full_change_info.goods_list[this_cheks].goods_number = parseInt(full_change_info.goods_list[this_cheks].goods_number) - 1;
    if (full_change_info.goods_list[this_cheks].goods_number <= 1) {
      full_change_info.goods_list[this_cheks].goods_number = 1;
      full_change_info.goods_list[this_cheks].is_zuixiao = 1;
      that.setData({
        full_change_info: full_change_info
      })
    }
    if (full_change_info.goods_list[this_cheks].goods_number < 1) {
      full_change_info.goods_list[this_cheks].goods_number = 1
      full_change_info.goods_list[this_cheks].is_zuixiao = 1;
      return false;
    }

    util.api('/api/wxapp/ship/goods/add', function (res) {
      if (res.error == 0) {
        full_change_info.total_select_number = res.content.select_goods_number;
        that.setData({
          full_change_info: full_change_info,
          al_goods_prices: res.content.total_select_money,
          // all_goods_doc: res.content.total_select_number,
        });
      } else {
        util.showModal("提示", res.message, function () { }, false);
        return false;
      }
    }, { rule_id: rule_id, goods_number: 1, product_id: full_change_info.goods_list[this_cheks].product_id, goods_id: full_change_info.goods_list[this_cheks].goods_id,action: -1 })
  },
  btn_add_al: function (e) {
    var that = this;
    this_chek = e.currentTarget.dataset.indexs;
    full_change_info.goods_list[this_chek].goods_number = parseInt(full_change_info.goods_list[this_chek].goods_number) + 1;
    if (full_change_info.goods_list[this_chek].goods_number > 1) {
      full_change_info.goods_list[this_chek].is_zuixiao = 0;
    }
    util.api('/api/wxapp/ship/goods/add', function (res) {
      if (res.error == 0) {
        full_change_info.total_select_number = res.content.select_goods_number;
        that.setData({
          full_change_info: full_change_info
        });
      } else if (res.error == 10) {
        util.showModal("提示", res.message, function () {
          full_change_info.goods_list[this_chek].is_zuida = 1;
          that.setData({
            full_change_info: full_change_info,
            al_goods_prices: res.content.total_select_money,
            // all_goods_doc: res.content.change_doc,
          });
        }, false);
        return false;
      } else {
        util.showModal("提示", res.message, function () { }, false);
        return false;
      }
    }, { rule_id: rule_id, goods_number: 1, product_id: full_change_info.goods_list[this_chek].product_id, goods_id:full_change_info.goods_list[this_chek].goods_id })
  },
  get_al_num: function (e) {
    var that = this;
    var this_goods_number = e.detail.value;
    inp_check = e.currentTarget.dataset.idnes;
    full_change_info.goods_list[inp_check].goods_number = this_goods_number;
    util.api('/api/wxapp/ship/goods/add', function (res) {
      if (res.error == 0) {
        that.setData({
          full_change_info: full_change_info,
          al_goods_prices: res.content.total_select_money,
          all_goods_doc: res.content.total_select_number, 
        });
      } else if (res.error == 10) {
        util.showModal("提示", res.message, function () {
          full_change_info.list[inp_check].is_zuida = 1;
          that.setData({
            full_change_info: full_change_info,
            al_goods_prices: res.content.total_select_money,
            // all_goods_doc: res.content.total_select_number,
          });
        }, false);
        return false;
      } else {
        util.showModal("提示", res.message, function () { }, false);
        return false;
      }
    }, { rule_id: rule_id, goods_number: this_goods_number, product_id: full_change_info.goods_list[inp_check].prd_id, goods_id: full_change_info.goods_list[inp_check].goods_id, action: 2 })
  },
  // 加入购物车
  add_to_cart: function (e) {
    var that = this;
    var send_data = {};
    var this_goods_id = e.currentTarget.dataset.goods_id;
    send_data.goods_id = this_goods_id;
    send_data.product_id = e.currentTarget.dataset.product_id;
    send_data.action = 0;
    send_data.rule_id = rule_id;
    send_data.user_id = util.getCache('user_id');
    util.api('/api/wxapp/ship/goods/add', function (res) {
      if (res.error == 0) {
        if (res.content.isShowSpec) {
          // res.content.goods.specs = res.content.goods.spec;
          that.setData({ goodsData: res.content.goods })
          that.bindAddCart()
        }
        else {
          util.toast_success('已加入购物车');
          that.setData({
            al_goods_prices: res.content.total_select_money,
          })
        }
      } else {
        util.showModal("提示", res.content, function () { }, false);
        return false;
      }
    }, send_data);
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
    util.api('/api/wxapp/ship/goods/list', function (res) {
      if (res.error == 0) {
        full_info = res.content;
        var full_goods_info = [];
        var full_goods_r = [];
        full_goods_r = full_info.goods.data;
        al_goods_prices = full_info.total_select_money;
        // all_goods_doc = full_info.total_select_number;
        if (full_goods_r.length > 0) {
          full_goods_info = full_goods_r;
          that.setData({
            full_goods_info: that.data.full_goods_info.concat(full_goods_info),
            full_info: full_info,
            al_goods_prices: al_goods_prices,
            // all_goods_doc: all_goods_doc
          })

        }
      } else {
        util.showModal("提示", res.message, function () {
          wx.navigateBack({

          })
        }, false);
        return false;
      }
    }, { rule_id: rule_id, page_no: that.data.page, search: searchText});
  },

  proActionChange: function () {
    this.setData({
      checkMode: true
    })
  },

  to_items: function (e) {
    var goods_id = e.currentTarget.dataset.goods_id;
    util.navigateTo({
      url: '/pages/item/item?goods_id=' + goods_id,
    })
  },
  isZK: function (e) {
    var that = this;
    that.setData({
      zk: !that.data.zk,
    });
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
    console.log(s)
    var gd = this.data.goodsData;
    return {
      goods_id: gd.goods_id,
      rule_id: rule_id,
      action: 0,
      product_id: s.has_spec ? s.select_prd.prd_id : gd.product_id,
      goods_number: s.goods_number,
      store_id: store_id
    };
  },
  addCart(choose_list) {
    var that = this;
    util.api('/api/wxapp/ship/goods/add', function (res) {
      if (res.error == 0) {
        util.toast_success('已加入购物车');
        that.setData({
          al_goods_prices: res.content.total_select_money,
        })
        that.bindCloseSpec();
      } else {
        util.showModal("提示", res.message);
        return false;
      }
    }, choose_list)
  },
  to_rule(e){
    util.navigateTo(
      {
        url: '/pages1/fullshiprule/fullshiprule?rule_id=' + rule_id,
      }
    )
  }
})
function full_request(that) {
  util.api('/api/wxapp/ship/goods/list', function (res) {
    if (res.error == 0) {
      full_info = res.content;
      that.data.last_page = full_info.goods.last_page;
      var full_goods_info = [];
      var full_goods_r = [];
      full_goods_r = full_info.goods.data;
      al_goods_prices = full_info.total_select_money;
      // all_goods_doc = full_info.total_select_number;
      if (full_goods_r.length > 0) {
        full_goods_info = full_goods_r;
        that.setData({
          full_goods_info: full_goods_info,
          full_info: full_info,
          al_goods_prices: al_goods_prices,
          // all_goods_doc: all_goods_doc
        })

      }
    } else {
      util.showModal("提示", res.message, function () {
        wx.navigateBack({

        })
      }, false);
      return false;
    }
  }, { rule_id: rule_id, page_no: that.data.page, search: searchText});
}
