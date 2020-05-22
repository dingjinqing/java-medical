var app = new getApp();
var util = require('../../utils/util.js');
var is_preview_link = '';
var is_preview = '';
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    assess_info: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.setData({
      assess_id: options.assess_id
    });
    is_preview_link = options.is_preview == 1 ? '&is_preview=1' : '';
    is_preview = options.is_preview == 1 ? 1 : 0;
    this.requestInfo(this.data.assess_id);
  },

  goAssessInfo() {
    let buttonImg = '';
    let buttonColor = '';
    let assess_judge_type = '&assess_judge_type=0';
    if (this.data.assess_info.cover_style_type == 1) {
      buttonImg =
        '&button_img=' + this.data.assess_info.cover_style.next_button_img;
    } else {
      buttonColor =
        '&button_color=' +
        JSON.stringify({
          text_color: this.data.assess_info.cover_style.button_text_color,
          bg_color: this.data.assess_info.cover_style.button_bg_color
        });
    }
    if (this.data.assess_info.assess_judge_type == 1) {
      assess_judge_type = '&assess_judge_type=1';
    }
    this.requestInfo(this.data.assess_id, () => {
      if (this.data.assess_info.is_block == 1 && is_preview != 1) {
        util.toast_fail('测评已停用');
        return false;
      }
      if (this.data.assess_info.del_flag == 1 && is_preview != 1) {
        util.toast_fail('测评已删除');
        return false;
      }
      if (this.data.assess_info.pub_flag == 0 && is_preview != 1) {
        util.toast_fail('测评未开始');
        return false;
      }
      let now = new Date();
      let assess_end_time = new Date(
        this.data.assess_info.end_time.replace(/-/g, '/')
      );
      if (
        this.data.assess_info.due_time_type === 0 &&
        now > assess_end_time &&
        is_preview != 1
      ) {
        util.toast_fail('测评已过期');
        return false;
      }
      if (this.data.assess_info.go_on == 0) {
        util.jumpLink(
          '/pages2/assessinfo/assessinfo?assess_id=' +
            this.data.assess_info.id +
            buttonImg +
            assess_judge_type +
            buttonColor +
            '&assess_name=' +
            this.data.assess_info.act_name +
            is_preview_link,
          'navigateTo'
        );
      } else {
        util.toast_fail('达到测评限制数量');
      }
    });
  },

  requestInfo(assess_id, cb) {
    var that = this;
    util.api(
      '/api/wxapp/assess/activity',
      function(res) {
        if (res.error == 0) {
          res.content.cover_style = JSON.parse(res.content.cover_style);
          res.content.cover_style.assess_desc = util.filterRichText(
            res.content.cover_style.assess_desc
              ? res.content.cover_style.assess_desc.replace(
                  '<!doctype html>',
                  ''
                )
              : ''
          );
          that.setData({
            assess_info: res.content
          });
          cb && cb();
        }
      },
      {
        assess_id: assess_id,
        is_preview: is_preview
      }
    );
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
  onReachBottom: function() {},

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {}
});
