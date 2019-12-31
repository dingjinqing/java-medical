// pages/newsearch/newsearch.js
var app = new getApp();
var util = require('../../utils/util.js');
var imageUrl = app.globalData.imageUrl;
var mobile = util.getCache('mobile');
var hot_info = [];
var search_word = '';
var input_value = "";
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    hot_info: [],
    null_marginTop: "200rpx",
    null_marginBottom: "20rpx",
    footer_color: "#fff",
    search_word: "",
    input_value: "",
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    search_word = '';
    input_value = '';


    hot_info = {
      history_words: ['123', '男装', '女装'],
      search_config: {
        is_open_history: "1",
        is_open_hot_words: "1",
        title_action: "3",
        title_custom: "香奈儿",
        hot_words: ['Q10Viking', '123456', '男鞋']
      }
    };
    if (hot_info.search_config.title_action == 3) {
      search_word = hot_info.search_config.title_custom;
      that.setData({
        search_word: search_word,
        auto_set_word: search_word
      })
      that.setData({
        hot_info: hot_info
      })
    }
    this.selectComponent('#recommend').resetDataList().resetPage().requestData() //推荐商品
    // util.api('/api/wxapp/search/hotwords', function (res) {
    //   if (res.error == 0) {
    //     hot_info = res.content;
    //     if (hot_info.search_config.title_action == 3) {
    //       search_word = hot_info.search_config.title_custom;
    //       that.setData({
    //         search_word: search_word,
    //         auto_set_word: search_word
    //       })
    //     }
    //     that.setData({
    //       hot_info: hot_info
    //     })
    //   } else {
    //     util.showModal("提示", res.message);
    //   }
    // }, { num: 10 })
  },
  bindSearchConfirm: function (e) {
    var search_text = e.detail.value;
    util.jumpLink('/pages1/search/search?keyWords=' + search_text, "redirectTo");
  },
  save_zhi: function (e) {
    search_word = e.detail.value;
    this.setData({
      search_word: search_word
    })
  },
  // 自定义的时候获取焦点
  chang_value: function (e) {
    // input_value = hot_info.search_config.title_custom;
    this.setData({
      // input_value: input_value,
      auto_set_word: ''
    })
  },
  change_word: function (e) {
    search_word = e.detail.value;
    this.setData({
      search_word: search_word
    })
  },
  bindSearch: function (e) {
    console.log(2);
    console.log(search_word);
    util.jumpLink('/pages1/search/search?keyWords=' + search_word, "redirectTo")
  },
  to_search: function (e) {
    var search_text = e.currentTarget.dataset.value;
    util.jumpLink('/pages1/search/search?keyWords=' + search_text, "redirectTo")
  },

  clear_value: function (e) {
    console.log(1);
    search_word = "";
    this.data.search_word = "";
    // auto_set_word = "";
    input_value = "";
    console.log(search_word);
    this.setData({
      input_value: '',
      search_word: '',
      auto_set_word: ''
    })
  },
  to_del: function () {
    var that = this;
    util.api('/api/wxapp/search/clearwords', function (res) {
      if (res.error == 0) {
        hot_info.history_words = "";
        that.setData({
          hot_info: hot_info
        })
      }
    }, {});
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
    this.selectComponent('#recommend').requestData()
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})
