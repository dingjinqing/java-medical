var util = require("../../utils/util.js");
var orderEvent = require("../common/order.js");
var app = getApp();
var imageUrl = app.globalData.imageUrl;

global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    orderInfo: null,
    img_noperson: imageUrl + 'image/wxapp/icon_group2.png',
    img_otherperson: imageUrl + 'image/wxapp/icon_group1.png',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.requestOrderInfo(options.orderSn);
  },
  // 请求订单详情
  requestOrderInfo (orderSn) {
    let that = this
    util.api(
      "/api/wxapp/order/get",
      res => {
        if (res.error === 0) {
          let orderInfo = this.formatData(res.content);
          //订单状态
          orderInfo.orderStatusName = orderEvent.getOrderStatus(orderInfo);
          //订单商品总价
          orderInfo.goodsTotalPrice = orderInfo.goods.reduce((total, item) => { return total += item.goodsPrice }, 0)
          // 订单活动类型
          if (orderInfo.orderType && orderInfo.orderType.length) {
            orderInfo.activityName = ""
            orderInfo.orderType.forEach(item => {
              if (item == 1) {
                orderInfo.activityName = that.$t('pages.order.fight')
                orderInfo.isFightGroup = 1;
              } else if (item == 3) {
                orderInfo.activityName = that.$t('pages.order.bargain')
              } else if (item == 5) {
                orderInfo.activityName = that.$t('pages.order.seckill')
              }
            })
          }
          //显示缺少的人数
          if (orderInfo.groupBuyUserInfos && orderInfo.groupBuyUserInfos != '') {
            if (orderInfo.groupBuyUserInfos.length > 5) {
              orderInfo.groupBuyUserInfos = orderInfo.groupBuyUserInfos.slice(0, 5);
            }
            if (orderInfo.groupBuyInfo.groupBuyLimitAmout > 5) {
              orderInfo.groupBuyInfo.show_noper = 5 - orderInfo.groupBuyUserInfos.length;
            } else {
              orderInfo.groupBuyInfo.show_noper = orderInfo.groupBuyInfo.groupBuyLimitAmout - orderInfo.groupBuyUserInfos.length;
            }
          }
          this.setData({
            orderInfo: orderInfo
          });
        }
      },
      { orderSn: orderSn }
    );
  },
  itemPage (e) {
    util.jumpLink(
      `pages/item/item?goodsId=${e.currentTarget.dataset.goods_id}`,
      "navigateTo"
    );
  },
  formatData (order) {
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
  handleOperate (e) {
    orderEvent.handleBtnEvent(e);
  },
  // 拼团详情
  goGroupBuyingDetail (e) {
    if (this.data.orderInfo.groupId) {
      util.navigateTo({
        url: '/pages1/groupbuyinfo/groupbuyinfo?group_id=' + this.data.orderInfo.groupId
      })
    } else {
      util.toast_fail(this.$t('pages.order.seckill'))
    }
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
  onReachBottom: function () { },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () { }
});
