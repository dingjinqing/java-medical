// collect.js
/**
 * 商品收藏列表
 * user：常乐
 */
var util = require('../../utils/util.js');
var app = getApp();
var Url = app.globalData.baseUrl;
var imageUrl = app.globalData.imageUrl;
var goods_array = [];
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    collectData: [],
    images: {},
    laquo: '<<',
    lt: '<',
    gt: '>',
    raquo: '>>',
    page: 1,
    collectData_no: false,
    main_url: app.globalData.baseUrl,
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // if (!util.check_setting(options)) return;
    var that = this;
    wx.hideShareMenu();
    that.data.user_id = util.getCache('user_id');
    that.requestCollect(that);
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
   * 图片加载
   */
  imageLoad: function (e) {
    var width = e.detail.width,    //获取图片真实宽度
      height = e.detail.height,
      ratio = width / height;    //图片的真实宽高比例
    var viewWidth = 80,           //设置图片显示宽度，左右留有16rpx边距
      viewHeight = 80 / ratio;    //计算的高度值

    var image = this.data.images;
    //将图片的datadata-index作为image对象的key,然后存储图片的宽高值
    image = {
      width: viewWidth,
      height: viewHeight
    }
    this.setData({
      images: image
    })
  },
  /**
     * 取消收藏
     */
  cancelCollect: function (e) {
    console.log(232323)
    var that = this;
    var id = e.currentTarget.dataset.id;
    util.api('/api/wxapp/collect/cancle', function (res1) {
      that.requestCollect(that);
    }, { id:id });
  },
  go_index: function () {
    util.reLaunch({
      url: '/pages/index/index',
    })
  },
  requestCollect: function (that) {
    util.api('/api/wxapp/collect/list', function (rest) {
      console.log(rest)
      if (rest.error == 0) {
        goods_array = rest.content.dataList;
        // if (rest.content.dataList.length > 0) {
        //   console.log(123)
        //   for (var i = 0; i < rest.content.dataList.length; i++) {
        //     if (parseFloat(rest.content.dataList[i].shop_price) < parseFloat(rest.content.dataList[i].goods_price)) {
        //       rest.content.dataList[i].less_money = parseFloat(rest.content.dataList[i].goods_price) - parseFloat(rest.content.dataList[i].shop_price);
        //       rest.content.dataList[i].less_money = parseFloat(rest.content.dataList[i].less_money).toFixed(2)
        //     }
        //   }
          that.setData({
            data: rest.content.dataList,
            collectData_no: false
          })

        // } else {
          // that.setData({
          //   collectData_no: true
          // })
        }
      // }
    }, { pageNo: that.data.page });
  },
  goodsItem: function (e) {
    var goods_id = e.currentTarget.dataset.goods_id;
    util.navigateTo({
      url: '../item/item?good_id=' + goods_id
    })
  },
  goodspinItem: function (e) {
    var goods_ids = e.currentTarget.dataset.goods_id;
    var pin_group_id = e.currentTarget.dataset.group_id;
    util.navigateTo({
      url: '/pages/groupbuyitem/groupbuyitem?pin_group_id=' + pin_group_id,
    })
  },
});

