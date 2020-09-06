const util = require("../../utils/util")
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    theDateToday: getToday(), //当前时间
    diagnose: { //诊断信息
      info: '',
      canClear: false
    },
    dialogGoodsList: [],    //弹窗商品列表
    cacheGoodsIdList: [], //商品ID缓存列表
    goodsIdList: [],
    cacheGoodsIdNum: [],
    goodsIdNum: [],
    cachePageGoodsList: [], //页面商品列表
    pageGoodsList: [], //页面商品缓存列表
    goodsName: '', //搜索商品名
    dialogPageParams: { //弹窗页码
      currentPage: 1,
      pageRows: 20
    },
    departmentCode: null,
    departmentName: null,
    doctorAdvice: null,
    depart_arr:[],
    depart_all:[],
    depart_index:-1
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let {patientId,userId:patientUserId} = options,doctorId = util.getCache('doctor_id') || util.getCache('bottom').doctor_id
    this.setData({
      patientId,
      doctorId,
      patientUserId
    })
    this.getDoctorDetail()
    this.getPatientDetail()
    this.getDepartmentList()
  },
  clearInput () {
    this.setData({
      'diagnose.canClear': false,
      'diagnose.info': ''
    })
  },
  getDiagnose ({ detail: { value } }) {
    let canClear = false
    if (value) canClear = true
    this.setData({
      'diagnose.canClear': canClear,
      'diagnose.info': value
    })
  },
  addGoods () {
    this.setData({
      showGoodsListDialog: true,
      cacheGoodsIdList: JSON.parse(JSON.stringify(this.data.goodsIdList)),
      cachePageGoodsList: JSON.parse(JSON.stringify(this.data.pageGoodsList)),
      cacheGoodsIdNum: JSON.parse(JSON.stringify(this.data.goodsIdNum)),
      'dialogPageParams.currentPage': 1
    })
    this.requestGoodsList()
  },
  requestGoodsList () {
    util.api('/api/wxapp/medical/goods/page/list', res => {
      console.log(res)
      if (res.error === 0) {
        if (this.data.dialogPageParams.currentPage === 1) {
          this.setData({
            dialogGoodsList: [[...this.resetDialogGoodsList(res.content.dataList,(parseInt(this.data.dialogPageParams.currentPage) - 1))]]
          })
        } else {
          this.setData({
            ['dialogGoodsList[' + (parseInt(this.data.dialogPageParams.currentPage) - 1) + ']']: this.resetDialogGoodsList(res.content.dataList,(parseInt(this.data.dialogPageParams.currentPage) - 1))
          })
        }
        this.setData({
          dialogPageParams: res.content.page
        })
      }
    }, {
      ...this.data.dialogPageParams,
      isMedical:1,
      goodsName: this.data.goodsName
    },'',true)
  },
  resetDialogGoodsList (dataList,parentIndex) {
    return dataList.map(item => {
      item.goodsImg = this.data.imageUrl + item.goodsImg
      item.prdNumber = item.goodsNumber
      item.cartNumber = this.getDefaultGoodsNum(item.goodsId,this.data.goodsIdNum)
      item.prdPrice = item.shopPrice
      item.parentIndex = parentIndex
      item.selected = this.data.cacheGoodsIdList.includes(item.goodsId)
      return item
    })
  },
  getDefaultGoodsNum(goodsId,cacheList){
    if(!cacheList.map(item=>item.goodsId).includes(goodsId)) return 1
    return cacheList.find(item=>item.goodsId === goodsId).dragSumNum
  },
  toggleSelect (e) {
    let { detail: { goodsId } } = e, targetIndex = null
    let parentIndex = this.data.dialogGoodsList.findIndex(item => {
      targetIndex = item.findIndex(goodsItem => goodsItem.goodsId === goodsId)
      return targetIndex !== -1
    })
    let Target = this.data.dialogGoodsList[parentIndex][targetIndex]
    let selected = !Target.selected
    let cacheGoodsIdList = this.data.cacheGoodsIdList, cachePageGoodsList = this.data.cachePageGoodsList, cacheGoodsIdNum = this.data.cacheGoodsIdNum
    if (selected) {
      cacheGoodsIdList.push(Target.goodsId)
      cachePageGoodsList.push(Target)
      cacheGoodsIdNum.push({ goodsId: Target.goodsId, dragSumNum: Target.cartNumber, prdId:Target.goodsSpecProducts[0].prdId })
      this.setData({
        cacheGoodsIdList,
        cachePageGoodsList,
        cacheGoodsIdNum
      })
    } else {
      cacheGoodsIdList.splice(cacheGoodsIdList.findIndex(item => item === Target.goodsId), 1)
      cachePageGoodsList.splice(cachePageGoodsList.findIndex(item => item.goodsId === Target.goodsId), 1)
      cacheGoodsIdNum.splice(cacheGoodsIdNum.findIndex(item => item.goodsId === Target.goodsId), 1)
      this.setData({
        cacheGoodsIdList,
        cachePageGoodsList,
        cacheGoodsIdNum
      })
    }
    this.setData({
      [`dialogGoodsList[${parentIndex}][${targetIndex}].selected`]: selected,
    })
  },
  searchDialogGoods (e) {
    let { detail: { value: goodsName } } = e
    this.setData({
      goodsName,
      'dialogPageParams.currentPage': 1
    })
    this.requestGoodsList()
  },
  confirmGoods () {
    this.setData({
      goodsIdList: JSON.parse(JSON.stringify(this.data.cacheGoodsIdList)),
      pageGoodsList: JSON.parse(JSON.stringify(this.data.cachePageGoodsList)),
      goodsIdNum: JSON.parse(JSON.stringify(this.data.cacheGoodsIdNum)),
      showGoodsListDialog: false,
    })
  },
  // selectDepartment () {
  //   util.jumpLink(`pages2/allDepartment/allDepartment${util.getUrlParams({
  //     source: 'prescribe',
  //     doctorId: this.data.doctorId
  //   })}`)
  // },
  getDoctorAdvice ({ detail: { value } }) {
    this.setData({
      doctorAdvice: value
    })
  },
  delGoodsItem (e) {
    let { goodsId } = e.currentTarget.dataset
    let goodsIdList = this.data.goodsIdList, pageGoodsList = this.data.pageGoodsList, goodsIdNum = this.data.goodsIdNum
    goodsIdList.splice(goodsIdList.findIndex(item => item === goodsId), 1)
    pageGoodsList.splice(pageGoodsList.findIndex(item => item.goodsId === goodsId), 1)
    goodsIdNum.splice(goodsIdNum.findIndex(item => item.goodsId === goodsId), 1)
    this.setData({
      goodsIdList,
      pageGoodsList,
      goodsIdNum
    })
  },
  createPrescription () {
    if(!this.data.departmentCode){
      util.showModal('提示','请选择开方科室')
      return
    }
    if(!this.data.diagnose.info){
      util.showModal('提示','请输入您的诊断')
      return
    }
    if(!this.data.doctorAdvice){
      util.showModal('提示','请输入医嘱')
      return
    }
    let params = {
      patientId: this.data.patientId,
      doctorId: util.getCache('doctor_id') || util.getCache('bottom').doctor_id,
      departmentCode: this.data.departmentCode,
      departmentName: this.data.departmentName,
      diagnosisName: this.data.diagnose.info,
      doctorAdvice: this.data.doctorAdvice,
      goodsList: this.data.goodsIdNum,
      userId:this.data.patientUserId
    }
    util.api('/api/wxapp/prescription/add', res => {
      console.log(res)
      if (res.error === 0) {
        let {prescriptionCode,diagnosisName,departmentName} = res.content
        let pageList = getCurrentPages();
        let prevPage = pageList[pageList.length - 2];
        prevPage.setData({
          prescriptionMessage:{
            prescriptionCode,
            diagnosisName,
            departmentName,
            doctorName:this.data.doctorInfo.name,
            time:this.data.theDateToday
          }
        })
        wx.navigateBack()
      }
    }, {
      ...params
    })
  },
  getDoctorDetail(){
    util.api('/api/wxapp/doctor/auth/info',res=>{
      let {name} = res.content
      if(res.error === 0){
        this.setData({
          doctorInfo:{
            name
          }
        })
      }
    })
  },
  getPatientDetail(){
    util.api('/api/wxapp/user/patient/get/detail',res=>{
      if(res.error === 0){
        let {name,sex,age} = res.content
        this.setData({
          patientInfo:{
            name,
            sex,
            age
          }
        })
      }
    },{
      patientId:this.data.patientId,
      userId:this.data.patientUserId
    })
  },
  cartNumChange({detail:goodsInfo}){
    console.log(goodsInfo)
    let targetIndex = this.data.dialogGoodsList[goodsInfo.parentIndex].findIndex(item=> item.goodsId === goodsInfo.goodsId)
    let cachePageGoodsList = this.data.cachePageGoodsList, cacheGoodsIdNum = this.data.cacheGoodsIdNum
    let cacheGoodsListIndex = cachePageGoodsList.findIndex(item => item.goodsId === goodsInfo.goodsId)
    let cacheGoodsIdNumIndex = cacheGoodsIdNum.findIndex(item=>item.goodsId === goodsInfo.goodsId)
    this.setData({
      [`dialogGoodsList[${goodsInfo.parentIndex}][${targetIndex}].cartNumber`]: goodsInfo.type === 'plus' ? goodsInfo.cartNumber + 1 : goodsInfo.cartNumber - 1,
    })
    if(cacheGoodsListIndex !== -1 && cacheGoodsIdNumIndex !== -1){
      this.setData({
        [`cachePageGoodsList[${cacheGoodsListIndex}].cartNumber`]:goodsInfo.type === 'plus' ? goodsInfo.cartNumber + 1 : goodsInfo.cartNumber - 1,
        [`cacheGoodsIdNum[${cacheGoodsIdNumIndex}].dragSumNum`]:goodsInfo.type === 'plus' ? goodsInfo.cartNumber + 1 : goodsInfo.cartNumber - 1
      })
    }
  },
  customCartNum({detail:goodsInfo}){
    console.log(goodsInfo)
    let {goodsNumber,prdNumber} = goodsInfo
    let targetIndex = this.data.dialogGoodsList[goodsInfo.parentIndex].findIndex(item=> item.goodsId === goodsInfo.goodsId)
    let cachePageGoodsList = this.data.cachePageGoodsList, cacheGoodsIdNum = this.data.cacheGoodsIdNum
    let cacheGoodsListIndex = cachePageGoodsList.findIndex(item => item.goodsId === goodsInfo.goodsId)
    let cacheGoodsIdNumIndex = cacheGoodsIdNum.findIndex(item=>item.goodsId === goodsInfo.goodsId)
    if(goodsNumber > prdNumber) goodsNumber = prdNumber
    if(goodsNumber < 1)  goodsNumber = 1
    this.setData({
      [`dialogGoodsList[${goodsInfo.parentIndex}][${targetIndex}].cartNumber`]:goodsNumber
    })
    if(cacheGoodsListIndex !== -1 && cacheGoodsIdNumIndex !== -1){
      this.setData({
        [`cachePageGoodsList[${cacheGoodsListIndex}].cartNumber`]:goodsNumber,
        [`cacheGoodsIdNum[${cacheGoodsIdNumIndex}].dragSumNum`]:goodsNumber
      })
    }
  },
  bindClose(){
    this.setData({
      showGoodsListDialog:false
    })
  },
  getDepartmentList(){
    util.api('/api/wxapp/department/list', res => {
      if(res.error == 0){
        this.data.depart_all = res.content
        for (let i in res.content){
          this.data.depart_arr.push(res.content[i].name)
        }
        this.setData({
          depart_arr: this.data.depart_arr
        })
      }
    },{keyword:'',doctorId:this.data.doctorId})
  },
  changeDepart (e) {
    this.data.depart_index = e.detail.value;
    this.data.departmentCode = this.data.depart_all[e.detail.value].code;
    this.data.departmentName = this.data.depart_all[e.detail.value].name
    this.setData({
      depart_index: this.data.depart_index
    })
  },
  areaFocus(){
    this.setData({
      focusStatus:true
    })
  },
  areaBlur(){
    this.setData({
      focusStatus:false
    })
  },
  changePage(){
    if (
      this.data.dialogPageParams &&
      this.data.dialogPageParams.currentPage === this.data.dialogPageParams.lastPage && this.data.showGoodsListDialog === false
    ) {
      return;
    }
    this.setData({
      'dialogPageParams.currentPage': this.data.dialogPageParams.currentPage + 1
    });
    this.requestGoodsList();
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

function getToday () {
  let now = new Date()
  return `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, 0)}-${now.getDate()}`
}