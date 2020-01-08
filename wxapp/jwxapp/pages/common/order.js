var util = require("../../utils/util.js");
const orderStatusList = [
  [null, "全部订单"],
  [0, "待付款"],
  [1, "订单取消"],
  [2, "订单关闭"],
  [3, "待发货/待核销"],
  [4, "已发货"],
  [5, "已收货/已自提"],
  [6, "订单完成"],
  [7, "退货中"],
  [8, "退货完成"],
  [9, "退款中"],
  [10, "退款完成"],
  [11, "拼团中"],
  [12, "已成团"],
  [13, "送礼完成"]
];
var order = {
  // 好友代付
  // 退货中心
  toReturnCenter ({order_sn:orderSn, order_id:orderId,is_return:isReturn}) {
    if (!isReturn) util.jumpLink("/pages1/returnorder/returnorder?order_sn=" + orderSn + "&order_id=" + orderId, "navigateTo");
    if (isReturn) util.jumpLink("/pages1/returnorderlist/returnorderlist?order_sn=" + orderSn + "&order_id=" + orderId, "navigateTo");
  },
  // 查看详情
  viewInfo ({order_sn:orderSn, order_id:orderId}) {
    util.jumpLink(
      `/pages/orderinfo/orderinfo?orderSn=${orderSn}`,
      "navigateTo"
    );
  },
  // 查看评价
  viewComment ({order_sn:orderSn, order_id:orderId}) {
    util.jumpLink(`/pages/comment/comment?orderSn=${orderSn}`, "navigateTo");
  },
  //发货
  confirmation ({order_sn:orderSn, order_id:orderId}){
    util.api(
      "/api/wxapp/order/operation",
      res => {
        if (res.error == 0) {
          util.toast_success("收货成功");
          this.requestList()
        } else {
          util.showModal("提示", res.message);
        }
      },
      {
        orderId: orderId,
        orderSn: orderSn,
        action: 6
      }
    )
  },
  // 再次购买
  addCart ({order_sn:orderSn, order_id:orderId}) {
    console.log(orderSn, orderId);
    util.api(
      "/api/wxapp/order/Repurchase",
      res => {
        if (res.error == 0) {
          util.toast_success("已加入购物车");
        } else {
          util.showModal("提示", res.message);
        }
      },
      {
        orderId: orderId,
        orderSn: orderSn,
        action: 2
      }
    );
  },
  // 删除订单
  delOrder ({order_sn:orderSn, order_id:orderId}) {
    util.showModal(
      "提示",
      "是否删除该订单",
      res => {
        util.api(
          "/api/wxapp/order/operation",
          function (res) {
            if (res.error == 0) {
            }
          },
          {
            orderId: orderId,
            orderSn: orderSn,
            action: 9
          }
        );
      },
      true
    );
  },
  // 提醒发货
  remindOrder ({order_sn:orderSn, order_id:orderId}) {
    util.api(
      "/api/wxapp/order/operation",
      res => {
        if (res.error == 0) {
          util.toast_success("提醒成功");
        } else {
          util.toast_fail(res.message);
        }
      },
      {
        orderId: orderId,
        orderSn: orderSn,
        action: 8
      }
    );
  },
  // 取消订单
  cancelOrder ({order_sn:orderSn, order_id:orderId}) {
    util.showModal(
      "提示",
      "是否取消该订单",
      function (res) {
        util.api(
          "/api/wxapp/order/operation",
          function (res) {
            if (res.error == 0) {
              util.navigateTo({ url: "/pages/orderlist/orderlist" });
            }
          },
          {
            orderId: orderId,
            orderSn: orderSn,
            action: 2
          }
        );
      },
      true
    );
  },
  // 过滤需要的参数
  filterObj (obj, arr) {
    if (typeof obj !== "object" || !Array.isArray(arr)) {
      throw new Error("参数格式不正确");
    }
    const result = {};
    Object.keys(obj)
      .filter(key => arr.includes(key))
      .forEach(key => {
        if (key === "isShowCommentType") {
          result[key + "-" + obj[key]] = obj[key];
        } else {
          result[key] = obj[key];
        }
      });
    return result;
  },
  // 订单下按钮事件
  handleBtnEvent (e) {
    console.log(e)
    let optionList = {
      orderInfo: (() => {
        return this.viewInfo;
      }),
      isShowCommentType: (() => {
        return this.viewComment;
      }),
      isCancel: (() => {
        return this.cancelOrder;
      }),
      isDelete: (() => {
        return this.delOrder;
      }),
      isExtendReceive: (() => {
        return this.viewComment;
      }),
      isRemindShip: (() => {
        return this.remindOrder;
      }),
      isShowAgainBuy: (() => {
        return this.addCart;
      }),
      isPayEndPayment: (() => {
        return this.viewComment;
      }),
      confirmation:(()=>{
        return this.confirmation;
      }),
      returnCenter: (() => {
        return this.toReturnCenter;
      })
    };
    let operate_info = e.currentTarget.dataset.operate_info;
    if (operate_info.indexOf("-") != -1) {
      operate_info = operate_info.substring(0, operate_info.indexOf("-"));
    }
    optionList[operate_info]().call(this,e.currentTarget.dataset)
  },
  getOrderStatus (orderData) {
    let typeArray = orderData.orderType;
    if (
      typeArray.indexOf("17") != -1 &&
      orderData.orderSn == orderData.mainOrderSn &&
      [8, 10, 13].indexOf(orderData.orderStatus)
    ) {
      return "等待领取";
    } else {
      if (orderData.orderStatus != 3 && orderData.partShipFlag != 5) {
        if (orderData.orderStatus == 0 && typeArray.indexOf("10") != -1) {
          if (orderData.bkOrderPaid == 0) {
            return "代付定金";
          } else {
            return "代付尾款";
          }
        } else {
          let orderStatusMap = new Map(orderStatusList);
          return orderStatusMap.get(orderData.orderStatus);
        }
      } else {
        if (orderData.deliverType == 1 && orderData.orderStatus == 3) {
          return "待核销";
        } else if (orderData.deliverType == 0 && orderData.orderStatus == 3) {
          return "待发货";
        } else if (orderData.deliverType == 1 && orderData.orderStatus == 5) {
          return "已自提";
        } else if (orderData.deliverType == 0 && orderData.orderStatus == 5) {
          return "已收货";
        }
      }
    }
    return "待发货";
  }
};

module.exports = order;
