const util = require("../../utils/util.js");
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    dataList: null,
    pageParams: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    console.log(options);
    let action = options.action ? options.action : 1;

    if (parseInt(action) === 1) {
      this.setData({
        page_name: "历史购买"
      });
    }
    this.requestList();
  },
  requestList() {
    let currentPage = this.data.pageParams
      ? this.data.pageParams.currentPage
      : 1;
    util.api(
      "/api/wxapp/footprint/list",
      res => {
        if(res.error === 0){
          let dataList = this.setDataList(res.content.footprintDay)
          this.setData({
            pageParams: res.content.page,
            ['dataList[' + (parseInt(currentPage) - 1) + ']']: dataList
          });
        }
      },
      {
        currentPage: currentPage,
        pageRows: 2,
        userId: util.getCache('user_id')
      }
    );
  },
  setDataList(dataList){
    if(!this.data.dataList){
      return dataList
    } else {
      let newData = dataList.map(item=>{return item.date})
      let oldData = []
      this.data.dataList.forEach((pageItem,pageIndex)=>{
        pageItem.forEach((dateItem, dateIndex) => {
          let { date } = dateItem
          oldData.push({
            date,
            dateIndex,
            pageIndex
          })
        })
      })
      let repeatDate = newData.map(newItem=>{
        return oldData.find(oldItem => oldItem.date === newItem)
      }).filter(item => item!==undefined)
      repeatDate.forEach(repeatItem=>{
        let concatData = dataList.find(item=>{
          return repeatItem.date === item.date
        })
        let target = `dataList[${repeatItem.pageIndex}][${repeatItem.dateIndex}].goodsList`
        let dataTarget = this.data.dataList[repeatItem.pageIndex][repeatItem.dateIndex].goodsList
        this.setData({
          [target]: dataTarget.concat(concatData.goodsList)
        })
        let repeatIndex = dataList.findIndex(item => {
          return repeatItem.date === item.date
        })
        if (repeatIndex !== -1){
          dataList.splice(repeatIndex,1)
        }
      })
      return dataList
    }
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {},

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {},

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
  onPullDownRefresh: function() {},

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    if (
      this.data.pageParams &&
      this.data.pageParams.currentPage === this.data.pageParams.lastPage
    )
      return;
    this.setData({
      'pageParams.currentPage': this.data.pageParams.currentPage + 1
    });
    this.requestList();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {}
});
