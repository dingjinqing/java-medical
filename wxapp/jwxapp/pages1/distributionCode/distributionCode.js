// pages1/distributionCode/distributionCode.js
var app = new getApp();
var util = require('../../utils/util.js');
var imageUrl = app.globalData.imageUrl;

global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    imageFile: {}, // 图片信息
    codeImage: '',
    imageId: '',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    this.initDataList()
  },

  // 初始化
  initDataList () {
    util.api('/api/wxapp/distribution/distributor/image/get', res => {
      if (res.error == 0) {
        this.setData({
          codeImage: res.content.imgUrl,
          imageId: res.content.imgId,
          imageFile: res.content
        })
      } else {
        util.showModal("提示", res.message);
        return;
      }
    }, {
      distributorId: util.getCache('user_id')
    })
  },

  // 上传图片
  upImage () {
    var that = this;
    var url = util.getUrl('/api/wxapp/image/upload');
    wx.chooseImage({
      count: 1, // 默认1
      sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths
        if (res) {
          for (var i = 0; i < tempFilePaths.length; i++) {
            (function (i) {
              wx.getImageInfo({
                src: tempFilePaths[i],
                success: function (obj) {
                  console.log(obj)
                  var params = {
                    userId: util.getCache('user_id'),
                    imgCatId: -1,
                    needImgWidth: obj.width,
                    needImgHeight: obj.height
                  }
                  util.uploadFile(url, tempFilePaths[i], params, function (e) {
                    var data = JSON.parse(e.data);
                    console.log(data)
                    if (data.error === 0) {
                      that.setData({
                        codeImage: data.content.imgUrl,
                        imageFile: data.content
                      })
                    } else {
                      util.toast_fail(data.message)
                    }
                  });
                },
                fail: function (err) {
                  throw err
                }
              })
            })(i)
          }
        }
      }
    })
  },

  // 删除图片
  delImage () {
    this.setData({ 
      codeImage: '',
      'imageFile.imgName': '',
      'imageFile.imgUrl': ''
    })
  },

  // 保存配置
  toSave () {
    // if (this.data.codeImage == '') {
    //   util.showModal("提示", "请上传图片");
    //   return;
    // }
    var params = {
      imageId: this.data.imageId,
      submittedFileName: this.data.imageFile.imgName || '',
      contentType: this.data.imageFile.imgType || '',
      size: this.data.imageFile.imgSize || 0,
      uploadImageParam: {
        needImgWidth: this.data.imageFile.imgWidth || 0,
        needImgHeight: this.data.imageFile.imgHeight || 0,
        imgCatId: -1,
        userId: util.getCache('user_id')
      },
      uploadPath: {
        relativeFilePath: this.data.imageFile.imgPath || '',
        imageUrl: this.data.imageFile.imgUrl || ''
      }
    }
    util.api('/api/wxapp/distribution/distributor/image/add', res => {
      if (res.error == 0) {
        util.toast_success('保存成功');
        setTimeout(function () {
          util.reLaunch({
            url: '/pages1/distribution/distribution',
          })
        }, 2000);
      }
    }, { ...params })
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})