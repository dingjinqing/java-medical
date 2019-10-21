var util = require('../../utils/util.js');
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    searchInput: null,
    scrollIntoId : 'ALL',
    currentPage:1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.requestList(this.data.currentPage);
  },
  requestList(currentPage) {
    util.api('/api/wxapp/order/list', function() {}, {
      currentPage: currentPage,
      pageRows: 20
    });
  },
  // 获取搜索栏input值
  handleSearchInput(e) {
    this.setData({
      searchInput: e.detail.value
    });
  },
  // 清空搜索栏
  clearSearchInput(){
    this.setData({
      searchInput:null
    })
  },
  handleSearch() {},
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
    let currentPage = this.data.currentPage + 1;
    this.requestList(currentPage)
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {}
});
