var util = require('../../utils/util.js')
var orderEvent = require('../common/order.js')
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: util.getImageUrl(''),
    balanceStatus: 0, //使用余额状态
    scoreStatus: 0, //使用积分状态
    cardBalanceStatus: 0, //使用会员卡余额状态
    couponArray: null, //优惠券列表
    defaultCouponIndex: null, //默认选择优惠券
    payType: [],
    choosePayType: 0, //所选支付方式
    showCardBalanceDialog: false, //是否显示会员卡余额弹框
    showBalanceDialog: false, //是否显示余额弹框
    showScoreDialog: false, //是否显示积分弹框
    showCardDialog: false, //是否显示选择会员卡弹框
    showStoreDialog: false, //是否显示选择门店弹框
    params: {
      action: 10,
      activityType: null, // 指定本次结算所参加的唯一营销活动类型
      activityId: null, // 指定本次结算所参加的唯一营销活动类型 ID
      recordId: null, // 砍价活动id
      groupId: null, // 拼团参团id
      addressId: null, // 地址id
      goods: null, // 商品列表
      deliverType: 0, // 配送方式
      storeId: null, // 门店id
      memberCardNo: 0, //0: 默认选第一张；null：不选；其他：卡号
      couponSn: 0, //0: 默认选第一张；null：不选；其他：优惠卷号
      scoreDiscount: null, // 积分抵扣金额
      balance: null, //余额抵扣金额
      cardBalance: null, //会员卡抵扣金额
      orderPayWay: null, //支付方式
      isCart: 0,
      patientId: null, //患者id
      lat: null,
      lng: null
    },
    usePayInfo: {
      moneyPaid: 0, //订单可支付的金额
      useCardBalance: 0, //已使用的会员卡余额
      useBalance: 0, //已使用的余额
      useScore: 0 //已使用的积分
    },
    orderInfo: {},
    invoiceArray: ['不需要', '普通发票', '增值税普通发票', '增值税专用发票'],
    invoiceIndex: 0,
    message: '', //买家备注
    showFriendPayDialog: false,
    must: {
      orderRealName: '',
      orderCid: '',
      consigneeRealName: '',
      consigneeCid: '',
      custom: ''
    },
    options: {},
    title_bgColor: '#26C4BC',
    patientDiagnose: null,
    selectedStoreInfo: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let goods = []
    let {
      goodsList,
      activityType = null,
      activityId = null,
      recordId = null,
      preSaleInfo = null,
      roomId = null,
      inviteId = null,
      memberCardNo = 0,
      addressId,
      isPrescription = null,
      prescriptionCode = null
    } = options
    console.log(options)
    wx.setStorage({
      data: options,
      key: 'orderOptions'
    })
    if (addressId) {
      this.setData({
        addressId: addressId,
        'params.addressId': addressId
      })
    }
    JSON.parse(goodsList).forEach(item => {
      let {
        goodsId,
        prdRealPrice: goodsPrice,
        goodsNum: goodsNumber,
        prdId: productId,
        isCart = 0
      } = item
      if (Number(activityType) === 4) {
        goodsPrice = 0
      }
      goods.push({
        goodsId,
        goodsPrice,
        goodsNumber,
        productId,
        isCart
      })
    })
    if (preSaleInfo) {
      preSaleInfo = JSON.parse(preSaleInfo)
      if (preSaleInfo.preSalePrdInfo && (preSaleInfo.preSalePrdInfo.discountPrice - preSaleInfo.preSalePrdInfo.depositPrice > 0)) {
        this.setData({
          preSaleDiscountPrice: (preSaleInfo.preSalePrdInfo.discountPrice - preSaleInfo.preSalePrdInfo.depositPrice).toFixed(2)
        })
      }
    }
    this.setData({
      'params.goods': goods,
      'params.isCart': goods[0].isCart, //购物车来源|商品详情
      'params.activityType': activityType,
      'params.activityId': activityId,
      'params.recordId': recordId,
      'params.roomId': roomId,
      'params.memberCardNo': memberCardNo,
      'params.prescriptionCode': prescriptionCode,
      'params.isPrescription': Number(isPrescription),
      preSaleInfo,
      inviteId,
      isPrescription: Number(isPrescription)
    })
    if (options.groupid) {
      this.setData({
        'params.groupId': options.groupid
      })
    }
    this.requestOrder()
  },
  requestOrder () {
    util.api(
      '/api/wxapp/order',
      res => {
        if (res.error === 0) {
          let orderInfo = res.content
          this.setCardData(orderInfo)
          let orderGoods = orderInfo.orderGoods,
            rxCount = 0,
            no_rxcount = 0,
            goods_num = 0
          orderGoods.forEach((item) => {
            if (item.isRx > 0) {
              rxCount++
            } else {
              no_rxcount++
            }
            goods_num += item.goodsNumber
          })
          this.setData({
            orderInfo,
            goods_num: goods_num,
            rxKind: (rxCount > 0 && no_rxcount > 0) ? '混合药' : (rxCount == 0 ? '非处方药' : '处方药'),
            isAward: res.content.activityType === 24, //是否奖品订单
            isCardExchange: res.content.activityType === 13, //是否限次卡兑换
          })
          this.setData({
            submitButtonStatus: this.getSubmitButtonStatus()
          })
          if (orderInfo.activityType === 4) { //积分兑换数据
            this.setScoreRedeemData(orderInfo)
          }
          this.getCouponData(orderInfo)
          this.defaultInput(orderInfo)
          this.getPayType(orderInfo)
        } else {
          util.showModal('提示', res.message, function () {
            let pages = getCurrentPages()
            if (res.error == 147012 && res.content) {
              util.jumpLink(res.content)
            } else if (pages.length > 1) {
              wx.navigateBack()
            } else {
              util.jumpLink('/pages/index/index', 'reLaunch')
            }
          }, false, '', '确定')
        }
      }, {
      ...this.data.params
    }
    )
  },
  getPayType (orderinfo) {
    let payType = this.data.payType
    if (!payType.includes(0) && (orderinfo.paymentList.wxpay || orderinfo.paymentList.balance || orderinfo.paymentList.score)) {
      payType.push(0)
      this.setData({
        choosePayType: 0
      })
    }
    if (!payType.includes(1) && orderinfo.paymentList.cod) {
      payType.push(1)
      if (payType.length === 1) {
        this.setData({
          choosePayType: 1
        })
      }
    }
    if (!payType.includes(2) && orderinfo.insteadPayCfg && orderinfo.insteadPayCfg.status && (orderinfo.insteadPayCfg.singlePay || orderinfo.insteadPayCfg.multiplePay)) {
      payType.push(2)
      if (payType.length === 1) {
        this.setData({
          choosePayType: 2
        })
      }
    }
    if (this.data.scoreRedeemData && this.data.scoreRedeemData.score) {
      payType = [3]
      this.setData({
        choosePayType: 3
      })
    }
    if (this.data.scoreRedeemData && this.data.scoreRedeemData.money) {
      payType = [0]
      this.setData({
        choosePayType: 0
      })
    }
    this.setData({
      payType
    })
  },
  // 选择地址
  addAddress () {
    let addressId = -1
    if (this.data.orderInfo && this.data.orderInfo.address && this.data.orderInfo.address.addressId) {
      addressId = this.data.orderInfo.address.addressId
    }
    util.navigateTo('/components/usercenter/useraddress/useraddress?select=' + addressId)
    // wx.chooseAddress({
    //   success: res => {
    //     util.api(
    //       '/api/wxapp/address/choose',
    //       res => {
    //         console.log(res)
    //         if (res.error === 0) {
    //           this.setData({
    //             'params.addressId': res.content.addressId
    //           })
    //           console.log()
    //           this.requestOrder()
    //         }
    //       },
    //       { wxAddress: { ...res } }
    //     )
    //   },
    //   fail: function () {
    //     wx.getSetting({
    //       success: function (res) {
    //         if (!res.authSetting['scope.address']) {
    //           util.showModal(
    //             '是否打开设置页面',
    //             '需要获取您的位置信息，请到小程序的设置页面打开授权',
    //             function () {
    //               wx.openSetting({
    //                 success: function (res) { }
    //               })
    //             }
    //           )
    //         }
    //       }
    //     })
    //   }
    // })
  },
  requestAddress () {
    let that = this
    util.api('/api/wxapp/address/get', op => {
      if (op.error === 0) {
        let wxAddress = op.content
        that.setData({
          'orderInfo.address': wxAddress
        })
        // util.api(
        //   '/api/wxapp/address/choose',
        //   res => {
        //     console.log(res)
        //     if (res.error === 0) {
        //       that.setData({
        //         'params.addressId': res.content.addressId
        //       })
        //       console.log()
        //       that.requestOrder()
        //     }
        //   },
        //   { wxAddress: {
        //     errMsg: '',
        //     userName: wxAddress.consignee,
        //     nationalCode: '',
        //     telNumber: wxAddress.mobile,
        //     postalCode: wxAddress.provinceCode,
        //     provinceName: wxAddress.provinceName,
        //     cityName: wxAddress.cityName,
        //     cityCode: wxAddress.cityCode,
        //     countyName: wxAddress.districtName,
        //     detailInfo: wxAddress.address
        //   } }
        // )
      }
    }, {
      addressId: this.data.addressId
    })
  },
  // 默认填充
  defaultInput (orderInfo) {
    let {
      isBalancePay,
      isCardPay,
      isScorePay,
      moneyPaid,
      memberCardMoney,
      userAccount,
      scorePayNum,
      userScore,
      scoreMaxDiscount,
      paymentList,
      scoreProportion
    } = orderInfo
    if (isCardPay === 1 && memberCardMoney > 0) {
      console.log(moneyPaid)
      let useCardBalance = parseFloat(moneyPaid - memberCardMoney > 0 ? memberCardMoney : moneyPaid).toFixed(3)
      useCardBalance = parseFloat(useCardBalance.substring(0, useCardBalance.length - 1))
      moneyPaid = parseFloat(moneyPaid - useCardBalance).toFixed(3)
      moneyPaid = parseFloat(moneyPaid.substring(0, moneyPaid.length - 1)).toFixed(3)
      this.setData({
        'usePayInfo.useCardBalance': useCardBalance,
        cardBalanceStatus: useCardBalance > 0 ? 1 : 0
      })
    } else {
      this.setData({
        'usePayInfo.useCardBalance': 0,
        cardBalanceStatus: 0
      })
    }
    if (paymentList.balance && isBalancePay === 1 && userAccount > 0) {
      console.log(moneyPaid)
      let useBalance = parseFloat(moneyPaid - userAccount > 0 ? userAccount : moneyPaid).toFixed(3)
      useBalance = parseFloat(useBalance.substring(0, useBalance.length - 1))
      moneyPaid = parseFloat(moneyPaid - useBalance).toFixed(3)
      moneyPaid = parseFloat(moneyPaid.substring(0, moneyPaid.length - 1)).toFixed(3)
      this.setData({
        'usePayInfo.useBalance': useBalance,
        balanceStatus: useBalance > 0 ? 1 : 0
      })
    } else {
      this.setData({
        'usePayInfo.useBalance': 0,
        balanceStatus: 0
      })
    }
    if (paymentList.score && isScorePay === 1 && userScore > scorePayNum && userScore > 0) {
      console.log(moneyPaid)
      let useScore =
        moneyPaid * scoreProportion > scoreMaxDiscount * scoreProportion ?
          scoreMaxDiscount * scoreProportion > userScore ?
            userScore :
            scoreMaxDiscount * scoreProportion :
          moneyPaid * scoreProportion > userScore ?
            userScore :
            moneyPaid * scoreProportion
      moneyPaid -= parseInt(useScore)
      this.setData({
        'usePayInfo.useScore': parseInt(useScore),
        scoreStatus: useScore > 0 ? 1 : 0
      })
    } else {
      this.setData({
        'usePayInfo.useScore': 0,
        scoreStatus: 0
      })
    }
    this.getPayMoney()
  },
  // 会员卡余额支付事件
  cardBalanceTap () {
    if (this.data.cardBalanceStatus) {
      this.setData({
        cardBalanceStatus: 0,
        showCardBalanceDialog: false,
        'usePayInfo.useCardBalance': 0
      })
    } else {
      this.setData({
        showCardBalanceDialog: true
      })
    }
    this.getPayMoney()
  },
  // 余额支付事件
  balanceTap () {
    if (this.data.balanceStatus) {
      this.setData({
        balanceStatus: 0,
        showBalanceDialog: false,
        'usePayInfo.useBalance': 0
      })
    } else {
      this.setData({
        showBalanceDialog: true
      })
    }
    this.getPayMoney()
  },
  // 积分支付事件
  scoreTap () {
    if (this.data.scoreStatus) {
      this.setData({
        scoreStatus: 0,
        showScoreDialog: false,
        'usePayInfo.useScore': 0
      })
    } else {
      this.setData({
        showScoreDialog: true
      })
    }
    this.getPayMoney()
  },
  // 获得输入的余额数
  getInputBalance (data) {
    this.setData({
      'usePayInfo.useBalance': data.detail,
      balanceStatus: 1
    })
    this.getPayMoney()
  },
  //获取输入的积分数
  getInputScore (data) {
    this.setData({
      'usePayInfo.useScore': parseInt(data.detail),
      scoreStatus: 1
    })
    this.getPayMoney()
  },
  // 获取输入的会员卡余额数
  getInputCardBalance (data) {
    this.setData({
      'usePayInfo.useCardBalance': data.detail,
      cardBalanceStatus: 1
    })
    this.getPayMoney()
  },
  // 获取优惠券数据
  getCouponData (orderInfo) {
    let {
      coupons,
      defaultCoupon
    } = orderInfo
    if (coupons === null || coupons.length === 0) {
      this.setData({
        couponArray: null,
        defaultCouponIndex: null
      })
      return
    }
    let couponList = coupons.map(item => {
      let newItem = JSON.parse(JSON.stringify({
        ...item,
        ...item.info
      }))
      if (newItem.type === 0) {
        if (newItem.useConsumeRestrict === 1) {
          newItem.text = `${this.$t('components.decorate.full')}${
            newItem.limitOrderAmount
            }${this.$t('components.decorate.reduce')}${newItem.amount}`
        } else {
          newItem.text = `${this.$t('components.decorate.coupon')}${this.$t(
            'components.decorate.reduce'
          )}${newItem.amount}`
        }
      } else if (newItem.type === 1) {
        if (newItem.useConsumeRestrict === 1) {
          newItem.text = `${this.$t('components.decorate.full')}${
            newItem.limitOrderAmount
            }${this.$t('components.decorate.hit')}${newItem.amount}${this.$t(
              'components.decorate.fracture'
            )}`
        } else {
          newItem.text = `${this.$t('components.decorate.coupon')}${this.$t(
            'components.decorate.hit'
          )}${newItem.amount}${this.$t('components.decorate.fracture')}`
        }
      }
      return newItem
    })
    let couponArray = [{
      couponSn: null,
      text: '不使用优惠券'
    }, ...couponList]
    let defaultCouponIndex =
      (defaultCoupon && couponArray.findIndex(item => item.couponSn === defaultCoupon.info.couponSn)) ||
      0
    this.setData({
      couponArray,
      defaultCouponIndex
    })
  },

  // 变更优惠券
  couponChange (e) {
    let {
      couponSn
    } = this.data.couponArray[parseInt(e.detail.value)]
    this.setData({
      defaultCouponIndex: parseInt(e.detail.value)
    })
    if (couponSn) {
      this.setData({
        'params.couponSn': couponSn
      })
    } else {
      let params = this.data.params
      delete params.couponSn
      this.setData({
        params
      })
    }

    this.requestOrder()
  },
  // 选择会员卡事件
  selectCardTap () {
    this.setData({
      showCardDialog: true
    })
  },
  // 得到选择后的会员卡
  getSelectCard (data) {
    if (data.detail) {
      this.setData({
        'params.memberCardNo': data.detail
      })
    } else {
      let params = this.data.params
      delete params.memberCardNo
      this.setData({
        params
      })
    }
    this.requestOrder()
  },
  // 变更支付类型
  changePayType (e) {
    this.setData({
      choosePayType: e.currentTarget.dataset.payType
    })
  },
  // 变更配送方式
  selectShippingMethod (e) {
    let that = this
    if (e.currentTarget.dataset.index !== 0) {
      (async () => {
        let res = await this.getLocationData()
        this.data.params.lat = res.latitude
        this.data.params.lng = res.longitude
        util.api('/api/wxapp/order/get/store', res => {
          if (res.error === 0) {
            let keysList = Object.keys(res.content)

            this.setData({
              selectedStoreInfo: {
                ...res.content[keysList[keysList.length - 1]],
                distance: keysList[keysList.length - 1]
              },
              storeList: res.content,
              'params.deliverType': e.currentTarget.dataset.index,
              'params.storeId': res.content[keysList[keysList.length - 1]].storeId
            })
          }
        }, {
          lat: this.data.params.lat,
          lng: this.data.params.lng
        })
        // this.setData({
        //   'params.lat':
        //   'params.lng':
        // })
      })()
    }
    if (e.currentTarget.dataset.index === 0) {
      this.setData({
        'params.deliverType': e.currentTarget.dataset.index
      })
    }

    this.requestOrder()
  },
  // 选择门店
  selectStore () {
    if (this.data.chooseShippingIndex === 0) return
    let storeDialogData = {}
    storeDialogData.openType = this.data.chooseShippingIndex
    storeDialogData.data = [{
      id: 1,
      storeName: '伊泰大厦',
      address: '潘家园',
      distance: '5.46'
    },
    {
      id: 2,
      storeName: '回龙观大厦',
      address: '西直门',
      distance: '0.15'
    },
    {
      id: 3,
      storeName: '西二旗大厦',
      address: '西二旗',
      distance: '15'
    },
    {
      id: 4,
      storeName: '霍营大厦',
      address: '蜂鸟社区',
      distance: '123'
    }
    ]
    this.setData({
      showStoreDialog: true,
      storeDialogData: storeDialogData
    })
  },
  //获取应付总金额
  getPayMoney () {
    let moneyPaid = this.data.orderInfo.moneyPaid,
      useScore = this.data.usePayInfo.useScore,
      useBalance = this.data.usePayInfo.useBalance,
      useCardBalance = this.data.usePayInfo.useCardBalance,
      scoreProportion = this.data.orderInfo.scoreProportion
    let floatNum = parseFloat(moneyPaid - useCardBalance - useBalance - useScore / scoreProportion).toFixed(3)
    floatNum = parseFloat(floatNum.substring(0, floatNum.length - 1))
    let floatScoreMoney = (useScore / scoreProportion).toFixed(3)
    floatScoreMoney = parseFloat(floatScoreMoney.substring(0, floatScoreMoney.length - 1))
    this.setData({
      'usePayInfo.moneyPaid': floatNum,
      useScoreMoney: floatScoreMoney
    })
  },
  // 获取门店改变
  getSelectStore (info) {
    let selectedStoreInfo = info.detail
    this.setData({
      selectedStoreInfo,
      'params.storeId': selectedStoreInfo.storeId
    })
  },
  // 是否使用发票
  bindInvoiceChange (e) {
    this.setData({
      invoiceIndex: parseInt(e.detail.value)
    })
  },
  // 下单必填项
  mustInput (e) {
    let {
      type
    } = e.currentTarget.dataset, {
      value
    } = e.detail
    switch (type) {
      case 'orderRealName':
        this.setData({
          'must.orderRealName': value
        })
        break
      case 'orderCid':
        this.setData({
          'must.orderCid': value
        })
        break
      case 'consigneeRealName':
        this.setData({
          'must.consigneeRealName': value
        })
        break
      case 'consigneeCid':
        this.setData({
          'must.consigneeCid': value
        })
        break
      default:
        this.setData({
          'must.custom': value
        })
        break
    }
  },
  // 添加备注
  addMsg (e) {
    let {
      value: message
    } = e.detail
    this.setData({
      message
    })
  },
  // 选择发票
  chooseInvoice () {
    wx.chooseInvoiceTitle({
      success (res) {
        // util.api('/api/wxapp/invoice/choose', function(e) {
        //   that.data.create_order.invoice_id = e.content;
        //   that.setData({
        //     invoiceTitle: res.title
        //   })
        // }, {
        //   invoice_info: JSON.stringify(res)
        // })
        console.log(res)
      },
      fail () {
        util.showModal('', '获取发票信息失败')
      }
    })
  },
  // 获取必填项
  getMust (params) {
    let must = {}
    if (this.data.orderInfo.must.isShow) {
      if (this.data.orderInfo.must.consigneeCid) {
        must.consigneeCid = this.data.must.consigneeCid
      }
      if (this.data.orderInfo.must.consigneeRealName) {
        must.consigneeRealName = this.data.must.consigneeRealName
      }
      if (this.data.orderInfo.must.orderCid) {
        must.orderCid = this.data.must.orderCid
      }
      if (this.data.orderInfo.must.orderRealName) {
        must.orderRealName = this.data.must.orderRealName
      }
      if (this.data.orderInfo.must.custom) {
        must.custom = this.data.must.custom
      }
    }
    if (Object.keys(must).length > 0) {
      params.must = must
    }
  },
  // 是否可提交
  canSubmit () {
    let addressId = (this.data.orderInfo.address && this.data.orderInfo.address.addressId) || null
    if (!addressId) {
      wx.showToast({
        title: '请选择地址',
        icon: 'none'
      })
      return false
    }
    let mustTips = ''
    if (this.data.orderInfo.must.isShow && this.data.orderInfo.must.consigneeCid && !this.data.must.consigneeCid) mustTips = '收货人身份证为必填项，请输入'
    if (this.data.orderInfo.must.isShow && this.data.orderInfo.must.consigneeRealName && !this.data.must.consigneeRealName) mustTips = '收货人姓名为必填项，请输入'
    if (this.data.orderInfo.must.isShow && this.data.orderInfo.must.orderCid && !this.data.must.orderCid) mustTips = '下单人身份证为必填项，请输入'
    if (this.data.orderInfo.must.isShow && this.data.orderInfo.must.orderRealName && !this.data.must.orderRealName) mustTips = '下单人姓名为必填项，请输入'
    if (this.data.orderInfo.must.isShow && this.data.orderInfo.must.custom && !this.data.must.custom) mustTips = `${this.data.orderInfo.must.custom}为必填项，请输入`
    if (mustTips) {
      util.showModal('提示', mustTips)
      return false
    }
    if (this.data.orderInfo.term && this.data.orderInfo.term.serviceTerms === 1 && this.data.orderInfo.term.serviceChoose === 0) {
      util.showModal('提示', `请同意${this.data.orderInfo.term.serviceName}后再试`)
      return false
    }

    if (!this.getSubmitButtonStatus()) {
      if (!this.data.orderInfo.patientInfo || !this.data.orderInfo.patientInfo.id) {
        this.setData({
          noPrescriptionType: 2
        })
      } else {
        this.setData({
          noPrescriptionType: 1
        })
      }
      this.setData({
        showNoPrescription: true
      })
      return false
    }

    return true
  },
  // 关闭弹窗
  closeDialog (target) {
    switch (target.detail) {
      case 'balance':
        this.setData({
          showBalanceDialog: false
        })
        break
      case 'score':
        this.setData({
          showScoreDialog: false
        })
        break
      default:
        this.setData({
          showCardBalanceDialog: false
        })
        break
    }
  },
  // 提交订单
  confirmOrder () {
    let pages = getCurrentPages(),
      currPage = null;
    if (pages.length) currPage = pages[pages.length - 2];
    let route = currPage.route
    if (route) {
      let name = {
        "title": {
          'search': '药品列表页',
          'index': '首页',
          'item': '药品详情页',
          'cart': '购物车页',
          'patientChat': '患者聊天页',
          'orderinfo': '订单详情页',
          'repurchaselist': '处方复购页'
        }
      }
      let lastindex = route.lastIndexOf('/');
      let lastSegment = route.substring(lastindex + 1);
      let title = name.title[lastSegment]
      util.handleBuriedPoint('create_order_submit', 'pages/checkout/checkout', [{
        key: '订单来源',
        value: title
      }, {
        key: '订单金额',
        value: this.data.usePayInfo.moneyPaid
      }, {
        key: '药品数量',
        value: this.data.goods_num
      }, {
        key: '药品种类',
        value: this.data.rxKind
      }])
    }
    if (!this.canSubmit()) return
    util.throttle(this.confirm, 5000)()
  },
  confirm () {
    util.getNeedTemplateId('add_order', () => {
      let {
        orderGoods: goods,
        orderAmount,
        paymentList,
        activityType,
        activityId
      } =
        this.data.orderInfo || {}
      let {
        useBalance: balance,
        useCardBalance: cardBalance,
        useScore: scoreDiscount
      } = this.data.usePayInfo
      let addressId = (this.data.orderInfo.address && this.data.orderInfo.address.addressId) || null
      let couponSn =
        (this.data.orderInfo.defaultCoupon && this.data.orderInfo.defaultCoupon.info.couponSn) || null
      let memberCardNo =
        (this.data.orderInfo.defaultMemberCard && this.data.orderInfo.defaultMemberCard.cardNo) ||
        null
      goods = goods.filter(item => {
        return item.isGift !== 1
      })
      let addParams = {}
      if (this.data.choosePayType === 2 && this.data.insteadPayNum !== null) {
        addParams.insteadPayNum = this.data.insteadPayNum
      } else if (addParams.insteadPayNum) {
        delete addParams.insteadPayNum
      }
      if (this.data.params.roomId) addParams.roomId = Number(this.data.params.roomId)
      if (this.data.inviteId) addParams.inviteId = Number(this.data.inviteId)
      if (this.data.orderInfo.patientInfo && this.data.orderInfo.patientInfo.patientId) addParams.patientId = Number(this.data.orderInfo.patientInfo.patientId)
      if (this.data.isPrescription && this.data.orderInfo.prescriptionList.length) {
        addParams.isPrescription = 1
        addParams.prescriptionCode = this.data.orderInfo.prescriptionList[0].prescriptionCode
      }
      if (this.data.patientDiagnose) addParams.patientDiagnose = this.data.patientDiagnose
      if (this.data.params.deliverType !== 0 && this.data.params.storeId) addParams.storeId = this.data.params.storeId
      let params = {
        goods,
        action: 10,
        orderAmount,
        addressId,
        balance,
        cardBalance,
        scoreDiscount,
        deliverType: this.data.params.deliverType,
        orderPayWay: this.data.choosePayType === 3 ? 0 : this.data.choosePayType,
        couponSn,
        message: this.data.message,
        memberCardNo,
        activityType,
        activityId,
        recordId: this.data.params.recordId,
        groupId: this.data.params.groupId,
        isCart: this.data.params.isCart,
        ...addParams
      }
      this.getMust(params)
      util.api(
        '/api/wxapp/order/submit',
        async res => {
          if (res.error === 0) {
            let {
              orderSn
            } = res.content
            if (this.data.insteadPayNum !== null && this.data.choosePayType === 2) {
              util.jumpLink(`/pages1/insteadinfo/insteadinfo?orderSn=${orderSn}`, 'redirectTo')
            } else if (this.data.choosePayType === 0 && res.content.webPayVo && paymentList.wxpay) {
              wx.requestPayment({
                timeStamp: res.content.webPayVo.timeStamp,
                nonceStr: res.content.webPayVo.nonceStr,
                package: res.content.webPayVo.package,
                signType: 'MD5',
                paySign: res.content.webPayVo.paySign,
                success: async res => {
                  util.toast_success('支付成功')
                  if (activityType === 8) {
                    let groupInfo = await this.requestGroupDraw(orderSn)
                    console.log(groupInfo)
                    if (!groupInfo) return
                    util.jumpLink(`pages1/pinlotteryinfo/pinlotteryinfo${this.getUrlParams({ group_draw_id: groupInfo.activityId, group_id: groupInfo.groupId, goods_id: goods[0].goodsId })}`, 'redirectTo')
                  } else {
                    let scoreRedeemData = this.data.orderInfo.activityType === 4 && this.data.scoreRedeemData.score > 0 ? {
                      useScore: this.data.scoreRedeemData.score
                    } : {}
                    util.jumpLink(
                      `pages1/payment/payment${this.getUrlParams({
                        orderSn,
                        useInfo: JSON.stringify({ ...this.data.usePayInfo, ...scoreRedeemData })
                      })}`,
                      'redirectTo'
                    )
                  }
                  let name = '订单结算页'
                  if (this.data.insteadPayNum) name = '好友代付'
                  util.handleBuriedPoint('order_pay_success', 'pages/checkout/checkout', [{
                    key: '路径来源',
                    value: name
                  }])
                },
                fail: res => {
                  console.log(res)
                  util.jumpLink(`/pages/orderinfo/orderinfo?orderSn=${orderSn}`, 'redirectTo')
                },
                complete: res => { }
              })
            } else {
              if (activityType === 8) {
                let groupInfo = await this.requestGroupDraw(orderSn)
                console.log(groupInfo)
                if (!groupInfo) return
                util.jumpLink(`pages1/pinlotteryinfo/pinlotteryinfo${this.getUrlParams({ group_draw_id: groupInfo.activityId, group_id: groupInfo.groupId, goods_id: goods[0].goodsId })}`, 'redirectTo')
              } else {
                let scoreRedeemData = this.data.orderInfo.activityType === 4 && this.data.scoreRedeemData.score > 0 ? {
                  useScore: this.data.scoreRedeemData.score
                } : {}
                util.jumpLink(
                  `pages1/payment/payment${this.getUrlParams({
                    orderSn,
                    useInfo: JSON.stringify({ ...this.data.usePayInfo, ...scoreRedeemData })
                  })}`,
                  'redirectTo'
                )
              }
            }
          } else {
            util.showModal('提示', res.message, function () {
              util.jumpLink('/pages/index/index', 'redirectTo')
            })
          }
        },
        params
      )
    })
  },
  //整合参数
  getUrlParams (obj) {
    return Object.keys(obj).reduce((UrlStr, item, index) => {
      if (index !== 0) UrlStr += `&`
      return (UrlStr += `${item}=${obj[item]}`)
    }, '?')
  },
  showShareFriend () {
    if (!this.canSubmit()) return
    this.setData({
      showFriendPayDialog: true
    })
  },
  getFriendPayData (e) {
    this.setData({
      insteadPayNum: e.detail.type
    })
    this.confirmOrder()
  },
  // 请求拼团抽奖订单详情
  requestGroupDraw (orderSn) {
    return new Promise(resolve => {
      util.api('/api/wxapp/groupdraw/info/ordersn', res => {
        console.log(res)
        if (res.error === 0 && res.content) {
          let {
            activityId,
            activityType,
            groupId
          } = res.content
          resolve({
            activityId,
            activityType,
            groupId
          })
        } else {
          util.jumpLink(
            `pages1/payment/payment${this.getUrlParams({
              orderSn,
              useInfo: JSON.stringify({ ...this.data.usePayInfo })
            })}`,
            'redirectTo'
          )
          resolve(false)
        }
      }, {
        orderSn
      })
    })
  },
  setCardData (orderInfo) {
    if (orderInfo.defaultMemberCard) {
      orderInfo.defaultMemberCard = {
        ...orderInfo.defaultMemberCard,
        ...orderInfo.defaultMemberCard.info
      }
    }
    if (orderInfo.memberCards) {
      orderInfo.memberCards = orderInfo.memberCards.map(item => {
        return {
          ...item,
          ...item.info
        }
      })
    }
  },
  // 积分兑换数据
  setScoreRedeemData (orderInfo) {
    let scoreRedeemData = orderInfo.orderGoods.reduce((defaultData, item, index) => {
      if (item.goodsScore) defaultData.score += item.goodsScore * item.goodsNumber
      if (item.goodsPrice) defaultData.money += parseFloat(item.goodsPrice * item.goodsNumber).toFixed(2)
      return defaultData
    }, {
      score: 0,
      money: 0
    })
    this.setData({
      scoreRedeemData
    })
    console.log(this.data.scoreRedeemData)
  },
  changeTerm () {
    this.setData({
      'orderInfo.term.serviceChoose': this.data.orderInfo.term.serviceChoose ? 0 : 1
    })
  },
  goService () {
    util.jumpToWeb('/wxapp/wxapp/checkout/services')
  },
  viewPreSaleRule () {
    this.setData({
      preSaleRuleShow: true
    })
  },
  handleShowDialog (e) {
    let {
      prescriptionCode
    } = e.currentTarget.dataset
    util.api('/api/wxapp/prescription/details', res => {
      if (res.error === 0) {
        this.setData({
          showPrescription: true,
          prescriptionData: res.content
        })
      }
    }, {
      prescriptionCode
    })

  },
  getSubmitButtonStatus () {
    let {
      checkPrescriptionStatus: status
    } = this.data.orderInfo
    if (status === 2) return false
    return true
  },
  togglePatient () {
    if (this.data.isPrescription) return
    util.handleBuriedPoint('create_order_detail_add_patient', 'pages/checkout/checkout', [{
      key: '点击'
    }])
    util.jumpLink('pages1/familylist/familylist?source=checkout')
  },
  getLocationData () {
    return new Promise((resolve, reject) => {
      if (wx.canIUse('wx.getLocation')) {
        console.log('can...')
      }
      wx.getLocation({
        success (res) {
          resolve(res)
        },
        fail (err) {
          if (err.errMsg.indexOf('auth') > -1) {
            wx.getSetting({
              success (tip) {
                console.log('getsetting...', tip)
                if (!tip.authSetting['scope.userLocation']) {
                  wx.showModal({
                    title: '是否授权当前位置',
                    content: '需要获取您的地理位置，请确认授权，否则定位功能将无法使用',
                    success: function (tip) {
                      if (tip.confirm) {
                        wx.openSetting({
                          success: (res) => {
                            console.log(res)
                            if (res.authSetting['scope.userLocation']) {
                              wx.showToast({
                                title: '地理位置授权成功',
                                icon: 'success'
                              })
                              wx.getLocation({
                                success (res) {
                                  console.log(res)
                                  resolve(res)
                                }
                              })
                            } else {
                              util.fail_toast('地理位置授权失败')
                            }
                          },
                          fail: (err) => {
                            console.log(err)
                          }
                        })
                      }
                    }
                  })
                }
              },
              fail (err) {
                console.log('getsetting fail', err)
              }
            })
          }
        }
      })
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () { },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    if (this.data.patientDiagnose) {
      if (this.data.choosePayType === 2) {
        this.showShareFriend()
      }
      this.confirm()
      return
    }
    this.requestOrder()
  },

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
  onReachBottom: function () { }
})
