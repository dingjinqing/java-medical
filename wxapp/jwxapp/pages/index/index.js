// pages/index/index.js
var util = require('../../utils/util.js');
var decorate = require('../common/decorate.js');
global.wxPage({
  mixins: [decorate],
  /**
   * 页面的初始数据
   */
  data: {},

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    console.log(options);
    // this._options = options;
    this.requestDecoratePageData(
      this._options.page,
      this._options.scene,
      this.renderData.bind(this),
      true
    );
  },
  //  渲染装修模块
  renderData(pageContent) {
    console.log(pageContent);

    this.setData({
      pageContent: pageContent
    });
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {},

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {},

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {},

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {},

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {},

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {},

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {}
});
