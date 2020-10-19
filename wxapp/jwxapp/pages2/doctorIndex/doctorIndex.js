// pages2/doctorIndex/doctorIndex.js
var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({
	/**
   * 页面的初始数据
   */
	data: {
		imageUrl: app.globalData.imageUrl,
		show_modal: 0,
		name: '',
		mobile: '',
		mobileCheckCode: '',
		hosCode: '',
		doctorInfo: [],
		// 工作状态
		work_status: 1,
		show_work_modal: 0,
		clerkImagePath:{}
	},
	/**
   * 生命周期函数--监听页面加载
   */
	onLoad: function(options) {
		if (!util.check_setting(options)) return
		if (this.data.bottom.user_type != 1) {
			this.data.show_modal = 1
		} else {
			this.data.show_modal = 0
		}
		this.setData({
			show_modal: this.data.show_modal
		})
		this.requestInfo()
	},
	close() {
		this.setData({
			show_modal: 0
		})
		util.jumpLink('/pages/usercenter/usercenter', 'reLaunch')
	},
	docName(e) {
		this.data.name = e.detail.value
	},
	docMobile(e) {
		this.data.mobile = e.detail.value
	},
	docCode(e) {
		this.data.hosCode = e.detail.value
	},
	verificationInput(e) {
		this.data.mobileCheckCode = e.detail.value
	},
	to_verify() {
		if (!this.data.name) {
			util.showModal('提示', '请输入医生姓名')
			return false
		}
		if (!this.data.mobile) {
			util.showModal('提示', '请输入医生手机号')
			return false
		}
		if (!this.data.mobileCheckCode) {
			util.showModal('提示', '请输入验证码')
			return false
		}
		if (!this.data.hosCode) {
			util.showModal('提示', '请输入医生院内编号')
			return false
		}
		if (!this.data.clerkImagePath.imgPath) {
			util.showModal('提示', '请签名添加图片')
			return false
		}
		util.api(
			'/api/wxapp/doctor/auth',
			res => {
				if (res.error == 0) {
					// this.data.bottom.user_type = 1;
					// util.setCache('bottom',this.data.bottom)
					this.setData({
						show_modal: 0
					})
					this.requestInfo()
					// util.jumpLink('/pages2/doctorIndex/doctorIndex','reLaunch')
				} else if (res.error == 400031) {
					util.showModal('提示', '认证失败', function() {
						util.jumpLink('/pages/usercenter/usercenter', 'reLaunch')
					})
				} else {
					util.showModal('提示', res.message, () => {
						util.jumpLink('/pages/usercenter/usercenter', 'reLaunch')
					})
				}
			},
			{
				doctorName: this.data.name,
				mobile: this.data.mobile,
				hospitalCode: this.data.hosCode,
				mobileCheckCode: this.data.mobileCheckCode,
				userId: util.getCache('user_id'),
				signature:this.data.clerkImagePath.imgPath
			}
		)
	},
	requestInfo() {
		util.api(
			'/api/wxapp/doctor/main',
			res => {
				if (res.error == 0) {
					this.data.doctorInfo = res.content
					this.data.doctorInfo.departArr = []
					for (var i in this.data.doctorInfo.departmentName) {
						if (!!this.data.doctorInfo.departmentName[i]) {
							this.data.doctorInfo.departArr.push(this.data.doctorInfo.departmentName[i])
						}
					}
					this.data.doctorInfo.departArr = this.data.doctorInfo.departArr.join(',')
					this.setData({
						doctorInfo: this.data.doctorInfo
					})
				}
			},
			{}
		)
	},
	to_awaitPre() {
		util.jumpLink('/pages2/awaitprescribe/awaitprescribe')
	},
	viewInquiryList(e) {
		let { type } = e.currentTarget.dataset
		util.jumpLink(
			`/pages2/inquiryList/inquiryList${util.getUrlParams({
				type
			})}`
		)
	},
	viewOperatePrescriptionList() {
		util.jumpLink('/pages2/operatePrescriptionList/operatePrescriptionList')
	},
	getVerificationCode() {
		if (this.data.countDown) return false
		if (!this.data.mobile) {
			util.showModal('提示', '请输入手机号！')
			return false
		}
		if (!/^1[3456789]\d{9}$/.test(this.data.mobile)) {
			util.showModal('提示', '请输入正确的手机号！')
			return false
		}
		util.api(
			'/api/wxapp/doctor/send/check/code',
			res => {
				if (res.error === 0) {
					const TIME_COUNT = 60
					this.setData({
						countDown: TIME_COUNT
					})
					if (!this.timer) {
						this.timer = setInterval(() => {
							if (this.data.countDown > 0 && this.data.countDown <= TIME_COUNT) {
								this.setData({
									countDown: --this.data.countDown
								})
							} else {
								clearInterval(this.timer)
								this.timer = null
							}
						}, 1000)
					}
				} else {
					util.showModal('提示', res.message)
				}
			},
			{
				mobile: this.data.mobile
			}
		)
	},
	showChangeModal() {
		this.setData({
			show_work_modal: 1
		})
	},
	changeStatus(status) {
		util.api(
			'/api/wxapp/doctor/on/duty/update',
			res => {
				if (res.error == 0) {
					this.setData({
						work_status: status
					})
				} else {
					util.showModal('提示', res.message)
					return false
				}
			},
			{
				doctorId: util.getCache('doctor_id'),
				onDutyStatus: status
			}
		)
	},
	bindChangeWork(e) {
		let to_status = e.currentTarget.dataset.type
		let that = this
		if (to_status == 1) {
			util.showModal(
				'提示',
				'暂停接诊24小时后将重新开始接诊，确定暂停吗？',
				function() {
					that.changeStatus(0)
				},
				true,
				'取消',
				'确认'
			)
		} else {
			util.showModal(
				'提示',
				'确定开始接诊吗？',
				function() {
					that.changeStatus(1)
				},
				true,
				'取消',
				'确认'
			)
		}
	},
	viewPrescriptionList(e) {
		let { status } = e.currentTarget.dataset
		util.jumpLink(
			`/pages2/doctorPrescriptionList/doctorPrescriptionList${util.getUrlParams({
				status
			})}`
		)
	},
	viewWithdraw() {
		util.jumpLink(`pages2/serviceWithdraw/serviceWithdraw`)
	},
	showTipsDialog() {
		this.setData({
			tipShowDialog: true
		})
	},
	bindCloseTipsDialog() {
		this.setData({
			tipShowDialog: false
		})
	},
	changeDoctorData() {
		util.jumpLink(
			`/pages2/doctorCenter/doctorCenter${util.getUrlParams({
				treatDisease: this.data.doctorInfo.treatDisease
			})}`
		)
	},
	showCanvasContent() {
		// this.setData({
		//   showCanvas: true
		// })
		util.jumpLink('/pages3/clerkSign/clerkSign')
	},
	/**
   * 生命周期函数--监听页面初次渲染完成
   */
	onReady: function() {},

	/**
   * 生命周期函数--监听页面显示
   */
	onShow: function() {
		this.requestInfo()
	},

	/**
   * 生命周期函数--监听页面隐藏
   */
	onHide: function() {},

	/**
   * 生命周期函数--监听页面卸载
   */
	onUnload: function() {},

	/**
   * 页面相关事件处理函数--监听用户下拉动作
   */
	onPullDownRefresh: function() {
		// 显示导航栏loading
		wx.showNavigationBarLoading()
		// 调用接口加载数据
		this.requestInfo()
		wx.hideNavigationBarLoading()
		// 当处理完数据刷新后，wx.stopPullDownRefresh可以停止当前页面的下拉刷新
		wx.stopPullDownRefresh()
	},
	viewQrCodeInfo(){
    util.api('/api/wxapp/public/service/bind/getOfficialQrCode',res=>{
      if(res.error === 0){
        this.setData({
          showQrCode:true,
          QrCodeImage:res.content
        })
      } else {
        util.showModal('提示','获取二维码失败')
      }
    },{})
	},
	bindCloseQrCode(){
    this.setData({
      showQrCode:false
    })
  },

	/**
   * 页面上拉触底事件的处理函数
   */
	onReachBottom: function() {},

	/**
   * 用户点击右上角分享
   */
	onShareAppMessage: function() {}
})
