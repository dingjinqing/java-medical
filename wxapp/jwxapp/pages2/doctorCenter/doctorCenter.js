var util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    doctorInfo:{}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getDoctorInfo()
  },
  changeAvatar(){
    util.uploadImage(1, (con) => {
      var data = JSON.parse(con.data);
      console.log(data)
      this.setData({
        'doctorInfo.url':data.content.imgPath
      })
    });
  },
  getDoctorInfo(){
    util.api('/api/wxapp/doctor/show/Information',({content,error,message})=>{
      if(error === 0){
        this.setData({
          doctorInfo:{
            ...content,
            url:content.url?content.url:'image/wxapp/doctor_default_icon.png', //头像
          }
        })
      } else {
        util.showModal('提示',message)
      }
    },{
      id:this.data.bottom.doctor_id
    })
  },
  setDoctorInfo(){
    util.api('/api/wxapp/doctor/update/Information',res=>{
      console.log(res)
    },{
      ...this.data.doctorInfo
    })
  },
  changeMoney({detail:{value}}){
    this.setData({
      'doctorInfo.consultationTotalMoney':value
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