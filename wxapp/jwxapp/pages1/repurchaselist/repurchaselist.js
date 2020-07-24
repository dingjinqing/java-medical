const util = require("../../utils/util")

global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    goodsItem:{
      goodsId: 397,
        goodsName: "华为 HUAWEI nova 5 Pro 前置3200万人像超级夜景4800万AI四摄麒麟980芯片8GB+128GB绮",
        goodsImg: "http://jmptestimg.weipubao.cn/upload/819021/image/20200527/xPKbEWZobOdlLxJAcydo.jpeg",
        marketPrice: null,
        shopPrice: 2399,
        productId: 1797,
        prdPrice: 2399,
        prdNumber: 5,
        prdDesc: "颜色:绮境森林;版本:8GB+128GB;选择版本:标准版",
        prdImg: "",
        goodsNumber: 2,
        cartNumber: 2,
        groupId: 1
    }
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