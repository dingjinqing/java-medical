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
    pageParams: {
      currentPage: 0
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    let lotteryId = options.lotteryId;
    this.setData({
      lotteryId: lotteryId
    })
    // lottery_user_request(that);
    this.lotteryListRequest()
  },

  lotteryListRequest () {
    let that = this
    let nextPage = this.data.pageParams.currentPage + 1
    if (this.data.pageParams.nextPage < nextPage) {
      return false
    }
    let params = Object.assign({}, {
      lotteryId: Number(this.data.lotteryId),
      currentPage: nextPage,
      pageRows: this.data.pageParams.pageRows?this.data.pageParams.pageRows:20
    })
    util.api('/api/wxapp/lottery/user/list', function(res) {
      if (res.error === 0) {
        let lotteryList = that.data.lotteryList
        lotteryList.push(...res.content.dataList)
        that.setData({
          lotteryList: lotteryList,
          pageParams: res.content.page
        })
      }
    }, params)
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
  },

  to_detail:function(e){
    var mingxi = e.currentTarget.dataset.mingxi;
    util.showModal("明细", mingxi);
  },
  to_details:function(e){
    var type = e.currentTarget.dataset.types;
    if(type == 1){
      util.navigateTo({
        url: '/pages/integral/integral',
      })
    }else if(type == 2){
      util.navigateTo({
        url: '/pages/account/account',
      })
    } else if (type == 3) {
      util.navigateTo({
        url: '/pages/coupon/coupon',
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
          if (lotteryList[i].lotteryAward.length > 20){
            lotteryList[i].lotteryAward = lotteryList[i].lotteryAward.substring(0,19) + "...";
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
