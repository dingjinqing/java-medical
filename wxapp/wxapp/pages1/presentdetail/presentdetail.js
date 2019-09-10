var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var order_sn;
var gift_info = [];
var rest_num;
var pictorial;
var time_diff;
var total_micro_second;
var set_time_out;
var if_expire = 0;
var posterBase64 = '';
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    baseUrl: app.globalData.baseUrl,
    is_share:0,
    order_sn:"",
    gift_info:[],
    rest_num:'',
    time_diff:"",
    if_expire:0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    order_sn = options.order_sn;
    var that = this;
    gift_request(that);
    clearTimeout(set_time_out);
  },
  // 倒计时
  countdown: function (that) {
    that.setData({
      clock: util.dateformat(total_micro_second)
    });
    if (total_micro_second <= 0) {
      that.setData({
        clock: ""
      });
      // timeout则跳出递归
      return;
    }
    set_time_out = setTimeout(function () {
      // 放在最后--
      total_micro_second -= 1;
      that.countdown(that);
    }, 1000)
  },
  // 自己发到朋友圈
  get_share_img:function(){
    var that = this;
    wx.showLoading({
      title: '生成中',
    })
    util.api('/api/wxapp/givegift/share', function (res) {
      if (res.error == 0) {
        pictorial = res.content;
        if (pictorial) {
          util.api('/api/wxapp/upayyun/image', function (res) {
            if (res.error == 0) {
              pictorial = imageUrl + pictorial + "!big";
              posterBase64 = res.content;
              that.setData({
                pictorial: posterBase64
              })
              wx.hideLoading();
              that.setData({
                is_share: 1
              })
            }
          }, { image_path: pictorial });
        }
      } else {
        wx.hideLoading();
        util.toast_fail(res.message);
        return false;
      }
    }, { order_sn:order_sn })
  },
  not_show_share: function () {
    var that = this;
    that.setData({
      is_share: 0
    })
  },
  saveImgToPhotosAlbumTap: function () {
    var that = this;
    var os_type = '';
    if (posterBase64) {
      util.base64ImageHandle(posterBase64, function (res) {
        wx.getSystemInfo({
          success: function (res) {
            os_type = res.platform
          }
        })
        if (os_type == 'ios') {
          util.toast_success('保存成功');
        } else {
          util.toast_success('图片已保存到相册');
        }
        that.setData({
          is_share: 0
        })
      });
    } else {
      util.toast_fail('正在生成中...')
    }
  },

  // 朋友领取礼物
  get_gift:function(e){
    var that = this;
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    if (gift_info.cart_info.gift_type == 3){
      util.jumpLink('pages1/presentcheckout/presentcheckout?order_sn=' + order_sn);
    }else{
      util.api('/api/wxapp/givegift/receive', function (res) {
        if (res.error == 0) {
          util.jumpLink('pages1/presentcheckout/presentcheckout?order_sn=' + order_sn);
        } else {
          util.showModal('提示', res.message, function () {
            util.jumpLink("/pages/index/index", 'redirectTo')
          }, false);
          return false
        }
      }, { order_sn: order_sn, form_id: form_id, open_id: open_id })
    }
  },
  // 领完了去完善信息
  get_gifted:function(){
    util.jumpLink('pages1/presentcheckout/presentcheckout?order_sn=' + order_sn);
  },
  // 查看订单
  to_orderinfo:function(){
    util.jumpLink("/pages/orderinfo/orderinfo?order_sn=" + order_sn)
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
    var send_name = gift_info.order_user.username;
    return{
      title: send_name +"挑了好久，决定送你这份礼物",
      path:"pages1/presentdetail/presentdetail?order_sn=" + order_sn
    }
  }
})
function gift_request(that) {
  util.api('/api/wxapp/givegift/info', function (res) {
    if (res.error == 0) {
      gift_info = res.content;
      rest_num = parseFloat(gift_info.total_num) - parseFloat(gift_info.received_num);
      if (gift_info.cart_info.surplus_second) {
        total_micro_second = gift_info.cart_info.surplus_second;
        if (total_micro_second > 0) {
          that.countdown(that);
        } else {
          that.setData({
            clock: ""
          });
        }
      }
      if (gift_info.receviced_order_list != ""){
        var now_time = util.formatTime(new Date());
        console.log(now_time);
        now_time = Date.parse(now_time.replace(/-/g,'/'));
        console.log(now_time);
        for (var i = 0; i < gift_info.receviced_order_list.length;i++){
          var add_time = gift_info.receviced_order_list[i].add_time;
          console.log(add_time);
          add_time = Date.parse(add_time.replace(/-/g,'/'));
          console.log(add_time);
          if (now_time - add_time < 600000){
            gift_info.receviced_order_list[i].if_use = 1;
          }else{
            gift_info.receviced_order_list[i].if_use = 0
          }
        }
      }
      if (gift_info.cart_info.gift_type == 3 && gift_info.cart_info.draw_time != null){
        var noes = util.formatTime(new Date());
        noes = Date.parse(noes);

        var dt = Date.parse(gift_info.cart_info.draw_time);
        if(noes > dt){
          if_expire = 1;
          that.setData({
            if_expire:if_expire
          })
        }
      }
      that.setData({
        gift_info: gift_info,
        user_id_now:util.getCache('user_id'),
        rest_num: rest_num
      })
    } else {
      util.showModal('提示', res.message, function () {
        util.jumpLink("/pages/index/index", 'redirectTo')
      }, false);
      return false
    }
  }, { order_sn:order_sn })
}