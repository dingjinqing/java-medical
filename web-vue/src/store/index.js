import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import leftnav from './modules/admin/leftNav'
import leftsysnav from './modules/system/leftsysNav'
import smallProcedures from './modules/admin/smallProcedures'
import smallProgramManagement from './modules/admin/smallProgramManagement'
import crumbs from './modules/admin/crumbs'
Vue.use(Vuex)
export default new Vuex.Store({
  modules: {
    leftnav,
    leftsysnav,
    smallProcedures,
    smallProgramManagement,
    crumbs
  },
  getters
})
