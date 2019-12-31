var util = require("../../utils/util.js");
var orderEvent = require("../common/order.js");

global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: util.getImageUrl(""),
    searchInput: null,
    scrollIntoId: null,
    currentPage: 1,
    pageParams: null,
    navType: {
      ALL: 0,
      WAIT_PAY: 1,
      WAIT_DELIVERY: 2,
      SHIPPED: 3,
      RETURNING: 5
    },
    dataList: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    let scrollIntoId =
      options &&
      options.datas &&
      JSON.parse(options.datas) &&
      JSON.parse(options.datas).type;
    this.setData({
      scrollIntoId: scrollIntoId ? scrollIntoId : "ALL"
    });
    this.requestList();
  },
  requestList() {
    let currentPage = this.data.pageParams
      ? this.data.pageParams.currentPage
      : 1;
    util.api("/api/wxapp/order/statistic", res => {
      this.setData({
        navStatusNum: res.content
      });
    });
    util.api(
      "/api/wxapp/order/list",
      res => {
        if (res.error === 0) {
          let dataList = this.formatData(res.content.dataList);
          if(dataList.length < 20){
            this.selectComponent('#recommend').resetDataList().resetPage().requestData()
          }
          this.setData({
            pageParams: res.content.page,
            ["dataList[" + (parseInt(currentPage) - 1) + "]"]: dataList
          });
        }
      },
      {
        currentPage: currentPage,
        pageRows: 20,
        type: this.data.navType[this.data.scrollIntoId],
        search: this.data.searchInput
      }
    );
  },
  formatData(order) {
    let formatOrderItem = order.map(item => {
      const filterArr = [
        "isShowPay",
        "isPayEndPayment",
        "isExtendReceive",
        "isRemindShip",
        "isShowCommentType",
        "isDelete",
        "isCancel"
      ];
      item.operate = orderEvent.filterObj(item, filterArr);
      item.orderStatusName = orderEvent.getOrderStatus(item);
      return item;
    });
    console.log(formatOrderItem);
    return formatOrderItem;
  },
  // 清空搜索栏
  clearSearchInput() {
    this.setData({
      searchInput: null
    });
  },
  getSearchInput(e) {
    this.setData({
      searchInput: e.detail.value
    });
  },
  handleSearch(e) {
    this.setData({
      pageParams: null,
      dataList: [],
      scrollIntoId: "ALL",
      searchInput: e.detail.value ? e.detail.value : this.data.searchInput
    });
    this.requestList();
  },
  // 切换导航
  handleChangeNav(e) {
    this.setData({
      scrollIntoId: e.currentTarget.id,
      pageParams: null,
      dataList: []
    });
    this.requestList();
  },
  // 订单下按钮事件集合
  handleOperate(e) {
    orderEvent.handleBtnEvent(e);
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {},

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {},

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {},

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {},

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {},

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    if (
      this.data.pageParams &&
      this.data.pageParams.currentPage === this.data.pageParams.lastPage
    ){
      this.selectComponent('#recommend').requestData()
      return;
    }
    this.setData({
      "pageParams.currentPage": this.data.pageParams.currentPage + 1
    });
    this.requestList();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {}
});
