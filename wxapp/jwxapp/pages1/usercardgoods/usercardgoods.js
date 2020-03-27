var util = require('../../utils/util.js')
var scene;
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
    console.log(options)
    this.setData({
      page_name: '兑换商品'
    })
    let { ruleId } = options
    this.setData({
      ruleId
    })
    this.requestGoodsList() // 请求兑换商品列表的数据
    // this.requestCartGoodsList() // 请求已选择的兑换商品数据
  },


  requestGoodsList () {
    let currentPage = this.data.pageParams
      ? this.data.pageParams.currentPage
      : 1;
    util.api('/api/wxapp/freeship/goods/list', res => {
      if (res.error === 0 && res.content !== null) {
        this.setData({
          pageParams: res.content.pageResult.page,
          ['dataList[' + (parseInt(currentPage) - 1) + ']']: res.content.pageResult.dataList,
          delMarket: res.content.delMarket,
          showCart: {
            ...res.content.showCart,
            show_cart: 1
          },
          ruleInfo: res.content.freeShippingRule
        });
      }
    }, {
      searchText: this.data.searchText,
      ruleId: this.data.ruleId,
      currentPage: currentPage,
      pageRows: 20,
      scene: scene
    })
  },
  requestCartGoodsList () {
    util.api('/api/wxapp/freeship/cart/goods/list', res => {
      if (res.error === 0) {
        this.setData({
          cartData: res.content
        })
      }
    }, {
      ruleId: this.data.ruleId,
    })
  },
  getSearchText (data) { // 点击搜索
    this.setData({
      searchText: data.detail,
      'pageParams.currentPage': 1,
      dataList: null
    })
    this.requestGoodsList()
  },
  showSelected () { // 点击已选商品
    this.setData({
      showSelectedDialog: true
    })
  },
  showSpecDialog (e) { // 调起规格弹窗
    console.log(e)
    util.api("/api/wxapp/goods/detail", res => {
      if (res.error === 0) {
        let productsInfo = {
          activity: res.content.activity,
          defaultPrd: res.content.defaultPrd,
          goodsId: res.content.goodsId,
          goodsImgs: res.content.goodsImgs,
          goodsNumber: res.content.goodsNumber,
          limitBuyNum: res.content.limitBuyNum,
          limitMaxNum: res.content.limitMaxNum,
          products: res.content.products
        }
        this.setData({
          productsInfo,
          showSpec: true
        })
      }
    }, {
      goodsId: e.detail.goodsId,
      activityId: e.detail.activityId,
      activityType: e.detail.activityType,
      userId: util.getCache("user_id"),
      lon: null,
      lat: null
    })
  },
  bindCloseSpec () { // 关闭规格弹窗
    this.setData({
      showSpec: false
    })
  },
  getProductData (e) { // 获取规格弹窗操作回传数据
    console.log(e)
    this.setData({
      product: e.detail,
      limitInfo: {
        activityType: this.data.productsInfo.activityType,
        limitBuyNum: e.detail.limitBuyNum,
        limitMaxNum: e.detail.limitMaxNum,
        prdNumber: e.detail.prdNumber
      }
    })
  },
  getGoodsNum (e) { // 获取选中的规格弹窗里商品数量
    this.setData({
      productInfo: { ...this.data.product, goodsNum: e.detail.goodsNum }
    });
    console.log(this.data.productInfo)
  },
  addCart () {
    let { goodsNum: goodsNumber, prdId } = this.data.productInfo
    console.log('已选择')
    // util.api(
    //   "/api/wxapp/cart/add",
    //   res => {
    //     if (res.error == 0) {
    //       util.toast_success('添加成功')
    //       this.requestCartGoodsList()
    //     } else {
    //       util.toast_fail('添加失败')
    //     }
    //     this.bindCloseSpec()
    //   },
    //   {
    //     goodsNumber: goodsNumber,
    //     prdId: prdId
    //   }
    // );
    util.toast_success('添加成功')
    this.bindCloseSpec()
  },
  cartChange () {
    console.log(123)
    this.requestCartGoodsList()
  },
  goCheckOut () {
    console.log('点击立即兑换跳转结算页面')
    util.jumpLink('pages/checkout/checkout', 'navigateTo')
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
  }
})