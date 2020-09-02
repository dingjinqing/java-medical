// pages2/allDepartment/allDepartment.js
var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: util.getImageUrl(""),
    departmentList:[],
    keyword:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
     let {source = null,doctorId = null} = options
     this.setData({
      source,
      doctorId
     })
     this.requestList()
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

  },
  toDoctorSearch:function(e){
    let id = e.currentTarget.dataset.id;
    let name = e.currentTarget.dataset.name;
    let code = e.currentTarget.dataset.code;
    if(this.data.source === 'prescribe'){
      let pageList = getCurrentPages();
      let prevPage = pageList[pageList.length - 2];
      prevPage.setData({
        departmentCode:code,
        departmentName:name
      })
      wx.navigateBack()
    } else {
      util.navigateTo({
        url: "/pages2/doctorSearch/doctorSearch?id=" + id + '&name=' + name 
      })
    }
  },
  changeInput (e) {
    this.setData({
      keyword: e.detail.value
    })
  },
  inputSearch () {
    let keyword = this.data.keyword;
    this.requestList(keyword)
  },
  requestList:function(keyword = ''){
    let that = this;
    // let currentPage = this.data.pageParams ? this.data.pageParams.currentPage : 1;
    util.api('/api/wxapp/department/list', (res) => {
      console.log(res)
      if (res.error === 0) {
         that.setData({
          departmentList:res.content
         })
      }
    }, {
      keyword: keyword,
      doctorId: this.data.doctorId
      });
  }
})