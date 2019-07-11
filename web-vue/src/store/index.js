import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'

import leftnav from './modules/admin/leftNav'
Vue.use(Vuex)
export default new Vuex.Store({
  modules: {
    leftnav
  },
  getters
})
