// pages/express/express.js
var util = require('../../utils/util.js');
var app = getApp()
var Url = app.globalData.baseUrl;
var imageUrl = app.globalData.imageUrl;
var express_info = [];
var length;
global.wxPage({
  
  /**
   * 页面的初始数据
   */
  data: {
    order_sn: '',
    main_url:Url,
    hiddenModal:true,
    ex_no:'',
    imageUrl:app.globalData.imageUrl,
    order_info: [],
    batch_no:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    var ex_no = options.ex_no;
    var order_sn = options.order_sn;
    if(options.batch_no != ""){
      that.data.batch_no = options.batch_no
    }
    util.api('/api/wxapp/logistics/list',function(rest){
      express_info = rest.content;
      console.log(express_info)
      if (express_info.data){
        express_info.length = express_info.data.length; 
        express_info.height = express_info.data.length*170-146;
      }
      that.setData({
        express_info: express_info
      });
     
    }, { batch_no: that.data.batch_no,order_sn:order_sn});
  
  },
  toOrder:function(e){
    var order_sn = e.currentTarget.dataset.order_sn;
    util.navigateTo({
      url: '/pages/orderinfo/orderinfo?order_sn=' + order_sn,
    })
  },
  onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
  },
})
