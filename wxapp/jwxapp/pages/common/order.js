var util = require('../../utils/util.js');
var order = {
  // 好友代付
  // 退货中心
  toReturnCenter(orderSn,isReturn){
    if (!isReturn) util.jumpLink('','navigateTo')
    if (isReturn) util.jumpLink('', 'navigateTo')
  },
  // 查看详情
  viewInfo(orderSn) {
    util.jumpLink(`/pages/orderinfo/orderinfo?order_sn=${orderSn}`, 'navigateTo')
  },
  // 查看评价
  viewComment(orderSn) {
    util.jumpLink(`/pages/comment/comment?order_sn=${orderSn}`, 'navigateTo')
  },
  // 再次购买
  addCart(orderSn,orderId) {
    console.log(orderSn, orderId)
    util.api(
      '/api/wxapp/order/Repurchase',
      (res) => {
        if (res.error == 0) {
          util.toast_success('已加入购物车');
        } else {
          util.showModal('提示', res.message);
        }
      },
      {
        orderId: orderId,
        orderSn: orderSn,
        action:2
      }
    );
  },
  // 删除订单
  delOrder(orderSn, orderId) {
    util.showModal(
      '提示',
      '是否删除该订单',
      (res) => {
        util.api(
          '/api/wxapp/order/operation',
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
  remindOrder(orderSn, orderId) {
    util.api(
      '/api/wxapp/order/operation',
      (res) => {
        if (res.error == 0) {
          util.toast_success('提醒成功');
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
  cancelOrder(orderSn, orderId) {
    util.showModal('提示', '是否取消该订单', function (res) {
      util.api('/api/wxapp/order/operation', function (res) {
        if (res.error == 0) {
          util.navigateTo({ url: '/pages/orderlist/orderlist' })
        }
      }, { 
          orderId: orderId,
          orderSn: orderSn,
          action: 2
       });
    }, true);
  },
  filterObj(obj, arr) {
    if (typeof (obj) !== "object" || !Array.isArray(arr)) {
      throw new Error("参数格式不正确")
    }
    const result = {}
    Object.keys(obj).filter((key) => arr.includes(key)).forEach((key) => {
      if (key === 'isShowCommentType') {
        result[key + '-' + obj[key]] = obj[key]
      } else {
        result[key] = obj[key]
      }
    })
    return result
  },
  // 订单下按钮事件
  handleBtnEvent(e){
    let optionList = {
      orderInfo: (() => {
        return this.viewInfo;
      })(),
      isShowCommentType: (() => {
        return this.viewComment;
      })(),
      isCancel: (() => {
        return this.cancelOrder;
      })(),
      isDelete: (() => {
        return this.delOrder;
      })(),
      isExtendReceive: (() => {
        return this.viewComment;
      })(),
      isRemindShip: (() => {
        return this.remindOrder;
      })(),
      isShowAgainBuy: (() => {
        return this.addCart;
      })(),
      isPayEndPayment: (() => {
        return this.viewComment;
      })(),
      returnCenter:(() => {
        return this.toReturnCenter;
      })()
    };
    let operate_info = e.currentTarget.dataset.operate_info
    if (operate_info.indexOf('-') != -1) {
      operate_info = operate_info.substring(0, operate_info.indexOf('-'))
    }
    if (operate_info === 'returnCenter'){
      optionList[operate_info](e.currentTarget.dataset.order_sn, e.currentTarget.dataset.is_return);
    } else {
      optionList[operate_info](e.currentTarget.dataset.order_sn, e.currentTarget.dataset.order_id);
    }
  }
}

module.exports = order;