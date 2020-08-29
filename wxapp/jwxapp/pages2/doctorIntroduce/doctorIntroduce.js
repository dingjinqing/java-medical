var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    isAttention: false,
    show_announce: false,
    pageParams: {
      currentPage: 1,
      pageRows: 20
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let {
      doctorId
    } = options
    this.setData({
      doctorId: doctorId
    }, function () {
      this.getDoctor()
    })
    this.getCommentList()
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
    if (this.data.pageParams.currentPage === this.data.pageParams.lastPage) return;
    this.setData({
      'pageParams.currentPage': this.data.pageParams.currentPage + 1
    });
    this.getCommentList();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  choose_focus() {
    this.setData({
      isAttention: !this.data.isAttention
    })
    this.updateFocus()
  },
  patient_announce() {
    this.setData({
      show_announce: true
    })
  },
  updateFocus() {
    let focus = this.data.isAttention;
    let status;
    if (focus == true) {
      status = 1
    } else {
      status = 0
    }
    util.api('/api/wxapp/user/doctor/attention/update', function (res) {
      console.log(res)
      if (res.error == 0) {
        if (status == 1) {
          util.toast_success('已关注');
        } else {
          util.toast_success('取消关注');
        }

      }
    }, {
      doctorId: this.data.doctorId,
      userId: util.getCache('user_id'),
      status: status
    })
  },
  getDoctor() {
    let that = this;
    util.api('/api/wxapp/consultation/doctor/info', function (res) {
      console.log(res)
      if (res.error == 0) {
        that.setData({
          con: res.content,
          isAttention: res.content.isAttention
        })
      }
    }, {
      doctorId: that.data.doctorId,
      userId: util.getCache('user_id'),
    })
  },

  updateFocus() {
    let focus = this.data.isAttention;
    let status;
    if (focus == true) {
      status = 1
    } else {
      status = 0
    }
    util.api('/api/wxapp/user/doctor/attention/update', function (res) {
      console.log(res)
      if (res.error == 0) {
        if (status == 1) {
          util.toast_success('已关注');
        } else {
          util.toast_success('取消关注');
        }

      }
    }, {
      doctorId: this.data.doctorId,
      userId: util.getCache('user_id'),
      status: status
    })
  },
  getCommentList() {
    let currentPage = this.data.pageParams ? this.data.pageParams.currentPage : 1;
    util.api('/api/wxapp/patient/doctor/comment/list', res => {
      console.log(res)
      if (res.error === 0) {
        let list = res.content.dataList
        list.forEach(function (item, index) {
          if (item.commNoteLength >= 75) {
            item.new_comm_note = item.commNote.substring(0, 72) + '...';
            item.need_expand = true;
            item.zk = true
          } else {
            item.new_comm_note = item.commNote;
          }
          if (item.replylist) {
            if (item.replylist[0].replyNote.length >= 70) {
              item.rp_new_comm_note = item.replylist[0].replyNote.substring(0, 67) + '...';
              item.rp_need_expand = true;
              item.rp_zk = true
            } else {
              item.rp_new_comm_note = item.replylist[0].replyNote;
            }
          }
        })
        if (this.data.pageParams.currentPage === 1) {
          this.setData({
            dataList: [
              [...list]
            ]
          })
        } else {
          this.setData({
            ['dataList[' + (parseInt(currentPage) - 1) + ']']: list
          })
        }
        this.setData({
          pageParams: res.content.page
        })
      }
    }, {
      ...this.data.pageParams,
      doctorId: this.data.doctorId
    })
  },
  zk_sq(e) {
    let pidx = e.currentTarget.dataset.pidx;
    let idx = e.currentTarget.dataset.idx;
    let dataList = this.data.dataList;
    this.setData({
      ['dataList[' + pidx + '][' + idx + '].zk']: !dataList[pidx][idx].zk
    })
  },
  rp_zk_sq(e){
    let pidx = e.currentTarget.dataset.pidx;
    let idx = e.currentTarget.dataset.idx;
    let dataList = this.data.dataList;
    this.setData({
      ['dataList[' + pidx+ '][' + idx +'].rp_zk']: !dataList[pidx][idx].rp_zk
    })
  },
  toChat(e) {
    let doctor_id = e.currentTarget.dataset.doctor_id;
    let price = e.currentTarget.dataset.price;
    let that = this;
    util.api('/api/wxapp/user/patient/get/default', function (res) {
      console.log(res)
      if (res.error == 0 && res.content.id) {
        let patientId = res.content.id;
        that.requestOrderStatus(0, patientId, doctor_id, price)
      } else {
        util.navigateTo({
          url: "pages1/getprescription/getprescription?list=1"
        })
      }
    }, {})
  },
  requestOrderStatus(depar_id, patientId, doctor_id, price) {
    let that = this;
    util.api('/api/wxapp/inquiry/order/undone/get', function (res) {
      console.log(res)
      if (res.error == 0 && res.content) {
        let {
          orderSn,
          orderStatus
        } = res.content;
        if (orderStatus == 1 || orderStatus == 2) {
          util.navigateTo({
            url: "/pages2/patientChat/patientChat?orderSn=" + orderSn
          })
        }
      } else {
        util.navigateTo({
          url: "/pages2/doctorPatientMessage/doctorPatientMessage?doctor_id=" + doctor_id + "&depar_id=" + depar_id + "&price=" + price
        })
      }
    }, {
      userId: util.getCache('user_id'),
      departmentId: depar_id,
      patientId: patientId,
      doctorId: doctor_id
    })
  },

})