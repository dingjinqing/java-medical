var util = require('../../utils/util.js')
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    prescriptionImageList:[],
    diagnose:['心绞痛','高血压','心肌炎','肺炎','咳嗽','感冒'],
    selectedDiagnose:[],
    optionsList:{
      isUsed:true,
      hasBadReaction:false,
      isGravida:false 
    },
    optionsName:{
      isUsed:'曾服用过本药品',
      hasBadReaction:'有无不良反应',
      isGravida:'妊娠哺乳'
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let {patientInfo} = options
    this.setData({
      patientInfo:JSON.parse(patientInfo)
    })
  },
  uploadImage(){
    let prescriptionImageList = this.data.prescriptionImageList
    util.uploadImage(1, (res) => {
      let data = JSON.parse(res.data);
      if (data.error == 0) {
        prescriptionImageList.push(data.content.imgUrl);
        this.setData({
          prescriptionImageList
        })
      }
    });
  },
  chooseDiagnose(e){
    let {index:targetIndex} = e.currentTarget.dataset
    let selectedDiagnose = this.data.selectedDiagnose
    if(selectedDiagnose.includes(this.data.diagnose[targetIndex])){
      selectedDiagnose.splice(selectedDiagnose.findIndex(item=>item===this.data.diagnose[targetIndex]),1)
    } else {
      selectedDiagnose.push(this.data.diagnose[targetIndex])
    }
    this.setData({
      selectedDiagnose
    })
  },
  toggleOptions(e){
    let {key,status} = e.currentTarget.dataset
    console.log(key,status)
    this.setData({
      [`optionsList.${key}`]:!!Number(status)
    })
  },
  confirm(){
    if(!this.data.selectedDiagnose.length)  {
      util.showModal('提示','请选择历史诊断内容')
      return
    }
    let params = {
      patientName:this.data.patientInfo.name,
      patientId:this.data.patientInfo.patientId,
      sex:this.data.patientInfo.sex,
      age:this.data.patientInfo.age,
      imagesList:JSON.stringify(this.data.prescriptionImageList),
      patientComplain:JSON.stringify({
        ...this.data.optionsList,
        selectedDiagnose:this.data.selectedDiagnose,
      })
    }
    let pageList = getCurrentPages();
    let prevPage = pageList[pageList.length - 2];
    prevPage.setData({
      patientDiagnose:params
    })
    wx.navigateBack()
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