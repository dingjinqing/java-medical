var cache = require('./cache.js');
var config = require('../config.js');

var helper = {
  getShopId () {
    return config.shop_id;
  },
  isStr (v) {
    return typeof v === 'string';
  },
  isNum (v) {
    return typeof v === 'number';
  },
  isArr (v) {
    return Array.isArray(v);
  },
  isUndef (v) {
    return v === undefined;
  },
  isObject (v) {
    return v !== null && typeof v === 'object'
  },
  isPlainObject (obj) {
    return _toString.call(obj) === '[object Object]';
  },
  hasOwn (obj, key) {
    var hasOwnProperty = Object.prototype.hasOwnProperty;
    return hasOwnProperty.call(obj, key)
  },
  isFunc (v) {
    return typeof v === 'function';
  },
  isJson (str) {
    try {
      return (typeof JSON.parse(str) == "object");
    } catch (e) {
      return false;
    }
  },
  toArray (list, start) {
    if (start === void 0) start = 0;

    var i = list.length - start;
    var rst = new Array(i);
    while (i--) {
      rst[i] = list[i + start];
    }
    return rst;
  },

  leftPad0 (n, totalBits) {
    var ret = n + "";
    for (var i = ret.length; i < totalBits; i++) {
      ret = "0" + ret;
    }
    return ret;
  },

  // 倒计时处理时间的函数
  expandTime (seconds) {
    return {
      date: Math.floor(seconds / 3600 / 24 / 1000),
      hour: this.leftPad0(Math.floor(seconds / 3600 / 1000) % 24, 2),
      minute: this.leftPad0(Math.floor(seconds / 60 / 1000) % 60, 2),
      second: this.leftPad0(Math.floor(seconds / 1000) % 60, 2)
    };
  },

  formatTime (date) {
    return date.getFullYear() + "-" +
      this.leftPad0(date.getMonth() + 1, 2) + "-" +
      this.leftPad0(date.getDate(), 2) + " " +
      this.leftPad0(date.getHours(), 2) + ":" +
      this.leftPad0(date.getMinutes(), 2) + ":" +
      this.leftPad0(date.getSeconds(), 2);
  },

  getDistance (lat1, lng1, lat2, lng2) {
    lat1 = lat1 || 0;
    lng1 = lng1 || 0;
    lat2 = lat2 || 0;
    lng2 = lng2 || 0;
    var rad1 = lat1 * Math.PI / 180.0;
    var rad2 = lat2 * Math.PI / 180.0;
    var a = rad1 - rad2;
    var b = lng1 * Math.PI / 180.0 - lng2 * Math.PI / 180.0;
    var r = 6378137;
    var dis = r * 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(rad1) * Math.cos(rad2) * Math.pow(Math.sin(b / 2), 2)))
    return (dis / 1000).toFixed(2);
  },

  formatNumber (n) {
    n = n.toString()
    return n[1] ? n : '0' + n
  },

  // 倒计时处理时间的函数
  dateformat: function (seconds) {
    var d = this.expandTime(seconds);
    var result = [d.hour, d.minute, d.second];
    if (d.date > 0) result = [d.date, d.hour, d.minute, d.second];
    return result;
  },

  // 倒计时时间格式2,返回格式： d天hh:mm:ss
  dateformat2: function (seconds) {
    var d = this.expandTime(seconds);
    var result = d.hour + ':' + d.minute + ":" + d.second;
    if (d.date > 0) result = d.date + '天' + result;
    return result;
  },

  concatJson (json1, json2) {
    var resultJson = {};
    for (var attr in json1) {
      resultJson[attr] = json1[attr];
    }
    for (var attr in json2) {
      resultJson[attr] = json2[attr];
    }
    return resultJson;
  },

  isIPhoneX () {
    var res = wx.getSystemInfoSync();
    var model = res.model
    return (model.search('iPhone X') != -1 || model.search('iPhone XS') != -1 ||
      model.search('iPhone XR') != -1 || model.search('iPhone XS Max') != -1)
  },

  toast_success (title, back) {
    wx.showToast({
      title: title,
      icon: 'success',
      duration: 2000,
      success: function () {
        if (back) {
          back();
        }
      }
    })
  },

  toast_fail (title) {
    wx.showToast({
      title: title,
      image: '/images/fail.png',
      duration: 2000,
    })
  },
  alert (content, title = null, back = null, confirmText = null) {
    title = title || "提示";
    this.showModal(title, content, back, false, null, confirmText);
  },
  confirm (title, content, back, cancelText, confirmText) {
    this.showModal(title, content, back, true, cancelText, confirmText);
  },
  showModal (title, content, back, showCancel, cancelText, confirmText) {
    cancelText = cancelText || "取消";
    confirmText = confirmText || '确定';
    showCancel = showCancel || false;
    wx.showModal({
      title: title,
      content: content,
      showCancel: showCancel,
      cancelText: cancelText,
      confirmText: confirmText,
      confirmColor: cache.getCache('main_colors'),
      success: function (res) {
        if (res.confirm && back) {
          back(res);
        }
      }
    })
  },
  getUserLocation (callBack, refresh, altitude) {
    refresh = refresh ? true : false;
    var location = cache.getCache('userLocation');
    if (refresh || !location) {
      wx.getLocation({
        type: 'wgs84',
        altitude: altitude ? true : false,
        success (res) {
          cache.setCache('userLocation', res)
          location = res;
          if (callBack) callBack(location);
        },
        fail (res) {
          if (callBack) callBack(location);
        }
      })
    } else {
      if (callBack) callBack(location);
    }
  },
  colorRgb (str, opacity) {
    var sColor = str.toLowerCase();
    if (sColor) {
      if (sColor.length === 4) {
        var sColorNew = "#";
        for (var i = 1; i < 4; i += 1) {
          sColorNew += sColor.slice(i, i + 1).concat(sColor.slice(i, i + 1));
        }
        sColor = sColorNew;
      }
      //处理六位的颜色值
      var sColorChange = [];
      for (var i = 1; i < 7; i += 2) {
        sColorChange.push(parseInt("0x" + sColor.slice(i, i + 2)));
      }
      return "rgba(" + sColorChange.join(",") + "," + opacity + ")";
    } else {
      return sColor;
    }
  },
  getDistance (lat1, lng1, lat2, lng2) {
    lat1 = lat1 || 0;
    lng1 = lng1 || 0;
    lat2 = lat2 || 0;
    lng2 = lng2 || 0;
    var rad1 = lat1 * Math.PI / 180.0;
    var rad2 = lat2 * Math.PI / 180.0;
    var a = rad1 - rad2;
    var b = lng1 * Math.PI / 180.0 - lng2 * Math.PI / 180.0;
    var r = 6378137;
    var dis = r * 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(rad1) * Math.cos(rad2) * Math.pow(Math.sin(b / 2), 2)))
    return (dis / 1000).toFixed(2);
  },
  check_setting (o) {
    // 兼容
    return true;
  },
  values (obj, props) {
    if (typeof obj != 'object') return {};
    var ret = {};
    for (var i in props) {
      if (obj[props[i]]) ret[props[i]] = obj[props[i]];
    }
    return ret;
  },
  addHtmlProp (html, tag, prop, value) {
    var reg = new RegExp("(<" + tag + ")(>|\\s+[^>]*?>)", "gim");
    return html.replace(reg, function (arg0, arg1, arg2) {
      var reg2 = new RegExp(prop + "=\[\"'\](.*?)\['\"\]", "gim");
      if (reg2.test(arg2)) {
        return arg1 + arg2.replace(reg2, prop + "='$1" + value + "'");
      } else {
        return arg1 + " " + prop + "='" + value + "' " + arg2;
      }
    });
  },
  filterRichText (rich_text) {
    rich_text = this.addHtmlProp(rich_text, "img", "style", ";vertical-align:middle;max-width:100%;height:auto");
    return rich_text;
  },
  proxyMethod (_this, method, queue, queue_before) {
    var old = _this[method];
    _this[method] = function () {
      if (queue_before) {
        for (var i in queue) {
          queue[i].fn.apply(queue[i]._this, arguments);
        }
      }
      if (old) old.apply(_this, arguments);
      if (!queue_before) {
        for (var i in queue) {
          queue[i].fn.apply(queue[i]._this, arguments);
        }
      }
    }
  },
  base64ImageHandle (base64, cb) {
    const that = this;
    const timestamp = new Date().getTime() + Math.ceil(Math.random() * 1000000);
    // 指定图片的临时路径
    const path = `${wx.env.USER_DATA_PATH}/${timestamp}.png`;
    var imageData = base64.replace(/^data:image\/\w+;base64,/, "");
    // 获取小程序的文件系统
    const fsm = wx.getFileSystemManager();
    fsm.readdir({
      dirPath: `${wx.env.USER_DATA_PATH}`, /// 获取文件列表
      success (res) {
        console.log(res)
        res.files.forEach((val) => { // 遍历文件列表里的数据
          fsm.unlink({
            filePath: `${wx.env.USER_DATA_PATH}/${val}`
          });
        })
        // 把数据写入到临时目录中
        fsm.writeFile({
          filePath: path,
          data: imageData,
          encoding: 'base64',
          success: (res) => {
            console.log(res);
            wx.saveImageToPhotosAlbum({
              filePath: path,
              success: function (res) {
                cb && cb(res);
              },
              fail: function (res) {
                console.log(res)
                wx.getSetting({
                  success: function (res) {
                    if (!res.authSetting['scope.writePhotosAlbum']) {
                      that.showModal('是否打开设置页面', '需要获取您的相册权限，请到小程序的设置页面打开授权', function () {
                        wx.openSetting({
                          success: function (res) {

                          }
                        })
                      })
                    }
                  }
                })
              },
            })
          },
          fail: (err) => {
            console.log('base err', err);
            wx.showToast({
              title: '保存失败',
              icon: 'none',
            })
          }
        });
      },
      fail (err) {
        console.log(err)
      }
    })
  },
  getColors () {
    let helpColor = cache.getCache("main_colors");
    let mainColor = cache.getCache("help_colors");
    var colors = {
      commonColor: helpColor, //辅色,
      comColor: mainColor, // 主色
      somColor: helper.colorRgb(mainColor, 0.2), //标签背景渐变色,
      linColor: helper.colorRgb(mainColor, 0.8), //活动，下载海报按钮背景渐变色
      comColor1: helper.colorRgb(mainColor, 1), //主色rgba
      borColor: helper.colorRgb(mainColor, 0.4), //item优惠券边框颜色
      speColor: helper.colorRgb(mainColor, 0.1), //预售背景色,
    }
    return colors;
  }
}

module.exports = helper;