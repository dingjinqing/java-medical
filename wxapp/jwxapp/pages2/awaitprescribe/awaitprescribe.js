// pages2/awaitprescribe/awaitprescribe.js
var util = require('../../utils/util.js');
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    page_info: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    this.requestInfo();
  },
  requestInfo () {
    util.api('/api/wxapp/order/medical/get', res => {
      if(res.error == 0) {
        this.data.page_info = res.content;
        for (var i in this.data.page_info) {
          this.data.page_info[i].if_show_more = 0;
          this.data.page_info[i].patient.gestationName = this.getGesName(this.data.page_info[i].patient.gestationType);
          if(!!this.data.page_info[i].medicalHistory && !!this.data.page_info[i].medicalHistory.diseaseHistory){
            this.data.page_info[i].medicalHistory.diseaseHistory =JSON.parse(JSON.stringify(this.data.page_info[i].medicalHistory.diseaseHistory));
            console.log(this.data.page_info[i].medicalHistory.diseaseHistory)
          }
          if(!!this.data.page_info[i].medicalHistory && !!this.data.page_info[i].medicalHistory.imagesList){
            this.data.page_info[i].medicalHistory.imagesList = JSON.parse(this.data.page_info[i].medicalHistory.imagesList);
            console.log(this.data.page_info[i].medicalHistory.imagesList)
          }
        }
        this.setData({
          page_info: this.data.page_info
        })
      } else {
        util.showModal('提示', res.message);
        return false
      }
    },{})
  },
  getGesName (data) {
    switch(data){
      case 0:
        return '未知'
      case 1:
        return '无'
      case 2:
        return '备孕中'
      case 3:
        return '怀孕中'
      case 4:
        return '正在哺乳'
    }
  },
  show_more (e) {
    var this_index = e.currentTarget.dataset.index;
    if(this.data.page_info[this_index].if_show_more == 0) {
      this.data.page_info[this_index].if_show_more = 1
    } else {
      this.data.page_info[this_index].if_show_more = 0
    }
    this.setData({
      page_info: this.data.page_info
    })
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

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})