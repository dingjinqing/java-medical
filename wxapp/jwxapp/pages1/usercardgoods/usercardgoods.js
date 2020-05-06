var util = require('../../utils/util.js')
var scene;
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    cartData: [],
    dataList: [],
    pageParams: null,
    searchText: null,
    totalNumber: 0,  //已选择兑换商品的数量
    exchangSurplus: 0 // 剩余兑换总数量
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options)
    this.setData({
      page_name: this.$t('page1.usercardgoods.exchangeGoods')
    })
    let { cardNo, cardId } = options
    this.setData({
      cardNo,
      cardId
    })
    this.requestGoodsList() // 请求兑换商品列表的数据
    this.requestCartGoodsList() // 请求已选择的兑换商品数据
  },


  requestGoodsList () {
    let currentPage = this.data.pageParams
      ? this.data.pageParams.currentPage
      : 1;
    util.api('/api/wxapp/card/change/goodslist', res => {
      console.log(res)
      if (res.error === 0 && res.content !== null) {
        res.content.goodsPageResult.dataList.forEach((item, index) => {
          if (item.marketPrice) {
            if (item.shopPrice < item.marketPrice) {
              item.showLinePrice = true
            } else {
              item.showLinePrice = false
            }
          }

        })
        this.setData({
          pageParams: res.content.goodsPageResult.page,
          ['dataList[' + (parseInt(currentPage) - 1) + ']']: res.content.goodsPageResult.dataList,
          totalNumber: res.content.totalNumber,
          exchangSurplus: res.content.cardInfo.exchang_surplus
          // delMarket: res.content.delMarket,
          // showCart: {
          //   ...res.content.showCart,
          //   show_cart: 1
          // },
          // ruleInfo: res.content.freeShippingRule
        });
      }
    }, {
      search: this.data.searchText,
      cardNo: this.data.cardNo,
      currentPage: currentPage,
      pageRows: 20
    })
  },
  requestCartGoodsList () {
    util.api('/api/wxapp/card/change/checkedlist', res => {
      console.log(res)
      if (res.error === 0) {
        res.content.goodsList.dataList.forEach((item, index) => {
          item.goodsPrice = item.marketPrice
          item.cartNumber = item.goodsNumber
        })
        console.log(res.content.goodsList.dataList)
        this.setData({
          cartData: res.content.goodsList.dataList,
          totalNumber: res.content.totalNumber
        })
      }
    }, {
      identityId: this.data.cardNo,
    })
  },
  deletCart (res) {
    console.log(res)
    util.api('/api/wxapp/card/change/remove', res => {
      console.log(res)
      if (res.error === 0) {
        util.toast_success(this.$t('page1.usercardgoods.deleteSucceeded'))
        this.requestCartGoodsList()
      } else {
        util.toast_fail(this.$t('page1.usercardgoods.deleteFailed'))
      }
    }, {
      identityId: this.data.cardNo,
      productId: res.detail.productId,
      goodsId: res.detail.goodsId
    })
  },
  cartNumChange (res) {
    console.log(res)
    this.addCart(res.detail)
  },
  getSearchText (data) { // 点击搜索
    this.setData({
      searchText: data.detail,
      'pageParams.currentPage': 1,
      dataList: []
    })
    this.requestGoodsList()
  },
  showSelected () { // 点击已选商品
    this.setData({
      showSelectedDialog: true
    })
  },
  showSpecDialog (e) { // 调起规格弹窗
    console.log(e.currentTarget.dataset.goodsnumber)
    if (!e.currentTarget.dataset.goodsnumber) {
      util.toast_success(`${e.currentTarget.dataset.goodsName} ${this.$t('page1.usercardgoods.inventoryIs')}`)
      return
    }
    util.api("/api/wxapp/goods/detail", res => {
      if (res.error === 0) {
        let productsInfo = {
          activity: null,
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
      goodsId: e.currentTarget.dataset.goodsid,
      activityId: null,
      activityType: null,
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
  addCart (res) {
    console.log(res.id)
    let paramsPrdNumber = ''
    let paramsPrdId = ''
    let paramsGoodsId = ''

    if (res.id != undefined) {
      console.log('plus')
      if (res.type == "plus") {
        res.cartNumber++
      } else {
        res.cartNumber--
      }
      let { cartNumber, productId, goodsId } = res
      paramsPrdNumber = cartNumber
      paramsPrdId = productId
      paramsGoodsId = goodsId
    } else {
      let { goodsNum, prdId, goodsId } = this.data.productInfo
      paramsPrdNumber = goodsNum
      paramsPrdId = prdId
      paramsGoodsId = goodsId
    }
    console.log(this.data.productInfo)
    console.log('已选择')
    util.api('/api/wxapp/card/change/add', res => {
      console.log(res)
      if (res.error === 0) {
        if (res.id == undefined) {
          util.toast_success(this.$t('page1.usercardgoods.successfullyAdded'))
        }
        this.requestCartGoodsList()
        this.bindCloseSpec()
      } else {
        if (res.id == undefined) {
          util.toast_success(this.$t('page1.usercardgoods.addFailed'))
        }
      }
    }, {
      goodsId: paramsGoodsId,
      productId: paramsPrdId,
      prdNumber: paramsPrdNumber,
      cardNo: this.data.cardNo,
    })
  },
  cartChange () {
    console.log(123)
    this.requestCartGoodsList()
  },
  goCheckOut () {
    console.log('点击立即兑换跳转结算页面')
    //  this.data.cartData   
    // util.jumpLink('pages/checkout/checkout', 'navigateTo')
  },
  to_goods: function (e) {
    console.log('to_goods', this.data)
    let goods_id = e.currentTarget.dataset.goods_id;
    util.api('/api/wxapp/card/exchange/judge', function (res) {
      if (res.error == 0) {
        util.navigateTo({
          url: `/pages/item/item?gid=${goods_id}&cardNo=${this.data.cardNo}&cardId=${this.data.cardId}&isChange=1`,
        })
      } else {
        util.showModal(this.$t('page1.usercardgoods.tips'), res.message, function () {
          util.jumpLink('/pages/item/item?gid=' + goods_id, 'navigateTo')
        }, true, this.$t('page1.usercardgoods.cancel'), this.$t('page1.usercardgoods.originalPricePurchase'))
      }
    }, { cardNo: this.data.cardNo, goodsId: goods_id, isList: 2 })
    if (this.data.exchangSurplus == 0) {
      util.showModal(this.$t('page1.usercardgoods.tips'), this.$t('page1.usercardgoods.noRedeemableTimes'), function () {
        util.jumpLink('/pages/item/item?gid=' + goods_id, 'navigateTo')
      }, true, this.$t('page1.usercardgoods.cancel'), this.$t('page1.usercardgoods.originalPricePurchase'))
    } else {
      util.navigateTo({
        url: `/pages/item/item?gid=${goods_id}&cardNo=${this.data.cardNo}&cardId=${this.data.cardId}&isChange=1`,
      })
    }
  },
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    console.log('触底')
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