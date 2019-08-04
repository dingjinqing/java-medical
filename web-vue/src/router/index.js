import Vue from 'vue'
import Router from 'vue-router'
import Cookies from 'js-cookie'
import store from '../store' // 引入vuex实例对象

// 引入其他路由文件
import indexRoutes from '@/router/index/index'
import adminRoutes from '@/router/admin/index'
import systemRouters from '@/router/system/index'
Vue.use(Router)

const indexlogin = () => import('@/components/index/login')
const systemlogin = () => import('@/components/system/login')
const cropperDome = () => import('@/components/admin/cropperDome')
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
  },
  {
    path: '/admin/cropperDome',
    name: 'cropperDome',
    component: cropperDome
  }
]
const routes = baseRoutes.concat(baseRoutes, indexRoutes, adminRoutes, systemRouters)

const router = new Router({
  routes
})
// 全局路由守卫
router.beforeEach((to, from, next) => {
  const nextRoute = ['shopMain'] // 需要登录的页面
  let token = Cookies.get('V-Token') // 判断是否登录
  if (nextRoute.indexOf(to.name) >= 0) { // 检测是否登录的页面
    if (token) {
      next()
    } else {
      // 如果没有登录你访问的不是login就让你强制跳转到login页面
      if (to.path !== '/index/login') {
        next({ path: '/index/login' })
      } else {

      }
    }
  } else {
    next()
  }
})

// 路由全局到达后钩子
router.afterEach((to, from) => {
  store.commit('UPDATE_BREADCRUMB_TITLE', to.meta.title)
})

export default router
