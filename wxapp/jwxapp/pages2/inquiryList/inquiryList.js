var util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    targetStatus:'1',
    filterParams:{
      sessionStatus:[0],
      doctorId:null,
      userId:null
    },
    pageParams:{
      currentPage:1,
      pageRows:20
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      'filterParams.doctorId':1
    })
    this.requestSessionList()
  },
  requestSessionList(){
    util.api('/api/wxapp/im/session/page/list',res=>{
      console.log(res)
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
      ...this.data.filterParams,
      ...this.data.pageParams
    })
  },
  toggleStatus(e){
    let {type} = e.currentTarget.dataset
    this.setData({
      targetStatus:type,
      'pageParams.currentPage':1,
      'filterParams.sessionStatus':type === '1' ? [0] : [1,3]
    })
    this.requestSessionList()
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