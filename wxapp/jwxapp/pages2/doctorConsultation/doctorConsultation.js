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
    pageParams: null,
    dataList: null,
    patientId: 1,
    can_show:false,
    choose_type:'',
    search_data:[
      '主治医生',
      '主治医生',
      '主治医生'
    ],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
     this.requestList()
  },
  changeInput(e) {
    this.setData({
      keyWords: e.detail.value
    })
  },
  inputSearch() {
    // this.setData({
    //   'pageParams.currentPage': 1
    // })
    // 添加热词
    var data = this.data.keyWords.replace(/\s/g, "");
  },
  choose_type(e){
    let that = this;
    let type = e.currentTarget.dataset.type;
    let choose_type = that.data.choose_type;
    let can_show = false;
    if(choose_type == '' || choose_type != type){
      choose_type = type;
      can_show = true;
    }else{
      choose_type = ''
    }
    that.setData({
      can_show:can_show,
      choose_type:choose_type
    })
  },
  hide_cover(){
    let that = this;
    that.setData({
      can_show:false,
      choose_type:''
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

  requestList:function(){
    // let currentPage = this.data.pageParams ? this.data.pageParams.currentPage : 1;
    util.api('/api/wxapp/recommend/doctor/list', (res) => {
      console.log(res)
      if (res.error === 0) {
        // let dataList = this.formatData(res.content.dataList);
        // this.setData({
        //   pageParams: res.content.page,
        //   ['dataList[' + (parseInt(currentPage) - 1) + ']']: dataList
        // })
      }
    }, {
        userId: util.getCache("user_id"),
        patierntId:2
      });
  }
})