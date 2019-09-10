var util = require('../../utils/util.js')
var spec_mixin = require("../../pages/goodscommon/spec.js")
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var gift_list = [];
var is_load;
var gift_id;
var gooded_num;
var total_money;
var modal_gift_info = [];
var searchText = '';
var selected_info = [];
// 是否显示删除按钮 默认0 不显示
var can_del = 0;
global.wxPage({
  mixins: [spec_mixin],
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    page: 1,
    last_page: 1,
    gift_list: [],
    is_load: 0,
    gift_id:'',
    modal_gift_info:[],
    searchText: "",
    changeMove: true,
    selected_info:[],
    can_del:0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    gift_id = options.gift_id;
    searchText = "";
    list_request(that);
  },
  // 搜索
  searchGoods: function (e) {
    searchText = e.detail.value;
    var that = this;
    that.setData({
      searchText: searchText
    })
    list_request(that);
  },
  // 添加商品
  add_goods:function(e){
    var goods_id = e.currentTarget.dataset.goods_id;
    var that = this;
    let choose_goods_info = this.data.gift_list.find(obj => {return obj.goods_id == goods_id});
    console.log(choose_goods_info);
    if (choose_goods_info.is_card_exclusive == 1){
      if (choose_goods_info.card_can == false) {
        if (choose_goods_info.buy_card_list.length <= 0 && choose_goods_info.exclusive_grade_card != null) {
          util.showModal('提示', '您当前的会员等级不满足，仅拥有“' + choose_goods_info.exclusive_grade_card.card_name + '”等级卡用户可购买此商品。可在“个人中心”查看会员卡权益');
          return false;
        }
        util.showModal('提示', '本商品为会员专享商品，开通会员即可购买', function () {
          let goods_id = choose_goods_info.goods_id;
          if (choose_goods_info.buy_card_list != 'undefined' && choose_goods_info.buy_card_list.length == 1) {
            util.navigateTo({
              url: '/pages/usercardinfo/usercardinfo?card_list=1&card_id=' + choose_goods_info.buy_card_list[0].id + '&gift_id=' + gift_id,
            })
          } else {
            util.navigateTo({
              url: '/pages/buycardlist/buycardlist?goods_id=' + goods_id + '&gift_id=' + gift_id,
            })
          }
        }, true, '取消', '开通会员')
        return false;
      }
      if (choose_goods_info.card_can == true) {
        let count = 0;
        if (choose_goods_info.buy_card_list.length != 0) {
          for (var i = 0, buy_card_list = choose_goods_info.buy_card_list; i < buy_card_list.length; i++) {
            if (buy_card_list[i].status == 1) {
              count++;
              break;
            }
          }
        }
        if (choose_goods_info.exclusive_grade_card && ((choose_goods_info.user_grade_card != null && choose_goods_info.user_grade_card.activation == 1 && choose_goods_info.user_grade_card.activation_time != null) || (choose_goods_info.user_grade_card && choose_goods_info.user_grade_card.activation == 0))) {
          count++;
        }
        if (count == 0) {
          util.showModal('提示', '请激活会员卡', function () {
            util.navigateTo({
              url: '/pages/usercardlist/usercardlist',
            })
          });
          return false;
        }
      }
    }
    
    
    util.api("/api/wxapp/givegift/add", function (res) {
        if(res.error == 0){
          modal_gift_info = res.content;
          if(modal_gift_info.is_show_spec == 1){
            modal_gift_info.goods.specs = modal_gift_info.goods.spec;
            that.setData({ goodsData: modal_gift_info.goods })
            that.bindAddGoods()
          }else{
            total_money = parseFloat(modal_gift_info.total_price).toFixed(2);
            util.toast_success('已添加商品');
            that.setData({
              gooded_num: modal_gift_info.goods_num,
              total_money: total_money
            })
          }
        }else{
          util.showModal('提示', res.message, function () {
            util.jumpLink("/pages/index/index", 'redirectTo')
          }, false);
          return false;
        }
    },{gift_id:gift_id,goods_id:goods_id})
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
      gift_id: gift_id,
      product_id: s.has_spec ? s.select_prd.prd_id : gd.prd_id,
      prd_number: s.goods_number,
      goods_id: gd.goods_id
    };
  },
  addGoods(choose_list) {
    var that = this;
    util.api("/api/wxapp/givegift/add", function (res) {
      if (res.error == 0) {
        modal_gift_info = res.content;
        total_money = parseFloat(modal_gift_info.total_price).toFixed(2);
        util.toast_success('已添加商品');
        that.setData({
          gooded_num: modal_gift_info.goods_num,
          total_money: total_money
        })
        that.bindCloseSpec();
      } else {
        util.showModal('提示', res.message, function () {
          util.jumpLink("/pages/index/index", 'redirectTo')
        }, false);
        return false;
      }
    }, { gift_id: choose_list.gift_id, product_id: choose_list.product_id,prd_number:choose_list.prd_number,goods_id:choose_list.goods_id,is_add:1 })
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
    util.api("/api/wxapp/common/removegoods", function (res) {
      if (res.error == 0) {
        util.api('/api/wxapp/givegift/checkedlist', function (res) {
          if (res.error == 0) {
            selected_info = res.content;
            gooded_num = res.content.count;
            if (gooded_num != 0) {
              can_del = 1;
              that.data.can_del = 1;
            } else {
              can_del = 0;
              that.data.can_del = 0;
            }
            for (let i = 0; i < selected_info.list.length; i++) {
              if (selected_info.list[i].goods_number == 0) {
                selected_info.list[i].if_min = 1
              } else {
                selected_info.list[i].if_min = 0
              }
              if (selected_info.list[i].goods_number >= selected_info.list[i].prd_number) {
                selected_info.list[i].if_max = 1;
              } else {
                selected_info.list[i].if_max = 0;
              }
            }
            that.setData({
              selected_info: selected_info,
              changeMove: false,
              gooded_num: gooded_num,
              can_del: can_del
            })
          } else {
            util.showModal('提示', res.message, function () {
              util.jumpLink("/pages/index/index", 'redirectTo')
            }, false);
            return false;
          }
        }, {gift_id: gift_id});
      } else {
        util.showModal("提示", res.message, function () {
          util.jumpLink("pages/index/index", 'redirectTo')
        }, false);
        return false;
      }
    }, {
        identity_id: gift_id,
        param_id: record_id,
        action: "give_gift"
      })
  },
  // 已选商品查看以及修改数量
  showGoods: function (e) {
    var that = this;
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    util.api('/api/wxapp/givegift/checkedlist', function (res) {
      if (res.error == 0) {
        selected_info = res.content;
        can_del = 0;
        that.data.can_del = 0;
        for (let i = 0; i < selected_info.list.length;i++){
          if (selected_info.list[i].goods_number == 0){
            selected_info.list[i].if_min = 1
          }else{
            selected_info.list[i].if_min = 0
          }
          if (selected_info.list[i].goods_number >= selected_info.list[i].prd_number ){
            selected_info.list[i].if_max = 1;
          }else{
            selected_info.list[i].if_max = 0;
          }
        }
        that.setData({
          selected_info: selected_info,
          changeMove: false,
          can_del: can_del
        })
      } else {
        util.showModal('提示', res.message, function () {
          util.jumpLink("/pages/index/index", 'redirectTo')
        }, false);
        return false;
      }
    }, {

        gift_id:gift_id,
        form_id: form_id,
        open_id: open_id
      });
  },
  proActionChange: function () {
    var that = this;
    that.setData({
      changeMove: true
    })
  },
  // 已选商品减少
  goods_jian:function(e){
    let select_num = e.currentTarget.dataset.num;
    let prds_nums = e.currentTarget.dataset.spec;
    let index_father = e.currentTarget.dataset.index1;
    let goods_id = e.currentTarget.dataset.goods_id;
    let prd_id = e.currentTarget.dataset.prd_id;
    let that = this;
    selected_info.list[index_father].goods_number = parseFloat(select_num) - 1;
    var goods_num = selected_info.list[index_father].goods_number;
    if (selected_info.list[index_father].goods_number < 0) {
      selected_info.list[index_father].if_min = 1;
      selected_info.list[index_father].goods_number = 0;
      goods_num = 0;
    }
    if (selected_info.list[index_father].goods_number < selected_info.list[index_father].prd_number) {
      selected_info.list[index_father].if_max = 0;
    }
    that.setData({
      selected_info: selected_info
    })
    var choose_list = {};
    choose_list.goods_id = goods_id;
    choose_list.prd_id = prd_id;
    choose_list.goods_num = goods_num;
    this.addRequest(that, choose_list)
    
  },
  // 已选商品增加
  goods_add:function(e){
    let select_num = e.currentTarget.dataset.num;
    let prds_nums = e.currentTarget.dataset.spec;
    let index_father = e.currentTarget.dataset.index1;
    let goods_id = e.currentTarget.dataset.goods_id;
    let prd_id = e.currentTarget.dataset.prd_id;
    let that = this;
    selected_info.list[index_father].goods_number = parseFloat(select_num) + 1;
    var goods_num = selected_info.list[index_father].goods_number;
    if (selected_info.list[index_father].goods_number > 0){
      selected_info.list[index_father].if_min = 0
    }
    if (selected_info.list[index_father].goods_number > selected_info.list[index_father].prd_number) {
      util.showModal("提示", '库存不足');
      selected_info.list[index_father].if_max = 1;
      selected_info.list[index_father].goods_number = selected_info.list[index_father].prd_number;
      goods_num = selected_info.list[index_father].prd_number
    }
    that.setData({
      selected_info: selected_info
    })
    var choose_list = {};
    choose_list.goods_id = goods_id;
    choose_list.prd_id = prd_id;
    choose_list.goods_num = goods_num;
    this.addRequest(that, choose_list)
  },
  change_select_num:function(e){
    let select_num = e.detail.value;
    let prds_nums = e.currentTarget.dataset.spec;
    let index_father = e.currentTarget.dataset.index1;
    let goods_id = e.currentTarget.dataset.goods_id;
    let prd_id = e.currentTarget.dataset.prd_id;
    let that = this;
    selected_info.list[index_father].goods_number = select_num;
    var goods_num = selected_info.list[index_father].goods_number;
    if (selected_info.list[index_father].goods_number < 0){
      selected_info.list[index_father].goods_number = 1;
      selected_info.list[index_father].if_min = 0;
      goods_num = 0;
    }
    if (selected_info.list[index_father].goods_number > 0) {
      selected_info.list[index_father].if_min = 0;
    }
    if (selected_info.list[index_father].goods_number > selected_info.list[index_father].prd_number ){
      util.showModal("提示", '库存不足');
      selected_info.list[index_father].if_max = 1;
      selected_info.list[index_father].goods_number = selected_info.list[index_father].prd_number;
      goods_num = selected_info.list[index_father].prd_number
    }
    that.setData({
      selected_info: selected_info
    })
    var choose_list = {};
    choose_list.goods_id = goods_id;
    choose_list.prd_id = prd_id;
    choose_list.goods_num = goods_num;
    this.addRequest(that, choose_list)
  },
  addRequest(that, choose_list) {
    util.api("/api/wxapp/givegift/add", function (res) {
      if (res.error == 0) {
        // util.toast_success('成功');
        util.api('/api/wxapp/givegift/checkedlist', function (res) {
          if (res.error == 0) {
            selected_info = res.content;
            for (let i = 0; i < selected_info.list.length; i++) {
              if (selected_info.list[i].goods_number == 0) {
                selected_info.list[i].if_min = 1
              } else {
                selected_info.list[i].if_min = 0
              }
              if (selected_info.list[i].goods_number >= selected_info.list[i].prd_number) {
                selected_info.list[i].if_max = 1;
              } else {
                selected_info.list[i].if_max = 0;
              }
            }
            that.setData({
              selected_info: selected_info,
              changeMove: false,
            })
          } else {
            util.showModal('提示', res.message, function () {
              util.jumpLink("/pages/index/index", 'redirectTo')
            }, false);
            return false;
          }
        }, {
            gift_id: gift_id,
          });
        var jine = parseFloat(res.content.total_price).toFixed(2);
        that.setData({
          gooded_num: res.content.goods_num,
          total_money: jine
        })
      } else {
        util.showModal('提示', res.message, function () {
          util.jumpLink("/pages/index/index", 'redirectTo')
        }, false);
        return false
      }
    }, { gift_id: gift_id, goods_id: choose_list.goods_id, product_id: choose_list.prd_id, prd_number: choose_list.goods_num })
  },
  // 确定
  go_check:function(){
    util.jumpLink("/pages1/presentinfo/presentinfo?gift_id=" + gift_id)
  },
  to_refresh:function(){
    var that = this;
    this.setData({
      changeMove:true
    })
    list_request(that)
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
    that.setData({
      is_load: 1
    })
    if (that.data.page == that.data.last_page) {
      that.setData({
        is_load: 0
      })
      return;
    }
    var pageNo = that.data.page + 1;
    util.api("/api/wxapp/givegift/goodslist", function (res) {
      if (res.error == 0) {
        var giftL = res.content;
        var gift_list = [];
        gooded_num = giftL.goods_num;
        total_money = parseFloat(giftL.total_price).toFixed(2);
        if (giftL.goods_list.data.length > 0) {
          gift_list = giftL.goods_list.data;
        }
        that.setData({
          gift_list: that.data.gift_list.concat(gift_list),
          gooded_num: gooded_num,
          total_money: total_money,
        })
      } else {
        util.showModal('提示', res.message, function () {
          util.jumpLink("/pages/index/index", 'redirectTo')
        }, false);
        return false;
      }
    }, { gift_id: gift_id, page: that.data.page, search: searchText })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})
function list_request(that){
  util.api("/api/wxapp/givegift/goodslist",function(res){
    if(res.error == 0){
      var giftL = res.content;
      var gift_list = [];
      gooded_num = giftL.goods_num;
      total_money = parseFloat(giftL.total_price).toFixed(2);
      that.data.last_page = giftL.goods_list.last_page;
      if (giftL.goods_list.data.length > 0) {
        gift_list = giftL.goods_list.data;
      }
      that.setData({
        gift_list: gift_list,
        gooded_num: gooded_num,
        total_money: total_money,
      })
    }else{
      util.showModal('提示', res.message, function () {
        util.jumpLink("/pages/index/index", 'redirectTo')
      }, false);
      return false;
    }
  }, { gift_id: gift_id, search: searchText, page: that.data.page})
}