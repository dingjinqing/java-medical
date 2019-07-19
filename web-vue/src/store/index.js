import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'

import leftnav from './modules/admin/leftNav'
import leftsysnav from './modules/system/leftsysNav'
import smallProcedures from './modules/admin/smallProcedures'
import smallProgramManagement from './modules/admin/smallProgramManagement'
Vue.use(Vuex)
export default new Vuex.Store({
  modules: {
    leftnav,
    leftsysnav,
    smallProcedures,
    smallProgramManagement
  },
  getters
})
