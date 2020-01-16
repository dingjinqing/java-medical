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
      ruleId:62,
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
      ruleId:62,
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