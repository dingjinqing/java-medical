// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import i18n from './i18n/i18n'
import vueSwiper from 'vue-awesome-swiper'
import ElementUI from 'element-ui'
import Distpicker from 'v-distpicker'
// el-dialog 可拖拽
import './directive/index'
// 颜色选择器
import vcolorpicker from 'vcolorpicker'
import 'swiper/dist/css/swiper.css'
import 'element-ui/lib/theme-chalk/index.css'
import 'font-awesome/css/font-awesome.min.css'
import myMixins from '@/components/mixins.js'
import VueCropper from 'vue-cropper'
// 全局flag
localStorage.setItem('V-overallFlag', true)
Vue.prototype.$imageHost = 'http://mpimg2.weipubao.cn'
Vue.use(VueCropper)

Vue.component('v-distpicker', Distpicker)
Vue.prototype.$http = new Vue()

Vue.use(vcolorpicker)
Vue.use(ElementUI)
Vue.use(vueSwiper)
Vue.config.productionTip = false
Vue.mixin(myMixins)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  i18n,
  store,
  router,
  components: { App },
  template: '<App/>'
})
