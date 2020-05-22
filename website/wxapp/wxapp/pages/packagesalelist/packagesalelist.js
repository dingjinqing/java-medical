// pages/packagesalelist/packagesalelist.js
var util = require('../../utils/util.js')
var spec_mixin = require("../goodscommon/spec.js")
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var is_load = 0;
// 页面信息，商品信息，商品组信息
var sale_info = [];
var sale_list = [];
var tab_list = [];
var tab_len = 0;
// 搜索
var searchText = "";
// 添加商品按钮
var modal_goods_info = [];
// 已选商品框
var selected_info = [];
var total_number = 0;
var total_money = 0;
var group_id;
var package_id;
var set_num;
// 是否显示删除按钮 默认0 不显示
var can_del = 0;
var spec_list = {};
var prd_list = {};
var spec_view = 0;
var spec_check = 0;
var spec_name;
var speclist = [];
var is_max = 0;
var is_min = 0;
var action = 0;
var can_bug = 0;
var if_min = 0;
var if_max = 0;

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
    changeMove: true,
    page: 1,
    last_page: 1,
    is_load: 0,
    tab_len: 0,
    sale_info: [],
    sale_list: [],
    tab_list: [],
    searchText: "",
    sort_name: "",
    sort_order: "desc",
    modal_goods_info: [],
    selected_info: [],
    total_number: 0,
    total_money: 0,
    group_id: 1,
    package_id:"",
    set_num: "1",
    can_del: 0,
    specMove: true,
    spec_view: 0,
    speclist: [],
    can_bug: 0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    if (!util.check_setting(options)) return;
    package_id = options.package_id;
    var that = this;
    wx.hideShareMenu();
    searchText = "";
    var sort_order = that.data.sort_order;
    var sort_name = that.data.sort_name;
    group_id = 1;
    can_bug = 0;
    sale_request(that);
  },
  // 搜索
  searchGoods: function(e) {
    searchText = e.detail.value;
    var that = this;
    that.setData({
      searchText: searchText
    })
    sale_request(that);
  },
  // 销量和价格
  switchOrder: function(e) {
    var sort_order = this.data.sort_order;
    var sort_name = this.data.sort_name;
    var now_sort_name = e.target.dataset.type;
    if (now_sort_name != sort_name) {
      if (now_sort_name != "shop_price") {
        this.setData({
          sort_name: "goods_sale_num",
          now_sort_name: "goods_sale_num",
          sort_order: "desc"
        });
      } else {
        this.setData({
          sort_name: "shop_price",
          now_sort_name: "shop_price",
          sort_order: "asc"
        });
      }
    } else {
      if (sort_order == "desc") {
        this.setData({
          sort_order: "asc"
        });
      } else {
        this.setData({
          sort_order: "desc"
        });
      }
    }
    sale_request(this);
  },
  // 选择商品
  add_goods: function(e) {
    var goods_id = e.currentTarget.dataset.goods_id;
    var that = this;
    util.api("/api/wxapp/package/goods/add", function(res) {
      if (res.error == 0) {
        modal_goods_info = res.content;
        modal_goods_info.total_select_money = parseFloat(modal_goods_info.total_select_money).toFixed(2);
        if (modal_goods_info.tab_list) {
          that.setData({
            tab_list: modal_goods_info.tab_list,
          })
        }
        if (res.content.isShowSpec == 1) {
          action = 1;
          // console.log(that.getNowGroup())
          // return false;
          var now_group = that.getNowGroup();
          res.content.goods.limit_max_num = now_group.goods_number - now_group.select_number
          res.content.goods.specs = res.content.goods.spec;
          that.setData({ goodsData: res.content.goods })
          that.bindAddGoods()
          
        } else {
          if (modal_goods_info.select_goods_number == sale_info.total_goods_number) {
            can_bug = 1;
          }
          total_number = modal_goods_info.select_goods_number;
          action = 0;
          util.toast_success('已添加商品');
          that.setData({
            modal_goods_info: modal_goods_info,
            total_number: modal_goods_info.select_goods_number,
            total_money: modal_goods_info.total_select_money,
            can_bug: can_bug
          })
          that.onPullDownRefresh();
        }
      } else {
        util.showModal("提示", res.content);
        return false;
      }
    }, {
      package_id: package_id,
      group_id: that.data.group_id,
      action: 0,
      product_id: '',
      goods_number: 1,
      goods_id: goods_id
    })
  },
  getNowGroup(){
    for (var index in this.data.tab_list) {
      if (this.data.tab_list[index].group_id == this.data.group_id){
        return this.data.tab_list[index]
      }
    }
    return this.data.tab_list[1];
  },
  
  // 切换商品组
  change_tab: function(e) {
    var that = this;
    that.data.group_id = e.currentTarget.dataset.group_id;
    group_id = e.currentTarget.dataset.group_id;
    searchText = "";
    that.setData({
      searchText:searchText
    })
    sale_request(that);
  },
  // 产看已选商品
  showGoods: function(e) {
    var that = this;
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    util.api('/api/wxapp/package/select/list', function(res) {
      if (res.error == 0) {
        selected_info = res.content;
        var other_goods = parseInt(sale_info.total_goods_number - total_number);
        can_del = 0;
        that.data.can_del = 0;
        for (var i in selected_info.goods_list) {
          for (var j in selected_info.goods_list[i].select_goods) {
            if (selected_info.goods_list[i].select_goods[j].goods_number == 1) {
              selected_info.goods_list[i].select_goods[j].if_min = 1;
            } else {
              selected_info.goods_list[i].select_goods[j].if_min = 0;
            }
            if (selected_info.goods_list[i].select_goods[j].goods_number >= selected_info.goods_list[i].select_goods[j].prd_number) {
              selected_info.goods_list[i].select_goods[j].if_max = 1;
            } else {
              selected_info.goods_list[i].select_goods[j].if_max = 0;
            }
            if (selected_info.goods_list[i].select_goods[j].goods_number == tab_list[i].goods_number){
              selected_info.goods_list[i].select_goods[j].if_max = 1;
            }
          }
        }
        that.setData({
          selected_info: selected_info,
          other_goods: other_goods,
          changeMove: false,
          can_del: can_del,

        })
      } else {
        util.showModal("提示", res.content);
        return false;
      }
    }, {

      package_id: package_id,
        form_id: form_id,
        open_id: open_id
    });
  },
  proActionChange: function() {
    var that = this;
    that.setData({
      changeMove: true
    })
    that.onPullDownRefresh();
  },
  // 删除已选商品
  to_del_goods: function() {
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
  to_del_geted: function(e) {
    var goods_num = e.currentTarget.dataset.goods_number;
    var record_id = e.currentTarget.dataset.record_id;
    var that = this;
    wx.showLoading({
      title: '删除中...',
    })
    util.api("/api/wxapp/package/select/change", function(res) {
      if (res.error == 0) {
        wx.hideLoading();
        selected_info = res.content;
        total_number = selected_info.select_goods_number;
        var other_goods = parseInt(sale_info.total_goods_number - total_number);
        if (other_goods != 0){
          can_bug = 0;
          that.data.can_bug = 0;
          that.setData({
            can_bug:can_bug
          })
        }
        for (var i in selected_info.goods_list) {
          for (var j in selected_info.goods_list[i].select_goods) {
            if (selected_info.goods_list[i].select_goods[j].goods_number == 1) {
              selected_info.goods_list[i].select_goods[j].if_min = 1;
            } else {
              selected_info.goods_list[i].select_goods[j].if_min = 0;
            }
            selected_info.goods_list[i].select_goods[j].if_max = 0;
          }
        }
        if (total_number == 0) {
          can_del = 0;
          that.data.can_del = 0;
          that.setData({
            can_del: can_del
          })
        }
        that.setData({
          selected_info: selected_info,
          other_goods: other_goods,
          total_number: total_number,
          total_money: selected_info.total_select_money
        })
      } else {
        util.showModal("提示", res.content);
        return false;
      }
    }, {

      record_id: record_id,
      goods_number: goods_num,
      action: "delete_goods"
    })
  },
  to_refresh: function() {
    var that = this;
    that.setData({
      changeMove: true
    })
    that.onPullDownRefresh();
  },
  // 去普通商品详情页面
  to_items: function(e) {
    var goods_id = e.currentTarget.dataset.goods_id;
    util.navigateTo({
      url: '/pages/item/item?good_id=' + goods_id,
    })
  },
  // 切换已选商品数量
  goods_jian: function(e) {
    var select_num = e.currentTarget.dataset.num;
    var prds_nums = e.currentTarget.dataset.spec;
    var index_father = e.currentTarget.dataset.index1;
    var index_child = e.currentTarget.dataset.index2;
    var record_id = e.currentTarget.dataset.record_id;
    var that = this;
    selected_info.goods_list[index_father].select_goods[index_child].goods_number = select_num - 1;
    var goods_num = selected_info.goods_list[index_father].select_goods[index_child].goods_number;
    if (selected_info.goods_list[index_father].select_goods[index_child].goods_number == 0) {
      selected_info.goods_list[index_father].select_goods[index_child].if_min = 1;
      selected_info.goods_list[index_father].select_goods[index_child].goods_number = 1;
      goods_num = 1;
    }
    if (selected_info.goods_list[index_father].select_goods[index_child].goods_number < selected_info.goods_list[index_father].select_goods[index_child].prds_nums){
      selected_info.goods_list[index_father].select_goods[index_child].if_min = 0;
    }
    that.setData({
      selected_info: selected_info
    })
    util.api("/api/wxapp/package/select/change", function(res) {
      if (res.error == 0) {
        selected_info = res.content;
        total_number = selected_info.select_goods_number;
        var other_goods = parseInt(sale_info.total_goods_number - total_number);
        if(other_goods != 0){
          can_bug = 0;
          that.data.can_bug = 0;
          that.setData({
            can_bug: can_bug
          })
        }
        for (var i in selected_info.goods_list) {
          for (var j in selected_info.goods_list[i].select_goods) {
            if (selected_info.goods_list[i].select_goods[j].goods_number == 1){
              selected_info.goods_list[i].select_goods[j].if_min = 1;
            }else{
              selected_info.goods_list[i].select_goods[j].if_min = 0;
            }
            selected_info.goods_list[i].select_goods[j].if_max = 0;
          }
        }
        that.setData({
          selected_info: selected_info,
          other_goods: other_goods,
          total_number: total_number,
          total_money: selected_info.total_select_money
        })
      } else {
        util.showModal("提示", res.content);
        return false;
      }
    }, {
      record_id: record_id,
      goods_number: goods_num,
      action: "change_number"
    })
  },
  goods_add:function(e){
    var select_num = e.currentTarget.dataset.num;
    var prds_nums = e.currentTarget.dataset.spec;
    var index_father = e.currentTarget.dataset.index1;
    var index_child = e.currentTarget.dataset.index2;
    var record_id = e.currentTarget.dataset.record_id;
    var that = this;
    selected_info.goods_list[index_father].select_goods[index_child].goods_number = select_num + 1;
    var goods_num = selected_info.goods_list[index_father].select_goods[index_child].goods_number;
    if (selected_info.goods_list[index_father].select_goods[index_child].goods_number > prds_nums){
      util.showModal("提示", '库存不足');
      selected_info.goods_list[index_father].select_goods[index_child].if_max = 1;
      selected_info.goods_list[index_father].select_goods[index_child].goods_number = prds_nums;
    }
    that.setData({
      selected_info: selected_info
    })
    util.api("/api/wxapp/package/select/change", function (res) {
      if (res.error == 0) {
        selected_info = res.content;
        total_number = selected_info.select_goods_number;
        var other_goods = parseInt(sale_info.total_goods_number - total_number);
        if (other_goods != 0) {
          can_bug = 0;
          that.data.can_bug = 0;
          that.setData({
            can_bug: can_bug
          })
        }
        for (var i in selected_info.goods_list) {
          for (var j in selected_info.goods_list[i].select_goods) {
            if (selected_info.goods_list[i].select_goods[j].goods_number == 1) {
              selected_info.goods_list[i].select_goods[j].if_min = 1;
            } else {
              selected_info.goods_list[i].select_goods[j].if_min = 0;
            }
            if (selected_info.goods_list[i].select_goods[j].goods_number >= selected_info.goods_list[i].select_goods[j].prd_number){
              selected_info.goods_list[i].select_goods[j].if_max = 1;
            }else{
              selected_info.goods_list[i].select_goods[j].if_max = 0;
            }

          }
        }
        that.setData({
          selected_info: selected_info,
          other_goods: other_goods,
          total_number: total_number,
          total_money: selected_info.total_select_money
        })
      } else {
        util.showModal("提示", res.content, function () {
          selected_info.goods_list[index_father].select_goods[index_child].goods_number = selected_info.goods_list[index_father].select_goods[index_child].goods_number - 1;
          selected_info.goods_list[index_father].select_goods[index_child].if_max = 1;
          that.setData({
            selected_info: selected_info,
          })
         }, false);
        return false;
      }
    }, {
        record_id: record_id,
        goods_number: goods_num,
        action: "change_number"
      })
  },
  change_select_num:function(e){
    var select_num = e.detail.value;
    var prds_nums = e.currentTarget.dataset.spec;
    var index_father = e.currentTarget.dataset.index1;
    var index_child = e.currentTarget.dataset.index2;
    var record_id = e.currentTarget.dataset.record_id;
    var that = this;
    selected_info.goods_list[index_father].select_goods[index_child].goods_number = select_num;
    var goods_num = selected_info.goods_list[index_father].select_goods[index_child].goods_number;
    if (selected_info.goods_list[index_father].select_goods[index_child].goods_number > prds_nums) {
      util.showModal("提示", '库存不足');
      selected_info.goods_list[index_father].select_goods[index_child].if_max = 1;
      selected_info.goods_list[index_father].select_goods[index_child].goods_number = prds_nums;
    }
    that.setData({
      selected_info: selected_info
    })
    util.api("/api/wxapp/package/select/change", function (res) {
      if (res.error == 0) {
        selected_info = res.content;
        total_number = selected_info.select_goods_number;
        var other_goods = parseInt(sale_info.total_goods_number - total_number);
        if (other_goods != 0) {
          can_bug = 0;
          that.data.can_bug = 0;
          that.setData({
            can_bug: can_bug
          })
        }
        for (var i in selected_info.goods_list) {
          for (var j in selected_info.goods_list[i].select_goods) {
            if (selected_info.goods_list[i].select_goods[j].goods_number == 1) {
              selected_info.goods_list[i].select_goods[j].if_min = 1;
            } else {
              selected_info.goods_list[i].select_goods[j].if_min = 0;
            }
            if (selected_info.goods_list[i].select_goods[j].goods_number >= selected_info.goods_list[i].select_goods[j].prd_number) {
              selected_info.goods_list[i].select_goods[j].if_max = 1;
            } else {
              selected_info.goods_list[i].select_goods[j].if_max = 0;
            }
            if (selected_info.goods_list[i].select_goods[j].goods_number == tab_list[i].goods_number) {
              selected_info.goods_list[i].select_goods[j].if_max = 1;
            }
          }
        }
        that.setData({
          selected_info: selected_info,
          other_goods: other_goods,
          total_number: total_number,
          total_money: selected_info.total_select_money
        })
      } else {
        util.showModal("提示", res.content, function () {
          util.api('/api/wxapp/package/select/list', function (res) {
            if (res.error == 0) {
              selected_info = res.content;
              var other_goods = parseInt(sale_info.total_goods_number - total_number);
              can_del = 0;
              that.data.can_del = 0;
              for (var i in selected_info.goods_list) {
                for (var j in selected_info.goods_list[i].select_goods) {
                  if (selected_info.goods_list[i].select_goods[j].goods_number == 1) {
                    selected_info.goods_list[i].select_goods[j].if_min = 1;
                  } else {
                    selected_info.goods_list[i].select_goods[j].if_min = 0;
                  }
                  if (selected_info.goods_list[i].select_goods[j].goods_number >= selected_info.goods_list[i].select_goods[j].prd_number) {
                    selected_info.goods_list[i].select_goods[j].if_max = 1;
                  } else {
                    selected_info.goods_list[i].select_goods[j].if_max = 0;
                  }
                }
              }
              that.setData({
                selected_info: selected_info,
                other_goods: other_goods,
                changeMove: false,
                can_del: can_del,

              })
            } else {
              util.showModal("提示", res.content);
              return false;
            }
          }, {

              package_id: package_id,
            });
        }, false);
        return false;
      }
    }, {

        record_id: record_id,
        goods_number: goods_num,
        action: "change_number"
      })
  },
  // 去结算
  go_check: function(e) {
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    if (can_bug == 0) {
      util.showModal("提示", "商品数量未满，请继续选择");
      return false;
    }
    util.api('/api/wxapp/package/checkout',function(res){
      if (res.error == 0) {
        var choose_list = { pin_group_id: package_id };
        util.navigateTo({
          url: '/pages/goodsCheckout/goodsCheckout?order_type=package_sale&choose_list=' + JSON.stringify(choose_list),
        })
      } else {
        util.showModal("提示", res.content);
      }
    },{package_id: package_id,  open_id: open_id,form_id: form_id})
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
    // 显示导航栏loading
    wx.showNavigationBarLoading();
    // 调用接口加载数据
    var that = this;
    sale_request(that);
    wx.hideNavigationBarLoading();
    // 当处理完数据刷新后，wx.stopPullDownRefresh可以停止当前页面的下拉刷新
    wx.stopPullDownRefresh();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    var that = this;
    that.setData({
      is_load: 1
    })
    if (that.data.page == that.data.last_page) {
      that.setData({
        is_load: 0
      })
      return;
    }
    that.data.page = that.data.page + 1;
    var sort_order = that.data.sort_order;
    var sort_name = that.data.sort_name;
    util.api("/api/wxapp/package/goods/list", function(res) {
      if (res.error == 0) {
        sale_info = res.content;
        sale_info.total_select_money = parseFloat(sale_info.total_select_money).toFixed(2);
        if (sale_info.goods.data.length > 0) {
          sale_list = sale_info.goods.data;
        }
        if (sale_info.tab_list != "") {
          tab_list = sale_info.tab_list;
          for (var i in tab_list) {
            if (i < 2) {
              tab_len = 1;
            } else {
              tab_len = 0;
            }
          }
        }
        total_number = sale_info.total_select_number;
        if (total_number == sale_info.total_goods_number) {
          can_bug = 1;
        }
        that.setData({
          sale_info: sale_info,
          sale_list: that.data.sale_list.concat(sale_list),
          tab_list: tab_list,
          tab_len: tab_len,
          can_bug: can_bug,
          total_number: sale_info.total_select_number,
          total_money: sale_info.total_select_money,
        })
      } else {
        util.showModal('提示', res.content)
      }
    }, {

      page_no: that.data.page,
      package_id: package_id,
      group_id: that.data.group_id,
      search: searchText,
      sort_name: that.data.sort_name,
      sort_order: that.data.sort_order
    })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

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
  bindAddGoods() {
    if (!this.checkSelBuy()) return;
    this.addGoods(this.getChooseList());
  },
  getChooseList() {
    var s = this._sel_buy;
    var gd = this.data.goodsData;
    return {
      package_id: package_id,
      group_id: this.data.group_id,
      action: 0,
      product_id: s.has_spec ? s.select_prd.prd_id : gd.prd_id,
      goods_number: s.goods_number,
      goods_id: gd.goods_id
    };
  },
  addGoods(choose_list) {
    var that = this;
    util.api('/api/wxapp/package/goods/add', function (res) {
      if (res.error == 0) {
        modal_goods_info = res.content;
        modal_goods_info.total_select_money = parseFloat(modal_goods_info.total_select_money).toFixed(2);
        if (modal_goods_info.tab_list) {
          that.setData({
            tab_list: modal_goods_info.tab_list,
          })
        }
        if (modal_goods_info.select_goods_number == sale_info.total_goods_number) {
          can_bug = 1;
        }
        total_number = modal_goods_info.select_goods_number;
        action = 1;
        util.toast_success('已添加商品');
        that.setData({
          modal_goods_info: modal_goods_info,
          total_number: modal_goods_info.select_goods_number,
          total_money: modal_goods_info.total_select_money,
          can_bug: can_bug
        })
        that.bindCloseSpec();
        that.onPullDownRefresh();
      } else {
        util.showModal("提示", res.content);
        return false;
      }
    }, choose_list)
   
  },
})

function sale_request(that) {
  util.api("/api/wxapp/package/goods/list", function(res) {
    if (res.error == 0) {
      sale_info = res.content;
      that.data.last_page = sale_info.goods.last_page;
      sale_info.total_select_money = parseFloat(sale_info.total_select_money).toFixed(2);
      if (sale_info.goods.data.length > 0) {
        sale_list = sale_info.goods.data;
      }
      if (sale_info.tab_list != "") {
        tab_list = sale_info.tab_list;
        for (var i in tab_list) {
          if (i < 2) {
            tab_len = 1;
          } else {
            tab_len = 0;
          }
        }
      }
      total_number = sale_info.total_select_number;
      if (total_number == sale_info.total_goods_number) {
        can_bug = 1;
      }
      that.setData({
        sale_info: sale_info,
        sale_list: sale_list,
        tab_list: tab_list,
        tab_len: tab_len,
        can_bug: can_bug,
        total_number: sale_info.total_select_number,
        total_money: sale_info.total_select_money,
      })
    } else {
      util.showModal('提示', res.content)
    }
  }, {

    page_no: that.data.page,
    package_id: package_id,
    group_id: group_id,
    search: searchText,
    sort_name: that.data.sort_name,
    sort_order: that.data.sort_order
  })
}
