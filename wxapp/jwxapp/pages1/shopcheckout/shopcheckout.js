// pages1/shopcheckout/shopcheckout.js
// 门店买单
var util = require('../../utils/util.js');

global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    storeId: '', // 门店id
    userId: '', // 用户id
    orderInfo: {
      score: '', // 用户积分
      account: '', // 用户余额
      invoiceSwitch: false, // 是否默认选中发票开关
      memberCardList: [], // 可用会员卡列表
      shopLogo: '', // 店铺logo
      shopBusinessState: 1, // 店铺营业状态
      storeBusinessState: 1, // 门店营业状态
      storeBuy: 1, // 门店是否支持买单开关
      delFlag: 0, // 门店是否已删除 
    },
    payInfo: {
      cardNo: '', // 会员卡号
      orderAmount: '', // 总金额
      cardDisAmount: 0, // 会员卡折扣抵扣金额
      cardAmount: 0, // 会员卡余额抵扣金额
      inputScore: '', // 输入积分
      scoreAmount: 0, // 积分抵扣金额
      balanceAmount: 0, // 余额抵扣金额
      moneyPaid: '', // 订单金额
      totalDiscount: 0, // 总折扣
      invoiceId: '', // 发票id
      formId: '', // 微信formId
      remark: '' // 留言
    },

    showCardDialog: false, // 会员卡弹窗是否显示
    useCard: {}, // 选中的会员卡
    invoiceInfo: {}, // 选择的发票
    discount_block: 0 // 是否展开折扣详情
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    let that = this
    let storeId = options.storeId
    this.setData({
      storeId: storeId,
      userId: util.getCache('user_id')
    })
    this.initOrderInfo()
  },

  initOrderInfo () {
    // 请求用户信息
    let params = {
      storeId: this.data.storeId,
      userId: util.getCache('user_id')
    }
    let that = this
    util.api('/api/wxapp/store/payOrder', function (res) {
      if (res.error === 0) {
        console.log(res)
        let info = res.content;
        if (info.storeBuy === 0) {
          util.showModal(that.$t('pages.store.prompt'), that.$t('pages.store.noPayment'), function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return;
        }
        if (info.delFlag == 1) {
          util.showModal(that.$t('pages.store.prompt'), that.$t('pages.store.hasDeleteStore'), function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return;
        }
        if (info.shopBusinessState == 0) {
          util.showModal(that.$t('pages.store.prompt'), that.$t('pages.store.wanderAround'), function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
          return;
        }
        if (info.storeBusinessState == 0) {
          util.showModal(that.$t('pages.store.prompt'), that.$t('pages.store.wanderAround'), function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          });
        }
        //会员卡
        var src_yes = that.data.imageUrl + 'image/wxapp/selected.png';
        var src_no = that.data.imageUrl + 'image/wxapp/icon_rectangle.png';
        var useCard = {}
        if (info.memberCardList && info.memberCardList.length > 0) {
          info.memberCardList.forEach((item, i) => {
            if (item.discount == null) {
              item.discount = 10
            }
            item.src_yes = src_yes
            item.src_no = src_no
          })
          useCard = info.memberCardList.find(item => item.isDefault == 1)
          if (typeof (useCard) === 'undefined') {
            useCard = info.memberCardList[0]
          }
        }
        // 发票开关
        if (info.invoiceSwitch) {
        }
        that.setData({
          orderInfo: info,
          useCard: useCard,
          'payInfo.cardNo': useCard.cardNo
        })
      }
    }, params)
  },

  // 获取发票
  initInvoice () {

  },

  // 输入要支付的金额
  inputMoney: function (e) {
    let money = e.detail.value;
    if (money) {
      //限制只能输入一个点
      var ipt_money_arr = money.toString().split('.');
      if (ipt_money_arr.length > 2) {
        money = ipt_money_arr[0] + '.' + ipt_money_arr[1];
      }
      //小数点后只能输2位
      var temp = money.toString().split('.');
      if (temp.length == 2) {
        if (temp[1].length >= 3) {
          money = temp[0] + '.' + temp[1].substring(0, 2);
        }
      }
    } else if (money == '') {
      money = 0;
    }
    this.setData({
      'payInfo.orderAmount': money
    })
    this.computedMoney(money)
  },

  // 计算金额
  computedMoney: function () {
    let payInfo = this.data.payInfo
    let money = payInfo.orderAmount
    let useCard = this.data.useCard
    // 如果会员卡有折扣
    if (useCard) {
      let discount = this.data.useCard.discount
      if (discount) {
        payInfo.cardDisAmount = ((1 - discount / 10) * money).toFixed(2)
      }
    }
    payInfo.totalDiscount = parseFloat(Number(payInfo.cardDisAmount) + Number(payInfo.cardAmount) + Number(payInfo.scoreAmount) + Number(payInfo.balanceAmount)).toFixed(2)
    payInfo.moneyPaid = parseFloat(payInfo.orderAmount - payInfo.totalDiscount).toFixed(2)
    this.setData({
      payInfo: payInfo
    })
  },

  // 查看折扣详情
  look_detail () {
    let discount_block = this.data.discount_block
    if (discount_block == 0) {
      discount_block = 1;
    } else {
      discount_block = 0;
    }
    this.setData({
      discount_block: discount_block
    })
  },

  // 选择会员卡弹窗 
  cardClick () {
    if (!this.data.payInfo.orderAmount) {
      util.showModal('', this.$t('pages.store.enterAmount'))
      return false
    }
    this.setData({
      showCardDialog: true
    })
  },

  // 选择会员卡回调
  getSelectCard (e) {
    let cardNo = e.detail
    let useCard = this.data.orderInfo.memberCardList.find(item => item.cardNo === cardNo)
    console.log(useCard)
    this.setData({
      useCard: useCard,
      'payInfo.cardNo': useCard.cardNo
    })
    this.computedMoney()
  },

  // 输入会员卡余额抵扣金额
  card_discount: function (e) {
    let value = e.detail.value
    console.log(value)
    if (value) {
      value = Number(value)
      let isPrice = this.isAmount(value)
      if (!isPrice) {
        util.showModal('', this.$t('pages.store.enterCorrect'))
        this.setData({
          'payInfo.cardAmount': 0
        })
        this.computedMoney()
        return false
      }
      if (value > this.data.useCard.money) {
        util.showModal('', this.$t('pages.store.upTo') + this.data.useCard.money + this.$t('pages.store.memberCardBalance'))
        this.setData({
          'payInfo.cardAmount': 0
        })
        this.computedMoney()
        return false
      }
      if (value > this.data.payInfo.moneyPaid) {
        util.showModal('', this.$t('pages.store.notExceed'))
        this.setData({
          'payInfo.cardAmount': 0
        })
        this.computedMoney()
        return false
      }
      this.setData({
        'payInfo.cardAmount': parseFloat(value).toFixed(2)
      })
      this.computedMoney()
    } else {
      this.setData({
        'payInfo.cardAmount': 0
      })
      this.computedMoney()
    }
  },

  // 输入积分
  score_money: function (e) {
    let value = e.detail.value
    console.log(value)
    if (value) {
      value = Number(value)
      if (isNaN(value) || value < 0) {
        util.showModal('', this.$t('pages.store.correctScore'))
        this.setData({
          'payInfo.inputScore': '',
          'payInfo.scoreAmount': 0
        })
        this.computedMoney()
        return false
      }
      if (value % 100 !== 0) {
        util.showModal('', this.$t('pages.store.integerMultiple'))
        this.setData({
          'payInfo.inputScore': '',
          'payInfo.scoreAmount': 0
        })
        this.computedMoney()
        return false
      }
      if (value > this.data.orderInfo.score) {
        util.showModal('', this.$t('pages.store.upTo') + this.data.orderInfo.score + this.$t('pages.store.integral'))
        this.setData({
          'payInfo.inputScore': '',
          'payInfo.scoreAmount': 0
        })
        this.computedMoney()
        return false
      }
      let amount = parseFloat(Number(value) / 100).toFixed(2)
      if (amount > this.data.payInfo.moneyPaid) {
        util.showModal('', this.$t('pages.store.scoreLimit'))
        this.setData({
          'payInfo.inputScore': '',
          'payInfo.scoreAmount': 0
        })
        this.computedMoney()
        return false
      }
      this.setData({
        'payInfo.inputScore': value,
        'payInfo.scoreAmount': amount
      })
      this.computedMoney()
    } else {
      this.setData({
        'payInfo.inputScore': '',
        'payInfo.scoreAmount': 0
      })
      this.computedMoney()
    }
  },

  // 输入余额
  user_account: function (e) {
    let value = e.detail.value
    if (value) {
      value = Number(value)
      if (isNaN(value) || !(this.isAmount(value))) {
        util.showModal('', this.$t('pages.store.enterCorrect'))
        this.setData({
          'payInfo.balanceAmount': 0
        })
        this.computedMoney()
        return false
      }
      if (value > this.data.orderInfo.account) {
        util.showModal('', this.$t('pages.store.upTo') + this.orderInfo.account + this.$t('pages.store.balance'))
        this.setData({
          'payInfo.balanceAmount': 0
        })
        this.computedMoney()
        return false
      }
      if (value > this.data.payInfo.moneyPaid) {
        util.showModal('', this.$t('pages.store.notExceedPaid'))
        this.setData({
          'payInfo.balanceAmount': 0
        })
        this.computedMoney()
        return false
      }
      this.setData({
        'payInfo.balanceAmount': parseFloat(value).toFixed(2)
      })
      this.computedMoney()
    } else {
      this.setData({
        'payInfo.balanceAmount': 0
      })
      this.computedMoney()
    }
  },

  // 验证输入的是金额
  isAmount (price) {
    let reg = /^[0-9]+(.[0-9]+)?$/
    if (reg.test(price)) {
      return true
    }
    return false
  },

  // 选择发票回调
  chooseInvoice: function () {
    var that = this;
    wx.chooseInvoiceTitle({
      success (res) {
        let invoiceInfo = that.data.invoiceInfo;
        invoiceInfo = res;
        invoiceInfo.invoice_title = res.title;
        // var ress = JSON.stringify(res);
        // util.api('/api/wxapp/invoice/choose', function (e) {
        //   create_order.invoice = e.content;
        // }, { invoiceInfo: ress })
        that.setData({
          invoiceInfo: invoiceInfo
        })
      },
      fail () {
        util.showModal('', that.$t('pages.store.failedGetInvoice'));
      }
    })
  },

  // 备注输入
  remarkInput: function (e) {
    let value = e.detail.value;
    this.setData({
      'payInfo.remark': value
    })
  },

  // 支付
  toConfirm: function (e) {
    let that = this
    if (this.data.orderInfo.shopBusinessState == 0) {
      util.showModal(that.$t('pages.store.prompt'), that.$t('pages.store.wanderAround'), function () {
        util.reLaunch({
          url: '/pages/index/index'
        })
      });
      return false;
    }
    if (this.data.orderInfo.storeBusinessState == 0) {
      util.showModal(that.$t('pages.store.prompt'), that.$t('pages.store.storeClosed'), function () {
        util.reLaunch({
          url: '/pages/index/index'
        })
      });
      return false;
    }
    if (this.data.payInfo.orderAmount == '') {
      util.showModal('', that.$t('pages.store.enterAmount'));
      return false;
    }
    if (this.data.payInfo.orderAmount == 0) {
      util.showModal('', that.$t('pages.store.cannotBe0'));
      return flase;
    }
    let payInfo = this.data.payInfo
    payInfo.storeId = this.data.storeId
    payInfo.formId = e.detail.formId
    let params = {
      userId: this.data.userId,
      orderInfo: payInfo
    }
    util.api('/api/wxapp/store/confirmPay', function (res) {
      if (res.error == 0) {
        if (payInfo.moneyPaid > 0) {
          var order_sn = res.content.orderSn;
          if (typeof (res.content.timeStamp) != 'undefined') {
            wx.requestPayment({
              'timeStamp': res.content.timeStamp,
              'nonceStr': res.content.nonceStr,
              'package': res.content.package,
              'signType': typeof res.content.signType == "undefined" ? 'MD5' : res.content.signType,
              'paySign': res.content.paySign,
              'success': function (res) {
                util.toast_success(that.$t('pages.store.paymentSuccessful'));
                util.redirectTo({
                  url: '/pages1/shoporderinfo/shoporderinfo?order_sn=' + order_sn,
                })
              },
              'fail': function (res) {
                let discount_block = 0;
                that.setData({
                  discount_block: discount_block
                })
                util.toast_fail(that.$t('pages.store.paymentFailed'));
                that.initOrderInfo()
              }
            });
          } else {
            let discount_block = 0;
            that.setData({
              discount_block: discount_block
            })
            util.toast_fail(that.$t('pages.store.paymentFailed'));
            that.initOrderInfo()
          }
        } else {
          var order_sn = res.content.orderSn;
          util.toast_success(that.$t('pages.store.paymentSuccessful'));
          util.redirectTo({
            url: '/pages1/shoporderinfo/shoporderinfo?order_sn=' + order_sn,
          })
        }
      } else {
        util.showModal(that.$t('pages.store.prompt'), res.message, function () {
          // util.reLaunch({
          //   url: '/pages/index/index',
          // })
        }, false);
      }
    }, params)
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    return {
      path: "/pages/shopcheckout/shopcheckout?store_id=" + create_order.store_id + "&invite_id=" + util.getCache('user_id')
    }
  }
})