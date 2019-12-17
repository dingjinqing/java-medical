// components/popup/get-phone-number/index.js
var util = require("../../../utils/util.js");
var base = require("../base/base.js")

global.wxComponent({
  mixins: [base],

  /**
   * 组件的属性列表
   */
  properties: {
    content: {
      type: String,
      value: "您需要绑定手机号才可以去购买商品"
    }
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    bindGetPhoneNumber(e) {
      var _this = this;
      if (e.detail.errMsg == "getPhoneNumber:ok") {
        this.setData({
          show: false
        })
        util.api('/api/wxapp/wxdecrypt', function (res) {
          if (res.error == 0) {
            var mobile = res.content.phoneNumber;
            util.setCache("mobile", mobile);
            _this.$emit("get_phone_number_ok", res.content);
            return;
          } else if (res.error == 41001) {
            util.wxLogin(function () {
              console.log("please retry get phone number");
              _this.$emit("error", res);
            })
          } else {
            _this.$emit("error", res);
          }

        }, {
          iv: e.detail.iv,
          crypt_data: e.detail.encryptedData
        })
      }
    }
  }
})
