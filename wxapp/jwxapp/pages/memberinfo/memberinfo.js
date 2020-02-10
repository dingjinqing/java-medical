var app = new getApp();
var util = require('../../utils/util.js');
var imageUrl = app.globalData.imageUrl;
var mobile = util.getCache('mobile');
var dates;
var region = ['', '', ''];
var regionCode = ['', '', ''];
var real_name = '';
var remarks = '';
var invitation_code = '';
var sex_index = 0;
var act = 0;
var user_block = 0;
var card_no;
var id_num = '';
var marry_index = 0;
var edu_array = [];
var work_arr = [];
var custom_arr = [];
var edu_select = 0; 
var work_select = 0;
var user_nick_name;
var examine = 0;
var distribution = 0;
var is_fullprice = 0;
var code = 0;
var seckillId = 0;
var goods_id = 0;
var gift_id = 0;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    user_info: {},
    is_work: 0,
    dates: '选择您的生日',
    region: ['', '', ''],
    regionCode: ['', '', ''],
    imageUrl: app.globalData.imageUrl,
    array: ['请选择', '男', '女'],
    marry_arr: ['请选择', '未婚', '已婚', '保密'],
    sex_index: 0,
    marry_index: 0,
    act: 0,
    id_num: "",
    edu_array: [],
    work_arr: [],
    edu_select: 0,
    work_select: 0,
    custom_arr: [],
    if_custom: 0,
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
    if_invitation: 0,
    user_nick_name: "",
    save_flag: 1,
    img_len: 0,
    image: false,
    comm_img: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    if (options.is_fullprice) {
      is_fullprice = options.is_fullprice;
    } else {
      is_fullprice = 0
    }
    seckillId = options.seckillId ? options.seckillId : 0;
    code = options.code ? options.code : 0;
    card_no = options.card_no ? options.card_no : 0;
    goods_id = options.goods_id ? options.goods_id : 0;
    gift_id = options.gift_id ? options.gift_id : 0;
    examine = options.examine ? options.examine : 0;
    distribution = options.distribution ? options.distribution : 0;
    remarks = '';
    id_num = '';
    work_select = 0;
    edu_select = 0;
    work_arr = [];
    edu_array = [];
    custom_arr = [];
    sex_index = 0;
    user_nick_name = '';
    real_name = '';
    invitation_code = '';
    marry_index = 0;
    region: ['', '', ''];
    regionCode: ['', '', ''];
    dates: '选择您的生日';
    var that = this;
    that.setData({
      user_block: 0,
      examine: examine,
      distribution: distribution
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
    if (distribution == 1) {
      // console.log(options)
      util.api('/api/wxapp/distribution/distributor/activation', function (res) {
        if (res.error == 0) {
          var user_info = res.content.userBaseInfo;
          // 自定义激活项
          var custom_arr = res.content.cfg.custom_options
          if (custom_arr.length > 0) {
            for (var i in custom_arr) {
              if (custom_arr[i].custom_type == 0) {
                custom_arr[i].custom_select = 0
                for (var j in custom_arr[i].option_arr) {
                  if (custom_arr[i].is_checked == 1) {
                    if (j == 0) {
                      custom_arr[i].option_arr[j].checked = true
                    } else {
                      custom_arr[i].option_arr[j].checked = false
                    }
                  } else {
                    custom_arr[i].option_arr[j].checked = false
                  }
                }
              } else if (custom_arr[i].custom_type == 1) {
                for (var j in custom_arr[i].option_arr) {
                  custom_arr[i].option_arr[j].checked = false
                }
              } else if (custom_arr[i].custom_type == 2) {
                custom_arr[i].text = ''
              }
              that.setData({
                if_custom: 1,
                custom_arr: custom_arr
              })
            }
            
          }
          var fi_arr = res.content.cfg.activation_cfg
          for (var i in fi_arr) {
            if (fi_arr[i] == 'username') {
              that.setData({
                if_username: 1
              })
            }
            if (fi_arr[i] == '手机号') {
              that.setData({
                if_mobile: 1
              })
            }
            if (fi_arr[i] == '真实姓名') {
              that.setData({
                if_realname: 1
              })
            }
            if (fi_arr[i] == '身份证号码') {
              that.setData({
                if_cid: 1
              })
            }
            if (fi_arr[i] == '所在行业') {
              that.setData({
                if_work: 1
              })
            }
            // 分销员分组
            if (fi_arr[i] == '分销员分组' && res.content.groupList) {
              var group_arr = [];
              var group_select = 0;
              for (var i in res.content.groupList) {
                group_arr.push(res.content.groupList[i].groupName)
                // if (res.content.default_invite_group == that.data.rebate_groups[i].id) {
                //   group_select = i;
                // }
              }
              if (group_arr.length) {
                that.setData({
                  rebate_groups: res.content.groupList,
                  group_arr: group_arr,
                  group_select: group_select,
                  if_rebate_group: 1
                })
              }
            }
            if (fi_arr[i] == '所在地') {
              that.setData({
                if_citydoce: 1
              })
            }
            if (fi_arr[i] == '性别') {
              that.setData({
                if_sex: 1
              })
            }
            if (fi_arr[i] == '生日') {
              that.setData({
                if_birthdayyear: 1
              })
            }
            if (fi_arr[i] == '婚姻状况') {
              that.setData({
                if_mar: 1
              })
            }
            if (fi_arr[i] == '教育程度') {
              that.setData({
                if_edu: 1
              })
            }
            if (fi_arr[i] == '备注') {
              that.setData({
                if_remark: 1
              })
            }
            if (fi_arr[i] == '图片上传') {
              that.setData({
                if_upImage: 1
              })
            }
            if (fi_arr[i] == '邀请码') {
              that.setData({
                if_invitation: 1
              })
            }
          }


          // 会员昵称
          if (user_info.username) {
            user_nick_name = user_info.username
          }
          // 真实姓名
          if (user_info.realName) {
            real_name = user_info.realName
          }
          // 身份证
          if (user_info.cid) {
            id_num = user_info.cid;
            that.setData({
              id_num: id_num,
            })
          }
          // 所在行业
          if (res.content.industryList) {
            res.content.industryList.unshift({
              value: 0,
              label: "请选择"
            })
            for (var i in res.content.industryList) {
              work_arr.push(res.content.industryList[i].label)
            }
            if (user_info.industryInfo == null) {
              work_select = 0;
            } else {
              work_select = user_info.industryInfo;
            }
            that.setData({
              work_arr: work_arr,
              work_select: work_select,
            })
          }
          //所在地
          if (user_info.cityCode) {
            regionCode[0] = user_info.provinceCode;
            regionCode[1] = user_info.cityCode;
            regionCode[2] = user_info.districtCode;
            region[0] = user_info.provinceCode;
            region[1] = user_info.cityCode;
            region[2] = user_info.districtCode;
            that.setData({
              region: region,
            })
          }
          //性别
          if (user_info.sex) {
            if (user_info.sex == "女") {
              sex_index = 2;
            } else {
              sex_index = 1;
            }
            that.setData({
              sex_index: sex_index,
            })
          }
          //生日
          if (user_info.birthdayDay != null && user_info.birthdayDay != 0) {
            if (parseInt(user_info.birthdayMonth) < 10) {
              user_info.birthdayMonth = '0' + user_info.birthdayMonth;
            }
            if (parseInt(user_info.birthdayDay) < 10) {
              user_info.birthdayDay = '0' + user_info.birthdayDay;
            }
            dates = user_info.birthdayYear + '-' + user_info.birthdayMonth + '-' + user_info.birthdayDay;
            that.setData({
              dates: dates,
            })
          }
          // 婚姻状况
          if (user_info.maritalStatus) {
            if (user_info.maritalStatus == null) {
              marry_index = 0;
            } else {
              marry_index = user_info.maritalStatus;
            }
            that.setData({
              marry_index: marry_index
            })
          }
          // 教育程度
          if (res.content.educationList) {
            res.content.educationList.unshift({
              value: 0,
              label: "请选择"
            })
            for (var i in res.content.educationList) {
              edu_array.push(res.content.educationList[i].label)
            }
            if (user_info.education == null) {
              edu_select = 0;
            } else {
              edu_select = user_info.education;
            }
            that.setData({
              edu_array: edu_array,
              edu_select: edu_select,
            })
          }
          // 邀请码
          if (user_info.invitationCode) {
            invitation_code = user_info.invitationCode
          }

          that.setData({
            user_info: user_info,
            mobile: mobile,
          })
        } else {
          util.showModal("提示", res.message, function () {
            util.redirectTo({
              url: '/pages/distributionspread/distributionspread'
            })
          }, false);
          return false
        }

      }, {})

    }
  },
  toSave: function (e) {
    var that = this;
    setTimeout(function () {
      that.bind_submit(e)
    }, 100);
  },
  // 提交审核申请
  bind_submit: function (e) {
    var user_info = {};
    var that = this;
    // 会员昵称
    if (this.data.if_username == 1) {
      user_info.username = user_nick_name
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
      user_info.real_name = real_name;
      if (user_info.real_name == '') {
        util.showModal("提示", "请输入真实姓名");
        return false;
      }
    }
    // 身份证
    if (this.data.if_cid == 1) {
      if (id_num == 'null' || id_num == undefined) {
        id_num = "";
      }
      var re = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
      if (id_num.replace(/^\s+|\s+$/g, '') == "") {
        util.showModal("提示", "请输入正确的身份证号");
        return false;
      }
      if (!(re.test(id_num)) || id_num.replace(/^\s+|\s+$/g, '').length != 18) {
        util.showModal("提示", "请输入正确的身份证号");
        return false;
      }
      user_info.cid = id_num;
    }
    // 所在行业
    if (this.data.if_work == 1) {
      user_info.industry_info = work_select;
    }
    // 分销员分组
    if (this.data.if_rebate_group == 1) {
      user_info.rebate_group = this.data.rebate_groups[this.data.group_select].id
    }
    //所在地
    if (this.data.if_citydoce == 1) {
      user_info.province_code = regionCode[0];
      user_info.city_code = regionCode[1];
      user_info.district_code = regionCode[2];
      user_info.address = "";
    }
    //性别
    if (this.data.if_sex == 1) {
      if (sex_index == 1) {
        user_info.sex = 'm';
      } else if (sex_index == 2) {
        user_info.sex = 'f';
      }
    }
    //生日
    if (this.data.if_birthdayyear == 1 && dates != undefined) {
      var date_arr = dates.split('-');
      user_info.birthday_year = date_arr[0];
      user_info.birthday_month = date_arr[1];
      user_info.birthday_day = date_arr[2];
    }
    // 婚姻状况
    if (this.data.if_mar == 1) {
      user_info.marital_status = marry_index;
    }
    // 教育程度
    if (this.data.if_edu == 1) {
      user_info.education = edu_select;
    }
    // 备注
    if (this.data.if_remark == 1) {
      user_info.remarks = remarks;
    }
    // 上传图片
    if (this.data.if_upImage == 1) {
      user_info.upload_image = that.data.comm_img[0];
    }
    // 邀请码
    if (this.data.if_invitation == 1) {
      user_info.invitation_code = invitation_code
    }
    // 自定义激活项
    var custom_arr = that.data.custom_arr
    var custom_options = []
    for (var i in custom_arr) {
      if (custom_arr[i].is_checked == 1) {
        // 必填项
        if (custom_arr[i].option_ver == 1) {
          if (custom_arr[i].custom_type == 0 || custom_arr[i].custom_type == 1) {
            var result = custom_arr[i].option_arr.some(function (item) {
              if (item.checked == true) {
                return true
              } else {
                return false;
              }
            })
            if (result == false) {
              util.showModal("提示", "请填写" + custom_arr[i].custom_title);
              return;
            }
          } else if (custom_arr[i].custom_type == 2 && custom_arr[i].text == '') {
            util.showModal("提示", "请填写" + custom_arr[i].custom_title);
            return;
          }
        }
        custom_options.push(custom_arr[i])
        user_info.custom_options = custom_options
      }
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
    if (user_info.city_code == "" && this.data.if_citydoce == 1) {
      util.showModal("提示", "请选择所在地");
      return;
    }
    if (sex_index == 0 && this.data.if_sex == 1) {
      util.showModal("提示", "请选择性别");
      return;
    }
    if (user_info.birthday_year == null && this.data.if_birthdayyear == 1) {
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
    if (user_info.remarks == '' && this.data.if_remark == 1) {
      util.showModal("提示", "请输入备注内容");
      return;
    }
    if ((user_info.upload_image == '' || user_info.upload_image == undefined) && this.data.if_upImage == 1) {
      util.showModal("提示", "请上传图片");
      return;
    }
    if (user_info.invitation_code == '' && this.data.if_invitation == 1) {
      util.showModal("提示", "请输入邀请码");
      return
    }

    if (!distribution) user_info.card_no = card_no;

    if (that.data.save_flag == 1) {
      that.setData({
        save_flag: 0
      })
      if (distribution == 1) {
        console.log(user_info)
        util.api('/api/wxapp/distribution/distributor/apply', function (res) {
          if (res.error == 0) {
            if (res.error == -1) {
              // 申请不成功, 邀请码不存在
              util.showModal("提示", "邀请码不存在");
            }
            util.redirectTo({
              url: '/pages/distributionspread/distributionspread'
            })
          }
        }, {
          activationFields: user_info,
          configFields: "[]"
        })
        
      } else {
        // util.api('/api/wxapp/activation/card', function (res) {
        //   // console.log(res)
        //   if (res.error == 0) {
        //     if (examine == 0) {
        //       util.toast_success('激活成功', function () {
        //         if (is_fullprice == 0 && code == 0 && seckillId == 0 && goods_id == 0) {
        //           setTimeout(function () {
        //             util.redirectTo({
        //               url: '/pages/usercardinfo/usercardinfo?card_list=1&card_no=' + card_no
        //             })
        //           }, 2000);
        //         } else if (parseInt(gift_id)) {
        //           util.redirectTo({
        //             url: 'pages1/presentchoose/presentchoose?gift_id=' + gift_id,
        //           })
        //         } else if (parseInt(is_fullprice)) {
        //           util.redirectTo({
        //             url: '/pages/fullprice/fullprice?identity_id=' + is_fullprice,
        //           })
        //         } else if (parseInt(seckillId)) {
        //           util.redirectTo({
        //             url: '/pages/seckillitem/seckillitem?sk_id=' + seckillId,
        //           })
        //         } else if (parseInt(goods_id)) {
        //           util.redirectTo({
        //             url: '/pages/item/item?good_id=' + goods_id,
        //           })
        //         } else {
        //           util.redirectTo({
        //             url: '/pages/getcoupon/getcoupon?code=' + code,
        //           })
        //         }
        //       });
        //     } else {
        //       util.toast_success('申请已提交', function () {
        //         setTimeout(function () {
        //           util.redirectTo({
        //             url: '/pages/usercardinfo/usercardinfo?card_list=1&card_no=' + card_no
        //           })
        //         }, 2000);
        //       });
        //     }
        //   } else {
        //     util.showModal("提示", res.message);
        //     return;
        //   }
        // }, user_info)
      }
    } else {
      util.showModal("提示", '请勿重复提交');
    }
  },
  bindDateChange: function (e) {
    dates = e.detail.value;
    this.setData({
      dates: e.detail.value
    })
  },
  bindRegionChange: function (e) {
    region = e.detail.value;
    regionCode = e.detail.code;
    this.setData({
      region: e.detail.value,
      regionCode: e.detail.code
    })
  },
  bindSexChange: function (e) {
    sex_index = e.detail.value;
    this.setData({
      sex_index: e.detail.value
    })
  },
  bindMarryChange: function (e) {
    marry_index = e.detail.value;
    this.setData({
      marry_index: e.detail.value
    })
  },
  bindEduChange: function (e) {
    edu_select = e.detail.value;
    this.setData({
      edu_select: edu_select
    })
  },
  bindWorkChange: function (e) {
    work_select = e.detail.value;
    this.setData({
      work_select: work_select
    })
  },
  bindGroupChange: function (e) {
    this.setData({
      group_select: e.detail.value
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
    real_name = e.detail.value;
  },
  // 备注
  remarkBlur: function (e) {
    remarks = e.detail.value;
  },
  //上传图片
  upImage: function (e) {
    var that = this;
    var comm_img = that.data.comm_img;
    util.uploadImage(1, function (res) {
      let data = JSON.parse(res.data);
      console.log(data)
      if (data.error == 0) {
        comm_img.push(data.content.imgUrl);
        that.setData({
          comm_img: comm_img,
          image: true,
        })
      }
    });
  },
  // 邀请码
  invitationCode: function (e) {
    if (e.detail.value == "") {
      util.showModal("提示", "请填写邀请码");
    }
    invitation_code = e.detail.value;
  },
  // 自定义单选
  bindRadioChange: function (e) {
    var that = this
    var index = e.target.dataset.index
    var custom_arr = that.data.custom_arr
    var custom_select = e.detail.value

    custom_arr[index].custom_select = custom_select
    for (var i in custom_arr[index].option_arr) {
      if (i == custom_select) {
        custom_arr[index].option_arr[i].checked = true
      } else 
        custom_arr[index].option_arr[i].checked = false
    }
    

    that.setData({
      custom_arr: custom_arr
    })
  },
  // 自定义多选
  checkboxChange: function (e) {
    var that = this
    var index = e.target.dataset.index
    var custom_arr = that.data.custom_arr
    var valueList = e.detail.value

    custom_arr[index].option_arr.find((item, index) => {
      item.checked = false
      valueList.find((val, key) => {
        if (val == item.option_title) {
          item.checked = true
        }
      })
    })
   
    that.setData({
      custom_arr: custom_arr
    })
  },
  // 自定义文本
  textBlur: function (e) {
    var that = this
    var index = e.target.dataset.index
    var custom_arr = that.data.custom_arr
    var value = e.detail.value

    if (value == '') {
      util.showModal('提示', "请输入内容！");
      return false;
    } else {
      custom_arr[index].text = value
    }

    that.setData({
      custom_arr: custom_arr
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
    id_num = e.detail.value;
  },
  // 授权手机号
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
        util.setCache("mobile", res.content);
        mobile = res.content;
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
    }, { 
      iv: iv, 
      encrypted_data: data
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
    wx.stopPullDownRefresh();
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
