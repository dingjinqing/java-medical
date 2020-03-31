// pages1/inviteduser/inviteduser.js
var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    page: 1,
    last_page: 1,
    server_list: [], //列表数据
    is_load: 0,
    if_fliter: 0, // 筛选条件
    search: {
      tab_type: 0, //0有效1将过期2已失效
      user_name: '',
      invite_type: 0, //0全部1直接2间接
      indirect_type: 0, //0全部1直接2间接
      rebate_date: 0, //0默认倒序1邀请10天
      protect_date: 0,//0默认倒序1保护10天
      sort_field: '', //0默认倒序1正序
      sort_type: 0,
      distributor_level: []
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    this.getSeachData();
    invite_request(that);
  },
  show_nouse_message: function (e) {
    var noue_tip;
    var in_type = e.currentTarget.dataset.invite_name;
    // 有邀请人
    noue_tip = "该用户在店铺下单，不会返利到您的账户";
    util.showModal('提示', noue_tip);
  },
  show_message: function (e) {
    // 保护期剩余
    var protect_effect = e.currentTarget.dataset.protect_effect;
    // 返利期剩余
    var rebate_effect = e.currentTarget.dataset.rebate_effect;
    var tip_message;

    //不跳转
    // 保护期剩余三天的时候显示这个
    if (protect_effect > 0) {
      tip_message = protect_effect + "天后，您和该用户的邀请关系将不再受到保护，店铺所有" + this.data.distributor_name + "均可与该用户重新建立邀请关系";
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
      tip_message = "您和该用户的邀请关系不受保护，店铺所有" + this.data.distributor_name + "均可与该用户重新建立邀请关系";
      util.showModal('提示', tip_message);
    }
    // 返利器剩余n天
    if (rebate_effect > 0) {
      tip_message = rebate_effect + "天后，该用户在店铺下单，将不会返利到您的账户";
      util.showModal('提示', tip_message);
    }

  },

  // 查看返利订单
  toOrder: function (e) {
    var usernames = e.currentTarget.dataset.username;
    var userdd = e.currentTarget.dataset.userid
    util.navigateTo({
      // url: '/pages/distributionorder/distributionorder?user_id=' + userdd + "&username=" + usernames,
    })
  },

  // 条件排序
  changeSearch(e) {
    var dataset = e.currentTarget.dataset;
    let changeArray = ['rebate_date', 'protect_date', 'invite_type', 'indirect_type'];
    if (changeArray.indexOf(dataset.field) != -1) {
      this.data.search[dataset.field] = this.data.search[dataset.field] == 1 ? 0 : 1;
    }
    let requestArray = ['invite_date', 'order_number', 'rebate_money'];
    if (requestArray.indexOf(dataset.field) != -1) {
      if (this.data.search.sort_field == dataset.field) {
        this.data.search.sort_type = this.data.search.sort_type == 1 ? 0 : 1;
      } else {
        this.data.search.sort_field = dataset.field;
        this.data.search.sort_type = 0;
      }
      invite_request(this);
    }
    if (dataset.field == 'tab_type') {
      if (this.data.search.tab_type == dataset.value) return false;
      this.data.search.tab_type = dataset.value;
      for (var index in changeArray) {
        this.data.search[changeArray[index]] = 0;
      }
      invite_request(this);
    }
    if (dataset.field == 'distributor_level') {
      var has_index = this.data.search.distributor_level.indexOf(dataset.value)
      if (has_index > -1) {
        this.data.search.distributor_level.splice(has_index, 1);
        this.data.distributor_level[dataset.value].select = 0;
      } else {
        this.data.search.distributor_level.push(dataset.value);
        this.data.distributor_level[dataset.value].select = 1;
      }
      this.setData({ distributor_level: this.data.distributor_level })
    }
    this.setData({ search: this.data.search })
  },

  // 搜索
  changeUserName(e) {
    this.data.search.user_name = e.detail.value;
    invite_request(this);
  },

  // 筛选条件展示
  showSearch() {
    this.setData({ if_fliter: this.data.if_fliter == 1 ? 0 : 1 })
  },

  // 确定筛选条件搜索
  bindSearch() {
    invite_request(this);
  },

  // 重置筛选条件
  bindReset: function () {
    this.data.search.invite_type = 0;
    this.data.search.indirect_type = 0;
    this.data.search.rebate_date = 0;
    this.data.search.protect_date = 0;
    this.data.search.distributor_level = [];
    for (var index in this.data.distributor_level) {
      this.data.distributor_level[index].select = 0;
    }
    this.setData({
      search: this.data.search, distributor_level: this.data.distributor_level
    })
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
      path: this.route + '?invite_id=' + util.getCache('user_id')
    }
  },
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    var that = this;
    that.setData({
      is_load: 1
    })
    if (that.data.page >= that.data.last_page) {
      that.setData({
        is_load: 0
      })
      return;
    }
    that.data.page = that.data.page + 1;
    util.api('api/wxapp/distribution/myInvite', function (rest) {
      if (res.error == 0) {
        var server_list = [];
        if (res.content.dataList.length > 0) {
          server_list = res.content.dataList
        }
        server_list.forEach(item => {
          if (item.username.length > 8) {
            item.username = item.username.substr(0, 7) + "..."
          }
          if (item.total_fanli_money) {
            item.total_fanli_money = parseFloat(item.total_fanli_money).toFixed(2);
          } else {
            item.total_fanli_money = 0.00
          }
          if (!item.order_number) {
            item.order_number = 0
          }
          item.invite_time = item.invite_time.substr(0, 10)
        })
        that.setData({
          server_list: that.data.server_list.concat(server_list),
          page: res.content.page.currentPage,
          last_page: res.content.page.lastPage,
          is_load: 0,
          if_fliter: 0
        });
      }
      
    }, { 
      userId: util.getCache('user_id'),
      username: util.getCache('nickName'),
      pageNo: that.data.page, 
      searchData: JSON.stringify(that.data.search) 
    });
  },

  getSeachData() {
    var that = this;
    // util.api('/api/wxapp/rebate/distributor/level', function (res) {
    //   if (res.error == 0) {
    //     if (res.content.distributor_level) {
    //       that.setData({
    //         distributor_level: res.content.distributor_level,
    //         distributor_name: res.content.distributor_name
    //       })
    //     }
    //   }
    // })
  }
})
function invite_request(that) {
  util.api('api/wxapp/distribution/myInvite', function (res) {
    if (res.error == 0) {
      var server_list = [];
      if (res.content.dataList.length > 0) {
        server_list = res.content.dataList
      }
      server_list.forEach(item => {
        if (item.username.length > 8) {
          item.username = item.username.substr(0, 7) + "..."
        }
        if (item.total_fanli_money) {
          item.total_fanli_money = parseFloat(item.total_fanli_money).toFixed(2);
        } else {
          item.total_fanli_money = 0.00
        }
        if (!item.order_number) {
          item.order_number = 0
        }
        item.invite_time = item.invite_time.substr(0, 10)
      })
      that.setData({
        server_list: server_list,
        page: res.content.page.currentPage,
        last_page: res.content.page.lastPage,
        if_fliter: 0
      });
    }
  }, { 
      userId: util.getCache('user_id'),
      username: util.getCache('nickName'),
      pageNo: that.data.page,
      searchData: JSON.stringify(that.data.search) 
  });
}
