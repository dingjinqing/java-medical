import Vue from 'vue'
import Router from 'vue-router'
// 引入其他路由文件
import indexRoutes from '@/router/index/index'
import adminRoutes from '@/router/admin/index'
Vue.use(Router)

const indexlogin = r => require.ensure([], () => r(require('@/components/index/login')), 'indexLogin')
const systemlogin = r => require.ensure([], () => r(require('@/components/system/login')), 'systemLogin')
const baseRoutes = [
  {
    path: '/',
    redirect: '/index/home/main'
  },
  {
    path: '/index/login',
    name: 'indexLogin',
    component: indexlogin
  },
  {
    path: '/system/login',
    name: 'systemLogin',
    component: systemlogin
  }
]
const routes = baseRoutes.concat(baseRoutes, indexRoutes, adminRoutes)
export default new Router({
  routes
})
