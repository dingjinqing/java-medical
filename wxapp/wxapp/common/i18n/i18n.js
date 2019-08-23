import { info } from "./info"
import { cache } from "../util/cache.js"
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

export function cacheLang(lang) {
  cache.setCache("V-LANG", lang);
}

export function lang() {
  var lang = cache.getCache("V-LANG") || _defaultLang();
  if (i18n[lang] == undefined) {
    lang = _defaultLang();
  }
  return lang;
}

export function trans(code) {
  return transLang(lang(), code);
}

export function transLang(lang, code) {
  return i18n[lang][code] || i18n[_defaultLang()][code];
}
