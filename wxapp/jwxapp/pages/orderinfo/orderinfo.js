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
          orderInfo.deliverType = 0;
          orderInfo.orderStatusName = orderEvent.getOrderStatus(orderInfo);
          orderInfo.deliverTypeName =
            orderInfo.deliverType === 0
              ? "快递"
              : orderInfo.deliverType === 1
              ? "自提"
              : "同城配送";
          this.setData({
            orderInfo: orderInfo
          });
        }
      },
      { orderSn: orderSn }
    );
  },

  // 订单状态
  // getOrderStatus(orderInfo){
  //   let str = '已发货'
  //   if (orderInfo.orderPayWay === 0 || orderInfo.orderPayWay === 2){
  //     if (orderInfo.orderStatus === 0){
  //       str = '待付款'
  //     } else {
  //       if (orderInfo.deliverType == 1 && orderInfo.orderStatus == 3) {
  //         str = "待核销";
  //       }
  //       if (orderInfo.deliverType != 1 && orderInfo.orderStatus == 3) {
  //         str = "待发货";
  //       }
  //       if (orderInfo.deliverType == 1 && orderInfo.orderStatus == 5) {
  //         str = "已自提";
  //       }
  //       if (orderInfo.deliverType != 1 && orderInfo.orderStatus == 5) {
  //         str = "已收货";
  //       }
  //       if (orderInfo.orderSn === orderInfo.mainOrderSn && orderInfo.orderStatus >= 3 && orderInfo.orderStatus < 13){
  //         str = '进行中'
  //       }
  //     }

  //   } else if (orderInfo.orderPayWay === 1){
  //     if (orderInfo.orderStatus === 0){
  //       if (orderInfo.bkOrderPaid === 0){
  //         str = '待付定金'
  //       } else if (orderInfo.bkOrderPaid === 1){
  //         str = '待付尾款'
  //       }
  //     }
  //   }
  //   return str;
  // },
  itemPage(e) {
    console.log(e);
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
      "isShowAgainBuy",
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
