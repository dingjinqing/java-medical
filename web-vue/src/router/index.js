import Vue from 'vue'
import Router from 'vue-router'
// 引入其他路由文件
import indexRoutes from '@/router/index/index'
Vue.use(Router)

const login = r => require.ensure([], () => r(require('@/view/admin/login')), 'adminLogin')
const indexlogin = r => require.ensure([], () => r(require('@/components/index/login')), 'indexLogin')
const systemlogin = r => require.ensure([], () => r(require('@/components/system/login')), 'systemLogin')
const baseRoutes = [
  {
    path: '/',
    component: login,
    redirect: '/index/home/main'
  },
  {
    path: '/index/login',
    name: 'indexLogin',
    component: indexlogin
  },
  {
    path: '/admin/login',
    name: 'adminLogin',
    component: login
  },
  {
    path: '/system/login',
    name: 'systemLogin',
    component: systemlogin
  }
]
const routes = baseRoutes.concat(baseRoutes, indexRoutes)
export default new Router({
  routes
})
