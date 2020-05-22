global.wxPage({
  onLoad: function (options) {
    let {pledgeList} = options
    this.setData({
      pledgeList:JSON.parse(pledgeList)
    })
  }
})