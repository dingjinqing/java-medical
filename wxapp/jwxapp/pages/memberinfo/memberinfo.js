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
    comm_img: [],
    mobile: util.getCache('mobile'),
    checkbox_no: imageUrl + '/image/admin/select.png',
    uploadPictureTitle: '上传图片'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options)
    if (!util.check_setting(options)) return;
    if (options.is_fullprice) {
      is_fullprice = options.is_fullprice;
    } else {
      is_fullprice = 0
    }
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
      distribution: distribution,
      mobile: util.getCache('mobile')
    })
    console.log(that.data.mobile)
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
          console.log(res.content)
          var user_info = res.content.userBaseInfo;
          // 自定义激活项
          let custom_arr = res.content.cfg.custom_options
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
            console.log(custom_arr)
            that.setData({
              custom_arr: custom_arr
            })
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
            // region[0] = user_info.provinceCode;
            // region[1] = user_info.cityCode;
            // region[2] = user_info.districtCode;
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

    } else {
      console.log(card_no)
      util.api('/api/wxapp/activation/card', function (res) {
        console.log(res)
        if (res.error === 0) {

          // 自定义激活项
          let custom_arr = res.content.customOptions ? res.content.customOptions : []

          if (custom_arr.length > 0) {
            for (var i in custom_arr) {
              if(custom_arr[i].customType == 3){
                that.setData({
                  uploadPictureTitle: custom_arr[i].customTitle
                })
              }
              if (custom_arr[i].customType == 0) {
                custom_arr[i].custom_select = 0
                for (var j in custom_arr[i].optionArr) {
                  let obj = {
                    optionTitle: custom_arr[i].optionArr[j],
                    isChecked: 0
                  }
                  custom_arr[i].optionArr[j] = obj
                  if (custom_arr[i].isChecked == 1) {
                    if (j == 0) {
                      custom_arr[i].optionArr[j].isChecked = true
                    } else {
                      custom_arr[i].optionArr[j].isChecked = false
                    }
                  } else {
                    custom_arr[i].optionArr[j].isChecked = false
                  }
                }
              } else if (custom_arr[i].customType == 1) {
                for (var j in custom_arr[i].optionArr) {
                  let obj = {
                    optionTitle: custom_arr[i].optionArr[j],
                    isChecked: 0
                  }
                  custom_arr[i].optionArr[j] = obj
                  custom_arr[i].optionArr[j].isChecked = false
                }
              } else if (custom_arr[i].customType == 2) {
                custom_arr[i].text = ''
              } else if (custom_arr[i].customType == 3) {
                console.log(custom_arr[i])
                custom_arr[i].comm_img = [];
                custom_arr[i].can_click = true;
              }

            }
            console.log(custom_arr)
            // 模拟自定义激活项上传图片数据
            // let obj = {
            //   custom_image: "5",
            //   custom_title: "测试1",
            //   custom_type: "3",
            //   is_checked: 1,
            //   option_ver: 1,
            //   comm_img: []
            // }
            // custom_arr.push(obj)

            that.setData({
              custom_arr: custom_arr
            })
          }


          that.data.template_ids = res.content.template_ids || [];
          var user_info = res.content.data;
          var fi_arr = res.content.fields;
          console.log(fi_arr)
          let keyArr = ['if_username', 'if_mobile', 'if_cid', 'if_realname', 'if_invitation_code', 'if_work', 'if_citydoce', 'if_sex', 'if_birthdayyear', 'if_mar', 'if_edu', 'if_upImage']
          let valArr = ['username', 'mobile', 'cid', 'realName', 'invitation_code', 'industryInfo', 'cityCode', 'sex', 'birthdayYear', 'maritalStatus', 'education']
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
  toSave: function (e) {
    util.getNeedTemplateId('audit_upgrade', () => {
      var that = this;
      setTimeout(function () {
        that.bind_submit(e)
      }, 100);
    })

  },
  // 提交审核申请
  bind_submit: function (e) {
    var user_info = {};
    var config = []
    var that = this;
    // 会员昵称
    if (this.data.if_username == 1) {
      user_info.username = user_nick_name
      config.push('username')
    }
    // 手机号
    if (this.data.if_mobile == 1) {
      user_info.mobile = mobile;
      config.push('mobile')
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
      config.push('real_name')
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
      config.push('cid')
    }
    // 所在行业
    if (this.data.if_work == 1) {
      user_info.industry_info = work_select;
      config.push('industry_info')
    }
    // 分销员分组
    if (this.data.if_rebate_group == 1) {
      user_info.rebate_group = this.data.rebate_groups[this.data.group_select].id
      config.push('rebate_group')
    }
    //所在地
    if (this.data.if_citydoce == 1) {
      user_info.province_code = region[0] == '' ? '' : regionCode[0];
      user_info.city_code = region[1] == '' ? '' : regionCode[1];
      user_info.district_code = region[2] == '' ? '' : regionCode[2];
      user_info.address = "";
      config.push('address')
    }
    //性别
    if (this.data.if_sex == 1) {
      if (sex_index == 1) {
        user_info.sex = 'm';
      } else if (sex_index == 2) {
        user_info.sex = 'f';
      }
      config.push('sex')
    }
    //生日
    if (this.data.if_birthdayyear == 1 && dates != undefined) {
      var date_arr = dates.split('-');
      user_info.birthday_year = date_arr[0];
      user_info.birthday_month = date_arr[1];
      user_info.birthday_day = date_arr[2];
      config.push('birthday_year')
      config.push('birthday_month')
      config.push('birthday_day')
    }
    // 婚姻状况
    if (this.data.if_mar == 1) {
      user_info.marital_status = marry_index;
      config.push('marital_status')
    }
    // 教育程度
    if (this.data.if_edu == 1) {
      user_info.education = edu_select;
      config.push('education')
    }
    // 备注
    if (this.data.if_remark == 1) {
      user_info.remarks = remarks;
      config.push('remarks')
    }
    // 上传图片
    if (this.data.if_upImage == 1) {
      user_info.upload_image = that.data.comm_img[0];
      config.push('upload_image')
    }
    // 邀请码
    if (this.data.if_invitation == 1) {
      user_info.invitation_code = invitation_code
    }
    // 自定义激活项
    var custom_arr = that.data.custom_arr
    var custom_options = []
    if (distribution == 1) {
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
    } else {
      for (var i in custom_arr) {
        if (custom_arr[i].isChecked == 1) {
          // 必填项
          if (custom_arr[i].optionVer == 1) {
            if (custom_arr[i].customType == 0 || custom_arr[i].customType == 1) {
              var result = custom_arr[i].optionArr.some(function (item) {
                if (item.isChecked == true) {
                  return true
                } else {
                  return false;
                }
              })
              if (result == false) {
                util.showModal("提示", "请填写" + custom_arr[i].customTitle);
                return;
              }


            } else if (custom_arr[i].customType == 2 && custom_arr[i].text == '') {
              util.showModal("提示", "请填写" + custom_arr[i].customTitle);
              return;
            }
          }
          if (custom_arr[i].customType == 0 || custom_arr[i].customType == 1) {
            // isChecked处理
            custom_arr[i].optionArr.forEach((item, index) => {
              if (item.isChecked == true) {
                custom_arr[i].optionArr[index].isChecked = 1
              } else {
                custom_arr[i].optionArr[index].isChecked = 0
              }
            })
          }
          custom_options.push(custom_arr[i])

        }
      }
      console.log(custom_options)
    }

    //激活
    if (user_info.real_name == "" && this.data.if_realname == 1) {
      util.showModal("提示", "请填写真实姓名");
      return;
    }
    console.log(user_info, this.data)
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


    if (distribution == 1) {
      console.log(user_info)
      util.api('/api/wxapp/distribution/distributor/apply', function (res) {
        if (res.error == 0) {
          if (res.content == -1) {
            // 申请不成功, 邀请码不存在
            util.showModal("提示", "邀请码不存在");
            return false;
          } else if (res.content == 1) {
            util.showModal('提示', '自动审核通过', function () {
              util.redirectTo({
                url: '/pages/distributionspread/distributionspread'
              })
            });
          } else if (res.content === 0) {
            util.showModal('提示', '申请成功', function () {
              util.redirectTo({
                url: '/pages/distributionspread/distributionspread'
              })
            });
          }
        } else {
          util.showModal('提示', res.message)
        }
      }, {
        activationFields: user_info,
        configFields: JSON.stringify(config)
      })
    } else {
      if (that.data.save_flag == 1) {
        that.setData({
          save_flag: 0
        })
        
        console.log(card_no)
        console.log(user_info)

        util.api('/api/wxapp/activation/card', function (res) {
          console.log(res)
          if (res.error === 0) {
            util.toast_success(res.message, function () {
              setTimeout(function () {
                util.redirectTo({
                  url: '/pages/cardlist/cardlist',
                })
              }, 2000);
            });
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
        }, { cardNo: card_no, isSetting: 1, activateOption: user_info, customOptions: custom_options })
      } else {
        util.showModal("提示", '请勿重复提交');
      }
    }
  },
  // 单选 选项
  bindRadiosChange (e) {
    console.log(e.detail.value)
    let that = this
    let index = e.target.dataset.index
    let custom_arr = that.data.custom_arr
    let valueList = e.detail.value
    console.log(index)
    custom_arr[index].optionArr.find((item, index) => {
      item.isChecked = false
      if (valueList == index) {
        item.isChecked = true
      }
    })
    that.setData({
      custom_arr: custom_arr
    })
  },
  // 多选 选项
  bindCheckboxChange (e) {
    console.log(e.detail.value)
    // var m = this.data.m;
    // m.module_value = e.detail.value;
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
    let that = this
    let index = e.currentTarget.dataset.index
    let custom_arr = that.data.custom_arr
    let valueList = e.currentTarget.dataset.value
    console.log(index, custom_arr, e, valueList)
    if (distribution == 1) {
      custom_arr[index].option_arr.find((item, index) => {
        if (valueList == item.option_title) {
          item.checked = !item.checked
        }
      })
    } else {
      custom_arr[index].optionArr.find((item, index) => {
        if (valueList == item.optionTitle) {
          item.isChecked = !item.isChecked
        }
      })
    }
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
  customUpImage: function (e) {
    var that = this;
    var custom_op = that.data.custom_arr;
    var index = e.currentTarget.dataset.idx;
    var url = util.getUrl('/api/wxapp/image/upload');
    var num = parseInt(e.currentTarget.dataset.num);
    let count = num - custom_op[index].comm_img.length;
    if (custom_op[index].can_click == false) return;
    that.setData({
      ['custom_arr[' + index + '].can_click']: false,
    })
    wx.chooseImage({
      count: count,
      sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths;
        if (res) {
          var can_click = false;
          console.log(res)

          for (var i = 0; i < tempFilePaths.length; i++) {
            let img = tempFilePaths[i]
            wx.getImageInfo({
              src: img,
              success: function (obj) {
                let params = {
                  needImgWidth: obj.width,
                  needImgHeight: obj.height,
                  imgCatId: -1,
                  userId: util.getCache('user_id')
                }
                util.uploadFile(url, img, params, function (e) {
                  let data = JSON.parse(e.data)
                  console.log(data)
                  if (data.error === 0) {
                    custom_op[index].comm_img.push(data.content.imgUrl);
                    if (i == tempFilePaths.length) can_click = true;
                    console.log(index, custom_op)
                    that.setData({
                      ['custom_arr[' + index + ']']: custom_op[index],
                      ['custom_arr[' + index + '].can_click']: can_click,
                    })

                  }
                }, function (err) {
                  util.toast_fail(that.$t('page1.reserve.uploadFailed'))
                  console.log(err)
                });
              }
            })

          }
        }
      },
      fail: function () {
        that.setData({
          ['custom_arr[' + index + '].can_click']: true,
        })
      }
    })
  },
  customDelImage: function (e) {
    var that = this;
    var index = e.currentTarget.dataset.idx;
    var imgindex = e.currentTarget.dataset.imgindex;
    var custom_op = that.data.custom_arr;
    console.log(e)
    console.log(custom_op, index, imgindex)
    custom_op[index].comm_img.splice(imgindex, 1);
    that.setData({
      ['custom_arr[' + index + ']']: custom_op[index],
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