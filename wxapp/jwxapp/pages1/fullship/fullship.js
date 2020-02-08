var util = require('../../utils/util.js')
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    pageParams: null,
    searchText: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let {ruleId} = options
    this.setData({
      ruleId
    })
    this.requestGoodsList()
    this.requestCartGoodsList()
  },
  

  requestGoodsList(){
    let currentPage = this.data.pageParams
      ? this.data.pageParams.currentPage
      : 1;
    util.api('/api/wxapp/freeship/goods/list',res=>{
      if(res.error === 0 && res.content !== null){
        this.setData({
          pageParams: res.content.pageResult.page,
          ['dataList[' + (parseInt(currentPage) - 1) + ']']: res.content.pageResult.dataList,
          delMarket:res.content.delMarket,
          showCart:{
            ...res.content.showCart,
            show_cart:1
          }
        });
      }
    },{
      searchText:this.data.searchText,
      ruleId:this.data.ruleId,
      currentPage: currentPage,
      pageRows: 20,
    })
  },
  requestCartGoodsList(){
    util.api('/api/wxapp/freeship/cart/goods/list',res=>{
      if(res.error === 0){
        this.setData({
          cartData:res.content
        })
      }
    },{
      ruleId:this.data.ruleId,
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
    console.log(this.data.productInfo)
  },
  addCart(){
    let { goodsNum: goodsNumber, prdId } = this.data.productInfo
      util.api(
        "/api/wxapp/cart/add",
        res => {
          if (res.error == 0) {
            util.toast_success('添加成功')
            this.requestCartGoodsList()
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
  cartChange(){
    console.log(123)
    this.requestCartGoodsList()
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