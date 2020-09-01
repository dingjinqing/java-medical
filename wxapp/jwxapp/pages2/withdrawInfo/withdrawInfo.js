const util = require("../../utils/util")
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    title_bgColor:'#26C4BC',
    activeStyle1:'border-radius:16rpx 16rpx 0 0;background-color:#fff',
    activeStyle2:'border-radius:0 16rpx 0 16rpx;background-color:#f5f5f5',
    activeType:'1',
    targetStatus:'0',
    pageParams: {
      currentPage: 1,
      pageRows: 20
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.requestWithdrawList()
  },
  changeType(e){
    let {type} = e.currentTarget.dataset
    this.setData({
      activeType:type
    })
    if(type === '1'){
      this.setData({
        activeStyle1:'border-radius:16rpx 16rpx 0 0;background-color:#fff',
        activeStyle2:'border-radius:0 16rpx 0 16rpx;background-color:#f5f5f5'
      })
    } else if (type === '2') {
      this.setData({
        activeStyle1:'border-radius:16rpx 0 16rpx 0;background-color:#f5f5f5',
        activeStyle2:'border-radius:16rpx 16rpx 0 0;background-color:#fff'
      })
    }
    this.requestWithdrawList()
  },
  toggleStatus(e){
    let {type} = e.currentTarget.dataset
    this.setData({
      targetStatus:type,
      'pageParams.currentPage':1
    })
    this.requestWithdrawList()
  },
  requestWithdrawList(){
    let apiStr = this.data.activeType === '1' ? '/api/wxapp/doctor/rebate/prescription/list' : '/api/wxapp/doctor/rebate/inquiryOrder/list'
    let params = {
      doctorId:util.getCache('doctor_id') || util.getCache('bottom').doctor_id,
      status:Number(this.data.targetStatus) === 0 ? null : Number(this.data.targetStatus),
      ...this.pageParams
    }
    util.api(apiStr,res=>{
      if(res.error === 0){
        if(this.data.pageParams.currentPage === 1){
          this.setData({
            dataList:[[...res.content.dataList]]
          })
        } else {
          this.setData({
            ['dataList[' + (parseInt(this.data.pageParams.currentPage) - 1) + ']']:res.content.dataList
          })
        }
        this.data.pageParams = res.content.page
      }
    },{
      ...params
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
    this.requestWithdrawList()
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})