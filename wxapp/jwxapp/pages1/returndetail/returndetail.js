// pages1/returedetail/returndetail.js
var util = require('../../utils/util.js');
var app = getApp();
var imageUrl = app.globalData.imageUrl;
var totalMicroSecond = 0; // 倒计时总秒数
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: imageUrl,
    click_look: imageUrl + 'image/wxapp/click_look.png',
    returnSn: '', // 退货订单号
    orderSn: '', // 订单号
    orderId: '', // 订单id
    orderInfo: {}, // 订单信息
    refundStatus: 0, // 订单状态
    returnType: 0, // 仅退款0 退货退款1
    clock: '', // 倒计时
    goodsImages: [], // 申请凭证图
    voucherImages: [], // 提交物流时凭证图
    returnGoods: [], // 售后商品
    applicationTime: '', // 申请时间
    return: ['仅退款', '退货退款', '仅退运费', '手动退款', '换货'], // 售后类型
    reasone: ['协商一致退款', '未按约定时间发货', '缺货', '拍错/多拍/不想要', '其他'], // 退货退款原因
    reasone_huan: ['协商一致换货', '商品与页面描述不符', '发错货', '商品损坏', '其他'], // 换货原因
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let returnSn = options.return_sn
    let orderSn = '', orderId = ''
    if (options.order_sn) {
      orderSn = options.order_sn
    }
    if (options.order_id) {
      orderId = options.order_id
    }
    this.setData({
      returnSn: returnSn,
      orderSn: orderSn,
      orderId: orderId
    })
    this.initData()
  },

  // 获取申请订单详情
  initData () {
    let that = this
    util.api('/api/wxapp/order/refund/info', function (res) {
      if (res.error === 0) {
        let orderInfo = res.content
        let refundStatus = Number(orderInfo.refundStatus)
        let returnType = Number(orderInfo.returnType)
        let applicationTime = ""
        // 倒计时
        if (refundStatus === 4 && returnType === 0) {
          totalMicroSecond = orderInfo.returnMoneyDays / 1000 || 0
        } else if (refundStatus === 1 && returnType === 1) {
          totalMicroSecond = orderInfo.returnAddressDays / 1000 || 0
        } else if (refundStatus === 4 && returnType === 1) {
          totalMicroSecond = orderInfo.returnShippingDays / 1000 || 0
        } else if (refundStatus === 2) {
          totalMicroSecond = orderInfo.returnAuditPassNotShoppingDays / 1000 || 0
        }
        // 申请时间
        if (refundStatus === 1 || refundStatus === 2) {
          applicationTime = orderInfo.applyTime
        } else {
          applicationTime = orderInfo.shippingOrRefundTime
        }
        // 申请时凭证图
        let goodsImages = JSON.parse(orderInfo.goodsImages) || []
        let voucherImages = JSON.parse(orderInfo.voucherImages) || []
        that.setData({
          orderInfo: orderInfo,
          orderSn: orderInfo.orderSn,
          orderId: orderInfo.orderId,
          refundStatus: refundStatus,
          returnType: returnType,
          goodsImages: goodsImages,
          voucherImages: voucherImages,
          returnGoods: orderInfo.returnGoods,
          applicationTime: applicationTime
        })
        that.countdown()
      }
    }, { returnOrderSn: that.data.returnSn })
  },

  // 倒计时
  countdown: function () {
    let that = this;
    let timer;
    that.setData({
      clock: that.dateFormat(totalMicroSecond)
    })
    if (totalMicroSecond <= 0) {
      that.setData({
        clock: '已经截止'
      })
      if (timer) {
        clearTimeout(timer)
      }
      return false;
    }
    timer = setTimeout(function () {
      totalMicroSecond -= 1;
      that.countdown()
    }, 1000);
  },

  // 时间格式化输出，如3:25:19 86。每10ms都会调用一次
  dateFormat: function (micro_second) {
    // 秒数
    var second = Math.floor(micro_second);
    //天数位
    var date = Math.floor(second / 86400);
    // 小时位
    var hr = Math.floor((second - date * 24 * 3600) / 3600);
    if (hr < 10) {
      hr = "0" + hr;
    }
    // 分钟位
    var min = Math.floor((second - hr * 60 * 60 - date * 24 * 3600) / 60);
    if (min < 10) {
      min = "0" + min;
    }
    // 秒位
    var sec = second % 60;
    if (sec < 10) {
      sec = "0" + sec;
    }
    return date + "天" + hr + '时' + min + "分" + sec + "秒";
  },

  // 提交物流
  submitPhysics (e) {
    let returnSn = this.data.returnSn
    util.navigateTo({
      url: '/pages1/returnlogistics/returnlogistics?return_sn=' + returnSn
    })
  },

  // 查看详情
  viewOrder () {
    let orderSn = this.data.orderSn
    util.navigateTo({
      url: 'pages/orderinfo/orderinfo?orderSn=' + orderSn
    })
  },

  // 撤销申请
  cancelApplication (e) {
    let that = this
    let orderInfo = that.data.orderInfo
    let params = {
      orderId: orderInfo.orderId,
      retId: orderInfo.retId,
      orderSn: orderInfo.orderSn,
      action: 1,
      returnoperate: 1
    }
    util.showModal('提示', '您确定要撤销该申请吗？', function () {
      util.api('/api/wxapp/order/refund', function (res) {
        if (res.error === 0) {
          console.log(res.content)
          util.toast_success('撤销成功')
        } else {
          util.toast_fail('撤销失败')
        }
      }, params)
    }, true, '取消', '确定')
  },

  // 创建售后申请
  createReturnOrder () {
    util.navigateTo({
      url: '/pages1/returnorder/returnorder?order_sn=' + this.data.orderSn + '&order_id=' + this.data.orderId
    })
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