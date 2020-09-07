var util = require('../../utils/util.js')
const actBaseInfo = {
  1: {
    actName: '拼团',
    multiSkuAct: true,
    prdListName: 'groupBuyPrdMpVos',
    actStatus: {
      0: '距结束仅剩',
      1: '活动不存在',
      2: '活动已停用',
      3: '据开始仅剩',
      4: '活动已结束',
      5: '商品已抢光',
      6: '超购买上限'
    },
    prdPriceName: {
      prdRealPrice: 'groupPrice',
      prdLinePrice: 'prdPrice'
    },
    countDownInfo: {
      canCountDown: [0, 3],
      3: 'startTime',
      0: 'endTime'
    }
  },
  3: {
    actName: '砍价',
    multiSkuAct: false,
    prdRealPrice: 'bargainPrice',
    actStatus: {
      0: '距结束仅剩',
      1: '活动不存在',
      2: '活动已停用',
      3: '据开始仅剩',
      4: '活动已结束',
      5: '商品已抢光',
      6: '超购买上限'
    },
    countDownInfo: {
      canCountDown: [0, 3],
      3: 'startTime',
      0: 'endTime'
    }
  },
  4: {
    actName: '积分',
    multiSkuAct: true,
    prdListName: 'integralMallPrdMpVos',
    actStatus: {
      0: '距结束仅剩',
      1: '活动不存在',
      2: '活动已停用',
      3: '据开始仅剩',
      4: '活动已结束',
      5: '商品已抢光',
      6: '超购买上限'
    }
  },
  5: {
    actName: '秒杀',
    multiSkuAct: true,
    actStatus: {
      0: '距结束仅剩',
      1: '活动不存在',
      2: '活动已停用',
      3: '据开始仅剩',
      4: '活动已结束',
      5: '商品已抢光',
      6: '超购买上限'
    },
    prdListName: 'actProducts',
    prdPriceName: {
      prdRealPrice: 'secKillPrice',
      prdLinePrice: 'prdPrice'
    },
    countDownInfo: {
      canCountDown: [0, 3],
      3: 'startTime',
      0: 'endTime'
    }
  },
  8: {
    actName: '拼团抽奖',
    multiSkuAct: false,
    actStatus: {
      0: '距结束仅剩',
      1: '活动不存在',
      2: '活动已停用',
      3: '据开始仅剩',
      4: '活动已结束',
      5: '商品已抢光',
      6: '超购买上限'
    },
    prdRealPrice: 'payMoney',
    countDownInfo: {
      canCountDown: [0, 3],
      3: 'startTime',
      0: 'endTime'
    }
  },
  10: {
    actName: '定金膨胀',
    multiSkuAct: true,
    actStatus: {
      0: '距结束仅剩',
      1: '活动不存在',
      2: '活动已停用',
      3: '据开始仅剩',
      4: '活动已结束',
      5: '商品已抢光',
      6: '超购买上限'
    },
    prdListName: 'preSalePrdMpVos',
    prdPriceName: {
      prdRealPrice: 'preSalePrice',
      prdLinePrice: 'prdPrice'
    },
    countDownInfo: {
      canCountDown: [0, 3],
      3: 'startTime',
      0: 'endTime'
    }
  },
  18: {
    actName: '首单特惠',
    multiSkuAct: true,
    prdListName: 'firstSpecialPrdMpVos',
    prdPriceName: {
      prdRealPrice: 'firsSpecialPrice',
      prdLinePrice: 'prdPrice'
    }
  },
  6: {
    actName: '限时降价',
    multiSkuAct: true,
    prdListName: 'reducePricePrdMpVos',
    prdPriceName: {
      prdRealPrice: 'reducePrice',
      prdLinePrice: 'prdPrice'
    }
  },
  22: {
    actName: '会员价',
    multiSkuAct: true,
    prdListName: 'gradePrdMpVos',
    prdPriceName: {
      prdRealPrice: 'gradePrice',
      prdLinePrice: 'prdPrice'
    }
  },
  98: {
    actName: '会员价|限时降价',
    multiSkuAct: true,
    prdListName: 'gradeReducePrdVos',
    prdPriceName: {
      prdRealPrice: 'activityPrice',
      prdLinePrice: 'prdPrice'
    }
  }
}
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    goodsRecords: [],
    actBarInfo: {},
    shareAwardId: '',
    actRuleText: {
      1: {
        title: '拼团规则',
        ruleList: ['选择商品，付款开团/参团', '邀请好友,支付参团', '满员发货，不满自动退款']
      },
      3: {
        title: '砍价规则',
        ruleList: ['点击下方“砍价拿”按钮开始', '邀请好友来砍价', '砍价成功，商品低价拿']
      },
      8: {
        title: '拼团抽奖玩法',
        ruleList: [
          ['image/wxapp/pl_icons1.png', '付款开团'],
          ['image/wxapp/pl_icons2.png', '邀请好友'],
          ['image/wxapp/pl_icons3.png', '成团抽奖'],
          ['image/wxapp/pl_icons4.png', '中奖发货']
        ]
      }
    },
    showLive: true,
    addressId: null,
    deliverTemplateId: null
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options, '++++++++++++++++++++++++')
    if (!options.gid) return
    let {
      gid: goodsId,
      aid: activityId = null,
      atp: activityType = null,
      room_id: roomId = null,
      rebateConfig = null,
      inviteId = null,
      isChange = null,
      cardNo = null,
      cardId = null,
      shareAwardLaunchUserId = null,
      shareAwardId = null,
      rebateSId = null,
      uid = null
    } = options
    if(rebateConfig){
      try {
        rebateConfig = JSON.parse(rebateConfig)
      } catch (error) {
        rebateConfig = rebateConfig
      }
    }
    this.setData({
      goodsId,
      activityId: activityId === 'null' ? null : activityId,
      activityType: activityType === 'null' || activityType === '0' ? null : activityType,
      roomId: roomId,
      rebateConfig : rebateConfig,
      inviteId,
      shareAwardLaunchUserId,
      shareAwardId,
      cardExchange: {
        isChange,
        cardNo,
        cardId
      },
      rebateSId,
      uid
    })
    this.requestGoodsInfo()
  },
  // 商品详情请求
  async requestGoodsInfo () {
    let result = new Promise((resolve, reject) => {
      let customParams = {}
      if (this.data.rebateConfig) {
        try {
          customParams.rebateConfig = JSON.parse(this.data.rebateConfig)
        } catch (error) {
          customParams.rebateConfig = this.data.rebateConfig
        }
      }
      if (this.data.shareAwardId && (this.data.shareAwardLaunchUserId || this.data.inviteId || this.data.uid)) {
        customParams.shareAwardId = this.data.shareAwardId
        customParams.shareAwardLaunchUserId = this.data.shareAwardLaunchUserId || this.data.inviteId || this.data.uid
      }
      if(this.data.rebateSId) customParams.rebateSId = this.data.rebateSId
      let userlocation = wx.getStorageSync('userLocation')||{}
      util.api(
        '/api/wxapp/goods/detail',
        res => {
          if (res.error === 0) {
            util.setCache('goods_id', res.content.goodsId)
            if (res.content.delFlag === 1 || (res.content.isOnSale === 0 && !(res.content.activity && res.content.activity.activityType === 4))) {
              console.log(111)
              let tips = res.content.delFlag === 1 ? '抱歉，该商品已删除' : '抱歉，该商品已下架';
              let pageFlag = getCurrentPages().length > 1
              util.showModal('提示', tips, () => {
                if (pageFlag) {
                  wx.navigateBack();
                  return
                }
                util.jumpLink(`pages/index/index`, 'redirectTo')
              }, false, '', pageFlag ? '返回上一页' : '回到首页')
            }
            if (res.content.activity && [1, 3, 5, 8, 10].includes(res.content.activity.activityType)) {
              this.getActivity(res.content) //需要状态栏价格并且倒计时的活动
            }
            if (res.content.activity && [1, 3, 4, 5, 8, 10,].includes(res.content.activity.activityType)) {
              this.setData({
                page_name: actBaseInfo[res.content.activity.activityType]['actName'] + this.$t("components.navigation.title.item")
              })
            }
            let {
              comment,
              goodsImgs,
              goodsVideo,
              goodsVideoImg,
              coupons,
              goodsDesc = null,
              isPageUp = 0,
              goodsPageId = null,
              deliverPlace,
              defaultPrd,
              activity,
              goodsNumber,
              goodsSaleNum,
              labels,
              goodsAd,
              isCollected,
              products,
              goodsName,
              deliverPrice,
              limitBuyNum,
              limitMaxNum,
              goodsId,
              goodsGifts,
              showSalesNumber,
              customService,
              goodsDistribution,
              roomDetailMpInfo,
              deliverFeeAddressVo,
              shareAwardId,
              showMall,
              unit,
              deliverTemplateId, // 运费模板
              isMedical,
              isRx
            } = res.content
            let goodsMediaInfo = {
              goodsImgs, //商品图片
              goodsVideo, //商品视频
              goodsVideoImg //视频封面
            }
            let goodsDescInfo = {
              goodsDesc, //商品描述
              isPageUp, //描述上下位置
              goodsPageId //页面模板ID
            }
            let specParams = {
              goodsId,
              goodsNumber,
              defaultPrd,
              activity,
              products,
              limitBuyNum,
              limitMaxNum,
              goodsImgs,
              unit
            }
            let goodsInfo = {
              goodsSaleNum,
              labels,
              goodsAd,
              isCollected,
              goodsName,
              deliverPrice,
              showSalesNumber,
              customService,
              isMedical,
              isRx,
              ...specParams
            }
            this.setData({
              deliverTemplateId,
              comment, //评价
              deliverPlace, //发货地
              defaultPrd, //是否单规格
              goodsMediaInfo,
              couponList: coupons, //优惠券
              goodsDescInfo,
              goodsGifts, // 赠品,
              goodsDistribution, //分销信息,
              roomDetailMpInfo,
              deliverFeeAddressVo,
              showMall
            })
            this.setData({
              specParams,
              shareAwardId
            })
            this.setData({
              goodsInfo: {
                ...goodsInfo,
                ...this.getPrice(goodsInfo)
              },
              goodsShowStock: this.getGoodsShowStock(specParams)
            })
            if (activity && activity.activityType === 3 && activity.actState === 6) {
              util.jumpLink(`/pages/bargaininfo/bargaininfo?record_id=${activity.recordId}`, 'redirectTo')
            }
            // 限时降价状态栏
            if (res.content.activity && [6, 98].includes(res.content.activity.activityType)) {
              this.setData({
                reduceActBarPrice: this.getActBarPrice(products, activity, 'prdRealPrice')
              })
            }
            // 定金膨胀价格
            if (res.content.activity && res.content.activity.activityType === 10) {
              this.getPreSaleDiscount(res.content.activity.preSalePrdMpVos)
            }
            // 获取促销信息
            this.getPromotions(res.content)

            // 获取直播信息
            if (this.data.roomDetailMpInfo) this.getLiveInfo()

            // [1,5,6,10] 会展示活动预告的活动
            if (res.content.activityAnnounceMpVo) this.getAnnounce(res.content.activityAnnounceMpVo, res.content.defaultPrd)
            this.getShareData() //获取分享内容
            if(res.content.showMall) this.setShareButtonData() //获取好物推荐信息
            resolve(res.content)
            // 购买记录
            this.setData({
              goodsRecords: res.content.goodsRecord
            })
          }
        }, {
        goodsId: this.data.goodsId,
        activityId: this.data.activityId,
        activityType: this.data.activityType,
        userId: util.getCache('user_id'),
        lon: userlocation.longitude?userlocation.longitude:null,
        lat: userlocation.latitude?userlocation.latitude:null,
        ...customParams
      }
      )
    })
    this.requestPledge(await result)
    this.shareInviteData()
  },
  // 服务承诺请求
  requestPledge ({
    brandId = null,
    goodsId,
    catId = null
  }) {
    util.api(
      '/api/wxapp/config/pledge/list',
      res => {
        if (res.error === 0) {
          this.setData({
            pledgeInfo: res.content
          })
        }
      }, {
      goodsId: goodsId,
      catId: catId,
      brandId: brandId
    }
    )
    this.selectComponent('#recommend').requestData() //推荐商品请求
  },
  // 获取规格信息
  getProduct ({
    detail: {
      prdNumber,
      limitBuyNum = null,
      limitMaxNum = null
    }
  }) {
    this.setData({
      limitInfo: {
        prdNumber,
        limitBuyNum,
        limitMaxNum,
        activityType: this.data.specParams.activity ? this.data.specParams.activity.activityType : null
      }
    })
  },
  // 获取选中规格详情
  getProductInfo (data) {
    this.setData({
      productInfo: data.detail
    })
    console.log(this.data.productInfo)
    if (this.data.specParams.activity && this.data.specParams.activity.activityType === 10) {
      this.getPreSaleAct()
    }
    this.setDealtAct()
  },
  // 打开规格弹窗
  showSpecDialog (trigger) {
    this.setData({
      showSpec: true,
      triggerButton: trigger.detail
    })
  },
  // 关闭item页规格弹窗
  bindCloseSpec () {
    this.setData({
      showSpec: false
    })
  },
  // 获取活动信息
  getActivity ({
    activity,
    products
  }) {
    if (!activity) return
    let actBarInfo = {
      activityType: activity.activityType,
      actStatusName: this.getActStatusName(activity),
      prdRealPrice: this.getActBarPrice(products, activity, 'prdRealPrice'),
      prdLinePrice: this.getActBarPrice(products, activity, 'prdLinePrice')
    }
    actBarInfo.actName = this.getActName(activity, actBarInfo)
    this.setData({
      actBarInfo
    })
    this.getCountDown(activity)
    if (activity.activityType === 1 && activity.groupBuyListMpVos.length > 0) {
      this.setListCountDown(activity.groupBuyListMpVos, 'groupBuyListMpVos')
    }
  },

  // 获取actBar活动名称
  getActName ({
    activityType
  }, actBarInfo) {
    if (!activityType || activityType === 3) {
      return null
    }
    if (activityType === 1) {
      return `开团省${(actBarInfo.prdLinePrice - actBarInfo.prdRealPrice).toFixed(2)}元`
    }
    return actBaseInfo[activityType].actName
  },
  // 获取actBar活动状态
  getActStatusName ({
    actState,
    activityType
  }) {
    console.log(actBaseInfo[activityType])
    return actBaseInfo[activityType]['actStatus'][actState] || null
  },
  // 获取actBar价格
  getActBarPrice (products, activity, getPrice) {
    if (getPrice === 'prdLinePrice') {
      return this.getMax(products.map(item => item.prdRealPrice))
    } else if (actBaseInfo[activity.activityType].multiSkuAct) {
      return this.getMin(
        activity[[actBaseInfo[activity.activityType]['prdListName']]].map(item => {
          let {
            [actBaseInfo[activity.activityType]['prdPriceName'][getPrice]]: price
          } = item
          return price
        })
      )
    } else if (getPrice === 'prdRealPrice') {
      return activity[actBaseInfo[activity.activityType][getPrice]]
    }
  },
  // 获取actBar活动倒计时
  getCountDown ({
    activityType,
    actState,
    endTime,
    startTime
  }) {
    if (!actBaseInfo[activityType]['countDownInfo']['canCountDown'].includes(actState)) return
    let total_micro_second =
      actBaseInfo[activityType]['countDownInfo'][actState] === 'startTime' ? startTime : endTime
    console.log(total_micro_second)
    console.log(actBaseInfo[activityType]['countDownInfo'][actState])
    this.countdown(total_micro_second, actState, activityType)
  },
  // 倒计时
  countdown (total_micro_second, actState, activityType) {
    this.actBartime = setInterval(() => {
      total_micro_second -= 1
      let clock = total_micro_second <= 0 ? '已经截至' : util.dateformat(total_micro_second)
      this.setData({
        'actBarInfo.clock': clock
      })
      this.getActCanBuy(total_micro_second, actState, activityType)
    }, 1000)
  },
  getActCanBuy (total_micro_second, actState, activityType) {
    const state = new Map([
      [{
        actState: 'endTime',
        second: true
      },
      () => { }
      ],
      [{
        actState: 'endTime',
        second: false
      },
      () => {
        let actState = Number(Object.keys(actBaseInfo[activityType]['actStatus']).find(k => {
          return actBaseInfo[activityType]['actStatus'][k] === '活动已结束'
        }))
        this.setData({
          'actBarInfo.actStatusName': this.getActStatusName({
            activityType,
            actState
          }),
          'specParams.activity.actState': actState
        })
        clearTimeout(this.actBartime)
        this.getCountDown({
          activityType,
          actState,
          endTime: this.data.specParams.activity.endTime,
          startTime: this.data.specParams.activity.endTime
        })
      }
      ],
      [{
        actState: 'startTime',
        second: true
      },
      () => { }
      ],
      [{
        actState: 'startTime',
        second: false
      },
      () => {
        let actState = Number(Object.keys(actBaseInfo[activityType]['actStatus']).find(k => {
          return actBaseInfo[activityType]['actStatus'][k] === '距结束仅剩'
        }))
        this.setData({
          'actBarInfo.actStatusName': this.getActStatusName({
            activityType,
            actState
          }),
          'specParams.activity.actState': actState
        })
        clearTimeout(this.actBartime)
        console.log(activityType, actState)
        this.getCountDown({
          activityType,
          actState,
          endTime: this.data.specParams.activity.endTime,
          startTime: this.data.specParams.activity.endTime
        })
      }
      ]
    ]);
    [...state]
      .find(([key]) => {
        return (
          key.actState === actBaseInfo[activityType]['countDownInfo'][actState] &&
          key.second === total_micro_second > 0
        )
      })[1]
      .call(this)
  },
  // 设置列表倒计时
  setListCountDown (listData, target) {
    this.listCountDown = setInterval(() => {
      listData = listData.map((v, i) => {
        if (v.remainTime <= 0) {
          v.remainTime = 0
        }
        v.remainTime -= 1
        v.countDown = this.dateformats(v.remainTime)
        return v
      })
      this.setData({
        [target]: listData
      })
    }, 1000)
  },
  // 格式化时间
  dateformats: function (micro_second) {
    // 秒数
    var second = Math.floor(micro_second)
    // 小时位
    var hr = Math.floor(second / 3600)
    // 分钟位
    var min = Math.floor((second - hr * 60 * 60) / 60)
    // 秒位
    var sec = second % 60
    return `${String(hr).padStart(2, '0')}:${String(min).padStart(2, '0')}:${String(sec).padStart(
      2,
      '0'
    )}`
  },
  // 去拼团
  goGroup (e) {
    let activity = this.data.specParams.activity
    if (activity.groupType === 2 && !activity.isNewUser) {
      util.showModal('提示', '抱歉，您不是新用户')
      return
    }
    util.jumpLink(
      `pages1/groupbuyinfo/groupbuyinfo?group_id=${e.currentTarget.dataset.groupId}`,
      'navigateTo'
    )
  },
  // 分享弹窗
  share () {
    this.setData({
      showShareDialog: true
    })
    this.shareInviteData()
  },
  //请求分享数据
  async getShareData () {
    let activityData = {}
    let {
      goodsId: targetId,
      singleRealPrice: realPrice,
      singleLinePrice: linePrice
    } = this.data.goodsInfo
    console.log(this.data.goodsInfo)
    if (this.data.goodsInfo.activity != null) {
      activityData.activityId = this.data.goodsInfo.activity.activityId
      activityData.activityType = this.data.goodsInfo.activity.activityType
      let priceData = {}
      switch (activityData.activityType) {
        case 1:
          activityData.pageType = 1
          break;
        case 3:
          activityData.pageType = 1
          activityData.realPrice = this.data.actBarInfo.prdRealPrice
          activityData.linePrice = this.data.actBarInfo.prdLinePrice
          break;
        case 4:
          activityData.pageType = 1
          activityData.realPrice = this.data.goodsInfo.prdRealPrice.money
          activityData.linePrice = this.data.goodsInfo.prdLinePrice
          activityData.score = this.data.goodsInfo.prdRealPrice.score
          break;
        case 8:
          activityData.pageType = 1
          activityData.realPrice = this.data.actBarInfo.prdRealPrice
          activityData.linePrice = this.data.actBarInfo.prdLinePrice
          break;
        case 10:
          activityData.depositPrice = this.data.goodsInfo.activity === 0 ? this.getMin(this.data.goodsInfo.activity.preSalePrdMpVos.map(item => {
            return item.depositPrice
          })) : this.getMin(this.data.goodsInfo.activity.preSalePrdMpVos.map(item => {
            return item.preSalePrice
          }))
          break;
        case 22:
          priceData = this.data.goodsInfo.products.reduce((defaultData,item)=>{
            if(item.prdRealPrice) defaultData.realPrice.push(item.prdRealPrice)
            if(item.prdLinePrice) defaultData.linePrice.push(item.prdLinePrice)
            return defaultData
          },{realPrice:[],linePrice:[]})
          activityData.realPrice = this.getMin(priceData.realPrice)
          activityData.linePrice = this.getMax(priceData.linePrice)
          break;
        case 98:
          priceData = this.data.goodsInfo.activity.gradeReducePrdVos.reduce((defaultData,item)=>{
            if(item.reducePrice) defaultData.realPrice.push(item.reducePrice)
            if(item.prdPrice) defaultData.linePrice.push(item.prdPrice)
            return defaultData
          },{realPrice:[],linePrice:[]})
          activityData.realPrice = this.getMin(priceData.realPrice)
          activityData.linePrice = this.getMax(priceData.linePrice)
          break;
      }
    }
    let shareData = {
      targetId,
      realPrice,
      linePrice,
      shareAwardId:this.data.shareAwardId || null,
      ...activityData
    }
    console.log(shareData)
    // 提前请求分享内容
    const apiInfo = {
      1: '/api/wxapp/groupbuy/share/info', //拼团
      3: '/api/wxapp/bargain/share/info', //砍价
      4: '/api/wxapp/integral_mall/share/info', //积分兑换
      5: '/api/wxapp/seckill/share/info', //秒杀
      6: '/api/wxapp/reduceprice/share/info', //限时降价
      8: '/api/wxapp/groupdraw/share/info', //拼团抽奖
      10: '/api/wxapp/presale/share/info', //定金膨胀
      18: '/api/wxapp/firstspecial/share/info', //首单特惠
      98: '/api/wxapp/reduceprice/share/info', //限时降价|会员价
      default: '/api/wxapp/goods/share/info' //普通商品
    }
    let target = [1, 3, 4, 5, 6, 8, 10, 18, 98].includes(shareData.activityType) ? apiInfo[shareData.activityType] : apiInfo['default']
    let buttonShareData = await this.requestShareData(target, shareData)
    console.log(buttonShareData)
    this.setData({
      shareData,
      buttonShareData
    })
    console.log(this.data.shareData)
  },
  // 分享有礼接口数据请求
  shareInviteData () {
    util.api('/api/wxapp/shareaward/goods/sharedetail', res => {
      console.log(res, 'get res-data')
      if (res.error === 0) {
        var shareLimit = res.content.dailyShareLimit
        var returnData = res.content.infoVo
        var shareContent1 = res.content.infoVo.shareRules[0]
        var shareContent2 = res.content.infoVo.shareRules[1]
        var shareContent3 = res.content.infoVo.shareRules[2]

        //分享有礼的用户参与信息
        let shareUserArr = res.content.infoVo.shareRules
        // 未邀请
        let notInvited = 4   // 未邀请
        let goingNum = 0   // 每一级参与的人数
        let isHasUser = false  // 是否有用户

        for (i = 0; i < 3; i++) {
          let currentRule = shareUserArr[i]

          if (!currentRule) break
          //	状态
          let cur = currentRule.share_state

          currentRule.share_state = notInvited

          if (!isHasUser) {
            isHasUser = currentRule.user_info_list.length > 0
            console.log(isHasUser)
          }

          if (cur !== 0) {
            currentRule.share_state = cur
          } else if (cur === 0) {
            // cur = 0
            if (goingNum === 0 && isHasUser) {
              currentRule.share_state = cur
              goingNum++
            } else {
              currentRule.share_state = notInvited
            }
          } else {
            currentRule.share_state = cur
          }
        }

        var not_join_user1 = shareContent1.invite_num - shareContent1.user_info_list.length
        for (var i = 0; i < not_join_user1; i++) {
          var peo1 = shareContent1.user_info_list.push(null)
        }
        if (shareContent2) {
          var not_join_user2 = shareContent2.invite_num - shareContent2.user_info_list.length
          for (var i = 0; i < not_join_user2; i++) {
            var peo2 = shareContent2.user_info_list.push(null)
          }
        }
        if (shareContent3) {
          var not_join_user3 = shareContent3.invite_num - shareContent3.user_info_list.length
          for (var i = 0; i < not_join_user3; i++) {
            var peo3 = shareContent3.user_info_list.push(null)
          }
        }
        this.setData({
          returnData,
          shareContent1: peo1,
          shareContent2: peo2,
          shareContent3: peo3,
          shareLimit: shareLimit
        })
      }
    }, {
      "activityId": this.data.shareAwardId,
      "userId": util.getCache('user_id'),
      "goodsId": util.getCache('goods_id')
    })
  },
  // 分享有礼-查看奖励跳转
  getShare (e) {
    console.log(e)
    let reward_type = e.currentTarget.dataset.type
    let stock = e.currentTarget.dataset.stock
    let lottery = e.currentTarget.dataset.lotterty
    console.log(lottery)
    if (reward_type === 1) {
      util.jumpLink('/pages1/integral/integral')
    } else if (reward_type === 2) {
      util.jumpLink('/pages/coupon/coupon')
    } else {
      if (stock === 0) {
        wx.showToast({
          title: '奖励已被领取完',
          duration: 1000
        })
      } else {
        util.jumpLink('pages1/lottery/lottery?lotteryId=' + lottery)
      }
    }
  },
  // 切换收藏
  toogleCollect () {
    let {
      goodsId,
      isCollected
    } = this.data.goodsInfo
    const apiMap = new Map([
      [true, {
        api: '/api/wxapp/cancel/collect',
        msg: '已取消',
        error: '取消失败'
      }],
      [false, {
        api: '/api/wxapp/add/collect',
        msg: '收藏成功',
        error: '收藏失败'
      }]
    ])
    util.api(
      apiMap.get(isCollected).api,
      res => {
        if (res.error === 0) {
          util.toast_success(apiMap.get(isCollected).msg)
          this.setData({
            'goodsInfo.isCollected': !isCollected
          })
        } else {
          util.toast_fail(apiMap.get(isCollected).error)
        }
      }, {
      goodsId
    }
    )
  },
  // 获取最小值
  getMin (arr) {
    return Math.min(...arr)
  },
  // 获取最大值
  getMax (arr) {
    return Math.max(...arr)
  },
  // 获取价格
  getPrice (data) {
    let {
      products,
      activity
    } = data
    if (activity && activity.activityType === 4) {
      let actProductList = JSON.parse(JSON.stringify(activity[actBaseInfo[activity.activityType]['prdListName']]))
      actProductList.sort((a, b) => {
        return a.score - b.score
      })
      return {
        prdRealPrice: actProductList[0],
        prdLinePrice: products.find(item => {
          return item.prdId === actProductList[0].productId
        }).prdRealPrice,
      }
    } else {
      if (activity && actBaseInfo[activity.activityType].multiSkuAct) {
        products = activity[actBaseInfo[activity.activityType]['prdListName']]
        if (activity.activityType === 6 && activity.actState != 0) {
          products = data.products
        }
      }
      let {
        realPrice,
        linePrice
      } = products.reduce(
        (defaultData, val) => {
          if (activity && actBaseInfo[activity.activityType].multiSkuAct) {
            var {
              [actBaseInfo[activity.activityType]['prdPriceName']['prdRealPrice']]: prdRealPrice,
              [actBaseInfo[activity.activityType]['prdPriceName']['prdLinePrice']]: prdLinePrice
            } = val
            if (activity.activityType === 6 && activity.actState != 0) {
              var {
                prdRealPrice,
                prdLinePrice
              } = val
            }
          } else {
            var {
              prdRealPrice,
              prdLinePrice
            } = val
          }
          defaultData.realPrice.push(prdRealPrice)
          defaultData.linePrice.push(prdLinePrice)
          return defaultData
        }, {
        realPrice: [],
        linePrice: []
      }
      )
      let realMinPrice = this.getMin(realPrice),
        realMaxPrice = this.getMax(realPrice),
        lineMinPrice = this.getMin(linePrice),
        lineMaxPrice = this.getMax(linePrice)
      return {
        prdRealPrice: data.defaultPrd ?
          realPrice[0] : realMinPrice === realMaxPrice ?
            realMinPrice : `${realMinPrice}~${realMaxPrice}`,
        prdLinePrice: data.defaultPrd ?
          linePrice[0] : lineMinPrice === lineMaxPrice ?
            lineMaxPrice : `${lineMinPrice}~${lineMaxPrice}`,
        singleRealPrice: realMinPrice,
        singleRealMaxPrice: realMaxPrice,
        singleLinePrice: lineMaxPrice
      }
    }
  },
  // 获取促销信息
  getPromotions ({
    promotions
  }) {
    if (JSON.stringify(promotions) === '{}') return
    let promotionArr = Object.keys(promotions).map(k => {
      return promotions[k].map(item => {
        return {
          type: k,
          ...this.getPromotionInfo(k, item)
        }
      })
    })
    this.setData({
      promotionInfo: promotionArr.flat(Infinity)
    })
  },
  getPromotionInfo (promotionType, info) {
    let data = {
      id: info.promotionId
    }
    switch (promotionType) {
      case '7':
        if (Array.isArray(info.purchasePriceRules) && info.purchasePriceRules.length > 0) {
          data.desc = ''
          info.purchasePriceRules.forEach((item, index) => {
            if (index > 0) {
              data.desc += '，或'
            }
            data.desc += `满${item.fullPrice}元另加${item.purchasePrice}元`
            if (index === info.purchasePriceRules.length - 1) {
              data.desc += `即可换购商品`
            }
          })
        }
        return data
      case '9':
        data.desc = `${info.goodsCount}件${info.priceOrDiscount + (info.packageType === 1 ? '折' : '元')}`
        return data
      case '15':
        if (info.conType === 0) {
          data.desc = `满${info.money}元，`
        }
        if (info.conType === 1) {
          data.desc = `满${info.num}件，`
        }
        if (info.conType === 2) {
          data.desc = `满${info.money}元或${info.num}件，`
        }
        data.desc += `部分地区包邮`
        return data
      case '18':
        if (info.isLimit) {
          if (info.limitFlag) data.desc = `每人限购${info.limitAmount}，购买不超过限购数量时享受单价￥${this.data.goodsInfo.singleRealPrice}`
          if (!info.limitFlag) data.desc = `购买不超过${info.limitAmount}个时享受单价￥${this.data.goodsInfo.singleRealPrice}，超出则该商品全部恢复非活动价`
        } else {
          data.desc = `首单享低价`
        }
        return data
      case '19':
        if (info.goodsAreaType === 1) {
          data.desc = `购买“全部商品”`
        } else {
          data.desc = `购买“指定商品”`
        }
        if (info.minPayMoney > 0) {
          data.desc += `且“订单金额满${info.minPayMoney}元”`
        }
        data.desc += `可获得活动奖励`
        return data
      case '21':
        data.desc = ''
        data.isExclusive = info.isExclusive
        info.rules.forEach((item, index) => {
          if (index !== 0) data.desc += '，或'
          if (info.type === 1) {
            if (item.amount > 0) {
              data.desc += `每满${item.amount}件`
            } else {
              data.desc += `每满${item.fullMoney}元`
            }
          } else if (info.type === 2 || info.type === 3) {
            if (item.amount > 0) {
              data.desc += `满${item.amount}件`
            } else {
              data.desc += `满${item.fullMoney}元`
            }
          } else if (info.type === 4) {
            data.desc += `第${item.amount}件`
          }
          if (info.type === 1 || info.type === 2) {
            data.desc += `，减${item.reduceMoney}元`
          } else {
            data.desc += `，打${item.discount}折`
          }
        })
        return data
    }
  },
  goRule (e) {
    let {
      type
    } = e.currentTarget.dataset
    switch (type) {
      case 1:
        util.jumpToWeb('/wxapp/wxapp/group/help', '&gid=' + this.data.specParams.activity.activityId)
        break

      case 3:
        util.jumpToWeb('/wxapp/wxapp/bargain/help')
        break

      case 8:
        util.jumpToWeb('/wxapp/wxapp/pinlottery/help', '&gid=' + this.data.specParams.activity.activityId)
        break
    }
  },
  setDealtAct (actState) {
    let {
      activity
    } = this.data.goodsInfo,
      productInfo = this.data.productInfo
    let dealtAct = {
      error: 0
    }
    if ((actState && actState === 3) || actState === 4) {
      dealtAct = {
        error: 1,
        errorMessage: `${actBaseInfo[activity.activityType]['actName']}${
          actState === 4 ? actBaseInfo[activity.activityType]['actStatus'][actState] : '活动未开始'
          }`
      }
    } else if (activity && [1, 3, 5, 10].includes(activity.activityType) && [1, 2, 3, 4, 5, 6].includes(activity.actState)) {
      dealtAct = {
        error: 1,
        errorMessage: `${actBaseInfo[activity.activityType]['actName']}${
          actBaseInfo[activity.activityType]['actStatus'][activity.actState]
          }`
      }
    }
    this.setData({
      dealtAct
    })
  },
  getPreSaleAct () {
    let preActBarStr = ''
    console.log(this.data.productInfo)
    if (this.data.specParams.activity.preSaleType !== 1) {
      preActBarStr = `付定金立减:￥${(this.data.productInfo.actProduct.discountPrice - this.data.productInfo.actProduct.depositPrice).toFixed(2)}`
    } else {
      preActBarStr = `定金:￥${this.data.productInfo.actProduct.preSalePrice}`
    }
    this.setData({
      'actBarInfo.preSaleActInfo': preActBarStr
    })
  },
  getPreSaleDiscount (prdList) {
    if (this.defaultPrd) {
      this.setData({
        PreSaleDiscountPrice: prdList[0].discountPrice
      })
    } else {
      let priceArr = prdList.map(item => {
        return item.discountPrice
      });
      let minPrice = this.getMin(priceArr),
        maxPrice = this.getMax(priceArr)
      this.setData({
        PreSaleDiscountPrice: minPrice === maxPrice ? minPrice : `${minPrice}~${maxPrice}`
      })
    }
  },
  goPledge () {
    util.jumpLink(
      'pages1/pledgeannounce/pledgeannounce?pledgeList=' +
      JSON.stringify(this.data.pledgeInfo.pledgeListVo)
    )
  },
  viewPreSaleRule () {
    this.setData({
      preSaleRuleShow: true
    })
  },
  goLive () {
    let {
      roomId
    } = this.data.roomDetailMpInfo
    roomId = [roomId]
    wx.navigateTo({
      url: `plugin-private://wx2b03c6e691cd7370/pages/live-player-plugin?room_id=${roomId}`
    })
  },
  getLiveInfo () {
    let { roomId, liveStatus } = this.data.roomDetailMpInfo
    livePlayer.getLiveStatus({ room_id: roomId })
      .then(res => {
        let liveStatus = res.liveStatus
        this.setData({
          liveStatus
        })
        console.log('get live status', liveStatus)
      })
      .catch(err => {
        console.log('get live status', err)
      })
  },
  closeLive () {
    this.setData({
      showLive: false
    })
  },
  getGoodsShowStock ({
    goodsNumber,
    activity = null
  }) {
    if (activity && [1, 3, 4, 5, 10].includes(activity.activityType)) {
      return activity.stock
    }
    return goodsNumber
  },
  getAnnounce (announce, isDefault) {
    let priceName = {
      1: '拼团价',
      5: '秒杀价',
      6: '限时价',
      10: '定金价'
    }
    this.setData({
      announceData: {
        tagName: actBaseInfo[announce.activityType].actName,
        priceName: priceName[announce.activityType],
        startTime: announce.startTime.substring(5, 16),
        price: parseFloat(announce.realPrice).toFixed(2) + (!isDefault ? '起' : '')
      }
    })
  },
  addressChange ({
    detail: addressData
  }) {
    this.setData({
      'deliverFeeAddressVo.status': addressData.status
    })
  },
  setShareButtonData(){
    let goodsId = this.data.goodsId
    util.api('/api/wxapp/mall/goods',res=>{
      if(res.error === 0){
        this.setData({
          shareButtonData:res.content[0].product
        })
      }
    },{goodsId:[goodsId]})
  },
  // 获取购物车中商品数量
  getCartNum () {
    util.api('/api/wxapp/cart/list', res => {
      if (res.error === 0) {
        let {
          cartGoodsList,
          purchasePriceGoodsMap,
          fullReductionGoodsMap
        } = res.content
        let purchaseGoodsList = Object.keys(purchasePriceGoodsMap).reduce((defaultData,item)=>{
          defaultData = [...defaultData,...purchasePriceGoodsMap[item]]
          return defaultData
        },[])
        let fullReductionGoodsList = Object.keys(fullReductionGoodsMap).reduce((defaultData,item)=>{
          defaultData = [...defaultData,...fullReductionGoodsMap[item]]
          return defaultData
        },[])
        let goodsList = [...purchaseGoodsList,...fullReductionGoodsList,...cartGoodsList]
        console.log(goodsList)
        let cartNum = goodsList.reduce((total, item, index) => {
          return total += item.cartNumber
        }, 0)
        this.setData({
          cartNum
        })
      }
    })
  },
  handleDownloadCb(){
    let toast = this.selectComponent('#toast')
    if(!this.data.goodsDistribution || !this.data.goodsDistribution.promotionLanguage) {
      toast.showToast({
        title: '图片已保存到相册',
        duration:2000
      })
    } else {
      wx.setClipboardData({
        data: this.data.goodsDistribution.promotionLanguage,
        success: (res) => {
          wx.hideToast();
          toast.showToast({
            title: '图片已保存到相册',
            content:`${this.data.goodsDistribution.promotionLanguage} 以上推广语已复制`,
            duration:4000
          })
        }
      });
    }
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () { },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    let pages = getCurrentPages()
    console.log(pages)
    if (this.data.addressId) {
      // this.requestAddress()
      this.calculateShipping()
    }
  },

  requestAddress () {
    let that = this
    util.api('/api/wxapp/address/get', op => {
      if (op.error === 0) {
        that.setData({
          'deliverFeeAddressVo.address': op.content
        })
      }
    }, {addressId: this.data.addressId})
  },

  calculateShipping () {
    let that = this
    util.api('/api/wxapp/address/shipping', res => {
      if (res.error === 0) {
        console.log('hasV', res)
        that.setData({
          'deliverFeeAddressVo': res.content
        })
      }
    }, {
      goodsId: that.data.goodsId,
      addressId: that.data.addressId,
      deliverTemplateId: that.data.deliverTemplateId,
      goodsNum: this.data.productInfo.goodsNum,
      goodsPrice: this.data.productInfo.prdRealPrice,
      goodsWeight: this.data.productInfo.prdWeight || 1
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () { },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    clearTimeout(this.actBartime)
    clearTimeout(this.listCountDown)
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () { },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    this.selectComponent('#recommend').requestData()
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    console.log(this.data.buttonShareData)
    //分享记录
    util.api('/api/wxapp/shareaward/goods/share', res => {
      console.log(res)
    }, {
      goodsId: this.data.goodsId,
      activityId: this.data.goodsInfo.activity != null ? this.data.goodsInfo.activity.activityId : null,
      activityType: this.data.goodsInfo.activity != null ? this.data.goodsInfo.activity.activityType : null,
      userId: util.getCache('user_id'),
    })
    //分享有礼记录
    if (this.data.shareAwardId) {
      util.api('/api/wxapp/shareaward/goods/shareaward', res => {
        console.log(res)
      }, {
        goodsId: this.data.goodsId,
        activityId: this.data.shareAwardId,
        userId: util.getCache('user_id'),
      })
    }
    return {
      ...this.data.buttonShareData
    }
  },
  requestShareData (target, shareData) {
    return new Promise(resolve => {
      util.api(target, res => {
        if (res.error === 0) {
          let path = `/pages/item/item?gid=${this.data.goodsId}`
          if (this.data.goodsInfo.activity) {
            path += `&atp=${this.data.goodsInfo.activity.activityType}&aid=${this.data.goodsInfo.activity.activityId}`
          }
          if (this.data.shareAwardId) {
            path += `&shareAwardId=${this.data.shareAwardId}&shareAwardLaunchUserId=${util.getCache('user_id')}`
          }
          console.log(path)
          resolve({
            title: res.content.shareDoc,
            path: path,
            imageUrl: res.content.imgUrl,
          })
        }
      }, {
        ...shareData,
        userName: util.getCache('nickName')
      })
    })
  }
})