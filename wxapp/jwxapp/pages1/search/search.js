const util = require("../../utils/util.js");
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: util.getImageUrl(""),
    dataList: [],
    pageParams: null,
    showFilterDialog: false,
    keyWords: null,
    couponSn: null,
    sortItem:0,
    sortDirection:0,
    filterData: {
      minPrice: null,
      maxPrice: null,
      sortIds: [],
      brandIds: [],
      activityTypes: [],
      labelIds: [],
      pageFrom:null,
      actId:null,
      goodsIds:[]
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.loadFilter(options).then(() => {
      this.requestList()
    })
  },
  showFilter () {
    this.setData({
      showFilterDialog: true
    })
  },
  requestList () {
    let currentPage = this.data.pageParams
      ? this.data.pageParams.currentPage
      : 1;
    this.setData({
      loaded:false
    })
    util.api(
      '/api/wxapp/goods/search',
      res => {
        console.log(res)
        if (res.error === 0) {
          if(res.content.pageResult.dataList.length < 20){
            this.selectComponent('#recommend').resetDataList().resetPage().requestData()
          }
          if(currentPage === 1){
            this.setData({
              dataList:[]
            })
          }
          this.setData({
            pageParams: res.content.pageResult.page,
            loaded:true,
            delMarket:res.content.delMarket,
            showCart:res.content.showCart,
            ['dataList[' + (parseInt(currentPage) - 1) + ']']: res.content.pageResult.dataList
          });
        }
      },
      {
        currentPage: currentPage,
        pageRows: 20,
        keyWords: this.data.keyWords,
        sortItem: this.data.sortItem,
        sortDirection: this.data.sortDirection,
        couponSn: this.data.couponSn,
        ...this.data.filterData
      },'',true
    );
  },
  // 获取右侧筛选信息
  getSelectedData (data) {
    let { selectedSort: sortIds, selectedBrands: brandIds, selectedLabels: labelIds, selectedActTypes: activityTypes, minPrice, maxPrice } = data.detail
    console.log(data)
    this.setData({
      filterData: {
        minPrice,
        maxPrice,
        sortIds,
        brandIds,
        labelIds,
        activityTypes,
        pageFrom:null,
        actId:null,
        goodsIds:[]
      },
      'pageParams.currentPage': 1
    })
    this.requestList()
  },
  changeInput (e) {
    this.setData({
      keyWords: e.detail.value
    })
  },
  inputSearch () {
    this.setData({
      'pageParams.currentPage': 1
    })
    // 添加热词
    util.api('/api/wxapp/search/addHotWords', function (res) {
    }, { userId: util.getCache("user_id"), hotWords: this.data.keyWords })
    this.requestList()
  },
  loadFilter (options) {
    return new Promise((resolve, reject) => {
      let target = {
        filterData: {},
        data: {}
      }
      Object.keys(options).forEach(item => {
        if (Object.keys(this.data.filterData).includes(item)) {
          try {
            target['filterData'][item] = JSON.parse(options[item])
          } catch (error) {
            target['filterData'][item] = options[item]
          }
        } else {
          try {
            target['data'][item] = JSON.parse(options[item])
          } catch (error) {
            target['data'][item] = options[item]
          }
        }
      })
      this.setData({
        filterData: {
          ...this.data.filterData,
          ...target.filterData
        },
        ...target.data
      })
      resolve()
    })
  },
  salesVolumeFilter(){
    let sortDirection = this.data.sortDirection,sortItem = this.data.sortItem
    this.setData({
      sortItem:1,
      sortDirection : sortItem === 2 || sortItem === 0 ? 0 : !sortDirection ? 1 : 0,
      'pageParams.currentPage': 1
    })
    this.requestList()
  },
  priceFilter(){
    let sortDirection = this.data.sortDirection,sortItem = this.data.sortItem
    this.setData({
      sortItem:2,
      sortDirection : sortItem === 1 || sortItem === 0 ? 1 : !sortDirection ? 1 : 0,
      'pageParams.currentPage': 1
    })
    this.requestList()
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
        activityType:this.data.productsInfo.activity ? this.data.productsInfo.activity.activityType : null,
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
  resetScene(scene){
    return decodeURIComponent(scene).split('&').reduce((defaultData,item)=>{
      let params = item.split('=')
      defaultData[params[0]] = params[1]
      return defaultData
    },{})
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () { },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () { },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () { },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () { },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () { },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    if (
      this.data.pageParams &&
      this.data.pageParams.currentPage === this.data.pageParams.lastPage
    ) {
      this.selectComponent('#recommend').requestData()
      return;
    }
    this.setData({
      'pageParams.currentPage': this.data.pageParams.currentPage + 1
    });
    this.requestList();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () { }
});
