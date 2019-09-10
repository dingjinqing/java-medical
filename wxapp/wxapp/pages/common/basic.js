var util = require("../../utils/util.js");
var common_basic = require("../../common/mixins/basic.js");

function page_event() {
  var e = {};
  var methods = ['onLoad', 'onReady', 'onShow', 'onHide', 'onUnload', 'onPullDownRefresh', 'onReachBottom', 'onShareAppMessage', 'onPageScroll', 'onResize'];
  for (var i in methods) {
    var m = methods[i];
    (function(m) {
      e[m] = function() {
        for (var i in this._v_components) {
          var c = this._v_components[i];
          if (typeof c[m] == 'function')
            c[m].apply(c, arguments);
        }
      };
    }(m));
  }
  return e;
}

var basic = {
  addComponent(component) {
    this._v_components = this._v_components || [];
    this._v_components.push(component);
  },
  removeComponent(component) {
    if (this._v_components) {
      for (var i in this._v_components) {
        if (this._v_components[i] == component) {
          delete this._v_components[i];
        }
      }
    }
  },
  /**
   * 调用组件方法
   * 
   * @param {string} name 组件名称对应组件中name属性，支持*，如 test* 代表 test 开头的名称 
   * @param {string} method_name 方法名
   * @param {Array} args 参数列表
   */
  callComponentMethod(name, method_name, args) {
    if (this._v_components) {
      for (var i in this._v_components) {
        var c = this._v_components[i];
        var match = false;
        name = name.replace("*", ".*");
        if (name.indexOf("*") !== -1) {
          var reg = new RegExp("^" + name + "$", "gim");
          match = reg.test(c.data.name);
        } else {
          match = c.data.name == name;
        }
        if (match) {
          if (typeof c[method_name] == 'function') {
            c[method_name].apply(c, args);
          }
        }
      }
    }
  },
  ...page_event(), // 添加页面默认事件，处理组件相应事件
  ...common_basic,
};

module.exports = basic;