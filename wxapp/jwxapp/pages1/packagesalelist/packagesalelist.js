var util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    search: null,
    pageParams: null,
    groupId: 1,
    sortName: null,
    sortOrder: null,
    isFirstLoad: true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      packageId: options.packageId
    })
    this.requestGoodsList()
    this.requestCartGoodsList()
  },

  requestGoodsList() {
    let {
      packageId,
      groupId,
      search,
      sortName,
      sortOrder
    } = this.data
    let currentPage = this.data.pageParams ?
      this.data.pageParams.currentPage :
      1;
    util.api('/api/wxapp/packagesale/goodslist', res => {
      console.log(res)
      if (res.error === 0 && res.content) {
        if(currentPage === 1){
          this.setData({
            dataList:[]
          })
        }
        this.setData({
          pageParams: res.content.goods.page,
          ['dataList[' + (parseInt(currentPage) - 1) + ']']: this.resetGoodsList(res.content.goods.dataList),
          tabList: res.content.tabList,
          ruleText: this.getRuleText(res.content),
          totalGoodsNumber: res.content.totalGoodsNumber,
          rule: res.content.title,
          delMarket:this.data.isFirstLoad ? res.content.delMarket : this.data.delMarket,
          showCart: this.data.isFirstLoad ? {
            cart_type: res.content.showCart.cart_type,
            show_cart: 1
          } : this.data.showCart,
          isFirstLoad: false
        })
      }
    }, {
      packageId,
      search,
      groupId,
      sortName,
      sortOrder,
      currentPage,
      pageRows: 20
    }, '', true)
  },
  requestCartGoodsList() {
    let {
      packageId
    } = this.data
    util.api('/api/wxapp/packagesale/checkedlist', res => {
      console.log(res)
      if (res.error === 0) {
        this.setData({
          cartData: {
            cartGoodsList: this.resetCartList(res.content),
            totalSelectNumber: res.content.totalSelectNumber,
            totalMoney: res.content.totalMoney
          }
        })
      }
    }, {
      packageId
    })
  },
  resetGoodsList(dataList) {
    return dataList.map(item => {
      return {
        ...item,
        goodsImg: `${this.data.imageUrl}${item.goodsImg}`,
        realPrice: item.shopPrice,
        linePrice: item.marketPrice
      }
    })
  },
  resetCartList({
    goodsList
  }) {
    return goodsList.reduce((defaultData, item, index) => {
      let {
        selectList,
        groupId
      } = item
      selectList.forEach(cartItem => {
        cartItem.cartNumber = cartItem.goodsNumber;
        cartItem.groupId = groupId
      })
      defaultData = [...defaultData, ...selectList]
      return defaultData
    }, [])
  },
  customFilter(e) {
    let {
      type
    } = e.currentTarget.dataset
    let {
      sortName,
      sortOrder
    } = this.data
    switch (type) {
      case '1':
        this.setData({
          sortName: 1,
          sortOrder: sortName === 2 || sortName === null ? 1 : sortOrder === 1 ? 2 : 1,
          'pageParams.currentPage': 1
        })
        break;
      case '2':
        this.setData({
          sortName: 2,
          sortOrder: sortName === 1 || sortName === null ? 2 : sortOrder === 1 ? 2 : 1,
          'pageParams.currentPage': 1
        })
        break;
      default:
        break;
    }
    this.requestGoodsList()
  },
  changeGroup(e) {
    let {
      type: groupId
    } = e.currentTarget.dataset
    this.setData({
      groupId,
      sortName: null,
      sortOrder: null,
      'pageParams.currentPage': 1
    })
    this.requestGoodsList()
  },
  getSearchText(data) {
    this.setData({
      search: data.detail,
      sortName: null,
      sortOrder: null,
      'pageParams.currentPage': 1
    })
    this.requestGoodsList()
  },
  getRuleText({
    title,
    tabList
  }) {
    let ruleText = ''
    if (title.packageType === 1) {
      ruleText = `以下商品购满${title.totalGoodsNumber}件可享${title.discountTotalRatio}折优惠`;
    } else {
      ruleText = `以下商品${title.totalMoney}元${title.totalGoodsNumber}件`;
    }
    let groupStr = tabList.reduce((defaultStr, item, index) => {
      if (tabList.length > 1) {
        if (index === 0) defaultStr += `，其中，`
        defaultStr += `${item.groupName}需选择${item.goodsNumber}件，`
        if (index === tabList.length - 1) defaultStr += `满足选择条件即可结算`
      }
      return defaultStr
    }, '')
    return `${ruleText}${groupStr}`
  },
  showSelected() {
    this.setData({
      showSelectedDialog: true
    })
  },
  showSpecDialog(e) {
    console.log(e)
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
      goodsId: e.detail.goodsId,
      activityId: e.detail.activityId,
      activityType: e.detail.activityType,
      userId: util.getCache("user_id"),
      lon: null,
      lat: null
    })
  },
  bindCloseSpec() {
    this.setData({
      showSpec: false
    })
  },
  getProductData(e) {
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
  getGoodsNum(e) {
    this.setData({
      productInfo: {
        ...this.data.product,
        goodsNum: e.detail.goodsNum
      }
    });
  },
  addCart() {
    let {
      goodsNum: goodsNumber,
      prdId: productId,
      goodsId
    } = this.data.productInfo
    let {
      packageId,
      groupId
    } = this.data
    this.cartOperate(packageId,groupId,goodsId, productId,goodsNumber)
  },
  deletCart({
    detail: goodsData
  }) {
    let {packageId} = this.data
    let {groupId,goodsId,productId} = goodsData
    util.api('/api/wxapp/packagesale/delete',res => {
      if(res.error === 0){
        util.toast_success('删除成功')
        this.requestGoodsList()
          this.requestCartGoodsList()
      }
    },{
      packageId,
      groupId,
      goodsId,
      productId
    })
  },
  cartNumChange({
    detail: goodsData
  }) {
    let {packageId} = this.data
    let {type,groupId,goodsId,productId} = goodsData
    let goodsNumber = type === 'plus' ? 1 : -1
    this.cartOperate(packageId,groupId,goodsId, productId,goodsNumber,type)
  },
  cartOperate(packageId,groupId,goodsId, productId,goodsNumber,type){
    util.api(
      "/api/wxapp/packagesale/add",
      res => {
        console.log(res)
        if (res.error == 0 && res.content.state === 0) {
          if(!type) util.toast_success('添加成功')
          if(type === 'delete') util.toast_success('删除成功')
          this.requestGoodsList()
          this.requestCartGoodsList()
        } else if (res.error == 0 && res.content.state !== 0) {
          let errorMessage = {
            1: '该一口价活动已删除',
            2: '该一口价活动已停用',
            3: '该一口价活动已过期',
            4: '该一口价活动未开始',
            5: '活动规则已发生变化，请重新选择',
            6: '分组已选满',
            7: '该商品已失效',
            8: '该商品已下架',
            9: '该商品库存不足'
          }
          if (![5, 6].includes(res.content.state)) util.showModal('提示', errorMessage[res.content.state]);
          if (res.content.state === 5) {
            util.showModal("提示", errorMessage[res.content.state], () => {
              this.setData({
                groupId: 1,
                search: null,
                sortName: null,
                sortOrder: null,
                'pageParams.currentPage': 1
              })
              this.requestGoodsList()
              this.requestCartGoodsList()
            }, true, "取消", "继续参加")
          }
          if (res.content.state === 6) {
            util.showModal('提示', `${res.content.groupName}${errorMessage[res.content.state]}`)
          }
        } else {
          util.showModal('提示', res.message);
        }
        this.bindCloseSpec()
      }, {
        packageId,
        groupId,
        goodsId,
        productId,
        goodsNumber
      }
    );
  },
  goCheckOut(){
    let {packageId} = this.data
    util.api('/api/wxapp/packagesale/checkout',res=>{
      console.log(res)
      if(res.error === 0 && res.content.state === 0){
        let goodsList = res.content.goods.map(item=>{
          item.prdId = item.productId,
          item.goodsNum = item.goodsNumber
          return item
        })
        util.jumpLink(`pages/checkout/checkout${util.getUrlParams({ goodsList: JSON.stringify(goodsList), activityType:9, activityId:packageId})}`, "navigateTo")
      } else if (res.error === 0 && res.content.state !== 0) {
        let errorMessage = {
          1:'该活动已删除',
          2:'该活动已停用',
          3:'该活动已过期',
          4:'分组商品选择数量不足',
          5:'分组商品超出可结算数量'
        }
        if([1,2,3].includes(res.content.state)) util.showModal('提示', errorMessage[res.content.state]);
        if([4,5].includes(res.content.state)) {
          util.showModal('提示', `${res.content.groupName}${errorMessage[res.content.state]}`);
        }
      } else {
        util.showModal('提示', res.message);
      }
    },{packageId})
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