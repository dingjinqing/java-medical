const util = require("../../utils/util")

// pages2/operatePrescriptionList/operatePrescriptionList.js
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    pageParams:{
      currentPage:1,
      pageRows:20
    },
    show_modal: 0,
    taped_order_id: '',
    taped_order_sn: '',
    taped_par_index: '',
    taped_old_code: '',
    rejectReason: '',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.requestList()
  },
  requestList(){
    util.api('/api/wxapp/doctor/audit/list',res=>{
      if(res.error === 0){
        if(this.data.pageParams.currentPage === 1){
          this.setData({
            dataList:[[...res.content.dataList]]
          })
        } else {
          this.setData({
            ['dataList[' + (parseInt(this.data.pageParams.currentPage) - 1) + ']']:res.content.dataList
          })
        }
        this.setData({
          pageParams:res.content.page
        })
      }
    },{
      ...this.data.pageParams
    })
  },
  handleShowDialog(e){
    let {prescriptionCode} = e.currentTarget.dataset
    util.api('/api/wxapp/prescription/details',res=>{
      if(res.error === 0){
        this.setData({
          showPrescription:true,
          prescriptionData:res.content
        })
      }
    },{
      prescriptionCode
    })
  },
  changeStatus(e){
    let {type,orderId,orderSn,parentIndex,prescriptionOldCode,doctorAdvice=null} = e.currentTarget.dataset
    let dataListItem = this.data.dataList[parentIndex]
    let targetIndex = dataListItem.findIndex(item=>item.orderSn === orderSn && item.orderId === orderId)
    let auditStatus = {
      'reject':2,
      'pass':1
    }
    let tipsInfo = {
      'reject':['确认驳回患者处方申请并退款吗？','驳回'],
      'pass':['确认通过患者处方申请吗？','通过']
    }
    if(auditStatus[type] == 2 && this.data.rejectReason == ''){
      util.showModal('提示', "请输入驳回原因")
      return false
    }
    util.showModal('提示',tipsInfo[type][0],()=>{
      util.api('/api/wxapp/doctor/audit/pass',res=>{
        console.log(res)
        if(res.error === 0){
          this.data.taped_order_id = '';
          this.data.taped_order_sn = '';
          this.data.taped_par_index = '';
          this.data.taped_old_code = '';
          this.data.rejectReason = '';
          dataListItem.splice(targetIndex,1)
          this.setData({
            [`dataList[${parentIndex}]`]:dataListItem,
            show_modal: 0,
            taped_order_id: '',
            taped_order_sn: '',
            taped_par_index: '',
            taped_old_code: '',
            rejectReason: ''
          })
        } else {
          util.showModal('提示',res.message)
        }
      },{
        orderId,
        orderSn,
        doctorId:util.getCache('doctor_id') || util.getCache('bottom').doctor_id,
        prescriptionOldCode,
        auditStatus:auditStatus[type],
        doctorAdvice
      })
    },true,'取消',tipsInfo[type][1])
  },
  viewGoodsInfo(e){
    let {gid} = e.currentTarget.dataset
    util.jumpLink(`pages/item/item${util.getUrlParams({
      gid
    })}`)
  },
  showRejectModal (e) {
    this.data.taped_order_id = e.currentTarget.dataset.order_id;
    this.data.taped_order_sn = e.currentTarget.dataset.order_sn;
    this.data.taped_par_index = e.currentTarget.dataset.parent_index;
    this.data.taped_old_code = e.currentTarget.dataset.prescription_old_code;
    this.setData({
      show_modal: 1,
      taped_order_id: this.data.taped_order_id,
      taped_order_sn: this.data.taped_order_sn,
      taped_par_index: this.data.taped_par_index,
      taped_old_code: this.data.taped_old_code
    })
  },
  close_modal () {
    this.data.taped_order_id = '';
    this.data.taped_order_sn = '';
    this.data.taped_par_index = '';
    this.data.taped_old_code = '';
    this.data.rejectReason = '';
    this.setData({
      show_modal: 0,
      taped_order_id: '',
      taped_order_sn: '',
      taped_par_index: '',
      taped_old_code: '',
      rejectReason: ''
    })
  },
  rejectReason (e) {
    this.data.rejectReason = e.detail.value
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
    this.setData({
      'pageParams.currentPage': 1
    })
    this.requestList()
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    if (
      this.data.pageParams &&
      this.data.pageParams.currentPage === this.data.pageParams.lastPage
    ) {
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
  onShareAppMessage: function () {

  }
})