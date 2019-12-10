// pages/distributionspread/distributionspread.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var mobile = util.getCache('mobile');
var dis_content = [];
var status = null; // 分销状态
var is_block = 0;
var is_bind_mobile;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    is_block: 0,
    is_authorize: 0,
    has_apply: false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    is_block = 0;
    dis_request(that);
  },
  // 申请成为分销员
  apply_get: function (e) {
    util.navigateTo({
      url: '/pages/memberinfo/memberinfo?distribution=1'
    })
    // var form_id = e.detail.formId;
    // var open_id = util.getCache("openid");
    // var that = this;
    // if (is_bind_mobile == 1 && mobile == "") {
    //   util.checkSession(function () {
    //     that.setData({
    //       is_block: is_block = 1
    //     })
    //   })
    //   return false;
    // }
    // if (that.data.has_apply) return false;
    // that.data.has_apply = true;
    // if (that.data.dis_content.activation == 1) {
    //   util.navigateTo({
    //     url: '/pages/memberinfo/memberinfo?distribution=1'
    //   })
    // } else {
    //   util.api('/api/wxapp/distributor/apply', function (res) {
    //     if (res.error == 0) {
    //       dis_request(that);
    //     } else {
    //       dis_request(that);
    //     }
    //     that.data.has_apply = false;
    //   }, {
    //       form_id: form_id,
    //       open_id: open_id
    //     });
    // }
  },
  // 进入分销中心
  to_distribution: function () {
    util.navigateTo({
      url: '/pages/distribution/distribution',
    })
  },

  bindGetPhoneNumberOk: function (e) {
    mobile = e.detail.phoneNumber;
    this.setData({
      is_authorize: 1,
      is_block: 0
    })
  },
  close_authorize: function () {
    this.setData({
      is_authorize: 0
    })
  },


  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    return {
      path: "/pages/distributionspread/distributionspread",

    }
  }
})



