var app = new getApp();
var util = require('../../utils/util.js');
var is_preview = ''
var is_preview_link = ''
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    assessData:{
      assess_id:0,
      topic_id:0,
      record_id:0,
      option_id:0,
      next_skip:0,
      is_last_topic:0
    },
    topic_num:0,
    topic_ids:[],
    topic_point:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var button_img = options.button_img ? options.button_img : '';
    var button_color = options.button_color ? JSON.parse(options.button_color) : '';
    var assess_judge_type = options.assess_judge_type;
    is_preview_link = options.is_preview == 1 ? '&is_preview=1' : '';
    is_preview = options.is_preview == 1 ? 1 : 0;
    if (this.data.linColor){
      let linColor = this.data.linColor.slice(0, this.data.linColor.lastIndexOf(',')) + ',0.4)';
      this.setData({
        topicBgColor: linColor
      })
    }
    this.setData({
      'assessData.assess_id': options.assess_id,
      button_img: button_img,
      assess_judge_type: assess_judge_type,
      button_color: button_color,
      assess_name:options.assess_name
    })
    console.log(this.data.assessData)
    this.request_info();
  },


  request_info(cb){
    var that = this;
    util.api('/api/wxapp/assess/topic', function (res) {
      if (res.error == 0) {
        if (res.content.assess_status && res.content.assess_status == 1){
          util.jumpLink('/pages2/assessend/assessend?assess_id=' + res.content.assess_id + '&record_id=' + res.content.record_id + is_preview_link, 'redirectTo')
        } else {
          res.content.option_content = JSON.parse(res.content.option_content);
          that.setData({
            topic_info: res.content,
            topic_num: ++that.data.topic_num
          })
          cb && cb()
        }
        }
    }, { ...this.data.assessData, is_preview: is_preview })
  },

  single_choice(e){
    let index = e.currentTarget.dataset.index;
    let option_content =  this.data.topic_info.option_content;
    option_content.forEach((el, i) => {
      el.isCheck = 0
    })
    option_content[index].isCheck = 1;
    this.setData({
      'topic_info.option_content': option_content,
      'assessData.next_skip': this.data.topic_info.next_skip,
      'assessData.topic_id': this.data.topic_info.id,
      'assessData.option_id': index,
      'assessData.record_id': this.data.topic_info.record_id,
      'assessData.is_last_topic': this.data.topic_info.is_last_topic
    })
  },
  multiple_choice(e){
    let index = e.currentTarget.dataset.index;
    let option_content = this.data.topic_info.option_content;
    let topic_ids = this.data.topic_ids;
    option_content.forEach((el, i) => {
      if(index == i){
        if (el.isCheck && el.isCheck == 1){
          el.isCheck = 0;
          if(topic_ids.indexOf(index) != -1){
            topic_ids.splice(topic_ids.indexOf(index),1)
          }
        } else {
          el.isCheck = 1;
          topic_ids.push(index)
        }
      }
    })
    console.log(topic_ids);
    this.setData({
      'topic_info.option_content': option_content,
      'assessData.next_skip': this.data.topic_info.next_skip,
      'assessData.topic_id': this.data.topic_info.id,
      'assessData.option_id': topic_ids.join(','),
      'assessData.record_id': this.data.topic_info.record_id,
      'assessData.is_last_topic': this.data.topic_info.is_last_topic,
      topic_ids: topic_ids
    })
  },
  nextTopic(){
    let choice = 0
    this.data.topic_info.option_content.forEach((el)=>{
      if (el.isCheck && el.isCheck == 1){
        choice = 1;
        return;
      }
    })
    if (choice){
      // if (this.data.assessData.skip_type == 2 || this.data.topic_info.option_skip_type == 2 || this.data.topic_info.is_last_topic == 1){
        
      //   util.jumpLink('/pages2/assessend/assessend?assessdata=' + JSON.stringify(this.data.assessData) + '&option_skip_value=' + this.data.topic_info.option_skip_value,'redirectTo')
      // } else {
        this.request_info(()=>{
          this.setData({
            topic_ids: []
          })
          wx.pageScrollTo({
            scrollTop: 0,
            duration:0
          })
        });
      // }
    } else {
      util.toast_fail('请选择选项');
    }
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

})