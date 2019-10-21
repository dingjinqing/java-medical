var util = require('../../utils/util.js');
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    searchInput: null,
    scrollIntoId : 'ALL',
    currentPage:1,
    pageParams:null,
    dataList:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.requestList();
  },
  requestList() {
    let currentPage = this.data.pageParams ? this.data.pageParams.currentPage : 1;
    console.log(parseInt(currentPage) - 1)
    util.api('/api/wxapp/order/list', (res) => {
      if(res.error === 0){
        let dataList = this.formatData(res.content.dataList);
        this.setData({
          pageParams: res.content.page,
          ['dataList[' + (parseInt(currentPage) - 1) + ']']: dataList
        })
      }
    }, {
        currentPage: currentPage,
      pageRows: 20,
      type:0
    });
  },
  // 获取搜索栏input值
  handleSearchInput(e) {
    this.setData({
      searchInput: e.detail.value
    });
  },
  formatData(order){
    let formatOrderItem = order.map(item=>{
      const filterArr = ['isShowPay', 'isLotteryGift', 'isReturn', 'isPayEndPayment', 'isExtendReceive', 'isShowAgainBuy', 'isRemindShip', 'isShowCommentType', 'payOperationTime','isDelete']
      let d =  this.filterObj(item, filterArr);
      for(let k in d ){
        if(!(d[k] > 0)){
          delete d[k]
        }
      }
      console.log(d)
      item.operate = d
    })
    return formatOrderItem
  },
  // 清空搜索栏
  clearSearchInput(){
    this.setData({
      searchInput:null
    })
  },
  handleSearch() {
    this.searchInput()
  },
  // 切换导航
  handleChangeNav(e){
    this.setData({
      scrollIntoId:e.currentTarget.id
    })
  },
  // 订单列表下按钮事件集合
  handleOperate(e) {
    let optionList = {
      0: (() => {
        return this.TODO;
      })(),
      1: (() => {
        return this.TODO;
      })()
    };
    // optionList[e.currentTarget.dataset.index](e.currentTarget.dataset.order_sn);
  },
  // 查看详情
  viewInfo(orderSn) {
    util.jumpLink(`/page/order_info/order_info?order_sn=${orderSn}`, 'navigateTo')
  },
  // 查看评价
  viewComment(orderSn) {
    util.jumpLink(`/pages/comment/comment?order_sn=${orderSn}`, 'navigateTo')
  },
  // 再次购买
  addCart(orderSn) {
    util.api(
      '/api/wxapp/order/Repurchase',
      (res) => {
        if (res.error == 0) {
          util.toast_success('已加入购物车');
        } else {
          util.showModal('提示', res.message);
        }
      },
      {
        order_sn: orderSn
      }
    );
  },
  // 删除订单
  delOrder(orderSn) {
    util.showModal(
      '提示',
      '是否删除该订单',
      (res) => {
        util.api(
          '/api/wxapp/order/del',
          function (res) {
            if (res.error == 0) {
            }
          },
          { order_sn: orderSn }
        );
      },
      true
    );
  },
  // 提醒发货
  remindOrder(orderSn) {
    util.api(
      '/api/wxapp/order/remind',
      (res) => {
        if (res.error == 0) {
          util.toast_success('提醒成功');
        } else {
          util.toast_fail(res.message);
        }
      },
      {
        order_sn: orderSn
      }
    );
  },
  // 好友代付
  // 退货中心
  filterObj(obj, arr) {
    if(typeof (obj) !== "object" || !Array.isArray(arr)) {
      throw new Error("参数格式不正确")
    }
    const result = {}
    Object.keys(obj).filter((key) => arr.includes(key)).forEach((key) => {
      result[key] = obj[key]
    })
    return result
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {},
  
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {},

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {},

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {},

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {},

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    if (this.data.pageParams && this.data.pageParams.currentPage === this.data.pageParams.lastPage) return;
    this.setData({
      'pageParams.currentPage': this.data.pageParams.currentPage + 1
    })
    this.requestList()
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {}
});
