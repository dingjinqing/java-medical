
var app = new getApp();
var util = require('../../utils/util.js');
var mobile = util.getCache('mobile');
var dates;
var region = ['', '', ''];
var real_name = '';
var remarks = '';
var sex_index = 0;
var card_no;
var id_num = '';
var marry_index = 0;
var edu_array = [];
var work_arr = [];
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
    mobile: util.getCache('mobile')
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
    console.log(options)
    seckillId = options.seckillId ? options.seckillId : 0;
    code = options.code ? options.code : 0;
    card_no = options.cardNo ? options.cardNo : 0;
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
    sex_index = 0;
    user_nick_name = '';
    real_name = '';
    marry_index = 0;
    var that = this;
    that.setData({
      user_block: 0,
      examine: examine,
      distribution: distribution
    })
    wx.showLoading({
      title: '加载中',
    })
    this.handleToInitDdata()  // 处理初始请求数据
  },
  // 处理初始请求数据
  handleToInitDdata() {
    let that = this
    if (distribution == 1) {
      util.api('/api/wxapp/distribution/group/list', function (res) {
        console.log(res)
        if (res.error == 0) {
          var group_arr = [];
          var group_select = 0;
          for (var i = 0; i < res.content.length; i++) {
            group_arr.push(res.content[i].groupName)
          }
          if (group_arr.length) {
            that.setData({
              rebate_groups: res.content,
              group_arr: group_arr,
              group_select: group_select,
              if_rebate_group: 1
            })
          }
        }
        wx.hideLoading();
        that.setData({
          user_block: 1
        })

      })
    } else {
      util.api('/api/wxapp/activation/card', function (res) {
        console.log(res)
        if (res.error === 0) {
          that.data.template_ids = res.content.template_ids || [];
          var user_info = res.content.data;
          var fi_arr = res.content.fields;
          console.log(fi_arr)
          let keyArr = ['if_username', 'if_mobile', 'if_realname', 'if_invitation_code', 'if_work', 'if_citydoce', 'if_sex', 'if_birthdayyear', 'if_mar', 'if_edu']
          let valArr = ['username', 'mobile', 'realName', 'invitation_code', 'cid', 'industryInfo', 'cityCode', 'sex', 'birthdayYear', 'maritalStatus', 'education']
          fi_arr.map((item, index) => {
            var val = keyArr[valArr.indexOf(fi_arr[index])]
            let obj = {}
            obj[val] = 1
            if (valArr.indexOf(fi_arr[index]) != -1) {
              that.setData(obj)
            }
          })
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
          if (res.content.industry_info) {
            for (var i in res.content.industry_info) {
              work_arr.push(res.content.industry_info[i])
            }
            if (user_info.industry_info == null) {
              work_select = 0;
            } else {
              work_select = user_info.industry_info;
            }
            that.setData({
              work_arr: work_arr,
              work_select: work_select,
            })
          }
          //所在地
          if (user_info.city_code) {
            region[0] = user_info.province_code;
            region[1] = user_info.city_code;
            region[2] = user_info.district_code;
            that.setData({
              region: region,
            })
          }
          //性别
          if (user_info.sex) {
            if (user_info.sex == "f") {
              sex_index = 2;
            } else {
              sex_index = 1;
            }
            that.setData({
              sex_index: sex_index,
            })
          }
          //生日
          if (user_info.birthday_day != null && user_info.birthday_day != 0) {
            if (parseInt(user_info.birthday_month) < 10) {
              user_info.birthday_month = '0' + user_info.birthday_month;
            }
            if (parseInt(user_info.birthday_day) < 10) {
              user_info.birthday_day = '0' + user_info.birthday_day;
            }
            dates = user_info.birthday_year + '-' + user_info.birthday_month + '-' + user_info.birthday_day;
            that.setData({
              // date: dates,
              dates: dates,
            })
          }
          // 婚姻状况
          if (user_info.marital_status) {
            if (user_info.marital_status == null) {
              marry_index = 0;
            } else {
              marry_index = user_info.marital_status;
            }
            that.setData({
              marry_index: marry_index
            })
          }
          // 教育程度
          if (res.content.education) {
            for (var i in res.content.education) {
              edu_array.push(res.content.education[i])
            }
            if (user_info.education == null) {
              edu_select = 0;
            } else {
              edu_select = user_info.education;
            }
            console.log(edu_array, edu_select)
            that.setData({
              edu_array: edu_array,
              edu_select: edu_select,
            })
          }
          that.setData({
            user_info: user_info,
            mobile: that.data.mobile,
          })
        } else {
          util.showModal("提示", '操作失败');
        }
        wx.hideLoading();
        that.setData({
          user_block: 1
        })
      }, { cardNo: card_no, isSetting: 0 })

    }
  },
  toSave(e) {
    var that = this;
    setTimeout(function () {
      that.bind_submit(e)
    }, 100);
  },
  bind_submit(e) {
    var user_info = {};
    var that = this;
    user_info.is_setting = 1;
    user_info.user_id = util.getCache('user_id');
    user_info.open_id = util.getCache("openid");
    user_info.form_id = e.detail.formId;
    // 会员昵称
    if (this.data.if_username == 1) {
      user_info.username = user_nick_name
    }
    // 手机号
    if (this.data.if_mobile == 1) {
      user_info.mobile = that.data.mobile;
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
    if (this.data.if_rebate_group == 1) {
      user_info.rebate_group = this.data.rebate_groups[this.data.group_select].id
    }
    //所在地
    if (this.data.if_citydoce == 1) {
      user_info.province_code = region[0];
      user_info.city_code = region[1];
      user_info.district_code = region[2];
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
    console.log(this.data.if_edu, edu_select)
    // 教育程度
    if (this.data.if_edu == 1) {
      user_info.education = edu_select;
    }
    // 备注
    if (this.data.if_remark == 1) {
      user_info.remarks = remarks;
    }
    // 判断有效性
    let flag = this.handleTojudge(user_info)
    if (!flag) return
    // 接口调用
    if (this.data.save_flag == 1) {
      this.setData({
        save_flag: 0
      })
      if (distribution == 1) {
        this.handleToDistributor()
      } else {
        this.handleToActivation(user_info)
      }
    } else {
      util.showModal("提示", '请勿重复提交');
    }
  },
  handleToDistributor() {
    let that = this
    util.api('/api/wxapp/distributor/activation', function (res) {
      if (res.error == 0) {
        util.redirectTo({
          url: '/pages/distributionspread/distributionspread'
        })
      } else {
        if (res.content == 1) {
          util.showModal("提示", res.message, function () {
            util.redirectTo({
              url: '/pages/distribution/distribution'
            })
          }, false);
        } else {
          util.showModal("提示", res.message, function () {
            if (that.data.if_invitation_code != 1) {
              util.redirectTo({
                url: '/pages/distributionspread/distributionspread'
              })
            } else {
              that.setData({
                save_flag: 1
              })
            }
          }, false);
        }
      }
      that.data.has_apply = false;
    }, user_info);
  },
  handleToActivation(user_info) {
    util.api('/api/wxapp/activation/card', function (res) {
      console.log(res)
      if (res.error == 0) {
        if (examine == 0) {
          util.toast_success('激活成功', function () {
            console.log(is_fullprice, code, seckillId, goods_id)
            if (is_fullprice == 0 && code == 0 && seckillId == 0 && goods_id == 0) {
              setTimeout(function () {
                util.redirectTo({
                  url: '/pages/cardinfo/cardinfo?card_list=1&cardNo=' + card_no
                })
              }, 2000);
            } else if (parseInt(gift_id)) {
              util.redirectTo({
                url: 'pages1/presentchoose/presentchoose?gift_id=' + gift_id,
              })
            } else if (parseInt(is_fullprice)) {
              util.redirectTo({
                url: '/pages/fullprice/fullprice?identity_id=' + is_fullprice,
              })
            } else if (parseInt(seckillId)) {
              util.redirectTo({
                url: '/pages/seckillitem/seckillitem?sk_id=' + seckillId,
              })
            } else if (parseInt(goods_id)) {
              util.redirectTo({
                url: '/pages/item/item?good_id=' + goods_id,
              })
            } else {
              util.redirectTo({
                url: '/pages/getcoupon/getcoupon?code=' + code,
              })
            }
          });
        } else {
          util.toast_success('申请已提交', function () {
            setTimeout(function () {
              util.redirectTo({
                url: '/pages/usercardinfo/usercardinfo?card_list=1&card_no=' + card_no
              })
            }, 2000);
          });
        }
      } else {
        util.showModal("提示", res.message);
        return;
      }
    }, { cardNo: card_no, isSetting: 1, activateOption: user_info })
  },
  // 底部按钮点击时相关判断
  handleTojudge(user_info) {
    console.log(user_info)
    let that = this
    if (this.data.if_invitation_code == 1) {
      if (inviteCode.length != 6) {
        util.showModal("提示", "请输入正确的邀请码");
        return;
      }
      user_info.invitation_code = inviteCode;
    }
    // 上传图片
    if (this.data.if_upImage == 1) {
      user_info.upload_image = that.data.comm_img[0];
    }
    //激活
    if (user_info.real_name == "" && this.data.if_realname == 1) {
      util.showModal("提示", "请填写真实姓名");
      return;
    }
    console.log(user_info, this.data.if_mobile)
    // if (user_info.mobile == '' && this.data.if_mobile == 1) {
    //   util.showModal("提示", "请授权手机号");
    //   return;
    // }
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
    if ((user_info.upload_image == '' || user_info.upload_image == undefined) && that.data.img_len != 1 && this.data.if_upImage == 1) {
      util.showModal("提示", "请上传图片");
      return;
    }
    return true
  },
  bindDateChange(e) {
    dates = e.detail.value;
    this.setData({
      dates: e.detail.value
    })
  },
  bindRegionChange(e) {
    region = e.detail.value;
    this.setData({
      region: e.detail.value
    })
  },
  bindSexChange(e) {
    sex_index = e.detail.value;
    this.setData({
      sex_index: e.detail.value
    })
  },
  bindMarryChange(e) {
    marry_index = e.detail.value;
    this.setData({
      marry_index: e.detail.value
    })
  },
  bindEduChange(e) {
    edu_select = e.detail.value;
    this.setData({
      edu_select: edu_select
    })
  },
  bindWorkChange(e) {
    work_select = e.detail.value;
    this.setData({
      work_select: work_select
    })
  },
  bindGroupChange(e) {
    this.setData({
      group_select: e.detail.value
    })
  },
  // inputBlur(e) {
  //   if (/^1[3456789]\d{9}$/.test(e.detail.value)) {
  //     mobile = e.detail.value;
  //   } else {
  //     util.showModal('提示', "请输入正确的手机号！");
  //     return false;
  //   }
  // },
  realName(e) {
    if (e.detail.value.replace(/^\s+|\s+$/g, '') == "") {
      util.showModal("提示", "请填写真实姓名");
    }
    real_name = e.detail.value;
  },
  // 备注
  remarkBlur(e) {
    remarks = e.detail.value;
  },
  //上传图片
  upImage(e) {
    var that = this;
    var comm_img = that.data.comm_img;
    util.uploadImage(1, function (con) {
      var data = JSON.parse(con.data);
      for (var i in data.content) {
        comm_img.push(data.content[i].img_url);
      }
      var img_len = parseInt(comm_img.length);
      that.setData({
        comm_img: comm_img,
        image: true,
        img_len: img_len
      })
    });
  },
  // 身份证号
  IDnum(e) {
    var re = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
    if (e.detail.value.replace(/^\s+|\s+$/g, '') == "") {
      util.showModal("提示", "请输入正确的身份证号");
    }
    if (!(re.test(e.detail.value)) || e.detail.value.replace(/^\s+|\s+$/g, '').length != 18) {
      util.showModal("提示", "请输入正确的身份证号");
    }
    id_num = e.detail.value;
  },
  getPhoneNumber(e) {
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
  parseMobile(iv, data) {
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
  onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
  }
})
