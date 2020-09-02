var util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    targetStatus:0,
    pageParams:{
      currentPage:1,
      pageRows:20
    },
    dataList:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let {status = 0} = options
    this.setData({
      targetStatus:Number(status)
    })
    this.requestList()
  },
  requestList(){
    util.api('/api/wxapp/doctor/prescription/list',res=>{
      console.log(res)
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
        this.setData({
          pageParams:res.content.page
        })
      }
    },{
      doctorId:util.getCache('doctor_id') || util.getCache('bottom').doctor_id,
      type:this.data.targetStatus,
      ...this.data.pageParams
    })
  },
toggleStatus(e){
    let {type} = e.currentTarget.dataset
    this.setData({
      'pageParams.currentPage':1,
      targetStatus:Number(type)
    })
    this.requestList()
  },
  viewGoodsInfo(e){
    let {gid} = e.currentTarget.dataset
    util.jumpLink(`pages/item/item${util.getUrlParams({
      gid
    })}`)
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