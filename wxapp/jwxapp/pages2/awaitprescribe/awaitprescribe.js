// pages2/awaitprescribe/awaitprescribe.js
var util = require('../../utils/util.js');
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    page_info: null,
    pageParams: null,
    show_modal: 0,
    taped_order_id: '',
    rejectReason: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    this.requestInfo();
  },
  requestInfo () {
    let currentPage = this.data.pageParams ? this.data.pageParams.currentPage : 1;
    util.api('/api/wxapp/order/prescribe/medical/get', res => {
      if(res.error == 0) {
        let page_info = JSON.parse(JSON.stringify(res.content.dataList));
        for (var i in page_info) {
          page_info[i].if_show_more = 0;
          page_info[i].medicalHistory.gestationName = this.getGesName(page_info[i].medicalHistory.gestationType);
          if(!!page_info[i].medicalHistory && !!page_info[i].medicalHistory.patientComplain){
            page_info[i].medicalHistory.patientComplain =JSON.parse(page_info[i].medicalHistory.patientComplain);
          }
          if(!!page_info[i].medicalHistory && !!page_info[i].medicalHistory.imagesList){
            page_info[i].medicalHistory.imagesList = JSON.parse(page_info[i].medicalHistory.imagesList);
            console.log(page_info[i].medicalHistory.imagesList)
          }
        }
        this.setData({
          pageParams: res.content.page,
          ['page_info[' + (parseInt(currentPage) - 1) + ']']: page_info
        })
      } else {
        util.showModal('提示', res.message);
        return false
      }
    },{currentPage: currentPage, pageRows: 20})
  },
  getGesName (data) {
    switch(data){
      case 0:
        return '未知'
      case 1:
        return '无'
      case 2:
        return '备孕中'
      case 3:
        return '怀孕中'
      case 4:
        return '正在哺乳'
    }
  },
  show_more (e) {
    var this_index = e.currentTarget.dataset.index;
    var parent_index = e.currentTarget.dataset.parent_index;
    var page_info_list = this.data.page_info[parent_index];
    if(page_info_list[this_index].if_show_more == 0) {
      page_info_list[this_index].if_show_more = 1
    } else {
      page_info_list[this_index].if_show_more = 0
    }
    this.setData({
      [`page_info[${parent_index}]`]: page_info_list
    })
  },
  to_pre (e) {
    util.jumpLink('/pages2/prescribeinfo/prescribeinfo?orderId=' + e.currentTarget.dataset.order_id);
  },
  tp_reject (e) {
    this.data.taped_order_id = e.currentTarget.dataset.order_id;
    this.setData({
      show_modal: 1,
      taped_order_id: this.data.taped_order_id
    })
    
  },
  close_modal () {
    this.data.taped_order_id = '';
    this.data.rejectReason = '';
    this.setData({
      show_modal: 0,
      taped_order_id: '',
      rejectReason: ''
    })
  },
  rejectReason (e) {
    this.data.rejectReason = e.detail.value
  },
  to_reject (e) {
    if(this.data.rejectReason == ""){
      util.showModal('提示', "请输入驳回原因")
      return false
    }
    util.api('/api/wxapp/order/prescription/make', res => {
      if(res.error == ''){
        util.toast_success('驳回成功');
        this.requestInfo();
      } else {
        util.showModal('提示', res.message)
        return false
      }
    },{
      orderId: e.currentTarget.dataset.taped_order_id,
      auditStatus: 2
    })
    this.data.taped_order_id = '';
    this.data.rejectReason = '';
    this.setData({
      show_modal: 0,
      taped_order_id: '',
      rejectReason: ''
    })
  },
  viewGoodsInfo(e){
    let {gid} = e.currentTarget.dataset
    util.jumpLink(`pages/item/item${util.getUrlParams({
      gid
    })}`)
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
    if (this.data.pageParams && this.data.pageParams.currentPage === this.data.pageParams.lastPage )
    return;
    this.setData({
      'pageParams.currentPage': this.data.pageParams.currentPage + 1
    });
    this.requestInfo();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})