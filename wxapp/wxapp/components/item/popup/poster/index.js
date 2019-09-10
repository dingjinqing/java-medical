var util = require("../../../../utils/util.js");
var base = require("../../../popup/base/base.js")
global.wxComponent({
  mixins: [base],
  data: {
    show_popup: false
  },
  properties: {
    show: {
      type: Boolean,
      value: false,
      observer(newVal, oldVal, changedPath) {
        if (newVal) {
          this.check().then(() => this.getShareImage());
        }
      }
    },
    poster: {
      type: Object,
      value: {},
    }
  },
  methods: {
    check() {
      var _this = this;
      var poster = this.data.poster;
      return new Promise(function(resolve) {
        if (poster.action == 4) {
          _this.checkSecKill(resolve);
        } else if (poster.action == 3) {
          _this.checkBargainItem(resolve);
        } else {
          resolve();
        }
      });
    },
    checkSecKill(cb) {
      var poster = this.data.poster;
      util.api('/api/wxapp/seckill/goods', function(res) {
        if (res.content.is_delete == 1) {
          util.showModal('提示', '该商品已删除', function() {
            util.jumpLink("/pages/index/index", "reLaunch");
          });
          return;
        }
        if (res.content.can_seckill.status == 1 || res.content.can_seckill.status == 2 ||
          res.content.can_seckill.status == 4 || res.content.can_seckill.status == 6) {
          util.showModal('暂不支持分享', res.content.can_seckill.msg, function() {
            util.jumpLink("/pages/item/item?goods_id=" + poster.goods_id);
          }, false, '取消', '原价购买')
          return false;
        }
        cb();
      }, {
        seckill_id: poster.sk_id
      });
    },
    checkBargainItem(cb) {
      util.api('/api/wxapp/bargain/goods', function(res) {
        if (res.content.is_delete == 1) {
          util.showModal('提示', '该商品已删除', function() {
            util.jumpLink("/pages/index/index", "reLaunch");
          });
          return;
        }
        if (res.content.can_bargain.status == 1 || res.content.can_bargain.status == 2 || res.content.can_bargain.status == 4 || res.content.can_bargain.status == 6) {
          util.showModal('暂不支持分享', res.content.can_bargain.msg, function() {
            util.jumpLink("/pages/item/item?goods_id=" + poster.goods_id);
          }, false, '取消', '原价购买')
          return false;
        }
        cb();
      }, {
        bargain_id: poster.bargain_id
      });
    },
    checkGroupPinItem() {
      util.api('/api/wxapp/pin_goods', function(res) {
        if (res.error != 0) {
          util.alert("提示", res.content);
          return false
        }
        if (res.content.can_pin_group.status == 1 || res.content.can_pin_group.status == 2 ||
          res.content.can_pin_group.status == 4 || res.content.can_pin_group.status == 5) {
          util.showModal('暂不支持分享', res.content.can_pin_group.msg, function() {
            util.jumpLink("/pages/item/item?goods_id=" + poster.goods_id);
          }, false, '取消', '原价购买')
          return false;
        }
      }, {
        pin_group_id: poster.pin_group_id
      });
    },
    getShareImage: function() {
      var that = this;
      wx.showLoading({
        title: '生成中',
      })
      this.setData({
        show_popup: false,
      });
      util.api('/api/wxapp/pictorial', function(res) {
        if (res.error == 0) {
          var pictorial = res.content.pictorial;
          if (pictorial) {
            that.basePicUrl = that.data.baseUrl + pictorial;
            pictorial = that.data.imageUrl + pictorial + "!big";
            that.setData({
              show_popup: true,
              pictorial: pictorial,
            })
            wx.hideLoading();
          }
        } else {
          wx.hideLoading();
          util.toast_fail(res.message);
          return false;
        }
      }, this.data.poster)
    },
    bindSaveImgToPhotosAlbumTap(e) {
      if (!this.basePicUrl) {
        util.alert("图片还未生成，请等待！");
        return;
      }
      var that = this;
      wx.downloadFile({
        url: this.basePicUrl,
        success: function(res) {
          wx.saveImageToPhotosAlbum({
            filePath: res.tempFilePath,
            success: function(res) {
              wx.getSystemInfo({
                success: function(res) {
                  if (res.platform == 'ios') {
                    util.toast_success('保存成功');
                  }
                }
              })
              that.setData({
                show_popup: false,
              })
              that.bindClose(e);
            },
            fail: function(res) {}
          })
        },
        fail: function() {}
      })
    },
  }
})