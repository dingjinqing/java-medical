const util = require("../../utils/util.js");
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: util.getImageUrl(""),
    dataList: [],
    pageParams: null,
    showFilterDialog:false,
    action:1, //测试用后续删除
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.requestList()
  },
  showFilter(){
    this.setData({
      showFilterDialog:true
    })
  },
  requestList() {
    let currentPage = this.data.pageParams
      ? this.data.pageParams.currentPage
      : 1;
    let api = parseInt(this.data.action) === 1 ? '/api/wxapp/order/goods/history' : 'api/wxapp/footprint/list'
    util.api(
      api,
      res => {
        if (res.error === 0) {
          let dataList = res.content.day
          this.setData({
            pageParams: res.content.page,
            ['dataList[' + (parseInt(currentPage) - 1) + ']']: dataList
          });
        }
      },
      {
        currentPage: currentPage,
        pageRows: 20,
        userId: util.getCache('user_id')
      }
    );
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () { },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () { },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () { },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () { },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () { },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    if (
      this.data.pageParams &&
      this.data.pageParams.currentPage === this.data.pageParams.lastPage
    )
      return;
    this.setData({
      'pageParams.currentPage': this.data.pageParams.currentPage + 1
    });
    this.requestList();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () { }
});
