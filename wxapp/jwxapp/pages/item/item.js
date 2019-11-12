// pages/item/item.js
var util = require("../../utils/util.js")
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    goodsId:null,
    goodsMediaInfo:null,
    goodsInfo:null,
    couponList:null
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    options.goods_id = 66;
    if (!options.goods_id) return;
    this.setData({
      goodsId : options.goods_id
    })
    this.requestGoodsInfo()
  },
  backHome() {
    util.jumpLink('pages/index/index', 'redirectTo')
  },
  getActStatus(e){
    let second = e.detail
    console.log(second)
  },
  // 添加购物车
  addCart(){
    util.api('/api/admin/cart/add',res=>{
      console.log(res)
    },{
        goodsNumber: 1,
        prdId: 5126
    })
  },
  test(){
    util.jumpLink('pages/item/item', 'navigateTo')
  },
  requestGoodsInfo(){
    util.api('/api/wxapp/goods/detail',res=>{
      if(res.error === 0){
        this.getMediaInfo(res.content)
        this.getGoodsInfo(res.content)
        this.getCouponInfo(res.content)
      }
    },{
        goodsId: this.data.goodsId,
        userId: util.getCache('user_id')
    })
  },
  getMediaInfo(goodsInfo){
    let { goodsImgs, goodsVideo, goodsVideoImg } = goodsInfo
    this.setData({
      goodsMediaInfo: { goodsImgs, goodsVideo, goodsVideoImg }
    })
  },
  getGoodsInfo(goodsInfo){
    let { goodsName, goodsSaleNum, labels, defaultPrd, products, goodsNumber } = goodsInfo
    let info = { goodsName, goodsSaleNum, labels, defaultPrd, products, goodsNumber }
    this.setData({
      goodsInfo: info
    })
  },
  getCouponInfo(goodsInfo){
    let { coupons } = goodsInfo;
    this.setData({
      couponList:coupons
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