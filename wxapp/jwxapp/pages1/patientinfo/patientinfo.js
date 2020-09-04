// pages1/patientinfo/patientinfo.js
var util = require('../../utils/util.js');
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    // 患者id
    patient_id: 0,
    prescription_info: {},
    patient_info: {},
    showModal: 0,
    // 性别
    sex_index: -1,
    gender: ['男', '女'],
    pat_name: '',
    dates: "",
    pat_mobile: '',
    pat_id_num: '',
    pat_tip: '',
    // 妊娠哺乳
    feedStatus: [
      {
        id: 2,
        is_checked: 0,
        text: '备孕中'
      },
      {
        id: 3,
        is_checked: 0,
        text: '怀孕中'
      },
      {
        id: 4,
        is_checked: 0,
        text: '正在哺乳'
      }
    ],
    his_summary: '',
    // 过往病史
    has_dis_his: 0,
    diseaseHistory: [],
    disText:[],
    // 过敏史
    has_all_his: 0,
    allergyHistory: '',
    // 家族病史
    has_fam_his: 0,
    familyDiseaseHistory: [],
    familyText: [],
    // 肝功能
    liverFunctionOk: 1,
    // 肾功能
    kidneyFunctionOk: 1,
    // 妊娠哺乳
    is_feed: 1,
    gestationType: 1,
    feedText: '',
    // 是否编辑患者
    is_edit: 0,
    // 编辑的时候显示的疾病史
    edit_his_summary: [],
    // 是否来自咨询页面
    list: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    if(options.prescription_info) {
      this.data.prescription_info  = JSON.parse(options.prescription_info);
      this.data.patient_info = this.data.prescription_info;
      this.data.pat_name = this.data.prescription_info.name;
      this.data.pat_mobile = this.data.prescription_info.mobile;
      this.data.pat_id_num = this.data.prescription_info.identityCode;
      this.setData({
        patient_info: this.data.patient_info
      })
    }
    this.data.is_edit = !!options.is_edit ? "1":"0";
    this.setData({
      is_edit: this.data.is_edit
    })
    this.data.patient_id = options.patient_id ? options.patient_id : 0;
    if(options.list && options.list == 1){
      this.data.list = 1
    }
    this.getPatientInfo();
  },
  getPatientInfo () {
    util.api('/api/wxapp/user/patient/get/detail', res => {
      if(res.error == 0) {
        if(!!this.data.patient_info.name) {
          res.content.name = this.data.patient_info.name;
          this.data.pat_name = this.data.patient_info.name;
        }
        if(!!this.data.patient_info.mobile){
          res.content.mobile = this.data.patient_info.mobile;
          this.data.pat_mobile = this.data.patient_info.mobile;
        }
        if(!!this.data.patient_info.identityCode){
          res.content.identityCode = this.data.patient_info.identityCode;
          this.data.pat_id_num = this.data.patient_info.identityCode;
        }
        this.data.patient_info = res.content;
        this.data.pat_name = this.data.patient_info.name;
        this.data.pat_mobile = this.data.patient_info.mobile;
        this.data.pat_id_num = this.data.patient_info.identityCode;
        if( this.data.patient_info.sex >= 0 && this.data.patient_info.sex != null){
          this.data.sex_index = this.data.patient_info.sex 
        } else{
          this.data.sex_index = -1
        }
        this.data.dates = !!this.data.patient_info.birthday ? this.data.patient_info.birthday:'';
        // 疾病史编辑
        // 过往病史
        if(!!this.data.patient_info.diseaseHistory){
          this.data.has_dis_his = 1;
          this.data.diseaseHistory = this.data.patient_info.diseaseHistory.split(',');
          var dis_arr = [];
          dis_arr = this.data.patient_info.diseaseHistory.split(',');
          for (let i in dis_arr) {
            for (let j in this.data.patient_info.diseaseHistoryList){
              if(this.data.patient_info.diseaseHistoryList[j].id == dis_arr[i]) {
                this.data.patient_info.diseaseHistoryList[j].checked = 1;
                this.data.edit_his_summary.push(this.data.patient_info.diseaseHistoryList[j].name)
              }
            }
          }
        }
        // 过敏史
        if(!!this.data.patient_info.allergyHistory){
          this.data.has_all_his = 1;
          this.data.allergyHistory = this.data.patient_info.allergyHistory;
          this.data.edit_his_summary.push(this.data.patient_info.allergyHistory)
        }
        // 家族病史
        if( !!this.data.patient_info.familyDiseaseHistory ){
          this.data.has_fam_his = 1;
          this.data.familyDiseaseHistory = this.data.patient_info.familyDiseaseHistory.split(',');
          var dis_arr1 = [];
          dis_arr1 = this.data.patient_info.familyDiseaseHistory.split(',');
          for (let i in dis_arr1) {
            for (let j in this.data.patient_info.familyDiseaseHistoryList){
              if(this.data.patient_info.familyDiseaseHistoryList[j].id == dis_arr1[i]) {
                this.data.patient_info.familyDiseaseHistoryList[j].checked = 1;
                this.data.edit_his_summary.push(this.data.patient_info.familyDiseaseHistoryList[j].name)
              }
            }
          }
        }
        // 肝肾功能
        this.data.liverFunctionOk = this.data.patient_info.liverFunctionOk == 0 ? this.data.patient_info.liverFunctionOk : 1;
        if(this.data.liverFunctionOk == 0) {
          this.data.edit_his_summary.push('肝功能异常')
        }
        this.data.kidneyFunctionOk = this.data.patient_info.kidneyFunctionOk == 0 ? this.data.patient_info.liverFunctionOk : 1;
        if(this.data.kidneyFunctionOk == 0) {
          this.data.edit_his_summary.push('肾功能异常')
        }
        // 妊娠哺乳
        if(this.data.patient_info.gestationType > 1) {
          this.data.is_feed = 0;
          this.data.gestationType = this.data.patient_info.gestationType || 0;
          for (var i in this.data.feedStatus ){
            if(this.data.feedStatus[i].id == this.data.patient_info.gestationType){
              this.data.feedStatus[i].is_checked = 1;
              this.data.edit_his_summary.push(this.data.feedStatus[i].text)
            }
          }
        }
        this.data.pat_tip = !!this.data.patient_info.remarks ? this.data.patient_info.remarks : ''
        if(!!this.data.edit_his_summary){
          this.data.his_summary = this.data.edit_his_summary.join(',')
        }
        this.setData({
          patient_info: this.data.patient_info,
          sex_index: this.data.sex_index,
          dates: this.data.dates,
          has_dis_his:this.data.has_dis_his,
          has_all_his: this.data.has_all_his,
          has_fam_his: this.data.has_fam_his,
          liverFunctionOk:this.data.liverFunctionOk,
          kidneyFunctionOk:this.data.kidneyFunctionOk,
          is_feed: this.data.is_feed,
          feedStatus: this.data.feedStatus,
          his_summary: this.data.his_summary,
          allergyHistory:this.data.allergyHistory
        })
      }else {
        util.showModal("提示",res.message)
      }
    }, {patientId:this.data.patient_id,userId:util.getCache('user_id')})
  },
  nameInput (e) {
    this.data.pat_name = e.detail.value
  },
  bindDateChange: function (e) {
    this.data.dates = e.detail.value;
    this.setData({
      dates: e.detail.value
    });
  },
  bindSexChange (e) {
    this.data.sex_index = e.detail.value;
    this.setData({
      sex_index: this.data.sex_index
    })
  },
  idInput (e) {
    this.data.pat_id_num = e.detail.value
  },
  mobileInput (e) {
    this.data.pat_mobile = e.detail.value
  },
  tipInpuit (e) {
    this.data.pat_tip = e.detail.value
  },
  close_modal () {
    this.setData({
      showModal: 0
    })
  },
  open_modal () {
    this.setData({
      showModal: 1
    })
  },
  bindSubmit () {
    if ( !this.data.pat_name) {
      util.showModal("提示", "请输入真实姓名");
      return false;
    }
    if ( !this.data.dates) {
      util.showModal("提示", "请选择出生日期");
      return false;
    }
    if (this.data.sex_index == -1) {
      util.showModal("提示", "请选择性别");
      return false;
    }
    var re = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
    if (this.data.pat_id_num.replace(/^\s+|\s+$/g, '') == "") {
      util.showModal("提示", "请输入正确的身份证号");
      return false;
    }
    if (!(re.test(this.data.pat_id_num)) || this.data.pat_id_num.replace(/^\s+|\s+$/g, '').length != 18) {
      util.showModal("提示", "请输入正确的身份证号");
      return false;
    }
    if (!this.data.pat_mobile) {
      util.showModal("提示", "请输入手机号");
      return false;
    } 
    if (!/^1[3456789]\d{9}$/.test(this.data.pat_mobile)) {
      util.showModal("提示", "请输入正确的手机号！");
        return false;
    } 
    if(!this.data.pat_tip) {
      util.showModal("提示", "请输入备注");
      return false;
    }
    this.data.patient_info.name = this.data.pat_name;
    this.data.patient_info.mobile = this.data.pat_mobile;
    this.data.patient_info.identityCode = this.data.pat_id_num;
    this.data.patient_info.identityType = 1;
    this.data.patient_info.sex = this.data.sex_index == 0 ? 0:1;
    this.data.patient_info.birthday = this.data.dates + " 00:00:00";
    this.data.patient_info.diseaseHistory = this.data.diseaseHistory.join(',');
    this.data.patient_info.allergyHistory = this.data.allergyHistory
    this.data.patient_info.familyDiseaseHistory = this.data.familyDiseaseHistory.join(',');
    this.data.patient_info.kidneyFunctionOk = this.data.kidneyFunctionOk;
    this.data.patient_info.liverFunctionOk = this.data.liverFunctionOk;
    this.data.patient_info.gestationType = this.data.gestationType;
    this.data.patient_info.remarks = this.data.pat_tip;
    util.api('/api/wxapp/user/patient/add', res => {
      if(res.error == 0){
        util.toast_success('成功')
        util.handleBuriedPoint('create_patient_success', 'pages1/patientinfo/patientinfo', [{
          key: '添加就诊人'
        }])
        if(this.data.list == 1){
          util.jumpLink('/pages2/doctorConsultation/doctorConsultation','redirectTo');
        }else{
          util.jumpLink('/pages1/familylist/familylist','redirectTo')
        }
      }
    },{
      id:this.data.patient_id,
      name: this.data.patient_info.name,
      mobile: this.data.patient_info.mobile,
      identityCode:this.data.patient_info.identityCode,
      identityType: this.data.patient_info.identityType,
      sex: this.data.patient_info.sex,
      birthday:this.data.patient_info.birthday,
      diseaseHistory:this.data.patient_info.diseaseHistory,
      allergyHistory:this.data.patient_info.allergyHistory,
      familyDiseaseHistory:this.data.patient_info.familyDiseaseHistory,
      kidneyFunctionOk:this.data.patient_info.kidneyFunctionOk,
      liverFunctionOk:this.data.patient_info.liverFunctionOk,
      gestationType:this.data.patient_info.gestationType,
      remarks:this.data.patient_info.remarks,
      userId: util.getCache('user_id'),
      patientCode: ''
    })
  },

  // 疾病史：
  // 过往病史
  changeDisHis (e) {
    if(e.currentTarget.dataset.has_dis_his == 0) {
      this.setData({
        has_dis_his: 0
      })
      this.data.diseaseHistory = []
      this.data.disText = ''
    }else{
      this.setData({
        has_dis_his: 1
      })
    }
  },
  choose_dis_his (e) {
    let this_target = e.currentTarget.dataset
    if(this_target.check == 0) {
      this.data.patient_info.diseaseHistoryList[this_target.index].checked = 1;
      this.data.diseaseHistory.push(this_target.id)
      this.data.disText.push(this_target.name)
    } else {
      this.data.patient_info.diseaseHistoryList[this_target.index].checked = 0;
      this.data.diseaseHistory.splice(this.data.diseaseHistory.indexOf(this.data.patient_info.diseaseHistoryList[this_target.index].id), 1)
      this.data.disText.splice(this.data.disText.indexOf(this.data.patient_info.diseaseHistoryList[this_target.index].name), 1)
    }
    console.log(this.data.disText)
    this.setData({
     'patient_info.diseaseHistoryList': this.data.patient_info.diseaseHistoryList
    })
  },
  // 过敏史
  changeAllHis (e) {
    if(e.currentTarget.dataset.has_all_his == 0) {
      this.setData({
        has_all_his: 0
      })
      this.data.allergyHistory = ''
    }else{
      this.setData({
        has_all_his: 1
      })
    }
  },
  saveAllHis (e) {
    this.data.allergyHistory = e.detail.value
  },
  // 家族病史
  changeFimHis (e) {
    if(e.currentTarget.dataset.has_fam_his == 0) {
      this.setData({
        has_fam_his: 0
      })
      this.data.familyDiseaseHistory = [];
      this.data.familyText = ''
    }else{
      this.setData({
        has_fam_his: 1
      })
    }
  },
  choose_fam_his (e) {
    let this_target = e.currentTarget.dataset
    if(this_target.check == 0) {
      this.data.patient_info.familyDiseaseHistoryList[this_target.index].checked = 1;
      this.data.familyDiseaseHistory.push(this_target.id)
      this.data.familyText.push(this_target.name)
    } else {
      this.data.patient_info.familyDiseaseHistoryList[this_target.index].checked = 0;
      this.data.familyDiseaseHistory.splice(this.data.diseaseHistory.indexOf(this.data.patient_info.familyDiseaseHistoryList[this_target.index].id), 1)
      this.data.familyText.splice(this.data.familyText.indexOf(this.data.patient_info.familyDiseaseHistoryList[this_target.index].name), 1)
    }
    console.log(this.data.familyText)
    this.setData({
     'patient_info.familyDiseaseHistoryList': this.data.patient_info.familyDiseaseHistoryList
    })
  },
  // 肝功能
  changeLiveFun (e) {
    if(e.currentTarget.dataset.live_fun_ok == 0) {
      this.setData({
        liverFunctionOk: 0
      })
    }else{
      this.setData({
        liverFunctionOk: 1
      })
    }
  },
  // 肾功能
  changeKidney (e) {
    if(e.currentTarget.dataset.kidney_ok == 0) {
      this.setData({
        kidneyFunctionOk: 0
      })
    }else{
      this.setData({
        kidneyFunctionOk: 1
      })
    }
  },
  // 妊娠哺乳
  changFeedStatus (e) {
    if(e.currentTarget.dataset.is_feed == 0) {
      this.setData({
        is_feed: 0
      })
    }else{
      this.setData({
        is_feed: 1
      })
      this.data.gestationType = 1
    }
  },
  chooseFeedType (e) {
    let this_index = e.currentTarget.dataset;
    this.data.gestationType = this_index.id;
    for (var i in this.data.feedStatus) {
      this.data.feedStatus[i].is_checked = 0
    }
    this.data.feedStatus[this_index.index].is_checked = 1;
    this.data.feedText = this_index.text;
    this.setData({
      feedStatus: this.data.feedStatus
    })
  },
  // 弹筐里的提交
  checkHistory () {
    if(this.data.has_dis_his == 1 && this.data.diseaseHistory == ""){
      util.showModal('提示','请选择过往病史');
      return false
    }
    if(this.data.has_all_his == 1 && this.data.allergyHistory == ""){
      util.showModal('提示','请填写过敏史');
      return false
    }
    if(this.data.has_fam_his == 1 && this.data.familyDiseaseHistory == ""){
      util.showModal('提示','请选择家族病史');
      return false
    }
    if(this.data.is_feed == 0 && this.data.gestationType < 2){
      util.showModal('提示','请选择妊娠哺乳状态');
      return false
    }
    var disText = this.data.disText != "" ? this.data.disText.join(',') + ',' : ''
    var familyText = this.data.familyText != "" ? this.data.familyText.join(',') + ',' : '' 
    var gan_ok = this.data.liverFunctionOk == 1 ? "":"肝功能异常," 
    var shen_ok =  this.data.kidneyFunctionOk == 1 ? "":"肾功能异常,"
    var allergyHi = this.data.allergyHistory == "" ? '' : this.data.allergyHistory + ','
    this.data.his_summary = disText + allergyHi + familyText + gan_ok + shen_ok + this.data.feedText
    this.setData({
      showModal: 0,
      his_summary: this.data.his_summary
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