// pages1/inviteduser/inviteduser.js
var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    search: {
      username: '',
      inviteUserStatus: 0, // 邀请用户类型(0有效,1将过期,2已失效)
      inviteType: 0, // 邀请关系(0全部,1直接,2间接)
      validityDate: 0, // 有效期(0全不选,1全部,2保护有效期,3返利有效期)
      distributorLevel: [], // 分销员等级
      sortField: '', // 排序条件(inviteTime邀请时间,orderNumber订单,totalFanliMoney返利佣金)
      sortType: 'desc', // 排序(asc升序,desc降序)
    },
    directType: 0, // 直接邀请(0未选择, 1选择)
    indirectType: 0, // 间接邀请(0未选择, 1选择)
    rebateDate: 0, // 返利期(0未选择, 1选择)
    protectDate: 0,// 保护期(0未选择, 1选择)

    page: 1,
    lastPage: 1,
    pageRows: 20,
    isLoad: 0,
    ifFliter: 0, // 筛选条件窗
    serverList: [], //列表数据
    distributorLevel: [] // 已启用分销员等级列表
    // distributorName: '', // 分销员统称
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    this.getLevelData();
    invite_request(that);
  },

  // 获取已启用分销员等级
  getLevelData () {
    var that = this
    util.api('/api/wxapp/distribution/distributor/level/list',function(res){
      if(res.error == 0) {
        var distributorLevel = []
        if (res.content && res.content.length > 0) {
          distributorLevel = res.content
          distributorLevel.forEach(item => {
            item.select = 0
          })
        }
        that.setData({ 
          distributorLevel: distributorLevel
        })
      }
    })
  },

  // 搜索条件
  changeUserName(e) {
    this.data.search.username = e.detail.value;
    invite_request(this);
  },

  // 切换筛选条件
  changeSearch(e) {
    var dataset = e.currentTarget.dataset;
    // 邀请关系
    if (dataset.field == 'directType' || dataset.field == 'indirectType') {
      if (dataset.field == 'directType') {
        this.setData({ directType: this.data.directType == 1 ? 0 : 1 })
      } else {
        this.setData({ indirectType: this.data.indirectType == 1 ? 0 : 1 })
      }
      if (this.data.directType == 1 && this.data.indirectType == 0) {
        this.data.search.inviteType = 1
      } else if (this.data.directType == 0 && this.data.indirectType == 1) {
        this.data.search.inviteType = 2
      } else {
        this.data.search.inviteType = 0
      }
    }
    // 有效期
    if (dataset.field == 'protectDate' || dataset.field == 'rebateDate') {
      if (dataset.field == 'protectDate') {
        this.setData({ protectDate: this.data.protectDate == 1 ? 0 : 1 })
      } else {
        this.setData({ rebateDate: this.data.rebateDate == 1 ? 0 : 1 })
      }
      if (this.data.protectDate == 1 && this.data.rebateDate == 0) {
        this.data.search.validityDate = 2
      } else if (this.data.protectDate == 0 && this.data.rebateDate == 1) {
        this.data.search.validityDate = 3
      } else if (this.data.protectDate == 1 && this.data.rebateDate == 1) {
        this.data.search.validityDate = 1
      } else {
        this.data.search.validityDate = 0
      }
    }
    // 排序
    let requestArray = ['inviteTime', 'orderNumber', 'totalFanliMoney'];
    if (requestArray.indexOf(dataset.field) != -1) {
      if (this.data.search.sortField == dataset.field) {
        this.data.search.sortType = this.data.search.sortType == 'asc' ? 'desc' : 'asc';
      } else {
        this.data.search.sortField = dataset.field;
        this.data.search.sortType = 'desc';
      }
      invite_request(this);
    }
    // 邀请用户类型
    if (dataset.field == 'inviteUserStatus') {
      if (this.data.search.inviteUserStatus == dataset.value) return false;
      this.data.search.inviteUserStatus = dataset.value;
      this.data.search.inviteType = 0;
      this.data.search.validityDate =0;

      this.setData({
        directType: 0,
        indirectType: 0,
        rebateDate: 0,
        protectDate: 0
      })
      invite_request(this);
    }
    // 分销员等级
    if (dataset.field == 'distributorLevel') {
      var levelValue = []
      this.data.distributorLevel.forEach(item => {
        if (item.levelId == dataset.value) {
          item.select = item.select == 0 ? 1 : 0
        }
        if (item.select == 1) {
          levelValue.push(item.levelId)
        }
      })
      this.data.search.distributorLevel = levelValue
      this.setData({ distributorLevel: this.data.distributorLevel })
    }
    this.setData({ search: this.data.search })
  },

  // 确定筛选
  bindSearch() {
    invite_request(this);
  },

  // 筛选条件窗
  showSearch() {
    this.setData({ ifFliter: this.data.ifFliter == 1 ? 0 : 1 })
  },

  // 重置筛选
  bindReset: function () {
    this.data.search.inviteType = 0;
    this.data.search.validityDate = 0;
    this.data.search.distributorLevel = [];
    this.data.distributorLevel.forEach(item => {
      item.select = 0
    })
    this.setData({
      search: this.data.search, 
      directType: 0,
      indirectType: 0,
      rebateDate: 0,
      protectDate: 0,
      distributorLevel: this.data.distributorLevel
    })
  },

  // 查看返利订单
  toOrder: function (e) {
    var userId = e.currentTarget.dataset.user_id
    util.navigateTo({
      url: '/pages/distributionorder/distributionorder?userId=' + userId,
    })
  },

  // 保护期/返利期提示
  show_message: function (e) {
    var protect_effect = e.currentTarget.dataset.protect_effect; // 保护期剩余
    var rebate_effect = e.currentTarget.dataset.rebate_effect; // 返利期剩余
    var tip_message;
    // var name = this.data.distributorName || '分销员'

    //不跳转
    // 保护期剩余三天的时候显示这个
    if (protect_effect > 0) {
      tip_message = protect_effect + "天后，您和该用户的邀请关系将不再受到保护，店铺所有分销员均可与该用户重新建立邀请关系";
      util.showModal('提示', tip_message);
    }
    // 返利器剩余 已过期的时候显示这个
    if (rebate_effect == 0) {
      tip_message = "该用户在店铺下单，不会返利到您的账户";
      util.showModal('提示', tip_message);
    }


    // 跳转
    // 保护期剩余已过期显示这个
    if (protect_effect == 0) {
      tip_message = "您和该用户的邀请关系不受保护，店铺所有分销员均可与该用户重新建立邀请关系";
      util.showModal('提示', tip_message);
    }
    // 返利器剩余n天
    if (rebate_effect > 0) {
      tip_message = rebate_effect + "天后，该用户在店铺下单，将不会返利到您的账户";
      util.showModal('提示', tip_message);
    }
  },

  // 失效提示
  show_nouse_message: function (e) {
    util.showModal('提示', "该用户在店铺下单，不会返利到您的账户");
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    return {
      path: this.route + '?inviteId=' + util.getCache('user_id')
    }
  },
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    var that = this;
    that.setData({ isLoad: 1 })
    if (that.data.page >= that.data.lastPage) {
      that.setData({ isLoad: 0 })
      return;
    }
    that.data.page = that.data.page + 1;

    var paramData = that.data.search
    paramData.currentPage = that.data.page
    paramData.pageRows = that.data.pageRows
    util.api('/api/wxapp/distribution/myInvite', function (rest) {
      if (res.error == 0) {
        var data = res.content;
        var serverList = []
        if (data.dataList && data.dataList.length > 0) {
          serverList = data.dataList
          serverList.forEach(item => {
            if (item.username.length > 8) {
              item.username = item.username.substr(0, 7) + "..."
            }
            if (!item.orderNumber) {
              item.orderNumber = 0
            }
            item.totalFanliMoney = parseFloat(item.totalFanliMoney || 0).toFixed(2);
            item.totalFanliMoney = parseFloat(item.totalFanliMoney || 0).toFixed(2);
            item.inviteTime = item.inviteTime.substr(0, 10)
          })
        }
        if (data.page) {
          var page = data.page.currentPage
          var lastPage = data.page.lastPage
        }
        that.setData({
          serverList: that.data.serverList.concat(serverList),
          page: page || 1,
          lastPage: lastPage || 1,
          isLoad: 0,
          ifFliter: 0
        })
      } else {
        util.showModal(res.message);
        return false
      }
    }, { ...paramData});
  }
})
function invite_request(that) {
  var paramData = that.data.search
  paramData.currentPage = that.data.page
  paramData.pageRows = that.data.pageRows
  util.api('/api/wxapp/distribution/myInvite', function (res) {
    if (res.error == 0) {
      var data = res.content;
      var serverList = []
      if (data.dataList && data.dataList.length > 0) {
        serverList = data.dataList
        serverList.forEach(item => {
          if (item.username.length > 8) {
            item.username = item.username.substr(0, 7) + "..."
          }
          if (!item.orderNumber) {
            item.orderNumber = 0
          }
          item.totalFanliMoney = parseFloat(item.totalFanliMoney || 0).toFixed(2);
          item.totalFanliMoney = parseFloat(item.totalFanliMoney || 0).toFixed(2);
          item.inviteTime = item.inviteTime.substr(0, 10)
        })
      }
      if (data.page) {
        var page = data.page.currentPage
        var lastPage = data.page.lastPage
      }
      that.setData({
        serverList: serverList,
        page: page || 1,
        lastPage: lastPage || 1,
        ifFliter: 0
      })
    } else {
      util.showModal(res.message);
      return false 
    }
  }, { ...paramData });
}
