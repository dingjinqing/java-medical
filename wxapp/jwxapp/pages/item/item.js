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
        ruleList: [['image/wxapp/pl_icons1.png', '付款开团'], ['image/wxapp/pl_icons2.png', '邀请好友'], ['image/wxapp/pl_icons3.png', '成团抽奖'], ['image/wxapp/pl_icons4.png', '中奖发货']]
      }
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options, '++++++++++++++++++++++++')
    if (options.scene) options = this.resetScene(options.scene)
    if (!options.gid) return
    let { gid: goodsId, aid: activityId = null, atp: activityType = null } = options
    this.setData({
      goodsId,
      activityId,
      activityType
    })
    this.requestGoodsInfo()
  },
  // 商品详情请求
  async requestGoodsInfo () {
    let result = new Promise((resolve, reject) => {
      util.api(
        '/api/wxapp/goods/detail',
        res => {
          if (res.error === 0) {
            if (res.content.delFlag === 1 || res.content.isOnSale === 0) {
              let tips = res.content.delFlag === 1 ? '抱歉，该商品已删除' : '抱歉，该商品已下架';
              let pageFlag = getCurrentPages().length > 1
              util.showModal('提示', tips, () => {
                if (pageFlag) { wx.navigateBack(); return }
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
              goodsDistribution
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
              goodsImgs
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
              ...specParams
            }
            this.setData({
              comment, //评价
              deliverPlace, //发货地
              defaultPrd, //是否单规格
              goodsMediaInfo,
              couponList: coupons, //优惠券
              goodsDescInfo,
              goodsGifts, // 赠品,
              goodsDistribution //分销信息
            })
            this.setData({
              specParams
            })
            this.setData({
              goodsInfo: {
                ...goodsInfo,
                ...this.getPrice(goodsInfo)
              }
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
            this.getPromotions(res.content)
            resolve(res.content)
            // 购买记录
            this.setData({
              goodsRecords: res.content.goodsRecord
            })
          }
        },
        {
          goodsId: this.data.goodsId,
          activityId: this.data.activityId,
          activityType: this.data.activityType,
          userId: util.getCache('user_id'),
          lon: null,
          lat: null
        }
      )
    })
    this.requestPledge(await result)
  },
  // 服务承诺请求
  requestPledge ({ brandId = null, goodsId, catId = null }) {
    util.api(
      '/api/wxapp/config/pledge/list',
      res => {
        if (res.error === 0) {
          this.setData({
            pledgeInfo: res.content
          })
        }
      },
      {
        goodsId: goodsId,
        catId: catId,
        brandId: brandId
      }
    )
    this.selectComponent('#recommend').requestData() //推荐商品请求
  },
  // 获取规格信息
  getProduct ({
    detail: { prdNumber, limitBuyNum = null, limitMaxNum = null }
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
  getActivity ({ activity, products }) {
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
  getActName ({ activityType }, actBarInfo) {
    if (!activityType || activityType === 3) {
      return null
    }
    if (activityType === 1) {
      return `开团省${(actBarInfo.prdLinePrice - actBarInfo.prdRealPrice).toFixed(2)}元`
    }
    return actBaseInfo[activityType].actName
  },
  // 获取actBar活动状态
  getActStatusName ({ actState, activityType }) {
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
          let { [actBaseInfo[activity.activityType]['prdPriceName'][getPrice]]: price } = item
          return price
        })
      )
    } else if (getPrice === 'prdRealPrice') {
      return activity[actBaseInfo[activity.activityType][getPrice]]
    }
  },
  // 获取actBar活动倒计时
  getCountDown ({ activityType, actState, endTime, startTime }) {
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
      [
        { actState: 'endTime', second: true },
        () => { }
      ],
      [
        { actState: 'endTime', second: false },
        () => {
          let actState = Number(Object.keys(actBaseInfo[activityType]['actStatus']).find(k => {
            return actBaseInfo[activityType]['actStatus'][k] === '活动已结束'
          }))
          this.setData({
            'actBarInfo.actStatusName': this.getActStatusName({ activityType, actState }),
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
      [
        { actState: 'startTime', second: true },
        () => { }
      ],
      [
        { actState: 'startTime', second: false },
        () => {
          let actState = Number(Object.keys(actBaseInfo[activityType]['actStatus']).find(k => {
            return actBaseInfo[activityType]['actStatus'][k] === '距结束仅剩'
          }))
          this.setData({
            'actBarInfo.actStatusName': this.getActStatusName({ activityType, actState }),
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
    ])
      ;[...state]
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
  async share () {
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
      switch (activityData.activityType) {
        case 1:
          activityData.pageType = 1
          break;
        case 3:
          activityData.pageType = 1
          activityData.realPrice = this.data.actBarInfo.prdRealPrice
          activityData.linePrice = this.data.actBarInfo.prdLinePrice
          break;
        case 8:
          activityData.pageType = 1
          activityData.realPrice = this.data.actBarInfo.prdRealPrice
          activityData.linePrice = this.data.actBarInfo.prdLinePrice
          break;
        case 10:
          activityData.depositPrice = this.getMin(this.data.goodsInfo.activity.preSalePrdMpVos.map(item => { return item.depositPrice }))
          break;
      }
    }
    let shareData = {
      targetId,
      realPrice,
      linePrice,
      ...activityData
    }
    // 提前请求分享内容
    const apiInfo = {
      1: '/api/wxapp/groupbuy/share/info',//拼团 
      3: '/api/wxapp/bargain/share/info', //砍价
      6: '/api/wxapp/reduceprice/share/info', //限时降价
      8: 'url:/api/wxapp/groupdraw/share/info', //拼团抽奖
      10: '/api/wxapp/presale/share/info', //定金膨胀
      18: '/api/wxapp/firstspecial/share/info', //首单特惠
      98: '/api/wxapp/reduceprice/share/info', //限时降价|会员价
      default: '/api/wxapp/goods/share/info'//普通商品
    }
    let target = [1, 3, 5, 6, 8, 10, 18, 98].includes(shareData.activityType) ? apiInfo[shareData.activityType] : apiInfo['default']
    let buttonShareData = await this.requestShareData(target, shareData)
    console.log(buttonShareData)
    this.setData({
      shareData,
      showShareDialog: true,
      buttonShareData
    })
  },
  // 切换收藏
  toogleCollect () {
    let { goodsId, isCollected } = this.data.goodsInfo
    const apiMap = new Map([
      [true, { api: '/api/wxapp/cancel/collect', msg: '已取消', error: '取消失败' }],
      [false, { api: '/api/wxapp/add/collect', msg: '收藏成功', error: '收藏失败' }]
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
      },
      { goodsId }
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
    let { products, activity } = data
    if (activity && activity.activityType === 4) {
      let actProductList = JSON.parse(JSON.stringify(activity[actBaseInfo[activity.activityType]['prdListName']]))
      actProductList.sort((a, b) => {
        return a.score - b.score
      })
      return {
        prdRealPrice: actProductList[0],
        prdLinePrice: products.find(item => { return item.prdId === actProductList[0].productId }).prdRealPrice,
      }
    } else {
      if (activity && actBaseInfo[activity.activityType].multiSkuAct) {
        products = activity[actBaseInfo[activity.activityType]['prdListName']]
        if (activity.activityType === 6 && activity.actState != 0) {
          products = data.products
        }
      }
      let { realPrice, linePrice } = products.reduce(
        (defaultData, val) => {
          if (activity && actBaseInfo[activity.activityType].multiSkuAct) {
            var {
              [actBaseInfo[activity.activityType]['prdPriceName']['prdRealPrice']]: prdRealPrice,
              [actBaseInfo[activity.activityType]['prdPriceName']['prdLinePrice']]: prdLinePrice
            } = val
            if (activity.activityType === 6 && activity.actState != 0) {
              var { prdRealPrice, prdLinePrice } = val
            }
          } else {
            var { prdRealPrice, prdLinePrice } = val
          }
          defaultData.realPrice.push(prdRealPrice)
          defaultData.linePrice.push(prdLinePrice)
          return defaultData
        },
        { realPrice: [], linePrice: [] }
      )
      let realMinPrice = this.getMin(realPrice),
        realMaxPrice = this.getMax(realPrice),
        lineMinPrice = this.getMin(linePrice),
        lineMaxPrice = this.getMax(linePrice)
      return {
        prdRealPrice: data.defaultPrd
          ? realPrice[0]
          : realMinPrice === realMaxPrice
            ? realMinPrice
            : `${realMinPrice}~${realMaxPrice}`,
        prdLinePrice: data.defaultPrd
          ? linePrice[0]
          : lineMinPrice === lineMaxPrice
            ? lineMaxPrice
            : `${lineMinPrice}~${lineMaxPrice}`,
        singleRealPrice: realMinPrice,
        singleRealMaxPrice: realMaxPrice,
        singleLinePrice: lineMaxPrice
      }
    }
  },
  // 获取促销信息
  getPromotions ({ promotions }) {
    if (JSON.stringify(promotions) === '{}') return
    let promotionArr = Object.keys(promotions).map(k => {
      return promotions[k].map(item => {
        return { type: k, ...this.getPromotionInfo(k, item) }
      })
    })
    this.setData({
      promotionInfo: promotionArr.flat(Infinity)
    })
  },
  getPromotionInfo (promotionType, info) {
    let data = { id: info.promotionId }
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
          data.desc = `购买“指定商品”`
        } else {
          data.desc = `购买“全部商品”`
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
    let { type } = e.currentTarget.dataset
    switch (type) {
      case 1:
        util.jumpToWeb('/wxapp/group/help')
        break

      case 3:
        util.jumpToWeb('/wxapp/bargain/help')
        break

      case 8:
        util.jumpToWeb('/wxapp/pinlottery/help')
        break
    }
  },
  setDealtAct (actState) {
    let { activity } = this.data.goodsInfo,
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
    } else if (activity && [1, 3, 5, 10].includes(activity.activityType) && [1, 2, 3, 4, 5, 6].includes(activity.actState)
    ) {
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
      preActBarStr = `付定金立减:￥${this.data.productInfo.actProduct.discountPrice - this.data.productInfo.actProduct.depositPrice}`
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
      let priceArr = prdList.map(item => { return item.discountPrice });
      let minPrice = this.getMin(priceArr), maxPrice = this.getMax(priceArr)
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
  resetScene (scene) {
    return decodeURIComponent(scene).split('&').reduce((defaultData, item) => {
      let params = item.split('=')
      defaultData[params[0]] = params[1]
      return defaultData
    }, {})
  },
  viewPreSaleRule () {
    this.setData({
      preSaleRuleShow: true
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
          console.log(res)
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
