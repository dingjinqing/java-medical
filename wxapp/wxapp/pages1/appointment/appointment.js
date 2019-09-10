// APPOINTMENT.JS 2018.03.07
const util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var count;
var timeMode = true;
var techMode = true;
var service_info = [];
var date_info = [];
var img_info = [];
var store_info = [];
var tech_array = [];
var comment_info = [];
var index = 1;
var lat = 0;
var lon = 0;
var opt={};
var commimgUrls;
var service_id ;
var square_no = imageUrl + 'image/wxapp/icon_rectangle.png';
var square_yes = imageUrl + 'image/wxapp/selected.png';
var technician_id;
var tech_name;
var shop_id;
var mobile = util.getCache('mobile');
var is_bind_mobile;
var is_block = 0;
var is_second;
global.wxPage({
  data: {
    imageUrl: imageUrl,
    images:{},
    img_cart: imageUrl + '/image/wxapp/cart_icon.png',
    img_store: imageUrl + 'image/wxapp/sto_logo1.png',
    img_close: imageUrl + '/image/wxapp/close_icon.png',
    img_addr: imageUrl + '/image/wxapp/store_address.png',
    img_time: imageUrl + '/image/wxapp/store_time.png',
    img_charge:imageUrl + '/image/wxapp/icon_notice.png',
    img_arrow: imageUrl + '/image/wxapp/backward_right.png',
    img_sercer: imageUrl + '/image/wxapp/server_icon.png',
    img_success: imageUrl + '/image/wxapp/con_btn_success.png',
    img_iconsel: imageUrl + '/image/wxapp/selected.png',
    img_service: imageUrl + 'image/wxapp/icon_service.png',
    timeMode:true,
    techMode:true,
    index:1,
    is_second:0
  },
  onLoad: function (option) {
    if (!util.check_setting(option)) return;
    var that = this;
    service_id = option.service_id;
    opt = {};
    util.api('api/wxapp/store/service2', function (e) {
      service_info = e.content;
      if (e.content.store.is_delete == 1 || e.content.is_delete) {
        util.showModal('提示', '该服务已删除', function () {
          util.reLaunch({
            url: '/pages/index/index'
          })
        });
        return;
      }
      if (service_info.business_state == 0){
        util.showModal('提示', '该店铺未营业，随便逛逛', function () {
          util.reLaunch({
            url: '/pages/bottom/bottom'
          })
        });
            return false;
      }
      if (service_info.store.business_state == 0) {
        util.showModal('提示', '该门店未营业，随便逛逛', function () {
          util.reLaunch({
            url: '/pages/bottom/bottom'
          })
        });
      return false;
      }
      is_block = 0;
      mobile = util.getCache('mobile');
      is_bind_mobile = service_info.is_bind_mobile;//是否要绑定手机号判断
      date_info = e.content.date;
      shop_id = service_info.shop_id;
      img_info = e.content.service_img;
      img_info = JSON.parse(img_info);
      img_info.pop();
      count = img_info.length;
      store_info = e.content.store;
      comment_info = e.content.comment_info;
      opt.store_id = service_info.store_id;
      if (e.content.content != null) {
		  that.setData({
			  content:util.filterRichText(e.content.content)
		  });
      };
      var dis = 0;
      wx.getLocation({
        type: 'wgs84',
        success: function (d) {
          var latitude = d.latitude;
          var longitude = d.longitude;
          lat = store_info.latitude;
          lon = store_info.longitude;
          dis = app.getDistance(lat, lon, latitude, longitude);
          store_info.dis = dis;
          that.setData({
            store_info: store_info
          });
        }
      })
      //评价
      var strRegex = "^((https|http|ftp|rtsp|mms)?://)";
      var re = new RegExp(strRegex);
      if (comment_info != null && comment_info != "") {
        if (!re.test(comment_info.user_avatar)) {
          comment_info.user_avatar = imageUrl + comment_info.user_avatar;
        }
        comment_info.comm_img = JSON.parse(comment_info.comm_img);
        commimgUrls = comment_info.comm_img;
        var com_reg = /^(.).+(.)$/g;
        comment_info.name = comment_info.username.replace(com_reg, "$1**$2");
      }
      if (service_info.payment) {
        var psy_way = service_info.payment;
        service_info.payment_way = 0;
        for (var i = 0; i < psy_way.length; i++) {
          if (psy_way[i].pay_code == 'wxpay') {
            service_info.payment_way = 1;
          }
        }
      }
      that.setData ({
        service_info: service_info,
        date_info: date_info,
        img_info: img_info,
        count: count,
        store_info: store_info,
        comment_info: comment_info,
        show_id:-1,
        show_time:-1,
        is_block: is_block
      })
    }, {   service_id: service_id})

  },
  clickComment: function (e) {
    var nowImgUrl = e.target.dataset.src;
    var arr = [];
    for (var i in commimgUrls) {
      arr.push(commimgUrls[i]); //属性
    }
    wx.previewImage({
      current: nowImgUrl, // 当前显示图片的http链接
      urls: arr // 需要预览的图片http链接列表
    })
  },
  showMap: function (e) {
    var address = service_info.store.address;
    var latt = parseFloat(lat);
    var lonn = parseFloat(lon);
    wx.openLocation({
      latitude: latt,
      longitude: lonn,
      scale: 28,
      name:address
    })
  },
  //轮播
  onSlideChangeEnd: function (e) {
    var that = this;
    that.setData({
      index: e.detail.current + 1,
    })
  },
  //选择时间弹框
  timeShow:function(){
    if (date_info != ""){
      this.setData({
        timeMode: false,
        show_id: 0,
        show_time: 0
      })
      opt.date = opt.time = 0;
      opt.day = date_info[0].date;
      opt.hour = date_info[0].time[0];
      opt.service_id = service_id;

    }else{
      util.showModal('提示', '暂无时间可选择！');
    }
  },
  timeClose:function(){
    this.setData({
      timeMode:true
    })
  },
  timeConfirm:function(){
    this.setData({
      timeMode:true,
      tech_name:''
    })
    if (service_info.service_type == 1){
      this.techShow();
    }

  },
  //技师
  techShow: function () {
    var param = {};
    var that = this;
    param.store_id = store_info.store_id;
    param.service_id = service_id;
    param.date = date_info[opt.date].date;
    param.time = date_info[opt.date].time[opt.time];
    if (param.date && param.time) {
      util.api('/api/wxapp/technician/list', function (res) {
        if (res && res.error == 0 && res.content) {
          if (res.content.length > 0) {
            tech_array = res.content;
            var strRegex = "^((https|http|ftp|rtsp|mms)?://)";
            var re = new RegExp(strRegex);
            for (var i in tech_array) {
              //没有头像加默认头像
              if (tech_array[i].bg_img_path == null) {
                tech_array[i].bg_img_path = imageUrl + 'image/admin/tech_moren.png'
              }
              //有头像没有域名的加域名
              if (!re.test(tech_array[i].bg_img_path)) {
                tech_array[i].bg_img_path = imageUrl + tech_array[i].bg_img_path;
              }
              tech_array[i].src = square_no;
              if (tech_array[i].id == opt.technician_id) {
                tech_array[i].src = '';
                tech_name = tech_array[i].technician_name;
                that.setData({
                  tech_name: tech_name
                })
              }
            }
            that.setData({
              techMode: false,
              tech_array: tech_array,
            })
          } else {
            util.showModal('提示', '该时间段内没有' + service_info.technician_title +'，请换个时间段再选择');
          }
        } else {
          util.showModal('提示', '该时间段内没有' + service_info.technician_title +'，请换个时间段再选择');
        }
      }, param)
    } else {
      util.showModal('提示', '请先选择服务时间');
    }

  },
  chooseTach:function(e){
    var index = e.currentTarget.dataset.index;
    var id = e.currentTarget.dataset.id;
    var name = e.currentTarget.dataset.name;
    technician_id = id;
    tech_name = name;
    for (var i in tech_array){
      tech_array[i].src = square_no;
      if(index == i){
        tech_array[i].src = '';
      }
    }
    this.setData({
      tech_array: tech_array
    })
  },
  techConfirm:function(){
    opt.technician_id = technician_id;
    tech_name = tech_name;
    opt.technician_name = tech_name;
    this.setData({
      techMode: true,
      tech_name: tech_name
    })
  },
  techClose:function(){
    this.setData({
      techMode: true
    })
  },
  selectedHour: function (e) {
    var key = e.currentTarget.dataset.time_id;
    opt.time = key;
    this.setData({
      show_time: key
    })
  },
  click_to_detail:function(){
    util.navigateTo({
      url: '/pages/appointcomment/appointcomment?service_id=' + service_id + "&shop_id=" + shop_id,
    })
  },
  //  立即预约
  OneClickBuy:function(e) {
    //判断是否要去绑定手机号
	  var that = this;
    if (service_info.business_state == 0) {
      util.showModal('提示', '该店铺未营业，随便逛逛', function () {
        util.reLaunch({
          url: '/pages/index/index'
        })
      });
      return false;
    }
    if (service_info.store.business_state == 0) {
      util.showModal('提示', '该门店未营业，随便逛逛', function () {
        util.reLaunch({
          url: '/pages/index/index'
        })
      });
      return false;
    }
    if (is_bind_mobile == 1 && util.getCache('mobile') == '') {
      util.checkSession(function () {
        that.setData({
          is_block: is_block = 1
        })
      })
      return false;
    }

    if (date_info.length > 0 && opt.date!=undefined && opt.time!=undefined){

      opt.day = date_info[opt.date].date;
      opt.hour = date_info[opt.date].time[opt.time];
      opt.service_id = service_id;
    } else if (opt.date == undefined && opt.time == undefined && that.data.show_id == -1){
      that.setData({
        timeMode: false,
        show_id:0,
        show_time:0
      })
      opt.date = opt.time = 0;
      opt.day = date_info[0].date;
      opt.hour = date_info[0].time[0];
      opt.service_id = service_id;
      return false;
    }
    if (date_info == ""){
      util.showModal('提示', '暂无时间可选择！');
      return false;
    }
    if (!opt["technician_id"] && service_info.service_type==1){
      util.showModal('', '请选择' + service_info.technician_title);
      return;
    }
    util.navigateTo({
      url: '/pages/appointorder/appointorder?date='+JSON.stringify(opt),
    })
  },
  bindGetPhoneNumberOk(e){
    mobile = e.detail.phoneNumber;
  },
  // 跳到门店首页
  toIndex:function() {
    util.reLaunch({
      url: '/pages/storeinfo/storeinfo?id=' + service_info.store_id,
    })
  },
  //选择日期
  dataClick:function(e){
    var key = e.currentTarget.dataset.key;
    var date_click = e.currentTarget.dataset.date_click;
    var that = this;
    opt.date = key;
    if (date_click == 'no' && key > 0){
      util.api('/api/wxapp/store/service/time', function(res){
        if (res.error == 0) {
          date_info[key].time = res.content;
          date_info[key].date_click = 'yes';
          util.setCache("mobile", mobile);

          that.setData({
            date_info: date_info,
            show_id: key
          })
        }
      }, { service_id: service_id, date: date_info[key].date, period: date_info[key].time[0]})
    } else {
      that.setData({
        show_id: key
      })
    }
  },

  onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
  },
  /**
* 用户点击右上角分享
*/
  onShareAppMessage: function () {
    return {
      path: '/pages/appointment/appointment?service_id=' + service_id + "&invite_id=" + util.getCache('user_id'),
    }
  },
})
