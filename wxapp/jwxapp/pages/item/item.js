var util = require("../../utils/util.js");
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    goodsId: null,
    goodsMediaInfo: null,
    goodsInfo: null,
    couponList: null,
    product: null,
    pledgeInfo: null,
    limitInfo: null,
    productInfo: null
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    if (!options.goodsId) return;
    let {goodsId,activityId=null,activityType=null} = options
    this.setData({
      goodsId,
      activityId,
      activityType
    });
    this.requestGoodsInfo().then(res => {
      this.requestPledge(res);
    });
  },
  // 获取活动倒计时
  getActStatus(e) {
    let {canBuy} = e.detail;
    console.log(canBuy)
  },
  // 商品详情请求
  requestGoodsInfo() {
    return new Promise((resolve, reject) => {
      util.api(
        "/api/wxapp/goods/detail",
        res => {
          if (res.error === 0) {
            this.getMediaInfo(res.content);
            this.getGoodsInfo(res.content);
            this.getCouponInfo(res.content);
            this.getGoodsDescInfo(res.content);
            this.getComment(res.content)
            this.getActivity(res.content)
            resolve(res.content);
          }
        },
        {
          goodsId: this.data.goodsId,
          activityId:this.data.activityId,
          activityType:this.data.activityType,
          userId: util.getCache("user_id"),
          lon:null,
          lat:null
        }
      );
    });
  },
  // 商品详情-自定以内容
  getGoodsDescInfo(goodsInfo){
    let { goodsDesc = null, isPageUp = 0, goodsPageId = null } = goodsInfo
    console.log(goodsDesc, isPageUp)
    this.setData({
      goodsDescInfo:{
        goodsDesc,
        isPageUp,
        goodsPageId
      }
    })
  },
  // 商品评价信息
  getComment(goodsInfo){
    let { comment } = goodsInfo
    this.setData({
      comment
    })
  },
  // 服务承诺请求
  requestPledge(goodsInfo) {
    let { brandId = null, goodsId, sortId = null } = goodsInfo;
    util.api(
      "/api/wxapp/config/pledge/list",
      res => {
        if (res.error === 0) {
          this.setData({
            pledgeInfo: res.content
          });
        }
      },
      {
        goodsId: goodsId,
        sortId: sortId,
        brandId: brandId
      }
    );
  },
  // 获取规格信息
  getProduct(data) {
    let { prdNumber, limitBuyNum, limitMaxNum } = data.detail;
    this.setData({
      product: data.detail,
      limitInfo: { prdNumber, limitBuyNum, limitMaxNum }
    });
  },
  getProductInfo(data) {
    this.setData({
      productInfo: data.detail
    });
    console.log(this.data.productInfo);
  },
  // 获取商品轮播图/视频
  getMediaInfo(goodsInfo) {
    let { goodsImgs, goodsVideo, goodsVideoImg } = goodsInfo;
    this.setData({
      goodsMediaInfo: { goodsImgs, goodsVideo, goodsVideoImg }
    });
  },
  // 获取商品基本信息
  getGoodsInfo(goodsInfo) {
    let {
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
      deliverPrice
    } = goodsInfo;
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
      deliverPrice
    };
    this.setData({
      goodsInfo: info
    });
  },
  // 获取商品优惠券信息
  getCouponInfo(goodsInfo) {
    let { coupons } = goodsInfo;
    this.setData({
      couponList: coupons
    });
  },
  showSpecDialog(trigger){
    console.log(trigger)
    this.setData({
      showSpec:true,
      triggerButton: trigger.detail
    })
  },
  bindCloseSpec(){
    this.setData({
      showSpec: false,
      triggerButton:''
    })
  },
  getActivity({activity}){
    if(!activity) return
    this.setData({
      activity
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {},

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {},

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {},

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {},

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {},

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {},

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {
    console.log(1)
  }
});
