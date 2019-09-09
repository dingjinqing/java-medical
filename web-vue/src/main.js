// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
/* eslint-disable */
import Vue from 'vue';
import App from './App';
import router from './router';
import store from './store';
import i18n from './i18n/i18n';
import ElementUI from 'element-ui';
// 阿里图标
import './assets/aliIcon/iconfont.css';
import 'swiper/dist/css/swiper.css';
import 'element-ui/lib/theme-chalk/index.css';
import 'font-awesome/css/font-awesome.min.css';
import myMixins from '@/components/mixins.js';
// 全局flag
localStorage.setItem('V-overallFlag', true);
Vue.prototype.$imageHost = 'http://jmpdevimg.weipubao.cn';
Vue.prototype.$imageHostDev = `@/assets/image/admin/`;
// 全局事件总线
Vue.prototype.$http = new Vue();
// Vue.prototype.$t = (key, value) => i18n.t(key, value);
Vue.use(ElementUI);
Vue.config.productionTip = false;
Vue.mixin(myMixins);

new Vue({
  el: '#app',
  i18n,
  store,
  router,
  components: { App },
  template: '<App/>'
});

// /image/admin/sort_moren.png
