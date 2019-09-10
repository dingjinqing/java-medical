var app = new getApp();
var util = require('../../utils/util.js');
var imageUrl = app.globalData.imageUrl;
var mobile = util.getCache('mobile');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    user_info: {},
    is_work: 0,
    dates: '',
    region: ['', '', ''],
    imageUrl: app.globalData.imageUrl,
    array: ['请选择', '男', '女'],
    marry_arr: ['请选择', '未婚', '已婚', '保密'],
    sex_index: 0,
    marry_index: 0,
    act: 0,
    id_num: "",
    real_name:"",
    edu_array: ["请选择", "初中", "高中", "中专", "大专", "本科", "硕士", "博士", "其他"],
    work_arr: ["请选择", "计算机硬件及网络设备", "计算机软件", "IT服务（系统/数据/维护）/多领域经营", "互联网/电子商务", "网络游戏", "通讯（设备/运营/增值服务）", "电子技术/半导体/集成电路", "仪器仪表及工业自动化", "金融/银行/投资/基金/证券", "保险", "房地产/建筑/建材/工程", "家居/室内设计/装饰装潢", "物业管理/商业中心", "广告/会展/公关/市场推广", "媒体/出版/影视/文化/艺术", "印刷/包装/造纸", "咨询/管理产业/法律/财会", "教育/培训", "检验/检测/认证", "中介服务", "贸易/进出口", "零售/批发", "快速消费品（食品/饮料/烟酒/化妆品", "耐用消费品（服装服饰/纺织/皮革/家具/家电）", "办公用品及设备", "礼品/玩具/工艺美术/收藏品", "大型设备/机电设备/重工业", "加工制造（原料加工/模具）", "汽车/摩托车（制造/维护/配件/销售/服务）", "交通/运输/物流", "医药/生物工程", "医疗/护理/美容/保健", "医疗设备/器械", "酒店/餐饮", "娱乐/体育/休闲", "旅游/度假", "石油/石化/化工", "能源/矿产/采掘/冶炼", "电气/电力/水利", "航空/航天", "学术/科研", "政府/公共事业/非盈利机构", "环保", "农/林/牧/渔", "跨领域经营", "其它"],
    edu_select: 0,
    work_select: 0,
    if_username: 0,
    if_mobile: 0,
    if_realname: 0,
    if_cid: 0,
    if_work: 0,
    if_citydoce: 0,
    if_sex: 0,
    if_birthdayyear: 0,
    if_mar: 0,
    if_edu: 0,
    if_remark: 0,
    if_upImage: 0,
    user_nick_name: "",
    save_flag: 1,
    img_len: 0,
    image: false,
    comm_img: [],
    mobile:mobile,
    drawStatus:0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    that.setData({
      user_block: 0,
      assess_id: options.assess_id,
      record_id: options.record_id,
      result_id: options.result_id,
      reward_type: options.reward_type
    })
    wx.showLoading({
      title: '加载中',
    })
    setTimeout(function () {
      wx.hideLoading();
      that.setData({
        user_block: 1
      })
    }, 500)
    let fi_arr = options.condition.split(',');
    for (var i in fi_arr) {
      if (fi_arr[i] == '1') {
        that.setData({
          if_realname: 1
        })
      }
      if (fi_arr[i] == '2') {
        that.setData({
          if_mobile: 1
        })
      }
      if (fi_arr[i] == '3') {
        that.setData({
          if_cid: 1
        })
      }
      if (fi_arr[i] == '4') {
        that.setData({
          if_sex: 1
        })
      }
      if (fi_arr[i] == '5') {
        that.setData({
          if_birthdayyear: 1
        })
      }
      if (fi_arr[i] == '6') {
        that.setData({
          if_mar: 1
        })
      }
      if (fi_arr[i] == '7') {
        that.setData({
          if_edu: 1
        })
      }
      if (fi_arr[i] == '8') {
        that.setData({
          if_work: 1
        })
      }
      if (fi_arr[i] == '9') {
        that.setData({
          if_citydoce: 1
        })
      }
    }

  },
  toSave: function (e) {
    var that = this;
    setTimeout(function () {
      that.bind_submit(e)
    }, 100);
  },
  bind_submit: function (e) {
    var user_info = {};
    var that = this;
    user_info.is_setting = 1;
    user_info.user_id = util.getCache('user_id');
    user_info.open_id = util.getCache("openid");
    user_info.form_id = e.detail.formId;
    // 会员昵称
    if (this.data.if_username == 1) {
      user_info.username = this.data.user_nick_name
    }
    // 手机号
    if (this.data.if_mobile == 1) {
      user_info.mobile = mobile;
    }
    //真实名字
    if (this.data.if_realname == 1) {
      if (user_info.real_name == "null" || user_info.real_name == undefined) {
        user_info.real_name = '';
      }
      user_info.real_name = this.data.real_name;
      if (user_info.real_name == '') {
        util.showModal("提示", "请输入真实姓名");
        return false;
      }
    }
    // 身份证
    if (this.data.if_cid == 1) {
      if (this.data.id_num == 'null' || this.data.id_num == undefined) {
        this.setData({
          id_num : ""
        })
      }
      var re = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
      if (this.data.id_num.replace(/^\s+|\s+$/g, '') == "") {
        util.showModal("提示", "请输入正确的身份证号");
        return false;
      }
      if (!(re.test(this.data.id_num)) || this.data.id_num.replace(/^\s+|\s+$/g, '').length != 18) {
        util.showModal("提示", "请输入正确的身份证号");
        return false;
      }
      user_info.cid = this.data.id_num;
    }
    // 所在行业
    if (this.data.if_work == 1) {
      user_info.industry_info = this.data.work_select;
    }
    //所在地
    if (this.data.if_citydoce == 1) {
      user_info.province_code = this.data.region[0];
      user_info.city_code = this.data.region[1];
      user_info.district_code = this.data.region[2];
      user_info.address = "";
    }
    //性别
    if (this.data.if_sex == 1) {
      if (this.data.sex_index == 1) {
        user_info.sex = 'm';
      } else if (this.data.sex_index == 2) {
        user_info.sex = 'f';
      }
    }
    //生日
    if (this.data.if_birthdayyear == 1 && this.data.dates != undefined) {
      var date_arr = this.data.dates.split('-');
      user_info.birthday_year = date_arr[0];
      user_info.birthday_month = date_arr[1];
      user_info.birthday_day = date_arr[2];
    }
    // 婚姻状况
    if (this.data.if_mar == 1) {
      user_info.marital_status = this.data.marry_index;
    }
    // 教育程度
    if (this.data.if_edu == 1) {
      user_info.education = this.data.edu_select;
    }
    //激活
    if (user_info.real_name == "" && this.data.if_realname == 1) {
      util.showModal("提示", "请填写真实姓名");
      return;
    }
    if (user_info.mobile == '' && this.data.if_mobile == 1) {
      util.showModal("提示", "请授权手机号");
      return;
    }
    if (user_info.cid == "" && this.data.if_cid == 1) {
      util.showModal("提示", "请输入正确的身份证号");
      return;
    }
    if (this.data.sex_index == 0 && this.data.if_sex == 1) {
      util.showModal("提示", "请选择性别");
      return;
    }
    if (user_info.birthday_year == "" && this.data.if_birthdayyear == 1) {
      util.showModal("提示", "请选择您的生日");
      return;
    }
    if (user_info.marital_status == 0 && this.data.if_mar == 1) {
      util.showModal("提示", "请选择婚姻状况");
      return;
    }
    if (user_info.education == 0 && this.data.if_edu == 1) {
      util.showModal("提示", "请选择教育程度");
      return;
    }
    if (user_info.industry_info == 0 && this.data.if_work == 1) {
      util.showModal("提示", "请选择所在行业");
      return;
    }
    if (user_info.city_code == "" && this.data.if_citydoce == 1) {
      util.showModal("提示", "请选择所在地");
      return;
    }
    if (that.data.save_flag == 1) {
      this.getGift(user_info)
    } else {
      util.showModal("提示", '请勿重复提交');
    }
  },
  bindDateChange: function (e) {
    var dates = e.detail.value;
    this.setData({
      dates: e.detail.value
    })
  },
  bindRegionChange: function (e) {
    var region = e.detail.value;
    this.setData({
      region: e.detail.value
    })
  },
  bindSexChange: function (e) {
    var sex_index = e.detail.value;
    this.setData({
      sex_index: e.detail.value
    })
  },
  bindMarryChange: function (e) {
    var marry_index = e.detail.value;
    this.setData({
      marry_index: e.detail.value
    })
  },
  bindEduChange: function (e) {
    var edu_select = e.detail.value;
    this.setData({
      edu_select: edu_select
    })
  },
  bindWorkChange: function (e) {
    var work_select = e.detail.value;
    this.setData({
      work_select: work_select
    })
  },
  inputBlur: function (e) {
    if (/^1[3456789]\d{9}$/.test(e.detail.value)) {
      mobile = e.detail.value;
    } else {
      util.showModal('提示', "请输入正确的手机号！");
      return false;
    }
  },
  realName: function (e) {
    if (e.detail.value.replace(/^\s+|\s+$/g, '') == "") {
      util.showModal("提示", "请填写真实姓名");
    }
    this.setData({
      real_name : e.detail.value
    })
  },
  // 身份证号
  IDnum: function (e) {
    var re = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
    if (e.detail.value.replace(/^\s+|\s+$/g, '') == "") {
      util.showModal("提示", "请输入正确的身份证号");
    }
    if (!(re.test(e.detail.value)) || e.detail.value.replace(/^\s+|\s+$/g, '').length != 18) {
      util.showModal("提示", "请输入正确的身份证号");
    }
    this.setData({
      id_num : e.detail.value
    })
  },
  getPhoneNumber: function (e) {
    var that = this;
    if (e.detail.errMsg == 'getPhoneNumber:ok') {
      var iv = e.detail.iv;
      var encryptedData = e.detail.encryptedData;
      util.checkSession(function () {
        that.parseMobile(iv, encryptedData);
      })
    }
  },
  // 解析手机号
  parseMobile: function (iv, data) {
    var that = this;
    util.api('/api/wxapp/wxdecrypt', function (res) {
      if (res.error == 0) {
        util.setCache("mobile", res.content.phoneNumber);
        mobile = res.content.phoneNumber;
        that.setData({
          mobile: mobile,
        })
      } else if (res.error == 41001) {
        util.wxLogin(function () {

        })
      } else {
        util.showModal('提示', '授权失败，请重试！', function () {
        }, false);
      }
    }, { iv: iv, crypt_data: data })
  },
  getGift(userinfo) {
    var that = this;
    if (!this.data.drawStatus) {
      util.api('/api/wxapp/assess/getAward', function (res) {
        if (res.error == 0) {
          if (res.content.status == 0) {
            let gift_info = '';
            if (that.data.reward_type == 2) {
              gift_info = { ...res.content }
            } else {
              gift_info = res.content.desc
            }
            that.setData({
              is_award: 1,
              gift_info: gift_info,
              drawStatus: 1,
              save_flag:0
            })
          } else {
            util.showModal('提示', '奖品已赠完');            
          }
        }
      }, { assess_id: this.data.assess_id, result_id: this.data.result_id, record_id: this.data.record_id,...userinfo})
    } else {
      util.showModal('提示', '已领取过奖品');
    }
  },
  to_get_gift() {
    var params = {};
    params.goods_id = this.data.gift_info.goods_id;
    params.goods_price = 0;
    params.user_id = util.getCache('user_id');
    params.goods_number = 1;
    params.prd_sn = this.data.gift_info.prd_sn;
    params.prd_id = this.data.gift_info.prd_id;
    params.product_id = this.data.gift_info.prd_id;
    params.lc_id = this.data.gift_info.lc_id;
    var query_param = {};
    query_param.goods_id = this.data.gift_info.goods_id;
    console.log(JSON.stringify(params));
    util.navigateTo({
      url: '/pages/goodsCheckout/goodsCheckout?order_type=assess_present&choose_list=' + JSON.stringify(params) + '&query_param=' + JSON.stringify(query_param),
    })
  },
  guandiao1() {
    this.setData({
      is_award: 0
    })
    wx.navigateBack()
  },
  giftInfo() {
    const url = new Map([
      [1, ['pages/couponlist/couponlist']],
      [2, ['pages/lotterygift/lotterygift']],
      [3, ['pages/integral/integral']],
      [4, ['pages/account/account']]
    ])
    let action = url.get(this.data.reward_type)
    util.jumpLink(action[0], 'navigateTo')
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
    wx.stopPullDownRefresh();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },
})
