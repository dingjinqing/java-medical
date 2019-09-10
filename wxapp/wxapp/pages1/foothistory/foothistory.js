// pages1/foothistory/foothistory.js
var util = require('../../utils/util.js');
var spec_mixin = require("../../pages/goodscommon/spec.js")
var app = getApp()
var Url = app.globalData.baseUrl;
var imageUrl = app.globalData.imageUrl;
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
    page: 1,
    last_page: 1,
    foot_list: [],
    pin_goods:[],
    searchText:"",
    footL:[],
    null_marginTop: "200rpx",
    null_marginBottom: "20rpx",
    footer_color: "#fff",
    isNull:false,
    action:2
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    if(options.action){
      if(options.action == 1){
        that.data.action = 1;
        that.setData({
          page_name : '历史购买'
        })
      }else{
        if(options.action == 2){
          that.data.action = 2;
          that.setData({
            page_name : '我的足迹'
          })
        }
      }
    }
    list_request(that)
  },
  // 阻止背景滚动穿透，勿删
  preventMove() { },
  hasRecommend(e) {
    if (e.detail.hasRecommend) {
      this.setData({
        null_marginTop: "60rpx",
        null_marginBottom: "36rpx",
        footer_color: "#f5f5f5"
      })
    }
  },
  //加购
  bindAddCart(e) {
    var that = this;
    util.api('/api/wxapp/popup/goods/data', function (res) {
      console.log(res)
      if (res.error == 0) {
        that.setData({ goodsData: res.content })
        console.log(that.showSpec(0,1))
      } else {
        util.showModal('提示', res.message);
      }
    }, { goods_id: e.currentTarget.dataset.goods_id })
  },
  toDetail: function (e) {
    var goods_ids = e.currentTarget.dataset.goods_ids;
    var goods_type = e.currentTarget.dataset.goods_type;
    if (goods_type != 1 && goods_type != 3 && goods_type != 5 && goods_type != 10) {
      util.navigateTo({
        url: '/pages/item/item?goods_id=' + goods_ids,
      })
    } else if (goods_type == 1) {
      var pin_group_id = this.data.pin_goods[goods_ids].pin_activity_id;
      util.navigateTo({
        url: '/pages/groupbuyitem/groupbuyitem?pin_group_id=' + pin_group_id,
      })
    } else if (goods_type == 5) {
      var seckill_id = e.currentTarget.dataset.seckill_id;
      util.navigateTo({
        url: '/pages/seckillitem/seckillitem?sk_id=' + seckill_id,
      })
    } else if (goods_type == 3) {
      var is_prd = e.currentTarget.dataset.is_prd;
      var bargain_id = e.currentTarget.dataset.bargain_id;
      if (is_prd == 1) {
        util.navigateTo({
          url: '/pages/bargainitem/bargainitem?bargain_id=' + bargain_id,
        })
      } else if (is_prd == 0) {
        var choose_list = {};
        choose_list.prd_id = e.currentTarget.dataset.prd_id;
        choose_list.goods_id = e.currentTarget.dataset.goods_id;
        choose_list.goods_price = e.currentTarget.dataset.goods_price;
        choose_list.bargain_id = bargain_id;
        choose_list.user_id = util.getCache('user_id');
        util.api("/api/wxapp/bargain/apply", function (res) {
          if (res.error == 0) {
            util.navigateTo({
              url: "/pages/bargaininfo/bargaininfo?record_id=" + res.content.record_id + "&bargain_money=" + res.content.bargain_money
            })
          } else {
            util.showModal('提示', res.content);
          }
        }, {choose_list: JSON.stringify(choose_list)})
      }
    } else if (goods_type == 10) {
      var presale_id = e.currentTarget.dataset.presale_id;
      util.navigateTo({
        url: '/pages/presaleitem/presaleitem?presale_id=' + presale_id,
      })
    }
  },
  // 搜索
  searchInput:function(e){
    var that = this;
    that.data.searchText = e.detail.value;
    that.setData({
      searchText: e.detail.value
    });
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
    wx.showLoading({
      title: '加载中',
    })
    that.setData({
      is_load: 1
    })
    if (that.data.page >= that.data.last_page) {
      that.setData({
        is_load: 0
      })
      wx.hideLoading();
      return;
    }
    that.data.page = that.data.page + 1;
    util.api('/api/wxapp/user/history', function (res) {
      wx.hideLoading();
      if (res.error == 0) {
        var footL = res.content;
        var foot_list = [];
        if (footL.goods.data != '') {
          foot_list = footL.goods.data;
        }
        if (footL.pin_group_goods != '') {
          that.data.pin_goods = footL.pin_group_goods
        }
        that.setData({
          footL: footL,
          foot_list: that.data.foot_list.concat(foot_list),
          pin_goods: that.data.pin_goods,
          isNull: res.content.goods.data == "",
        })
      } else {
        util.showModal('提示', res.message, function () {
          util.jumpLink("/pages/index/index", 'redirectTo')
        }, false);
        return false;
      }
    }, { page_no: that.data.page, search: that.data.searchText, action: that.data.action })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})
function list_request(that) {
  util.api('/api/wxapp/user/history', function (res) {
    if (res.error == 0) {
      var footL = res.content;
      var foot_list = [];
      that.data.last_page = footL.goods.last_page;
      if (footL.goods.data != ''){
        foot_list = footL.goods.data;
      }
      if (footL.pin_group_goods != ''){
        that.data.pin_goods = footL.pin_group_goods
      }
      that.setData({
        footL: footL,
        foot_list: foot_list,
        pin_goods: that.data.pin_goods,
        isNull: res.content.goods.data=="",
      })
    } else {
      util.showModal('提示', res.message, function () {
        util.jumpLink("/pages/index/index", 'redirectTo')
      }, false);
      return false;
    }
  }, { page_no: that.data.page, search: that.data.searchText, action: that.data.action })
}