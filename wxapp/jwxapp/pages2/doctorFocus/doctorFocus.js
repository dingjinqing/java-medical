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
    search_data: [],
    departmentList: [],
    doctorList: [],
    titleList: [],
    deparName: '科室',
    doctorName: '职称',
    deparId: 0,
    doctorId: 0,
    keyword: '',
    pageParams: {
      currentPage: 1,
      pageRows: 20
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this;
    let deparId = 0;
    this.requestList('', deparId)
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
    if (this.data.pageParams && this.data.pageParams.currentPage === this.data.pageParams.lastPage)
      return;
    this.setData({
      'pageParams.currentPage': this.data.pageParams.currentPage + 1
    });
    this.requestList();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  // changeInput(e) {
  //   this.setData({
  //     keyword: e.detail.value
  //   })
  // },
  // inputSearch() {
  //   let keyword = this.data.keyword;
  //   let deparId = this.data.deparId;
  //   let doctorId = this.data.doctorId;
  //   this.requestList(keyword, deparId, doctorId)
  // },

  requestList: function (keyword = '', departmentId = 0, titleId = 0) {
    let that = this;
    if (departmentId !== 0 || titleId !== 0 || keyword !== '') that.setData({
      'pageParams.currentPage': 1
    })
    let currentPage = that.data.pageParams ? that.data.pageParams.currentPage : 1;
    util.api('/api/wxapp/doctor/list', (res) => {
      console.log(res)
      if (res.error === 0) {
        let con = res.content
        if (that.data.pageParams.currentPage === 1) {
          that.setData({
            doctorList: [
              [...con.doctorList.dataList]
            ],
            departmentList: con.departmentList,
            titleList: con.titleList
          })
        } else {
          that.setData({
            ['doctorList[' + (parseInt(currentPage) - 1) + ']']: con.doctorList.dataList
          })
        }
        that.setData({
          pageParams: con.doctorList.page
        })
      }
    }, {
      ...that.data.pageParams,
      keyword: keyword,
      departmentId: departmentId,
      titleId: titleId,
      type:1,
      userId:util.getCache('user_id')
    }, '', true);
  }
})