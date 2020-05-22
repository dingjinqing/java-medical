// pages1/presentinfo/presentinfo.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var gift_id;
var prd_id;
var prd_num;
var gift_info = [];
var choosed_play_mode;
var flag = 0;
var save_word = '';
var give_type = 1;
var choosed_time = "请选择开奖时间";
var choosed_date = "请选择开奖日期";
var if_time = 0;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    if_play_mode:0,
    gift_info:[],
    choosed_play_mode:"",
    flag:0,
    save_word:'',
    give_type:1,
    choosed_time:"请选择开奖时间",
    choosed_date:"请选择开奖日期",
    if_time:0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    console.log(options);
    gift_id = options.gift_id;
    prd_id = options.product_id;
    prd_num = options.product_num;
    let now_time = util.formatTime(new Date());
    save_word = "";
    // 当前日期
    let now_dates = now_time.substr(0, 10);
    // 当前时间
    now_time = now_time.substr(11,5);
    

    let day_after = Date.parse(util.formatTime(new Date()));
    day_after = day_after + 24*60*60*1000;
    day_after = util.formatTime(new Date(day_after));
    // 24小时之后的日期
    let end_dates = day_after.substr(0, 10);
    // 24小时之后的时间
    day_after = day_after.substr(11,5);
    
    console.log(end_dates);
    that.setData({
      now_time: now_time,
      one_day_time:'',
      now_dates: now_dates,
      end_dates: end_dates
    })
    gift_request(that);
  },
  // 活动规则
  toRule:function(){
    util.jumpToWeb("/wxapp/present/rule")
  },
  // 更多玩法
  more_act_type:function(){
    this.setData({
      if_play_mode:1
    })
  },
  // 更多玩法的取消
  btn_cancel_act:function(){
    this.setData({
      if_play_mode: 0
    })
  },
  // 选择玩法
  choose_play_mode:function(e){
    var play_mode = e.currentTarget.dataset.paly;
    if(play_mode == 0){
      choosed_play_mode = "直接送礼",
      give_type = 1;
      if_time = 0;
    }else if(play_mode == 1){
      choosed_play_mode = "先到先得",
      give_type = 2;
      if_time = 0;
    }else{
      choosed_play_mode = "定时开奖"
      give_type = 3;
      if_time = 1;
    }
    this.setData({
      choosed_play_mode: choosed_play_mode,
      if_play_mode: 0,
      if_time: if_time
    })
  },
  // 继续添加
  go_on_add_gift:function(){
    util.jumpLink('/pages1/presentchoose/presentchoose?gift_id=' + gift_id);
  },
  // 查看全部
  to_look_all:function(){
    var that = this;
    if(flag == 0){
      flag = 1;
      this.setData({
        flag: 1
      })
    }else{
      flag = 0;
      this.setData({
        flag: 0
      })
    }
    prd_id = "";
    prd_num = "";
    gift_request(that)
  },
  // 留言
  changeMsg:function(e){
    save_word = e.detail.value;
  },
  // 生成礼单
  fill_order:function(e){
    var that = this;
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    if (give_type == 3){
      var draw_time = choosed_date + " " + choosed_time;
    }else{
      var draw_time = ''
    }
    util.api('/api/wxapp/givegift/generate', function (res) {
      if (res.error == 0) {
        util.jumpLink("/pages/goodsCheckout/goodsCheckout?order_type=give_gift&gift_id=" + gift_id);
      } else {
        util.showModal('提示', res.message);
        return false
      }
    }, { gift_id: gift_id, message: save_word, gift_type: give_type, draw_time: draw_time, form_id: form_id, open_id: open_id})
  },
  // 减数量
  goods_jian:function(e){
    let goods_id = e.currentTarget.dataset.goods_id;
    let prd_id = e.currentTarget.dataset.prd_id;
    let prd_num = e.currentTarget.dataset.prd_num;
    let index_this = e.currentTarget.dataset.index;
    let that = this;
    gift_info.goods_list[index_this].goods_number = parseFloat(gift_info.goods_list[index_this].goods_number) - 1;
    let now_gm = gift_info.goods_list[index_this].goods_number;
    if (gift_info.goods_list[index_this].goods_number < 0){
      gift_info.goods_list[index_this].if_min = 1;
      gift_info.goods_list[index_this].goods_number = 0;
      now_gm = 0;
    }
    that.setData({
      gift_info: gift_info
    })
    var choose_list = {};
    choose_list.goods_id = goods_id;
    choose_list.prd_id = prd_id;
    choose_list.now_gm = now_gm;
    this.addRequest(that, choose_list);
  },
  // 加数量
  goods_add:function(e){
    let goods_id = e.currentTarget.dataset.goods_id;
    let prd_id = e.currentTarget.dataset.prd_id;
    let prd_num = e.currentTarget.dataset.prd_num;
    let index_this = e.currentTarget.dataset.index;
    let that = this;
    gift_info.goods_list[index_this].goods_number = parseFloat(gift_info.goods_list[index_this].goods_number) + 1;
    let now_gm = gift_info.goods_list[index_this].goods_number;
    if (gift_info.goods_list[index_this].goods_number > 0) {
      gift_info.goods_list[index_this].if_min = 0;
    }
    if (gift_info.goods_list[index_this].goods_number > gift_info.goods_list[index_this].prd_number){
      util.showModal("提示", '库存不足');
      gift_info.goods_list[index_this].if_max = 1;
      gift_info.goods_list[index_this].goods_number = gift_info.goods_list[index_this].prd_number;
      now_gm = gift_info.goods_list[index_this].prd_number;
    }
    that.setData({
      gift_info: gift_info
    })
    var choose_list = {};
    choose_list.goods_id = goods_id;
    choose_list.prd_id = prd_id;
    choose_list.now_gm = now_gm;
    this.addRequest(that, choose_list)
  },
  // 修改数量
  goods_change:function(e){
    let goods_id = e.currentTarget.dataset.goods_id;
    let prd_id = e.currentTarget.dataset.prd_id;
    let prd_num = e.currentTarget.dataset.prd_num;
    let index_this = e.currentTarget.dataset.index;
    let that = this;
    let input_num = e.detail.value;
    gift_info.goods_list[index_this].goods_number = input_num;
    let now_gm = gift_info.goods_list[index_this].goods_number;
    if (gift_info.goods_list[index_this].goods_number < 0) {
      gift_info.goods_list[index_this].if_min = 1;
      gift_info.goods_list[index_this].goods_number = 0;
      now_gm = 0;
    }
    if (gift_info.goods_list[index_this].goods_number > 1){
      gift_info.goods_list[index_this].if_min = 0;
    }
    if (gift_info.goods_list[index_this].goods_number > gift_info.goods_list[index_this].prd_number) {
      util.showModal("提示", '库存不足');
      gift_info.goods_list[index_this].if_max = 1;
      gift_info.goods_list[index_this].goods_number = gift_info.goods_list[index_this].prd_number;
      now_gm = gift_info.goods_list[index_this].prd_number;
      gift_info.goods_list[index_this].if_min = 0;
    }
    that.setData({
      gift_info: gift_info
    })
    var choose_list = {};
    choose_list.goods_id = goods_id;
    choose_list.prd_id = prd_id;
    choose_list.now_gm = now_gm;
    this.addRequest(that, choose_list)
  },
  addRequest(that, choose_list){
    util.api("/api/wxapp/givegift/add", function (res) {
      if (res.error == 0) {
        prd_id = "";
        prd_num = "";
        gift_request(that);
      } else {
        util.showModal('提示', res.message);
        return false
      }
    }, { gift_id: gift_id, goods_id: choose_list.goods_id, product_id: choose_list.prd_id, prd_number: choose_list.now_gm })
  },
  // 选择日期
  change_open_date:function(e){
    choosed_date = e.detail.value;
    var now_days = util.formatTime(new Date());
    now_days = now_days.substr(0, 10);
    if (choosed_date != now_days){
      this.setData({
        now_time:""
      })
    }
    this.setData({
      choosed_date: choosed_date
    })
  },
  // 选择时间
  change_open_time:function(e){
    if (choosed_date == "请选择开奖日期"){
      util.showModal("提示","请先选择开奖日期");
      return false;
    }
    choosed_time = e.detail.value;
    this.setData({
      choosed_time: choosed_time
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

  }
})
function gift_request(that){
  util.api('/api/wxapp/givegift/cartlist', function (res) {
    if(res.error == 0){
      gift_info = res.content;
      for(var i=0;i<gift_info.goods_list.length;i++){
        if (gift_info.goods_list[i].goods_number == 0){
          gift_info.goods_list[i].if_min = 1
        }else{
          gift_info.goods_list[i].if_min = 0
        }

        if (parseInt(gift_info.goods_list[i].goods_number) >= gift_info.goods_list[i].prd_number){
          gift_info.goods_list[i].is_max = 1;
          gift_info.goods_list[i].goods_number = gift_info.goods_list[i].prd_number
        }else{
          gift_info.goods_list[i].is_max = 0
        }
      }
      if (gift_info.gift_info.act_type_direct_giving == 1){
        choosed_play_mode = "直接送礼"
        give_type = 1;
      } else if (gift_info.gift_info.act_type_first_served == 1){
        choosed_play_mode = "先到先得";
        give_type = 2;
      }else{
        choosed_play_mode = "定时开奖";
        give_type = 3;
        if_time = 1;
        that.setData({
          if_time:if_time
        })
      }
      that.setData({
        gift_info: gift_info,
        user_name: util.getCache('nickName'),
        user_img: util.getCache('avatarUrl'),
        choosed_play_mode: choosed_play_mode
      })
    }else{
      util.showModal('提示', res.message, function () {
        util.jumpLink("/pages/index/index", 'redirectTo')
      }, false);
      return false
    }
  },{gift_id:gift_id,product_id:prd_id,product_number:prd_num})
}