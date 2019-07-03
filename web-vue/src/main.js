// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import i18n from './i18n/i18n'
import vueSwiper from 'vue-awesome-swiper'
import ElementUI from 'element-ui'
import 'swiper/dist/css/swiper.css'
import 'element-ui/lib/theme-chalk/index.css'
import 'font-awesome/css/font-awesome.min.css'
import myMixins from '@/components/mixins.js'
Vue.prototype.$http = new Vue()
Vue.prototype.$imageHost = localStorage.getItem('V-ImageHost')
Vue.use(ElementUI)
Vue.use(vueSwiper)
Vue.config.productionTip = false
Vue.mixin(myMixins)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  i18n,
  router,
  components: { App },
  template: '<App/>'
})
