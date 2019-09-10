//index.js
var util = require('../../utils/util.js');
//获取应用实例
var app = getApp();
var Url = app.globalData.baseUrl;
var is_load = 0;
global.wxPage({
  data: {
    imageUrl:app.globalData.imageUrl,
    transform:0,
    disabled_r:'block',
    disabled_l:'none',
    type:'all',
    hiddenModal: true,
    searchLoading:false,
    loadingComplete:false,
    main_url:Url,
    page:1,
    order_list: [],
    last_page:1,
    is_load:0,
    input_value:'',
    order_num:'',
    is_search:0,
  },
  //事件处理函数
  bindViewTapR: function(e) {
      this.data.transform +=-10;
      var disabled_r='';
      var disabled_l='';
      if(this.data.transform<=-30){
        disabled_r='none';
      }else{
        disabled_r='block';
      }
      if(this.data.transform<=-10){
        disabled_l='block';
      }else{
        disabled_l='none';
      }

      this.setData({
        transform: this.data.transform,
        disabled_r:disabled_r,
        disabled_l:disabled_l
      });
  },
  onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
  },
  onReachBottom:function(e){
    var that = this;
    that.setData({
      is_load:1
    })
    if (that.data.page == that.data.last_page){
      that.setData({
        is_load: 0
      })
      return;
    }
    var pageNo = that.data.page + 1;
    util.api('/api/wxapp/order/list', function (rest) {
      if (rest.error == 0) {
        var orderL = rest.content;
        that.data.page = orderL.current_page;
        var order_list_r = orderL.data;
        var order_list = [];
        if (order_list_r.length > 0) {
          order_list = order_list_r;
          for (var i in order_list) {
            order_list[i].type_icon = ''
            order_list[i].goods_type = order_list[i].goods_type.split(",");
            for (var j in order_list[i].goods_type) {
              if (order_list[i].goods_type[j] == 1) {
                order_list[i].type_icon = "拼团"
              } else if (order_list[i].goods_type[j] == 3) {
                order_list[i].type_icon = '砍价'
              } else if (order_list[i].goods_type[j] == 5) {
                order_list[i].type_icon = "秒杀"
              } else {
                order_list[i].type_icon == ""
              }
            }
            if (order_list[i].goods_type == 4) {
              order_list[i].goods[0].money = parseFloat(order_list[i].goods[0].goods_price - order_list[i].goods[0].goods_score / 100).toFixed(2);
            }
            if (order_list[i].deliver_type == 1 && order_list[i].order_status == 3) {
              order_list[i].order_status_name = "待核销";
            }
            if (order_list[i].deliver_type == 0 && order_list[i].order_status == 3) {
              order_list[i].order_status_name = "待发货";
            }
            if (order_list[i].deliver_type == 1 && order_list[i].order_status == 5) {
              order_list[i].order_status_name = "已自提";
            }
            if (order_list[i].deliver_type == 0 && order_list[i].order_status == 5) {
              order_list[i].order_status_name = "已收货";
            }
            if (order_list[i].pre_sale && order_list[i].pre_sale != null){
              let start_time = order_list[i].pre_sale.start_time;
              let end_time = order_list[i].pre_sale.end_time;
              order_list[i].pre_sale.start_time = start_time.substring(0, start_time.length - 3);
              order_list[i].pre_sale.end_time = end_time.substring(0, end_time.length - 3);
            }
          }
        }
        that.setData({
          order_list: that.data.order_list.concat(order_list),
          is_load: 0
        });
      }
    
    }, { type: that.data.type, pageNo: pageNo, search: that.data.input_value },'',true);
  },
  //事件处理函数
  bindViewTapL: function(e) {
      this.data.transform -=-10;
      var disabled_r='';
      var disabled_l='';
      if(this.data.transform>-30){
        disabled_r='block';
      }else{
        disabled_r='none';

      }
      if(this.data.transform>-10){
        disabled_l='none';
      }else{
        disabled_l='block';

      }

      this.setData({
        transform: this.data.transform,
        disabled_r:disabled_r,
        disabled_l:disabled_l
      });

  },
  //事件处理函数
  bindOrderNav: function(e) {
    var that = this;
    var order_type = e.target.dataset.type;
    var datas = e.target.dataset
    that.setData({
        type:order_type,
        is_search:0,
    });
    datas = JSON.stringify(datas);
    that.order_request(that)
  },
  bindOrderBut:function(e){
    var data = e.target.dataset
    util.showModal('提示','是否取消该订单',function(res){
      util.api('/api/wxapp/order/cancel',function(res){
       if(res.error==0){
          util.navigateTo({url:'/pages/orderlist/orderlist'})
        }
      },{order_sn:data.order_sn});
    },true);

  },
  bindOrderButDel:function(e){
    var data = e.target.dataset
     util.showModal('提示','是否删除该订单',function(res){
       util.api('/api/wxapp/order/del',function(res){
          if(res.error==0){
            util.navigateTo({url:'/pages/orderlist/orderlist'})
          }
        }, {order_sn:data.order_sn});
     },true);

  },
  bindLoading:function(e){


  },

  onLoad:function(options){
    if (!util.check_setting(options)) return;

    var opt = options.datas;
    if (opt) {
      var obj = JSON.parse(opt);
      this.data.type = obj.type;
      this.data.transform = obj.transform;
      this.data.disabled_r = obj.disabled_r;
      this.data.disabled_l = obj.disabled_l;
    } else {
      this.data.type = 'ALL';
      this.data.page = 1;
    }
    if (this.data.type == "WAIT_DELIVERY") {
      this.setData({
        scroll_left: 200
  })
    }
    if (this.data.type == 'FINISHED'  || this.data.type == 'SHIPPED') {
      this.setData({
        scroll_left: 500
      })
    }
    if (this.data.type == "RETURNING"){
      this.setData({
        scroll_left:750
      })
    }
    this.order_request(this);
  },

  listenerButton:function(e) {
    var order_sn = e.target.dataset.order_sn;
    var openid = util.getCache('openid');
    var order_pay_way = e.target.dataset.order_pay_way;
    var that = this;
    if (order_pay_way == 2) {
      util.navigateTo({
        url: '/pages/insteadinfo/insteadinfo?order_sn=' + order_sn,
      })
      return false;
    }
    util.api('/api/wxapp/order/pay',function(res){
      if(res.error == 0){
        var rest = res.content;
        wx.requestPayment({
          'timeStamp': rest.timeStamp,
          'nonceStr': rest.nonceStr,
          'package': rest.package,
          'signType': typeof res.content.signType == "undefined" ? 'MD5' : res.content.signType,
          'paySign':rest.paySign,
          'success':function(res){
            util.toast_success('支付成功');
            util.navigateTo({
              url:'/pages/orderinfo/orderinfo?order_sn='+order_sn,
            })
          },
          'fail':function(res){
            util.navigateTo({
              url:'/pages/orderinfo/orderinfo?order_sn='+order_sn,
            })
          },
          'complete':function(res){
          }
        });
      } else {
        if (res.error == '9999') {
          util.showModal('提示', res.message, function () {
            that.data.is_can_order = 1;
            that.listenerButton(e);
          }, true, '再看看', '继续付款');
        } else {
          util.showModal('提示', res.message);
        }
      }
    }, { order_sn: order_sn, open_id: openid, is_can_order: that.data.is_can_order ? 1 : 0});

  },

  listenerConfirm:function() {
   this.setData({
    hiddenModal: true
   })
  },

  listenerCancel:function() {
   this.setData({
    hiddenModal: true
   })
},
  orderInfo:function(e){
    var order_sn=e.target.dataset.order_sn;
    util.navigateTo({url:'/pages/orderinfo/orderinfo?order_sn='+order_sn})
  },
  bindOrderOk:function(e){
    var that = this;
    var data = e.target.dataset
    util.showModal('提示','是否确认收货',function(res){
      util.api('/api/wxapp/order/receive',function(res){
          if(res.error==0){
            that.order_request(that)
            util.jumpLink('/pages/commentcomplete/commentcomplete?can_send=' + data.can_send + '&order_sn=' + data.order_sn ,'navigateTo');
          }
      }, { order_sn: data.order_sn });
    },true);

  },
  //商品评价
  comment: function (e) {
    var order_sn = e.target.dataset.order_sn;
    util.navigateTo({
      url: '../comment/comment?order_sn=' + order_sn
    })
  },
 
  save_zhi: function (e) {
    var search_word;
    search_word = e.detail.value;
    console.log(e.detail.value)
    console.log(search_word)
    this.setData({
      input_value: search_word
    })
  },
  change_word: function (e) {
   var search_input = e.detail.value;
    this.setData({
      input_value: search_input
    })
  },
  bindSearch: function (e) {
    var that = this;
    var search_input = that.data.input_value;
    wx.showLoading({ title: '加载中...', mask: true, })
    util.api('/api/wxapp/order/list', function (rest) {
      if (rest.error == 0) {
        var orderL = rest.content;
        that.data.page = orderL.current_page;
        that.data.order_num = orderL.total;
        var order_list_r = orderL.data;
        var order_list = [];
        if (order_list_r.length > 0) {
          order_list = order_list_r;
          for (var i in order_list) {
            order_list[i].type_icon = ''
            order_list[i].goods_type = order_list[i].goods_type.split(",");
            for (var j in order_list[i].goods_type) {
              if (order_list[i].goods_type[j] == 1) {
                order_list[i].type_icon = "拼团"
              } else if (order_list[i].goods_type[j] == 3) {
                order_list[i].type_icon = '砍价'
              } else if (order_list[i].goods_type[j] == 5) {
                order_list[i].type_icon = "秒杀"
              } else {
                order_list[i].type_icon == ""
              }
            }
            if (order_list[i].goods_type == 4) {
              order_list[i].goods[0].money = parseFloat(order_list[i].goods[0].goods_price - order_list[i].goods[0].goods_score / 100).toFixed(2);
            }
            if (order_list[i].deliver_type == 1 && order_list[i].order_status == 3) {
              order_list[i].order_status_name = "待核销";
            }
            if (order_list[i].deliver_type == 0 && order_list[i].order_status == 3) {
              order_list[i].order_status_name = "待发货";
            }
            if (order_list[i].deliver_type == 1 && order_list[i].order_status == 5) {
              order_list[i].order_status_name = "已自提";
            }
            if (order_list[i].deliver_type == 0 && order_list[i].order_status == 5) {
              order_list[i].order_status_name = "已收货";
            }
            if (order_list[i].pre_sale && order_list[i].pre_sale != null) {
              let start_time = order_list[i].pre_sale.start_time;
              let end_time = order_list[i].pre_sale.end_time;
              order_list[i].pre_sale.start_time = start_time.substring(0, start_time.length - 3);
              order_list[i].pre_sale.end_time = end_time.substring(0, end_time.length - 3);
            }
          }
        }
      
        that.setData({
          page : orderL.current_page,
          last_page : orderL.last_page,
          order_list:order_list,
          is_load: 0,
          order_num:orderL.total,
          is_search:1,
        });
      }
    }, { type: that.data.type, search: search_input }, '', true);
  },
  clear_value: function (e) {
    this.setData({
      input_value: ''
    })
  },
addCart:function(e){
  var order_sn = e.currentTarget.dataset.order_sn;
  util.api('/api/wxapp/order/Repurchase', function (res) {
    if (res.error == 0) {
      util.toast_success('已加入购物车');
    } else {
      util.showModal('提示', res.message);
    }
  }, {
     order_sn:order_sn,
    });
},

  onIndex:function(e){
    util.navigateTo({ url: '/pages/index/index'})
  },
  returnOrder:function(e){
    var order_sn = e.currentTarget.dataset.order_sn;
    var is_return = e.currentTarget.dataset.is_return
    if (is_return == 0){
      util.navigateTo({ url: '/pages/returnorder/returnorder?order_sn=' + order_sn })
    }else{
      util.navigateTo({
        url: '../return_order_list/return_order_list?order_sn=' + order_sn
      })
    }

  },
  view_goods:function(e){
    var goods_id = e.currentTarget.dataset.gid;
    var integral_goods_id = e.currentTarget.dataset.in_id;
    var goods_type = e.currentTarget.dataset.goods_type;
    if(goods_type == 4){
      util.navigateTo({
        url: '../integralitem/integralitem?integral_goods_id=' + integral_goods_id,
      })
    }else{
      util.navigateTo({
        url: '../item/item?good_id=' + goods_id
      })
    }
  },
  order_request: function (that){
    util.api('/api/wxapp/order/list', function (rest) {
      if (rest.error == 0) {
        var orderL = rest.content;
        that.data.page = orderL.current_page;
        that.data.last_page = orderL.last_page;
        var order_list_r = orderL.data;
        var order_list = [];
        if (order_list_r.length > 0) {
          order_list = order_list_r;
          for (var i in order_list) {
            order_list[i].type_icon = ''
            order_list[i].goods_type = order_list[i].goods_type.split(",");
            for (var j in order_list[i].goods_type) {
              if (order_list[i].goods_type[j] == 1) {
                order_list[i].type_icon = "拼团"
              } else if (order_list[i].goods_type[j] == 3) {
                order_list[i].type_icon = '砍价'
              } else if (order_list[i].goods_type[j] == 5) {
                order_list[i].type_icon = "秒杀"
              } else {
                order_list[i].type_icon == ""
              }
            }
            if (order_list[i].goods_type == 4){
              order_list[i].goods[0].money = parseFloat(order_list[i].goods[0].goods_price - order_list[i].goods[0].goods_score / 100).toFixed(2);
            }
            if (order_list[i].deliver_type == 1 && order_list[i].order_status == 3) {
              order_list[i].order_status_name = "待核销";
            }
            if (order_list[i].deliver_type == 0 && order_list[i].order_status == 3) {
              order_list[i].order_status_name = "待发货";
            }
            if (order_list[i].deliver_type == 1 && order_list[i].order_status == 5) {
              order_list[i].order_status_name = "已自提";
            }
            if (order_list[i].deliver_type == 0 && order_list[i].order_status == 5) {
              order_list[i].order_status_name = "已收货";
            }
            if (order_list[i].pre_sale && order_list[i].pre_sale != null) {
              let start_time = order_list[i].pre_sale.start_time;
              let end_time = order_list[i].pre_sale.end_time;
              order_list[i].pre_sale.start_time = start_time.substring(0, start_time.length - 3);
              order_list[i].pre_sale.end_time = end_time.substring(0, end_time.length - 3);
            }
          }
        }
        that.setData({
          order_list: order_list,
          type: that.data.type,
          transform: that.data.transform,
          disabled_r: that.data.disabled_r,
          disabled_l: that.data.disabled_l,
          page_num: orderL.last_page,
          curpage: orderL.current_page,
          loadingComplete: false,
          searchLoading: false,
        });
        if (orderL.last_page > orderL.current_page) {
          that.setData({
            loadingComplete: false,
            searchLoading: true,
          });
        }
      }
    }, { type: that.data.type }, '', true);
    util.api('/api/wxapp/order/list_data', function (rest) {
      var orderNum;
      if (rest.error == 0) {
        orderNum = rest.content.order_number;
      }
      that.setData({
        orderNum: orderNum
      });
    }, {   })
  },
  to_frined:function(e){
    var orders = e.currentTarget.dataset.order_sn;
    util.jumpLink('pages1/presentdetail/presentdetail?order_sn=' + orders)
  },
});
