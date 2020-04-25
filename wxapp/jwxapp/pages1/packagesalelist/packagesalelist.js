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
      packageId: 30
    })
    this.requestGoodsList()
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
    if (currentPage === 1) {
      this.setData({
        dataList: []
      })
    }
    util.api('/api/wxapp/packagesale/goodslist', res => {
      console.log(res)
      if (res.error === 0 && res.content) {
        this.setData({
          pageParams: res.content.goods.page,
          ['dataList[' + (parseInt(currentPage) - 1) + ']']: this.resetGoodsList(res.content.goods.dataList),
          tabList: res.content.tabList,
          showCart: this.data.isFirstLoad ? {
            cart_type: 1,
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
      'pageParams.currentPage': 1
    })
    this.requestGoodsList()
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