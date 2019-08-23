
/**
 * 配置事件的选项默认回调
 */
export function lifecycleEventOptions(setting, cb) {
  var options = {};
  setting.lifecycle.forEach(name => {
    options[name[0]] = {};
    var obj = name.length == 1 ? options : options[name[0]];
    var n = name.length == 1 ? name[0] : name[1];
    var ev = name.length == 1 ? name[0] : name[0] + name[1].charAt(0).toUpperCase + name[1].substring(1);
    (function (obj, n, ev) {
      obj[n] = function () {
        cb.apply(this, ev, arguments);
      };
    })(obj, n, ev);
  });
  return options;
}


/**
 * 初始化选项
 */
export function initOptions(options, setting) {
  options.$lifecycle = {};
  setting.lifecycle.forEach(name => {
    options[name[0]] = {};
    options.$lifecycle[name.join(".")] = [];
    (function (name) {
      var obj = name.length == 1 ? options : options[name[0]];
      var n = name.length == 1 ? name[0] : name[1];
      obj[n] = function () {
        var args = arguments;
        var funcs = options.$lifecycle[name.join(".")];
        for (var i = 0; i < funcs.length; i++) {
          var ret = funcs[i].apply(this, arguments);
          if (ret === false) {
            return false;
          }
        }
      }
    }
    )(name);
  });

  setting.props_o.forEach(name => {
    options[name] = {};
  });

  setting.props_a.forEach(name => {
    options[name] = [];
  });

}

/**
 * 合并选项
 */
export function mergeOptions(scrOpts, options, setting) {
  if (Array.isArray(options.mixins)) {
    options.mixins.forEach(opt => {
      mergeOptions(scrOpts, opt, setting)
    });
  }

  // 处理有优先级的maps
  if (setting.maps) {
    for (var name in setting.maps) {
      var names = setting.maps[name];
      var obj = options[names[0]];
      var n = names[1];
      if (obj && typeof obj[n] == 'function') {
        scrOpts.$lifecycle[name].push(obj[n]);
        delete obj[n];
        if (typeof options[name] == 'function') {
          delete obj[name];
        }
      }
    }
  }

  setting.lifecycle.forEach(name => {
    var obj = name.length == 1 ? options : options[name[0]];
    var n = name.length == 1 ? name[0] : name[1];
    if (obj && typeof obj[n] == 'function') {
      scrOpts.$lifecycle[name.join(".")].push(obj[n]);
      delete obj[n];
    }
  });

  setting.props_a.forEach(name => {
    if (options[name]) {
      scrOpts[name] = (scrOpts[name] || []).concat(Array.isArray(options[name]) ? options[name] : [options[name]]);
      delete scrOpts[name];
    }
  });
  Object.assign(scrOpts, options);
}

