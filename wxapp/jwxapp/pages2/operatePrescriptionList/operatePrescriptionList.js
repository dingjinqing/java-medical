const util = require("../../utils/util")

// pages2/operatePrescriptionList/operatePrescriptionList.js
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
    this.requestList()
  },
  requestList(){
    util.api('/api/wxapp/docker/audit/list',res=>{
      if(res.error === 0){
        if(this.data.pageParams.currentPage === 1){
          this.setData({
            dataList:[[...res.content.dataList]]
          })
        } else {
          this.setData({
            ['dataList[' + (parseInt(currentPage) - 1) + ']']:res.content.dataList
          })
        }
        this.setData({
          pageParams:res.content.page
        })
      }
    },{
      ...this.data.pageParams
    })
  },
  handleShowDialog(e){
    let {prescriptionCode} = e.currentTarget.dataset
    util.api('/api/wxapp/prescription/details',res=>{
      if(res.error === 0){
        this.setData({
          showPrescription:true,
          prescriptionData:res.content
        })
      }
    },{
      prescriptionCode
    })
  },
  changeStatus(e){
    let {type,orderId,orderSn,parentIndex} = e.currentTarget.dataset
    let dataListItem = this.data.dataList[parentIndex]
    let targetIndex = dataListItem.findIndex(item=>item.orderSn === orderSn && item.orderId === orderId)
    if(res.error === 0){
      dataListItem.splice(targetIndex,1)
      this.setData({
        [`dataList[${parentIndex}]`]:dataListItems
      })
    }
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
    this.requestList();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})