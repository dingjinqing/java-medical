import Vue from 'vue'
import locale from 'element-ui/lib/locale'
import VueI18n from 'vue-i18n'
import messages from './langs'

Vue.use(VueI18n)
const i18n = new VueI18n({
  locale: localStorage.lang || 'cn',
  messages,
  silentTranslationWarn: true
})
locale.i18n((key, value) => i18n.t(key, value))

// const loadedLanguages = [] // 我们已經加載的语言
// function setI18nLanguage (lang) {
//   i18n.locale = lang
//   return lang
// }
// export function loadLanguageAsync (lang) {
//   if (i18n.locale !== lang) {
//     if (!loadedLanguages.includes(lang)) {
//       /* webpackChunkName: "lang-[request]" */
//       return import(`@/assets/lang/${lang}`).then(msgs => {
//         i18n.setLocaleMessage(lang, msgs)
//         loadedLanguages.push(lang)
//         return setI18nLanguage(lang)
//       })
//     }
//     return Promise.resolve(setI18nLanguage(lang))
//   }
//   return Promise.resolve(lang)
// }
export default i18n
