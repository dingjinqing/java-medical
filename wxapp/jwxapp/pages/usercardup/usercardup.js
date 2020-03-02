let util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: util.getImageUrl(""),
    currentPage: 1,
    pageRows: 20
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options, 'get option')
    var that = this;
    util.api('/api/wxapp/card/upgrade', res => {
      if (res && res.error == 0) {
        console.log(res, 'get res')
        var resultData = res.content.dataList
        for (var i in resultData) {
          if (resultData[i].new_grade > resultData[i].old_grade) {
            resultData[i].grade = 1;
          } else if (resultData[i].new_grade == resultData[i].old_grade) {
            resultData[i].grade = 0;
          } else {
            resultData[i].grade = -1;
          }
        }
        var list = resultData;
        var no_card_record = 1;
        list.forEach((item, i) => {
          if (item.grade != 0) {
            no_card_record = 0;
            return;
          }
        })
        that.setData({
          list: list,
          no_card_record: no_card_record,
        })
      }
    }, {
      currentPage: this.data.currentPage,
      pageRows: this.data.pageRows
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