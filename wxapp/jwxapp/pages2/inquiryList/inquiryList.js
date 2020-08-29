var util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    targetStatus: '1',
    filterParams: {
      sessionStatus: [1],
      doctorId: util.getCache('doctor_id') || util.getCache('bottom').doctor_id,
      userId: null
    },
    pageParams: {
      currentPage: 1,
      pageRows: 20
    },
    show_modal: 0,
    refundReason: '',
    returnTargetData: {},
    show_announce: false,
    comment_note:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let {
      type
    } = options
    if (type === 'wait') {
      this.setData({
        targetStatus: '1',
        'filterParams.sessionStatus': [1],
      })
    }
    if (type === 'my') {
      this.setData({
        targetStatus: '2',
        'filterParams.sessionStatus': [2, 4, 5, 6],
      })
    }
    this.requestSessionList()
  },
  requestSessionList() {
    util.api('/api/wxapp/im/session/page/list', res => {
      console.log(res)
      if (res.error === 0) {
        if (this.data.pageParams.currentPage === 1) {
          this.setData({
            dataList: [
              [...res.content.dataList]
            ]
          })
        } else {
          this.setData({
            ['dataList[' + (parseInt(this.data.pageParams.currentPage) - 1) + ']']: res.content.dataList
          })
        }
        this.setData({
          pageParams: res.content.page
        })
      }
    }, {
      ...this.data.filterParams,
      ...this.data.pageParams
    })
  },
  getCommentList() {
    let currentPage = this.data.pageParams ? this.data.pageParams.currentPage : 1;
    util.api('/api/wxapp/patient/doctor/comment/list', res => {
      console.log(res)
      if (res.error === 0) {
        let list = res.content.dataList
        list.forEach(function (item, index) {
          if (item.commNoteLength >= 75) {
            item.new_comm_note = item.commNote.substring(0, 72) + '...';
            item.need_expand = true;
            item.zk = true
          } else {
            item.new_comm_note = item.commNote;
          }
          if(item.replylist){
            if(item.replylist[0].replyNote.length >= 70){
              item.rp_new_comm_note = item.replylist[0].replyNote.substring(0, 67) + '...';
              item.rp_need_expand = true;
              item.rp_zk = true
            }else{
              item.rp_new_comm_note = item.replylist[0].replyNote;
            }
          }
        })
        if (this.data.pageParams.currentPage === 1) {
          this.setData({
            dataList: [
              [...list]
            ]
          })
        } else {
          this.setData({
            ['dataList[' + (parseInt(currentPage) - 1) + ']']: list
          })
        }
        this.setData({
          pageParams: res.content.page
        })
      }
    }, {
      ...this.data.pageParams,
      doctorId: this.data.doctorId
    })
  },

  toggleStatus(e) {
    let {
      type
    } = e.currentTarget.dataset
    this.setData({
      targetStatus: type,
      'pageParams.currentPage': 1,
      'filterParams.sessionStatus': type === '1' ? [1] : [2, 4, 5, 6]
    })
    if (this.data.targetStatus == 3) {
      this.getCommentList()
    } else {
      this.requestSessionList()
    }

  },
  accept(e) {
    let {
      parentIndex,
      sessionId
    } = e.currentTarget.dataset
    let targetIndex = this.data.dataList[parentIndex].findIndex(item => item.id === sessionId)
    let target = this.data.dataList[parentIndex][targetIndex]
    let changeOrderStatus = {
      1: 2, //待接诊-正在接诊状态,
      4: 2 //会话结束-正在接诊状态
    }
    util.api('/api/wxapp/inquiry/order/status/update', res => {
      if (res.error === 0) {
        if (target.sessionStatus === 1) {
          let targetList = this.data.dataList[parentIndex]
          targetList.splice(targetList.findIndex(item => item.id === sessionId), 1)
          this.setData({
            [`dataList[${parentIndex}]`]: targetList
          })
        } else {
          this.setData({
            [`dataList[${parentIndex}][${targetIndex}].sessionStatus`]: 5
          })
        }
        util.jumpLink(`pages2/doctorChat/doctorChat${util.getUrlParams({
          targetUserInfo:JSON.stringify({...{...target,sessionStatus:target.sessionStatus === 1 ? 2 : 5},parentIndex}),
          source:'inquiryList'
        })}`)
      }
    }, {
      orderSn: target.orderSn,
      orderStatus: changeOrderStatus[target.sessionStatus],
      sessionId
    })
  },
  returnOrder(e) {
    util.showModal('提示', '确认取消此次问诊么？', () => {
      let {
        parentIndex,
        sessionId
      } = e.currentTarget.dataset
      let targetIndex = this.data.dataList[parentIndex].findIndex(item => item.id === sessionId)
      let target = this.data.dataList[parentIndex][targetIndex]
      this.data.returnTargetData = {
        parentIndex,
        sessionId,
        target
      }
      this.setData({
        show_modal: 1,
        refundReason: ''
      })
    }, true, '取消', '确认')
  },
  requestReturn() {
    let {
      parentIndex,
      sessionId,
      target
    } = this.data.returnTargetData
    util.api('/api/wxapp/inquiry/order/refund', res => {
      console.log(res)
      if (res.error === 0) {
        let targetList = this.data.dataList[parentIndex]
        console.log(targetList.findIndex(item => item.id === sessionId))
        targetList.splice(targetList.findIndex(item => item.id === sessionId), 1)
        this.setData({
          [`dataList[${parentIndex}]`]: targetList
        })
        this.close_modal()
      }
    }, {
      orderSn: target.orderSn,
      refundReason: this.data.refundReason
    })
  },
  viewChat(e) {
    let {
      parentIndex,
      sessionId
    } = e.currentTarget.dataset
    let targetIndex = this.data.dataList[parentIndex].findIndex(item => item.id === sessionId)
    let target = this.data.dataList[parentIndex][targetIndex]
    if (![2, 4, 5, 6].includes(target.sessionStatus)) return
    util.jumpLink(`pages2/doctorChat/doctorChat${util.getUrlParams({
      targetUserInfo:JSON.stringify({...target,parentIndex}),
      source:'inquiryList'
    })}`)
  },
  close_modal() {
    this.setData({
      show_modal: 0,
      refundReason: ''
    })
  },
  changeReason(e) {
    let {
      value
    } = e.detail
    this.setData({
      refundReason: value
    })
  },
  to_reply(e) {
    let {pidx,idx,id} = e.currentTarget.dataset
    this.setData({
      commentId:id,
      pidx:pidx,
      idx:idx,
      show_announce:true,
      comment_note:''
    })
  },
  del_reply(e){
    let that = this
    let {pidx,idx,id} = e.currentTarget.dataset
    util.showModal('', '确定要删除该回复吗？', function () {
      util.api('/api/wxapp/doctor/comment/reply/delete', function (res) {
        if (res.error === 0) {
          that.setData({
            ['dataList[' + pidx+ '][' + idx +'].replylist']: null
          })
        }
      }, { id: id })
    }, true, '取消', '确定')
   
  },
  com_publish() {
    let that = this;
    let comment = that.selectComponent("#comment");
    let {pidx,idx} = that.data
    util.api('/api/wxapp/doctor/comment/reply/add', res => {
      console.log(res)
      if (res.error === 0) {
        util.toast_success('回复成功！')
        let item = that.data.dataList[pidx][idx]
        if(res.content.replyNote.length >= 70){
          item.rp_new_comm_note = res.content.replyNote.substring(0, 67) + '...';
          item.rp_need_expand = true;
          item.rp_zk = true
        }else{
          item.rp_new_comm_note = res.content.replyNote;
        }
        item.replylist = []
        item.replylist.push(res.content)
        this.setData({
          ['dataList[' + pidx+ '][' + idx +']']: item,
          show_announce: false,
        })
        return
      }
    }, {
      commentId: that.data.commentId,
      replyNote: that.data.comment_note
    })
  },
  comment_note(e){
    this.setData({
      comment_note:e.detail.value
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
    // this.setData({
    //   'pageParams.currentPage': 1
    // })
    // this.requestSessionList()
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    if (
      this.data.pageParams &&
      this.data.pageParams.currentPage === this.data.pageParams.lastPage
    ) {
      return;
    }
    this.setData({
      'pageParams.currentPage': this.data.pageParams.currentPage + 1
    });
    if (this.data.targetStatus == 3) {
      this.getCommentList()
    } else {
      this.requestSessionList()
    }
  },
  zk_sq(e){
    let pidx = e.currentTarget.dataset.pidx;
    let idx = e.currentTarget.dataset.idx;
    let dataList = this.data.dataList;
    this.setData({
      ['dataList[' + pidx+ '][' + idx +'].zk']: !dataList[pidx][idx].zk
    })
  },
  rp_zk_sq(e){
    let pidx = e.currentTarget.dataset.pidx;
    let idx = e.currentTarget.dataset.idx;
    let dataList = this.data.dataList;
    this.setData({
      ['dataList[' + pidx+ '][' + idx +'].rp_zk']: !dataList[pidx][idx].rp_zk
    })
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

})