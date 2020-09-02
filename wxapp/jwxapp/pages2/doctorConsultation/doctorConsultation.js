// pages1/prescriptionlist/prescriptionlist.wxml.js
var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    tabIndex: 'doctor',
    dataList: null,
    patientId: 1,
    can_show: false,
    choose_type: '',
    search_data: [
      '主治医生',
      '主治医生',
      '主治医生'
    ],
    departmentList: [],
    doctorList: [],
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
      tab
    } = options
    this.requestList()
    if (tab) {
      this.setData({
        tabIndex: 'myChat'
      })
      this.requestSessionList()
    }
  },

  choose_type(e) {
    let that = this;
    let type = e.currentTarget.dataset.type;
    let choose_type = that.data.choose_type;
    let can_show = false;
    if (choose_type == '' || choose_type != type) {
      choose_type = type;
      can_show = true;
    } else {
      choose_type = ''
    }
    that.setData({
      can_show: can_show,
      choose_type: choose_type
    })
  },
  hide_cover() {
    let that = this;
    that.setData({
      can_show: false,
      choose_type: ''
    })
  },
  handleChangeNav(e) {
    let id = e.currentTarget.dataset.id
    this.setData({
      tabIndex: id
    })
    if (id == 'myChat') {
      this.requestSessionList()
    }
  },
  toDoctorSearch() {
    util.navigateTo({
      url: "/pages2/doctorSearch/doctorSearch"
    })
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
    if (this.data.tabIndex == 'doctor' || (
        this.data.pageParams &&
        this.data.pageParams.currentPage === this.data.pageParams.lastPage)) return;

    this.setData({
      'pageParams.currentPage': this.data.pageParams.currentPage + 1
    });
    this.requestSessionList();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  toDepartment: function (e) {
    let id = e.currentTarget.dataset.id;
    let name = e.currentTarget.dataset.name;
    util.navigateTo({
      url: "/pages2/doctorSearch/doctorSearch?id=" + id + '&name=' + name
    })
  },
  toAllDepartment: function () {
    util.navigateTo({
      url: "/pages2/allDepartment/allDepartment"
    })
  },

  requestList: function () {
    let that = this;
    util.api('/api/wxapp/recommend/doctor/list', (res) => {
      console.log(res)
      if (res.error === 0) {
        let con = res.content;
        that.setData({
          departmentList: con.recommendDepartment,
          ['doctorList[0]']: con.doctorList
        })
      }
    }, {
      userId: util.getCache("user_id"),
      patierntId: 2
    });
  },
  requestSessionList() {
    let currentPage = this.data.pageParams ? this.data.pageParams.currentPage : 1;
    util.api('/api/wxapp/im/session/page/list', res => {
      console.log(res)
      if (res.error === 0) {
        if (this.data.pageParams.currentPage === 1) {
          this.setData({
            dataList: [
              [...res.content.dataList]
            ]
          })
        } else {
          this.setData({
            ['dataList[' + (parseInt(currentPage) - 1) + ']']: res.content.dataList
          })
        }
        this.setData({
          pageParams: res.content.page
        })
      }
    }, {
      ...this.data.pageParams,
      userId:util.getCache('user_id')
    })
  },
  toChat(e) {
    let status = e.currentTarget.dataset.status;
    let orderSn = e.currentTarget.dataset.orderSn;
      util.navigateTo({
        url: "/pages2/patientChat/patientChat?orderSn=" + orderSn + "&sessionStatus=" + status
      })
  },
  toComment(e){
    let orderSn = e.currentTarget.dataset.orderSn;
    let patientId = e.currentTarget.dataset.patientId;
    let doctorId = e.currentTarget.dataset.doctorId;
    let imSessionId = e.currentTarget.dataset.sessionId;
    util.jumpLink(
      `pages2/doctorComment/doctorComment${util.getUrlParams({
      orderSn:orderSn,
      patientId: patientId,
      doctorId:doctorId,
      imSessionId:imSessionId
    })}`,
      'navigateTo'
    )
  }
})