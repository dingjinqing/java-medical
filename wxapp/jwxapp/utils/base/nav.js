var config = require('../config.js');
var cache = require('./cache.js');
var pathConfig = require('./path.js');

var nav = {
  getUrl (path) {
    path = path || "";
    path = path.indexOf("/") === 0 ? path.substr(1) : path;
    var schema = config.schema ? config.schema : "https";
    return schema + "://" + config.main_host + "/" + path;
  },
  getImageUrl (path) {
    path = path || "";
    path = path.indexOf("/") === 0 ? path.substr(1) : path;
    return "http://" + config.image_host + "/" + path;
  },
  toMiniProgram (appId, path = '', extraData = {}, envVersion = '') {
    util.jumpLink({
      appId: appId,
      path: path,
      extraData: extraData,
      envVersion: envVersion
    });
  },
  switchTab (o) {
    return this.jumpLink(o, "switchTab");
  },
  reLaunch (o) {
    return this.jumpLink(o, "reLaunch");
  },
  redirectTo (o) {
    return this.jumpLink(o, "redirectTo");
  },
  navigateTo (o) {
    return this.jumpLink(o, "navigateTo");
  },
  jumpLink (url, linkType, success, fail, complete) {
    var o = {};
    if (typeof url == "string" && url.indexOf("{") === 0) {
      var linkObj = JSON.parse(url);
      linkObj.appId = linkObj.appId || linkObj.appid; // 兼容
      linkObj.path = linkObj.path || linkObj.link_path; // 兼容
      if (linkObj.appId && linkObj.path) {
        return wx.navigateToMiniProgram(linkObj);
      }
      url = linkObj.path;
    }

    linkType = linkType || 'navigateTo';
    if (!wx[linkType]) linkType = 'navigateTo';
    if (linkType == 'navigateTo' && getCurrentPages().length >= 10) {
      linkType = 'redirectTo';
    }
    if (typeof url == 'string') {
      if (url) {
        if (url.indexOf("https://") === 0) {
          url = '/pages/webview/webview?url=' + encodeURIComponent(url);
        }
      }
      o.url = url;
    } else if (typeof url == 'object') {
      o = url;
    }
    o.url = this.validUrl(o.url); // be this.validUrl(o.url) after added subPackages;
    if (success) o.success = success;
    if (fail) o.fail = fail;
    if (complete) o.complete = complete;
    return wx[linkType](o);
  },
  getCurrentPage () {
    var pages = getCurrentPages();
    return pages.length > 0 ? pages[pages.length - 1] : null;
  },
  getPath (path, query) {
    path = path.indexOf("/") === 0 ? path : "/" + path;
    var params = [];
    for (var i in query) {
      params.push(i + '=' + encodeURIComponent(query[i]));
    }
    return path + (params.length > 0 ? "?" + params.join("&") : "");
  },
  getCurrentPath (options) {
    var pages = getCurrentPages();
    var path = pages.length > 0 ? pages[pages.length - 1].route : null;
    return this.getPath(path, options);
  },

  absolutePageUrl (url) {
    url = url || "";
    if (url && url.indexOf("../../") === 0) url = url.substr(5);
    if (url && url.indexOf("../") === 0) url = "/pages/" + url.substr(3);
    if (url && url.indexOf("/") !== 0) url = "/" + url;
    return url;
  },
  validUrl (url) {
    url = this.absolutePageUrl(url);
    var o = this.parseUrl(url);
    var arr = o.path.split("/");
    for (var root in pathConfig) {
      if (arr[1] != root && pathConfig[root].indexOf(arr[2] + "/" + arr[3]) !== -1) {
        return url.replace("/" + arr[1] + "/", '/' + root + '/');
      }
    }

    return url;
  },
  parseUrl (url) {
    url = this.absolutePageUrl(url);
    var url_arr = url.split("?");
    var params = !url_arr[1] ? [] : url_arr[1].split("&");
    var query = {};
    for (var i in params) {
      var t = params[i].split("=")
      query[t[0]] = decodeURIComponent(t[1] || "");
    }
    return {
      path: url_arr[0],
      query: query
    }
  },
  equalUrl (url1, url2, fullCompare) {
    if (typeof url1 == 'string' && typeof url2 == 'string') {
      url1 = this.parseUrl(url1);
      url2 = this.parseUrl(url2);
    }
    if (url1.path != url2.path) return false;
    if (!fullCompare) return true;

    if (Array.isArray(fullCompare) && fullCompare.length > 0) {
      for (var i in fullCompare) {
        var param = fullCompare[i];
        if (url1.query[param] != url2.query[param]) return false;
      }
      return true;
    }

    if (url1.query.length != url2.query.length) return false;
    for (var i in query1) {
      if (url1.query[i] != url2.query[i]) return false;
      if (query1[i] != query2[i]) return false;
    }
    return true;
  },

  matchParams (query1, query2, mustKeys) {
    var matchNumber = 0;
    var matcheKeys = 0;
    for (var key in query1) {
      if (query1[key] == query2[key]) {
        if (mustKeys.indexOf(key) != -1) matcheKeys++;
        matchNumber++;
      }
    }
    return mustKeys.length == matcheKeys ? matchNumber : -1;
  },
  inPaths (url, paths) {
    for (var i in paths) {
      if (this.equalUrl(paths[i], url, false)) {
        return i;
      }
    }
    return -1;
  },

  jumpToWeb (path, param) {
    if (typeof param == 'object') {
      let paramStr = '';
      for (let k in param) {
        paramStr += '&' + k + '=' + encodeURIComponent(param[k]);
      }
      param = paramStr;
    }
    if (typeof param == 'undefined') {
      param = '';
    }
    let reg = /navHeight/
    let flag = reg.test(this.getUrl(path))
    console.log(this.getUrl(path), flag)
    let params = flag ? '&' : '?'
    var url = this.getUrl(path) + params + "shop_id=" + config.shop_id + "&user_id=" + cache.getCache('user_id') + param;
    this.navigateTo({
      url: '/pages/webview/webview?url=' + encodeURIComponent(url),
    })
    console.log(url)
  }
}
module.exports = nav;