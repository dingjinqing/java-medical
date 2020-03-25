// pages/sort/sort.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var sort_info = [];
var sort_menu = []; // 左侧数据
var sort_content = []; // 右侧数据
var menuType; // 菜单类型
var menuId; // 菜单id
var left_height;
var right_height;
var indexs;

global.wxPage({
  // mixins: [spec_mixin],
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    is_reco: 0,// 推荐分类
    is_brand: 0,// 推荐品牌
    all_brand: 0,// 全部品牌
    goodsArry: [], // 内容布局商品列表
    page: 1, // 当前页码
    last_page: 1, //最后页码
    isSingleGoods: 0,// 商品分类
    show_rcommend_brand: 3 // 布局类型(全部品牌分类)
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    var info = wx.getSystemInfoSync();
    var screen_height = wx.getSystemInfoSync().windowHeight;
    var screen_width = info.windowWidth;

    wx.createSelectorQuery().select('.input_waimian').boundingClientRect().exec(function (rect) {
      if (!rect || !rect[0]) return;
      rect = rect[0] ? rect[0] : rect;
      var h = screen_height - rect.height;
      if (that.data.isIpx == 1) {
        h = h - 25
      }
      wx.createSelectorQuery().select('.bottom').boundingClientRect().exec(function (reda) {
        console.log(reda)
        console.log(h)
        if (!reda || !reda[0]) return;
        reda = reda[0] ? reda[0] : reda;
        h = h - reda.height;
        that.setData({
          left_height: h,
          right_height: h
        });
      })
      h = h - wx.getMenuButtonBoundingClientRect().bottom - 8;
      that.setData({
        left_height: h,
        right_height: h
      });
    });

    // 初始化左侧数据
    util.api('/api/wxapp/sort/init', function (res) {
      if (res.error == 0) {
        sort_menu = res.content;
        sort_menu.forEach((item, index) => {
          if (index == 0 && (!item.menuContent || item.menuContent != null || item.menuContent != '')) {
            sort_content = item.menuContent
            item.colors = that.data.comColor;
            item.borderright = "6rpx solid " + that.data.comColor;
            item.backgr = "#ffffff";
          }
          if (item.menuType == 1) {
            item.menuName = '全部品牌';
          }
          if (item.menuType == 5) {
            item.menuName = '推荐分类';
          }
        })
        if (sort_content.menuContentType == 1 || sort_content.menuContentType == 4) {
          // 全部品牌
          that.setData({
            is_brand: 1,
            all_brand: 1,
            is_reco: 0,
            isSingleGoods: 0,
            show_rcommend_brand: 3
          })
        } else if (sort_content.menuContentType == 3) {
          // 推荐品牌
          that.setData({
            is_brand: 1,
            all_brand: 0,
            is_reco: 0,
            isSingleGoods: 0,
            show_rcommend_brand: 2
          })
        } else if (sort_content.menuContentType == 5) {
          // 推荐分类
          that.setData({
            is_reco: 1,
            is_brand: 0,
            all_brand: 0,
            isSingleGoods: 0
          })
        } else if (sort_content.menuContentType == 6) {
          sort_content.menuImg = that.data.imageUrl + '/' + sort_content.menuImg
          // 其他分类
          that.setData({
            is_reco: 0,
            is_brand: 0,
            all_brand: 0,
            isSingleGoods: 0
          })
        } else if (sort_content.menuContentType == 7) {
          // 商品分类
          that.setData({
            isSingleGoods: 1,
            is_reco: 0,
            is_brand: 0,
            all_brand: 0
          })
        }
        that.setData({
          sort_menu: sort_menu,
          sort_content: sort_content,
        });
      }
    });
  },
  // 切换左侧菜单
  changeSort: function (e) {
    menuType = e.currentTarget.dataset.type;
    menuId = e.currentTarget.dataset.id;
    indexs = e.currentTarget.dataset.selected;
    var that = this;
    sort_menu.forEach((item, index) => {
      item.colors = "#333333";
      item.borderright = "6rpx solid #f6f6f6";
      item.backgr = "#f5f5f5";
    })
    sort_menu[indexs].colors = that.data.comColor;
    sort_menu[indexs].borderright = "6rpx solid " + that.data.comColor;
    sort_menu[indexs].backgr = "#ffffff";
    // 获取右侧数据
    util.api('/api/wxapp/sort/get/content', function (res) {
      if (res.error == 0) {
        sort_content = res.content;
        if (sort_content.menuContentType == 1 || sort_content.menuContentType == 4) {
          // 全部品牌
          that.setData({
            is_brand: 1,
            all_brand: 1,
            is_reco: 0,
            isSingleGoods: 0
          })
        } else if (sort_content.menuContentType == 3) {
          // 推荐品牌
          that.setData({
            is_brand: 1,
            all_brand: 0,
            is_reco: 0,
            isSingleGoods: 0
          })
        } else if (sort_content.menuContentType == 5) {
          // 推荐分类
          that.setData({
            is_reco: 1,
            is_brand: 0,
            all_brand: 0,
            isSingleGoods: 0
          })
        } else if (sort_content.menuContentType == 6) {
          sort_content.menuImg = that.data.imageUrl + '/' + sort_content.menuImg
          // 其他分类
          that.setData({
            is_reco: 0,
            is_brand: 0,
            all_brand: 0,
            isSingleGoods: 0
          })
        } else if (sort_content.menuContentType == 7) {
          // 商品分类
          that.setData({
            isSingleGoods: 1,
            is_reco: 0,
            is_brand: 0,
            all_brand: 0,
            goodsArry: sort_content.goodsListMpVos
          })
        }
        that.setData({
          sort_menu: sort_menu,
          sort_content: sort_content,
        });
      }
    }, {
        menuType: menuType,
        menuId: menuId
      })

  },
  // 搜索
  to_search() {
    util.jumpLink('/pages/newsearch/newsearch', 'navigateTo')
  },
  // 头图跳转
  go_tiaozhuan: function (e) {
    var link = e.currentTarget.dataset.link;
    util.navigateTo({ url: link })
  },
  // 二级分类跳转
  go_search: function (e) {
    var sort_ids = e.currentTarget.dataset.sord_ids;
    var sort_names = e.currentTarget.dataset.sort_names;
    util.navigateTo({
      url: `/pages1/search/search?sortIds=${JSON.stringify([sort_ids])}`,
    })
  },
  // 推荐分类跳转
  go_where: function (e) {
    var links = e.currentTarget.dataset.link;
    if (links != null && links != "") {
      util.navigateTo({ url: links })
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
    wx.stopPullDownRefresh();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})