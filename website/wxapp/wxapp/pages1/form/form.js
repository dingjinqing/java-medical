// pages/test/test.js
var util = require("../../utils/util.js")
var decorate = require("../../pages/common/decorate.js")

global.wxPage({
  mixins: [decorate],
  onLoad: function(options) {
    this._options = options;
    this.requestPageData();
  },
  requestPageData() {
    var _this = this;
    this.requestFormPageData(this._options.page_id, this.renderForm.bind(this));
  },
  renderForm(pageContent) {
    if (pageContent.can_submit.status > 0) {
      this.setData({ pageContent })
      return;
    }
    this.setData({
      page_name: pageContent.page_name
    })
    if (!!pageContent.form_cfg && pageContent.form_cfg.has_bottom == 1 && !this.data.show_bottom) {
      this.setData({
        show_back: false,
        show_bottom: true
      });
    }
    util.api('/api/wxapp/user_goods/record', () => {}, {
      goods_id: this._options.page_id,
      active_type: 6,
      active_id: this._options.page_id,
      type: 1
    })
    this.setData({
      pageContent: pageContent
    });
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
    // 当处理完数据刷新后，wx.stopPullDownRefresh可以停止当前页面的下拉刷新
    wx.stopPullDownRefresh();
  },


  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {
    util.api("/api/wxapp/share/record", function(d) {}, {
      activity_id: this._options.page_id,
      activity_type: 6
    });
    return {
      path: "pages/form/form?page_id=" + this._options.page_id + "&invite_id=" + util.getCache('user_id'),
    }
  },
})
