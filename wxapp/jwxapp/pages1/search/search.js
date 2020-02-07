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
    sortItem: 0,
    sortDirection: 1,
    couponSn: null,
    filterData: {
      minPrice: null,
      maxPrice: null,
      sortId: null,
      brandIds: [],
      activityTypes: [],
      labelIds: []
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
    util.api(
      '/api/wxapp/goods/search',
      res => {
        console.log(res)
        if (res.error === 0) {
          if(res.content.pageResult.dataList.length < 20){
            this.selectComponent('#recommend').resetDataList().resetPage().requestData()
          }
          this.setData({
            pageParams: res.content.pageResult.page,
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
      }
    );
  },
  // 获取右侧筛选信息
  getSelectedData (data) {
    let { selectedSort: sortId, selectedBrands: brandIds, selectedLabels: labelIds, selectedActTypes: activityTypes, minPrice, maxPrice } = data.detail
    console.log(data)
    this.setData({
      filterData: {
        minPrice,
        maxPrice,
        sortId,
        brandIds,
        labelIds,
        activityTypes
      },
      'pageParams.currentPage': 1,
      dataList: []
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
      'pageParams.currentPage': 1,
      dataList: []
    })
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
