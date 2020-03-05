const util = require("../../utils/util.js");
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: util.getImageUrl("")
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      options
    })
    this.requestInfo()
  },
  requestInfo(){
    let orderSn = this.data.options.orderSn
    util.api('api/wxapp/order/pay/instead/detail',res=>{
      if(res.error === 0 && res.content){
        this.setData({
          pageInfo:res.content,
          manageMsg:this.setMessage(res.content),
          progress:this.getProgress(res.content)
        })
      }
    },{
      orderSn
    })
  },
  setMessage(pageInfo){
    let manageMsg = null
    if(pageInfo.isSelf) manageMsg = pageInfo.message
    if(this.data.options && this.data.options.message) manageMsg = this.data.options.message
    return manageMsg
  },
  getProgress({amountPaid,waitPayMoney}){
    let num = parseFloat(amountPaid),total = parseFloat(amountPaid + waitPayMoney)
    return (Math.round(num / total * 10000) / 100.00);
  },
  editMsg(e){
    this.setData({
      manageMsg:e.detail.value
    })
  },
  viewItem(){
    util.jumpLink(`pages/item/item?gid=${this.data.pageInfo.order.goods[0].goodsId}`,'navitageTo')
  },
  viewOrder(){
    util.jumpLink(`pages/orderinfo/orderinfo?orderSn=${this.data.pageInfo.order.orderSn}`,'navitageTo')
  },
  viewCheckout(){
    util.jumpLink(`pages1/insteadcheckout/insteadcheckout?orderSn=${this.data.pageInfo.order.orderSn}&orderId=${this.data.pageInfo.order.orderId}`,'navitageTo')
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
    console.log(`pages1/insteadinfo/insteadinfo?orderSn=${this.data.options.orderSn}&message=${this.data.manageMsg}&inviteId=${util.getCache('user_id')}`)
    return {
      title: "相中了一件商品，帮人家付一下呗！",
      path: `pages1/insteadinfo/insteadinfo?orderSn=${this.data.options.orderSn}&message=${this.data.manageMsg}&inviteId=${util.getCache('user_id')}`,
      imageUrl: this.data.imageUrl + this.data.pageInfo.order.goods[0].goodsImg
    }
  }
})