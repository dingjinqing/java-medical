import Vue from 'vue'
import locale from 'element-ui/lib/locale'
import VueI18n from 'vue-i18n'
// import store from '../store'
// import messages from '@/i18n/langs/cn'
Vue.use(VueI18n)
const i18n = new VueI18n({
  locale: '',
  silentTranslationWarn: true
  // fallbackLocale: 'cn',
})
locale.i18n((key, value) => i18n.t(key, value))

const loadedLanguages = [] // 我们已經加載的语言
function setI18nLanguage (lang) {
  console.log(lang)
  i18n.locale = lang
  document.querySelector('html').setAttribute('lang', lang)
  return lang
}
export function loadLanguageAsync (lang) {
  console.log(i18n.locale, lang, loadedLanguages)
  if (i18n.locale === lang) {
    return Promise.resolve(setI18nLanguage(lang))
  }
  if (loadedLanguages.includes(lang)) {
    return Promise.resolve(setI18nLanguage(lang))
  }
  console.log(123)
  /* webpackChunkName: "lang-[request]" */
  return import(`@/i18n/langs/${lang}`).then(msgs => {
    console.log(msgs)
    i18n.setLocaleMessage(lang, msgs.default)
    loadedLanguages.push(lang)
    return setI18nLanguage(lang)
  })
}
export default i18n
