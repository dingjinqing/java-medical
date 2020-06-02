var util = require('../../utils/util.js')
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
    payType: [0,1],
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
      isCart: 0
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
    options: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let goods = []
    let { goodsList, activityType, activityId, recordId, preSaleInfo=null } = options
    console.log(options)
    wx.setStorage({
      data: options,
      key: 'orderOptions'
    })
    JSON.parse(goodsList).forEach(item => {
      let {
        goodsId,
        prdRealPrice: goodsPrice,
        goodsNum: goodsNumber,
        prdId: productId,
        isCart = 0
      } = item
      if(Number(activityType) === 4) {goodsPrice = 0}
      goods.push({ goodsId, goodsPrice, goodsNumber, productId, isCart })
    })
    if (preSaleInfo) {
      preSaleInfo = JSON.parse(preSaleInfo)
    }
    this.setData({
      'params.goods': goods,
      'params.isCart': goods[0].isCart, //购物车来源|商品详情
      'params.activityType': activityType,
      'params.activityId': activityId,
      'params.recordId': recordId
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
          this.setData({
            orderInfo
          })
          console.log(this.data.orderInfo)
          if(orderInfo.activityType === 4){ //积分兑换数据
            this.setScoreRedeemData(orderInfo)
          }
          this.getCouponData(orderInfo)
          this.defaultInput(orderInfo)
          this.getPayType(orderInfo)
        } else {
          util.showModal('提示', res.message, function () {
            let pages = getCurrentPages()
            if (pages.length > 1) {
              wx.navigateBack()
            } else {
              util.jumpLink('/pages/index/index', 'reLaunch')
            }
          }, false, '', '确定')
        }
      },
      { ...this.data.params }
    )
  },
  getPayType (orderinfo) {
    let payType = this.data.payType
    if (!payType.includes(2) && orderinfo.insteadPayCfg && orderinfo.insteadPayCfg.status && (orderinfo.insteadPayCfg.singlePay || orderinfo.insteadPayCfg.multiplePay)) {
      payType.push(2)
    }
    if(this.data.scoreRedeemData && this.data.scoreRedeemData.score){
      payType = [3]
      this.setData({
        choosePayType : 3
      })
    }
    if(this.data.scoreRedeemData && this.data.scoreRedeemData.money){
      payType = [0]
      this.setData({
        choosePayType : 0
      })
    }
    this.setData({
      payType
    })
  },
  // 选择地址
  addAddress () {
    util.navigateTo('/components/usercenter/useraddress/useraddress?select=1')
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
      let useCardBalance = moneyPaid - memberCardMoney > 0 ? memberCardMoney : moneyPaid
      moneyPaid -= useCardBalance
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
      let useBalance = moneyPaid - userAccount > 0 ? userAccount : moneyPaid
      moneyPaid -= useBalance
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
      let useScore =
        moneyPaid * scoreProportion > scoreMaxDiscount * scoreProportion
          ? scoreMaxDiscount * scoreProportion > userScore
            ? userScore
            : scoreMaxDiscount * scoreProportion
          : moneyPaid * scoreProportion > userScore
            ? userScore
            : moneyPaid * scoreProportion
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
    let { coupons, defaultCoupon } = orderInfo
    if (coupons === null || coupons.length === 0) {
      this.setData({
        couponArray: null,
        defaultCouponIndex: null
      })
      return
    }
    let couponList = coupons.map(item => {
      let newItem = JSON.parse(JSON.stringify(item))
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
    let couponArray = [{ couponSn: null, text: '不使用优惠券' }, ...couponList]
    let defaultCouponIndex =
      (defaultCoupon && couponArray.findIndex(item => item.couponSn === defaultCoupon.couponSn)) ||
      0
    this.setData({
      couponArray,
      defaultCouponIndex
    })
  },

  // 变更优惠券
  couponChange (e) {
    let { couponSn } = this.data.couponArray[parseInt(e.detail.value)]
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
    this.setData({
      'params.deliverType': e.currentTarget.dataset.index
    })
    this.requestOrder()
  },
  // 选择门店
  selectStore () {
    if (this.data.chooseShippingIndex === 0) return
    let storeDialogData = {}
    storeDialogData.openType = this.data.chooseShippingIndex
    storeDialogData.data = [
      { id: 1, storeName: '伊泰大厦', address: '潘家园', distance: '5.46' },
      { id: 2, storeName: '回龙观大厦', address: '西直门', distance: '0.15' },
      { id: 3, storeName: '西二旗大厦', address: '西二旗', distance: '15' },
      { id: 4, storeName: '霍营大厦', address: '蜂鸟社区', distance: '123' }
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
    let { id: storeId, openType } = info.detail
    console.log(storeId, openType)
    switch (openType) {
      case 2:
        break
      default:
        break
    }
  },
  // 是否使用发票
  bindInvoiceChange (e) {
    this.setData({
      invoiceIndex: parseInt(e.detail.value)
    })
  },
  // 下单必填项
  mustInput (e) {
    let { type } = e.currentTarget.dataset,
      { value } = e.detail
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
    let { value: message } = e.detail
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
    console.log(this.data.orderInfo.must)
    console.log(this.data.must)
    let mustTips = ''
    if(this.data.orderInfo.must.isShow && this.data.orderInfo.must.consigneeCid && !this.data.must.consigneeCid) mustTips = '收货人身份证为必填项，请输入'
    if(this.data.orderInfo.must.isShow && this.data.orderInfo.must.consigneeRealName && !this.data.must.consigneeRealName) mustTips = '收货人姓名为必填项，请输入'
    if(this.data.orderInfo.must.isShow && this.data.orderInfo.must.orderCid && !this.data.must.orderCid) mustTips = '下单人身份证为必填项，请输入'
    if(this.data.orderInfo.must.isShow && this.data.orderInfo.must.orderRealName && !this.data.must.orderRealName) mustTips = '下单人姓名为必填项，请输入'
    if(this.data.orderInfo.must.isShow && this.data.orderInfo.must.custom && !this.data.must.custom) mustTips = `${this.data.orderInfo.must.custom}为必填项，请输入`
    if(mustTips){
      util.showModal('提示',mustTips)
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
  confirmOrder() {
    console.log(11111)
    util.throttle(this.confirm,5000)()
  },
  confirm(){
    if(!this.canSubmit()) return
    util.getNeedTemplateId('add_order',()=>{
      let { orderGoods: goods, orderAmount, paymentList, activityType, activityId } =
        this.data.orderInfo || {}
      let {
        useBalance: balance,
        useCardBalance: cardBalance,
        useScore: scoreDiscount
      } = this.data.usePayInfo
      let addressId = (this.data.orderInfo.address && this.data.orderInfo.address.addressId) || null
      let couponSn =
        (this.data.orderInfo.defaultCoupon && this.data.orderInfo.defaultCoupon.couponSn) || null
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
      console.log(addParams)
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
            let { orderSn } = res.content
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
                  if(activityType === 8){
                    let groupInfo = await this.requestGroupDraw(orderSn)
                    console.log(groupInfo)
                    if(!groupInfo) return
                    util.jumpLink(`pages1/pinlotteryinfo/pinlotteryinfo${this.getUrlParams({group_draw_id:groupInfo.activityId,group_id:groupInfo.groupId,goods_id:goods[0].goodsId})}`,'redirectTo')
                  } else {
                    let scoreRedeemData = this.data.orderInfo.activityType === 4 && this.data.scoreRedeemData.score > 0 ? {useScore:this.data.scoreRedeemData.score} : {}
                    util.jumpLink(
                      `pages1/payment/payment${this.getUrlParams({
                        orderSn,
                        useInfo: JSON.stringify({ ...this.data.usePayInfo,...scoreRedeemData })
                      })}`,
                      'redirectTo'
                    )
                  }
                },
                fail: res => {
                  console.log(res)
                  util.jumpLink(`/pages/orderinfo/orderinfo?orderSn=${orderSn}`, 'redirectTo')
                },
                complete: res => { }
              })
            } else {
              if(activityType === 8){
                let groupInfo = await this.requestGroupDraw(orderSn)
                console.log(groupInfo)
                if(!groupInfo) return
                util.jumpLink(`pages1/pinlotteryinfo/pinlotteryinfo${this.getUrlParams({group_draw_id:groupInfo.activityId,group_id:groupInfo.groupId,goods_id:goods[0].goodsId})}`,'redirectTo')
              } else {
                let scoreRedeemData = this.data.orderInfo.activityType === 4 && this.data.scoreRedeemData.score > 0 ? {useScore:this.data.scoreRedeemData.score} : {}
                util.jumpLink(
                  `pages1/payment/payment${this.getUrlParams({
                    orderSn,
                    useInfo: JSON.stringify({ ...this.data.usePayInfo, ...scoreRedeemData})
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
  requestGroupDraw(orderSn){
    return new Promise(resolve=>{
      util.api('/api/wxapp/groupdraw/info/ordersn',res=>{
        console.log(res)
        if(res.error === 0 && res.content){
          let {activityId,activityType,groupId} = res.content
          resolve({activityId,activityType,groupId}) 
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
      },{
        orderSn
      })
    })
  },
  setCardData(orderInfo){
    if(orderInfo.defaultMemberCard){
      orderInfo.defaultMemberCard = {...orderInfo.defaultMemberCard,...orderInfo.defaultMemberCard.info}
    }
    if(orderInfo.memberCards){
      orderInfo.memberCards = orderInfo.memberCards.map(item=>{
        return {...item,...item.info}
      })
    }
  },
  // 积分兑换数据
  setScoreRedeemData(orderInfo){
   let scoreRedeemData = orderInfo.orderGoods.reduce((defaultData,item,index)=>{
      if(item.goodsScore) defaultData.score += item.goodsScore * item.goodsNumber
      if(item.discountedGoodsPrice) defaultData.money += item.discountedGoodsPrice * item.goodsNumber
      return defaultData
    },{score:0,money:0})
    this.setData({scoreRedeemData})
    console.log(this.data.scoreRedeemData)
  },
  viewPreSaleRule(){
    this.setData({
      preSaleRuleShow:true
    })
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
  onReachBottom: function () { }
})
