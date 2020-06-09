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

    that.getConfig()
    that.getHistory()

    this.selectComponent('#recommend').resetDataList().resetPage().requestData() //推荐商品
  },
  // 获取搜索配置, 热词搜索
  getConfig () {
    var that = this
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
      } else {
        util.showModal("提示", res.message);
      }
    })
  },
  // 获取历史搜索
  getHistory () {
    var that = this
    util.api('/api/wxapp/search/userSearchHot', function (res) {
      if (res.error == 0) {
        that.setData({
          history_words: res.content == [] ? [] : res.content
        })
        console.log(that.data.history_words)
      } else {
        util.showModal("提示", res.message);
      }
    }, {
      userId: userId,
      num: 10
    })
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
    console.log(this.data.search_word);
    var data = this.data.search_word.replace(/\s/g,"");
    // 添加热词
    if (data != "") {
      util.api('/api/wxapp/search/addHotWords', function (res) {}, {
        userId: userId,
        hotWords: data
      })
    }
    util.jumpLink('/pages/search/search?keyWords=' + data, "redirectTo")
  },
  
  to_search: function (e) {
    search_word = e.currentTarget.dataset.value
    this.setData({
      search_word: e.currentTarget.dataset.value
    })
    this.bindSearch()
  },

  // 清空搜索框
  clear_value: function (e) {
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
        that.getHistory()
      } else {
        util.showModal("提示", res.message);
      }
    }, { userId: userId });
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    this.selectComponent('#recommend').requestData()
  }
})
