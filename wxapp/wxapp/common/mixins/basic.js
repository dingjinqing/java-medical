var util = require("../../utils/util.js");
var basic = {
  getRect(selector, all) {
    var _this = this;
    return new Promise(function (resolve) {
      wx.createSelectorQuery().in(_this)[all ? 'selectAll' : 'select'](selector).boundingClientRect(function (rect) {
        if (all && Array.isArray(rect) && rect.length) {
          resolve(rect);
        }
        if (!all && rect) {
          resolve(rect);
        }
      }).exec();
    });
  },
  eventData(event, key) {
    var d = event.currentTarget.dataset;
    if (key == undefined) return d;
    return d[key];
  },
  bindJumpLink(e) {
    var d = this.eventData(e);
    if (d.skip) return false;
    if (d.url) {
      util.jumpLink(d.url, d.linkType);
    }
  },
  bindPreviewImage(e) {
    var d = this.eventData(e);
    if (d.skip) return false;
    if (d.src) {
      wx.previewImage({
        current: d.src,
        urls: [d.src],
      })
    }
  },
  // type = interval ||  timeout
  createTimer(type, name, cb, seps = 1000) {
    this.killTimer(type, name);
    this._timers = this._timers || {};
    name = "interval_" + name;
    var fn = (type == 'interval') ? setInterval : setTimeout;
    this._timers[name] = fn(function () {
      cb();
    }, seps);
  },
  // type = interval ||  timeout
  killTimer(type, name) {
    name = type + "_" + name;
    if (this._timers && this._timers[name]) {
      var fn = (type == 'interval') ? clearInterval : clearTimeout;
      fn(this._timers[name]);
    }
  },
  clearTimers() {
    if (this._timers) {
      for (var name in this._timers) {
        var t = name.split("_");
        this.killTimer(t[0], t[1]);
        delete this._timers[name];
      }
    }
  },
  $set(data, cb) {
    if (typeof data == "string") {
      if (this.data[data]) {
        var o = {};
        o[data] = this.data[data];
        this.setData(o, cb);
      }
    } else if (typeof data == "object") {
      this.setData(data, cb);
    } else {
      this.setData(this.data || {}, cb);
    }
  },
}
module.exports = basic;