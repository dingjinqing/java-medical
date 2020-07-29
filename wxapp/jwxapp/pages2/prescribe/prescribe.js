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
    GoodsIdNum: [],
    cachePageGoodsList: [], //页面商品列表
    pageGoodsList: [], //页面商品缓存列表
    goodsName: '', //搜索商品名
    dialogPageParams: { //弹窗页码
      currentPage: 1,
      pageRows: 20
    },
    departmentCode: null,
    departmentName: null,
    doctorAdvice: null
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  },
  clearInput () {
    this.setData({
      'prescriptionInfo.diagnose.canClear': false,
      'prescriptionInfo.diagnose.info': ''
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
      cacheGoodsIdList: this.data.goodsIdList,
      cachePageGoodsList: this.data.pageGoodsList,
      cacheGoodsIdNum: this.data.goodsIdNum,
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
            dialogGoodsList: [[...this.resetDialogGoodsList(res.content.dataList)]]
          })
        } else {
          this.setData({
            ['dialogGoodsList[' + (parseInt(currentPage) - 1) + ']']: this.resetDialogGoodsList(res.content.dataList)
          })
        }
        this.setData({
          dialogPageParams: res.content.page
        })
      }
    }, {
      ...this.data.dialogPageParams,
      goodsName: this.data.goodsName
    })
  },
  resetDialogGoodsList (dataList) {
    return dataList.map(item => {
      item.goodsImg = this.data.imageUrl + item.goodsImg
      item.prdNumber = item.goodsNumber
      item.cartNumber = 1
      item.prdPrice = item.shopPrice
      item.selected = this.data.cacheGoodsIdList.includes(item.goodsId)
      return item
    })
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
      cacheGoodsIdNum.push({ goodsId: Target.goodsId, goodsNumber: Target.cartNumber })
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
      goodsIdList: this.data.cacheGoodsIdList,
      pageGoodsList: this.data.cachePageGoodsList,
      goodsIdNum: this.data.cacheGoodsIdNum,
      showGoodsListDialog: false,
    })
  },
  selectDepartment () {
    util.jumpLink(`pages2/allDepartment/allDepartment${util.getUrlParams({
      source: 'prescribe'
    })}`)
  },
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
    let params = {
      patientId: 137,
      doctorId: 1,
      departmentCode: this.data.departmentCode,
      departmentName: this.data.departmentName,
      diagnosisName: this.data.diagnose.info,
      doctorAdvice: this.data.doctorAdvice,
      goodsIdList: this.data.goodsIdList
    }
    util.api('/api/wxapp/prescription/add', res => {
      console.log(res)
      if (res.error === 0) {

      }
    }, {
      ...params
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

function getToday () {
  let now = new Date()
  return `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, 0)}-${now.getDate()}`
}