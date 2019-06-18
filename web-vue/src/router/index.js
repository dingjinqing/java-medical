import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const login = r => require.ensure([], () => r(require('@/view/admin/login')), 'adminLogin')

export default new Router({
  routes: [
    {
      path: '/',
      component: login
    },
    {
      path: '/admin/login',
      name: 'adminLogin',
      component: login
    }
  ]
})
