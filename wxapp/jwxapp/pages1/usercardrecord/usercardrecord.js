// pages/usercardrecord/usercardrecord.js
var util = require('../../utils/util.js');
var app = getApp()
var Url = app.globalData.baseUrl;
var is_submit = false;
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: util.getCache("imageHost"),
    card_no: '',
    record_list: [],
    page: 1,
    last_page: 0,
    show_type: -1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad (options) {
    if (!util.check_setting(options)) return;
    console.log(options)
    this.setData({
      record_list: [],
      cardNo: options.cardNo
    })
    this.get_accout();
  },
  useDetail () {
    console.log('左')
    this.setData({
      page: 1,
      show_type: 1,
      record_list: []
    })
    this.get_accout();
  },
  chargeDetail () {
    console.log('右')
    this.setData({
      page: 1,
      show_type: -1,
      record_list: []
    })
    this.get_accout();
  },
  get_accout () {
    let that = this
    if (is_submit) return;
    is_submit = true;
    console.log(that.data)
    util.api('/api/wxapp/card/use', function (res) {
      console.log(res)
      // var show_type = that.data.show_type;
      if (res.error == 0) {
        // if (res.content.card_type == 0) {
        //   var title = '可用余额';
        //   var show_number = res.content.money;

        // } else if (res.content.card_type == 1) {
        //   var title = '剩余可使用服务次数';
        //   var show_number = res.content.surplus;
        //   if (res.content.is_exchang > 0) {
        //     var title2 = '剩余可兑换商品次数';
        //     var show_number2 = res.content.exchang_surplus;
        //   }
        // }
        // that.data.page = res.content.page.currentPage;
        // that.data.last_page = res.content.page.lastPage;
        // that.data.record_list = that.data.record_list.concat(res.content.dataList);
        that.setData({
          // title: title,
          // show_number: show_number,
          // card_type: res.content.card_type,
          // title2: title2 ? title2 : '',
          // show_number2: show_number2 ? show_number2 : '',
          page: res.content.page.currentPage,
          last_page: res.content.page.lastPage,
          record_list: that.data.record_list.concat(res.content.dataList)
        })

      } else {
        is_submit = false;
        util.showModal('提示', res.message, function (res) {

        }, false);
      }
      is_submit = false;
    }, { cardNo: that.data.cardNo, showType: that.data.show_type, currentPage: that.data.page, pageRows: 20 })
  },
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom () {
    console.log(this.data.page, this.data.last_page)
    if (this.data.page == this.data.last_page) return;
    this.setData({
      page: this.data.page + 1
    })
    this.get_accout();
  }
})
