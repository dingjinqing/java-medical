// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
/* eslint-disable */
import Vue from 'vue';
import App from './App';
import router from './router';
import store from './store';
import i18n from './i18n/i18n';
import vueSwiper from 'vue-awesome-swiper';
import ElementUI from 'element-ui';
import Distpicker from 'v-distpicker';
import vuescroll from 'vuescroll';
import 'vuescroll/dist/vuescroll.css';
Vue.use(vuescroll);
// 富文本编辑器
import tinymce from 'vue-tinymce-editor';
// 颜色选择器
import vcolorpicker from 'vcolorpicker';
import 'swiper/dist/css/swiper.css';
import 'element-ui/lib/theme-chalk/index.css';
import 'font-awesome/css/font-awesome.min.css';
import myMixins from '@/components/mixins.js';
// 全局flag
localStorage.setItem('V-overallFlag', true);
Vue.prototype.$imageHost = 'http://jmpdevimg.weipubao.cn';
Vue.prototype.$imageHostDev = `@/assets/image/admin/`;
Vue.component('v-distpicker', Distpicker);
// 全局事件总线
Vue.prototype.$http = new Vue();

Vue.use(vcolorpicker);
Vue.use(ElementUI);
Vue.use(vueSwiper);
Vue.config.productionTip = false;
Vue.mixin(myMixins);
Vue.component('tinymce', tinymce);

new Vue({
  el: '#app',
  i18n,
  store,
  router,
  components: { App },
  template: '<App/>'
});

// /image/admin/sort_moren.png
