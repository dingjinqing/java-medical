import Vue from 'vue'
import Router from 'vue-router'
// import Cookies from 'js-cookie'
import store from '../store' // 引入vuex实例对象
// 引入其他路由文件
import indexRoutes from '@/router/index/index'
import {
  loadLanguageAsync
} from '@/i18n/i18n.js'
Vue.use(Router)

const baseRoutes = [{
  path: '/',
  component: () => import('@/view/index/home'),
  meta: {
    defaultTurn: true
  }
},
{
  path: '/admin/index/login',
  name: 'indexLogin',
  component: () => import('@/components/index/login')
},
{
  path: '/system/index',
  redirect: '/system/system/login'
},
{
  path: '/home/index',
  redirect: '/admin/index/login'
},
{
  path: '*',
  name: 'NotFount',
  component: () => import('@/view/index/notFound')
}
]
const routes = baseRoutes.concat(
  indexRoutes,
  baseRoutes
)

const router = new Router({
  mode: 'history',
  routes
})
// 全局路由守卫
// router.beforeEach((to, from, next) => {
//   const nextRoute = ['shopMain', 'shop_view'] // 需要登录的页面
//   let token = Cookies.get('V-Index-Token') // 判断是否登录
//   if (nextRoute.indexOf(to.name) >= 0) {
//     // 检测是否登录的页面
//     console.log(token)
//     console.log(to)
//     if (token) {
//       next()
//     } else {
//       // 如果没有登录你访问的不是login就让你强制跳转到login页面
//       if (to.path !== '/admin/index/login') {
//         next({
//           path: '/admin/index/login'
//         })
//       }
//     }
//   } else {
//     next()
//   }
// })

// 路由全局到达后钩子
router.afterEach((to, from) => {
  store.commit('UPDATE_BREADCRUMB_TITLE', to.meta.title)
})

router.beforeEach((to, from, next) => {
  // let lang = localStorage.getItem('WEPUBAO_LANGUAGE')

  let lang = localStorage.getItem('WEPUBAO_LANGUAGE')
  if (lang === 'en_US') {
    lang = 'en'
  } else {
    lang = 'cn'
  }
  console.log(to)
  console.log(lang)
  loadLanguageAsync(lang).then(() => {
    next()
  })
})
export default router
