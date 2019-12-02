var util = require("../../utils/util.js");
var orderEvent = require("../common/order.js");
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    orderInfo: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.requestOrderInfo(options.orderSn);
  },
  // 请求订单详情
  requestOrderInfo(orderSn) {
    util.api(
      "/api/wxapp/order/get",
      res => {
        if (res.error === 0) {
          let orderInfo = this.formatData(res.content);
          //订单状态
          orderInfo.orderStatusName = orderEvent.getOrderStatus(orderInfo);
          //订单商品总价
          orderInfo.goodsTotalPrice = orderInfo.goods.reduce((total, item) => { return total += item.goodsPrice},0)
          this.setData({
            orderInfo: orderInfo
          });
        }
      },
      { orderSn: orderSn }
    );
  },
  itemPage(e) {
    util.jumpLink(
      `pages/item/item?goodsId=${e.currentTarget.dataset.goods_id}`,
      "navigateTo"
    );
  },
  formatData(order) {
    const filterArr = [
      "isShowPay",
      "isPayEndPayment",
      "isExtendReceive",
      "isRemindShip",
      "isShowCommentType",
      "isDelete",
      "isCancel"
    ];
    order.operate = orderEvent.filterObj(order, filterArr);
    console.log(order);
    return order;
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
  onReachBottom: function() {},

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {}
});
