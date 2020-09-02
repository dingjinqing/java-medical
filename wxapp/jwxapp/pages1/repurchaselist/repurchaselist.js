const util = require("../../utils/util")

global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let {prescriptionCode} = options
    this.setData({
      prescriptionCode
    })
    this.requestList()
  },
  requestList(){
    util.api('/api/wxapp/prescription/goods/list',res=>{
      if(res.error === 0){
        this.setData({
          goodsList:this.resetCartList(res.content)
        })
      }
    },{
      prescriptionCode:this.data.prescriptionCode
    })
  },
  resetCartList(goodsList){
    return goodsList.map(item => {
      item.prdNumber = item.goodsNumber;
      item.cartNumber = 1;
      item.goodsImg = this.data.imageUrl + item.goodsImg;
      item.prdPrice = item.shopPrice;
      item.selected = true
      return item
    })
  },
  cartNumChange({detail:goodsInfo}){
    let targetIndex = this.data.goodsList.findIndex(item=> item.goodsId === goodsInfo.goodsId)
    this.setData({
      [`goodsList[${targetIndex}].cartNumber`]: goodsInfo.type === 'plus' ? goodsInfo.cartNumber + 1 : goodsInfo.cartNumber - 1
    })
  },
  customCartNum({detail:goodsInfo}){
    console.log(goodsInfo)
    let {goodsNumber,prdNumber} = goodsInfo
    let targetIndex = this.data.goodsList.findIndex(item=> item.goodsId === goodsInfo.goodsId)
    if(goodsNumber > prdNumber) goodsNumber = prdNumber
    if(goodsNumber < 1)  goodsNumber = 1
    this.setData({
      [`goodsList[${targetIndex}].cartNumber`]:goodsNumber
    })
  },
  orderSettlement(){
    let goodsList = this.data.goodsList.filter(item=>item.selected).map(item=>{
      let {goodsId,shopPrice:prdRealPrice,cartNumber:goodsNum,prdId} = item
      return {goodsId,prdRealPrice,goodsNum,prdId}
    })
    if(!goodsList.length) {
      util.showModal('提示','请确认已选商品') 
      return false
    }
    util.jumpLink(`pages/checkout/checkout${util.getUrlParams({
      goodsList:JSON.stringify(goodsList),
      isPrescription:1,
      prescriptionCode:this.data.prescriptionCode
    })}`)
  },
  toggleSelect(e){
    let {goodsId} = e.detail
    let targetIndex = this.data.goodsList.findIndex(item=>item.goodsId === goodsId)
    this.setData({
      [`goodsList[${targetIndex}].selected`]:!this.data.goodsList[targetIndex].selected
    })
  },
  addCart(){
    let wxAppAddGoodsToCartParams = this.data.goodsList.filter(item=>item.selected).map(item=>{
        let {cartNumber:goodsNumber,prdId} = item
        return {userId:util.getCache('user_id'),type:2,goodsNumber,activityType:0,activityId:0,prdId}
    })
    if(!wxAppAddGoodsToCartParams.length) {
      util.showModal('提示','请确认已选商品') 
      return false
    }
    util.api('/api/wxapp/prescription/cart/batch/add',res=>{
      if(res.error === 0){
        util.showModal('提示','已加入清单',()=>{
          util.jumpLink('pages/cart/cart')
        },true,'取消','查看清单')
      }
    },{
      wxAppAddGoodsToCartParams
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

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})