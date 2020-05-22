// pages/usercardup/usercardup.js
var util = require('../../utils/util.js');
var app = getApp()
var imageUrl = app.globalData.imageUrl;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    util.api('/api/wxapp/card/upgrade', function (res) {
      if (res && res.error == 0) {
        for(var i in res.content.data){
          if (res.content.data[i].new_grade > res.content.data[i].old_grade){
            res.content.data[i].grade = 1;
          } else if (res.content.data[i].new_grade == res.content.data[i].old_grade){
            res.content.data[i].grade = 0;
          }else{
            res.content.data[i].grade = -1;
          }
        }
        var list = res.content.data;
        var no_card_record = 1;
        list.forEach((item,i)=>{
          if ( item.grade != 0 ){
            no_card_record = 0;
            return;
          }
        })
        that.setData({
          list: list,
          no_card_record: no_card_record
        })
      }
    }, {   })
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