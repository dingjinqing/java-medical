var util = require('../../utils/util.js');
var order = {
  // 好友代付
  // 退货中心
  // 查看详情
  viewInfo(orderSn) {
    util.jumpLink(`/pages/orderinfo/orderinfo?order_sn=${orderSn}`, 'navigateTo')
  },
  // 查看评价
  viewComment(orderSn) {
    util.jumpLink(`/pages/comment/comment?order_sn=${orderSn}`, 'navigateTo')
  },
  // 再次购买
  addCart(orderSn) {
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
        order_sn: orderSn
      }
    );
  },
  // 删除订单
  delOrder(orderSn) {
    util.showModal(
      '提示',
      '是否删除该订单',
      (res) => {
        util.api(
          '/api/wxapp/order/del',
          function (res) {
            if (res.error == 0) {
            }
          },
          { order_sn: orderSn }
        );
      },
      true
    );
  },
  // 提醒发货
  remindOrder(orderSn) {
    util.api(
      '/api/wxapp/order/remind',
      (res) => {
        if (res.error == 0) {
          util.toast_success('提醒成功');
        } else {
          util.toast_fail(res.message);
        }
      },
      {
        order_sn: orderSn
      }
    );
  },
  // 取消订单
  cancelOrder(orderSn) {
    util.showModal('提示', '是否取消该订单', function (res) {
      util.api('/api/wxapp/order/cancel', function (res) {
        if (res.error == 0) {
          util.navigateTo({ url: '/pages/orderlist/orderlist' })
        }
      }, { order_sn: orderSn });
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
      })()
    };
    let operate_info = e.currentTarget.dataset.operate_info
    if (operate_info.indexOf('-') != -1) {
      operate_info = operate_info.substring(0, operate_info.indexOf('-'))
    }
    optionList[operate_info](e.currentTarget.dataset.order_sn);
  }
}

module.exports = order;