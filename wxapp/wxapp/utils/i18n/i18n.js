
var info = require("./info.js")
var cache = require("../base/cache.js")

var i18n = {
  zh_CN: {
    ...info.zh_CN,
  },
  en_US: {
    ...info.en_US,
  }
};

function _defaultLang() {
  return "zh_CN";
}

function cacheLang(lang) {
  cache.setCache("V-LANG", lang);
}

function lang() {
  var lang = cache.getCache("V-LANG") || _defaultLang();
  if (i18n[lang] == undefined) {
    lang = _defaultLang();
  }
  return lang;
}

function trans(code) {
  return transLang(lang(), code);
}

function transLang(lang, code) {
  return i18n[lang][code] || i18n[_defaultLang()][code];
}

module.exports = {
  lang: lang,
  trans: trans,
  transLang: transLang,
  cacheLang: cacheLang
};