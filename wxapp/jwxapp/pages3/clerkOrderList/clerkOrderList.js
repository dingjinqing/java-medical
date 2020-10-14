const util = require("../../utils/util");
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
    let {storeId,orderStatus:shippingStatus} = options
    this.data.storeId = storeId
    this.setData({
      shippingStatus:Number(shippingStatus)
    })
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
  // viewMap({currentTarget:{dataset:{lat,lng,completeAddress}}}){
  //   let key = 'C3JBZ-4UXEJ-PCLFP-KNWNV-UDVUT-IJFES';  //使用在腾讯位置服务申请的key
  //   let referer = '旺店精选';   //调用插件的app的名称
  //   let endPoint = JSON.stringify({  //终点
  //       'name': completeAddress,
  //       'latitude': lat,
  //       'longitude': lng
  //   });
  //   wx.navigateTo({
  //       url: 'plugin://routePlan/index?key=' + key + '&referer=' + referer + '&endPoint=' + endPoint
  //   });
  // },
  deliver({currentTarget:{dataset:{orderId,orderSn,parentIndex}}}){
    let orderList = this.data.dataList[parentIndex]
    let target = orderList.findIndex(item=>item.orderId === orderId)
    let shipGoods = orderList[target].goods.map(item=>{return {recId:item.recId,sendNumber:item.goodsNumber}})
    util.showModal('提示','确认发货？',()=>{
      util.api('/api/wxapp/store/order/ship',res=>{
        console.log(res)
        if(res.error === 0){
          this.setData({
            [`dataList[${parentIndex}][${target}].orderStatus`]:4
          })
        } else {
          util.showModal('提示',res.message)
        }
      },{
        action:0,
        orderId:orderId,
        orderSn:orderSn,
        shipGoods,
        shippingId:42,
        shippingNo:1
      })
    },true,'取消','确认发货')
  },
  confirm({currentTarget:{dataset:{orderId,orderSn,parentIndex}}}){
    let orderList = this.data.dataList[parentIndex]
    let target = orderList.findIndex(item=>item.orderId === orderId)
    util.showModal('提示','确认送达？',()=>{
      util.api('/api/wxapp/store/order/receive',res=>{
        console.log(res)
        if(res.error === 0){
          this.setData({
            [`dataList[${parentIndex}][${target}].orderStatus`]:5
          })
        } else {
          util.showModal('提示',res.message)
        }
      },{
        action:6,
        orderId,
        orderSn
      })
    },true,'取消','确认送达')
  },
  return({currentTarget:{dataset:{orderId,orderSn,parentIndex}}}){
    let orderList = this.data.dataList[parentIndex]
    let target = orderList.findIndex(item=>item.orderId === orderId)
    util.showModal('提示','确认退款？',()=>{
      util.api('/api/wxapp/store/order/refund',res=>{
        console.log(res)
        if(res.error === 0){
          this.setData({
            [`dataList[${parentIndex}][${target}].orderStatus`]:10
          })
        } else {
          util.showModal('提示',res.message)
        }
      },{
        action:1,
        returnType:1,
        orderSn,
        orderId
      })
    },true,'取消','确认退款')
  },
  call({currentTarget:{dataset:{phone:phoneNumber}}}){
    wx.makePhoneCall({
      phoneNumber
    })
  },
  viewMap ({currentTarget:{dataset:{lat,lng,completeAddress}}}) {
    wx.openLocation({
      latitude: Number(lat),
      longitude: Number(lng),
      scale: 28,
      name: completeAddress
    })
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
    ) {
      return;
    }
    this.setData({
      'pageParams.currentPage': this.data.pageParams.currentPage + 1
    });
    this.requestOrderList()
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})