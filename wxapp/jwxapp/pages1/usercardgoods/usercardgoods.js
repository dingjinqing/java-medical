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
      page_name: '兑换商品'
    })
    let { cardNo } = options
    this.setData({
      cardNo
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
      searchText: this.data.searchText,
      cardNo: this.data.cardNo,
      currentPage: currentPage,
      pageRows: 20
    })
  },
  requestCartGoodsList () {
    util.api('/api/wxapp/card/change/checkedlist', res => {
      console.log(res)
      if (res.error === 0) {
        this.setData({
          cartData: this.data.cartData.concat(res.content.dataList)
        })
      }
    }, {
      identityId: this.data.cardNo,
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
    console.log(e.currentTarget.dataset.goodsid)
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
  addCart () {
    let { goodsNum: prdNumber, prdId, goodsId } = this.data.productInfo
    console.log(this.data.productInfo)
    console.log('已选择')
    util.api('/api/wxapp/card/change/add', res => {
      console.log(res)
      if (res.error === 0) {
        util.toast_success('添加成功')
        this.requestCartGoodsList()
        this.bindCloseSpec()
      } else {
        util.toast_fail('添加失败')
      }
    }, {
      goodsId: goodsId,
      productId: prdId,
      prdNumber: prdNumber,
      cardNo: this.data.cardNo,
    })
  },
  cartChange () {
    console.log(123)
    this.requestCartGoodsList()
  },
  goCheckOut () {
    console.log('点击立即兑换跳转结算页面')
    util.jumpLink('pages/checkout/checkout', 'navigateTo')
  },
  to_goods: function (e) {
    console.log('to_goods')
    // let goods_id = e.currentTarget.dataset.goods_id;
    // util.api('/api/card/exchange/judge', function (res) {
    //   if (res.error == 0) {
    //     util.navigateTo({
    //       url: '/pages/item/item?good_id=' + goods_id + '&from_count_card=1&card_no=' + card_no,
    //     })
    //   } else {
    //     util.showModal('提示', res.message, function () {
    //       util.jumpLink('/pages/item/item?good_id=' + goods_id, 'navigateTo')
    //     }, true, '取消', '原价购买')
    //   }
    // }, { card_no: card_no, goods_id: goods_id, is_list: 2 })
    // if (card_info.exchang_surplus == 0) {
    //   util.showModal('提示', '此卡无剩余可兑换次数', function () {
    //     util.jumpLink('/pages/item/item?good_id=' + goods_id, 'navigateTo')
    //   }, true, '取消', '原价购买')
    // } else {
    //   util.navigateTo({
    //     url: '/pages/item/item?good_id=' + goods_id + '&from_count_card=1&card_no=' + card_no,
    //   })
    // }
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