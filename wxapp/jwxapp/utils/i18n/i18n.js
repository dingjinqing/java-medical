
var cache = require("../base/cache.js")
var config = require('../config.js');
var zhCnLocalePack = require("./zh_CN.js")

var i18n = {
  zh_CN: zhCnLocalePack
};

function defaultLocale() {
  return config.shopLanguage;
}

function setLocale(locale) {
  cache.setCache("V-Locale", locale);
}

function getLocale() {
  var locale = cache.getCache("V-Locale") || defaultLocale();
  return locale;
}

function setLocalePack(locale, localePack) {
  var cacheKey = "V-Locale-PACK-" + locale;
  i18n[locale] = localePack;
  cache.setCache(cacheKey, localePack)
}

function getLocalePack(locale) {
  if (!i18n[locale]) {
    var cacheKey = "V-Locale-PACK-" + locale;
    var localePack = cache.getCache(cacheKey)
    if (localePack) {
      i18n[locale] = localePack;
    }
  }
  return i18n[locale] || i18n[defaultLocale()]
}

/**
 * 当前语言转换
 */
function trans(code, params = {}) {
  var locale = getLocale();
  var result = transLocale(locale, code, params);
  if (result == undefined && locale != 'zh_CN') {
    result = transLocale('zh_CN', code, params);
  }
  return result;
}


/**
 * 例子： cn = {
 * common: [{
 * title:{
 *  info:'hello {value}'
 * }
 * }]
 * }
 * 
 * transLocale("zh_CN","common.title[0].info",{value:"hello"})
 * 
 * @param {string} locale
 * @param {string} code 例子 common.title[0].info
 * @param {object} params
 */
function transLocale(locale, code, params = {}) {
  var val = getLocalePack(locale);
  if (!val) return undefined;
  params = params || {};
  var i = 0;
  var last = 0;
  while (i < code.length) {
    var c = code.charAt(i);
    var isLastC = i == code.length - 1;
    var isSegC = c == '.' || c == '[' || c == ']';
    if (isSegC || isLastC) {
      var key = code.substring(last, isSegC ? i : i + 1);
      if (c == ']') key = parseInt(key);
      val = val[key];
      if (val == undefined) return val;
      last = i + 1;
    }
    i++;
  }
  if (typeof val == 'string') {
    var result = "";
    i = 0;
    last = 0;
    var inBrackets = false;
    while (i < val.length) {
      var c = val.charAt(i);
      if (c == '{') {
        last = i + 1;
        inBrackets = true;
      } else if (inBrackets && c == '}') {
        var key = val.substring(last, i).trim();
        var value = params[key] || "";
        result += value;
        inBrackets = false;
      } else if (!inBrackets) {
        result += c;
      }
      i++;
    }
    val = result;

  }
  return val;
}

module.exports = {
  defaultLocale,
  setLocale,
  getLocale,
  setLocalePack,
  getLocalePack,
  trans,
};