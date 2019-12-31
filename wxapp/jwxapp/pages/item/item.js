var util = require("../../utils/util.js");
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
  }
}
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    actBarInfo: {}
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!options.goodsId) return;
    let {
      goodsId,
      activityId = null,
      activityType = null
    } = options
    this.setData({
      goodsId,
      activityId,
      activityType
    });
    this.requestGoodsInfo()
  },
  // 商品详情请求
  async requestGoodsInfo () {
    let result = new Promise((resolve, reject) => {
      util.api(
        "/api/wxapp/goods/detail",
        res => {
          if (res.error === 0) {
            let { comment, goodsImgs, goodsVideo, goodsVideoImg, coupons, goodsDesc = null, isPageUp = 0, goodsPageId = null, deliverPlace, defaultPrd, activity, goodsNumber, goodsSaleNum, labels, goodsAd, isCollected, products, goodsName, deliverPrice, limitBuyNum,
              limitMaxNum, goodsId } = res.content
            let goodsMediaInfo = {
              goodsImgs,//商品图片
              goodsVideo,//商品视频
              goodsVideoImg//视频封面
            }
            let goodsDescInfo = {
              goodsDesc,//商品描述
              isPageUp,//描述上下位置
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
              ...specParams
            }
            this.setData({
              comment,//评价
              deliverPlace, //发货地
              defaultPrd,//是否单规格
              goodsMediaInfo,
              couponList: coupons,//优惠券
              goodsDescInfo
            })
            this.setData({
              specParams
            })
            this.setData({
              goodsInfo
            })
            // this.getGoodsInfo(res.content);
            this.getActivity(res.content)
            resolve(res.content);
          }
        }, {
        goodsId: this.data.goodsId,
        activityId: this.data.activityId,
        activityType: this.data.activityType,
        userId: util.getCache("user_id"),
        lon: null,
        lat: null
      }
      );
    });
    this.requestPledge(await result)
  },
  // 服务承诺请求
  requestPledge ({
    brandId = null,
    goodsId,
    sortId = null
  }) {
    util.api(
      "/api/wxapp/config/pledge/list",
      res => {
        if (res.error === 0) {
          this.setData({
            pledgeInfo: res.content
          });
        }
      }, {
      goodsId: goodsId,
      sortId: sortId,
      brandId: brandId
    }
    );
    this.selectComponent('#recommend').requestData() //推荐商品请求
  },
  // 获取规格信息
  getProduct ({
    detail: {
      prdNumber,
      limitBuyNum=null,
      limitMaxNum=null,
      activityType=null
    }
  }) {
    this.setData({
      limitInfo: {
        prdNumber,
        limitBuyNum,
        limitMaxNum,
        activityType
      }
    });
  },
  // 获取选中规格详情
  getProductInfo (data) {
    this.setData({
      productInfo: data.detail
    });
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
      showSpec: false,
      triggerButton: ''
    })
  },
  // 获取活动信息
  getActivity ({
    activity
  }) {
    if (!activity) return
    this.setData({
      'actBarInfo.actName': this.getActName(activity),
      'actBarInfo.actStatusName': this.getActStatusName(activity),
      'actBarInfo.prdRealPrice': this.getActBarPrice(activity, 'prdRealPrice'),
      'actBarInfo.prdLinePrice': this.getActBarPrice(activity, 'prdLinePrice')
    })
    this.getCountDown(activity)
    if (activity.activityType === 1 && activity.groupBuyListMpVos.length > 0) {
      this.setListCountDown(activity.groupBuyListMpVos, 'groupBuyListMpVos')
    }
  },
  // 获取actBar活动名称
  getActName ({
    activityType
  }) {
    if (!activityType) return null
    return actBaseInfo[activityType].actName
  },
  // 获取actBar活动状态
  getActStatusName ({
    actState,
    activityType
  }) {
    return actBaseInfo[activityType]['actStatus'][actState] || null
  },
  // 获取actBar价格
  getActBarPrice (activity, getPrice) {
    if (actBaseInfo[activity.activityType].multiSkuAct) {
      return this.getMin(activity[[actBaseInfo[activity.activityType]['prdListName']]].map(item => {
        let { [actBaseInfo[activity.activityType]['prdPriceName'][getPrice]]: price } = item;
        return price
      }))
    } else if (getPrice === 'prdRealPrice') {
      return activity[actBaseInfo[activity.activityType][getPrice]]
    } else if (getPrice === 'prdLinePrice') {
      return this.getMin(this.data.specParams.products.map(item => item.prdRealPrice))
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
    let total_micro_second = actBaseInfo[activityType]['countDownInfo'][actState] === 'startTime' ? startTime : endTime
    this.countdown(total_micro_second, actState, activityType)
  },
  // 获取最小值
  getMin (arr) {
    return Math.min(...arr);
  },
  // 倒计时
  countdown (total_micro_second, actState, activityType) {
      this.actBartime=setInterval(()=>{
        total_micro_second -= 1
        let clock = total_micro_second <= 0 ? "已经截至" : util.dateformat(total_micro_second);
        this.setData({
          'actBarInfo.clock': clock
        });
        this.getActCanBuy(total_micro_second, actState, activityType)
      },1000)
  },
  getActCanBuy (total_micro_second, actState, activityType) {
    const state = new Map([
      [{ actState: "endTime", second: true }, () => {
        this.setData({
          'dealtAct.canBuy': true
        })
      }],
      [{ actState: "endTime", second: false }, () => {
        let actState = Object.keys(actBaseInfo[activityType]['actStatus']).find((k) => { return actBaseInfo[activityType]['actStatus'][k] === '活动已结束' })
        this.setData({
          'dealtAct.canBuy': false,
          'actBarInfo.actStatusName': this.getActStatusName({ activityType, actState }),
        })
        clearTimeout(this.actBartime)
        this.getCountDown({ activityType, actState, endTime: this.specParams.activity.endTime, startTime: this.specParams.activity.endTime })
      }],
      [{ actState: "startTime", second: true }, () => {
        this.setData({
          'dealtAct.canBuy': false,
        })
      }],
      [{ actState: "startTime", second: false }, () => {
        let actState = Object.keys(actBaseInfo[activityType]['actStatus']).find((k) => { return actBaseInfo[activityType]['actStatus'][k] === '距结束仅剩' })
        this.setData({
          'dealtAct.canBuy': true,
          'actBarInfo.actStatusName': this.getActStatusName({ activityType, actState }),
        })
        clearTimeout(this.actBartime)
        this.getCountDown({ activityType, actState, endTime: this.specParams.activity.endTime, startTime: this.specParams.activity.endTime })
      }]
    ])
      ;[...state].find(([key]) => { return key.actState === actBaseInfo[activityType]['countDownInfo'][actState] && key.second === total_micro_second > 0 })[1].call(this)
  },
  // 设置列表倒计时
  setListCountDown (listData, target) {
      this.listCountDown=setInterval(() => {
        listData = listData.map((v, i) => {
          if (v.remainTime <= 0) {
            v.remainTime = 0;
          }
          v.remainTime -= 1;
          v.countDown = this.dateformats(v.remainTime);
          return v;
        })
        this.setData({
          [target]: listData
        });
      }, 1000)
  },
  dateformats: function (micro_second) {
    // 秒数
    var second = Math.floor(micro_second);
    // 小时位
    var hr = Math.floor((second) / 3600);
    // 分钟位
    var min = Math.floor((second - hr * 60 * 60) / 60);
    // 秒位
    var sec = second % 60;
    return `${String(hr).padStart(2, '0')}:${String(min).padStart(2, '0')}:${String(sec).padStart(2, '0')}`
  },
  goGroup (e) {
    util.jumpLink(`pages1/groupbuyinfo/groupbuyinfo?group_id=${e.currentTarget.dataset.groupId}`, 'navigateTo')
  },
  share(){
    this.setData({
      showShareDialog:true
    })
  },
  // 切换收藏
  toogleCollect(){
    let {goodsId,isCollected} = this.data.goodsInfo
    const apiMap = new Map([
      [true,{api:'/api/wxapp/cancel/collect',msg:'已取消',error:'取消失败'}],
      [false,{api:'/api/wxapp/add/collect',msg:'收藏成功',error:'收藏失败'}]
    ])
    util.api(apiMap.get(isCollected).api,res=>{
      if(res.error === 0){
        util.toast_success(apiMap.get(isCollected).msg)
        this.setData({
          'goodsInfo.isCollected':!isCollected
        })
      } else {
        util.toast_fail(apiMap.get(isCollected).error)
      }
    },{goodsId})
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
    util.api('/api/wxapp/groupbuy/share/info',res=>{
      console.log(res)
    },{
        "activityId":38,
        "realPrice":12,
        "linePrice":30
    })
  }
});