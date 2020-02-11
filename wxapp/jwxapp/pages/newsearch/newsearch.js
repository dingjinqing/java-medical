// pages/newsearch/newsearch.js
var app = new getApp();
var util = require('../../utils/util.js');
var imageUrl = app.globalData.imageUrl;
var mobile = util.getCache('mobile');
var userId = util.getCache('user_id');
var search_word = '';
var input_value = "";
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    null_marginTop: "200rpx",
    null_marginBottom: "20rpx",
    footer_color: "#fff",
    search_word: "", // 自定义搜索内容
    input_value: "",

    history_words: [], // 搜索历史
    search_config: [] // 搜索配置
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    search_word = '';
    input_value = '';

    // 获取历史搜索
    util.api('/api/wxapp/search/userSearchHot', function (res) {
      if (res.error == 0) {
        that.setData({
          history_words: res.content
        })
      } else {
        util.showModal("提示", res.message);
      }
    }, { 
      userId: userId,
      num: 10 
    })

    // 获取搜索配置, 热词搜索
    util.api('/api/wxapp/search/config', function (res) {
      if (res.error == 0) {
        that.setData({
          search_config: res.content
        })
        // 自定义
        if (res.content.title_action == 3) {
          search_word = res.content.title_custom;
          that.setData({
            search_word: search_word,
            auto_set_word: search_word
          })
        }
      }
    })
    
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
    this.setData({
      auto_set_word: ''
    })
  },
  change_word: function (e) {
    search_word = e.detail.value;
    this.setData({
      search_word: search_word
    })
  },
  // 搜索
  bindSearch: function (e) {
    console.log(search_word);
    // 添加热词
    util.api('/api/wxapp/search/addHotWords', function (res) {
      if (res.error == 0) {
        util.jumpLink('/pages1/search/search?keyWords=' + search_word, "redirectTo")
      }
    }, {
      userId: userId,
      hotWords: search_word
    })
    
  },
  to_search: function (e) {
    var search_text = e.currentTarget.dataset.value;
    util.jumpLink('/pages1/search/search?keyWords=' + search_text, "redirectTo")
  },

  clear_value: function (e) {
    console.log(1);
    search_word = "";
    this.data.search_word = "";
    input_value = "";
    console.log(search_word);
    this.setData({
      input_value: '',
      search_word: '',
      auto_set_word: ''
    })
  },

  // 删除搜索词
  to_del: function () {
    var that = this;
    util.api('/api/wxapp/search/clearWords', function (res) {
      if (res.error == 0) {
        that.setData({
          history_words: ""
        })
      }
    }, {
      userId: userId
    });
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
    // this.selectComponent('#recommend').requestData()
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})
