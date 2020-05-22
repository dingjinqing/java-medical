// pages/returnorder/returnorder.js
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var util = require('../../utils/util.js');
var Url = app.globalData.baseUrl;
var checkbox_no = imageUrl + '/image/admin/select.png';
var checkbox_yes = imageUrl + '/image/wxapp/checkbox_ed.png';
var order_goods_rec = {};
var order_rec_id;
var total_shipping_fee = 0;
var returns = [];
var steps;
var goods_type=0;
var auto_return = 0;
var total_micro_second = 0;
var set_time_out;
var return_service;
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    checkbox_no: imageUrl + '/image/admin/select.png',
    checkbox_yes: imageUrl + '/image/wxapp/checkbox_ed.png',
    type_active: imageUrl + '/image/wxapp/con_btn_success.png',
    click_more: imageUrl + '/image/wxapp/click_look.png',
    add_img: imageUrl + '/image/wxapp/return_img_icom.png',
    areaIndex: 0,
    main_url: app.globalData.baseUrl,
    imgHide: true,
    imgUpload: false,
    img_len: 0,
    singImg: [],
    rec: {},
    returns:[],
    order_goods_rec:{},
    return_money: '0.00',
    can_shipping_fee: 0,
    return_type:1,
    imageUrl: app.globalData.imageUrl,
    area: ['协商一致退款', '未按约定时间发货', '缺货', '多拍/不想要', '其他'],
    steps:"",
    auto_return:0,
    total_micro_second:0,
    return_service:0,
    voucher_images:"",
  },

  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    order_goods_rec = {};
    var order_sn = options.order_sn;
    var ret_id = options.ret_id ? options.ret_id : 0;
    var data = {}
    data.order_sn = order_sn;
    if(ret_id>0){
      data.ret_id = ret_id;
    }
    else{
      data.ret_id = 0;
    }
    var that = this;
    util.api('/api/wxapp/return/order', function (rest) {
      if (rest.error == 0) {
        var order = rest.content;
        var shipping = [];
        var info={};
        for (var i in order.shipping) {
          info = {};
          info.id = order.shipping[i].shipping_id;
          info.name = order.shipping[i].shipping_name;
          shipping.push(info);
        }
        var total_goods_number = 0;
        if (order.order_goods !=null){
          for (var i in order.order_goods) {
            total_goods_number = parseInt(total_goods_number) + parseInt(order.order_goods[i].goods_number);
            order.order_goods[i].checked = 0;
            order.order_goods[i].checkbox = checkbox_no;
            if (order.order_info.goods_type == 4 && order.order_info != null) {
              order.order_goods[i].money = parseFloat(order.order_goods[i].goods_price - order.order_goods[i].goods_score / 100).toFixed(2);
              goods_type = 4;
              order.order_goods[i].discounted_goods_price = parseInt(order.order_goods[i].discounted_goods_price * 100);
              order.order_goods[i].max_return_money = parseInt(order.order_goods[i].max_return_money * 100);
            }
          }
        }
        order.order_info.type_icon = ''
        order.order_info.goods_type = order.order_info.goods_type.split(",");
        for (var j in order.order_info.goods_type) {
          if (order.order_info.goods_type[j] == 1) {
            order.order_info.type_icon = "拼团"
          } else if (order.order_info.goods_type[j] == 3) {
            order.order_info.type_icon = '砍价'
          } else if (order.order_info.goods_type[j] == 5) {
            order.order_info.type_icon = "秒杀"
          } else {
            order.order_info.type_icon == ""
          }
        }
        if (order.order_info.goods_type == 4 && order.order_info != null) {
          for (var i in order.return_goods) {
            order.return_goods[i].goods_price = parseInt(order.return_goods[i].goods_price * 100);
          }
        }
        order_rec_id = order.rec_id;
        if (order.order_info.order_status == 3 && (order.order_info.pay_code == 'wxpay' || (order.order_info.pay_code == 'cod' && (order.order_info.score_discount > 0 || order.order_info.use_account > 0)) || order.operation.can_user_apply_return == false)){
          that.setData({
            return_type: 0
          });
        }
        total_shipping_fee = order.total_shipping_fee;
        var can_shipping_fee = '0.00';
        if (order.order_info.order_status == 3) {
          can_shipping_fee = order.can_return_free != undefined ? order.can_return_free : 0;
        }
        if (order.return_order != null && order.order_info.goods_type == 4) {
          order.return_order.score = parseInt(order.return_order.money * 100 * order.order_info.score_discount / order.order_info.order_amount);
          order.return_order.money = parseFloat(order.return_order.money * (order.order_info.order_amount - order.order_info.score_discount) / order.order_info.order_amount).toFixed(2);
        }
        returns = order.order_goods;
        if(order.step == 4 || order.step == 5){
          returns = order.return_goods
        }
        if (order.return_order != null && order.order_info.goods_type == 4 && returns != null){
          order.return_order.per_score = parseInt(order.return_order.score / returns[0].goods_number);
          order.return_order.per_money = parseFloat(order.return_order.money / returns[0].goods_number).toFixed(2);
        }
        steps = order.step;
        return_service = order.return_service;
        if (order.auto_return == 1 && order.count_down != undefined && order.count_down){
          total_micro_second = order.count_down;
          if (total_micro_second > 0) {
            that.countdown(that);
            that.setData({
              act_open: 1
            });
          }
        }
        that.setData({
          return_service: return_service,
          auto_return: order.auto_return,
          step: order.step,
          operator:order.operator,
          operation: order.operation,
          order_info: order.order_info,
          order_goods: that.changeSendType(order.order_goods, that.data.return_type),
          returns: returns,
          return_order: order.return_order,
          shipping: shipping,
          ret_id: ret_id,
          order_sn: order_sn,
          total_goods_number: total_goods_number,
          total_shipping_fee: total_shipping_fee,
          can_shipping_fee: can_shipping_fee,
          goods_number: 0,
          goods_num_can_refund: order.goods_num_can_refund,
          returnTypeName: rest.content.returnTypeName
        });
      } else {
        util.showModal('提示', rest.message);
      }
    }, data);

  },
  returnOrder: function (e) {
    util.navigateTo({ url: '/pages/returnorder/returnorder?order_sn=' + e.target.dataset.order_sn })
  },
  //倒计时
  countdown: function (that) {
    that.setData({
      clock: that.dateformat(total_micro_second)
    });
    if (total_micro_second <= 0) {
      that.setData({
        clock: "已经截止"
      });
      return;
    }
    set_time_out = setTimeout(function () {
      // 放在最后--
      total_micro_second -= 1;
      that.countdown(that);
    }, 1000)
  },
  // 时间格式化输出，如3:25:19 86。每10ms都会调用一次
  dateformat: function (micro_second) {
    // 秒数
    var second = Math.floor(micro_second);
    //天数位
    var date = Math.floor(second / 86400);
    // 小时位
    var hr = Math.floor((second - date * 24 * 3600) / 3600);
    if (hr < 10) {
      hr = "0" + hr;
    }
    // 分钟位
    var min = Math.floor((second - hr * 60 * 60 - date * 24 * 3600) / 60);
    if (min < 10) {
      min = "0" + min;
    }
    // 秒位
    var sec = second % 60;
    if (sec < 10) {
      sec = "0" + sec;
    }
    return date + "天" + hr + '时' + min + "分" + sec + "秒";
  },
  onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
  },

  bindPickerChange: function (e) {
    this.setData({
      areaIndex: e.detail.value
    })
  },
  returnType1:function(e){
    this.setData({
      order_goods: this.changeSendType(this.data.order_goods, 1),
      return_type: 1
    })
    this.refresh_return_money();
  },
  returnType2: function (e) {
    this.setData({
      order_goods: this.changeSendType(this.data.order_goods, 0),
      return_type: 0
    })
    this.refresh_return_money();
  },
  checkChoose:function(e){
    var that = this;
    var rec_id = e.currentTarget.dataset.rec_id;
    var good_number = e.currentTarget.dataset.goods_number;
    var goods_price = e.currentTarget.dataset.goods_price;
    var id = e.currentTarget.dataset.id;
    var good_money = good_number * goods_price;
    if (order_rec_id != null){
      util.showModal('提示', rec_id);
    }else{
      let order_goods = this.data.order_goods;
      for (var i in order_goods) {
        if (id == i) {
          if (order_goods[id].checkbox == checkbox_no) {
            order_goods[id].checkbox = 1;
            order_goods[id].checked = 1;
            that.data.rec[rec_id] = good_number;
          } else {
            order_goods[id].checkbox = checkbox_no;
            order_goods[id].checked = 0;
            delete that.data.rec[rec_id];
          }
        }
      }
      that.refresh_return_money();
    }
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
  goodsItem: function (e) {
    util.navigateTo({ url: '/pages/good/item?good_id=' + e.target.dataset.goods_id })
  },
  orderInfo: function (e) {
    util.navigateTo({ url: '/pages/orderinfo/orderinfo?order_sn=' + e.target.dataset.order_sn })
  },
  orderList: function (e) {
    util.navigateTo({ url: '/pages/orderlist/orderlist' })
  },
  uploadImg: function (e) {
    var that = this
    util.uploadImage(1, function (res) {
      var data = res.data
      var content = JSON.parse(data);
      if (content.error == 0) {
        for(var i in content.content){
          that.data.singImg.push(content.content[i].img_url);
        }
        var img_len = parseInt(that.data.singImg.length);
        that.setData({
          singImg: that.data.singImg,
          img_len: img_len,
          imgUpload:true
        });
      }
    });
  },
  textBlur: function (e) {
    var that = this;
    that.setData({
      return_desc: e.detail.value,
    });
  },
  Submit: function (e) {
    var that = this;
    var data = {}
    data.rec = JSON.stringify(that.data.rec);
    data.order_sn = that.data.order_sn;
    data.return_type = that.data.return_type;
    data.money = that.data.return_money;
    data.reason = that.data.area[that.data.areaIndex];
    data.form_id = e.detail.formId;
    data.open_id = util.getCache("openid");
    if (that.data.return_desc !=undefined){
      data.return_desc = that.data.return_desc;
    }
    else{
      data.return_desc = '';
    }
    var pngzheng = [];
    for (var i in that.data.singImg){
      pngzheng.push(that.data.singImg[i]);
    };
    if(steps == 2){
      data.voucher_images = pngzheng.join(",");
    }else{
      data.goods_images = pngzheng.join(",");
    }

    if (data.rec == '{}') {
      util.showModal('提示', '请选择商品');
      return false;
    }
    util.api('/api/wxapp/return/add', function (rest) {
      if (rest.error == 0) {
        util.navigateTo({ url: '/pages/returnorder/returnorder?order_sn=' + data.order_sn + '&ret_id=' + rest.content })
      } else {
        util.showModal('提示', rest.message);
      }
    }, data);
  },
  btnNum: function (e) {
    var that = this;
    var rec = that.data.rec;
    var rec_id = e.target.dataset.rec_id;
    var max_num = e.target.dataset.max_num;
    var goods_number = e.target.dataset.goods_number;
    var type = e.target.dataset.type;
    if (type == 'dce') {
      if(goods_number>1 && goods_number>0){
        goods_number -= 1;
      }else{
        type = '';
        util.showModal("提示", "商品数量达到下限");
      }
    } else {
      if (goods_number<max_num){
        goods_number += 1;
      }else{
        type = '';
        util.showModal("提示","商品数量达到上限");
      }
    }
    if (goods_number > 0 && goods_number <= max_num) {
      let order_goods = that.data.order_goods;
      for (var i in order_goods) {
        if (order_goods[i].rec_id == rec_id) {
          order_goods[i].goods_number = goods_number;
          that.data.rec[rec_id] = goods_number;
          this.refresh_return_money();
        }
      }
    }
  },
  refresh_return_money:function(){
    var shipping_fee = this.data.order_info.shipping_fee;
    var order_status = this.data.order_info.order_status;
    this.data.return_money = '0.00';
    this.data.return_score = 0;
    this.data.goods_number = 0;
    this.data.max_return_number = 0;
    this.data.max_return_money = 0.00;
    var rec_id = [];
    var goods_number = [];
    let order_goods = this.data.order_goods;
    for(var i in order_goods){
      if (order_goods[i].checkbox == 1) {
        console.log(goods_type);
        if (goods_type == 4){
          this.data.return_money = (parseFloat(this.data.return_money) + parseFloat(this.data.rec[order_goods[i].rec_id] * order_goods[i].money)).toFixed(2);
          this.data.return_score = (parseFloat(this.data.return_score) + parseInt(this.data.rec[order_goods[i].rec_id] * order_goods[i].goods_score));
        }else{
          this.data.return_money = (parseFloat(this.data.return_money) + parseFloat(this.data.rec[order_goods[i].rec_id] * order_goods[i].discounted_goods_price)).toFixed(2);
        }
        this.data.goods_number = parseInt(this.data.goods_number) + parseInt(this.data.rec[order_goods[i].rec_id]);
        this.data.max_return_number = parseInt(this.data.max_return_number) + parseInt(order_goods[i].max_return_number);
        this.data.max_return_money = (parseFloat(this.data.max_return_money) + parseFloat(order_goods[i].max_return_money)).toFixed(2);
        rec_id.push(order_goods[i].rec_id);
        goods_number.push(this.data.rec[order_goods[i].rec_id]);
      }
    }

    if (this.data.goods_number == this.data.max_return_number && goods_type != 4) {
      this.data.return_money = this.data.max_return_money;
    }

    this.data.can_shipping_fee = 0;
    if (parseFloat(shipping_fee) > parseFloat(total_shipping_fee) && order_status == 3 && this.data.goods_number > 0) {
      this.data.can_shipping_fee = 1;
    }
    this.setData({
      return_money: this.data.return_money,
      return_score: this.data.return_score,
      order_goods: order_goods,
      can_shipping_fee: this.data.can_shipping_fee
    });

    var calculate_shipping_fee = 0;
    var that = this;
    util.api('/api/wxapp/calculate/shipping', function (response) {
      if (response.error == 0) {
        calculate_shipping_fee = response.content;
        that.setData({
          shipping_fee: calculate_shipping_fee
        });
      } else {
        util.showModal('提示', response.code);
      }
    }, { rec_id: rec_id.join(','), goods_number: goods_number.join(','), order_sn: this.data.order_sn});
  },
  expressTel: function (e) {
    var that = this;
    if (e.detail.value == ""){
      util.showModal('提示', "请输入手机号！");
    }else{
      if (/^1[3456789]\d{9}$/.test(e.detail.value)) {
        that.setData({
          phone: e.detail.value
        })
      } else {
        util.showModal('提示', "请输入正确的手机号！");
      }
    }
  },
  exnumInput:function(e){
    if (e.detail.value == ""){
      util.showModal('提示', "请输入运单号码");
    }
  },
  expressNum: function (e) {
    var that = this;
    that.setData({
      shipping_no: e.detail.value,
    });
  },
  exSubmit: function (e) {
    var that = this;
    var data = {}
    data.order_sn = that.data.order_sn;
    data.ret_id = that.data.ret_id;
    data.shipping_type = that.data.shipping[that.data.areaIndex].id;
    data.shipping_name = that.data.shipping[that.data.areaIndex].name;
    data.shipping_no = that.data.shipping_no;
    data.phone = that.data.phone;
    data.voucher_images = that.data.singImg;
    if (that.data.singImg.length > 0){
      data.voucher_images = that.data.singImg.split(',');
    }else {
      data.voucher_images = ''
    }
    if (that.data.shipping_no == "" || that.data.shipping_no == undefined){
      util.showModal("提示", '请输入运单号码！');
      return false;
    }
    if (that.data.phone == "" || that.data.phone == undefined){
      util.showModal("提示",'请输入手机号！');
      return false;
    }
    util.api('/api/wxapp/return/shipping', function (rest) {
      if (rest.error == 0) {
        util.navigateTo({ url: '/pages/returnorder/returnorder?order_sn=' + data.order_sn + '&ret_id=' + data.ret_id })
      } else {
        util.showModal('提示', rest.code);
      }
    }, data);
  },
  userExpress: function (e) {
    var ex_no = e.target.dataset.ex_no;
    var order_sn = e.target.dataset.order_sn;
    util.navigateTo({ url: '/pages/express/express?ex_no=' + ex_no + '&order_sn=' + order_sn + '&user_id=' + util.getCache('user_id') })
  },
  // 撤销退款申请
  cancel_apply:function(e){
    var orders_sns = e.currentTarget.dataset.order_sn;
    var rets_ids = e.currentTarget.dataset.ret_id;
    var that = this;
    wx.showModal({
      title: '撤销退款/退货申请',
      content: '撤销退款/退货申请后，本次申请将关闭，如后续仍有问题，可以再次发起申请',
      cancelText: "取消",
      cancelColor: "#333333",
      confirmText: "撤销申请",
      confirmColor: that.data.comColor,
      success(res) {
        if (res.confirm) {
          util.api('/api/wxapp/revoke/returnorder', function (res) {
              if(res.error == 0){
                util.navigateTo({
                  url: '/pages/return_order_list/return_order_list?order_sn=' + orders_sns,
                })
              }else{
                util.showModal("提示",'撤销失败');
                return false;
              }
          },{order_sn:orders_sns,ret_id:rets_ids})
        } else if (res.cancel) {

        }
      }
    })
  },
  to_record:function(e){
    var orders_sns = e.currentTarget.dataset.order_sn;
    var rets_ids = e.currentTarget.dataset.ret_id;
    util.navigateTo({
      url: '/pages/returnrecord/returnrecord?order_sn=' + orders_sns + "&ret_id=" + rets_ids,
    })
  },
  changeSendType(order_goods, return_type) {
    let can_return_all = true;
    for (var id in order_goods) {
      if (return_type == 1) {
        if (order_goods[id].send_number == 0) {
          if (can_return_all) can_return_all = false;
          order_goods[id].checkbox = -1;
          order_goods[id].checked = 0;
          delete this.data.rec[order_goods[id].rec_id];
        }
      } else {
        if (order_goods[id].checkbox == -1) {
          order_goods[id].checkbox = checkbox_no;
          order_goods[id].checked = 0;
        }
      }
    }
    this.setData({ can_return_all: can_return_all })
    return order_goods;
  },
  copyOrder() {
    let _this = this
    wx.setClipboardData({
      data: _this.data.order_info.order_sn,
      success: (res) => {
        console.log('copy_success', res);
        util.toast_success('内容已复制');
      }
    })
  },
})
