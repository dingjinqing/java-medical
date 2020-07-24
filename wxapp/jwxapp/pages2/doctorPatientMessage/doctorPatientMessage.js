// pages1/prescriptionsheet/prescriptionsheet.js
var app = new getApp();
var imageUrl = app.globalData.imageUrl;
var util = require('../../utils/util.js')
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    array: ['请选择', '男', '女'],
    sex_index: 0,
    comm_img:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },
  bindSexChange: function (e) {
    this.setData({
      sex_index: e.detail.value
    })
  },
  //上传图片
  customUpImage: function (e) {
    var that = this;
    var comm_img = that.data.comm_img;
    util.uploadImage(1, function (res) {
      let data = JSON.parse(res.data);
      console.log(data)
      if (data.error == 0) {
        comm_img.push(data.content.imgUrl);
        that.setData({
          comm_img: comm_img,
          image: true,
        })
      }
    });
  },
  customDelImage: function (e) {
    var that = this;
    var imgindex = e.currentTarget.dataset.imgindex;
    var comm_img = that.data.comm_img;
    comm_img.splice(imgindex, 1);
    that.setData({
      comm_img: comm_img,
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