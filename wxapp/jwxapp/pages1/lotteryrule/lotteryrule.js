// pages/lotteryrule/lotteryrule.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var lotteryId;
var lotteryList;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    page: 1,
    last_page: 1,
    lotteryList:[],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    lotteryId = options.lotteryId;
    this.setData({
      lotteryId: lotteryId
    })
    // lottery_user_request(that);
    this.lotteryListRequest()
  },

  lotteryListRequest () {
    let that = this
    util.api('/api/wxapp/lottery/user/list', function(res) {
      if (res.error === 0) {
        console.log(res)
        that.setData({
          lotteryList: res.content.dataList
        })
      }
    }, {lotteryId: Number(this.data.lotteryId)})
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
    if (that.data.page == that.data.last_page) return;
    that.data.page = that.data.page + 1;
    util.api('/api/wxapp/lottery/records', function (res) {
      var lottL = res.content;
      that.data.last_page = lottL.last_page;
      var server_list_r = res.content.data;
      var lotteryList = [];
      if (server_list_r.length > 0) {
        lotteryList = server_list_r;
        if (lotteryList[i].lotteryType == 3) {
          if (lotteryList[i].lottery_award.length > 20) {
            lotteryList[i].lottery_award = lotteryList[i].lottery_award.substring(0, 19) + "...";
            lotteryList[i].is_length = 1
          }
        }
      }
      that.setData({
        lotteryList: that.data.lotteryList.concat(lotteryList),
        page_num: lottL.last_page,
        curpage: lottL.current_page,
      });
    }, {   lotteryId: lotteryId, pageNo: that.data.page });
  },

  to_detail:function(e){
    var mingxi = e.currentTarget.dataset.mingxi;
    util.showModal("明细", mingxi);
  },
  to_details:function(e){
    var tyes = e.currentTarget.dataset.types;
    if(tyes == 0){
      util.navigateTo({
        url: '/pages/integral/integral',
      })
    }else if(tyes == 1){
      util.navigateTo({
        url: '/pages/account/account',
      })
    } else if (tyes == 2) {
      util.navigateTo({
        url: '/pages/couponlist/couponlist',
      })
    }
  },
})
function lottery_user_request(that){
  util.api('/api/wxapp/lottery/records', function (res) {
    var lottL = res.content;
    that.data.last_page = lottL.last_page;
    var server_list_r = res.content.data;
    var lotteryList = [];
    if (server_list_r.length > 0) {
      lotteryList = server_list_r;
      for (var i = 0; i < lotteryList.length;i++){
        if (lotteryList[i].lotteryType == 3){
          if (lotteryList[i].lottery_award.length > 20){
            lotteryList[i].lottery_award = lotteryList[i].lottery_award.substring(0,19) + "...";
            lotteryList[i].is_length = 1
          }
        }

      }
    }
    that.setData({
      lotteryList: lotteryList,
      page_num: lottL.last_page,
      curpage: lottL.current_page,
    });
  }, {   lotteryId: lotteryId,pageNo:that.data.page});
}
