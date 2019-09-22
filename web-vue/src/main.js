// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
/* eslint-disable */
import Vue from 'vue';
import App from './App';
import router from './router';
import store from './store';
import i18n from './i18n/i18n';
import ElementUI from 'element-ui';
import DonMessage from './util/singleMessage'

// 格式化时间
import Moment from 'moment';
// 阿里图标
// import './assets/aliIcon/iconfont.css';
import 'element-ui/lib/theme-chalk/index.css';
import 'font-awesome/css/font-awesome.min.css';
import myMixins from '@/components/mixins.js';
// 引入基于 Vue2.0 和 echarts 封装的 v-charts 图表组件
import VCharts from 'v-charts';
Vue.use(VCharts);
Vue.prototype.$imageHost = 'http://jmpdevimg.weipubao.cn';
Vue.prototype.$imageHostDev = `@/assets/image/admin/`;
// 全局事件总线
Vue.prototype.$http = new Vue();
// Vue.prototype.$t = (key, value) => i18n.t(key, value);
Vue.use(ElementUI);
Vue.prototype.$message = new DonMessage()
Vue.config.productionTip = false;
Vue.mixin(myMixins);
// 全局引用Moment
Vue.prototype.moment = Moment;
let vm = new Vue({
  el: '#app',
  router,
  i18n,
  store,
  components: { App },
  template: '<App/>'
});

export default vm
// /image/admin/sort_moren.png
