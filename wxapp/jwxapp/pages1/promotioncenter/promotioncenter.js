// pages1/promotioncenter/promotioncenter.js
var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    isLoad: 0,
    searchText: '', // 搜索内容
    showChange: false, // 查看下拉
    showWhich: 0, // 查看展示方式( 0单列, 1双列, 2多图)
    sortItem: 0, // 排序名称(0默认, 1销量, 2价格, 7佣金)
    sortDirection: 0, // 排序方式(0降序，1升序)
    showFilterDialog: false, // 筛选弹窗
    // 筛选弹窗入参
    filterData: {
      minPrice: null,
      maxPrice: null,
      sortIds: [],
      brandIds: [],
      activityTypes: [],
      // labelIds: [],
      pageFrom:null,
      outerPageParam:{}
    },
    distributionFlag: true,
    currentType: '',
    
    proInfo: [],
    proGoods: [], // 列表数据
    page: 1, // 当前页
    lastPage: 1, // 最后页
    pageRows: 20, // 页容量
    
    shareGood: false, // 分享弹窗
    currentData: {}, // 分享的当前数据
    pageType: 0, // 页面类型(0推广中心页, 1个人推广页)
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    wx.hideShareMenu();
    that.setData({ 
      pageType: options.type ? options.type : 0
    })
    if (that.data.pageType == 1) {
      wx.showShareMenu()
    }
    proRequest(that);
    console.log(that.data.pageType)
  },
  // 输入搜索内容
  searchGoods: function (e) {
    this.setData({ searchText: e.detail.value });
    this.btnSearch();
  },
  // 点击查看
  changeStyle: function () {
    this.setData({ showChange: !this.data.showChange })
  },
  // 切换查看方式
  toggleStyle: function (e) {
    var currentStyle = e.currentTarget.dataset.style;
    this.setData({
      showChange: false,
      showWhich: currentStyle
    })
  },
  // 点击排序
  switchOrder: function (e) {
    var currentType = Number(e.target.dataset.type);
    if (currentType == this.data.sortItem) {
      this.setData({
        sortDirection: this.data.sortDirection === 0 ? 1 : 0
      });
    } else {
      this.setData({
        sortItem: currentType,
        currentType: currentType,
        sortDirection: currentType == 2 ? 1 : 0 
      });
    }
    this.btnSearch();
  },
  // 加载页面
  btnSearch: function () {
    var that = this;
    that.setData({ page: 1 })
    proRequest(that);
  },
  // 显示筛选框
  leftSlideIn: function () {
    this.setData({ showFilterDialog : true })
  },
  // 筛选弹窗回调函数
  getSelectedData (data) {
    let { selectedSort: sortIds, selectedBrands: brandIds, selectedLabels: labelIds, selectedActTypes: activityTypes, minPrice, maxPrice } = data.detail
    console.log(data)
    this.setData({
      filterData: {
        ...this.data.filterData,
        minPrice,
        maxPrice,
        sortIds,
        brandIds,
        labelIds,
        activityTypes
      },
      page: 1,
      // 'pageParams.currentPage': 1
    })
    // this.selectComponent('#recommend').resetDataList().resetPage()
    proRequest(this);
  },
  // 下载多图
  goShare: function (e) {
    var that = this;
    var goodsId = e.currentTarget.dataset.goods_id
    var data = that.data.proGoods.find(item => {return item.goodsId == goodsId})
    that.setData({ currentData: data })

    wx.showLoading({
      title: '生成中',
    })
    util.api('/api/wxapp/goods/download/images', function (res) {
      if (res.error == 0) {
        wx.hideLoading();
        var goodsImage = res.content
        util.api('/api/wxapp/goods/pictorial/info', function (res) {
          if (res.error == 0 && res.content !== null) {
            wx.hideLoading()
            that.setData({
              showPoster: true,
              posterImage: [res.content, ...goodsImage]
            })
          }
        }, {
          targetId: data.goodsId,
          linePrice: data.prdLinePrice,
          realPrice: data.shopPrice,
          activityId: null
        });
      } else {
        wx.hideLoading();
        util.toast_fail(res.message);
        return false;
      }
    }, { targetId: goodsId })
  },
  // 保存图片回调函数
  handleDownloadCb () {
    let toast = this.selectComponent('#toast')
    if(!this.data.currentData.promotionLanguage) {
      toast.showToast({
        title: '图片已保存到相册',
        duration:2000
      })
    } else {
      wx.setClipboardData({
        data: this.data.currentData.promotionLanguage,
        success: (res) => {
          wx.hideToast();
          toast.showToast({
            title: '图片已保存到相册',
            content:`${this.data.currentData.promotionLanguage} 以上推广语已复制`,
            duration:4000
          })
        }
      });
    }
  },
  // 立即分享
  shareGoods: function (e) {
    var that = this;
    var goodsId = e.currentTarget.dataset.goods_id
    var data = that.data.proGoods.find(item => {return item.goodsId == goodsId})
    that.setData({ currentData: data })
    // 分享数据
    that.setData({
      shareData: {
        targetId: data.goodsId,
        linePrice: data.prdLinePrice,
        realPrice: data.shopPrice,
      }
    })

    setTimeout(function () {
      that.setData({
        shareGood: true
      })
    }, 500);
  },
  // 跳转商品详情
  toTtem: function (e) {
    util.jumpLink('/pages/item/item?gid=' + e.currentTarget.dataset.goods_id);
  },
  // 添加推广中心收藏
  setOwn: function (e) {
    var that = this;
    var type = e.currentTarget.dataset.type
    var goodsId = e.currentTarget.dataset.goods_id
    util.api('/api/wxapp/distribution/distributor/collect', res => {
      if (res.error == 0) {
        let toast = this.selectComponent('#toast')
        toast.showToast({
          title: type == 0 ? '已成功添加到“我的推广中心”' : '已成功从“我的推广中心”移除',
          icon: 'success',
          duration: 1000
        })
        that.setData({ page: 1 })
        proRequest(that);
      } else {
        util.showModal("提示",res.message);
        return false;
      }
     }, { 
      distributorId: util.getCache('user_id'),
      goodsId: goodsId,
      type: type
    });
  },
  // 去推广中心
  toProCenter: function () {
    util.jumpLink('/pages1/promotioncenter/promotioncenter')
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    var that = this;
    wx.showLoading({
      title: '加载中',
    })
    that.setData({ isLoad: 1 })
    if (that.data.page == that.data.lastPage) {
      that.setData({ isLoad: 0 })
      wx.hideLoading();
      return;
    }
    that.data.page = that.data.page + 1;

    var params = {
      userId: util.getCache('user_id'),
      search: that.data.searchText,
      currentPage: that.data.page,
      pageRows: that.data.pageRows,
      sortItem: that.data.sortItem,
      sortDirection: that.data.sortDirection,
      ...that.data.filterData
    }
    if (that.data.pageType == 1) {
      params.isPersonal = 1
    }
    util.api('/api/wxapp/distribution/promoteGoods', function (res) {
      wx.hideLoading();
      if (res.error == 0) {
        var proInfo = res.content;
        var proGoods = [];
        if (proInfo.dataList && proInfo.dataList.length > 0) {
          proGoods = proInfo.dataList
          proGoods.forEach(item => {
            item.highRebate = parseFloat(item.highRebate || 0).toFixed(2)
            // item.rebateMoney = parseFloat(item.shopPrice * (item.maxRebate / 100)).toFixed(2)
            // // 成本价保护
            // if (item.costPrice && (item.rebateMoney > item.costPrice)) {
            //   item.rebateMoney = item.costPrice
            // }
            item.reducePrice = item.shopPrice
            if (item.isMorePrd == 1) {
              if (item.lowPrdPrice == item.highPrdPrice) {
                item.regionPrice = item.lowPrdPrice
              } else {
                item.regionPrice = item.lowPrdPrice + '~' + item.highPrdPrice // 多规格
              }
              // item.rebateMoney = parseFloat(item.highPrdPrice * (item.maxRebate / 100)).toFixed(2)
              // // 成本价保护
              // if (item.costPrice && (item.rebateMoney > item.costPrice)) {
              //   item.rebateMoney = item.costPrice
              // }
            } else {
              item.regionPrice = item.shopPrice // 单规格
            }
          })
        } else {
          that.setData({ isLoad : 0})
        }
        if (proInfo.page.currentPage == proInfo.page.lastPage) {
          that.setData({ isLoad : 0})
        }
        that.setData({
          proInfo: proInfo,
          proGoods: that.data.proGoods.concat(proGoods),
          page: proInfo.page.currentPage,
          lastPage: proInfo.page.lastPage
        })
      } else {
        util.showModal("提示", res.message, function () { });
        return false;
      }
    }, { ...params });
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (e) {
    var that = this;
    console.log(that.data.proGoods)
    var rebateGoodsId = []
    if (that.data.proGoods && that.data.proGoods.length > 0) {
      that.data.proGoods.forEach(item => {
        rebateGoodsId.push(item.goodsId)
      })
    }
    if (e.from === 'button') {
      var goodsId = that.data.currentData.goodsId
      var goodsImg = that.data.currentData.goodsImg
      var goodsName = that.data.currentData.goodsName
      return {
        path: '/pages/item/item?gid=' + goodsId + '&inviteId=' + util.getCache('user_id'),
        title: util.getCache('nickName') + '为您独家推荐' + goodsName,
        imageUrl: goodsImg,
      }
    } else {
      util.setCache('distributionType', 2); // 独立商品页面类型
      util.setCache('rebateGoodsIds', JSON.stringify(rebateGoodsId)); // 分享商品id集合
      return{
        // path: '/pages/search/search?distributorId=' + util.getCache('user_id') + '&rebateGoodsIds=' + rebateGoodsId,
        path: `/pages/search/search${util.getUrlParams({
          distributorId: util.getCache('user_id'),
          rebateGoodsIds: JSON.stringify(rebateGoodsId)
        })}&inviteId=${util.getCache('user_id')}`,
        title: util.getCache('nickName') + '分享好物给你！',
        imageUrl: that.data.proGoods && that.data.proGoods[0] ? that.data.proGoods[0].goodsImg : ''
      }
    }
  }
})
function proRequest(that) {
  var params = {
    userId: util.getCache('user_id'),
    search: that.data.searchText,
    currentPage: that.data.page,
    pageRows: that.data.pageRows,
    sortItem: that.data.sortItem,
    sortDirection: that.data.sortDirection,
    ...that.data.filterData
  }
  if (that.data.pageType == 1) {
    params.isPersonal = 1
  }
  util.api('/api/wxapp/distribution/promoteGoods', function (res) {
    if (res.error == 0) {
      var proInfo = res.content;
      var proGoods = [];
      if (proInfo.dataList && proInfo.dataList.length > 0) {
        proGoods = proInfo.dataList
        proGoods.forEach(item => {
          item.highRebate = parseFloat(item.highRebate || 0).toFixed(2)
          // item.rebateMoney = parseFloat(item.shopPrice * (item.maxRebate / 100)).toFixed(2)
          // // 成本价保护
          // if (item.costPrice && (item.rebateMoney > item.costPrice)) {
          //   item.rebateMoney = item.costPrice
          // }
          item.reducePrice = item.shopPrice
          if (item.isMorePrd == 1) {
            if (item.lowPrdPrice == item.highPrdPrice) {
              item.regionPrice = item.lowPrdPrice
            } else {
              item.regionPrice = item.lowPrdPrice + '~' + item.highPrdPrice // 多规格
            }
            // item.rebateMoney = parseFloat(item.highPrdPrice * (item.maxRebate / 100)).toFixed(2)
            // // 成本价保护
            // if (item.costPrice && (item.rebateMoney > item.costPrice)) {
            //   item.rebateMoney = item.costPrice
            // }
          } else {
            item.regionPrice = item.shopPrice // 单规格
          }
        })
      } else {
        that.setData({ isLoad : 0})
      }
      if (proInfo.page.currentPage == proInfo.page.lastPage) {
        that.setData({ isLoad : 0})
      }
      that.setData({
        proInfo: proInfo,
        proGoods: proGoods,
        page: proInfo.page.currentPage,
        lastPage: proInfo.page.lastPage
      })
    } else {
      util.showModal("提示", res.message, function () { });
      return false;
    }
  }, { ...params });
}