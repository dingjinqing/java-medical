const util = require("../../utils/util");

let plugin = requirePlugin('routePlan');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    storeId:null,
    pageParams: {
      currentPage: 1,
      pageRows: 20
    },
    shippingStatus:8,
    dataList:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let {storeId} = options
    this.data.storeId = storeId
    this.requestOrderList()
  },
  requestOrderList(){
    util.api('/api/wxapp/store/order/list',res=>{
      console.log(res)
      if (res.error === 0) {
        if (this.data.pageParams.currentPage === 1) {
          this.setData({
            dataList: [
              [...res.content.orders.dataList]
            ]
          })
        } else {
          this.setData({
            ['dataList[' + (parseInt(this.data.pageParams.currentPage) - 1) + ']']: res.content.orders.dataList
          })
        }
        this.setData({
          pageParams: res.content.orders.page
        })
      }
    },{
      storeId:Number(this.data.storeId),
      type:this.data.shippingStatus,
      ...this.data.pageParams
    })
  },
  // 切换筛选条件
  toggleFilter({currentTarget:{dataset:{shippingStatus}}}){
    this.setData({
      shippingStatus:Number(shippingStatus)
    })
    this.requestOrderList()
  },
  viewMap({currentTarget:{dataset:{lat,lng,completeAddress}}}){
    let key = 'C3JBZ-4UXEJ-PCLFP-KNWNV-UDVUT-IJFES';  //使用在腾讯位置服务申请的key
    let referer = '旺店精选';   //调用插件的app的名称
    let endPoint = JSON.stringify({  //终点
        'name': completeAddress,
        'latitude': lat,
        'longitude': lng
    });
    wx.navigateTo({
        url: 'plugin://routePlan/index?key=' + key + '&referer=' + referer + '&endPoint=' + endPoint
    });
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