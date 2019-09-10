
/**
 * 解析选项
 */
function parseOptions(opts, lifecycle, props_a, setting) {

  setting.lifecycle.forEach(name => {
    var obj = name.length == 1 ? opts : opts[name[0]];
    var n = name.length == 1 ? name[0] : name[1];
    if (obj && typeof obj[n] == 'function') {
      var key = name.join(".");
      lifecycle[key] = (lifecycle[key] || []).concat(obj[n]);
    }
  });

  var reps = {};
  for (var key in setting.maps) {
    var rep = setting.maps[key];
    var names = key.split(".");
    var fn = opts[names[0]] && typeof opts[names[0]][names[1]] == 'function' ? opts[names[0]][names[1]] : opts[rep];
    if (fn)  {
      lifecycle[rep] = (lifecycle[rep] || []).concat(fn);
    }
  }

  setting.props_a.forEach(name => {
    if (opts[name]) {
      props_a[name] = (props_a[name] || []).concat(Array.isArray(opts[name]) ? opts[name] : [opts[name]]);
    }
  });
}


/**
 * 合并选项
 */
function merge(options, setting) {

  function mixins(opts) {
    if (Array.isArray(opts.mixins)) {
      var mixes = [];
      opts.mixins.forEach(opt => {
        mixes = mixes.concat(mixins(opt));
      });
      return mixes.concat([opts]);
    }
    return [opts];
  }

  var lifecycle = {};
  var props_a = {};
  var all_opts = mixins(options);
  all_opts.forEach(opt => {
    parseOptions(opt, lifecycle, props_a, setting);
  })

  var scrOpts = {};

  all_opts.forEach(opt => {
    for (var key in opt) {
      if (scrOpts[key] && setting.props_o.indexOf(key) != -1) {
        scrOpts[key] = Object.assign({}, scrOpts[key], opt[key]);
      } else {
        scrOpts[key] = opt[key];
      }
    }
  });


  for (var key in setting.maps) {
    var rep = setting.maps[key];
    var names = key.split(".");
    if (scrOpts[names[0]] && typeof scrOpts[names[0]][names[1]] == 'function') {
      delete scrOpts[names[0]][names[1]];
    }
  }

  var keys = Object.keys(lifecycle);
  keys.forEach(key => {
    (function (key) {
      var names = key.split(".");
      scrOpts[names[0]] = {};
      var obj = names.length == 1 ? scrOpts : scrOpts[names[0]];
      var n = names.length == 1 ? names[0] : names[1];
      obj[n] = function () {
        for (var i in lifecycle[key]) {
          var ret = lifecycle[key][i].apply(this, arguments);
          if (ret === false) return false;
        }
      };
    })(key);
  });
  keys = Object.keys(props_a);
  keys.forEach(key => {
    scrOpts[key] = props_a[key];
  });

  scrOpts.$ref = {
    all_opts: all_opts,
    lifecycle: lifecycle,
    props_a: props_a
  };

  return scrOpts;
}

module.exports = merge;