function dis_request(that) {
  // dis_content = {
  //   activation: 1,
  //   activation_cfg: ["rebate_group", "remarks"],
  //   distribution_apply: {
  //     activation_fields: {"rebate_group":12,"remarks":"123456"},
  //     add_time: "2019-10-15 09:54:24",
  //     config_fields: ["rebate_group","remarks"],
  //     del_flag: 0,
  //     id: 230,
  //     msg: null,
  //     status: 1,
  //     update_time: "0000-00-00 00:00:00",
  //     user_id: 3184
  //   },
  //   documents: {
  //     document: `
  //     <p style='color:#e53333;font-size:18px;font-weight:bold;'>
  //       <span style='font-size:12px;font-weight:normal;'>小伙伴，欢迎加入我们</span>
  //     </p>
  //       <p><br /></p>
  //       <p>我们是XXX运营团队，竭诚为你的销售工作提供完善的支持。</p>
  //       <p><br /></p>
  //       <p>我们诚挚邀请你加入我们的分销员推广计划，无任何成本即可成为XXX的分销员，一起分享收获的喜悦。你只需将高品质商品分享推荐给他人，收获他人的感谢的同时，挣得属于自己的利润。</p>
  //       <p><br /></p>
  //       <p>1. 返利说明</p>
  //       <p>1）买家购买返利商品，下单支付成功，则邀请该用户注册的分销员可获得佣金返利；佣金值=商品实际支付金额*佣金比例。</p>
  //       <p>2）邀请新用户注册，或者分享给已经注册的但没有邀请人的用户都算作该分销员邀请的用户。</p>
  //       <p>3）订单支付成功做返利佣金为待返利状态，交易完成则该佣金返利成功，自动提现到分销员的用户余额中。交易完成前发生退款的订单，相应的分销员返利佣金为已退款返利失败状态。</p>
  //       <p>4）仅在线支付的订单算作业绩，即微信支付、余额支付（会员卡余额、用户余额）、货到付款的订单计算返利佣金。例如买家购买返利商品A价格为100元，该买家使用了20元的优惠券，其余金额使用微信支付，返利比例为10%，则该邀请该买家的分销员可获得返利佣金为（100-20）*10%=8元。</p>
  //       <p>5）买家仅返利商品，该买家的邀请人可获得返利佣金，购买普通商品不返利。</p>
  //       <p><br /></p>
  //       <p>2. 结算说明</p>
  //       <p>1）返利佣金比例为X%。</p>
  //       <p>2）返利有效期X50，（用户被分销员邀请注册开始计算，在该天数限制内该用户购买分销商品给分销员计算佣金返利，一旦超过该天数，则不再给分销员佣金返利)。</p>
  //       <p><br /></p>
  //       <p>3. 其他说明</p>
  //       <p>1）分享前，请确定商品页面有【···】按钮；</p>
  //       <p>2）销售过程中有任何疑问，请直接联系商家；</p>
  //       <p>3）已售出商品的任何售后问题，由本商城处理；</p>
  //       <p>4）不传播或者扩散有关于政治、色情等任何违法的信息，一经发现，则立即封号，如果触犯任何法律相关问题，商城不负任何责任；</p>
  //       <p>5）以上内容解释权归本商城所有。</p>
  //       <p>4. 联系方式</p>
  //       <p>1）联络人</p>
  //       <p>2）手机：1234567889</p>
  //       <p>3）分销员QQ交流群：12345678</p>
  //       <p>4）邮箱：xxx@xxx.com</p>
  //       <p>无论是销售、对账，还是结算问题，请通过以上方式咨询。</p>
  //       <p><br /></p>
  //     `,
  //     title: "分销员推广测试",
  //     update_time: 1563247089,
  //   },
  //   is_bind_mobile: "0",
  //   user: {
  //     account: "0.00",
  //     ali_user_id: "",
  //     create_time: "2019-10-14 11:28:15",
  //     cumulative_money: "0.00",
  //     cumulative_score: 234,
  //     delete_time: null,
  //     device: "iOS 10.0.1",
  //     discount: 0,
  //     discount_grade: 0,
  //     distributor_level: 1,
  //     email: null,
  //     fanli_grade: 0,
  //     get_collect_gift: 1,
  //     growth: 0,
  //     invitation_code: 0,
  //     invite: 0,
  //     invite_act_id: 0,
  //     invite_expiry_date: null,
  //     invite_group: 0,
  //     invite_id: 0,
  //     invite_protect_date: null,
  //     invite_source: null,
  //     invite_time: null,
  //     is_delete: 0,
  //     is_distributor: 0,
  //     look_collect_time: null,
  //     mobile: null,
  //     scene: -4,
  //     score: 244,
  //     shop_id: 0,
  //     source: 0,
  //     unit_price: "0.00",
  //     update_time: "2019-10-15 11:31:34",
  //     user_cid: "",
  //     user_code: null,
  //     user_grade: 1,
  //     user_id: 3184,
  //     user_pwd: "",
  //     username: "用户13184",
  //     wechat: "",
  //     wx_openid: "o-2MM5E3hvudxtrnXPBBZsr0WvWk",
  //     wx_union_id: "",
  //     wx_unionid: ""
  //   }
  // };
  // is_bind_mobile = dis_content.is_bind_mobile;
  // that.setData({
  //   dis_content: dis_content,
  //   is_block: is_block
  // })
  // that.setData({
  //   page_name: dis_content.documents.title
  // })
  // if (dis_content.documents.document != null) {
  //   that.setData({
  //     dis_desc: util.filterRichText(dis_content.documents.document)
  //   });
  // }

  // 查看最新的审核状态
  util.api('/api/wxapp/distribution/distributor/apply/detail', function (res) {
    if (res.error === 0 && res.content) {
      console.log(res.content.status)
      that.setData({
        status: res.content.status,
      })
    }
  }, {});


  // util.api('/api/wxapp/distributor/document', function (res) {
  //   dis_content = res.content;
  //   is_bind_mobile = res.content.is_bind_mobile;
  //   that.setData({
  //     dis_content: dis_content,
  //     is_block: is_block
  //   })
  //   that.setData({
  //     page_name: dis_content.documents.title
  //   })
  //   if (dis_content.documents.document != null) {
  //     that.setData({
  //       dis_desc: util.filterRichText(dis_content.documents.document)
  //     });
  //   }
  // }, {});
}
