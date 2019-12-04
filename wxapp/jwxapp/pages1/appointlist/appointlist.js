// pages1/appointlist/appointlist.js
let util = require('../../utils/util.js');

global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    tabStatus: [
      { name: '全部预约', status: '', num: 0 },
      { name: '待付款', status: 0, num: 0 },
      { name: '待服务', status: 1, num: 0 },
      { name: '已取消', status: 2, num: 0 },
      { name: '已完成', status: 3, num: 0 }
    ],
    activeStatus: '',
    appointInfo: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getAppoint()
  },

  getAppoint (status) {
    let that = this
    let orderStatus = status !== '' ? status : '';
    util.api('/api/wxapp/store/service/reservationList', function (res) {
      if (res.error === 0) {
        console.log(res.content)
        let appointInfo = res.content
        if (appointInfo && appointInfo.length) {
          appointInfo.forEach((item, i) => {
            if (item.storeImgs) {
              item.storeImg = JSON.parse(item.storeImgs)[0]
            }
            // if (item.serviceImg) {
            //   item.serviceImg = JSON.parse(item.serviceImg)[0]
            // }
          })
        }
        that.countStatus(appointInfo, orderStatus)
        that.setData({
          appointInfo: appointInfo
        })
      } else {
        util.showModal('提示', '列表错误', function () {
          wx.navigateBack();
        })
      }
    }, {
      userId: util.getCache('user_id'),
      orderStatus: orderStatus
    })
  },

  // 统计各种状态的单据个数
  countStatus (datas, orderStatus) {
    if (datas && datas.length) {
      let tabStatus = this.data.tabStatus
      if (typeof orderStatus === 'undefined') {
        orderStatus = ''
      }
      if (orderStatus === '') {
        tabStatus[0].num = datas.length
        datas.forEach(function (item, i) {
          switch (item.orderStatus) {
            case 0:
              tabStatus[1].num++
              break
            case 1:
              tabStatus[2].num++
              break
            case 2:
              tabStatus[3].num++
              break
            case 3:
              tabStatus[4].num++
          }
        })
        this.setData({
          tabStatus: tabStatus
        })
      } else {
        tabStatus[orderStatus + 1].num = datas.length
        this.setData({
          tabStatus: tabStatus
        })
      }
    }
  },

  tabItemSwitch (e) {
    let status = e.currentTarget.dataset.status
    this.setData({
      activeStatus: status
    })
    this.getAppoint(status)
  },

  toInfo: function (e) {
    var order_sn = e.currentTarget.dataset.order_sn;
    util.navigateTo({
      url: '/pages/appointinfo/appointinfo?order_sn=' + order_sn
    })
  },
  toStore: function (e) {
    var id = e.currentTarget.dataset.id;
    util.navigateTo({
      url: '/pages/storeinfo/storeinfo?id=' + id
    })
  },
  toDetail: function (e) {
    var service_id = e.currentTarget.dataset.id;
    util.navigateTo({
      url: '/pages/appointment/appointment?service_id=' + service_id
    })
  },
  ser_comment: function (e) {
    var order_sn = e.currentTarget.dataset.order_sn;
    var store_id = e.currentTarget.dataset.store_id;
    util.navigateTo({
      url: '../servicecomment/servicecomment?order_sn=' + order_sn + "&store_id=" + store_id
    })
  },
  // 取消预约
  serverCancel: function (e) {
    var mobile = e.currentTarget.dataset.mobile;
    util.showModal('提示', '请与商家联系后，由商家取消', function () {
      wx.makePhoneCall({
        phoneNumber: mobile
      })
    }, true, '取消', '直接联系');
  },
  // 取消预约
  toTrueCancel: function (e) {
    var that = this;
    var form_id = e.detail.formId;
    var orderId = e.currentTarget.dataset.order_id
    util.showModal('提示', '是否取消该订单', function () {
      util.api('/api/wxapp/store/service/cancelReservation', function (res) {
        if (res.error == 0) {
          util.toast_success('取消成功');
          util.navigateTo({ url: '/pages/appointlist/appointlist' });
        } else if (res.error == 400002) {
          util.toast_success('操作成功');
        }
      }, { orderId: orderId, cancelReason: "取消原因", form_id: form_id })
    }, true);
  },
  //删除预约
  toDelete: function (e) {
    var data = e.currentTarget.dataset;
    var form_id = e.detail.formId;
    util.showModal("提示", "是否删除该预约服务订单", function () {
      util.api('/api/wxapp/store/service/reservationDel', function (res) {
        if (res.error == 0) {
          util.toast_success('删除成功')
          util.navigateTo({ url: '/pages/appointlist/appointlist' });
        }
      }, { orderId: data.order_id, form_id: form_id })
    }, true);
  },
  // 去支付
  toPay: function (e) {
    let order_sn = e.currentTarget.dataset.order_sn
    let form_id = e.detail.formId
    util.api('/api/wxapp/store/service/submitReservation', function (res) {
      if (res.error == 0) {
        if (typeof (res.content.timeStamp) != 'undefined') {
          wx.requestPayment({
            'timeStamp': res.content.timeStamp,
            'nonceStr': res.content.nonceStr,
            'package': res.content.package,
            'signType': typeof res.content.signType == "undefined" ? 'MD5' : res.content.signType,
            'paySign': res.content.paySign,
            'success': function (res) {
              util.toast_success('支付成功');
              util.navigateTo({
                url: '/pages/appointinfo/appointinfo?order_sn=' + order_sn,
              })
            },
            'fail': function (res) {
              util.toast_fail('支付失败');
              util.navigateTo({
                url: '/pages/appointinfo/appointinfo?order_sn=' + order_sn,
              })
            },
            'complete': function (res) {
            }
          });
        } else {
          util.toast_fail('支付失败');
          util.redirectTo({
            url: '/pages/appointinfo/appointinfo?order_sn=' + order_sn,
          })
        }
      } else if (e.error == 400002) {
        util.showModal('提示', e.content, function () {
          wx.navigateBack();
        });
      } else {
        util.showModal("提示", '调起支付失败', function () {
          wx.navigateBack({})
        });
        return false;
      }
    }, { orderSn: order_sn, form_id: form_id })
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    this.getAppoint(this.data.activeStatus)
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