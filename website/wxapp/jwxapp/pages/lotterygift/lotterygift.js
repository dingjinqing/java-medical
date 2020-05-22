var util = require("../../utils/util.js");
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    status:0,
    pageParams: null,
    giftType:{
      0:'幸运大抽奖',
      1:'好友助力',
      2:'测评',
      3:'支付有礼'
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.requestGiftList()
  },
  // 请求奖品接口数据
  requestGiftList() {
    let currentPage = this.data.pageParams
      ? this.data.pageParams.currentPage
      : 1;
    if(currentPage === 1){
      this.setData({
        dataList:[]
      })
    }
    this.setData({
      loaded:false
    })
    util.api('/api/wxapp/prize/list', res => {
      if(res.error === 0 && res.content){
        this.setData({
          ['dataList[' + (parseInt(currentPage) - 1) + ']']: res.content.dataList,
          pageParams: res.content.page,
          loaded:true
        })
      }
    }, {
      currentPage,
      pageRows:20,
      status:this.data.status
    }, '', true)
  },
  toggleStatus(e){
    let {status} = e.currentTarget.dataset
    this.setData({
      status:Number(status),
      'pageParams.currentPage':1
    })
    this.requestGiftList()
  },
  viewOrder(e){
    let {orderSn} = e.currentTarget.dataset
    util.jumpLink(`pages/orderinfo/orderinfo?orderSn=${orderSn}`,'navigateTo')
  },
  goCheckOut(e){
    let {activityId,goodsInfo} = e.currentTarget.dataset
    goodsInfo.prdRealPrice = 0.00
    goodsInfo.goodsNum = 1
    goodsInfo.prdId = goodsInfo.productId
    let goodsList = [goodsInfo]
    console.log(goodsList)
    util.jumpLink(`pages/checkout/checkout${util.getUrlParams({
      activityType:24,
      goodsList:JSON.stringify(goodsList),
      activityId
    })}`)
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
    if (
      this.data.pageParams &&
      this.data.pageParams.currentPage === this.data.pageParams.lastPage
    )
      return;
    this.setData({
      'pageParams.currentPage': this.data.pageParams.currentPage + 1
    });
    this.requestGiftList();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})