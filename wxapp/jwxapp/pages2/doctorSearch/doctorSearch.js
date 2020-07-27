var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    tabIndex: 'doctor',
    pageParams: null,
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
    deparId:0,
    doctorId:0,
    keyword:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this;
    this.requestList('', options.id)
    if (options.name) {
      that.setData({
        deparName: options.name
      })
    }
  },
  choose_type(e) {
    let that = this;
    let type = e.currentTarget.dataset.type;
    let choose_type = that.data.choose_type;
    let can_show = false;
    let search_data = [];
    if (choose_type == '' || choose_type != type) {
      choose_type = type;
      if (choose_type == 'doctor') {
        search_data = that.data.titleList;
      } else {
        search_data = that.data.departmentList;
      }
      can_show = true;
      that.setData({
        search_data: search_data
      })
    } else {
      choose_type = ''
    }
    that.setData({
      can_show: can_show,
      choose_type: choose_type
    })
  },
  choose_item: function (e) {
    let that = this;
    let id = e.currentTarget.dataset.id;
    let name = e.currentTarget.dataset.name;
    let choose_type = that.data.choose_type;
    let deparName = that.data.deparName;
    let doctorName = that.data.doctorName;
    let deparId = that.data.deparId;
    let doctorId = that.data.doctorId;
    let keyword = that.data.keyword;
    if(choose_type == 'doctor'){
      doctorName = name;
      doctorId = id;
    }else{
      deparName = name;
      deparId = id;
    }
    this.requestList(keyword,deparId,doctorId)
    that.setData({
      doctorName:doctorName,
      deparName:deparName,
      doctorId:doctorId,
      deparId:deparId,
      can_show:false,
      choose_type:''
    })
  },
  hide_cover() {
    let that = this;
    that.setData({
      can_show: false,
      choose_type: ''
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
    // if (this.data.pageParams && this.data.pageParams.currentPage === this.data.pageParams.lastPage)
    //   return;
    // this.setData({
    //   'pageParams.currentPage': this.data.pageParams.currentPage + 1
    // });
    // this.requestList();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  changeInput (e) {
    this.setData({
      keyword: e.detail.value
    })
  },
  inputSearch () {
    let keyword = this.data.keyword;
    let deparId = this.data.deparId;
    let doctorId = this.data.doctorId;
    this.requestList(keyword,deparId,doctorId)
  },

  requestList: function (keyword = '', departmentId = 0, titleId = 0) {
    let that = this;
    // let currentPage = this.data.pageParams ? this.data.pageParams.currentPage : 1;
    util.api('/api/wxapp/doctor/list', (res) => {
      console.log(res)
      if (res.error === 0) {
        let con = res.content;
        that.setData({
          departmentList: con.departmentList,
          doctorList: con.doctorList,
          titleList: con.titleList
        })

      }
    }, {
      keyword: keyword,
      departmentId: departmentId,
      titleId: titleId
    });
  }
})