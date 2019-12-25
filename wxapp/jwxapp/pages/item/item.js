var util = require("../../utils/util.js");
const actBaseInfo = {
  1: {
    actName: '拼团'
  },
  3: {
    actName: '砍价'
  },
  5: {
    actName: '秒杀',
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
    goodsId: null,
    goodsMediaInfo: null,
    goodsInfo: null,
    couponList: null,
    pledgeInfo: null,
    limitInfo: null,
    productInfo: null,
    canBuy: true,
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
  async requestGoodsInfo() {
    let result = new Promise((resolve, reject) => {
      util.api(
        "/api/wxapp/goods/detail",
        res => {
          if (res.error === 0) {
            let {comment,goodsImgs,goodsVideo,goodsVideoImg,coupons,goodsDesc = null,isPageUp = 0,goodsPageId = null,deliverPlace, defaultPrd,activity,goodsNumber,goodsSaleNum,labels,goodsAd,isCollected,products,goodsName,deliverPrice}= res.content
            this.setData({
              comment,//评价
              deliverPlace, //发货地
              defaultPrd,//是否单规格
              goodsMediaInfo:{
                goodsImgs,//商品图片
                goodsVideo,//商品视频
                goodsVideoImg//视频封面
              },
              couponList:coupons,//优惠券
              goodsDescInfo:{
                goodsDesc,//商品描述
                isPageUp,//描述上下位置
                goodsPageId //页面模板ID
              },
              goodsInfo:{
                activity,
                defaultPrd,
                goodsNumber,
                goodsSaleNum,
                labels,
                goodsAd,
                isCollected,
                products,
                goodsName,
                deliverPrice
              }
            })
            // this.getGoodsInfo(res.content);
            // this.getActivity(res.content)
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
  requestPledge({
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
          console.log(this.data.pledgeInfo)
        }
      }, {
        goodsId: goodsId,
        sortId: sortId,
        brandId: brandId
      }
    );
  },
  // 获取规格信息
  getProduct({
    detail: {
      prdNumber,
      limitBuyNum,
      limitMaxNum
    }
  }) {
    this.setData({
      limitInfo: {
        prdNumber,
        limitBuyNum,
        limitMaxNum
      }
    });
  },
  // 获取选中规格详情
  getProductInfo(data) {
    this.setData({
      productInfo: data.detail
    });
  },
  // 获取商品基本信息
  getGoodsInfo({
    goodsId,
    goodsName,
    goodsSaleNum,
    labels,
    goodsAd,
    defaultPrd,
    products,
    goodsNumber,
    goodsImgs,
    limitBuyNum,
    limitMaxNum,
    deliverPlace,
    isCollected
  }) {
    let info = {
      goodsId,
      goodsName,
      goodsAd,
      goodsSaleNum,
      labels,
      defaultPrd,
      products,
      goodsNumber,
      goodsImgs,
      limitBuyNum,
      limitMaxNum,
      deliverPlace,
      deliverPrice,
      isCollected
    };
    this.setData({
      goodsInfo: info
    });
  },
  // 打开规格弹窗
  showSpecDialog(trigger) {
    console.log(trigger)
    this.setData({
      showSpec: true,
      triggerButton: trigger.detail
    })
  },
  // 关闭item页规格弹窗
  bindCloseSpec() {
    this.setData({
      showSpec: false,
      triggerButton: ''
    })
  },
  // 获取活动信息
  getActivity({
    activity
  }) {
    if (!activity) return
    this.setData({
      activity,
      'actBarInfo.actName': this.getActName(activity),
      'actBarInfo.actStatusName': this.getActStatusName(activity),
      'actBarInfo.prdRealPrice': this.getMin(activity.actProducts.map(item => {
        let {
          [actBaseInfo[activity.activityType]['prdPriceName']['prdRealPrice']]: prdRealPrice
        } = item;
        return prdRealPrice
      })),
      'actBarInfo.prdLinePrice': this.getMin(activity.actProducts.map(item => {
        let {
          [actBaseInfo[activity.activityType]['prdPriceName']['prdLinePrice']]: prdLinePrice
        } = item;
        return prdLinePrice
      }))
    })
    this.getCountDown(activity)
  },
  // 获取actBar活动名称
  getActName({
    activityType
  }) {
    if (!activityType) return null
    return actBaseInfo[activityType].actName
  },
  // 获取actBar活动状态
  getActStatusName({
    actState,
    activityType
  }) {
    return actBaseInfo[activityType]['actStatus'][actState] || null
  },
  // 获取actBar活动倒计时
  getCountDown({
    activityType,
    actState,
    endTime,
    startTime
  }) {
    if (!actBaseInfo[activityType]['countDownInfo']['canCountDown'].includes(actState)) return
    let total_micro_second = Math.round((actBaseInfo[activityType]['countDownInfo'][actState] === 'startTime' ? startTime : endTime) / 1000)
    console.log(total_micro_second, actState, activityType)
    this.countdown(total_micro_second, actState, activityType)
  },
  // 获取最小值
  getMin(arr) {
    return Math.min(...arr);
  },
  // 倒计时
  countdown(total_micro_second, actState, activityType) {
    let clock =
      total_micro_second <= 0 ?
      "已经截至" :
      util.dateformat(total_micro_second);
    this.setData({
      'actBarInfo.clock': clock
    });
    this.getActCanBuy(total_micro_second,actState,activityType)
    if (total_micro_second <= 0) return;
    this.setData({
      actBartime: setTimeout(() => {
        total_micro_second -= 1;
        this.countdown(total_micro_second, actState, activityType);
      }, 1000)
    });
  },
  getActCanBuy(total_micro_second,actState,activityType){
    const state = new Map([
      [{actState:"endTime",second:true},()=>{
        this.setData({
          'dealtAct.canBuy': true
        })
      }],
      [{actState:"endTime",second:false},()=>{
        let actState = Object.keys(actBaseInfo[activityType]['actStatus']).find((k)=>{return actBaseInfo[activityType]['actStatus'][k] === '活动已结束'})
        this.setData({
          'dealtAct.canBuy': false,
          'activity.actState': actState,
          'actBarInfo.actStatusName': this.getActStatusName({...this.data.activity,actState}),
        })
        this.getCountDown(this.data.activity)
      }],
      [{actState:"startTime",second:true},()=>{
        this.setData({
          'dealtAct.canBuy': false,
        })
      }],
      [{actState:"startTime",second:false},()=>{
        let actState = Object.keys(actBaseInfo[activityType]['actStatus']).find((k)=>{return actBaseInfo[activityType]['actStatus'][k] === '距结束仅剩'})
        this.setData({
          'dealtAct.canBuy': true,
          'activity.actState': actState,
          'actBarInfo.actStatusName': this.getActStatusName({...this.data.activity,actState}),
        })
        this.getCountDown(this.data.activity)
      }]
    ])
    ;[...state].find(([key])=>{return key.actState === actBaseInfo[activityType]['countDownInfo'][actState] && key.second === total_micro_second > 0})[1].call(this)
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {},

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {},

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {},

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    clearTimeout(this.data.actBartime)
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {},

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {},

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    return {
      title:'已删除title'
    }
  }
});