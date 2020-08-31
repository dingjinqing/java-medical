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
    titleList: [
      {id:0,name:'综合'},
      {id:1,name:'医师级别'},
      {id:2,name:'评价'},
      {id:3,name:'响应时间'},
      {id:4,name:'接诊量'},
      {id:5,name:'关注数'},
    ],
    deparName: '科室',
    composName: '综合',
    deparId: 0,
    sortType: 0,
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
    if (options.id) {
      that.setData({
        deparId: options.id
      })
      deparId = options.id
    }
    if (options.name) {
      that.setData({
        deparName: options.name
      })
    }
    this.requestList('', deparId)
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
    let composName = that.data.composName;
    let deparId = that.data.deparId;
    let sortType = that.data.sortType;
    let keyword = that.data.keyword;
    if (choose_type == 'doctor') {
      composName = name;
      sortType = id;
    } else {
      deparName = name;
      deparId = id;
    }
    this.requestList(keyword, deparId, sortType)
    that.setData({
      composName: composName,
      deparName: deparName,
      sortType: sortType,
      deparId: deparId,
      can_show: false,
      choose_type: ''
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
    if (this.data.pageParams && this.data.pageParams.currentPage === this.data.pageParams.lastPage)
      return;
    this.setData({
      'pageParams.currentPage': this.data.pageParams.currentPage + 1
    });
    this.inputSearch(1);
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  changeInput(e) {
    this.setData({
      keyword: e.detail.value
    })
  },
  inputSearch(type = 0) {
    let keyword = this.data.keyword;
    let deparId = this.data.deparId;
    let sortType = this.data.sortType;
    this.requestList(keyword, deparId, sortType,type)
  },

  requestList: function (keyword = '', departmentId = 0, sortType = 0,type = 0) {
    let that = this;
    if ((departmentId !== 0 || sortType !== 0 || keyword !== '') && type == 0) that.setData({
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
      sortType:sortType
    }, '', true);
  }
})