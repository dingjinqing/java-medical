let util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    pageParams:null,
    dataList:null,
    navType:0,
    imageUrl: util.getImageUrl("")
  },


  requestList() {
    let currentPage = this.data.pageParams ? this.data.pageParams.currentPage : 1;
    util.api('/api/wxapp/bargain/list', (res) => {
      console.log(res)
      if (res.error === 0) {
        let dataList = this.formatData(res.content.dataList);
        this.setData({
          pageParams: res.content.page,
          ['dataList[' + (parseInt(currentPage) - 1) + ']']: dataList
        })
      }
    }, {
        currentPage: currentPage,
        pageRows: 20,
        status: this.data.navType
      });
  },
  formatData(dataList){
    return dataList.map(item=>{
      item.endTime = item.endTime.substring(0,10)
      return item
    })
  },
  changeNav(e){
    let status = parseInt(e.currentTarget.dataset.nav_type)
    this.setData({
      navType: status,
      dataList:null,
      pageParams:null
    })
    this.requestList()
  },
  continueBargain(e){
    util.jumpLink(`/pages/bargaininfo/bargaininfo?record_id=${e.currentTarget.dataset.recordId}`,'navigateTo')
  },
  checkOut(){
    console.log(123)
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.requestList()
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
    if (this.data.pageParams && this.data.pageParams.currentPage === this.data.pageParams.lastPage) return;
    this.setData({
      'pageParams.currentPage': this.data.pageParams.currentPage + 1
    })
    this.requestList()
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})