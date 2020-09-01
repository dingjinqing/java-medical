const util = require("../../utils/util")

// pages2/doctorWidthdrawRecord/doctorWidthdrawRecord.js
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    pageParams:{
      currentPage:1,
      pageRows:20
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.requestWithdrawRecord()
  },
  requestWithdrawRecord(){
    util.api('/api/wxapp/doctor/withdraw/list',res=>{
      console.log(res)
      if(res.error === 0){
        if (this.data.pageParams.currentPage === 1) {
          this.setData({
            dataList: [
              [...res.content.dataList]
            ]
          })
        } else {
          this.setData({
            ['dataList[' + (parseInt(this.data.pageParams.currentPage) - 1) + ']']: res.content.dataList
          })
        }
        this.setData({
          pageParams: res.content.page
        })
      }
    },{
      doctorId:util.getCache('doctor_id') || util.getCache('bottom').doctor_id,
      ...this.data.pageParams
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
    if (
      this.data.pageParams &&
      this.data.pageParams.currentPage === this.data.pageParams.lastPage
    ) {
      return;
    }
    this.setData({
      'pageParams.currentPage': this.data.pageParams.currentPage + 1
    });
    this.requestWithdrawRecord()
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})