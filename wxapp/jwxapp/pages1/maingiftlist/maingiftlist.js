var util = require('../../utils/util.js')
var scene;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    pageParams: null,
    searchText: null,
    isGiftViewMore:false,
    isFirstLoad:true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let {actId:giftId} = options
    this.setData({
      giftId
    })
    this.requestGoodsList()
    this.requestCartGoodsList()
  },
  

  requestGoodsList(){
    let currentPage = this.data.pageParams
      ? this.data.pageParams.currentPage
      : 1;
    util.api('/api/wxapp/gift/goodslist',res=>{
      if(res.error === 0 && res.content !== null){
        this.setData({
          ['dataList[' + (parseInt(currentPage) - 1) + ']']: this.resetGoodsList(res.content.goods.dataList),
          giftList:this.data.isFirstLoad ? res.content.giftProductList : this.data.giftList,
          checkedGoodsPrice:res.content.checkedGoodsPrice,
          pageParams: res.content.goods.page,
          ruleStr:this.data.isFirstLoad ? this.getActRule(res.content.rule,res.content.giftProductList) : this.data.ruleStr,
          changeDoc: this.getGiftDoc(res.content.rule,res.content.checkedGoodsPrice),
          delMarket: res.content.delMarket,
          showCart: this.data.isFirstLoad ? {
            cart_type:res.content.showCart.cartType,
            show_cart:1
          } : this.data.showCart,
          isFirstLoad:false
        });
      }
    },{
      search:this.data.searchText,
      giftId:this.data.giftId,
      currentPage: currentPage,
      pageRows: 20,
    })
  },
  requestCartGoodsList(){
    util.api('/api/wxapp/gift/checkedlist',res=>{
      if(res.error === 0){
        this.setData({
          cartData:{
            cartGoodsList:res.content
          }
        })
      }
    },{
      giftId:this.data.giftId
    })
  },
  getSearchText(data){
    this.setData({
      searchText:data.detail,
      'pageParams.currentPage':1,
      dataList:null
    })
    this.requestGoodsList()
  },
  showSelected(){
    this.setData({
      showSelectedDialog:true
    })
  },
  showSpecDialog(e){
    console.log(e)
    util.api("/api/wxapp/goods/detail",res=>{
      if(res.error === 0){
        let productsInfo = {
          activity:res.content.activity,
          defaultPrd:res.content.defaultPrd,
          goodsId:res.content.goodsId,
          goodsImgs:res.content.goodsImgs,
          goodsNumber:res.content.goodsNumber,
          limitBuyNum:res.content.limitBuyNum,
          limitMaxNum:res.content.limitMaxNum,
          products:res.content.products
        }
        this.setData({
          productsInfo,
          showSpec:true
        })
      }
    },{
      goodsId: e.detail.goodsId,
      activityId: e.detail.activityId,
      activityType: e.detail.activityType,
      userId: util.getCache("user_id"),
      lon: null,
      lat: null
    })
  },
  bindCloseSpec(){
    this.setData({
      showSpec:false
    })
  },
  getProductData(e){
    this.setData({
      product:e.detail,
      limitInfo:{
        activityType:this.data.productsInfo.activityType,
        limitBuyNum:e.detail.limitBuyNum,
        limitMaxNum:e.detail.limitMaxNum,
        prdNumber:e.detail.prdNumber
      }
    })
  },
  getGoodsNum(e) {
    this.setData({
      productInfo: { ...this.data.product, goodsNum:e.detail.goodsNum }
    });
  },
  setGiftViewMore(){
    this.setData({
      isGiftViewMore:!this.data.isGiftViewMore
    })
  },
  addCart(){
    let { goodsNum: goodsNumber, prdId } = this.data.productInfo
      util.api(
        "/api/wxapp/cart/add",
        res => {
          if (res.error == 0) {
            util.toast_success('添加成功')
            this.requestCartGoodsList()
            this.requestGoodsList()
          } else {
            util.toast_fail('添加失败')
          }
          this.bindCloseSpec()
        },
        {
          goodsNumber: goodsNumber,
          prdId: prdId
        }
      );
  },
  getActRule(rule,giftList){
    if (!rule) return false;
    let ruleStr = ''
    if (rule.fullPrice) {
      ruleStr = `以下商品：满${rule.fullPrice}元，赠`
    } else if (rule.fullNumber) {
      ruleStr = `以下商品：满${rule.fullNumber}件，赠`
    }
    return ruleStr += `${giftList.length}件商品`;
  },
  getGiftDoc(rule,mainPrice,mainNum){
    if (!rule) return "快来选择商品获得赠品吧";
    if (rule.fullPrice) {
        if (rule.fullPrice > mainPrice){
          return `购买满${rule.fullPrice}元立赠`
        } else {
          return "下单立赠"
        }
    }else if (rule.fullNumber) {
        if (rule.fullNumber > mainNum) {
          return `购买满${rule.fullNumber}件立赠`
        } else {
          return "下单立赠"
        } 
    }
    return "快来选择商品获得赠品吧"
  },
  resetGoodsList(goodsList){
    return goodsList.map(item=>{
      return {
        ...item,
        realPrice:item.goodsPrice,
        linePrice:item.marketPrice
      }
    })
  },
  cartChange(){
    this.requestCartGoodsList()
    this.requestGoodsList()
  },
  goCart(){	
    util.jumpLink('pages/cart/cart','navigateTo')	
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
    this.requestGoodsList();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})