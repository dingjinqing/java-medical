// pages/lottery/lottery.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var is_award;
var not_award;
var final_index;
var is_meng = 0;
var lottery_info = [];
var award_grade = [];
var lottery_id;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    is_award:0,
    not_award:0,
    is_meng:0,
    lottery_info:[],
    award_grade:[],
    is_disable:false,
    last_index: 0,//上一回滚动的位置
    final_index: -1,//轮盘的当前滚动位置
    roll_flag: true,//是否允许滚动
    max_number: 9,//轮盘的全部数量
    speed: 100,//速度，速度值越大，则越慢 初始化为300
    finalindex: 0,//最终的奖励
    myInterval: "",//定时器
    max_speed: 40,//滚盘的最大速度
    minturns: 2,//最小的圈数为2
    runs_now: -1,//当前已跑步数
    lottery_source: 0,
    nickName: util.getCache('nickName'),
    click_num: false,
    lottery_act_id:0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    lottery_id = options.lottery_id;
    if (options.lottery_source) this.data.lottery_source = options.lottery_source;
    if (options.act_id) this.data.lottery_act_id = options.act_id;
    lottery_request(that);
    var user_name = util.getCache('nickName');
    var user_avatar = util.getCache('avatarUrl');
    if (!user_name || user_name == '用户' + parseInt(util.getCache('user_id') + 10000)
      || user_name == util.getCache('openid') || !user_avatar
      || user_avatar.indexOf('image/admin/head_icon.png') > -1) {
      that.setData({
        getsq: false,
      })
    } else {
      that.setData({
        getsq: true,
      })
    }
  },
  to_index:function(){
    util.redirectTo({
      url: '/pages/index/index',
    })
  },

  //开始滚动
  startrolling: function (e) {
    var that = this;
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    //初始化步数
    that.data.runs_now = -1;
    this.data.final_index = -1;
    this.setData({
      is_meng:1
    })
    //free抽奖
    if (lottery_info.can_lottery.status == 0 ){
      var lottery_type = "free"
    } else if (lottery_info.can_lottery.status == 5){
      var lottery_type = "share"
    } else if (lottery_info.can_lottery.status == 7) {
      var lottery_type = "score"
    }
    util.api('/api/wxapp/lottery/draw', function (res) {
      award_grade = res.content;
      if(res.error == 0){
        var final_area = res.content.stop_number;
        //当前可以点击的状态下
        if (that.data.roll_flag) {
          that.data.roll_flag = false;
          //启动滚盘，注，若是最终后台无返回就不好意思里
          that.setData({
            is_disable: true,
            award_grade: award_grade
          })
          that.rolling(that.data.final_index, final_area);

        }
      } else {
        util.showModal('提示', res.content, function () {
          lottery_request(that);
        });
      }
    }, {   lottery_id: lottery_id, lottery_type: lottery_type, lottery_source: that.data.lottery_source, act_id: that.data.lottery_act_id,form_id:form_id,open_id:open_id})

  },
  //滚动轮盘的动画效果
  rolling: function (final_index,final_area) {
    var that = this;
    // this.data.final_index = 0;
    this.data.myInterval = setTimeout(function () { that.rolling(final_index, final_area); }, this.data.speed);
    this.data.runs_now++;//已经跑步数加一
    this.data.final_index++;//当前的加一
    //获取总步数，接口延迟问题，所以最后还是设置成1s以上
    var count_num = this.data.minturns * this.data.max_number + final_area + 1;
    //抽奖结束
     if (this.data.runs_now == count_num) {
       this.data.final_index = final_area;
      clearInterval(this.data.myInterval);
      this.data.roll_flag = true;
       if (final_area == 1) {
         that.setData({
           not_award: 1
         })
       }else{
         that.setData({
           is_award: 1
         })
       }
    }
    if (this.data.final_index > this.data.max_number) {//判定！是否大于最大数
      this.data.final_index = 0;
    }
    this.setData(this.data);

  },
  to_list:function(){
    util.navigateTo({
      url: '/pages/lotteryrule/lotteryrule?lottery_id=' + lottery_id,
    })
  },
  refresh_page:function(){
    var that = this;
    that.setData({
      not_award: 0
    })
    lottery_request(that);
  },
  refresh_pages: function () {
    var that = this;
    that.setData({
      is_award: 0
    })
    lottery_request(that);
  },
  guandiao:function(){
    var that = this;
    that.setData({
      not_award: 0
    })
    lottery_request(that);
  },
  getUserInfo: function (e) {
    this.setData({
      show_user_modal: 0
    });
    var that = this;
    var canIUse = wx.canIUse('button.open-type.getUserInfo');
    if (e.detail.userInfo) {
      if (canIUse) {
        var user_avatar = e.detail.userInfo.avatarUrl;
        var user_name = e.detail.userInfo.nickName;
        util.setCache("nickName", user_name);
        util.setCache("avatarUrl", user_avatar);
        util.api('/api/wxapp/account/updateUser', function (res) {
        }, {

            username: user_name,
            user_avatar: user_avatar
          });
      } else {
        wx.getUserInfo({
          success: res => {
            var user_avatar = e.detail.userInfo.avatarUrl;
            var user_name = e.detail.userInfo.nickName;
            util.setCache("nickName", user_name);
            util.setCache("avatarUrl", user_avatar);
            util.api('/api/wxapp/account/updateUser', function (res) {
            }, {

                username: user_name,
                user_avatar: user_avatar
              });
          }
        })
      }
      that.setData({
        nickName: user_name,
      })
    }
    that.startrolling(e);
    that.setData({
      click_num: true,
    })
  },
  guandiao1: function () {
    var that = this;
    that.setData({
      is_award: 0
    })
    lottery_request(that);
  },
  // 立即领取赠品
  to_get_gift:function(e){
    var params = {};
    params.goods_id = award_grade.goods_id;
    params.goods_price = 0;
    params.user_id = util.getCache('user_id');
    params.goods_number = 1;
    params.prd_sn = award_grade.prd_sn;
    params.prd_id = award_grade.prd_id;
    params.product_id = award_grade.prd_id;
    params.lc_id = award_grade.lottery_id;
    var query_param = {};
    query_param.goods_id = award_grade.goods_id;
    console.log(JSON.stringify(params));
    util.navigateTo({
      url: '/pages/goodsCheckout/goodsCheckout?order_type=lottery_present&choose_list='+JSON.stringify(params)+'&query_param='+JSON.stringify(query_param),
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

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    var that = this;
    var username = util.getCache('nickName');
    if(username == "" || username == null){
      username = "神秘的小伙伴"
    }
    util.api('/api/wxapp/lottery/share', function (res) {
      if (res.content > 0) {
        lottery_request(that);
      }
    }, { lottery_id: lottery_id })

    util.api("/api/wxapp/share/record", function (d) {

    }, { activity_id: lottery_id, activity_type: 7 });
    return {
      path: '/pages/lottery/lottery?invite_id=' + util.getCache('user_id') + "&lottery_id=" + lottery_id + '&invite_source=lottery&invite_act_id=' + lottery_id,
      title: username + '邀你免费拿大奖，限时免费立即参加吧！！！',
      imageUrl: imageUrl + "/image/wxapp/share_lott1.jpg"
    }
}
})
function lottery_request(that){
  util.api('/api/wxapp/lottery/info', function (res) {
    if (res.error == 0) {
      lottery_info = res.content;
      if (lottery_info.can_lottery.status == 1) {
        util.showModal('提示', '该活动不存在', function () {
          wx.navigateBack({})
        });
        return false;
      }
      if (lottery_info.lottery_explain != null) {
        var goods_descs = lottery_info.lottery_explain;
		    goods_descs = util.filterRichText(goods_descs);
      }
      if (lottery_info.goods_name && lottery_info.goods_name !=''){
        // lottery_info.goods_name = JSON.parse(lottery_info.goods_name);
        console.log(lottery_info.goods_name[1])
      }
      that.setData({
        lottery_info: lottery_info,
        is_disable: false,
      })
    }else{
      util.showModal('提示', res.content, function () {
        wx.navigateBack({})
      });
    }

  }, {   lottery_id: lottery_id });
}

