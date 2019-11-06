var util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    address:null,
    balanceStatus:0,
    scoreStatus:0,
    showBalanceDialog:false,
    showScoreDialog:false,
    showCardDialog:false,
    showStoreDialog:false,
    couponArray:[
      1,2,3,4,5
    ],
    payType:[0,1,2],
    shippingMethod:[0,1,2],
    chooseShippingIndex:0,
    choosePayTypeIndex:0,
    moneyInfo:{
      totalPrice: 100, //总金额
      useBalance: 0, //使用余额
      useScore: 20, //使用积分
      useConpon: 16.5, //使用优惠券优惠
      useCardBalance: 15, //使用会员卡余额优惠
      useCardReduce: 0, //会员卡优惠金额
      useTotalprice: 0, //应付金额,
      useShipping: 0 //运费
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getPayMoney()
    wx.hideShareMenu()
  },
  // 选择地址
  addAddress(){
    wx.chooseAddress({
      success: (res) => {
        let address = res
        this.setData({
          address: address
        })
      },
      fail: function () {
        wx.getSetting({
          success: function (res) {
            if (!res.authSetting['scope.address']) {
              util.showModal('是否打开设置页面', '需要获取您的位置信息，请到小程序的设置页面打开授权', function () {
                wx.openSetting({
                  success: function (res) {
                  }
                })
              })
            }
          }
        })
      }
    })
  },
  // 余额支付事件
  balanceTap(){
    if (this.data.balanceStatus){
      this.setData({
        balanceStatus: 0,
        showBalanceDialog:false
      })
    } else {
      this.setData({
        showBalanceDialog:true
      })
    }
  },
  // 积分支付事件
  scoreTap(){
    if (this.data.scoreStatus){
      this.setData({
        scoreStatus: 0,
        showScoreDialog:false
      })
    } else {
      this.setData({
        showScoreDialog:true
      })
    }
  },
  // 变更优惠券
  couponChange(){

  },
  // 选择会员卡事件
  selectCardTap(){
    this.setData({
      showCardDialog:true
    })
  },
  // 变更支付类型
  changePayType(e){
    this.setData({
      choosePayTypeIndex:e.currentTarget.dataset.index
    })
  },
  // 变更配送方式
  selectShippingMethod(e){
    this.setData({
      chooseShippingIndex:e.currentTarget.dataset.index
    })
  },
  // 选择门店
  selectStore(){
    if(this.data.chooseShippingIndex === 0) return;
    let storeDialogData = {};
    storeDialogData.openType = this.data.chooseShippingIndex;
    storeDialogData.data = [
      { id: 1, storeName: '伊泰大厦', address: '潘家园', distance:'5.46'},
      { id: 2, storeName: '回龙观大厦', address: '西直门', distance:'0.15'},
      { id: 3, storeName: '西二旗大厦', address: '西二旗', distance:'15'},
      { id: 4, storeName: '霍营大厦', address: '蜂鸟社区', distance:'123'},
    ];
    this.setData({
      showStoreDialog: true,
      storeDialogData: storeDialogData
    })
  },
  // 获取应付总金额
  getPayMoney(){
    let money = this.data.moneyInfo
    this.setData({
      'moneyInfo.useTotalprice': money.totalPrice - money.useBalance - money.useScore - money.useConpon - money.useCardBalance - money.useCardReduce - money.useTotalprice - money.useShipping
    })
  },
  // 获取门店改变
  getSelectStore(info){
    let {id:storeId,openType} = info.detail;
    console.log(storeId, openType)
    switch (openType) {
      case 2:

      break;
      default:

      break;
    }
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

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})